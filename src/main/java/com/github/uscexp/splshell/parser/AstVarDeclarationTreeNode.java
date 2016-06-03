/*
 * Copyright (C) 2014 - 2015 by haui - all rights reserved
 */
package com.github.uscexp.splshell.parser;

import java.util.List;
import java.util.Stack;

import com.github.uscexp.grappa.extension.nodes.AstTreeNode;

/**
 * Command implementation for the <code>SplParser</code> rule: varDeclaration.
 */
public class AstVarDeclarationTreeNode<V> extends AstBaseCommandTreeNode<V> {

	public AstVarDeclarationTreeNode(String rule, String value) {
		super(rule, value);
	}

	@Override
	protected void interpretBeforeChilds(Long id) throws Exception {
		super.interpretBeforeChilds(id);
	}

	@Override
	protected void interpretAfterChilds(Long id)
		throws Exception {
		super.interpretAfterChilds(id);
		Stack<Object> stack = processStore.getTierStack();
		Object variableValue = null;
		if(hasChildAssignmentLiteral()) {
			variableValue = stack.pop();
		}
		String variableName = (String) stack.pop();
		int arrayDimension = (int) stack.pop();
		String variableType = (String) stack.pop();

		Object variableObject = createVariableObject(arrayDimension, variableType, variableValue);
		processStore.setLocalVariable(variableName, variableObject);
	}

	public boolean hasChildAssignmentLiteral() {
		boolean result = false;
		List<AstTreeNode<V>> children = getChildren();
		
		for (AstTreeNode<V> child : children) {
			if(child instanceof AstVariableDeclaratorTreeNode && !result) {
				List<AstTreeNode<V>> subChildren = child.getChildren();
				for (AstTreeNode<V> subChild : subChildren) {
					if(subChild instanceof AstAssignmentLiteralTreeNode) {
						result = true;
						break;
					}
				}
			}
		}
		return result;
	}
}
