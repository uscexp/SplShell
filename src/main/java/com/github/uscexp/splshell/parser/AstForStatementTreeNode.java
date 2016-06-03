/*
 * Copyright (C) 2014 - 2016 by haui - all rights reserved
 */
package com.github.uscexp.splshell.parser;

import com.github.uscexp.grappa.extension.interpreter.ProcessStore;
import com.github.uscexp.grappa.extension.nodes.AstTreeNode;

/**
 * Command implementation for the <code>SplParser</code> rule: forStatement.
 */
public class AstForStatementTreeNode<V> extends AstBaseCommandTreeNode<V> {

	public AstForStatementTreeNode(String rule, String value) {
		super(rule, value);
	}

	@Override
	protected void interpretAfterChilds(Long id)
		throws Exception {
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
