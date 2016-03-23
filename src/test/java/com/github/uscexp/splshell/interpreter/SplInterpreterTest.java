/*
 * Copyright (C) 2014 - 2015 by haui - all rights reserved
 */
package com.github.uscexp.splshell.interpreter;

import java.io.File;
import java.net.URL;
import java.nio.charset.Charset;

import org.junit.Test;

/**
 * @author haui
 *
 */
public class SplInterpreterTest {

	private static final String SPL_INPUT_PATH = "test.spl";

	@Test
	public void testExecuteFromStringInputFromFileStringCharset()
			throws Exception {
		URL url = this.getClass().getClassLoader().getResource(SPL_INPUT_PATH);
		File file = new File(url.toURI());

		SplInterpreter.getInstance().executeFromFile(file.getAbsolutePath(),
				Charset.forName("UTF-8"));
	}

}
