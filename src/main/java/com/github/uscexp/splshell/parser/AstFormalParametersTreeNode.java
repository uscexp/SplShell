/*
 * Copyright (C) 2014 by haui - all rights reserved
 */
package com.github.uscexp.splshell.parser;

import org.parboiled.Node;


/**
 * Command implementation for the <code>SplParser</code> rule: formalParameters.
 * 
 */
public class AstFormalParametersTreeNode<V >
    extends AstBaseCommandTreeNode<V> implements AstMethodInterface
{


    public AstFormalParametersTreeNode(Node<?> node, String value) {
		super(node, value);
    }

    @Override
    protected void interpretAfterChilds(Long id)
        throws Exception
    {
		super.interpretAfterChilds(id);
    	System.out.println("FormalParameters");
    }

	@Override
	public void invoke(Long id) {
	}

}
