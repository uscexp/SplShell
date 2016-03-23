/*
 * Copyright (C) 2014 - 2015 by haui - all rights reserved
 */
package com.github.uscexp.splshell.interpreter;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Arrays;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.github.uscexp.grappa.extension.interpreter.type.MethodDeclaration;
import com.github.uscexp.grappa.extension.interpreter.type.MethodSignature;
import com.github.uscexp.grappa.extension.interpreter.type.Primitive;

/**
 * @author  haui
 */
public class MethodDefinition implements MethodDeclaration {

	private Class<?> type;
	private Constructor<?> constructor;
	private Method method;
	private boolean statik;
	private Class<?>[] parameter;
	private String returnType;

	public MethodDefinition(String type, String realMethod, boolean statik, Object[] parameter, String returnType)
		throws ReflectiveOperationException {
		super();

		Class<?> clazz = Class.forName(type);

		Class<?>[] params = null;
		if(parameter != null) {
			params = new Class<?>[parameter.length];
			for (int i = 0; i < parameter.length; i++) {
				if(parameter[i].equals("object[]")) {
					params[i] = Object[].class;
				} else {
					params[i] = Primitive.getClassFromType((String) parameter[i]);
				}
			}
		}

		if (realMethod.equals("constructor")) {
			this.constructor = clazz.getConstructor(params);
		} else {
			this.method = getDeclaredMethod(clazz, realMethod, params);
		}

		this.type = clazz;
		this.statik = statik;
		this.parameter = params;
		this.returnType = returnType;
	}

	/**
	 * invoke method.
	 * 
	 * @param  obj  the object the underlying method is invoked from
	 * @param  args  the arguments used for the method call
	 * @return  the result of dispatching the method represented by this object on {@code obj} with parameters
	 * @throws ReflectiveOperationException 
	 */
	public Object invoke(Object obj, Object[] args) throws ReflectiveOperationException {
		Object result = null;
		if(method != null) {
			result = method.invoke(obj, args);
		} else {
			result = constructor.newInstance(args);
		}
		return result;
	}

	public Class<?> getType() {
		return type;
	}

	public Constructor<?> getConstructor() {
		return constructor;
	}

	public Method getMethod() {
		return method;
	}

	public boolean isStatik() {
		return statik;
	}

	public Class<?>[] getParameter() {
		return parameter;
	}

	public String getReturnType() {
		return returnType;
	}

	public static Method getDeclaredMethod(Class<?> clazz, String methodName, Class<?>[] paramTypes) throws NoSuchMethodException {
		Method method = null;
		try {
			method = clazz.getDeclaredMethod(methodName, paramTypes);
		} catch (NoSuchMethodException e) {
			clazz = clazz.getSuperclass();
			if(clazz != null)
				method = getDeclaredMethod(clazz, methodName, paramTypes);
			if(method == null)
				throw e;
		}
		return method;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((constructor == null) ? 0 : constructor.hashCode());
		result = prime * result + ((method == null) ? 0 : method.hashCode());
		result = prime * result + Arrays.hashCode(parameter);
		result = prime * result
				+ ((returnType == null) ? 0 : returnType.hashCode());
		result = prime * result + (statik ? 1231 : 1237);
		result = prime * result + ((type == null) ? 0 : type.getName().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MethodDefinition other = (MethodDefinition) obj;
		if (constructor == null) {
			if (other.constructor != null)
				return false;
		} else if (!constructor.equals(other.constructor))
			return false;
		if (method == null) {
			if (other.method != null)
				return false;
		} else if (!method.equals(other.method))
			return false;
		if (!Arrays.equals(parameter, other.parameter))
			return false;
		if (returnType == null) {
			if (other.returnType != null)
				return false;
		} else if (!returnType.equals(other.returnType))
			return false;
		if (statik != other.statik)
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.getName().equals(other.type.getName()))
			return false;
		return true;
	}

	@Override
	public MethodSignature getMethodSignature() {
		MethodSignature methodSignature = new MethodIdentification(method.getName(), parameter);
		return methodSignature;
	}
}
