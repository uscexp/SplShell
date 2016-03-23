/*
 * Copyright (C) 2014 by haui - all rights reserved
 */
package com.github.uscexp.splshell.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.parboiled.Node;

import com.github.uscexp.grappa.extension.interpreter.type.MethodDeclaration;
import com.github.uscexp.grappa.extension.interpreter.type.MethodSignature;
import com.github.uscexp.grappa.extension.interpreter.type.Primitive;
import com.github.uscexp.grappa.extension.nodes.AstTreeNode;
import com.github.uscexp.splshell.interpreter.MethodIdentification;
import com.github.uscexp.splshell.interpreter.ScriptMethodDefinition;

/**
 * Command implementation for the <code>SplParser</code> rule: methodCallStatement.
 */
public class AstMethodCallStatementTreeNode<V> extends AstBaseCommandTreeNode<V> {

	private MethodSignature methodSignature;

	public AstMethodCallStatementTreeNode(Node<?> node, String value) {
		super(node, value);
	}

	@Override
	protected void interpretBeforeChilds(Long id) throws Exception {
		super.interpretBeforeChilds(id);
		processStore.tierOneUp(true);
	}

	@Override
	protected void interpretAfterChilds(Long id)
		throws Exception {
		super.interpretAfterChilds(id);
		int i, k = 0;

		processStore.moveWorkingMapToArchive();
		processStore.createNewBlockVariableMap();
		
		Stack<Object> stack = processStore.getTierStack();
		List<Object> args = new ArrayList<>();
		Object arg = null;
		String methodName = null;
		
		while (!stack.isEmpty()) {
			arg = stack.pop();
			if(stack.isEmpty()) {
				methodName = (String) arg;
			} else {
				args.add(0, arg);
			}
		}
		
		Class<?>[] paramenterTypes = new Class[args.size()];
		for (int j = 0; j < args.size(); j++) {
			paramenterTypes[j] = getClass(args.get(j));
		}
		methodSignature = new MethodIdentification(methodName, paramenterTypes);
		

		MethodDeclaration methodDeclaration = processStore.getMethod(methodSignature);
		if (methodDeclaration != null) {
			if(methodDeclaration instanceof ScriptMethodDefinition) {
				AstTreeNode<Object> methodImplementaion = ((ScriptMethodDefinition)methodDeclaration).getMethodImplementaion();
				
				for (int j = 0; j < args.size(); j++) {
					processStore.setNewVariable(((ScriptMethodDefinition) methodDeclaration).getParameter()[j], args.get(j));
				}
				// TODO: interpret methodImplementation and get result.
			}
		}
		Object result = null;
		if(!stack.isEmpty()) {
			result = stack.pop();
		}
		processStore.tierOneDown(true);
		if(result != null) {
			processStore.getTierStack().push(result);
		}

		processStore.restoreLastMapFromArchive();
	}

	private Class<?> getClass(Object arg) {
		Class<?> result = null;
		if(arg instanceof Primitive) {
			result = Primitive.getClassFromTypeIdx(((Primitive)arg).getTypeId());
		} else {
			result = arg.getClass();
		}
		return result;
	}

}
