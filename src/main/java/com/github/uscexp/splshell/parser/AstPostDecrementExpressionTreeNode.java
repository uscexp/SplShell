/*
 * Copyright (C) 2014 - 2016 by haui - all rights reserved
 */
package com.github.uscexp.splshell.parser;

import com.github.uscexp.grappa.extension.interpreter.type.Primitive;


/**
 * Command implementation for the <code>SplParser</code> rule: postDecrementExpression.
 * 
 */
public class AstPostDecrementExpressionTreeNode<V >
    extends AstBaseCommandTreeNode<V>
{


    public AstPostDecrementExpressionTreeNode(String rule, String value) {
		super(rule, value);
    }

    @Override
    protected void interpretAfterChilds(Long id)
        throws Exception
    {
		super.interpretAfterChilds(id);
		if(value != null) {
			String name = value.substring(0, value.length() - 2);
	        Primitive primitive = processStore.getPrimitiveVariable( name);
	        processStore.getTierStack().push( primitive.getValue());
	        primitive.decrement();
	        processStore.setVariable( name, primitive);
		}
    }

}
