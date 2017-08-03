/*
 * Copyright (C) 2014 - 2016 by haui - all rights reserved
 */
package com.github.uscexp.splshell.parser;

import com.github.uscexp.grappa.extension.util.IStack;

/**
 * Command implementation for the <code>SplParser</code> rule: block.
 */
public class AstBlockStatementTreeNode<V> extends AstBaseCommandTreeNode<V> {

	public AstBlockStatementTreeNode(String rule, String value) {
		super(rule, value);
	}

	@Override
	protected void interpretBeforeChilds(Long id) throws Exception {
		super.interpretBeforeChilds(id);
		IStack<Object> stack = processStore.tierOneUp(true);
	}

	@Override
	protected void interpretAfterChilds(Long id)
		throws Exception {
		super.interpretAfterChilds(id);
		IStack<Object> stack = processStore.tierOneDown(true);
	}

}
