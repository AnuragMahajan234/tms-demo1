package org.yash.rms.util;

import java.util.HashMap;
import java.util.Map;

public class ResourceAllocationSearchCriteria {
	
	Map<String,String> colandTableNameMap = new HashMap<String,String>();

	String[] cols;
	
	String[] resAllocCols;
	
	String refTableName;
	
	String refColName;

	String searchValue;
	
	String sortTableName;
	
	String tableColumnForSort;
	
	public String getTableColumnForSort() {
		
		return sortTableName+"Id";
	}
	
	public void setTableColumnForSort(String tableColumnForSort) {
		
		this.tableColumnForSort = tableColumnForSort;
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
		
		return refTableName+"Id";
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

	String baseTableCol;
	
	String orderStyle;
	
	String orederBy;
	
	public String getRefColName() {
		
		return refColName;
	}

	public void setRefColName(String refColName) {
		
		this.refColName = refColName;
	}

	public String[] getResAllocCols() {
		return resAllocCols;
	}

	public void setResAllocCols(String[] resAllocCols) {
		this.resAllocCols = resAllocCols;
	}
	
	public ResourceAllocationSearchCriteria() {
		
		/*colandTableNameMap.put("designationName", "designation");
		colandTableNameMap.put("grade", "grade");*/
		colandTableNameMap.put("grade", "grade");
		colandTableNameMap.put("designationName", "designation");
		colandTableNameMap.put("projectName", "currentProject");
		colandTableNameMap.put("location", "location");
		colandTableNameMap.put("currentBgBu", "currentBu");
				
		cols = new String[]{ "employeeId", "yashEmpId", "employeeName", "designationName", "grade", "projectName", "totalPlannedHrs" , "totalReportedHrs" , "totalBilledHrs" };
		resAllocCols = new String[]{ "employeeId", "yashEmpId", "employeeName", "designationName", "grade", "currentBgBu","location","projectName" };
	}

	
}
