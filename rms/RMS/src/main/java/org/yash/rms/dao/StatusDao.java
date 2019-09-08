package org.yash.rms.dao;

import org.yash.rms.domain.Status;

public interface StatusDao {

	Status createStatus(Status status);
	
	Status updateStatus(Status status);
}
