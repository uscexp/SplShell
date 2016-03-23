/*
 * Copyright (C) 2014 - 2015 by haui - all rights reserved
 */
package com.github.uscexp.splshell.parser;

import java.util.Stack;

import org.parboiled.Node;

/**
 * Command implementation for the <code>SplParser</code> rule: IDENTIFIER.
 */
public class AstIDENTIFIERTreeNode<V> extends AstBaseCommandTreeNode<V> {

	public AstIDENTIFIERTreeNode(Node<?> node, String value) {
		super(node, value);
	}

	@Override
	protected void interpretAfterChilds(Long id)
		throws Exception {
		super.interpretAfterChilds(id);
		Stack<Object> stack = processStore.getTierStack();
		String literal = value.trim();
		stack.push(literal);
	}
}
