/*
 * Copyright (C) 2014 - 2016 by haui - all rights reserved
 */
package com.github.uscexp.splshell.parser;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;

import com.github.uscexp.grappa.extension.interpreter.type.Primitive;

/**
 * Command implementation for the <code>SplParser</code> rule: mappedBlock.
 */
public class AstMappedBlockTreeNode<V> extends AstBaseCommandTreeNode<V> {

	private boolean statik = false;
	private ArrayList<Object> paramDefs = new ArrayList<>();
	private String methodName;
	private String typeName;
	private String returnType;

	public AstMappedBlockTreeNode(String rule, String value) {
		super(rule, value);
	}

	@Override
	protected void interpretAfterChilds(Long id)
		throws Exception {
		super.interpretAfterChilds(id);
		processStore.createNewBlockVariableMap();

		Object object = null;
		Primitive[] primitives = new Primitive[paramDefs.size()];
		Object[] params = new Object[paramDefs.size()];
		Class<?>[] clazzs = new Class[paramDefs.size()];

		if (!statik) {
			object = processStore.getTierStack().pop();
		}

		for (int i = 0; i < paramDefs.size(); ++i) {
			int type = 0; // getType( (String)paramDefs.get( i));
			Object value = processStore.getTierStack().pop();
			if (value instanceof ArrayList) {
				params[i] = value;
				clazzs[i] = params[i].getClass();
			} else {
				Primitive primitive = new Primitive(String.class /* getClassFromType(type) */);
				primitive.setValue(value);
				primitives[i] = primitive;
				params[i] = primitive.getValue();
				clazzs[i] = primitives[i].getPrimitiveType();
			}
		}

		Class<?> clazz;
		Object returnObj = null;
		Object retVal = null;
		try {
			clazz = Class.forName(typeName);
			if (methodName.equals("constructor")) {
				Constructor<?> constructor = clazz.getConstructor(clazzs);
				returnObj = constructor.newInstance(params);
			} else {
				Method method = clazz.getMethod(methodName, clazzs);
				returnObj = method.invoke(object, params);
			}

			boolean array = false;

			if ((returnType != null) && returnType.endsWith("]")) {
				int idx = returnType.indexOf('[');
				if (idx > 0) {
					returnType = returnType.substring(0, idx);
					array = true;
				}
			}

			if (returnObj != null) {
				if ((returnObj instanceof Collection) && returnType.equals("ArrayList")) {
					ArrayList<Object> list = new ArrayList<>((Collection<?>) returnObj);
					retVal = list;
				} else if (array) {
					// TODO extend for multidimensional arrays
					ArrayList<Object> list = new ArrayList<>();
					Primitive primitive = null;
					int type = 0; //getType( returnType);
					for (int i = 0; i < Array.getLength(returnObj); ++i) {
						primitive = new Primitive(String.class /* getClassFromType( type) */);
						primitive.setValue(Array.get(returnObj, i));
						list.add(primitive);
					}
					retVal = list;
				} else {
					try {
						Primitive primitive = null;
						if (returnType != null) {
							int type = 0; //getType( returnType);
							primitive = new Primitive(String.class /* getClassFromType( type) */);
							primitive.setValue(returnObj);
						} else {
							primitive = new Primitive(returnObj);
						}
						retVal = primitive.getValue();
					} catch (Exception e) {
						System.err.println(e.getMessage() + " Return type not supported!");
					}
				}
				if (retVal != null)
					processStore.getTierStack().push(retVal);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		processStore.removeLastBlockVariableMap();
	}

}
