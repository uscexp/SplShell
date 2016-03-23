/*
 * Copyright (C) 2014 by haui - all rights reserved
 */
package com.github.uscexp.splshell.parser;

import org.parboiled.Node;

import com.github.uscexp.grappa.extension.interpreter.type.Primitive;

/**
 * Command implementation for the <code>SplParser</code> rule: readStatement.
 */
public class AstReadStatementTreeNode<V> extends AstBaseCommandTreeNode<V> {

	public AstReadStatementTreeNode(Node<?> node, String value) {
		super(node, value);
	}

	@Override
	protected void interpretAfterChilds(Long id)
		throws Exception {
		super.interpretAfterChilds(id);
		Primitive primitive;
		byte[] b = new byte[64];
		int i;

		String name = "";

		if ((primitive = processStore.getPrimitiveVariable(name)) == null)
			System.err.println("Undefined variable : " + name);

		System.out.print("Enter a value for \'" + name + "\' (" + primitive.getPrimitiveType().getName() + ") : ");
		System.out.flush();
		i = System.in.read(b);
		primitive.setValue((new String(b, 0, 0, i - 1)).trim());
		processStore.setVariable(name, primitive);
	}

}
