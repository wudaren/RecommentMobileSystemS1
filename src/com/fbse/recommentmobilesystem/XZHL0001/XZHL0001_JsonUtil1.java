package com.fbse.recommentmobilesystem.XZHL0001;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

public class XZHL0001_JsonUtil1 {


	public static Map<String, Object> beanProperties2Map(Object obj) {
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		Field[] fields = obj.getClass().getDeclaredFields();
		for (Field field : fields) {
			Object value = getValue(obj, field);
			if (value != null) {
				map.put(field.getName(), value);
			}
		}
		return map;
	}

	private static Object getValue(Object object, Field field) {
		field.setAccessible(true);
		try {
			return field.get(object);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	public static String json2Miwen(String json) {
		JSONObject jso;
		try {
			jso = new JSONObject(json);
			return jso.getString("success");
		} catch (JSONException e) {
			e.printStackTrace();
			return "{JSONERROR}";
		}
	}
}