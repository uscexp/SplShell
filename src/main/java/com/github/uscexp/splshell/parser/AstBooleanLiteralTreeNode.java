/*
 * Copyright (C) 2014 by haui - all rights reserved
 */
package com.github.uscexp.splshell.parser;

import org.parboiled.Node;


/**
 * Command implementation for the <code>SplParser</code> rule: booleanLiteral.
 * 
 */
public class AstBooleanLiteralTreeNode<V >
    extends AstBaseCommandTreeNode<V>
{


    public AstBooleanLiteralTreeNode(Node<?> node, String value) {
        super(node, value);
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
        Boolean bool = new Boolean(value);
        processStore.getTierStack().push(bool);
    }

}
