package org.yash.rms.excel;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
import org.yash.rms.dto.ResourceCommentDTO;
import org.yash.rms.report.dto.RequestRequisitionReport;
import org.yash.rms.util.Constants;


public class RRFSubmissionExcelReportView extends AbstractExcelView {
	
	private static final String FONT_CALIBRI = "Calibri";
	
	private static final String SUBMISSION_REPORT_FILE_NAME = "RRF_SUBMISSION_REPORT";
	
	private static final String RRF_SUBMISSION_REPORT = "RRF Submission Report";
	
	private static final String[] RRF_REPORT_HEADER = {"Client","RRF ID","Requirement Type","Status", "Type", "Resource Name","contact Number", "Experience", "Email id" , "Location","Notice Period",
			"Status","Joining Date","Interview Date","Profile Submitted On","Comments (Resource Submission)s", "Required Skill","Designation",
			"Requested   By","Requesting Practice","Hiring Unit", "Requestor Unit", "RRF Close Date", "Addtional Comments"	,"RMG POC", "TAC Team POC", "Requirement Area"};

	@Override
	protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.debug("----------Entered into RRFSubmissionExcelReportView and start to build excel document-----------");
		
		response.reset();
		response.setContentType("application/vnd.ms-excel");
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date todayDate = new Date();
		
		
		response.setHeader("Content-Disposition","attachment; filename="+SUBMISSION_REPORT_FILE_NAME+"_"+dateFormat.format(todayDate)+".xls");
		
			try {
				prepareExcelReportForRequestSubmission(model, workbook);
				workbook.write(response.getOutputStream());
			}catch (ParseException e) {
				logger.error("Error in parse date.");
			} 
			catch (Exception e) {
				e.printStackTrace();
			}
		
		logger.debug("----------Exit from RRFSubmissionExcelReportView and build excel document finished-----------");
	}
	
	private void prepareExcelReportForRequestSubmission(Map<String, Object> model, HSSFWorkbook workbook) throws ParseException{
		
		String startDate =   (String)model.get("startDate");
		String endDate =   (String)model.get("endDate");
		String statusIds =   (String)model.get("statusIds");
		
		HSSFSheet submissionReportSheet = ExcelUtility.getHSSFSheet(workbook,RRF_SUBMISSION_REPORT);
		
		setHeadersInExcel( workbook,  submissionReportSheet,  startDate,  endDate,statusIds);	
		
		List<RequestRequisitionReport> rrReportList = (List<RequestRequisitionReport>) model.get("rrReportList");
		List<RequestRequisitionReport> rrReportDBList = (List<RequestRequisitionReport>) model.get("rrReportDBList");

		prepareSubmissionReport(submissionReportSheet, rrReportList, workbook);	
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
	private HSSFColor setCustColor(HSSFWorkbook workbook, byte r,byte g, byte b){
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

	 private void prepareSubmissionReport(HSSFSheet sheet, List<RequestRequisitionReport> rrReportList,HSSFWorkbook workbook) throws ParseException {
		
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
		
		// setting the headers 
		row = sheet.createRow(rowIndex);
		//row.setRowStyle(cellStyle);
		setStyleForTableHeader(xssfCellStyleForDataCell, workbook);
		ExcelUtility.createHeaders(row, xssfCellStyleForDataCell, font, RRF_REPORT_HEADER,true);
		CellStyle cellHeaderStatusStyle = workbook.createCellStyle();
		setCellStyleForStatusHeader(cellHeaderStatusStyle,workbook);
		row.setHeightInPoints(40);
		CellStyle cellHeaderTotPosStyle = workbook.createCellStyle();
		setCellStyleForTotPosHeader(cellHeaderTotPosStyle,workbook);
		row.getCell(3).setCellStyle(cellHeaderStatusStyle);
		row.getCell(4).setCellStyle(cellStyleForSubTableHeader);
		row.getCell(5).setCellStyle(cellStyleForSubTableHeader);
		row.getCell(6).setCellStyle(cellStyleForSubTableHeader);
		row.getCell(7).setCellStyle(cellStyleForSubTableHeader);
		row.getCell(8).setCellStyle(cellStyleForSubTableHeader);
		row.getCell(9).setCellStyle(cellStyleForSubTableHeader);
		row.getCell(10).setCellStyle(cellStyleForSubTableHeader);
		row.getCell(11).setCellStyle(cellStyleForSubTableHeader);
		row.getCell(12).setCellStyle(cellStyleForSubTableHeader);
		row.getCell(13).setCellStyle(cellStyleForSubTableHeader);
		row.getCell(14).setCellStyle(cellStyleForSubTableHeader);
		CellStyle cellHeaderOpenStyle = workbook.createCellStyle();
		setCellStyleForOpenHeader(cellHeaderOpenStyle,workbook);
		//row.getCell(7).setCellStyle(cellHeaderOpenStyle);
		CellStyle simpleCellStyle = workbook.createCellStyle();
		setSimpleCellStyle(simpleCellStyle,workbook);
		
		
		// setting the cell style for main data table 
		CellStyle dateCellStyle = workbook.createCellStyle();
        setCellStyleForMainTable(cellStyle, font);

		if(rrReportList !=null &&  rrReportList.size()>0){
			for(RequestRequisitionReport reportData : rrReportList ){
				
				//if(reportData.getRequisitionResourceList() !=null && reportData.getRequisitionResourceList().size()>0){
				List<RequisitionResourceDTO> reportDataRequisitionResourceDTOList = reportData.getRequisitionResourceList();
				if(reportDataRequisitionResourceDTOList !=null && !reportDataRequisitionResourceDTOList.isEmpty()){
					logger.debug("Resource List Size :: "+reportDataRequisitionResourceDTOList.size() );
					
					for(RequisitionResourceDTO requisitionResourceDTO : reportDataRequisitionResourceDTOList){
						
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
						
						cell = row.createCell(columnIndex);
						cell.setCellStyle(cellStatusStyle);
						if(!reportData.getReportStatus().equalsIgnoreCase("Closed")||!reportData.getReportStatus().equalsIgnoreCase("Open"))
							row.setHeightInPoints(30);
						cell.setCellValue(reportData.getReportStatus());
						columnIndex++;
						
						cell = row.createCell(columnIndex);
						cell.setCellValue(requisitionResourceDTO.getResourceType());
						cell.setCellStyle(cellStyleForSubTableDates);
						row.setHeightInPoints(30);
						columnIndex++;
						
						cell = row.createCell(columnIndex);
						cell.setCellValue(requisitionResourceDTO.getResourceName());
						cell.setCellStyle(cellStyleForSubTableDates);
						columnIndex++;
							
						cell = row.createCell(columnIndex);
						cell.setCellValue(requisitionResourceDTO.getContactNum());
						cell.setCellStyle(cellStyleForSubTableDates);
						row.setHeightInPoints(30);
						columnIndex++;
						
						cell = row.createCell(columnIndex);
						cell.setCellValue(requisitionResourceDTO.getTotalExperince());
						cell.setCellStyle(cellStyleForSubTableDates);
						columnIndex++;
						
						cell = row.createCell(columnIndex);
						cell.setCellValue(requisitionResourceDTO.getEmailId());
						cell.setCellStyle(cellStyleForSubTableDates);
						columnIndex++;
						
						cell = row.createCell(columnIndex);
						cell.setCellValue(requisitionResourceDTO.getLocation());
						cell.setCellStyle(cellStyleForSubTableDates);
						columnIndex++;
						
						cell = row.createCell(columnIndex);
						cell.setCellValue(requisitionResourceDTO.getNoticePeriod());
						cell.setCellStyle(cellStyleForSubTableDates);
						columnIndex++;
						
						cell = row.createCell(columnIndex);
						cell.setCellValue(requisitionResourceDTO.getStatus());
						cell.setCellStyle(cellStyleForSubTableDates);
						columnIndex++;		
						
						
						CreationHelper creationHelper = workbook.getCreationHelper();
						HSSFCellUtil.setCellStyleProperty((HSSFCell) cell, workbook, CellUtil.DATA_FORMAT, HSSFDataFormat.getBuiltinFormat(("dd-MMM-yy")));
						
						cell = row.createCell(columnIndex);	
						if (requisitionResourceDTO.getJoiningDate() != null && requisitionResourceDTO.getJoiningDate() != "") {
							cellStyleForSubTableDates.setDataFormat(creationHelper.createDataFormat().getFormat(("dd-MMM-yy")));
							cell.setCellValue(HSSFDateUtil.getExcelDate(new SimpleDateFormat("dd-MMM-yyyy").parse(requisitionResourceDTO.getJoiningDate())));
						}
						cell.setCellStyle(cellStyleForSubTableDates);
						columnIndex++;
						
						cell = row.createCell(columnIndex);	
					    if (requisitionResourceDTO.getInterviewDate() != null && requisitionResourceDTO.getInterviewDate() != "") {
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
						
						cell = row.createCell(columnIndex);
						String comment =  "";
						for(ResourceCommentDTO commentDTO : requisitionResourceDTO.getAllResourceCommentByResourceId()){
							comment = comment + commentDTO.getCommentBy() + "( On "+commentDTO.getComment_Date().split(" ")[0]+") - " + commentDTO.getResourceComment() + "\n"; 
						}
						cell.setCellValue(comment);
						cell.setCellStyle(cellStyle);
						columnIndex++;
																
						cell = row.createCell(columnIndex);
						cell.setCellValue(requisitionResourceDTO.getSkill());
						cell.setCellStyle(cellStyle);
						columnIndex++;
						
						cell = row.createCell(columnIndex);
						cell.setCellValue(requisitionResourceDTO.getDesignation());
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
				        if (reportData.getRrfCloserDate() != null && reportData.getRrfCloserDate() != "" && Integer.valueOf(reportData.getOpen())==0) {
		                        CreationHelper creationHelper1 = workbook.getCreationHelper();
		                        HSSFCellUtil.setCellStyleProperty((HSSFCell) cell, workbook, CellUtil.DATA_FORMAT,
		                        HSSFDataFormat.getBuiltinFormat(("dd-MMM-yy")));
		                        Date parsedDate = new SimpleDateFormat("MM/dd/yyyy").parse(reportData.getRrfCloserDate());
		                        //Date parsedDate1 = new SimpleDateFormat("dd-MMM-yy").parse(parsedDate.toString());

		                        cellStyleSub.setDataFormat(creationHelper1.createDataFormat().getFormat(("dd-MMM-yy")));
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
						
						//For requirement Area - start
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
						//For requirement Area - end
						
					}
					//sheet.autoSizeColumn((short)2);
				}
					
					
					
					}
				}
				
				
	}

}
