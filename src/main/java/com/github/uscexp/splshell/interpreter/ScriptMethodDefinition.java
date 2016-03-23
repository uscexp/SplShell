/*
 * Copyright (C) 2014 - 2015 by haui - all rights reserved
 */
package com.github.uscexp.splshell.interpreter;

import com.github.uscexp.grappa.extension.interpreter.type.MethodDeclaration;
import com.github.uscexp.grappa.extension.interpreter.type.MethodSignature;
import com.github.uscexp.grappa.extension.nodes.AstTreeNode;

/**
 * @author haui
 *
 */
public class ScriptMethodDefinition implements MethodDeclaration {

	private String name;
	private Parameter[] parameter;
	private String returnType;
	private AstTreeNode<Object> methodImplementaion;
	
	public ScriptMethodDefinition(String name, String returnType,
			Parameter[] parameter, AstTreeNode<Object> methodImplementaion) {
		super();
		this.name = name;
		this.returnType = returnType;
		this.parameter = parameter;
		this.methodImplementaion = methodImplementaion;
	}

	public String getName() {
		return name;
	}

	public Class<?>[] getParameterTypes() {
		Class<?>[] parameterTypes = new Class[parameter.length];
		for (int i = 0; i < parameter.length; i++) {
			parameterTypes[i] = parameter[i].getType();
		}
		return parameterTypes;
	}

	public Parameter[] getParameter() {
		return parameter;
	}

	public String getReturnType() {
		return returnType;
	}

	public AstTreeNode<Object> getMethodImplementaion() {
		return methodImplementaion;
	}

	@Override
	public MethodSignature getMethodSignature() {
		return new MethodIdentification(name, getParameterTypes());
	}
}
