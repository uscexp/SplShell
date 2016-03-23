/*
 * Copyright (C) 2014 by haui - all rights reserved
 */
package com.github.uscexp.splshell.parser;

import org.parboiled.Node;


/**
 * Command implementation for the <code>SplParser</code> rule: forInit.
 * 
 */
public class AstForInitTreeNode<V >
    extends AstBaseCommandTreeNode<V>
{


    public AstForInitTreeNode(Node<?> node, String value) {
		super(node, value);
    }

    @Override
    protected void interpretAfterChilds(Long id)
        throws Exception
    {
		super.interpretAfterChilds(id);
    	System.out.println("ForInit");
    }

}
