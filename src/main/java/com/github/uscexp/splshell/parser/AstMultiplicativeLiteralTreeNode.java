/*
 * Copyright (C) 2014 - 2016 by haui - all rights reserved
 */
package com.github.uscexp.splshell.parser;

/**
 * Command implementation for the <code>SplParser</code> rule:
 * multiplicativeLiteral.
 */
public class AstMultiplicativeLiteralTreeNode<V> extends AstBaseCommandTreeNode<V> {

	public AstMultiplicativeLiteralTreeNode(String rule, String value) {
		super(rule, value);
	}

	@Override
	protected void interpretAfterChilds(Long id)
			throws Exception {
		super.interpretAfterChilds(id);
	}
}
