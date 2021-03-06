/*
 * Copyright (C) 2014 - 2015 by haui - all rights reserved
 */
package com.github.uscexp.splshell.parser;

/**
 * Command implementation for the <code>SplParser</code> rule: mainMethodDeclaration.
 */
public class AstMainMethodDeclarationTreeNode<V> extends AstBaseCommandTreeNode<V> {

	public AstMainMethodDeclarationTreeNode(String rule, String value) {
		super(rule, value);
	}

	@Override
	protected void interpretBeforeChilds(Long id)
		throws Exception {
		super.interpretBeforeChilds(id);
	}

	@Override
	protected void interpretAfterChilds(Long id)
		throws Exception {
		super.interpretAfterChilds(id);
	}

}
