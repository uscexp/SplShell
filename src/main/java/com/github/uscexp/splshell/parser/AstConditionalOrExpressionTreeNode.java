/*
 * Copyright (C) 2014 by haui - all rights reserved
 */
package com.github.uscexp.splshell.parser;

import org.parboiled.Node;


/**
 * Command implementation for the <code>SplParser</code> rule: conditionalOrExpression.
 * 
 */
public class AstConditionalOrExpressionTreeNode<V >
    extends AstBaseCommandTreeNode<V>
{


    public AstConditionalOrExpressionTreeNode(Node<?> node, String value) {
		super(node, value);
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
