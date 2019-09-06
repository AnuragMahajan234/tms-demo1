package org.yash.rms.excel;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.springframework.web.servlet.view.document.AbstractExcelView;
import org.yash.rms.report.dto.DayData;
import org.yash.rms.report.dto.WeeklyData;
import org.yash.rms.util.Constants;

public class UtilizationDetailReportExcel extends AbstractExcelView {

	String[] headerNames = { "Employee name", "Week End Date", "Billed Hours",
			"Planned Hours", "Total Actual Hours", "Productive Hrs",
			"Non Productive Hrs", "Non Prodhours", "Utilization %",
			"Project Name", "Project Code", "Status", "Date", "Actual Hours",
			"Activity", "Module", "Comments" };

	String[] subHeaderNames = { "BU", "ALL", "PROJECT", "ALL", "Location",
			"ALL", "EMPLOYEE", "ALL" };

	private static final String SHEET_NAME_UTILIZATION_DETAIL_REPORT = "UtilizationDetailReport_ROLE";

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

			@SuppressWarnings("unused")
			Date startDate = (Date) (model.get("startDate"));

			@SuppressWarnings("unused")
			Date endDate = (Date) (model.get("endDate"));
			
			setHeaderValuesInExcel(workbook, sheet, startDate, endDate);

			HSSFCellStyle style = ExcelUtility.getHSSFCellStyleForDataCell(workbook);
			style.setFillForegroundColor(IndexedColors.GOLD.getIndex());
			
			TreeMap<String, List<WeeklyData>> utilizationDetailReport = (TreeMap<String, List<WeeklyData>>) model.get("UtilizationDetailReportList");
			setExcelDataForRows(sheet, utilizationDetailReport, style, workbook);

			workbook.write(response.getOutputStream());

		} catch (IOException e) {
			e.printStackTrace();
			logger.debug(e.getMessage());
			logger.debug("----------Inside UtilizationDetailReportExcel End buildExcelDocument-----------");
		}

		System.out
				.println("Excel create successfully ----------------------------------------");

	}

	private void setHeaderValuesInExcel(HSSFWorkbook workbook, HSSFSheet sheet, Date startDate, Date endDate) {

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
		two.createCell(3);

		ExcelUtility.getHSSFCellForData(two, 3, xssfCellStyleForDataCellResource, "Utilization Detail From  "+ Constants.DATE_FORMAT_REPORTS.format(startDate) + "  To  " + Constants.DATE_FORMAT_REPORTS.format(endDate));

		sheet.addMergedRegion(new CellRangeAddress(2, 2, 3, 37));

		HSSFRow thrd = sheet.createRow((short) 3);
		thrd.setRowStyle(xssfCellStyleForDataCellResource);
		ExcelUtility.getHSSFCellForData(thrd, 4,
				xssfCellStyleForDataCellResource, "");
		sheet.addMergedRegion(new CellRangeAddress(3, 3, 4, 37));

		HSSFRow four = sheet.createRow((short) 4);
		HSSFCellStyle styleChange = ExcelUtility
				.getHSSFCellStyleHeader(workbook);
		four.setRowStyle(styleChange);

		HSSFCellStyle styleChange1 = ExcelUtility
				.getHSSFCellStyleHeader(workbook);
		styleChange1.setFillBackgroundColor(IndexedColors.PINK.getIndex());

		ExcelUtility.getHSSFCellForData(four, 0, styleChange1, "BU");
		ExcelUtility.getHSSFCellForData(four, 1, styleChange, "All");
		ExcelUtility.getHSSFCellForData(four, 2, styleChange1, "PROJECT");
		ExcelUtility.getHSSFCellForData(four, 3, styleChange, "All");
		ExcelUtility.getHSSFCellForData(four, 4, styleChange1, "LOCATION");
		ExcelUtility.getHSSFCellForData(four, 5, styleChange, "All");
		ExcelUtility.getHSSFCellForData(four, 6, styleChange1, "EMPLOYEE");
		ExcelUtility.getHSSFCellForData(four, 7, styleChange, "All");

		HSSFRow five = sheet.createRow((short) 5);
		five.setRowStyle(styleChange);
		ExcelUtility.getHSSFCellForData(five, 0, styleChange,
				"Utilization % = (Billed hours / Productive hours) * 100");
		sheet.addMergedRegion(new CellRangeAddress(5, 5, 0, 2));
		sheet.addMergedRegion(new CellRangeAddress(5, 5, 2, 37));

		HSSFRow six = sheet.createRow((short) 6);
		six.setRowStyle(styleChange);
		ExcelUtility
				.getHSSFCellForData(six, 0, styleChange,
						"Status:  P = Pending Approval, A = Approved, N = Not Submitted, R = Rejected");
		sheet.addMergedRegion(new CellRangeAddress(6, 6, 0, 3));
		sheet.addMergedRegion(new CellRangeAddress(6, 6, 3, 37));

		HSSFRow seven = sheet.createRow((short) 7);
		seven.setRowStyle(xssfCellStyleForDataCellResource);
		sheet.addMergedRegion(new CellRangeAddress(7, 7, 0, 4));
		ExcelUtility.getHSSFCellForData(seven, 5,
				xssfCellStyleForDataCellResource, "Actual Hours");
		sheet.addMergedRegion(new CellRangeAddress(7, 7, 6, 37));

	}

	public void setExcelDataForRows(HSSFSheet sheet,
			TreeMap<String, List<WeeklyData>> utilizationDetailReportList,
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
		

		int rowIndex = 8;
		int columnIndex = 0;

		HSSFRow row = sheet.createRow(rowIndex);
		row.setRowStyle(cellStyle);
		ExcelUtility.createHeaders(row, xssfCellStyleForDataCell, font,
				headerNames);

		Iterator iterateUtilityReport = utilizationDetailReportList.entrySet()
				.iterator();
		double grandtotalPlannedHours = 0.0;
		double grandtotalBilledHours = 0.0;
		double grandtotalActualHours = 0.0;
		double grandtotalProductiveHours = 0.0;
		double grandtotalNonProductiveHours = 0.0;
		while (iterateUtilityReport.hasNext()) {
			Map.Entry resourceUtilizationDetail = (Map.Entry) iterateUtilityReport
					.next();

			 TreeMap<Date, WeeklyData> weeklyDataList = ( TreeMap<Date, WeeklyData>) resourceUtilizationDetail
					.getValue();
			columnIndex = 0;
			
			Iterator iterateWeeklyData = weeklyDataList.entrySet().iterator();
						
			Row resourceSummaryUtilizationRow = sheet.createRow(sheet
					.getLastRowNum() + 1);
			
			resourceSummaryUtilizationRow.setRowStyle(cellStyleForResource);
			cell = resourceSummaryUtilizationRow.createCell(columnIndex);
			cell.setCellValue((String) resourceUtilizationDetail.getKey());
			
			cell.setCellStyle(cellStyleForResource);
			//sheet.autoSizeColumn((short)2);
			columnIndex++;
			int noOfWeek = weeklyDataList.size();

			rowIndex = sheet.getLastRowNum() + 1;

			int initialWeekIndex = rowIndex;
	
			double totalPlannedHours = 0.0;
			double totalBilledHours = 0.0;
			double totalActualHours = 0.0;
			double totalProductiveHours = 0.0;
			double totalNonProductiveHours = 0.0;
			String totalUtilizationPercentage = "0.00";

			String endRange ="";
			String startRange="";
			int noOfweekCount=0;
			while (iterateWeeklyData.hasNext()) {
				
				Map.Entry weeklyDetail = (Map.Entry) iterateWeeklyData.next();
				WeeklyData weeklyData= (WeeklyData) weeklyDetail.getValue();
				
				if(noOfweekCount==0){
					startRange = weeklyData.getWeekEndDate()
								.toString();
								noOfweekCount++;
								
				}
				endRange = weeklyData.getWeekEndDate().toString();
				
				int rowno = rowIndex;
				
				int k = 0;
				List<DayData> dayDataList = weeklyData.getDayData();
				double actualHours=0.0;
				String utilizationPectWeekly = "0.00";
				int firstrowofWeekIndex= sheet.getLastRowNum()+1;
				for(DayData dayData: dayDataList ){
					
					Row dayIndex = sheet.createRow(sheet.getLastRowNum() + 1);
					//sheet.autoSizeColumn((short)2);
					dayIndex.setRowStyle(cellStyle);
					
						if (k == 0) {

						if (weeklyData.getProductiveHours() > 0) {
							double utilizationWeeklypct = weeklyData.getBilledHours()
									/ weeklyData.getProductiveHours() * 100;
							DecimalFormat df = new DecimalFormat("####0.00");
							utilizationPectWeekly = df.format(utilizationWeeklypct) +"%";
						
						}
						else{
							utilizationPectWeekly="N.A";
						}

						totalBilledHours += weeklyData.getBilledHours();
						totalPlannedHours += weeklyData.getPlannedHours();
						totalProductiveHours += weeklyData.getProductiveHours();
						totalNonProductiveHours += weeklyData
								.getNonProductiveHours();

						columnIndex = 1;
						cell = dayIndex.createCell(columnIndex);
						cell.setCellValue(weeklyData.getWeekEndDate()
								.toString());
						cell.setCellStyle(cellStyle);
						//sheet.autoSizeColumn((short)2);
						columnIndex++;

						cell = dayIndex.createCell(columnIndex);
						cell.setCellValue(weeklyData.getBilledHours());
						cell.setCellStyle(cellStyle);
						//sheet.autoSizeColumn((short)2);
						columnIndex++;

						cell = dayIndex.createCell(columnIndex);
						cell.setCellValue(weeklyData.getPlannedHours());
						cell.setCellStyle(cellStyle);
						//sheet.autoSizeColumn((short)2);
						columnIndex++;

						cell = dayIndex.createCell(columnIndex);
						cell.setCellValue(actualHours);// total actual hours
						cell.setCellStyle(cellStyle);
						//sheet.autoSizeColumn((short)2);
						columnIndex++;

						cell = dayIndex.createCell(columnIndex);
						cell.setCellValue(weeklyData.getProductiveHours());
						cell.setCellStyle(cellStyle);
						//sheet.autoSizeColumn((short)2);
						columnIndex++;

						cell = dayIndex.createCell(columnIndex);
						cell.setCellValue(weeklyData.getNonProductiveHours());
						cell.setCellStyle(cellStyle);
						//sheet.autoSizeColumn((short)2);
						columnIndex++;

						cell = dayIndex.createCell(columnIndex);
						cell.setCellValue(0.0); // Non Prodhours Vacation/Holiday
						cell.setCellStyle(cellStyle);
						//sheet.autoSizeColumn((short)2);
						columnIndex++;

						cell = dayIndex.createCell(columnIndex);
						cell.setCellValue(utilizationPectWeekly);
						cell.setCellStyle(cellStyle);
						//sheet.autoSizeColumn((short)2);
						columnIndex++;

						cell = dayIndex.createCell(columnIndex);
						cell.setCellValue(weeklyData.getProjectName()); // Project Name
						cell.setCellStyle(cellStyle);
						//sheet.autoSizeColumn((short)2);
						columnIndex++;

						cell = dayIndex.createCell(columnIndex);
						cell.setCellValue(weeklyData.getProjectCode());// project code
						cell.setCellStyle(cellStyle);
						//sheet.autoSizeColumn((short)2);
						columnIndex++;

						cell = dayIndex.createCell(columnIndex);
						cell.setCellValue(weeklyData.getStatus().toString());// project
						cell.setCellStyle(cellStyle);
						//sheet.autoSizeColumn((short)2);
						k++;

					}
					actualHours += dayData.getActualHours();

					if(dayData.getProjectName()!=null){
						
						columnIndex=9;
						cell = dayIndex.createCell(columnIndex);
						cell.setCellValue(dayData.getProjectName());
						cell.setCellStyle(cellStyle);
						//sheet.autoSizeColumn((short)2);
						columnIndex++;
						
						if(dayData.getProjectCode()!=null){
						cell = dayIndex.createCell(columnIndex);
						cell.setCellValue(dayData.getProjectCode());
						cell.setCellStyle(cellStyle);
						//sheet.autoSizeColumn((short)2);
						columnIndex++;}
						
						if(dayData.getStatus()!=null){
						cell = dayIndex.createCell(columnIndex);
						cell.setCellValue(dayData.getStatus().toString());
						cell.setCellStyle(cellStyle);
						//sheet.autoSizeColumn((short)2);
						columnIndex++;
						}
						
						
					}
					
					
					
					
		
					columnIndex = 12;

					cell = dayIndex.createCell(columnIndex);
					cell.setCellValue(dayData.getDate());
					cell.setCellStyle(cellStyle);
					//sheet.autoSizeColumn((short)2);
					columnIndex++;

					cell = dayIndex.createCell(columnIndex);
					cell.setCellValue(dayData.getActualHours());
					cell.setCellStyle(cellStyle);
					//sheet.autoSizeColumn((short)2);
					columnIndex++;

					cell = dayIndex.createCell(columnIndex);
					cell.setCellValue(dayData.getActivity());
					cell.setCellStyle(cellStyle);
					//sheet.autoSizeColumn((short)2);
					columnIndex++;

					cell = dayIndex.createCell(columnIndex);
					cell.setCellValue(dayData.getModule());
					cell.setCellStyle(cellStyle);
					//sheet.autoSizeColumn((short)2);
					columnIndex++;

					cell = dayIndex.createCell(columnIndex);
					cell.setCellValue(dayData.getComment());
					cell.setCellStyle(cellStyle);
					//sheet.autoSizeColumn((short)2);

					//rowIndex = rowIndex + 1;
				}
				totalActualHours += actualHours;
				Row weekRow =sheet.getRow(firstrowofWeekIndex);
				cell = weekRow.createCell(4);
				cell.setCellValue(actualHours);// total actual hours
				cell.setCellStyle(cellStyle);
				//sheet.autoSizeColumn((short)2);
				
				HSSFRow extrarow =sheet.createRow(sheet.getLastRowNum() + 1);
				extrarow.setRowStyle(cellStyle);
				rowIndex = sheet.getLastRowNum() + 1;
				sheet.groupRow(rowno + 1, rowIndex - 1);
				sheet.setRowGroupCollapsed(rowno + 1, true);
			}
			// set total values
			grandtotalPlannedHours += totalPlannedHours;
			grandtotalBilledHours += totalBilledHours;
			grandtotalActualHours += totalActualHours;
			grandtotalProductiveHours += totalProductiveHours;
			grandtotalNonProductiveHours += totalNonProductiveHours;
			
			
			
			if (totalProductiveHours > 0) {
				double UtilizationPercentage = totalBilledHours
						/ totalProductiveHours * 100;
				DecimalFormat df = new DecimalFormat("####0.00");
				totalUtilizationPercentage= df.format(UtilizationPercentage) +"%";
			}
			else{
				totalUtilizationPercentage="N.A";
			}
			columnIndex = 1;
			cell = resourceSummaryUtilizationRow.createCell(columnIndex);
			cell.setCellValue(startRange+"-"+ endRange);
			cell.setCellStyle(cellStyle);
			//sheet.autoSizeColumn((short)2);
			columnIndex++;
			cell = resourceSummaryUtilizationRow.createCell(columnIndex);
			cell.setCellValue(totalBilledHours);
			cell.setCellStyle(cellStyle);
			//sheet.autoSizeColumn((short)2);
			columnIndex++;
			cell = resourceSummaryUtilizationRow.createCell(columnIndex);
			cell.setCellValue(totalPlannedHours);
			cell.setCellStyle(cellStyle);
			//sheet.autoSizeColumn((short)2);
			columnIndex++;
			cell = resourceSummaryUtilizationRow.createCell(columnIndex);
			cell.setCellValue(totalActualHours);
			cell.setCellStyle(cellStyle);
			//sheet.autoSizeColumn((short)2);
			columnIndex++;
			cell = resourceSummaryUtilizationRow.createCell(columnIndex);
			cell.setCellValue(totalProductiveHours);
			cell.setCellStyle(cellStyle);
			//sheet.autoSizeColumn((short)2);
			columnIndex++;
			cell = resourceSummaryUtilizationRow.createCell(columnIndex);
			cell.setCellValue(totalNonProductiveHours);
			cell.setCellStyle(cellStyle);
			//sheet.autoSizeColumn((short)2);
			columnIndex++;
			cell = resourceSummaryUtilizationRow.createCell(columnIndex);
			cell.setCellValue(0.0);// /Need this data********/
			cell.setCellStyle(cellStyle);
			//sheet.autoSizeColumn((short)2);
			columnIndex++;
			cell = resourceSummaryUtilizationRow.createCell(columnIndex);
			cell.setCellValue(totalUtilizationPercentage);
			cell.setCellStyle(cellStyle);
			//sheet.autoSizeColumn((short)2);

			HSSFRow extrarow =sheet.createRow(sheet.getLastRowNum() + 1);
			extrarow.setRowStyle(cellStyle);
			
			sheet.groupRow(initialWeekIndex, sheet.getLastRowNum() - 1);

			sheet.setRowGroupCollapsed(initialWeekIndex, true);

		}
		Row total = sheet.createRow(sheet.getLastRowNum() + 1);
		cs.setAlignment(XSSFCellStyle.ALIGN_CENTER);
		cs.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		cs.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		cs.setBorderRight(HSSFCellStyle.BORDER_THIN);
		cs.setBorderTop(HSSFCellStyle.BORDER_THIN);
		total.setRowStyle(cs);
		columnIndex = 0;
		cell = total.createCell(columnIndex);
		cell.setCellValue("Total");
		cell.setCellStyle(cs);
		//sheet.autoSizeColumn((short)2);
		columnIndex++;
		cell = total.createCell(columnIndex);
		cell.setCellValue("Total");
		cell.setCellStyle(cs);
		//sheet.autoSizeColumn((short)2);
		columnIndex++;
		cell = total.createCell(columnIndex);
		cell.setCellValue(grandtotalBilledHours);
		cell.setCellStyle(cs);
		//sheet.autoSizeColumn((short)2);
		columnIndex++;
		cell = total.createCell(columnIndex);
		cell.setCellValue(grandtotalPlannedHours);
		cell.setCellStyle(cs);
		//sheet.autoSizeColumn((short)2);
		columnIndex++;
		cell = total.createCell(columnIndex);
		cell.setCellValue(grandtotalActualHours);
		cell.setCellStyle(cs);
		//sheet.autoSizeColumn((short)2);
		columnIndex++;
		cell = total.createCell(columnIndex);
		cell.setCellValue(grandtotalProductiveHours);
		cell.setCellStyle(cs);
		//sheet.autoSizeColumn((short)2);
		columnIndex++;
		cell = total.createCell(columnIndex);
		cell.setCellValue(grandtotalNonProductiveHours);
		cell.setCellStyle(cs);
		//sheet.autoSizeColumn((short)2);
		columnIndex++;
		cell = total.createCell(columnIndex);
		cell.setCellValue(0.0);
		cell.setCellStyle(cs);
		//sheet.autoSizeColumn((short)2);

		logger.debug("End buildExcelDocument in UtilizationExcelView Class  ");
	}

}
