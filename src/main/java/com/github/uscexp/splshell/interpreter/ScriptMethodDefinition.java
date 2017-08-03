/*
 * Copyright (C) 2014 - 2015 by haui - all rights reserved
 */
package com.github.uscexp.splshell.interpreter;

import java.util.List;

import com.github.uscexp.grappa.extension.exception.AstInterpreterException;
import com.github.uscexp.grappa.extension.interpreter.AstInterpreter;
import com.github.uscexp.grappa.extension.interpreter.ProcessStore;
import com.github.uscexp.grappa.extension.interpreter.type.MethodDeclaration;
import com.github.uscexp.grappa.extension.interpreter.type.MethodSignature;
import com.github.uscexp.grappa.extension.nodes.AstTreeNode;
import com.github.uscexp.grappa.extension.util.IStack;
import com.github.uscexp.splshell.parser.SplParser;

/**
 * @author haui
 *
 */
public class ScriptMethodDefinition implements MethodDeclaration {

	private String name;
	private Parameter[] parameters;
	private String returnType;
	private AstTreeNode<String> methodImplementaion;
	
	public ScriptMethodDefinition(String name, String returnType,
			Parameter[] parameters, AstTreeNode<String> methodImplementaion) {
		super();
		this.name = name;
		this.returnType = returnType;
		this.parameters = parameters;
		this.methodImplementaion = methodImplementaion;
	}

	public String getName() {
		return name;
	}

	public Class<?>[] getParameterTypes() {
		Class<?>[] parameterTypes = new Class[parameters.length];
		for (int i = 0; i < parameters.length; i++) {
			parameterTypes[i] = parameters[i].getType();
		}
		return parameterTypes;
	}

	public Parameter[] getParameter() {
		return parameters;
	}

	public String getReturnType() {
		return returnType;
	}

	public AstTreeNode<String> getMethodImplementaion() {
		return methodImplementaion;
	}

	public Object invoke(long id, ProcessStore<Object> processStore, List<Object> args) throws ReflectiveOperationException, AstInterpreterException {
		Object result = null;
		IStack<Object> stack = processStore.getTierStack();
		
		AstTreeNode<String> methodImplementaion = getMethodImplementaion();
		
		for (int j = 0; j < args.size(); j++) {
			processStore.setNewVariable(getParameter()[j], args.get(j));
		}
		
		AstInterpreter<String> astInterpreter = new AstInterpreter<>();
		
		astInterpreter.interpretForewardOrder(SplParser.class, methodImplementaion, id);
		stack = processStore.getTierStack();
		
		if(!stack.isEmpty())
			result = stack.pop();
		return result;
	}

	@Override
	public MethodSignature getMethodSignature() {
		return new MethodIdentification(name, getParameterTypes());
	}
}
