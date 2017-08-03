package com.github.uscexp.splshell.parser;

import java.util.ArrayList;
import java.util.List;

import com.github.uscexp.grappa.extension.interpreter.type.Primitive;
import com.github.uscexp.grappa.extension.nodes.AstTreeNode;
import com.github.uscexp.grappa.extension.util.IStack;

public abstract class AstCalculationExpressionTreeNode<V> extends AstBaseCommandTreeNode<V> {

	public AstCalculationExpressionTreeNode(String rule, String value) {
		super(rule, value);
	}

	@SuppressWarnings("rawtypes")
	protected void process(Long id, Class<? extends AstTreeNode> valueTreeNode, Class<? extends AstTreeNode> operatorTreeNode)
			throws Exception {
		IStack<Object> stack = processStore.getTierStack();
		Primitive result = null;
		List<Primitive> values = new ArrayList<>();
		List<String> operators = new ArrayList<>();
		for (AstTreeNode<V> astTreeNode : children) {
			if (astTreeNode.getClass().isAssignableFrom(valueTreeNode)) {
				values.add(getPrimitive(stack.pop()));
			} else if (operatorTreeNode != null && astTreeNode.getClass().isAssignableFrom(operatorTreeNode)) {
				operators.add(astTreeNode.getValue().trim());
				if (!stack.isEmpty() && astTreeNode.getValue().trim().equals(stack.peek())) {
					stack.pop();
				}
			}
		}
		if (values.size() > 1) {
			result = values.get(values.size() - 1);
			for (int i = values.size() - 2; i >= 0; i--) {
				String operator = null;
				if (operators != null && !operators.isEmpty()) {
					operator = operators.get(i);
				}
				result = calculate(operator, result, values.get(i));
			}
		} else {
			result = values.get(0);
			if (operators != null && !operators.isEmpty()) {
				String operator = operators.get(0);
				result = calculate(operator, result, null);
			}
		}
		stack.push(result);
	}

	abstract protected Primitive calculate(String operator, Primitive v1, Primitive v2);
}
