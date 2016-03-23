/*
 * Copyright (C) 2014 - 2015 by haui - all rights reserved
 */
package com.github.uscexp.splshell.parser;

import org.parboiled.Node;

/**
 * Command implementation for the <code>SplParser</code> rule: mainParameter.
 */
public class AstMainParameterTreeNode<V> extends AstBaseCommandTreeNode<V> {

	public AstMainParameterTreeNode(Node<?> node, String value) {
		super(node, value);
	}

}
