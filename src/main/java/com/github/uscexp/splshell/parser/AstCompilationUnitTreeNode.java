/*
 * Copyright (C) 2014 - 2015 by haui - all rights reserved
 */
package com.github.uscexp.splshell.parser;

/**
 * Command implementation for the <code>SplParser</code> rule: compilationUnit.
 * 
 */
public class AstCompilationUnitTreeNode<V> extends AstBaseCommandTreeNode<V> {

	public AstCompilationUnitTreeNode(String rule, String value) {
		super(rule, value);
	}

	@Override
	protected void interpretBeforeChilds(Long id) throws Exception {
		super.interpretBeforeChilds(id);
		processStore.tierOneUp(true);
	}

	@Override
	protected void interpretAfterChilds(Long id) throws Exception {
		super.interpretAfterChilds(id);
		processStore.tierOneDown(true);
	}

}
