/*
 * Copyright (C) 2014 - 2016 by haui - all rights reserved
 */
package com.github.uscexp.splshell.parser;

import com.github.uscexp.grappa.extension.interpreter.ProcessStore;
import com.github.uscexp.grappa.extension.interpreter.type.Primitive;
import com.github.uscexp.grappa.extension.nodes.AstTreeNode;
import com.github.uscexp.splshell.exception.SplShellException;

/**
 * Command implementation for the <code>SplParser</code> rule: forStatement.
 */
public class AstForStatementTreeNode<V> extends AstBaseCommandTreeNode<V> {

	public AstForStatementTreeNode(String rule, String value) {
		super(rule, value);
	}

	@Override
	protected void interpretBeforeChilds(Long id) throws Exception {
		super.interpretBeforeChilds(id);
		if (getChildren().size() != 4)
			throw new SplShellException(
					String.format("Wrong for loop definition! It needs 4 children: an AstForInitTreeNode, an AstConditionalOrExpressionTreeNode, an AstForUpdateTreeNode and a type of statement, Given are these: %s", this.toString()));
		AstTreeNode<V> initTreeNode = getChildren().get(0);
		AstTreeNode<V> conditionTreeNode = getChildren().get(1);
		AstTreeNode<V> updateTreeNode = getChildren().get(2);
		AstTreeNode<V> statementTreeNode = getChildren().get(3);
		
		initTreeNode.interpretIt(id, true);
		
		while (evaluateCondition(id, updateTreeNode)) {
			
		}
	}

	private boolean evaluateCondition(Long id, AstTreeNode<V> updateTreeNode) throws Exception {
		updateTreeNode.interpretIt(id, true);
		
		Object conditionValue = processStore.getTierStack().pop();
		
		if(conditionValue instanceof Primitive) {
			conditionValue = ((Primitive)conditionValue).getValue();
		}
		
		return ((Boolean)conditionValue).booleanValue();
	}

	@Override
	protected void interpretAfterChilds(Long id) throws Exception {
		super.interpretAfterChilds(id);
		boolean exit = false;
		int i, k = 0;
		AstTreeNode updateNode = null;

		processStore.createNewBlockVariableMap();

		do {
			for (i = 0; (i < k) && !exit; i++) {
				{
					// Statement
					if (processStore.getExecState() == ProcessStore.BREAK_STATE) {
						processStore.setExecState(ProcessStore.OK_STATE);
						exit = true;
						break;
					}
					if (processStore.getExecState() == ProcessStore.CONT_STATE) {
						processStore.setExecState(ProcessStore.OK_STATE);
					}
					// ForUpdate
					if (updateNode != null)
						updateNode.interpretIt(id, true);
				}
			}
		} while (!exit);

		processStore.removeLastBlockVariableMap();
	}

}
