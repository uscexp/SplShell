/*
 * Copyright (C) 2014 - 2016 by haui - all rights reserved
 */
package com.github.uscexp.splshell.parser;

import java.util.ArrayList;
import java.util.List;

import com.github.uscexp.grappa.extension.interpreter.type.Primitive;
import com.github.uscexp.splshell.util.ArrayUtil;

/**
 * Command implementation for the <code>SplParser</code> rule: assignment.
 * 
 */
public class AstAssignmentTreeNode<V> extends AstBaseCommandTreeNode<V> {

	public AstAssignmentTreeNode(String rule, String value) {
		super(rule, value);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void interpretAfterChilds(Long id) throws Exception {
		super.interpretAfterChilds(id);
		Object nameValue = null;
		String name = null;

		Object object = processStore.getTierStack().pop();

		nameValue = processStore.getTierStack().pop();

		if (nameValue instanceof Primitive) {
			name = (String) ((Primitive) nameValue).getValue();
		} else {
			name = (String) nameValue;
		}

		Object variableValue = processStore.getVariable(name);

		if (object instanceof ArrayList) {
			variableValue = ArrayUtil.getArrayValueFromList((List<Object>) object);
		} else if (object.getClass().isArray()) {
			variableValue = object;
		} else {
			variableValue = getPrimitive(object);
		}

		if (variableValue != null) {
			if (ArrayUtil.isArray(name)) {
				ArrayUtil.setArrayValueOnIndex(name, variableValue, processStore);
			} else {
				processStore.setVariable(name, variableValue);
			}
		}
	}

}
