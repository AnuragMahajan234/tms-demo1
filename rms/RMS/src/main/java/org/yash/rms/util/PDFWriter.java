package org.yash.rms.util;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.servlet.view.AbstractView;
import org.yash.rms.dto.RequestRequisitionSkillDTO;
import org.yash.rms.service.ResourceService;

import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;


public class PDFWriter<E> extends AbstractView {
	
	 private static String FILE = "C:/temp/RRF.pdf"; //System.getProperty("user.home")+"\\temp\\RRF.pdf";
     
     private static final Logger logger = LoggerFactory.getLogger(PDFWriter.class);
     
    @Autowired
 	@Qualifier("ResourceService")
 	ResourceService resourceService;
          
	@Override
	protected void renderMergedOutputModel(Map<String, Object> model,HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		
		File tempDir = new File("C:/temp");

		// if the directory does not exist, create it
		if (!tempDir.exists()) {
		    	tempDir.mkdirs();
		}		
		logger.info("PDF File Path: "+FILE);
		
		List<RequestRequisitionSkillDTO> dataList = (List<RequestRequisitionSkillDTO>) model.get("dataList");
		Map<String, String> dataHeader = new LinkedHashMap<String, String>();
		for(RequestRequisitionSkillDTO str:dataList){
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy");
			dataHeader.put("Requesting Practice : ",str.getRequestRequisition().getProjectBU());
			dataHeader.put("Line of Business : ", str.getRequestRequisition().getCustomer().getCustomerName());
			dataHeader.put("Resource initiated date (MM/DD/YYYY) : ", str.getRequestRequisition().getDate());
			dataHeader.put("Hiring Practice : ", str.getRequestRequisition().getHiringBGBU());
			
			/*Calendar c = Calendar.getInstance();
			c.setTime(str.getRequestRequisition().getDate());
			c.add(Calendar.DATE, Integer.parseInt(str.getTimeFrame()));*/
			
			
			dataHeader.put("Resource required date (MM/DD/YYYY) : ",str.getRequestRequisition().getResourceRequiredDate());
			/*if(str.getRequestRequisition().getResource().getCurrentBuId().getEmployeeId() != null){
				dataHeader.put("Resource Approved by : ", str.getRequestRequisition().getResource().getCurrentBuId().getEmployeeId().getEmployeeName());
			} else{*/
				dataHeader.put("Resource Approved by : ", "Arun Kumar Mishra");
			//}
			
			dataHeader.put("No. of positions to be hired : ", str.getNoOfResources().toString());
			dataHeader.put("Req ID (by Recruiter) : ", "");
			dataHeader.put("CTC offer range : ", "As Per Market Rate");
			if(str.getExperience().equalsIgnoreCase("Others")){
				dataHeader.put("Years of Experience : ", str.getRequestRequisition().getExpOtherDetails()); //not for old UI
			}else{
				dataHeader.put("Years of Experience : ", str.getExperience());
			}
			
			dataHeader.put("Designation : ", str.getDesignation().getDesignationName());
			dataHeader.put("Work Location : ", str.getLocation().getLocation());
			dataHeader.put("Request raised by –  ", str.getRequestRequisition().getRequestor().getEmployeeName()); //logged in user, can change to requestor
			
			String requirementArea = str.getRequirementArea();
			if(requirementArea!=null) {
			if(requirementArea.equalsIgnoreCase(Constants.NON_SAP)) {
				dataHeader.put("Requirement Area : ", Constants.NON_SAP_STRING);
			}else if(requirementArea.equalsIgnoreCase(Constants.SAP)) {
				dataHeader.put("Requirement Area : ", Constants.SAP);
			}
			}else {
				dataHeader.put("Requirement Area : ", Constants.NON_SAP_STRING);
			}
		}
		
		Map<String, String> data = new LinkedHashMap<String, String>();
		for (RequestRequisitionSkillDTO str : dataList) {
			
			data.put("primary skill", str.getPrimarySkills());
			
			if(null!=str.getDesirableSkills()){
				data.put("Desirable Skills", str.getDesirableSkills());
			}else{
				data.put("Desirable Skills", " ");
			}
			if(null!=str.getCareerGrowthPlan()){
				data.put("Career Growth Plan", str.getCareerGrowthPlan());
			}else{
				data.put("Career Growth Plan", " ");
			}
			if(null!=str.getTargetCompanies()){
				data.put("Target companies", str.getTargetCompanies());
			}else{
				data.put("Target companies"," ");
			}
			
			data.put("Responsibilities", str.getResponsibilities());
			
			if(str.getSkillsToEvaluate()!=null && !str.getSkillsToEvaluate().isEmpty())	{
				data.put("Key scanners", str.getSkillsToEvaluate());
			} else{
				data.put("Key scanners", str.getKeyScanners());
			}
			if(str.getKeyInterviewersOne()!=null ){
				data.put("Key Interviewers1", str.getKeyInterviewersOne());	
			}else{
				data.put("Key Interviewers1", "");
			}
			
			if(str.getKeyInterviewersTwo()!=null){
				data.put("Key Interviewers2", str.getKeyInterviewersTwo());
			}else{
				data.put("Key Interviewers2", "");
			}
			if (str.getAdditionalComments() != null) {
				data.put("Additional comments", str.getComments());
			} else {
				if ( str.getRequestRequisition().getComments()!= null) {
					data.put("Additional comments",  str.getRequestRequisition().getComments());
				} else {
					data.put("Additional comments", " ");
				}
			}
			if(str.getRequestRequisition()!=null) {
				if(null != str.getRequestRequisition().getLastupdatedTimestamp()){
					System.out.println(str.getRequestRequisition().getLastupdatedTimestamp().toString());
					data.put("Last Updated", str.getRequestRequisition().getLastupdatedTimestamp().toString());
				}else if(str.getRequestRequisition().getCreationTimestamp()!=null){
					System.out.println(str.getRequestRequisition().getCreationTimestamp().toString());
					data.put("Last Updated", str.getRequestRequisition().getCreationTimestamp().toString());
				} else {
					data.put("Last Updated", new Date().toString());
				}	
			}
			
			String projectShiftType = "";
			if(str.getRequestRequisition().getShiftType().getShiftTimings()!=null && !str.getRequestRequisition().getShiftType().getShiftTimings().isEmpty()) {
				projectShiftType = str.getRequestRequisition().getShiftType().getShiftTimings();
			}
			
			if(str.getRequestRequisition()!=null) {
				if(!projectShiftType.equalsIgnoreCase("general") ) {
					if(str.getRequestRequisition().getProjectShiftOtherDetails()!=null) {
						data.put("Shift type / Shift Timing Details", projectShiftType + "/" +   str.getRequestRequisition().getProjectShiftOtherDetails());
					}
				} else {
					if(str.getRequestRequisition().getProjectShiftOtherDetails() != null && !str.getRequestRequisition().getProjectShiftOtherDetails().isEmpty()) {
						data.put("Shift type / Shift Timing Details", projectShiftType + "/ " +  str.getRequestRequisition().getProjectShiftOtherDetails());
					} else {
						data.put("Shift type / Shift Timing Details", projectShiftType + "/ " +  "Not Available");
					}
					
				}
			}
			
		}
   	 
   	 Map<String, java.util.List<String>> skillData = new LinkedHashMap<String, java.util.List<String>>();
   	 
   	    java.util.List<String> primary = new ArrayList<String>();
   	 	primary.add(data.get("primary skill"));
   	 	
   	 	java.util.List<String> desirable = new ArrayList<String>();
   	    desirable.add(data.get("Desirable Skills"));
		 	
		java.util.List<String> careerGrowth = new ArrayList<String>();
		careerGrowth.add(data.get("Career Growth Plan"));
		
		java.util.List<String> targetCompanies = new ArrayList<String>();
		targetCompanies.add(data.get("Target companies"));
		
		java.util.List<String> responsibilities = new ArrayList<String>();
		responsibilities.add(data.get("Responsibilities"));
		
		java.util.List<String> keyScanners = new ArrayList<String>();
		keyScanners.add(data.get("Key scanners"));
		
		java.util.List<String> keyInterviewers1 = new ArrayList<String>();
		keyInterviewers1.add(data.get("Key Interviewers1"));
		
		java.util.List<String> KeyInterviewers2 = new ArrayList<String>();
		KeyInterviewers2.add(data.get("Key Interviewers2"));
		
		java.util.List<String> additionalComments = new ArrayList<String>();
		additionalComments.add(data.get("Additional comments"));
		
		java.util.List<String> currentdate = new ArrayList<String>();
		currentdate.add(data.get("Last Updated"));
		
		java.util.List<String> shiftTimings = new ArrayList<String>();
		shiftTimings.add(data.get("Shift type / Shift Timing Details"));
		
	 	skillData.put("Primary skills",primary);
	 	skillData.put("Desirable Skills", desirable);
	 	skillData.put("Career Growth Plan", careerGrowth);
	 	skillData.put("Target companies", targetCompanies);
	 	
	 	skillData.put("Responsibilities", responsibilities);
	 	skillData.put("Key scanners", keyScanners);
	 	skillData.put("Key Interviewers1", keyInterviewers1);
	 	skillData.put("Key Interviewers2", KeyInterviewers2);
	 	skillData.put("Additional comments", additionalComments);
	 	skillData.put("Last Updated", currentdate);
	 	skillData.put("Shift type / Shift Timing Details", shiftTimings);
	 	
	 	Map<String, String> requestID = new LinkedHashMap<String, String>();
	 	requestID.put("RMG RRF ID : ", dataList.get(0).getRequirementId());
   	 
       try {
    	   response.setContentType("application/pdf");
           response.setHeader("Content-Disposition", "attachment; filename= " + "RRF_"+ dataList.get(0).getRequirementId() +".pdf");
       	    HeaderFooterPageEvent event = new HeaderFooterPageEvent(PDFWriterUtiliy.getHeaderTable("RECRUITMENT REQUISITION FORM"));
            
            Document document = new Document(PageSize.A4, 20, 20, 60 + event.getTableHeight(), 20);
            
            PdfWriter.getInstance(document, response.getOutputStream()).setPageEvent(event);
            
          /*  PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(FILE));
            writer.setPageEvent(event);*/
           
            document.open();
            
          //  BaseFont base = BaseFont.createFont("c:/windows/fonts/arial.ttf", BaseFont.WINANSI, false);
            
            Font font = new Font(Font.FontFamily.TIMES_ROMAN, 10,Font.BOLD);
            PdfPTable table3 = PDFWriterUtiliy.addTable( 1, font , requestID);
        	table3.setSpacingBefore(40);
        	
        	document.add(table3);
            
        	
            PdfPTable table = PDFWriterUtiliy.addTable( 2, font , dataHeader);
			table.addCell(" ");
            	table.setSpacingBefore(5);
            
            document.add(table);
           
            PdfPTable  table2 = PDFWriterUtiliy.addTableWithList(1, font, skillData);
            table2.setSpacingBefore(5);
            table2.setSpacingAfter(5);
            
            document.add(table2);
            document.close();
           
           
        } catch (Exception e) {
            	logger.error("PDF File Exception Occurred  "+e.getMessage());
                e.printStackTrace();
        }
    }

	

}
