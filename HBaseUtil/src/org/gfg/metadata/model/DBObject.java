package org.gfg.metadata.model;

import java.util.Map;

import org.bson.BSONObject;


public interface DBObject extends BSONObject{
	public Map<String, Object> toMap();
}
