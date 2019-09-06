package org.yash.rms.excel;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.poi.ss.usermodel.DateUtil;

public class Cell {
	private static final String DISPLAY_DATE_FORMAT = "dd-MM-yyyy";
	
	public static final String CELL_TYPE_NUMERIC = "n";
	public static final String CELL_TYPE_BOOLEAN = "b";
	public static final String CELL_TYPE_STRING = "inlineStr";
	public static final String CELL_TYPE_SSTINDEX = "s";
	public static final String CELL_TYPE_FORMULA = "str";
	public static final String CELL_TYPE_ERROR = "e";
	public static final String CELL_TYPE_BLANK = "";
	
	Object cellValue;
	String cellType;
	Integer columnIndex;
	
	public Object getCellValue() {
		return cellValue;
	}
	public void setCellValue(Object cellValue) {
		this.cellValue = cellValue;
	}
	public String getCellType() {
		return cellType;
	}
	public void setCellType(String cellType) {
		this.cellType = cellType;
	}
	
	public Integer getColumnIndex() {
		return columnIndex;
	}
	public void setColumnIndex(Integer columnIndex) {
		this.columnIndex = columnIndex;
	}
	public String getStringCellValue(){
		return (String)this.cellValue;
	}
	
	public double getNumericCellValue(){
		return getDouble(this.cellValue);
	}
	
	public Date getDateCellValue(){
		return getDate(this.cellValue);
	}
	
	public boolean getBooleanCellValue(){
		return getBoolean(this.cellValue);
	}
	
	@Override
	public String toString() {
		return this.cellValue == null ? null : this.cellValue.toString();
	}
	
	private Date getDate(Object cellValue) {
		if (cellValue == null || cellValue.toString().isEmpty()) {
			return null;
		}
		Date dateValue = null;
		if(cellValue instanceof String){
			dateValue = stringToDate((String)cellValue);
		} else if(cellValue instanceof Double){
			if(DateUtil.isValidExcelDate((Double)cellValue)){
				dateValue = DateUtil.getJavaDate((Double)cellValue);
			}
		}
		return dateValue;
	}
	
	private Date stringToDate(String cellValue) {
		if (cellValue == null || cellValue.isEmpty()) {
			return null;
		}
		Date dateValue = null;
		try {
			dateValue = new SimpleDateFormat(DISPLAY_DATE_FORMAT).parse(cellValue);
		} catch (ParseException e) {
			//e.printStackTrace();
		}
		return dateValue;
	}
	
	private Boolean getBoolean(Object cellValue){
		if (cellValue == null || cellValue.toString().isEmpty()) {
			return null;
		}
		Boolean booleanValue = null;
		try{
			booleanValue = ((Boolean)cellValue).booleanValue();
		} catch(ClassCastException e){
			//e.printStackTrace();
			if(cellValue instanceof String){
				booleanValue = stringToBoolean((String)cellValue);
			}
		}
		return booleanValue;
	}
	
	private Boolean stringToBoolean(String cellValue) {
		if (cellValue == null || cellValue.isEmpty()) {
			return null;
		}
		return cellValue.trim() == "true" ? true : false;
	}
	
	private Double getDouble(Object cellValue){
		if(cellValue == null || cellValue.toString().isEmpty()){
			return null;
		}
		Double doubleValue = null;
		try{
			doubleValue = ((Double)cellValue).doubleValue();
		} catch(ClassCastException e){
			//e.printStackTrace();
			if(cellValue instanceof String){
				doubleValue = stringToDouble((String)cellValue);
			}
		}
		return doubleValue;
	}
	
	private Double stringToDouble(String cellValue) {
		if (cellValue == null || cellValue.isEmpty()) {
			return null;
		}
		Double doubleValue = null;
		try{
			doubleValue = Double.valueOf(cellValue);
		}		
		catch (NumberFormatException e) {
			//e.printStackTrace();
		}
		return doubleValue;
	}
}	