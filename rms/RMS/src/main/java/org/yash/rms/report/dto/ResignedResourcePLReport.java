package org.yash.rms.report.dto;

import java.util.Date;

public class ResignedResourcePLReport {

	private Integer newYashEmpId;
	private String name;
	private String degination; //Group1
	private String businessUnit; //Group2
	private String projectName; //Group3
	private String grade;
	private Date startDate; //From
	private Date endDate; //To
	private Date releaseDate;
	
	
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
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
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
	public Date getReleaseDate() {
		return releaseDate;
	}
	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}
	
	
	
}
