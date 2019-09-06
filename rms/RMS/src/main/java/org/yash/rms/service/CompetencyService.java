package org.yash.rms.service;

import org.yash.rms.domain.Competency;


 

public interface CompetencyService extends RmsCRUDService<Competency>{

	
	 
public Competency findById(int id);
	 
}
