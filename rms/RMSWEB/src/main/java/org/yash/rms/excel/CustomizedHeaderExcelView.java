package org.yash.rms.excel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.view.document.AbstractExcelView;
import org.yash.rms.report.dto.MuneerReport;
public class CustomizedHeaderExcelView extends AbstractExcelView {

	private static final Logger logger = LoggerFactory.getLogger(CustomizedHeaderExcelView.class);


	@Override
	protected void buildExcelDocument(Map<String, Object> model,HSSFWorkbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		logger.debug("----------Inside CustomizedHeaderExcelView Start buildExcelDocument-----------");

		@SuppressWarnings("unchecked")
		Set<MuneerReport> customizedHeaderList = (Set<MuneerReport>) model.get("report");
		response.reset();
		//response.setHeader("Expires:", "0");
		if (customizedHeaderList != null) {
			try {
				HSSFWorkbook workBook = CustomizedHeaderExcelView.generateExcelSheet(customizedHeaderList);
				response.setContentType("application/vnd.ms-excel");
				response.setHeader("Content-Disposition","attachment; filename=CustomizedHeaderReport.xls");
				workBook.write(response.getOutputStream());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		logger.debug("----------Inside CustomizedHeaderExcelView End buildExcelDocument-----------");
	}

	public static HSSFWorkbook generateExcelSheet(Set<MuneerReport> customizedHeaderList) {

		// Create Workbook
		HSSFWorkbook workbook = new HSSFWorkbook();
		generateSelectedHeaderResourceSheet(customizedHeaderList,workbook, "REPORT_SHEET");
		return workbook;
	}

	private static void generateSelectedHeaderResourceSheet(Set<MuneerReport> customizedHeaderSet,
			HSSFWorkbook workBook, String sheetName) {

		List<MuneerReport> customizedHeaderList = new ArrayList(customizedHeaderSet);
		HSSFSheet customizedReportSheet = workBook.createSheet(sheetName);
		// Create row object
		HSSFRow row = null;  
		int rowId = 0;
		CellStyle styleBlue = backgroundBlue(workBook);
		/*HSSFRow headerRow = customizedReportSheet.createRow(0);
		rowId++;*/
		String[] headers = new String[25];
		if(!customizedHeaderList.isEmpty())
		 headers = getReportHeaders(customizedHeaderList.get(0));
		else
		headers= new String[]  {"Yash_Emp_Id","Resource_Allocation_End_Date","Release_Date","Primary_skill","Primary_Project","Grade","Employee_Name","Designation","Date_Of_Joining","Currnet_Bu","Current_Location","Base_Location","Allocation_Type","Allocation_Start_Date" };
		
		CellStyle setFont = setFontStyleHeader(workBook);

		for (int j = 0; j <= customizedHeaderList.size(); j++) {

			row = customizedReportSheet.createRow(rowId++);

			for (int i = 0; i < headers.length; i++) {
				customizedReportSheet.autoSizeColumn(i);
				if(j==0){
					Cell cellEmployeeID = row.createCell(i);
					cellEmployeeID.setCellValue(headers[i]);
					cellEmployeeID.setCellStyle(styleBlue);
				}else{
					MuneerReport report = customizedHeaderList.get(j-1);
					Cell cell = row.createCell(i);
					setCellValueAndStyle(cell ,report, setFont, headers[i]);
				}
			}
		}
		
		if(customizedHeaderList.isEmpty()){
			for (int i = 0; i < headers.length; i++) {
				customizedReportSheet.autoSizeColumn(i);
					Cell cellEmployeeID = row.createCell(i);
					cellEmployeeID.setCellValue(headers[i]);
					cellEmployeeID.setCellStyle(styleBlue);
			}
		}
		

	}

	private static String[] getReportHeaders(MuneerReport report) {
		String[] strArray = report.getSelectedHeaders().split(",");
		// TODO Auto-generated method stub
		return strArray;
	}

	private static void setCellValueAndStyle(Cell dataCell, MuneerReport dataObj, CellStyle setFont, String headerName) {
		headerName = headerName.trim();
		if (headerName.equals("Yash_Emp_Id")) {
			setNumericValueToExcelCell(dataCell, dataObj.getYashEmpID(), setFont);
		} else if (headerName.equals("Employee_Name")) {
			setStringValueToExcelCell(dataCell, dataObj.getEmployeeName(), setFont);
		} else if (headerName.equals("Designation")) {
			setStringValueToExcelCell(dataCell, dataObj.getDesignation(), setFont);
		}else if (headerName.equals("Grade")) {
			setStringValueToExcelCell(dataCell, dataObj.getGrade(), setFont);
		}else if (headerName.equals("Release_Date")) {
			setDateValueToExcelCell(dataCell, dataObj.getReleaseDate(), setFont);
		}else if (headerName.equals("Base_Location")) {
			setStringValueToExcelCell(dataCell, dataObj.getBaseLocation(), setFont);
		}else if (headerName.equals("Current_Location")) {
			setStringValueToExcelCell(dataCell, dataObj.getCurrentLocation(), setFont);
		}else if (headerName.equals("Currnet_Bu")) {
			setStringValueToExcelCell(dataCell, dataObj.getCurrentBu(), setFont);
		}else if (headerName.equals("Primary_Project")) {
			setStringValueToExcelCell(dataCell, dataObj.getPrimaryProject(), setFont);
		}else if (headerName.equals("Allocation_Start_Date")) {
			setDateValueToExcelCell(dataCell, dataObj.getAllocationStartDate(), setFont);
		}else if (headerName.equals("Resource_Allocation_End_Date")) {
			setDateValueToExcelCell(dataCell, dataObj.getAllocationEndDate(), setFont);
		}else if (headerName.equals("Primary_skill")) {
			setStringValueToExcelCell(dataCell, dataObj.getPrimarySkill(), setFont);
		}else if (headerName.equals("Allocation_Type")) {
			setStringValueToExcelCell(dataCell, dataObj.getAllocationType(), setFont);
		}
	}

	private static void setDateValueToExcelCell(Cell dataCell,Date date, CellStyle setFont) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
		try {
			if(date != null){
				String dateToStr = sdf.format(date);
				dataCell.setCellValue(dateToStr);
				dataCell.setCellStyle(setFont);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void setStringValueToExcelCell(Cell dataCell,String value, CellStyle setFont) {
		dataCell.setCellType(XSSFCell.CELL_TYPE_STRING);
		dataCell.setCellValue(value);
		dataCell.setCellStyle(setFont);

	}

	/**
	 * @param dataCell
	 * @param numericValue
	 * @param setFont
	 */
	private static void setNumericValueToExcelCell(Cell dataCell,String numericValue, CellStyle setFont) {
		dataCell.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
		dataCell.setCellValue(numericValue);
		dataCell.setCellStyle(setFont);
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


	private static void setFontStyleRow(HSSFWorkbook workbook,
			CellStyle styleBUReport) {

		Font font = workbook.createFont();
		font.setFontName("Calibri");
		font.setFontHeightInPoints((short) 11);// Create font
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);// Make font bold
		styleBUReport.setFont(font);
	}


	private static CellStyle backgroundBlue(HSSFWorkbook workbook) {

		CellStyle style = workbook.createCellStyle();
		setFontStyleRow(workbook, style);
		borderStyle(style);
		style.setFillForegroundColor(HSSFColor.CORNFLOWER_BLUE.index);
		return style;
	}



}





