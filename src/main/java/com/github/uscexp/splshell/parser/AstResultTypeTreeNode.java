/*
 * Copyright (C) 2014 - 2016 by haui - all rights reserved
 */
package com.github.uscexp.splshell.parser;

import java.util.Stack;


/**
 * Command implementation for the <code>SplParser</code> rule: resultType.
 * 
 */
public class AstResultTypeTreeNode<V >
    extends AstBaseCommandTreeNode<V> implements AstMethodInterface
{


    public AstResultTypeTreeNode(String rule, String value) {
		super(rule, value);
    }

    @Override
    protected void interpretAfterChilds(Long id)
        throws Exception
    {
		super.interpretAfterChilds(id);
		String returnValue = value.trim();
		
		if(returnValue.equals("void")) {
			Stack<Object> stack = processStore.getTierStack();
			stack.push(returnValue);
		}
    }

	@Override
	public void invoke(Long id) {
	}

}
