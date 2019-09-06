package org.yash.rms.excel;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.apache.poi.ss.util.CellUtil;
import org.json.JSONObject;
import org.springframework.web.servlet.view.document.AbstractExcelView;
import org.yash.rms.dto.InfogramActiveResourceDTO;
import org.yash.rms.dto.InfogramInactiveResourceDTO;
import org.yash.rms.util.DateUtil;

public class InfogramInActiveResourceListExcelView extends AbstractExcelView{

	@Override
	protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, 
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		logger.debug("----------InfogramActiveResourceListExcelView buildExcelDocument :: [START]-----------");
		List<InfogramInactiveResourceDTO> inActiveResourceList = (List<InfogramInactiveResourceDTO>) model.get("inActiveResourceList");
		
		Date todaysDate = DateUtil.getTodaysDate();
		Sheet sheet = workbook.createSheet("INACTIVE_RESOURCE" + DateUtil.getDateInDD_MMM_yyyyFormat(todaysDate));
		response.setHeader("Content-Disposition","attachment; filename=INFORGRAM_INACTIVE_RESOURCE_REPORT_"+ DateUtil.getDateInDD_MMM_yyyyFormat(todaysDate)+".xls");
		sheet.setDefaultColumnWidth(28);

		HSSFCellStyle styleHeader = ExcelUtility.getCellStyleForHeaders(workbook);
		HSSFCellStyle styleRow = ExcelUtility.getHSSFCellStyleForDataCell(workbook);
		
		HSSFCellStyle styleOfColouredRow = ExcelUtility.getHSSFCellStyleForColouredDataCell(workbook);
		Map<Integer, String> sortKey = new HashMap<Integer, String>();
		Cell cell = null;
		Row row = null;

		try {

			if (!inActiveResourceList.isEmpty()) {

				int rownum = 0;
				int j = 1;
				for (InfogramInactiveResourceDTO inActiveResource : inActiveResourceList) {

					row = sheet.createRow(rownum++);
					int cellnum = 0;
					JSONObject jsonObject = new JSONObject(
							InfogramInactiveResourceDTO.toJson(inActiveResource));

					jsonObject.put("S.No", j++);

					if (sortKey.isEmpty()) {
						for (Object objKey : jsonObject.keySet()) {
							String key = (String) objKey;

							if (key.equalsIgnoreCase("S.No")) {
								sortKey.put(0, key);
							} else if (key.equalsIgnoreCase("employeeId")) {
								sortKey.put(1, key);
							} else if (key.equalsIgnoreCase("employeeName")) {
								sortKey.put(2, key);
							} else if (key.equalsIgnoreCase("resignedDate")) {
								sortKey.put(3, key);
							} else if (key.equalsIgnoreCase("releasedDate")) {
								sortKey.put(4, key);
							} else if (key.equalsIgnoreCase("processStatus")) {
								sortKey.put(5, key);
							}
						}

						String newFieldName = "";
						for (String fieldName : sortKey.values()) {
							
						cell = row.createCell(cellnum++);
						newFieldName = WordUtils.capitalize(fieldName);
						String output = newFieldName.replaceAll("(\\p{Ll})(\\p{Lu})","$1 $2");
						cell.setCellValue(output);
						cell.setCellStyle(styleHeader);

						/*for (int k = 0; k <= jsonObject.length(); k++) {
								sheet.autoSizeColumn(k);
							}*/
						}
						cellnum = 0;
						row = sheet.createRow(rownum++);
					}

					for (String strSortKey : sortKey.values()) {	
						Object object = jsonObject.get(strSortKey);

						cell = row.createCell(cellnum++);
						
						cell.setCellStyle(styleRow);
						
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
						
						SimpleDateFormat datetemp = new SimpleDateFormat("dd-MMM-yy");												
						CreationHelper creationHelper = workbook.getCreationHelper();
						HSSFCellUtil.setCellStyleProperty((HSSFCell) cell, workbook, CellUtil.DATA_FORMAT,
						HSSFDataFormat.getBuiltinFormat(("dd-MMM-yy")));
						
						if((!object.equals(null))  && strSortKey.equalsIgnoreCase("DOJ")) {	
							Date newdate = datetemp.parse((String) object);	
							styleRow.setDataFormat(creationHelper.createDataFormat().getFormat(("dd-MMM-yy")));	
							cell.setCellStyle(styleRow);
							cell.setCellValue(HSSFDateUtil.getExcelDate(newdate));
						}
						
					}

				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.debug(e.getMessage());
		}
	
		logger.debug("----------InfogramActiveResourceListExcelView buildExcelDocument :: [END]-----------");
		
	
		
	}

}
