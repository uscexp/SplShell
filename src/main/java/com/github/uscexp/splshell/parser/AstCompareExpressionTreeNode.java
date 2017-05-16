/*
 * Copyright (C) 2014 - 2016 by haui - all rights reserved
 */
package com.github.uscexp.splshell.parser;

import com.github.uscexp.grappa.extension.interpreter.type.Comperator;
import com.github.uscexp.grappa.extension.interpreter.type.Primitive;
import com.github.uscexp.grappa.extension.util.IStack;

/**
 * Command implementation for the <code>SplParser</code> base class for
 * conditional expressions.
 * 
 */
public abstract class AstCompareExpressionTreeNode<V>
		extends AstBaseCommandTreeNode<V> {

	enum ConditionLiteral {
		gt(Comperator.GREATER), lt(Comperator.LESSER), ge(Comperator.GREATER_EQUALS), le(Comperator.LESSER_EQUALS), eq(Comperator.EQUALS), ne(Comperator.NOT_EQUALS);

		Comperator comperator;

		private ConditionLiteral(Comperator comperator) {
			this.comperator = comperator;
		}

		public boolean compare(Primitive value1, Primitive value2) {
			return value1.compare(comperator, value2);
		}

		public static ConditionLiteral valueByString(String conditionalLiteral) {
			Comperator comperator = Comperator.valueByString(conditionalLiteral);
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
		IStack<Object> stack = processStore.getTierStack();
		if (existChildConditionalLiteral()) {
			Primitive secondLiteral = getPrimitive(stack.pop());
			String condition = (String) stack.pop();
			Primitive firstLiteral = getPrimitive(stack.pop());

			boolean result = ConditionLiteral.valueByString(condition).compare(firstLiteral, secondLiteral);

			stack.push(result);
		}
	}

	abstract boolean existChildConditionalLiteral();
}
