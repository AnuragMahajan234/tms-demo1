package org.yash.rms.excel;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.springframework.web.servlet.view.document.AbstractExcelView;

public class CurrentWeekAllocationReportExcelUtility extends AbstractExcelView {


	@Override
	protected void buildExcelDocument(Map<String, Object> model,HSSFWorkbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		logger.debug("----------Inside CurrentWeekAllocationReportExcelUtility Start buildExcelDocument-----------");
		@SuppressWarnings("unchecked")
		Map<String, List<Object[]>> dataMap = (Map<String, List<Object[]>>) model.get("cwaMap");

		response.reset();
		//response.setHeader("Expires:", "0");

		if (dataMap != null) {
			try {
				HSSFWorkbook workBook = CurrentWeekAllocationReportExcelUtility.generateExcelForCWARport(dataMap);
				response.setContentType("application/vnd.ms-excel");
				response.setHeader("Content-Disposition","attachment; filename=CurrentWeekAllocationReport.xls");
				workBook.write(response.getOutputStream());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		logger.debug("----------Inside CurrentWeekAllocationReportExcelUtility End buildExcelDocument-----------");
	}

	public static HSSFWorkbook generateExcelForCWARport(Map<String, List<Object[]>> dataMap) {
		// Create Workbook
		HSSFWorkbook workbook = new HSSFWorkbook();
		generateCurrentWeekAllocationSheet(dataMap,workbook, "CurrentWeekAllocation");
		return workbook;
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

	private static void setFontStyleRow(HSSFWorkbook workbook,CellStyle styleBUReport) {

		Font font = workbook.createFont();
		font.setFontName("Calibri");
		font.setFontHeightInPoints((short) 11);// Create font
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);// Make font bold
		styleBUReport.setFont(font);
	}
	
	private static void setFontStyleAndColor(HSSFWorkbook workbook,CellStyle styleBUReport) {

		Font font = workbook.createFont();
		font.setFontName("Calibri");
		font.setFontHeightInPoints((short) 11);// Create font
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);// Make font bold
		styleBUReport.setFont(font);
	}

	private static void setHeaderFont(HSSFWorkbook workbook,CellStyle styleBUReport) {

		Font font = workbook.createFont();
		font.setFontName("Calibri");
		font.setFontHeightInPoints((short) 20);// Create font
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);// Make font bold
		styleBUReport.setFont(font);
	}
	
	private static void setOrangeFont(HSSFWorkbook workbook,CellStyle styleBUReport) {

		Font font = workbook.createFont();
		font.setFontName("Calibri");
		font.setFontHeightInPoints((short) 11);// Create font
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);// Make font bold
		
		HSSFPalette palette = workbook.getCustomPalette();
		HSSFColor myColor = palette.findSimilarColor(151, 71, 6);
		short palIndex = myColor.getIndex();
		
		font.setColor(palIndex);
		styleBUReport.setFont(font);
	}

	private static void setOrangeFontAndSize(HSSFWorkbook workbook,CellStyle styleBUReport) {

		Font font = workbook.createFont();
		font.setFontName("Calibri");
		font.setFontHeightInPoints((short) 16);// Create font
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);// Make font bold
		
		HSSFPalette palette = workbook.getCustomPalette();
		HSSFColor myColor = palette.findSimilarColor(151, 71, 6);
		short palIndex = myColor.getIndex();
		
		font.setColor(palIndex);
		styleBUReport.setFont(font);
	}
	

	private static CellStyle lightOrangeColor(HSSFWorkbook workbook) {

		HSSFCellStyle  style = workbook.createCellStyle();
		setFontStyleRow(workbook, style);
		borderStyle(style);

		HSSFPalette palette = workbook.getCustomPalette();
		HSSFColor myColor = palette.findSimilarColor(255, 179, 128);
		short palIndex = myColor.getIndex();
		style.setFillForegroundColor(palIndex);
		return style;
	}

	private static CellStyle backGroundGoldWithLargeFontSize(HSSFWorkbook workbook) {

		CellStyle style = workbook.createCellStyle();
		setOrangeFontAndSize(workbook, style);
		borderStyle(style);
		style.setFillForegroundColor(IndexedColors.GOLD.getIndex());
		return style;
	}

	private static CellStyle backGroundOrange(HSSFWorkbook workbook) {

		CellStyle style = workbook.createCellStyle();
		setFontStyleRow(workbook, style);
		borderStyle(style);
		style.setFillForegroundColor(IndexedColors.TAN.getIndex());
		return style;
	}

	private static CellStyle backGroundGray(HSSFWorkbook workbook) {

		CellStyle style = workbook.createCellStyle();
		setOrangeFont(workbook, style);
		borderStyle(style);
		style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		return style;
	}
	
	private static CellStyle lightOrangeyFont(HSSFWorkbook workbook) {

		CellStyle style = workbook.createCellStyle();
		setOrangeFont(workbook, style);
		borderStyle(style);
		style.setFillForegroundColor(IndexedColors.WHITE.getIndex());
		return style;
	}

	private static CellStyle backGroundGrayForDataSet(HSSFWorkbook workbook) {

		CellStyle style = workbook.createCellStyle();
		setFontStyleRow(workbook, style);
		borderStyle(style);
		style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		return style;
	}

	private static CellStyle backGroundGoldDataHeader(HSSFWorkbook workbook) {

		CellStyle style = workbook.createCellStyle();
		setFontStyleRow(workbook, style);
		borderStyle(style);
		style.setFillForegroundColor(IndexedColors.GOLD.getIndex());
		return style;
	}

	private static void generateCurrentWeekAllocationSheet(Map<String, List<Object[]>> dataMap,
			HSSFWorkbook workBook, String sheetName) {

		HSSFSheet cwaReportSheet = workBook.createSheet(sheetName);
		// Create row object
		int firstRow=1;
		int lastRow=0;
		int totalNumberOfEmp=0;
		HSSFRow cwaRow;
		int increasingRow = 0;
		
		Date inputEndDate = null;
		
		List<Integer> inputbuIds = new ArrayList<Integer>();
		List<Integer> inputLocationIds = new ArrayList<Integer>(); 
		List<Integer> inputProjIds = new ArrayList<Integer>();
		List<Integer> inputOwnerShipIds = new ArrayList<Integer>();
		List<Integer> inputCurrentBUIds = new ArrayList<Integer>();
		
		CellStyle styleGoldMainHeader = backGroundGoldWithLargeFontSize(workBook);
		CellStyle styleGrayFilterHeader = backGroundGray(workBook);
		CellStyle styleGoldDataHeader = backGroundGoldDataHeader(workBook);
		CellStyle styleOrangeHeader = backGroundOrange(workBook);
		CellStyle styleGrayDataSet = backGroundGrayForDataSet(workBook);
		CellStyle orangeFont = lightOrangeyFont(workBook);
		
		inputEndDate = populateInputDataFromMap(inputEndDate, inputbuIds, inputLocationIds, inputProjIds, inputOwnerShipIds, inputCurrentBUIds, dataMap);

		createMainHeader(cwaReportSheet, styleGoldMainHeader);

		createFilterdRequstHeader(cwaReportSheet,styleGrayFilterHeader, orangeFont);


		increasingRow = 7;
		HSSFRow cwaHeaderRow = cwaReportSheet.createRow(increasingRow);
		increasingRow++;
		CWADataHeader[] headers = CWADataHeader.values();

		CreateHeaderForCWA(cwaHeaderRow,headers,styleGoldDataHeader);

		for (String cwaKey : dataMap.keySet()) {
			if(cwaKey.equals("inputItem"))
				continue;
			System.out.println("Map Key = " +cwaKey );
			List<Object[]> cwaList = dataMap.get(cwaKey);
			cwaRow = cwaReportSheet.createRow(increasingRow++);
			CreateHeaderForGroupEntity(cwaRow,cwaKey,styleOrangeHeader, cwaList.size());

			for (int j = 0; j < cwaList.size(); j++) {
				Object[] cwaRowData = cwaList.get(j);
				cwaRow = cwaReportSheet.createRow(increasingRow++);
				if(j == 0){
					System.out.println("List size = "+cwaList.size());
					totalNumberOfEmp = totalNumberOfEmp+cwaList.size();
					firstRow = increasingRow;
					lastRow= increasingRow+cwaList.size();
				}
				for (int i = 0; i < cwaRowData.length+2; i++) {
					//cwaReportSheet.autoSizeColumn(i);
					Cell cell = cwaRow.createCell(i);
					if(i>=2)
						setCellValueAndStyle(cell ,cwaRowData[i-2], styleGrayDataSet,i);
				}
			}
			//Group the Rows together
			cwaReportSheet.groupRow(firstRow-1,lastRow-1);	
			cwaReportSheet.setRowGroupCollapsed(firstRow-1, true);

			/*Creating Blank Row*/
			cwaRow = cwaReportSheet.createRow(increasingRow++);
			for (int i = 0; i < headers.length; i++) {
				//cwaReportSheet.autoSizeColumn(i);
				Cell cell = cwaRow.createCell(i);
			}
			/*Creating Blank Row*/
		}
		cwaRow = cwaReportSheet.createRow(increasingRow++);
		CreateRowForTotalGroupEntity(cwaRow,"Total",styleGrayDataSet, totalNumberOfEmp);
	}

	private static Date populateInputDataFromMap(Date inputEndDate,List<Integer> inputbuIds, List<Integer> inputLocationIds,
			List<Integer> inputProjIds, List<Integer> inputOwnerShipIds,List<Integer> inputCurrentBUIds, Map<String, List<Object[]>> dataMap) {
		for (String cwaKey : dataMap.keySet()) {
			if(cwaKey.equals("inputItem")){
				List<Object[]> inputList = dataMap.get(cwaKey);
				Object[] objects = inputList.get(0);
				inputEndDate = (Date) objects[0];
				inputbuIds.addAll((List<Integer>) objects[1]);
				inputLocationIds.addAll((List<Integer>) objects[2]);
				inputProjIds.addAll((List<Integer>) objects[3]);
				inputOwnerShipIds.addAll((List<Integer>) objects[4]);
				inputCurrentBUIds.addAll((List<Integer>) objects[5]);
				
				break;
			}
		}
		return inputEndDate;
	}

	/**
	 * @param cwaReportSheet
	 * @param styleGoldMainHeader
	 */
	private static void createMainHeader(HSSFSheet cwaReportSheet,
			CellStyle styleGoldMainHeader) {
		HSSFRow cwaDataHeaderRow = cwaReportSheet.createRow((short) 0);
		for (int i = 0; i < 9; i++) {
			Cell cwaDataHeaderCell = cwaDataHeaderRow.createCell((short) 0);
			String objString =  "Current Week Allocation";
			cwaDataHeaderCell.setCellType(XSSFCell.CELL_TYPE_STRING);
			cwaDataHeaderCell.setCellValue(objString);
			cwaDataHeaderCell.setCellStyle(styleGoldMainHeader);

		}
		cwaReportSheet.addMergedRegion(new CellRangeAddress(0, 1, 0, 9));
	}

	private static void createFilterdRequstHeader(HSSFSheet cwaReportSheet,	CellStyle styleGrayFilterHeader, CellStyle orangeFont) {

		CWARequstHeader[] headers = CWARequstHeader.values();
		int number=0;
		for (int j = 0; j < headers.length; j++) {
			number = j;
			HSSFRow cwaDataHeaderRow = cwaReportSheet.createRow(number+2);
			for (int i = 0; i <= 2; i++) {
				Cell cellEmployeeID = cwaDataHeaderRow.createCell(i);
				if(i==1){
					cellEmployeeID.setCellValue(headers[j].headerName());
					cellEmployeeID.setCellStyle(styleGrayFilterHeader);
				}else if(i==2){
					number=0;
					number = j;
					cellEmployeeID.setCellValue("All");
					cellEmployeeID.setCellStyle(orangeFont);
				}
			}
		}
	}


	private static void CreateRowForTotalGroupEntity(HSSFRow cwaRow, String cwaKey,CellStyle styleBlue, int numberOfEmp) {

		for (int j = 0; j < 2; j++) {

			Cell cellEmployeeID = cwaRow.createCell(j);
			if(j==0)
				cellEmployeeID.setCellValue(cwaKey);
			else
				cellEmployeeID.setCellValue(numberOfEmp);	
			cellEmployeeID.setCellStyle(styleBlue);
		}
	}

	private static void CreateHeaderForGroupEntity(HSSFRow cwaRow, String cwaKey,CellStyle styleBlue, int numberOfEmp) {

		for (int j = 0; j < 2; j++) {

			Cell cellEmployeeID = cwaRow.createCell(j);
			if(j==0)
				cellEmployeeID.setCellValue(cwaKey);
			else
				cellEmployeeID.setCellValue(numberOfEmp);	
			cellEmployeeID.setCellStyle(styleBlue);
		}
	}


	private static void CreateHeaderForCWA(HSSFRow CWADataHeaderRow,CWADataHeader[] headers, CellStyle styleBlue) {

		for (int j = 0; j < headers.length; j++) {
			Cell cellEmployeeID = CWADataHeaderRow.createCell(j);
			cellEmployeeID.setCellValue(headers[j].headerName());
			cellEmployeeID.setCellStyle(styleBlue);
		}
	}

	private static void setCellValueAndStyle(Cell dataCell, Object dataObj, CellStyle styleGrayDataSet, int cellNumber) {

		if (dataObj instanceof Integer) {
			Integer objInteger = (Integer) dataObj;
			dataCell.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
			dataCell.setCellValue(objInteger.intValue());
			dataCell.setCellStyle(styleGrayDataSet);
		}  else if (dataObj instanceof String) {
			String objString = (String) dataObj;
			dataCell.setCellType(XSSFCell.CELL_TYPE_STRING);
			dataCell.setCellValue(objString);
			dataCell.setCellStyle(styleGrayDataSet);
		} 
	}

}


enum CWADataHeader {
	Status("Status"),
	No_Of_Emp("No. Of Emp"),
	AllocationType("Allocation Type"),
	Employee_Id("Employee Id"),
	Employee_Name("Employee Name"),
	Project_Name("Project Name"),
	Designation_Name("Designation Name"),
	Grade("Grade"),
	Base_Location("Base Location"),
	Bu_Name("Bu Name");

	private String cwaDataHeader;

	CWADataHeader(String cwaDataHeader) {
		this.cwaDataHeader = cwaDataHeader;
	}
	public String headerName() {
		return cwaDataHeader;
	}
}

enum CWARequstHeader {
	Project_BU("Project BU"),
	Location("Location"),
	Project("Project"),
	Ownership("Ownership"),
	Resource_BU("Resource BU");

	private String cwaHeader;

	CWARequstHeader(String cwaHeader) {
		this.cwaHeader = cwaHeader;
	}
	public String headerName() {
		return cwaHeader;
	}
}


