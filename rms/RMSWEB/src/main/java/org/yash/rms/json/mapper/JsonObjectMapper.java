package org.yash.rms.json.mapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;
import org.yash.rms.util.Constants;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import flexjson.transformer.DateTransformer;
@Component
public class JsonObjectMapper<T> {
	
	public String toJson(T t){
		return new JSONSerializer().exclude("*.class").serialize(t);
	}
    
    public T fromJsonToObject(String json,Class<T> myclass){
    	return new JSONDeserializer<T>().use(null, myclass).deserialize(json);
    }
    
    public String toJsonArray(Collection<T> collection){
    	return new JSONSerializer().exclude("*.class").serialize(collection);
    }
    
    public String toJsonArrayModule(Collection<T> collection){
      return new JSONSerializer().exclude("*.class","projectId").serialize(collection);
    }
    
    public String toJsonArrayOrg(Collection<T> collection){
    	return new JSONSerializer().include("id","name","creationDate","active","parentDeactive","moveLink","orgHierarchies.id","orgHierarchies.name","orgHierarchies.parentId.id","orgHierarchies.parentId.name","orgHierarchies.description","orgHierarchies.creationDate","orgHierarchies.employeeId.employeeId","orgHierarchies.employeeId.employeeName","orgHierarchies.active","orgHierarchies.parentDeactive","parentId.id","parentId.name","orgHierarchies","employeeId.employeeName","orgHierarchies.moveLink","employeeId.employeeId","employeeId").exclude("*").transform(new DateTransformer(Constants.DATE_PATTERN), Date.class).serialize(collection);
    }
    
    public Collection<T> fromJsonArrayToObjects(String json,Class<T> myclass){
    	return new JSONDeserializer<List<T>>().use(null, ArrayList.class).use("values", myclass).deserialize(json);
    }
    public String toJsonTicketPriority(Collection<T> collection){
    		return new JSONSerializer()
    			.include("id", "priority","active", "projectId")
    			.transform(new DateTransformer(Constants.DATE_PATTERN_4),
    					Date.class).exclude("*").serialize(collection);
    }
    public String toJsonTicketStatus(Collection<T> collection){
		return new JSONSerializer()
			.include("id", "status","active", "projectId")
			.transform(new DateTransformer(Constants.DATE_PATTERN_4),
					Date.class).exclude("*").serialize(collection);
}
}
