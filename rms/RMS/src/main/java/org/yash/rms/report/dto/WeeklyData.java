package org.yash.rms.report.dto;

import java.util.Date;
import java.util.List;

public class WeeklyData {

	private Double productiveHours;
	private Double nonProductiveHours;
	private Double plannedHours;
	private Double billedHours;
	private Date weekStartDate;
	private Date weekEndDate;
	
	//Changes for Utilization Report
	
	private Double d1_hours;
	private Double d2_hours;
	private Double d3_hours;
	private Double d4_hours;
	private Double d5_hours;
	private Double d6_hours;
	private Double d7_hours;
	
	private String d1_comment;
	private String d2_comment;
	private String d3_comment;
	private String d4_comment;
	private String d5_comment;
	private String d6_comment;
	private String d7_comment;
	
	private String activity_name;
	private String module;
	
	private String projectName;
	private String projectCode;
	
	private Character status;
	
	private String custom_activity_name;
	
	private List<DayData> dayData;
	
	public String getCustom_activity_name() {
		return custom_activity_name;
	}

	public void setCustom_activity_name(String custom_activity_name) {
		this.custom_activity_name = custom_activity_name;
	}
	
	public List<DayData> getDayData() {
		return dayData;
	}

	public void setDayData(List<DayData> dayData) {
		this.dayData = dayData;
	}

	public Double getD1_hours() {
		return d1_hours;
	}

	public void setD1_hours(Double d1_hours) {
		this.d1_hours = d1_hours;
	}

	public Double getD2_hours() {
		return d2_hours;
	}

	public void setD2_hours(Double d2_hours) {
		this.d2_hours = d2_hours;
	}

	public Double getD3_hours() {
		return d3_hours;
	}

	public void setD3_hours(Double d3_hours) {
		this.d3_hours = d3_hours;
	}

	public Double getD4_hours() {
		return d4_hours;
	}

	public void setD4_hours(Double d4_hours) {
		this.d4_hours = d4_hours;
	}

	public Double getD5_hours() {
		return d5_hours;
	}

	public void setD5_hours(Double d5_hours) {
		this.d5_hours = d5_hours;
	}

	public Double getD6_hours() {
		return d6_hours;
	}

	public void setD6_hours(Double d6_hours) {
		this.d6_hours = d6_hours;
	}

	public Double getD7_hours() {
		return d7_hours;
	}

	public void setD7_hours(Double d7_hours) {
		this.d7_hours = d7_hours;
	}

	public String getD1_comment() {
		return d1_comment;
	}

	public void setD1_comment(String d1_comment) {
		this.d1_comment = d1_comment;
	}

	public String getD2_comment() {
		return d2_comment;
	}

	public void setD2_comment(String d2_comment) {
		this.d2_comment = d2_comment;
	}

	public String getD3_comment() {
		return d3_comment;
	}

	public void setD3_comment(String d3_comment) {
		this.d3_comment = d3_comment;
	}

	public String getD4_comment() {
		return d4_comment;
	}

	public void setD4_comment(String d4_comment) {
		this.d4_comment = d4_comment;
	}

	public String getD5_comment() {
		return d5_comment;
	}

	public void setD5_comment(String d5_comment) {
		this.d5_comment = d5_comment;
	}

	public String getD6_comment() {
		return d6_comment;
	}

	public void setD6_comment(String d6_comment) {
		this.d6_comment = d6_comment;
	}

	public String getD7_comment() {
		return d7_comment;
	}

	public void setD7_comment(String d7_comment) {
		this.d7_comment = d7_comment;
	}

	public String getActivity_name() {
		return activity_name;
	}

	public void setActivity_name(String activity_name) {
		this.activity_name = activity_name;
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}
	
	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public Character getStatus() {
		return status;
	}

	public void setStatus(Character status) {
		this.status = status;
	}
	
	public String getProjectCode() {
		return projectCode;
	}

	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}

	//Changes for Utilization Report
	
	public Double getProductiveHours() {
		return productiveHours;
	}

	public void setProductiveHours(Double productiveHours) {
		this.productiveHours = productiveHours;
	}

	public Double getNonProductiveHours() {
		return nonProductiveHours;
	}

	public void setNonProductiveHours(Double nonProductiveHours) {
		this.nonProductiveHours = nonProductiveHours;
	}

	public Double getPlannedHours() {
		return plannedHours;
	}

	public void setPlannedHours(Double plannedHours) {
		this.plannedHours = plannedHours;
	}

	public Double getBilledHours() {
		return billedHours;
	}

	public void setBilledHours(Double billedHours) {
		this.billedHours = billedHours;
	}

	public Date getWeekStartDate() {
		return weekStartDate;
	}

	public void setWeekStartDate(Date weekStartDate) {
		this.weekStartDate = weekStartDate;
	}

	public Date getWeekEndDate() {
		return weekEndDate;
	}

	public void setWeekEndDate(Date weekEndDate) {
		this.weekEndDate = weekEndDate;
	}

}
