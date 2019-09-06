package org.yash.rms.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.cxf.jaxrs.ext.search.SearchContext;
import org.dozer.DozerBeanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.yash.rms.dao.InfogramActiveResourceDao;
import org.yash.rms.domain.Competency;
import org.yash.rms.domain.Designation;
import org.yash.rms.domain.EmployeeCategory;
import org.yash.rms.domain.Grade;
import org.yash.rms.domain.InfogramActiveResource;
import org.yash.rms.domain.Location;
import org.yash.rms.domain.OrgHierarchy;
import org.yash.rms.domain.Ownership;
import org.yash.rms.domain.Resource;
import org.yash.rms.domain.ResourceAllocation;
import org.yash.rms.domain.UserActivity;
import org.yash.rms.domain.Visa;
import org.yash.rms.dto.InfogramActiveResourceDTO;
import org.yash.rms.exception.BusinessException;
import org.yash.rms.exception.DAOException;
import org.yash.rms.exception.DaoRestException;
import org.yash.rms.exception.RMSServiceException;
import org.yash.rms.exception.RestException;
import org.yash.rms.helper.EmailHelper;
import org.yash.rms.helper.ResourceHelper;
import org.yash.rms.helper.UserActivityHelper;
import org.yash.rms.rest.dao.generic.impl.HibernateGenericDao;
import org.yash.rms.rest.service.generic.AbstractService;
import org.yash.rms.rest.utils.ExceptionUtil;
import org.yash.rms.util.AppContext;
import org.yash.rms.util.Constants;
import org.yash.rms.util.DataTableParser;
import org.yash.rms.util.DozerMapperUtility;
import org.yash.rms.util.ExceptionConstant;
import org.yash.rms.util.InfogramProcessStatusConstants;
import org.yash.rms.util.ResourceUtility;
import org.yash.rms.util.SearchCriteriaGeneric;


@Service
@Transactional
public class InfogramActiveResourceServiceImpl implements InfogramActiveResourceService {
	
	@Autowired
	private InfogramActiveResourceDao infogramActiveResourceDao;
	
	@Autowired
	EmployeeCategoryService employeeCategoryService;
	
	@Autowired
	DozerMapperUtility dozerMapperUtility;
	
	@Autowired
	ResourceService resourceService;
	
	@Autowired
	ResourceAllocationService resourceAllocationService;
	
	@Autowired
	OrgHierarchyService orgHierarchyService;
	
	@Autowired
	GradeService gradeService;
	
	@Autowired
	DesignationService designationService;
	
	@Autowired
	OwnershipService ownershipService;
	
	@Autowired
	LocationService locationService;
	
	@Autowired
	CompetencyService competencyService;
	
	@Autowired
	ResourceHelper resourceHelper;
	
	@Autowired
	EmailHelper emailHelper;
	
	@Autowired
	UserActivityHelper userActivityHelper;
	
	@Autowired
	ResourceUtility resourceUtility;
	
	@Autowired
	@Qualifier("mapper")
	DozerBeanMapper mapper;

	private static final Logger logger = LoggerFactory.getLogger(InfogramActiveResourceServiceImpl.class);
	private static final String SCHEDULER_TIMER="*/10 * * * * ?";

	public List<InfogramActiveResourceDTO> getAllInfogramActiveResource() {
		logger.info("------------------getAllInfogramActiveResource method start-----------");
		
		List<InfogramActiveResourceDTO> activeResourceDTOs = new ArrayList<InfogramActiveResourceDTO>();
		
		List<InfogramActiveResource> list =  infogramActiveResourceDao.getAllInfogramActiveResources();
		
		if(!list.isEmpty()) {
			activeResourceDTOs =  dozerMapperUtility.convertInfogramActiveResourceDomainListToDTO(list,null);
		}
		
		logger.info("------------------getAllInfogramActiveResource method end-----------");
		return activeResourceDTOs;
		
	}
	
	public List<InfogramActiveResource> getAllInfogramActiveResource(String resourceType, String processStatus, String recordStatus, HttpServletRequest httpRequest) {
		logger.info("------------------getAllInfogramActiveResource for dataTable method start-----------");
		
		SearchCriteriaGeneric searchCriteriaGeneric=DataTableParser.getSerchCriteria(httpRequest,new InfogramActiveResource());
	    List<InfogramActiveResource> activeResources =  infogramActiveResourceDao.getAllInfogramActiveResources(resourceType, processStatus, recordStatus, searchCriteriaGeneric);
		logger.info("------------------getAllInfogramActiveResource for dataTable method end-----------");
		return activeResources;
		
	}

	//This will create new employee or update delta changes in the existing resource in RMS. 
	@Transactional
	public Boolean saveInfogramActiveResource(Integer id, Resource resource) throws RMSServiceException{

		logger.info("------------------saveInfogramActiveResource method start-----------");
		
		/*dao call to get infogram resource object */
		InfogramActiveResource infogramActiveResource=null;		
		Boolean isSuccess = true;
		RMSServiceException serviceException = new RMSServiceException();
		Resource mappedExistingResource = new Resource();
		try {
			infogramActiveResource = infogramActiveResourceDao.getInfogramActiveResourceById(id);
			Resource existingResource = resourceService.findResourcesByYashEmpIdEquals(infogramActiveResource.getEmployeeId());
			if(existingResource!=null) {
				//mapper.map(existingResource, mappedExistingResource);
				mappedExistingResource = map(existingResource, mappedExistingResource);
			} 
			 
			String resourceType = infogramActiveResource.getResourceType().equalsIgnoreCase("New")? "New" : "Existing";
			/* converting infogram resource object to RMS object.*/
			Resource rmsResource = new Resource();
			if(infogramActiveResource!=null){
				
			rmsResource = convertInfogramActiveResourceToRMSResource(infogramActiveResource );
			
			}  
			Resource newResourceWithInfogramChanges= rmsResource;
			
			
			
			String sentMailTo = rmsResource.getEmailId();
            String rm1Name = new String();
            String rm1EmailId = new String();
            String rm2Name = new String();
            String rm2EmailId = new String();
            String currentBU  = new String();
            String parentBU = new String();
            String userName = rmsResource.getFirstName();
            if(rmsResource.getCurrentReportingManager()!=null) {
                  rm1Name = rmsResource.getCurrentReportingManager().getFirstName() + " " + rmsResource.getCurrentReportingManager().getLastName();
                  rm1EmailId = rmsResource.getCurrentReportingManager().getEmailId();
            }
            if(rmsResource.getCurrentReportingManagerTwo()!=null) {
                  rm2Name = rmsResource.getCurrentReportingManagerTwo().getFirstName() + " " + rmsResource.getCurrentReportingManagerTwo().getLastName();
                  rm2EmailId = rmsResource.getCurrentReportingManagerTwo().getEmailId();
            }
            if(rmsResource.getCurrentBuId()!=null && rmsResource.getCurrentBuId().getParentId()!=null) {
                  currentBU = rmsResource.getCurrentBuId().getParentId().getName() + "-" + rmsResource.getCurrentBuId().getName();
            }
            if(rmsResource.getBuId()!=null && rmsResource.getBuId().getParentId()!=null) {
                  parentBU  = rmsResource.getBuId().getParentId().getName() + "-" + rmsResource.getBuId().getName();
            }

            Character userProfile = 'Y';
            String details[] = { rm1Name, rm2Name, rm1EmailId, rm2EmailId, currentBU, parentBU };


			/*service call to save or update the resource object received.*/
			if(rmsResource!=null){ 
				isSuccess = resourceService.saveOrupdate(rmsResource);
			}
			 if(isSuccess) {
				 infogramActiveResource.setProcessStatus(InfogramProcessStatusConstants.SUCCESS.toString());
				 infogramActiveResource.setModifiedTime(new Date(System.currentTimeMillis()));
				 infogramActiveResource.setModifiedBy(resource.getEmployeeId().toString());
				 String middleName=resource.getMiddleName().length()>0?resource.getMiddleName()+" ":"";
				 String name = resource.getFirstName()+" "+middleName+resource.getLastName();
				 infogramActiveResource.setModifiedName(name);
				 System.out.println(infogramActiveResource.getResourceType());
                 Map<String, Object> model = new HashMap<String, Object>();
                 if(resourceType.equalsIgnoreCase("New")) {
                	 resourceHelper.setEmailContentNewResource(model, sentMailTo, userName, null , details, userProfile);
                     emailHelper.sendEmail(model, sentMailTo);
                 } else {
                	 Map<String, List<String>> information = resourceUtility.getDeltaChangesForResource(mappedExistingResource, newResourceWithInfogramChanges);
                	 /*userActivityHelper.setEmailContentForLoanTransfer(model, rmsResource.getFirstName().concat(" ").concat(rmsResource.getLastName()) ,information,"",rmsResource,false);
                	 mailHelper.sendEmailNotification(model, toAddress,toAddresscc);*/
                 }
                 updateInfogramNonTransactional(infogramActiveResource);
				 //updateInfogramObject(infogramActiveResource);
			 } else {
				 infogramActiveResource.setProcessStatus(InfogramProcessStatusConstants.FAILURE.toString());
				 infogramActiveResource.setModifiedTime(new Date());
				 infogramActiveResource.setModifiedName(Constants.SYSTEM);
				 infogramActiveResource.setFailureReason("Error while converting infogram resource to RMS resource ");
				 updateInfogramNonTransactional(infogramActiveResource);
				// updateInfogramObject(infogramActiveResource);
			 }
		}catch (Exception exception) {
			isSuccess = false;
	           
			if(exception instanceof DAOException) {
                  serviceException.setErrCode(((DAOException) exception).getErrCode());
            }else {
                  ((RMSServiceException) serviceException).setErrCode("404");
            }
                  
            ((RMSServiceException) serviceException).setMessage(exception.getMessage());
            infogramActiveResource.setProcessStatus(InfogramProcessStatusConstants.FAILURE.toString());
            infogramActiveResource.setModifiedTime(new Date());
            infogramActiveResource.setModifiedName(Constants.SYSTEM);
			infogramActiveResource.setFailureReason("Error : "+exception.getMessage());
			InfogramActiveResourceService service= AppContext.getApplicationContext().getBean(InfogramActiveResourceService.class);
			service.updateInfogramObject(infogramActiveResource);
            throw serviceException;
			
		} 
		 
		 
		logger.info("------------------saveInfogramActiveResource method end-----------");
		return isSuccess;
	}

	
	

	private Resource map(Resource existingResource, Resource mappedExistingResource) {
		if(existingResource.getbUHName()!=null)
			mappedExistingResource.setbUHName(existingResource.getbUHName());
		if(existingResource.getOwnership()!=null)
			mappedExistingResource.setOwnership(existingResource.getOwnership());
		if(existingResource.getEmployeeCategory()!=null)
			mappedExistingResource.setEmployeeCategory(existingResource.getEmployeeCategory());
		if(existingResource.getDesignationId()!=null)
			mappedExistingResource.setDesignationId(existingResource.getDesignationId());
		if(existingResource.getGradeId()!=null)
			mappedExistingResource.setGradeId(existingResource.getGradeId());
		if(existingResource.getLocationId()!=null)
			mappedExistingResource.setLocationId(existingResource.getLocationId());
		if(existingResource.getDeploymentLocation()!=null)
			mappedExistingResource.setDeploymentLocation(existingResource.getDeploymentLocation());
		if(existingResource.getBuId()!=null)
			mappedExistingResource.setBuId(existingResource.getBuId());
		if(existingResource.getCurrentBuId()!=null)
			mappedExistingResource.setCurrentBuId(existingResource.getCurrentBuId());
		if(existingResource.getCurrentReportingManager()!=null)
			mappedExistingResource.setCurrentReportingManager(existingResource.getCurrentReportingManager());
		if(existingResource.getCurrentReportingManagerTwo()!=null)
			mappedExistingResource.setCurrentReportingManagerTwo(existingResource.getCurrentReportingManagerTwo());
		if(existingResource.getDateOfJoining()!=null)
			mappedExistingResource.setDateOfJoining(existingResource.getDateOfJoining());
		if(existingResource.getConfirmationDate()!=null)
			mappedExistingResource.setConfirmationDate(existingResource.getConfirmationDate());
		if(existingResource.getLastAppraisal()!=null)
			mappedExistingResource.setLastAppraisal(existingResource.getLastAppraisal());
		if(existingResource.getPenultimateAppraisal()!=null)
			mappedExistingResource.setPenultimateAppraisal(existingResource.getPenultimateAppraisal());
		if(existingResource.getReleaseDate()!=null)
			mappedExistingResource.setReleaseDate(existingResource.getReleaseDate());
		if(existingResource.getTransferDate()!=null)
			mappedExistingResource.setTransferDate(existingResource.getTransferDate());
		if(existingResource.getEndTransferDate()!=null)
			mappedExistingResource.setEndTransferDate(existingResource.getEndTransferDate());
		if(existingResource.getbGHComments()!=null)
			mappedExistingResource.setbGHComments(existingResource.getbGHComments());
		if(existingResource.getbGCommentsTimestamp()!=null)
			mappedExistingResource.setbGCommentsTimestamp(existingResource.getbGCommentsTimestamp());
		if(existingResource.getbGHName()!=null)
			mappedExistingResource.setbGHName(existingResource.getbGHName());
		if(existingResource.getbUHComments()!=null)
			mappedExistingResource.setbUHComments(existingResource.getbUHComments());
		if(existingResource.getbUCommentTimestamp()!=null)
			mappedExistingResource.setbUCommentTimestamp(existingResource.getbUCommentTimestamp());
		if(existingResource.gethRName()!=null)
			mappedExistingResource.sethRName(existingResource.gethRName());
		if(existingResource.gethRComments()!=null)
			mappedExistingResource.sethRComments(existingResource.gethRComments());
		if(existingResource.gethRCommentTimestamp()!=null)
			mappedExistingResource.sethRCommentTimestamp(existingResource.gethRCommentTimestamp());
		if(existingResource.getEmailId()!=null)
			mappedExistingResource.setEmailId(existingResource.getEmailId());
		if(existingResource.getEventId()!=null)
			mappedExistingResource.setEventId(existingResource.getEventId());
		if(existingResource.getEventId()!=null)
			mappedExistingResource.setEventId(existingResource.getEventId());
		if(existingResource.getEmployeeId()!=null)
			mappedExistingResource.setEmployeeId(existingResource.getEmployeeId());
		if(existingResource.getYashEmpId()!=null)
			mappedExistingResource.setYashEmpId(existingResource.getYashEmpId());
		if(existingResource.getFirstName()!=null)
			mappedExistingResource.setFirstName(existingResource.getFirstName());
		if(existingResource.getLastName()!=null)
			mappedExistingResource.setLastName(existingResource.getLastName());
		
		return mappedExistingResource;
	}

	private Resource convertInfogramActiveResourceToRMSResource(InfogramActiveResource infogramActiveResource) throws RMSServiceException {
		Resource resourceNew = new Resource();
		RMSServiceException serviceException = new RMSServiceException();
		Resource resourceAvailable =null;
		Resource resourceAvailable2=new Resource();
		try{
			 resourceAvailable = resourceService.findResourcesByYashEmpIdEquals(infogramActiveResource.getEmployeeId());
			 resourceAvailable2=resourceAvailable;
		
		if (resourceAvailable != null) {
			resourceAvailable = helperToConvertInfoDataToResource(resourceAvailable2, infogramActiveResource);
			return resourceAvailable;
		} else {
			
			//in case of creating new employee in RMS. 
			resourceNew = helperToConvertInfoDataToResource(resourceNew, infogramActiveResource);
			
			resourceNew.setYashEmpId(infogramActiveResource.getEmployeeId());
			
			//Below code is not correct; as it cause double and wrong calculation of exp fields
			/*resourceNew.setTotalExper(getTotalExperience(infogramActiveResource.getDateOfJoining()));
			
			resourceNew.setRelevantExper(getTotalExperience(infogramActiveResource.getDateOfJoining()));*/
			
			Ownership ownership = ownershipService.findById(5); //default owned.
			resourceNew.setOwnership(ownership);
			
			
			String empType = infogramActiveResource.getEmployeeType();
			if(empType!=null && !empType.isEmpty()){
				EmployeeCategory employeeCategory = employeeCategoryService.getEmployeeCategoryByName(empType);
				resourceNew.setEmployeeCategory(employeeCategory);
			}else{
				serviceException.setErrCode("404");
				serviceException.setMessage("Employee Type is not available in infogram");
				throw serviceException;
			}

			Competency competency = competencyService.findById(4); // setting not available by default. 
			resourceNew.setCompetency(competency);
			
			/* Default value for visa would be no visa(enhancements in next phase)*/
			Visa visa = new Visa();
			visa.setId(1);
			resourceNew.setVisaId(visa);
			
			return resourceNew;
		}
		}catch (Exception exception) {
	           
			if(exception instanceof DAOException) {
                  serviceException.setErrCode(((DAOException) exception).getErrCode());
            }else {
                  ((RMSServiceException) serviceException).setErrCode("404");
            }
                  
            ((RMSServiceException) serviceException).setMessage(exception.getMessage());
            throw serviceException;
			
		}

	}

	private double getTotalExperience(Date dateOfJoining) {
		return Double.parseDouble(Resource.getCalYearDiff(dateOfJoining, new Date()));
	}

	private OrgHierarchy getBUIdForResourceFromBGBUName(String businessGroup, String businessUnit) {
		OrgHierarchy bgObject = orgHierarchyService.getOrgHierarchyByName(businessGroup);
		OrgHierarchy bgBuObject = orgHierarchyService.getOrgHierarchyByBgIdBuName(bgObject.getId(),businessUnit );
		
		return bgBuObject;
	}

	public void saveEditedInfogramActiveResource(InfogramActiveResourceDTO infogramActiveResourceDTO) throws RMSServiceException {
		List<InfogramActiveResourceDTO> dto = new ArrayList<InfogramActiveResourceDTO>();
		dto.add(infogramActiveResourceDTO);
		RMSServiceException serviceException = new RMSServiceException();
		List<InfogramActiveResource> infoDomain =  dozerMapperUtility.convertInfogramActiveResourceDTOListToDomainList(dto);
		Resource resource;
		try {
			resource = convertInfogramActiveResourceToRMSResource(infoDomain.get(0));
			if(resource!=null){
				logger.info("Inside Resource save update");
				resourceService.saveOrupdate(resource);
			}
		} catch (Exception exception) {
            if(exception instanceof DAOException) {
                  serviceException.setErrCode(((DAOException) exception).getErrCode());
            }else {
                  ((RMSServiceException) serviceException).setErrCode("404");
            }
                  
            ((RMSServiceException) serviceException).setMessage(exception.getMessage());
            throw serviceException;
		}
		
	}

	private Resource helperToConvertInfoDataToResource(Resource resource, InfogramActiveResource infogramActiveResource) throws RMSServiceException {
		RMSServiceException serviceException = new RMSServiceException();
		try{
				
		String  bgId = infogramActiveResource.getBusinessGroup();
		String  buId = infogramActiveResource.getBusinessUnit();
		if(bgId!=null && buId!=null &&!bgId.isEmpty()&&!buId.isEmpty()){
			OrgHierarchy bgbuId = getBUIdForResourceFromBGBUName(infogramActiveResource.getBusinessGroup(),infogramActiveResource.getBusinessUnit());
			if (null != bgbuId) {
				resource.setBuId(bgbuId);
				resource.setCurrentBuId(bgbuId);
			}
			else{
				serviceException.setErrCode("404");
				serviceException.setMessage(ExceptionConstant.RMS_BG_BU_NOT_FOUND);
				throw serviceException;
			}
		}else{
			serviceException.setErrCode("404");
			serviceException.setMessage(ExceptionConstant.BG_BU_NOT_FOUND);
			throw serviceException;
		}
		
		/* Setting date of Joining */
		
		Date dateOfJoining = infogramActiveResource.getDateOfJoining();
		if(dateOfJoining!=null){
			resource.setDateOfJoining(infogramActiveResource.getDateOfJoining());
		}else{
			serviceException.setErrCode("404");
			serviceException.setMessage(ExceptionConstant.DOJ_NOT_FOUND);
			throw serviceException;
		}

		/* Setting the grade Object through Grade name */
		String infoGramGrade = infogramActiveResource.getGrade();
		if(infoGramGrade!=null && !infoGramGrade.isEmpty()){
			Grade grade = gradeService.findByName(infoGramGrade);
			if (null != grade) {
				resource.setGradeId(grade);
			}else{
				serviceException.setErrCode("404");
				serviceException.setMessage(ExceptionConstant.RMS_GRADE_NOT_FOUND);
				throw serviceException;
			}
		}else{
			serviceException.setErrCode("404");
			serviceException.setMessage(ExceptionConstant.GRADE_NOT_FOUND);
			throw serviceException;
		}
			
		
		
		/* Base location for the resource*/
		
		String baseLocation = infogramActiveResource.getBaseLocation();
		if(baseLocation!=null &&!baseLocation.isEmpty()){
			Location location = locationService.findLocationByName(baseLocation);
			if (null != location) {
				resource.setLocationId(location);  //base location refers to location id in rms
			}
			else{
				serviceException.setErrCode("404");
				serviceException.setMessage(ExceptionConstant.RMS_BASELOC_NOT_FOUND);
				throw serviceException;
			}
		}else{
			serviceException.setErrCode("404");
			serviceException.setMessage(ExceptionConstant.BASELOC_NOT_FOUND);
			throw serviceException;
		}
		
		
		String currentLocation = infogramActiveResource.getCurrentLocation();
		if(currentLocation!=null && !currentLocation.isEmpty()){
			Location location = locationService.findLocationByName(currentLocation);
			if (null != location) {
				resource.setDeploymentLocation(location);
			}
			else{
				serviceException.setErrCode("404");
			    serviceException.setMessage(ExceptionConstant.RMS_CURRENTLOC_NOT_FOUND);
				throw serviceException;
			}
		}else{
			serviceException.setErrCode("404");
			serviceException.setMessage(ExceptionConstant.CURRENTLOC_NOT_FOUND);
			throw serviceException;
		}
		

		/* Setting the Designation Object through Designation name */
		String infoGramDesignation = infogramActiveResource.getDesignation();
		infoGramDesignation=infoGramDesignation.trim();
		if(infoGramDesignation!=null && !infoGramDesignation.isEmpty()){
			Designation designation = designationService.findByNameIgnoreSpace(infoGramDesignation);
			if (null != designation) {
				resource.setDesignationId(designation);
			}else{
				serviceException.setErrCode("404");
				serviceException.setMessage(ExceptionConstant.RMS_DESIGNATION_NOT_FOUND);
				throw serviceException;
				}
			}else{
			serviceException.setErrCode("404");
			serviceException.setMessage(ExceptionConstant.DESIGNATION_NOT_FOUND);
			throw serviceException;
		}
		
		//resource.setEmailId(infogramActiveResource.getEmailId());
		String mailid = infogramActiveResource.getEmailId();
		if(mailid!=null&&!mailid.isEmpty()){
			resource.setEmailId(infogramActiveResource.getEmailId());
		}else{
			serviceException.setErrCode("404");
			serviceException.setMessage(ExceptionConstant.EMAILID_NOT_FOUND);
			throw serviceException;
		}

		String irmId = infogramActiveResource.getIrmEmployeeId();
		if(irmId!=null && !irmId.isEmpty()){
			Resource irm = resourceService.findResourcesByYashEmpIdEquals(irmId);
			if (null != irm) {
				resource.setCurrentReportingManager(irm);
			}else{
				serviceException.setErrCode("404");
				serviceException.setMessage(ExceptionConstant.RMS_IRM_NOT_FOUND);
				throw serviceException;
				}
		}else{
			serviceException.setErrCode("404");
			serviceException.setMessage(ExceptionConstant.IRM_NOT_FOUND);
			throw serviceException;
		}

		String srmId = infogramActiveResource.getSrmEmployeeId();
		if(srmId!=null && !srmId.isEmpty()){
			Resource srm = resourceService.findResourcesByYashEmpIdEquals(srmId);
			if (null != srm) {
				resource.setCurrentReportingManagerTwo(srm);
			}else{
				serviceException.setErrCode("404");
				serviceException.setMessage(ExceptionConstant.RMS_SRM_NOT_FOUND);
				throw serviceException;
				}
			
		}else{
			serviceException.setErrCode("404");
			serviceException.setMessage(ExceptionConstant.SRM_NOT_FOUND);
			throw serviceException;
		}

		String hrbpId = infogramActiveResource.getHrbpEmployeeId();
		if(hrbpId!=null && !hrbpId.isEmpty()){
			Resource hrbp = resourceService.findResourcesByEmailIdEquals(hrbpId);
			if (null != hrbp) {
				resource.sethRName(hrbp);
			}
		}

		String firstName = infogramActiveResource.getFirstName();
		if(firstName!=null && !firstName.isEmpty()){
			resource.setFirstName(infogramActiveResource.getFirstName());
			
		}else{
			serviceException.setErrCode("404");
			serviceException.setMessage(ExceptionConstant.FNAME_NOT_FOUND);
			throw serviceException;
		}

		/* Resource middle name, may or may not be there */
		if (infogramActiveResource.getMiddleName() != null && !infogramActiveResource.getMiddleName().isEmpty()) {
			resource.setMiddleName(infogramActiveResource.getMiddleName());
		} else{
			resource.setMiddleName("");
		}
		
		String lastName = infogramActiveResource.getLastName();
		if(lastName!=null && !lastName.isEmpty()){
			resource.setLastName(lastName);
		}else{
			serviceException.setErrCode("404");
			serviceException.setMessage(ExceptionConstant.LNAME_NOT_FOUND);
			throw serviceException;
		}
		}catch(Exception exception) {
            if(exception instanceof DAOException) {
                  serviceException.setErrCode(((DAOException) exception).getErrCode());
            }else {
                  ((RMSServiceException) serviceException).setErrCode("404");
            }
                  
            ((RMSServiceException) serviceException).setMessage(exception.getMessage());
            throw serviceException;

		}
		return resource;
	}
	
	
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void updateInfogramObject(InfogramActiveResource infogramActiveResource) throws RMSServiceException{
		//InfogramActiveResource object = infogramActiveResourceDao.getInfogramActiveResourceById(infogramActiveResource.getId().toString());
		try{
				infogramActiveResourceDao.updateInfogramObject(infogramActiveResource);
		}
		catch(DAOException exception){
			throw new RMSServiceException(exception.getErrCode(), exception.getMessage());
		}
	}
	
	public void updateInfogramNonTransactional(InfogramActiveResource infogramActiveResource) throws RMSServiceException{
		//InfogramActiveResource object = infogramActiveResourceDao.getInfogramActiveResourceById(infogramActiveResource.getId().toString());
		try{
				infogramActiveResourceDao.updateInfogramObject(infogramActiveResource);
		}
		catch(DAOException exception){
			throw new RMSServiceException(exception.getErrCode(), exception.getMessage());
		}
	}
	
	
	
	
	@Transactional
	public void discardInfogramResourceById(Integer id, Resource resource ) throws RMSServiceException {
		InfogramActiveResource infogramActiveResource=null;
		
		try {
				infogramActiveResource=infogramActiveResourceDao.getInfogramActiveResourceById(id);
				if(infogramActiveResource!=null){
					infogramActiveResource.setModifiedBy(resource.getEmployeeId().toString());
					String middleName=resource.getMiddleName().length()>0?resource.getMiddleName()+" ":"";
					String name = resource.getFirstName()+" "+middleName+resource.getLastName();
					infogramActiveResource.setModifiedName(name);
					infogramActiveResource.setModifiedTime(new Date(System.currentTimeMillis()));
					infogramActiveResource.setProcessStatus(InfogramProcessStatusConstants.DISCARD.toString());
					infogramActiveResourceDao.updateInfogramObject(infogramActiveResource);
				}
				else
				{
					throw new RMSServiceException("404","Employee not found for Employee Id in Infogram ");
				}
		}
		catch(DAOException exception) {
            throw new RMSServiceException(exception.getErrCode(),exception.getMessage());
		}
	}
	
	public Long getTotalCountInfogramResource(String status,String processStatus,HttpServletRequest httpRequest) throws Exception{
		Long count = 0L;
		SearchCriteriaGeneric searchCriteriaGeneric=DataTableParser.getSerchCriteria(httpRequest,new InfogramActiveResource());
		try {
		count =  infogramActiveResourceDao.getTotalCountInfogramResource(status,processStatus,searchCriteriaGeneric);
		
		}catch (Exception e) {

			count = 0L;
			e.printStackTrace();
			logger.error("Exception Occurred while counting resources in infogram and yash "+ e.getMessage());
			throw e;
		}
		return count;
	}
	
	
	public List<InfogramActiveResource> getAllInfogramActiveResources(){
		return infogramActiveResourceDao.getAllInfogramActiveResources();
	}
	
	

	
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public void updateProcessStatus(Integer id)
	{
		logger.info("updateProcessStatus method Start ");
		InfogramActiveResource infogramActiveResource=null;
	
		try{
			infogramActiveResource= infogramActiveResourceDao.getInfogramActiveResourceById(id);
			infogramActiveResource.setModifiedName(Constants.SYSTEM);
			infogramActiveResource.setModifiedTime(new Date());
			infogramActiveResource.setProcessStatus("DISCARD");
			logger.info("Updated Duplicate record........."+infogramActiveResource.getEmployeeId());
			infogramActiveResourceDao.updateInfogramObject(infogramActiveResource);
		}
		catch(DAOException ex)
		{
			logger.error("Excption occurred in updateProcessStatus : "+ex.getMessage());
		}
		logger.info("updateProcessStatus method End ");
		
		
	}
		public List<InfogramActiveResourceDTO> getInfogramActiveResourceReport() {
		logger.info("InfogramActiveResourceServiceImpl :: getInfogramActiveResourceReport [Start] ");
		List<InfogramActiveResourceDTO> activeResourceList =  infogramActiveResourceDao.getAllInfogramActiveResourcesReport();
		logger.info("InfogramActiveResourceServiceImpl getInfogramActiveResourceReport [END] ");
		return activeResourceList;
	}


	public InfogramActiveResource findById(Integer id) {

		return infogramActiveResourceDao.getInfogramActiveResourceById(id);
	}
	
		
	/**
	 * method to Finding list of roles for user by fiql.
	 *
	 * @param ctx the ctx
	 * @param maxLimit the max limit
	 * @param minLimit the min limit
	 * @param orderby the orderby
	 * @param orderType the order type
	 * @return roles
	 * @throws BusinessException the business exception
	 */
	
	public List<InfogramActiveResource> searchWithLimitAndOrderBy(SearchContext ctx,Integer maxLimit, Integer minLimit, String orderby, String orderType) throws BusinessException{
	logger.info("InfogramActiveResourceServiceImpl :: searchWithLimitAndOrderBy - start");
	try{
		return infogramActiveResourceDao.searchWithLimitAndOrderBy(ctx, maxLimit, minLimit, orderby, orderType);
		}catch(Exception ex)
		{
		logger.error("Error Inside  @class :"+this.getClass().getName()+" @Method :searchWithLimitAndOrderBy()"+ex.getMessage());
			
		throw new BusinessException(ExceptionUtil.generateExceptionCode("Service","InfogramActiveResource",ex));	
		}
	}
	public Long count(SearchContext ctx) throws BusinessException {
		logger.info("InfogramActiveResourceServiceImpl :: count - start");
		try{
			
			return infogramActiveResourceDao.count(ctx);
			}catch(Exception ex)
			{
			logger.error("Error Inside  @class :"+this.getClass().getName()+" @Method :count()"+ex.getMessage());
				
			throw new BusinessException(ExceptionUtil.generateExceptionCode("Service","InfogramActiveResource",ex));	
			}
	}
	
	
	
	
	
	
	public List<InfogramActiveResource> searchWithLimit(SearchContext context, Integer maxLimit, Integer minLimit)
			throws BusinessException {
		try{
			return infogramActiveResourceDao.searchWithLimit(context, maxLimit, minLimit);
		}catch(Exception ex)
		{
			logger.error("Error Inside  @class :"+this.getClass().getName()+" @Method :searchWithLimit"+ex.getMessage());
			throw new BusinessException(ExceptionUtil.generateExceptionCode("Service","InfogramActiveResource",ex));
		}
		
	}

	
	public List<InfogramActiveResource> search(InfogramActiveResource entity) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Transactional
	public InfogramActiveResource create(InfogramActiveResource infogramActiveResource) throws Exception {
		// TODO Auto-generated method stub
	return	infogramActiveResourceDao.create(infogramActiveResource);
	}
	
	@Transactional
	public InfogramActiveResource update(InfogramActiveResource infogramActiveResource) throws Exception {
		// TODO Auto-generated method stub
	return	infogramActiveResourceDao.update(infogramActiveResource);
	}

	@Transactional
	public void removeById(Integer primaryKey) throws BusinessException {
		try {
		infogramActiveResourceDao.deleteByPk(primaryKey);
		}catch(DaoRestException ex)
		{
			throw new BusinessException(ex);
		}
	}
	
}
