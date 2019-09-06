package org.yash.rms.mapper;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.yash.rms.domain.Priority;
import org.yash.rms.domain.ReasonForReplacement;
import org.yash.rms.domain.RequestRequisition;
import org.yash.rms.domain.RequestRequisitionResource;
import org.yash.rms.domain.Resource;
import org.yash.rms.dto.PriorityDTO;
import org.yash.rms.dto.ReasonForReplacementDTO;
import org.yash.rms.dto.RequestRequisitionDTO;
import org.yash.rms.dto.RequisitionResourceDTO;
import org.yash.rms.helper.ByteConvHelper;
import org.yash.rms.service.ResourceService;
import org.yash.rms.util.Constants;
import org.yash.rms.util.GeneralFunction;
import org.yash.rms.util.UserUtil;

@Component("requestRequisitionFormMapper")
public class RequestRequisitionFormMapper {
	
	@Autowired
	@Qualifier("mapper")
	private DozerBeanMapper mapper;
	
	@Autowired
	ResourceService resourceService;

	@Autowired
	UserUtil userUtil;
	
	public RequestRequisitionDTO convertDomainToDTO(org.yash.rms.domain.RequestRequisition requestRequisition) {
		RequestRequisitionDTO requestRequisitionDto = new RequestRequisitionDTO();
		requestRequisitionDto.setProjectBU(requestRequisition.getProjectBU());
		requestRequisitionDto.setId(requestRequisition.getId());
		requestRequisitionDto.setComments(requestRequisition.getComments());
		requestRequisitionDto.setCustomer(requestRequisition.getCustomer());
		requestRequisitionDto.setDeliveryPOC(requestRequisition.getDeliveryPOC());
		requestRequisitionDto.setbGHApprovalFileName(requestRequisition.getBGUApprovalFileName());
		requestRequisitionDto.setShiftType(requestRequisition.getShiftTypeId());
		requestRequisitionDto.setLastupdatedTimestamp(requestRequisition.getLastupdatedTimestamp());
		requestRequisitionDto.setCreationTimestamp(requestRequisition.getCreationTimestamp());
		
		if(null != requestRequisition.getBGUApprovalFile()) {
			ByteConvHelper byteConvHelperForBGH = new ByteConvHelper(requestRequisition.getBGUApprovalFile(), requestRequisition.getBGUApprovalFileName());
			requestRequisitionDto.setbGHFile(byteConvHelperForBGH);
		}
			  
		if(null != requestRequisition.getBUHApprovalFile()){
			ByteConvHelper byteConvHelperForBUH = new ByteConvHelper(requestRequisition.getBUHApprovalFile(), requestRequisition.getBUHApprovalFileName());
			requestRequisitionDto.setbUHFile(byteConvHelperForBUH);
		}
		
		SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.DATE_PATTERN);
		requestRequisitionDto.setDate(dateFormat.format(requestRequisition.getDate()));
		requestRequisitionDto.setResourceRequiredDate(dateFormat.format(requestRequisition.getResourceRequiredDate()));

		requestRequisitionDto.setNotifyMailTo(requestRequisition.getNotifyMailTo());
		requestRequisitionDto.setProject(requestRequisition.getProject());
		requestRequisitionDto.setResource(requestRequisition.getResource());
		requestRequisitionDto.setSentMailTo(requestRequisition.getSentMailTo());
		
		if(null != requestRequisition.getGroup()){
			requestRequisitionDto.setGroup(requestRequisition.getGroup());
		}
		//requestRequisitionDto.setToMailIds(requestRequisition.getSentMailTo().split(",")); //copy RRF
		requestRequisitionDto.setbUHApprovalFileName(requestRequisition.getBUHApprovalFileName());
		//requestRequisitionDto.setCcMailIds(requestRequisition.getNotifyMailTo().split(",")); //copy RRF
		requestRequisitionDto.setClientType(requestRequisition.getClientType());
		//requestRequisitionDto.setCurrentBU(requestRequisition.get);
		requestRequisitionDto.setCustomerName(requestRequisition.getCustomer().getCustomerName());
		if(null != requestRequisition.getGroup()){
			requestRequisitionDto.setGroupName(requestRequisition.getGroup().getCustomerGroupName());
		}
		requestRequisitionDto.setIsBGVrequired(requestRequisition.getIsBGVrequired());
		requestRequisitionDto.setIsClientInterviewRequired(requestRequisition.getIsClientInterviewRequired());
		requestRequisitionDto.setPdlEmailIds(requestRequisition.getPdlEmailIds());
		requestRequisitionDto.setProjectType(requestRequisition.getProjectType());
		requestRequisitionDto.setProjectDuration(requestRequisition.getProjectDuration());
		requestRequisitionDto.setProjectShiftOtherDetails(requestRequisition.getProjectShiftOtherDetails());
		requestRequisitionDto.setRequestor(requestRequisition.getRequestor());
		requestRequisitionDto.setRequiredFor(requestRequisition.getRequiredFor());
		requestRequisitionDto.setAmJobCode(requestRequisition.getAmJobCode());
		requestRequisitionDto.setExpOtherDetails(requestRequisition.getExpOtherDetails());
		requestRequisitionDto.setHiringBGBU(requestRequisition.getHiringBGBU());
		ReasonForReplacementDTO dto = new ReasonForReplacementDTO();
		if(requestRequisition.getReason()!=null){
			mapper.map(requestRequisition.getReason(), dto);
			requestRequisitionDto.setReason(dto);
		}
		
		if(requestRequisition.getPriority() != null){
			PriorityDTO prioritydto = new PriorityDTO();
			mapper.map(requestRequisition.getPriority(), prioritydto);
			requestRequisitionDto.setPriority(prioritydto);
		}
		
		requestRequisitionDto.setSuccessFactorId(requestRequisition.getSuccessFactorId());
		requestRequisitionDto.setReplacementResource(requestRequisition.getReplacementResource());
		requestRequisitionDto.setRmgPoc(requestRequisition.getRmgPoc());
		requestRequisitionDto.setTechTeamPoc(requestRequisition.getTecTeamPoc());
		
		//setting RMG POC and TAC team and mailTO and Notify To POC in list<Object> format - Start 
		String rmgPoc = StringUtils.trimToEmpty(requestRequisition.getRmgPoc()); 
		String tecTeamPoc = StringUtils.trimToEmpty(requestRequisition.getTecTeamPoc());
		String sentMailToPoc = StringUtils.trimToEmpty(requestRequisition.getSentMailTo());
		String notifyMailToPoc = StringUtils.trimToEmpty(requestRequisition.getNotifyMailTo());
		 
		if(!rmgPoc.isEmpty()) {
            List<String> stringList = Arrays.asList(rmgPoc.split(","));
            List<Integer> integerList = (List<Integer>) GeneralFunction.convertStringListToIntList(stringList);
            List<Resource> resources = resourceService.findResourceByEmployeeIds(integerList);
            if(resources!=null && resources.size()>0)
            	requestRequisitionDto.setRmgPOCList(resources);
      }
		if(!tecTeamPoc.isEmpty()) {
            List<String> stringList = Arrays.asList(tecTeamPoc.split(","));
            List<Integer> integerList = (List<Integer>) GeneralFunction.convertStringListToIntList(stringList);
            List<Resource> resources = resourceService.findResourceByEmployeeIds(integerList);
            if(resources!=null && resources.size()>0)
            	requestRequisitionDto.setTechTeamPocList(resources);
      }
		if(!sentMailToPoc.isEmpty()) {

            List<String> stringList = Arrays.asList(sentMailToPoc.split(","));
            List<Integer> integerList = (List<Integer>) GeneralFunction.convertStringListToIntList(stringList);
            List<Resource> resources = resourceService.findResourceByEmployeeIds(integerList);
            if(resources!=null && resources.size()>0 )
            	requestRequisitionDto.setSentMailToList(resources);
      }
		if(!notifyMailToPoc.isEmpty()) {
            List<String> stringList = Arrays.asList(notifyMailToPoc.split(","));
            List<Integer> integerList = (List<Integer>) GeneralFunction.convertStringListToIntList(stringList);
            List<Resource> resources = resourceService.findResourceByEmployeeIds(integerList);
            if(resources!=null && resources.size()>0)
            	requestRequisitionDto.setNotifyMailToList(resources);
      }
		
		
		//setting RMG POC and TAC team and mailTO and Notify To POC in list<Object> format - End 
		
		
		
		return requestRequisitionDto;
	}
	
	
	public RequestRequisition convertRequestRequisitionEntityToDomainForCopyRRF(RequestRequisition domain) {

		RequestRequisition copiedObject = new RequestRequisition();
		copiedObject.setProjectBU(domain.getProjectBU());
		copiedObject.setComments(domain.getComments());
		copiedObject.setCustomer(domain.getCustomer());
		copiedObject.setDeliveryPOC(domain.getDeliveryPOC());
		copiedObject.setShiftTypeId(domain.getShiftTypeId());
		
		/*if(null != domain.getBGUApprovalFile()) {
			copiedObject.setBGUApprovalFile(domain.getBGUApprovalFile());
			copiedObject.setBGUApprovalFileName(domain.getBGUApprovalFileName());
		}
		if(null != domain.getBUHApprovalFile()){
			copiedObject.setBUHApprovalFile(domain.getBUHApprovalFile());
			copiedObject.setBUHApprovalFileName(domain.getBUHApprovalFileName());
		}*/
		
		copiedObject.setCreationTimestamp(new Date());
		Resource loggedInResource = userUtil.getLoggedInResource();
		copiedObject.setResource(loggedInResource);
		copiedObject.setRequestor(loggedInResource);
		Calendar cal = Calendar.getInstance(); 
		cal.add(Calendar.MONTH, 2); //samiksha 
		copiedObject.setResourceRequiredDate(cal.getTime());
		copiedObject.setDate(new Date());
		copiedObject.setNotifyMailTo(domain.getNotifyMailTo());
		copiedObject.setProject(domain.getProject());
		//copiedObject.setResource(domain.getResource());
		copiedObject.setSentMailTo(domain.getSentMailTo());
		
		if(null != domain.getGroup()){
			copiedObject.setGroup(domain.getGroup());
		}
		
		copiedObject.setClientType(domain.getClientType());
		//requestRequisitionDto.setCurrentBU(domain.get);
		copiedObject.setCustomer(domain.getCustomer());
		/*if(null != domain.getGroup()){
			copiedObject.setGroupName(domain.getGroup().getCustomerGroupName());
		}
		*/
		copiedObject.setIsBGVrequired(domain.getIsBGVrequired());
		copiedObject.setIsClientInterviewRequired(domain.getIsClientInterviewRequired());
		copiedObject.setPdlEmailIds(domain.getPdlEmailIds());
		copiedObject.setProjectType(domain.getProjectType());
		copiedObject.setProjectDuration(domain.getProjectDuration());
		copiedObject.setProjectShiftOtherDetails(domain.getProjectShiftOtherDetails());
		//copiedObject.setRequestor(domain.getRequestor());
		copiedObject.setRequiredFor(domain.getRequiredFor());
		copiedObject.setAmJobCode(domain.getAmJobCode());
		copiedObject.setExpOtherDetails(domain.getExpOtherDetails());
		copiedObject.setHiringBGBU(domain.getHiringBGBU());
		if(domain.getSuccessFactorId()!=null && !domain.getSuccessFactorId().isEmpty()) {
			copiedObject.setSuccessFactorId(domain.getSuccessFactorId());
		} else {
			copiedObject.setSuccessFactorId("");
		}
		
		if(domain.getReason()!=null){
			copiedObject.setReason(mapper.map(domain.getReason(), ReasonForReplacement.class));
		}
		
		if(domain.getPriority() != null){
			copiedObject.setPriority(mapper.map(domain.getPriority(), Priority.class));
		}
		
		if(domain.getReplacementResource()!=null) {
			copiedObject.setReplacementResource(domain.getReplacementResource());
		}
		
		if(domain.getRmgPoc()!=null && !domain.getRmgPoc().isEmpty()) {
			copiedObject.setRmgPoc(domain.getRmgPoc());
		}	
		
		if(domain.getTecTeamPoc()!=null && !domain.getTecTeamPoc().isEmpty()) {
			copiedObject.setTecTeamPoc(domain.getTecTeamPoc());
		}
		
		return copiedObject;
	
	}
	
	public RequestRequisition convertDTOTODomain(RequestRequisitionDTO requestRequisitionDto) {
		RequestRequisition requestRequisition = new RequestRequisition();
		try {
		
			requestRequisition.setId(requestRequisitionDto.getId());
			requestRequisition.setComments(requestRequisitionDto.getComments());
			requestRequisition.setCustomer(requestRequisitionDto.getCustomer());
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		
			Date date = new Date(requestRequisitionDto.getDate());
			java.sql.Date sqlDate = new java.sql.Date(date.getTime());
			requestRequisition.setDate(sqlDate);
			requestRequisition.setNotifyMailTo(requestRequisitionDto.getNotifyMailTo());
			requestRequisition.setProject(requestRequisitionDto.getProject());
			requestRequisition.setResource(requestRequisitionDto.getResource());
			requestRequisition.setSentMailTo(requestRequisitionDto.getSentMailTo());
			requestRequisition.setGroup(requestRequisitionDto.getGroup());
			requestRequisition.setPdlEmailIds(requestRequisitionDto.getPdlEmailIds());
			requestRequisition.setClientType(requestRequisitionDto.getClientType());
			requestRequisition.setDeliveryPOC(requestRequisitionDto.getDeliveryPOC());
			requestRequisition.setIsBGVrequired(requestRequisitionDto.getIsBGVrequired());
			requestRequisition.setIsClientInterviewRequired(requestRequisitionDto.getIsClientInterviewRequired());
			requestRequisition.setProjectBU(requestRequisitionDto.getProjectBU());
			requestRequisition.setProjectDuration(requestRequisitionDto.getProjectDuration());
			
			if(requestRequisitionDto.getProjectShiftOtherDetails()!=null && !requestRequisitionDto.getProjectShiftOtherDetails().isEmpty()){
				requestRequisition.setProjectShiftOtherDetails(requestRequisitionDto.getProjectShiftOtherDetails());
			}
			
			requestRequisition.setProjectType(requestRequisitionDto.getProjectType());
			requestRequisition.setRequestor(requestRequisitionDto.getRequestor());
			Date resourceRequiredDate = new Date(requestRequisitionDto.getResourceRequiredDate());
			java.sql.Date sqlRequiredDate = new java.sql.Date(resourceRequiredDate.getTime());
			requestRequisition.setResourceRequiredDate(sqlRequiredDate);
			requestRequisition.setShiftTypeId(requestRequisitionDto.getShiftType());
			if(requestRequisitionDto.getbGHFile()!=null){
				requestRequisition.setBGUApprovalFile(requestRequisitionDto.getbGHFile().getBytes());
				requestRequisition.setBGUApprovalFileName(requestRequisitionDto.getBGHApprovalFileName());
			}
			if(requestRequisitionDto.getbUHFile()!=null){
				requestRequisition.setBUHApprovalFile(requestRequisitionDto.getbUHFile().getBytes());
				requestRequisition.setBUHApprovalFileName(requestRequisitionDto.getBUHApprovalFileName());
			}
			requestRequisition.setShiftTypeId(requestRequisitionDto.getShiftType());
			requestRequisition.setRequiredFor(requestRequisitionDto.getRequiredFor());
			requestRequisition.setExpOtherDetails(requestRequisitionDto.getExpOtherDetails());
			requestRequisition.setHiringBGBU(requestRequisitionDto.getHiringBGBU());
			
			ReasonForReplacement reasonDomain = new ReasonForReplacement();
			if(requestRequisitionDto.getReason()!= null){
				mapper.map(requestRequisitionDto.getReason(), reasonDomain);
				requestRequisition.setReason(reasonDomain);
				
			}
			requestRequisition.setReplacementResource(requestRequisitionDto.getReplacementResource());
			requestRequisition.setAmJobCode(requestRequisitionDto.getAmJobCode());			
			requestRequisition.setSuccessFactorId(requestRequisitionDto.getSuccessFactorId());
			if(requestRequisitionDto.getPriority() != null){
				Priority domain = new Priority();
				mapper.map(requestRequisitionDto.getPriority(), domain);
				requestRequisition.setPriority(domain);
			}
			
			requestRequisition.setRmgPoc(requestRequisitionDto.getRmgPoc());
			requestRequisition.setTecTeamPoc(requestRequisitionDto.getTechTeamPoc());
			
			//requestRequisition.setLocation(requestRequisitionDto.getLocation());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return requestRequisition;
	}
	
	public RequestRequisition updateDomanin(RequestRequisitionDTO requestRequisitionDto, RequestRequisition requestRequisition) {

		try {
		
			requestRequisition.setId(requestRequisitionDto.getId());
			requestRequisition.setComments(requestRequisitionDto.getComments());
			requestRequisition.setCustomer(requestRequisitionDto.getCustomer());
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		
			Date date = new Date(requestRequisitionDto.getDate());
			java.sql.Date sqlDate = new java.sql.Date(date.getTime());
			requestRequisition.setDate(sqlDate);
			requestRequisition.setNotifyMailTo(requestRequisitionDto.getNotifyMailTo());
			requestRequisition.setProject(requestRequisitionDto.getProject());
			requestRequisition.setResource(requestRequisitionDto.getResource());
			requestRequisition.setSentMailTo(requestRequisitionDto.getSentMailTo());
		//	requestRequisition.setGroup(requestRequisitionDto.getGroup());
			requestRequisition.setPdlEmailIds(requestRequisitionDto.getPdlEmailIds());
			requestRequisition.setClientType(requestRequisitionDto.getClientType());
			
			requestRequisition.setDeliveryPOC(requestRequisitionDto.getDeliveryPOC());
			requestRequisition.setIsBGVrequired(requestRequisitionDto.getIsBGVrequired());
			requestRequisition.setIsClientInterviewRequired(requestRequisitionDto.getIsClientInterviewRequired());
			requestRequisition.setProject(requestRequisitionDto.getProject());
			requestRequisition.setProjectBU(requestRequisitionDto.getProjectBU());
			requestRequisition.setProjectDuration(requestRequisitionDto.getProjectDuration());
			requestRequisition.setProjectShiftOtherDetails(requestRequisitionDto.getProjectShiftOtherDetails());
			requestRequisition.setProjectType(requestRequisitionDto.getProjectType());
			requestRequisition.setRequestor(requestRequisitionDto.getRequestor());
			//Am Job Code
			requestRequisition.setAmJobCode(requestRequisitionDto.getAmJobCode());
			requestRequisition.setSuccessFactorId(requestRequisitionDto.getSuccessFactorId());
			
			Date resourceRequiredDate = new Date(requestRequisitionDto.getResourceRequiredDate());
			java.sql.Date sqlRequiredDate = new java.sql.Date(resourceRequiredDate.getTime());
			requestRequisition.setResourceRequiredDate(sqlRequiredDate);
			requestRequisition.setShiftTypeId(requestRequisitionDto.getShiftType());
			if(requestRequisitionDto.getbGHFile()!=null){
				requestRequisition.setBGUApprovalFile(requestRequisitionDto.getbGHFile().getBytes());
				requestRequisition.setBGUApprovalFileName(requestRequisitionDto.getBGHApprovalFileName());
			}
			if(requestRequisitionDto.getbUHFile()!=null){
				requestRequisition.setBUHApprovalFile(requestRequisitionDto.getbUHFile().getBytes());
				requestRequisition.setBUHApprovalFileName(requestRequisitionDto.getBUHApprovalFileName());
			}
			requestRequisition.setShiftTypeId(requestRequisitionDto.getShiftType());
			requestRequisition.setRequiredFor(requestRequisitionDto.getRequiredFor());
			requestRequisition.setExpOtherDetails(requestRequisitionDto.getExpOtherDetails());
			requestRequisition.setLastupdatedTimestamp(new Date());
			requestRequisition.setRmgPoc(requestRequisitionDto.getRmgPoc());
			requestRequisition.setTecTeamPoc(requestRequisitionDto.getTechTeamPoc());
			requestRequisition.setHiringBGBU(requestRequisitionDto.getHiringBGBU());
			//requestRequisition.setLocation(requestRequisitionDto.getLocation());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return requestRequisition;
	}
	
	public RequisitionResourceDTO covertDomainToDTO(RequestRequisitionResource requestRequisitionResource ){
		
		RequisitionResourceDTO requisitionResourceDTO = new RequisitionResourceDTO();
		if(requestRequisitionResource.getResource()==null){
			requisitionResourceDTO.setResourceName(requestRequisitionResource.getExternalResourceName());
			requisitionResourceDTO.setSkillReqName(requestRequisitionResource.getRequestRequisitionSkill().getRequirementId());
			requisitionResourceDTO.setResourceType(Constants.EXTERNAL_USER);
			requisitionResourceDTO.setDesignation("");
			requisitionResourceDTO.setStatus(requestRequisitionResource.getRequestRequisitionResourceStatus().getStatusName());
			requisitionResourceDTO.setSkill(requestRequisitionResource.getSkills());
			
			requisitionResourceDTO.setEmailId(requestRequisitionResource.getEmailId());
			requisitionResourceDTO.setContactNum(requestRequisitionResource.getContactNumber());
			requisitionResourceDTO.setTotalExperince(requestRequisitionResource.getTotalExperience());
			
			
			if(requestRequisitionResource.getNoticePeriod()!=null) {
			requisitionResourceDTO.setNoticePeriod(requestRequisitionResource.getNoticePeriod());
			}
			
			requisitionResourceDTO.setResourceId(requestRequisitionResource.getId().toString());
			requisitionResourceDTO.setInternalResId(requestRequisitionResource.getId().toString());
			
			requisitionResourceDTO.setLocation(requestRequisitionResource.getLocation());
			
			requisitionResourceDTO.setSkillReqId((requestRequisitionResource.getRequestRequisitionSkill().getId())+"");
			
			requisitionResourceDTO.setInterviewDate(requestRequisitionResource.getInterviewDate() != null
					? Constants.DATE_FORMAT_REPORTS.format(requestRequisitionResource.getInterviewDate())
					: "");
			requisitionResourceDTO.setJoiningDate(requestRequisitionResource.getAllocationDate() != null
                    ? Constants.DATE_FORMAT_REPORTS.format(requestRequisitionResource.getAllocationDate())
                    : "");			
			requisitionResourceDTO.setAllocationDate(requestRequisitionResource.getAllocationDate() != null
					? Constants.DATE_FORMAT_REPORTS.format(requestRequisitionResource.getAllocationDate())
					: "");
			requisitionResourceDTO.setResourceSubmittedDate(requestRequisitionResource.getSubmittedToPocDate() != null
					? Constants.DATE_FORMAT_REPORTS.format(requestRequisitionResource.getSubmittedToPocDate())
					: "");
			requisitionResourceDTO.setPositionCloseDate(requestRequisitionResource.getJoinedDate()!= null
			            ? Constants.DATE_FORMAT_REPORTS.format(requestRequisitionResource.getJoinedDate())
			            : "");
			
			requisitionResourceDTO.setResourceRejectedDate(requestRequisitionResource.getRejectionDate()!= null
                    ? Constants.DATE_FORMAT_REPORTS.format(requestRequisitionResource.getRejectionDate())
                    : "");
			
			requisitionResourceDTO.setResourceSelectedDate(requestRequisitionResource.getShortlistedDate()!= null
                    ? Constants.DATE_FORMAT_REPORTS.format(requestRequisitionResource.getShortlistedDate())
                    : "");
			requisitionResourceDTO.setResourceRejectedDate(requestRequisitionResource.getRejectionDate()!= null
                    ? Constants.DATE_FORMAT_REPORTS.format(requestRequisitionResource.getRejectionDate())
                    : "");
			requisitionResourceDTO.setResourceSelectedDate(requestRequisitionResource.getShortlistedDate()!= null
                    ? Constants.DATE_FORMAT_REPORTS.format(requestRequisitionResource.getShortlistedDate())
                    : "");
			
			 requisitionResourceDTO.setResourcePOCsubmittedDate(requestRequisitionResource.getSubmittedToPocDate()!= null
	                    ? Constants.DATE_FORMAT_REPORTS.format(requestRequisitionResource.getSubmittedToPocDate())
	                    : "");
	            
	            requisitionResourceDTO.setResourceAMsubmittedDate(requestRequisitionResource.getSubmittedToAmDate()!= null
	                    ? Constants.DATE_FORMAT_REPORTS.format(requestRequisitionResource.getSubmittedToAmDate())
	                    : "");
		}else{
			requisitionResourceDTO.setInternalResId(requestRequisitionResource.getResource().getEmployeeId().toString());
			requisitionResourceDTO.setResourceId(requestRequisitionResource.getId().toString());
			requisitionResourceDTO.setResourceName(requestRequisitionResource.getResource().getEmployeeName());
			requisitionResourceDTO.setSkillReqId((requestRequisitionResource.getRequestRequisitionSkill().getId())+"");
			requisitionResourceDTO.setSkillReqName(requestRequisitionResource.getRequestRequisitionSkill().getRequirementId());
			requisitionResourceDTO.setResourceType(Constants.INTERNAL_USER);
			requisitionResourceDTO.setDesignation(requestRequisitionResource.getResource().getDesignationId().getDesignationName());
			requisitionResourceDTO.setStatus(requestRequisitionResource.getRequestRequisitionResourceStatus().getStatusName());
			
			requisitionResourceDTO.setSkill(requestRequisitionResource.getResource().getCompetency().getSkill());
			requisitionResourceDTO.setEmailId(requestRequisitionResource.getResource().getEmailId());
			requisitionResourceDTO.setContactNum(requestRequisitionResource.getResource().getContactNumber());
			requisitionResourceDTO.setTotalExperince(requestRequisitionResource.getResource().getTotalExper()+"");
			
			if(requestRequisitionResource.getNoticePeriod()!=null) {
				requisitionResourceDTO.setNoticePeriod(requestRequisitionResource.getNoticePeriod());
			}
			requisitionResourceDTO.setLocation(requestRequisitionResource.getLocation());
			
			requisitionResourceDTO.setInterviewDate(requestRequisitionResource.getInterviewDate() != null
					? Constants.DATE_FORMAT_REPORTS.format(requestRequisitionResource.getInterviewDate())
					: "");
			requisitionResourceDTO.setJoiningDate(requestRequisitionResource.getAllocationDate() != null
					? Constants.DATE_FORMAT_REPORTS.format(requestRequisitionResource.getAllocationDate())
					: "");
			
			requisitionResourceDTO.setResourceSubmittedDate(requestRequisitionResource.getSubmittedToPocDate() != null
					? Constants.DATE_FORMAT_REPORTS.format(requestRequisitionResource.getSubmittedToPocDate())
					: "");
			requisitionResourceDTO.setPositionCloseDate(requestRequisitionResource.getJoinedDate()!= null
             ? Constants.DATE_FORMAT_REPORTS.format(requestRequisitionResource.getJoinedDate())
             : "");
			
			requisitionResourceDTO.setResourceRejectedDate(requestRequisitionResource.getRejectionDate()!= null
                    ? Constants.DATE_FORMAT_REPORTS.format(requestRequisitionResource.getRejectionDate())
                    : "");
            
            requisitionResourceDTO.setResourceSelectedDate(requestRequisitionResource.getShortlistedDate()!= null
                    ? Constants.DATE_FORMAT_REPORTS.format(requestRequisitionResource.getShortlistedDate())
                    : "");
			requisitionResourceDTO.setResourceRejectedDate(requestRequisitionResource.getRejectionDate()!= null
                    ? Constants.DATE_FORMAT_REPORTS.format(requestRequisitionResource.getRejectionDate())
                    : "");
            requisitionResourceDTO.setResourceSelectedDate(requestRequisitionResource.getShortlistedDate()!= null
                    ? Constants.DATE_FORMAT_REPORTS.format(requestRequisitionResource.getShortlistedDate())
                    : "");
            
            requisitionResourceDTO.setResourcePOCsubmittedDate(requestRequisitionResource.getSubmittedToPocDate()!= null
                    ? Constants.DATE_FORMAT_REPORTS.format(requestRequisitionResource.getSubmittedToPocDate())
                    : "");
            
            requisitionResourceDTO.setResourceAMsubmittedDate(requestRequisitionResource.getSubmittedToAmDate()!= null
                    ? Constants.DATE_FORMAT_REPORTS.format(requestRequisitionResource.getSubmittedToAmDate())
                    : "");
		}
		if (requestRequisitionResource.getRequestRequisitionResourceStatus().getId() == 9 || requestRequisitionResource.getRequestRequisitionResourceStatus().getId() == 14){
			requisitionResourceDTO.setAllocationDate(requestRequisitionResource.getInterviewDate()!=null? Constants.DATE_FORMAT_REPORTS.format(requestRequisitionResource.getInterviewDate()): "");
		} else if (requestRequisitionResource.getRequestRequisitionResourceStatus().getId() == 2 || requestRequisitionResource.getRequestRequisitionResourceStatus().getId() == 8) {
			requisitionResourceDTO.setAllocationDate(requestRequisitionResource.getAllocationDate()!=null? Constants.DATE_FORMAT_REPORTS.format(requestRequisitionResource.getAllocationDate()): "");
		} else {
			requisitionResourceDTO.setAllocationDate("");
		}
		
		return requisitionResourceDTO;
	}
}
