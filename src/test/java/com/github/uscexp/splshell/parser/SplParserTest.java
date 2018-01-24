/*
 * Copyright (C) 2014 - 2015 by haui - all rights reserved
 */
package com.github.uscexp.splshell.parser;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.github.fge.grappa.Grappa;
import com.github.uscexp.grappa.extension.nodes.AstTreeNode;
import com.github.uscexp.grappa.extension.parser.Parser;

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

		SplParser parser = Grappa.createParser(SplParser.class);
		AstTreeNode<String> rootNode = Parser.parseInput(SplParser.class, parser.compilationUnit(), input, true);

		assertNotNull(rootNode);
	}

}
