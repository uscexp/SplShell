/*
 * Copyright (C) 2014 - 2016 by haui - all rights reserved
 */
package com.github.uscexp.splshell.parser;

import java.util.ArrayList;

import com.github.uscexp.grappa.extension.interpreter.type.Primitive;

/**
 * Command implementation for the <code>SplParser</code> rule: assignment.
 * 
 */
public class AstAssignmentTreeNode<V> extends AstBaseCommandTreeNode<V> {

	public AstAssignmentTreeNode(String rule, String value) {
		super(rule, value);
	}

	@Override
	protected void interpretAfterChilds(Long id) throws Exception {
		super.interpretAfterChilds(id);
		String name = null;

		Object object = processStore.getTierStack().peek();

		name = "";

		{
			processStore.getTierStack().pop();
			name = "";
			object = null;
		}

		if (object instanceof ArrayList) {
			@SuppressWarnings({ "unchecked" })
			ArrayList<Object> list = (ArrayList<Object>) processStore.getVariable(name);
			processStore.setVariable(name, object);
		} else {
			Primitive primitive = processStore.getPrimitiveVariable(name);
			primitive.setValue(processStore.getTierStack().peek());
			processStore.setVariable(name, primitive);
		}
	}

}
