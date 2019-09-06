package org.yash.rms.excel;

import java.math.BigDecimal;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.springframework.web.servlet.view.document.AbstractExcelView;
import org.yash.rms.util.Constants;

public class CustomizeReportExcelUtility extends AbstractExcelView {
	


	public static HSSFWorkbook generateExcelForPLRport(
			Map<String, List<Object[]>> dataMap) {

		// Create Workbook
		HSSFWorkbook workbook = new HSSFWorkbook();
		
		generateDashboard((HashMap<String, List<Integer>>)dataMap.get("CountOfEachMonth"),workbook);	
		generateBUResourceSheet(dataMap.get("ResourceOnBenchStartOfMonthReport"), workbook,"Month Start Bench");
		generateBUResourceSheet(dataMap.get("ResourceOnBenchEndOfMonthReport"),workbook, "Month End Bench");
		generateBUResourceSheet(dataMap.get("ResourceOnBenchMaxOfMonthReport"),workbook, "Month Max Bench");
		generateBUResourceSheet(dataMap.get("ResourceAllocatedFromBenchToProject"), workbook,"Bench To Project");
		generateBUResourceSheet(dataMap.get("BackToBackFullfillmentForReport"), workbook,"Back To Back Fullfillment");
		generateBUResourceSheet(dataMap.get("NewJoineeOnBenchForReport"),workbook, "New Joinee On Bench");
		generateBUResourceSheet(dataMap.get("ReleasedResource"),workbook, "Released Resource");
		generateBUTransferResourceSheet(dataMap.get("ResourceTransferToBG4BU5"),workbook, "Transfer To BG4BU5");
		generateBUTransferResourceSheet(dataMap.get("ResourceTransferFromBG4BU5"),workbook, "Transfer From BG4BU5");


		
		return workbook;
	}

	private static void generateBUResourceSheet(List<Object[]> buReportList,
			HSSFWorkbook workbook, String sheetName) {

		// Create a blank BuReportSpreadsheet borrowedresourceList
		HSSFSheet buResourcePLReportSpreadsheet = workbook
				.createSheet(sheetName);

		// Create row object
		HSSFRow rowBUResource;

		// This data needs to be written (Object[])
		int rowidBUResource = 0;
		int cellidBUResource = 0;

		CellStyle styleLemonChiffon = backgroundLemonChiffon(workbook);

		CellStyle styleBlue = backgroundBlue(workbook);
		CellStyle styleGreen = backgroundGreen(workbook);
		CellStyle styleGray = backgroundGray(workbook);
		CellStyle styleYellow = backgroundYellow(workbook);

		// Header

		HSSFRow headerRowBUResource1 = buResourcePLReportSpreadsheet
				.createRow(0);
		rowidBUResource++;

		Cell cellEmployeeID = headerRowBUResource1.createCell(0);
		cellEmployeeID.setCellValue("Yash Employee ID");
		cellEmployeeID.setCellStyle(styleLemonChiffon);

		Cell cellEmployeeName = headerRowBUResource1.createCell(1);
		cellEmployeeName.setCellValue("Employee Name ");
		cellEmployeeName.setCellStyle(styleLemonChiffon);

		Cell cellDesignation = headerRowBUResource1.createCell(2);
		cellDesignation.setCellValue("Designation");
		cellDesignation.setCellStyle(styleBlue);

		Cell cellBGBU = headerRowBUResource1.createCell(3);
		cellBGBU.setCellValue("  BG-BU Group ");
		cellBGBU.setCellStyle(styleBlue);

		Cell cellProject = headerRowBUResource1.createCell(4);
		cellProject.setCellValue("Project Name");
		cellProject.setCellStyle(styleBlue);

		Cell cellSkills = headerRowBUResource1.createCell(5);
		cellSkills.setCellValue("Skills");
		cellSkills.setCellStyle(styleGreen);

		Cell cellBaseLocation = headerRowBUResource1.createCell(6);
		cellBaseLocation.setCellValue("Base Location");
		cellBaseLocation.setCellStyle(styleGray);

		Cell cellDeploymentLocation = headerRowBUResource1.createCell(7);
		cellDeploymentLocation.setCellValue("Deployment Location");
		cellDeploymentLocation.setCellStyle(styleGray);

		Cell cellClient = headerRowBUResource1.createCell(8);
		cellClient.setCellValue("Client (If Known)");
		cellClient.setCellStyle(styleGreen);

		Cell cellDOJ = headerRowBUResource1.createCell(9);
		cellDOJ.setCellValue("Date Of Joining (DOJ)");
		cellDOJ.setCellStyle(styleGreen);

		Cell cellGrade = headerRowBUResource1.createCell(10);
		cellGrade.setCellValue("Grade");
		cellGrade.setCellStyle(styleGreen);

		Cell cellSDate = headerRowBUResource1.createCell(11);
		cellSDate.setCellValue("Allocation Start Date");
		cellSDate.setCellStyle(styleGreen);

		Cell cellEDate = headerRowBUResource1.createCell(12);
		cellEDate.setCellValue("Allocation End Date");
		cellEDate.setCellStyle(styleGreen);

		Cell cellAllocationType = headerRowBUResource1.createCell(13);
		cellAllocationType.setCellValue("Allocation Type");
		cellAllocationType.setCellStyle(styleYellow);

		Cell cellStartDate = headerRowBUResource1.createCell(14);
		cellStartDate.setCellValue("Start Date");
		cellStartDate.setCellStyle(styleYellow);

		Cell cellEndDate = headerRowBUResource1.createCell(15);
		cellEndDate.setCellValue("End Date");
		cellEndDate.setCellStyle(styleYellow);

		Cell cellMonth = headerRowBUResource1.createCell(16);
		cellMonth.setCellValue("Month");
		cellMonth.setCellStyle(styleYellow);
		
		if(sheetName.equals("Transfer To BG4BU5") || sheetName.equals("Transfer From BG4BU5")){
			Cell cellPreviousBgBu = headerRowBUResource1.createCell(17);
			cellPreviousBgBu.setCellValue("Previous BG-BU Group");
			cellPreviousBgBu.setCellStyle(styleYellow);
		}
		
		
		setHeaderSize(buResourcePLReportSpreadsheet);

		CellStyle setFont = setFontStyleHeader(workbook);

		// write data in cells//
		for (Object[] buReport : buReportList) {
			String id = (String) buReport[0];
			String name = (String) buReport[1];
			String designation = (String) buReport[2];
			String bgbu = (String) buReport[3];
			String projectName = (String) buReport[4];
			String skill = (String) buReport[5];
			String baseLocation = (String) buReport[6];
			String currentLocation = (String) buReport[7];
			String customerName = (String) buReport[8];
			Date DOJ = (Date) buReport[9];
			String grade = (String) buReport[10];

			Date allocationStartDate = (Date) buReport[11];
			Date allocationEndDate = (Date) buReport[12];
			String allocationType = (String) buReport[13];

			Date startDate = (Date) buReport[14];
			Date endDate = (Date) buReport[15];

			String month = (String) buReport[16];
			
			String previousBgBu="";
			if(sheetName.equals("Transfer To BG4BU5") || sheetName.equals("Transfer From BG4BU5"))
				 previousBgBu = (String) buReport[19];

			cellidBUResource = 0;
			rowBUResource = buResourcePLReportSpreadsheet
					.createRow(rowidBUResource++);

			Cell cell = rowBUResource.createCell(cellidBUResource++);
			cell.setCellValue(id);
			cell.setCellStyle(setFont);

			Cell cell1 = rowBUResource.createCell(cellidBUResource++);
			cell1.setCellValue(name);
			cell1.setCellStyle(setFont);

			Cell cell2 = rowBUResource.createCell(cellidBUResource++);
			cell2.setCellValue(designation);
			cell2.setCellStyle(setFont);

			Cell cell3 = rowBUResource.createCell(cellidBUResource++);
			cell3.setCellValue(bgbu);
			cell3.setCellStyle(setFont);

			Cell cell4 = rowBUResource.createCell(cellidBUResource++);
			cell4.setCellValue(projectName);
			cell4.setCellStyle(setFont);

			Cell cell5 = rowBUResource.createCell(cellidBUResource++);
			cell5.setCellValue(skill);
			cell5.setCellStyle(setFont);

			Cell cell6 = rowBUResource.createCell(cellidBUResource++);
			cell6.setCellValue(baseLocation);
			cell6.setCellStyle(setFont);

			Cell cell7 = rowBUResource.createCell(cellidBUResource++);
			cell7.setCellValue(currentLocation);
			cell7.setCellStyle(setFont);

			Cell cell8 = rowBUResource.createCell(cellidBUResource++);
			cell8.setCellValue(customerName);
			cell8.setCellStyle(setFont);

			Cell cell9 = rowBUResource.createCell(cellidBUResource++);
			cell9.setCellValue(Constants.DATE_FORMAT_REPORTS.format(DOJ));
			cell9.setCellStyle(setFont);

			Cell cell10 = rowBUResource.createCell(cellidBUResource++);
			cell10.setCellValue(grade);
			cell10.setCellStyle(setFont);

			Cell cell11 = rowBUResource.createCell(cellidBUResource++);
			cell11.setCellValue(Constants.DATE_FORMAT_REPORTS
					.format(allocationStartDate));
			cell11.setCellStyle(setFont);

			Cell cell12 = rowBUResource.createCell(cellidBUResource++);
			cell12.setCellValue(Constants.DATE_FORMAT_REPORTS
					.format(allocationEndDate));
			cell12.setCellStyle(setFont);

			Cell cell13 = rowBUResource.createCell(cellidBUResource++);
			cell13.setCellValue(allocationType);
			cell13.setCellStyle(setFont);

			Cell cell14 = rowBUResource.createCell(cellidBUResource++);
			cell14.setCellValue(Constants.DATE_FORMAT_REPORTS.format(startDate));
			cell14.setCellStyle(setFont);

			Cell cell15 = rowBUResource.createCell(cellidBUResource++);

			if (endDate != null) {

				cell15.setCellValue(Constants.DATE_FORMAT_REPORTS
						.format(endDate));
				cell15.setCellStyle(setFont);
			}

			Cell cell16 = rowBUResource.createCell(cellidBUResource++);
			cell16.setCellValue(month);
			cell16.setCellStyle(setFont);
			
			if(sheetName.equals("Transfer To BG4BU5") || sheetName.equals("Transfer From BG4BU5")){
				Cell cell17 = rowBUResource.createCell(cellidBUResource++);
				cell17.setCellValue(previousBgBu.substring(previousBgBu.lastIndexOf(',') + 1).trim());
				cell17.setCellStyle(setFont);
			}
		}

	}
	
	//private static void generateDashboard(List<Object[]> CountList, HSSFWorkbook workbook) {
		private static void generateDashboard(HashMap<String, List<Integer>> count, HSSFWorkbook workbook) {

	
		String months[]={"January","February","March","April","May","June","July","August","September","October","November","December"};
		HSSFSheet customizeReportSpreadsheet = workbook.createSheet("Dashboard");

				
		int rowidDashboard = 0;
		//int cellidDashboard = 0;
		
		List<Integer> OnBenchStartCount = count.get("OnBenchStart");
		List<Integer> OnBenchMaxCount = count.get("OnBenchMax");
		List<Integer> OnBenchEndCount = count.get("OnBenchEnd");	
		List<Integer> benchToProjectCount = count.get("benchToProject");
		List<Integer> newJoineeOnBenchCount = count.get("newJoineeOnBench");
		List<Integer> newJoineeDirectAllocationCount = count.get("newJoineeDirectAllocation");
		List<Integer> releasedResource = count.get("releasedResource");
		List<Integer> resourceTransferToBG4BU5 = count.get("resourceTransferToBG4BU5");
		List<Integer> resourceTransferFromBG4BU5 = count.get("resourceTransferFromBG4BU5");
		
		CellStyle setFont = styleForDataRow(workbook);
		CellStyle styleGreen = backgroundGreen(workbook);

		HSSFRow headerRowDashboard = customizeReportSpreadsheet.createRow(0);
		rowidDashboard++;

		Cell cellMonth = headerRowDashboard.createCell(0);
		cellMonth.setCellValue("Month");
		cellMonth.setCellStyle(styleGreen);

		Cell cellBenchStart = headerRowDashboard.createCell(1);
		cellBenchStart.setCellValue("Bench-Month Start");
		cellBenchStart.setCellStyle(styleGreen);

		Cell cellBenchMonthMax = headerRowDashboard.createCell(2);
		cellBenchMonthMax.setCellValue("Bench-Month Max");
		cellBenchMonthMax.setCellStyle(styleGreen);
		
		Cell cellBenchMonthEnd = headerRowDashboard.createCell(3);
		cellBenchMonthEnd.setCellValue("Bench-Month End");
		cellBenchMonthEnd.setCellStyle(styleGreen);
		

		Cell cellBGBU = headerRowDashboard.createCell(4);
		cellBGBU.setCellValue("Allocated from Bench");
		cellBGBU.setCellStyle(styleGreen);

		Cell cellProject = headerRowDashboard.createCell(5);
		cellProject.setCellValue("New Joinee-On Bench");
		cellProject.setCellStyle(styleGreen);

		Cell cellSkills = headerRowDashboard.createCell(6);
		cellSkills.setCellValue("New Joinee-Direct Allocation");
		cellSkills.setCellStyle(styleGreen);

		Cell cellGrade = headerRowDashboard.createCell(7);
		cellGrade.setCellValue("Yash Release-All");
		cellGrade.setCellStyle(styleGreen);
		
		Cell cellDeploymentLocation = headerRowDashboard.createCell(8);
		cellDeploymentLocation.setCellValue("Transfer INTO BG4-BU5");
		cellDeploymentLocation.setCellStyle(styleGreen);

		Cell cellClient = headerRowDashboard.createCell(9);
		cellClient.setCellValue("Transfer FROM BG4-BU5");
		cellClient.setCellStyle(styleGreen);


		setHeaderSize(customizeReportSpreadsheet);
		
		for(int i=0; i< OnBenchStartCount.size(); i++){
			
			HSSFRow dataRow = customizeReportSpreadsheet.createRow(rowidDashboard);
			
			Cell month = dataRow.createCell(0);
			month.setCellValue(  (months[i]));
			month.setCellStyle(setFont);
			
			
			Cell OnBenchStart = dataRow.createCell(1);
			OnBenchStart.setCellValue(OnBenchStartCount.isEmpty() ? 0 : OnBenchStartCount.get(i));
			OnBenchStart.setCellStyle(setFont);
			
			
			Cell onBenchEnd = dataRow.createCell(2);
			onBenchEnd.setCellValue(OnBenchMaxCount.isEmpty() ? 0 : OnBenchMaxCount.get(i));	
			onBenchEnd.setCellStyle(setFont);
			
			
			Cell onBenchMax = dataRow.createCell(3);
			onBenchMax.setCellValue(OnBenchEndCount.isEmpty() ? 0 : OnBenchEndCount.get(i) );
			onBenchMax.setCellStyle(setFont);
			
			 
			Cell benchToProject = dataRow.createCell(4);
			benchToProject.setCellValue(benchToProjectCount.isEmpty() ? 0 : benchToProjectCount.get(i));
			benchToProject.setCellStyle(setFont);
			
			Cell newJoineeDirectAllocation = dataRow.createCell(5);
			newJoineeDirectAllocation.setCellValue(newJoineeOnBenchCount.isEmpty() ? 0 : newJoineeOnBenchCount.get(i));
			newJoineeDirectAllocation.setCellStyle(setFont);
			
			Cell newJoineeOnBench = dataRow.createCell(6);
			newJoineeOnBench.setCellValue(newJoineeDirectAllocationCount.isEmpty() ? 0 : newJoineeDirectAllocationCount.get(i) );
			newJoineeOnBench.setCellStyle(setFont);
			
			
			Cell releasedResources = dataRow.createCell(7);
			releasedResources.setCellValue(releasedResource.isEmpty() ? 0 : releasedResource.get(i));
			releasedResources.setCellStyle(setFont); 
			
			Cell resourceTransferToBG4BU5cell = dataRow.createCell(8);
			resourceTransferToBG4BU5cell.setCellValue(resourceTransferToBG4BU5.isEmpty() ? 0 : resourceTransferToBG4BU5.get(i));
			resourceTransferToBG4BU5cell.setCellStyle(setFont); 
			
			Cell resourceTransferFromBG4BU5cell = dataRow.createCell(9);
			resourceTransferFromBG4BU5cell.setCellValue(resourceTransferFromBG4BU5.isEmpty() ? 0 : resourceTransferFromBG4BU5.get(i));
			resourceTransferFromBG4BU5cell.setCellStyle(setFont);
			
			rowidDashboard++;
			
		}
}
	
	

	private static void setHeaderSize(HSSFSheet spreadSheet) {

		HSSFRow row = spreadSheet.getRow(0);

		for (int columnPosition = 0; columnPosition < row.getLastCellNum(); columnPosition++) {
			spreadSheet.autoSizeColumn((short) (columnPosition));

		}
	}

	private static void borderStyle(CellStyle style) {

		style.setFillBackgroundColor(IndexedColors.BLACK.getIndex());
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
	}

	private static CellStyle setFontStyleHeader(HSSFWorkbook workbook) {

		CellStyle setFont = workbook.createCellStyle();

		Font font = workbook.createFont();
		font.setFontName("Calibri");
		
		font.setFontHeightInPoints((short) 12);// Create font
		setFont.setFont(font);

		return setFont;
	}

	private static CellStyle backgroundGreen(HSSFWorkbook workbook) {

		CellStyle style = workbook.createCellStyle();

		setFontStyleRow(workbook, style);

		borderStyle(style);

		style.setFillForegroundColor(HSSFColor.LIGHT_GREEN.index);

		return style;
	}

	private static void setFontStyleRow(HSSFWorkbook workbook,
			CellStyle styleBUReport) {

		Font font = workbook.createFont();
		font.setFontName("Calibri");

		font.setFontHeightInPoints((short) 11);// Create font
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);// Make font bold

		styleBUReport.setFont(font);
	}

	private static CellStyle backgroundYellow(HSSFWorkbook workbook) {

		CellStyle style = workbook.createCellStyle();

		setFontStyleRow(workbook, style);

		borderStyle(style);

		style.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);

		return style;
	}

	private static CellStyle backgroundGray(HSSFWorkbook workbook) {

		CellStyle style = workbook.createCellStyle();

		setFontStyleRow(workbook, style);

		borderStyle(style);

		style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);

		return style;
	}

	private static CellStyle backgroundLemonChiffon(HSSFWorkbook workbook) {

		CellStyle style = workbook.createCellStyle();

		setFontStyleRow(workbook, style);

		borderStyle(style);

		style.setFillForegroundColor(HSSFColor.LEMON_CHIFFON.index);

		return style;
	}

	private static CellStyle backgroundBlue(HSSFWorkbook workbook) {

		CellStyle style = workbook.createCellStyle();

		setFontStyleRow(workbook, style);

		borderStyle(style);

		style.setFillForegroundColor(HSSFColor.CORNFLOWER_BLUE.index);

		return style;
	}
	
	private static CellStyle styleForDataRow(HSSFWorkbook workbook) {

		CellStyle style = workbook.createCellStyle();

		setFontStyleRow(workbook, style);

		borderStyle(style);

		style.setFillForegroundColor(HSSFColor.WHITE.index);

		return style;
	}

	@Override
	protected void buildExcelDocument(Map<String, Object> model,
			HSSFWorkbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		logger.debug("----------Inside PLReportExcelUtility Start buildExcelDocument-----------");

		@SuppressWarnings("unchecked")
		Map<String, List<Object[]>> dataMap = (Map<String, List<Object[]>>) model
				.get("report");

		response.reset();
		//response.setHeader("Expires:", "0");

		if (dataMap != null) {

			try {

				HSSFWorkbook workBook = CustomizeReportExcelUtility
						.generateExcelForPLRport(dataMap);

				response.setContentType("application/vnd.ms-excel");
				response.setHeader("Content-Disposition",
						"attachment; filename=CustomizedReportForBG4_BU5.xls");

				// response.setCharacterEncoding(DEFAULT_CONTENT_TYPE);
				workBook.write(response.getOutputStream());

			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		logger.debug("----------Inside PLReportExcelUtility End buildExcelDocument-----------");

	}
	
	
	private static void generateBUTransferResourceSheet(List<Object[]> buReportList,
			HSSFWorkbook workBook, String sheetName) {

		HSSFSheet resourceCustomizedReportSheet = workBook.createSheet(sheetName);
		// Create row object
		HSSFRow rowBUResource;
		int rowidBUResource = 0;
		CellStyle styleBlue = backgroundBlue(workBook);
			HSSFRow headerRowBUResource1 = resourceCustomizedReportSheet.createRow(0);
			rowidBUResource++;
			TransferHeader[] headers = TransferHeader.values();
			CellStyle setFont = setFontStyleHeader(workBook);
			
			for (int j = 0; j < buReportList.size(); j++) {
				Object[] buReport = buReportList.get(j);
				rowBUResource = resourceCustomizedReportSheet.createRow(rowidBUResource++);
				for (int i = 0; i < buReport.length; i++) {
					//resourceCustomizedReportSheet.autoSizeColumn(i);
					if(j==0){
					Cell cellEmployeeID = headerRowBUResource1.createCell(i);
					cellEmployeeID.setCellValue(headers[i].headerName());
					cellEmployeeID.setCellStyle(styleBlue);
					}
					Cell cell = rowBUResource.createCell(i);
					setCellValueAndStyle(cell ,buReport[i], setFont,i);
				}
			}
			
	}

	private static void setCellValueAndStyle(Cell dataCell, Object dataObj, CellStyle setFont, int cellNumber) {
		
		if (dataObj instanceof Integer) {
			Integer objInteger = (Integer) dataObj;
			dataCell.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
			dataCell.setCellValue(objInteger.intValue());
			dataCell.setCellStyle(setFont);
		} else if (dataObj instanceof Date) {
			Date objDate = (Date) dataObj;
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
			String dateToStr = sdf.format(objDate);
			dataCell.setCellValue(dateToStr);
			dataCell.setCellStyle(setFont);
		} else if (dataObj instanceof String) {
			String objString = (String) dataObj;
			dataCell.setCellType(XSSFCell.CELL_TYPE_STRING);
			if(cellNumber == 3)
				dataCell.setCellValue(objString.substring(objString.lastIndexOf(',') + 1).trim());
			else
			dataCell.setCellValue(objString);
			dataCell.setCellStyle(setFont);
		} else if (dataObj instanceof Boolean) {
			Boolean objBoolean = (Boolean) dataObj;
			dataCell.setCellType(XSSFCell.CELL_TYPE_BOOLEAN);
			dataCell.setCellValue(objBoolean.booleanValue());
			dataCell.setCellStyle(setFont);
		} else if (dataObj instanceof Double) {
			Double objDoble = (Double) dataObj;
			dataCell.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
			dataCell.setCellValue(objDoble.doubleValue());
			dataCell.setCellStyle(setFont);
		} else if (dataObj instanceof BigDecimal) {
			BigDecimal objDoble = (BigDecimal) dataObj;
			dataCell.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
			dataCell.setCellValue(objDoble.doubleValue());
			dataCell.setCellStyle(setFont);
		} else {
			String objString = (String) dataObj;
			dataCell.setCellType(XSSFCell.CELL_TYPE_BLANK);
			dataCell.setCellValue(objString);
			dataCell.setCellStyle(setFont);
		}
	}
	private static CellStyle backGroundBlueWithWrapText(HSSFWorkbook workbook) {

		CellStyle style = workbook.createCellStyle();
		setFontStyleRow(workbook, style);
		borderStyle(style);
		style.setWrapText(true);
		style.setFillForegroundColor(HSSFColor.CORNFLOWER_BLUE.index);
		return style;
	}

}
	enum TransferHeader {

		Yash_Employee_ID("Yash Employee ID"),
		Employee_Name("Employee Name"),
		Designation("Designation"),
		Previous_BG_BU("Previous BG-BU"),
		CURRENT_BG_BU_GROUP("Current BG-BU Group"),
		Transfer_Month("Transfer Month"),
		Project_Name("Project Name"),
		Skills("Skills"),
		Base_Location("Base Location"),
		Deployment_Location("Deployment Location"),
		Client("Client"),
		Current_Allocation_Type("Current Allocation Type"),
		Transfer_Date("Transfer Date"),
		Project_Allocation_Start_Date("Project Allocation Start Date"),
		Project_Allocation_End_Date("Project Allocation End Date");
		
	    private String headerName;
	    TransferHeader(String headerName) {
	        this.headerName = headerName;
	    }
	    public String headerName() {
	        return headerName;
	    }

}


