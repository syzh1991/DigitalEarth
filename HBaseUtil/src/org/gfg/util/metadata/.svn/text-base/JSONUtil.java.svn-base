package org.gfg.util.metadata;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

public class JSONUtil {
	public static Map<String, String> getMapFromJSON(String fileName) {
		Map<String, String> transform = new HashMap<String, String>();
		String content = ReadFile.readFile(fileName);
		JSONObject json;
		try {
			json = new JSONObject(content);
			Iterator<String> iterator = json.keys();
			while (iterator.hasNext()) {
				String key = iterator.next();
				String value = json.getString(key);
				transform.put(key, value);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return transform;

	}

	public static void main(String[] args) {
		System.out.println(System.getProperty("user.dir"));
		String fileName=System.getProperty("user.dir")+"/res/level1.json";
		Map<String,String> map = JSONUtil.getMapFromJSON(fileName);
		for(String key:map.keySet()){
			System.out.println(key+"----"+map.get(key));
		}
	}
}
