package org.yash.rms.report.dto;

public class TimeSheetDetailReport {

	private String empId;
	private String name;
	private String businessUnit;
	private String location;
	private String project;
	private String reportingMgr;
	private String emailId;

	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the businessUnit
	 */
	public String getBusinessUnit() {
		return businessUnit;
	}

	/**
	 * @param businessUnit the businessUnit to set
	 */
	public void setBusinessUnit(String businessUnit) {
		this.businessUnit = businessUnit;
	}

	/**
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * @param location the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}

	/**
	 * @return the project
	 */
	public String getProject() {
		return project;
	}

	/**
	 * @param project the project to set
	 */
	public void setProject(String project) {
		this.project = project;
	}

	/**
	 * @return the reportingMgr
	 */
	public String getReportingMgr() {
		return reportingMgr;
	}

	/**
	 * @param reportingMgr the reportingMgr to set
	 */
	public void setReportingMgr(String reportingMgr) {
		this.reportingMgr = reportingMgr;
	}

	/**
	 * @return the emailId
	 */
	public String getEmailId() {
		return emailId;
	}

	/**
	 * @param emailId the emailId to set
	 */
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "TimeSheetDetailReport [empId=" + empId + " name=" + name + ", businessUnit=" + businessUnit
				+ ", location=" + location + ", project=" + project + ", reportingMgr=" + reportingMgr + ", emailId="
				+ emailId + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		TimeSheetDetailReport other = (TimeSheetDetailReport) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	public TimeSheetDetailReport() {
		super();
	}

	public TimeSheetDetailReport(String empId, String name, String businessUnit, String location, String project,
			String reportingMgr, String emailId) {
		super();
		this.empId = empId;
		this.name = name;
		this.businessUnit = businessUnit;
		this.location = location;
		this.project = project;
		this.reportingMgr = reportingMgr;
		this.emailId = emailId;
	}

}
