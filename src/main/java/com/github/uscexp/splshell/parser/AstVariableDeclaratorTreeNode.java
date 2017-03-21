/*
 * Copyright (C) 2014 - 2016 by haui - all rights reserved
 */
package com.github.uscexp.splshell.parser;

/**
 * Command implementation for the <code>SplParser</code> rule: variableDeclarator.
 * 
 */
public class AstVariableDeclaratorTreeNode<V >
    extends AstBaseCommandTreeNode<V>
{


    public AstVariableDeclaratorTreeNode(String rule, String value) {
		super(rule, value);
    }

    @Override
	protected void interpretBeforeChilds(Long id) throws Exception {
		super.interpretBeforeChilds(id);
	}

	@Override
    protected void interpretAfterChilds(Long id)
        throws Exception
    {
		super.interpretAfterChilds(id);
    }

}
