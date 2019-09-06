package org.yash.rms.util;

import java.util.HashMap;
import java.util.Map;

public class ResourceSearchCriteria {
	
	Map<String,String> colandTableNameMap = new HashMap<String,String>();

	String[] cols;
	
	String refTableName;
	
	String refColName;

	String searchValue;
	
	String sortTableName;
	
	String tableColumnForSort;
	
	public String getTableColumnForSort() {
		
		if("resource".equals(sortTableName)) {
			return "CM";
		
		}else if("ownership".equals(sortTableName)) {
			return sortTableName;
		
		}else {
			return sortTableName+"Id";
		}
	}

	public void setTableColumnForSort(String tableColumnForSort) {
		
		this.tableColumnForSort = tableColumnForSort;
	}

	String baseTableCol;
	
	String orderStyle;
	
	String orederBy;
	
	
	public String getRefColName() {
		
		return refColName;
	}

	public void setRefColName(String refColName) {
		
		this.refColName = refColName;
	}

	public ResourceSearchCriteria() {
		
		colandTableNameMap.put("currentReportingManager", "resource");
		colandTableNameMap.put("currentReportingManagerTwo", "resource");		
		colandTableNameMap.put("designationName", "designation");
		colandTableNameMap.put("grade", "grade");
		colandTableNameMap.put("visa", "visa");
		colandTableNameMap.put("location", "location");
		colandTableNameMap.put("ownershipName", "ownership");
		colandTableNameMap.put("currentBgBuName", "currentBu");
		colandTableNameMap.put("parentBgBuName", "bu");
		
		//cols = new String[]{ "employeeId","yashEmpId", "employeeName", "designationName", "grade", "dateOfJoining", "releaseDate", "ownershipName", "currentBgBuName", "parentBgBuName", "emailId", /*"resumeFileName",*/ "contactNumber", "location", "currentReportingManager", "currentReportingManagerTwo", "userRole"};
		cols = new String[]{ "employeeId","yashEmpId", "employeeName", "designationName", "grade","resume","tef", "dateOfJoining", "releaseDate", "ownershipName","yearDiff", "totalExper", "relevantExper", "currentBgBuName", "parentBgBuName", "emailId", /*"resumeFileName",*/ "location","currentReportingManager", "currentReportingManagerTwo", "userRole"};
	}
	
	public Map<String, String> getColandTableNameMap() {
		
		return colandTableNameMap;
	}

	public void setColandTableNameMap(Map<String, String> colandTableNameMap) {
		
		this.colandTableNameMap = colandTableNameMap;
	}

	public String[] getCols() {
		
		return cols;
	}

	public void setCols(String[] cols) {
		
		this.cols = cols;
	}

	public String getRefTableName() {
		
		return refTableName;
	}

	public void setRefTableName(String refTableName) {
		
		this.refTableName = refTableName;
	}

	public String getBaseTableCol() {
		
		if("resource".equals(refTableName)) {
			return "CM";
		
		}else if("ownership".equals(refTableName)) {
			return refTableName;
		
		}else {
			return refTableName+"Id";
		}
	}

	public void setBaseTableCol(String baseTableCol) {
		
		this.baseTableCol = baseTableCol;
	}

	public String getSearchValue() {
		
		return searchValue;
	}

	public void setSearchValue(String searchValue) {
		
		this.searchValue = searchValue;
	}
	
	public Integer getIntegerValue() {
		
		Integer intValue = 0;
		
		if(refColName.equals("plannedCapacity") || refColName.equals("actualCapacity")) {
			
			intValue = Integer.parseInt(getSearchValue());
		}
		return intValue;
	}
	
	public boolean isSearchOnDate() {
		
		boolean searchOnDate = false;
		
		if(refColName.equals("dateOfJoining") || refColName.equals("releaseDate")) {
			
			searchOnDate = true; 
		}
		return searchOnDate;
	}

	public String getOrderStyle() {
		
		return orderStyle;
	}

	public void setOrderStyle(String orderStyle) {
		
		this.orderStyle = orderStyle;
	}

	public String getOrederBy() {
		
		return orederBy;
	}

	public void setOrederBy(String orederBy) {
		
		this.orederBy = orederBy;
	}

	public String getSortTableName() {
		
		return sortTableName;
	}

	public void setSortTableName(String sortTableName) {
		this.sortTableName = sortTableName;
	}
}
