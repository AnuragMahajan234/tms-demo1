package org.yash.rms.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.FilterDefs;
import org.hibernate.annotations.Filters;
import org.hibernate.annotations.ParamDef;

/**
 * This class represents java entity for Infogram Table in RMS for in-active
 * resource.
 * 
 * @author samiksha.sant
 *
 */

@Entity
@Table(name = "info_inactive_employee")	

@FilterDefs({ @FilterDef(name = InfogramInactiveResource.PENDING_FAILURE,parameters={@ParamDef(name="processStatus1", type="string"),@ParamDef(name="processStatus2",type="string")})})
@Filters({@Filter(name = InfogramInactiveResource.PENDING_FAILURE, condition = "process_status=:processStatus1 or process_status=:processStatus2 ")})
public class InfogramInactiveResource {
	
	public final static String INFOID = "id";
	public final static String CREATEDTIME = "createdTime";
	public final static String PENDING_FAILURE = "PendingorFailure";
	public final static String PENDING = "Pending";
	public final static String FAILURE = "Failure";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "emp_id")
	private String employeeId;

	@Column(name = "emp_name")
	private String employeeName;

	@Column(name = "emp_status")
	private String employeeStatus;

	@Column(name = "resigned_date")
	private Date resignedDate;

	@Column(name = "relieving_date")
	private Date releasedDate;
	
	@Column(name = "process_status")
	private String processStatus; 
	
	@Column(name = "failure_reason")
	private String failureReason;
	
	@Column(name = "insertion_timestamp")
	private Date createdTime;
	
	@Column(name = "modified_by")
	private String modifiedBy;
	

	@Column(name = "modified_name")
	private String modifiedName;
	

	@Column(name = "modified_time")
	private Date modifiedTime;

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
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

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getEmployeeStatus() {
		return employeeStatus;
	}

	public void setEmployeeStatus(String employeeStatus) {
		this.employeeStatus = employeeStatus;
	}

	public Date getResignedDate() {
		return resignedDate;
	}

	public void setResignedDate(Date resignedDate) {
		this.resignedDate = resignedDate;
	}

	public Date getReleasedDate() {
		return releasedDate;
	}

	public void setReleasedDate(Date releasedDate) {
		this.releasedDate = releasedDate;
	}

	public String getProcessStatus() {
		return processStatus;
	}

	public void setProcessStatus(String processStatus) {
		this.processStatus = processStatus;
	}

	public String getFailureReason() {
		return failureReason;
	}

	public void setFailureReason(String failureReason) {
		this.failureReason = failureReason;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public String getModifiedName() {
		return modifiedName;
	}

	public void setModifiedName(String modifiedName) {
		this.modifiedName = modifiedName;
	}

	public Date getModifiedTime() {
		return modifiedTime;
	}

	public void setModifiedTime(Date modifiedTime) {
		this.modifiedTime = modifiedTime;
	}
	
	

}
