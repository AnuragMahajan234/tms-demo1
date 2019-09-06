package org.yash.rms.report.dto;

import java.util.Collection;
import java.util.Date;

import org.yash.rms.util.Constants;

import flexjson.JSONSerializer;
import flexjson.transformer.DateTransformer;

public class ResourceMovementReport {

	private String yashEmpID;
	private String employeeName;
	private String projectName;
	private String allocationType;
	private String bGBUName;
	private Date startDate;
	private Date endDate;
	private Date dateOfJoining;
	private String allocationChange;
	private String previousAllocationType;
	private String ResourceType;

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

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getAllocationType() {
		return allocationType;
	}

	public void setAllocationType(String allocationType) {
		this.allocationType = allocationType;
	}

	public String getBGBUName() {
		return bGBUName;
	}

	public void setBGBUName(String bGBUName) {
		this.bGBUName = bGBUName;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Date getDateOfJoining() {
		return dateOfJoining;
	}

	public void setDateOfJoining(Date dateOfJoining) {
		this.dateOfJoining = dateOfJoining;
	}

	public String getPreviousAllocationType() {
		return previousAllocationType;
	}

	public void setPreviousAllocationType(String previousAllocationType) {
		this.previousAllocationType = previousAllocationType;
	}

	public String getAllocationChange() {
		return allocationChange;
	}

	public void setAllocationChange(String allocationChange) {
		this.allocationChange = allocationChange;
	}

	public String getResourceType() {
		return ResourceType;
	}

	public void setResourceType(String resourceType) {
		ResourceType = resourceType;
	}

	public static String toJsonArray(Collection<ResourceMovementReport> collection) {
		String jsonString = new JSONSerializer().include("yashEmpID", "employeeName", "projectName", "allocationType", "bGBUName", "startDate")
				.transform(new DateTransformer(Constants.DATE_PATTERN_4), Date.class).serialize(collection);
		return jsonString;
	}
}