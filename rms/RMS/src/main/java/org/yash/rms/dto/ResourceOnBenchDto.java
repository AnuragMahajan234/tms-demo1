package org.yash.rms.dto;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;


public class ResourceOnBenchDto {

	  private Integer employeeId;
	  private String yashEmpId;
	  private String employeeName;
	  private String designation_name;
	  private String allocationType;
	  private String resume;
	  private java.sql.Date dateOfJoining;
	  private java.sql.Date allocStartDate;
	  private java.sql.Date allocEndDate;
	  
	  public java.sql.Date getAllocEndDate() {
		return allocEndDate;
	}

	public java.sql.Date getAllocStartDate() {
		return allocStartDate;
	}

	public void setAllocStartDate(java.sql.Date allocStartDate) {
		this.allocStartDate = allocStartDate;
	}

	public void setAllocEndDate(java.sql.Date allocEndDate) {
		this.allocEndDate = allocEndDate;
	}

	public ResourceOnBenchDto() {}

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

	public String getAllocationType() {
		return allocationType;
	}

	public void setAllocationType(String allocationType) {
		this.allocationType = allocationType;
	}

	public String getResume() {
		return resume;
	}

	public void setResume(String resume) {
		this.resume = resume;
	}
	  
	public static List<JSONArray> toJsonArray(List<ResourceOnBenchDto> allResources) {
		List<JSONArray> dataArray = new ArrayList<JSONArray>();
		for (ResourceOnBenchDto resource : allResources) {
			JSONArray object = new JSONArray();
			object.put(0, resource.getResume() + "");
			object.put(1, resource.getEmployeeId() + "");
			object.put(2, resource.getYashEmpId());
			object.put(3, resource.getEmployeeName());
			object.put(4, resource.getDesignation_name());
			object.put(5, resource.getAllocationType());
			object.put(6, resource.getDateOfJoining());
			object.put(7, resource.getAllocStartDate());
			object.put(8, resource.getAllocEndDate());

			dataArray.add(object);
		}

		return dataArray;
	}

	public java.sql.Date getDateOfJoining() {
		return dateOfJoining;
	}

	public void setDateOfJoining(java.sql.Date dateOfJoining) {
		this.dateOfJoining = dateOfJoining;
	}
	  
}
