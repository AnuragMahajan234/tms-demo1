package org.yash.rms.form;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.FactoryUtils;
import org.apache.commons.collections.list.LazyList;


public class UserActivityForm {
	
	private String submitStatus;
	
	private Date weekEndDate;
	
	private Date weekStartDate;
	
	private List<NewTimeSheet> entries = LazyList.decorate(new ArrayList<NewTimeSheet>(), FactoryUtils.instantiateFactory(NewTimeSheet.class)); 
	
	public List<NewTimeSheet> getEntries() {
		return entries;
	}
	public void setEntries(List<NewTimeSheet> entries) {
		this.entries = entries;
	}
	public String getSubmitStatus() {
		return submitStatus;
	}
	public void setSubmitStatus(String submitStatus) {
		this.submitStatus = submitStatus;
	}
	public Date getWeekEndDate() {
		return weekEndDate;
	}
	public void setWeekEndDate(Date weekEndDate) {
		this.weekEndDate = weekEndDate;
	}
	public Date getWeekStartDate() {
		return weekStartDate;
	}
	public void setWeekStartDate(Date weekStartDate) {
		this.weekStartDate = weekStartDate;
	}
	
}
