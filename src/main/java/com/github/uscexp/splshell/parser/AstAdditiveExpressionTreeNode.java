/*
 * Copyright (C) 2014 -2016 by haui - all rights reserved
 */
package com.github.uscexp.splshell.parser;

import java.util.Stack;

import com.github.uscexp.grappa.extension.interpreter.type.Primitive;

/**
 * Command implementation for the <code>SplParser</code> rule: additiveExpression.
 */
public class AstAdditiveExpressionTreeNode<V> extends AstBaseCommandTreeNode<V> {

	public AstAdditiveExpressionTreeNode(String rule, String value) {
		super(rule, value);
	}

	@Override
	protected void interpretAfterChilds(Long id)
		throws Exception {
		super.interpretAfterChilds(id);
		if(!isFirstChildAnExpression()) {
			Stack<Object> stack = processStore.getTierStack();
			Primitive result = null;
			Primitive v2 = new Primitive(stack.pop());
			if(!stack.isEmpty()) {
				Primitive v1 = new Primitive(stack.pop());
				result = v1.add(v2);
			} else {
				result = v2;
			}
			processStore.getStack().push(result.getValue());
		}
	}

}
