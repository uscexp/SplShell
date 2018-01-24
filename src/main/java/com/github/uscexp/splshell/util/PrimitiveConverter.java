package com.github.uscexp.splshell.util;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class PrimitiveConverter {

	private PrimitiveConverter() {
	}
	
	public static Object primitiveToWraper(boolean value) {
		return new Boolean(value);
	}
	
	public static Object primitiveToWraper(short value) {
		return new Short(value);
	}
	
	public static Object primitiveToWraper(int value) {
		return new Integer(value);
	}
	
	public static Object primitiveToWraper(byte value) {
		return new Byte(value);
	}
	
	public static Object primitiveToWraper(float value) {
		return new Float(value);
	}
	
	public static Object primitiveToWraper(double value) {
		return new Double(value);
	}
	
	public static Object primitiveToWraper(char value) {
		return new Character(value);
	}
	
	public static Object primitiveToWraper(long value) {
		return new Long(value);
	}
	
	public static Object[] primitiveToWraper(Object values) {
		List<Object> result = new ArrayList<>();
		for (int i = 0; i < Array.getLength(values); i++) {
			result.add(Array.get(values, i));
		}
		return result.toArray(new Object[result.size()]);
	}
}
