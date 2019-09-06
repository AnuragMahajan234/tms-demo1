package org.yash.rms.excel;

import java.awt.Color;
import java.awt.Font;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFPicture;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.view.document.AbstractExcelView;
import org.yash.rms.report.dto.ResourceMovementReport;
import org.yash.rms.report.dto.ResourceMovementReportGraphs;
import org.yash.rms.util.Constants;

public class ResourceMovementReportExcel extends AbstractExcelView {

	private static final Logger logger = LoggerFactory.getLogger(ResourceMovementReportExcel.class);

	private static final String SHEET_NAME_RESOURCE_MOVEMENT_REPORT = "Resource Movement All";
	private static final String SHEET_NAME_RESOURCE_MOVEMENT_CHART = "Resource Movement Chart";
	private static final String SHEET_NAME_RESOURCE_MOVEMENT_ANALYSIS = "Resource Movement Analysis";

	@Override
	protected void buildExcelDocument(Map model, HSSFWorkbook workbook, HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.debug("Started buildExcelDocument in RMReportExcelView Class  ");

		String[] headerNames = { "S.No.", "Employee ID", "Employee Name", "Emp. Parent BG-BU", "Allocation Date", "Project", "Allocation Type" };

		String[] headerNamesForAnalysis = { "S.No.", "Employee ID", "Employee Name", "Emp. Parent BG-BU", "Allocation Date", "Current Project", "Current Allocation Type", "Previous Allocation Type",
				"Comments", "Resource Type" };

		HSSFSheet excelSheet = ExcelUtility.getHSSFSheet(workbook, SHEET_NAME_RESOURCE_MOVEMENT_REPORT);
		HSSFSheet excelSheetAnalysis = ExcelUtility.getHSSFSheet(workbook, SHEET_NAME_RESOURCE_MOVEMENT_ANALYSIS);
		HSSFSheet excelSheetChart = ExcelUtility.getHSSFSheet(workbook, SHEET_NAME_RESOURCE_MOVEMENT_CHART);

		// Customize for particular column Width
		excelSheet.setColumnWidth(0, 2000);
		excelSheet.setColumnWidth(5, 7500);
		excelSheet.setColumnWidth(6, 15000);
		excelSheet.setColumnWidth(2, 7500);

		excelSheetAnalysis.setColumnWidth(0, 2000);
		excelSheetAnalysis.setColumnWidth(5, 7500);
		excelSheetAnalysis.setColumnWidth(6, 7500);
		excelSheetAnalysis.setColumnWidth(2, 7500);
		excelSheetAnalysis.setColumnWidth(7, 7500);
		excelSheetAnalysis.setColumnWidth(8, 8500);

		response.setHeader("Content-Disposition", "attachment; filename=Resource_Movement_Report.xls");

		HSSFCellStyle yellowCellStyle = ExcelUtility.getCellStyleForHeaders(workbook);

		ExcelUtility.createHeadersForExcel(excelSheet, yellowCellStyle, headerNames);
		ExcelUtility.createHeadersForExcel(excelSheetAnalysis, yellowCellStyle, headerNamesForAnalysis);

		@SuppressWarnings("unchecked")
		List<ResourceMovementReport> resMoveReportList = (List<ResourceMovementReport>) model.get("resourceMovementReportList");

		@SuppressWarnings("unchecked")
		ResourceMovementReportGraphs resourceMovementReportGraphs = (ResourceMovementReportGraphs) model.get("resourceMovementReportGraphs");

		@SuppressWarnings("unused")
		Date startDate = (Date) (model.get("startDate"));

		@SuppressWarnings("unused")
		Date endDate = (Date) (model.get("endDate"));

		HSSFCellStyle xssfCellStyleForDataCell = ExcelUtility.getHSSFCellStyleForDataCell(workbook);

		// Set data for first sheet
		setExcelDataForRows(excelSheet, resMoveReportList, xssfCellStyleForDataCell, workbook);

		// Set data for second sheet
		setExcelDataForAnalysisSheet(excelSheetAnalysis, resourceMovementReportGraphs.getResourceMovementAnalysedReportList(), xssfCellStyleForDataCell, workbook);

		// Set Chart Data
		// Create rows to set matrix data
		HSSFRow firstRow = excelSheetChart.createRow((short) 0);
		excelSheetChart.addMergedRegion(CellRangeAddress.valueOf("A1:M1"));
		HSSFCellStyle xssfCellStyleForDataCellGraph = ExcelUtility.getHSSFCellStyleForDataCell(workbook);
		xssfCellStyleForDataCellGraph.setAlignment(XSSFCellStyle.ALIGN_CENTER);
		ExcelUtility.getHSSFCellForData(firstRow, 0, xssfCellStyleForDataCellGraph, "Data For Date Range  " + Constants.DATE_FORMAT_REPORTS.format(startDate) + "  To  " + Constants.DATE_FORMAT_REPORTS.format(endDate));

		excelSheetChart.setColumnWidth(0, 3000);
		excelSheetChart.setColumnWidth(1, 8000);
		excelSheetChart.setColumnWidth(2, 3500);
		excelSheetChart.setColumnWidth(3, 2050);
		excelSheetChart.setColumnWidth(4, 2050);
		excelSheetChart.setColumnWidth(5, 8000);
		excelSheetChart.setColumnWidth(6, 3500);
		excelSheetChart.setColumnWidth(7, 2050);
		excelSheetChart.setColumnWidth(8, 2050);
		excelSheetChart.setColumnWidth(9, 8000);
		excelSheetChart.setColumnWidth(10, 3500);
		excelSheetChart.setColumnWidth(11, 1100);
		excelSheetChart.setColumnWidth(12, 1100);

		HSSFRow excelRow = excelSheetChart.createRow(17);
		HSSFRow excelRow1 = excelSheetChart.createRow(18);
		HSSFRow excelRow2 = excelSheetChart.createRow(19);
		HSSFRow excelRow3 = excelSheetChart.createRow(20);
		HSSFRow excelRow4 = excelSheetChart.createRow(39);
		HSSFRow excelRow5 = excelSheetChart.createRow(40);
		HSSFRow excelRow6 = excelSheetChart.createRow(41);
		HSSFRow excelRow7 = excelSheetChart.createRow(42);
		HSSFRow excelRow8 = excelSheetChart.createRow(43);

		setNonBillableChartData(startDate, workbook, excelSheetChart, resourceMovementReportGraphs, excelRow, excelRow1, excelRow2, excelRow3);
		setBillableChartData(startDate, workbook, excelSheetChart, resourceMovementReportGraphs, excelRow, excelRow1, excelRow2, excelRow3);
		setPartialChartData(startDate, workbook, excelSheetChart, resourceMovementReportGraphs, excelRow, excelRow1, excelRow2, excelRow3);
		setInvestmentChartData(startDate, workbook, excelSheetChart, resourceMovementReportGraphs, excelRow4, excelRow5, excelRow6, excelRow7);
		setNewHiringChartData(startDate, workbook, excelSheetChart, resourceMovementReportGraphs, excelRow4, excelRow5, excelRow6, excelRow7, excelRow8);
		setResourceTypeChartData(startDate, workbook, excelSheetChart, resourceMovementReportGraphs, excelRow4, excelRow5, excelRow6);

		System.out.println("Excel create successfully ----------------------------------------");

	}

	private void setExcelDataForAnalysisSheet(HSSFSheet excelSheetAnalysis, List<ResourceMovementReport> resourceMovementReportList, HSSFCellStyle xssfCellStyleForDataCell, HSSFWorkbook workbook) {

		int record = 1;
		int sNo = 1;

		for (ResourceMovementReport valResMoveReportList : resourceMovementReportList) {

			HSSFRow excelRow = excelSheetAnalysis.createRow(record++);

			ExcelUtility.getHSSFCellForData(excelRow, 0, xssfCellStyleForDataCell, String.valueOf(sNo));

			ExcelUtility.getHSSFCellForData(excelRow, 1, xssfCellStyleForDataCell, valResMoveReportList.getYashEmpID());

			ExcelUtility.getHSSFCellForData(excelRow, 2, xssfCellStyleForDataCell, valResMoveReportList.getEmployeeName());

			ExcelUtility.getHSSFCellForData(excelRow, 3, xssfCellStyleForDataCell, valResMoveReportList.getBGBUName());

			ExcelUtility.getHSSFCellForData(excelRow, 4, xssfCellStyleForDataCell, Constants.DATE_FORMAT_REPORTS.format(valResMoveReportList.getStartDate()));

			ExcelUtility.getHSSFCellForData(excelRow, 5, xssfCellStyleForDataCell, valResMoveReportList.getProjectName());

			ExcelUtility.getHSSFCellForData(excelRow, 6, xssfCellStyleForDataCell, valResMoveReportList.getAllocationType());

			ExcelUtility.getHSSFCellForData(excelRow, 7, xssfCellStyleForDataCell, valResMoveReportList.getPreviousAllocationType());

			ExcelUtility.getHSSFCellForData(excelRow, 8, xssfCellStyleForDataCell, valResMoveReportList.getAllocationChange());

			ExcelUtility.getHSSFCellForData(excelRow, 9, xssfCellStyleForDataCell, valResMoveReportList.getResourceType());

			sNo++;

		}

	}

	public void setExcelDataForRows(HSSFSheet excelSheet, List<ResourceMovementReport> resMoveReportList, HSSFCellStyle xssfCellStyleForDataCell, HSSFWorkbook workbook) {

		int record = 1;
		int sNo = 1;

		for (ResourceMovementReport valResMoveReportList : resMoveReportList) {

			HSSFRow excelRow = excelSheet.createRow(record++);

			ExcelUtility.getHSSFCellForData(excelRow, 0, xssfCellStyleForDataCell, String.valueOf(sNo));

			ExcelUtility.getHSSFCellForData(excelRow, 1, xssfCellStyleForDataCell, valResMoveReportList.getYashEmpID());

			ExcelUtility.getHSSFCellForData(excelRow, 2, xssfCellStyleForDataCell, valResMoveReportList.getEmployeeName());

			ExcelUtility.getHSSFCellForData(excelRow, 3, xssfCellStyleForDataCell, valResMoveReportList.getBGBUName());

			ExcelUtility.getHSSFCellForData(excelRow, 4, xssfCellStyleForDataCell, Constants.DATE_FORMAT_REPORTS.format(valResMoveReportList.getStartDate()));

			ExcelUtility.getHSSFCellForData(excelRow, 5, xssfCellStyleForDataCell, valResMoveReportList.getProjectName());

			ExcelUtility.getHSSFCellForData(excelRow, 6, xssfCellStyleForDataCell, valResMoveReportList.getAllocationType());

			sNo++;

		}

		logger.debug("End buildExcelDocument in RMReportExcelView Class  ");
	}

	public void setNonBillableChartData(Date startDate, HSSFWorkbook workbook, HSSFSheet excelSheetChart, ResourceMovementReportGraphs movementReportGraphs, HSSFRow excelRow, HSSFRow excelRow1,
			HSSFRow excelRow2, HSSFRow excelRow3) {

		final DefaultCategoryDataset dataset = new DefaultCategoryDataset();

		String totalPreviousNBAllocation = "";
		String totalBFTfromNBAllocation = "";
		String totalBPfromNBAllocation = "";
		String totalInvestmentfromNBAllocation = "";

		if (movementReportGraphs.getTotalPreviousNBAllocationType() != null && movementReportGraphs.getTotalBFTfromNBAllocationType() != null) {

			Double differenceBf = (double) (movementReportGraphs.getTotalPreviousNBAllocationType() - movementReportGraphs.getTotalBFTfromNBAllocationType());
			dataset.addValue((100 - ((float) ((differenceBf * 100) / movementReportGraphs.getTotalPreviousNBAllocationType()))), "Billable", "Billable");
			totalPreviousNBAllocation = movementReportGraphs.getTotalPreviousNBAllocationType().toString();
			totalBFTfromNBAllocation = movementReportGraphs.getTotalBFTfromNBAllocationType().toString();
		}
		if (movementReportGraphs.getTotalPreviousNBAllocationType() != null && movementReportGraphs.getTotalBPartFromNBAllocationType() != null) {

			Double differenceBp = (double) (movementReportGraphs.getTotalPreviousNBAllocationType() - movementReportGraphs.getTotalBPartFromNBAllocationType());
			dataset.addValue((100 - ((float) ((differenceBp * 100) / movementReportGraphs.getTotalPreviousNBAllocationType()))), "Partial", "Partial");
			totalBPfromNBAllocation = movementReportGraphs.getTotalBPartFromNBAllocationType().toString();
		}

		if (movementReportGraphs.getTotalPreviousNBAllocationType() != null && movementReportGraphs.getTotalInvestmentfromNBAllocationType() != null) {

			Double differenceInvest = (double) (movementReportGraphs.getTotalPreviousNBAllocationType() - movementReportGraphs.getTotalInvestmentfromNBAllocationType());
			dataset.addValue((100 - ((float) ((differenceInvest * 100) / movementReportGraphs.getTotalPreviousNBAllocationType()))), "Investment", "Investment");
			totalInvestmentfromNBAllocation = movementReportGraphs.getTotalInvestmentfromNBAllocationType().toString();
		}

		// Create the chart
		createChart(dataset, workbook, excelSheetChart, "Non-Billable conversion to ", 2, 0);

		// Adding Total value for percentage calculation in sheet

		HSSFCellStyle xssfCellStyleForDataCell = ExcelUtility.getHSSFCellStyleForDataCell(workbook);
		ExcelUtility.getHSSFCellForData(excelRow, 1, xssfCellStyleForDataCell, "Non-Billable Allocation to " + Constants.DATE_FORMAT_REPORTS.format(startDate));
		ExcelUtility.getHSSFCellForData(excelRow, 2, xssfCellStyleForDataCell, totalPreviousNBAllocation);

		ExcelUtility.getHSSFCellForData(excelRow1, 1, xssfCellStyleForDataCell, "Converted To Billable = ");
		ExcelUtility.getHSSFCellForData(excelRow1, 2, xssfCellStyleForDataCell, totalBFTfromNBAllocation);

		ExcelUtility.getHSSFCellForData(excelRow2, 1, xssfCellStyleForDataCell, "Converted To Partial = ");
		ExcelUtility.getHSSFCellForData(excelRow2, 2, xssfCellStyleForDataCell, totalBPfromNBAllocation);

		ExcelUtility.getHSSFCellForData(excelRow3, 1, xssfCellStyleForDataCell, "Converted To Investment = ");
		ExcelUtility.getHSSFCellForData(excelRow3, 2, xssfCellStyleForDataCell, totalInvestmentfromNBAllocation);
	}

	public void setBillableChartData(Date startDate, HSSFWorkbook workbook, HSSFSheet excelSheetChart, ResourceMovementReportGraphs movementReportGraphs, HSSFRow excelRow, HSSFRow excelRow1,
			HSSFRow excelRow2, HSSFRow excelRow3) {

		final DefaultCategoryDataset dataset = new DefaultCategoryDataset();

		String totalPreviousBFAllocation = "";
		String totalNBTfromBFAllocation = "";
		String totalBPfromBFAllocation = "";
		String totalInvestmentfromBFAllocation = "";

		if (movementReportGraphs.getTotalpreviousFBillAllocationType() != null && movementReportGraphs.getTotalNBfromBFAllocationType() != null) {

			Double differenceNb = (double) (movementReportGraphs.getTotalpreviousFBillAllocationType() - movementReportGraphs.getTotalNBfromBFAllocationType());
			dataset.addValue((100 - ((float) ((differenceNb * 100) / movementReportGraphs.getTotalpreviousFBillAllocationType()))), "Non-Billable", "Non-Billable");
			totalPreviousBFAllocation = movementReportGraphs.getTotalpreviousFBillAllocationType().toString();
			totalNBTfromBFAllocation = movementReportGraphs.getTotalNBfromBFAllocationType().toString();
		}
		if (movementReportGraphs.getTotalpreviousFBillAllocationType() != null && movementReportGraphs.getTotalBPartFromBFAllocationType() != null) {

			Double differenceBp = (double) (movementReportGraphs.getTotalpreviousFBillAllocationType() - movementReportGraphs.getTotalBPartFromBFAllocationType());
			dataset.addValue((100 - ((float) ((differenceBp * 100) / movementReportGraphs.getTotalpreviousFBillAllocationType()))), "Partial", "Partial");
			totalBPfromBFAllocation = movementReportGraphs.getTotalBPartFromBFAllocationType().toString();
		}

		if (movementReportGraphs.getTotalpreviousFBillAllocationType() != null && movementReportGraphs.getTotalInvestmentfromBFAllocationType() != null) {

			Double differenceInvest = (double) (movementReportGraphs.getTotalpreviousFBillAllocationType() - movementReportGraphs.getTotalInvestmentfromBFAllocationType());
			dataset.addValue((100 - ((float) ((differenceInvest * 100) / movementReportGraphs.getTotalpreviousFBillAllocationType()))), "Investment", "Investment");
			totalInvestmentfromBFAllocation = movementReportGraphs.getTotalInvestmentfromBFAllocationType().toString();
		}

		// Create the charts
		createChart(dataset, workbook, excelSheetChart, "Billable conversion to ", 2, 4);

		HSSFCellStyle xssfCellStyleForDataCell = ExcelUtility.getHSSFCellStyleForDataCell(workbook);
		ExcelUtility.getHSSFCellForData(excelRow, 5, xssfCellStyleForDataCell, "Billable Allocation to " + Constants.DATE_FORMAT_REPORTS.format(startDate));
		ExcelUtility.getHSSFCellForData(excelRow, 6, xssfCellStyleForDataCell, totalPreviousBFAllocation);

		ExcelUtility.getHSSFCellForData(excelRow1, 5, xssfCellStyleForDataCell, "Converted To Non-Billable = ");
		ExcelUtility.getHSSFCellForData(excelRow1, 6, xssfCellStyleForDataCell, totalNBTfromBFAllocation);

		ExcelUtility.getHSSFCellForData(excelRow2, 5, xssfCellStyleForDataCell, "Converted To Partial Billable = ");
		ExcelUtility.getHSSFCellForData(excelRow2, 6, xssfCellStyleForDataCell, totalBPfromBFAllocation);

		ExcelUtility.getHSSFCellForData(excelRow3, 5, xssfCellStyleForDataCell, "Converted To Investment = ");
		ExcelUtility.getHSSFCellForData(excelRow3, 6, xssfCellStyleForDataCell, totalInvestmentfromBFAllocation);

	}

	public void setPartialChartData(Date startDate, HSSFWorkbook workbook, HSSFSheet excelSheetChart, ResourceMovementReportGraphs movementReportGraphs, HSSFRow excelRow, HSSFRow excelRow1,
			HSSFRow excelRow2, HSSFRow excelRow3) {

		final DefaultCategoryDataset dataset = new DefaultCategoryDataset();

		String totalPreviousBPAllocation = "";
		String totalNBTfromBPAllocation = "";
		String totalBFfromBPAllocation = "";
		String totalInvestmtfromBPAllocation = "";

		if (movementReportGraphs.getTotalpreviousPartBillAllocationType() != null && movementReportGraphs.getTotalNBfromBPartAllocationType() != null) {

			Double differenceNb = (double) (movementReportGraphs.getTotalpreviousPartBillAllocationType() - movementReportGraphs.getTotalNBfromBPartAllocationType());
			dataset.addValue((100 - ((float) ((differenceNb * 100) / movementReportGraphs.getTotalpreviousPartBillAllocationType()))), "Non-Billable", "Non-Billable");
			totalPreviousBPAllocation = movementReportGraphs.getTotalpreviousPartBillAllocationType().toString();
			totalNBTfromBPAllocation = movementReportGraphs.getTotalNBfromBPartAllocationType().toString();
		}
		if (movementReportGraphs.getTotalpreviousPartBillAllocationType() != null && movementReportGraphs.getTotalBFTfromBpartAllocationType() != null) {

			Double differenceBp = (double) (movementReportGraphs.getTotalpreviousPartBillAllocationType() - movementReportGraphs.getTotalBFTfromBpartAllocationType());
			dataset.addValue((100 - ((float) ((differenceBp * 100) / movementReportGraphs.getTotalpreviousPartBillAllocationType()))), "Billable", "Billable");
			totalBFfromBPAllocation = movementReportGraphs.getTotalBFTfromBpartAllocationType().toString();
		}

		if (movementReportGraphs.getTotalpreviousPartBillAllocationType() != null && movementReportGraphs.getTotalInvestmentfromBPartAllocationType() != null) {

			Double differenceInvest = (double) (movementReportGraphs.getTotalpreviousPartBillAllocationType() - movementReportGraphs.getTotalInvestmentfromBPartAllocationType());
			dataset.addValue((100 - ((float) ((differenceInvest * 100) / movementReportGraphs.getTotalpreviousPartBillAllocationType()))), "Investment", "Investment");
			totalInvestmtfromBPAllocation = movementReportGraphs.getTotalInvestmentfromBPartAllocationType().toString();
		}

		// Create the charts
		createChart(dataset, workbook, excelSheetChart, "Partial conversion to ", 2, 8);

		// Adding Total value for percentage calculation in sheet

		HSSFCellStyle xssfCellStyleForDataCell = ExcelUtility.getHSSFCellStyleForDataCell(workbook);
		ExcelUtility.getHSSFCellForData(excelRow, 9, xssfCellStyleForDataCell, "Partial Allocation to " + Constants.DATE_FORMAT_REPORTS.format(startDate));
		ExcelUtility.getHSSFCellForData(excelRow, 10, xssfCellStyleForDataCell, totalPreviousBPAllocation);

		ExcelUtility.getHSSFCellForData(excelRow1, 9, xssfCellStyleForDataCell, "Converted To Non-Billable = ");
		ExcelUtility.getHSSFCellForData(excelRow1, 10, xssfCellStyleForDataCell, totalNBTfromBPAllocation);

		ExcelUtility.getHSSFCellForData(excelRow2, 9, xssfCellStyleForDataCell, "Converted To Billable = ");
		ExcelUtility.getHSSFCellForData(excelRow2, 10, xssfCellStyleForDataCell, totalBFfromBPAllocation);

		ExcelUtility.getHSSFCellForData(excelRow3, 9, xssfCellStyleForDataCell, "Converted To Investment = ");
		ExcelUtility.getHSSFCellForData(excelRow3, 10, xssfCellStyleForDataCell, totalInvestmtfromBPAllocation);

	}

	public void setInvestmentChartData(Date startDate, HSSFWorkbook workbook, HSSFSheet excelSheetChart, ResourceMovementReportGraphs movementReportGraphs, HSSFRow excelRow4, HSSFRow excelRow5,
			HSSFRow excelRow6, HSSFRow excelRow7) {

		final DefaultCategoryDataset dataset = new DefaultCategoryDataset();

		String totalPreviousInvestmentAllocation = "";
		String totalNBTfromInvestmentAllocation = "";
		String totalBFfromInvestmentAllocation = "";
		String totalBpartfromInvestmentAllocation = "";

		if (movementReportGraphs.getTotalInvestmentfromPreviousAllocationType() != null && movementReportGraphs.getTotalNBfromInvestmentAllocationType() != null) {

			Double differenceNb = (double) (movementReportGraphs.getTotalInvestmentfromPreviousAllocationType() - movementReportGraphs.getTotalNBfromInvestmentAllocationType());
			dataset.addValue((100 - ((float) ((differenceNb * 100) / movementReportGraphs.getTotalInvestmentfromPreviousAllocationType()))), "Non-Billable", "Non-Billable");
			totalPreviousInvestmentAllocation = movementReportGraphs.getTotalInvestmentfromPreviousAllocationType().toString();
			totalNBTfromInvestmentAllocation = movementReportGraphs.getTotalNBfromInvestmentAllocationType().toString();
		}
		if (movementReportGraphs.getTotalInvestmentfromPreviousAllocationType() != null && movementReportGraphs.getTotalBFTfromInvestmentAllocationType() != null) {

			Double differenceBF = (double) (movementReportGraphs.getTotalInvestmentfromPreviousAllocationType() - movementReportGraphs.getTotalBFTfromInvestmentAllocationType());
			dataset.addValue((100 - ((float) ((differenceBF * 100) / movementReportGraphs.getTotalInvestmentfromPreviousAllocationType()))), "Billable", "Billable");
			totalBFfromInvestmentAllocation = movementReportGraphs.getTotalBFTfromInvestmentAllocationType().toString();
		}

		if (movementReportGraphs.getTotalInvestmentfromPreviousAllocationType() != null && movementReportGraphs.getTotalPartBillFromInvestmentAllocationType() != null) {

			Double differenceBp = (double) (movementReportGraphs.getTotalInvestmentfromPreviousAllocationType() - movementReportGraphs.getTotalPartBillFromInvestmentAllocationType());
			dataset.addValue((100 - ((float) ((differenceBp * 100) / movementReportGraphs.getTotalInvestmentfromPreviousAllocationType()))), "Investment", "Investment");
			totalBpartfromInvestmentAllocation = movementReportGraphs.getTotalPartBillFromInvestmentAllocationType().toString();
		}

		// Create the charts
		createChart(dataset, workbook, excelSheetChart, "Investment conversion to ", 24, 0);

		// Adding Total value for percentage calculation in sheet

		HSSFCellStyle xssfCellStyleForDataCell = ExcelUtility.getHSSFCellStyleForDataCell(workbook);
		ExcelUtility.getHSSFCellForData(excelRow4, 1, xssfCellStyleForDataCell, "Investment Allocation to " + Constants.DATE_FORMAT_REPORTS.format(startDate));
		ExcelUtility.getHSSFCellForData(excelRow4, 2, xssfCellStyleForDataCell, totalPreviousInvestmentAllocation);

		ExcelUtility.getHSSFCellForData(excelRow5, 1, xssfCellStyleForDataCell, "Converted To Non-Billable = ");
		ExcelUtility.getHSSFCellForData(excelRow5, 2, xssfCellStyleForDataCell, totalNBTfromInvestmentAllocation);

		ExcelUtility.getHSSFCellForData(excelRow6, 1, xssfCellStyleForDataCell, "Converted To Billable = ");
		ExcelUtility.getHSSFCellForData(excelRow6, 2, xssfCellStyleForDataCell, totalBFfromInvestmentAllocation);

		ExcelUtility.getHSSFCellForData(excelRow7, 1, xssfCellStyleForDataCell, "Converted To Partial Billable = ");
		ExcelUtility.getHSSFCellForData(excelRow7, 2, xssfCellStyleForDataCell, totalBpartfromInvestmentAllocation);

	}

	public void setNewHiringChartData(Date startDate, HSSFWorkbook workbook, HSSFSheet excelSheetChart, ResourceMovementReportGraphs movementReportGraphs, HSSFRow excelRow4, HSSFRow excelRow5,
			HSSFRow excelRow6, HSSFRow excelRow7, HSSFRow excelRow8) {

		final DefaultCategoryDataset dataset = new DefaultCategoryDataset();

		String totalNewHire = "";
		String totalNBTfromNewHire = "";
		String totalBFfromNewHire = "";
		String totalBpartfromNewHire = "";
		String totalInvestmentfromNewHire = "";

		if (movementReportGraphs.getTotalNewHiredResourceAllocationType() != null && movementReportGraphs.getTotalNBfromNewHiredAllocationType() != null) {

			Double differenceNb = (double) (movementReportGraphs.getTotalNewHiredResourceAllocationType() - movementReportGraphs.getTotalNBfromNewHiredAllocationType());
			dataset.addValue((100 - ((float) ((differenceNb * 100) / movementReportGraphs.getTotalNewHiredResourceAllocationType()))), "Non-Billable", "Non-Billable");
			totalNewHire = movementReportGraphs.getTotalNewHiredResourceAllocationType().toString();
			totalNBTfromNewHire = movementReportGraphs.getTotalNBfromNewHiredAllocationType().toString();
		}
		if (movementReportGraphs.getTotalNewHiredResourceAllocationType() != null && movementReportGraphs.getTotalBFTfromNewHiredAllocationType() != null) {

			Double differenceBp = (double) (movementReportGraphs.getTotalNewHiredResourceAllocationType() - movementReportGraphs.getTotalBFTfromNewHiredAllocationType());
			dataset.addValue((100 - ((float) ((differenceBp * 100) / movementReportGraphs.getTotalNewHiredResourceAllocationType()))), "Billable", "Billable");
			totalBFfromNewHire = movementReportGraphs.getTotalBFTfromNewHiredAllocationType().toString();
		}

		if (movementReportGraphs.getTotalNewHiredResourceAllocationType() != null && movementReportGraphs.getTotalPartBillFromNewHiredAllocationType() != null) {

			Double differenceInvest = (double) (movementReportGraphs.getTotalNewHiredResourceAllocationType() - movementReportGraphs.getTotalPartBillFromNewHiredAllocationType());
			dataset.addValue((100 - ((float) ((differenceInvest * 100) / movementReportGraphs.getTotalNewHiredResourceAllocationType()))), "Partial", "Partial");
			totalBpartfromNewHire = movementReportGraphs.getTotalPartBillFromNewHiredAllocationType().toString();
		}

		if (movementReportGraphs.getTotalNewHiredResourceAllocationType() != null && movementReportGraphs.getTotalInvestmentfromNewHiredAllocationType() != null) {

			Double differenceInvest = (double) (movementReportGraphs.getTotalNewHiredResourceAllocationType() - movementReportGraphs.getTotalInvestmentfromNewHiredAllocationType());
			dataset.addValue((100 - ((float) ((differenceInvest * 100) / movementReportGraphs.getTotalNewHiredResourceAllocationType()))), "Investment", "Investment");
			totalInvestmentfromNewHire = movementReportGraphs.getTotalInvestmentfromNewHiredAllocationType().toString();
		}

		// Create the chart
		createChart(dataset, workbook, excelSheetChart, "New Hiring conversion to ", 24, 4);

		// Adding Total value for percentage calculation in sheet

		HSSFCellStyle xssfCellStyleForDataCell = ExcelUtility.getHSSFCellStyleForDataCell(workbook);
		ExcelUtility.getHSSFCellForData(excelRow4, 5, xssfCellStyleForDataCell, "New Hiring Allocation to " + Constants.DATE_FORMAT_REPORTS.format(startDate));
		ExcelUtility.getHSSFCellForData(excelRow4, 6, xssfCellStyleForDataCell, totalNewHire);

		ExcelUtility.getHSSFCellForData(excelRow5, 5, xssfCellStyleForDataCell, "Converted To Non-Billable = ");
		ExcelUtility.getHSSFCellForData(excelRow5, 6, xssfCellStyleForDataCell, totalNBTfromNewHire);

		ExcelUtility.getHSSFCellForData(excelRow6, 5, xssfCellStyleForDataCell, "Converted To Billable = ");
		ExcelUtility.getHSSFCellForData(excelRow6, 6, xssfCellStyleForDataCell, totalBFfromNewHire);

		ExcelUtility.getHSSFCellForData(excelRow7, 5, xssfCellStyleForDataCell, "Converted To Partial Billable = ");
		ExcelUtility.getHSSFCellForData(excelRow7, 6, xssfCellStyleForDataCell, totalBpartfromNewHire);

		ExcelUtility.getHSSFCellForData(excelRow8, 5, xssfCellStyleForDataCell, "Converted To Investment = ");
		ExcelUtility.getHSSFCellForData(excelRow8, 6, xssfCellStyleForDataCell, totalInvestmentfromNewHire);

	}

	public void setResourceTypeChartData(Date startDate, HSSFWorkbook workbook, HSSFSheet excelSheetChart, ResourceMovementReportGraphs movementReportGraphs, HSSFRow excelRow, HSSFRow excelRow1,
			HSSFRow excelRow2) {

		// Adding Total value for percentage calculation in sheet
		int rownum = 47;
		int rownum1 = 47;
		int rownum2 = 47;

		excelSheetChart.addMergedRegion(new CellRangeAddress(45, 45, 1, 4));
		HSSFRow row2nd = excelSheetChart.createRow(46);
		HSSFRow row1 = excelSheetChart.createRow(45);

		HSSFCellStyle xssfCellStyleForDataCellResource = ExcelUtility.getHSSFCellStyleForDataCell(workbook);
		xssfCellStyleForDataCellResource.setAlignment(XSSFCellStyle.ALIGN_CENTER);
		HSSFCellStyle xssfCellStyleForDataCellProject = ExcelUtility.getHSSFCellStyleForDataCell(workbook);
		xssfCellStyleForDataCellProject.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);

		ExcelUtility.getHSSFCellForData(row1, 1, xssfCellStyleForDataCellResource, "Project Resource Summary to " + Constants.DATE_FORMAT_REPORTS.format(startDate));
		ExcelUtility.getHSSFCellForData(row1, 2, xssfCellStyleForDataCellResource, "");
		ExcelUtility.getHSSFCellForData(row1, 3, xssfCellStyleForDataCellResource, "");
		ExcelUtility.getHSSFCellForData(row1, 4, xssfCellStyleForDataCellResource, "");

		ExcelUtility.getHSSFCellForData(row2nd, 1, xssfCellStyleForDataCellProject, "Project ");
		ExcelUtility.getHSSFCellForData(row2nd, 2, xssfCellStyleForDataCellProject, "External ");
		ExcelUtility.getHSSFCellForData(row2nd, 3, xssfCellStyleForDataCellProject, "Internal ");
		ExcelUtility.getHSSFCellForData(row2nd, 4, xssfCellStyleForDataCellProject, "Total ");

		HSSFCellStyle xssfCellStyleForDataCell = ExcelUtility.getHSSFCellStyleForDataCell(workbook);

		for (Map.Entry<String, Integer> entry : movementReportGraphs.getTotalProject().entrySet()) {

			HSSFRow row = excelSheetChart.createRow(rownum++);
			if (entry.getKey() != null) {

				ExcelUtility.getHSSFCellForData(row, 1, xssfCellStyleForDataCell, entry.getKey());

				if (entry.getValue() == 0 || entry.getValue().equals(0)) {
					ExcelUtility.getHSSFCellForData(row, 4, xssfCellStyleForDataCell, "0");
				} else {
					ExcelUtility.getHSSFCellForData(row, 4, xssfCellStyleForDataCell, entry.getValue().toString());
				}
			}
		}

		for (Map.Entry<String, Integer> entry1 : movementReportGraphs.getTotalExternalfromProject().entrySet()) {

			HSSFRow row2 = excelSheetChart.getRow(rownum2++);
			if (entry1.getKey() != null) {
				if (entry1.getValue() == 0 || entry1.getValue().equals(0)) {
					ExcelUtility.getHSSFCellForData(row2, 2, xssfCellStyleForDataCell, "0");
				} else {
					ExcelUtility.getHSSFCellForData(row2, 2, xssfCellStyleForDataCell, entry1.getValue().toString());
				}
			}
		}

		for (Map.Entry<String, Integer> entry2 : movementReportGraphs.getTotalInternalfromProject().entrySet()) {

			HSSFRow row0 = excelSheetChart.getRow(rownum1++);
			if (entry2.getKey() != null) {
				if (entry2.getValue() == 0 || entry2.getValue().equals(0)) {
					ExcelUtility.getHSSFCellForData(row0, 3, xssfCellStyleForDataCell, "0");

				} else {
					ExcelUtility.getHSSFCellForData(row0, 3, xssfCellStyleForDataCell, entry2.getValue().toString());
				}
			}
		}

	}

	public void createChart(DefaultCategoryDataset dataset, HSSFWorkbook workbook, HSSFSheet excelSheetChart, String title, int row, int column) {

		try {

			// Create the charts
			JFreeChart myBarChart = ChartFactory.createBarChart(title, "Category", "Value %", dataset, PlotOrientation.VERTICAL, true, true, false);
			myBarChart.setTitle(new org.jfree.chart.title.TextTitle(title, new java.awt.Font("SansSerif", java.awt.Font.BOLD, 12)));
			final CategoryPlot plot = myBarChart.getCategoryPlot();
			CategoryAxis domain = plot.getDomainAxis();
			domain.setCategoryLabelPositions(CategoryLabelPositions.STANDARD);
			plot.setBackgroundPaint(Color.lightGray);// change background color

			final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
			rangeAxis.setRange(0, 115);
			rangeAxis.setNumberFormatOverride(new DecimalFormat("##0'%'"));
			rangeAxis.setTickLabelFont(new Font("SansSerif", Font.PLAIN, 10));

			BarRenderer renderer = (BarRenderer) plot.getRenderer();
			DecimalFormat decimalformat1 = new DecimalFormat("##.0'%'");
			renderer.setItemLabelGenerator(new StandardCategoryItemLabelGenerator("{2}", decimalformat1));
			renderer.setItemLabelsVisible(true);
			renderer.setMaximumBarWidth(0.088);
			renderer.setItemMargin(-2);

			myBarChart.getCategoryPlot().setRenderer(renderer);

			// Specify the height and width of the Pie Chart
			int width = 450; // Width of the chart
			int height = 250; // Height of the chart
			float quality = 1; // Quality factor

			// We don't want to create an intermediate file. So, we create a
			// byte array output stream and byte array input stream And we pass
			// the chart data directly to input stream through this

			// Write chart as JPG to Output Stream
			ByteArrayOutputStream chart_out = new ByteArrayOutputStream();
			ChartUtilities.writeChartAsJPEG(chart_out, quality, myBarChart, width, height);

			// We now read from the output stream and frame the input chart data
			InputStream feed_chart_to_excel = new ByteArrayInputStream(chart_out.toByteArray());
			byte[] bytes = IOUtils.toByteArray(feed_chart_to_excel);

			// Add picture to workbook
			int my_picture_id = workbook.addPicture(bytes, workbook.PICTURE_TYPE_JPEG);

			// We can close Piped Input Stream. We don't need this
			feed_chart_to_excel.close();

			// Close PipedOutputStream also
			chart_out.close();

			// Create the drawing container
			HSSFPatriarch drawing = excelSheetChart.createDrawingPatriarch();

			// Create an anchor point
			CreationHelper helper = workbook.getCreationHelper();
			ClientAnchor my_anchor = helper.createClientAnchor();

			// Define top left corner, and we can resize picture suitable from
			// there
			my_anchor.setCol1(column);
			my_anchor.setRow1(row);

			// Invoke createPicture and pass the anchor point and ID
			HSSFPicture my_picture = drawing.createPicture(my_anchor, my_picture_id);

			// Call resize method, which resizes the image
			my_picture.resize();

		} catch (IOException e) {
			e.printStackTrace();
			logger.debug(e.getMessage());
			logger.debug("----------Inside ResourceMovementReportExcel End ResourceMovementReport-----------");
		}
	}

}