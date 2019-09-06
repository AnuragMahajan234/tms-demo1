package org.yash.rms.excel;

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
import org.apache.poi.xssf.usermodel.XSSFCellStyle;

/**
 * This class works as Utility to generate Excel File
 * 
 *
 */
public class ExcelUtility {

	private static final String FONT_CALIBRI = "Calibri";
	public static  String GREEN="green";
	public static  String RED="red";
	public static  String GREY="GREY";
	public static  String ORANGE="ORANGE";

	/**
	 * This method create and Returns XSSFSheet Object.
	 * 
	 * @param wb
	 * @param sheetName
	 * @return XSSFSheet
	 */
	public static HSSFSheet getHSSFSheet(HSSFWorkbook wb, String sheetName) {

		HSSFSheet sheet = wb.createSheet(sheetName);
		sheet.autoSizeColumn((short) 0);
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
	public static HSSFCell getHSSFCellForData(HSSFRow row, int index, HSSFCellStyle styleCellStyle, String cellValue) {
		HSSFCell cell = row.createCell(index);
		cell.setCellStyle(styleCellStyle);
		cell.setCellValue(cellValue);
		return cell;
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
	
	public static HSSFCellStyle getHSSFCellStyleForColouredDataCell(HSSFWorkbook wb) {
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
		styleCellStyle.setFillForegroundColor(palette.getColor(43).getIndex());
		styleCellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		styleCellStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		styleCellStyle.setWrapText(true);
		return styleCellStyle;

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
			style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
			style.setBorderLeft(XSSFCellStyle.BORDER_THIN);
			style.setBorderRight(XSSFCellStyle.BORDER_THIN);
			style.setBorderTop(XSSFCellStyle.BORDER_THIN);
			style.setBorderBottom(XSSFCellStyle.BORDER_THIN);
		}else{
			font.setBoldweight(Font.BOLDWEIGHT_BOLD);
			font.setItalic(true);
			style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
			style.setFont(font);
			style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
			style.setBorderLeft(XSSFCellStyle.BORDER_MEDIUM);
			style.setBorderRight(XSSFCellStyle.BORDER_MEDIUM);
			style.setBorderTop(XSSFCellStyle.BORDER_MEDIUM);
			style.setBorderBottom(XSSFCellStyle.BORDER_MEDIUM);
		}
		
		style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
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
	public static HSSFCellStyle getHeaderColorForCell(HSSFWorkbook wb, String color) {
		HSSFCellStyle cellStyle = wb.createCellStyle();

		HSSFFont font = wb.createFont();
		font.setFontName(FONT_CALIBRI);
		font.setFontHeightInPoints((short) 12);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		cellStyle.setFont(font);

		cellStyle.setFillBackgroundColor(IndexedColors.GREY_40_PERCENT.getIndex());
		cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		if(color.equalsIgnoreCase(ExcelUtility.GREY))
		cellStyle.setFillForegroundColor(HSSFColor.GREY_40_PERCENT.index);
		else if (color.equalsIgnoreCase(ExcelUtility.ORANGE))
			cellStyle.setFillForegroundColor(HSSFColor.LIGHT_ORANGE.index);
		cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		cellStyle.setWrapText(true);
		return cellStyle;
	}
	public static HSSFCellStyle getColorForCell(HSSFWorkbook wb, String color, String borderColor) {
		
		
		
		HSSFCellStyle styleCellStyle = wb.createCellStyle();

		Font font = wb.createFont();
		font.setFontName(FONT_CALIBRI);
		font.setFontHeightInPoints((short) 10);// Create font
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);// Make font bold
		styleCellStyle.setFont(font);// set it to bold

		//styleCellStyle.setFillBackgroundColor(IndexedColors.BLACK.getIndex());
		
		styleCellStyle.setBorderLeft(HSSFCellStyle.BORDER_THICK);
		styleCellStyle.setBorderBottom(HSSFCellStyle.BORDER_THICK);
		styleCellStyle.setBorderTop(HSSFCellStyle.BORDER_THICK);
		styleCellStyle.setBorderRight(HSSFCellStyle.BORDER_THICK);
		
		if(borderColor.equalsIgnoreCase(ExcelUtility.GREEN)){
		
		styleCellStyle.setBottomBorderColor(IndexedColors.BRIGHT_GREEN.getIndex());
		styleCellStyle.setTopBorderColor(IndexedColors.BRIGHT_GREEN.getIndex());
		styleCellStyle.setLeftBorderColor(IndexedColors.BRIGHT_GREEN.getIndex());
		styleCellStyle.setRightBorderColor(IndexedColors.BRIGHT_GREEN.getIndex());
		}
		else if(borderColor.equalsIgnoreCase(ExcelUtility.RED))
		{
			styleCellStyle.setBottomBorderColor(IndexedColors.DARK_RED.getIndex());
			styleCellStyle.setTopBorderColor(IndexedColors.DARK_RED.getIndex());
			styleCellStyle.setLeftBorderColor(IndexedColors.DARK_RED.getIndex());
			styleCellStyle.setRightBorderColor(IndexedColors.DARK_RED.getIndex());
			
		}
		else
		{
			styleCellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
			styleCellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			styleCellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
			styleCellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
			styleCellStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
			styleCellStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
			styleCellStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
			styleCellStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
		}
		if(color.equalsIgnoreCase(ExcelUtility.GREEN))
			styleCellStyle.setFillForegroundColor(HSSFColor.SEA_GREEN.index);
		else if (color.equalsIgnoreCase(ExcelUtility.RED))
			styleCellStyle.setFillForegroundColor(HSSFColor.RED.index);
		styleCellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		styleCellStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		styleCellStyle.setWrapText(true);
		return styleCellStyle;
	
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
			row.createCell(i).setCellValue(headerNames[i]);
			row.getCell(i).setCellStyle(style);
		}
	}
	
	public static HSSFCellStyle getHSSFCellStyleHeader(HSSFWorkbook wb) {
		HSSFCellStyle styleCellStyle = wb.createCellStyle();

		Font font = wb.createFont();
		font.setFontName(FONT_CALIBRI);
		font.setFontHeightInPoints((short) 7);// Create font
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);// Make font bold
		styleCellStyle.setFont(font);// set it to bold

		//HSSFPalette palette = wb.getCustomPalette();
		//palette.setColorAtIndex(new Byte((byte) 41), new Byte((byte) 210), new Byte((byte) 180), new Byte((byte) 140));

		//styleCellStyle.setFillBackgroundColor(IndexedColors.BLACK.getIndex());
		styleCellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		styleCellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		styleCellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		styleCellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		//styleCellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		styleCellStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		styleCellStyle.setWrapText(true);
		return styleCellStyle;

	}
}
