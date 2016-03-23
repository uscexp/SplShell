/*
 * Copyright (C) 2014 by haui - all rights reserved
 */
package com.github.uscexp.splshell.parser;

import org.parboiled.Node;


/**
 * Command implementation for the <code>SplParser</code> rule: id.
 * 
 */
public class AstIdTreeNode<V >
    extends AstBaseCommandTreeNode<V>
{


    public AstIdTreeNode(Node<?> node, String value) {
		super(node, value);
    }

    @Override
    protected void interpretAfterChilds(Long id)
        throws Exception
    {
		super.interpretAfterChilds(id);
		String identifier = value.trim();
		Object variable = processStore.getVariable(identifier);
        processStore.getTierStack().push(variable);
    }

}
