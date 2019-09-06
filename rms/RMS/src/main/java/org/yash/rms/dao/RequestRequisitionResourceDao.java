package org.yash.rms.dao;

import java.util.Date;
import java.util.List;

import org.yash.rms.domain.AllocationType;
import org.yash.rms.domain.RequestRequisitionResource;
import org.yash.rms.domain.RequestRequisitionResourceStatus;
import org.yash.rms.domain.RequestRequisitionSkill;


public interface RequestRequisitionResourceDao {
	
	public void saveRequestRequisitionResource(List<RequestRequisitionResource> requisitionResources, RequestRequisitionSkill skill, List<RequestRequisitionSkill> requestRequisitionResources)throws Exception;
	
	public List<RequestRequisitionResource> getRequestRequisitionResourceList(Integer id);
	
	public List<RequestRequisitionResource> updateResourceRequestWithStatus(List<RequestRequisitionResource > resources);
	
	public List<RequestRequisitionResource> getDataForAddDeleteResourcebySkillRequestID(Integer skillRequestId);

	public RequestRequisitionResourceStatus getRequestRequisitionResourceStatusById(Integer profileStaus);

	public AllocationType getAllocationTypeById(Integer allocationType);
	
	public List<RequestRequisitionResource> getAllSelectedRequestRequisitionResourceList();
	
	public List<RequestRequisitionResource> getSelectedResourceByRequestRequisitionSkillId(
			Integer requestRequisitionSkillId);
	
	public List<Object[]> getRequestRequisitionResources(Integer userId, String userRole,Integer skillResourceRequisitionId, List<Integer> resourceStatusList);

	public List<Object[]> getLatestCommentOnResource(Integer skillRequestId);
	
	public RequestRequisitionResource getResourceBySkillIdAndResourceId(Integer resourceId, Integer skillId);
	public List<Object[]> getResourceRequisitionProfileStatusCount(Integer userId, String role, Date startDate, Date endDate,List<Integer> customerList);
	
	public RequestRequisitionResource getRequestResourceById(Integer requestResourceId) throws Exception;
	public RequestRequisitionResourceStatus getStatusByStatusName(String status);
}
