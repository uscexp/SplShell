/*
 * Copyright (C) 2014 - 2016 by haui - all rights reserved
 */
package com.github.uscexp.splshell.parser;

import com.github.uscexp.grappa.extension.interpreter.type.Primitive;


/**
 * Command implementation for the <code>SplParser</code> rule: preDecrementExpression.
 * 
 */
public class AstPreDecrementExpressionTreeNode<V >
    extends AstBaseCommandTreeNode<V>
{


    public AstPreDecrementExpressionTreeNode(String rule, String value) {
		super(rule, value);
    }

    @Override
    protected void interpretAfterChilds(Long id)
        throws Exception
    {
		super.interpretAfterChilds(id);
		if(value != null) {
	        String name = value.substring(2);
	        Primitive primitive = processStore.getPrimitiveVariable( name);
	        primitive.decrement();
	        processStore.getTierStack().push( primitive.getValue());
	        processStore.setVariable( name, primitive);
		}
    }

}
