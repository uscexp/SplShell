/*
 * Copyright (C) 2014 - 2016 by haui - all rights reserved
 */
package com.github.uscexp.splshell.parser;

import java.util.Stack;


/**
 * Command implementation for the <code>SplParser</code> rule: type.
 * 
 */
public class AstTypeTreeNode<V >
    extends AstBaseCommandTreeNode<V> implements AstMethodInterface
{


    public AstTypeTreeNode(String rule, String value) {
		super(rule, value);
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
