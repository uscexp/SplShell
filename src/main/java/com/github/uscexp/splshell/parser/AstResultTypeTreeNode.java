/*
 * Copyright (C) 2014 - 2016 by haui - all rights reserved
 */
package com.github.uscexp.splshell.parser;

import com.github.uscexp.grappa.extension.util.IStack;

/**
 * Command implementation for the <code>SplParser</code> rule: resultType.
 * 
 */
public class AstResultTypeTreeNode<V>
		extends AstBaseCommandTreeNode<V> {

	public AstResultTypeTreeNode(String rule, String value) {
		super(rule, value);
	}

	@Override
	protected void interpretAfterChilds(Long id)
			throws Exception {
		super.interpretAfterChilds(id);
		String returnValue = value.trim();

		if (returnValue.equals("void")) {
			IStack<Object> stack = processStore.getTierStack();
			stack.push(returnValue);
		}
	}
}
