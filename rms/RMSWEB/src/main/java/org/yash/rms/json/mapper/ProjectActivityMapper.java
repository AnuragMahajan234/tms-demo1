/**
 * 
 */
package org.yash.rms.json.mapper;

import java.util.Collection;
import java.util.Date;

import org.springframework.stereotype.Component;
import org.yash.rms.domain.CustomActivity;

import org.yash.rms.util.Constants;

import flexjson.JSONSerializer;
import flexjson.transformer.DateTransformer;

/**
 * @author apurva.sinha
 * 
 */
@Component("ProjectActivityMapper")
public class ProjectActivityMapper  {
	public static String toJsonArray(Collection<CustomActivity> collection) {
		try{
			return new JSONSerializer()
				.include( "id","activityName","mandatory","active","activityId")
				.transform(new DateTransformer(Constants.DATE_PATTERN),
						Date.class).exclude("*").serialize(collection);
		}catch(Exception exception){
			exception.printStackTrace();
		}
		return null;
		}
	
	 

}
