package org.yash.rms.excel;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFCellUtil;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.util.CellUtil;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.springframework.web.servlet.view.document.AbstractExcelView;
import org.yash.rms.dto.RequisitionResourceDTO;
import org.yash.rms.report.dto.RequestRequisitionReport;
import org.yash.rms.util.Constants;
import org.yash.rms.util.DateUtil;

public class RequestRequisitionExcelReportView extends AbstractExcelView {

	private static final String RRF_REPORT_FILE_NAME = "RRF_REPORT";
	
	private static final String FONT_CALIBRI = "Calibri";
	
	private static final String RRF_REPORT_EXCEL_SHEET_NAME = "RRF As on date report";
	
	private static final String RRF_REPORT_EXCEL_SHEET_DASHBOARD_NAME = "DashBoard";
	
	private static final String RRF_REPORT_EXCEL_SHEET_MAIN_DATA = "RRF Report for Pivot";
	
//	private static final String RRF_SUBMISSION_REPORT = "RRF Submission Report";
	
	private static final String[] PIVOT_HEADERNAME = {"Client","RRF ID","Requirement Type", "Status","Allocation Type","Requested Date","Work Location",
			"Total Positions","Open","Submission","Joined",	"Shortlisted","Rejected","Hold","Lost",/*"Total Internal Submitted","Total External Submitted","Total Internal Joined", "Total External Joined",*/"Required Skill","Designation","Requested   By","Requesting Practice","Hiring Unit", "Requestor Unit", "RRF Close Date", "Addtional Comments","RMG POC", "TAC Team POC", "Requirement Area","AM Job Code","Client Group"	};
	
	private static final String[] HEADERNAME = {"Client","RRF ID","Requirement Type", "Open Date", "AM Job Code", "Client Group","No. of Positions", "Status","Allocation Type","Work Location",
	"Open","Submission","Joined",	"Shortlisted","Rejected","Hold","Lost",/*"Total Internal Submitted","Total External Submitted","Total Internal Joined", "Total External Joined",*/"Required Skill","Designation","Requested   By","Requesting Practice","Hiring Unit", "Requestor Unit" ,"RRF Close Date", "Addtional Comments","RMG POC", "TAC Team POC" , "Requirement Area"	};
	
	private static final String[] SUB_TABLE_HEADERNAME = {" ","Type", "Resource Name","Location","Notice Period",
			"Status" , "Joining/ Interview Date", "Profile Submitted On" ,"Position Close Date", "Submitted to POC (Days)", "Submitted to AM team (Days)","Resource Rejected on","Resource Selected on"};//,"","","","","","","","","","","",""};
	
	private static final String[] DBHEADERNAME = {"S. No","Client","Total Pos","Open","Close", "Status"};
	
	
	
	@Override
	protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.debug("----------Entered into RequestRequisitionReportExcelView :buildExcelDocument and start to build excel document-----------");
			
		response.reset();
		response.setContentType("application/vnd.ms-excel");
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date todayDate = new Date();
		
		
		response.setHeader("Content-Disposition","attachment; filename="+RRF_REPORT_FILE_NAME+"_"+dateFormat.format(todayDate)+".xls");
		
			try {
				prepareExcelReportForRequestRequisition(model, workbook);
				workbook.write(response.getOutputStream());
			}catch (ParseException e) {
				logger.error("Error in parse date.");
			} 
			catch (Exception e) {
				e.printStackTrace();
			}
		
		logger.debug("----------Exit from RequestRequisitionReportExcelView :buildExcelDocument and build excel document finished-----------");
	}
	
	private void prepareExcelReportForRequestRequisition(Map<String, Object> model, HSSFWorkbook workbook) throws ParseException{
		
		String startDate =   (String)model.get("startDate");
		String endDate =   (String)model.get("endDate");
		String statusIds =   (String)model.get("statusIds");
		
		
		HSSFSheet sheet = ExcelUtility.getHSSFSheet(workbook,RRF_REPORT_EXCEL_SHEET_NAME);
		HSSFSheet dashboardSheet = ExcelUtility.getHSSFSheet(workbook,RRF_REPORT_EXCEL_SHEET_DASHBOARD_NAME);
		HSSFSheet mainSheet = ExcelUtility.getHSSFSheet(workbook,RRF_REPORT_EXCEL_SHEET_MAIN_DATA);
//		HSSFSheet submissionReportSheet = ExcelUtility.getHSSFSheet(workbook,RRF_SUBMISSION_REPORT);
		
		setHeadersInExcel( workbook,  sheet,  startDate,  endDate,statusIds);	
		
		List<RequestRequisitionReport> rrReportList = (List<RequestRequisitionReport>) model.get("rrReportList");
		List<RequestRequisitionReport> rrReportDBList = (List<RequestRequisitionReport>) model.get("rrReportDBList");
		prapreExcelDataTable(sheet, rrReportList , workbook); 
		prapreExcelMainTable(mainSheet, rrReportList , workbook); 
		prepareExcelDBTable(dashboardSheet, rrReportDBList, workbook);
		
		
		 for(int i = 0; i < HEADERNAME.length; i++) {
			 if(i==1)
	            	sheet.setColumnWidth(i, 6000);
	            if(i==2)
	            	sheet.setColumnWidth(i, 6200);
	            if(i==3)
	            	sheet.setColumnWidth(i, 3500);
	            if(i==4||i==5)
	            	sheet.setColumnWidth(i, 3000);
	            if(i==6)
	            	sheet.setColumnWidth(i, 2000);	
	            if(i==7||i==8||i==9||i==10||i==11||i==12)
	            	sheet.setColumnWidth(i, 4800);	
	            if(i==14||i==15||i==16||i==17||i==18)
	            	sheet.setColumnWidth(i, 3000);
	        }
		
	}
	
	public HSSFColor setCustColor(HSSFWorkbook workbook, byte r,byte g, byte b){
	    HSSFPalette palette = workbook.getCustomPalette();
	    HSSFColor hssfColor = null;
	    try {
	        hssfColor= palette.findColor(r, g, b); 
	        if (hssfColor == null ){
	            palette.setColorAtIndex(HSSFColor.LAVENDER.index, r, g,b);
	            hssfColor = palette.getColor(HSSFColor.LAVENDER.index);
	        }
	    } catch (Exception e) {
	        logger.error(e);
	    }

	    return hssfColor;
	}
	
	private void setHeadersInExcel(HSSFWorkbook workbook, HSSFSheet sheet, String startDate, String endDate, String statusIds) throws ParseException{

		Date endDt = null;
		Date startDt = null;
		if(startDate!=null && startDate.trim().length()>0){
			startDt=new SimpleDateFormat("MM/dd/yyyy").parse(startDate);
		}
		if(endDate!=null && endDate.trim().length()>0){
			 endDt=new SimpleDateFormat("MM/dd/yyyy").parse(endDate);
		}
		
		int lastColumnIndex = 16;
		/* set back ground color for first 4 row*/ 
		HSSFCellStyle xssfCellStyleForDataCellResource = ExcelUtility.getHSSFCellStyleForDataCell(workbook);
		HSSFColor lightGray =  setCustColor(workbook,(byte) 49, (byte)134,(byte) 155);
		xssfCellStyleForDataCellResource.setFillForegroundColor(lightGray.getIndex());
		xssfCellStyleForDataCellResource.setBorderLeft(HSSFCellStyle.NO_FILL);
		xssfCellStyleForDataCellResource.setBorderBottom(HSSFCellStyle.NO_FILL);
		xssfCellStyleForDataCellResource.setBorderTop(HSSFCellStyle.NO_FILL);
		xssfCellStyleForDataCellResource.setBorderRight(HSSFCellStyle.NO_FILL);
		xssfCellStyleForDataCellResource.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		HSSFFont font = workbook.createFont();
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		font.setColor(HSSFColor.WHITE.index);
		xssfCellStyleForDataCellResource.setFont(font);
		
		
	}
	
	private void prapreExcelDataTable(HSSFSheet sheet, List<RequestRequisitionReport> rrReportList,HSSFWorkbook workbook) {

		HSSFRow row = null;
		HSSFRow subRow = null ;
		int rowIndex = 0;
		int columnIndex = 0;
		Cell cell = null;
		int lastRowIndex = 0;
		int initialIndex =0;  
		
		HSSFCellStyle xssfCellStyleForDataCell = ExcelUtility.getHSSFCellStyleForDataCell(workbook);
		Font font = workbook.createFont();
		
		// setting the cell style for main data table 
		CellStyle cellStyle = workbook.createCellStyle();
		setCellStyleForMainTable(cellStyle, font);
		
		CellStyle cellStyleSub = workbook.createCellStyle();
		setCellStyleForMainTable(cellStyleSub, font);
		//Setting the cell style for Open Status
		CellStyle cellStatusStyle = workbook.createCellStyle();
		setCellStyleForStatusCell(cellStatusStyle, font);
		
		CellStyle cellTotPosStyle = workbook.createCellStyle();
		setCellStyleForTotPosCell(cellTotPosStyle, font,workbook);
		CellStyle cellOpenStyle = workbook.createCellStyle();
		setCellStyleForOpenCell(cellOpenStyle, font,workbook);
		// setting the cell style for sub data table
		CellStyle cellStyleForSubTable = workbook.createCellStyle();
		Font fontForSubtable = workbook.createFont();
		setCellStyleForSubTable(cellStyleForSubTable, fontForSubtable,workbook);
		CellStyle cellStyleForSubTableDates = workbook.createCellStyle();
		setCellStyleForSubTable(cellStyleForSubTableDates, fontForSubtable,workbook);
		
		// setting the cell style for sub data table
		HSSFCellStyle cellStyleForSubTableHeader = workbook.createCellStyle();
		Font fontForSubtableHeader = workbook.createFont();
		setCellStyleForSubTableHeader(cellStyleForSubTableHeader,fontForSubtableHeader,workbook);	
		
		/* setting the headers */
		row = sheet.createRow(rowIndex);
		//row.setRowStyle(cellStyle);
		setStyleForTableHeader(xssfCellStyleForDataCell, workbook);
		ExcelUtility.createHeaders(row, xssfCellStyleForDataCell, font, HEADERNAME,true);
		CellStyle cellHeaderStatusStyle = workbook.createCellStyle();
		setCellStyleForStatusHeader(cellHeaderStatusStyle,workbook);
		row.getCell(7).setCellStyle(cellHeaderStatusStyle);
		row.setHeightInPoints(40);
		
		CellStyle cellHeaderTotPosStyle = workbook.createCellStyle();
		setCellStyleForTotPosHeader(cellHeaderTotPosStyle,workbook);
		//row.getCell(7).setCellStyle(cellHeaderTotPosStyle);
		row.getCell(11).setCellStyle(cellHeaderTotPosStyle);
		row.getCell(13).setCellStyle(cellHeaderTotPosStyle);
		
		row.getCell(13).setCellStyle(cellHeaderTotPosStyle);
		
		CellStyle cellHeaderOrange = workbook.createCellStyle();
		setCellStyleOrangeHeader(cellHeaderOrange,workbook);
		row.getCell(3).setCellStyle(cellHeaderOrange);
		row.getCell(4).setCellStyle(cellHeaderOrange);
		row.getCell(5).setCellStyle(cellHeaderOrange);
		row.getCell(6).setCellStyle(cellHeaderOrange);
		
		
		CellStyle cellHeaderOpenStyle = workbook.createCellStyle();
		setCellStyleForOpenHeader(cellHeaderOpenStyle,workbook);
		//row.getCell(7).setCellStyle(cellHeaderOpenStyle);
		CellStyle simpleCellStyle = workbook.createCellStyle();
		setSimpleCellStyle(simpleCellStyle,workbook);
		
		
		// setting the cell style for main data table 
		CellStyle dateCellStyle = workbook.createCellStyle();
        setCellStyleForMainTable(cellStyle, font);

		//if(rrReportList !=null &&  rrReportList.size()>0){
			if(rrReportList !=null &&  !rrReportList.isEmpty()){
			for(RequestRequisitionReport reportData : rrReportList ){
				
				columnIndex = 0;
				initialIndex = ++rowIndex;
				row = sheet.createRow(rowIndex);
				
				cell = row.createCell(columnIndex);
				cell.setCellValue(reportData.getClientName());
				cell.setCellStyle(cellStyle);
				//sheet.autoSizeColumn(columnIndex);
				columnIndex++;
				
				cell = row.createCell(columnIndex);
				cell.setCellValue(reportData.getRequirementId());
				cell.setCellStyle(cellStyle);
				//sheet.autoSizeColumn(columnIndex);
				columnIndex++;
				
				cell = row.createCell(columnIndex);
				cell.setCellValue(reportData.getRequirementType());
				cell.setCellStyle(cellStyle);
				//sheet.autoSizeColumn(columnIndex);
				columnIndex++;
				
				try {				
					cell = row.createCell(columnIndex);
						CreationHelper creationHelper = workbook.getCreationHelper();
						HSSFCellUtil.setCellStyleProperty((HSSFCell) cell, workbook, CellUtil.DATA_FORMAT,
						HSSFDataFormat.getBuiltinFormat(("dd-MMM-yy")));
					    Date parsedDate = new SimpleDateFormat("MM/dd/yyyy").parse(reportData.getRequestedDate());
					    cellStyleSub.setDataFormat(creationHelper.createDataFormat().getFormat(("dd-MMM-yy")));
						cell.setCellValue(HSSFDateUtil.getExcelDate(parsedDate));
						cell.setCellStyle(cellStyleSub);
					columnIndex++;
					} catch (ParseException e) {
						e.printStackTrace();
						logger.debug(e.getMessage());
					}
				
				cell = row.createCell(columnIndex);
				cell.setCellValue(reportData.getAmJobCode());//AM Job Code
				cell.setCellStyle(cellStyle);
				//sheet.autoSizeColumn(columnIndex);
				columnIndex++;
				
				cell = row.createCell(columnIndex);
				cell.setCellValue(reportData.getCustGroup());
				cell.setCellStyle(cellStyle);
				//sheet.autoSizeColumn(columnIndex);
				columnIndex++;
				
				cell = row.createCell(columnIndex);
				cell.setCellValue(Integer.valueOf(reportData.getNoOfResources()));
				cell.setCellStyle(cellStyle);
				columnIndex++;
				
				cell = row.createCell(columnIndex);
				cell.setCellStyle(cellStatusStyle);
				//if(!reportData.getReportStatus().equalsIgnoreCase("Closed")||!reportData.getReportStatus().equalsIgnoreCase("Open"))
				String reportDataStatus = reportData.getReportStatus();
				if(!reportDataStatus.equalsIgnoreCase("Closed")||!reportDataStatus.equalsIgnoreCase("Open"))
					row.setHeightInPoints(30);
				//cell.setCellValue(reportData.getReportStatus());
				cell.setCellValue(reportDataStatus);
				columnIndex++;
				
				String cellData="";
				cell = row.createCell(columnIndex);
				if(reportData.getAllocationDTO().getId()==2) {
					cell.setCellValue("Billable");
				}
				else if(reportData.getAllocationDTO().getId()==3){
					cell.setCellValue("Partial Billable");
				}
				else{
					cellData=reportData.getAllocationDTO().getAllocationType();
					cell.setCellValue(cellData.replace("Non-Billable", "NB"));
				}
				cell.setCellStyle(cellStyle);
				columnIndex++;
				
				
				cell = row.createCell(columnIndex);
				cell.setCellValue(reportData.getLocationName());
				cell.setCellStyle(cellStyle);
				columnIndex++;
				
				cell = row.createCell(columnIndex);
				cell.setCellValue(Integer.valueOf(reportData.getOpen()));
				cell.setCellStyle(cellOpenStyle);
				columnIndex++;
				
				cell = row.createCell(columnIndex);
				cell.setCellValue(Integer.valueOf(reportData.getSubmissions()));
				cell.setCellStyle(cellTotPosStyle);
				columnIndex++;
				
				cell = row.createCell(columnIndex);
				cell.setCellValue(Integer.valueOf(reportData.getClosed()));
				cell.setCellStyle(cellOpenStyle);
				columnIndex++;
				
				cell = row.createCell(columnIndex);
				cell.setCellValue(Integer.valueOf(reportData.getShortlisted()));
				cell.setCellStyle(cellTotPosStyle);
				columnIndex++;
				
				cell = row.createCell(columnIndex);
				cell.setCellValue(Integer.valueOf(reportData.getNotFitForRequirement()));
				cell.setCellStyle(cellStyle);
				columnIndex++;
				
				cell = row.createCell(columnIndex);
				/*if(reportData.getHold().equalsIgnoreCase(reportData.getNoOfResources()))
					cell.setCellValue("Inactive");
				else*/
				cell.setCellValue(Integer.valueOf(reportData.getHold()));
				cell.setCellStyle(cellStyle);
				columnIndex++;

				cell = row.createCell(columnIndex);
				cell.setCellValue(Integer.valueOf(reportData.getLost()));
				cell.setCellStyle(cellStyle);
				columnIndex++;
				
								
				cell = row.createCell(columnIndex);
				cell.setCellValue(reportData.getSkill());
				cell.setCellStyle(cellStyle);
				columnIndex++;
				
				cell = row.createCell(columnIndex);
				cell.setCellValue(reportData.getDesignationName());
				cell.setCellStyle(cellStyle);
				columnIndex++;
				
				cell = row.createCell(columnIndex);
				cell.setCellValue(reportData.getRequestedBy());
				cell.setCellStyle(cellStyle);
				columnIndex++;
				
				cell = row.createCell(columnIndex);
				cell.setCellValue(reportData.getBusinessGroupName());
				cell.setCellStyle(cellStyle);
				columnIndex++;
				
				cell = row.createCell(columnIndex);
				cell.setCellValue(reportData.getHiringUnit());
				cell.setCellStyle(cellStyle);
				columnIndex++;
				
				cell = row.createCell(columnIndex);
				cell.setCellValue(reportData.getReqUnit());
				cell.setCellStyle(cellStyle);
				columnIndex++;
				
			try {
				cell = row.createCell(columnIndex);
		        //if (reportData.getRrfCloserDate() != null && reportData.getRrfCloserDate() != "" && Integer.valueOf(reportData.getOpen())==0) {
				String reportDataRrfCloserDate = reportData.getRrfCloserDate();
				if (reportDataRrfCloserDate != null && reportDataRrfCloserDate != "" && Integer.valueOf(reportData.getOpen())==0) {
                        CreationHelper creationHelper = workbook.getCreationHelper();
                        HSSFCellUtil.setCellStyleProperty((HSSFCell) cell, workbook, CellUtil.DATA_FORMAT,
                        HSSFDataFormat.getBuiltinFormat(("dd-MMM-yy")));
                        Date parsedDate = new SimpleDateFormat("MM/dd/yyyy").parse(reportData.getRrfCloserDate());
                        //Date parsedDate1 = new SimpleDateFormat("dd-MMM-yy").parse(parsedDate.toString());

                        cellStyleSub.setDataFormat(creationHelper.createDataFormat().getFormat(("dd-MMM-yy")));
                        cell.setCellValue(HSSFDateUtil.getExcelDate(parsedDate));

                    }
			} catch (ParseException e) {
                e.printStackTrace();
                logger.debug(e.getMessage());
            }
		        cell.setCellStyle(cellStyleSub);
		        columnIndex++;
				
				cell = row.createCell(columnIndex);
				if(null!=reportData.getAddtionalComments())
					cell.setCellValue(reportData.getAddtionalComments());
				else
					cell.setCellValue(" ");
				cell.setCellStyle(cellStyle);
				columnIndex++;
				
				//Code for RMG POC and Tac Team POC
				cell = row.createCell(columnIndex);
				if(null!=reportData.getRmgPOC())
					cell.setCellValue(reportData.getRmgPOC());
				else
					cell.setCellValue(" ");
				cell.setCellStyle(cellStyle);
				columnIndex++;
				
				cell = row.createCell(columnIndex);
				if(null!=reportData.getTacTeamPOC())
					cell.setCellValue(reportData.getTacTeamPOC());
				else
					cell.setCellValue(" ");
				cell.setCellStyle(cellStyle);
				columnIndex++;

				//For Requirement Area
				cell = row.createCell(columnIndex);
				if(null!=reportData.getRequirementArea()) {
					if(reportData.getRequirementArea().equalsIgnoreCase(Constants.NON_SAP))
						cell.setCellValue(Constants.NON_SAP);
					else if(reportData.getRequirementArea().equalsIgnoreCase(Constants.SAP))
						cell.setCellValue(Constants.SAP);
					else
						cell.setCellValue(Constants.NON_SAP);
				}else {
					cell.setCellValue(Constants.NON_SAP);
				}
				cell.setCellStyle(cellStyle);
				columnIndex++;
				
				//if(reportData.getRequisitionResourceList() !=null && reportData.getRequisitionResourceList().size()>0){
				List<RequisitionResourceDTO> reportDataRequisitionResourceDTOList = reportData.getRequisitionResourceList();
				if(reportDataRequisitionResourceDTOList !=null && !reportDataRequisitionResourceDTOList.isEmpty()){
					logger.debug("Resource List Size :: "+reportDataRequisitionResourceDTOList.size() );
					subRow = sheet.createRow(++rowIndex);

					ExcelUtility.createHeaders(subRow, cellStyleForSubTableHeader, font, SUB_TABLE_HEADERNAME,true);
					sheet.getRow(rowIndex).getCell(0).setCellStyle(simpleCellStyle);
					
					//for(RequisitionResourceDTO requisitionResourceDTO : reportData.getRequisitionResourceList()){
					for(RequisitionResourceDTO requisitionResourceDTO : reportDataRequisitionResourceDTOList){
						rowIndex = rowIndex+1;
						createSubDataTable(requisitionResourceDTO, rowIndex, sheet, cellStyleForSubTable, workbook, cellStyleForSubTableDates,reportData);
					}
					logger.debug("Initial Index = "+initialIndex+1);
					logger.debug("Last Index = "+(rowIndex));
					sheet.groupRow(initialIndex+1, rowIndex);
					sheet.setRowGroupCollapsed(initialIndex+1, true);
					sheet.setRowSumsBelow(false);
				}
			}
			//sheet.autoSizeColumn((short)2);
		}
	}
	
	private void createSubDataTable (RequisitionResourceDTO requisitionResourceDTO, int rowIndex, HSSFSheet sheet, CellStyle cellStyle, HSSFWorkbook workbook, CellStyle cellStyleForSubTableDates, RequestRequisitionReport reportData){
		Cell cell = null;
		HSSFRow row = null;
		int columnIndex = 1;
		long POCsubmitted =0;
		long Amsubmitted =0;
	try {
		row = sheet.createRow(rowIndex);
		//row.setRowStyle(cellStyle);
		cell = row.createCell(columnIndex);
		cell.setCellValue(requisitionResourceDTO.getResourceType());
		cell.setCellStyle(cellStyle);
		columnIndex++;
		
		cell = row.createCell(columnIndex);
		cell.setCellValue(requisitionResourceDTO.getResourceName());
		cell.setCellStyle(cellStyle);
		columnIndex++;
			
		cell = row.createCell(columnIndex);
		cell.setCellValue(requisitionResourceDTO.getLocation());
		cell.setCellStyle(cellStyle);
		columnIndex++;
		
		cell = row.createCell(columnIndex);
		cell.setCellValue(requisitionResourceDTO.getNoticePeriod());
		cell.setCellStyle(cellStyle);
		columnIndex++;
		
		cell = row.createCell(columnIndex);
		cell.setCellValue(requisitionResourceDTO.getStatus());
		cell.setCellStyle(cellStyle);
		columnIndex++;		
		
		
		CreationHelper creationHelper = workbook.getCreationHelper();
		HSSFCellUtil.setCellStyleProperty((HSSFCell) cell, workbook, CellUtil.DATA_FORMAT,
		HSSFDataFormat.getBuiltinFormat(("dd-MMM-yy")));
		
		cell = row.createCell(columnIndex);	
		if (requisitionResourceDTO.getJoiningDate() != null && requisitionResourceDTO.getInterviewDate() != null	&& requisitionResourceDTO.getJoiningDate() != "" && requisitionResourceDTO.getInterviewDate() != "") {
   			cellStyleForSubTableDates.setDataFormat(creationHelper.createDataFormat().getFormat(("dd-MMM-yy")));
   			 Date allocationDate = new SimpleDateFormat("dd-MMM-yyyy").parse(requisitionResourceDTO.getJoiningDate());
   			 Date interviewDate= new SimpleDateFormat("dd-MMM-yyyy").parse(requisitionResourceDTO.getInterviewDate());
			cell.setCellValue( new SimpleDateFormat("dd-MMM-yy").format(allocationDate) + "  /  " + new SimpleDateFormat("dd-MMM-yy").format(interviewDate));
		} else if (requisitionResourceDTO.getJoiningDate() != null && requisitionResourceDTO.getJoiningDate() != "") {
			cellStyleForSubTableDates.setDataFormat(creationHelper.createDataFormat().getFormat(("dd-MMM-yy")));
			cell.setCellValue(HSSFDateUtil.getExcelDate(new SimpleDateFormat("dd-MMM-yyyy").parse(requisitionResourceDTO.getJoiningDate())));
		}else if (requisitionResourceDTO.getInterviewDate() != null && requisitionResourceDTO.getInterviewDate() != "") {
			cellStyleForSubTableDates.setDataFormat(creationHelper.createDataFormat().getFormat(("dd-MMM-yy")));
			cell.setCellValue(HSSFDateUtil.getExcelDate(new SimpleDateFormat("dd-MMM-yyyy").parse(requisitionResourceDTO.getInterviewDate())));
		}    
		cell.setCellStyle(cellStyleForSubTableDates);
		columnIndex++;
		
		cell = row.createCell(columnIndex);
		if (requisitionResourceDTO.getResourceSubmittedDate() != null && requisitionResourceDTO.getResourceSubmittedDate() != "") {
			cellStyleForSubTableDates.setDataFormat(creationHelper.createDataFormat().getFormat(("dd-MMM-yy")));
			cell.setCellValue(HSSFDateUtil.getExcelDate(new SimpleDateFormat("dd-MMM-yyyy").parse(requisitionResourceDTO.getResourceSubmittedDate())));
		}
		cell.setCellStyle(cellStyleForSubTableDates);
		columnIndex++;
		
		/*cell = row.createCell(columnIndex);
		cell.setCellValue(HSSFDateUtil.getExcelDate(new SimpleDateFormat("MM/dd/yyyy").parse(requisitionResourceDTO.getRRFCosedDate())));
		cell.setCellStyle(cellStyle);
		columnIndex++;*/
		cell = row.createCell(columnIndex);
		if (requisitionResourceDTO.getPositionCloseDate() != null && requisitionResourceDTO.getPositionCloseDate() != "" ) {
			cellStyleForSubTableDates.setDataFormat(creationHelper.createDataFormat().getFormat(("dd-MMM-yy")));
			cell.setCellValue(HSSFDateUtil.getExcelDate(new SimpleDateFormat("dd-MMM-yyyy").parse(requisitionResourceDTO.getPositionCloseDate())));
		}
		cell.setCellStyle(cellStyleForSubTableDates);
		columnIndex++;
		
		//displaying the number of days for POC submitted
		cell = row.createCell(columnIndex);
		if (requisitionResourceDTO.getResourcePOCsubmittedDate() != null && requisitionResourceDTO.getResourcePOCsubmittedDate() != "") {			
    		if(requisitionResourceDTO.getStatus().equalsIgnoreCase("SUBMITTED TO POC")) {
    		    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
                Date date = null;
                String convertedDate = null;
                date = dateFormat.parse(requisitionResourceDTO.getResourcePOCsubmittedDate());
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                convertedDate = simpleDateFormat.format(date);        
                SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date date1 = myFormat.parse(convertedDate);
                Date date2 = myFormat.parse(DateUtil.getTodaysDate().toString());
                long dateDiff = date2.getTime() - date1.getTime();
                long dateDifference = TimeUnit.DAYS.convert(dateDiff, TimeUnit.MILLISECONDS);
                POCsubmitted=dateDifference;
    		    cell.setCellValue(dateDifference);
    		}else if (requisitionResourceDTO.getResourceAMsubmittedDate() != null && requisitionResourceDTO.getResourceAMsubmittedDate() != "") {     
    		    long dateDiff = getDateDifference(requisitionResourceDTO.getResourcePOCsubmittedDate(), requisitionResourceDTO.getResourceAMsubmittedDate());
                long dateDifference = TimeUnit.DAYS.convert(dateDiff, TimeUnit.MILLISECONDS);                
                cell.setCellValue(dateDifference);    		    
    		}else if (requisitionResourceDTO.getResourceSelectedDate() != null && requisitionResourceDTO.getResourceSelectedDate() != "") {     
                long dateDiff = getDateDifference(requisitionResourceDTO.getResourcePOCsubmittedDate(), requisitionResourceDTO.getResourceSelectedDate());
                long dateDifference = TimeUnit.DAYS.convert(dateDiff, TimeUnit.MILLISECONDS);                
                cell.setCellValue(dateDifference);              
            }else if (requisitionResourceDTO.getResourceRejectedDate() != null && requisitionResourceDTO.getResourceRejectedDate() != "") {     
                long dateDiff = getDateDifference(requisitionResourceDTO.getResourcePOCsubmittedDate(), requisitionResourceDTO.getResourceRejectedDate());
                long dateDifference = TimeUnit.DAYS.convert(dateDiff, TimeUnit.MILLISECONDS);                
                cell.setCellValue(dateDifference);              
            }else if (requisitionResourceDTO.getPositionCloseDate() != null && requisitionResourceDTO.getPositionCloseDate() != "") {     
                long dateDiff = getDateDifference(requisitionResourceDTO.getResourcePOCsubmittedDate(), requisitionResourceDTO.getPositionCloseDate());
                long dateDifference = TimeUnit.DAYS.convert(dateDiff, TimeUnit.MILLISECONDS);                
                cell.setCellValue(dateDifference);              
            }else {
                cell.setCellValue("");		}
		}else {
			cell.setCellValue("");
		}
		cell.setCellStyle(cellStyle);
		columnIndex++;
        cell = row.createCell(columnIndex);
        if (requisitionResourceDTO.getResourceAMsubmittedDate() != null && requisitionResourceDTO.getResourceAMsubmittedDate() != "") {             
            if(requisitionResourceDTO.getStatus().trim().equalsIgnoreCase("SUBMITTED TO AM TEAM")) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
                Date date = null;
                String convertedDate = null;
                date = dateFormat.parse(requisitionResourceDTO.getResourceAMsubmittedDate());
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                convertedDate = simpleDateFormat.format(date);        
                SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date date1 = myFormat.parse(convertedDate);
                Date date2 = myFormat.parse(DateUtil.getTodaysDate().toString());
                long dateDiff = date2.getTime() - date1.getTime();
                long dateDifference = TimeUnit.DAYS.convert(dateDiff, TimeUnit.MILLISECONDS);
                cell.setCellValue(dateDifference);
		}else if (requisitionResourceDTO.getResourcePOCsubmittedDate() != null && requisitionResourceDTO.getResourcePOCsubmittedDate() != "" && requisitionResourceDTO.getStatus().trim().equalsIgnoreCase("SUBMITTED TO POC")) {     
            long dateDiff = getDateDifference(requisitionResourceDTO.getResourceAMsubmittedDate(), requisitionResourceDTO.getResourcePOCsubmittedDate());
            long dateDifference = TimeUnit.DAYS.convert(dateDiff, TimeUnit.MILLISECONDS);                
            cell.setCellValue(dateDifference);              
        }else if (requisitionResourceDTO.getResourceSelectedDate() != null && requisitionResourceDTO.getResourceSelectedDate() != "") {     
            long dateDiff = getDateDifference(requisitionResourceDTO.getResourceAMsubmittedDate(), requisitionResourceDTO.getResourceSelectedDate());
            long dateDifference = TimeUnit.DAYS.convert(dateDiff, TimeUnit.MILLISECONDS);                
            cell.setCellValue(dateDifference);              
        }else if (requisitionResourceDTO.getResourceRejectedDate() != null && requisitionResourceDTO.getResourceRejectedDate() != "") {     
            long dateDiff = getDateDifference(requisitionResourceDTO.getResourceAMsubmittedDate(), requisitionResourceDTO.getResourceRejectedDate());
            long dateDifference = TimeUnit.DAYS.convert(dateDiff, TimeUnit.MILLISECONDS);                
            cell.setCellValue(dateDifference);              
        }else if (requisitionResourceDTO.getPositionCloseDate() != null && requisitionResourceDTO.getPositionCloseDate() != "") {     
            long dateDiff = getDateDifference(requisitionResourceDTO.getResourceAMsubmittedDate(), requisitionResourceDTO.getPositionCloseDate());
            long dateDifference = TimeUnit.DAYS.convert(dateDiff, TimeUnit.MILLISECONDS);                
            cell.setCellValue(dateDifference);              
        }else {
			cell.setCellValue("");
		}	
        }else {
            cell.setCellValue("");
        }   
		cell.setCellStyle(cellStyle);
		columnIndex++;
		cell = row.createCell(columnIndex);
        if (requisitionResourceDTO.getResourceRejectedDate() != null && requisitionResourceDTO.getResourceRejectedDate() != "") {
            cellStyleForSubTableDates.setDataFormat(creationHelper.createDataFormat().getFormat(("dd-MMM-yy")));
            cell.setCellValue(HSSFDateUtil.getExcelDate(new SimpleDateFormat("dd-MMM-yyyy").parse(requisitionResourceDTO.getResourceRejectedDate())));
        }
        cell.setCellStyle(cellStyleForSubTableDates);
        columnIndex++;
        cell = row.createCell(columnIndex);
        if (requisitionResourceDTO.getResourceSelectedDate() != null && requisitionResourceDTO.getResourceSelectedDate() != "") {
            cellStyleForSubTableDates.setDataFormat(creationHelper.createDataFormat().getFormat(("dd-MMM-yy")));
            cell.setCellValue(HSSFDateUtil.getExcelDate(new SimpleDateFormat("dd-MMM-yyyy").parse(requisitionResourceDTO.getResourceSelectedDate())));
        }
        cell.setCellStyle(cellStyleForSubTableDates);
        columnIndex++;
		} catch (ParseException e) {
			e.printStackTrace();
			logger.debug(e.getMessage());
		}
	}
	
	private long getDateDifference(String resourcePOCsubmittedDate, String statusChangedDate ) {
	    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
        Date date = null;
        Date statusDate = null;
        String convertedDate = null;
        String statusConvertedDate = null;

        long dateDiff =0;
        try {
            date = dateFormat.parse(resourcePOCsubmittedDate);
            statusDate = dateFormat.parse(statusChangedDate);        
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            convertedDate = simpleDateFormat.format(date);    
            statusConvertedDate = simpleDateFormat.format(statusDate);     
            SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date1 = myFormat.parse(convertedDate);
            Date date2 = myFormat.parse(statusConvertedDate);      
            dateDiff = date2.getTime() - date1.getTime();
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return dateDiff;
    }
	
	private void setRowColor(HSSFWorkbook workbook, HSSFRow row, Font font){
		int lastRowIndex = 19;
	    CellStyle cellStyleForRow = workbook.createCellStyle();
		setCellStyleForRow(cellStyleForRow, font);
	    for(int i = 0; i <lastRowIndex; i++){//For each cell in the row 
	    	if(i==2||i==6||i==7)
	    		continue;
	    	row.getCell(i).setCellStyle(cellStyleForRow);
	    }
	}
	
    private void setCellStyleForMainTable(CellStyle cellStyle, Font font){
		cellStyle.setFillForegroundColor(IndexedColors.WHITE.getIndex());
		cellStyle.setFillPattern(XSSFCellStyle.BORDER_THIN);
		cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
		cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		font.setFontName("Arial");
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		font.setItalic(true);
		font.setFontHeightInPoints((short) 8);
		cellStyle.setFont(font);
		cellStyle.setWrapText(true);
	}
	private void setCellStyleForDBTable(CellStyle cellStyle, Font font){
		cellStyle.setFillForegroundColor(IndexedColors.WHITE.getIndex());
		cellStyle.setFillPattern(XSSFCellStyle.BORDER_THIN);
		cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
		cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		font.setFontName("Arial");
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		font.setItalic(true);
		font.setFontHeightInPoints((short) 10);
		cellStyle.setFont(font);
		cellStyle.setWrapText(true);
	}
	private void setCellStyleForRow(CellStyle cellStyle, Font font){
		cellStyle.setFillForegroundColor(IndexedColors.SKY_BLUE.getIndex());
		cellStyle.setFillPattern(XSSFCellStyle.BORDER_THIN);
		cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
		cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		font.setFontName("Arial");
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		font.setItalic(true);
		font.setFontHeightInPoints((short) 8);
		cellStyle.setFont(font);
		cellStyle.setWrapText(true);
	}
	
	private void setCellStyleForStatusCell(CellStyle cellStyle, Font font){
		cellStyle.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
		cellStyle.setFillPattern(XSSFCellStyle.BORDER_THIN);
		cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
		cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		font.setFontName("Arial");
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		font.setItalic(true);
		font.setFontHeightInPoints((short) 8);
		cellStyle.setFont(font);
		cellStyle.setWrapText(true);
	}
	private void setCellStyleForTotPosCell(CellStyle cellStyle, Font font, HSSFWorkbook workbook){
		
		//cellStyle.setFillForegroundColor(IndexedColors.LIGHT_ORANGE.getIndex());
		
		//HSSFWorkbook hwb = new HSSFWorkbook();
		HSSFPalette palette = workbook.getCustomPalette();
		// get the color which most closely matches the color you want to use
		HSSFColor myColor = palette.findSimilarColor(252,213,180);
		// get the palette index of that color 
		short palIndex = myColor.getIndex();
		// code to get the style for the cell goes here
		cellStyle.setFillForegroundColor(palIndex);
		cellStyle.setFillPattern(XSSFCellStyle.BORDER_THIN);
		cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
		cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		font.setFontName("Arial");
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		font.setItalic(true);
		font.setFontHeightInPoints((short) 8);
		cellStyle.setFont(font);
		cellStyle.setWrapText(true);
	}
	private void setCellStyleForOpenCell(CellStyle cellStyle, Font font, HSSFWorkbook workbook){
		cellStyle.setFillForegroundColor(IndexedColors.AQUA.getIndex());
		
		/*HSSFPalette palette = workbook.getCustomPalette();
		HSSFColor myColor = palette.findSimilarColor(185,220,255);
		short palIndex = myColor.getIndex();
		cellStyle.setFillForegroundColor(palIndex);*/
		
		cellStyle.setFillPattern(XSSFCellStyle.BORDER_THIN);
		cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
		cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		font.setFontName("Arial");
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		font.setItalic(true);
		font.setFontHeightInPoints((short) 8);
		cellStyle.setFont(font);
		cellStyle.setWrapText(true);
	}
	
	private void setCellStyleForStatusHeader(CellStyle cellStyle,HSSFWorkbook workbook){
		cellStyle.setFillForegroundColor(IndexedColors.DARK_GREEN.getIndex());
		cellStyle.setFillPattern(XSSFCellStyle.BORDER_THIN);
		cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
		cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		HSSFFont font = workbook.createFont();
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		font.setColor(HSSFColor.WHITE.index);
		font.setFontHeightInPoints((short) 8);
		cellStyle.setFont(font);
		cellStyle.setWrapText(true);
		
	}
	
	private void setCellStyleForTotPosHeader(CellStyle cellStyle,HSSFWorkbook workbook){
		cellStyle.setFillForegroundColor(IndexedColors.LIGHT_ORANGE.getIndex());
		
		cellStyle.setFillPattern(XSSFCellStyle.BORDER_THIN);
		cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
		cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		HSSFFont font = workbook.createFont();
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		font.setFontHeightInPoints((short) 8);
		font.setColor(HSSFColor.WHITE.index);
		cellStyle.setFont(font);
		cellStyle.setWrapText(true);
	}
	private void setCellStyleOrangeHeader(CellStyle cellStyle,HSSFWorkbook workbook){
		cellStyle.setFillForegroundColor(IndexedColors.ORANGE.getIndex());
		
		cellStyle.setFillPattern(XSSFCellStyle.BORDER_THIN);
		cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
		cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		HSSFFont font = workbook.createFont();
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		font.setFontHeightInPoints((short) 8);
		font.setColor(HSSFColor.WHITE.index);
		cellStyle.setFont(font);
		cellStyle.setWrapText(true);
	}
	
	private void setCellStyleForOpenHeader(CellStyle cellStyle,HSSFWorkbook workbook){
		//cellStyle.setFillForegroundColor(IndexedColors.DARK_BLUE.getIndex());
		HSSFPalette palette = workbook.getCustomPalette();
		HSSFColor myColor = palette.findSimilarColor(125,159,227);
		short palIndex = myColor.getIndex();
		cellStyle.setFillForegroundColor(palIndex);
		cellStyle.setFillPattern(XSSFCellStyle.BORDER_THIN);
		cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
		cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		HSSFFont font = workbook.createFont();
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		font.setFontHeightInPoints((short) 8);
		font.setColor(HSSFColor.WHITE.index);
		cellStyle.setFont(font);
		cellStyle.setWrapText(true);
	}
	private void setSimpleCellStyle(CellStyle cellStyle,HSSFWorkbook workbook){
		cellStyle.setFillForegroundColor(IndexedColors.WHITE.getIndex());
		cellStyle.setFillPattern(XSSFCellStyle.BORDER_THIN);
		cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
		cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		HSSFFont font = workbook.createFont();
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		font.setColor(HSSFColor.WHITE.index);
		cellStyle.setFont(font);
		cellStyle.setWrapText(true);
	}
	private void setCellStyleForSubTable(CellStyle cellStyle, Font font,HSSFWorkbook workbook){
		
		HSSFPalette palette = workbook.getCustomPalette();
		cellStyle.setFillForegroundColor(palette.getColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex()).getIndex());
		cellStyle.setFillPattern(XSSFCellStyle.BORDER_THIN);
		cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
		cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		font.setBoldweight(Font.BOLDWEIGHT_NORMAL);
		font.setFontHeightInPoints((short) 8);
		font.setFontName(FONT_CALIBRI);
		cellStyle.setFont(font);
		cellStyle.setWrapText(true);
	}
	
private void setCellStyleForSubTableHeader(HSSFCellStyle cellStyle, Font font,HSSFWorkbook workbook){
	
	//HSSFWorkbook hwb = new HSSFWorkbook();
	HSSFPalette palette = workbook.getCustomPalette();
	// get the color which most closely matches the color you want to use
	HSSFColor myColor = palette.findSimilarColor(225,240,249);
	// get the palette index of that color 
	short palIndex = myColor.getIndex();
	// code to get the style for the cell goes here
	cellStyle.setFillForegroundColor(palIndex);
	cellStyle.setFillPattern(XSSFCellStyle.BORDER_THIN);
	cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
	cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
	cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
	cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
	cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
	font.setBoldweight(Font.BOLDWEIGHT_BOLD);
	font.setFontHeightInPoints((short) 8);
	font.setFontName(FONT_CALIBRI);
	cellStyle.setFont(font);
	cellStyle.setWrapText(true);
	}
	
	/*private String prepareRRReportName(Date startDate, Date endDate, String statusIds){
		String status = null;
		String reportName = "RRF Report";
		if(statusIds != null && statusIds.trim().length()>0){
			if(statusIds.split(",").length<=1){
				status = statusIds.split(",")[0];
			}
		}
		if(status==null){
			reportName = reportName;
		}else if (status.equalsIgnoreCase("A")){
			reportName = "Active "+reportName;
		}else{
			reportName = "Inactive "+reportName;
		}
		if(startDate!=null){
			reportName = reportName+" From "+Constants.DATE_FORMAT_REPORTS.format(startDate);
		}
		if(endDate!=null){
			reportName = reportName+" To "+Constants.DATE_FORMAT_REPORTS.format(endDate);
		}
		return reportName;
	}*/
	
	
	private void setStyleForTableHeader(HSSFCellStyle xssfCellStyleForDataCellResource,HSSFWorkbook workbook){
		
		HSSFColor lightGray =  setCustColor(workbook,(byte) 49, (byte)134,(byte) 155);
		xssfCellStyleForDataCellResource.setFillForegroundColor(lightGray.getIndex());
		xssfCellStyleForDataCellResource.setBorderLeft(HSSFCellStyle.NO_FILL);
		xssfCellStyleForDataCellResource.setBorderBottom(HSSFCellStyle.NO_FILL);
		xssfCellStyleForDataCellResource.setBorderTop(HSSFCellStyle.NO_FILL);
		xssfCellStyleForDataCellResource.setBorderRight(HSSFCellStyle.NO_FILL);
		xssfCellStyleForDataCellResource.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		HSSFFont font = workbook.createFont();
		font.setFontHeightInPoints((short) 8);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		font.setColor(HSSFColor.WHITE.index);
		xssfCellStyleForDataCellResource.setFont(font);
		xssfCellStyleForDataCellResource.setWrapText(true);
		
	}
private void setStyleForDBTableHeader(HSSFCellStyle xssfCellStyleForDataCellResource,HSSFWorkbook workbook){
		HSSFColor lightGray =  setCustColor(workbook,(byte) 49, (byte)134,(byte) 155);
		xssfCellStyleForDataCellResource.setFillForegroundColor(lightGray.getIndex());
		xssfCellStyleForDataCellResource.setBorderLeft(HSSFCellStyle.NO_FILL);
		xssfCellStyleForDataCellResource.setBorderBottom(HSSFCellStyle.NO_FILL);
		xssfCellStyleForDataCellResource.setBorderTop(HSSFCellStyle.NO_FILL);
		xssfCellStyleForDataCellResource.setBorderRight(HSSFCellStyle.NO_FILL);
		xssfCellStyleForDataCellResource.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		HSSFFont font = workbook.createFont();
		font.setFontHeightInPoints((short) 10);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		font.setColor(HSSFColor.WHITE.index);
		xssfCellStyleForDataCellResource.setFont(font);
		xssfCellStyleForDataCellResource.setWrapText(true);
	}
	
	private void prepareExcelDBTable (HSSFSheet sheet, List<RequestRequisitionReport> rrReportDBList,HSSFWorkbook workbook)
	{
		HSSFRow row = null;
		int rowIndex = 0;
		int columnIndex = 0;
		Cell cell = null;
		int initialIndex =0;  
		
		HSSFCellStyle xssfCellStyleForDataCell = ExcelUtility.getHSSFCellStyleForDataCell(workbook);
		Font font = workbook.createFont();
		
		// setting the cell style for main data table 
		CellStyle cellStyle = workbook.createCellStyle();
		setCellStyleForDBTable(cellStyle, font);
		
		
		row = sheet.createRow(rowIndex);
		row.setRowStyle(cellStyle);
		setStyleForDBTableHeader(xssfCellStyleForDataCell, workbook);
		ExcelUtility.createHeaders(row, xssfCellStyleForDataCell, font, DBHEADERNAME,true);
		
		//if(rrReportDBList !=null &&  rrReportDBList.size()>0){
		if(rrReportDBList !=null &&  !rrReportDBList.isEmpty()){
			for(RequestRequisitionReport reportData : rrReportDBList ){
				columnIndex = 0;
				initialIndex = ++rowIndex;
				row = sheet.createRow(rowIndex);
				cell = row.createCell(columnIndex);
				cell.setCellValue(initialIndex);
				cell.setCellStyle(cellStyle);
				//sheet.autoSizeColumn(columnIndex);
				columnIndex++;
				cell = row.createCell(columnIndex);
				cell.setCellValue(reportData.getClientName());
				cell.setCellStyle(cellStyle);
				//sheet.autoSizeColumn(columnIndex);
				columnIndex++;
				cell = row.createCell(columnIndex);
				cell.setCellValue(reportData.getNoOfResources());
				cell.setCellStyle(cellStyle);
				//sheet.autoSizeColumn(columnIndex);
				columnIndex++;
				cell = row.createCell(columnIndex);
				cell.setCellValue(reportData.getOpen());;
				cell.setCellStyle(cellStyle);
				//sheet.autoSizeColumn(columnIndex);
				columnIndex++;
				cell = row.createCell(columnIndex);
				cell.setCellValue(reportData.getClosed());
				cell.setCellStyle(cellStyle);
				//sheet.autoSizeColumn(columnIndex);
				columnIndex++;
				cell = row.createCell(columnIndex);
				if(reportData.getStatus().equalsIgnoreCase("Closed"))
					cell.setCellValue("InActive");
				else
					cell.setCellValue("Active");
				cell.setCellStyle(cellStyle);
				//sheet.autoSizeColumn(columnIndex);
				columnIndex++;
			}
		}
	
	}
	private void prapreExcelMainTable(HSSFSheet mainSheet, List<RequestRequisitionReport> rrReportList,	HSSFWorkbook workbook) {
		HSSFRow row = null;
		HSSFRow subRow = null ;
		int rowIndex = 0;
		int columnIndex = 0;
		Cell cell = null;
		int lastRowIndex = 0;
		int initialIndex =0;  
		HSSFCellStyle xssfCellStyleForDataCell = ExcelUtility.getHSSFCellStyleForDataCell(workbook);
		Font font = workbook.createFont();
		// setting the cell style for main data table 
		CellStyle cellStyle = workbook.createCellStyle();
		setCellStyleForMainTable(cellStyle, font);
		
		//Setting the cell style for sub table
		CellStyle cellStyleSub = workbook.createCellStyle();
		setCellStyleForMainTable(cellStyleSub, font);
		
		//Setting the cell style for Open Status
		CellStyle cellStatusStyle = workbook.createCellStyle();
		setCellStyleForStatusCell(cellStatusStyle, font);
		
		CellStyle cellTotPosStyle = workbook.createCellStyle();
		setCellStyleForTotPosCell(cellTotPosStyle, font,workbook);
		CellStyle cellOpenStyle = workbook.createCellStyle();
		setCellStyleForOpenCell(cellOpenStyle, font,workbook);
		// setting the cell style for sub data table
		CellStyle cellStyleForSubTable = workbook.createCellStyle();
		Font fontForSubtable = workbook.createFont();
		setCellStyleForSubTable(cellStyleForSubTable, fontForSubtable,workbook);
		
		CellStyle cellStyleForSubTableDates = workbook.createCellStyle();
		setCellStyleForSubTable(cellStyleForSubTableDates, fontForSubtable,workbook);
		
		// setting the cell style for sub data table
		/*HSSFCellStyle cellStyleForSubTableHeader = workbook.createCellStyle();
		Font fontForSubtableHeader = workbook.createFont();
		setCellStyleForSubTableHeader(cellStyleForSubTableHeader,fontForSubtableHeader,workbook);	*/
		
		/* setting the headers */
		row = mainSheet.createRow(rowIndex);
		//row.setRowStyle(cellStyle);
		setStyleForTableHeader(xssfCellStyleForDataCell, workbook);
		ExcelUtility.createHeaders(row, xssfCellStyleForDataCell, font, PIVOT_HEADERNAME,true);
		CellStyle cellHeaderStatusStyle = workbook.createCellStyle();
		setCellStyleForStatusHeader(cellHeaderStatusStyle,workbook);
		row.getCell(3).setCellStyle(cellHeaderStatusStyle);
		row.setHeightInPoints(40);
		
		CellStyle cellHeaderTotPosStyle = workbook.createCellStyle();
		setCellStyleForTotPosHeader(cellHeaderTotPosStyle,workbook);
		row.getCell(7).setCellStyle(cellHeaderTotPosStyle);
		row.getCell(9).setCellStyle(cellHeaderTotPosStyle);
		row.getCell(11).setCellStyle(cellHeaderTotPosStyle);
		
		CellStyle cellHeaderOpenStyle = workbook.createCellStyle();
		setCellStyleForOpenHeader(cellHeaderOpenStyle,workbook);
		//row.getCell(7).setCellStyle(cellHeaderOpenStyle);
		//row.getCell(9).setCellStyle(cellHeaderOpenStyle);
		
		CellStyle simpleCellStyle = workbook.createCellStyle();
		setSimpleCellStyle(simpleCellStyle,workbook);
		
		
		// setting the cell style for main data table 
		CellStyle removeCellStyle = workbook.createCellStyle();	
		
		//if(rrReportList !=null &&  rrReportList.size()>0){
		if(rrReportList !=null &&  !rrReportList.isEmpty()){
			for(RequestRequisitionReport reportData : rrReportList ){
				
				columnIndex = 0;
				initialIndex = ++rowIndex;
				row = mainSheet.createRow(rowIndex);
				
				cell = row.createCell(columnIndex);
				cell.setCellValue(reportData.getClientName());
				cell.setCellStyle(cellStyle);
				//mainSheet.autoSizeColumn(columnIndex);
				columnIndex++;
				
				cell = row.createCell(columnIndex);
				cell.setCellValue(reportData.getRequirementId());
				cell.setCellStyle(cellStyle);
				//mainSheet.autoSizeColumn(columnIndex);
				columnIndex++;
				
				cell = row.createCell(columnIndex);
				cell.setCellValue(reportData.getRequirementType());
				cell.setCellStyle(cellStyle);
				//mainSheet.autoSizeColumn(columnIndex);
				columnIndex++;
				
				cell = row.createCell(columnIndex);
				cell.setCellStyle(cellStatusStyle);
				//if(!reportData.getStatus().equalsIgnoreCase("Closed")||!reportData.getStatus().equalsIgnoreCase("Open"))
				String reportDataStatus = reportData.getStatus();
				if(!reportDataStatus.equalsIgnoreCase("Closed")||!reportDataStatus.equalsIgnoreCase("Open"))
					row.setHeightInPoints(30);
				cell.setCellValue(reportData.getReportStatus());
				columnIndex++;
				
				String cellData="";
				cell = row.createCell(columnIndex);
				if(reportData.getAllocationDTO().getId()==2) {
					cell.setCellValue("Billable");
				}
				else if(reportData.getAllocationDTO().getId()==3){
					cell.setCellValue("Partial Billable");
				}
				else{
					cellData=reportData.getAllocationDTO().getAllocationType();
					cell.setCellValue(cellData.replace("Non-Billable", "NB"));
				}
				cell.setCellStyle(cellStyle);
				columnIndex++;	
				
			try {				
					cell = row.createCell(columnIndex);
					CreationHelper creationHelper = workbook.getCreationHelper();
					HSSFCellUtil.setCellStyleProperty((HSSFCell) cell, workbook, CellUtil.DATA_FORMAT,
					HSSFDataFormat.getBuiltinFormat(("dd-MMM-yy")));
				    Date parsedDate = new SimpleDateFormat("MM/dd/yyyy").parse(reportData.getRequestedDate());
				    cellStyleSub.setDataFormat(creationHelper.createDataFormat().getFormat(("dd-MMM-yy")));
					cell.setCellValue(HSSFDateUtil.getExcelDate(parsedDate));
					cell.setCellStyle(cellStyleSub);
					columnIndex++;
					
				} catch (ParseException e) {
					e.printStackTrace();
					logger.debug(e.getMessage());
				}
				
				cell = row.createCell(columnIndex);
				cell.setCellValue(reportData.getLocationName());
				cell.setCellStyle(cellStyle);
				columnIndex++;
				
				cell = row.createCell(columnIndex);
				cell.setCellValue(Integer.valueOf(reportData.getNoOfResources()));
				cell.setCellStyle(cellTotPosStyle);
				columnIndex++;
				
				cell = row.createCell(columnIndex);
				cell.setCellValue(Integer.valueOf(reportData.getOpen()));
				cell.setCellStyle(cellOpenStyle);
				columnIndex++;
				
				cell = row.createCell(columnIndex);
				cell.setCellValue(Integer.valueOf(reportData.getSubmissions()));
				cell.setCellStyle(cellTotPosStyle);
				columnIndex++; 
				
				cell = row.createCell(columnIndex);
				cell.setCellValue(Integer.valueOf(reportData.getClosed()));
				cell.setCellStyle(cellOpenStyle);
				columnIndex++;
				
				cell = row.createCell(columnIndex);
				cell.setCellValue(Integer.valueOf(reportData.getShortlisted()));
				cell.setCellStyle(cellTotPosStyle);
				columnIndex++;
				
				
				
				cell = row.createCell(columnIndex);
				cell.setCellValue(Integer.valueOf(reportData.getNotFitForRequirement()));
				cell.setCellStyle(cellStyle);
				columnIndex++;
				
				cell = row.createCell(columnIndex);
				/*if(reportData.getHold().equalsIgnoreCase(reportData.getNoOfResources()))
					cell.setCellValue("Inactive");
				else*/
				cell.setCellValue(Integer.valueOf(reportData.getHold()));
				cell.setCellStyle(cellStyle);
				columnIndex++;

				cell = row.createCell(columnIndex);
				cell.setCellValue(Integer.valueOf(reportData.getLost()));
				cell.setCellStyle(cellStyle);
				columnIndex++;							
								
				cell = row.createCell(columnIndex);
				cell.setCellValue(reportData.getSkill());
				cell.setCellStyle(cellStyle);
				columnIndex++;
				
				cell = row.createCell(columnIndex);
				cell.setCellValue(reportData.getDesignationName());
				cell.setCellStyle(cellStyle);
				columnIndex++;
				
				cell = row.createCell(columnIndex);
				cell.setCellValue(reportData.getRequestedBy());
				cell.setCellStyle(cellStyle);
				columnIndex++;
				
				cell = row.createCell(columnIndex);
				cell.setCellValue(reportData.getBusinessGroupName());
				cell.setCellStyle(cellStyle);
				columnIndex++;
				
				cell = row.createCell(columnIndex);
				cell.setCellValue(reportData.getHiringUnit());
				cell.setCellStyle(cellStyle);
				columnIndex++;
				
				cell = row.createCell(columnIndex);
				cell.setCellValue(reportData.getReqUnit());
				cell.setCellStyle(cellStyle);
				columnIndex++;
				
	            try {
	                cell = row.createCell(columnIndex);
	                //if (reportData.getRrfCloserDate() != null && reportData.getRrfCloserDate() != "" && Integer.valueOf(reportData.getOpen())==0) {
	                String reportDataRrfCloserDate  = reportData.getRrfCloserDate();
	                if (reportDataRrfCloserDate != null && reportDataRrfCloserDate != "" && Integer.valueOf(reportData.getOpen())==0) {
	                        CreationHelper creationHelper = workbook.getCreationHelper();
	                        HSSFCellUtil.setCellStyleProperty((HSSFCell) cell, workbook, CellUtil.DATA_FORMAT,
	                        HSSFDataFormat.getBuiltinFormat(("dd-MMM-yy")));
	                        Date parsedDate = new SimpleDateFormat("MM/dd/yyyy").parse(reportData.getRrfCloserDate());
	                        //Date parsedDate1 = new SimpleDateFormat("dd-MMM-yy").parse(parsedDate.toString());

	                        cellStyleSub.setDataFormat(creationHelper.createDataFormat().getFormat(("dd-MMM-yy")));
	                        cell.setCellValue(HSSFDateUtil.getExcelDate(parsedDate));

	                    }
    	            } catch (ParseException e) {
    	                e.printStackTrace();
    	                logger.debug(e.getMessage());
    	            }
	                cell.setCellStyle(cellStyleSub);
	                columnIndex++;
				cell = row.createCell(columnIndex);
				if(null!=reportData.getAddtionalComments())
					cell.setCellValue(reportData.getAddtionalComments());
				else
					cell.setCellValue(" ");
				cell.setCellStyle(cellStyle);
				columnIndex++;	
				
				//Code for RMG POC and TACTEAMPOC
				cell = row.createCell(columnIndex);
				if(null!=reportData.getRmgPOC())
					cell.setCellValue(reportData.getRmgPOC());
				else
					cell.setCellValue(" ");
				cell.setCellStyle(cellStyle);
				columnIndex++;
				
				cell = row.createCell(columnIndex);
				if(null!=reportData.getTacTeamPOC())
					cell.setCellValue(reportData.getTacTeamPOC());
				else
					cell.setCellValue(" ");
				cell.setCellStyle(cellStyle);
				columnIndex++;
				
				//For Requirement Area
				cell = row.createCell(columnIndex);
				if(null!=reportData.getRequirementArea()) {
					if(reportData.getRequirementArea().equalsIgnoreCase(Constants.NON_SAP))
						cell.setCellValue(Constants.NON_SAP);
					else if(reportData.getRequirementArea().equalsIgnoreCase(Constants.SAP))
						cell.setCellValue(Constants.SAP);
					else
						cell.setCellValue(Constants.NON_SAP);
				}else {
					cell.setCellValue(Constants.NON_SAP);
				}
				cell.setCellStyle(cellStyle);
				columnIndex++;
				
				cell = row.createCell(columnIndex);
				cell.setCellValue(reportData.getAmJobCode());//AM Job Code
				cell.setCellStyle(cellStyle);
				//sheet.autoSizeColumn(columnIndex);
				columnIndex++;
				
				cell = row.createCell(columnIndex);
				cell.setCellValue(reportData.getCustGroup());
				cell.setCellStyle(cellStyle);
				//sheet.autoSizeColumn(columnIndex);
				columnIndex++;
				
			}
			//mainSheet.autoSizeColumn((short)2);
		}
	}
}
