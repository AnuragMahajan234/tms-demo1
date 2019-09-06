package org.yash.rms.excel;

import java.sql.Date;
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
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.web.servlet.view.document.AbstractExcelView;
import org.yash.rms.util.Constants;

public class PLReportExcelUtility extends AbstractExcelView {

	public static HSSFWorkbook generateExcelForPLRport(Map<String, List<Object[]>> dataMap) {

		// Create Workbook
		HSSFWorkbook workbook = new HSSFWorkbook();

		generateResignResourceSheet(dataMap.get("resignedResourcePLReportList"), workbook);

		generateBUResourceSheet(dataMap.get("bUResourcesPLReportList"), workbook);

		generateContractResourceSheet(dataMap.get("contractResourcePLReportList"), workbook);

		generateBorrowedResourceSheet(dataMap.get("borrowedResourcesPLReportList"), workbook);

		return workbook;
	}

	private static void generateContractResourceSheet(List<Object[]> contractList, HSSFWorkbook workbook) {

		// Create a blank contractResourcePLReportSpreadsheet
		HSSFSheet contractResourcePLReportSpreadsheet = workbook.createSheet("Resources On Contract");

		// Create row object

		HSSFRow headerRowContractResource;

		headerRowContractResource = contractResourcePLReportSpreadsheet.createRow(0);
		headerRowContractResource.createCell(0).setCellValue("Yash Employee ID");
		headerRowContractResource.createCell(1).setCellValue("Employee Name (As per Payroll Record)");
		headerRowContractResource.createCell(2).setCellValue("BG-BU");
		headerRowContractResource.createCell(3).setCellValue("Skills");
		headerRowContractResource.createCell(4).setCellValue("Designation");
		headerRowContractResource.createCell(5).setCellValue("Joining Date");
		headerRowContractResource.createCell(6).setCellValue("Base Location");
		headerRowContractResource.createCell(7).setCellValue("Project");
		headerRowContractResource.createCell(8).setCellValue("Allocation Start Date");
		headerRowContractResource.createCell(9).setCellValue("Allocation End Date");
		headerRowContractResource.createCell(10).setCellValue("Actual Res Utilization");

		setRowStyle(workbook, headerRowContractResource);

		setHeaderSize(contractResourcePLReportSpreadsheet);

		CellStyle setFont = setFontStyleHeader(workbook);

		// write data in cells//

		int rowidContractResource = 1;
		int cellidContractResource = 0;
		HSSFRow rowContractResource;

		for (Object[] contractReport : contractList) {
			String id = (String) contractReport[0];
			String name = (String) contractReport[1];
			String bgbu = (String) contractReport[2];
			String skill = (String) contractReport[3];
			String designation = (String) contractReport[4];
			Date joiningDate = (Date) contractReport[5];
			String baseLocation = (String) contractReport[6];
			String project = (String) contractReport[7];
			Date startDate = (Date) contractReport[8];
			Date endDate = (Date) contractReport[9];
			Integer resAllocPer =(Integer) contractReport[10];

			cellidContractResource = 0;

			rowContractResource = contractResourcePLReportSpreadsheet.createRow(rowidContractResource++);

			Cell cell = rowContractResource.createCell(cellidContractResource++);
			cell.setCellValue(id);
			cell.setCellStyle(setFont);

			Cell cell1 = rowContractResource.createCell(cellidContractResource++);
			cell1.setCellValue(name);
			cell1.setCellStyle(setFont);

			Cell cell2 = rowContractResource.createCell(cellidContractResource++);
			cell2.setCellValue(bgbu);
			cell2.setCellStyle(setFont);

			Cell cell3 = rowContractResource.createCell(cellidContractResource++);
			cell3.setCellValue(skill);
			cell3.setCellStyle(setFont);

			Cell cell4 = rowContractResource.createCell(cellidContractResource++);
			cell4.setCellValue(designation);
			cell4.setCellStyle(setFont);

			Cell cell5 = rowContractResource.createCell(cellidContractResource++);
			cell5.setCellValue(Constants.DATE_FORMAT_REPORTS.format(joiningDate));
			cell5.setCellStyle(setFont);

			Cell cell6 = rowContractResource.createCell(cellidContractResource++);
			cell6.setCellValue(baseLocation);
			cell6.setCellStyle(setFont);

			Cell cell7 = rowContractResource.createCell(cellidContractResource++);
			cell7.setCellValue(project);
			cell7.setCellStyle(setFont);

			Cell cell8 = rowContractResource.createCell(cellidContractResource++);
			cell8.setCellValue(Constants.DATE_FORMAT_REPORTS.format(startDate));
			cell8.setCellStyle(setFont);

			Cell cell9 = rowContractResource.createCell(cellidContractResource++);
			if (endDate != null) {

				cell9.setCellValue(Constants.DATE_FORMAT_REPORTS.format(endDate));
				cell9.setCellStyle(setFont);
			}
			
			Cell cell10 = rowContractResource.createCell(cellidContractResource++);
			cell10.setCellValue(resAllocPer);
			cell10.setCellStyle(setFont);

		}

	}

	private static void generateResignResourceSheet(List<Object[]> resignList, HSSFWorkbook workbook) {

		// Create a blank resignedResourcePLReportSpreadsheet
		HSSFSheet resignedResourcePLReportSpreadsheet = workbook.createSheet("Resigned Released");

		// Create row object

		CellStyle styleLemonChiffon = backgroundLemonChiffon(workbook);
		CellStyle styleBlue = backgroundBlue(workbook);
		CellStyle styleGreen = backgroundGreen(workbook);
		CellStyle setFont = setFontStyleHeader(workbook);

		HSSFRow headerRowResignedResource = resignedResourcePLReportSpreadsheet.createRow(0);

		Cell cellCoulourStyle = headerRowResignedResource.createCell(0);
		cellCoulourStyle.setCellValue("Yash Employee ID");
		cellCoulourStyle.setCellStyle(styleLemonChiffon);

		Cell cellCoulourStyle1 = headerRowResignedResource.createCell(1);
		cellCoulourStyle1.setCellValue("Employee Name (As per Payroll Records)");
		cellCoulourStyle1.setCellStyle(styleLemonChiffon);

		Cell cellCoulourStyle2 = headerRowResignedResource.createCell(2);
		cellCoulourStyle2.setCellValue("Designation (Group 01)");
		cellCoulourStyle2.setCellStyle(styleBlue);

		Cell cellCoulourStyle3 = headerRowResignedResource.createCell(3);
		cellCoulourStyle3.setCellValue("BG - BU (Group 02)");
		cellCoulourStyle3.setCellStyle(styleBlue);

		Cell cellCoulourStyle4 = headerRowResignedResource.createCell(4);
		cellCoulourStyle4.setCellValue("Project (Group 03)");
		cellCoulourStyle4.setCellStyle(styleBlue);

		Cell cellCoulourStyle5 = headerRowResignedResource.createCell(5);
		cellCoulourStyle5.setCellValue("Grade");
		cellCoulourStyle5.setCellStyle(styleGreen);

		Cell cellCoulourStyle6 = headerRowResignedResource.createCell(6);
		cellCoulourStyle6.setCellValue("Allocation Start Date");
		cellCoulourStyle6.setCellStyle(styleGreen);

		Cell cellCoulourStyle7 = headerRowResignedResource.createCell(7);
		cellCoulourStyle7.setCellValue("Allocation End Date");
		cellCoulourStyle7.setCellStyle(styleGreen);

		Cell cellCoulourStyle8 = headerRowResignedResource.createCell(8);
		cellCoulourStyle8.setCellValue("Release Date");
		cellCoulourStyle8.setCellStyle(styleGreen);
		
		Cell cellCoulourStyle9 = headerRowResignedResource.createCell(9);
		cellCoulourStyle9.setCellValue("Actual Res Utilization");
		cellCoulourStyle9.setCellStyle(styleGreen);

		setHeaderSize(resignedResourcePLReportSpreadsheet);

		// write data in cells//

		int rowidResignedResource = 1;
		int cellidResignedResource = 0;
		HSSFRow rowResignedResource;

		for (Object[] resignedReport : resignList) {
			String id = (String) resignedReport[0];
			String name = (String) resignedReport[1];
			String designation = (String) resignedReport[2];
			String bgbu = (String) resignedReport[3];
			String projectName = (String) resignedReport[4];
			String grade = (String) resignedReport[5];
			Date startDate = (Date) resignedReport[6];
			Date endDate = (Date) resignedReport[7];

			Date releaseDate = (Date) resignedReport[8];
			
			Integer resAllocPercentage =(Integer) resignedReport[9];

			cellidResignedResource = 0;
			rowResignedResource = resignedResourcePLReportSpreadsheet.createRow(rowidResignedResource++);

			Cell cell = rowResignedResource.createCell(cellidResignedResource++);
			cell.setCellValue(id);
			cell.setCellStyle(setFont);

			Cell cell1 = rowResignedResource.createCell(cellidResignedResource++);
			cell1.setCellValue(name);
			cell1.setCellStyle(setFont);

			Cell cell2 = rowResignedResource.createCell(cellidResignedResource++);
			cell2.setCellValue(designation);
			cell2.setCellStyle(setFont);

			Cell cell3 = rowResignedResource.createCell(cellidResignedResource++);
			cell3.setCellValue(bgbu);
			cell3.setCellStyle(setFont);

			Cell cell4 = rowResignedResource.createCell(cellidResignedResource++);
			cell4.setCellValue(projectName);
			cell4.setCellStyle(setFont);

			Cell cell5 = rowResignedResource.createCell(cellidResignedResource++);
			cell5.setCellValue(grade);
			cell5.setCellStyle(setFont);

			Cell cell6 = rowResignedResource.createCell(cellidResignedResource++);
			cell6.setCellValue(Constants.DATE_FORMAT_REPORTS.format(startDate));
			cell6.setCellStyle(setFont);

			Cell cell7 = rowResignedResource.createCell(cellidResignedResource++);
			if (endDate != null) {

				cell7.setCellValue(Constants.DATE_FORMAT_REPORTS.format(endDate));
				cell7.setCellStyle(setFont);
			}

			Cell cell8 = rowResignedResource.createCell(cellidResignedResource++);
			cell8.setCellValue(Constants.DATE_FORMAT_REPORTS.format(releaseDate));
			cell8.setCellStyle(setFont);
			
			Cell cell9 = rowResignedResource.createCell(cellidResignedResource++);
			cell9.setCellValue(resAllocPercentage);
			cell9.setCellStyle(setFont);

		}
	}

	private static void generateBUResourceSheet(List<Object[]> buReportList, HSSFWorkbook workbook) {

		// Create a blank BuReportSpreadsheet borrowedresourceList
		HSSFSheet buResourcePLReportSpreadsheet = workbook.createSheet("BG-BU Resources");

		// Create row object
		HSSFRow rowBUResource;
		HSSFRow headerRowBUResource;

		// This data needs to be written (Object[])
		int rowidBUResource = 0;
		int cellidBUResource = 0;

		// First Header

		headerRowBUResource = buResourcePLReportSpreadsheet.createRow(0);
		rowidBUResource++;

		CellStyle styleLemonChiffon = backgroundLemonChiffon(workbook);

		Cell cellCoulourStyle = headerRowBUResource.createCell(0);
		cellCoulourStyle.setCellValue("From Payroll Records (E No, Name)");
		cellCoulourStyle.setCellStyle(styleLemonChiffon);

		Cell cellCoulourStyle1 = headerRowBUResource.createCell(1);
		cellCoulourStyle1.setCellValue("");
		cellCoulourStyle1.setCellStyle(styleLemonChiffon);

		buResourcePLReportSpreadsheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 1));

		CellStyle styleBlue = backgroundBlue(workbook);

		Cell cellCoulourStyle2 = headerRowBUResource.createCell(2);
		cellCoulourStyle2.setCellValue("Division By Business Groups");
		cellCoulourStyle2.setCellStyle(styleBlue);

		Cell cellCoulourStyle3 = headerRowBUResource.createCell(3);
		cellCoulourStyle3.setCellValue("");
		cellCoulourStyle3.setCellStyle(styleBlue);

		Cell cellCoulourStyle4 = headerRowBUResource.createCell(4);
		cellCoulourStyle4.setCellValue("");
		cellCoulourStyle4.setCellStyle(styleBlue);

		buResourcePLReportSpreadsheet.addMergedRegion(new CellRangeAddress(0, 0, 2, 4));

		CellStyle styleGreen = backgroundGreen(workbook);

		Cell cellCoulourStyle5 = headerRowBUResource.createCell(5);
		cellCoulourStyle5.setCellValue("Division By Skill");
		cellCoulourStyle5.setCellStyle(styleGreen);

		CellStyle styleGray = backgroundGray(workbook);

		Cell cellCoulourStyle6 = headerRowBUResource.createCell(6);
		cellCoulourStyle6.setCellValue("Division By Location");
		cellCoulourStyle6.setCellStyle(styleGray);

		Cell cellCoulourStyle7 = headerRowBUResource.createCell(7);
		cellCoulourStyle7.setCellValue("");
		cellCoulourStyle7.setCellStyle(styleGray);

		buResourcePLReportSpreadsheet.addMergedRegion(new CellRangeAddress(0, 0, 6, 7));

		Cell cellCoulourStyle8 = headerRowBUResource.createCell(8);
		cellCoulourStyle8.setCellValue("Division By Allocation/Billability");
		cellCoulourStyle8.setCellStyle(styleGreen);

		Cell cellCoulourStyle9 = headerRowBUResource.createCell(9);
		cellCoulourStyle9.setCellValue("");
		cellCoulourStyle9.setCellStyle(styleGreen);

		Cell cellCoulourStyle10 = headerRowBUResource.createCell(10);
		cellCoulourStyle10.setCellValue("");
		cellCoulourStyle10.setCellStyle(styleGreen);

		buResourcePLReportSpreadsheet.addMergedRegion(new CellRangeAddress(0, 0, 8, 10));

		Cell cellCoulourStyle11 = headerRowBUResource.createCell(11);
		cellCoulourStyle11.setCellValue("Worked During the Month");
		cellCoulourStyle11.setCellStyle(styleGreen);

		Cell cellCoulourStyle12 = headerRowBUResource.createCell(12);
		cellCoulourStyle12.setCellValue("");
		cellCoulourStyle12.setCellStyle(styleGreen);

		buResourcePLReportSpreadsheet.addMergedRegion(new CellRangeAddress(0, 0, 11, 12));

		CellStyle styleYellow = backgroundYellow(workbook);

		Cell cellCoulourStyle13 = headerRowBUResource.createCell(13);
		cellCoulourStyle13.setCellValue("For your Reference");
		cellCoulourStyle13.setCellStyle(styleYellow);

		Cell cellCoulourStyle14 = headerRowBUResource.createCell(14);
		cellCoulourStyle14.setCellValue("For Your Reference - Project Allocation");
		cellCoulourStyle14.setCellStyle(styleYellow);

		Cell cellCoulourStyle15 = headerRowBUResource.createCell(15);
		cellCoulourStyle15.setCellValue("");
		cellCoulourStyle15.setCellStyle(styleYellow);
		
		Cell cellCoulourStyle16 = headerRowBUResource.createCell(16);
		cellCoulourStyle16.setCellValue("");
		cellCoulourStyle16.setCellStyle(styleYellow);

		buResourcePLReportSpreadsheet.addMergedRegion(new CellRangeAddress(0, 0, 14, 15));

		// Second Header

		HSSFRow headerRowBUResource1 = buResourcePLReportSpreadsheet.createRow(1);
		rowidBUResource++;

		Cell cellEmployeeID = headerRowBUResource1.createCell(0);
		cellEmployeeID.setCellValue("Yash Employee ID");
		cellEmployeeID.setCellStyle(styleLemonChiffon);

		Cell cellEmployeeName = headerRowBUResource1.createCell(1);
		cellEmployeeName.setCellValue("Employee Name (As per Payroll Records)");
		cellEmployeeName.setCellStyle(styleLemonChiffon);

		Cell cellDesignation = headerRowBUResource1.createCell(2);
		cellDesignation.setCellValue("Designation Group 01");
		cellDesignation.setCellStyle(styleBlue);

		Cell cellBGBU = headerRowBUResource1.createCell(3);
		cellBGBU.setCellValue("  BG-BU Group 02 ");
		cellBGBU.setCellStyle(styleBlue);

		Cell cellProject = headerRowBUResource1.createCell(4);
		cellProject.setCellValue("Project Name Group 03");
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

		Cell resAllocPer = headerRowBUResource1.createCell(16);
		resAllocPer.setCellValue("Actual Res Utilization");
		resAllocPer.setCellStyle(styleYellow);
		
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
			
			Integer allocPercentage =(Integer) buReport[16];
			//System.out.println("Percentage allocation................................................................."+allocPercentage);

			cellidBUResource = 0;
			rowBUResource = buResourcePLReportSpreadsheet.createRow(rowidBUResource++);

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
			cell11.setCellValue(Constants.DATE_FORMAT_REPORTS.format(allocationStartDate));
			cell11.setCellStyle(setFont);

			Cell cell12 = rowBUResource.createCell(cellidBUResource++);
			cell12.setCellValue(Constants.DATE_FORMAT_REPORTS.format(allocationEndDate));
			cell12.setCellStyle(setFont);

			Cell cell13 = rowBUResource.createCell(cellidBUResource++);
			cell13.setCellValue(allocationType);
			cell13.setCellStyle(setFont);

			Cell cell14 = rowBUResource.createCell(cellidBUResource++);
			cell14.setCellValue(Constants.DATE_FORMAT_REPORTS.format(startDate));
			cell14.setCellStyle(setFont);

			Cell cell15 = rowBUResource.createCell(cellidBUResource++);

			if (endDate != null) {

				cell15.setCellValue(Constants.DATE_FORMAT_REPORTS.format(endDate));
				cell15.setCellStyle(setFont);
			}
			
			Cell cell16 = rowBUResource.createCell(cellidBUResource++);
			cell16.setCellValue(allocPercentage);
			cell16.setCellStyle(setFont);

		}

	}

	private static void generateBorrowedResourceSheet(List<Object[]> borrowedresourceList, HSSFWorkbook workbook) {

		// Create a blank BorrowedReportSpreadsheet buReportList
		HSSFSheet borrowedResourcePLReportSpreadsheet = workbook.createSheet("Borrowed Resource");

		// Create row object

		HSSFRow headerRowBorrowedResource = borrowedResourcePLReportSpreadsheet.createRow(0);
		headerRowBorrowedResource.createCell(0).setCellValue("Yash Employee ID");
		headerRowBorrowedResource.createCell(1).setCellValue("Employee Name");
		headerRowBorrowedResource.createCell(2).setCellValue("Project Name");
		headerRowBorrowedResource.createCell(3).setCellValue("Business Unit");
		headerRowBorrowedResource.createCell(4).setCellValue("Borrowed Business Unit");
		headerRowBorrowedResource.createCell(5).setCellValue("Designation");
		headerRowBorrowedResource.createCell(6).setCellValue("Skills");
		headerRowBorrowedResource.createCell(7).setCellValue("Date Of Joining");
		headerRowBorrowedResource.createCell(8).setCellValue("Grade");
		headerRowBorrowedResource.createCell(9).setCellValue("Allocation Type");
		headerRowBorrowedResource.createCell(10).setCellValue("Allocation Start Date");
		headerRowBorrowedResource.createCell(11).setCellValue("Allocation End Date");
		headerRowBorrowedResource.createCell(12).setCellValue("Customer Name");
		headerRowBorrowedResource.createCell(13).setCellValue("Base Location");
		headerRowBorrowedResource.createCell(14).setCellValue("Current Location");
		headerRowBorrowedResource.createCell(15).setCellValue("Actual Res Utilization");

		setRowStyle(workbook, headerRowBorrowedResource);

		setHeaderSize(borrowedResourcePLReportSpreadsheet);

		// write data in cells//

		HSSFRow rowBorrowedResource;

		int rowidBorrowedResource = 1;
		int cellidBorrowedResource = 0;

		CellStyle setFont = setFontStyleHeader(workbook);

		for (Object[] bowrrowedReport : borrowedresourceList) {

			String id = (String) bowrrowedReport[0];
			String name = (String) bowrrowedReport[1];
			String projectName = (String) bowrrowedReport[2];

			String buname = (String) bowrrowedReport[3];
			String borrowedbuname = (String) bowrrowedReport[4];
			String designation = (String) bowrrowedReport[5];
			String skill = (String) bowrrowedReport[6];
			Date DOJ = (Date) bowrrowedReport[7];
			String grade = (String) bowrrowedReport[8];
			String allocationType = (String) bowrrowedReport[9];
			Date startDate = (Date) bowrrowedReport[10];
			Date endDate = (Date) bowrrowedReport[11];
			String customerName = (String) bowrrowedReport[12];
			String baseLocation = (String) bowrrowedReport[13];
			String currentLocation = (String) bowrrowedReport[14];
			Integer resAllocPercentage = (Integer) bowrrowedReport[15];

			cellidBorrowedResource = 0;

			rowBorrowedResource = borrowedResourcePLReportSpreadsheet.createRow(rowidBorrowedResource++);

			Cell cell0 = rowBorrowedResource.createCell(cellidBorrowedResource++);
			cell0.setCellValue(id);
			cell0.setCellStyle(setFont);

			Cell cell1 = rowBorrowedResource.createCell(cellidBorrowedResource++);
			cell1.setCellValue(name);
			cell1.setCellStyle(setFont);

			Cell cell2 = rowBorrowedResource.createCell(cellidBorrowedResource++);
			cell2.setCellValue(projectName);
			cell2.setCellStyle(setFont);

			Cell cell3 = rowBorrowedResource.createCell(cellidBorrowedResource++);
			cell3.setCellValue(buname);
			cell3.setCellStyle(setFont);

			Cell cell4 = rowBorrowedResource.createCell(cellidBorrowedResource++);
			cell4.setCellValue(borrowedbuname);
			cell4.setCellStyle(setFont);

			Cell cell5 = rowBorrowedResource.createCell(cellidBorrowedResource++);
			cell5.setCellValue(designation);
			cell5.setCellStyle(setFont);

			Cell cell6 = rowBorrowedResource.createCell(cellidBorrowedResource++);
			cell6.setCellValue(skill);
			cell6.setCellStyle(setFont);

			Cell cell7 = rowBorrowedResource.createCell(cellidBorrowedResource++);
			cell7.setCellValue(Constants.DATE_FORMAT_REPORTS.format(DOJ));
			cell7.setCellStyle(setFont);

			Cell cell8 = rowBorrowedResource.createCell(cellidBorrowedResource++);
			cell8.setCellValue(grade);
			cell8.setCellStyle(setFont);

			Cell cell9 = rowBorrowedResource.createCell(cellidBorrowedResource++);
			cell9.setCellValue(allocationType);
			cell9.setCellStyle(setFont);

			Cell cell10 = rowBorrowedResource.createCell(cellidBorrowedResource++);
			cell10.setCellValue(Constants.DATE_FORMAT_REPORTS.format(startDate));
			cell10.setCellStyle(setFont);

			Cell cell11 = rowBorrowedResource.createCell(cellidBorrowedResource++);
			if (endDate != null) {

				cell11.setCellValue(Constants.DATE_FORMAT_REPORTS.format(endDate));
				cell11.setCellStyle(setFont);
			}

			Cell cell12 = rowBorrowedResource.createCell(cellidBorrowedResource++);
			cell12.setCellValue(customerName);
			cell12.setCellStyle(setFont);

			Cell cell13 = rowBorrowedResource.createCell(cellidBorrowedResource++);
			cell13.setCellValue(baseLocation);
			cell13.setCellStyle(setFont);

			Cell cell14 = rowBorrowedResource.createCell(cellidBorrowedResource++);
			cell14.setCellValue(currentLocation);
			cell14.setCellStyle(setFont);
			
			Cell cell15 = rowBorrowedResource.createCell(cellidBorrowedResource++);
			cell15.setCellValue(resAllocPercentage);
			cell15.setCellStyle(setFont);

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

	// method for header style
	private static void setRowStyle(HSSFWorkbook workbook, HSSFRow headerRow) {

		// set style on header row starts//
		CellStyle style = workbook.createCellStyle();// Create style

		setFontStyleRow(workbook, style);

		borderStyle(style);

		style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);

		headerRow.setHeight((short) 400);

		for (int i = 0; i < headerRow.getLastCellNum(); i++) {// For each cell
																// in the row
			headerRow.getCell(i).setCellStyle(style);// Set the style
		}
	}

	private static CellStyle backgroundGreen(HSSFWorkbook workbook) {

		CellStyle style = workbook.createCellStyle();

		setFontStyleRow(workbook, style);

		borderStyle(style);

		style.setFillForegroundColor(HSSFColor.LIGHT_GREEN.index);

		return style;
	}

	private static void setFontStyleRow(HSSFWorkbook workbook, CellStyle styleBUReport) {

		Font font = workbook.createFont();
		font.setFontName("Calibri");

		font.setFontHeightInPoints((short) 10);// Create font
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

	@Override
	protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, HttpServletRequest request, HttpServletResponse response) throws Exception {

		logger.debug("----------Inside PLReportExcelUtility Start buildExcelDocument-----------");

		@SuppressWarnings("unchecked")
		Map<String, List<Object[]>> dataMap = (Map<String, List<Object[]>>) model.get("report");

		response.reset();
		//response.setHeader("Expires:", "0");

		if (dataMap != null) {

			try {

				HSSFWorkbook workBook = PLReportExcelUtility.generateExcelForPLRport(dataMap);

				response.setContentType("application/vnd.ms-excel");
				response.setHeader("Content-Disposition", "attachment; filename=PLReport_.xls");

				// response.setCharacterEncoding(DEFAULT_CONTENT_TYPE);
				workBook.write(response.getOutputStream());

			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		logger.debug("----------Inside PLReportExcelUtility End buildExcelDocument-----------");

	}

}
