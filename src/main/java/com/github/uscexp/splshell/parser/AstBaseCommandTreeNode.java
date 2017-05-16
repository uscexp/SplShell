/*
 * Copyright (C) 2014 - 2016 by haui - all rights reserved
 */
package com.github.uscexp.splshell.parser;

import java.lang.reflect.Array;
import java.util.Arrays;

import com.github.uscexp.grappa.extension.interpreter.ProcessStore;
import com.github.uscexp.grappa.extension.interpreter.type.Primitive;
import com.github.uscexp.grappa.extension.nodes.AstCommandTreeNode;
import com.github.uscexp.grappa.extension.nodes.AstTreeNode;

/**
 * @author haui
 */
public class AstBaseCommandTreeNode<V> extends AstCommandTreeNode<V> {

	protected ProcessStore<Object> processStore;

	public AstBaseCommandTreeNode(String rule, String value) {
		super(rule, value);
	}

	@Override
	protected void interpretBeforeChilds(Long id)
			throws Exception {
		processStore = ProcessStore.getInstance(id);
		if (processStore.isLogging())
			System.out.println(toString() + " - before");
	}

	@Override
	protected void interpretAfterChilds(Long id)
			throws Exception {
		processStore = ProcessStore.getInstance(id);
		if (processStore.isLogging())
			System.out.println(toString() + " - after");
	}

	public Object createVariableObject(int arrayDimension, String variableType) {
		return createVariableObject(arrayDimension, variableType, null);
	}

	public Object createVariableObject(int arrayDimension, String variableType, Object variableValue) {
		Object variableObject = null;
		if (variableValue instanceof Primitive)
			variableValue = ((Primitive) variableValue).getValue();
		if (arrayDimension > 0) {
			int[] dimensions = new int[arrayDimension];
			for (int i = 0; i < dimensions.length; i++) {
				dimensions[i] = 0;
			}
			variableObject = Array.newInstance(Primitive.class, dimensions);
			int[] indices = new int[arrayDimension];
			for (int i = 0; i < indices.length; i++) {
				indices[i] = 0;
			}
			if (variableValue == null)
				setValue(variableObject, new Primitive(Primitive.getClassFromType(variableType)), indices);
			else {
				setValue(variableObject, new Primitive(Primitive.getClassFromType(variableType), variableValue), indices);
			}
		} else {
			if (variableValue == null)
				variableObject = new Primitive(Primitive.getClassFromType(variableType));
			else
				variableObject = new Primitive(Primitive.getClassFromType(variableType), variableValue);
		}
		return variableObject;
	}

	protected Object getValue(Object array, int... indecies) {
		if (indecies.length == 1)
			return ((Object[]) array)[indecies[0]];
		else
			return getValue(Array.get(array, indecies[0]), tail(indecies));
	}

	protected void setValue(Object array, Object value, int... indecies) {
		if (indecies.length == 1) {
			if (Array.getLength(array) > indecies[0])
				Array.set(array, indecies[0], value);
		} else {
			if (Array.getLength(array) > indecies[0]) {
				setValue(Array.get(array, indecies[0]), value, tail(indecies));
			}
		}
	}

	private int[] tail(int[] arr) {
		return Arrays.copyOfRange(arr, 1, arr.length);
	}

	protected Primitive getPrimitive(Object object) {
		Primitive result = null;

		if (object instanceof Primitive) {
			result = (Primitive) object;
		} else {
			result = new Primitive(object);
		}

		return result;
	}

	protected boolean isParentAnExpression() {
		boolean result = false;
		result = isTreeNodeAnExpression(getParent());
		return result;
	}

	protected boolean isFirstChildAnExpression() {
		boolean result = false;
		if (getChildren().size() > 0) {
			AstTreeNode<V> treeNode = getChildren().get(0);
			result = isTreeNodeAnExpression(treeNode);
		}
		return result;
	}

	protected boolean isTreeNodeAnExpression(AstTreeNode<V> treeNode) {
		boolean result = false;
		if (treeNode instanceof AstAdditiveExpressionTreeNode
				|| treeNode instanceof AstAndExpressionTreeNode
				|| treeNode instanceof AstConditionalAndExpressionTreeNode
				|| treeNode instanceof AstConditionalOrExpressionTreeNode
				|| treeNode instanceof AstEqualityExpressionTreeNode
				|| treeNode instanceof AstExclusiveOrExpressionTreeNode
				|| treeNode instanceof AstInclusiveOrExpressionTreeNode
				|| treeNode instanceof AstMultiplicativeExpressionTreeNode
				|| treeNode instanceof AstPostDecrementExpressionTreeNode
				|| treeNode instanceof AstPostIncrementExpressionTreeNode
				|| treeNode instanceof AstPreDecrementExpressionTreeNode
				|| treeNode instanceof AstPreIncrementExpressionTreeNode
				|| treeNode instanceof AstPrimaryExpressionTreeNode
				|| treeNode instanceof AstRelationalExpressionTreeNode
				|| treeNode instanceof AstStatementExpressionTreeNode
				|| treeNode instanceof AstUnaryExpressionTreeNode
		//				|| treeNode instanceof AstExpressionTreeNode
		) {
			result = true;
		}
		return result;
	}

	protected boolean checkForExistingChildren(Class<AstTreeNode<V>>... classes) {
		boolean result = false;

		if (getChildren().size() >= classes.length) {
			for (int i = 0; i < classes.length; i++) {
				result = getChildren().get(i).getClass().equals(classes[i]);
				if (!result)
					break;
			}
		}

		return result;
	}

	protected boolean evaluateCondition(Long id, AstTreeNode<V> conditionTreeNode) throws Exception {
		conditionTreeNode.interpretIt(id, true);

		Object conditionValue = processStore.getTierStack().pop();

		if (conditionValue instanceof Primitive) {
			conditionValue = ((Primitive) conditionValue).getValue();
		}

		return ((Boolean) conditionValue).booleanValue();
	}
}
