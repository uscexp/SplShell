/*
 * Copyright (C) 2014 - 2016 by haui - all rights reserved
 */
package com.github.uscexp.splshell.parser;

/**
 * Command implementation for the <code>SplParser</code> rule: relationalExpression.
 * 
 */
public class AstRelationalExpressionTreeNode<V >
    extends AstBaseCommandTreeNode<V>
{


    public AstRelationalExpressionTreeNode(String rule, String value) {
		super(rule, value);
    }

    @Override
    protected void interpretAfterChilds(Long id)
        throws Exception
    {
		super.interpretAfterChilds(id);
        if(!isFirstChildAnExpression()) {
        	
        }
    }

}
