/*
 * Copyright (C) 2014 - 2016 by haui - all rights reserved
 */
package com.github.uscexp.splshell.parser;

/**
 * Command implementation for the <code>SplParser</code> rule: switchStatement.
 * 
 */
public class AstSwitchStatementTreeNode<V>
		extends AstBaseCommandTreeNode<V> {

	public AstSwitchStatementTreeNode(String rule, String value) {
		super(rule, value);
	}

	@Override
	protected void interpretAfterChilds(Long id)
			throws Exception {
		super.interpretAfterChilds(id);
	}

}
