/**
 * 
 */
package org.yash.rms.report.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.yash.rms.util.Constants;

import flexjson.JSONSerializer;
import flexjson.transformer.DateTransformer;

import org.yash.rms.report.dto.WeeklyData;


public class UtilizationDetailReport {
	
	private String employeeNameId;
	private String weekEndDate;
	private Double billedHours;
	private Double plannedHours;
	private Double totalActualHours;
	private Double productiveHours;
	private Double nonProductiveHours;
	private Double nonProdHours;
	private Double utilizationPercentage;
	
	
	public String getEmployeeNameId() {
		return employeeNameId;
	}

	public void setEmployeeNameId(String employeeNameId) {
		this.employeeNameId = employeeNameId;
	}
		
	private List<WeeklyData> weeklyDataList=  new ArrayList<WeeklyData>();
	
	private WeeklyData weeklyData = new WeeklyData();
	
	
	public List<WeeklyData> getWeeklyDataList() {
		return weeklyDataList;
	}

	public void setWeeklyData(List<WeeklyData> weeklyDataList) {
		this.weeklyDataList = weeklyDataList;
	}

	public void setWeeklyData(WeeklyData weeklyData) {
		this.weeklyData = weeklyData;
	}
	public WeeklyData getWeeklyData() {
		return weeklyData;
	}

	public Double getTotalActualHours() {
		return totalActualHours;
	}

	public void setTotalActualHours(Double totalActualHours) {
		this.totalActualHours = totalActualHours;
	}

	public Double getNonProdHours() {
		return nonProdHours;
	}

	public void setNonProdHours(Double nonProdHours) {
		this.nonProdHours = nonProdHours;
	}

	public Double getUtilizationPercentage() {
		return utilizationPercentage;
	}

	public void setUtilizationPercentage(Double utilizationPercentage) {
		this.utilizationPercentage = utilizationPercentage;
	}


	public String getWeekEndDate() {
		return weekEndDate;
	}

	public void setWeekEndDate(String weekEndDate) {
		this.weekEndDate = weekEndDate;
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
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((employeeNameId == null) ? 0 : employeeNameId.hashCode());
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
		UtilizationDetailReport other = (UtilizationDetailReport) obj;
		if (employeeNameId == null) {
			if (other.employeeNameId != null)
				return false;
		} else if (!employeeNameId.equals(other.employeeNameId))
			return false;		
		return true;
	}
	
	public static String toJson(Object[] teamReport) {
		return new JSONSerializer()
				.include("employeeName", "employeeId", "weekEndDate", "billedHours", "plannedHours", "totalActualHours", "productiveHours", "nonProductiveHours",
						"nonProdHours", "utilizationPercentage", "date", "actualHours","activity","module","comments").exclude("*")
				.transform(new DateTransformer(Constants.DATE_PATTERN), Date.class).serialize(teamReport);
	}
	

}
