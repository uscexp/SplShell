/*
 * Copyright (C) 2014 by haui - all rights reserved
 */
package com.github.uscexp.splshell.parser;

import org.parboiled.Node;


/**
 * Command implementation for the <code>SplParser</code> rule: mainDeclaratorId.
 * 
 */
public class AstMainDeclaratorIdTreeNode<V >
    extends AstBaseCommandTreeNode<V>
{


    public AstMainDeclaratorIdTreeNode(Node<?> node, String value) {
		super(node, value);
    }

    @Override
    protected void interpretAfterChilds(Long id)
        throws Exception
    {
		super.interpretAfterChilds(id);
        String name = "args";
        Object value = processStore.getArgs();
        if( value == null)
          value = new String[0];
        
        processStore.setNewVariable( name, value);
    }

}
