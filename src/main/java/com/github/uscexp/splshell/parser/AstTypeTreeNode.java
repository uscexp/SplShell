/*
 * Copyright (C) 2014 by haui - all rights reserved
 */
package com.github.uscexp.splshell.parser;

import java.util.Stack;

import org.parboiled.Node;


/**
 * Command implementation for the <code>SplParser</code> rule: type.
 * 
 */
public class AstTypeTreeNode<V >
    extends AstBaseCommandTreeNode<V> implements AstMethodInterface
{


    public AstTypeTreeNode(Node<?> node, String value) {
		super(node, value);
    }

    @Override
    protected void interpretAfterChilds(Long id)
        throws Exception
    {
		super.interpretAfterChilds(id);
		Stack<Object> stack = processStore.getTierStack();
		String returnValue = value.trim();
		stack.push(returnValue);
    }

	@Override
	public void invoke(Long id) {
	}

}
