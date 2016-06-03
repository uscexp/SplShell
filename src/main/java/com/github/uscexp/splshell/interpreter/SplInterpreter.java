/*
 * Copyright (C) 2014 -2015 by haui - all rights reserved
 */
package com.github.uscexp.splshell.interpreter;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.github.fge.grappa.Grappa;
import com.github.uscexp.blockformatpropertyfile.PropertyFile;
import com.github.uscexp.blockformatpropertyfile.PropertyStruct;
import com.github.uscexp.blockformatpropertyfile.exception.PropertyFileException;
import com.github.uscexp.grappa.extension.interpreter.AstInterpreter;
import com.github.uscexp.grappa.extension.interpreter.ProcessStore;
import com.github.uscexp.grappa.extension.nodes.AstTreeNode;
import com.github.uscexp.grappa.extension.parser.Parser;
import com.github.uscexp.splshell.exception.SplShellException;
import com.github.uscexp.splshell.parser.SplParser;

/**
 * @author  haui
 */
public class SplInterpreter {

    private static final String ARGUMENTS = "args";

    private static final String APP_DIR = ".splshell";

    public static final String GLOBAL_MAPPED_METHOD_DEFINITION_MAP = "GLOBAL_MAPPED_METHOD_DEFINITION_MAP";

    protected final static String MAPPED_METHOD_FILE = "MethodAliases.def";

    private SplParser parser;

    private static SplInterpreter instance = new SplInterpreter();

    private SplInterpreter() {
        parser = Grappa.createParser(SplParser.class);
    }

    public static SplInterpreter getInstance() {
        return instance;
    }

    public void executeFromFile(String path, Charset encoding)
        throws SplShellException {
        executeFromFile(path, encoding, (Long) null);
    }

    public void executeFromFile(String path, Charset encoding, Long id)
        throws SplShellException {
        executeFromFile(path, encoding, (String[]) null, id);
    }

    public void executeFromFile(String path, Charset encoding, String[] args)
            throws SplShellException {
    	executeFromFile(path, encoding, args, null);
    }
    
    public void executeFromFile(String path, Charset encoding, String[] args, Long id)
        throws SplShellException {
        String input = null;
        try {
            input = readStringFromFile(path, encoding);
        } catch (Exception e) {
            throw new SplShellException(String.format("SplShell read file %s error!", path), e);
        }

        if (input != null)
            executeFromStringInput(input, args, id);
    }

    private String readStringFromFile(String path, Charset encoding)
        throws IOException {
        String input;
        byte[] encoded;
        encoded = Files.readAllBytes(Paths.get(path));
        input = new String(encoded, encoding);
        return input;
    }

    public void executeFromStringInput(String input)
        throws SplShellException {
        executeFromStringInput(input, (String[])null);
    }

	public void executeFromStringInput(String input, Long id)
			throws SplShellException {
		executeFromStringInput(input, (String[]) null, id);
	}

    public void executeFromStringInput(String input, String[] args)
        throws SplShellException {
    	executeFromStringInput(input, args, null);
    }
    
    public void executeFromStringInput(String input, String[] args, Long id)
        throws SplShellException {
        AstTreeNode<String> rootNode = Parser.parseInput(SplParser.class, parser.compilationUnit(), input, true);

        OutputStream astTreePrintStream = null;
        AstInterpreter<String> astInterpreter;

        astTreePrintStream = System.out;

        astInterpreter = new AstInterpreter<>(astTreePrintStream);

        Long internalId = new Date().getTime();
        if(id != null)
        	internalId = id;
        try {
            Map<String, MethodDefinition> methodDefinitionMap = loadMappedMethods();
            ProcessStore<Object> processStore = ProcessStore.getInstance(internalId);
            processStore.setGlobalVariable(GLOBAL_MAPPED_METHOD_DEFINITION_MAP, methodDefinitionMap);
            if (args != null) {
                processStore.setGlobalVariable(ARGUMENTS, args);
            }
            astInterpreter.interpretForewardOrder(SplParser.class, rootNode, internalId);
        } catch (Exception e) {
            throw new SplShellException("SplShell interpretation error!", e);
        } finally {
        	if(id == null)
        		astInterpreter.cleanUp(internalId);
        }
    }

    protected Map<String, MethodDefinition> loadMappedMethods()
        throws PropertyFileException, ReflectiveOperationException, IOException, URISyntaxException {
        Map<String, MethodDefinition> methodDefinitionMap = new HashMap<>();
        List<Path> paths = getMethodDefinitionFiles();

        for (int j = 0; j < paths.size(); ++j) {
            Path p = paths.get(j);

            PropertyFile propertyFile = new PropertyFile(p.toUri(), true);

            propertyFile.load();

            methodDefinitionMap.putAll(createMethodDefinitionMap(propertyFile));
        }
        return methodDefinitionMap;
    }

    private Map<String, MethodDefinition> createMethodDefinitionMap(PropertyFile propertyFile)
        throws ReflectiveOperationException {
        Map<String, MethodDefinition> methodDefinitionMap = new HashMap<>();
        Collection<?> values = propertyFile.getValueMap().values();

        if (values.size() > 0) {
            for (Iterator<?> it = values.iterator(); it.hasNext();) {
                PropertyStruct propertyStruct = (PropertyStruct) it.next();

                String method = propertyStruct.getName();
                String typeName = propertyStruct.stringValue("type");
                String methodName = propertyStruct.stringValue("realMethod");
                boolean statik = propertyStruct.booleanValue("static");
                Object[] paramDefs = (Object[]) propertyStruct.arrayValue("realParams");
                String returnType = propertyStruct.stringValue("returnType");

                MethodDefinition methodDefinition = new MethodDefinition(typeName, methodName, statik, paramDefs, returnType);

                methodDefinitionMap.put(method, methodDefinition);
            }
        }
        return methodDefinitionMap;
    }

    private List<Path> getMethodDefinitionFiles()
        throws IOException, URISyntaxException {
        List<Path> paths = new ArrayList<>();
        Path path = Paths.get(".").toAbsolutePath();
        getFilesFromDir(path, paths);
        if (paths.isEmpty()) {
            URI uri = Thread.currentThread().getContextClassLoader().getResource("").toURI();
            path = Paths.get(uri);
            getFilesFromDir(path, paths);
        }
        if (paths.isEmpty()) {
            URI uri = Thread.currentThread().getContextClassLoader().getResource(MAPPED_METHOD_FILE).toURI();
            path = Paths.get(uri);
            paths.add(path);
        }
        String homeDir = System.getProperty("user.home");
        if (homeDir != null) {
            homeDir = homeDir + APP_DIR;
            path = Paths.get(APP_DIR);

            if (!Files.exists(path)) {
                Files.createDirectory(path);
            }

            getFilesFromDir(path, paths);
        }
        return paths;
    }

    private String getFilesFromDir(Path path, List<Path> paths)
        throws IOException {
        String pattern = "*.def";
        DirectoryStream<Path> directoryStream = Files.newDirectoryStream(path, pattern);

        for (Path p : directoryStream) {
            paths.add(p);
        }
        return pattern;
    }
}
