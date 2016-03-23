/*
 * Copyright (C) 2014 - 2015 by haui - all rights reserved
 */
package com.github.uscexp.splshell.parser;

import java.lang.reflect.Array;
import java.util.Arrays;

import org.parboiled.Node;

import com.github.uscexp.grappa.extension.interpreter.ProcessStore;
import com.github.uscexp.grappa.extension.interpreter.type.Primitive;
import com.github.uscexp.grappa.extension.nodes.AstCommandTreeNode;
import com.github.uscexp.grappa.extension.nodes.AstTreeNode;

/**
 * @author  haui
 */
public class AstBaseCommandTreeNode<V> extends AstCommandTreeNode<V> {

	protected ProcessStore<Object> processStore;

	public AstBaseCommandTreeNode(Node<?> node, String value) {
		super(node, value);
	}

	@Override
	protected void interpretBeforeChilds(Long id)
		throws Exception {
		processStore = ProcessStore.getInstance(id);
		System.out.println(toString() + " - before");
	}

	@Override
	protected void interpretAfterChilds(Long id)
		throws Exception {
		processStore = ProcessStore.getInstance(id);
		System.out.println(toString() + " - after");
	}

	public Object createVariableObject(int arrayDimension, String variableType) {
		return createVariableObject(arrayDimension, variableType, null);
	}
	
	public Object createVariableObject(int arrayDimension, String variableType, Object variableValue) {
		Object variableObject = null;
		if(variableValue instanceof Primitive)
			variableValue = ((Primitive)variableValue).getValue();
		if (arrayDimension > 0) {
			int[] dimensions = new int[arrayDimension];
			for (int i = 0; i < dimensions.length; i++) {
				dimensions[i] = 1;
			}
			variableObject = Array.newInstance(Primitive.class, dimensions);
			int[] indices = new int[arrayDimension];
			for (int i = 0; i < indices.length; i++) {
				indices[i] = 0;
			}
			if(variableValue == null)
				setValue(variableObject, new Primitive(Primitive.getClassFromType(variableType)), indices);
			else {
				setValue(variableObject, new Primitive(Primitive.getClassFromType(variableType), variableValue), indices);
			}
		} else {
			if(variableValue == null)
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
		if (indecies.length == 1)
			((Object[]) array)[indecies[0]] = value;
		else
			setValue(Array.get(array, indecies[0]), value, tail(indecies));
	}

	private int[] tail(int[] arr) {
		return Arrays.copyOfRange(arr, 1, arr.length);
	}
	
	protected boolean isFirstChildAnExpression() {
		boolean result = false;
		if(getChildren().size() > 0) {
			AstTreeNode<V> treeNode = getChildren().get(0);
			if(treeNode instanceof AstAdditiveExpressionTreeNode
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
//					|| treeNode instanceof AstExpressionTreeNode
					) {
				result = true;
			}
		}
		return result;
	}
}
