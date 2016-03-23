/*
 * Copyright (C) 2014 - 2015 by haui - all rights reserved
 */
package com.github.uscexp.splshell.parser;

import static org.junit.Assert.assertFalse;

import org.junit.Test;
import org.parboiled.Parboiled;
import org.parboiled.errors.ErrorUtils;
import org.parboiled.parserunners.RecoveringParseRunner;
import org.parboiled.support.ParsingResult;

/**
 * @author haui
 *
 */
public class SplParserTest {

	@Test
	public void testCompilationUnit() throws Exception {
		String input = "int n;\r\n" + 
				"int fact;\r\n" + 
				"\r\n" + 
				"fact = 1;\r\n" + 
				"\r\n" + 
				"read n;\r\n" + 
				"\r\n" + 
				"while (n > 1)\r\n" + 
				"{\r\n" + 
				"   fact = fact * n;\r\n" + 
				"   n = n - 1;\r\n" + 
				"}\r\n" + 
				"\r\n" + 
				"write fact;\r\n";

		SplParser parser = Parboiled.createParser(SplParser.class);
		RecoveringParseRunner<SplParser> recoveringParseRunner = new RecoveringParseRunner<>(parser.compilationUnit());
		
		ParsingResult<SplParser> parsingResult = recoveringParseRunner.run(input);
		
		if(parsingResult.hasErrors()) {
			System.err.println(String.format("Input parse error(s): %s", ErrorUtils.printParseErrors(parsingResult)));
		}
		
		assertFalse(parsingResult.hasErrors());
	}

}
