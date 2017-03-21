/*
 * Copyright (C) 2014 - 2015 by haui - all rights reserved
 */
package com.github.uscexp.splshell.parser;

import com.github.uscexp.grappa.extension.util.IStack;

/**
 * Command implementation for the <code>SplParser</code> rule: IDENTIFIER.
 */
public class AstIDENTIFIERTreeNode<V> extends AstBaseCommandTreeNode<V> {

	public AstIDENTIFIERTreeNode(String rule, String value) {
		super(rule, value);
	}

	@Override
	protected void interpretAfterChilds(Long id)
		throws Exception {
		super.interpretAfterChilds(id);
		IStack<Object> stack = processStore.getTierStack();
		if(getSibling(1) == null || !(getSibling(1) instanceof AstMethodCallStatementTreeNode)) {
			String literal = value.trim();
			stack.push(literal);
		}
	}
}
