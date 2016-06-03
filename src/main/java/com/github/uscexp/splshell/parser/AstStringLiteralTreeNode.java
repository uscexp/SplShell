/*
 * Copyright (C) 2014 - 2016 by haui - all rights reserved
 */
package com.github.uscexp.splshell.parser;

/**
 * Command implementation for the <code>SplParser</code> rule: stringLiteral.
 * 
 */
public class AstStringLiteralTreeNode<V >
    extends AstBaseCommandTreeNode<V>
{


    public AstStringLiteralTreeNode(String rule, String value) {
        super(rule, value);
    }

    @Override
    protected void interpretAfterChilds(Long id)
        throws Exception
    {
        super.interpretAfterChilds(id);
        String string = value.trim();
        string = string.substring(1, string.length()-1);
        processStore.getTierStack().push(string);
    }

    @Override
    protected void interpretBeforeChilds(Long id)
        throws Exception
    {
        super.interpretBeforeChilds(id);
    }

}
