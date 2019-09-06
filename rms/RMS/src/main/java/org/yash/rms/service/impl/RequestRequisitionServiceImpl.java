package org.yash.rms.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.dozer.DozerBeanMapper;
import org.hibernate.validator.util.privilegedactions.GetClassLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.yash.rms.dao.RequestDao;
import org.yash.rms.dao.RequestRequisitionDao;
import org.yash.rms.dao.RequestRequisitionResourceDao;
import org.yash.rms.dao.RequestRequisitionSkillDao;
import org.yash.rms.domain.AllocationType;
import org.yash.rms.domain.Customer;
import org.yash.rms.domain.CustomerGroup;
import org.yash.rms.domain.KeyGenerator;
import org.yash.rms.domain.Location;
import org.yash.rms.domain.RequestRequisition;
import org.yash.rms.domain.RequestRequisitionResource;
import org.yash.rms.domain.RequestRequisitionSkill;
import org.yash.rms.domain.Resource;
import org.yash.rms.dto.CustomerDTO;
import org.yash.rms.dto.EditProfileDTO;
import org.yash.rms.dto.ProjectDTO;
import org.yash.rms.dto.RequestRequisitionFormDTO;
import org.yash.rms.dto.RequestRequisitionSkillDTO;
import org.yash.rms.exception.DAOException;
import org.yash.rms.exception.RMSServiceException;
import org.yash.rms.helper.EmailHelper;
import org.yash.rms.helper.ResourceHelper;
import org.yash.rms.mapper.RequestRequisitionFormMapper;
import org.yash.rms.mapper.RequestRequisitionSkillMapper;
import org.yash.rms.service.AllocationTypeService;
import org.yash.rms.service.CustomerGroupService;
import org.yash.rms.service.CustomerService;
import org.yash.rms.service.KeyGeneratorService;
import org.yash.rms.service.LocationService;
import org.yash.rms.service.ProjectAllocationService;
import org.yash.rms.service.RequestRequisitionService;
import org.yash.rms.service.ResourceService;
import org.yash.rms.util.Constants;
import org.yash.rms.util.DataTableParser;
import org.yash.rms.util.DozerMapperUtility;
import org.yash.rms.util.GeneralFunction;
import org.yash.rms.util.SearchCriteriaGeneric;
import org.yash.rms.util.UserUtil;

import javassist.NotFoundException;

@Service("RequestRequisitionService")
public class RequestRequisitionServiceImpl implements RequestRequisitionService {
	
	private static final Logger logger = LoggerFactory.getLogger(RequestRequisitionServiceImpl.class);

	@Autowired
	@Qualifier("RequestDao")
	RequestDao requestDao;
	
	@Autowired
	ResourceHelper resourceHelper;
	
	@Autowired
	LocationService locationService;

	@Autowired
	EmailHelper emailHelper;
	
	@Autowired
	@Qualifier("CustomerService")
	CustomerService customerService;

	@Autowired
	@Qualifier("CustomerGroupService")
	CustomerGroupService customerGroupService;
	
	@Autowired
	RequestRequisitionSkillDao requestRequisitionSkillDao;
	
	@Autowired
	RequestRequisitionDao requestRequisitionDao;
	
	@Autowired
	private RequestRequisitionSkillMapper requestRequisitionSkillMapper;
	
	@Autowired
	private RequestRequisitionFormMapper requestRequisitionFormMapper;
	
	@Autowired
	private RequestRequisitionResourceDao requestRequisitionResourceDao;
	@Autowired
	private DozerMapperUtility dozerMapperUtility;
	
	@Autowired
	@Qualifier("allocatioTypeService")
	AllocationTypeService allocationTypeService;

	@Autowired
	ResourceService resourceService;
	
	
	@Autowired
	@Qualifier("mapper")
	DozerBeanMapper mapper;
	
	@Autowired
	@Qualifier("ProjectAllocationService")
	ProjectAllocationService projectAllocationService;
	
	@Autowired
	@Qualifier("keyGeneratorService")
	private KeyGeneratorService keyGeneratorService;
	

	public List getLastAddedRequestID() {
		// TODO Auto-generated method stub
		return requestDao.getLastAddedRequestID();
	}

	@Transactional(propagation=Propagation.REQUIRES_NEW)
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
			List<String> keyScanners, Integer projectId, String sentMailTo,String notifyto, String skillRequestId, String requestId, Integer customerName, List<String> reqIdList) {
		// TODO Auto-generated method stub
		
		/*
		 * Convert DTO to RequestRequisitionDomain
		 * Convert DTO to RequestRequisitionSkillDomain
		 * 
		 * call RequestRequisitionDao.save
		 * call RequestRequisitionSkillDao.save in loop
		 * 
		 */
		return requestDao.saveRequest(currentResource, currentBU, projID,
				skillList, resourceNo, designationList, experience, allocationTypeList, type,
				time, date, comments, primarySkills, desirableSkills,
				responsibilities, targetCompanies, keyInterviewersOneText,
				keyInterviewersTwoText, additionalComments, careerGrowthPlan,
				keyScanners, projectId, sentMailTo, notifyto,skillRequestId,requestId, customerName,reqIdList);
	}
	
	public int getUpdatedId() {
		return requestDao.getUpdatedId();
	}

	public File createAttachment(Map<String, Object> model, File file) {
		return requestDao.createAttachment(model,file);
	}
	
	public File createTempFile(String prefix, String suffix){
		return requestDao.createTempFile(prefix, suffix);
	}
	public RequestRequisition getReqById(Integer id) {
		return requestDao.getReqById(id);
	}
	public List<RequestRequisitionSkill> findRequestRequisitionSkillsByRequestRequisitionId(int requestRequisitionId){
		return requestRequisitionSkillDao.findRequestRequisitionSkillsByRequestRequisitionId(requestRequisitionId);
	}

	public RequestRequisition findRequestRequisitionById(Integer requestRequisitionId) {
		return requestRequisitionDao.findRequestRequisitionById(requestRequisitionId);
	}
	
	public RequestRequisitionSkillDTO getRequestRecordForEdit(Integer id) {
		//RequestRequisitionSkill requestRequisitionSkill = (RequestRequisitionSkill) requestRequisitionSkillDao.editReqId(id).get(0);
		List<RequestRequisitionSkill> requestRequisitionSkill =  requestRequisitionSkillDao.editReqId(id);
		List<RequestRequisitionSkillDTO> requestRequisitionSkillDTOs = requestRequisitionSkillMapper.convertDomainsToDTOs(requestRequisitionSkill);
		return requestRequisitionSkillDTOs.get(0);
		//return mapper.map(requestRequisitionSkill, RequestRequisitionSkillDTO.class);
	}
	
	public RequestRequisitionSkillDTO getRequestRecordForCopy (Integer id) {
		//RequestRequisitionSkill requestRequisitionSkill = (RequestRequisitionSkill) requestRequisitionSkillDao.editReqId(id).get(0);
		List<RequestRequisitionSkill> requestRequisitionSkill =  requestRequisitionSkillDao.editReqId(id);
		List<RequestRequisitionSkillDTO> requestRequisitionSkillDTOs = requestRequisitionSkillMapper.convertDomainsToDTOs(requestRequisitionSkill);
		return requestRequisitionSkillDTOs.get(0);
		//return mapper.map(requestRequisitionSkill, RequestRequisitionSkillDTO.class);
	}

	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public String saveRequest(RequestRequisitionFormDTO requestRequisitionFormDTO) {
		
		RequestRequisition requestRequisition = requestRequisitionFormMapper.convertDTOTODomain(requestRequisitionFormDTO.getRequestRequisitionDto());
			
		requestRequisitionDao.save(requestRequisition);
			String reqId = new String();
		//requestRequisitionFormDTO.setRequestRequisitionDto(requestRequisitionFormMapper.convertDomainToDTO(requestRequisition));
		requestRequisitionFormDTO.getRequestRequisitionDto().setId(requestRequisition.getId());
		
		for(RequestRequisitionSkillDTO requestRequisitionSkillDto : requestRequisitionFormDTO.getRequestRequisitionSkillDto()) {
			requestRequisitionSkillDto.setRequestRequisition(requestRequisitionFormDTO.getRequestRequisitionDto());
			RequestRequisitionSkill requestRequisitionSkill = requestRequisitionSkillMapper.convertDTOTODomain(requestRequisitionSkillDto);
			requestRequisitionSkill.setRemaining(requestRequisitionSkill.getNoOfResources().toString());
			requestRequisitionSkill.setFulfilled(0);
			requestRequisitionSkill.setStatus(getPositionStatus(requestRequisitionSkill));
			reqId = requestRequisitionSkillDao.save(requestRequisitionSkill);
			
			requestRequisitionSkillDto.setId(requestRequisitionSkill.getId());
			
		}		
		
		return reqId;
	}
	
	//New Method for update
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public Integer updateRequisitionRequestSkill(RequestRequisitionFormDTO requestRequisitionFormDTO) {
		
		RequestRequisition requestRequisition = requestRequisitionDao.findRequestRequisitionById(requestRequisitionFormDTO.getRequestRequisitionDto().getId());
		
		requestRequisitionFormMapper.updateDomanin(requestRequisitionFormDTO.getRequestRequisitionDto(), requestRequisition);
			
		requestRequisitionDao.save(requestRequisition);
			
		//requestRequisitionFormDTO.setRequestRequisitionDto(requestRequisitionFormMapper.convertDomainToDTO(requestRequisition));
		requestRequisitionFormDTO.getRequestRequisitionDto().setId(requestRequisition.getId());
		
		RequestRequisitionSkill requestRequisitionSkill;
		
		for(RequestRequisitionSkillDTO requestRequisitionSkillDto : requestRequisitionFormDTO.getRequestRequisitionSkillDto()) {
		
			requestRequisitionSkill = requestRequisitionSkillDao.findRequestRequisitionSkillBySkillId(requestRequisitionSkillDto.getId());
			
			//requestRequisitionSkillDto.setRequestRequisition(requestRequisitionFormDTO.getRequestRequisitionDto());
			requestRequisitionSkill = requestRequisitionSkillMapper.updateDomain(requestRequisitionSkillDto, requestRequisitionSkill);
			
			//If HOLD and LOST have not increased then everything remains same
			
			
			
			requestRequisitionSkillDao.save(requestRequisitionSkill);
			
			requestRequisitionSkillDto.setId(requestRequisitionSkill.getId());
		}		
		
		return requestRequisition.getId();
	}
	
	
	
	@Transactional(propagation=Propagation.REQUIRES_NEW, isolation=Isolation.READ_COMMITTED)
	public RequestRequisitionFormDTO getRequestRequisitionFormDTO(Integer requestRequisitionId) {
		
		List<RequestRequisitionSkill> requestRequisitionSkills = requestRequisitionSkillDao.findRequestRequisitionSkillsByRequestRequisitionId(requestRequisitionId);
		
		RequestRequisitionFormDTO requestRequisitionFormDTO = new RequestRequisitionFormDTO();
				requestRequisitionFormDTO.setRequestRequisitionSkillDto(requestRequisitionSkillMapper.convertDomainsToDTOs(requestRequisitionSkills));
		
		return requestRequisitionFormDTO;
		
	}
		
	public void sendEmail(RequestRequisitionFormDTO requestRequisitionFormDTO, boolean holdFlag, boolean lostFlag, boolean update, boolean copyFlag) {
		
		RequestRequisitionSkill requestRequistionSkill = null;
		List<RequestRequisitionSkill> requestRequistionSkills = requestRequisitionSkillDao.findRequestRequisitionSkillsByRequestRequisitionId(requestRequisitionFormDTO.getRequestRequisitionDto().getId());
		
		for (RequestRequisitionSkill skillReq : requestRequistionSkills) {
			
			requestRequistionSkill = (RequestRequisitionSkill) requestRequisitionSkillDao.editReqId(skillReq.getId()).get(0);
			RequestRequisition requestRequisition = requestRequistionSkill.getRequestRequisition();
			AllocationType allocationType = new AllocationType();
			Resource deliveryPOC = new Resource();
			Location location = new Location();
			EditProfileDTO requestor = new EditProfileDTO();
			CustomerDTO customerDTO = new CustomerDTO();
			
			if(requestRequistionSkill.getAllocationType()!=null)
			 allocationType =allocationTypeService.getAllocationTypeById(requestRequistionSkill.getAllocationType().getId());
			
			if(requestRequisition.getDeliveryPOC()!=null)
			 deliveryPOC=resourceService.find(requestRequisition.getDeliveryPOC().getEmployeeId());
			
			if(requestRequistionSkill.getLocation()!=null)
			 location=locationService.findById(requestRequistionSkill.getLocation().getId());
			
			if(requestRequisitionFormDTO.getRequestRequisitionDto()!=null)
			{
				if(requestRequisitionFormDTO.getRequestRequisitionDto().getRequestor()!=null)
				{
					requestor =  resourceService.getResourceDetailsByEmployeeId(requestRequisitionFormDTO.getRequestRequisitionDto().getRequestor().getEmployeeId());
			
				}
			}
			UserUtil userUtil = new UserUtil();
			Resource loggedInResource=userUtil.getLoggedInResource();
			requestRequistionSkill.setAllocationType(allocationType);
			requestRequistionSkill.setLocation(location);
			Map<String, Object> model = new HashMap<String, Object>();
			
			// new code for adding attachment in mail
			Map<String, Object> modelMap = new HashMap<String, Object>();
				modelMap.put("dataList", requestRequistionSkill);
				modelMap.put("requestorObject", requestor);
			String pocName=deliveryPOC.getFirstName()+" "+deliveryPOC.getLastName();
				
			File tempFile = createTempFile("RRF", ".pdf");
			
			System.out.println("Temp file : " + tempFile.getAbsolutePath());
			
			tempFile = createAttachment(modelMap, tempFile);
			// end

			/*List<RequestRequisitionSkill> skills = new ArrayList<RequestRequisitionSkill>();
					skills.add(requestRequistionSkill);*/
			
		//	String name = skills.get(0).getSkill().getSkill();
			if(requestRequisitionFormDTO.getRequestRequisitionDto()!=null)
				if(requestRequisitionFormDTO.getRequestRequisitionDto().getCustomer()!=null)
					customerDTO = customerService.findCustomer(requestRequisitionFormDTO.getRequestRequisitionDto().getCustomer().getId());
			
			CustomerGroup customerGroup = new CustomerGroup();
			if(null != requestRequisitionFormDTO.getRequestRequisitionDto()){
				if(requestRequisitionFormDTO.getRequestRequisitionDto().getGroup()!=null)
				 customerGroup = customerGroupService.findByGroupId(requestRequisitionFormDTO.getRequestRequisitionDto().getGroup().getGroupId());
			}else {
				customerGroup.setCustomerGroupName("Not Available");
			}
			
			
			String requestorName = requestor.getFirstName().concat(" ").concat(requestor.getLastName());
			
			//String experience =  new String();
			String pocBU=deliveryPOC.getCurrentBuId().getParentId().getName()+"-"+deliveryPOC.getCurrentBuId().getName();
			String projectBU = requestRequisitionFormDTO.getRequestRequisitionDto().getProjectBU()!=null && !requestRequisitionFormDTO.getRequestRequisitionDto().getProjectBU().isEmpty() ? requestRequisitionFormDTO.getRequestRequisitionDto().getProjectBU() : "NA";
			String rmgPoc = StringUtils.trimToEmpty(requestRequisition.getRmgPoc()); 
			String tecTeamPoc = StringUtils.trimToEmpty(requestRequisition.getTecTeamPoc());
			
			if(!rmgPoc.isEmpty()) {
				List<String> stringList = Arrays.asList(rmgPoc.split(","));
				List<Integer> integerList = (List<Integer>) GeneralFunction.convertStringListToIntList(stringList);
				List<Resource> resources = resourceService.findResourceByEmployeeIds(integerList);
				List<String> rmgPocList = new ArrayList<String>();
				Iterator<Resource> iterator = resources.iterator();
				while(iterator.hasNext()){
					Resource resource2 =  (Resource) iterator.next();
					StringBuilder str = new StringBuilder();
					rmgPocList.add(str.append(resource2.getFirstName()).append(" ").append(resource2.getLastName()).append(" [")
					.append(resource2.getYashEmpId()).append("]").toString());
				}
				rmgPoc = StringUtils.join(rmgPocList, " ,  ");
			}
			
			if(!tecTeamPoc.isEmpty()) {
				List<String> stringList = Arrays.asList(tecTeamPoc.split(","));
				List<Integer> integerList = (List<Integer>) GeneralFunction.convertStringListToIntList(stringList);
				List<Resource> resources = resourceService.findResourceByEmployeeIds(integerList);
				List<String> tecPocList = new ArrayList<String>();
				Iterator<Resource> iterator = resources.iterator();
				while(iterator.hasNext()){
					Resource resource2 =  (Resource) iterator.next();
					StringBuilder str = new StringBuilder();
					tecPocList.add(str.append(resource2.getFirstName()).append(" ").append(resource2.getLastName()).append(" [")
					.append(resource2.getYashEmpId()).append("]").toString());
				}
				tecTeamPoc = StringUtils.join(tecPocList, " ,  ");
			}
			String requirementArea = requestRequistionSkill.getRequirementArea();
			resourceHelper.setEmailContentForRequest(model, requestRequisitionFormDTO.getRequestRequisitionDto().getToMailIds(), requestRequisitionFormDTO.getRequestRequisitionDto().getCcMailIds(), requestorName, requestRequisitionFormDTO.getRequestRequisitionDto().getProject().getProjectName(),
						projectBU, requestRequisitionFormDTO.getRequestRequisitionDto().getSendMailFrom(), requestRequistionSkills, requestRequisitionFormDTO.getRequestRequisitionDto().getComments(),requestRequisition.getAmJobCode(),
						customerDTO.getCustomerName(), requestRequistionSkill.getRequirementId() , customerGroup.getCustomerGroupName(),pocName,requestRequistionSkill.getHold(),holdFlag, requestRequistionSkill.getLost(),lostFlag,update,requestRequistionSkill.getNoOfResources(),location.getLocation(),pocBU, 
						requestRequisition.getResource(),requestRequisition.getSuccessFactorId(), requirementArea, rmgPoc,tecTeamPoc,null, loggedInResource);
				
			emailHelper.sendEmail(model, requestRequisitionFormDTO.getRequestRequisitionDto().getToMailIds(), requestRequisitionFormDTO.getRequestRequisitionDto().getCcMailIds(), tempFile, requestRequistionSkill.getRequirementId());
			
			
		}
	}
	
	private String getPositionStatus(RequestRequisitionSkill requestRequisitionSkill) {
		List<RequestRequisitionResource> requestRequisitionResourceList = requestRequisitionResourceDao.getSelectedResourceByRequestRequisitionSkillId(requestRequisitionSkill.getId());
		RequestRequisitionSkill requestRequisitionSkillTemp  =  requestRequisitionSkillDao.findRequestRequisitionSkillBySkillId(requestRequisitionSkill.getId());
		
		if(requestRequisitionSkillTemp != null ){
			requestRequisitionSkillTemp.setLost(requestRequisitionSkill.getLost());
			requestRequisitionSkillTemp.setHold(requestRequisitionSkill.getHold());
			
			// set the value back in RequestRequisitionSkill object
			requestRequisitionSkill.setClosed(requestRequisitionSkillTemp.getClosed());
			requestRequisitionSkill.setFulfilled(requestRequisitionSkillTemp.getFulfilled());
			requestRequisitionSkill.setSubmissions(requestRequisitionSkillTemp.getSubmissions());
			requestRequisitionSkill.setShortlisted(requestRequisitionSkillTemp.getShortlisted());
			requestRequisitionSkill.setNotFitForRequirement(requestRequisitionSkillTemp.getNotFitForRequirement());
			if(requestRequisitionSkillTemp.getOpen() != null){
				requestRequisitionSkill.setOpen(
					requestRequisitionSkill.getNoOfResources()-
					(requestRequisitionSkillTemp.getClosed()==null?0:requestRequisitionSkillTemp.getClosed()));
				requestRequisitionSkillTemp.setOpen(requestRequisitionSkill.getOpen());
			}else{
				requestRequisitionSkill.setOpen(requestRequisitionSkill.getNoOfResources());
			}
			
			int selected = 0;
			if(requestRequisitionResourceList !=null){
				selected = requestRequisitionResourceList.size();
			}
			String noOfPositions = String.valueOf(requestRequisitionSkill.getNoOfResources());
			boolean positionFlag = true;
			
			if (requestRequisitionSkillTemp.getClosed() != null && noOfPositions.equals(String.valueOf(requestRequisitionSkillTemp.getClosed()))) {
				requestRequisitionSkillTemp.setStatus("Closed");
				positionFlag =false;
			}else if(requestRequisitionSkillTemp.getLost() != null && noOfPositions.equals(String.valueOf(requestRequisitionSkillTemp.getLost()))){
				requestRequisitionSkillTemp.setStatus("Lost");
				positionFlag =false;
			}else if (requestRequisitionSkillTemp.getHold() != null && noOfPositions.equals(String.valueOf(requestRequisitionSkillTemp.getHold()))) {
				requestRequisitionSkillTemp.setStatus("Hold");
				positionFlag =false;
			}else if (selected > 0 && noOfPositions.equals(String.valueOf(selected))) {
				requestRequisitionSkillTemp.setStatus("Fulfilled");
				positionFlag =false;
			}			
			
			if(requestRequisitionSkillTemp.getStatus() == null || positionFlag){
					boolean isOnlyOpen = true;
					StringBuilder status = new StringBuilder();
					if(requestRequisitionSkillTemp.getOpen() != null && requestRequisitionSkillTemp.getOpen() > 0){
						status.append(" Open : "+requestRequisitionSkillTemp.getOpen());
						status.append(" ");
					}else{
						status.append(" Open : "+requestRequisitionSkillTemp.getNoOfResources());
						status.append(" ");
					}
					if(requestRequisitionSkillTemp.getClosed() != null && requestRequisitionSkillTemp.getClosed() > 0){
						isOnlyOpen = false;
						status.append(" Closed : "+requestRequisitionSkillTemp.getClosed());
						status.append(" ");
					}
					if(requestRequisitionSkillTemp.getHold() != null && requestRequisitionSkillTemp.getHold() > 0){
						isOnlyOpen = false;
						status.append(" Hold : "+requestRequisitionSkillTemp.getHold());
						status.append(" ");
					}
					if(requestRequisitionSkillTemp.getLost() != null && requestRequisitionSkillTemp.getLost() > 0){
						isOnlyOpen = false;
						status.append(" Lost : "+requestRequisitionSkillTemp.getLost());
						status.append(" ");
					}
					if(selected > 0){
						isOnlyOpen = false;
						status.append(" Selected : "+selected);
					}
				if(isOnlyOpen){
					requestRequisitionSkillTemp.setStatus("Open");
				}else{
					requestRequisitionSkillTemp.setStatus(status.toString());
				}
			}
			return requestRequisitionSkillTemp.getStatus();
		}else{
			StringBuilder status = new StringBuilder();
			
			requestRequisitionSkill.setOpen(requestRequisitionSkill.getNoOfResources());
			requestRequisitionSkill.setSubmissions(0);
			requestRequisitionSkill.setShortlisted(0);
			requestRequisitionSkill.setNotFitForRequirement(0);
			requestRequisitionSkill.setClosed(0);
			
			boolean isOnlyOpen= true;
			if(requestRequisitionSkill.getNoOfResources() != null && requestRequisitionSkill.getNoOfResources()  > 0){
				status.append(" Open : "+requestRequisitionSkill.getNoOfResources());
				status.append(" ");
			}			
			if(requestRequisitionSkill.getHold() != null && requestRequisitionSkill.getHold() > 0){
				isOnlyOpen =false;
				status.append(" Hold : "+requestRequisitionSkill.getHold());
				status.append(" ");
			}
			if(requestRequisitionSkill.getLost() != null && requestRequisitionSkill.getLost() > 0){
				isOnlyOpen = false;
				status.append(" Lost : "+requestRequisitionSkill.getLost());
				status.append(" ");
			}
			if(isOnlyOpen){
				status = new StringBuilder();;
				status.append("Open");
			}
			return status.toString();
		}
	}

	public void saveOrUpdateRRF(RequestRequisitionFormDTO requestRequisitionFormDTO) {
		// TODO Auto-generated method stub
		
	}

	public void saveSkillRequestRequisition(RequestRequisitionFormDTO requestRequisitionFormDTO,
			RequestRequisition requestRequisition) {
		// TODO Auto-generated method stub
		
	}
	
	public List<CustomerDTO> getRequestRequisitionsCustomer(Integer userId, String userRole) {
		List <ProjectDTO> projectDTOList =  projectAllocationService.getProjectsForUser(userId, userRole, "active");
		List<Integer> projectList = null;
		if(projectDTOList != null){
			projectList = new ArrayList<Integer>();
			for(ProjectDTO projectDTO : projectDTOList){
				projectList.add(projectDTO.getProjectId());
			}
		}
		List<Customer> customerDomainList = requestRequisitionDao.getRequestRequisitionsCustomer(userId, userRole,projectList);
		List<CustomerDTO> customerDTOList =  dozerMapperUtility.convertCustomerDomainListToDTOList(customerDomainList);
		return customerDTOList;
	}
	
	@Transactional(isolation=Isolation.READ_COMMITTED)
	public String createRRFRequirementId(final String type) throws Exception {
		
			KeyGenerator keyGenerator;
			RMSServiceException rmsServiceException = null;
			try {
				keyGenerator = this.getMinimumRRFTokenId(type);
				if (keyGenerator != null) {
					long value  = keyGenerator.getValue() , digitCount=0;
					while(value > 0) {
						value = value / 10;
						digitCount = digitCount++; 
					}
					if(digitCount > 5)
					 return type.concat("-").concat(String.valueOf(keyGenerator.getValue()));
					else
					  return type.concat("-").concat(String.format("%05d", keyGenerator.getValue()));
				} else {
					rmsServiceException = new RMSServiceException("404", "RRF Request Id does not exist, first create Request Id");
					throw rmsServiceException;
				}
			} catch (Exception ex) {
				logger.error("----------Exception occured in createRRFRequirementId -----------" + ex.getMessage());
				ex.printStackTrace();
				throw ex;
			}
	}
	
	private synchronized KeyGenerator getMinimumRRFTokenId(final String type) throws Exception {
	
		KeyGenerator keyGenerator = keyGeneratorService.findKeyByStatusType(type, Constants.UNASSIGNED);
		if(keyGenerator == null) {
			// create tokens in a range and then find minimum token id
			Long valueId = keyGeneratorService.findMaxAssginedValue();
			if(valueId == null || valueId == 0L )
				valueId = 0L;

			long minValue = valueId.longValue()+1L; ;
			long maxValue = minValue+Constants.TOKEN_INCREAENT_ORDER;
			keyGeneratorService.generateToken(type, Constants.UNASSIGNED,minValue,maxValue);
			keyGenerator = keyGeneratorService.findKeyByStatusType(type, Constants.UNASSIGNED);
		}
		
		if(keyGenerator != null)
		 {
			keyGenerator.setStatus(Constants.ASSIGNED);
			keyGeneratorService.saveOrUpdate(keyGenerator);
		 }
		return keyGenerator;
	}

	@Transactional
	public boolean saveOrupdate(RequestRequisition requestRequisition) throws Exception {
		if(requestRequisition == null)
			return false;
		try{
			boolean isUpdated = requestRequisitionDao.saveOrupdate(requestRequisition);
			return isUpdated;
		}
		catch(Exception ex)
		{
			if(ex instanceof DAOException) 
			  throw new RMSServiceException("416",ex.getMessage());
			else
				throw ex;
		}
	}
	
	public List<RequestRequisitionSkill> findRRFByProjectId(Integer id){
		
		List<RequestRequisitionSkill> requestRequisitionSkills= new ArrayList<RequestRequisitionSkill>();
		
		List<RequestRequisitionSkill> updatedList= new ArrayList<RequestRequisitionSkill>();
		String statusRRF="";
		requestRequisitionSkills= requestRequisitionDao.findRRFByProjectId(id);
		
		for (RequestRequisitionSkill requestRequisitionSkill : requestRequisitionSkills) {
			statusRRF="";
			statusRRF=findRRFStatus(requestRequisitionSkill);
			
			if(!statusRRF.equalsIgnoreCase(Constants.CLOSED)){
				requestRequisitionSkill.setStatus(statusRRF);
				updatedList.add(requestRequisitionSkill);
			}
				
		}
		
		return updatedList;
	}
	
	public String findRRFStatus(RequestRequisitionSkill requestRequisitionSkill)
	{
		String status="";
		Integer position=0;
		Integer open =0;
		Integer closed =0;
		Integer hold =0;
		Integer lost =0;
		Integer shortlisted =0;
		

		position=requestRequisitionSkill.getNoOfResources();
		open=requestRequisitionSkill.getOpen();
		closed=requestRequisitionSkill.getClosed();
		hold=requestRequisitionSkill.getHold();
		lost=requestRequisitionSkill.getLost();
		shortlisted=requestRequisitionSkill.getShortlisted();
  		  
  		 if(position - (hold + lost + shortlisted + closed) > 0){
  			status = "OPEN"; 
  		 }else if((position - lost) == 0){
  			status = "LOST";
  		 }else if(((position -hold) == 0) || ((position-(hold+lost) == 0)) && (hold != 0)){
  			status = "HOLD";
  		 }else if((position - (hold + lost + closed)) <= 0 ){
  			status = "CLOSED";
  		}else if((position - (hold + lost + shortlisted + closed)) <= 0){
  			status = "FULLFILLED";
  		}
  		 
  		 return status;
	}
}
