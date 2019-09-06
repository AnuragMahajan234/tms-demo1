package org.yash.rms.excel;

import java.lang.reflect.Field;

public class ObjectType {
	
	public ObjectType(String excelColumn, Field fieldName) {
		super();
		this.fieldName = fieldName;
		this.excelColumn = excelColumn;
	}
	
	public ObjectType(Field fieldName, String excelColumn, String mapTo) {
		super();
		this.fieldName = fieldName;
		this.excelColumn = excelColumn;
		this.mapTo = mapTo;
	}

	public ObjectType(Field fieldName) {
		super();
		this.fieldName = fieldName;
	}

	public ObjectType() {
		super();
	}

	public ObjectType(String excelColumn) {
		super();
		this.excelColumn = excelColumn;
	}

	private Field fieldName;
	private String excelColumn;
	private String mapTo;
	
	public String getMapTo() {
		return mapTo;
	}

	public void setMapTo(String mapTo) {
		this.mapTo = mapTo;
	}

	public String getExcelColumn() {
		return excelColumn;
	}

	public void setExcelColumn(String excelColumn) {
		this.excelColumn = excelColumn;
	}

	public Field getFieldName() {
		return fieldName;
	}
	public void setFieldName(Field fieldName) {
		this.fieldName = fieldName;
	}
	
	

}
