package com.github.uscexp.splshell.util;

import java.lang.reflect.Array;

public class ArrayUtil {

	private ArrayUtil() {

	}

	public static int getArrayDimension(Object array) {
		int dimension = 0;
		Class<?> c = array.getClass();
		if (!c.isArray()) {
			throw new IllegalArgumentException("Object is not  an  array");
		}
		while (c.isArray()) {
			dimension++;
			c = c.getComponentType();
		}
		return dimension;
	}
	
	public static Object getArrayValue(Object array, int...idx) {
		Object result = array;
		
		for (int i = 0; i < idx.length; i++) {
			if(!result.getClass().isArray()) {
				throw new IllegalArgumentException(String.format("Input is not an array of %d dimensions", idx.length));
			}
			result = Array.get(result, idx[i]);
		}
		return result;
	}
}
