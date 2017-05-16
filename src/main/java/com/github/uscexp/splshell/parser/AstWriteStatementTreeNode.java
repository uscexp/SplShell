/*
 * Copyright (C) 2014 - 2016 by haui - all rights reserved
 */
package com.github.uscexp.splshell.parser;

import com.github.uscexp.grappa.extension.util.IStack;

/**
 * Command implementation for the <code>SplParser</code> rule: writeStatement.
 * 
 */
public class AstWriteStatementTreeNode<V>
		extends AstBaseCommandTreeNode<V> {

	public AstWriteStatementTreeNode(String rule, String value) {
		super(rule, value);
	}

	@Override
	protected void interpretAfterChilds(Long id)
			throws Exception {
		super.interpretAfterChilds(id);
		IStack<Object> stack = processStore.getTierStack();
		Object printValue = stack.pop();
		String outputValue = printValue.toString();
		outputValue.replaceAll("\\n", "\n");
		outputValue.replaceAll("\\r", "\r");
		outputValue.replaceAll("\\t", "\t");

		System.out.print(printValue);
	}

}
