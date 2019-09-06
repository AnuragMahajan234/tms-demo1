package org.yash.rms.service;

import java.util.List;

import org.yash.rms.domain.ClientType;
import org.yash.rms.dto.ClientTypeDTO;

public interface ClientTypeService {
	
	public List<ClientType> getAllClientTypes();
	
}
