/*
 * Copyright (C) 2014 by haui - all rights reserved
 */
package com.github.uscexp.splshell.parser;

import org.parboiled.Node;


/**
 * Command implementation for the <code>SplParser</code> rule: charLiteral.
 * 
 */
public class AstCharLiteralTreeNode<V >
    extends AstBaseCommandTreeNode<V>
{


    public AstCharLiteralTreeNode(Node<?> node, String value) {
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
        Character character = value.trim().charAt(1);
        processStore.getTierStack().push(character);
    }

}
