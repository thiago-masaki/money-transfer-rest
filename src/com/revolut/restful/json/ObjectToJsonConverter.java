package com.revolut.restful.json;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class ObjectToJsonConverter {
	public String convertToJson(Object object) throws JsonSerializationException {
		try {

			checkIfSerializable(object);
			initializeObject(object);
			return getJsonString(object,false);

		} catch (Exception e) {
			throw new JsonSerializationException(e.getMessage());
		}
	}

	private void checkIfSerializable(Object object) {
		if (Objects.isNull(object)) {
			throw new JsonSerializationException("Can't serialize a null object");
		}

		Class<?> clazz = object.getClass();
		if (!clazz.isAnnotationPresent(JsonSerializable.class)) {
			throw new JsonSerializationException(
					"The class " + clazz.getSimpleName() + " is not annotated with JsonSerializable");
		}
	}

	private void initializeObject(Object object)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Class<?> clazz = object.getClass();
		for (Method method : clazz.getDeclaredMethods()) {
			if (method.isAnnotationPresent(Init.class)) {
				method.setAccessible(true);
				method.invoke(object);
			}
		}
	}

	private String getJsonString(Object object, boolean t) throws IllegalArgumentException, IllegalAccessException, InstantiationException {
		Class<?> clazz = object.getClass();
		Map<String, String> jsonElementsMap = new HashMap<>();
		for (Field field : clazz.getDeclaredFields()) {
			field.setAccessible(true);
			if (field.isAnnotationPresent(JsonElement.class)) {
				if (!field.getType().isPrimitive() && !field.getType().getName().startsWith("java.lang")) {
					Object obj = field.get(object);
					String json = getJsonString(obj,true);
					jsonElementsMap.put(getKey(field), json);
				} else {
					if (field.getType().getSimpleName().equals("String"))
						jsonElementsMap.put(getKey(field), (String) "\""+field.get(object)+"\"");
					else
						jsonElementsMap.put(getKey(field), (String) ""+field.get(object)+"");
				}
			}
		}

		String jsonString;
		
		jsonString = jsonElementsMap.entrySet().stream()
				.map(entry -> "\"" + entry.getKey() + "\":" + entry.getValue() + "")
				.collect(Collectors.joining(","));
			
		return "{" + jsonString + "}";
	}

	private String getKey(Field field) {
		String value = field.getAnnotation(JsonElement.class).key();
		return value.isEmpty() ? field.getName() : value;
	}
}
