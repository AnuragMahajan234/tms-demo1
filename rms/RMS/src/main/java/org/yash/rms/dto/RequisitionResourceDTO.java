package org.yash.rms.dto;

import java.util.ArrayList;
import java.util.List;

import org.yash.rms.domain.Resource;

public class RequisitionResourceDTO {

	private String resourceId;

	private String skillReqId;

	private String skillReqName;

	private String internalResId;

	private String resourceType;

	private String resourceName;

	private String allocationDate;

	private String skill;

	private String designation;

	private String status;

	private String closed;

	private String interviewDate;

	private String location;
	private String noticePeriod;
	private String emailId;
	private String totalExperince;
	private String contactNum;
	private String resourceSubmittedDate;
	private String joiningDate;
	private String positionCloseDate;
	private String resourceRejectedDate;
	private String resourceSelectedDate;

	private String resourcePOCsubmittedDate;
	private String resourceAMsubmittedDate;

	private List<Resource> rmgPOCList;
	private List<Resource> tecTeamList;

	List<ResourceCommentDTO> allResourceCommentByResourceId = new ArrayList<ResourceCommentDTO>();

	public List<Resource> getRmgPOCList() {
		return rmgPOCList;
	}

	public void setRmgPOCList(List<Resource> rmgPOCList) {
		this.rmgPOCList = rmgPOCList;
	}


	public List<Resource> getTecTeamList() {
		return tecTeamList;
	}

	public void setTecTeamList(List<Resource> tecTeamList) {
		this.tecTeamList = tecTeamList;
	}

	public String getPositionCloseDate() {
		return positionCloseDate;
	}

	public void setPositionCloseDate(String positionCloseDate) {
		this.positionCloseDate = positionCloseDate;
	}

	public String getResourcePOCsubmittedDate() {
		return resourcePOCsubmittedDate;
	}

	public void setResourcePOCsubmittedDate(String resourcePOCsubmittedDate) {
		this.resourcePOCsubmittedDate = resourcePOCsubmittedDate;
	}

	public String getResourceAMsubmittedDate() {
		return resourceAMsubmittedDate;
	}

	public void setResourceAMsubmittedDate(String resourceAMsubmittedDate) {
		this.resourceAMsubmittedDate = resourceAMsubmittedDate;
	}

	public String getResourceRejectedDate() {
		return resourceRejectedDate;
	}

	public void setResourceRejectedDate(String resourceRejectedDate) {
		this.resourceRejectedDate = resourceRejectedDate;
	}

	public String getResourceSelectedDate() {
		return resourceSelectedDate;
	}

	public void setResourceSelectedDate(String resourceSelectedDate) {
		this.resourceSelectedDate = resourceSelectedDate;
	}

	public String getJoiningDate() {
		return joiningDate;
	}

	public void setJoiningDate(String joiningDate) {
		this.joiningDate = joiningDate;
	}

	public String getResourceId() {
		return resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

	public String getResourceType() {
		return resourceType;
	}

	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}

	public String getResourceName() {
		return resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	public String getAllocationDate() {
		return allocationDate;
	}

	public void setAllocationDate(String allocationDate) {
		this.allocationDate = allocationDate;
	}

	public String getSkill() {
		return skill;
	}

	public void setSkill(String skill) {
		this.skill = skill;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getClosed() {
		return closed;
	}

	public void setClosed(String closed) {
		this.closed = closed;
	}

	public String getInterviewDate() {
		return interviewDate;
	}

	public void setInterviewDate(String interviewDate) {
		this.interviewDate = interviewDate;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getNoticePeriod() {
		return noticePeriod;
	}

	public void setNoticePeriod(String noticePeriod) {
		this.noticePeriod = noticePeriod;
	}

	public String getResourceSubmittedDate() {
		return resourceSubmittedDate;
	}

	public void setResourceSubmittedDate(String resourceSubmittedDate) {
		this.resourceSubmittedDate = resourceSubmittedDate;
	}

	public String getSkillReqId() {
		return skillReqId;
	}

	public void setSkillReqId(String skillReqId) {
		this.skillReqId = skillReqId;
	}

	public String getInternalResId() {
		return internalResId;
	}

	public void setInternalResId(String internalResId) {
		this.internalResId = internalResId;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getTotalExperince() {
		return totalExperince;
	}

	public void setTotalExperince(String totalExperince) {
		this.totalExperince = totalExperince;
	}

	public String getContactNum() {
		return contactNum;
	}

	public void setContactNum(String contactNum) {
		this.contactNum = contactNum;
	}

	public String getSkillReqName() {
		return skillReqName;
	}

	public void setSkillReqName(String skillReqName) {
		this.skillReqName = skillReqName;
	}

	public List<ResourceCommentDTO> getAllResourceCommentByResourceId() {
		return allResourceCommentByResourceId;
	}

	public void setAllResourceCommentByResourceId(List<ResourceCommentDTO> allResourceCommentByResourceId) {
		this.allResourceCommentByResourceId = allResourceCommentByResourceId;
	}

	
}
