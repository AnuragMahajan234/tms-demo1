package org.yash.rms.json.mapper;

import org.yash.rms.domain.BillingScale;
import org.yash.rms.domain.ProjectMethodology;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ProjectMethodologyJsonMapper {
    
    public String toJson() {
        return new JSONSerializer().exclude("*.class").serialize(this);
    }
    
    public static ProjectMethodology fromJsonToProjectMethodology(String json) {
        return new JSONDeserializer<ProjectMethodology>().use(null, ProjectMethodology.class).deserialize(json);
    }
    
    public static String toJsonArray(Collection<BillingScale> collection) {
        return new JSONSerializer().exclude("*.class").serialize(collection);
    }
    
    public static Collection<ProjectMethodology> fromJsonArrayToProjectMethodology(String json) {
        return new JSONDeserializer<List<ProjectMethodology>>().use(null, ArrayList.class).use("values", ProjectMethodology.class).deserialize(json);
    }
    
}