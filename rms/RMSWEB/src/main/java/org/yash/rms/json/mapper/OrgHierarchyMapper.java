package org.yash.rms.json.mapper;

import java.util.Collection;
import java.util.Date;

import org.springframework.stereotype.Component;
import org.yash.rms.domain.OrgHierarchy;
import org.yash.rms.domain.ResourceAllocation;
import org.yash.rms.util.Constants;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import flexjson.transformer.DateTransformer;

public class OrgHierarchyMapper {
	
	
	  public static OrgHierarchy  fromJsonToOrgHierarchy(String json) {
	        return new JSONDeserializer<OrgHierarchy>().use(null, OrgHierarchy.class).use(Date.class, new DateTransformer(Constants.DATE_PATTERN_4)).deserialize(json);
	    }

	  public static String toJson(OrgHierarchy orgHierarchy) {
	        return new JSONSerializer().include("id","name","creationDate","active","parentDeactive","moveLink","orgHierarchies.id","orgHierarchies.name","orgHierarchies.parentId.id","orgHierarchies.parentId.name","orgHierarchies.description","orgHierarchies.creationDate","orgHierarchies.employeeId.employeeId","orgHierarchies.employeeId.employeeName","orgHierarchies.active","orgHierarchies.parentDeactive","parentId.id","parentId.name","orgHierarchies","employeeId.employeeName","orgHierarchies.moveLink","employeeId.employeeId","employeeId").exclude("*").transform(new DateTransformer(Constants.DATE_PATTERN_4), Date.class).serialize(orgHierarchy);
	    }

}
