package org.yash.rms.util;

import java.util.Date;
import java.util.Set;

public class SearchCriteriaGeneric {

	String iSortColumn;
	/*public String getiSortColumn() {
		return iSortColumn;
	}



	public void setiSortColumn(String iSortColumn) {
		this.iSortColumn = iSortColumn;
	}*/



	public String getISortDir() {
		return iSortDir;
	}



	public String getISortColumn() {
		return iSortColumn;
	}



	public void setISortColumn(String iSortColumn) {
		this.iSortColumn = iSortColumn;
	}



	public void setiSortDir(String iSortDir) {
		this.iSortDir = iSortDir;
	}



	public Integer getPage() {
		return page;
	}



	public void setPage(Integer page) {
		this.page = page;
	}



	public Integer getSize() {
		return size;
	}



	public void setSize(Integer size) {
		this.size = size;
	}



	String iSortDir;
	Integer page;
	Integer size;
	Set<SearchColumn> searchColumns;
	
	
	public Set<SearchColumn> getSearchColumns() {
		return searchColumns;
	}



	public void setSearchColumns(Set<SearchColumn> searchColumns) {
		this.searchColumns = searchColumns;
	}


	public class SearchColumn{
		
		String columnName;
		String type;
		String operater;
		public String getOperater() {
			return operater;
		}
		public void setOperater(String operater) {
			this.operater = operater;
		}
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		public String getColumnName() {
			return columnName;
		}
		public void setColumnName(String columnName) {
			this.columnName = columnName;
		}
		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
		String value;
		
	}
	
}
