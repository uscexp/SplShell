/*
 * Copyright (C) 2014 - 2016 by haui - all rights reserved
 */
package com.github.uscexp.splshell.parser;

import com.github.uscexp.grappa.extension.nodes.AstTreeNode;

/**
 * Command implementation for the <code>SplParser</code> rule: equalityExpression.
 * 
 */
public class AstEqualityExpressionTreeNode<V >
    extends AstCompareExpressionTreeNode<V>
{

    public AstEqualityExpressionTreeNode(String rule, String value) {
		super(rule, value);
    }

    @Override
    protected void interpretAfterChilds(Long id)
        throws Exception
    {
		super.interpretAfterChilds(id);
    }

	protected boolean isSecondChildAnEqualityLiteral() {
		boolean result = false;
		if(getChildren().size() > 1) {
			AstTreeNode<V> treeNode = getChildren().get(1);
			if(treeNode instanceof AstEqualityLiteralTreeNode) {
				result = true;
			}
		}
		return result;
	}

	@Override
	boolean existChildConditionalLiteral() {
		return isSecondChildAnEqualityLiteral();
	}

}
