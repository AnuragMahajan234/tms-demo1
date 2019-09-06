package org.yash.rms.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "timehrs_employeeid_projectname")
public class TimehrsEmployeeIdProjectView implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "employee_id")
	private Integer employeeId;

	@Id
	@Column(name = "project_name")
	private String projectName;

	@Id
	@Column(name = "alloc_end_date")
	private Date allocEndDate;

	public Integer getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public Date getAllocEndDate() {
		return allocEndDate;
	}

	public void setAllocEndDate(Date allocEndDate) {
		this.allocEndDate = allocEndDate;
	}

}
