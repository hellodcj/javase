package com.dcj.core.collection;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 通过entryset可以一次性获得map中的key和value值
 * @author dcj
 *
 */
public class MapEntryDemo {
	public void test(){
		Map<String,Object> map = new HashMap<String,Object>();
		for (Entry entry : map.entrySet()){
			String key = entry.getKey().toString();
			Object value = entry.getValue();
		}
	}
	
	public void test02(){
		Map<String,Object> map = new HashMap<String,Object>();
		for (String key : map.keySet()){
			Object value = map.get(key);
		}
	}
}
