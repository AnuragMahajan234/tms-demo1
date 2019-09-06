package org.yash.rms.dao;

import java.util.List;

import org.yash.rms.domain.Customer;
import org.yash.rms.domain.RequestRequisition;
import org.yash.rms.domain.RequestRequisitionSkill;


public interface RequestRequisitionDao extends RmsCRUDDAO<RequestRequisition>{

	public RequestRequisition findRequestRequisitionById(Integer requestRequisitionId);
	
	public void save(RequestRequisition requestRequisition);
	
	public List<Customer> getRequestRequisitionsCustomer(Integer userId, String userRole, List<Integer> projectList);
	
	public Object getBUHApprovalById(Integer requestID);

	public Object getBGHApprovalById(Integer id);
	
	public List<RequestRequisitionSkill> getRRFDetailsForClosingProject();
	
	public List<RequestRequisitionSkill> findRRFByProjectId(Integer Id);
	
}
