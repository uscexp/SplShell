/*
 * Copyright (C) 2014 - 2015 by haui - all rights reserved
 */
package com.github.uscexp.splshell.parser;

import java.io.File;
import java.net.URL;
import java.nio.charset.Charset;

import com.github.uscexp.splshell.interpreter.SplInterpreter;

/**
 * Command implementation for the <code>SplParser</code> rule: includeStatement.
 */
public class AstIncludeStatementTreeNode<V> extends AstBaseCommandTreeNode<V> {

	private String path;

	public AstIncludeStatementTreeNode(String rule, String value) {
		super(rule, value);
	}

	@Override
	protected void interpretAfterChilds(Long id)
		throws Exception {
		super.interpretAfterChilds(id);
		path = (String) processStore.getTierStack().pop();
		// delete leading and ending <code>"</code>
		if (path.startsWith("\""))
			path = path.substring(1, path.length() - 1);
		if (path != null) {
			URL url = this.getClass().getClassLoader().getResource(path);
			File file = new File(url.toURI());

			SplInterpreter.getInstance().executeFromFile(file.getAbsolutePath(), Charset.forName("UTF-8"), id);
		}
	}

}
