/*
 * Copyright (C) 2014 - 2016 by haui - all rights reserved
 */
package com.github.uscexp.splshell.parser;

import com.github.uscexp.grappa.extension.interpreter.type.Primitive;
import com.github.uscexp.grappa.extension.nodes.AstTreeNode;

/**
 * Command implementation for the <code>SplParser</code> rule:
 * multiplicativeExpression.
 * 
 */
public class AstMultiplicativeExpressionTreeNode<V>
		extends AstCalculationExpressionTreeNode<V> {

	public AstMultiplicativeExpressionTreeNode(String rule, String value) {
		super(rule, value);
	}

	@Override
	protected void interpretAfterChilds(Long id)
			throws Exception {
		super.interpretAfterChilds(id);
		if (isMultiplicativeExpressionPattern()) {
			process(id, AstUnaryExpressionTreeNode.class, AstMultiplicativeLiteralTreeNode.class);
		}
	}

	@Override
	protected Primitive calculate(String operator, Primitive v1, Primitive v2) {
		Primitive result = null;
		switch (operator.charAt(0)) {
		case '%':
			result = v1.mod(v2);
			break;

		case '*':
			result = v1.multiplicate(v2);
			break;

		case '/':
			result = v1.divide(v2);
			break;
		}

		return result;
	}

	protected boolean isMultiplicativeExpressionPattern() {
		boolean result = false;
		if (getChildren().size() >= 2) {
			AstTreeNode<V> treeNode1 = getChildren().get(0);
			AstTreeNode<V> treeNode2 = getChildren().get(1);
			AstTreeNode<V> treeNode3 = getChildren().get(2);
			if (treeNode1 instanceof AstUnaryExpressionTreeNode
					&& treeNode2 instanceof AstMultiplicativeLiteralTreeNode
					&& treeNode3 instanceof AstUnaryExpressionTreeNode) {
				result = true;
			}
		}
		return result;
	}
}
