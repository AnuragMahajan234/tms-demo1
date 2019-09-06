package org.yash.rms.service;

import java.util.List;

public interface PDLAndResourceMappingService {

	public Integer getResourceIdByPDLId(Integer pdl_id);
	
	public List<Integer> getPdlIdsByResourceId(Integer resource_id);
	
	public void deleteAll();
	
}
