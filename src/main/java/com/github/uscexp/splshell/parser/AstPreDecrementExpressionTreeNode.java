/*
 * Copyright (C) 2014 by haui - all rights reserved
 */
package com.github.uscexp.splshell.parser;

import org.parboiled.Node;

import com.github.uscexp.grappa.extension.interpreter.type.Primitive;


/**
 * Command implementation for the <code>SplParser</code> rule: preDecrementExpression.
 * 
 */
public class AstPreDecrementExpressionTreeNode<V >
    extends AstBaseCommandTreeNode<V>
{


    public AstPreDecrementExpressionTreeNode(Node<?> node, String value) {
		super(node, value);
    }

    @Override
    protected void interpretAfterChilds(Long id)
        throws Exception
    {
		super.interpretAfterChilds(id);
        String name = "";
        Primitive primitive = processStore.getPrimitiveVariable( name);
        primitive.decrement();
        processStore.getTierStack().push( primitive.getValue());
        processStore.setVariable( name, primitive);
    }

}
