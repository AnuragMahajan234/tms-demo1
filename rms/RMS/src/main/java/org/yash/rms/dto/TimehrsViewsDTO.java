package org.yash.rms.dto;

import java.sql.Date;
import java.util.List;
public class TimehrsViewsDTO
{
  private Integer employeeId;
  private String yashEmpId;
  private String employeeName;
  private String designation_name;
  private String grade;
  private int plannedCapacity;
  private int actualCapacity;
  private float totalPlannedHrs;
  private float totalReportedHrs;
  private float totalBilledHrs;
  private List<String> projectName;
  private boolean submitStatus;
  private Date dateOfJoining;
  private String currentProject;
  private Date releaseDate;
  private String locName;
  private String curBgBu;
  
  public String getLocName() {
	return locName;
}
public void setLocName(String locName) {
	this.locName = locName;
}
public String getCurBgBu() {
	return curBgBu;
}
public void setCurBgBu(String curBgBu) {
	this.curBgBu = curBgBu;
}
public Integer getEmployeeId() {
	return employeeId;
}
public void setEmployeeId(Integer employeeId) {
	this.employeeId = employeeId;
}

public String getYashEmpId() {
	return yashEmpId;
}
public void setYashEmpId(String yashEmpId) {
	this.yashEmpId = yashEmpId;
}
public String getEmployeeName() {
	return employeeName;
}
public void setEmployeeName(String employeeName) {
	this.employeeName = employeeName;
}
public String getDesignation_name() {
	return designation_name;
}
public void setDesignation_name(String designation_name) {
	this.designation_name = designation_name;
}
public String getGrade() {
	return grade;
}
public void setGrade(String grade) {
	this.grade = grade;
}
public int getPlannedCapacity() {
	return plannedCapacity;
}
public void setPlannedCapacity(int plannedCapacity) {
	this.plannedCapacity = plannedCapacity;
}
public int getActualCapacity() {
	return actualCapacity;
}
public void setActualCapacity(int actualCapacity) {
	this.actualCapacity = actualCapacity;
}
public float getTotalPlannedHrs() {
	return totalPlannedHrs;
}
public void setTotalPlannedHrs(float totalPlannedHrs) {
	this.totalPlannedHrs = totalPlannedHrs;
}
public float getTotalReportedHrs() {
	return totalReportedHrs;
}
public void setTotalReportedHrs(float totalReportedHrs) {
	this.totalReportedHrs = totalReportedHrs;
}
public float getTotalBilledHrs() {
	return totalBilledHrs;
}
public void setTotalBilledHrs(float totalBilledHrs) {
	this.totalBilledHrs = totalBilledHrs;
}
public List<String> getProjectName() {
	return projectName;
}
public void setProjectName(List<String> projectName) {
	this.projectName = projectName;
}
public boolean isSubmitStatus() {
	return submitStatus;
}
public void setSubmitStatus(boolean submitStatus) {
	this.submitStatus = submitStatus;
}
public Date getDateOfJoining() {
	return dateOfJoining;
}
public void setDateOfJoining(Date dateOfJoining) {
	this.dateOfJoining = dateOfJoining;
}
public String getCurrentProject() {
	return currentProject;
}
public void setCurrentProject(String currentProject) {
	this.currentProject = currentProject;
}
public Date getReleaseDate() {
	return releaseDate;
}
public void setReleaseDate(Date releaseDate) {
	this.releaseDate = releaseDate;
}
  
}