package org.yash.rms.json.mapper;

import java.util.Collection;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.yash.rms.domain.Activity;
import org.yash.rms.domain.DefaultProject;
import org.yash.rms.domain.OrgHierarchy;
import org.yash.rms.util.Constants;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import flexjson.transformer.DateTransformer;
/**
 * 
 * @author purva.bhate
 *
 */
@Component
public class DefaultProjectJsonMapper {

	
	private static final Logger logger = LoggerFactory
			.getLogger(DefaultProjectJsonMapper.class);
	// added by purva For US3119 to save a default project
	  public  DefaultProject  fromJsonToDefaultProject(String json) {
	        return new JSONDeserializer<DefaultProject>().use(null, DefaultProject.class).use(Date.class, new DateTransformer(Constants.DATE_PATTERN)).deserialize(json);
	    }

	 
	// added by purva For US3119 to save a default project
	  public String toJsonArray(Collection<DefaultProject> collection) {
			try {
				return new JSONSerializer()
						.include("id", "orgHierarchy.id")
						.exclude("*")
						.serialize(collection);
			} catch (Exception ex) {
				ex.printStackTrace();
				logger.error("EXCEPTION OCCURED IN MAPPING ACTIVITY LIST TO JSON ARRAY "
						+ ex.getMessage());
				return "";
			}
		}
	
	
	
	
}
