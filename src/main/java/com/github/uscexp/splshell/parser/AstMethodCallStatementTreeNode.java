/*
 * Copyright (C) 2014 - 2016 by haui - all rights reserved
 */
package com.github.uscexp.splshell.parser;

import java.util.ArrayList;
import java.util.List;

import com.github.uscexp.grappa.extension.interpreter.type.MethodDeclaration;
import com.github.uscexp.grappa.extension.interpreter.type.MethodSignature;
import com.github.uscexp.grappa.extension.interpreter.type.Primitive;
import com.github.uscexp.grappa.extension.util.IStack;
import com.github.uscexp.splshell.interpreter.MethodDefinition;
import com.github.uscexp.splshell.interpreter.MethodIdentification;
import com.github.uscexp.splshell.interpreter.ScriptMethodDefinition;

/**
 * Command implementation for the <code>SplParser</code> rule: methodCallStatement.
 */
public class AstMethodCallStatementTreeNode<V> extends AstBaseCommandTreeNode<V> {

	private MethodSignature methodSignature;

	public AstMethodCallStatementTreeNode(String rule, String value) {
		super(rule, value);
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

		processStore.moveWorkingMapToArchive();
		processStore.createNewBlockVariableMap();
		
		IStack<Object> stack = processStore.getTierStack();
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
		if(methodDeclaration == null) {
			
			MethodIdentification methodIdentification = ((MethodIdentification)methodSignature);
			Class<?>[] parameterTypes = methodIdentification.getParameterTypes();
			for (int i = 0; i < parameterTypes.length; ++i) {
				Class<?> parameterType = paramenterTypes[i];
				if(parameterType.isArray()) {
					parameterType = new Object[0].getClass();
					paramenterTypes[i] = parameterType;
				}
			}
			methodDeclaration = processStore.getMethod(methodSignature);
		}
		Object result = null;
		if (methodDeclaration != null) {
			if(methodDeclaration instanceof ScriptMethodDefinition) {
				result = ((ScriptMethodDefinition)methodDeclaration).invoke(id, processStore, args);
			} else if (methodDeclaration instanceof MethodDefinition) {
				result = ((MethodDefinition)methodDeclaration).invoke(id, processStore, args);
			}
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
			Object primitiveValue = ((Primitive)arg).getValue();
			if(primitiveValue.getClass().isArray()) {
				result = primitiveValue.getClass();
			} else {
				result = Primitive.getClassFromTypeIdx(((Primitive)arg).getTypeId());
			}
		} else {
			result = arg.getClass();
		}
		return result;
	}

}
