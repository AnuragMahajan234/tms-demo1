package org.yash.rms.dto;


import org.springframework.web.multipart.MultipartFile;

public class ResourceFileDTO {
	
	private MultipartFile file;
	
	private String resourceIds;
	
	private String resourceType;
	
	private  String externalResourceName;
	
	private  String noticePeriod;
	private  String location;
	private  String emailId;
	private  String totalExperience;
	private  String skills;
	private  String contactNumber;
	private MultipartFile tacFile;
	

	public MultipartFile getTacFile() {
		return tacFile;
	}

	public void setTacFile(MultipartFile tacFile) {
		this.tacFile = tacFile;
	}

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

	public String getResourceIds() {
		return resourceIds;
	}

	public void setResourceIds(String resourceIds) {
		this.resourceIds = resourceIds;
	}

	public String getResourceType() {
		return resourceType;
	}

	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}

	public String getExternalResourceName() {
		return externalResourceName;
	}

	public String getNoticePeriod() {
		return noticePeriod;
	}
	public void setNoticePeriod(String noticePeriod) {
		this.noticePeriod = noticePeriod;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public void setExternalResourceName(String externalResourceName) {
		this.externalResourceName = externalResourceName;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
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

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

}
