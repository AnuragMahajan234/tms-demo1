package org.yash.rms.helper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.yash.rms.domain.Resource;
import org.yash.rms.domain.RequestRequisitionSkill;
import org.yash.rms.domain.SkillsMapping;
import org.yash.rms.domain.UserProfile;
import org.yash.rms.dto.RequestRequisitionResourceFormDTO;
import org.yash.rms.dto.ResourceStatusDTO;
import org.yash.rms.util.Constants;
import org.yash.rms.util.UserUtil;
import org.yash.rms.wrapper.RequestRequisitionWrapper;
import org.yash.rms.wrapper.ResourceInterviewWrapper;
import org.yash.rms.dao.ProjectAllocationDao;
import org.yash.rms.domain.CATicket;
import org.yash.rms.domain.RequestRequisitionResource;
import org.yash.rms.form.CATicketForm;
import org.yash.rms.service.CATicketService;
import org.yash.rms.service.ProjectService;
import org.yash.rms.service.ResourceService;
import org.yash.rms.util.RMSUtil;


/** This class is used to implement the methods which are being used by the controller.
 * @author deepesh.gupta
 */
@Component
public class ResourceHelper {
	
	@Autowired
	UserUtil userUtil;
	
	@Autowired
	ResourceService resourceService;
	
	@Autowired
	ProjectAllocationDao projectAllocationDao;
	
	@Autowired
	CATicketService caTicketService;
	
	@Autowired
	ProjectService projectService;
	
	String subject = "YASH RMS Tool: Welcome Aboard";
	Boolean newResource = true;
	String subjectRequest = "RRF Creation"; 
	String subjectRequestnew = "RRF updated"; 
	String subjectRRFForward = "RRF Forwarded";
	
	
	public void setEmailContent(Map<String, Object> model, String approvalURL, String denyURL) {
		Date currentDate = new Date();		
		model.put(Constants.APPROVAL_URL, approvalURL);
		model.put(Constants.DENY_URL, denyURL);		
		model.put(Constants.CURRENT_DATE, currentDate);		
		model.put(Constants.USER_NAME, userUtil.getCurrentUserName());
		model.put(Constants.USER_EMP_ID, userUtil.getLoggedInResource().getYashEmpId());
		model.put(Constants.FILE_NAME, Constants.USER_PROFILE_FTL);
		model.put(Constants.SUBJECT, Constants.USER_PROFILE_SUBJECT);
	}
	
	public void setEmailContentNewResource(Map<String, Object> model, String sendMailTo, String userName, HttpServletRequest httpServletRequest, String otherDetails[], Character userProfile){
		model.put(Constants.FILE_NAME, Constants.NEW_RESOURCE_FTL);
		model.put(Constants.RESOURCE_URL, userUtil.getURL(httpServletRequest, null, userProfile));
		model.put("userName", userName);
		model.put("toMail", sendMailTo);
		model.put("subject", subject);
		model.put("rm1Name", otherDetails[0]);
		model.put("rm2Name", otherDetails[1]);
		model.put("rm1Email", otherDetails[2]);
		model.put("rm2Email", otherDetails[3]);
		model.put("currentBU", otherDetails[4]);
		model.put("parentBU", otherDetails[5]);
		model.put(Constants.USER_PROFILE, userProfile);
		model.put("newResource", newResource);
	}
	
	public void setEmailContentForResourceInterviewDetails(Map<String, Object> model, String[] sendMailTo, String[] ccMails,String loggedInResourceName, String emailtext, String resourceName, ResourceInterviewWrapper resourceInterviewWrapper, RequestRequisitionWrapper requisitionWrapper, String contactPersonNames, String sendMailFrom){
		model.put(Constants.FILE_NAME, Constants.RESOURCE_INTERVIEW_TEMPLATE_HTML);
		String currentDate=org.yash.rms.util.DateUtil.getStringDate(new Date());
		String currentTime=org.yash.rms.util.DateUtil.getStringTime(new Date());		
		model.put("currentDate", currentDate);
		model.put("currentTime", currentTime);
		model.put("userName", loggedInResourceName);
		model.put("toMail", sendMailTo);
		model.put("ccMails", ccMails);
		model.put("sendMailFrom", sendMailFrom);
		model.put("subject", "RMS - RRF : Technical Discussion Scheduled - Details");
		model.put("intervieweeName", resourceName);
		model.put("yashInternalFlag", false);
		model.put("clientFlag", false);
		
		if(resourceInterviewWrapper.getDiscussionType().equalsIgnoreCase("yashInternal")) {
			model.put("yashInternalFlag", true);
		}else {
			model.put("clientFlag", true);
		}
		
		if(resourceInterviewWrapper.getVenue()!=null) {
			model.put("venue", resourceInterviewWrapper.getVenue());
		} else {
			model.put("venue", Constants.NOT_AVAILABLE);
		}
		
		if(requisitionWrapper.getRequirementID()!=null) {
			model.put("requirementId", requisitionWrapper.getRequirementID());
		} else {
			model.put("requirementId", Constants.NOT_AVAILABLE);
		}
		
		if(resourceInterviewWrapper.getAmPOCName()!=null && !resourceInterviewWrapper.getAmPOCName().isEmpty() ) {
			model.put("amPOCName", resourceInterviewWrapper.getAmPOCName());
		} else {
			model.put("amPOCName", Constants.NOT_AVAILABLE);
		}
		if(requisitionWrapper.getCustomerName()!=null) {
			model.put("customerName", requisitionWrapper.getCustomerName());
		} else {
			model.put("customerName", Constants.NOT_AVAILABLE);
		}
		if(resourceInterviewWrapper.getCustPOCName()!=null && !resourceInterviewWrapper.getCustPOCName().isEmpty()) {
			model.put("customerPOCName", resourceInterviewWrapper.getCustPOCName());
		} else {
			model.put("customerPOCName", Constants.NOT_AVAILABLE);
		}
		if(resourceInterviewWrapper.getCustomerPOCContact()!=null && !resourceInterviewWrapper.getCustomerPOCContact().isEmpty()) {
			model.put("customerPOCContact", resourceInterviewWrapper.getCustomerPOCContact());
		} else {
			model.put("customerPOCContact", Constants.NOT_AVAILABLE);
		}
		if(resourceInterviewWrapper.getGatenumber()!=null) {
			model.put("gatePass", resourceInterviewWrapper.getGatenumber());
		} else {
			model.put("gatePass", Constants.NOT_AVAILABLE);
		}
		if(resourceInterviewWrapper.getInterviewDate()!=null) {
			model.put("interviewDate", resourceInterviewWrapper.getInterviewDate());
		} else {
			model.put("interviewDate", Constants.NOT_AVAILABLE);
		}
		if(requisitionWrapper.getRequestorsBGBU()!=null) {
			model.put("requestorsBGBU", requisitionWrapper.getRequestorsBGBU());
		} else {
			model.put("requestorsBGBU", Constants.NOT_AVAILABLE);
		}
		if(requisitionWrapper.getJobLocation()!=null) {
			model.put("location", requisitionWrapper.getJobLocation());
		} else {
			model.put("location", Constants.NOT_AVAILABLE);
		}
		if(requisitionWrapper.getDeliveryPOCName()!=null) {
			model.put("deliveryPOCName", requisitionWrapper.getDeliveryPOCName());
		} else {
			model.put("deliveryPOCName", Constants.NOT_AVAILABLE);
		}
		if(requisitionWrapper.getResourceContactNumber()!=null) {
			model.put("resourceContactNumber", requisitionWrapper.getResourceContactNumber());
		} else {
			model.put("resourceContactNumber", Constants.NOT_AVAILABLE);
		}
		if(requisitionWrapper.getResourceEmpId()!=null) {
			model.put("resourceEmpID", requisitionWrapper.getResourceEmpId());
		} else {
			model.put("resourceEmpID", Constants.NOT_AVAILABLE);
		}
		
		if(resourceInterviewWrapper.getAmPOCCont()!=null && !resourceInterviewWrapper.getAmPOCCont().isEmpty()) {
			model.put("amPOCContact", resourceInterviewWrapper.getAmPOCCont());
		} else {
			model.put("amPOCContact", Constants.NOT_AVAILABLE);
		}
		if(requisitionWrapper.getDeliveryPOCContact()!=null) {
			model.put("deliveryPOCContact", requisitionWrapper.getDeliveryPOCContact());
		} else {
			model.put("deliveryPOCContact", Constants.NOT_AVAILABLE);
		}
		if(resourceInterviewWrapper.getInterviewTime()!=null) {
			model.put("interviewTime", resourceInterviewWrapper.getInterviewTime());
		} else {
			model.put("interviewTime", Constants.NOT_AVAILABLE);
		}
		
		if(resourceInterviewWrapper.getInterviewMode()!=null) {
			model.put("interviewMode", resourceInterviewWrapper.getInterviewMode());
		} else {
			model.put("interviewMode", Constants.NOT_AVAILABLE);
		}
		if(requisitionWrapper.getSkillCategory()!=null) {
			model.put("skillCategory", requisitionWrapper.getSkillCategory());
		} else {
			model.put("skillCategory", Constants.NOT_AVAILABLE);
		}
		if(resourceInterviewWrapper.getJobdesc()!=null) {
			model.put("jobDesc", resourceInterviewWrapper.getJobdesc());
		} else {
			model.put("jobDesc", Constants.NOT_AVAILABLE);
		}
		if(contactPersonNames!=null && !contactPersonNames.isEmpty()) {
			model.put("contactPersons", contactPersonNames);
		} else {
			model.put("contactPersons", Constants.NOT_AVAILABLE);
		}
		if(resourceInterviewWrapper.getMailText()!=null && !resourceInterviewWrapper.getMailText().isEmpty()) {
			model.put("additionalInfo", resourceInterviewWrapper.getMailText());
		} else {
			model.put("additionalInfo", Constants.NOT_AVAILABLE);
		}
		
	}
	
	public void setEmailContentForRequest(Map<String, Object> model, String[] tomailIDsList, String[] ccmailIDs, String sender, 
			String projectName, String projectBU, String sendMailFrom, List<RequestRequisitionSkill> requestMailList,
			String comments, String amJobCode, String customerName,	String reqId, String groupName,String pocName,
			Integer hold, boolean holdFlag, Integer lost, boolean lostFlag, boolean update, int noOfPos,
			String location, String pocBU, Resource senderResource,String successFactorId, String requirementArea, String rmgPoc, String tecTeamPoc, String mailType, Resource loggedInResource) {
		boolean rrfForwardFlag = false;
		boolean rrfcreateFlag = false;
		model.put(Constants.FILE_NAME, Constants.RESOURCE_REQUEST_HTML);
		model.put("toMail", tomailIDsList); 
		if(!update){
				
			if (mailType != null && mailType.equalsIgnoreCase(Constants.FORWARD_RRF)) {
					rrfForwardFlag = true;
					model.put("subject", subjectRRFForward + " - " + reqId + " - [ " + amJobCode + " ] -No of position- ("
							+ noOfPos + ")");
					if (null != amJobCode && !amJobCode.isEmpty()) {
						model.put("subject", subjectRRFForward + " - " + reqId + " - [ " + amJobCode
								+ " ] -No of position- (" + noOfPos + ")");
					} else {
						model.put("subject", subjectRRFForward + " - " + reqId + " -No of position- (" + noOfPos + ")");
					}
				} else if (null != amJobCode && !amJobCode.isEmpty()) {
					rrfcreateFlag = true;
					model.put("subject",
							subjectRequest + " - " + reqId + " - [ " + amJobCode + " ] -No of position- (" + noOfPos + ")");
				} else {
					rrfcreateFlag = true;
					model.put("subject", subjectRequest + " - " + reqId + " -No of position- (" + noOfPos + ")");
				}
			} else {
				if (holdFlag && lostFlag) {
					if (null != amJobCode && !amJobCode.isEmpty())
						model.put("subject", subjectRequestnew + " - " + reqId + " - [ " + amJobCode + " ] - (Hold-" + hold
								+ " and Lost-" + lost + ")");
					else
						model.put("subject",
								subjectRequestnew + " - " + reqId + " - (Hold-" + hold + " and Lost-" + lost + ")");
				} else if (holdFlag) {
					if (null != amJobCode && !amJobCode.isEmpty())
						model.put("subject",
								subjectRequestnew + " - " + reqId + "  - [ " + amJobCode + " ] - (Hold-" + hold + ")");
					else
						model.put("subject", subjectRequestnew + " - " + reqId + " - (Hold-" + hold + ")");
				} else if (lostFlag) {
					if (null != amJobCode && !amJobCode.isEmpty())
						model.put("subject",
								subjectRequestnew + " - " + reqId + "  - [ " + amJobCode + " ](Lost-" + lost + ")");
					else
						model.put("subject", subjectRequestnew + " - " + reqId + " - (Lost-" + lost + ")");
				} else {
					if (null != amJobCode && !amJobCode.isEmpty())
						model.put("subject", subjectRequestnew + " - " + reqId + " -No of position- (" + noOfPos + ") - [ "
								+ amJobCode + " ]");
					else
						model.put("subject", subjectRequestnew + " - " + reqId + " -No of position- (" + noOfPos + ")");
				}
			}
		
		String currentDate=org.yash.rms.util.DateUtil.getStringDate(new Date());
		String currentTime=org.yash.rms.util.DateUtil.getStringTime(new Date());		
		model.put("currentDate", currentDate);
		model.put("currentTime", currentTime);
	   	model.put("projectName", projectName);
		model.put("sendMailFrom", sendMailFrom); 
		model.put("projectBU", projectBU);
		model.put("clientName", customerName); 
		model.put(Constants.INDENTER, sender);
		model.put("ccMail", ccmailIDs);
		model.put(Constants.COMMENTS, comments);
		model.put("groupName", groupName);
		model.put("pocName", pocName);
		model.put("hold", hold);
		model.put("holdFlag", holdFlag);
		model.put("lost", lost);
		model.put("lostFlag", lostFlag);
		model.put("update", update);
		model.put("isOthers", false);
		model.put("isNotOthers", false);
		model.put("location", location);
		model.put("pocBU", pocBU);
		model.put("amJobCode", amJobCode);
		model.put("successFactorId",successFactorId);
		model.put("rmgPoc",rmgPoc);
		model.put("tecTeamPoc",tecTeamPoc);
		model.put("requirementArea",requirementArea);
		model.put("rrfForwardComment", requestMailList.get(0)!=null ? requestMailList.get(0).getRrfForwardComment() : "" );
		model.put("rrfForwardFlag", rrfForwardFlag);
		model.put("rrfCreateFlag", rrfcreateFlag);
		
		if(requestMailList.get(0)!=null) {
			if(requestMailList.get(0).getRequestRequisition()!=null) {
				if(requestMailList.get(0).getRequestRequisition().getDate()!=null) {
					model.put("dateOfIndent",requestMailList.get(0).getRequestRequisition().getDate() );
				}
			}
		}
		 
		if(loggedInResource!=null){
		 model.put("loggedInResourceName", !loggedInResource.getEmployeeName().isEmpty()? loggedInResource.getEmployeeName() : "");
		}
		else{
			model.put("loggedInResourceName","System");
		}
		
		/*Start - Code to pass sender's bg-bu */
		String senderBu =  senderResource.getCurrentBuId().getParentId().getName() + "-" + senderResource.getCurrentBuId().getName();
		model.put("senderBU", senderBu);
		
		/*End - Code to pass sender's bg-bu */
		if(requestMailList.get(0).getExperience()!=null && requestMailList.get(0).getExperience().equalsIgnoreCase("Others")){
            model.put("isOthers", true);
            model.put("others", requestMailList.get(0).getRequestRequisition().getExpOtherDetails()); //re-coded - Samiksha
	     } else{
	            model.put("isNotOthers", true);
	     }
		model.put(Constants.REQ_MAIL_LIST, requestMailList);		
		//requestMailList trim primary skills to 30 chars  Start
		getTrimPrimarySkills(requestMailList, model);
		// requestMailList trim primary skills to 30 chars     End
	}
	
	public void setEmailContentForRequestForSubmittedToAMTeam(Map<String, Object> model, String[] tomailIDsList, String[] ccmailIDs, String sender, String projectName, 
			String projectBU, String sendMailFrom, List<RequestRequisitionSkill> requestMailList, String comments, String customerName, String reqId, String groupName) {
		model.put(Constants.FILE_NAME, Constants.RESOURCE_STATUS_UPDATION_HTML);
		model.put("toMail", tomailIDsList); 
		model.put("subject", subjectRequest + " - " +reqId);
		model.put("projectName", projectName);
		model.put("projectBU", projectBU);
		model.put("sendMailFrom", sendMailFrom); 
		model.put("clientName", customerName); 
		model.put(Constants.INDENTER, sender);
		model.put("ccMail", ccmailIDs);
		model.put(Constants.COMMENTS, comments);
		model.put("groupName", groupName);
		model.put(Constants.REQ_MAIL_LIST, requestMailList);		
			getTrimPrimarySkills(requestMailList, model);
	}
	
	                                                       
	private void getTrimPrimarySkills(List<RequestRequisitionSkill> requestMailList, Map<String, Object> model) {
		
		/*
				for (RequestRequisitionSkill requestRequisitionSkill : requestMailList) {
			int limit=500;
			  if(requestRequisitionSkill.getPrimarySkills().length()>=limit)
		         {
			String[] spllitedString=requestRequisitionSkill.getPrimarySkills().split(",|\\s");
			int i=0;
			int j=0;
			String newString="";
			while(i<limit && j<spllitedString.length)
			{
				i+=spllitedString[j].length()+1;
				newString+=spllitedString[j];
				j++;
				newString+=" ";
				
			}
			if(i>=limit){
				newString+="...";
				 requestRequisitionSkill.setPrimarySkills(newString);
				 System.out.println("newString       =="+newString);
				 model.put(Constants.REQ_MAIL_LIST, requestMailList); 
				}
		         }
			  else
			  {
			model.put(Constants.REQ_MAIL_LIST, requestMailList); 
			  }

	}
		
	*/
		for (RequestRequisitionSkill requestRequisitionSkill : requestMailList) {
			
			Integer limit = 1000;
			String primarySkill = requestRequisitionSkill.getPrimarySkills();
			
			if(primarySkill.length()<=limit) {
				 model.put("PrimarySkill", primarySkill); 
			} else {
				primarySkill = primarySkill.substring(0, limit);
				primarySkill.concat("...");
				model.put("PrimarySkill", primarySkill); 
			}
		}
	
	
	}

	/*public void setEmailContentForRequest(Map<String, Object> model, String[] tomailIDs, String[] ccmailIDs, String sender, String sendMailFrom, String projectBU, String projectName, String[] skills, List<Integer> resourceNo, String comments){
		model.put(Constants.FILE_NAME, Constants.RESOURCE_REQUEST_FTL);
		model.put("toMail", tomailIDs); 
		model.put("subject", subjectRequest);
		model.put("sendMailFrom", sendMailFrom); 
		model.put(Constants.INDENTER, sender);
		model.put("ccMail", ccmailIDs); 
		model.put("projectBU", projectBU);
		model.put("projectName", projectName);
		//model.put(Constants.REQ_LIST, reqList);
		model.put(Constants.COMMENTS, comments);
		model.put(Constants.REQUESTED_SKILLS, skills);
		model.put(Constants.REQUESTED_RESOURCE, resourceNo); 
	}*/
	
	public static List<SkillsMapping> getSecondarySkillSMapping(List<Object[]>resultList)
	{
		List<SkillsMapping>  SkillsMappingList=new ArrayList<SkillsMapping>();
		if(resultList!=null&&resultList.size()>0){
		for(Object[] skillRatings:resultList){
			SkillsMapping skillsMapping=new SkillsMapping();
			if(skillRatings[0]!=null)
			//skillsMapping.setEmployee_id((Integer)skillRatings[0]);
			if(skillRatings[1]!=null)
			skillsMapping.setSecondarySkill_Id((Integer)skillRatings[1]);
			if(skillRatings[2]!=null)
			skillsMapping.setSkill_Name((String)skillRatings[2]);
			if(skillRatings[3]!=null)
			//skillsMapping.setSkill_Type((String)skillRatings[3]);
				skillsMapping.setSecondaryExperience((Integer)skillRatings[3]);
			if(skillRatings[4]!=null)
				//skillsMapping.setSkill_Type((String)skillRatings[3]);
			if(skillRatings[5]!=null)
				skillsMapping.setRating_Name((String)skillRatings[5]);
						
				if(skillRatings[6]!=null)
					skillsMapping.setSecondarySkillRating_Id((Integer)skillRatings[6]);
			if(skillRatings.length>7){
				if(skillRatings[7]!=null)
					skillsMapping.setSecondarySkillPKId((Integer)skillRatings[7]);
			}
			
			SkillsMappingList.add(skillsMapping);
		  }
			if(SkillsMappingList.size()>0)
				return SkillsMappingList;
		}
		return null;
	}
	
	public static List<SkillsMapping> getPrimarySkillSMapping(List<Object[]>resultList)
	{
		 List<SkillsMapping>  SkillsMappingList=new ArrayList<SkillsMapping>();
		if(resultList!=null&&resultList.size()>0){
		for(Object[] skillRatings:resultList){
			SkillsMapping skillsMapping=new SkillsMapping();
			if(skillRatings[0]!=null)
			//skillsMapping.setEmployee_id((Integer)skillRatings[0]);
			if(skillRatings[1]!=null)
			skillsMapping.setPrimarySkill_Id((Integer)skillRatings[1]);
			if(skillRatings[2]!=null)
			skillsMapping.setSkill_Name((String)skillRatings[2]);
			if(skillRatings[3]!=null)
					skillsMapping.setPrimaryExperience((Integer)skillRatings[3]);
			//skillsMapping.setSkill_Type((String)skillRatings[3]);
			//if(skillRatings[4]!=null)
				//skillsMapping.setSkill_Type((String)skillRatings[3]);
			if(skillRatings[5]!=null)
			skillsMapping.setRating_Name((String)skillRatings[5]);
				if(skillRatings[6]!=null)
			skillsMapping.setPrimarySkillRating_Id((Integer)skillRatings[6]);
			if(skillRatings.length>7){
				if(skillRatings[7]!=null)
			 skillsMapping.setPrimarkSkillPKId((Integer)skillRatings[7]);
			}
			SkillsMappingList.add(skillsMapping);
		  }
			if(SkillsMappingList.size()>0)
				return SkillsMappingList;
		}
		return null;
	}
	
	public static  Resource copyObject(UserProfile userProfile,Resource resource ) {
			resource.setEmailId(userProfile.getEmailId());
			resource.setContactNumber(userProfile.getContactNumberOne());
			resource.setContactNumberTwo(userProfile.getContactNumberTwo());
			resource.setFirstName(userProfile.getFirstName());
			resource.setLastName(userProfile.getLastName());
			resource.setMiddleName(userProfile.getMiddleName());
			resource.setResumeFileName(userProfile.getResumeFileName());
//			byte [] resume = userProfile.getResume();
//			if ((resume != null) && (resume.length > 0)){
//				resource.setResume(resume);
//			} 
			resource.setCustomerIdDetail(userProfile.getCustomerIdDetail());
			return resource;
		}
	
	public static  Resource populateResource(UserProfile userProfile) {
		// TODO Auto-generated method stub
		Resource resource= new Resource();
		resource.setEmailId(userProfile.getEmailId());
		resource.setYashEmpId(userProfile.getYashEmpId());
		resource.setContactNumber(userProfile.getContactNumberOne());
		resource.setContactNumberTwo(userProfile.getContactNumberTwo());
		resource.setFirstName(userProfile.getFirstName());
		resource.setLastName(userProfile.getLastName());
		resource.setMiddleName(userProfile.getMiddleName());
		resource.setResumeFileName(userProfile.getResumeFileName());
		resource.setCustomerIdDetail(userProfile.getCustomerIdDetail());
		
		return resource;
	}

	public void setEmailContentforCATicket(Map<String, Object> model,CATicket caTicket,CATicketForm caTicketForm, String[] assineeIds, String[] ccMails,String subjectFlag,String methodName) {

		if(subjectFlag.equalsIgnoreCase("Ticket_Create")&& methodName.equalsIgnoreCase("create")){
			model.put(Constants.FILE_NAME, Constants.CA_TICKET_CREATE_FTL);
			model.put(Constants.SUBJECT, resourceService.find(caTicket.getAssigneeId().getEmployeeId()).getFirstName()+" - Ticket Ceated and Assigned to you. Ticket No: "+ caTicket.getCaTicketNo()+" ; Reviewer :"+resourceService.find(caTicket.getReviewer().getEmployeeId()).getFirstName()+" "+resourceService.find(caTicket.getReviewer().getEmployeeId()).getLastName());
			model.put(Constants.CATicketMail, caTicket);
			model.put(Constants.EMAIL_ID, assineeIds[0]);
			model.put(Constants.PROJECT_NAME,projectService.findProject(caTicket.getModuleId().getId()).getProjectName());
			model.put(Constants.ASSIGNEE_NAME, resourceService.find(caTicket.getAssigneeId().getEmployeeId()).getFirstName()+" "+(resourceService.find(caTicket.getAssigneeId().getEmployeeId())).getLastName());
			model.put(Constants.REVIEWER_NAME,resourceService.find(caTicket.getReviewer().getEmployeeId()).getFirstName()+" "+(resourceService.find(caTicket.getReviewer().getEmployeeId())).getLastName());
			model.put(Constants.UNIT_NAME,caTicketService.getUnit(caTicket.getUnitId().getId()).getUnitName());
			model.put(Constants.REGION_NAME, caTicketService.getRegion(caTicket.getRegion().getId()).getRegionName());
			model.put(Constants.LANDSCAPE_NAME, caTicketService.getLandscape(caTicket.getLandscapeId().getId()).getLandscapeName());
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
			Date now;
			try {
				now = simpleDateFormat.parse(simpleDateFormat.format(Calendar.getInstance().getTime()));
				model.put(Constants.AGING,RMSUtil.isAgingCrossed(now, caTicket.getCreationDate(),caTicket.getPriority(),caTicket.getClosePendingCustomerApprovalDate()));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			model.put(Constants.SOLUTION_READY_FOR_REVIEW, caTicket.getSolutionReadyForReview());
		}else if (subjectFlag.equalsIgnoreCase("Soution_Ready_Ror_Review")&& methodName.equalsIgnoreCase("create")) {
			model.put(Constants.FILE_NAME, Constants.CA_TICKET_CREATE_FTL);
			model.put(Constants.SUBJECT, resourceService.find(caTicket.getAssigneeId().getEmployeeId()).getFirstName()+" -Ticket Ceated and Assigned to you. Solution is ready for review. Ticket No: "+ caTicket.getCaTicketNo()+" ; Reviewer :"+resourceService.find(caTicket.getReviewer().getEmployeeId()).getFirstName()+" "+resourceService.find(caTicket.getReviewer().getEmployeeId()).getLastName());
			model.put(Constants.CATicketMail, caTicket);
			model.put(Constants.EMAIL_ID, assineeIds[0]);
			model.put(Constants.PROJECT_NAME,projectService.findProject(caTicket.getModuleId().getId()).getProjectName());
			model.put(Constants.ASSIGNEE_NAME, resourceService.find(caTicket.getAssigneeId().getEmployeeId()).getFirstName()+" "+(resourceService.find(caTicket.getAssigneeId().getEmployeeId())).getLastName());
			model.put(Constants.REVIEWER_NAME,resourceService.find(caTicket.getReviewer().getEmployeeId()).getFirstName()+" "+(resourceService.find(caTicket.getReviewer().getEmployeeId())).getLastName());
			model.put(Constants.UNIT_NAME,caTicketService.getUnit(caTicket.getUnitId().getId()).getUnitName());
			model.put(Constants.REGION_NAME, caTicketService.getRegion(caTicket.getRegion().getId()).getRegionName());
			model.put(Constants.LANDSCAPE_NAME, caTicketService.getLandscape(caTicket.getLandscapeId().getId()).getLandscapeName());
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
			Date now;
			try {
				now = simpleDateFormat.parse(simpleDateFormat.format(Calendar.getInstance().getTime()));
				model.put(Constants.AGING,RMSUtil.isAgingCrossed(now, caTicket.getCreationDate(),caTicket.getPriority(),caTicket.getClosePendingCustomerApprovalDate()));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			model.put(Constants.SOLUTION_READY_FOR_REVIEW, caTicket.getSolutionReadyForReview());
		}else if (subjectFlag.equalsIgnoreCase("Soution_Ready_Ror_Review")&& methodName.equalsIgnoreCase("edit")) {
			model.put(Constants.FILE_NAME, Constants.CA_TICKET_EDIT_FTL);
			model.put(Constants.SUBJECT, resourceService.find(caTicketForm.getAssigneeId().getEmployeeId()).getFirstName()+" -Ticket Edited. Solution is ready for review. Ticket No: "+ caTicketForm.getCaTicketNo()+" ; Reviewer :"+resourceService.find(caTicketForm.getReviewer().getEmployeeId()).getFirstName()+" "+resourceService.find(caTicketForm.getReviewer().getEmployeeId()).getLastName());
			model.put(Constants.CATicketMail, caTicket);
			model.put(Constants.EMAIL_ID, assineeIds[0]);
			model.put(Constants.PROJECT_NAME,projectService.findProject(caTicket.getModuleId().getId()).getProjectName());
			model.put(Constants.ASSIGNEE_NAME, resourceService.find(caTicket.getAssigneeId().getEmployeeId()).getFirstName()+" "+(resourceService.find(caTicket.getAssigneeId().getEmployeeId())).getLastName());
			model.put(Constants.REVIEWER_NAME,resourceService.find(caTicket.getReviewer().getEmployeeId()).getFirstName()+" "+(resourceService.find(caTicket.getReviewer().getEmployeeId())).getLastName());
			model.put(Constants.UNIT_NAME,caTicketService.getUnit(caTicket.getUnitId().getId()).getUnitName());
			model.put(Constants.REGION_NAME, caTicketService.getRegion(caTicket.getRegion().getId()).getRegionName());
			model.put(Constants.LANDSCAPE_NAME, caTicketService.getLandscape(caTicket.getLandscapeId().getId()).getLandscapeName());
			model.put(Constants.DESCRIPTION , caTicket.getDescription());
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
			Date now;
			try {
				now = simpleDateFormat.parse(simpleDateFormat.format(Calendar.getInstance().getTime()));
				model.put(Constants.AGING,RMSUtil.isAgingCrossed(now, caTicket.getCreationDate(),caTicket.getPriority(),caTicket.getClosePendingCustomerApprovalDate()));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			model.put(Constants.SOLUTION_READY_FOR_REVIEW, caTicket.getSolutionReadyForReview());
			
			
			model.put(Constants.PROJECT_NAME_UPDATED,projectService.findProject(caTicketForm.getModuleId().getId()).getProjectName());
			model.put(Constants.ASSIGNEE_NAME_UPDATED, resourceService.find(caTicketForm.getAssigneeId().getEmployeeId()).getFirstName()+" "+(resourceService.find(caTicketForm.getAssigneeId().getEmployeeId())).getLastName());
			model.put(Constants.REVIEWER_NAME_UPDATED,resourceService.find(caTicketForm.getReviewer().getEmployeeId()).getFirstName()+" "+(resourceService.find(caTicketForm.getReviewer().getEmployeeId())).getLastName());
			model.put(Constants.UNIT_NAME_UPDATED,caTicketService.getUnit(caTicketForm.getUnitId().getId()).getUnitName());
			model.put(Constants.REGION_NAME_UPDATED, caTicketService.getRegion(caTicketForm.getRegion().getId()).getRegionName());
			model.put(Constants.LANDSCAPE_NAME_UPDATED, caTicketService.getLandscape(caTicketForm.getLandscapeId().getId()).getLandscapeName());
			model.put(Constants.DESCRIPTION_UPDATED , caTicketForm.getDescription());
			SimpleDateFormat simpleDateFormatupdated = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
			Date nowupdated;
			try {
				nowupdated = simpleDateFormatupdated.parse(simpleDateFormatupdated.format(Calendar.getInstance().getTime()));
				model.put(Constants.AGING_UPDATED,RMSUtil.isAgingCrossed(nowupdated, caTicketForm.getCreationDate(),caTicket.getPriority(),caTicket.getClosePendingCustomerApprovalDate()));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			model.put(Constants.SOLUTION_READY_FOR_REVIEW_UPDATED, caTicketForm.getSolutionReadyForReview());
			
		}else if (subjectFlag.equalsIgnoreCase("edit_ticket")) {
			model.put(Constants.FILE_NAME, Constants.CA_TICKET_EDIT_FTL);
			model.put(Constants.SUBJECT, resourceService.find(caTicketForm.getAssigneeId().getEmployeeId()).getFirstName()+" - Ticket has been edited. Ticket No: "+ caTicket.getCaTicketNo()+" ; Reviewer :"+resourceService.find(caTicketForm.getReviewer().getEmployeeId()).getFirstName()+" "+resourceService.find(caTicketForm.getReviewer().getEmployeeId()).getLastName());
			model.put(Constants.CATicketMail, caTicket);
			model.put(Constants.EMAIL_ID, assineeIds[0]);
			model.put(Constants.PROJECT_NAME,projectService.findProject(caTicket.getModuleId().getId()).getProjectName());
			model.put(Constants.ASSIGNEE_NAME, resourceService.find(caTicket.getAssigneeId().getEmployeeId()).getFirstName()+" "+(resourceService.find(caTicket.getAssigneeId().getEmployeeId())).getLastName());
			model.put(Constants.REVIEWER_NAME,resourceService.find(caTicket.getReviewer().getEmployeeId()).getFirstName()+" "+(resourceService.find(caTicket.getReviewer().getEmployeeId())).getLastName());
			model.put(Constants.UNIT_NAME,caTicketService.getUnit(caTicket.getUnitId().getId()).getUnitName());
			model.put(Constants.REGION_NAME, caTicketService.getRegion(caTicket.getRegion().getId()).getRegionName());
			model.put(Constants.LANDSCAPE_NAME, caTicketService.getLandscape(caTicket.getLandscapeId().getId()).getLandscapeName());
			model.put(Constants.DESCRIPTION , caTicket.getDescription());
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
			Date now;
			try {
				now = simpleDateFormat.parse(simpleDateFormat.format(Calendar.getInstance().getTime()));
				model.put(Constants.AGING,RMSUtil.isAgingCrossed(now, caTicket.getCreationDate(),caTicket.getPriority(),caTicket.getClosePendingCustomerApprovalDate()));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			model.put(Constants.SOLUTION_READY_FOR_REVIEW, caTicket.getSolutionReadyForReview());
			
			
			
			model.put(Constants.PROJECT_NAME_UPDATED,projectService.findProject(caTicketForm.getModuleId().getId()).getProjectName());
			model.put(Constants.ASSIGNEE_NAME_UPDATED, resourceService.find(caTicketForm.getAssigneeId().getEmployeeId()).getFirstName()+" "+(resourceService.find(caTicketForm.getAssigneeId().getEmployeeId())).getLastName());
			model.put(Constants.REVIEWER_NAME_UPDATED,resourceService.find(caTicketForm.getReviewer().getEmployeeId()).getFirstName()+" "+(resourceService.find(caTicketForm.getReviewer().getEmployeeId())).getLastName());
			model.put(Constants.UNIT_NAME_UPDATED,caTicketService.getUnit(caTicketForm.getUnitId().getId()).getUnitName());
			model.put(Constants.REGION_NAME_UPDATED, caTicketService.getRegion(caTicketForm.getRegion().getId()).getRegionName());
			model.put(Constants.LANDSCAPE_NAME_UPDATED, caTicketService.getLandscape(caTicketForm.getLandscapeId().getId()).getLandscapeName());
			model.put(Constants.DESCRIPTION_UPDATED , caTicketForm.getDescription());
			SimpleDateFormat simpleDateFormatupdated = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
			Date nowupdated;
			try {
				nowupdated = simpleDateFormatupdated.parse(simpleDateFormatupdated.format(Calendar.getInstance().getTime()));
				model.put(Constants.AGING_UPDATED,RMSUtil.isAgingCrossed(nowupdated, caTicketForm.getCreationDate(),caTicket.getPriority(),caTicket.getClosePendingCustomerApprovalDate()));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			model.put(Constants.SOLUTION_READY_FOR_REVIEW_UPDATED, caTicketForm.getSolutionReadyForReview());
		}else if(subjectFlag.equalsIgnoreCase("change_Fun")||subjectFlag.equalsIgnoreCase("trans_Fun")){
			model.put(Constants.FILE_NAME, Constants.CA_TICKET_EDIT_FTL);
			model.put(Constants.SUBJECT, resourceService.find(caTicketForm.getAssigneeId().getEmployeeId()).getFirstName()+" - Ticket has been edited. Ticket No: "+ caTicket.getCaTicketNo()+" ; Reviewer :"+resourceService.find(caTicketForm.getReviewer().getEmployeeId()).getFirstName()+" "+resourceService.find(caTicketForm.getReviewer().getEmployeeId()).getLastName());
			model.put(Constants.CATicketMail, caTicket);
			model.put(Constants.EMAIL_ID, assineeIds[0]);
			model.put(Constants.PROJECT_NAME,projectService.findProject(caTicket.getModuleId().getId()).getProjectName());
			model.put(Constants.ASSIGNEE_NAME, resourceService.find(caTicket.getAssigneeId().getEmployeeId()).getFirstName()+" "+(resourceService.find(caTicket.getAssigneeId().getEmployeeId())).getLastName());
			model.put(Constants.REVIEWER_NAME,resourceService.find(caTicket.getReviewer().getEmployeeId()).getFirstName()+" "+(resourceService.find(caTicket.getReviewer().getEmployeeId())).getLastName());
			model.put(Constants.UNIT_NAME,caTicketService.getUnit(caTicket.getUnitId().getId()).getUnitName());
			model.put(Constants.REGION_NAME, caTicketService.getRegion(caTicket.getRegion().getId()).getRegionName());
			model.put(Constants.LANDSCAPE_NAME, caTicketService.getLandscape(caTicket.getLandscapeId().getId()).getLandscapeName());
			model.put(Constants.DESCRIPTION , caTicket.getDescription());
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
			Date now;
			try {
				now = simpleDateFormat.parse(simpleDateFormat.format(Calendar.getInstance().getTime()));
				model.put(Constants.AGING,RMSUtil.isAgingCrossed(now, caTicket.getCreationDate(),caTicket.getPriority(),caTicket.getClosePendingCustomerApprovalDate()));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			model.put(Constants.SOLUTION_READY_FOR_REVIEW, caTicket.getSolutionReadyForReview());
			
			
			
			model.put(Constants.PROJECT_NAME_UPDATED,projectService.findProject(caTicketForm.getModuleId().getId()).getProjectName());
			model.put(Constants.ASSIGNEE_NAME_UPDATED, resourceService.find(caTicketForm.getAssigneeId().getEmployeeId()).getFirstName()+" "+(resourceService.find(caTicketForm.getAssigneeId().getEmployeeId())).getLastName());
			model.put(Constants.REVIEWER_NAME_UPDATED,resourceService.find(caTicketForm.getReviewer().getEmployeeId()).getFirstName()+" "+(resourceService.find(caTicketForm.getReviewer().getEmployeeId())).getLastName());
			model.put(Constants.UNIT_NAME_UPDATED,caTicketService.getUnit(caTicket.getUnitId().getId()).getUnitName());
			model.put(Constants.REGION_NAME_UPDATED, caTicketService.getRegion(caTicket.getRegion().getId()).getRegionName());
			model.put(Constants.LANDSCAPE_NAME_UPDATED, caTicketService.getLandscape(caTicket.getLandscapeId().getId()).getLandscapeName());
			model.put(Constants.DESCRIPTION_UPDATED , caTicket.getDescription());
			SimpleDateFormat simpleDateFormatupdated = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
			Date nowupdated;
			try {
				nowupdated = simpleDateFormatupdated.parse(simpleDateFormatupdated.format(Calendar.getInstance().getTime()));
				model.put(Constants.AGING_UPDATED,RMSUtil.isAgingCrossed(nowupdated, caTicket.getCreationDate(),caTicket.getPriority(),caTicket.getClosePendingCustomerApprovalDate()));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			model.put(Constants.SOLUTION_READY_FOR_REVIEW_UPDATED, caTicket.getSolutionReadyForReview());
		}else if (subjectFlag.equalsIgnoreCase("Ticket_Create")
				&& methodName.equalsIgnoreCase("upload")) {
			model.put(Constants.FILE_NAME, Constants.CA_TICKET_CREATE_FTL);
			model.put(
					Constants.SUBJECT,
					resourceService.find(
							caTicket.getAssigneeId().getEmployeeId())
							.getFirstName()
							+ " - Ticket Ceated and Assigned to you. Ticket No: "
							+ caTicket.getCaTicketNo()
							+ " ; Reviewer :"
							+ resourceService.find(
									caTicket.getReviewer().getEmployeeId())
									.getFirstName()
							+ " "
							+ resourceService.find(
									caTicket.getReviewer().getEmployeeId())
									.getLastName());
			model.put(Constants.CATicketMail, caTicket);
			model.put(Constants.EMAIL_ID, assineeIds[0]);
			model.put(Constants.PROJECT_NAME,
					projectService.findProject(caTicket.getModuleId().getId())
							.getProjectName());
			model.put(
					Constants.ASSIGNEE_NAME,
					resourceService.find(
							caTicket.getAssigneeId().getEmployeeId())
							.getFirstName()
							+ " "
							+ (resourceService.find(caTicket.getAssigneeId()
									.getEmployeeId())).getLastName());
			model.put(
					Constants.REVIEWER_NAME,
					resourceService
							.find(caTicket.getReviewer().getEmployeeId())
							.getFirstName()
							+ " "
							+ (resourceService.find(caTicket.getReviewer()
									.getEmployeeId())).getLastName());
			if (caTicket.getUnitId() != null) {
				model.put(Constants.UNIT_NAME,
						caTicketService.getUnit(caTicket.getUnitId().getId())
								.getUnitName());
			} else {
				model.put(Constants.UNIT_NAME, "");
			}

			if (caTicket.getRegion() != null) {
				model.put(Constants.REGION_NAME,
						caTicketService.getRegion(caTicket.getRegion().getId())
								.getRegionName());
			} else {
				model.put(Constants.REGION_NAME, "");
			}

			if (caTicket.getLandscapeId() != null) {
				model.put(Constants.LANDSCAPE_NAME, caTicketService
						.getLandscape(caTicket.getLandscapeId().getId())
						.getLandscapeName());
			} else {
				model.put(Constants.LANDSCAPE_NAME, "");
			}
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
					"yyyy/MM/dd hh:mm:ss");
			Date now;
			try {
				now = simpleDateFormat.parse(simpleDateFormat.format(Calendar
						.getInstance().getTime()));
				model.put(Constants.AGING, RMSUtil.isAgingCrossed(now,
						caTicket.getCreationDate(), caTicket.getPriority(),
						caTicket.getClosePendingCustomerApprovalDate()));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			model.put(Constants.SOLUTION_READY_FOR_REVIEW,
					caTicket.getSolutionReadyForReview());
		}
		
		
	}

	public void setEmailContentForInternalRequest(Map<String, Object> model, String[] tomailIDsList,
			String[] sentMailCCTo, String sender, String projectName, String projectBU, String sendMailFrom,
			String comments, String client, String reqId, Integer noOfResources, String designationName, 
			String indentBy, String amJobCode, String groupName, List<ResourceStatusDTO> resourceStatuslistOld,
			List<ResourceStatusDTO> resourceStatusNewlist, RequestRequisitionResourceFormDTO requestRequisitionResourceFormDTO,
			List<RequestRequisitionSkill> requestRequisitionSkillsList,String rrfBgBu,String successFactorId,String requirementArea) {
		String currentDate=org.yash.rms.util.DateUtil.getStringDate(new Date());
		String currentTime=org.yash.rms.util.DateUtil.getStringTime(new Date());
		model.put(Constants.FILE_NAME, Constants.RESOURCE_REQUEST_UPDATION_HTML);
		if(null!=amJobCode && !amJobCode.isEmpty())
			model.put("subject", Constants.RRF_RESOURCE_SUBMIT_SUBJECT +  reqId+" - [ "+amJobCode+" ]");
		else
			model.put("subject", Constants.RRF_RESOURCE_SUBMIT_SUBJECT +  reqId);
		model.put("toMail", tomailIDsList); 
		model.put("rrfName", reqId);
		model.put("projectName", projectName);
		model.put("projectBU", projectBU);
		model.put("sendMailFrom", sendMailFrom); 
		model.put("clientName", client); 
		model.put(Constants.INDENTER, sender);
		model.put("ccMail", sentMailCCTo);
		model.put(Constants.COMMENTS, comments);
		model.put("groupName", groupName);
		model.put("noOfResources", noOfResources);
		model.put("designationName", designationName);
		model.put("indentBy", indentBy);
		model.put("amJobCode", amJobCode);
		model.put("currentDate", currentDate);
		model.put("currentTime", currentTime);
		model.put("requirementArea",requirementArea);
		model.put(Constants.NEW_RESOURCE_STATUS_LIST, resourceStatusNewlist);
		model.put(Constants.RESOURCE_STATUS_LIST, resourceStatuslistOld);
		model.put(Constants.REQUEST_RESOURCE_STATUS_LIST, requestRequisitionResourceFormDTO.getRequestRequisitionResourceStatusList());		
		System.out.println(model);
		model.put(Constants.REQ_MAIL_LIST, requestRequisitionSkillsList);
		/*Start - Code to pass RRF bg-bu */
		if(null!=rrfBgBu && !rrfBgBu.isEmpty()) {
			model.put("rrfBgBu", rrfBgBu);
		}else {
			model.put("rrfBgBu", "NA");
		}
		model.put("successFactorId", successFactorId);
		/*End - Code to pass RRF bg-bu */
		getTrimPrimarySkills(requestRequisitionSkillsList, model);
		//model.put(Constants.REQ_MAIL_LIST,  requestRequisitionSkillsList);
	}
	
	public void setEmailContentForRRFClosureDate(Map<String, Object> model, String[] tomailIDsList,
			String[] sentMailCCTo, String sender, String rrfName, String customerName,String customerGroupName, String projectName,
			String projectBU,String sendMailFrom, Integer noOfResources, Integer joined,  Integer hold, Integer lost, String designationName,
			String indentBy, String amJobCode, List<RequestRequisitionResource> requestRequisitionResources, 
			List<ResourceStatusDTO> resourceStatusList ,List<RequestRequisitionSkill> requestRequisitionSkillsList,String successFactorId, String requirementArea) {
		String closureDate=org.yash.rms.util.DateUtil.getStringDate(new Date());
		String currentTime=org.yash.rms.util.DateUtil.getStringTime(new Date());
		model.put(Constants.FILE_NAME, Constants.RRF_CLOSURE_MAIL_HTML);
		model.put("toMail", tomailIDsList); 
		//model.put("subject", "RRF Closure Mail for - " +  rrfName);
		if(null!=amJobCode && !amJobCode.isEmpty())
			model.put("subject", "RRF Closed - " +rrfName+" - (Closed-"+joined+") - [ "+amJobCode+" ]");
		else
			model.put("subject", "RRF Closed - " +rrfName+" - (Closed-"+joined+")");
		model.put("rrfName", rrfName);
		model.put("customerName", customerName);
		model.put("customerGroupName", customerGroupName);
		model.put("projectName", projectName);
		model.put("projectBU", projectBU); 
		model.put("sendMailFrom", sendMailFrom); 
		model.put("noOfResources", noOfResources); 
		model.put(Constants.INDENTER, sender);
		model.put("ccMail", sentMailCCTo);
		model.put("joined", joined);
		model.put("hold", hold);
		model.put("lost", lost);
		model.put("closureDate", closureDate);
		model.put("designationName", designationName);
		model.put("indentBy", indentBy);
		model.put("amJobCode", amJobCode);
		model.put("currentTime", currentTime);
		model.put("requirementArea",requirementArea);
		model.put("requestRequisitionResources", requestRequisitionResources); //TODO : external and internal bifercation and details to be included
		model.put(Constants.RESOURCE_STATUS_LIST, resourceStatusList);
		System.out.println(model);
		model.put(Constants.REQ_MAIL_LIST, requestRequisitionSkillsList);	
		model.put("successFactorId",successFactorId);
		getTrimPrimarySkills(requestRequisitionSkillsList, model);
	//	model.put(Constants.REQ_MAIL_LIST,  requestRequisitionSkillsList);
	}
	public void setEmailContentForGoingtoCloseProject(Map<String, Object> model, String[] tomailIDsList,
			String[] sentMailCCTo, String projectName, String comments, String client, String reqId, Integer noOfResources, String designationName, 
			String indentBy, String amJobCode, String groupName,String rrfBgBu,String successFactorId,String requirementArea) 
	{
		String currentDate=org.yash.rms.util.DateUtil.getStringDate(new Date());
		String currentTime=org.yash.rms.util.DateUtil.getStringTime(new Date());
		Calendar c = Calendar.getInstance(); // starts with today's date and time
		c.add(Calendar.DAY_OF_YEAR, Integer.parseInt(Constants.CLOSING_PROJECT_RRF_DAYS));  // advances day by 2
		String futureDate=org.yash.rms.util.DateUtil.getStringDate(c.getTime());
		model.put(Constants.FILE_NAME, Constants.Project_GoingTo_Close_HTML);
		if(null!=amJobCode && !amJobCode.isEmpty())
			model.put("subject", Constants.CLOSING_PROJECT_RRF_SUBJECT+  reqId+" - [ "+amJobCode+" ]");
		else
			model.put("subject", Constants.CLOSING_PROJECT_RRF_SUBJECT +  reqId);
		model.put("toMail", tomailIDsList); 
		model.put("rrfName", reqId);
		model.put("projectName", projectName);
		if(requirementArea!=null && requirementArea.equalsIgnoreCase(Constants.SAP)){
			model.put("sendMailFrom", Constants.RMG_ERP_PDL_EMAIL); 
			model.put("requirementArea",requirementArea);
			}
		else{
			model.put("sendMailFrom",  Constants.RMG_PDL_EMAIL); 
			model.put("requirementArea",Constants.NON_SAP_STRING);
		}
			
		
		model.put("clientName", client); 
		model.put("ccMail", sentMailCCTo);
		model.put(Constants.COMMENTS, comments);
		model.put("groupName", groupName);
		model.put("noOfResources", noOfResources);
		model.put("designationName", designationName);
		model.put("indentBy", indentBy);
		model.put("amJobCode", amJobCode);
		model.put("currentDate", currentDate);
		model.put("currentTime", currentTime);
	
		model.put("futureDate",futureDate);
		System.out.println(model);
		if(null!=rrfBgBu && !rrfBgBu.isEmpty()) {
			model.put("rrfBgBu", rrfBgBu);
		}else {
			model.put("rrfBgBu", "NA");
		}
		model.put("successFactorId", successFactorId);
		
	}
}

