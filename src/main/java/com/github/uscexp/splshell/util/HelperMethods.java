/*
 * Copyright (C) 2014 - 2016 by haui - all rights reserved
 */
package com.github.uscexp.splshell.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * HelperMethods
 *
 * @author haui
 */
public class HelperMethods {

	public static final String EOF = "__EOF__";

	public static BufferedReader createBufferedReader(String fileName)
			throws FileNotFoundException, URISyntaxException {
		File file = getFile(fileName);
		return new BufferedReader(new FileReader(file));
	}

	private static File getFile(String fileName) throws URISyntaxException {
		URL url = HelperMethods.class.getClassLoader().getResource(fileName);
		File file = new File(url.toURI());
		return file;
	}

	public static BufferedWriter createBufferedWriter(String fileName, boolean append)
			throws IOException, URISyntaxException {
		//		File file = getFile(fileName);
		return new BufferedWriter(new FileWriter(fileName, append));
	}

	public static String readLine(BufferedReader br)
			throws IOException {
		String str = null;
		str = br.readLine();

		if (str == null) {
			str = EOF;
		}

		return str;
	}

	public static String isEOF() {
		return EOF;
	}

	public static char isCharEOF() {
		return (char) -1;
	}

	public static int arrayLength(Object[] ar) {
		return ar.length;
	}
}
