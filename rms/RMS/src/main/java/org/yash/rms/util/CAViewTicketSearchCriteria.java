package org.yash.rms.util;

import java.util.HashMap;
import java.util.Map;

public class CAViewTicketSearchCriteria {
	
	Map<String,String> colandTableNameMap = new HashMap<String,String>();

	String[] cols;
	
	String refTableName;
	
	String refColName;

	String searchValue;
	
	String sortTableName;
	
	String tableColumnForSort;
	
	String baseTableCol;
	
	String orderStyle;
	
	String orederBy;

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

	public String getRefColName() {
		return refColName;
	}

	public void setRefColName(String refColName) {
		this.refColName = refColName;
	}

	public String getSearchValue() {
		return searchValue;
	}

	public void setSearchValue(String searchValue) {
		this.searchValue = searchValue;
	}

	public String getSortTableName() {
		return sortTableName;
	}

	public void setSortTableName(String sortTableName) {
		this.sortTableName = sortTableName;
	}

	public String getTableColumnForSort() {
		return tableColumnForSort;
	}

	public void setTableColumnForSort(String tableColumnForSort) {
		this.tableColumnForSort = tableColumnForSort;
	}

	public String getBaseTableCol() {
		return baseTableCol;
	}

	public void setBaseTableCol(String baseTableCol) {
		this.baseTableCol = baseTableCol;
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
	
	
}
