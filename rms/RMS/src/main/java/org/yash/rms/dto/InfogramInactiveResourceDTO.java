package org.yash.rms.dto;

import java.util.Date;

import org.yash.rms.util.Constants;

import flexjson.JSONSerializer;
import flexjson.transformer.DateTransformer;

public class InfogramInactiveResourceDTO {
	
	private Integer id;

	private String employeeId;

	private String employeeName;

	private String employeeStatus;

	private Date resignedDate;

	private Date releasedDate;
	
	private String processStatus;

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

	public static String toJson(InfogramInactiveResourceDTO inActiveResource) {

		return new JSONSerializer()
				.include("id", "employeeId", "employeeName", "employeeStatus", "resignedDate", "releasedDate", "processStatus")
				.exclude("*")
				.transform(new DateTransformer(Constants.DATE_PATTERN_4),
						Date.class).serialize(inActiveResource);
	}

	
}
