/*
 * Copyright (C) 2014 - 2015 by haui - all rights reserved
 */
package com.github.uscexp.splshell.parser;

/**
 * Command implementation for the <code>SplParser</code> rule: methodDeclarator.
 */
public class AstMethodDeclaratorTreeNode<V> extends AstBaseCommandTreeNode<V> implements AstMethodInterface {

	public AstMethodDeclaratorTreeNode(String rule, String value) {
		super(rule, value);
	}

	@Override
	protected void interpretAfterChilds(Long id)
		throws Exception {
		super.interpretAfterChilds(id);
	}

	@Override
	public void invoke(Long id) {
	}

}
