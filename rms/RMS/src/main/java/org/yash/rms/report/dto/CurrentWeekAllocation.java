package org.yash.rms.report.dto;


public class CurrentWeekAllocation {
	
	private String allocationType;
	private String numberOfEmp;
	private Integer employeeId;
	private String empName;
	private String projectName;
	private String designationName;
	private String grade;
	private String baseLocation;
	private String buName;
	
	public String getAllocationType() {
		return allocationType;
	}
	public void setAllocationType(String allocationType) {
		this.allocationType = allocationType;
	}
	public Integer getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
	}
	public String getEmpName() {
		return empName;
	}
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getDesignationName() {
		return designationName;
	}
	public void setDesignationName(String designationName) {
		this.designationName = designationName;
	}
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	public String getBaseLocation() {
		return baseLocation;
	}
	public void setBaseLocation(String baseLocation) {
		this.baseLocation = baseLocation;
	}
	public String getBuName() {
		return buName;
	}
	public void setBuName(String buName) {
		this.buName = buName;
	}
	public String getNumberOfEmp() {
		return numberOfEmp;
	}
	public void setNumberOfEmp(String numberOfEmp) {
		this.numberOfEmp = numberOfEmp;
	}
	
	
	
}
