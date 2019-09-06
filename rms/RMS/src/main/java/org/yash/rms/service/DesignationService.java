package org.yash.rms.service;

 

import org.yash.rms.domain.Designation;
import org.yash.rms.domain.Grade;
import org.yash.rms.domain.Location;
 

 

public interface DesignationService extends RmsCRUDService<Designation>{

	
	 
public Designation findById(int id);
public Designation findByNameIgnoreSpace(String name);
	 
}
