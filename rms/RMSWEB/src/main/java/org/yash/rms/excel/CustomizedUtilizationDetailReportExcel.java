package org.yash.rms.excel;

import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.springframework.web.servlet.view.document.AbstractExcelView;
import org.yash.rms.report.dto.DayData;
import org.yash.rms.report.dto.WeeklyData;
import org.yash.rms.util.Constants;
import org.yash.rms.util.DateUtil;

public class CustomizedUtilizationDetailReportExcel extends AbstractExcelView {

	String[] headerNames = { "WEEK" };

	private static final String SHEET_NAME_UTILIZATION_DETAIL_REPORT = "UtilizationDetailReport_ROLE";

	@SuppressWarnings("unchecked,unused")
	@Override
	protected void buildExcelDocument(Map model, HSSFWorkbook workbook,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		logger.debug("Started buildExcelDocument in UtilizationDetailReportExcel Class  ");
		response.setHeader("Content-Disposition",
				"attachment; filename=Utilization_Detail_Report.xls");

		try {
			HSSFSheet sheet = ExcelUtility.getHSSFSheet(workbook,
					SHEET_NAME_UTILIZATION_DETAIL_REPORT);

			Date startDate = (Date) (model.get("startDate"));

			Date endDate = (Date) (model.get("endDate"));

			HSSFCellStyle style = ExcelUtility
					.getHSSFCellStyleForDataCell(workbook);
			style.setFillForegroundColor(IndexedColors.GOLD.getIndex());

			TreeMap<String, List<WeeklyData>> utilizationDetailReport = (TreeMap<String, List<WeeklyData>>) model
					.get("UtilizationDetailReportList");

			setHeaderValuesInExcel(workbook, sheet, startDate, endDate,
					utilizationDetailReport);
			setExcelDataForRows(sheet, utilizationDetailReport, style,
					workbook, startDate, endDate);

			workbook.write(response.getOutputStream());

		} catch (IOException e) {
			e.printStackTrace();
			logger.debug(e.getMessage());
			logger.debug("----------Inside UtilizationDetailReportExcel End buildExcelDocument-----------");
		}

		logger.debug("Excel create successfully ----------------------------------------");

	}

	@SuppressWarnings("deprecation")
	private void setHeaderValuesInExcel(HSSFWorkbook workbook, HSSFSheet sheet,
			Date startDate, Date endDate,
			TreeMap<String, List<WeeklyData>> utilizationDetailReport) {

		HSSFCellStyle xssfCellStyleForDataCellResource = ExcelUtility
				.getHSSFCellStyleForDataCell(workbook);
		xssfCellStyleForDataCellResource
				.setFillForegroundColor(IndexedColors.GOLD.getIndex());
		xssfCellStyleForDataCellResource.setBorderLeft(HSSFCellStyle.NO_FILL);
		xssfCellStyleForDataCellResource.setBorderBottom(HSSFCellStyle.NO_FILL);
		xssfCellStyleForDataCellResource.setBorderTop(HSSFCellStyle.NO_FILL);
		xssfCellStyleForDataCellResource.setBorderRight(HSSFCellStyle.NO_FILL);

		HSSFRow firstRow = sheet.createRow((short) 0);
		firstRow.setRowStyle(xssfCellStyleForDataCellResource);
		sheet.addMergedRegion(CellRangeAddress.valueOf("A1:AH1"));

		HSSFRow secondRow = sheet.createRow((short) 1);

		ExcelUtility.getHSSFCellForData(secondRow, 0,
				xssfCellStyleForDataCellResource, new Date().toString());
		sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 37));

		HSSFRow two = sheet.createRow((short) 2);
		two.setRowStyle(xssfCellStyleForDataCellResource);
		// two.createCell(4);

		ExcelUtility.getHSSFCellForData(
				two,
				2,
				xssfCellStyleForDataCellResource,
				"Start Date  "
						+ Constants.DATE_FORMAT_REPORTS.format(startDate)
						+ "  End Date  "
						+ Constants.DATE_FORMAT_REPORTS.format(endDate));

		sheet.addMergedRegion(new CellRangeAddress(2, 2, 4, 37));

		HSSFRow thrd = sheet.createRow((short) 3);
		thrd.setRowStyle(xssfCellStyleForDataCellResource);
		ExcelUtility.getHSSFCellForData(thrd, 2,
				xssfCellStyleForDataCellResource, "");
		sheet.addMergedRegion(new CellRangeAddress(3, 3, 4, 37));

		HSSFCellStyle styleChange1 = ExcelUtility
				.getHSSFCellStyleHeader(workbook);
		styleChange1.setFillBackgroundColor(IndexedColors.PINK.getIndex());

	}

	@SuppressWarnings({ "rawtypes", "unchecked", "unused" })
	public void setExcelDataForRows(HSSFSheet sheet,
			TreeMap<String, List<WeeklyData>> utilizationDetailReportList,
			HSSFCellStyle xssfCellStyleForDataCell, HSSFWorkbook workbook,
			Date startDate, Date endDate) {

		Cell cell = null;
		CellStyle cs = workbook.createCellStyle();
		// cs.setFillForegroundColor(IndexedColors.GOLD.getIndex());
		// cs.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
		Font font = workbook.createFont();
		// font.setFontHeightInPoints((short) 10);// Create font
		// font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		// cs.setFont(font);

		// Cell style for weekly data
		CellStyle cellStyle = workbook.createCellStyle();
		font = workbook.createFont();
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		font.setFontHeightInPoints((short) 10);
		cellStyle.setFillForegroundColor(IndexedColors.TAN.getIndex());
		cellStyle.setFillPattern(XSSFCellStyle.BORDER_THIN);
		cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);

		cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		cellStyle.setFont(font);

		CellStyle cellStyleForResource = workbook.createCellStyle();
		font = workbook.createFont();
		font.setBoldweight(Font.BOLDWEIGHT_NORMAL);
		font.setFontHeightInPoints((short) 10);
		cellStyleForResource.setFillForegroundColor(IndexedColors.TAN
				.getIndex());
		cellStyleForResource.setFillPattern(XSSFCellStyle.BORDER_THIN);
		cellStyleForResource.setAlignment(XSSFCellStyle.ALIGN_LEFT);

		cellStyleForResource.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		cellStyleForResource.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		cellStyleForResource.setBorderRight(HSSFCellStyle.BORDER_THIN);
		cellStyleForResource.setBorderTop(HSSFCellStyle.BORDER_THIN);
		cellStyleForResource.setFont(font);

		int rowIndex = 4;
		int columnIndex = 1;

		HSSFRow row = sheet.createRow(rowIndex);
		row.setRowStyle(cellStyle);

		ExcelUtility.createHeaders(row, xssfCellStyleForDataCell, font,
				headerNames);

		Iterator iterateUtilityReport = utilizationDetailReportList.entrySet()
				.iterator();

		int rowvalue = 2;
		for (Entry<String, List<WeeklyData>> itr : utilizationDetailReportList
				.entrySet()) {
			createHSSFCellForHeaders((short) columnIndex, row,
					xssfCellStyleForDataCell, font, itr.getKey(), true);
			columnIndex++;
		}

		Calendar c = Calendar.getInstance();
		c.setTime(startDate);
		c.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
		startDate = c.getTime();

		String weekstartdate = new SimpleDateFormat("yyyy-MM-dd")
				.format(startDate);
		String weekenddate = new SimpleDateFormat("yyyy-MM-dd").format(endDate);

		LocalDate start = LocalDate.parse(weekstartdate);
		LocalDate end = LocalDate.parse(weekenddate);
		LocalDate next = start.minusDays(7);

		DateTimeFormatter formatters = DateTimeFormatter
				.ofPattern("dd-MMM-yyyy");

		while ((next = next.plusDays(7)).isBefore(end.plusDays(7))) {
			String text = next.format(formatters);
			Row dayI = sheet.createRow(sheet.getLastRowNum() + 1);
			cell = dayI.createCell(0);
			cell.setCellValue(text);
			cell.setCellStyle(cellStyle);
			//sheet.autoSizeColumn((short) 2);
		}

		columnIndex = 1;
		for (Entry<String, List<WeeklyData>> itr : utilizationDetailReportList
				.entrySet()) {
			rowIndex = 5;
			TreeMap<java.sql.Date, WeeklyData> weeklyListData = (TreeMap) itr
					.getValue();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			boolean isMatched = false;
			next = start.minusDays(7);
			while ((next = next.plusDays(7)).isBefore(end.plusDays(7))) {
				Row dayI = sheet.getRow(rowIndex);
				cell = dayI.getCell(columnIndex);
				if (cell == null) {
					cell = dayI.createCell(columnIndex);
				}
				isMatched = false;

				for (Map.Entry<java.sql.Date, WeeklyData> entry : weeklyListData
						.entrySet()) {
					if (entry.getKey() != null
							&& dateFormat.format(entry.getKey())
									.equalsIgnoreCase(next.toString())) {
						if (entry.getValue() != null) {
							cell.setCellValue(((WeeklyData) entry.getValue())
									.getProductiveHours());
							isMatched = true;
							break;
						}
					}
				}

				if (!isMatched) {
					cell.setCellValue(0);
				}
				cell.setCellStyle(cellStyle);
				//sheet.autoSizeColumn((short) 2);
				rowIndex++;
			}
			columnIndex++;
		}

		HSSFRow extrarow = sheet.createRow(sheet.getLastRowNum() + 1);
		extrarow.setRowStyle(cellStyle);

		Row total = sheet.createRow(sheet.getLastRowNum() + 1);
		cs.setAlignment(XSSFCellStyle.ALIGN_CENTER);
		cs.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		cs.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		cs.setBorderRight(HSSFCellStyle.BORDER_THIN);
		cs.setBorderTop(HSSFCellStyle.BORDER_THIN);
		total.setRowStyle(cs);
		columnIndex = 0;

		logger.debug("End buildExcelDocument in UtilizationExcelView Class  ");
	}

	private HSSFCell createHSSFCellForHeaders(short s, HSSFRow row,
			HSSFCellStyle style, Font font, String value, boolean isSubtable) {
		int index = s;
		HSSFCell cell = row.createCell(index);

		if (isSubtable) {
			style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
			style.setBorderLeft(XSSFCellStyle.BORDER_THIN);
			style.setBorderRight(XSSFCellStyle.BORDER_THIN);
			style.setBorderTop(XSSFCellStyle.BORDER_THIN);
			style.setBorderBottom(XSSFCellStyle.BORDER_THIN);
		} else {
			font.setBoldweight(Font.BOLDWEIGHT_BOLD);
			font.setItalic(true);
			style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT
					.getIndex());
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

}
