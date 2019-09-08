package org.yash.rms.service;

import org.yash.rms.domain.Status;

public interface StatusService {
	
	void createStatus(Status status);
	
	void approveStatus(Status status);
	
	void declinedStatus(Status status);
	
	

}
