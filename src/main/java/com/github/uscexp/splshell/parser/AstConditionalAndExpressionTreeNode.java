/*
 * Copyright (C) 2014 - 2016 by haui - all rights reserved
 */
package com.github.uscexp.splshell.parser;

import com.github.uscexp.grappa.extension.interpreter.type.Primitive;
import com.github.uscexp.grappa.extension.nodes.AstTreeNode;

/**
 * Command implementation for the <code>SplParser</code> rule:
 * conditionalAndExpression.
 * 
 */
public class AstConditionalAndExpressionTreeNode<V>
		extends AstCalculationExpressionTreeNode<V> {

	public AstConditionalAndExpressionTreeNode(String rule, String value) {
		super(rule, value);
	}

	@Override
	protected void interpretAfterChilds(Long id)
			throws Exception {
		super.interpretAfterChilds(id);
		if (isConditionalAndExpressionPattern()) {
			process(id, AstInclusiveOrExpressionTreeNode.class, null);
		}
	}

	private boolean isConditionalAndExpressionPattern() {
		boolean result = false;
		if (getChildren().size() >= 2) {
			AstTreeNode<V> treeNode1 = getChildren().get(0);
			AstTreeNode<V> treeNode2 = getChildren().get(1);
			if (treeNode1 instanceof AstInclusiveOrExpressionTreeNode
					&& getValue().indexOf("&&") > 0
					&& treeNode2 instanceof AstInclusiveOrExpressionTreeNode) {
				result = true;
			}
		}
		return result;
	}

	@Override
	protected Primitive calculate(String operator, Primitive v1, Primitive v2) {
		boolean resultValue = v1.getBooleanValue() && v2.getBooleanValue();
		Primitive result = new Primitive(Boolean.class, resultValue);
		return result;
	}

}
