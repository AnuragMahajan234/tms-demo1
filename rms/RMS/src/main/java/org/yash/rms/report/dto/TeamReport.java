package org.yash.rms.report.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.yash.rms.util.Constants;

import flexjson.JSONSerializer;
import flexjson.transformer.DateTransformer;

public class TeamReport {
	private Integer teamId;
	private Integer projectId;

	private String yashEmpId;
	private String employeeName;

	private String projectName;

	private Date weekStartDate;
	private Date weekEndDate;

	private Double productiveHours;
	private Double nonProductiveHours;
	private Double plannedHours;
	private Double billedHours;
	private String teamName;

	List<WeeklyData> weeklyData = new ArrayList<WeeklyData>();

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

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
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

	public List<WeeklyData> getWeeklyData() {
		return weeklyData;
	}

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

	public void setWeeklyData(List<WeeklyData> weeklyData) {
		this.weeklyData = weeklyData;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public Integer getProjectId() {
		return projectId;
	}

	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}

	public Integer getTeamId() {
		return teamId;
	}

	public void setTeamId(Integer teamId) {
		this.teamId = teamId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((projectId == null) ? 0 : projectId.hashCode());
		result = prime * result + ((teamId == null) ? 0 : teamId.hashCode());
		result = prime * result
				+ ((yashEmpId == null) ? 0 : yashEmpId.hashCode());
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
		TeamReport other = (TeamReport) obj;
		if (projectId == null) {
			if (other.projectId != null)
				return false;
		} else if (!projectId.equals(other.projectId))
			return false;
		if (teamId == null) {
			if (other.teamId != null)
				return false;
		} else if (!teamId.equals(other.teamId))
			return false;
		if (yashEmpId == null) {
			if (other.yashEmpId != null)
				return false;
		} else if (!yashEmpId.equals(other.yashEmpId))
			return false;
		return true;
	}

	public static String toJson(Object[] teamReport) {
		return new JSONSerializer()
				.include("teamId", "projectId", "yashEmpId", "employeeName",
						"projectName", "weekStartDate", "weekEndDate",
						"productiveHours", "nonProductiveHours",
						"plannedHours", "billedHours", "teamName", "weeklyData")
				.exclude("*")
				.transform(new DateTransformer(Constants.DATE_PATTERN),
						Date.class).serialize(teamReport);
	}

}
