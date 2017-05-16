/*
 * Copyright (C) 2014 - 2016 by haui - all rights reserved
 */
package com.github.uscexp.splshell.parser;

import com.github.uscexp.grappa.extension.nodes.AstTreeNode;

/**
 * Command implementation for the <code>SplParser</code> rule: relationalExpression.
 * 
 */
public class AstRelationalExpressionTreeNode<V >
    extends AstCompareExpressionTreeNode<V>
{

    public AstRelationalExpressionTreeNode(String rule, String value) {
		super(rule, value);
    }

    @Override
    protected void interpretAfterChilds(Long id)
        throws Exception
    {
		super.interpretAfterChilds(id);
    }

	protected boolean isSecondChildAnRelationalLiteral() {
		boolean result = false;
		if(getChildren().size() > 1) {
			AstTreeNode<V> treeNode = getChildren().get(1);
			if(treeNode instanceof AstRelationalLiteralTreeNode) {
				result = true;
			}
		}
		return result;
	}

	@Override
	boolean existChildConditionalLiteral() {
		return isSecondChildAnRelationalLiteral();
	}
}
