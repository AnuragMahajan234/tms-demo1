package org.yash.rms.dto;


public class ResourceStatusDTO {
	
	private Integer resourceId;
	
	private String resourceType;
	
	private String resourceName;
	
	private Integer profileStaus;
	
	private String profileStausName;
	
	private String allocationStartDate;
	
	private String interviewDate;
	
	private Integer allocationType;
	
	private Integer employeeId;
	
	private String location;
	private String noticePeriod;
	
	private String emailId;
	private String contactNumber;
	private String totalExperience;
	private String skills;
	private String skillReqId;
	
	public ResourceStatusDTO(){}
	
	public ResourceStatusDTO(Integer resourceId, String resourceType, String resourceName, Integer profileStaus,
			String allocationStartDate, Integer allocationType,Integer employeeId,String location,String noticePeriod,String emailId,String contactNumber,String totalExperience,String skills, String profileStausName) {
		super();
		this.resourceId = resourceId;
		this.resourceType = resourceType;
		this.resourceName = resourceName;
		this.profileStaus = profileStaus;
		this.allocationStartDate = allocationStartDate;
		this.allocationType = allocationType;
		this.employeeId = employeeId;
		this.location=location;
		this.noticePeriod=noticePeriod;
		this.emailId = emailId;
		this.contactNumber=contactNumber;
		this.totalExperience=totalExperience;
		this.skills=skills;
		this.profileStausName = profileStausName;
	}

	public Integer getResourceId() {
		return resourceId;
	}

	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	}

	public Integer getProfileStaus() {
		return profileStaus;
	}

	public void setProfileStaus(Integer profileStaus) {
		this.profileStaus = profileStaus;
	}

	public String getAllocationStartDate() {
		return allocationStartDate;
	}

	public void setAllocationStartDate(String allocationStartDate) {
		this.allocationStartDate = allocationStartDate;
	}

	public Integer getAllocationType() {
		return allocationType;
	}

	public void setAllocationType(Integer allocationType) {
		this.allocationType = allocationType;
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

	public Integer getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
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

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public String getTotalExperience() {
		return totalExperience;
	}

	public void setTotalExperience(String totalExperience) {
		this.totalExperience = totalExperience;
	}

	public String getSkills() {
		return skills;
	}

	public void setSkills(String skills) {
		this.skills = skills;
	}

	public String getSkillReqId() {
		return skillReqId;
	}

	public void setSkillReqId(String skillReqId) {
		this.skillReqId = skillReqId;
	}

	public String getProfileStausName() {
		return profileStausName;
	}

	public void setProfileStausName(String profileStausName) {
		this.profileStausName = profileStausName;
	}

	
}
