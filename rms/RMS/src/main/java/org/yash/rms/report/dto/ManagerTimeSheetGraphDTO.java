package org.yash.rms.report.dto;

import java.util.Collection;

import flexjson.JSONSerializer;

 

public class ManagerTimeSheetGraphDTO {
	
	private String projectName;
	private Integer billable;
	private Integer others;
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public Integer getBillable() {
		return billable;
	}
	public void setBillable(Integer billable) {
		this.billable = billable;
	}
	public Integer getOthers() {
		return others;
	}
	public void setOthers(Integer others) {
		this.others = others;
	}
	
	public static String toJsonArray(Collection<ManagerTimeSheetGraphDTO> collection) {
		String jsonString = new JSONSerializer().include("projectName", "billable", "others")
				.serialize(collection);
		return jsonString;
	}
	 
}
