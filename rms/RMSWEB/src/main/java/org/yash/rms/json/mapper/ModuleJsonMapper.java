package org.yash.rms.json.mapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.yash.rms.domain.Module;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

public class ModuleJsonMapper {
    
    public String toJson() {
        return new JSONSerializer().exclude("*.class").serialize(this);
    }
    
    public static Module fromJsonToModule(String json) {
        return new JSONDeserializer<Module>().use(null, Module.class).deserialize(json);
    }
    
    public static String toJsonArray(Collection<Module> collection) {
        return new JSONSerializer().exclude("*.class").serialize(collection);
    }
    
    public static Collection<Module> fromJsonArrayToModule(String json) {
        return new JSONDeserializer<List<Module>>().use(null, ArrayList.class).use("values", Module.class).deserialize(json);
    }
    
}