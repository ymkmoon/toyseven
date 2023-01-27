package com.toyseven.ymk.common.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.google.common.base.Joiner;

import lombok.experimental.UtilityClass;

@UtilityClass
public final class DataParsingUtil {

	public static Map<String, Object> toMap(JSONObject jsonobj)  {
		Map<String, Object> map = new HashMap<String, Object>();
		@SuppressWarnings("unchecked")
		Iterator<String> keys = jsonobj.keySet().iterator();
		while(keys.hasNext()) {
			String key = keys.next();
			Object value = jsonobj.get(key);
			if (value instanceof JSONArray) {
				value = toList((JSONArray) value);
			} else if (value instanceof JSONObject) {
				value = toMap((JSONObject) value);
			}   
			map.put(key, value);
		}   return map;
	}

	public static List<Object> toList(JSONArray array) {
		List<Object> list = new ArrayList<Object>();
		for(int i = 0; i < array.size(); i++) {
			Object value = array.get(i);
			if (value instanceof JSONArray) {
				value = toList((JSONArray) value);
			}
			else if (value instanceof JSONObject) {
				value = toMap((JSONObject) value);
			}
			list.add(value);
		}   return list;
	}
	
	public static String paramMapToString(Map<String, String[]> paramMap) {
	    return paramMap.entrySet().stream()
	    		.map(entry -> String.format("%s -> (%s)",entry.getKey(), Joiner.on(",").join(entry.getValue())))
	    		.collect(Collectors.joining(", "));
	}
}
