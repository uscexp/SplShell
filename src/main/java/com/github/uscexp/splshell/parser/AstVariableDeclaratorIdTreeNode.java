/*
 * Copyright (C) 2014 by haui - all rights reserved
 */
package com.github.uscexp.splshell.parser;

import org.parboiled.Node;


/**
 * Command implementation for the <code>SplParser</code> rule: variableDeclaratorId.
 * 
 */
public class AstVariableDeclaratorIdTreeNode<V >
    extends AstBaseCommandTreeNode<V>
{


    public AstVariableDeclaratorIdTreeNode(Node<?> node, String value) {
		super(node, value);
    }

    @Override
	protected void interpretBeforeChilds(Long id) throws Exception {
		super.interpretBeforeChilds(id);
		int arrayDimension = 0;
		int idx = 0;
		if(value != null) {
			while ((idx = value.indexOf("[", idx+1)) > 0) {
				++arrayDimension;
			}
		}
		processStore.getTierStack().push(arrayDimension);
	}

	@Override
    protected void interpretAfterChilds(Long id)
        throws Exception
    {
		super.interpretAfterChilds(id);
    }

}
