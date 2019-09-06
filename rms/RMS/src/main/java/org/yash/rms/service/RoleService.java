package org.yash.rms.service;

 

import org.yash.rms.domain.Designation;
import org.yash.rms.domain.Grade;
import org.yash.rms.domain.Location;
import org.yash.rms.domain.Roles;
 

 

public interface RoleService extends RmsCRUDService<Roles>{

	
	 
public Roles findById(int id);

public Roles findByRole(String role);
	 
}
