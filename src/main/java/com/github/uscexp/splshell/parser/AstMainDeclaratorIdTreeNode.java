/*
 * Copyright (C) 2014 - 2016 by haui - all rights reserved
 */
package com.github.uscexp.splshell.parser;

/**
 * Command implementation for the <code>SplParser</code> rule: mainDeclaratorId.
 * 
 */
public class AstMainDeclaratorIdTreeNode<V >
    extends AstBaseCommandTreeNode<V>
{


    public AstMainDeclaratorIdTreeNode(String rule, String value) {
		super(rule, value);
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
