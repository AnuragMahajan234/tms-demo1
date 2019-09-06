package org.yash.rms.util;

import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.view.document.AbstractExcelView;
import org.yash.rms.dao.RequestRequisitionSkillDao;
import org.yash.rms.domain.RequestRequisitionSkill;

/**
 * Utility used to generate Excel file
 * 
 * @author 1001847
 * @param <E>
 * 
 */
public class GenerateExcelUtility<E> extends AbstractExcelView{
	
	@Autowired
	private RequestRequisitionSkillDao requestRequisitionSkillDao;

	/**
	 * Method to get fields of domain
	 * @param clazz
	 * @return
	 * @throws Exception
	 */
	private List<String> setupFieldsForClass(Class<?> clazz) throws Exception {
		List<String> fieldNames = new ArrayList<String>();
		Field[] fields = clazz.getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			if(!boolean.class.equals(fields[i].getType())){
				fieldNames.add(fields[i].getName());
			}
		}
		return fieldNames;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void buildExcelDocument(Map<String, Object> model,
			HSSFWorkbook workbook, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		String fileName = (String) model.get("fileName");
		List<E> dataList = (List<E>) model.get("dataList");
		List<String> columnNameList = (List<String>) model.get("colNames");
		
		
		setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		response.setHeader("Content-Disposition", "attachment; filename=\""+fileName+".xls\"");
		// New sheet having name same as name of domain
		Sheet sheet = workbook.createSheet("REPORT_SHEET");
		
		List<String> fieldNames = new ArrayList<String>();
			fieldNames = setupFieldsForClass(dataList.get(0).getClass());
		
		// Create a row and put some cells in it. Rows are 0 based.
		int rowCount = 0;
		int columnCount = 0;
		// Create cell style
		Font font= workbook.createFont();
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		font.setFontName(HSSFFont.FONT_ARIAL);
		font.setFontHeightInPoints((short) 10);
		CellStyle styleForColHeading = workbook.createCellStyle();
		styleForColHeading.setFont(font);
		styleForColHeading.setAlignment(CellStyle.ALIGN_CENTER);
			
		//rowHeader.
		// New row
		Row row = sheet.createRow(rowCount++);
		for (String fieldName : columnNameList) {
			Cell cel = row.createCell(columnCount++);
			cel.setCellValue(fieldName);
			cel.setCellStyle(styleForColHeading);
		}
			//Class<? extends Object> classz = dataList.get(0).getClass();
			for (int i=0;i<dataList.size();i++) {
				row = sheet.createRow(rowCount++);
				columnCount = 0;
				
					RequestRequisitionSkill request2= (RequestRequisitionSkill) dataList.get(i);
					Cell cel = row.createCell(columnCount);
					this.setCellValue(cel,new SimpleDateFormat("MM-dd-yyyy")
					.format(request2.getRequestRequisition().getDate()));
					columnCount++;
					 cel = row.createCell(columnCount);
					 this.setCellValue(cel, request2.getRequestRequisition().getProjectBU());
					 columnCount++;
					 cel = row.createCell(columnCount);
					 this.setCellValue(cel, request2.getRequestRequisition().getResource().getEmployeeName());
					 columnCount++;
					 cel = row.createCell(columnCount);
					 this.setCellValue(cel, request2.getRequestRequisition().getProject().getProjectName());
					 columnCount++;
					 List<RequestRequisitionSkill> skillRequests=requestRequisitionSkillDao.findRequestRequisitionSkillsByRequestRequisitionId(request2.getRequestRequisition().getId());
					 for(int j=0;j<skillRequests.size();j++){
						 if(j>0){
							 columnCount=4;
							 row = sheet.createRow(rowCount++);
						 }
						 RequestRequisitionSkill skillRequest=skillRequests.get(j);
						 cel = row.createCell(columnCount);
						 this.setCellValue(cel, skillRequest.getSkill().getSkill());
						 columnCount++;
						 cel = row.createCell(columnCount);
						 this.setCellValue(cel,skillRequest.getDesignation().getDesignationName());
						 columnCount++;
						 cel = row.createCell(columnCount);
						 this.setCellValue(cel,skillRequest.getExperience());
						 columnCount++;
						 cel = row.createCell(columnCount);
					
						 this.setCellValue(cel,skillRequest.getAllocationType().getAllocationType());
						 columnCount++;
						 cel = row.createCell(columnCount);
						 String type="";
						 if(skillRequest.getType()==1){
							 type="New";
						 }else {
							 type="Replacement";
						}
						 this.setCellValue(cel,type);
						 columnCount++;
						 cel = row.createCell(columnCount);
						 this.setCellValue(cel,skillRequest.getTimeFrame());
						 columnCount++;
						 cel = row.createCell(columnCount);
						 this.setCellValue(cel,skillRequest.getNoOfResources());
						 columnCount++;
						 cel = row.createCell(columnCount);
						 this.setCellValue(cel,skillRequest.getFulfilled());
						 columnCount++;
						 cel = row.createCell(columnCount);
						 this.setCellValue(cel,skillRequest.getRemaining());
						 columnCount++;
						 cel = row.createCell(columnCount);
						 this.setCellValue(cel,skillRequest.getComments());
						 
					 }
					
					 columnCount++;
					 cel = row.createCell(columnCount);
					 this.setCellValue(cel,request2.getComments());
										
			}
		}
	
	private void setCellValue (Cell cel, Object value) {
		
		if (value instanceof String) {
			cel.setCellValue((String) value);
		}
		else if (value instanceof Boolean) {
			cel.setCellValue((Boolean) value);
		}
		else if (value instanceof Double) {
			cel.setCellValue((Double) value);
		}
		else if (value instanceof Integer) {
			cel.setCellValue((Integer) value);
		}
		else if (value instanceof Long) {
			cel.setCellValue((Long) value);
		}
		else if (value instanceof Character) {
			cel.setCellValue((Character) value);
		}
		else if (value instanceof Float) {
			cel.setCellValue((Float) value);
		}
		else if (value instanceof Date) {
			cel.setCellValue((Date) value);
		}
		else if (value instanceof Timestamp) {
			SimpleDateFormat datetemp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS");
			String cellValue = datetemp.format(value);
			cel.setCellValue(cellValue);
		}	
	}
}
