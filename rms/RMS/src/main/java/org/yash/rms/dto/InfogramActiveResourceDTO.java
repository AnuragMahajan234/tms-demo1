package org.yash.rms.dto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.yash.rms.domain.InfogramActiveResource;
import org.yash.rms.domain.Location;
import org.yash.rms.domain.OrgHierarchy;
import org.yash.rms.domain.Resource;

import org.yash.rms.util.Constants;

import flexjson.JSONSerializer;
import flexjson.transformer.DateTransformer;

/**
 * This class is DTO for InfogramActiveResource domain. 
 * @author samiksha.sant
 *
 */

public class InfogramActiveResourceDTO {
	
	private Integer id;

	private String employeeId;

	private String name;
	
	private String firstName;
	
	private String lastName;
	
	private String middleName;

	private String status;

	private Date dateOfJoining;

	private Date dateOfBirth;

	private String employeeType;

	private String employeeCategory;

	private String grade;

	private String designation;

	private String gender;

	private String emailId;

	private String baseLocation;

	private String locationDivision;

	private String currentLocation;

	private String businessGroup;

	private String businessUnit;

	private String irmName;

	private String srmName;

	private String buhName;

	private String bghName;

	private String hrbpName;

	private String irmEmployeeId;

	private String srmEmployeeId;
	
	private String rmsIrmEmployeeId;

	private String rmsSrmEmployeeId;

	private String buhEmployeeId;

	private String bghEmployeeId;

	private String hrbpEmployeeId;

	private String irmEmailId;

	private String srmEmailId;

	private String buhEmailId;

	private String bghEmailId;

	private String hrbpEmailId;

	private Date resignedDate;

	private Date createdTime;
	
	private String processStatus;
	
	private String resourceType;
	
	private String failureReason;
	
	/*private Location locationInRMS;*/
	private String locationInRMS;
	
	/*private Resource irmInRMS;*/
	
	/*private Resource srmInRMS;*/
	
	/*private OrgHierarchy buIdInRMS;*/
	
	
	
	private String irmInRMS;
	
	public String getRmsIrmEmployeeId() {
		return rmsIrmEmployeeId;
	}

	public void setRmsIrmEmployeeId(String rmsIrmEmployeeId) {
		this.rmsIrmEmployeeId = rmsIrmEmployeeId;
	}

	public String getRmsSrmEmployeeId() {
		return rmsSrmEmployeeId;
	}

	public void setRmsSrmEmployeeId(String rmsSrmEmployeeId) {
		this.rmsSrmEmployeeId = rmsSrmEmployeeId;
	}

	private String srmInRMS;
	
	private String buIdInRMS;
	
	private String rmsBu;
	
	private String rmsBg;
	
	private String rmsDesignation;
	
	private String currentLocationInRMS;
	
	private Date creationTimestamp;
	
	private String modifiedBy;
	
	private String projectBGBU;
	
	private String rmsResourceBGBU;
	
	private String infoResourceBGBU;
	
	private String infoIRMBGBU;
	
	private String infoSRMBGBU;
	
	private String rmsIRMBGBU;
	
	private String rmsSRMBGBU;
	
	
	public String getRmsResourceBGBU() {
		return rmsResourceBGBU;
	}

	public void setRmsResourceBGBU(String rmsResourceBGBU) {
		this.rmsResourceBGBU = rmsResourceBGBU;
	}

	public Date getCreationTimestamp() {
		return creationTimestamp;
	}

	public void setCreationTimestamp(Date creationTimestamp) {
		this.creationTimestamp = creationTimestamp;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public String getProjectBGBU() {
		return projectBGBU;
	}

	public void setProjectBGBU(String projectBGBU) {
		this.projectBGBU = projectBGBU;
	}

	public String getInfoResourceBGBU() {
		return infoResourceBGBU;
	}

	public void setInfoResourceBGBU(String infoResourceBGBU) {
		this.infoResourceBGBU = infoResourceBGBU;
	}

	public String getInfoIRMBGBU() {
		return infoIRMBGBU;
	}

	public void setInfoIRMBGBU(String infoIRMBGBU) {
		this.infoIRMBGBU = infoIRMBGBU;
	}

	public String getInfoSRMBGBU() {
		return infoSRMBGBU;
	}

	public void setInfoSRMBGBU(String infoSRMBGBU) {
		this.infoSRMBGBU = infoSRMBGBU;
	}

	public String getRmsIRMBGBU() {
		return rmsIRMBGBU;
	}

	public void setRmsIRMBGBU(String rmsIRMBGBU) {
		this.rmsIRMBGBU = rmsIRMBGBU;
	}

	public String getRmsSRMBGBU() {
		return rmsSRMBGBU;
	}

	public void setRmsSRMBGBU(String rmsSRMBGBU) {
		this.rmsSRMBGBU = rmsSRMBGBU;
	}

	public String getFailureReason() {
		return failureReason;
	}
	
	public void setFailureReason(String failureReason) {
		this.failureReason = failureReason;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getDateOfJoining() {
		return dateOfJoining;
	}

	public void setDateOfJoining(Date dateOfJoining) {
		this.dateOfJoining = dateOfJoining;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getEmployeeType() {
		return employeeType;
	}

	public void setEmployeeType(String employeeType) {
		this.employeeType = employeeType;
	}

	public String getEmployeeCategory() {
		return employeeCategory;
	}

	public void setEmployeeCategory(String employeeCategory) {
		this.employeeCategory = employeeCategory;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getBaseLocation() {
		return baseLocation;
	}

	public void setBaseLocation(String baseLocation) {
		this.baseLocation = baseLocation;
	}

	public String getLocationDivision() {
		return locationDivision;
	}

	public void setLocationDivision(String locationDivision) {
		this.locationDivision = locationDivision;
	}

	public String getCurrentLocation() {
		return currentLocation;
	}

	public void setCurrentLocation(String currentLocation) {
		this.currentLocation = currentLocation;
	}

	public String getBusinessGroup() {
		return businessGroup;
	}

	public void setBusinessGroup(String businessGroup) {
		this.businessGroup = businessGroup;
	}

	public String getBusinessUnit() {
		return businessUnit;
	}

	public void setBusinessUnit(String businessUnit) {
		this.businessUnit = businessUnit;
	}

	public String getIrmName() {
		return irmName;
	}

	public void setIrmName(String irmName) {
		this.irmName = irmName;
	}

	public String getSrmName() {
		return srmName;
	}

	public void setSrmName(String srmName) {
		this.srmName = srmName;
	}

	public String getBuhName() {
		return buhName;
	}

	public void setBuhName(String buhName) {
		this.buhName = buhName;
	}

	public String getBghName() {
		return bghName;
	}

	public void setBghName(String bghName) {
		this.bghName = bghName;
	}

	public String getHrbpName() {
		return hrbpName;
	}

	public void setHrbpName(String hrbpName) {
		this.hrbpName = hrbpName;
	}

	public String getIrmEmployeeId() {
		return irmEmployeeId;
	}

	public void setIrmEmployeeId(String irmEmployeeId) {
		this.irmEmployeeId = irmEmployeeId;
	}

	public String getSrmEmployeeId() {
		return srmEmployeeId;
	}

	public void setSrmEmployeeId(String srmEmployeeId) {
		this.srmEmployeeId = srmEmployeeId;
	}

	public String getBuhEmployeeId() {
		return buhEmployeeId;
	}

	public void setBuhEmployeeId(String buhEmployeeId) {
		this.buhEmployeeId = buhEmployeeId;
	}

	public String getBghEmployeeId() {
		return bghEmployeeId;
	}

	public void setBghEmployeeId(String bghEmployeeId) {
		this.bghEmployeeId = bghEmployeeId;
	}

	public String getHrbpEmployeeId() {
		return hrbpEmployeeId;
	}

	public void setHrbpEmployeeId(String hrbpEmployeeId) {
		this.hrbpEmployeeId = hrbpEmployeeId;
	}

	public String getIrmEmailId() {
		return irmEmailId;
	}

	public void setIrmEmailId(String irmEmailId) {
		this.irmEmailId = irmEmailId;
	}

	public String getSrmEmailId() {
		return srmEmailId;
	}

	public void setSrmEmailId(String srmEmailId) {
		this.srmEmailId = srmEmailId;
	}

	public String getBuhEmailId() {
		return buhEmailId;
	}

	public void setBuhEmailId(String buhEmailId) {
		this.buhEmailId = buhEmailId;
	}

	public String getBghEmailId() {
		return bghEmailId;
	}

	public void setBghEmailId(String bghEmailId) {
		this.bghEmailId = bghEmailId;
	}

	public String getHrbpEmailId() {
		return hrbpEmailId;
	}

	public void setHrbpEmailId(String hrbpEmailId) {
		this.hrbpEmailId = hrbpEmailId;
	}

	public Date getResignedDate() {
		return resignedDate;
	}

	public void setResignedDate(Date resignedDate) {
		this.resignedDate = resignedDate;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public String getProcessStatus() {
		return processStatus;
	}

	public void setProcessStatus(String processStatus) {
		this.processStatus = processStatus;
	}

	public String getResourceType() {
		return resourceType;
	}

	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	/*public Location getLocationInRMS() {
		return locationInRMS;
	}

	public void setLocationInRMS(Location locationInRMS) {
		this.locationInRMS = locationInRMS;
	}*/

	/*public Resource getIrmInRMS() {
		return irmInRMS;
	}

	public void setIrmInRMS(Resource irmInRMS) {
		this.irmInRMS = irmInRMS;
	}

	public Resource getSrmInRMS() {
		return srmInRMS;
	}

	public void setSrmInRMS(Resource srmInRMS) {
		this.srmInRMS = srmInRMS;
	}

	public OrgHierarchy getBuIdInRMS() {
		return buIdInRMS;
	}

	public void setBuIdInRMS(OrgHierarchy buIdInRMS) {
		this.buIdInRMS = buIdInRMS;
	}*/

	public String getLocationInRMS() {
		return locationInRMS;
	}

	public void setLocationInRMS(String locationInRMS) {
		this.locationInRMS = locationInRMS;
	}

	public String getIrmInRMS() {
		return irmInRMS;
	}

	public void setIrmInRMS(String irmInRMS) {
		this.irmInRMS = irmInRMS;
	}

	public String getSrmInRMS() {
		return srmInRMS;
	}

	public void setSrmInRMS(String srmInRMS) {
		this.srmInRMS = srmInRMS;
	}

	public String getBuIdInRMS() {
		return buIdInRMS;
	}

	public void setBuIdInRMS(String buIdInRMS) {
		this.buIdInRMS = buIdInRMS;
	}

	public String getRmsBu() {
		return rmsBu;
	}

	public void setRmsBu(String rmsBu) {
		this.rmsBu = rmsBu;
	}

	public String getRmsBg() {
		return rmsBg;
	}

	public void setRmsBg(String rmsBg) {
		this.rmsBg = rmsBg;
	}
	
	
	public String getRmsDesignation() {
		return rmsDesignation;
	}

	public void setRmsDesignation(String rmsDesignation) {
		this.rmsDesignation = rmsDesignation;
	}

	public String getCurrentLocationInRMS() {
		return currentLocationInRMS;
	}

	public void setCurrentLocationInRMS(String currentLocationInRMS) {
		this.currentLocationInRMS = currentLocationInRMS;
	}
/*	public static String toJson(InfogramActiveResourceDTO activeResource) {

		return new JSONSerializer()
				.include("id", "employeeId", "name", "firstName", "lastName", "middleName", "status", "dateOfJoining", "dateOfBirth", "employeeType", 
						"employeeCategory", "grade", "designation", "gender", "emailId", "baseLocation", "locationDivision", "currentLocation", 
						"businessGroup", "businessUnit", "irmName", "srmName", "buhName", "bghName", "hrbpName", "irmEmployeeId", "srmEmployeeId", 
						"buhEmployeeId", "bghEmployeeId", "hrbpEmployeeId", "irmEmailId", "srmEmailId", "buhEmailId", "bghEmailId", "hrbpEmailId", 
						"resignedDate", "createdTime", "processStatus", "resourceType", "failureReason", "locationInRMS", "irmInRMS", "srmInRMS", 
						"buIdInRMS", "rmsBu", "rmsBg", "rmsDesignation", "currentLocationInRMS")
				.exclude("*")
				.transform(new DateTransformer(Constants.DATE_PATTERN_4),
						Date.class).serialize(activeResource);
	}*/
	
	public static String toJson(InfogramActiveResourceDTO activeResource) {

		return new JSONSerializer()
				.include("employeeId","name", "processStatus", "dateOfJoining","emailId",
						"designation","rmsDesignation" , "baseLocation", "locationInRMS", "currentLocation", "currentLocationInRMS",
						"businessGroup", "businessUnit", "irmName","irmInRMS", "srmName", "srmInRMS","creationTimestamp", "modifiedBy","irmEmployeeId","srmEmployeeId","rmsIrmEmployeeId","rmsSrmEmployeeId", "projectBGBU", "infoResourceBGBU", "rmsResourceBGBU", 
						"infoIRMBGBU", "infoSRMBGBU", "rmsIRMBGBU", "rmsSRMBGBU", "rmsBg", "rmsBu","status")
				.exclude("*")
				.transform(new DateTransformer(Constants.DATE_PATTERN_4),
						Date.class).serialize(activeResource);
	}

}
