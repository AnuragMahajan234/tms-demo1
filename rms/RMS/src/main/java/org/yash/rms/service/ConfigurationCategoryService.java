package org.yash.rms.service;

 

import org.yash.rms.domain.ConfigurationCategory;
import org.yash.rms.domain.Designation;
import org.yash.rms.domain.Grade;
import org.yash.rms.domain.Location;
import org.yash.rms.domain.Roles;
 

 

public interface ConfigurationCategoryService extends RmsCRUDService<ConfigurationCategory>{

	
	 
public ConfigurationCategory findById(int id);
	 
}
