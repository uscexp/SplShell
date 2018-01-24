/*
 * Copyright (C) 2014 - 2015 by haui - all rights reserved
 */
package com.github.uscexp.splshell.parser;

/**
 * Command implementation for the <code>SplParser</code> rule: mainMethodDeclarator.
 */
public class AstMainMethodDeclaratorTreeNode<V> extends AstBaseCommandTreeNode<V> {

	public AstMainMethodDeclaratorTreeNode(String rule, String value) {
		super(rule, value);
	}

	@Override
	protected void interpretAfterChilds(Long id) throws Exception {
		super.interpretAfterChilds(id);
		processStore.getTierStack().clear();
	}
}
