package org.yash.rms.excel;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.WordUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFCellUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellUtil;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.view.document.AbstractExcelView;
import org.yash.rms.report.dto.RMReport;
import org.yash.rms.util.DateUtil;

public class RMReportExcelView extends AbstractExcelView {

	private static final Logger logger = LoggerFactory
			.getLogger(RMReportExcelView.class);

	@SuppressWarnings({ "unchecked" })
	@Override
	protected void buildExcelDocument(Map<String, Object> model,
			HSSFWorkbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		logger.debug("Started buildExcelDocument in RMReportExcelView Class  ");
		Set<RMReport> list = (Set<RMReport>) model.get("rmReportsList");
		String roleid = (String) model.get("role");
		String reportId = (String) model.get("reportId");
		if(reportId.equalsIgnoreCase("-1")) {
			reportId = "All";
		}
		Date todaysDate = DateUtil.getTodaysDate();
		Sheet sheet = workbook.createSheet("RMREPORT_SHEET_" + DateUtil.getDateInDD_MMM_yyyyFormat(todaysDate));
		response.setHeader("Content-Disposition","attachment; filename=RM_Report_"+ DateUtil.getDateInDD_MMM_yyyyFormat(todaysDate)+".xls");
		sheet.setDefaultColumnWidth(28);

		HSSFCellStyle styleHeader = ExcelUtility.getCellStyleForHeaders(workbook);
		HSSFCellStyle styleRow = ExcelUtility.getHSSFCellStyleForDataCell(workbook);
		
		HSSFCellStyle styleOfColouredRow = ExcelUtility.getHSSFCellStyleForColouredDataCell(workbook);
		Map<Integer, String> sortKey = new HashMap<Integer, String>();
		Cell cell = null;
		Row row = null;

		try {

			if (!list.isEmpty()) {

				int rownum = 0;
				int j = 1;
				for (RMReport rmReport : list) {

					row = sheet.createRow(rownum++);
					int cellnum = 0;
					JSONObject jsonObject = new JSONObject(
							RMReport.toJson(rmReport));

					jsonObject.put("S.No", j++);

					if (sortKey.isEmpty()) {

						/*Row row1 = sheet.createRow(0);

						for (int i = 0; i <= 31; i++) {
							sheet.addMergedRegion(new CellRangeAddress(0, 0, 0,
									i));

						}

						cell = row1.createCell(0);
						cell.setCellStyle(styleHeader);
						cell.setCellValue("                                                                               RMS Report Detail for " +reportId+ "																																																																																																																																																																	        ");
*/
						/*for (int i = 0; i <= 28; i++) {
							sheet.addMergedRegion(new CellRangeAddress(0, 1, 0,
									i));
						}

						cell = row1.createCell(1);
						cell.setCellStyle(styleHeader);*/

						for (Object objKey : jsonObject.keySet()) {
							String key = (String) objKey;
							if (key.equalsIgnoreCase("S.No")) {
								sortKey.put(0, key);
							} else if (key.equalsIgnoreCase("YashEmpID")) {
								sortKey.put(1, key);
							} else if (key.equalsIgnoreCase("EmployeeName")) {
								sortKey.put(2, key);
							} else if (key.equalsIgnoreCase("EmailId")) {
								sortKey.put(3, key);
							} else if (key.equalsIgnoreCase("Designation")) {
								sortKey.put(4, key);
							} else if (key.equalsIgnoreCase("Grade")) {
								sortKey.put(5, key);
							} else if (key.equalsIgnoreCase("DateOfJoining")) {
								sortKey.put(6, key);
							} else if (key.equalsIgnoreCase("ReleaseDate")) {
								if (!roleid.equalsIgnoreCase("Release_Date_IS_NULL")) {
								sortKey.put(7, key);
								}
							} else if (key.equalsIgnoreCase("BaseLocation")) {
								sortKey.put(8, key);
							} else if (key.equalsIgnoreCase("CurrentLocation")) {
								sortKey.put(9, key);
							} else if (key.equalsIgnoreCase("ParentBu")) {
								sortKey.put(10, key);
							} else if (key.equalsIgnoreCase("CurrentBu")) {
								sortKey.put(11, key);
							} else if (key.equalsIgnoreCase("Ownership")) {
								sortKey.put(12, key);
							} else if (key.equalsIgnoreCase("CurrentRM1")) {
								sortKey.put(13, key);
							} else if (key.equalsIgnoreCase("CurrentRM2")) {
								sortKey.put(14, key);
							} else if (key.equalsIgnoreCase("PrimaryProject")) {
								if(!reportId.equalsIgnoreCase("Discrepancy")){
								sortKey.put(15, key);
								}
							} else if (key.equalsIgnoreCase("CurrentProjectIndicator")) {
								sortKey.put(16, key);
							} else if (key.equalsIgnoreCase("CustomerName")) {
								if(!reportId.equalsIgnoreCase("Discrepancy")){
								sortKey.put(17, key);
								}
								if(reportId.equalsIgnoreCase("All")) {
								    sortKey.remove(17, key); 
								}
							} else if (key.equalsIgnoreCase("ProjectBu")) {
								sortKey.put(18, key);
							} else if (key.equalsIgnoreCase("AllocationStartDate")) {
								sortKey.put(19, key);
							} else if (key.equalsIgnoreCase("AllocationType")) {
								sortKey.put(20, key);
							} else if (key.equalsIgnoreCase("ResourceType")) {
								sortKey.put(21, key);
							} else if (key.equalsIgnoreCase("TransferDate")) {
								sortKey.put(22, key);
							} else if (key.equalsIgnoreCase("Visa")) {
								sortKey.put(23, key);
							} else if (key.equalsIgnoreCase("Competency")) {
								sortKey.put(24, key);
							} else if (key.equalsIgnoreCase("PrimarySkill")) {
								sortKey.put(25, key);
							} else if (key.equalsIgnoreCase("SecondarySkill")) {
								sortKey.put(26, key);
							} else if (key.equalsIgnoreCase("CustomerId")) {
								sortKey.put(27, key);
							} else if (key.equalsIgnoreCase("ProjectEndDate")) {
								sortKey.put(28, key);
							} else if (key.equalsIgnoreCase("LastUpdateBy")) {
								sortKey.put(29, key);
							} else if (key.equalsIgnoreCase("LastupdateTimeStamp")) {
								sortKey.put(30, key);
							} else if (key.equalsIgnoreCase("ResumeYN")) {
								sortKey.put(31, key);
							} else if (key.equalsIgnoreCase("TefYN")) {
								sortKey.put(32, key);
							}
							else if (key.equalsIgnoreCase("projectManager")) {
								sortKey.put(33, key);
							}
							else if (key.equalsIgnoreCase("TotalExp")) {
								sortKey.put(34, key);
							}
							else if (key.equalsIgnoreCase("RelExp")) {
								sortKey.put(35, key);
							}
							else if (key.equalsIgnoreCase("YashExp")) {
								sortKey.put(36, key);
							}
							else if (key.equalsIgnoreCase("allocatedSince")) {
								sortKey.put(37, key);
							}
							else if (key.equalsIgnoreCase("deliveryManager")) {
								sortKey.put(38, key);
							}
						}

						String newFieldName = "";
						for (String fieldName : sortKey.values()) {
							
						cell = row.createCell(cellnum++);
						newFieldName = WordUtils.capitalize(fieldName);
						String output = newFieldName.replaceAll("(\\p{Ll})(\\p{Lu})","$1 $2");
						if ((output.equalsIgnoreCase("Resume YN"))) {
							output = "Resume Y/N";
						}
						if ((output.equalsIgnoreCase("Tef YN"))) {
							output = "Tef Y/N";
						}
						cell.setCellValue(output);
						//cell.setCellValue(fieldName.toUpperCase());
						cell.setCellStyle(styleHeader);

						/*for (int k = 0; k <= jsonObject.length(); k++) {
								sheet.autoSizeColumn(k);
							}*/
						}
						cellnum = 0;
						row = sheet.createRow(rownum++);
					}

					String currentBUnit = "";
					String projectBUnit = "";
					for (String strSortKey : sortKey.values()) {
						boolean currentBUnitAvailable = false;
						boolean projectBUnitAvailable = false;
						boolean changeColor = false;
					
							currentBUnit = jsonObject.get("currentBu").toString();
						projectBUnit = jsonObject.get("projectBu").toString();
							if(currentBUnit != "null") {
							
								currentBUnitAvailable = true;
							}
							if(projectBUnit == "null")
							{
								changeColor = false;         
							}
							if(projectBUnit != "null") {
								
								projectBUnitAvailable = true;
							
							}
							
							if(currentBUnitAvailable && projectBUnitAvailable){
								
								if(!currentBUnit.equals(projectBUnit)) {
									changeColor = true;
									//break;
								}
							}
						Object object = jsonObject.get(strSortKey);

						cell = row.createCell(cellnum++);
						if(changeColor) {
						// logic to change color
							cell.setCellStyle(styleOfColouredRow);
							
						} 
						else {
						cell.setCellStyle(styleRow);
						}

						if (object.equals(null)) {
							cell.setCellValue("  ");
						} else if (object instanceof String) {
							cell.setCellValue((String) object);
						}
						if (object instanceof Date) {
							cell.setCellValue((Date) object);
						} else if (object instanceof Long) {
							cell.setCellValue((Long) object);
						} else if (object instanceof Boolean) {
							cell.setCellValue((Boolean) object);
						} else if (object instanceof Double) {
							cell.setCellValue((Double) object);

						} else if (object instanceof Integer) {
							cell.setCellValue((Integer) object);

						} else if (object instanceof Timestamp) {
							SimpleDateFormat datetemp1 = new SimpleDateFormat(
									"MM-dd-yyyy HH:mm:ss.SSSSSS");
							String cellValue = datetemp1.format(object);
							cell.setCellValue(cellValue);
						}
						/** 1003881 Start for Date filter issue **/

						//Anjana
						SimpleDateFormat datetemp = new SimpleDateFormat("dd-MMM-yy");												
						CreationHelper creationHelper = workbook.getCreationHelper();
						HSSFCellUtil.setCellStyleProperty((HSSFCell) cell, workbook, CellUtil.DATA_FORMAT,
						HSSFDataFormat.getBuiltinFormat(("dd-MMM-yy")));
						//Anjana
						
						 if((!object.equals(null))  && strSortKey.equalsIgnoreCase("dateOfJoining")) {	
							 Date newdate = datetemp.parse((String) object);	
							if (changeColor) {
								styleOfColouredRow.setDataFormat(creationHelper.createDataFormat().getFormat(("dd-MMM-yy")));	
								cell.setCellStyle(styleOfColouredRow);
								cell.setCellValue(HSSFDateUtil.getExcelDate(newdate));
							} else {
								styleRow.setDataFormat(creationHelper.createDataFormat().getFormat(("dd-MMM-yy")));	
								cell.setCellStyle(styleRow);
								cell.setCellValue(HSSFDateUtil.getExcelDate(newdate));
							}
						}
						 if((!object.equals(null))  && strSortKey.equalsIgnoreCase("allocationStartDate")) {
							 Date newdate = datetemp.parse((String) object);	
								if (changeColor) {
									styleOfColouredRow.setDataFormat(creationHelper.createDataFormat().getFormat(("dd-MMM-yy")));	
									cell.setCellStyle(styleOfColouredRow);
									cell.setCellValue(HSSFDateUtil.getExcelDate(newdate));
								} else {
									styleRow.setDataFormat(creationHelper.createDataFormat().getFormat(("dd-MMM-yy")));	
									cell.setCellStyle(styleRow);
									cell.setCellValue(HSSFDateUtil.getExcelDate(newdate));
								}
						}
						 if((!object.equals(null))  && strSortKey.equalsIgnoreCase("lastupdateTimeStamp")) {
							 Date newdate = datetemp.parse((String) object);	
								if (changeColor) {
									// logic to change color
									styleOfColouredRow.setDataFormat(creationHelper.createDataFormat().getFormat(("dd-MMM-yy")));	
									cell.setCellStyle(styleOfColouredRow);
									cell.setCellValue(HSSFDateUtil.getExcelDate(newdate));
								} else {
									styleRow.setDataFormat(creationHelper.createDataFormat().getFormat(("dd-MMM-yy")));	
									cell.setCellStyle(styleRow);
									cell.setCellValue(HSSFDateUtil.getExcelDate(newdate));
								}						    
						}
						 if((!object.equals(null)) && strSortKey.equalsIgnoreCase("transferDate")) {
							 Date newdate = datetemp.parse((String) object);	
								if (changeColor) {
									// logic to change color
									styleOfColouredRow.setDataFormat(creationHelper.createDataFormat().getFormat(("dd-MMM-yy")));	
									cell.setCellStyle(styleOfColouredRow);
									cell.setCellValue(HSSFDateUtil.getExcelDate(newdate));
								} else {
									styleRow.setDataFormat(creationHelper.createDataFormat().getFormat(("dd-MMM-yy")));	
									cell.setCellStyle(styleRow);
									cell.setCellValue(HSSFDateUtil.getExcelDate(newdate));
								}
						}

						/** End 1003881 **/

					}

				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.debug(e.getMessage());
		}
		logger.debug("End buildExcelDocument in RMReportExcelView Class  ");
	}

}
