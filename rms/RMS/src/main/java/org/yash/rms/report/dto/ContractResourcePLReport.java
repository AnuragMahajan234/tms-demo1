package org.yash.rms.report.dto;

import java.util.Date;

public class ContractResourcePLReport {

	private Integer newYashEmpId; // Contract ID
	private String name;
	private String projectName; // Project
	private String designation;
	private String skill;
	private Date joiningDate;
	private Date startDate; // From
	private Date endDate;//To
	private String customerName; // Dept
	private String baseLocation; // Location
	private String currentLocation; // Location
	
	

	public String getCurrentLocation() {
		return currentLocation;
	}

	public void setCurrentLocation(String currentLocation) {
		this.currentLocation = currentLocation;
	}

	public Integer getNewYashEmpId() {
		return newYashEmpId;
	}

	public void setNewYashEmpId(Integer newYashEmpId) {
		this.newYashEmpId = newYashEmpId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBusinessUnit() {
		return customerName;
	}

	public void setBusinessUnit(String businessUnit) {
		this.customerName = businessUnit;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	/*
	 * public String getGrade() { return grade; } public void setGrade(String
	 * grade) { this.grade = grade; }
	 */
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

	public String getBaseLocation() {
		return baseLocation;
	}

	public void setBaseLocation(String baseLocation) {
		this.baseLocation = baseLocation;
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

	public Date getJoiningDate() {
		return joiningDate;
	}

	public void setJoiningDate(Date joiningDate) {
		this.joiningDate = joiningDate;
	}

}
