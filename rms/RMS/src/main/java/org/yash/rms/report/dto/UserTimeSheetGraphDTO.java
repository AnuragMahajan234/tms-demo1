package org.yash.rms.report.dto;

import java.util.Collection;
import java.util.Date;

import org.yash.rms.util.Constants;

import flexjson.JSONSerializer;
import flexjson.transformer.DateTransformer;

public class UserTimeSheetGraphDTO {
	
	String weekEndDate;
	String approvedCount;
	String pendingCount;
	String notSubmittedCount;
	String rejectedCount;
	
	public String getWeekEndDate() {
		return weekEndDate;
	}
	
	public void setWeekEndDate(String weekEndDate) {
		this.weekEndDate = weekEndDate;
	}
	
	public String getApprovedCount() {
		return approvedCount;
	}
	
	public void setApprovedCount(String approvedCount) {
		this.approvedCount = approvedCount;
	}
	
	public String getPendingCount() {
		return pendingCount;
	}
	
	public void setPendingCount(String pendingCount) {
		this.pendingCount = pendingCount;
	}
	
	public String getNotSubmittedCount() {
		return notSubmittedCount;
	}
	
	public void setNotSubmittedCount(String notSubmittedCount) {
		this.notSubmittedCount = notSubmittedCount;
	}
	
	public String getRejectedCount() {
		return rejectedCount;
	}

	public void setRejectedCount(String rejectedCount) {
		this.rejectedCount = rejectedCount;
	}
	
	public static String toJsonArray(Collection<UserTimeSheetGraphDTO> collection) {
		String jsonString = new JSONSerializer().include("weekEndDate", "approvedCount", "pendingCount", "notSubmittedCount", "rejectedCount")
				.serialize(collection);
		return jsonString;
	}

}
