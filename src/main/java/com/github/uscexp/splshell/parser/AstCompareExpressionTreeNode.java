/*
 * Copyright (C) 2014 - 2016 by haui - all rights reserved
 */
package com.github.uscexp.splshell.parser;

import com.github.uscexp.grappa.extension.interpreter.type.Comparator;
import com.github.uscexp.grappa.extension.interpreter.type.Primitive;
import com.github.uscexp.grappa.extension.nodes.AstTreeNode;

/**
 * Command implementation for the <code>SplParser</code> base class for
 * conditional expressions.
 * 
 */
public abstract class AstCompareExpressionTreeNode<V>
		extends AstCalculationExpressionTreeNode<V> {

	enum ConditionLiteral {
		gt(Comparator.GREATER), lt(Comparator.LESSER), ge(Comparator.GREATER_EQUALS), le(Comparator.LESSER_EQUALS), eq(Comparator.EQUALS), ne(Comparator.NOT_EQUALS);

		Comparator comperator;

		private ConditionLiteral(Comparator comperator) {
			this.comperator = comperator;
		}

		public Primitive compare(Primitive value1, Primitive value2) {
			Primitive result = null;
			boolean compareResult = value1.compare(comperator, value2);

			result = new Primitive(Boolean.class, compareResult);
			return result;
		}

		public static ConditionLiteral valueByString(String conditionalLiteral) {
			Comparator comperator = Comparator.valueByString(conditionalLiteral);
			ConditionLiteral conditionLiteral = null;
			for (ConditionLiteral literal : values()) {
				if (literal.comperator == comperator) {
					conditionLiteral = literal;
					break;
				}
			}
			return conditionLiteral;
		}
	}

	public AstCompareExpressionTreeNode(String rule, String value) {
		super(rule, value);
	}

	@Override
	protected void interpretAfterChilds(Long id)
			throws Exception {
		super.interpretAfterChilds(id);
		if (existChildConditionalLiteral()) {
			process(id, getValueTreeNodeClass(), getOperatorTreeNodeClass());
		}
	}

	@Override
	protected Primitive calculate(String operator, Primitive v1, Primitive v2) {
		Primitive result = null;

		result = ConditionLiteral.valueByString(operator).compare(v1, v2);

		return result;
	}

	abstract boolean existChildConditionalLiteral();

	@SuppressWarnings("rawtypes")
	abstract Class<? extends AstTreeNode> getValueTreeNodeClass();

	@SuppressWarnings("rawtypes")
	abstract Class<? extends AstTreeNode> getOperatorTreeNodeClass();
}
