package org.yash.rms.service.impl;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yash.rms.dao.RequestRequisitionResourceDao;
import org.yash.rms.domain.Competency;
import org.yash.rms.domain.Designation;
import org.yash.rms.domain.RequestRequisitionResource;
import org.yash.rms.domain.RequestRequisitionResourceStatus;
import org.yash.rms.domain.RequestRequisitionSkill;
import org.yash.rms.domain.Resource;
import org.yash.rms.dto.RequisitionResourceDTO;
import org.yash.rms.dto.ResourceCommentDTO;
import org.yash.rms.dto.ResourceStatusDTO;
import org.yash.rms.mapper.RequestRequisitionFormMapper;
import org.yash.rms.service.RequestRequisitionDashboardService;
import org.yash.rms.service.RequestRequisitionResourceService;
import org.yash.rms.util.Constants;
import org.yash.rms.util.DateUtil;
import org.yash.rms.util.RequestRequisitionStatusConstants;

@Transactional
@Service("requestRequisitionResourceService")
public class RequestRequisitionResourceServiceImpl implements RequestRequisitionResourceService {
	
	@Autowired
	@Qualifier("requestRequisitionResourceDao")
	private RequestRequisitionResourceDao requestRequisitionResourceDao;
	
	@Autowired
	@Qualifier("requestRequisitionFormMapper")
	private RequestRequisitionFormMapper requestRequisitionFormMapper;
	
	@Autowired
	private RequestRequisitionDashboardService requestRequisitionDashboardService;
	
	private static final Logger logger = LoggerFactory.getLogger(RequestRequisitionResourceService.class);

	public List<RequisitionResourceDTO> getRequestRequisitionResources(Integer userId, String userRole,Integer skillResourcerRequisitionId, String resourceStatusIds) {
		logger.info("----------Entered into getRequestRequisitionResources-----------");
		
		List<Integer> statusList = null;
		List<RequisitionResourceDTO> requestRequisitionResourceDtoList  = null;
		
		try{
			
			if(resourceStatusIds != null && resourceStatusIds.trim().length()>0){
				statusList = new ArrayList<Integer>();
				for(String status : resourceStatusIds.split(",")){
					statusList.add(Integer.valueOf(status));
				}
			}
		
			List<RequestRequisitionResource>  requestRequisitionResourceList = convertObjToDomain(
					requestRequisitionResourceDao.getRequestRequisitionResources(userId, userRole,skillResourcerRequisitionId, statusList));
			/*List<RequestRequisitionResource>  requestRequisitionResourceList = 
					 requestRequisitionResourceDao.getRequestRequisitionResources(userId, userRole,skillResourcerRequisitionId, statusList);*/
			 RequisitionResourceDTO requisitionResourceDTO = null;
			 if(requestRequisitionResourceList !=null && requestRequisitionResourceList.size()>0){
				 requestRequisitionResourceDtoList  = new ArrayList<RequisitionResourceDTO>();
				 for(RequestRequisitionResource requestRequisitionResource : requestRequisitionResourceList){
					 requisitionResourceDTO = requestRequisitionFormMapper.covertDomainToDTO(requestRequisitionResource);
					 //Comments on RRF Submission Report
					/* requisitionResourceDTO.setAllResourceCommentByResourceId(requestRequisitionDashboardService
							 .getAllResourceCommentByResourceId(Integer.parseInt(requisitionResourceDTO.getResourceId())));*/
					 requestRequisitionResourceDtoList.add(requisitionResourceDTO);
				 }
			 }
		}catch (NumberFormatException ex) {
			logger.error("Resource status ids not in number format.");
		}
		 logger.info("----------Exit from getRequestRequisitionResources-----------");
		 return requestRequisitionResourceDtoList;
	}
	private List<RequestRequisitionResource> convertObjToDomain(List<Object[]> requisitionResourceList){
		RequestRequisitionResource  domain = null;
		List<RequestRequisitionResource> requestRequisitionResourceList  = new ArrayList<RequestRequisitionResource>(); 
		DateFormat dateFormat = new SimpleDateFormat(Constants.DATE_PATTERN_NEW);
		if(requisitionResourceList != null){
			int columnIndex = 0;
			for(Object[] row : requisitionResourceList){
				domain = new RequestRequisitionResource();
				columnIndex = 0;
				domain.setId((Integer)row[columnIndex]);

				++columnIndex;
				domain.setExternalResourceName((String)row[columnIndex]);

				RequestRequisitionSkill reqRequisitionSkill = new RequestRequisitionSkill();
				++columnIndex;
				reqRequisitionSkill.setRequirementId((String)row[columnIndex]);
				++columnIndex;
				reqRequisitionSkill.setId((Integer) row[columnIndex]);

				domain.setRequestRequisitionSkill(reqRequisitionSkill);

				RequestRequisitionResourceStatus requestRequisitionResourceStatus = new RequestRequisitionResourceStatus();
				++columnIndex;
				requestRequisitionResourceStatus.setStatusName((String)row[columnIndex]);
				++columnIndex;
				requestRequisitionResourceStatus.setId((Integer)row[columnIndex]);
				domain.setRequestRequisitionResourceStatus(requestRequisitionResourceStatus);

				++columnIndex;
				domain.setSkills((String)row[columnIndex]);

				++columnIndex;
				domain.setEmailId((String)row[columnIndex]);

				++columnIndex;
				domain.setContactNumber((String)row[columnIndex]);

				++columnIndex;
				domain.setTotalExperience((String)row[columnIndex]);

				++columnIndex;
				domain.setNoticePeriod((String)row[columnIndex]);

				++columnIndex;
				domain.setLocation((String)row[columnIndex]);

				++columnIndex;
				domain.setAllocationDate((Date)row[columnIndex]);

				++columnIndex;
				domain.setInterviewDate((Date)row[columnIndex]);

				++columnIndex;
				domain.setJoinedDate((Date)row[columnIndex]);

				++columnIndex;
				domain.setSubmittedToPocDate((Date)row[columnIndex]);

				++columnIndex;
				domain.setRejectionDate((Date)row[columnIndex]);

				++columnIndex;
				domain.setShortlistedDate((Date)row[columnIndex]);

				++columnIndex;
				domain.setSubmittedToAmDate((Date)row[columnIndex]);


				Resource resource = null;
				++columnIndex;
				if((String)row[columnIndex] != null){
					resource = new Resource();
					resource.setFirstName((String)row[columnIndex]);
					++columnIndex;
					resource.setLastName((String)row[columnIndex]);

					Competency competency = new Competency();
					++columnIndex;
					competency.setSkill((String)row[columnIndex]);
					resource.setCompetency(competency);

					++columnIndex;
					if(((BigDecimal)row[columnIndex]) != null )
						resource.setTotalExper( ((BigDecimal)row[columnIndex]).doubleValue());


					++columnIndex;
					resource.setEmailId((String)row[columnIndex]);
					++columnIndex;
					resource.setContactNumber((String)row[columnIndex]);


					++columnIndex;
					Designation designation = new Designation();
					designation.setDesignationName((String)row[columnIndex]);
					resource.setDesignationId(designation);
					++columnIndex;
					resource.setEmployeeId((Integer)row[columnIndex]);
					domain.setResource(resource);
				}
				
				requestRequisitionResourceList.add(domain);
			}
		}
		return requestRequisitionResourceList;
	}

	public List<ResourceStatusDTO> getRequestRequisitionResources(Integer userId, String userRole,Integer skillResourcerRequisitionId) {
		logger.info("----------Entered into getRequestRequisitionResources-----------");
		List<ResourceStatusDTO> resourceStatusDTOList = new ArrayList<ResourceStatusDTO>();
		try{
			List<RequestRequisitionResource>  requestRequisitionResourceList = convertObjToDomain(
					requestRequisitionResourceDao.getRequestRequisitionResources(userId, userRole,skillResourcerRequisitionId,null));
			/*List<RequestRequisitionResource>  requestRequisitionResourceList = 
					 requestRequisitionResourceDao.getRequestRequisitionResources(userId, userRole,skillResourcerRequisitionId,null);*/
			if(requestRequisitionResourceList!=null && !requestRequisitionResourceList.isEmpty()){
				 ResourceStatusDTO resourceStatusDTO = null;
				for(RequestRequisitionResource resource : requestRequisitionResourceList){
					resourceStatusDTO = convertDomainToDTO(resource);
					resourceStatusDTOList.add(resourceStatusDTO);
				}
			}
		}catch (Exception e) {
			logger.error("----------Error into getRequestRequisitionResources-----------");	
		}
		logger.info("----------Exit from getRequestRequisitionResources-----------");
		return resourceStatusDTOList;
	}
	
	private ResourceStatusDTO convertDomainToDTO(RequestRequisitionResource resource){
		ResourceStatusDTO  dto = new ResourceStatusDTO();
		if(null!=resource.getExternalResourceName()){
			dto.setResourceType("External");
		}else{
			dto.setResourceType("Internal");
			dto.setEmployeeId(null!= resource.getResource() ? resource.getResource().getEmployeeId(): 0);
		}
		if(null!=resource.getExternalResourceName()){
			dto.setResourceName(resource.getExternalResourceName());
		}else{
			dto.setResourceName(resource.getResource().getFirstName()+" "+resource.getResource().getLastName());
		}
		dto.setResourceId(resource.getId());
		dto.setAllocationType(null != resource.getAllocation() ? resource.getAllocation().getId(): 0);
		dto.setProfileStaus(null !=resource.getRequestRequisitionResourceStatus() ? resource.getRequestRequisitionResourceStatus().getId() : 0);
		dto.setAllocationStartDate(DateUtil.getDateInDD_MMM_yyyyFormat(resource.getAllocationDate()));
		dto.setInterviewDate(DateUtil.getDateInDD_MMM_yyyyFormat(resource.getInterviewDate()));
		dto.setLocation(resource.getLocation());
		dto.setNoticePeriod(resource.getNoticePeriod());
		dto.setSkills(resource.getSkills());
		dto.setEmailId(resource.getEmailId());
		dto.setContactNumber(resource.getContactNumber());
		dto.setTotalExperience(resource.getTotalExperience());
		return dto;
	}
	
	public List<ResourceCommentDTO> getLatestCommentOnResource(Integer skillRequestId){
		List<Object[]> resourceCommentList  
				= requestRequisitionResourceDao.getLatestCommentOnResource(skillRequestId);
		List<ResourceCommentDTO> resourceCommentDTOList  = convertObjectToDTO(resourceCommentList);
		return resourceCommentDTOList;
	}
	
	private List<ResourceCommentDTO> convertObjectToDTO(List<Object[]> resourceCommentList){
		ResourceCommentDTO  dto = null;
		List<ResourceCommentDTO> resourceCommentDTOList  = new ArrayList<ResourceCommentDTO>(); 
		DateFormat dateFormat = new SimpleDateFormat(Constants.DATE_PATTERN_NEW);
		if(resourceCommentList != null){
			int columnIndex = 0;
			for(Object[] row : resourceCommentList){
				dto = new ResourceCommentDTO();
				columnIndex = 0;
				dto.setResourceName((String)row[columnIndex]);
				columnIndex = ++columnIndex;
				dto.setResourceType((String)row[columnIndex]);
				columnIndex = ++columnIndex;
				dto.setResourceComment((String)row[columnIndex]);
				{	
					columnIndex = ++columnIndex;
					Date commentDate = (Date)row[columnIndex];
				    String strDate = dateFormat.format(commentDate);
					dto.setComment_Date(strDate);
				}
				columnIndex = ++columnIndex;
				dto.setCommentBy((String)row[columnIndex]);
				columnIndex = ++columnIndex;
				dto.setResourceCommentId((Integer)row[columnIndex]);
				columnIndex = ++columnIndex;
				dto.setResourceId((Integer)row[columnIndex]);
				resourceCommentDTOList.add(dto);
			}
		}
		return resourceCommentDTOList;
	}
	public RequestRequisitionResource getRequestRequisitionResourceById(Integer requestResourceId) throws Exception {
		try {
			return requestRequisitionResourceDao.getRequestResourceById(requestResourceId);
		} catch (Exception e) {
			throw e;
		}
	}
	public void mapResourceToNewRRF(int oldSkillRequestId, int newskillRequestId, String resourceIdToMap) throws NumberFormatException, Exception {
		
		try {
			RequestRequisitionResource requestRequisitionResource = getRequestRequisitionResourceById(Integer.parseInt(resourceIdToMap));
			RequestRequisitionResource requestRequisitionResourcenew = new RequestRequisitionResource();
			RequestRequisitionSkill requestRequisitionSkill = new RequestRequisitionSkill();
			
			List<RequestRequisitionSkill> requestRequisitionSkillList = requestRequisitionDashboardService.getRequestRequisitionSkillByReqSkillId(newskillRequestId);
			
			if(!requestRequisitionSkillList.isEmpty()) {
				requestRequisitionSkill = requestRequisitionSkillList.get(0);
			}
			
			if(requestRequisitionSkill!=null) {
				 requestRequisitionResourcenew = createNewResourceObjectFromRRF(requestRequisitionResource, requestRequisitionSkill);
			}

			List<RequestRequisitionResource> resources = new ArrayList<RequestRequisitionResource>();
			resources.add(requestRequisitionResourcenew);
			requestRequisitionResourceDao.updateResourceRequestWithStatus(resources);
			
			if(requestRequisitionSkill.getSubmissions()!=null) {
				requestRequisitionSkill.setSubmissions(requestRequisitionSkill.getSubmissions() + 1);
			} else {
				requestRequisitionSkill.setSubmissions(1);
			}
			
			requestRequisitionDashboardService.saveOrupdate(requestRequisitionSkill);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	private RequestRequisitionResource  createNewResourceObjectFromRRF(RequestRequisitionResource oldResource,
			RequestRequisitionSkill requestRequisitionSkill) {
		
		RequestRequisitionResource newResource = new RequestRequisitionResource();
		
		if(oldResource.getAllocation()!=null)
		newResource.setAllocation(oldResource.getAllocation());
		
		if(oldResource.getAllocationDate()!=null)
		newResource.setAllocationDate(oldResource.getAllocationDate());
		
		if(oldResource.getContactNumber()!=null)
		newResource.setContactNumber(oldResource.getContactNumber());

		newResource.setCreationTimestamp(new Date());
		
		if(oldResource.getEmailId()!=null)
		newResource.setEmailId(oldResource.getEmailId());
		
		if(oldResource.getExternalResourceName()!=null)
		newResource.setExternalResourceName(oldResource.getExternalResourceName());
		
		if(oldResource.getInterviewDate()!=null)
		newResource.setInterviewDate(oldResource.getInterviewDate());
		
		if(oldResource.getJoinedDate()!=null)
		newResource.setJoinedDate(oldResource.getJoinedDate());
		
		if(oldResource.getLocation()!=null)
		newResource.setLocation(oldResource.getLocation());
		
		if(oldResource.getNoticePeriod()!=null)
		newResource.setNoticePeriod(oldResource.getNoticePeriod());
		
		if(oldResource.getRejectionDate()!=null)
		newResource.setRejectionDate(oldResource.getRejectionDate());
		
		newResource.setRequestRequisitionResourceStatus(requestRequisitionResourceDao.getStatusByStatusName(RequestRequisitionStatusConstants.SUBMITTED_TO_POC.toString())); 
		
		if(requestRequisitionSkill!=null)
		newResource.setRequestRequisitionSkill(requestRequisitionSkill);
		
		if(oldResource.getResource()!=null)
		newResource.setResource(oldResource.getResource());
		
		if(oldResource.getResumeFileName()!=null)
		newResource.setResumeFileName(oldResource.getResumeFileName());
		
		if(oldResource.getShortlistedDate()!=null)
		newResource.setShortlistedDate(oldResource.getShortlistedDate());
		
		if(oldResource.getSkills()!=null)
		newResource.setSkills(oldResource.getSkills());
		
		if(oldResource.getSubmittedToAmDate()!=null)
		newResource.setSubmittedToAmDate(oldResource.getSubmittedToAmDate());
		
		if(oldResource.getSubmittedToPocDate()!=null)
		newResource.setSubmittedToPocDate(oldResource.getSubmittedToPocDate());
		
		if(oldResource.getTotalExperience()!=null)
		newResource.setTotalExperience(oldResource.getTotalExperience());
		
		if(oldResource.getUploadResume()!=null)
		newResource.setUploadResume(oldResource.getUploadResume());
		
		if(oldResource.getUploadTacFile()!=null)
		newResource.setUploadTacFile(oldResource.getUploadTacFile());
		
		if(oldResource.getUploadTacFileName()!=null)
		newResource.setUploadTacFileName(oldResource.getUploadTacFileName());

		return newResource;
		
	}
}
