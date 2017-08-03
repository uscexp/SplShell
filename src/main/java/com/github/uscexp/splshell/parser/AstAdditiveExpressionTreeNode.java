/*
 * Copyright (C) 2014 -2016 by haui - all rights reserved
 */
package com.github.uscexp.splshell.parser;

import com.github.uscexp.grappa.extension.interpreter.type.Primitive;
import com.github.uscexp.grappa.extension.nodes.AstTreeNode;

/**
 * Command implementation for the <code>SplParser</code> rule:
 * additiveExpression.
 */
public class AstAdditiveExpressionTreeNode<V> extends AstCalculationExpressionTreeNode<V> {

	public AstAdditiveExpressionTreeNode(String rule, String value) {
		super(rule, value);
	}

	@Override
	protected void interpretAfterChilds(Long id)
			throws Exception {
		super.interpretAfterChilds(id);
		if (isAdditiveExpressionPattern()) {
			process(id, AstMultiplicativeExpressionTreeNode.class, AstAdditiveLiteralTreeNode.class);
		}
	}

	protected boolean isAdditiveExpressionPattern() {
		boolean result = false;
		if (getChildren().size() >= 2) {
			AstTreeNode<V> treeNode1 = getChildren().get(0);
			AstTreeNode<V> treeNode2 = getChildren().get(1);
			AstTreeNode<V> treeNode3 = getChildren().get(2);
			if (treeNode1 instanceof AstMultiplicativeExpressionTreeNode
					&& treeNode2 instanceof AstAdditiveLiteralTreeNode
					&& treeNode3 instanceof AstMultiplicativeExpressionTreeNode) {
				result = true;
			}
		}
		return result;
	}

	@Override
	protected Primitive calculate(String operator, Primitive v1, Primitive v2) {
		Primitive result = null;
		switch (operator.charAt(0)) {
		case '+':
			result = v1.add(v2);
			break;

		case '-':
			result = v1.substract(v2);
			break;
		}

		return result;
	}

}
