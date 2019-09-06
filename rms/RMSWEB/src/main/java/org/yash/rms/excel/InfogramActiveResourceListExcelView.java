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
import org.yash.rms.domain.InfogramActiveResource;
import org.yash.rms.dto.InfogramActiveResourceDTO;
import org.yash.rms.util.Constants;
import org.yash.rms.util.DateUtil;
import org.yash.rms.util.InfogramProcessStatusConstants;

public class InfogramActiveResourceListExcelView extends AbstractExcelView{
	
	
	
	
	@Override
	protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, 
		HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.debug("----------InfogramActiveResourceListExcelView buildExcelDocument :: [START]-----------");
		List<InfogramActiveResourceDTO> activeResourceList = (List<InfogramActiveResourceDTO>) model.get("activeResourceList");
		
		Date todaysDate = DateUtil.getTodaysDate();
		Sheet sheet = workbook.createSheet("ACTIVE_RESOURCE" + DateUtil.getDateInDD_MMM_yyyyFormat(todaysDate));
		response.setHeader("Content-Disposition","attachment; filename=INFORGRAM_ACTIVE_RESOURCE_REPORT_"+ DateUtil.getDateInDD_MMM_yyyyFormat(todaysDate)+".xls");
		sheet.setDefaultColumnWidth(28);

		HSSFCellStyle greyColor = ExcelUtility.getHeaderColorForCell(workbook, ExcelUtility.GREY);
		//setHeaderColor(workbook,styleHeader);
		HSSFCellStyle orangeColor = ExcelUtility.getHeaderColorForCell(workbook, ExcelUtility.ORANGE);
		
		HSSFCellStyle redColor = ExcelUtility.getColorForCell(workbook, ExcelUtility.RED,"");
		//setRedColor(workbook,redColor);
		
		HSSFCellStyle greenColor = ExcelUtility.getColorForCell(workbook, ExcelUtility.GREEN,"");
		//setGreenColor(workbook,greenColor);
		
		
		HSSFCellStyle greenBgBodrColor = ExcelUtility.getColorForCell(workbook, ExcelUtility.GREEN,ExcelUtility.GREEN);
		
		HSSFCellStyle greenRedBgBodrColor = ExcelUtility.getColorForCell(workbook, ExcelUtility.GREEN,ExcelUtility.RED);
		
		HSSFCellStyle redBgBodrColor = ExcelUtility.getColorForCell(workbook, ExcelUtility.RED,ExcelUtility.RED);
		
		HSSFCellStyle redGreenBgBodrColor = ExcelUtility.getColorForCell(workbook, ExcelUtility.RED,ExcelUtility.GREEN);
		
		HSSFCellStyle styleRow = ExcelUtility.getHSSFCellStyleForDataCell(workbook);
		
		//HSSFCellStyle styleOfColouredRow = ExcelUtility.getHSSFCellStyleForColouredDataCell(workbook);
		Map<Integer, String> sortKey = new HashMap<Integer, String>();
		Cell cell = null;
		Row row = null;

		try {

			if (!activeResourceList.isEmpty()) {

				int rownum = 0;
				int j = 1;
				String projectBGBU ="";
				Object prjBGBUObj=null;
				String prjBGBU[]=null;
				String pStatus="";
				for (InfogramActiveResourceDTO activeResource : activeResourceList) {

					row = sheet.createRow(rownum++);
					int cellnum = 0;
					JSONObject jsonObject = new JSONObject(
							InfogramActiveResourceDTO.toJson(activeResource));

					jsonObject.put("S.No", j++);

					if (sortKey.isEmpty()) {
						for (Object objKey : jsonObject.keySet()) {
							String key = (String) objKey;

							if (key.equalsIgnoreCase("S.No")) {
								sortKey.put(0, key);
							} else if (key.equalsIgnoreCase("employeeId")) {
								sortKey.put(1, key);
							} else if (key.equalsIgnoreCase("name")) {
								sortKey.put(2, key);
							} else if (key.equalsIgnoreCase("status")) {
								sortKey.put(3, key);
							}
							else if (key.equalsIgnoreCase("processStatus")) {
								sortKey.put(4, key);
							} else if (key.equalsIgnoreCase("dateOfJoining")) {
								sortKey.put(5, key);
							} else if (key.equalsIgnoreCase("emailId")) {
								sortKey.put(6, key);
							} else if (key.equalsIgnoreCase("designation")) {
								sortKey.put(7, key);
							} else if (key.equalsIgnoreCase("rmsDesignation")) {
								sortKey.put(8, key);
							} else if (key.equalsIgnoreCase("baseLocation")) {
								sortKey.put(9, key);
							} else if (key.equalsIgnoreCase("locationInRMS")) {
								sortKey.put(10, key);
							} else if (key.equalsIgnoreCase("currentLocation")) {
								sortKey.put(11, key);
							} else if (key.equalsIgnoreCase("currentLocationInRMS")) {
								sortKey.put(12, key);
							} else if (key.equalsIgnoreCase("businessGroup")) {
								sortKey.put(13, key);
							} else if (key.equalsIgnoreCase("businessUnit")) {
								sortKey.put(14, key);
							}  else if (key.equalsIgnoreCase("rmsBg")) {
								sortKey.put(15, key);
							} else if (key.equalsIgnoreCase("rmsBu")) {
								sortKey.put(16, key);
							} 
							else if (key.equalsIgnoreCase("irmName")) {
								sortKey.put(17, key);
							} else if (key.equalsIgnoreCase("irmInRMS")) {
								sortKey.put(18, key);
							} else if (key.equalsIgnoreCase("srmName")) {
								sortKey.put(19, key);
							} else if (key.equalsIgnoreCase("srmInRMS")) {
								sortKey.put(20, key);
							} else if (key.equalsIgnoreCase("creationTimestamp")) {
								sortKey.put(21, key);
							} else if (key.equalsIgnoreCase("modifiedBy")) {
								sortKey.put(22, key);
							}
							else if (key.equalsIgnoreCase("projectBGBU")) {
								sortKey.put(23, key);
							} 
							else if (key.equalsIgnoreCase("irmEmployeeId")) {
								sortKey.put(24, key);
							} 
							else if (key.equalsIgnoreCase("srmEmployeeId")) {
								sortKey.put(25, key);
							} 
							else if (key.equalsIgnoreCase("rmsIrmEmployeeId")) {
								sortKey.put(26, key);
							} 
							
							else if (key.equalsIgnoreCase("rmsSrmEmployeeId")) {
								sortKey.put(27, key);
							} 
							else if (key.equalsIgnoreCase("infoResourceBGBU")) {
								sortKey.put(28, key);
							} 
							else if (key.equalsIgnoreCase("rmsResourceBGBU")) {
								sortKey.put(29, key);
							} 
							else if (key.equalsIgnoreCase("infoIRMBGBU")) {
								sortKey.put(30, key);
							} 
							
							else if (key.equalsIgnoreCase("infoSRMBGBU")) {
								sortKey.put(31, key);
							} 
							else if (key.equalsIgnoreCase("rmsIRMBGBU")) {
								sortKey.put(32, key);
							} 
							else if (key.equalsIgnoreCase("rmsSRMBGBU")) {
								sortKey.put(33, key);
							} 
							
							
							
						}

						String newFieldName = "";
						for (String fieldName : sortKey.values()) {
							
						if(cellnum>22)
							continue;
						cell = row.createCell(cellnum++);
						newFieldName = WordUtils.capitalize(fieldName);
						String output = newFieldName.replaceAll("(\\p{Ll})(\\p{Lu})","$1 $2");
						cell.setCellValue(output);
						if(cellnum==9||cellnum==11||cellnum==13||cellnum==16||cellnum==17||cellnum==19||cellnum==21)
							cell.setCellStyle(orangeColor);
						else
							cell.setCellStyle(greyColor);

						/*for (int k = 0; k <= jsonObject.length(); k++) {
								sheet.autoSizeColumn(k);
							}*/
						}
						cellnum = 0;
						row = sheet.createRow(rownum++);
					}

					for (String strSortKey : sortKey.values()) {	
						if(cellnum>22)
							continue;
						Object object = jsonObject.get(strSortKey);

						cell = row.createCell(cellnum++);
						cell.setCellStyle(styleRow);
						/*if(strSortKey.equalsIgnoreCase("status"))
						{
							if(object.equals(null))
								object="New Joinee";
							else
								object="Existing";
						}*/
						
						
						if (object.equals(null)) {
							cell.setCellValue("  ");
						} else if (object instanceof String) {
							pStatus=(String)(jsonObject.get("processStatus"));
							if(pStatus.equalsIgnoreCase(InfogramProcessStatusConstants.PENDING.toString())||pStatus.equalsIgnoreCase(InfogramProcessStatusConstants.FAILURE.toString())){
							if(strSortKey.equalsIgnoreCase("rmsDesignation"))
							{
								
								if(((String)(jsonObject.get(strSortKey))).replaceAll("\\s+","").trim().equalsIgnoreCase(((String)(jsonObject.get("designation"))).replaceAll("\\s+","").trim()))
									cell.setCellStyle(greenColor);
								else
									cell.setCellStyle(redColor);
							}
							if(strSortKey.equalsIgnoreCase("locationInRMS"))
							{
								if(((String)(jsonObject.get(strSortKey))).equalsIgnoreCase((String)(jsonObject.get("baseLocation"))))
									cell.setCellStyle(greenColor);
								else
									cell.setCellStyle(redColor);
							}
							if(strSortKey.equalsIgnoreCase("currentLocationInRMS"))
							{
								if(((String)(jsonObject.get(strSortKey))).equalsIgnoreCase((String)(jsonObject.get("currentLocation"))))
									cell.setCellStyle(greenColor);
								else
									cell.setCellStyle(redColor);
							}
							projectBGBU="";
							prjBGBUObj = jsonObject.get("projectBGBU");
							if(!prjBGBUObj.equals(null) && !prjBGBUObj.equals("NA"))
								projectBGBU=(String)jsonObject.get("projectBGBU");
							
							if(projectBGBU.length()>0){
								prjBGBU=projectBGBU.split("-");
							}
							
							if(strSortKey.equalsIgnoreCase("businessGroup"))  
							{
								if(null!=projectBGBU && projectBGBU.length()>0){
									
								if(prjBGBU[0].equalsIgnoreCase((String) object))
									cell.setCellStyle(greenColor);
								else
									cell.setCellStyle(redColor);
								}
							}
							if(strSortKey.equalsIgnoreCase("businessUnit"))  
							{
								if(null!=projectBGBU && projectBGBU.length()>0){
								if(prjBGBU[1].equalsIgnoreCase((String) object))
									cell.setCellStyle(greenColor);
								else
									cell.setCellStyle(redColor);
								}
							}
							if(strSortKey.equalsIgnoreCase("rmsBg"))  
							{
								if(null!=projectBGBU && projectBGBU.length()>0){
									
								if(prjBGBU[0].trim().equalsIgnoreCase((String) object))
									cell.setCellStyle(greenColor);
								else
									cell.setCellStyle(redColor);
								}
							}
							if(strSortKey.equalsIgnoreCase("rmsBu"))  
							{
								if(null!=projectBGBU && projectBGBU.length()>0){
									
								if(prjBGBU[1].trim().equalsIgnoreCase((String) object))
									cell.setCellStyle(greenColor);
								else
									cell.setCellStyle(redColor);
								}
							}
								
							
							if(strSortKey.equalsIgnoreCase("irmName"))  
							{
							 if(((String)jsonObject.get("status")).equalsIgnoreCase("EXISTING") && projectBGBU.length()>0){
								Object irmBgBuInfo=null;
								String infoIRMBGBU="";
								irmBgBuInfo = jsonObject.get("infoIRMBGBU");
								if(!irmBgBuInfo.equals(null))
									infoIRMBGBU=(String)jsonObject.get("infoIRMBGBU");
								
								Object infoIrm=null;
								infoIrm=jsonObject.get("irmEmployeeId");
								String infoIrmId="";
								if(!infoIrm.equals(null))
								{
									infoIrmId=(String)jsonObject.get("irmEmployeeId");
								}
								String rmsIrmId="";
								Object rmsIrm=null;
								rmsIrm=jsonObject.get("rmsIrmEmployeeId");
								if(!rmsIrm.equals(null))
									rmsIrmId=(String)jsonObject.get("rmsIrmEmployeeId");
								
								
								if(infoIRMBGBU.equalsIgnoreCase(projectBGBU)){
									
									if(infoIrmId.equalsIgnoreCase(rmsIrmId))
										cell.setCellStyle(greenColor);
									else
										cell.setCellStyle(greenRedBgBodrColor);
									
								}
								else{
									
									if(infoIrmId.equalsIgnoreCase(rmsIrmId))
										cell.setCellStyle(redColor);
									else
										cell.setCellStyle(redBgBodrColor);
								
								}
							 }
							}
							if(strSortKey.equalsIgnoreCase("srmName"))  
							{
								if(((String)jsonObject.get("status")).equalsIgnoreCase("EXISTING") && projectBGBU.length()>0){
								Object srmBgBuInfo=null;
								String infoSRMBGBU="";
								srmBgBuInfo = jsonObject.get("infoSRMBGBU");
								if(!srmBgBuInfo.equals(null))
									infoSRMBGBU=(String)jsonObject.get("infoSRMBGBU");
								
								Object infoSrm=null;
								infoSrm=jsonObject.get("srmEmployeeId");
								String infoSrmId="";
								if(!infoSrm.equals(null))
								{
									infoSrmId=(String)jsonObject.get("srmEmployeeId");
								}
								String rmsSrmId="";
								Object rmsSrm=null;
								rmsSrm=jsonObject.get("rmsSrmEmployeeId");
								if(!rmsSrm.equals(null))
									rmsSrmId=(String)jsonObject.get("rmsSrmEmployeeId");
								
								
								if(infoSRMBGBU.equalsIgnoreCase(projectBGBU)){
									
									if(infoSrmId.equalsIgnoreCase(rmsSrmId))
										cell.setCellStyle(greenColor);
									else
										cell.setCellStyle(greenRedBgBodrColor);
									
								}
								else{
									
									if(infoSrmId.equalsIgnoreCase(rmsSrmId))
										cell.setCellStyle(redColor);
									else
										cell.setCellStyle(redBgBodrColor);
								
								}
							 }
							}
							if(strSortKey.equalsIgnoreCase("irmInRMS")) 
							{
								if(projectBGBU.length()>0){
								Object irm=null;
								String rmsIRMBGBU="";
								irm = jsonObject.get("rmsIRMBGBU");
								
								if(!irm.equals(null))
									rmsIRMBGBU=(String)jsonObject.get("rmsIRMBGBU");
								
								if(rmsIRMBGBU.equalsIgnoreCase(projectBGBU))
									cell.setCellStyle(greenColor);
								else
									cell.setCellStyle(redColor);
								}
								
							}
							if(strSortKey.equalsIgnoreCase("srmInRMS"))  
							{
								if(projectBGBU.length()>0){
								Object srmRms=null;
								String rmsSRMBGBU="";
								srmRms = jsonObject.get("rmsSRMBGBU");
								if(!srmRms.equals(null))
									rmsSRMBGBU=(String)jsonObject.get("rmsSRMBGBU");
								if(rmsSRMBGBU.equalsIgnoreCase(projectBGBU))
									cell.setCellStyle(greenColor);
								else
									cell.setCellStyle(redColor);
							 }
							}
							}
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
