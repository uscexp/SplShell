/*
 * Copyright (C) 2014 by haui - all rights reserved
 */
package com.github.uscexp.splshell.parser;

import org.parboiled.Node;


/**
 * Command implementation for the <code>SplParser</code> rule: labeledStatement.
 * 
 */
public class AstLabeledStatementTreeNode<V >
    extends AstBaseCommandTreeNode<V>
{


    public AstLabeledStatementTreeNode(Node<?> node, String value) {
		super(node, value);
    }

    @Override
    protected void interpretAfterChilds(Long id)
        throws Exception
    {
		super.interpretAfterChilds(id);
        System.out.println("LabeledStatement");;
    }

}
