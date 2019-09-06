package org.yash.rms.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang.WordUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Workbook;

public class BlockedResourceExcelUtility {

	private static final String FONT_CALIBRI = "Calibri";
	
	/**
	 * This method create and Returns XSSFSheet Object.
	 * 
	 * @param wb
	 * @param sheetName
	 * @return XSSFSheet
	 */
	public static HSSFSheet getHSSFSheet(HSSFWorkbook wb, String sheetName) {

		HSSFSheet sheet = wb.createSheet(sheetName);
		sheet.setDefaultColumnWidth(20);
		return sheet;
	}
	
	/**
	 * This method creates and returns XSSFCell for data cells object.
	 * 
	 * @param row
	 * @param index
	 * @param style1CellStyle
	 * @param cellValue
	 * @return XSSFCell
	 */
	public static HSSFCell createHSSFCellForData(HSSFSheet excelSheet,HSSFRow row, int index, HSSFCellStyle styleCellStyle, Object cellValue) {
		HSSFCell cell = row.createCell(index);
		cell.setCellStyle(styleCellStyle);
		setCellValue(cell, cellValue);
		excelSheet.autoSizeColumn(cell.getColumnIndex());
		return cell;
	}
	
	/**
	 * This method takes XSSFRow, XSSFCellStyle, Font and Array of header Names
	 * and creates cells accordingly.
	 * 
	 * @param row
	 * @param style
	 * @param font
	 * @param headerNames
	 * @param isSubtable
	 */

	public static void createHeaders(HSSFRow row, HSSFCellStyle style, Font font, String[] headerNames, boolean isSubtable) {
		for ( int i=0; i <headerNames.length; i++) {
			createHSSFCellForHeaders((short) i, row, style, font, headerNames[i], isSubtable);
		}
	}
	
	/**
	 * This method takes XSSFRow, XSSFCellStyle, Font and Array of header Names
	 * and creates cells accordingly.
	 * 
	 * @param row
	 * @param style
	 * @param font
	 * @param headerNames
	 */

	public static void createHeaders(HSSFRow row, HSSFCellStyle style, Font font, String[] headerNames) {
		createHeaders(row,style,font,headerNames, false);
	}
	
	/**
	 * This method takes index, XSSFRow, XSSFCellStyle, Font and a string value
	 * and creates cell for data.
	 * 
	 * @param s
	 * @param row
	 * @param style
	 * @param font
	 * @param value
	 * @return Cell
	 */
	public static Cell createHSSFCellForHeaders(short s, HSSFRow row, HSSFCellStyle style, Font font, String value, boolean isSubtable) {
		int index = s;
		HSSFCell cell = row.createCell(index);
		
		if(isSubtable)
		{
			style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
			style.setBorderRight(HSSFCellStyle.BORDER_THIN);
			style.setBorderTop(HSSFCellStyle.BORDER_THIN);
			style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		}else{
			font.setBoldweight(Font.BOLDWEIGHT_BOLD);
			font.setItalic(true);
			style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
			style.setFont(font);
			style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			style.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
			style.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
			style.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
			style.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
		}
		
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setWrapText(true);
		cell.setCellStyle(style);
		
		cell.setCellValue(value);
		return cell;
	}
	
	/**
	 * This method returns HSSFCellStyle for Headers
	 * 
	 * @param wb
	 * @return HSSFCellStyle
	 */
	public static HSSFCellStyle getCellStyleForHeaders(HSSFWorkbook wb) {
		HSSFCellStyle cellStyle = wb.createCellStyle();

		HSSFFont font = wb.createFont();
		font.setFontName(FONT_CALIBRI);
		font.setFontHeightInPoints((short) 12);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		cellStyle.setFont(font);

		cellStyle.setFillBackgroundColor(IndexedColors.BLACK.getIndex());
		cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		cellStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
		cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		cellStyle.setWrapText(true);
		return cellStyle;
	}
	
	/**
	 * This method is used to create Header names
	 * 
	 * @param excelSheet
	 * @param style
	 * @param headerNames
	 * @param row
	 */
	public static void createHeadersForExcel(HSSFSheet excelSheet, HSSFCellStyle style, String[] headerNames) {

		HSSFRow row = excelSheet.createRow((short) 0);
		for (int i = 0; i < headerNames.length; i++) {
			Cell cell = row.createCell(i);
			cell.setCellValue(WordUtils.capitalizeFully(headerNames[i]));
			cell.setCellStyle(style);
    	    excelSheet.autoSizeColumn(cell.getColumnIndex());
		}
	}
	
	/**
	 * This method creates and returns XSSFCellStyle for Data Cells object.
	 * 
	 * @param wb
	 * @return XSSFCellStyle
	 */
	public static HSSFCellStyle getHSSFCellStyleForDataCell(HSSFWorkbook wb) {
		HSSFCellStyle styleCellStyle = wb.createCellStyle();

		Font font = wb.createFont();
		font.setFontName(FONT_CALIBRI);
		font.setFontHeightInPoints((short) 10);// Create font
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);// Make font bold
		styleCellStyle.setFont(font);// set it to bold

		HSSFPalette palette = wb.getCustomPalette();
		palette.setColorAtIndex(new Byte((byte) 41), new Byte((byte) 210), new Byte((byte) 180), new Byte((byte) 140));

		styleCellStyle.setFillBackgroundColor(IndexedColors.BLACK.getIndex());
		styleCellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		styleCellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		styleCellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		styleCellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		styleCellStyle.setFillForegroundColor(palette.getColor(9).getIndex());
		styleCellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		styleCellStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		styleCellStyle.setWrapText(true);
		return styleCellStyle;

	}
	
	public static void setCellValue (Cell cell, Object value) {
		value = ObjectUtils.defaultIfNull(value, "NA");
		if (value instanceof String) {
			cell.setCellValue((String) value);
		}
		else if (value instanceof Boolean) {
			cell.setCellValue((Boolean) value);
		}
		else if (value instanceof Double) {
			cell.setCellValue((Double) value);
		}
		else if (value instanceof Integer) {
			cell.setCellValue((Integer) value);
		}
		else if (value instanceof Long) {
			cell.setCellValue((Long) value);
		}
		else if (value instanceof Character) {
			cell.setCellValue((Character) value);
		}
		else if (value instanceof Float) {
			cell.setCellValue((Float) value);
		}
		else if (value instanceof Date) {
			cell.setCellValue((Date) value);
		}
		else if (value instanceof Timestamp) {
			SimpleDateFormat datetemp = new SimpleDateFormat(Constants.DATE_PATTERN_6);
			String cellValue = datetemp.format(value);
			cell.setCellValue(cellValue);
		}	
	}
	
	public static void putBlockedResourceDatainReportFile(HSSFWorkbook workbook, HSSFSheet sheet, List<Map<String, Object>> resourceList) {
		
		int rowNum = 1;
		int cellNum = 0;
		   
		for(int index = 0; index < resourceList.size(); index++) {
		   
		   Map<String, Object> resourceListMap = resourceList.get(index);
		   Set<Entry<String, Object>> entrySet = resourceListMap.entrySet();
		   HSSFRow row = sheet.createRow(rowNum++);
		  
		   for(Entry<String, Object> entry : entrySet) {

			   HSSFCellStyle dateCellStyle = BlockedResourceExcelUtility.getHSSFCellStyleForDataCell(workbook);
			   Object cellValue = entry.getValue();
			   BlockedResourceExcelUtility.createHSSFCellForData(sheet, row, cellNum++, dateCellStyle, cellValue);
			 }
		   
		   cellNum = 0;
		}
	}
	
  public static File writeOutputFile(Workbook workbook, String fileName, String fileExt) {
	  
	  File tempFile = null;
	  OutputStream outputStream = null;
		synchronized (BlockedResourceExcelUtility.class) {
			try {
				tempFile = GeneralFunction.createTempFile(fileName, fileExt);
				// Write the output to a file
				outputStream = new FileOutputStream(tempFile);
				workbook.write(outputStream);
			} 
			catch (Exception e) {
				e.printStackTrace();
			} 
			finally {
				if (outputStream != null)
					try {
						// Closing the workbook and outputStream
						outputStream.flush();
						outputStream.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
			}
		}
		return tempFile;
	}
  
}
