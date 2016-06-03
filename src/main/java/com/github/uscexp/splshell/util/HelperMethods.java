/*
 * Copyright (C) 2014 - 2016 by haui - all rights reserved
 */
package com.github.uscexp.splshell.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * HelperMethods
 *
 * @author  haui
 */
public class HelperMethods {

	public static final String EOF = "__EOF__";

	public static BufferedReader createBufferedReader(String fileName)
		throws FileNotFoundException {
		return new BufferedReader(new FileReader(fileName));
	}

	public static BufferedWriter createBufferedWriter(String fileName, boolean append)
		throws IOException {
		return new BufferedWriter(new FileWriter(fileName, append));
	}

	public static String readLine(BufferedReader br)
		throws IOException {
		String str = null;
		str = br.readLine();

		if (str == null)
			str = EOF;

		return str;
	}

	public static String isEOF() {
		return EOF;
	}

	public static Character isCharEOF() {
		return new Character((char) -1);
	}

	public static int arrayLength(Object[] ar) {
		return ar.length;
	}
}
