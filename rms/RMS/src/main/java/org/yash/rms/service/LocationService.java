package org.yash.rms.service;

 

import org.yash.rms.domain.Location;
 

 

public interface LocationService extends RmsCRUDService<Location>{

	
	 
public Location findById(int id);

public Location findLocationByName(String locationName);
	 
}
