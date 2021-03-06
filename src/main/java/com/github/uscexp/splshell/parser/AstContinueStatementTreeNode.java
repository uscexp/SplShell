/*
 * Copyright (C) 2014 - 2016 by haui - all rights reserved
 */
package com.github.uscexp.splshell.parser;

import com.github.uscexp.grappa.extension.interpreter.ProcessStore;


/**
 * Command implementation for the <code>SplParser</code> rule: continueStatement.
 * 
 */
public class AstContinueStatementTreeNode<V >
    extends AstBaseCommandTreeNode<V>
{


    public AstContinueStatementTreeNode(String rule, String value) {
		super(rule, value);
    }

    @Override
    protected void interpretAfterChilds(Long id)
        throws Exception
    {
		super.interpretAfterChilds(id);
        processStore.setExecState( ProcessStore.CONT_STATE);
    }

}
