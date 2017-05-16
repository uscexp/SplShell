/*
 * Copyright (C) 2014 - 2016 by haui - all rights reserved
 */
package com.github.uscexp.splshell.parser;

import com.github.uscexp.grappa.extension.nodes.AstTreeNode;
import com.github.uscexp.splshell.exception.SplShellException;

/**
 * Command implementation for the <code>SplParser</code> rule: forStatement.
 */
public class AstForStatementTreeNode<V> extends AstBaseCommandTreeNode<V> {

	private AstTreeNode<V> initTreeNode;
	private AstTreeNode<V> conditionTreeNode;
	private AstTreeNode<V> updateTreeNode;
	private AstTreeNode<V> statementTreeNode;

	public AstForStatementTreeNode(String rule, String value) {
		super(rule, value);
		this.skipAutoInterpretation = true; // take control over the execution process
	}

	@Override
	protected void interpretBeforeChilds(Long id) throws Exception {
		super.interpretBeforeChilds(id);
		if (getChildren().size() != 4)
			throw new SplShellException(
					String.format("Wrong for loop definition! It needs 4 children: an AstForInitTreeNode, an AstConditionalOrExpressionTreeNode, an AstForUpdateTreeNode and a type of statement, Given are these: %s", this.toString()));
		initTreeNode = getChildren().get(0);
		conditionTreeNode = getChildren().get(1);
		updateTreeNode = getChildren().get(2);
		statementTreeNode = getChildren().get(3);
	}

	@Override
	protected void interpretAfterChilds(Long id) throws Exception {
		super.interpretAfterChilds(id);
		processStore.createNewBlockVariableMap();

		initTreeNode.interpretIt(id, true);
		
		while (evaluateCondition(id, conditionTreeNode)) {
			statementTreeNode.interpretIt(id, true);
			
			// update loop variable
			updateTreeNode.interpretIt(id, true);
		}

		processStore.removeLastBlockVariableMap();
	}

}
