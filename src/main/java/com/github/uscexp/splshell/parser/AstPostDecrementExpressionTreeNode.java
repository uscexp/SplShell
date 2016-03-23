/*
 * Copyright (C) 2014 by haui - all rights reserved
 */
package com.github.uscexp.splshell.parser;

import org.parboiled.Node;

import com.github.uscexp.grappa.extension.interpreter.type.Primitive;


/**
 * Command implementation for the <code>SplParser</code> rule: postDecrementExpression.
 * 
 */
public class AstPostDecrementExpressionTreeNode<V >
    extends AstBaseCommandTreeNode<V>
{


    public AstPostDecrementExpressionTreeNode(Node<?> node, String value) {
		super(node, value);
    }

    @Override
    protected void interpretAfterChilds(Long id)
        throws Exception
    {
		super.interpretAfterChilds(id);
        String name = "";
        Primitive primitive = processStore.getPrimitiveVariable( name);
        processStore.getTierStack().push( primitive.getValue());
        primitive.decrement();
        processStore.setVariable( name, primitive);
    }

}
