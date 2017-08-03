/*
 * Copyright (C) 2014 - 2016 by haui - all rights reserved
 */
package com.github.uscexp.splshell.parser;

import com.github.uscexp.grappa.extension.interpreter.type.Primitive;
import com.github.uscexp.grappa.extension.nodes.AstTreeNode;
import com.github.uscexp.grappa.extension.util.IStack;

/**
 * Command implementation for the <code>SplParser</code> rule: ifStatement.
 * 
 */
public class AstIfStatementTreeNode<V>
		extends AstBaseCommandTreeNode<V> {

	private AstTreeNode<V> conditionExpression;
	private AstTreeNode<V> thenBlock;
	private AstTreeNode<V> elseBlock;

	public AstIfStatementTreeNode(String rule, String value) {
		super(rule, value);
		this.skipAutoInterpretation = true; // take control over the execution process
	}

	@Override
	protected void interpretBeforeChilds(Long id) throws Exception {
		super.interpretBeforeChilds(id);
		conditionExpression = getChildren().get(0);
		thenBlock = getChildren().get(1);
		if (getChildren().size() > 2) {
			elseBlock = getChildren().get(2);
		}
	}

	@Override
	protected void interpretAfterChilds(Long id)
			throws Exception {
		super.interpretAfterChilds(id);
		IStack<Object> stack = processStore.getTierStack();
		conditionExpression.interpretIt(id, true);
		Object object = stack.pop();
		Primitive primitive = getPrimitive(object);
		if (primitive.getBooleanValue()) {
			thenBlock.interpretIt(id, true);
		} else if (elseBlock != null) {
			elseBlock.interpretIt(id, true);
		}
	}

}
