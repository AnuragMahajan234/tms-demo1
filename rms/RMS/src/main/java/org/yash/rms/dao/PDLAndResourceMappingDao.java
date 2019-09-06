package org.yash.rms.dao;

import java.util.List;

import org.yash.rms.domain.PDLAndResourceMapping;

/**
 * 
 * @author samiksha.sant
 *
 */

public interface PDLAndResourceMappingDao {

	public Integer getResourceIdByPDLId(Integer pdl_id);
	
	public List<Integer> getPDLIdbyResourceId(Integer resourceId);
	
	public void addResourcesInPDL(List<PDLAndResourceMapping> pdlResourceList);
	
	public void deleteAll();
	
}
