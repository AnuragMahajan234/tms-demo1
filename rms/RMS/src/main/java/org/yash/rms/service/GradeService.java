package org.yash.rms.service;

 

import org.yash.rms.domain.Grade;
import org.yash.rms.domain.Location;
 

 

public interface GradeService extends RmsCRUDService<Grade>{

	
	 
public Grade findById(int id);
public Grade findByName(String name);
	 
}
