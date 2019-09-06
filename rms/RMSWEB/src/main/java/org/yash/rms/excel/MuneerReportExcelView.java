package org.yash.rms.excel;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.view.document.AbstractExcelView;
import org.yash.rms.report.dto.MuneerReport;
import org.yash.rms.report.dto.MuneerReportDto;
import org.yash.rms.util.Constants;

public class MuneerReportExcelView extends AbstractExcelView {

	private static final Logger logger = LoggerFactory.getLogger(MuneerReportExcelView.class);

	@SuppressWarnings({ "unchecked" })
	@Override
	protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, HttpServletRequest request, HttpServletResponse response) throws Exception {

		logger.debug("Started buildExcelDocument in RMReportExcelView Class  ");

		HSSFSheet sheet = workbook.createSheet("REPORT_SHEET");

		response.setHeader("Content-Disposition", "attachment; filename=Management_Report.xls");

		sheet.setDefaultColumnWidth(28);

		CellStyle style_Grey = style_Grey(workbook);

		CellStyle style_Blue = style_Blue(workbook);

		CellStyle style_Orange = style_Orange(workbook);

		CellStyle style_Green = style_Green(workbook);

		CellStyle style_Pink = style_Pink(workbook);

		CellStyle style_Yellow = style_Yellow(workbook);

		CellStyle style_Grey1 = style_Grey2(workbook);

		CellStyle style_Orange2 = style_Orange2(workbook);

		Cell cell = null;

		// Row row = null;

		HttpSession session = request.getSession();

		Map<String, Map<String, Integer>> clientData = (Map<String, Map<String, Integer>>) session.getAttribute("MuneerClientData");

		Set<MuneerReport> list = (Set<MuneerReport>) model.get("rmReportsList");

		Set<String> allocationTypeSet = new HashSet<String>();

		// Header

		try {

			// Row 0
			HSSFRow muneerReportHeaderRow = sheet.createRow(0);
			Cell cellHeader = muneerReportHeaderRow.createCell(0);
			cellHeader.setCellValue("");
			cellHeader.setCellStyle(style_Orange);
			sheet.setColumnWidth(0, 1500);
			cellHeader = muneerReportHeaderRow.createCell(1);
			cellHeader.setCellValue("From Payroll Records ENo/Ename");
			cellHeader.setCellStyle(style_Orange);
			sheet.addMergedRegion(new CellRangeAddress(0, 0, 1, 2));
			cellHeader = muneerReportHeaderRow.createCell(3);
			cellHeader.setCellValue("Business Groups");
			cellHeader.setCellStyle(style_Blue);
			sheet.addMergedRegion(new CellRangeAddress(0, 0, 3, 4));
			cellHeader = muneerReportHeaderRow.createCell(5);
			cellHeader.setCellValue("");
			cellHeader.setCellStyle(style_Pink);
			cellHeader = muneerReportHeaderRow.createCell(6);
			cellHeader.setCellValue("If On Loan Or Loaned");
			cellHeader.setCellStyle(style_Blue);
			sheet.addMergedRegion(new CellRangeAddress(0, 0, 6, 7));
			cellHeader = muneerReportHeaderRow.createCell(8);
			cellHeader.setCellValue("Division By Skill");
			cellHeader.setCellStyle(style_Green);
			sheet.addMergedRegion(new CellRangeAddress(0, 0, 8, 9));
			cellHeader = muneerReportHeaderRow.createCell(10);
			cellHeader.setCellValue("Division By Location");
			cellHeader.setCellStyle(style_Grey);
			sheet.addMergedRegion(new CellRangeAddress(0, 0, 10, 11));
			cellHeader = muneerReportHeaderRow.createCell(12);
			cellHeader.setCellValue("Division By Allocation / Billability");
			cellHeader.setCellStyle(style_Green);
			sheet.addMergedRegion(new CellRangeAddress(0, 0, 12, 19));
			cellHeader = muneerReportHeaderRow.createCell(20);
			cellHeader.setCellValue(" ");
			cellHeader.setCellStyle(style_Grey);
			cellHeader = muneerReportHeaderRow.createCell(21);
			cellHeader.setCellValue(" ");
			cellHeader.setCellStyle(style_Grey);
			cellHeader = muneerReportHeaderRow.createCell(22);
			cellHeader.setCellValue(" ");
			cellHeader.setCellStyle(style_Grey);
			cellHeader = muneerReportHeaderRow.createCell(23);
			cellHeader.setCellValue(" ");
			cellHeader.setCellStyle(style_Grey);
			cellHeader = muneerReportHeaderRow.createCell(24);
			cellHeader.setCellValue(" ");
			cellHeader.setCellStyle(style_Grey);
			cellHeader = muneerReportHeaderRow.createCell(25);
			cellHeader.setCellValue("Percentage Allocation");
			cellHeader.setCellStyle(style_Grey);

			// Row 1
			HSSFRow muneerReportHeaderRow1 = sheet.createRow(1);
			cellHeader = muneerReportHeaderRow1.createCell(0);
			cellHeader.setCellValue("S.No.");
			cellHeader.setCellStyle(style_Orange);
			cellHeader = muneerReportHeaderRow1.createCell(1);
			cellHeader.setCellValue("HRIMS Code");
			cellHeader.setCellStyle(style_Orange);
			cellHeader = muneerReportHeaderRow1.createCell(2);
			cellHeader.setCellValue("Name");
			cellHeader.setCellStyle(style_Orange);
			cellHeader = muneerReportHeaderRow1.createCell(3);
			cellHeader.setCellValue("BG");
			cellHeader.setCellStyle(style_Yellow);
			cellHeader = muneerReportHeaderRow1.createCell(4);
			cellHeader.setCellValue("BU");
			cellHeader.setCellStyle(style_Yellow);
			cellHeader = muneerReportHeaderRow1.createCell(5);
			cellHeader.setCellValue("Ownership");
			cellHeader.setCellStyle(style_Pink);
			cellHeader = muneerReportHeaderRow1.createCell(6);
			cellHeader.setCellValue("BG");
			cellHeader.setCellStyle(style_Yellow);
			cellHeader = muneerReportHeaderRow1.createCell(7);
			cellHeader.setCellValue("BU");
			cellHeader.setCellStyle(style_Yellow);
			cellHeader = muneerReportHeaderRow1.createCell(8);
			cellHeader.setCellValue("Practise");
			cellHeader.setCellStyle(style_Green);
			cellHeader = muneerReportHeaderRow1.createCell(9);
			cellHeader.setCellValue("Competency");
			cellHeader.setCellStyle(style_Green);
			cellHeader = muneerReportHeaderRow1.createCell(10);
			cellHeader.setCellValue("Base Location");
			cellHeader.setCellStyle(style_Grey);
			cellHeader = muneerReportHeaderRow1.createCell(11);
			cellHeader.setCellValue("Deployment Location");
			cellHeader.setCellStyle(style_Grey);
			cellHeader = muneerReportHeaderRow1.createCell(12);
			cellHeader.setCellValue("Allocation");
			cellHeader.setCellStyle(style_Green);
			cellHeader = muneerReportHeaderRow1.createCell(13);
			cellHeader.setCellValue("Start Date");
			cellHeader.setCellStyle(style_Green);
			cellHeader = muneerReportHeaderRow1.createCell(14);
			cellHeader.setCellValue("Allocated Since");
			cellHeader.setCellStyle(style_Green);
			cellHeader = muneerReportHeaderRow1.createCell(15);
			cellHeader.setCellValue("Billable");
			cellHeader.setCellStyle(style_Green);
			cellHeader = muneerReportHeaderRow1.createCell(16);
			cellHeader.setCellValue("Client");
			cellHeader.setCellStyle(style_Green);
			cellHeader = muneerReportHeaderRow1.createCell(17);
			cellHeader.setCellValue("Project");
			cellHeader.setCellStyle(style_Green);
			cellHeader = muneerReportHeaderRow1.createCell(18);
			cellHeader.setCellValue("SubProject");
			cellHeader.setCellStyle(style_Green);
			cellHeader = muneerReportHeaderRow1.createCell(19);
			cellHeader.setCellValue("Available to");
			cellHeader.setCellStyle(style_Green);
			cellHeader = muneerReportHeaderRow1.createCell(20);
			cellHeader.setCellValue("Remark From Delivery Unit / Business Unit");
			cellHeader.setCellStyle(style_Grey);
			cellHeader = muneerReportHeaderRow1.createCell(21);
			cellHeader.setCellValue("Designation ");
			cellHeader.setCellStyle(style_Grey);
			cellHeader = muneerReportHeaderRow1.createCell(22);
			cellHeader.setCellValue("Grade");
			cellHeader.setCellStyle(style_Grey);
			cellHeader = muneerReportHeaderRow1.createCell(23);
			cellHeader.setCellValue("Primary Skill");
			cellHeader.setCellStyle(style_Grey);
			cellHeader = muneerReportHeaderRow1.createCell(24);
			cellHeader.setCellValue("Secondary Skill");
			cellHeader.setCellStyle(style_Grey);
			cellHeader = muneerReportHeaderRow1.createCell(25);
			cellHeader.setCellValue("Percentage Allocation");
			cellHeader.setCellStyle(style_Grey);
			// Data
			if (!list.isEmpty()) {

				int rownum = 2;
				int j = 1;
				for (MuneerReport muneerReport : list) {

					muneerReportHeaderRow = sheet.createRow(rownum++);
					int cellnum = 0;
					cellHeader = muneerReportHeaderRow.createCell(cellnum++);
					cellHeader.setCellValue(j++);
					cellHeader = muneerReportHeaderRow.createCell(cellnum++);
					cellHeader.setCellValue(muneerReport.getYashEmpID());
					cellHeader = muneerReportHeaderRow.createCell(cellnum++);
					cellHeader.setCellValue(muneerReport.getEmployeeName());
					cellHeader = muneerReportHeaderRow.createCell(cellnum++);
					cellHeader.setCellValue(muneerReport.getBg());
					cellHeader = muneerReportHeaderRow.createCell(cellnum++);
					cellHeader.setCellValue(muneerReport.getBu());
					cellHeader = muneerReportHeaderRow.createCell(cellnum++);
					cellHeader.setCellValue(muneerReport.getOwnership());
					cellHeader = muneerReportHeaderRow.createCell(cellnum++);
					String bg = "", bu = "";
					if (muneerReport.getOwnership().equals("Loan")) {
						String[] bgBu = muneerReport.getCurrentBu().split("-");
						bg = bgBu[0];
						bu = bgBu[1];
					}
					cellHeader.setCellValue(bg);

					cellHeader = muneerReportHeaderRow.createCell(cellnum++);
					cellHeader.setCellValue(bu);
					cellHeader = muneerReportHeaderRow.createCell(cellnum++);
					cellHeader.setCellValue(muneerReport.getParentBu());
					cellHeader = muneerReportHeaderRow.createCell(cellnum++);
					cellHeader.setCellValue(muneerReport.getCompetency());
					cellHeader = muneerReportHeaderRow.createCell(cellnum++);
					cellHeader.setCellValue(muneerReport.getBaseLocation());
					cellHeader = muneerReportHeaderRow.createCell(cellnum++);
					cellHeader.setCellValue(muneerReport.getCurrentLocation());
					cellHeader = muneerReportHeaderRow.createCell(cellnum++);
					cellHeader.setCellValue(muneerReport.getAllocationType());
					allocationTypeSet.add(muneerReport.getAllocationType());
					cellHeader = muneerReportHeaderRow.createCell(cellnum++);
					try{
					cellHeader.setCellValue(Constants.DATE_FORMAT_REPORTS.format(muneerReport.getAllocationStartDate()));
					}
					catch(NullPointerException exception){
						System.out.println("Allcation Start date is not sepecified for empoloyee"+ muneerReport.getEmployeeName());
						
					}
					cellHeader = muneerReportHeaderRow.createCell(cellnum++);

					cellHeader.setCellValue(muneerReport.getAllocatedSince());

					cellHeader = muneerReportHeaderRow.createCell(cellnum++);
					cellHeader.setCellValue(muneerReport.getBillable());
					cellHeader = muneerReportHeaderRow.createCell(cellnum++);
					cellHeader.setCellValue(muneerReport.getCustomerName());
					cellHeader = muneerReportHeaderRow.createCell(cellnum++);
					if (null != muneerReport.getTeamName()) {
						cellHeader.setCellValue(muneerReport.getTeamName());
					} else {
						cellHeader.setCellValue(muneerReport.getPrimaryProject());
					}
					cellHeader = muneerReportHeaderRow.createCell(cellnum++);
					if (null != muneerReport.getTeamName()) {
						cellHeader.setCellValue(muneerReport.getPrimaryProject());
					}
					cellHeader = muneerReportHeaderRow.createCell(cellnum++);
					cellHeader.setCellValue("");
					cellHeader = muneerReportHeaderRow.createCell(cellnum++);
					cellHeader.setCellValue(muneerReport.getRemarks());
					cellHeader = muneerReportHeaderRow.createCell(cellnum++);
					cellHeader.setCellValue(muneerReport.getDesignation());
					cellHeader = muneerReportHeaderRow.createCell(cellnum++);
					cellHeader.setCellValue(muneerReport.getGrade());
					cellHeader = muneerReportHeaderRow.createCell(cellnum++);
					cellHeader.setCellValue(muneerReport.getPrimarySkill());
					cellHeader = muneerReportHeaderRow.createCell(cellnum++);
					cellHeader.setCellValue(muneerReport.getSecondarySkill());
					//Allocation Percentage
					cellHeader = muneerReportHeaderRow.createCell(cellnum++);
					cellHeader.setCellValue(muneerReport.getPercentageAllocation().toString());

					style_Brown(workbook, muneerReportHeaderRow);

				}

				// CLIENT_REPORT_SHEET

				int rowCount = 0;
				int columnCount = 0;
				HSSFSheet clienSheet = workbook.createSheet("CLIENT_REPORT_SHEET");

				//clienSheet.autoSizeColumn(1);

				Row row1 = clienSheet.createRow(++rowCount);

				cell = row1.createCell(0);
				cell.setCellStyle(style_Grey1);

				cell = row1.createCell(0);
				cell.setCellStyle(style_Grey1);

				cell = row1.createCell(1);
				Cell cell1 = row1.createCell(columnCount++);
				cell1.setCellStyle(style_Grey1);
				cell1.setCellValue("Client");

				if (allocationTypeSet.contains(Constants.BILLABLE_FULL_TIME)) {
					cell1 = row1.createCell(columnCount++);
					cell1.setCellStyle(style_Grey1);
					cell1.setCellValue(Constants.BILLABLE_FULL_TIME);
				}
				if (allocationTypeSet.contains(Constants.BILLABLE_PARTIA_SHAREDPOOL_FIXBIDPROJECTS)) {
					cell1 = row1.createCell(columnCount++);
					cell1.setCellStyle(style_Grey1);
					cell1.setCellValue(Constants.BILLABLE_PARTIA_SHAREDPOOL_FIXBIDPROJECTS);
				}
				if (allocationTypeSet.contains(Constants.NONBILLABLE_BLOCKED)) {
					cell1 = row1.createCell(columnCount++);
					cell1.setCellStyle(style_Grey1);
					cell1.setCellValue(Constants.NONBILLABLE_BLOCKED);
				}
				if (allocationTypeSet.contains(Constants.NONBILLABLE_CONTINGENT)) {
					cell1 = row1.createCell(columnCount++);
					cell1.setCellStyle(style_Grey1);
					cell1.setCellValue(Constants.NONBILLABLE_CONTINGENT);
				}

				if (allocationTypeSet.contains(Constants.NONBILLABLE_DELIVERY_MANAGEMENT)) {
					cell1 = row1.createCell(columnCount++);
					cell1.setCellStyle(style_Grey1);
					cell1.setCellValue(Constants.NONBILLABLE_DELIVERY_MANAGEMENT);
				}
				if (allocationTypeSet.contains(Constants.NONBILLABLE_OPERATIONS)) {
					cell1 = row1.createCell(columnCount++);
					cell1.setCellStyle(style_Grey1);
					cell1.setCellValue(Constants.NONBILLABLE_OPERATIONS);
				}
				if (allocationTypeSet.contains(Constants.NONBILLABLE_SHADOW)) {
					cell1 = row1.createCell(columnCount++);
					cell1.setCellStyle(style_Grey1);
					cell1.setCellValue(Constants.NONBILLABLE_SHADOW);
				}
				if (allocationTypeSet.contains(Constants.NONBILLABLE_TRAINEE)) {
					cell1 = row1.createCell(columnCount++);
					cell1.setCellStyle(style_Grey1);
					cell1.setCellValue(Constants.NONBILLABLE_TRAINEE);
				}
				if (allocationTypeSet.contains(Constants.NONBILLABLE_TRANSITION)) {
					cell1 = row1.createCell(columnCount++);
					cell1.setCellStyle(style_Grey1);
					cell1.setCellValue(Constants.NONBILLABLE_TRANSITION);
				}
				if (allocationTypeSet.contains(Constants.NONBILLABLE_UNALLOCATED)) {
					cell1 = row1.createCell(columnCount++);
					cell1.setCellStyle(style_Grey1);
					cell1.setCellValue(Constants.NONBILLABLE_UNALLOCATED);
				}

				if (allocationTypeSet.contains(Constants.NONBILLABLE_ABSCONDING)) {
					cell1 = row1.createCell(columnCount++);
					cell1.setCellStyle(style_Grey1);
					cell1.setCellValue(Constants.NONBILLABLE_ABSCONDING);
				}
				if (allocationTypeSet.contains(Constants.NONBILLABLE_ACCOUNT_MANAGEMENT)) {
					cell1 = row1.createCell(columnCount++);
					cell1.setCellStyle(style_Grey1);
					cell1.setCellValue(Constants.NONBILLABLE_ACCOUNT_MANAGEMENT);
				}
				if (allocationTypeSet.contains(Constants.NONBILLABLE_INSIDESALE)) {
					cell1 = row1.createCell(columnCount++);
					cell1.setCellStyle(style_Grey1);
					cell1.setCellValue(Constants.NONBILLABLE_INSIDESALE);
				}
				if (allocationTypeSet.contains(Constants.NONBILLABLE_INVESTMENT)) {
					cell1 = row1.createCell(columnCount++);
					cell1.setCellStyle(style_Grey1);
					cell1.setCellValue(Constants.NONBILLABLE_INVESTMENT);
				}
				if (allocationTypeSet.contains(Constants.NONBILLABLE_LONGLEAVE)) {
					cell1 = row1.createCell(columnCount++);
					cell1.setCellStyle(style_Grey1);
					cell1.setCellValue(Constants.NONBILLABLE_LONGLEAVE);
				}
				if (allocationTypeSet.contains(Constants.NONBILLABLE_MANAGEMENT)) {
					cell1 = row1.createCell(columnCount++);
					cell1.setCellStyle(style_Grey1);
					cell1.setCellValue(Constants.NONBILLABLE_MANAGEMENT);
				}
				if (allocationTypeSet.contains(Constants.NONBILLABLE_PMO)) {
					cell1 = row1.createCell(columnCount++);
					cell1.setCellStyle(style_Grey1);
					cell1.setCellValue(Constants.NONBILLABLE_PMO);
				}
				if (allocationTypeSet.contains(Constants.NONBILLABLE_PRESALE)) {
					cell1 = row1.createCell(columnCount++);
					cell1.setCellStyle(style_Grey1);
					cell1.setCellValue(Constants.NONBILLABLE_PRESALE);
				}
				if (allocationTypeSet.contains(Constants.NONBILLABLE_SALES)) {
					cell1 = row1.createCell(columnCount++);
					cell1.setCellStyle(style_Grey1);
					cell1.setCellValue(Constants.NONBILLABLE_SALES);
				}

				if (allocationTypeSet.contains(Constants.NONBILLABLE_EXITRELEASE)) {
					cell1 = row1.createCell(columnCount++);
					cell1.setCellStyle(style_Grey1);
					cell1.setCellValue(Constants.NONBILLABLE_EXITRELEASE);
				}
				if (allocationTypeSet.contains(Constants.PIP)) {
					cell1 = row1.createCell(columnCount++);
					cell1.setCellStyle(style_Grey1);
					cell1.setCellValue(Constants.PIP);
				}

				cell1 = row1.createCell(columnCount++);

				cell1.setCellStyle(style_Grey1);
				cell1.setCellValue("Grand Total");

				setHeaderSize(clienSheet);

				Field[] fields = MuneerReportDto.class.getDeclaredFields();

				Iterator<Entry<String, Map<String, Integer>>> entries = clientData.entrySet().iterator();

				List<MuneerReportDto> muneerlist = new LinkedList<MuneerReportDto>();

				while (entries.hasNext()) {

					row1 = clienSheet.createRow(++rowCount);
					Integer grandTotal = 0;
					columnCount = 0;

					Map.Entry<String, Map<String, Integer>> entry = (Map.Entry<String, Map<String, Integer>>) entries.next();
					String key = (String) entry.getKey();
					cell1 = row1.createCell(columnCount++);
					cell1.setCellValue(key);
					cell1.setCellStyle(style_Orange2);
					clienSheet.setColumnWidth(0, 10000);
					MuneerReportDto muneerReportDto = (MuneerReportDto) entry.getValue();
					muneerlist.add(muneerReportDto);

					for (int i = 0; i < fields.length; i++) {
						String methodName = getMethodName(fields[i]);
						int count = (Integer) muneerReportDto.getClass().getMethod(methodName).invoke(muneerReportDto, (Object[]) null);
						if (count >= 0) {
							cell1 = row1.createCell(columnCount++);
							cell1.setCellValue((Integer) muneerReportDto.getClass().getMethod(methodName).invoke(muneerReportDto, (Object[]) null));
							cell1.setCellStyle(style_Orange2);
							grandTotal = grandTotal + count;
						}

					}
					cell1 = row1.createCell(columnCount++);
					cell1.setCellValue(grandTotal);
					cell1.setCellStyle(style_Orange2);
				}

				row1 = clienSheet.createRow(++rowCount);
				columnCount = 0;
				cell1 = row1.createCell(columnCount++);
				cell1.setCellValue("Grand Total");
				cell1.setCellStyle(style_Grey1);
				int totalCount = 0;
				for (int i = 0; i < fields.length; i++) {
					String methodName = getMethodName(fields[i]);
					int colmnCount = 0;
					for (int z = 0; z < muneerlist.size(); z++) {
						MuneerReportDto dto = muneerlist.get(z);
						int count = (Integer) dto.getClass().getMethod(methodName).invoke(dto, (Object[]) null);
						colmnCount = colmnCount + count;
					}
					if (colmnCount >= 0) {
						cell1 = row1.createCell(columnCount++);
						cell1.setCellStyle(style_Grey1);
						cell1.setCellValue(colmnCount);
						totalCount = totalCount + colmnCount;
					}

				}
				cell1 = row1.createCell(columnCount++);
				cell1.setCellStyle(style_Grey1);
				cell1.setCellValue(totalCount);

			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.debug(e.getMessage());
		}
		logger.debug("End buildExcelDocument in RMReportExcelView Class  ");
	}

	private static void setHeaderSize(HSSFSheet spreadSheet) {

		HSSFRow row = spreadSheet.getRow(1);

		for (int columnPosition = 0; columnPosition < row.getLastCellNum(); columnPosition++) {
			spreadSheet.autoSizeColumn((short) (columnPosition));

		}
	}

	private CellStyle style_Grey(HSSFWorkbook workbook) {

		CellStyle style = workbook.createCellStyle();
		font(workbook, style);

		borderStyle(style);

		HSSFPalette palette = workbook.getCustomPalette();

		palette.setColorAtIndex(new Byte((byte) 47), new Byte((byte) 191), new Byte((byte) 191), new Byte((byte) 191));

		style.setFillForegroundColor(palette.getColor(47).getIndex());

		return style;
	}

	private CellStyle style_Blue(HSSFWorkbook workbook) {

		CellStyle style = workbook.createCellStyle();

		borderStyle(style);

		HSSFPalette palette = workbook.getCustomPalette();

		palette.setColorAtIndex(new Byte((byte) 46), new Byte((byte) 141), new Byte((byte) 180), new Byte((byte) 226));

		style.setFillForegroundColor(palette.getColor(46).getIndex());

		font(workbook, style);
		return style;
	}

	private CellStyle style_Orange(HSSFWorkbook workbook) {

		CellStyle style = workbook.createCellStyle();
		font(workbook, style);

		borderStyle(style);

		HSSFPalette palette = workbook.getCustomPalette();

		palette.setColorAtIndex(new Byte((byte) 42), new Byte((byte) 252), new Byte((byte) 213), new Byte((byte) 180));

		style.setFillForegroundColor(palette.getColor(42).getIndex());

		return style;
	}

	private CellStyle style_Green(HSSFWorkbook workbook) {

		CellStyle style = workbook.createCellStyle();
		font(workbook, style);

		borderStyle(style);

		HSSFPalette palette = workbook.getCustomPalette();

		palette.setColorAtIndex(new Byte((byte) 43), new Byte((byte) 196), new Byte((byte) 215), new Byte((byte) 155));

		style.setFillForegroundColor(palette.getColor(43).getIndex());

		return style;
	}

	private CellStyle style_Yellow(HSSFWorkbook workbook) {

		CellStyle style = workbook.createCellStyle();
		font(workbook, style);

		borderStyle(style);

		HSSFPalette palette = workbook.getCustomPalette();

		palette.setColorAtIndex(new Byte((byte) 44), new Byte((byte) 255), new Byte((byte) 255), new Byte((byte) 0));

		style.setFillForegroundColor(palette.getColor(44).getIndex());

		return style;
	}

	private CellStyle style_Pink(HSSFWorkbook workbook) {

		CellStyle style = workbook.createCellStyle();
		font(workbook, style);

		borderStyle(style);

		HSSFPalette palette = workbook.getCustomPalette();

		palette.setColorAtIndex(new Byte((byte) 45), new Byte((byte) 230), new Byte((byte) 184), new Byte((byte) 183));

		style.setFillForegroundColor(palette.getColor(45).getIndex());

		return style;
	}

	private CellStyle style_Brown(HSSFWorkbook workbook, HSSFRow headerRow) {

		CellStyle style = workbook.createCellStyle();

		font(workbook, style);

		borderStyle(style);

		HSSFPalette palette = workbook.getCustomPalette();

		palette.setColorAtIndex(new Byte((byte) 41), new Byte((byte) 210), new Byte((byte) 180), new Byte((byte) 140));

		style.setFillForegroundColor(palette.getColor(41).getIndex());

		for (int i = 0; i < headerRow.getLastCellNum(); i++) {// For each cell
			// in the row
			headerRow.getCell(i).setCellStyle(style);// Set the
														// style
		}
		return style;
	}

	private CellStyle style_Grey2(HSSFWorkbook workbook) {

		CellStyle style = workbook.createCellStyle();

		font(workbook, style);

		borderStyle(style);

		HSSFPalette palette = workbook.getCustomPalette();

		palette.setColorAtIndex(new Byte((byte) 48), new Byte((byte) 220), new Byte((byte) 230), new Byte((byte) 241));

		style.setFillForegroundColor(palette.getColor(48).getIndex());

		return style;
	}

	private CellStyle style_Orange2(HSSFWorkbook workbook) {

		CellStyle style = workbook.createCellStyle();

		font(workbook, style);

		borderStyle(style);

		HSSFPalette palette = workbook.getCustomPalette();

		palette.setColorAtIndex(new Byte((byte) 49), new Byte((byte) 244), new Byte((byte) 164), new Byte((byte) 96));

		style.setFillForegroundColor(palette.getColor(49).getIndex());

		return style;
	}

	private void borderStyle(CellStyle style) {

		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
	}

	private void font(HSSFWorkbook workbook, CellStyle style) {

		Font font = workbook.createFont();
		font.setFontName("Calibri");

		font.setFontHeightInPoints((short) 10);// Create font
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);// Make font bold
		style.setFont(font);
	}

	static String getMethodName(Field oldBOL) {

		String methodName = "get" + oldBOL.getName().substring(0, 1).toUpperCase() + oldBOL.getName().substring(1);
		if (oldBOL.getType().equals(boolean.class)) {
			methodName = "is" + oldBOL.getName().substring(0, 1).toUpperCase() + oldBOL.getName().substring(1);
		}
		return methodName;
	}

}
