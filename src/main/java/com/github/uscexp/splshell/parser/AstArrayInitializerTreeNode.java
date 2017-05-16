/*
 * Copyright (C) 2014 - 2016 by haui - all rights reserved
 */
package com.github.uscexp.splshell.parser;

import java.util.ArrayList;

import com.github.uscexp.grappa.extension.util.IStack;


/**
 * Command implementation for the <code>SplParser</code> rule: arrayInitializer.
 * 
 */
public class AstArrayInitializerTreeNode<V >
    extends AstBaseCommandTreeNode<V>
{


    public AstArrayInitializerTreeNode(String rule, String value) {
		super(rule, value);
    }

	@Override
	protected void interpretBeforeChilds(Long id) throws Exception {
		super.interpretBeforeChilds(id);
		processStore.tierOneUp(true);
	}

    @Override
    protected void interpretAfterChilds(Long id)
        throws Exception
    {
		super.interpretAfterChilds(id);
        ArrayList<Object> list = null;
        IStack<Object> stack = processStore.getTierStack();
        
        while (!stack.isEmpty()) {
			if(list == null)
				list = new ArrayList<>();
			list.add(0, processStore.getTierStack().pop());
		}
                
        processStore.tierOneDown(true);
        if(list != null)
        	processStore.getTierStack().push( list);
    }

}
