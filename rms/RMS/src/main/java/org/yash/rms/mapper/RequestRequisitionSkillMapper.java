package org.yash.rms.mapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.yash.rms.dao.RequestRequisitionResourceDao;
import org.yash.rms.domain.AllocationType;
import org.yash.rms.domain.Competency;
import org.yash.rms.domain.Designation;
import org.yash.rms.domain.Experience;
import org.yash.rms.domain.Location;
import org.yash.rms.domain.RequestRequisitionResource;
import org.yash.rms.domain.RequestRequisitionSkill;
import org.yash.rms.domain.Resource;
import org.yash.rms.domain.Skills;
import org.yash.rms.dto.AllocationTypeDTO;
import org.yash.rms.dto.CompetencyDTO;
import org.yash.rms.dto.DesignationDTO;
import org.yash.rms.dto.LocationDTO;
import org.yash.rms.dto.RequestRequisitionSkillDTO;
import org.yash.rms.dto.SkillsDTO;
import org.yash.rms.service.RequestRequisitionDashboardService;
import org.yash.rms.service.ResourceService;
import org.yash.rms.service.impl.RequestRequisitionDashboardServiceImpl;
import org.yash.rms.util.GeneralFunction;

@Component("requestRequisitionSkillMapper")
public class RequestRequisitionSkillMapper {

	@Autowired
	@Qualifier("mapper")
	private DozerBeanMapper mapper;
	
	@Autowired
	private org.yash.rms.service.CompetencyService competencyService;
	
	@Autowired
	@Qualifier("ResourceService")
	ResourceService resourceService;
	
	@Autowired
	RequestRequisitionDashboardService requestRequisitionDashboardService ;
	
	@Autowired
	private RequestRequisitionFormMapper  requestRequisitionFormMapper;
	
	@Autowired
	@Qualifier("requestRequisitionResourceDao")
	RequestRequisitionResourceDao requestRequisitionResourceDao;
	
	public List<RequestRequisitionSkillDTO> convertDomainsToDTOs(List<org.yash.rms.domain.RequestRequisitionSkill> requestRequisitionSkill) {
		
		List<RequestRequisitionSkillDTO> requestRequisitionSkillList = new ArrayList<RequestRequisitionSkillDTO>();
		RequestRequisitionSkillDTO requestRequisitionSkillDTO = null;
		
		for (org.yash.rms.domain.RequestRequisitionSkill requestReqSkill : requestRequisitionSkill) {
		
			requestRequisitionSkillDTO = new RequestRequisitionSkillDTO();
			requestRequisitionSkillDTO.setId(requestReqSkill.getId());
			requestRequisitionSkillDTO.setComments(requestReqSkill.getComments());
			requestRequisitionSkillDTO.setAdditionalComments(requestReqSkill.getAdditionalComments());
			AllocationTypeDTO allocationDto = new AllocationTypeDTO();
			allocationDto.setAliasAllocationName(requestReqSkill.getAllocationType().getAliasAllocationName());
			mapper.map(requestReqSkill.getAllocationType(), allocationDto);
			requestRequisitionSkillDTO.setAllocationType(allocationDto);
			requestRequisitionSkillDTO.setCareerGrowthPlan(requestReqSkill.getCareerGrowthPlan());
			requestRequisitionSkillDTO.setLocation(mapper.map(requestReqSkill.getLocation(), LocationDTO.class));
			
			DesignationDTO designation = new DesignationDTO();
				designation.setId(requestReqSkill.getDesignation().getId());
				designation.setDesignationName(requestReqSkill.getDesignation().getDesignationName());
				
			requestRequisitionSkillDTO.setDesignation(designation);
			
			requestRequisitionSkillDTO.setDesirableSkills(requestReqSkill.getDesirableSkills());
			requestRequisitionSkillDTO.setExperience(requestReqSkill.getExperience());
			requestRequisitionSkillDTO.setFulfilled(requestReqSkill.getFulfilled());
			
			if(requestReqSkill.getKeyInterviewersOne() != null){
				if(!requestReqSkill.getKeyInterviewersOne().isEmpty() && !requestReqSkill.getKeyInterviewersOne().matches(".*\\d+.*"))
					requestRequisitionSkillDTO.setKeyInterviewersOne(getInterviewsInIDFormat(requestReqSkill.getKeyInterviewersOne()));
				else
					requestRequisitionSkillDTO.setKeyInterviewersOne(requestReqSkill.getKeyInterviewersOne().replace(", ", ",").replaceAll("\\[", "").replaceAll("\\]", "").trim());
			}
			
			if(requestReqSkill.getKeyInterviewersTwo() != null) {
				if(!requestReqSkill.getKeyInterviewersTwo().isEmpty() && !requestReqSkill.getKeyInterviewersTwo().matches(".*\\d+.*"))
					requestRequisitionSkillDTO.setKeyInterviewersTwo(getInterviewsInIDFormat(requestReqSkill.getKeyInterviewersTwo()));
				else
					requestRequisitionSkillDTO.setKeyInterviewersTwo(requestReqSkill.getKeyInterviewersTwo().replace(", ", ",").replaceAll("\\[", "").replaceAll("\\]", "").trim());
			}
			
			//setting key interviewers in object format - Start
			String keyInterviewers1 = StringUtils.trimToEmpty(requestReqSkill.getKeyInterviewersOne()); 
				if(!keyInterviewers1.isEmpty()) {
	                List<String> stringList = Arrays.asList(keyInterviewers1.split(","));
	                List<Integer> integerList = (List<Integer>) GeneralFunction.convertStringListToIntList(stringList);
	                List<Resource> resources1 = resourceService.findResourceByEmployeeIds(integerList);
					requestRequisitionSkillDTO.setKeyInterviewersOneList(resources1);
				}
				
			String keyInterviewers2 = StringUtils.trimToEmpty(requestReqSkill.getKeyInterviewersTwo()); 
				if(!keyInterviewers2.isEmpty()) {
	                List<String> stringList = Arrays.asList(keyInterviewers2.split(","));
	                List<Integer> integerList = (List<Integer>) GeneralFunction.convertStringListToIntList(stringList);
	                List<Resource> resources2 = resourceService.findResourceByEmployeeIds(integerList);
					requestRequisitionSkillDTO.setKeyInterviewersTwoList(resources2);
				}
			//setting key interviewers in object format - End
				
			requestRequisitionSkillDTO.setKeyScanners(requestReqSkill.getKeyScanners());
			requestRequisitionSkillDTO.setNoOfResources(requestReqSkill.getNoOfResources());
			requestRequisitionSkillDTO.setPrimarySkills(requestReqSkill.getPrimarySkills());
			requestRequisitionSkillDTO.setRemaining(requestReqSkill.getRemaining());
			requestRequisitionSkillDTO.setRequestRequisition(requestRequisitionFormMapper.convertDomainToDTO(requestReqSkill.getRequestRequisition()));
			requestRequisitionSkillDTO.setRequirementId(requestReqSkill.getRequirementId());
			requestRequisitionSkillDTO.setResponsibilities(requestReqSkill.getResponsibilities());
			
			requestRequisitionSkillDTO.setSkill(mapper.map(requestReqSkill.getSkill(), SkillsDTO.class));
			requestRequisitionSkillDTO.setTargetCompanies(requestReqSkill.getTargetCompanies());
			requestRequisitionSkillDTO.setTimeFrame(requestReqSkill.getTimeFrame());
			requestRequisitionSkillDTO.setHold(requestReqSkill.getHold());
			requestRequisitionSkillDTO.setLost(requestReqSkill.getLost());
			requestRequisitionSkillDTO.setType(requestReqSkill.getType());
			requestRequisitionSkillDTO.setSkillsToEvaluate(requestReqSkill.getSkillsToEvaluate());
			
			//Code To fetch selected RequirementArea of RRF - Start
			if(requestReqSkill.getRequirementArea()!=null)
				requestRequisitionSkillDTO.setRequirementArea(requestReqSkill.getRequirementArea());
			//Code To fetch selected RequirementArea of RRF - End
			requestRequisitionSkillList.add(requestRequisitionSkillDTO);
		}
		return requestRequisitionSkillList;
	}
	
	/**
	 * This method is for copy RRF purpose where some of the fields should be empty.
	 * Hence while mapping domain to DTO not setting some of the fields.
	 * 
	 * @param domain
	 * @return
	 */
	public RequestRequisitionSkillDTO getDTOForRRFFromDomain(RequestRequisitionSkill domain) {

		RequestRequisitionSkillDTO requestRequisitionSkillDTO = new RequestRequisitionSkillDTO();
		
		//requestRequisitionSkillDTO.setId(domain.getId());
		requestRequisitionSkillDTO.setComments(domain.getComments());
		requestRequisitionSkillDTO.setAdditionalComments(domain.getAdditionalComments());
		
		AllocationTypeDTO allocationDto = new AllocationTypeDTO();
			allocationDto.setAliasAllocationName(domain.getAllocationType().getAliasAllocationName());
			mapper.map(domain.getAllocationType(), allocationDto);
			requestRequisitionSkillDTO.setAllocationType(allocationDto);
			
		requestRequisitionSkillDTO.setCareerGrowthPlan(domain.getCareerGrowthPlan());
		requestRequisitionSkillDTO.setLocation(mapper.map(domain.getLocation(), LocationDTO.class));

		DesignationDTO designation = new DesignationDTO();
			designation.setId(domain.getDesignation().getId());
			designation.setDesignationName(domain.getDesignation().getDesignationName());
			requestRequisitionSkillDTO.setDesignation(designation);

		requestRequisitionSkillDTO.setDesirableSkills(domain.getDesirableSkills());
		requestRequisitionSkillDTO.setExperience(domain.getExperience());
		requestRequisitionSkillDTO.setFulfilled(domain.getFulfilled());

		if (domain.getKeyInterviewersOne() != null && domain.getKeyInterviewersTwo() != null) {
			if (!domain.getKeyInterviewersOne().matches(".*\\d+.*")) {
				Map<String, String> mapInterwierwers = getInterviewsInIDFormat(domain.getKeyInterviewersOne(),
						domain.getKeyInterviewersTwo());
				requestRequisitionSkillDTO.setKeyInterviewersOne(mapInterwierwers.get("round1"));
				requestRequisitionSkillDTO.setKeyInterviewersTwo(mapInterwierwers.get("round2"));
			} else {
				requestRequisitionSkillDTO.setKeyInterviewersOne(domain.getKeyInterviewersOne());
				requestRequisitionSkillDTO.setKeyInterviewersTwo(domain.getKeyInterviewersTwo());
			}
		}

		requestRequisitionSkillDTO.setKeyScanners(domain.getKeyScanners());
		//requestRequisitionSkillDTO.setNoOfResources(domain.getNoOfResources());
		requestRequisitionSkillDTO.setPrimarySkills(domain.getPrimarySkills());
		//requestRequisitionSkillDTO.setRemaining(domain.getRemaining());
		requestRequisitionSkillDTO.setRequestRequisition(requestRequisitionFormMapper.convertDomainToDTO(domain.getRequestRequisition()));
		//requestRequisitionSkillDTO.setRequirementId(domain.getRequirementId());
		requestRequisitionSkillDTO.setResponsibilities(domain.getResponsibilities());

		requestRequisitionSkillDTO.setSkill(mapper.map(domain.getSkill(), SkillsDTO.class));
		requestRequisitionSkillDTO.setTargetCompanies(domain.getTargetCompanies());
		requestRequisitionSkillDTO.setTimeFrame(domain.getTimeFrame());
		//requestRequisitionSkillDTO.setHold(domain.getHold());
		//requestRequisitionSkillDTO.setLost(domain.getLost());
		requestRequisitionSkillDTO.setType(domain.getType());
		requestRequisitionSkillDTO.setSkillsToEvaluate(domain.getSkillsToEvaluate());
	
		return requestRequisitionSkillDTO;
	}
	
	public RequestRequisitionSkill convertDTOTODomain(RequestRequisitionSkillDTO requestRequisitionSkillDto) {
		RequestRequisitionSkill requestRequisitionSkill = new RequestRequisitionSkill();
		requestRequisitionSkill.setId(requestRequisitionSkillDto.getId());
		requestRequisitionSkill.setComments(requestRequisitionSkillDto.getComments());
		requestRequisitionSkill.setAdditionalComments(requestRequisitionSkillDto.getAdditionalComments());
		requestRequisitionSkill.setAllocationType(mapper.map(requestRequisitionSkillDto.getAllocationType(), AllocationType.class));
		requestRequisitionSkill.setCareerGrowthPlan(requestRequisitionSkillDto.getCareerGrowthPlan());
		requestRequisitionSkill.setDesignation(mapper.map(requestRequisitionSkillDto.getDesignation(), Designation.class));
		requestRequisitionSkill.setDesirableSkills(requestRequisitionSkillDto.getDesirableSkills());
		requestRequisitionSkill.setExperience(requestRequisitionSkillDto.getExperience());
		requestRequisitionSkill.setFulfilled(requestRequisitionSkillDto.getFulfilled());
		requestRequisitionSkill.setKeyInterviewersOne(requestRequisitionSkillDto.getKeyInterviewersOne());
		requestRequisitionSkill.setKeyInterviewersTwo(requestRequisitionSkillDto.getKeyInterviewersTwo());
		requestRequisitionSkill.setKeyScanners(requestRequisitionSkillDto.getKeyScanners());
		requestRequisitionSkill.setNoOfResources(requestRequisitionSkillDto.getNoOfResources());
		requestRequisitionSkill.setPrimarySkills(requestRequisitionSkillDto.getPrimarySkills());
		requestRequisitionSkill.setRemaining(requestRequisitionSkillDto.getRemaining());
		requestRequisitionSkill.setRequestRequisition(requestRequisitionFormMapper.convertDTOTODomain(requestRequisitionSkillDto.getRequestRequisition()));
		requestRequisitionSkill.setRequirementId(requestRequisitionSkillDto.getRequirementId()); 
		requestRequisitionSkill.setResponsibilities(requestRequisitionSkillDto.getResponsibilities());
		requestRequisitionSkill.setSkill(mapper.map(requestRequisitionSkillDto.getSkill(), Skills.class));
		requestRequisitionSkill.setTargetCompanies(requestRequisitionSkillDto.getTargetCompanies());
		requestRequisitionSkill.setTimeFrame(requestRequisitionSkillDto.getTimeFrame());
		requestRequisitionSkill.setType(requestRequisitionSkillDto.getType());
		requestRequisitionSkill.setHold(requestRequisitionSkillDto.getHold());
		requestRequisitionSkill.setLost(requestRequisitionSkillDto.getLost());
		requestRequisitionSkill.setLocation(mapper.map(requestRequisitionSkillDto.getLocation(), Location.class));
		requestRequisitionSkill.setSkillsToEvaluate(requestRequisitionSkillDto.getSkillsToEvaluate());
		requestRequisitionSkill.setRequirementArea(requestRequisitionSkillDto.getRequirementArea());
		return requestRequisitionSkill;
	}
	
	///
	public RequestRequisitionSkill updateDomain(RequestRequisitionSkillDTO requestRequisitionSkillDto,RequestRequisitionSkill requestRequisitionSkill ) {

		requestRequisitionSkill.setComments(requestRequisitionSkillDto.getComments());
		requestRequisitionSkill.setAdditionalComments(requestRequisitionSkillDto.getAdditionalComments());
		requestRequisitionSkill.setAllocationType(mapper.map(requestRequisitionSkillDto.getAllocationType(), AllocationType.class));
		requestRequisitionSkill.setCareerGrowthPlan(requestRequisitionSkillDto.getCareerGrowthPlan());
		requestRequisitionSkill.setDesignation(mapper.map(requestRequisitionSkillDto.getDesignation(), Designation.class));
		requestRequisitionSkill.setDesirableSkills(requestRequisitionSkillDto.getDesirableSkills());
		requestRequisitionSkill.setExperience(requestRequisitionSkillDto.getExperience());
		//requestRequisitionSkill.setFulfilled(requestRequisitionSkillDto.getFulfilled());
		requestRequisitionSkill.setKeyInterviewersOne(requestRequisitionSkillDto.getKeyInterviewersOne());
		requestRequisitionSkill.setKeyInterviewersTwo(requestRequisitionSkillDto.getKeyInterviewersTwo());
		requestRequisitionSkill.setKeyScanners(requestRequisitionSkillDto.getKeyScanners());
		requestRequisitionSkill.setNoOfResources(requestRequisitionSkillDto.getNoOfResources());
		requestRequisitionSkill.setPrimarySkills(requestRequisitionSkillDto.getPrimarySkills());
		requestRequisitionSkill.setRemaining(requestRequisitionSkillDto.getRemaining());
		//requestRequisitionSkill.setRequestRequisition(requestRequisitionFormMapper.convertDTOTODomain(requestRequisitionSkillDto.getRequestRequisition()));
		requestRequisitionSkill.setRequirementId(requestRequisitionSkillDto.getRequirementId()); 
		requestRequisitionSkill.setResponsibilities(requestRequisitionSkillDto.getResponsibilities());
		requestRequisitionSkill.setSkill(mapper.map(requestRequisitionSkillDto.getSkill(), Skills.class));
		requestRequisitionSkill.setTargetCompanies(requestRequisitionSkillDto.getTargetCompanies());
		requestRequisitionSkill.setTimeFrame(requestRequisitionSkillDto.getTimeFrame());
		requestRequisitionSkill.setType(requestRequisitionSkillDto.getType());
		requestRequisitionSkill.setLocation(mapper.map(requestRequisitionSkillDto.getLocation(), Location.class));
		requestRequisitionSkill.setSkillsToEvaluate(requestRequisitionSkillDto.getSkillsToEvaluate());
		//requestRequisitionSkill.setHold(requestRequisitionSkillDto.getHold());
		//requestRequisitionSkill.setLost(requestRequisitionSkillDto.getLost());
		requestRequisitionSkill.setRequirementArea(requestRequisitionSkillDto.getRequirementArea());
		setPositionStatus(requestRequisitionSkillDto,requestRequisitionSkill);
		return requestRequisitionSkill;
	}
	
	private void setPositionStatus(RequestRequisitionSkillDTO requestRequisitionSkillDto,RequestRequisitionSkill requestRequisitionSkill){
        
        boolean isHoldLostChange = false;
        boolean positionFlag = true;
        int totalResource = requestRequisitionSkillDto.getNoOfResources();
        String noOfPositions = requestRequisitionSkillDto.getNoOfResources()+"";
        int noOfPos=requestRequisitionSkill.getNoOfResources();
		int closed=requestRequisitionSkill.getClosed();
		int fulfilled=requestRequisitionSkill.getFulfilled();
		StringBuilder posStatus = new StringBuilder();
        if(requestRequisitionSkillDto.getHold()!=null){
               totalResource = totalResource - requestRequisitionSkillDto.getHold();
               requestRequisitionSkill.setHold(requestRequisitionSkillDto.getHold());
               isHoldLostChange = true;
        }
        int hold=requestRequisitionSkill.getHold();
        
        if(requestRequisitionSkillDto.getLost()!=null){
               totalResource = totalResource - requestRequisitionSkillDto.getLost();
               requestRequisitionSkill.setLost(requestRequisitionSkillDto.getLost());
               isHoldLostChange = true;
        }
        int lost=requestRequisitionSkill.getLost();
        
        if(isHoldLostChange){
              // totalResource = totalResource -(requestRequisitionSkill.getClosed()!=null?requestRequisitionSkill.getClosed():0);
        	   totalResource = totalResource -(requestRequisitionSkill.getShortlisted()!=null?requestRequisitionSkill.getShortlisted():0);
        	   totalResource = totalResource -(requestRequisitionSkill.getClosed()!=null?requestRequisitionSkill.getClosed():0);
               if(totalResource<=0)
            	   totalResource=0;
        	   requestRequisitionSkill.setOpen(totalResource);
        }
        
        /* if (requestRequisitionSkill.getClosed() != null && noOfPositions.equals(String.valueOf(requestRequisitionSkill.getClosed()))) {
               requestRequisitionSkill.setStatus("Closed");
               positionFlag =false;
        } else if(requestRequisitionSkill.getLost() != null && noOfPositions.equals(String.valueOf(requestRequisitionSkill.getLost()))){
               requestRequisitionSkill.setStatus("Lost");
               positionFlag =false;
        }else if (requestRequisitionSkill.getHold() != null && noOfPositions.equals(String.valueOf(requestRequisitionSkill.getHold()))) {
               requestRequisitionSkill.setStatus("Hold");
               positionFlag =false;
        } 
      */
        if(noOfPos<=closed)
        {
        	requestRequisitionSkill.setStatus("Closed");
			requestRequisitionSkill.setOpen(0);
			positionFlag = false;
        }
        else if(noOfPos<=lost)
		{
			requestRequisitionSkill.setStatus("Lost");
			requestRequisitionSkill.setOpen(0);
			positionFlag = false;
		}
    	else if(noOfPos<=hold)
		{
			requestRequisitionSkill.setStatus("Hold");
			requestRequisitionSkill.setOpen(0);
			positionFlag = false;
		}
	/*	else if(closed<noOfPos&&hold<noOfPos&&lost<noOfPos&&((noOfPos<=closed+fulfilled+lost+hold)||(noOfPos<=closed+fulfilled+lost)||(noOfPos<=closed+fulfilled)))
		{
			if(!(noOfPos<+hold+closed+lost))
				posStatus.append("Fulfilled,");
			
			if(closed>0)
				posStatus.append(" Closed : "+requestRequisitionSkill.getClosed());
			if(hold>0)
				posStatus.append(", Hold : "+requestRequisitionSkill.getHold());
			if(lost>0)
				posStatus.append(", Lost : "+requestRequisitionSkill.getLost());
			
			
			if(noOfPos<=lost+hold+closed)
				requestRequisitionSkill.setOpen(0);
			
			requestRequisitionSkill.setStatus(posStatus.toString());
			positionFlag = false;
		}    */        
        
        if(requestRequisitionSkill.getStatus() == null || positionFlag){
                     boolean isOnlyOpen = true;
                     StringBuilder status = new StringBuilder();
                     if(requestRequisitionSkill.getOpen() != null && requestRequisitionSkill.getOpen() >0 ){
                            status.append(" Open : "+requestRequisitionSkill.getOpen());
                            status.append(" ");
                     }
                     if(requestRequisitionSkill.getClosed() != null && requestRequisitionSkill.getClosed() > 0){
                            isOnlyOpen = false;
                            status.append(" Closed : "+requestRequisitionSkill.getClosed());
                            status.append(" ");
                     }
                     if(requestRequisitionSkill.getHold() != null && requestRequisitionSkill.getHold() > 0){
                            isOnlyOpen = false;
                            status.append(" Hold : "+requestRequisitionSkill.getHold());
                            status.append(" ");
                     }
                     if(requestRequisitionSkill.getLost() != null && requestRequisitionSkill.getLost() > 0){
                            isOnlyOpen = false;
                            status.append(" Lost : "+requestRequisitionSkill.getLost());
                            status.append(" ");
                     }
                     if(requestRequisitionSkill.getShortlisted()!=null && requestRequisitionSkill.getShortlisted() > 0){
                            isOnlyOpen = false;
                            status.append(" Selected : "+requestRequisitionSkill.getShortlisted());
                     }
               if(isOnlyOpen){
                     requestRequisitionSkill.setStatus("Open");
               }else{
                     requestRequisitionSkill.setStatus(status.toString());
               }
        }
        if(requestRequisitionSkill.getNoOfResources() <= (requestRequisitionSkill.getClosed()+requestRequisitionSkill.getHold()+requestRequisitionSkill.getLost())) {
        	 if(requestRequisitionSkill.getClosed()!=null && requestRequisitionSkill.getClosed()>0) {
        	requestRequisitionSkill.setClosureDate(new Date());
        	List<RequestRequisitionResource> requestRequisitionResources = requestRequisitionResourceDao.getDataForAddDeleteResourcebySkillRequestID(requestRequisitionSkill.getId());
        	requestRequisitionDashboardService.sendRRFClosureMail(requestRequisitionResources, requestRequisitionSkill.getId(), requestRequisitionSkill);
        	}
        }
        else requestRequisitionSkill.setClosureDate(null);
				
 }
	
	private Map<String, String>  getInterviewsInIDFormat(String  round1, String round2){
		String keyinterviewer1String = round1;
		String keyinterviewer2String = round2;
		Map<String, String> mapOfInterviewers = new HashMap<String, String>();
		
		String[] keyInterviewers1EmailinArray =keyinterviewer1String.split(","); 
		String[] keyInterviewers2EmailinArray =keyinterviewer2String.split(","); 
		
		List<String> interviewersRound1 = new ArrayList<String>();
		List<String> interviewersRound2 = new ArrayList<String>();
		List<Integer> round1Ids = new ArrayList<Integer>();
		List<Integer> round2Ids = new ArrayList<Integer>();
		
		for (int i =0; i<keyInterviewers1EmailinArray.length ; i++) {
			interviewersRound1.add(keyInterviewers1EmailinArray[i].replaceAll("\\[", "").replaceAll("\\]", "").trim());
		}
		
		for (int i =0; i<keyInterviewers2EmailinArray.length ; i++) {
			interviewersRound2.add(keyInterviewers2EmailinArray[i].replaceAll("\\[", "").replaceAll("\\]", "").trim());
		}
		
			round1Ids = resourceService.findResourcesEmployeeIDByEmailIds(interviewersRound1);

			round2Ids = resourceService.findResourcesEmployeeIDByEmailIds(interviewersRound2);
			
			
			
			HashSet<Integer> hashSetRound1 = new HashSet<Integer>(round1Ids);
			HashSet<Integer> hashSetRound2 = new HashSet<Integer>(round2Ids);
			String interviewers1 = hashSetRound1.toString();
			String interviewers2 = hashSetRound2.toString();

			mapOfInterviewers.put("round1", interviewers1.replaceAll("\\[", "").replaceAll("\\]", "").trim());
			mapOfInterviewers.put("round2", interviewers2.replaceAll("\\[", "").replaceAll("\\]", "").trim());
	
	return mapOfInterviewers;
	}
	
	
	public String getInterviewsInIDFormat(String keyInterviewersEmail){
		String interviewers = "";
		if(keyInterviewersEmail != null && !keyInterviewersEmail.isEmpty()){
			String[] keyInterviewers1EmailinArray = keyInterviewersEmail.split(","); 
			if(keyInterviewers1EmailinArray.length < 1)
				return interviewers;
			List<String> interviewersRound1 = new ArrayList<String>();
			for (int i = 0; i<keyInterviewers1EmailinArray.length ; i++) {
				interviewersRound1.add(keyInterviewers1EmailinArray[i].replaceAll("\\[", "").replaceAll("\\]", "").trim());
			}
			if(!interviewersRound1.isEmpty()){
				List<Integer> round1Ids = resourceService.findResourcesEmployeeIDByEmailIds(interviewersRound1);
				Set<Integer> hashSetRound1 = new HashSet<Integer>(round1Ids);
				interviewers = hashSetRound1.toString().replace(", ", ",").replaceAll("\\[", "").replaceAll("\\]", "").trim();
			}
		}
		return interviewers;
	}
	
	public List<Resource> convertKeyInterviewToResourceObject(String keyInterview){
		
		List<Resource> resourcesKeyInterviewer  = new ArrayList<Resource>();
		keyInterview = StringUtils.trimToEmpty(keyInterview);
		if(!keyInterview.isEmpty()){
		
			keyInterview = keyInterview.replace(", ", ",").replaceAll("\\[", "").replaceAll("\\]", "").trim();
			List<String> stringList = Arrays.asList(keyInterview.split(","));
			if (!keyInterview.matches(".*\\d+.*")){
				resourcesKeyInterviewer = resourceService.findResourcesByEmialIds(stringList);
			}
			else{
				List<Integer> integerList = (List<Integer>) GeneralFunction.convertStringListToIntList(stringList);
				resourcesKeyInterviewer = resourceService.findResourceByEmployeeIds(integerList);
			}
		 }
		return resourcesKeyInterviewer;
	}

}
