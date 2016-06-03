/*
 * Copyright (C) 2014 - 2016 by haui - all rights reserved
 */
package com.github.uscexp.splshell.parser;

/**
 * Command implementation for the <code>SplParser</code> rule: returnStatement.
 * 
 */
public class AstReturnStatementTreeNode<V >
    extends AstBaseCommandTreeNode<V>
{


    public AstReturnStatementTreeNode(String rule, String value) {
		super(rule, value);
    }

    @Override
    protected void interpretAfterChilds(Long id)
        throws Exception
    {
		super.interpretAfterChilds(id);
        System.out.println("ReturnStatement");;
    }

}
