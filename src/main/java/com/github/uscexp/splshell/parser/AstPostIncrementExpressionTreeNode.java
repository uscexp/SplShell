/*
 * Copyright (C) 2014 by haui - all rights reserved
 */
package com.github.uscexp.splshell.parser;

import org.parboiled.Node;

import com.github.uscexp.grappa.extension.interpreter.type.Primitive;


/**
 * Command implementation for the <code>SplParser</code> rule: postIncrementExpression.
 * 
 */
public class AstPostIncrementExpressionTreeNode<V >
    extends AstBaseCommandTreeNode<V>
{


    public AstPostIncrementExpressionTreeNode(Node<?> node, String value) {
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
        primitive.increment();
        processStore.setVariable( name, primitive);
    }

}
