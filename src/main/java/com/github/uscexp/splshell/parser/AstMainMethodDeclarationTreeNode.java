/*
 * Copyright (C) 2014 - 2015 by haui - all rights reserved
 */
package com.github.uscexp.splshell.parser;

import org.parboiled.Node;

/**
 * Command implementation for the <code>SplParser</code> rule: mainMethodDeclaration.
 */
public class AstMainMethodDeclarationTreeNode<V> extends AstBaseCommandTreeNode<V> {

	public AstMainMethodDeclarationTreeNode(Node<?> node, String value) {
		super(node, value);
	}

	@Override
	protected void interpretBeforeChilds(Long id)
		throws Exception {
		super.interpretBeforeChilds(id);
	}

	@Override
	protected void interpretAfterChilds(Long id)
		throws Exception {
		super.interpretAfterChilds(id);
	}

}
