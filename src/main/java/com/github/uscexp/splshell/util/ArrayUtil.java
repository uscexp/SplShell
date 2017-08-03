package com.github.uscexp.splshell.util;

import java.lang.reflect.Array;
import java.util.List;

import com.github.uscexp.grappa.extension.interpreter.ProcessStore;
import com.github.uscexp.grappa.extension.interpreter.type.Primitive;

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
	
	public static Object setArrayValue(Object array, int idx, Object value) {
		Object result = array;
		
		if(!result.getClass().isArray()) {
			throw new IllegalArgumentException("Input is not an array");
		}
		Array.set(result, idx, value);
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public static Object getArrayValueFromList(List<Object> list) {
		Object result = new Object[list.size()];
		
		for (int i = 0; i < list.size(); i++) {
			Object object = list.get(i);
			if(object instanceof List) {
				object = getArrayValueFromList((List<Object>)object);
			} else {
				object = list.get(i);
			}
			Array.set(result, i, object);
		}
		return result;
	}
	
	public static boolean isArray(String id) {
		return id.indexOf('[') > 0;
	}
	
	public static String getIdFromArrayId(String arrayId) {
		int idx = arrayId.indexOf('[');
		String name = arrayId.substring(0, idx).trim();
		return name;
	}
	
	public static Object getArrayValueOnIndex(String id, ProcessStore<Object> processStore) {
		int idx = id.indexOf('[');
		String name = id.substring(0, idx).trim();
		Object variable = processStore.getVariable(name);
		if(variable instanceof Primitive) {
			variable = ((Primitive)variable).getValue();
		}
		int dim = idx > 0 ? 1 : 0;
		while ((idx = id.indexOf("[", idx+1)) > 0) {
			++dim;
		}
		int[] indices = new int[dim];
		int i = dim - 1;

		for (i = dim - 1; (i + dim) >= dim; --i) {
			Object idxValue = processStore.getTierStack().pop();
			if(idxValue instanceof Primitive) {
				indices[i] = ((Primitive)idxValue).getIntegerValue();
			} else {
				indices[i] = (int)idxValue;
			}
		}
		return ArrayUtil.getArrayValue(variable, indices);
	}
	
	public static Object setArrayValueOnIndex(String id, Object value, ProcessStore<Object> processStore) {
		int idx = id.indexOf('[');
		String name = id.substring(0, idx).trim();
		Object variable = processStore.getVariable(name);
		if(variable instanceof Primitive) {
			variable = ((Primitive)variable).getValue();
		}
		Object arrayToSet = variable;
		int dim = idx > 0 ? 1 : 0;
		while ((idx = id.indexOf("[", idx+1)) > 0) {
			++dim;
		}
		int[] indices = new int[dim];
		int i = dim - 1;

		Object val = null;
		int index = 0;
		for (i = dim - 1; (i + dim) >= dim; --i) {
			Object idxValue = processStore.getTierStack().pop();
			if(idxValue instanceof Primitive) {
				indices[i] = ((Primitive)idxValue).getIntegerValue();
			} else {
				indices[i] = (int)idxValue;
			}
		}
		for (int j = 0; j < indices.length; j++) {
			val = ArrayUtil.getArrayValue(arrayToSet, indices[j]);
			if(val.getClass().isArray())
				arrayToSet = val;
			else {
				index = indices[j];
				break;
			}
		}
		return ArrayUtil.setArrayValue(arrayToSet, index, value);
	}
}
