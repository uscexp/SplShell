/*
 * Copyright (C) 2014 - 2015 by haui - all rights reserved
 */
package com.github.uscexp.splshell.interpreter;

/**
 * @author haui
 *
 */
public class Parameter {

	private Class<?> type;
	private String name;
	private int dimension;
	
	public Parameter(Class<?> type, String name, int dimension) {
		super();
		this.type = type;
		this.name = name;
		this.dimension = dimension;
	}

	public Class<?> getType() {
		return type;
	}

	public String getName() {
		return name;
	}

	public int getDimension() {
		return dimension;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + dimension;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Parameter other = (Parameter) obj;
		if (dimension != other.dimension)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.getName().equals(other.type.getName()))
			return false;
		return true;
	}
}
