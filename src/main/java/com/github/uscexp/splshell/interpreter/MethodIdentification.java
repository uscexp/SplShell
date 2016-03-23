/*
 * Copyright (C) 2014 - 2015 by haui - all rights reserved
 */
package com.github.uscexp.splshell.interpreter;

import java.util.Arrays;

import com.github.uscexp.grappa.extension.interpreter.type.MethodSignature;

/**
 * @author haui
 *
 */
public class MethodIdentification implements MethodSignature {

	private String name;
	private Class<?>[] parameterTypes;
	
	public MethodIdentification(String name, Class<?>[] parameterTypes) {
		super();
		this.name = name;
		this.parameterTypes = parameterTypes;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		String[] classNames = getClassNames(parameterTypes);
		result = prime * result + Arrays.hashCode(classNames);
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
		MethodIdentification other = (MethodIdentification) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		String[] classNames = getClassNames(parameterTypes);
		String[] otherClassNames = getClassNames(other.parameterTypes);
		
		if (!Arrays.equals(classNames, otherClassNames))
			return false;
		return true;
	}

	private String[] getClassNames(Class<?>[] parameterTypes) {
		String[] classNames = new String[parameterTypes.length];
		for (int i = 0; i < parameterTypes.length; i++) {
			classNames[i] = parameterTypes[i].getName();
		}
		return classNames;
	}
}
