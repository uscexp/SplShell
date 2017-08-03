/*
 * Copyright (C) 2014 - 2016 by haui - all rights reserved
 */
package com.github.uscexp.splshell.parser;

import com.github.uscexp.grappa.extension.interpreter.type.Primitive;

/**
 * Command implementation for the <code>SplParser</code> rule: charLiteral.
 * 
 */
public class AstCharLiteralTreeNode<V >
    extends AstBaseCommandTreeNode<V>
{


    public AstCharLiteralTreeNode(String rule, String value) {
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
        Primitive character = Primitive.createValue(value.trim().charAt(1));
        processStore.getTierStack().push(character);
    }

}
