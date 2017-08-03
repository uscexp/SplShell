/*
 * Copyright (C) 2014 - 2016 by haui - all rights reserved
 */
package com.github.uscexp.splshell.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.github.uscexp.grappa.extension.interpreter.ProcessStore;
import com.github.uscexp.grappa.extension.interpreter.type.Primitive;
import com.github.uscexp.grappa.extension.nodes.AstTreeNode;
import com.github.uscexp.grappa.extension.util.IStack;

/**
 * Command implementation for the <code>SplParser</code> rule: switchStatement.
 * 
 */
public class AstSwitchStatementTreeNode<V>
		extends AstBaseCommandTreeNode<V> {

	private AstTreeNode<V> switchExpression;
	private Map<AstTreeNode<V>, List<AstTreeNode<V>>> switchCases = new HashMap<>();
	private AstTreeNode<V> defaultCase;

	public AstSwitchStatementTreeNode(String rule, String value) {
		super(rule, value);
		this.skipAutoInterpretation = true; // take control over the execution process
	}

	@Override
	protected void interpretBeforeChilds(Long id) throws Exception {
		super.interpretBeforeChilds(id);
		AstTreeNode<V> currentSwitchCase = null;
		for (AstTreeNode<V> astTreeNode : children) {
			if (astTreeNode instanceof AstConditionalOrExpressionTreeNode) {
				switchExpression = astTreeNode;
			} else if (astTreeNode instanceof AstSwitchLabelTreeNode) {
				currentSwitchCase = astTreeNode;
				List<AstTreeNode<V>> blockStatements = new ArrayList<>();
				switchCases.put(astTreeNode, blockStatements);
			} else if (astTreeNode instanceof AstBlockStatementTreeNode) {
				if (astTreeNode.getValue().startsWith("default")) {
					defaultCase = astTreeNode;
					currentSwitchCase = astTreeNode;
					List<AstTreeNode<V>> blockStatements = new ArrayList<>();
					switchCases.put(astTreeNode, blockStatements);
				} else {
					List<AstTreeNode<V>> blockStatements = switchCases.get(currentSwitchCase);
					blockStatements.add(astTreeNode);
				}
			}
		}
	}

	@Override
	protected void interpretAfterChilds(Long id)
			throws Exception {
		super.interpretAfterChilds(id);
		switchExpression.interpretIt(id, true);
		Object variable = processStore.getVariable(switchExpression.getValue().trim());
		Primitive toCompare = getPrimitive(variable);
		processStore.createNewBlockVariableMap();
		IStack<Object> stack = processStore.getTierStack();
		Set<AstTreeNode<V>> keySet = switchCases.keySet();
		boolean executed = false;
		Primitive primitive = null;
		for (AstTreeNode<V> astTreeNode : keySet) {
			if (astTreeNode instanceof AstSwitchLabelTreeNode) {
				astTreeNode.interpretIt(id, true);
				if (!executed && processStore.getExecState() == ProcessStore.OK_STATE) {
					Object object = stack.pop();
					if (object instanceof Primitive) {
						primitive = new Primitive(toCompare.getPrimitiveType(), ((Primitive) object).getValue());
					} else {
						primitive = new Primitive(toCompare.getPrimitiveType(), object);
					}
				}
				if (primitive.equals(toCompare)) {
					List<AstTreeNode<V>> blockStatements = switchCases.get(astTreeNode);
					for (AstTreeNode<V> blockStatement : blockStatements) {
						blockStatement.interpretIt(id, true);
					}
					executed = true;
					if (processStore.getExecState() == ProcessStore.BREAK_STATE) {
						break;
					}
				}
			}
		}
		if (!executed || (executed && processStore.getExecState() == ProcessStore.OK_STATE)) {
			List<AstTreeNode<V>> defaultBlockStatements = switchCases.get(defaultCase);
			defaultCase.interpretIt(id, true);
			for (AstTreeNode<V> astTreeNode : defaultBlockStatements) {
				astTreeNode.interpretIt(id, true);
			}
		}
		if (processStore.getExecState() == ProcessStore.BREAK_STATE) {
			processStore.setExecState(ProcessStore.OK_STATE);
		}
		processStore.removeLastBlockVariableMap();
	}

}
