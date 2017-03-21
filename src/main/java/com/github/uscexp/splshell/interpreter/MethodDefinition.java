/*
 * Copyright (C) 2014 - 2015 by haui - all rights reserved
 */
package com.github.uscexp.splshell.interpreter;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.github.uscexp.grappa.extension.interpreter.ProcessStore;
import com.github.uscexp.grappa.extension.interpreter.type.MethodDeclaration;
import com.github.uscexp.grappa.extension.interpreter.type.MethodSignature;
import com.github.uscexp.grappa.extension.interpreter.type.Primitive;
import com.github.uscexp.splshell.util.PrimitiveConverter;

/**
 * @author  haui
 */
public class MethodDefinition implements MethodDeclaration {

	private final String name;
	private final Class<?>[] parameters;
	private final Class<?> type;
	private final Constructor<?> constructor;
	private final Method method;
	private final boolean statik;
	private final Class<?>[] realParameters;
	private final String returnType;

	public MethodDefinition(String name, Object[] parameters, String type, String realMethod, boolean statik, Object[] realParameters, String returnType)
		throws ReflectiveOperationException {
		super();

		Class<?> clazz = Class.forName(type);

		Class<?>[] parametgerTypes = getParameterTypes(parameters);

		Class<?>[] realParametgerTypes = getParameterTypes(realParameters);

		if (realMethod.equals("constructor")) {
			this.constructor = clazz.getConstructor(realParametgerTypes);
			this.method = null;
		} else {
			this.constructor = null;
			this.method = getDeclaredMethod(clazz, realMethod, realParametgerTypes);
		}

		this.name = name;
		this.parameters = parametgerTypes;
		this.type = clazz;
		this.statik = statik;
		this.realParameters = realParametgerTypes;
		this.returnType = returnType;
	}

	private Class<?>[] getParameterTypes(Object[] parameters) {
		Class<?>[] parameterTypes = null;
		if(parameters != null) {
			parameterTypes = new Class<?>[parameters.length];
			for (int i = 0; i < parameters.length; i++) {
				if(parameters[i].equals("object[]")) {
					parameterTypes[i] = Object[].class;
				} else {
					parameterTypes[i] = Primitive.getClassFromType((String) parameters[i]);
				}
			}
		}
		return parameterTypes;
	}
	
	public Object invoke(long id, ProcessStore<Object> processStore, List<Object> args) throws ReflectiveOperationException {
		Object[] realArgs = null;
		int realParametersSize = 0;
		
		if(realParameters != null) {
			realParametersSize = realParameters.length;
			realArgs = new Object[realParametersSize];
		}
		Object executionObject = null;
		
		for (int i = 0, j = realParametersSize-1; i < args.size(); i++, j--) {
			Object object = args.get(i);
			if(object instanceof Primitive)
				object = ((Primitive)object).getValue();
			if(object.getClass().isArray()) {
				object = PrimitiveConverter.primitiveToWraper(object);
			}
			if(j >= 0) {
				realArgs[j] = object;
			} else {
				executionObject = object;
			}
		}
		
		return invoke(executionObject, realArgs);
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

	public String getName() {
		return name;
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
		return realParameters;
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
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((method == null) ? 0 : method.hashCode());
		if(parameters != null)
			result = prime * result + Arrays.hashCode(parameters);
		if(realParameters != null)
			result = prime * result + Arrays.hashCode(realParameters);
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
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (method == null) {
			if (other.method != null)
				return false;
		} else if (!method.equals(other.method))
			return false;
		if(parameters == null && other.parameters != null)
			return false;
		if(parameters != null && other.parameters == null)
			return false;
		if (!Arrays.equals(parameters, other.parameters))
			return false;
		if(realParameters == null && other.realParameters != null)
			return false;
		if(realParameters != null && other.realParameters == null)
			return false;
		if (!Arrays.equals(realParameters, other.realParameters))
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
		MethodSignature methodSignature = new MethodIdentification(name, parameters);
		return methodSignature;
	}
}
