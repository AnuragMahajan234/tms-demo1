package org.yash.rms.excel;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import org.yash.rms.report.dto.TimeSheetDetailReport;
import org.yash.rms.util.Constants;

public class TimeSheetDetailReportExcel extends AbstractExcelView {

	String[] headerNames = { "S.No.", "Employee Id", "Employee Name", "Employee EmailId", "Reporting Manager",
			"Location", "Project", "Business Unit" };

	private static final String SHEET_NAME_TIMESHEET_DETAIL_REPORT = "TimeSheetNotFilledDetailReport";

	@Override
	protected void buildExcelDocument(Map model, HSSFWorkbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		logger.debug("Started buildExcelDocument in TimeSheetDetailReportExcel Class  ");
		response.setHeader("Content-Disposition", "attachment; filename=TimeSheet_Detail_Report.xls");

		try {
			HSSFSheet sheet = ExcelUtility.getHSSFSheet(workbook, SHEET_NAME_TIMESHEET_DETAIL_REPORT);

			@SuppressWarnings("unused")
			Date endDate = (Date) (model.get("endDate"));
			Date startDate = (Date) (model.get("startDate"));

			setHeaderValuesInExcel(workbook, sheet, endDate, startDate);

			HSSFCellStyle style = ExcelUtility.getHSSFCellStyleForDataCell(workbook);
			style.setFillForegroundColor(IndexedColors.GOLD.getIndex());

			List<TimeSheetDetailReport> timeSheetDetailReport = (List<TimeSheetDetailReport>) model
					.get("TimeSheetDetailReportList");
			setExcelDataForRows(sheet, timeSheetDetailReport, style, workbook);

			workbook.write(response.getOutputStream());

		} catch (IOException e) {
			e.printStackTrace();
			logger.debug(e.getMessage());
			logger.debug("----------Inside TimeSheetDetailReportExcel End buildExcelDocument-----------");
		}

		System.out.println("Excel create successfully ----------------------------------------");
	}

	private void setHeaderValuesInExcel(HSSFWorkbook workbook, HSSFSheet sheet, Date endDate, Date startDate) {

		HSSFCellStyle xssfCellStyleForDataCellResource = ExcelUtility.getHSSFCellStyleForDataCell(workbook);
		xssfCellStyleForDataCellResource.setFillForegroundColor(IndexedColors.GOLD.getIndex());
		xssfCellStyleForDataCellResource.setBorderLeft(HSSFCellStyle.NO_FILL);
		xssfCellStyleForDataCellResource.setBorderBottom(HSSFCellStyle.NO_FILL);
		xssfCellStyleForDataCellResource.setBorderTop(HSSFCellStyle.NO_FILL);
		xssfCellStyleForDataCellResource.setBorderRight(HSSFCellStyle.NO_FILL);

		HSSFRow firstRow = sheet.createRow((short) 0);
		firstRow.setRowStyle(xssfCellStyleForDataCellResource);
		sheet.addMergedRegion(CellRangeAddress.valueOf("A1:H1"));

		HSSFRow secondRow = sheet.createRow((short) 1);

		ExcelUtility.getHSSFCellForData(secondRow, 0, xssfCellStyleForDataCellResource, new Date().toString());
		sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 37));

		HSSFRow two = sheet.createRow((short) 2);
		two.setRowStyle(xssfCellStyleForDataCellResource);
		two.createCell(3);

		ExcelUtility.getHSSFCellForData(two, 3, xssfCellStyleForDataCellResource,
				"Timesheet Compliance for Weekend "	+ Constants.DATE_FORMAT_REPORTS.format(endDate));

		sheet.addMergedRegion(new CellRangeAddress(2, 2, 3, 37));

		HSSFRow thrd = sheet.createRow((short) 3);
		thrd.setRowStyle(xssfCellStyleForDataCellResource);
		ExcelUtility.getHSSFCellForData(thrd, 4, xssfCellStyleForDataCellResource, "");
		sheet.addMergedRegion(new CellRangeAddress(3, 3, 4, 37));

		HSSFRow four = sheet.createRow((short) 4);
		HSSFCellStyle styleChange = ExcelUtility.getHSSFCellStyleHeader(workbook);
		four.setRowStyle(styleChange);

		HSSFCellStyle styleChange1 = ExcelUtility.getHSSFCellStyleHeader(workbook);
		styleChange1.setFillBackgroundColor(IndexedColors.PINK.getIndex());

	}

	public void setExcelDataForRows(HSSFSheet sheet, List<TimeSheetDetailReport> timeSheetDetailReportList,
			HSSFCellStyle xssfCellStyleForDataCell, HSSFWorkbook workbook) {

		Cell cell = null;
		// Cell style for header row
		CellStyle cs = workbook.createCellStyle();
		cs.setFillForegroundColor(IndexedColors.GOLD.getIndex());
		cs.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
		Font font = workbook.createFont();
		font.setFontHeightInPoints((short) 10);// Create font
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		cs.setFont(font);

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
		cellStyleForResource.setFillForegroundColor(IndexedColors.TAN.getIndex());
		cellStyleForResource.setFillPattern(XSSFCellStyle.BORDER_THIN);
		cellStyleForResource.setAlignment(XSSFCellStyle.ALIGN_LEFT);

		cellStyleForResource.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		cellStyleForResource.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		cellStyleForResource.setBorderRight(HSSFCellStyle.BORDER_THIN);
		cellStyleForResource.setBorderTop(HSSFCellStyle.BORDER_THIN);
		cellStyleForResource.setFont(font);

		int rowIndex = 4;
		int columnIndex = 0;

		HSSFRow row = sheet.createRow(rowIndex);
		row.setRowStyle(cellStyle);
		ExcelUtility.createHeaders(row, xssfCellStyleForDataCell, font, headerNames);

		int sNo = 1;
		for (TimeSheetDetailReport detailReport : timeSheetDetailReportList) {
			columnIndex = 0;

			Row resourceSummaryTimeSheetRow = sheet.createRow(sheet.getLastRowNum() + 1);
			cell = resourceSummaryTimeSheetRow.createCell(columnIndex);
			cell.setCellValue(sNo);
			cell.setCellStyle(cellStyle);
			//sheet.autoSizeColumn((short) 0);
			columnIndex++;

			cell = resourceSummaryTimeSheetRow.createCell(columnIndex);
			cell.setCellValue(detailReport.getEmpId());
			cell.setCellStyle(cellStyle);
			//sheet.autoSizeColumn((short) 2);
			columnIndex++;

			cell = resourceSummaryTimeSheetRow.createCell(columnIndex);
			cell.setCellValue(detailReport.getName());
			cell.setCellStyle(cellStyle);
			//sheet.autoSizeColumn((short) 2);
			columnIndex++;

			cell = resourceSummaryTimeSheetRow.createCell(columnIndex);
			cell.setCellValue(detailReport.getEmailId());
			cell.setCellStyle(cellStyle);
			//sheet.autoSizeColumn((short) 3);
			columnIndex++;

			cell = resourceSummaryTimeSheetRow.createCell(columnIndex);
			cell.setCellValue(detailReport.getReportingMgr());
			cell.setCellStyle(cellStyle);
			//sheet.autoSizeColumn((short) 2);
			columnIndex++;

			cell = resourceSummaryTimeSheetRow.createCell(columnIndex);
			cell.setCellValue(detailReport.getLocation());
			cell.setCellStyle(cellStyle);
			//sheet.autoSizeColumn((short) 2);
			columnIndex++;

			cell = resourceSummaryTimeSheetRow.createCell(columnIndex);
			cell.setCellValue(detailReport.getProject());
			cell.setCellStyle(cellStyle);
			//sheet.autoSizeColumn((short) 2);
			columnIndex++;

			cell = resourceSummaryTimeSheetRow.createCell(columnIndex);
			cell.setCellValue(detailReport.getBusinessUnit());
			cell.setCellStyle(cellStyle);
			//sheet.autoSizeColumn((short) 2);
			columnIndex++;

			sNo++;
		}

		logger.debug("End buildExcelDocument in TimeSheetExcelView Class  ");

	}
}
