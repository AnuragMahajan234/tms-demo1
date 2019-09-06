package org.yash.rms.service;

import java.util.List;

import org.yash.rms.domain.Module;
import org.yash.rms.domain.Ownership;

 

public interface OwnershipService extends RmsCRUDService<Ownership>{

	
	 
public Ownership findById(int id);
	 
}
