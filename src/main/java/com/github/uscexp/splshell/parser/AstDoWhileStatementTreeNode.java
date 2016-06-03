/*
 * Copyright (C) 2014 - 2016 by haui - all rights reserved
 */
package com.github.uscexp.splshell.parser;

import com.github.uscexp.grappa.extension.interpreter.ProcessStore;

/**
 * Command implementation for the <code>SplParser</code> rule: doWhileStatement.
 */
public class AstDoWhileStatementTreeNode<V> extends AstBaseCommandTreeNode<V> {

	public AstDoWhileStatementTreeNode(String rule, String value) {
		super(rule, value);
	}

	@Override
	protected void interpretAfterChilds(Long id)
		throws Exception {
		super.interpretAfterChilds(id);
		do {
			if (processStore.getExecState() == ProcessStore.BREAK_STATE) {
				processStore.setExecState(ProcessStore.OK_STATE);
				break;
			}
			if (processStore.getExecState() == ProcessStore.CONT_STATE) {
				processStore.setExecState(ProcessStore.OK_STATE);
			}

		} while (((Boolean) processStore.getTierStack().pop()).booleanValue());
	}

}
