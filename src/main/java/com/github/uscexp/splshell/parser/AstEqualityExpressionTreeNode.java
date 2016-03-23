/*
 * Copyright (C) 2014 by haui - all rights reserved
 */
package com.github.uscexp.splshell.parser;

import org.parboiled.Node;

import com.github.uscexp.grappa.extension.interpreter.type.Primitive;


/**
 * Command implementation for the <code>SplParser</code> rule: equalityExpression.
 * 
 */
public class AstEqualityExpressionTreeNode<V >
    extends AstBaseCommandTreeNode<V>
{


    public AstEqualityExpressionTreeNode(Node<?> node, String value) {
		super(node, value);
    }

    @Override
    protected void interpretAfterChilds(Long id)
        throws Exception
    {
		super.interpretAfterChilds(id);
		if(!isFirstChildAnExpression()) {
	        Primitive v2 = new Primitive( processStore.getTierStack().pop());
	        Primitive v1 = new Primitive( processStore.getTierStack().pop());
	        Primitive result = new Primitive( boolean.class, v1.equals( v2));
	        processStore.getTierStack().push( result.getValue());
		}
    }

}
