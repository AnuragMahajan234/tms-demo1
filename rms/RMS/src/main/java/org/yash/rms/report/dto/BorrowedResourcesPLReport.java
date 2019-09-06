package org.yash.rms.report.dto;

import java.util.Date;

public class BorrowedResourcesPLReport {

	private Integer newYashEmpId; // New ID and Emp ID 
	private String name;
	private String degination; 
	private String businessUnit; //Dept
	private String projectName; //Project
	private String skill;	
	private String baseLocation; //Location
	private Date startDate; //From
	private Date endDate; //To	
	private Date joiningDate; //DOJ
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
	public String getDegination() {
		return degination;
	}
	public void setDegination(String degination) {
		this.degination = degination;
	}
	public String getBusinessUnit() {
		return businessUnit;
	}
	public void setBusinessUnit(String businessUnit) {
		this.businessUnit = businessUnit;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getSkill() {
		return skill;
	}
	public void setSkill(String skill) {
		this.skill = skill;
	}
	public String getBaseLocation() {
		return baseLocation;
	}
	public void setBaseLocation(String baseLocation) {
		this.baseLocation = baseLocation;
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
	public Date getJoiningDate() {
		return joiningDate;
	}
	public void setJoiningDate(Date joiningDate) {
		this.joiningDate = joiningDate;
	}
	
}
