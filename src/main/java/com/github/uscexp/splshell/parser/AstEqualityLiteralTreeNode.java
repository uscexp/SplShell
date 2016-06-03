/*
 * Copyright (C) 2014 - 2016 by haui - all rights reserved
 */
package com.github.uscexp.splshell.parser;

/**
 * Command implementation for the <code>SplParser</code> rule: equalityLiteral.
 */
public class AstEqualityLiteralTreeNode<V> extends AstBaseCommandTreeNode<V> {

	public AstEqualityLiteralTreeNode(String rule, String value) {
		super(rule, value);
	}

	@Override
	protected void interpretAfterChilds(Long id)
		throws Exception {
		super.interpretAfterChilds(id);
    	System.out.println("EqualityLiteral");
	}
}
