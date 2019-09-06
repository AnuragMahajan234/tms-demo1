package org.yash.rms.dto;

import java.util.List;

import org.yash.rms.domain.AllocationType;
import org.yash.rms.domain.RequestRequisitionResource;
import org.yash.rms.domain.RequestRequisitionResourceStatus;
import org.yash.rms.domain.RequestRequisitionSkill;
import org.yash.rms.domain.Resource;

public class RequestRequisitionResourceFormDTO {
	
	List<Resource> activeUserList;
	
	List<Resource> mailingList;
	
	List<RequestRequisitionResourceStatus> requestRequisitionResourceStatusList;
	
	List<RequestRequisitionSkill> requestRequisitionResources;
	
	RequestRequisitionResourceDTO requestRequisitionResourceDTO;
	
	List<RequestRequisitionResource> requisitionResourceList;
	
	List<AllocationType> allocationTypeList;
	
	RequestRequisitionDTO requestRequisitionDTO;
	
	List<String> emailsToNotify;
	
	List<String> emailsToRequestTo;
	
	List<String> pdlGroup;

	public List<Resource> getActiveUserList() {
		return activeUserList;
	}

	public void setActiveUserList(List<Resource> activeUserList) {
		this.activeUserList = activeUserList;
	}

	public List<Resource> getMailingList() {
		return mailingList;
	}

	public void setMailingList(List<Resource> mailingList) {
		this.mailingList = mailingList;
	}

	public List<RequestRequisitionResourceStatus> getRequestRequisitionResourceStatusList() {
		return requestRequisitionResourceStatusList;
	}

	public void setRequestRequisitionResourceStatusList(List<RequestRequisitionResourceStatus> requestRequisitionResourceStatus) {
		this.requestRequisitionResourceStatusList = requestRequisitionResourceStatus;
	}

	public List<RequestRequisitionSkill> getRequestRequisitionResources() {
		return requestRequisitionResources;
	}

	public void setRequestRequisitionResources(List<RequestRequisitionSkill> requestRequisitionResources) {
		this.requestRequisitionResources = requestRequisitionResources;
	}

	public RequestRequisitionResourceDTO getRequestRequisitionResourceDTO() {
		return requestRequisitionResourceDTO;
	}

	public void setRequestRequisitionResourceDTO(RequestRequisitionResourceDTO requestRequisitionResourceDTO) {
		this.requestRequisitionResourceDTO = requestRequisitionResourceDTO;
	}
	
	public List<RequestRequisitionResource> getRequisitionResourceList() {
		return requisitionResourceList;
	}
	
	public void setRequisitionResourceList(List<RequestRequisitionResource> requisitionResourceList) {
		this.requisitionResourceList = requisitionResourceList;
	}

	public List<AllocationType> getAllocationTypeList() {
		return allocationTypeList;
	}

	public void setAllocationTypeList(List<AllocationType> allocationTypeList) {
		this.allocationTypeList = allocationTypeList;
	}

	public RequestRequisitionDTO getRequestRequisitionDTO() {
		return requestRequisitionDTO;
	}

	public void setRequestRequisitionDTO(RequestRequisitionDTO requestRequisitionDTO) {
		this.requestRequisitionDTO = requestRequisitionDTO;
	}

	public List<String> getEmailsToNotify() {
		return emailsToNotify;
	}

	public void setEmailsToNotify(List<String> emailsToNotify) {
		this.emailsToNotify = emailsToNotify;
	}

	public List<String> getEmailsToRequestTo() {
		return emailsToRequestTo;
	}

	public void setEmailsToRequestTo(List<String> emailsToRequestTo) {
		this.emailsToRequestTo = emailsToRequestTo;
	}

	public List<String> getPdlGroup() {
		return pdlGroup;
	}

	public void setPdlGroup(List<String> pdlGroup) {
		this.pdlGroup = pdlGroup;
	}
	
}
