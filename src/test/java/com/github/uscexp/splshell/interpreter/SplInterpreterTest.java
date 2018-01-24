/*
 * Copyright (C) 2014 - 2015 by haui - all rights reserved
 */
package com.github.uscexp.splshell.interpreter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
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

	private static final String SPL_INPUT_PATH_TEST = "test.spl";
	private static final String SPL_INPUT_PATH_FACT = "fact.spl";
	private static final String SPL_INPUT_PATH_SQRT = "sqrt.spl";
	private static final String SPL_INPUT_PATH_ODD = "odd.spl";

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
	public void testExecuteFromStringInputFromStringWhileLoopBreak()
			throws Exception {
		String input = "int main( string args[])\n"
				+ "{\n"
				+ "int i = 0;\r\n"
				+ "int calc = 0;\r\n"
				+ "	while(i <= 2)\r\n" +
				"	{\r\n" +
				"	  calc = calc + i;\r\n" +
				"     ++i;" +
				"	break;\r\n" +
				"	}\r\n" +
				"}\n";

		Long id = new Date().getTime() + UUID.randomUUID().hashCode();

		ProcessStore<Object> processStore = ProcessStore.getInstance(id, true);

		SplInterpreter.getInstance().executeFromStringInput(input, id);

		Primitive variable = (Primitive) processStore.getVariable("calc");
		assertEquals(0, variable.getValue());
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
	public void testExecuteFromStringInputFromStringDoWhileLoopBreak()
			throws Exception {
		String input = "int main( string args[])\n"
				+ "{\n"
				+ "int i = 0;\r\n"
				+ "int calc = 0;\r\n"
				+ "	do\r\n" +
				"	{\r\n" +
				"	  calc = calc + i;\r\n" +
				"     i++;" +
				"	break;\r\n" +
				"	}\r\n" +
				"	while(i <= 2);\r\n" +
				"}\n";

		Long id = new Date().getTime() + UUID.randomUUID().hashCode();

		ProcessStore<Object> processStore = ProcessStore.getInstance(id, true);

		SplInterpreter.getInstance().executeFromStringInput(input, id);

		Primitive variable = (Primitive) processStore.getVariable("calc");
		assertEquals(0, variable.getValue());
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
	public void testExecuteFromStringInputFromStringForLoopBreak()
			throws Exception {
		String input = "int main( string args[])\n"
				+ "{\n"
				+ "int calc = 0;\r\n"
				+ "for( int i = 0; i < 3; ++i)\r\n" +
				"	{\r\n" +
				"	  write i;\r\n"
				+ "   calc = calc + i;\r\n" +
				"	break;\r\n" +
				"	}\r\n" +
				"}\n";

		Long id = new Date().getTime() + UUID.randomUUID().hashCode();

		ProcessStore<Object> processStore = ProcessStore.getInstance(id, true);

		SplInterpreter.getInstance().executeFromStringInput(input, id);

		Primitive variable = (Primitive) processStore.getVariable("calc");
		assertEquals(0, variable.getValue());
	}

	@Test
	public void testExecuteFromStringInputFromStringForLoopContinue()
			throws Exception {
		String input = "int main( string args[])\n"
				+ "{\n"
				+ "int calc = 0;\r\n"
				+ "for( int i = 0; i < 3; ++i)\r\n" +
				"	{\r\n" +
				"	  write i;\r\n" +
				"	continue;\r\n" +
				"   calc = calc + i;\r\n" +
				"	}\r\n" +
				"}\n";

		Long id = new Date().getTime() + UUID.randomUUID().hashCode();

		ProcessStore<Object> processStore = ProcessStore.getInstance(id, true);

		SplInterpreter.getInstance().executeFromStringInput(input, id);

		Primitive variable = (Primitive) processStore.getVariable("calc");
		assertEquals(0, variable.getValue());
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
	public void testExecuteFromStringInputConditionalOr()
			throws Exception {
		String input = "int main( string args[])\n"
				+ "{\n"
				+ "boolean result;"
				+ "	result = true || false;"
				+ "}\n";

		Long id = new Date().getTime() + UUID.randomUUID().hashCode();

		ProcessStore<Object> processStore = ProcessStore.getInstance(id, true);

		SplInterpreter.getInstance().executeFromStringInput(input, id);

		Primitive variable = (Primitive) processStore.getVariable("result");
		assertEquals(true, variable.getBooleanValue());
	}

	@Test
	public void testExecuteFromStringInputConditionalAnd()
			throws Exception {
		String input = "int main( string args[])\n"
				+ "{\n"
				+ "boolean result;"
				+ "	result = true && false;"
				+ "}\n";

		Long id = new Date().getTime() + UUID.randomUUID().hashCode();

		ProcessStore<Object> processStore = ProcessStore.getInstance(id, true);

		SplInterpreter.getInstance().executeFromStringInput(input, id);

		Primitive variable = (Primitive) processStore.getVariable("result");
		assertEquals(false, variable.getBooleanValue());
	}

	@Test
	public void testExecuteFromStringInputExclusiveOr()
			throws Exception {
		String input = "int main( string args[])\n"
				+ "{\n"
				+ "boolean result;"
				+ "	result = true ^ false;"
				+ "}\n";

		Long id = new Date().getTime() + UUID.randomUUID().hashCode();

		ProcessStore<Object> processStore = ProcessStore.getInstance(id, true);

		SplInterpreter.getInstance().executeFromStringInput(input, id);

		Primitive variable = (Primitive) processStore.getVariable("result");
		assertEquals(true, variable.getBooleanValue());
	}

	@Test
	public void testExecuteFromStringInputInclusiveOr()
			throws Exception {
		String input = "int main( string args[])\n"
				+ "{\n"
				+ "boolean result;"
				+ "	result = true | false;"
				+ "}\n";

		Long id = new Date().getTime() + UUID.randomUUID().hashCode();

		ProcessStore<Object> processStore = ProcessStore.getInstance(id, true);

		SplInterpreter.getInstance().executeFromStringInput(input, id);

		Primitive variable = (Primitive) processStore.getVariable("result");
		assertEquals(true | false, variable.getBooleanValue());
	}

	@Test
	public void testExecuteFromStringInputAnd()
			throws Exception {
		String input = "int main( string args[])\n"
				+ "{\n"
				+ "boolean result;"
				+ "	result = true & false;"
				+ "}\n";

		Long id = new Date().getTime() + UUID.randomUUID().hashCode();

		ProcessStore<Object> processStore = ProcessStore.getInstance(id, true);

		SplInterpreter.getInstance().executeFromStringInput(input, id);

		Primitive variable = (Primitive) processStore.getVariable("result");
		assertEquals(true & false, variable.getBooleanValue());
	}

	@Test
	public void testExecuteFromStringInputUnaryExpNot()
			throws Exception {
		String input = "int main( string args[])\n"
				+ "{\n"
				+ "boolean result;"
				+ "	result = !true;"
				+ "}\n";

		Long id = new Date().getTime() + UUID.randomUUID().hashCode();

		ProcessStore<Object> processStore = ProcessStore.getInstance(id, true);

		SplInterpreter.getInstance().executeFromStringInput(input, id);

		Primitive variable = (Primitive) processStore.getVariable("result");
		assertEquals(false, variable.getBooleanValue());
	}

	@Test
	public void testExecuteFromStringInputUnaryExpTile()
			throws Exception {
		String input = "int main( string args[])\n"
				+ "{\n"
				+ "boolean result;"
				+ "	result = ~5;"
				+ "}\n";

		Long id = new Date().getTime() + UUID.randomUUID().hashCode();

		ProcessStore<Object> processStore = ProcessStore.getInstance(id, true);

		SplInterpreter.getInstance().executeFromStringInput(input, id);

		Primitive variable = (Primitive) processStore.getVariable("result");
		assertEquals(~5, variable.getValue());
	}

	@Test
	public void testExecuteFromStringInputUnaryExpNegative()
			throws Exception {
		String input = "int main( string args[])\n"
				+ "{\n"
				+ "boolean result;"
				+ "int i = 5;"
				+ "	result = -i;"
				+ "}\n";

		Long id = new Date().getTime() + UUID.randomUUID().hashCode();

		ProcessStore<Object> processStore = ProcessStore.getInstance(id, true);

		SplInterpreter.getInstance().executeFromStringInput(input, id);

		Primitive variable = (Primitive) processStore.getVariable("result");
		assertEquals(-5, variable.getValue());
	}

	@Test
	public void testExecuteFromStringInputUnaryExpPositiv()
			throws Exception {
		String input = "int main( string args[])\n"
				+ "{\n"
				+ "boolean result;"
				+ "int i = -5;"
				+ "	result = +i;"
				+ "}\n";

		Long id = new Date().getTime() + UUID.randomUUID().hashCode();

		ProcessStore<Object> processStore = ProcessStore.getInstance(id, true);

		SplInterpreter.getInstance().executeFromStringInput(input, id);

		Primitive variable = (Primitive) processStore.getVariable("result");
		assertEquals(-5, variable.getValue());
	}

	@Test
	public void testExecuteFromStringInputReadWriteFile()
			throws Exception {
		String input = "int main( string args[])\n" +
				"{\n" +
				"	char c;\r\n" +
				"	string text = \"b\";\r\n" +
				"	filereader fr = openFileToRead( \"test.spl\");\r\n" +
				"	filewriter fw = openFileToWrite( \"writetest.txt\", false);\r\n" +
				"	\r\n" +
				"	text = readLine(fr);\r\n" +
				"	string e = isEOF();\r\n" +
				"	while( text != e)\r\n" +
				"	{\r\n" +
				"	  write text + \"\n\";\r\n" +
				"	  writeString( fw, text + \"\n\");\r\n" +
				"	  text = readLine(fr);\r\n" +
				"	}\r\n" +
				"	closeFileReader(fr);\r\n" +
				"	\r\n" +
				"	\r\n" +
				"	closeFileWriter( fw);\r\n" +
				"}\n";

		Long id = new Date().getTime() + UUID.randomUUID().hashCode();

		ProcessStore<Object> processStore = ProcessStore.getInstance(id, true);

		SplInterpreter.getInstance().executeFromStringInput(input, id);

		URL expected = this.getClass().getClassLoader().getResource("test.spl");
		File testFile = new File("writetest.txt");
		BufferedReader expectedFileReader = new BufferedReader(new FileReader(expected.getFile()));
		BufferedReader testFileReader = new BufferedReader(new FileReader(testFile));

		try {
			String expectedLine;
			String testLine;
			while ((expectedLine = expectedFileReader.readLine()) != null) {
				testLine = testFileReader.readLine();

				assertEquals(expectedLine, testLine);
			}
		} finally {
			expectedFileReader.close();
			testFileReader.close();
			testFile.delete();
		}
	}

	@Test
	public void testExecuteFromStringInputSwitchStatement()
			throws Exception {
		String input = createSwitchStatement(1, false);

		Long id = new Date().getTime() + UUID.randomUUID().hashCode();

		ProcessStore<Object> processStore = ProcessStore.getInstance(id, true);

		SplInterpreter.getInstance().executeFromStringInput(input, id);

		Primitive variable = (Primitive) processStore.getVariable("result");
		assertEquals(1, variable.getValue());
	}

	@Test
	public void testExecuteFromStringInputSwitchStatementNoBreak()
			throws Exception {
		String input = createSwitchStatement(0, true);

		Long id = new Date().getTime() + UUID.randomUUID().hashCode();

		ProcessStore<Object> processStore = ProcessStore.getInstance(id, true);

		SplInterpreter.getInstance().executeFromStringInput(input, id);

		Primitive variable = (Primitive) processStore.getVariable("result");
		assertEquals(2, variable.getValue());
	}

	@Test
	public void testExecuteFromStringInputSwitchStatementDefaultCase()
			throws Exception {
		String input = createSwitchStatement(3, false);

		Long id = new Date().getTime() + UUID.randomUUID().hashCode();

		ProcessStore<Object> processStore = ProcessStore.getInstance(id, true);

		SplInterpreter.getInstance().executeFromStringInput(input, id);

		Primitive variable = (Primitive) processStore.getVariable("result");
		assertEquals(2, variable.getValue());
	}

	private String createSwitchStatement(int i, boolean removeBreaks) {
		String part1 = "int main( string args[])\n"
				+ "{\n"
				+ "int v = " + i + ";\r\n"
				+ "int result;\r\n"
				+ "	switch( v)\r\n" +
				"	{\r\n" +
				"	  case 0:\r\n" +
				"       result = 0;\r\n" +
				"	    write \"case 0\n\";\r\n";
		String breake = "	    break;\r\n";
		String noBreak = " \r\n";
		String part2 = "	    \r\n" +
				"	  case 1:\r\n" +
				"       result = 1;\r\n" +
				"	    write \"case 1\n\";\r\n";
		String part3 = "	\r\n" +
				"	  default:\r\n" +
				"       result = 2;\r\n" +
				"	    write \"default case\n\";\r\n" +
				"	    break;\r\n" +
				"	}\r\n" +
				""
				+ "}\n";
		String breakValue = removeBreaks ? noBreak : breake;
		String input = String.format("%s%s%s%s%s", part1, breakValue, part2, breakValue, part3);
		return input;
	}

	@Test
	public void testExecuteFromStringInputIfStatement()
			throws Exception {
		String input = createIfStatement(0);

		Long id = new Date().getTime() + UUID.randomUUID().hashCode();

		ProcessStore<Object> processStore = ProcessStore.getInstance(id, true);

		SplInterpreter.getInstance().executeFromStringInput(input, id);

		Primitive variable = (Primitive) processStore.getVariable("result");
		assertEquals(1, variable.getValue());
	}

	@Test
	public void testExecuteFromStringInputIfStatementElseBlock()
			throws Exception {
		String input = createIfStatement(1);

		Long id = new Date().getTime() + UUID.randomUUID().hashCode();

		ProcessStore<Object> processStore = ProcessStore.getInstance(id, true);

		SplInterpreter.getInstance().executeFromStringInput(input, id);

		Primitive variable = (Primitive) processStore.getVariable("result");
		assertEquals(2, variable.getValue());
	}

	private String createIfStatement(int i) {
		String input = "int main( string args[])\n"
				+ "{\n" +
				"int n = " + i + ";\r\n" +
				"int result;\r\n" +
				"\r\n" +
				"if(n == 0) {\r\n" +
				"	result = 1;\r\n" +
				"	write \"In Block; a: \" + result;\r\n" +
				"} else {\r\n" +
				"	result = 2;\r\n" +
				"	write \"In Block; b: \" + result;\r\n" +
				"}"
				+ "}\n";
		return input;
	}

	@Test
	public void testExecuteFromStringInputFromFileFact()
			throws Exception {
		URL url = this.getClass().getClassLoader().getResource(SPL_INPUT_PATH_FACT);
		File file = new File(url.toURI());

		Long id = new Date().getTime() + UUID.randomUUID().hashCode();
		ProcessStore<Object> processStore = ProcessStore.getInstance(id, true);

		SplInterpreter.getInstance().executeFromFile(file.getAbsolutePath(),
				Charset.forName("UTF-8"), id);

		Primitive variable = (Primitive) processStore.getVariable("fact");
		assertEquals(24, variable.getValue());
	}

	@Test
	public void testExecuteFromStringInputFromFileSqrt()
			throws Exception {
		URL url = this.getClass().getClassLoader().getResource(SPL_INPUT_PATH_SQRT);
		File file = new File(url.toURI());

		Long id = new Date().getTime() + UUID.randomUUID().hashCode();
		ProcessStore<Object> processStore = ProcessStore.getInstance(id, true, true);

		SplInterpreter.getInstance().executeFromFile(file.getAbsolutePath(),
				Charset.forName("UTF-8"), id);

		Primitive variable = (Primitive) processStore.getVariable("sqrt");
		assertEquals(3, variable.getIntegerValue());
	}

	@Test
	public void testExecuteFromStringInputFromFileOdd()
			throws Exception {
		URL url = this.getClass().getClassLoader().getResource(SPL_INPUT_PATH_ODD);
		File file = new File(url.toURI());

		Long id = new Date().getTime() + UUID.randomUUID().hashCode();
		ProcessStore<Object> processStore = ProcessStore.getInstance(id, true);

		SplInterpreter.getInstance().executeFromFile(file.getAbsolutePath(),
				Charset.forName("UTF-8"), id);

		Primitive variable = (Primitive) processStore.getVariable("odd");
		assertEquals(true, variable.getBooleanValue());
	}

	@Test
	public void testExecuteFromStringInputFromFileStringCharset()
			throws Exception {
		URL url = this.getClass().getClassLoader().getResource(SPL_INPUT_PATH_TEST);
		File file = new File(url.toURI());

		Long id = new Date().getTime() + UUID.randomUUID().hashCode();
		ProcessStore<Object> processStore = ProcessStore.getInstance(id, true, true);

		SplInterpreter.getInstance().executeFromFile(file.getAbsolutePath(),
				Charset.forName("UTF-8"), id);
	}

}
