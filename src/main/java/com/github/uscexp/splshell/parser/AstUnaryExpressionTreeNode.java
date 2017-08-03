/*
 * Copyright (C) 2014 - 2016 by haui - all rights reserved
 */
package com.github.uscexp.splshell.parser;

import com.github.uscexp.grappa.extension.interpreter.type.Primitive;
import com.github.uscexp.grappa.extension.nodes.AstTreeNode;

/**
 * Command implementation for the <code>SplParser</code> rule: unaryExpression.
 * 
 */
public class AstUnaryExpressionTreeNode<V>
		extends AstCalculationExpressionTreeNode<V> {

	public AstUnaryExpressionTreeNode(String rule, String value) {
		super(rule, value);
	}

	@Override
	protected void interpretAfterChilds(Long id)
			throws Exception {
		super.interpretAfterChilds(id);
		if (isUnaryExpressionPattern()) {
			process(id, AstUnaryExpressionTreeNode.class, AstUnaryLiteralTreeNode.class);
		}
	}

	@Override
	protected void interpretBeforeChilds(Long id)
			throws Exception {
		super.interpretBeforeChilds(id);
	}

	protected boolean isUnaryExpressionPattern() {
		boolean result = false;
		if (getChildren().size() >= 2) {
			AstTreeNode<V> treeNode1 = getChildren().get(0);
			AstTreeNode<V> treeNode2 = getChildren().get(1);
			if (treeNode1 instanceof AstUnaryLiteralTreeNode
					&& treeNode2 instanceof AstUnaryExpressionTreeNode) {
				result = true;
			}
		}
		return result;
	}

	@Override
	protected Primitive calculate(String operator, Primitive v1, Primitive v2) {
		Primitive result = null;
		switch (operator.charAt(0)) {
		case '!':
			result = new Primitive(Boolean.class, !v1.getBooleanValue());
			break;
		case '~':
			result = v1.bitwiseComplement();
			break;
		case '-':
			result = v1;
			result.negative();
			break;
		case '+':
			result = v1;
			result.positive();
			break;
		}
		return result;
	}
}
