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
import org.yash.rms.report.dto.ResourceSkillReport;
import org.yash.rms.util.DateUtil;

public class ResourceSkillReportExcelView extends AbstractExcelView  {
	private static final Logger logger = LoggerFactory
			.getLogger(ResourceSkillReportExcelView.class);
	
	@SuppressWarnings({ "unchecked" })
	@Override
	protected void buildExcelDocument(Map<String, Object> model,
			HSSFWorkbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		logger.debug("Started buildExcelDocument in ResourceSkillReportExcelView Class  ");
		Set<ResourceSkillReport> list = (Set<ResourceSkillReport>) model.get("resourceSkillReportList");
		
		Date todaysDate = DateUtil.getTodaysDate();
		Sheet sheet = workbook.createSheet("RESOURCE_SKILL_SHEET_" + DateUtil.getDateInDD_MMM_yyyyFormat(todaysDate));
		response.setHeader("Content-Disposition","attachment; filename=Resource_Skill_Report_"+ DateUtil.getDateInDD_MMM_yyyyFormat(todaysDate)+".xls");
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
				for (ResourceSkillReport resourceSkillReport : list) {

					row = sheet.createRow(rownum++);
					int cellnum = 0;
					JSONObject jsonObject = new JSONObject(
							ResourceSkillReport.toJson(resourceSkillReport));

					jsonObject.put("S.No", j++);

					if (sortKey.isEmpty()) {

						/*Row row1 = sheet.createRow(0);

						for (int i = 0; i <= 31; i++) {
							sheet.addMergedRegion(new CellRangeAddress(0, 0, 0,
									i));

						}
*/
						/*cell = row1.createCell(0);
						cell.setCellStyle(styleHeader);
						cell.setCellValue("                                                                               Resource Skill Report 																																																																																																																																																															        ");

						*/

						for (Object objKey : jsonObject.keySet()) {
							String key = (String) objKey;

							if (key.equalsIgnoreCase("S.No")) {
								sortKey.put(0, key);
							} else if (key.equalsIgnoreCase("YashEmpID")) {
								sortKey.put(1, key);
							} else if (key.equalsIgnoreCase("EmployeeName")) {
								sortKey.put(2, key);
							} else if (key.equalsIgnoreCase("Competency")) {
								sortKey.put(3, key);
							} else if (key.equalsIgnoreCase("PrimarySkill")) {
								sortKey.put(4, key);
							} else if (key.equalsIgnoreCase("SecondarySkill")) {
								sortKey.put(5, key);
							} else if (key.equalsIgnoreCase("Grade")) {
								sortKey.put(6, key);
							} else if (key.equalsIgnoreCase("CurrentLocation")) {
								sortKey.put(7, key);
							} else if (key.equalsIgnoreCase("PrimaryProject")) {
								sortKey.put(8, key);
							} else if (key.equalsIgnoreCase("AllocationType")) {
								sortKey.put(9, key);
							} else if (key.equalsIgnoreCase("EmailId")) {
								sortKey.put(10, key);
							} else if (key.equalsIgnoreCase("Designation")) {
								sortKey.put(11, key);
							} else if (key.equalsIgnoreCase("ParentBu")) {
								sortKey.put(12, key);
							} else if (key.equalsIgnoreCase("CurrentBu")) {
								sortKey.put(13, key);
							}
						}

						String newFieldName = "";
						for (String fieldName : sortKey.values()) {
							
						cell = row.createCell(cellnum++);
						newFieldName = WordUtils.capitalize(fieldName);
						String output = newFieldName.replaceAll("(\\p{Ll})(\\p{Lu})","$1 $2");
						if ((output.equalsIgnoreCase("Primary Skill"))) {
							output = "Primary Skill[Exp In Month - Rating]";
						}
						if ((output.equalsIgnoreCase("Secondary Skill"))) {
							output = "Secondary Skill[Exp In Month - Rating]";
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
					
					for(int i = 0 ; i < jsonObject.keySet().size(); i++){
						if(i==0)
							sheet.setColumnWidth(i, 2000);
						if(i==1)
							sheet.setColumnWidth(i, 3000);
						if(i==2)
							sheet.setColumnWidth(i, 4000);
						if(i==3)
							sheet.setColumnWidth(i, 4000);
						if(i==4||i==5)
							sheet.setColumnWidth(i, 10000);
						if(i==6)
							sheet.setColumnWidth(i, 2000);	
						if(i==10)
							sheet.setColumnWidth(i, 9000);	
						if(i==7||i==8||i==9||i==11 )
							sheet.setColumnWidth(i, 6000);	
						if(i==12||i==13)
							sheet.setColumnWidth(i, 3000);
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
					}

				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.debug(e.getMessage());
		}
		logger.debug("End buildExcelDocument in ResourceSkillReportExcelView Class  ");
	}


}
