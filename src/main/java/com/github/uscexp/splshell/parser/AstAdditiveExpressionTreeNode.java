/*
 * Copyright (C) 2014 -2016 by haui - all rights reserved
 */
package com.github.uscexp.splshell.parser;

import com.github.uscexp.grappa.extension.interpreter.type.Primitive;
import com.github.uscexp.grappa.extension.nodes.AstTreeNode;
import com.github.uscexp.grappa.extension.util.IStack;

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
		if(isAdditiveExpressionPattern()) {
			IStack<Object> stack = processStore.getTierStack();
			Primitive result = null;
			Primitive v2 = getPrimitive(stack.pop());
			if(!stack.isEmpty()) {
				Primitive v1 = getPrimitive(stack.pop());
				result = v1.add(v2);
			} else {
				result = v2;
			}
			stack.push(result);
		}
	}

	protected boolean isAdditiveExpressionPattern() {
		boolean result = false;
		if(getChildren().size() >= 2) {
			AstTreeNode<V> treeNode1 = getChildren().get(0);
			AstTreeNode<V> treeNode2 = getChildren().get(1);
			AstTreeNode<V> treeNode3 = getChildren().get(2);
			if(treeNode1 instanceof AstMultiplicativeExpressionTreeNode
					&& treeNode2 instanceof AstAdditiveLiteralTreeNode
					&& treeNode3 instanceof AstMultiplicativeExpressionTreeNode) {
				result = true;
			}
		}
		return result;
	}

}
