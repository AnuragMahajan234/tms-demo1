package org.yash.rms.dao.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.yash.rms.dao.RequestDao;
import org.yash.rms.domain.AllocationType;
import org.yash.rms.domain.Customer;
import org.yash.rms.domain.Designation;
import org.yash.rms.domain.OrgHierarchy;
import org.yash.rms.domain.Project;
import org.yash.rms.domain.RequestRequisition;
import org.yash.rms.domain.RequestRequisitionSkill;
import org.yash.rms.domain.Resource;
import org.yash.rms.domain.ShiftTypes;
import org.yash.rms.domain.Skills;
import org.yash.rms.dto.EditProfileDTO;
import org.yash.rms.service.CustomerService;
import org.yash.rms.service.OrgHierarchyService;
import org.yash.rms.service.ResourceService;
import org.yash.rms.service.ShiftTypesService;
import org.yash.rms.util.Constants;
import org.yash.rms.util.HeaderFooterPageEvent;
import org.yash.rms.util.PDFWriterUtiliy;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

@Repository("RequestDao")
public class RequestDaoImpl implements RequestDao {
	private static final Logger logger = LoggerFactory
			.getLogger(RequestDaoImpl.class);
	@PersistenceContext(name = "mysql", type = PersistenceContextType.TRANSACTION)
	private EntityManager entityManager;
	
	
	public EntityManager getEntityManager() {
		return entityManager;
	}
	
	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	@Autowired
	@Qualifier("ResourceService")
	ResourceService resourceService;
	
	@Autowired
	ShiftTypesService shiftTypesService;
	
	@Autowired
	@Qualifier("OrgHierarchyService")
	OrgHierarchyService orgHierarchyService;
	
	@Autowired
	CustomerService customerService; 
	@Transactional(propagation=Propagation.SUPPORTS , isolation=Isolation.READ_COMMITTED)
	/*public Boolean saveRequest(RequestRequisition savedRequest, Resource currentResource, String currentBU,
			String projID, List<Competency> skillList,
			List<Integer> resourceNo, List<Designation> designation,
			List<String> experience, List<AllocationType> bill,*/
	
	public Boolean saveRequest(Resource currentResource, String currentBU,
			String projID, List<Integer> skillList,
			List<Integer> resourceNo, List<Integer> designationList,
			List<String> experience, List<Integer> allocationTypeList,
			List<Integer> type, List<String> time, Date date, String comments,
			List<String> primarySkills, List<String> desirableSkills,
			List<String> responsibilities, List<String> targetCompanies,
			List<String> keyInterviewersOneText,
			List<String> keyInterviewersTwoText,
			List<String> additionalComments, List<String> careerGrowthPlan,
			List<String> keyScanners, Integer projectId, String sentMailTo,String finalMailNotifyTo,
			String skillRequestId, String requestId, Integer customerName, List<String> reqIdList) {
		
		Session currentSession = (Session) getEntityManager().getDelegate();

		RequestRequisition requestRequisition = new RequestRequisition();
			requestRequisition.setResource(currentResource);
			requestRequisition.setProjectBU(currentBU);
			requestRequisition.setComments(comments);
		
		Project project = new Project();
			project.setId(projectId);	
		
			requestRequisition.setProject(project);
			requestRequisition.setDate(date);
			requestRequisition.setSentMailTo(sentMailTo);
			requestRequisition.setNotifyMailTo(finalMailNotifyTo);
		
		Customer cust = new Customer();
		cust.setId(customerName);
		requestRequisition.setCustomer(cust);
		// 1003958 STARTS[]
		//currentSession.load(Request.class, id)
		if (null!=requestId && Integer.parseInt(requestId)>0) {
			
			requestRequisition.setId(Integer.parseInt(requestId));
			
			currentSession.merge(requestRequisition);
		} else {
			
			currentSession.save(requestRequisition);
		}
		// 1003958 ENDS[]
		
		List<RequestRequisitionSkill> skillRequestList = new ArrayList<RequestRequisitionSkill>();
		
		RequestRequisitionSkill skillRequest = null;

		for (int i = 0; i < resourceNo.size(); i++) {
			
			Integer remaining = resourceNo.get(i);
			Integer fulfilled = 0;
			
			skillRequest = new RequestRequisitionSkill();
			
			int id= Integer.parseInt(skillRequestId);
			if (id > 0) {
				
				skillRequest.setId(id);
			}
			Skills skill = new Skills();
				skill.setId(skillList.get(i));
				skillRequest.setSkill(skill);
			
			skillRequest.setNoOfResources(resourceNo.get(i));
			
			AllocationType allocationType = new AllocationType();
				allocationType.setId(allocationTypeList.get(i));
			
			skillRequest.setAllocationType(allocationType);
			
			Designation designation = new Designation();
				designation.setId(designationList.get(i));
			
			skillRequest.setDesignation(designation);
			skillRequest.setExperience(experience.get(i));
			skillRequest.setTimeFrame(time.get(i));
			skillRequest.setType(type.get(i));
			skillRequest.setFulfilled(fulfilled);
			skillRequest.setRemaining(remaining.toString());
			skillRequest.setPrimarySkills(primarySkills.get(i));
			skillRequest.setDesirableSkills(desirableSkills.get(i));
			skillRequest.setRequirementId(reqIdList.get(i));
			skillRequest.setResponsibilities((responsibilities.get(i)));
			skillRequest.setTargetCompanies((targetCompanies.get(i)));
			skillRequest.setKeyInterviewersOne((keyInterviewersOneText.get(i)));
			skillRequest.setKeyInterviewersTwo((keyInterviewersTwoText.get(i)));
			skillRequest.setAdditionalComments((additionalComments.get(i)));
			skillRequest.setCareerGrowthPlan((careerGrowthPlan.get(i)));
			skillRequest.setKeyScanners((keyScanners.get(i)));
			
			skillRequestList.add(skillRequest);
			
			skillRequest.setRequestRequisition(requestRequisition);
		}
		//request.setRequestRequisitionSkill(skillRequestList);
		
		//RequestRequisition requestsaved = (RequestRequisition) currentSession.merge(requestRequisition);
		for(RequestRequisitionSkill requestRequisitionSkill : skillRequestList) {
			
			currentSession.merge(requestRequisitionSkill);	
		}
		
		boolean result = true;
		
		
		return result;
	}

	public List getLastAddedRequestID() {
		Session session = (Session) getEntityManager().getDelegate();
		Criteria criteria = session.createCriteria(RequestRequisition.class)
				.setProjection(Projections.max("id"));
		Integer maxID = (Integer) criteria.uniqueResult();
		System.out.println("MaxID: " + maxID);

		Criteria cr = session.createCriteria(RequestRequisition.class);
		cr.add(Restrictions.eq("id", maxID));

		/*
		 * org.hibernate.Query lastAddedrequest = session.createSQLQuery(
		 * "SELECT * FROM `request` WHERE id = :maxID" );
		 * lastAddedrequest.setParameter("maxID", maxID);
		 */
		System.err.println("$$$$$" + cr.list());
		return cr.list();
	}

	public int getUpdatedId() {
		Session session = (Session) getEntityManager().getDelegate();
		Criteria criteria = session.createCriteria(RequestRequisitionSkill.class)
				.setProjection(Projections.max("id"));
		Integer maxID = (Integer) criteria.uniqueResult();
		return maxID;		
	}
	
	public File createAttachment(Map<String, Object> model, File file) {
		
		RequestRequisitionSkill requestRequisitionSkill = (RequestRequisitionSkill) model.get("dataList");
		EditProfileDTO requestor = (EditProfileDTO) model.get("requestorObject");
		Map<String, String> dataHeader = new LinkedHashMap<String, String>();
		    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
		   
			//dataHeader.put("Requesting Practice : ", requestRequisitionSkill.getRequestRequisition().getResource().getCurrentBuId().getParentId().getName() + "-" + requestRequisitionSkill.getRequestRequisition().getResource().getCurrentBuId().getName());
		    dataHeader.put("Requesting Practice : ", requestRequisitionSkill.getRequestRequisition().getProjectBU());
			dataHeader.put("Hiring Practice : ",  requestRequisitionSkill.getRequestRequisition().getHiringBGBU());
			dataHeader.put("Line of Business : ",  customerService.findCustomer(requestRequisitionSkill.getRequestRequisition().getCustomer().getId()).getCustomerName());
			dataHeader.put("Resource initiated date : ", dateFormat.format(requestRequisitionSkill.getRequestRequisition().getDate()));
			
                                            /* Calendar c = Calendar.getInstance();
                                             c.setTime(requestRequisitionSkill.getRequestRequisition().getDate());
                                             c.add(Calendar.DATE, Integer.parseInt(requestRequisitionSkill.getTimeFrame()));*/
                                             
            
			dataHeader.put("Resource required date : ", dateFormat.format(requestRequisitionSkill.getRequestRequisition().getResourceRequiredDate()));
			String checkbgBu = requestRequisitionSkill.getRequestRequisition().getProjectBU();
			String bgname = null;
			OrgHierarchy orgHierarchy = null;
			if(checkbgBu!=null) {
				bgname=checkbgBu.split("-")[0];
			
				orgHierarchy= orgHierarchyService.getOrgHierarchyByName(bgname);
			}
			
			if(orgHierarchy != null)
			{
				logger.info("inside if case of createAttachment  resource approved by");
				logger.info("orgHierarchy.getName()"+orgHierarchy.getName());
				if(orgHierarchy.getEmployeeId()!=null)
				{
				logger.info("orgHierarchy.getEmployeeId().getEmployeeName()"+orgHierarchy.getEmployeeId().getEmployeeName());
				//logger.info("resource.getCurrentBuId().getParentId().getEmployeeId()"+resource.getCurrentBuId().getParentId().getEmployeeId());
				dataHeader.put("Resource Approved by : ", orgHierarchy.getEmployeeId().getEmployeeName());
				}else
				{
					dataHeader.put("Resource Approved by : ", "Arun Kumar Mishra");
				}
			}
			else
			{
				logger.info("inside else case of createAttachment resource approved by");
				dataHeader.put("Resource Approved by : ", "Arun Kumar Mishra");
			}
			dataHeader.put("No. of positions to be hired : ", requestRequisitionSkill.getNoOfResources().toString());
			dataHeader.put("Req ID (by Recruiter) : ", " ");
			dataHeader.put("CTC offer range : ", "As Per Market Price");
			
			if(requestRequisitionSkill.getExperience().equalsIgnoreCase("others")){
				dataHeader.put("Years of Experience : ", requestRequisitionSkill.getRequestRequisition().getExpOtherDetails());
			}else{
				dataHeader.put("Years of Experience : ", requestRequisitionSkill.getExperience());
			}
			
			
			
		
			dataHeader.put("Designation & Grade : ", requestRequisitionSkill.getDesignation().getDesignationName());
			dataHeader.put("Work Location : ", requestRequisitionSkill.getLocation().getLocation());
			
			if(requestor!=null)
			{
		
			dataHeader.put("Request raised by –  ", requestor.getFirstName()+" "+requestor.getLastName());
			}else
			{
				Resource resourceReq=requestRequisitionSkill.getRequestRequisition().getRequestor();
				if(resourceReq!=null)
				{
				dataHeader.put("Request raised by –  ", resourceReq.getFirstName()+" "+resourceReq.getLastName());
				}else
				{
					dataHeader.put("Request raised by –  ", "NA");
				}
			}
			String requirementArea = requestRequisitionSkill.getRequirementArea();
			if(requirementArea!=null) {
			if(requirementArea.equalsIgnoreCase(Constants.NON_SAP)) {
				dataHeader.put("Requirement Area : ", Constants.NON_SAP_STRING);
			}else if(requirementArea.equalsIgnoreCase(Constants.SAP)) {
				dataHeader.put("Requirement Area : ", Constants.SAP);
			}
			}else {
				dataHeader.put("Requirement Area : ", Constants.NON_SAP_STRING);
			}
		
		Map<String, String> data = new LinkedHashMap<String, String>();
		if(requestRequisitionSkill.getKeyInterviewersOne()!= null){
			String keyinterviewer1String = requestRequisitionSkill.getKeyInterviewersOne();
			
			String[] keyInterviewersinArray =keyinterviewer1String.split(","); 
			
			ArrayList<Integer> interviewersRound1 = new ArrayList<Integer>();
			HashSet<String> hashSetRound1 = new HashSet<String>();
			
			boolean isKeyInterviewerAvail = false;
			
			for (int i =0; i<keyInterviewersinArray.length ; i++) {
				if(keyInterviewersinArray[i] != null && keyInterviewersinArray[i].trim().length()>0 && !(keyInterviewersinArray[i].contains("@"))){ // added the check for existing RRF where email address already contain instead of user id
					interviewersRound1.add(Integer.parseInt(keyInterviewersinArray[i].replaceAll("\\[", "").replaceAll("\\]", "").trim()));
					isKeyInterviewerAvail = true;
				}else{
					hashSetRound1.add(keyInterviewersinArray[i]);
				}
			}
			if(isKeyInterviewerAvail){
				List<String> round1Emails = resourceService.findEmailById(interviewersRound1);
				hashSetRound1.addAll(round1Emails);
			}
			String round1 = hashSetRound1.toString();
			data.put("Key Interviewers1", round1);
		}else{
			data.put("Key Interviewers1", "");
		}
		
		if( requestRequisitionSkill.getKeyInterviewersTwo()!=null){
			String keyinterviewer2String = requestRequisitionSkill.getKeyInterviewersTwo();
			String[] keyInterviewers2inArray =keyinterviewer2String.split(","); 
			ArrayList<Integer> interviewersRound2 = new ArrayList<Integer>();
			HashSet<String> hashSetRound2 = new HashSet<String>();
			boolean isKeyInterviewerAvail = false;
			for (int i =0; i<keyInterviewers2inArray.length ; i++) {
				if(keyInterviewers2inArray[i] != null && keyInterviewers2inArray[i].trim().length()>0 && !(keyInterviewers2inArray[i].contains("@"))){
				  interviewersRound2.add(Integer.parseInt(keyInterviewers2inArray[i].replaceAll("\\[", "").replaceAll("\\]", "").trim()));
				  isKeyInterviewerAvail = true;
				}else{
					hashSetRound2.add(keyInterviewers2inArray[i]);
				}
			}
			if(isKeyInterviewerAvail){
				List<String> round2Emails = resourceService.findEmailById(interviewersRound2);
				hashSetRound2.addAll(round2Emails);
			}
			String round2 = hashSetRound2.toString();
			data.put("Key Interviewers2", round2);	
		}else{
			data.put("Key Interviewers2", "");
		}
		
			data.put("primary skill", requestRequisitionSkill.getPrimarySkills());
			
			if(null!=requestRequisitionSkill.getDesirableSkills()){
				data.put("Desirable Skills", requestRequisitionSkill.getDesirableSkills());
			}else{
				data.put("Desirable Skills", " ");
			}
			if(null!=requestRequisitionSkill.getCareerGrowthPlan()){
				data.put("Career Growth Plan", requestRequisitionSkill.getCareerGrowthPlan());
			}else{
				data.put("Career Growth Plan", " ");
			}
			if(null!=requestRequisitionSkill.getTargetCompanies()){
				data.put("Target companies", requestRequisitionSkill.getTargetCompanies());
			}else{
				data.put("Target companies"," ");
			}
			
			data.put("Responsibilities", requestRequisitionSkill.getResponsibilities());
			if( requestRequisitionSkill.getSkillsToEvaluate() ==null || requestRequisitionSkill.getSkillsToEvaluate().isEmpty()){
				data.put("Key scanners", "");
			}else{
				data.put("Key scanners", requestRequisitionSkill.getSkillsToEvaluate());	
			}
			
			if(requestRequisitionSkill.getRequestRequisition()!=null) {
				
				if(null != requestRequisitionSkill.getRequestRequisition().getComments()){
					data.put("Additional comments", requestRequisitionSkill.getRequestRequisition().getComments());
				}else{
					data.put("Additional comments", "");
				}
				
				if(null != requestRequisitionSkill.getRequestRequisition().getLastupdatedTimestamp()){
					System.out.println("lastUpdated" + requestRequisitionSkill.getRequestRequisition().getLastupdatedTimestamp().toString());
					data.put("Last Updated", requestRequisitionSkill.getRequestRequisition().getLastupdatedTimestamp().toString());
				}else if(requestRequisitionSkill.getRequestRequisition().getCreationTimestamp()!=null){
					System.out.println("created time" + requestRequisitionSkill.getRequestRequisition().getCreationTimestamp().toString());
					data.put("Last Updated", requestRequisitionSkill.getRequestRequisition().getCreationTimestamp().toString());
				} else {
					data.put("Last Updated", new Date().toString());
				}
				String projectShiftType = "";
				if(requestRequisitionSkill.getRequestRequisition().getShiftTypeId()!=null ) {
				ShiftTypes shiftType=shiftTypesService.getShiftTypeById(requestRequisitionSkill.getRequestRequisition().getShiftTypeId().getId());
					if(shiftType!=null) {
						projectShiftType = shiftType.getShiftTimings();
					} 
				}
				if(requestRequisitionSkill.getRequestRequisition()!=null) {
					if(!projectShiftType.equalsIgnoreCase("general") && projectShiftType!= "" ) {
						if(requestRequisitionSkill.getRequestRequisition().getProjectShiftOtherDetails()!=null) {
							data.put("Shift type / Shift Timing Details", projectShiftType + "/" +   requestRequisitionSkill.getRequestRequisition().getProjectShiftOtherDetails());
						}
					} else {
						if(requestRequisitionSkill.getRequestRequisition().getProjectShiftOtherDetails()!=null && !requestRequisitionSkill.getRequestRequisition().getProjectShiftOtherDetails().isEmpty()) {
							data.put("Shift type / Shift Timing Details", projectShiftType + "/" +  requestRequisitionSkill.getRequestRequisition().getProjectShiftOtherDetails());
						} else {
							data.put("Shift type / Shift Timing Details", projectShiftType + "/" +  "NA");
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
		
		java.util.List<String> lastUpdatedTimestamp = new ArrayList<String>();
		lastUpdatedTimestamp.add(data.get("Last Updated"));
		
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
	 	skillData.put("Last Updated", lastUpdatedTimestamp);  
	 	skillData.put("Shift type / Shift Timing Details", shiftTimings);
	 	
	 	Map<String, String> requestID = new LinkedHashMap<String, String>();
	 	requestID.put("RMG RRF ID : ", requestRequisitionSkill.getRequirementId());
	 	try {
	 	HeaderFooterPageEvent event = new HeaderFooterPageEvent(PDFWriterUtiliy.getHeaderTable("REQUEST REQUISITION FORM"));
        
        Document document = new Document(PageSize.A4, 20, 20, 60 + event.getTableHeight(), 20);
       
			PdfWriter.getInstance(document, new FileOutputStream(
			        file)).setPageEvent(event);
		
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
        
    	table.setSpacingBefore(40);
    
        
        document.close();
        } catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return file;
		
	}
	
	public File createTempFile(String prefix, String suffix){
	     String tempDir = System.getProperty("java.io.tmpdir");
	     String fileName = (prefix != null ? prefix : "" ) + (suffix != null ? suffix : "" ) ;
	     return new File(tempDir, fileName);
	}
	
	 public RequestRequisition getReqById(Integer id) {
       
         RequestRequisition request = new RequestRequisition();
         Session session = (Session) getEntityManager().getDelegate();
         try {
                         Criteria criteria = session.createCriteria(RequestRequisition.class);
                         criteria.add(Restrictions.eq("id", id));
                         request = (RequestRequisition) criteria.uniqueResult();
         } catch (HibernateException e) {
                         e.printStackTrace();
                         throw e;
         }
         return request;
	 }
	
	 
	 private String getFullNameByResource(Resource resource)
	 	{
		 	String fullName ="";
		 	
		 if(resource!=null)
		 {
			 if(resource.getFirstName()!=null && resource.getFirstName().isEmpty())
			 {
				fullName= fullName+resource.getFirstName()+" ";
			 }
			 
			 if(resource.getMiddleName()!=null && resource.getMiddleName().isEmpty())
			 {
				fullName= fullName+resource.getMiddleName()+" ";
			 }
			 
			 if(resource.getLastName()!=null && resource.getLastName().isEmpty())
			 {
				fullName= fullName+resource.getLastName();
			 }
			 
			 
		 }
		 
	 	return fullName;
	 }
}
