package org.yash.rms.dto;

import java.util.List;

import org.yash.rms.domain.Customer;
import org.yash.rms.domain.PDLEmailGroup;
import org.yash.rms.domain.RequestRequisitionSkill;
import org.yash.rms.domain.Resource;
import org.yash.rms.dto.CustomerDTO;

public class RequestRequisitionDashboardDTO {

	List<RequestRequisitionSkillDTO> requestRequisitionDashboardList;
	
	List<CustomerDTO> customerList; 
	
	List<Resource> activeUserList;
	
	List<PDLEmailGroup> pdlGroup;

	List<ResourceCommentDTO> resourcecommentList; 
	
	public List<RequestRequisitionSkillDTO> getRequestRequisitionDashboardList() {
		return requestRequisitionDashboardList;
	}

	public void setRequestRequisitionDashboardList(List<RequestRequisitionSkillDTO> requestRequisitionDashboardList) {
		this.requestRequisitionDashboardList = requestRequisitionDashboardList;
	}

	public List<CustomerDTO> getCustomerList() {
		return customerList;
	}

	public void setCustomerList(List<CustomerDTO> customerList) {
		this.customerList = customerList;
	}

	public List<Resource> getActiveUserList() {
		return activeUserList;
	}

	public void setActiveUserList(List<Resource> activeUserList) {
		this.activeUserList = activeUserList;
	}

	public List<PDLEmailGroup> getPdlGroup() {
		return pdlGroup;
	}

	public void setPdlGroup(List<PDLEmailGroup> pdlGroup) {
		this.pdlGroup = pdlGroup;
	}
	
	public List<ResourceCommentDTO> getResourceCommentListList() {
		return resourcecommentList;
	}
}
