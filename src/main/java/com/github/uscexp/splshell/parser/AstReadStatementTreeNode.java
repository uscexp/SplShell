/*
 * Copyright (C) 2014 - 2016 by haui - all rights reserved
 */
package com.github.uscexp.splshell.parser;

import com.github.uscexp.grappa.extension.interpreter.type.Primitive;
import com.github.uscexp.grappa.extension.util.IStack;

/**
 * Command implementation for the <code>SplParser</code> rule: readStatement.
 */
public class AstReadStatementTreeNode<V> extends AstBaseCommandTreeNode<V> {

	public AstReadStatementTreeNode(String rule, String value) {
		super(rule, value);
	}

	@Override
	protected void interpretAfterChilds(Long id)
			throws Exception {
		super.interpretAfterChilds(id);
		IStack<Object> stack = processStore.getTierStack();
		Primitive primitive;
		byte[] b = new byte[64];
		int i;

		Object object = stack.pop();
		primitive = getPrimitive(object);
		String name = (String) primitive.getValue();

		if ((primitive = processStore.getPrimitiveVariable(name)) == null) {
			System.err.println("Undefined variable : " + name);
		}

		System.out.print("Enter a value for \'" + name + "\' (" + primitive.getPrimitiveType().getName() + ") : ");
		System.out.flush();
		i = System.in.read(b);
		primitive.setValue((new String(b, 0, 0, i - 1)).trim());
		processStore.setVariable(name, primitive);
	}

}
