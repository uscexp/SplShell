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
		int idx = value.indexOf('[');
		String name = value.substring(0, idx).trim();
		Object variable = processStore.getVariable(name);
		if(variable instanceof Primitive) {
			variable = ((Primitive)variable).getValue();
		}
		int dim = idx > 0 ? 1 : 0;
		while ((idx = value.indexOf("[", idx+1)) > 0) {
			++dim;
		}
		int[] indices = new int[dim];
		int i = dim - 1;

		for (i = dim - 1; (i + dim) >= dim; --i) {
			Object idxValue = processStore.getTierStack().pop();
			if(idxValue instanceof Primitive) {
				indices[i] = ((Primitive)idxValue).getIntegerValue();
			} else {
				indices[i] = (int)idxValue;
			}
		}
		Object result = ArrayUtil.getArrayValue(variable, indices);
		// remove var name from stack
		processStore.getTierStack().pop();
		processStore.getTierStack().push(result);
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
