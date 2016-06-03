/*
 * Copyright (C) 2014 - 2016 by haui - all rights reserved
 */
package com.github.uscexp.splshell.parser;

/**
 * Command implementation for the <code>SplParser</code> rule: integerLiteral.
 * 
 */
public class AstIntegerLiteralTreeNode<V >
    extends AstBaseCommandTreeNode<V>
{


    public AstIntegerLiteralTreeNode(String rule, String value) {
        super(rule, value);
    }

    @Override
    protected void interpretAfterChilds(Long id)
        throws Exception
    {
        super.interpretAfterChilds(id);
    }

    @Override
    protected void interpretBeforeChilds(Long id)
        throws Exception
    {
        super.interpretBeforeChilds(id);
        Integer integer = new Integer(value);
        processStore.getTierStack().push(integer);
    }

}
