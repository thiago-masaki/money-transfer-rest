package com.revolut.restful.json;

import java.io.StringReader;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.json.Json;
import javax.json.JsonNumber;
import javax.json.JsonObject;
import javax.json.JsonReader;

public class JsonToObjectConverter<T> {

	public Object convertToObject(String json, Class<?> cls) throws Exception {
		Class cl = Class.forName(cls.getName());
		T obj = (T) cl.newInstance();

		JsonReader jreader = Json.createReader(new StringReader(json));
		JsonObject jObject = jreader.readObject();
		jreader.close();

		for (Field attribute : cls.getDeclaredFields()) {

			if (attribute.isAnnotationPresent(JsonElement.class)) {
				String anottation = attribute.getAnnotation(JsonElement.class).key();
				String attributeName = attribute.getName();
				String jsonName = null;
				if (jObject.containsKey(attributeName))
					jsonName = attributeName;
				else if (jObject.containsKey(anottation))
					jsonName = anottation;
				if (jsonName != null) {
					if (!attribute.getType().isPrimitive()
							&& !attribute.getType().toString().startsWith("class java.lang")) {
						String jsonobj = jObject.getJsonObject(jsonName).toString();
						attribute.setAccessible(true);
						attribute.set(obj, convertToObject(jsonobj, Class.forName(attribute.getType().getName())));
					} else {
						java.lang.reflect.Method method = cl.getMethod(
								"set" + attributeName.substring(0, 1).toUpperCase() + attributeName.substring(1),
								attribute.getType());
						if (attribute.getType() == String.class)
							method.invoke(obj, jObject.getString(jsonName));
						else { 
							JsonNumber num = jObject.getJsonNumber(jsonName);
							if (num.isIntegral())
								method.invoke(obj, num.intValue());
							else
								method.invoke(obj, num.doubleValue());
						}						
					}
				}
			}
		}

		return obj;
	}
	
	private static final Map<String, Class> BUILT_IN_MAP = 
		    new ConcurrentHashMap<String, Class>();

		static {
		    for (Class c : new Class[]{Integer.class,String.class,void.class, boolean.class, byte.class, char.class,  
		            short.class, int.class, float.class, double.class, long.class})
		        BUILT_IN_MAP.put(c.getSimpleName(), c);
		}
}
