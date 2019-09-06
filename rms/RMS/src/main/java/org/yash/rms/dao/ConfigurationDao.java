package org.yash.rms.dao;

 
import org.yash.rms.domain.ConfigurationCategory;
import org.yash.rms.domain.Roles;

public interface ConfigurationDao extends RmsCRUDDAO<ConfigurationCategory>{

	
	public ConfigurationCategory findById(int id);
}
