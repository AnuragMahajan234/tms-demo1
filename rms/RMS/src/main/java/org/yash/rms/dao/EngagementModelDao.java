package org.yash.rms.dao;

import java.util.List;

import org.yash.rms.domain.EngagementModel;
import org.yash.rms.domain.SEPGPhases;

public interface EngagementModelDao extends RmsCRUDDAO<EngagementModel> {
	
	public List<EngagementModel> findLeftSepgEngagement(List<Integer> ids);
	
	public List<EngagementModel> findSelectSepgEngagement(List<Integer> ids);
	
	public List<SEPGPhases> getSEPGIdsWithEngagementModel(); 

	public EngagementModel findById(Integer id) throws Exception;
	
	public EngagementModel findEngagementModelByProjectId(Integer id) throws Exception ;
}
