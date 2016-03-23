/*
 * Copyright (C) 2014 - 2015 by haui - all rights reserved
 */
package com.github.uscexp.splshell.parser;

import org.parboiled.Node;

/**
 * Command implementation for the <code>SplParser</code> rule: mainMethodDeclarator.
 */
public class AstMainMethodDeclaratorTreeNode<V> extends AstBaseCommandTreeNode<V> {

	public AstMainMethodDeclaratorTreeNode(Node<?> node, String value) {
		super(node, value);
	}

	@Override
	protected void interpretAfterChilds(Long id) throws Exception {
		super.interpretAfterChilds(id);
		processStore.getTierStack().clear();
	}
}
