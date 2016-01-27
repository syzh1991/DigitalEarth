package org.gfg.metadata.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;


import org.bson.BSONObject;
import org.json.JSONException;
import org.json.JSONObject;


public class BasicDBObject implements DBObject {
	Map<String, Object> data;
    /**
     *  Creates an empty object.
     */
    public BasicDBObject(){
    	data = new HashMap<String, Object>();
    }
    
    /**
     * creates an empty object
     * @param size an estimate of number of fields that will be inserted
     */
    public BasicDBObject(int size){
    	data = new HashMap<String, Object>(size);
    }

    /**
     * creates an object with the given key/value
     * @param key  key under which to store
     * @param value value to stor
     */
    public BasicDBObject(String key, Object value){
    	data = new HashMap<String, Object>();
    	data.put(key, value);
    }

    /**
     * Creates an object from a map.
     * @param m map to convert
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public BasicDBObject(Map m) {
    	this.data = m;
    }

    /**
     * Returns a JSON serialization of the map
     * @return JSON String
     */    
    
    public String toString(){
    	JSONObject json = new JSONObject();
    	for(String s :this.data.keySet()){
    		try {
				json.put(s,this.data.get(s));
			} catch (JSONException e) {
				e.printStackTrace();
			}
    	}
        return json.toString();
    }

    public BasicDBObject append( String key , Object val ){
    	data.put(key, val);
        return this;
    }

	@Override
	public Object put(String key, Object v) {
		data.put(key, v);
		return this;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void putAll(BSONObject o) {
		this.data = o.toMap();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void putAll(Map m) {
		for(Object key : m.keySet()) {
			data.put((String)key, m.get(key));
		}
		this.data = m;		
	}

	@Override
	public Object get(String key) {
		return data.get(key);
	}

	@Override
	public Map<String, Object> toMap() {
		return this.data;
	}

	@Override
	public Object removeField(String key) {
		data.remove(key);
		return this;
	}

	@Override
	public boolean containsKey(String s) {
		return data.containsKey(s);
	}

	@Override
	public boolean containsField(String s) {
		return data.containsKey(s);
	}

	@Override
	public Set<String> keySet() {
		return data.keySet();
	}
	
}
