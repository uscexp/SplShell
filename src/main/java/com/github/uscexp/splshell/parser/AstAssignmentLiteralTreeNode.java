/*
 * Copyright (C) 2014 by haui - all rights reserved
 */
package com.github.uscexp.splshell.parser;

import org.parboiled.Node;


/**
 * Command implementation for the <code>SplParser</code> rule: assignmentLiteral.
 * 
 */
public class AstAssignmentLiteralTreeNode<V >
    extends AstBaseCommandTreeNode<V>
{


    public AstAssignmentLiteralTreeNode(Node<?> node, String value) {
        super(node, value);
    }

	@Override
	protected void interpretBeforeChilds(Long id) throws Exception {
		super.interpretBeforeChilds(id);
	}

	@Override
	protected void interpretAfterChilds(Long id) throws Exception {
		super.interpretAfterChilds(id);
	}
}