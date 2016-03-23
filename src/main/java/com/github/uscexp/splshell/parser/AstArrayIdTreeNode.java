/*
 * Copyright (C) 2014 by haui - all rights reserved
 */
package com.github.uscexp.splshell.parser;

import java.util.ArrayList;

import org.parboiled.Node;

import com.github.uscexp.grappa.extension.interpreter.type.Primitive;

/**
 * Command implementation for the <code>SplParser</code> rule: arrayId.
 */
public class AstArrayIdTreeNode<V> extends AstBaseCommandTreeNode<V> {

	private String name;
	private int dim;
	private Primitive[] primitiveIdxs;

	public AstArrayIdTreeNode(Node<?> node, String value) {
		super(node, value);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void interpretAfterChilds(Long id)
		throws Exception {
		super.interpretAfterChilds(id);
		ArrayList<Object> list = (ArrayList<Object>) processStore.getVariable(name);
		Object value = null;
		primitiveIdxs = new Primitive[dim];
		int i = 0;
		int ii = dim - 1;

		for (ii = dim - 1; (ii + dim) >= dim; --ii) {
			primitiveIdxs[ii] = new Primitive(processStore.getTierStack().pop());
		}
		for (i = 0; i < dim; ++i) {
			if ((i + 1) < dim)
				list = (ArrayList<Object>) list.get(primitiveIdxs[i].getIntegerValue());
			else {
				if (primitiveIdxs[i].getIntegerValue() >= list.size()) {
					expandArray(primitiveIdxs[i].getIntegerValue(), list);
				}
				value = list.get(primitiveIdxs[i].getIntegerValue());
			}
		}
		if (value instanceof ArrayList)
			processStore.getTierStack().push(value);
		else
			processStore.getTierStack().push(((Primitive) value).getValue());
	}

	@SuppressWarnings("unchecked")
	public Object setArrayValue(Long id, Object value) {
		ArrayList<Object> list = (ArrayList<Object>) processStore.getVariable(name);
		Object object = list;
		int i = 0;

		for (i = 0; i < (dim - 1); ++i) {
			list = (ArrayList<Object>) list.get(primitiveIdxs[i].getIntegerValue());
		}
		expandArray(primitiveIdxs[i].getIntegerValue(), list);
		Primitive primitive = (Primitive) list.get(primitiveIdxs[i].getIntegerValue());
		primitive.setValue(value);
		list.set(primitiveIdxs[i].getIntegerValue(), primitive);
		return object;
	}

	private void expandArray(int idx, ArrayList<Object> list) {
		int i = 0;

		Primitive primitive = (Primitive) list.get(0);
		Class<?> type = primitive.getPrimitiveType();

		for (i = list.size(); i < (idx + 1); ++i) {
			list.add(new Primitive(type));
		}
	}
}
