package org.yash.rms.report.dto;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;

import org.yash.rms.util.Constants;

import flexjson.JSONSerializer;
import flexjson.transformer.DateTransformer;

public class ResourceSkillReport {


	private String yashEmpID;

	private String employeeName;

	private String emailId;

	private String designation;

	private String grade;

	private Date dateOfJoining;

	private Date releaseDate;

	private String baseLocation;

	private String currentLocation;

	private String parentBu;

	private String currentBu;

	private String ownership;

	private String currentRM1;

	private String currentRM2;

	private String primaryProject;

	private String currentProjectIndicator;

	private String customerName;

	private String projectBu;

	private Date allocationStartDate;

	private String allocationType;

	private Date transferDate;

	private String visa;

	private String primarySkill;

	private String secondarySkill;

	private String customerId;

	private Integer buId;

	private Integer currentBuID;

	private Integer projectId;

	private Integer currProjectId;

	private Integer projectBuId;

	private Integer locationId;

	private String lastUpdateBy;

	private Timestamp lastupdateTimeStamp;

	private Date allocationEndDate;
	
	private String resourceType;	
	
	private String competency;
	
	private String resumeYN;
	
	private String tefYN;
	
	private String projectManager;
	
	private String totalExp;
	
	private String relExp;
	
	private String yashExp;
	
	private String allocatedSince;

	public String getResourceType() {
		return resourceType;
	}

	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}

	public String getCurrentProjectIndicator() {

		return currentProjectIndicator;
	}

	public void setCurrentProjectIndicator(String currentProjectIndicator) {

		this.currentProjectIndicator = currentProjectIndicator;
	}

	public String getYashEmpID() {

		return yashEmpID;
	}

	public void setYashEmpID(String yashEmpID) {

		this.yashEmpID = yashEmpID;
	}

	public String getEmployeeName() {

		return employeeName;
	}

	public void setEmployeeName(String employeeName) {

		this.employeeName = employeeName;
	}

	public String getEmailId() {

		return emailId;
	}

	public void setEmailId(String emailId) {

		this.emailId = emailId;
	}

	public String getDesignation() {

		return designation;
	}

	public void setDesignation(String designation) {

		this.designation = designation;
	}

	public String getGrade() {

		return grade;
	}

	public void setGrade(String grade) {

		this.grade = grade;
	}

	public Date getDateOfJoining() {

		return dateOfJoining;
	}

	public void setDateOfJoining(Date dateOfJoining) {

		this.dateOfJoining = dateOfJoining;
	}

	public Date getReleaseDate() {

		return releaseDate;
	}

	public void setReleaseDate(Date releaseDate) {

		this.releaseDate = releaseDate;
	}

	public String getBaseLocation() {

		return baseLocation;
	}

	public void setBaseLocation(String baseLocation) {

		this.baseLocation = baseLocation;
	}

	public String getCurrentLocation() {

		return currentLocation;
	}

	public void setCurrentLocation(String currentLocation) {

		this.currentLocation = currentLocation;
	}

	public String getParentBu() {

		return parentBu;
	}

	public void setParentBu(String parentBu) {

		this.parentBu = parentBu;
	}

	public String getCurrentBu() {

		return currentBu;
	}

	public void setCurrentBu(String currentBu) {

		this.currentBu = currentBu;
	}

	public String getOwnership() {

		return ownership;
	}

	public void setOwnership(String ownership) {

		this.ownership = ownership;
	}

	public String getCurrentRM1() {

		return currentRM1;
	}

	public void setCurrentRM1(String currentRM1) {

		this.currentRM1 = currentRM1;
	}

	public String getCurrentRM2() {

		return currentRM2;
	}

	public void setCurrentRM2(String currentRM2) {

		this.currentRM2 = currentRM2;
	}

	public String getPrimaryProject() {

		return primaryProject;
	}

	public void setPrimaryProject(String primaryProject) {

		this.primaryProject = primaryProject;
	}

	public String getCustomerName() {

		return customerName;
	}

	public void setCustomerName(String customerName) {

		this.customerName = customerName;
	}

	public String getProjectBu() {

		return projectBu;
	}

	public void setProjectBu(String projectBu) {

		this.projectBu = projectBu;
	}

	public Date getAllocationStartDate() {

		return allocationStartDate;
	}

	public void setAllocationStartDate(Date allocationStartDate) {

		this.allocationStartDate = allocationStartDate;
	}

	public String getAllocationType() {

		return allocationType;
	}

	public void setAllocationType(String allocationType) {

		this.allocationType = allocationType;
	}

	public Date getTransferDate() {

		return transferDate;
	}

	public void setTransferDate(Date transferDate) {

		this.transferDate = transferDate;
	}

	public String getVisa() {

		return visa;
	}

	public void setVisa(String visa) {

		this.visa = visa;
	}

	public String getPrimarySkill() {

		return primarySkill;
	}

	public void setPrimarySkill(String primarySkill) {

		this.primarySkill = primarySkill;
	}

	public String getSecondarySkill() {

		return secondarySkill;
	}

	public void setSecondarySkill(String secondarySkill) {

		this.secondarySkill = secondarySkill;
	}

	public String getCustomerId() {

		return customerId;
	}

	public void setCustomerId(String customerId) {

		this.customerId = customerId;
	}

	public Integer getBuId() {

		return buId;
	}

	public void setBuId(Integer buId) {

		this.buId = buId;
	}

	public Integer getCurrentBuID() {

		return currentBuID;
	}

	public void setCurrentBuID(Integer currentBuID) {

		this.currentBuID = currentBuID;
	}

	public Integer getProjectId() {

		return projectId;
	}

	public void setProjectId(Integer projectId) {

		this.projectId = projectId;
	}

	public Integer getLocationId() {

		return locationId;
	}

	public void setLocationId(Integer locationId) {

		this.locationId = locationId;
	}

	public String getLastUpdateBy() {

		return lastUpdateBy;
	}

	public void setLastUpdateBy(String lastUpdateBy) {

		this.lastUpdateBy = lastUpdateBy;
	}

	public Timestamp getLastupdateTimeStamp() {

		return lastupdateTimeStamp;
	}

	public void setLastupdateTimeStamp(Timestamp lastupdateTimeStamp) {

		this.lastupdateTimeStamp = lastupdateTimeStamp;

	}

	@Override
	public int hashCode() {

		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((allocationStartDate == null) ? 0 : allocationStartDate
						.hashCode());
		result = prime * result
				+ ((allocationType == null) ? 0 : allocationType.hashCode());
		result = prime * result
				+ ((baseLocation == null) ? 0 : baseLocation.hashCode());
		result = prime * result + ((buId == null) ? 0 : buId.hashCode());
		result = prime * result
				+ ((currProjectId == null) ? 0 : currProjectId.hashCode());
		result = prime * result
				+ ((currentBu == null) ? 0 : currentBu.hashCode());
		result = prime * result
				+ ((currentLocation == null) ? 0 : currentLocation.hashCode());
		result = prime
				* result
				+ ((currentProjectIndicator == null) ? 0
						: currentProjectIndicator.hashCode());
		result = prime * result
				+ ((currentRM1 == null) ? 0 : currentRM1.hashCode());
		result = prime * result
				+ ((currentRM2 == null) ? 0 : currentRM2.hashCode());
		result = prime * result
				+ ((customerName == null) ? 0 : customerName.hashCode());
		result = prime * result
				+ ((dateOfJoining == null) ? 0 : dateOfJoining.hashCode());
		result = prime * result
				+ ((designation == null) ? 0 : designation.hashCode());
		result = prime * result + ((emailId == null) ? 0 : emailId.hashCode());
		result = prime * result
				+ ((employeeName == null) ? 0 : employeeName.hashCode());
		result = prime * result + ((grade == null) ? 0 : grade.hashCode());
		result = prime * result
				+ ((locationId == null) ? 0 : locationId.hashCode());
		result = prime * result
				+ ((ownership == null) ? 0 : ownership.hashCode());
		result = prime * result
				+ ((parentBu == null) ? 0 : parentBu.hashCode());
		result = prime * result
				+ ((primaryProject == null) ? 0 : primaryProject.hashCode());
		result = prime * result
				+ ((projectBu == null) ? 0 : projectBu.hashCode());
		result = prime * result
				+ ((projectId == null) ? 0 : projectId.hashCode());
		result = prime * result
				+ ((releaseDate == null) ? 0 : releaseDate.hashCode());
		result = prime * result
				+ ((yashEmpID == null) ? 0 : yashEmpID.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {

		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ResourceSkillReport other = (ResourceSkillReport) obj;
		if (allocationStartDate == null) {
			if (other.allocationStartDate != null)
				return false;
		} else if (!allocationStartDate.equals(other.allocationStartDate))
			return false;
		if (allocationType == null) {
			if (other.allocationType != null)
				return false;
		} else if (!allocationType.equals(other.allocationType))
			return false;
		if (baseLocation == null) {
			if (other.baseLocation != null)
				return false;
		} else if (!baseLocation.equals(other.baseLocation))
			return false;
		if (buId == null) {
			if (other.buId != null)
				return false;
		} else if (!buId.equals(other.buId))
			return false;
		if (currProjectId == null) {
			if (other.currProjectId != null)
				return false;
		} else if (!currProjectId.equals(other.currProjectId))
			return false;
		if (currentBu == null) {
			if (other.currentBu != null)
				return false;
		} else if (!currentBu.equals(other.currentBu))
			return false;
		if (currentLocation == null) {
			if (other.currentLocation != null)
				return false;
		} else if (!currentLocation.equals(other.currentLocation))
			return false;
		if (currentProjectIndicator == null) {
			if (other.currentProjectIndicator != null)
				return false;
		} else if (!currentProjectIndicator
				.equals(other.currentProjectIndicator))
			return false;
		if (currentRM1 == null) {
			if (other.currentRM1 != null)
				return false;
		} else if (!currentRM1.equals(other.currentRM1))
			return false;
		if (currentRM2 == null) {
			if (other.currentRM2 != null)
				return false;
		} else if (!currentRM2.equals(other.currentRM2))
			return false;
		if (customerName == null) {
			if (other.customerName != null)
				return false;
		} else if (!customerName.equals(other.customerName))
			return false;
		if (dateOfJoining == null) {
			if (other.dateOfJoining != null)
				return false;
		} else if (!dateOfJoining.equals(other.dateOfJoining))
			return false;
		if (designation == null) {
			if (other.designation != null)
				return false;
		} else if (!designation.equals(other.designation))
			return false;
		if (emailId == null) {
			if (other.emailId != null)
				return false;
		} else if (!emailId.equals(other.emailId))
			return false;
		if (employeeName == null) {
			if (other.employeeName != null)
				return false;
		} else if (!employeeName.equals(other.employeeName))
			return false;
		if (grade == null) {
			if (other.grade != null)
				return false;
		} else if (!grade.equals(other.grade))
			return false;
		if (locationId == null) {
			if (other.locationId != null)
				return false;
		} else if (!locationId.equals(other.locationId))
			return false;
		if (ownership == null) {
			if (other.ownership != null)
				return false;
		} else if (!ownership.equals(other.ownership))
			return false;
		if (parentBu == null) {
			if (other.parentBu != null)
				return false;
		} else if (!parentBu.equals(other.parentBu))
			return false;
		if (primaryProject == null) {
			if (other.primaryProject != null)
				return false;
		} else if (!primaryProject.equals(other.primaryProject))
			return false;
		if (projectBu == null) {
			if (other.projectBu != null)
				return false;
		} else if (!projectBu.equals(other.projectBu))
			return false;
		if (projectId == null) {
			if (other.projectId != null)
				return false;
		} else if (!projectId.equals(other.projectId))
			return false;
		if (releaseDate == null) {
			if (other.releaseDate != null)
				return false;
		} else if (!releaseDate.equals(other.releaseDate))
			return false;
		if (yashEmpID == null) {
			if (other.yashEmpID != null)
				return false;
		} else if (!yashEmpID.equals(other.yashEmpID))
			return false;
		return true;
	}

	public Integer getCurrProjectId() {

		return currProjectId;
	}

	public void setCurrProjectId(Integer currProjectId) {

		this.currProjectId = currProjectId;
	}

	public Integer getProjectBuId() {

		return projectBuId;
	}

	public void setProjectBuId(Integer projectBuId) {

		this.projectBuId = projectBuId;
	}

	public Date getAllocationEndDate() {

		return allocationEndDate;
	}

	public void setAllocationEndDate(Date allocationEndDate) {

		this.allocationEndDate = allocationEndDate;
	}

	public String getCompetency() {
		return competency;
	}
	public void setCompetency(String competency) {
		this.competency = competency;
	}	
	
	public String getResumeYN() {
		return resumeYN;
	}

	public void setResumeYN(String resumeYN) {
		this.resumeYN = resumeYN;
	}

	public String getTefYN() {
		return tefYN;
	}

	public void setTefYN(String tefYN) {
		this.tefYN = tefYN;
	}
	
	public String getProjectManager() {
		return projectManager;
	}

	public void setProjectManager(String projectManager) {
		this.projectManager = projectManager;
	}

	public String getTotalExp() {
		return totalExp;
	}

	public void setTotalExp(String totalExp) {
		this.totalExp = totalExp;
	}

	public String getRelExp() {
		return relExp;
	}

	public void setRelExp(String relExp) {
		this.relExp = relExp;
	}

	public String getYashExp() {
		return yashExp;
	}

	public void setYashExp(String yashExp) {
		this.yashExp = yashExp;
	}
	
	public String getAllocatedSince() {
		return allocatedSince;
	}

	public void setAllocatedSince(String allocatedSince) {
		this.allocatedSince = allocatedSince;
	}

	public static String toJsonArray(Collection<ResourceSkillReport> collection) {

		return new JSONSerializer()
				.include(
						"yashEmpID", "employeeName", "competency", "primarySkill", "secondarySkill",
						"grade","currentLocation","primaryProject","allocationType",
						"emailId", "designation",  "parentBu", "currentBu", "projectBu"
						
						/*"yashEmpID", "employeeName", "emailId", "designation",
						"grade", "dateOfJoining", "releaseDate",
						"baseLocation", "currentLocation", "parentBu",
						"currentBu", "ownership", "currentRM1", "currentRM2",
						"primaryProject", "currentProjectIndicator",
						"customerName", "projectBu", "allocationStartDate",
						"allocationType","resourceType", "transferDate", "visa", "competency",
						"primarySkill", "secondarySkill", "customerId",
						"lastUpdateBy", "lastupdateTimeStamp",
						"allocationEndDate","resumeYN","tefYN", "projectManager"*/)
				.exclude("*")
				.transform(new DateTransformer(Constants.DATE_PATTERN_4),
						Date.class).serialize(collection);
	}

	/*public static String toJson(ResourceSkillReport resourceSkillReport) {

		return new JSONSerializer()
				.include("yashEmpID", "employeeName", "emailId", "designation",
						"grade", "dateOfJoining", "releaseDate",
						"baseLocation", "currentLocation", "parentBu",
						"currentBu", "ownership", "currentRM1", "currentRM2",
						"primaryProject", "currentProjectIndicator",
						"customerName", "projectBu", "allocationStartDate",
						"allocationType","resourceType", "transferDate", "visa", "competency",
						"primarySkill", "secondarySkill", "customerId",
						"lastUpdateBy", "lastupdateTimeStamp",
						"allocationEndDate","resumeYN","tefYN","projectManager", "totalExp", "relExp", "yashExp", "allocatedSince")
				.exclude("*")
				.transform(new DateTransformer(Constants.DATE_PATTERN_4),
						Date.class).serialize(resourceSkillReport);
	}*/
	public static String toJson(ResourceSkillReport resourceSkillReport) {

		return new JSONSerializer()
				.include("yashEmpID", "employeeName", "competency", "primarySkill", "secondarySkill",
						"grade","currentLocation","primaryProject","allocationType",
						"emailId", "designation",  "parentBu", "currentBu", "projectBu")
				.exclude("*")
				.transform(new DateTransformer(Constants.DATE_PATTERN_4),
						Date.class).serialize(resourceSkillReport);
	}


}
