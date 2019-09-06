package org.yash.rms.json.mapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Component;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
@Component
public class TeamResourceJsonMapper<T> {
	
	public String toJson(T t){
		return new JSONSerializer().exclude("*.class").serialize(t);
	}
    
    public T fromJsonToObject(String json,Class<T> myclass){
    	return new JSONDeserializer<T>().use(null, myclass).deserialize(json);
    }
    
    public String toJsonArray(Collection<T> collection){
    	return new JSONSerializer().exclude("*.class").serialize(collection);
    }
    
    public Collection<T> fromJsonArrayToObjects(String json,Class<T> myclass){
    	return new JSONDeserializer<List<T>>().use(null, ArrayList.class).use("values", myclass).deserialize(json);
    }
}
