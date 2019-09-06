package org.yash.rms.service;

import java.util.List;

import org.yash.rms.domain.RequestRequisitionResource;
import org.yash.rms.dto.RequisitionResourceDTO;
import org.yash.rms.dto.ResourceCommentDTO;
import org.yash.rms.dto.ResourceStatusDTO;

public interface RequestRequisitionResourceService {
	
	public List<RequisitionResourceDTO> getRequestRequisitionResources(Integer userId, String userRole,Integer skillResourcerRequisitionId, String resourceStatusIds);
	
	public List<ResourceStatusDTO> getRequestRequisitionResources(Integer userId, String userRole,Integer skillResourcerRequisitionId);
	
	public List<ResourceCommentDTO> getLatestCommentOnResource(Integer skillRequestId);
	
	public RequestRequisitionResource getRequestRequisitionResourceById(Integer requestResourceId) throws Exception;

	public void mapResourceToNewRRF(int oldSkillRequestId, int newskillRequestId, String resourceIdToMap) throws NumberFormatException, Exception;

}
