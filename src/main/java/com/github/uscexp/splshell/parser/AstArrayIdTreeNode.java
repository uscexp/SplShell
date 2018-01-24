/*
 * Copyright (C) 2014 - 2016 by haui - all rights reserved
 */
package com.github.uscexp.splshell.parser;

import java.util.ArrayList;

import com.github.uscexp.grappa.extension.interpreter.type.Primitive;
import com.github.uscexp.splshell.util.ArrayUtil;

/**
 * Command implementation for the <code>SplParser</code> rule: arrayId.
 */
public class AstArrayIdTreeNode<V> extends AstBaseCommandTreeNode<V> {

	public AstArrayIdTreeNode(String rule, String value) {
		super(rule, value);
	}

	@Override
	protected void interpretAfterChilds(Long id)
			throws Exception {
		super.interpretAfterChilds(id);
		String identifier = value.trim();
		if (getParent() != null && getParent().getParent() != null && getParent().getParent() instanceof AstAssignmentTreeNode) {
			processStore.getTierStack().push(identifier);
		} else {
			Object result = ArrayUtil.getArrayValueOnIndex(value, processStore);
			// remove var name from stack
			processStore.getTierStack().pop();
			processStore.getTierStack().push(result);
		}
	}

	@SuppressWarnings("unchecked")
	public Object setArrayValue(Long id, String name, Object value, int dim, Primitive[] primitiveIdxs) {
		ArrayList<Object> list = (ArrayList<Object>) processStore.getVariable(name);
		Object object = list;
		int i = 0;

		for (i = 0; i < (dim - 1); ++i) {
			list = (ArrayList<Object>) list.get(primitiveIdxs[i].getIntegerValue());
		}
		expandArray(primitiveIdxs[i].getIntegerValue(), list);
		Primitive primitive = (Primitive) list.get(primitiveIdxs[i].getIntegerValue());
		primitive.setObjectValue(value);
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
