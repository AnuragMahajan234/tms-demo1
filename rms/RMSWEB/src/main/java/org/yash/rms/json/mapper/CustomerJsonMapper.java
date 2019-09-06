package org.yash.rms.json.mapper;

import java.util.Date;

import org.springframework.stereotype.Component;
import org.yash.rms.dto.CustomerDTO;
import org.yash.rms.util.Constants;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import flexjson.transformer.DateTransformer;

@Component
public class CustomerJsonMapper {
	
	public String toJson(CustomerDTO customer) {
        return new JSONSerializer().include("id","accountManager","accountManagerContactNumber","code","createdId","creationTimestamp","customerAddress","customerName","customerEmail","geography","lastUpdatedId","lastupdatedTimestamp","custGroups", "custGroups.groupId","custGroups.groupName","custGroups.groupEmail","custGroups.active").exclude("*").serialize(customer);
    }

	public CustomerDTO fromJsonCustomerDTO(String json) {
        return new JSONDeserializer<CustomerDTO>().use(null, CustomerDTO.class).deserialize(json);
    }

}
