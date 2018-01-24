/*
 * Copyright (C) 2014 - 2015 by haui - all rights reserved
 */
package com.github.uscexp.splshell.parser;

import java.util.ArrayList;
import java.util.List;

import com.github.uscexp.grappa.extension.interpreter.ProcessStore;
import com.github.uscexp.grappa.extension.interpreter.type.MethodDeclaration;
import com.github.uscexp.grappa.extension.interpreter.type.Primitive;
import com.github.uscexp.grappa.extension.nodes.AstTreeNode;
import com.github.uscexp.grappa.extension.util.IStack;
import com.github.uscexp.splshell.interpreter.Parameter;
import com.github.uscexp.splshell.interpreter.ScriptMethodDefinition;

/**
 * Command implementation for the <code>SplParser</code> rule: methodDeclaration.
 */
public class AstMethodDeclarationTreeNode<V> extends AstBaseCommandTreeNode<V> {

	public AstMethodDeclarationTreeNode(String rule, String value) {
		super(rule, value);
	}

	@Override
	protected void interpretBeforeChilds(Long id)
		throws Exception {
		super.interpretBeforeChilds(id);
		processStore.tierOneUp(true);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void interpretAfterChilds(Long id)
		throws Exception {
		super.interpretAfterChilds(id);
		IStack<Object> stack = processStore.getTierStack();
		AstTreeNode<String> methodImplementation = (AstTreeNode<String>) stack.pop();
		String methodName = null;
		List<Parameter> parameter = new ArrayList<>();
		String returnType = null;
		while (stack.size() > 2) {
			String name = (String) stack.pop();
			int arrayDimension = (int) stack.pop();
			String type = (String) stack.pop();
			parameter.add(0, new Parameter(Primitive.getClassFromType(type), name, arrayDimension));
		}
		methodName = (String) stack.pop();
		String type = (String) stack.pop();
		returnType = type;
		MethodDeclaration methodDeclaration = new ScriptMethodDefinition(methodName, returnType,
				parameter.toArray(new Parameter[parameter.size()]), methodImplementation);
		processStore.addMethod(methodDeclaration.getMethodSignature(), methodDeclaration);
		processStore.tierOneDown(true);
	}

	@Override
	public void interpretIt(Long id, boolean forewardOrder) throws Exception {
	    if( !ProcessStore.getInstance(id).checkPrecondition())
	        return;
	    interpretBeforeChilds(id);
	    int i = 0;
	    for (i = 0; i < 2; i++) {
	    	getChildren().get(i).interpretIt(id, forewardOrder);
		}
	    IStack<Object> stack = ProcessStore.getInstance(id).getTierStack();
	    stack.push(getChildren().get(i));
	    
		interpretAfterChilds(id);
	}

}
