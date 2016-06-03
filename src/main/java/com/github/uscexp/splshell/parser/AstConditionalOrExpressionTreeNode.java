/*
 * Copyright (C) 2014 - 2016 by haui - all rights reserved
 */
package com.github.uscexp.splshell.parser;

/**
 * Command implementation for the <code>SplParser</code> rule: conditionalOrExpression.
 * 
 */
public class AstConditionalOrExpressionTreeNode<V >
    extends AstBaseCommandTreeNode<V>
{


    public AstConditionalOrExpressionTreeNode(String rule, String value) {
		super(rule, value);
    }

    @Override
    protected void interpretAfterChilds(Long id)
        throws Exception
    {
		super.interpretAfterChilds(id);
		if(!isFirstChildAnExpression()) {
			if (!((Boolean) processStore.getTierStack().peek()).booleanValue()) {
				return;
			}
	
			Boolean boolean2 = (Boolean) processStore.getTierStack().pop();
			Boolean boolean1 = (Boolean) processStore.getTierStack().pop();
	
			processStore.getTierStack().push(new Boolean(boolean1.booleanValue() || boolean2.booleanValue()));
		}
    }

}
