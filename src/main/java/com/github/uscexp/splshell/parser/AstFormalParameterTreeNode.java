/*
 * Copyright (C) 2014 - 2016 by haui - all rights reserved
 */
package com.github.uscexp.splshell.parser;

import java.util.ArrayList;

import com.github.uscexp.grappa.extension.interpreter.type.Primitive;


/**
 * Command implementation for the <code>SplParser</code> rule: formalParameter.
 * 
 */
public class AstFormalParameterTreeNode<V >
    extends AstBaseCommandTreeNode<V> implements AstMethodInterface
{


    public AstFormalParameterTreeNode(String rule, String value) {
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

	@Override
	public void invoke(Long id) {
        String name = "";
        Object value = processStore.getTierStack().pop();

        if( value instanceof ArrayList)
        {
          ArrayList<Object> list = (ArrayList<Object>)processStore.getVariable(name);
          processStore.setVariable( name, value);
        }
        else
        {
          Primitive primitive = processStore.getPrimitiveVariable( name);
          primitive.setValue( value);
          processStore.setVariable( name, primitive);
        }
	}

}
