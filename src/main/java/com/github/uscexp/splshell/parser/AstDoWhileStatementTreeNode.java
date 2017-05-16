/*
 * Copyright (C) 2014 - 2016 by haui - all rights reserved
 */
package com.github.uscexp.splshell.parser;

import com.github.uscexp.grappa.extension.nodes.AstTreeNode;
import com.github.uscexp.splshell.exception.SplShellException;

/**
 * Command implementation for the <code>SplParser</code> rule: doWhileStatement.
 */
public class AstDoWhileStatementTreeNode<V> extends AstBaseCommandTreeNode<V> {

	private AstTreeNode<V> conditionTreeNode;
	private AstTreeNode<V> statementTreeNode;

	public AstDoWhileStatementTreeNode(String rule, String value) {
		super(rule, value);
		this.skipAutoInterpretation = true; // take control over the execution process
	}

    @Override
	protected void interpretBeforeChilds(Long id) throws Exception {
		super.interpretBeforeChilds(id);
		if (getChildren().size() != 2)
			throw new SplShellException(
					String.format("Wrong while loop definition! It needs 2 children:  type of statement and a AstConditionalOrExpressionTreeNode, Given are these: %s", this.toString()));
		statementTreeNode = getChildren().get(0);
		conditionTreeNode = getChildren().get(1);
	}

	@Override
    protected void interpretAfterChilds(Long id)
        throws Exception
    {
		super.interpretAfterChilds(id);
		processStore.createNewBlockVariableMap();

		do {
			statementTreeNode.interpretIt(id, true);
		} while (evaluateCondition(id, conditionTreeNode));

		processStore.removeLastBlockVariableMap();
    }

}
