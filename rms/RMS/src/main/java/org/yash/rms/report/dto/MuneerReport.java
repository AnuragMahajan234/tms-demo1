package org.yash.rms.report.dto;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

import org.yash.rms.util.Constants;

import flexjson.JSONSerializer;
import flexjson.transformer.DateTransformer;

public class MuneerReport {
	private String yashEmpID;
	private String employeeName;
	private String parentBu;
	private String currentBu;

	private String ownership;
	private String bg;
	private String bu;

	// private String practice;

	private String competency;
	private String baseLocation;
	private String currentLocation;

	private String allocationType;
	private Date allocationStartDate;
	private Date allocationEndDate;
	private int allocatedSince;

	private String billable;

	private String customerName;
	private String teamName;
	private String primaryProject;

	// public String available;

	private String remarks;
	private String designation;
	private String grade;
	private String primarySkill;
	private String secondarySkill;

	// //////////// Unwanted Coloumns

	private String currentBgName;
	private String currentBuName;

	private String emailId;

	private Date dateOfJoining;
	private Date releaseDate;

	private String currentRM1;
	private String currentRM2;

	private String currentProjectIndicator;

	private String projectBu;

	private Date transferDate;
	private String visa;

	private String customerId;
	private Integer buId;
	private Integer currentBuID;
	private Integer projectId;
	private Integer currProjectId;
	private Integer projectBuId;
	private Integer locationId;
	private String lastUpdateBy;
	private Timestamp lastupdateTimeStamp;
	
	private String selectedHeaders;

	private Map<String, Map<String, Integer>> clientData;
	
	private BigDecimal percentageAllocation;

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

	public String getCurrentProjectIndicator() {
		return currentProjectIndicator;
	}

	public void setCurrentProjectIndicator(String currentProjectIndicator) {
		this.currentProjectIndicator = currentProjectIndicator;
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

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public String getBg() {
		return bg;
	}

	public void setBg(String bg) {
		this.bg = bg;
	}

	public String getBu() {
		return bu;
	}

	public void setBu(String bu) {
		this.bu = bu;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getCompetency() {
		return competency;
	}

	public void setCompetency(String competency) {
		this.competency = competency;
	}

	public int getAllocatedSince() {
		return allocatedSince;
	}

	public void setAllocatedSince(int obj) {
		this.allocatedSince = obj;
	}

	public Map<String, Map<String, Integer>> getClientData() {
		return clientData;
	}

	public void setClientData(Map<String, Map<String, Integer>> clientData) {
		this.clientData = clientData;
	}

	public BigDecimal getPercentageAllocation() {
		return percentageAllocation;
	}

	public void setPercentageAllocation(BigDecimal percentAlloc) {
		this.percentageAllocation = percentAlloc;
	}

	public String getCurrentBgName() {
		return currentBgName;
	}

	public void setCurrentBgName(String currentBgName) {
		this.currentBgName = currentBgName;
	}

	public String getCurrentBuName() {
		return currentBuName;
	}

	public void setCurrentBuName(String currentBuName) {
		this.currentBuName = currentBuName;
	}

	public String getBillable() {
		return billable;
	}

	public void setBillable(String billable) {
		this.billable = billable;
	}

	public Date getAllocationEndDate() {

		return allocationEndDate;
	}

	public void setAllocationEndDate(Date allocationEndDate) {

		this.allocationEndDate = allocationEndDate;
	}

	public static String toJsonArray(Collection<MuneerReport> collection) {
		return new JSONSerializer()
				.include("yashEmpID", "employeeName", "emailId", "designation", "grade", "dateOfJoining", "releaseDate", "baseLocation", "currentLocation", "parentBu", "currentBu", "ownership",
						"currentRM1", "currentRM2", "primaryProject", "currentProjectIndicator", "customerName", "projectBu", "allocationStartDate", "allocationType", "transferDate", "visa",
						"primarySkill", "secondarySkill", "customerId", "lastUpdateBy", "lastupdateTimeStamp", "teamName", "bg", "bu", "remarks", "competency", "allocatedSince", "currentBgName",
						"currentBuName", "billable", "allocationEndDate", "percentageAllocation").exclude("*").transform(new DateTransformer(Constants.DATE_PATTERN_4), Date.class).serialize(collection);
	}

	public static String toJson(MuneerReport muneerReport) {
		return new JSONSerializer()
				.include("yashEmpID", "employeeName", "emailId", "designation", "grade", "dateOfJoining", "releaseDate", "baseLocation", "currentLocation", "parentBu", "currentBu", "ownership",
						"currentRM1", "currentRM2", "primaryProject", "currentProjectIndicator", "customerName", "projectBu", "allocationStartDate", "allocationType", "transferDate", "visa",
						"primarySkill", "secondarySkill", "customerId", "lastUpdateBy", "lastupdateTimeStamp", "teamName", "bg", "bu", "remarks", "competency", "allocatedSince", "currentBgName",
						"currentBuName", "billable", "allocationEndDate", "percentageAllocation").exclude("*").transform(new DateTransformer(Constants.DATE_PATTERN_4), Date.class).serialize(muneerReport);
	}
	
	  @Override
	  public boolean equals(Object obj) {
	    if (this == obj)
	      return true;
	    if (obj == null)
	      return false;
	    if (getClass() != obj.getClass())
	      return false;
	    MuneerReport other = (MuneerReport) obj;
	    if (yashEmpID == null) {
	      if (other.yashEmpID != null)
	        return false;
	    } else if (!yashEmpID.equals(other.yashEmpID))
	      return false;
	    return true;
	  }
		
	public int hashCode()
	{
		return yashEmpID.hashCode();
	}
	public String getSelectedHeaders() {
		return selectedHeaders;
	}

	public void setSelectedHeaders(String selectedHeaders) {
		this.selectedHeaders = selectedHeaders;
	}	
}
