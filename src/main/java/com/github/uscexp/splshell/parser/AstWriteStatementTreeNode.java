/*
 * Copyright (C) 2014 - 2016 by haui - all rights reserved
 */
package com.github.uscexp.splshell.parser;

/**
 * Command implementation for the <code>SplParser</code> rule: writeStatement.
 * 
 */
public class AstWriteStatementTreeNode<V >
    extends AstBaseCommandTreeNode<V>
{


    public AstWriteStatementTreeNode(String rule, String value) {
		super(rule, value);
    }

    @Override
    protected void interpretAfterChilds(Long id)
        throws Exception
    {
		super.interpretAfterChilds(id);
        System.out.println("WriteStatement");
    }

}
