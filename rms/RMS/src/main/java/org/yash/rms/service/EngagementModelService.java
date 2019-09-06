package org.yash.rms.service;

import java.util.List;

import org.yash.rms.domain.EngagementModel;
import org.yash.rms.domain.SEPGPhases;

public interface EngagementModelService extends RmsCRUDService<EngagementModel> {
	
	public List<EngagementModel> findLeftSepgEngagement(List<Integer> ids);
	
	public List<EngagementModel> findSelectSepgEngagement(List<Integer> ids);
	
	public List<SEPGPhases> getSEPGIdsWithEngagementModel(); 
	
	public EngagementModel findById(Integer id) throws Exception ;
	
	public EngagementModel findEngagementModelByProjectId(Integer id) throws Exception;

}
