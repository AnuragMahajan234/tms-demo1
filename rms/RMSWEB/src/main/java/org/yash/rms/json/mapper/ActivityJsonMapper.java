package org.yash.rms.json.mapper;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.yash.rms.domain.Activity;

import flexjson.JSONSerializer;

@Component("ActivityJsonMapper")
public class ActivityJsonMapper extends JsonObjectMapper<Activity>{

	private static final Logger logger = LoggerFactory.getLogger(ActivityJsonMapper.class);


	public String toJsonArray(Collection<Activity> collection) {
		try {
			return new JSONSerializer()
					.include("id", "activityName", "activityType","productive","mandatory")
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
