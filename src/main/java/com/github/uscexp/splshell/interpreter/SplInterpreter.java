/*
 * Copyright (C) 2014 - 2026 by haui - all rights reserved
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
import java.util.Iterator;
import java.util.List;

import com.github.uscexp.blockformatpropertyfile.PropertyFile;
import com.github.uscexp.blockformatpropertyfile.PropertyStruct;
import com.github.uscexp.blockformatpropertyfile.exception.PropertyFileException;
import com.github.uscexp.parboiled.extension.interpreter.AstInterpreter;
import com.github.uscexp.parboiled.extension.interpreter.ProcessStore;
import com.github.uscexp.parboiled.extension.nodes.AstTreeNode;
import com.github.uscexp.parboiled.extension.parser.Parser;
import com.github.uscexp.splshell.exception.SplShellException;
import com.github.uscexp.splshell.parser.SplParser;
import org.parboiled.parser.Parboiled;

/**
 * Central entry point for parsing and executing SPL programs.
 * <p>
 * The interpreter is implemented as a singleton and reuses a single
 * {@link SplParser} instance for all executions. SPL source can either be read
 * from a file or passed directly as a string. The source is parsed into an AST
 * and then executed by an {@link AstInterpreter}.
 * </p>
 * <p>
 * Runtime state is scoped by a process id and stored in {@link ProcessStore}.
 * If no id is provided, a temporary id is generated for the current execution
 * and the runtime state is cleaned up afterwards. If an id is supplied, the
 * caller controls the lifecycle of the corresponding process state and can
 * inspect variables and registered methods after the execution completed.
 * </p>
 * <p>
 * Before the first execution for a process, built-in SPL callable aliases are
 * loaded from {@value #MAPPED_METHOD_FILE} and registered as reflective method
 * definitions.
 * </p>
 * <p>
 * In addition to the built-in alias file, users can provide their own
 * {@code *.def} files to extend the set of SPL-callable functions. These files
 * are discovered from the current working directory, from the classpath and
 * from the application specific directory {@code user.home/}{@value #APP_DIR}.
 * The latter is created automatically when it does not yet exist and serves as
 * a user-local extension point for custom alias definitions.
 * </p>
 * <p>
 * Alias definitions are stored as property blocks of the form
 * {@code method <aliasName> { ... }}. Each block declares the SPL-visible
 * alias name, the exposed SPL parameter types, the backing Java class, the real
 * Java method or constructor, whether the target is static, the Java parameter
 * types passed reflectively and the declared SPL return type. For instance
 * methods, the first SPL parameter represents the receiver object, while only
 * the remaining arguments are forwarded according to {@code realParams}.
 * </p>
 *
 * @author haui
 */
public class SplInterpreter {

	private static final String ARGUMENTS = "args";

	private static final String APP_DIR = ".splshell";

	/**
	 * Legacy key name for globally stored mapped method definitions.
	 */
	public static final String GLOBAL_MAPPED_METHOD_DEFINITION_MAP = "GLOBAL_MAPPED_METHOD_DEFINITION_MAP";

	/**
	 * Default definition file that contains SPL-to-Java method aliases.
	 * <p>
	 * The file uses property-style {@code method} blocks. A typical definition
	 * contains the keys {@code params}, {@code type}, {@code realMethod},
	 * {@code static}, {@code realParams} and {@code returnType}.
	 * </p>
	 */
	protected final static String MAPPED_METHOD_FILE = "MethodAliases.def";

	private SplParser parser;

	private OutputStream astTreePrintStream;

	private static SplInterpreter instance;

	/**
	 * Creates the singleton instance.
	 *
	 * @param astTreePrintStream optional output stream used by the AST interpreter
	 *                           for debug or tree output; may be {@code null}
	 */
	private SplInterpreter(OutputStream astTreePrintStream) {
		this.astTreePrintStream = astTreePrintStream;
		parser = Parboiled.createParser(SplParser.class);
	}

	/**
	 * Returns the singleton interpreter instance without AST output.
	 *
	 * @return the shared interpreter instance
	 */
	public static SplInterpreter getInstance() {
		return getInstance(null);
	}

	/**
	 * Returns the singleton interpreter instance.
	 * <p>
	 * The first invocation creates the instance and stores the provided output
	 * stream. Subsequent calls return the already created instance unchanged.
	 * </p>
	 *
	 * @param astTreePrintStream optional output stream for AST/debug output during
	 *                           interpretation
	 * @return the shared interpreter instance
	 */
	public static SplInterpreter getInstance(OutputStream astTreePrintStream) {
		if (instance == null) {
			instance = new SplInterpreter(astTreePrintStream);
		}
		return instance;
	}

	/**
	 * Reads SPL source from a file and executes it with an automatically managed
	 * process id.
	 *
	 * @param path path to the SPL source file
	 * @param encoding character encoding used to read the file
	 * @throws SplShellException if reading, parsing or execution fails
	 */
	public void executeFromFile(String path, Charset encoding)
			throws SplShellException {
		executeFromFile(path, encoding, (Long) null);
	}

	/**
	 * Reads SPL source from a file and executes it inside the given process.
	 * <p>
	 * Supplying an id allows the caller to reuse or inspect the associated
	 * {@link ProcessStore} after execution.
	 * </p>
	 *
	 * @param path path to the SPL source file
	 * @param encoding character encoding used to read the file
	 * @param id process id used to scope runtime state; may be {@code null}
	 * @throws SplShellException if reading, parsing or execution fails
	 */
	public void executeFromFile(String path, Charset encoding, Long id)
			throws SplShellException {
		executeFromFile(path, encoding, (String[]) null, id);
	}

	/**
	 * Reads SPL source from a file and executes it with command line arguments.
	 *
	 * @param path path to the SPL source file
	 * @param encoding character encoding used to read the file
	 * @param args arguments exposed to the SPL script as global {@code args}
	 * @throws SplShellException if reading, parsing or execution fails
	 */
	public void executeFromFile(String path, Charset encoding, String[] args)
			throws SplShellException {
		executeFromFile(path, encoding, args, null);
	}

	/**
	 * Reads SPL source from a file and executes it.
	 *
	 * @param path path to the SPL source file
	 * @param encoding character encoding used to read the file
	 * @param args arguments exposed to the SPL script as global {@code args}; may
	 *             be {@code null}
	 * @param id process id used to scope runtime state; if {@code null}, a
	 *           temporary process is created and cleaned up automatically
	 * @throws SplShellException if reading, parsing or execution fails
	 */
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

	/**
	 * Reads a complete text file into memory.
	 *
	 * @param path path to the file to read
	 * @param encoding character encoding used to decode the file content
	 * @return the decoded file content
	 * @throws IOException if the file cannot be read
	 */
	private String readStringFromFile(String path, Charset encoding)
			throws IOException {
		String input;
		byte[] encoded;
		encoded = Files.readAllBytes(Paths.get(path));
		input = new String(encoded, encoding);
		return input;
	}

	/**
	 * Parses and executes SPL source with an automatically managed process id.
	 *
	 * @param input complete SPL source code
	 * @throws SplShellException if parsing or execution fails
	 */
	public void executeFromStringInput(String input)
			throws SplShellException {
		executeFromStringInput(input, (String[]) null);
	}

	/**
	 * Parses and executes SPL source inside the given process.
	 *
	 * @param input complete SPL source code
	 * @param id process id used to scope runtime state; may be {@code null}
	 * @throws SplShellException if parsing or execution fails
	 */
	public void executeFromStringInput(String input, Long id)
			throws SplShellException {
		executeFromStringInput(input, (String[]) null, id);
	}

	/**
	 * Parses and executes SPL source with script arguments.
	 *
	 * @param input complete SPL source code
	 * @param args arguments exposed to the SPL script as global {@code args}
	 * @throws SplShellException if parsing or execution fails
	 */
	public void executeFromStringInput(String input, String[] args)
			throws SplShellException {
		executeFromStringInput(input, args, null);
	}

	/**
	 * Parses and executes SPL source code.
	 * <p>
	 * The method parses the given source into an AST, ensures that mapped SPL
	 * methods are available for the current process and then interprets the AST in
	 * forward order. If no process id is supplied, a temporary runtime context is
	 * created and removed in the {@code finally} block.
	 * </p>
	 *
	 * @param input complete SPL source code
	 * @param args arguments exposed to the SPL script as global {@code args}; may
	 *             be {@code null}
	 * @param id process id used to scope runtime state; if {@code null}, the
	 *           interpreter creates a temporary id for this invocation
	 * @throws SplShellException if parsing or execution fails
	 */
	public void executeFromStringInput(String input, String[] args, Long id)
			throws SplShellException {
		AstTreeNode<String> rootNode = Parser.parseInput(SplParser.class, parser.compilationUnit(), input, true);

		AstInterpreter<String> astInterpreter;

		astInterpreter = new AstInterpreter<>(astTreePrintStream);

		Long internalId = new Date().getTime();
		if (id != null)
			internalId = id;
		try {
			ProcessStore<Object> processStore = ProcessStore.getInstance(internalId);
			//            @SuppressWarnings("unchecked")
			//			Map<String, MethodDefinition> methodDefinitionMap = (Map<String, MethodDefinition>) processStore.getVariable(GLOBAL_MAPPED_METHOD_DEFINITION_MAP);
			if (processStore.getMethods().isEmpty()) {
				loadMappedMethods(processStore);
			}
			if (args != null) {
				processStore.setGlobalVariable(ARGUMENTS, args);
			}
			astInterpreter.interpretForewardOrder(SplParser.class, rootNode, internalId);
		} catch (Exception e) {
			throw new SplShellException("SplShell interpretation error!", e);
		} finally {
			if (id == null)
				astInterpreter.cleanUp(internalId);
		}
	}

	/**
	 * Loads mapped SPL method definitions for a process.
	 * <p>
	 * Definition files are discovered from a set of known locations and parsed as
	 * property files. Every declared method alias is converted into a
	 * {@link MethodDefinition} and registered in the supplied
	 * {@link ProcessStore}.
	 * </p>
	 * <p>
	 * This mechanism is also the supported extension point for users who want to
	 * contribute custom aliases without changing the parser or interpreter code:
	 * additional {@code *.def} files placed in the discovered directories are
	 * loaded alongside the built-in definitions.
	 * </p>
	 *
	 * @param processStore target process store that receives the mapped methods
	 * @throws PropertyFileException if a definition file cannot be parsed
	 * @throws ReflectiveOperationException if a mapped method definition is invalid
	 * @throws IOException if a definition file cannot be read
	 * @throws URISyntaxException if a resource URI cannot be converted to a path
	 */
	protected void loadMappedMethods(ProcessStore<Object> processStore)
			throws PropertyFileException, ReflectiveOperationException, IOException, URISyntaxException {
		List<Path> paths = getMethodDefinitionFiles();

		for (int j = 0; j < paths.size(); ++j) {
			Path p = paths.get(j);

			PropertyFile propertyFile = new PropertyFile(p.toUri(), true);

			propertyFile.load();

			createMethodDefinitionMap(propertyFile, processStore);
		}
	}

	/**
	 * Converts entries from a loaded property file into registered method
	 * definitions.
	 * <p>
	 * Each property block is expected to define an SPL alias with the following
	 * fields:
	 * </p>
	 * <ul>
	 * <li>{@code method <name>}: SPL-visible alias name.</li>
	 * <li>{@code params}: parameter types exposed in SPL and used for method
	 * resolution.</li>
	 * <li>{@code type}: fully qualified Java class that contains the target method
	 * or constructor.</li>
	 * <li>{@code realMethod}: Java method name or the special value
	 * {@code constructor}.</li>
	 * <li>{@code static}: indicates whether the Java target is static.</li>
	 * <li>{@code realParams}: Java parameter types used for reflective lookup of
	 * the target method or constructor.</li>
	 * <li>{@code returnType}: SPL type name declared for the alias result.</li>
	 * </ul>
	 * <p>
	 * For non-static aliases the first SPL parameter is interpreted as the target
	 * instance on which the reflected method is invoked. Therefore, it is part of
	 * {@code params}, but not part of {@code realParams}.
	 * </p>
	 *
	 * @param propertyFile parsed property file containing method alias
	 *                     declarations
	 * @param processStore target process store that receives the generated method
	 *                     definitions
	 * @throws ReflectiveOperationException if a definition cannot be translated
	 *                                      into a valid reflective method mapping
	 */
	private void createMethodDefinitionMap(PropertyFile propertyFile, ProcessStore<Object> processStore)
			throws ReflectiveOperationException {
		Collection<?> values = propertyFile.getValueMap().values();

		if (values.size() > 0) {
			for (Iterator<?> it = values.iterator(); it.hasNext();) {
				PropertyStruct propertyStruct = (PropertyStruct) it.next();

				String method = propertyStruct.getName();
				Object[] paramDefs = propertyStruct.arrayValue("params");
				String typeName = propertyStruct.stringValue("type");
				String methodName = propertyStruct.stringValue("realMethod");
				boolean statik = propertyStruct.booleanValue("static");
				Object[] realParamDefs = propertyStruct.arrayValue("realParams");
				String returnType = propertyStruct.stringValue("returnType");

				MethodDefinition methodDefinition = new MethodDefinition(method, paramDefs, typeName, methodName, statik, realParamDefs, returnType);

				processStore.addMethod(methodDefinition.getMethodSignature(), methodDefinition);
			}
		}
	}

	/**
	 * Resolves all available method definition files.
	 * <p>
	 * The lookup first scans the current working directory, then the classpath and
	 * finally the local application directory {@code user.home/}{@value #APP_DIR}.
	 * If no directory scan yields a definition file, the classpath resource named
	 * {@value #MAPPED_METHOD_FILE} is used as a direct fallback.
	 * </p>
	 * <p>
	 * The final lookup location is intended as a user extension directory for
	 * custom alias files. The implementation ensures that the directory exists
	 * before scanning it for additional definitions.
	 * </p>
	 *
	 * @return list of definition files that should be loaded
	 * @throws IOException if directory access fails
	 * @throws URISyntaxException if a classpath resource URI cannot be converted to
	 *                            a path
	 */
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
			path = Paths.get(homeDir, APP_DIR);

			if (!Files.exists(path)) {
				Files.createDirectories(path);
			}

			getFilesFromDir(path, paths);
		}
		return paths;
	}

	/**
	 * Adds all definition files with the {@code *.def} suffix from the given
	 * directory to the supplied list.
	 * <p>
	 * Definition files in this format may contain one or more {@code method}
	 * blocks that declare SPL aliases backed by reflective Java calls.
	 * </p>
	 *
	 * @param path directory to scan
	 * @param paths collection receiving all matching files
	 * @return the glob pattern used for the directory scan
	 * @throws IOException if the directory cannot be read
	 */
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
