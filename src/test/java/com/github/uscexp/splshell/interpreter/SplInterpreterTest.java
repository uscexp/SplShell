/*
 * Copyright (C) 2014 - 2015 by haui - all rights reserved
 */
package com.github.uscexp.splshell.interpreter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.junit.Test;

import com.github.uscexp.grappa.extension.interpreter.ProcessStore;
import com.github.uscexp.grappa.extension.interpreter.type.MethodDeclaration;
import com.github.uscexp.grappa.extension.interpreter.type.Primitive;

/**
 * @author haui
 *
 */
public class SplInterpreterTest {

	private static final String SPL_INPUT_PATH = "test.spl";

	@Test
	public void testExecuteFromStringInputFromStringIncludeFile()
			throws Exception {
		String text = "das ist ein test!\n";
		String input = "include \"inc.spl\";\n\n"
				+ "int main( string args[])\n"
				+ "{\n"
				+ "	string text = \"" + text + "\";"
				+ "	char ct[];\n"
				+ "	ct = charArray( text);\n"
				+ "	return 0;\n"
				+ "}\n";

		Long id = new Date().getTime() + UUID.randomUUID().hashCode();
		ProcessStore<Object> processStore = ProcessStore.getInstance(id, true);

		SplInterpreter.getInstance().executeFromStringInput(input, id);

		List<Class<?>> parameterTypes = new ArrayList<>();
		parameterTypes.add(String.class);
		parameterTypes.add(int.class);
		MethodIdentification methodIdentification = new MethodIdentification("printInt", parameterTypes.toArray(new Class[2]));
		MethodDeclaration methodDeclaration = processStore.getMethod(methodIdentification);

		assertNotNull(methodDeclaration);
		assertEquals(methodIdentification, methodDeclaration.getMethodSignature());

		parameterTypes = new ArrayList<>();
		parameterTypes.add(int.class);
		methodIdentification = new MethodIdentification("incVar", parameterTypes.toArray(new Class[1]));

		methodDeclaration = processStore.getMethod(methodIdentification);

		assertNotNull(methodDeclaration);
		assertEquals(methodIdentification, methodDeclaration.getMethodSignature());
	}

	@Test
	public void testExecuteFromStringInputFromStringAssignText()
			throws Exception {
		String text = "das ist ein test!\n";
		String input = "int main( string args[])\n"
				+ "{\n"
				+ "string text = \"" + text + "\";"
				+ "	return 0;\n"
				+ "}\n";

		Long id = new Date().getTime() + UUID.randomUUID().hashCode();

		ProcessStore<Object> processStore = ProcessStore.getInstance(id, true);

		SplInterpreter.getInstance().executeFromStringInput(input, id);

		Primitive variable = (Primitive) processStore.getVariable("text");
		assertEquals(text, variable.getValue());
	}

	@Test
	public void testExecuteFromStringInputFromStringWhileLoop()
			throws Exception {
		String input = "int main( string args[])\n"
				+ "{\n"
				+ "int i = 0;\r\n"
				+ "int calc = 0;\r\n"
				+ "	while(i <= 2)\r\n" +
				"	{\r\n" +
				"	  calc = calc + i;\r\n" +
				"     ++i;" +
				"	}\r\n" +
				"}\n";

		Long id = new Date().getTime() + UUID.randomUUID().hashCode();

		ProcessStore<Object> processStore = ProcessStore.getInstance(id, true);

		SplInterpreter.getInstance().executeFromStringInput(input, id);

		Primitive variable = (Primitive) processStore.getVariable("calc");
		assertEquals(3, variable.getValue());
	}

	@Test
	public void testExecuteFromStringInputFromStringDoWhileLoop()
			throws Exception {
		String input = "int main( string args[])\n"
				+ "{\n"
				+ "int i = 0;\r\n"
				+ "int calc = 0;\r\n"
				+ "	do\r\n" +
				"	{\r\n" +
				"	  calc = calc + i;\r\n" +
				"     i++;" +
				"	}\r\n" +
				"	while(i <= 2);\r\n" +
				"}\n";

		Long id = new Date().getTime() + UUID.randomUUID().hashCode();

		ProcessStore<Object> processStore = ProcessStore.getInstance(id, true);

		SplInterpreter.getInstance().executeFromStringInput(input, id);

		Primitive variable = (Primitive) processStore.getVariable("calc");
		assertEquals(3, variable.getValue());
	}

	@Test
	public void testExecuteFromStringInputFromStringForLoop()
			throws Exception {
		String input = "int main( string args[])\n"
				+ "{\n"
				+ "int calc = 0;\r\n"
				+ "for( int i = 0; i < 3; ++i)\r\n" +
				"	{\r\n" +
				"	  write i;\r\n"
				+ "   calc = calc + i;\r\n" +
				"	}\r\n" +
				"}\n";

		Long id = new Date().getTime() + UUID.randomUUID().hashCode();

		ProcessStore<Object> processStore = ProcessStore.getInstance(id, true);

		SplInterpreter.getInstance().executeFromStringInput(input, id);

		Primitive variable = (Primitive) processStore.getVariable("calc");
		assertEquals(3, variable.getValue());
	}

	@Test
	public void testExecuteFromStringInputMultiDimArray()
			throws Exception {
		String input = "int main( string args[])\n"
				+ "{\n"
				+ "int v;"
				+ "	int iArr[][][];\r\n"
				+ "	iArr = { { { 1, 2 } } };\r\n"
				+ "iArr[0][0][1] = 4;\r\n"
				+ "v = iArr[0][0][1];"
				+ "}\n";

		Long id = new Date().getTime() + UUID.randomUUID().hashCode();

		ProcessStore<Object> processStore = ProcessStore.getInstance(id, true);

		SplInterpreter.getInstance().executeFromStringInput(input, id);

		Primitive variable = (Primitive) processStore.getVariable("v");
		assertEquals(4, variable.getValue());
	}

	@Test
	public void testExecuteFromStringInputFromFileStringCharset()
			throws Exception {
		URL url = this.getClass().getClassLoader().getResource(SPL_INPUT_PATH);
		File file = new File(url.toURI());

		Long id = new Date().getTime() + UUID.randomUUID().hashCode();
		ProcessStore<Object> processStore = ProcessStore.getInstance(id, true, true);

		SplInterpreter.getInstance().executeFromFile(file.getAbsolutePath(),
				Charset.forName("UTF-8"), id);
	}

}
