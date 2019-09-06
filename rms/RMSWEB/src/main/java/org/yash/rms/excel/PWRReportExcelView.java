package org.yash.rms.excel;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.view.document.AbstractExcelView;
import org.yash.rms.report.dto.PWRReport;

public class PWRReportExcelView extends AbstractExcelView {

	private static final Logger logger = LoggerFactory.getLogger(PWRReportExcelView.class);

	@SuppressWarnings("unchecked")
	@Override
	protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, HttpServletRequest request, HttpServletResponse response) throws Exception {

		logger.debug("Started buildExcelDocument in PWRReportExcelView Class  ");

		response.setHeader("Content-Disposition", "attachment; filename=Project_Wise_Resource_Report.xls");

		Map<String, Object> pwrReportMap = (Map<String, Object>) model.get("model");

		Set<PWRReport> view1data = (Set<PWRReport>) pwrReportMap.get("pwrReportView1");

		Map<String, List<PWRReport>> view2data = (Map<String, List<PWRReport>>) pwrReportMap.get("pwrReportView2");

		CellStyle style = styleHeader(workbook);

		HSSFSheet excelSheet = workbook.createSheet("Project_Wise_Report_View1");

		setExcelHeaderView1(excelSheet, style);

		setExcelRowsView1(workbook, excelSheet, view1data);

		excelSheet = workbook.createSheet("Project_Wise_Report_View2");

		setExcelHeaderView2(excelSheet, style);

		setExcelRowsView2(workbook, excelSheet, view2data);

	}

	private CellStyle styleHeader(HSSFWorkbook workbook) {

		CellStyle style = workbook.createCellStyle();// Create style for heading

		Font font = workbook.createFont();
		font.setFontName("Calibri");

		font.setFontHeightInPoints((short) 12);// Create font
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);// Make font bold
		style.setFont(font);// set it to bold

		style.setFillBackgroundColor(IndexedColors.BLACK.getIndex());

		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);

		style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);

		style.setWrapText(true);

		return style;
	}

	private static CellStyle setRowStyleView1(HSSFWorkbook workbook, HSSFRow rowStyle) {

		CellStyle style = workbook.createCellStyle();// Create style
		setFont(workbook, style);

		style.setFillBackgroundColor(IndexedColors.BLACK.getIndex());
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setFillForegroundColor(HSSFColor.WHITE.index);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);

		for (int i = 0; i < rowStyle.getLastCellNum(); i++) {// For each cell
																// in the row
			rowStyle.getCell(i).setCellStyle(style);// Set the style
		}

		return style;
	}

	private CellStyle setRowStyleView2(HSSFWorkbook workbook) {

		CellStyle style = workbook.createCellStyle();// Create style
		setFont(workbook, style);

		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);

		return style;
	}

	private static void setFont(HSSFWorkbook workbook, CellStyle style) {
		Font font = workbook.createFont();
		font.setFontName("Calibri");

		font.setFontHeightInPoints((short) 11);// Create font

		style.setFont(font);
	}

	public void setExcelHeaderView1(HSSFSheet excelSheet, CellStyle style) {

		HSSFRow excelHeader = excelSheet.createRow(0);

		excelHeader.setHeight((short) 400);

		excelHeader.createCell(0).setCellValue(("S.NO").toUpperCase());
		excelHeader.createCell(1).setCellValue(("PROJECT NAME                          ").toUpperCase());
		excelHeader.createCell(2).setCellValue(("Billable (Full Time (FTE))").toUpperCase());
		excelHeader.createCell(3).setCellValue(("Billable (Partial / Shared Pool / Fix Bid Projects)").toUpperCase());

		excelHeader.createCell(4).setCellValue(("Non-Billable (Absconding)").toUpperCase());
		excelHeader.createCell(5).setCellValue(("Non-Billable (Account Management)").toUpperCase());
		excelHeader.createCell(6).setCellValue(("Non-Billable (Blocked)").toUpperCase());
		excelHeader.createCell(7).setCellValue(("Non-Billable (Contingent)").toUpperCase());
		excelHeader.createCell(8).setCellValue(("Non-Billable (Delivery Management)").toUpperCase());
		excelHeader.createCell(9).setCellValue(("Non-Billable (InsideSale)").toUpperCase());
		excelHeader.createCell(10).setCellValue(("Non-Billable (Investment)").toUpperCase());
		excelHeader.createCell(11).setCellValue(("Non-Billable (Long leave)").toUpperCase());

		excelHeader.createCell(12).setCellValue(("Non-Billable (Management)").toUpperCase());
		excelHeader.createCell(13).setCellValue(("Non-Billable (Operations)").toUpperCase());
		excelHeader.createCell(14).setCellValue(("Non-Billable (PMO)").toUpperCase());
		excelHeader.createCell(15).setCellValue(("Non-Billable (PreSale)").toUpperCase());
		excelHeader.createCell(16).setCellValue(("Non-Billable (Sales)").toUpperCase());
		excelHeader.createCell(17).setCellValue(("Non-Billable (Shadow)").toUpperCase());
		excelHeader.createCell(18).setCellValue(("Non-Billable (Trainee)").toUpperCase());
		excelHeader.createCell(19).setCellValue(("Non-Billable (Transition)").toUpperCase());
		excelHeader.createCell(20).setCellValue(("Non-Billable (Unallocated)").toUpperCase());
		excelHeader.createCell(21).setCellValue(("OUTBOUND (Exit/Release)").toUpperCase());
		excelHeader.createCell(22).setCellValue(("PIP").toUpperCase());
		excelHeader.createCell(23).setCellValue(("TOTAL").toUpperCase());

		for (int i = 0; i <= 23; i++) {

			excelHeader.getCell(i).setCellStyle(style);
		}

		HSSFRow row = excelSheet.getRow(0);

		for (int columnPosition = 0; columnPosition < row.getLastCellNum(); columnPosition++) {

			excelSheet.autoSizeColumn((short) (columnPosition));

		}
	}

	public void setExcelRowsView1(HSSFWorkbook workbook, HSSFSheet excelSheet, Set<PWRReport> pwrReportList) {

		int pwrReportRecord = 1;
		int pwrReportRowRecord = 1;

		for (PWRReport pwrReport : pwrReportList) {

			HSSFRow excelRow = excelSheet.createRow(pwrReportRecord++);

			excelRow.createCell(0).setCellValue(pwrReportRowRecord);

			excelRow.createCell(1).setCellValue(pwrReport.getProjectName());

			excelRow.createCell(2).setCellValue(pwrReport.getBillableFullTimeCount());

			excelRow.createCell(3).setCellValue(pwrReport.getBillablePartiaSharedpoolFixbidprojectsCount());

			excelRow.createCell(4).setCellValue(pwrReport.getNonBillableAbscondingCount());

			excelRow.createCell(5).setCellValue(pwrReport.getNonBillableAccountManagementCount());

			excelRow.createCell(6).setCellValue(pwrReport.getNonBillableBlockedCount());

			excelRow.createCell(7).setCellValue(pwrReport.getNonBillableContingentCount());

			excelRow.createCell(8).setCellValue(pwrReport.getNonBillableDeliveryManagementCount());

			excelRow.createCell(9).setCellValue(pwrReport.getNonBillableInsidesaleCount());

			excelRow.createCell(10).setCellValue(pwrReport.getNonBillableInvestmentCount());

			excelRow.createCell(11).setCellValue(pwrReport.getNonBillableLongleaveCount());

			excelRow.createCell(12).setCellValue(pwrReport.getNonBillableManagementCount());

			excelRow.createCell(13).setCellValue(pwrReport.getNonBillableOperationsCount());

			excelRow.createCell(14).setCellValue(pwrReport.getNonBillablePmoCount());

			excelRow.createCell(15).setCellValue(pwrReport.getNonBillablePresaleCount());

			excelRow.createCell(16).setCellValue(pwrReport.getNonBillableSalesCount());

			excelRow.createCell(17).setCellValue(pwrReport.getNonBillableShadowCount());

			excelRow.createCell(18).setCellValue(pwrReport.getNonBillableTraineeCount());

			excelRow.createCell(19).setCellValue(pwrReport.getNonBillableTransitionCount());

			excelRow.createCell(20).setCellValue(pwrReport.getNonBillableUnallocatedCount());

			excelRow.createCell(21).setCellValue(pwrReport.getNonBillableExitreleaseCount());

			excelRow.createCell(22).setCellValue(pwrReport.getPipCount());

			excelRow.createCell(23).setCellValue(pwrReport.getTotal());

			pwrReportRowRecord++;

			setRowStyleView1(workbook, excelRow);

		}

		logger.debug("End buildExcelDocument in PWReportExcelView Class  ");
	}

	public void setExcelHeaderView2(HSSFSheet excelSheet, CellStyle style) {

		HSSFRow excelHeader = excelSheet.createRow(0);

		excelHeader.setHeight((short) 400);

		excelHeader.createCell(0).setCellValue(("S.NO").toUpperCase());
		excelHeader.createCell(1).setCellValue(("PROJECT NAME                                    ").toUpperCase());
		excelHeader.createCell(2).setCellValue(("TOTAL").toUpperCase());
		excelHeader.createCell(3).setCellValue(("EMPLOYEE NAME                            ").toUpperCase());
		excelHeader.createCell(4).setCellValue(("ALLOCATION TYPE                                            ").toUpperCase());

		for (int i = 0; i <= 4; i++) {

			excelHeader.getCell(i).setCellStyle(style);
		}

		HSSFRow row = excelSheet.getRow(0);

		for (int columnPosition = 0; columnPosition < row.getLastCellNum(); columnPosition++) {

			excelSheet.autoSizeColumn((short) (columnPosition));

		}
	}

	public void setExcelRowsView2(HSSFWorkbook workbook, HSSFSheet excelSheet, Map<String, List<PWRReport>> pwrReportList) {

		CellStyle style = setRowStyleView2(workbook);

		int pwrReportRecord = 1;
		int pwrReportRowRecord = 1;

		HSSFRow excelRow = null;

		for (Map.Entry<String, List<PWRReport>> pwrReportListEntry : pwrReportList.entrySet()) {

			excelRow = excelSheet.createRow(pwrReportRecord++);

			HSSFCell cell = excelRow.createCell(0);

			cell.setCellValue(pwrReportRowRecord);
			cell.setCellStyle(style);

			HSSFCell cell1 = excelRow.createCell(1);

			cell1.setCellValue(pwrReportListEntry.getKey());
			cell1.setCellStyle(style);

			HSSFCell cell2 = excelRow.createCell(2);

			cell2.setCellValue(pwrReportListEntry.getValue().size());
			cell2.setCellStyle(style);

			for (PWRReport pwrReportListVal : pwrReportListEntry.getValue()) {

				HSSFCell cell3 = excelRow.createCell(3);

				cell3.setCellValue(pwrReportListVal.getEmployeeName());
				cell3.setCellStyle(style);

				HSSFCell cell4 = excelRow.createCell(4);

				cell4.setCellValue(pwrReportListVal.getAllocationType());
				cell4.setCellStyle(style);

				excelRow = excelSheet.createRow(pwrReportRecord++);

			}

			pwrReportRowRecord++;

		}

	}

}
