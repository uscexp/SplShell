/*
 * Copyright (C) 2014 - 2016 by haui - all rights reserved
 */
package com.github.uscexp.splshell.parser;

import com.github.uscexp.grappa.extension.interpreter.type.Primitive;


/**
 * Command implementation for the <code>SplParser</code> rule: postIncrementExpression.
 * 
 */
public class AstPostIncrementExpressionTreeNode<V >
    extends AstBaseCommandTreeNode<V>
{


    public AstPostIncrementExpressionTreeNode(String rule, String value) {
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
	        primitive.increment();
	        processStore.setVariable( name, primitive);
		}
    }

}
