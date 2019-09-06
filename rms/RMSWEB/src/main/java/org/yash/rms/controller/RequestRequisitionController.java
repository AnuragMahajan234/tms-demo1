package org.yash.rms.controller;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.dozer.DozerBeanMapper;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.yash.rms.domain.ClientType;
import org.yash.rms.domain.Customer;
import org.yash.rms.domain.CustomerGroup;
import org.yash.rms.domain.Designation;
import org.yash.rms.domain.Location;
import org.yash.rms.domain.PDLEmailGroup;
import org.yash.rms.domain.Project;
import org.yash.rms.domain.RequestRequisitionSkill;
import org.yash.rms.domain.Resource;
import org.yash.rms.domain.ShiftTypes;
import org.yash.rms.domain.Skills;
import org.yash.rms.dto.AllocationTypeDTO;
import org.yash.rms.dto.DesignationDTO;
import org.yash.rms.dto.EditProfileDTO;
import org.yash.rms.dto.LocationDTO;
import org.yash.rms.dto.PriorityDTO;
import org.yash.rms.dto.ReasonForReplacementDTO;
import org.yash.rms.dto.RequestRequisitionDTO;
import org.yash.rms.dto.RequestRequisitionFormDTO;
import org.yash.rms.dto.RequestRequisitionInformationDTO;
import org.yash.rms.dto.RequestRequisitionSkillDTO;
import org.yash.rms.exception.ControllerException;
import org.yash.rms.exception.RMSServiceException;
import org.yash.rms.dto.SkillsDTO;
import org.yash.rms.helper.EmailHelper;
import org.yash.rms.helper.ResourceHelper;
import org.yash.rms.json.mapper.JsonObjectMapper;
import org.yash.rms.mapper.RequestRequisitionSkillMapper;
import org.yash.rms.service.AllocationTypeService;
import org.yash.rms.service.ClientTypeService;
import org.yash.rms.service.CompetencyService;
import org.yash.rms.service.CustomerGroupService;
import org.yash.rms.service.CustomerService;
import org.yash.rms.service.DesignationService;
import org.yash.rms.service.ExperienceService;
import org.yash.rms.service.OrgHierarchyService;
import org.yash.rms.service.PDLEmailGroupService;
import org.yash.rms.service.PriorityService;
import org.yash.rms.service.ProjectService;
import org.yash.rms.service.ProjectTypeService;
import org.yash.rms.service.RequestRequisitionDashboardService;
import org.yash.rms.service.RequestRequisitionService;
import org.yash.rms.service.ResourceRequiredTypeService;
import org.yash.rms.service.ResourceService;
import org.yash.rms.service.RmsCRUDService;
import org.yash.rms.service.ShiftTypesService;
import org.yash.rms.service.SkillsService;
import org.yash.rms.util.Constants;
import org.yash.rms.util.ExceptionConstant;
import org.yash.rms.util.GenericSearch;
import org.yash.rms.util.UserUtil;

import flexjson.JSONDeserializer;

@Controller
@RequestMapping("/requests")
public class RequestRequisitionController {

	@Autowired
	@Qualifier("ResourceService")
	ResourceService resourceService;

	@Autowired
	@Qualifier("ProjectService")
	ProjectService projectService;

	@Autowired
	@Qualifier("CustomerGroupService")
	CustomerGroupService customerGroupService;

	@Autowired
	@Qualifier("SkillsService")
	SkillsService skillsService;

	@Autowired
	@Qualifier("RequestRequisitionService")
	RequestRequisitionService requestRequisitionService;

	@Autowired
	@Qualifier("CustomerService")
	CustomerService customerService;

	@Autowired
	@Qualifier("DesignationService")
	DesignationService designationService;

	@Autowired
	ResourceHelper resourceHelper;

	@Autowired
	EmailHelper emailHelper;

	@Autowired
	JsonObjectMapper<RequestRequisitionSkill> jsonMapper;
	@Autowired
	@Qualifier("allocatioTypeService")
	AllocationTypeService allocationTypeService;

	@Autowired
	private UserUtil userUtil;
	@Autowired
	@Qualifier("CompetencyService")
	CompetencyService competencyService;

	@Autowired
	RequestRequisitionDashboardService requestRequisitionDashboardService;
	@Autowired
	RequestRequisitionSkillMapper requestRequisitionSkillMapper;

	@Autowired
	PDLEmailGroupService pdlEmailGroupService;

	/*@Autowired @Qualifier("gradeService")
	RmsCRUDService<Grade> gradeServiceImpl;*/
	
	@Autowired @Qualifier("LocationService")
	RmsCRUDService<Location> locationService;
	
	@Autowired
	ExperienceService experienceService;
	
	@Autowired
	ShiftTypesService shiftTypesService;
	
	@Autowired
	ProjectTypeService projectTypeService;
	
	@Autowired
	ClientTypeService clientTypeService;
	
	@Autowired
	@Qualifier("OrgHierarchyService")
	OrgHierarchyService buService;
	
	@Autowired
	ResourceRequiredTypeService requiredTypeService;

	@Autowired
	@Qualifier("mapper")
	DozerBeanMapper mapper;
	
	@Autowired
	org.yash.rms.service.ReasonForReplacementService reasonForReplacementService;
	
	@Autowired
	PriorityService priorityService;
	
	@Autowired
	GenericSearch genericSearch;
	
	private static final Logger logger = LoggerFactory.getLogger(RequestRequisitionController.class);

	@RequestMapping(method = RequestMethod.GET)
	public String getRequestRequisition(Model uiModel, HttpServletRequest request) throws Exception {
		
		//Long startTime = System.currentTimeMillis();
		logger.info("------RequestRequisitionController getRequestRequisition method start------");
		
		try {
			Integer currentLoggedInUserId = UserUtil.getCurrentResource().getEmployeeId();
		//	String currentLoggedInUserName = UserUtil.getCurrentResource().getEmployeeName();

			Resource currentResource = resourceService.find(currentLoggedInUserId);
			//String currentBU = currentResource.getCurrentBuId().getParentId().getName() + "-" + currentResource.getCurrentBuId().getName();
		
			//List<Resource> activeUserList = resourceService.findActiveResources();
			List<Designation> designation = designationService.findAll();
			//List<PDLEmailGroup> pdlList = pdlEmailGroupService.getPdlEmails();
			//For Get Only Active PDL Email for RRF PDL Mail --(Arun)
			List<PDLEmailGroup> pdlList = pdlEmailGroupService.getActivePdlEmails();
			List<ClientType> clientTypes = clientTypeService.getAllClientTypes();
				
			List<Resource> mailList = new ArrayList<Resource>();
				//mailList.addAll(activeUserList);
			
			//uiModel.addAttribute("ActiveUserList", activeUserList);
			uiModel.addAttribute(Constants.LOCATION, locationService.findAll());
			uiModel.addAttribute(Constants.BUS, buService.findAllBu());
			uiModel.addAttribute("pdlList", pdlList);
			uiModel.addAttribute("priorities", priorityService.getAllPriorityTypes());
			uiModel.addAttribute("projectTypeList", projectTypeService.getAllProjectTypes());
			uiModel.addAttribute("clientTypeList",clientTypes );
			String userNameLoggedInResource=currentResource.getUserName();
			//Resource currentLoggedInResource = resourceService.getCurrentResource(userNameLoggedInResource);
			/*uiModel.addAttribute("loggedInUser", currentLoggedInResource);*/
			uiModel.addAttribute(Constants.CURRENT_LOGGED_IN_RESOURCE, currentResource);
		//	uiModel.addAttribute("raisedBy", currentLoggedInUserName);
			//uiModel.addAttribute(Constants.RESOURCE_UNIT, currentBU);
			uiModel.addAttribute("resourceRequiredFor", requiredTypeService.getResourceRequiredTypes());
			uiModel.addAttribute("allCustomers", customerService.findAll());
			uiModel.addAttribute(Constants.DESIGNATION, designation);
			uiModel.addAttribute(Constants.MAIL_LIST, mailList);
			uiModel.addAttribute(Constants.RESOURCE_ALLOCATION, allocationTypeService.getActiveAllocationTypesForRRF());   
			uiModel.addAttribute(Constants.SECONDRY_SKILLS, competencyService.findAll());
			uiModel.addAttribute("experienceList", experienceService.getAllExperience());
			uiModel.addAttribute("shiftTypeList", shiftTypesService.getAllShiftTypes());
			uiModel.addAttribute("skillsList", skillsService.findPrimarySkills());
			uiModel.addAttribute("reasonList", reasonForReplacementService.getAllReasons());
			uiModel.addAttribute("copyRRFFlag", false);
			/*uiModel.addAttribute("RMGPocList", activeUserList);
			uiModel.addAttribute("tecTeamPocList", activeUserList);*/
			String reqId = request.getParameter("reqId");
			String copyFlag = request.getParameter("copyflag");
			
			String skillRequestIdToCopy = request.getParameter("skillId");
			if (null != reqId ) {
				uiModel.addAttribute("copyRRFFlag", false);
				RequestRequisitionSkillDTO req = requestRequisitionService.getRequestRecordForEdit(Integer.parseInt(reqId));

				uiModel.addAttribute("requirementName", req.getRequirementId() );
				uiModel.addAttribute("requestRequisitionSkillId", reqId);
				uiModel.addAttribute("dataList", req);
				uiModel.addAttribute("othersForExp", req.getRequestRequisition().getExpOtherDetails());
				uiModel.addAttribute(Constants.CURRENT_LOGGED_IN_RESOURCE, req.getRequestRequisition().getResource().getEmployeeName());
				uiModel.addAttribute("buttonFlag", true);// Boolean to show buttons Update and Cancel button
				uiModel.addAttribute("copyFlag", copyFlag); // not a boolean copyFlagTrue - for copy.
				uiModel.addAttribute("requestId", reqId);
				uiModel.addAttribute("requestorDetails", getEmployeeDetailsById(req.getRequestRequisition().getRequestor().getEmployeeId()));
				
				if (req.getRequestRequisition().getCustomer() != null) {
					
					List<Project> prjList = (projectService.findProjectNamesByClientId(new Integer(req.getRequestRequisition().getCustomer().getId())));
					uiModel.addAttribute(Constants.ACTIVE_PROJECTS_OF_BU, prjList);
					
					if (req.getRequestRequisition().getGroup() != null) {
						
						List<CustomerGroup> groupList = (customerGroupService.findById(new Integer(req.getRequestRequisition().getCustomer().getId())));
						uiModel.addAttribute("groupList", groupList);
					}
				}
			} 
			
			
			if(copyFlag.equalsIgnoreCase("copyflagtrue")) {
				RequestRequisitionSkillDTO requestRequisitionSkill = requestRequisitionDashboardService
						.copyRRFBySkillId(Integer.parseInt(skillRequestIdToCopy));
				if (requestRequisitionSkill != null) {
					uiModel.addAttribute("copyRRFData", requestRequisitionSkill);
					uiModel.addAttribute("copyRRFFlag", true);
				} 
			}
			// Set Header values...

			Resource loggedInResource = userUtil.getLoggedInResource();
			try {
				if (loggedInResource.getUploadImage() != null && loggedInResource.getUploadImage().length > 0) {
					byte[] encodeBase64UserImage = Base64.encodeBase64(loggedInResource.getUploadImage());
					String base64EncodedUser = new String(encodeBase64UserImage, "UTF-8");
					uiModel.addAttribute("UserImage", base64EncodedUser);
				} else {
					uiModel.addAttribute("UserImage", "");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			uiModel.addAttribute("firstName", loggedInResource.getFirstName() + " " + loggedInResource.getLastName());
			uiModel.addAttribute("designationName", loggedInResource.getDesignationId().getDesignationName());
			
			Calendar cal = Calendar.getInstance();
				cal.setTime(loggedInResource.getDateOfJoining());
			
			int m = cal.get(Calendar.MONTH) + 1;
			String months = new DateFormatSymbols().getMonths()[m - 1];
			int year = cal.get(Calendar.YEAR);
			
			uiModel.addAttribute("DOJ", months + "-" + year);
			uiModel.addAttribute("ROLE", loggedInResource.getUserRole());
			
			logger.info("------RequestRequisitionController getRequestRequisition method end------");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//return "requests/list";
		return "requests/RequestRequisitionForm";
	}

	private RequestRequisitionFormDTO convertRequestParamToDTO(String requestId, String[] skillName, String[] tomailIDs,
			String[] ccmailIDs, Integer projectId, String projectName, String customerName, Integer customerId, String groupId,String groupName,
			List<Map<String, Object>> requestRequisitionSkillList, String[] pdlList) {

		ListIterator<Map<String, Object>> iterator = requestRequisitionSkillList.listIterator();
		String comments = null;
		List<Integer> skillList = new ArrayList<Integer>();

		List<RequestRequisitionSkillDTO> requestRequisitionSkillDTOs = new ArrayList();
		RequestRequisitionSkillDTO requestRequisitionSkillDTO = null;
		RequestRequisitionDTO requestRequisitionDTO = new RequestRequisitionDTO();
		while (iterator.hasNext()) {
			requestRequisitionSkillDTO = new RequestRequisitionSkillDTO();
			Map<String, Object> map = (Map<String, Object>) iterator.next();
			Set requestSet = map.entrySet();
			Iterator requestIterator = requestSet.iterator();
			while (requestIterator.hasNext()) {
				Map.Entry mapEntry = (Map.Entry) requestIterator.next();
				
				if(mapEntry.getKey().equals("workLocationIds")) {
					LocationDTO location = new LocationDTO();
					location.setId(Integer.parseInt(mapEntry.getValue().toString()));
					requestRequisitionSkillDTO.setLocation(location);
				}
				
				if (mapEntry.getKey().equals("skillRequestId")) {
					// Integer.parseInt(skillRequestId)>0
					requestRequisitionSkillDTO.setId(Integer.parseInt(mapEntry.getValue().toString()));
				}
				//Code To set selected RequirementArea of RRF - Start
				if (mapEntry.getKey().equals("requirementAreaId")) {
					requestRequisitionSkillDTO.setRequirementArea(mapEntry.getValue().toString());
				}
				//Code To set selected RequirementArea of RRF - End
				if (mapEntry.getKey().equals("comments")) {
					requestRequisitionDTO.setComments(mapEntry.getValue().toString());
				} else if (mapEntry.getKey().equals("skill")) {
					SkillsDTO skill = new SkillsDTO();
					skill.setId(Integer.parseInt(mapEntry.getValue().toString()));
					requestRequisitionSkillDTO.setSkill(skill);
				} else if (mapEntry.getKey().equals("noOfResources")) {
					requestRequisitionSkillDTO.setNoOfResources(Integer.parseInt(mapEntry.getValue().toString()));
				} else if (mapEntry.getKey().equals("designation")) {
					DesignationDTO designation = new DesignationDTO();
					designation.setId(Integer.parseInt(mapEntry.getValue().toString()));
					requestRequisitionSkillDTO.setDesignation(designation);
				} else if (mapEntry.getKey().equals("experience")) {
					requestRequisitionSkillDTO.setExperience(mapEntry.getValue().toString());
				} else if (mapEntry.getKey().equals("billable")) {
					AllocationTypeDTO allocationType = new AllocationTypeDTO();
					allocationType.setId(Integer.parseInt(mapEntry.getValue().toString()));
					requestRequisitionSkillDTO.setAllocationType(allocationType);

				} else if (mapEntry.getKey().equals("type")) {
					requestRequisitionSkillDTO.setType(Integer.parseInt(mapEntry.getValue().toString()));

				} else if (mapEntry.getKey().equals("reqId")) {
					requestRequisitionSkillDTO.setRequirementId(mapEntry.getValue().toString());

				} else if (mapEntry.getKey().equals("hold")) {

					if (!mapEntry.getValue().toString().isEmpty()) {

						requestRequisitionSkillDTO.setHold(Integer.parseInt(mapEntry.getValue().toString()));

					} else {

						requestRequisitionSkillDTO.setHold(0);
					}

				} else if (mapEntry.getKey().equals("lost")) {

					if (!mapEntry.getValue().toString().isEmpty()) {

						requestRequisitionSkillDTO.setLost(Integer.parseInt(mapEntry.getValue().toString()));

					} else {

						requestRequisitionSkillDTO.setLost(0);
					}

				}else if (mapEntry.getKey().equals("secondaryskillName")) {

					String allSkillRecord = mapEntry.getValue().toString();

					JSONObject jsonObject = new JSONObject(allSkillRecord);
					if (jsonObject.get("primarySkills") != null) {
						requestRequisitionSkillDTO.setPrimarySkills((String) jsonObject.get("primarySkills"));
					}
					if (jsonObject.get("desirableSkills") != null) {
						requestRequisitionSkillDTO.setDesirableSkills((String) jsonObject.get("desirableSkills"));
					}

					if (jsonObject.get("responsibilities") != null) {
						requestRequisitionSkillDTO.setResponsibilities((String) jsonObject.get("responsibilities"));
					}
					if (jsonObject.get("timeFrame") != null) {
						requestRequisitionSkillDTO.setTimeFrame((String) jsonObject.get("timeFrame"));
					}

					if (jsonObject.get("careerGrowthPlan") != null) {
						requestRequisitionSkillDTO.setCareerGrowthPlan((String) jsonObject.get("careerGrowthPlan"));
					}
					if (jsonObject.get("keyScanners") != null) {
						requestRequisitionSkillDTO.setKeyScanners((String) jsonObject.get("keyScanners"));
					}
					if (jsonObject.get("targetCompanies") != null) {
						requestRequisitionSkillDTO.setTargetCompanies((String) jsonObject.get("targetCompanies"));
					}

					if (jsonObject.get("keyInterviewersOneText") != null) {
						requestRequisitionSkillDTO
								.setKeyInterviewersOne((String) jsonObject.get("keyInterviewersOneText"));
					}
					if (jsonObject.get("keyInterviewersTwoText") != null) {
						requestRequisitionSkillDTO
								.setKeyInterviewersTwo((String) jsonObject.get("keyInterviewersTwoText"));
					}
					if (jsonObject.get("additionalComments") != null) {
						requestRequisitionSkillDTO.setAdditionalComments((String) jsonObject.get("additionalComments"));
					}
				}
				
				
			}
			requestRequisitionSkillDTOs.add(requestRequisitionSkillDTO);
		}

		Integer currentLoggedInUserId = UserUtil.getCurrentResource().getEmployeeId();
		Resource currentResource = resourceService.find(currentLoggedInUserId);
		String currentBU = currentResource.getCurrentBuId().getParentId().getName() + "-"
				+ currentResource.getCurrentBuId().getName();
		String sendMailFrom = currentResource.getEmailId();
		String sender = currentResource.getFirstName() + " " + currentResource.getLastName();

		Date date = new Date();
		for (RequestRequisitionSkillDTO requestRequisitionSkillDTO1 : requestRequisitionSkillDTOs) {
			if (requestId != null && Integer.parseInt(requestId) > 0) {
				if (requestRequisitionSkillDTO1.getId() != 0) {
					RequestRequisitionSkillDTO req = requestRequisitionService
							.getRequestRecordForEdit(requestRequisitionSkillDTO1.getId());
					currentResource = req.getRequestRequisition().getResource();
					sender = currentResource.getFirstName() + " " + currentResource.getLastName();
				}

			}
		}

		if (Integer.parseInt(requestId) > 0) {
			requestRequisitionDTO.setId(Integer.parseInt(requestId));
		}
		//// Hate Code
		// for Mail Sent to Start
		String mailId = "";
		String empId = "";
		String pdlEmailIds = "";
		String[] sentEmpId = null;
		String[] sentMailTo = null;
		if (tomailIDs != null && tomailIDs.length > 0) {

			for (String st : tomailIDs) {
				String[] str = st.split("-");
				if (str[1] != null) {
					if(!"0".equals(str[0])){
						empId += str[0] + ",";
					}
					if(!"no".equals(str[1])){
						mailId += str[1] + ",";
					}
				}
			
			}
			mailId+=sendMailFrom + ","; //mail to requester
			empId+=currentResource.getEmployeeId() + ",";
			for (String st : pdlList) {
				
				String str = st.substring(st.indexOf("-")+1, st.length()); 
				System.out.println("******************email id for pdl****************"+str);
				if (str != null) {
				//	empId += str[0] + ",";   //commented by Samiksha
					mailId += str + ",";
				}
			}
		}
		if (mailId != null && mailId != "" && empId != null && empId != "") {
			int lenMail = mailId.length();
			int lenemp = empId.length();
			String mailStr = mailId.substring(0, lenMail - 1);
			String empStr = empId.substring(0, lenemp - 1);
			sentMailTo = mailStr.split(",");
			sentEmpId = empStr.split(",");
		}
		// for Mail Sent to End
		// for CC Mail Start
		String mailCCId = "";
		String empCCId = "";
		String[] sentMailCCTo = null;
		String[] sentEmpCCId = null;
		if (ccmailIDs != null && ccmailIDs.length > 0) {
			for (String st : ccmailIDs) {
				String[] str = st.split("-");
				if (str[1] != null) {
					if (!str[0].equals("0")) {
						empCCId += str[0] + ",";
					}
					mailCCId += str[1] + ",";
				}
			}
		}
		if (mailCCId != null && mailCCId != "" && empCCId != null && empCCId != "") {
			int lenCCMail = mailCCId.length();
			int lenCCemp = empCCId.length();
			String mailCCStr = mailCCId.substring(0, lenCCMail - 1);
			String empCCStr = empCCId.substring(0, lenCCemp - 1);
			sentMailCCTo = mailCCStr.split(",");
			sentEmpCCId = empCCStr.split(",");
		}

		if (pdlList != null && pdlList.length > 0) {
			for (String pdl : pdlList) {
				if (null != pdl && !"".equals(pdl.trim())) {
					pdlEmailIds += pdl.trim() + ",";
				}
			}
			pdlEmailIds = pdlEmailIds.substring(0, pdlEmailIds.length() - 1);
		}
		// for CC Mail End
		// for check no duplicate Emp Id
		ArrayList<String> sentTo = new ArrayList<String>();
		ArrayList<String> notifyTo = new ArrayList<String>();
		if (sentEmpId != null) {
			List<String> empSentList = Arrays.asList(sentEmpId);
			sentTo.addAll(empSentList);
		}
		if (sentEmpCCId != null) {
			List<String> sentEmpCCList = Arrays.asList(sentEmpCCId);
			notifyTo.addAll(sentEmpCCList);
		} else{
			notifyTo.add(currentResource.getEmployeeId().toString());
		}
		HashSet<String> hashSet = new HashSet<String>(sentTo);
		String finalMailSentTo = hashSet.toString();
		finalMailSentTo = finalMailSentTo.replaceAll("\\[", "").replaceAll("\\]", "");

		hashSet = new HashSet<String>(notifyTo);
		String finalMailNotifyTo = hashSet.toString();
		finalMailNotifyTo = finalMailNotifyTo.replaceAll("\\[", "").replaceAll("\\]", "");

		String tomailIDsList[] = new String[(sentMailTo.length) + 1];
		for (int i = 0; i < sentMailTo.length; i++) {
			tomailIDsList[i] = new String();
			tomailIDsList[i] = sentMailTo[i];
		}
		tomailIDsList[sentMailTo.length] = sendMailFrom;

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yy");
		requestRequisitionDTO.setNotifyMailTo(finalMailNotifyTo);
		requestRequisitionDTO.setSentMailTo(finalMailSentTo);
		requestRequisitionDTO.setDate(simpleDateFormat.format(date));
		requestRequisitionDTO.setProjectBU(currentBU);
		requestRequisitionDTO.setResource(currentResource);// Indenter
		requestRequisitionDTO.setPdlEmailIds(pdlEmailIds);
		Project project = new Project();
		project.setId(projectId);
		Customer cust = new Customer();
		cust.setId(customerId);
		CustomerGroup custGrp = new CustomerGroup();//old
		// New Table(customer_grp) hence no data. Until group not created for all
		// clients
		if ("Select".equals(groupId) || "NA".equals(groupName)) {
			custGrp = customerGroupService.findById(0).get(0);
		} else {
			custGrp.setGroupId(Integer.parseInt(groupId));
			custGrp.setCustomerGroupName(groupName);
		}
		requestRequisitionDTO.setGroup(custGrp);
		requestRequisitionDTO.setCustomer(cust);
		requestRequisitionDTO.setProject(project);

		RequestRequisitionFormDTO requestRequisitionFormDTO = new RequestRequisitionFormDTO();
		requestRequisitionFormDTO.setRequestRequisitionDto(requestRequisitionDTO);
		requestRequisitionFormDTO.setRequestRequisitionSkillDto(requestRequisitionSkillDTOs);
		
		requestRequisitionDTO.setToMailIds(tomailIDsList);
		requestRequisitionDTO.setCcMailIds(sentMailCCTo);
		requestRequisitionDTO.setSender(sender);
		requestRequisitionDTO.setCurrentBU(currentBU);
		requestRequisitionDTO.setSendMailFrom(sendMailFrom);
		requestRequisitionDTO.setProjectName(projectName);
		requestRequisitionDTO.setGroupName(groupName);
		requestRequisitionDTO.setCustomerName(customerName);

		return requestRequisitionFormDTO;

	}

	// 1003958 added id the below requet mapping for updating the records
	@RequestMapping(value = "saveRequest/{requestId}/{skillName}/{mailIDs}/{mailIDcc}/{projects}/{selectedProjectName}/{selectedCustomerName}/{customerId}/{groupId}/{groupName}", method = RequestMethod.POST, produces = "text/html", headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> saveRequest(@RequestBody String requestJSON,
			@PathVariable("requestId") String requestId, @PathVariable("skillName") String[] skillName,
			@PathVariable("mailIDs") String[] tomailIDs, @PathVariable("mailIDcc") String[] ccmailIDs,
			@PathVariable("projects") Integer projectId, @PathVariable("selectedProjectName") String projectName,
			@PathVariable("selectedCustomerName") String customerName,
			@PathVariable("customerId") Integer customerId, @PathVariable("groupId") String groupId,@PathVariable("groupName") String groupName,
			@RequestParam(value = "pdlList", required = false) String[] pdlList) throws Exception {
		logger.info("------ResourceController saveRequest method start------");

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");

		List items = new JSONDeserializer<Map<String, List<Map<String, Object>>>>()
				.use("values.values", RequestRequisitionSkill.class).deserialize(requestJSON, String.class)
				.get("parameters");

		RequestRequisitionFormDTO requestRequisitionFormDTO = convertRequestParamToDTO(requestId,skillName, tomailIDs,
				ccmailIDs, projectId, projectName,customerName, customerId, groupId,groupName, items, pdlList);

		logger.info("------------save request method call-----------");
		requestRequisitionService.saveRequest(requestRequisitionFormDTO);
		
		logger.info("------------ save request method call end, calling send email method for RRF creation -----------");
		requestRequisitionService.sendEmail(requestRequisitionFormDTO,false, false, false, false);

		logger.info("------RequestRequisitionController sendEmail method end------");
	
	return new ResponseEntity<String>(headers,HttpStatus.OK);
	}
	/*@RequestMapping(value = "saveRRF", method = RequestMethod.POST, produces = "text/html", headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> saveRequestNew(@RequestParam(value ="buhFile" ,required=false) MultipartFile[] buhFile ,
			@RequestParam(value ="bghFile" ,required=false) MultipartFile[] bghFile, 
			@RequestParam(value ="json" ,required=false) String requestJson) throws Exception {
		logger.info("------ResourceController saveRequestNew method start------");

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		List items = new JSONDeserializer<Map<String, List<Map<String, Object>>>>()
				.use("values.values", RequestRequisitionInformationDTO.class).deserialize(requestJson, String.class)
				.get("parameters");
		
		RequestRequisitionFormDTO requestRequisitionFormDTO = convertRequestParamToDTO(requestId,skillName, tomailIDs,
				ccmailIDs, projectId, projectName,customerName, customerId, groupId,groupName, items, pdlList);

		RequestRequisitionFormDTO requestRequisitionFormDTO = convertmethod(items,bghFile,buhFile);
		
		logger.info("------------save request method call-----------");
		requestRequisitionService.saveRequest(requestRequisitionFormDTO);
		
		logger.info("------------ save request method call end, calling send email method for RRF creation -----------");
		requestRequisitionService.sendEmail(requestRequisitionFormDTO);

		logger.info("------ResourceController sendEmail method end------");
	
	return new ResponseEntity<String>(headers,HttpStatus.OK);
	}*/
	
	@RequestMapping(value = "saveRRF", method = RequestMethod.POST, produces = "text/html", headers = "Accept=application/json")
	@ResponseBody
	public String saveRequestNew(@RequestParam(value ="buhFile" ,required=false) MultipartFile[] buhFile ,
			@RequestParam(value ="bghFile" ,required=false) MultipartFile[] bghFile, 
			@RequestParam(value ="triggerEmail", defaultValue="true") boolean triggerEmail ,
			@RequestParam(value ="json" ,required=false) String requestJson,
			@RequestParam(value ="copyFlagButton",required=false) String copyFlagButton)throws Exception {
		logger.info("------RequestRequisitionController saveRequestNew method start------");

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		List items = new JSONDeserializer<Map<String, List<Map<String, Object>>>>()
				.use("values.values", RequestRequisitionInformationDTO.class).deserialize(requestJson, String.class)
				.get("parameters");
		boolean holdFlag=false;
		boolean lostFlag=false;
		boolean update=false;
		boolean copyFlag = false;
		RequestRequisitionFormDTO requestRequisitionFormDTO = convertmethod(items,bghFile,buhFile);
		String requirementId = "";
		if(requestRequisitionFormDTO.getRequestRequisitionDto().getId() > 0) {
			logger.info("------------Update request method called-----------");
			RequestRequisitionSkill requisitionSkill=null;
			requisitionSkill=requestRequisitionService.findRequestRequisitionSkillsByRequestRequisitionId(requestRequisitionFormDTO.getRequestRequisitionDto().getId()).get(0);
			if(requisitionSkill.getHold()!=requestRequisitionFormDTO.getRequestRequisitionSkillDto().get(0).getHold())
				holdFlag=true;
			if(requisitionSkill.getLost()!=requestRequisitionFormDTO.getRequestRequisitionSkillDto().get(0).getLost())
				lostFlag=true;
			update=true;
			requestRequisitionService.updateRequisitionRequestSkill(requestRequisitionFormDTO);
			
		} else {
			logger.info("------------ Save request method called -----------");
			requirementId = requestRequisitionService.saveRequest(requestRequisitionFormDTO);
		}
		if(copyFlagButton!=null && copyFlagButton.equalsIgnoreCase("copyFlagTrue")) {
			copyFlag = true; 
			update = false;
		}
		logger.info("------------ save request method call end, calling send email method for RRF creation -----------");
		if(triggerEmail) {
			requestRequisitionService.sendEmail(requestRequisitionFormDTO,holdFlag,lostFlag,update, copyFlag);
		}else {
			logger.info("------Request Requisition Controller NOT sending Email as Admin chose not to send------");
		}
		logger.info("------ResourceController sendEmail method end------");
	
   	    return requirementId;
 
	}

	
	private RequestRequisitionFormDTO convertmethod(List<Map<String, Object>> requestRequisitionSkillList, MultipartFile[] bghFile, MultipartFile[] buhFile) throws Exception{
		
		Integer currentLoggedInUserId = UserUtil.getCurrentResource().getEmployeeId();
		Resource currentResource = resourceService.find(currentLoggedInUserId);
		String currentBU = currentResource.getCurrentBuId().getParentId().getName() + "-"
				+ currentResource.getCurrentBuId().getName();
		String sendMailFrom = currentResource.getEmailId();
		String sender = currentResource.getFirstName() + " " + currentResource.getLastName();

		ListIterator<Map<String, Object>> iterator = requestRequisitionSkillList.listIterator();
		String comments = null;
		List<Integer> skillList = new ArrayList<Integer>();
		String finalMailPDLTo = new String();
		List<RequestRequisitionSkillDTO> requestRequisitionSkillDTOs = new ArrayList();
		RequestRequisitionSkillDTO requestRequisitionSkillDTO =  new RequestRequisitionSkillDTO();
		RequestRequisitionDTO requestRequisitionDTO = new RequestRequisitionDTO();
		while (iterator.hasNext()) {
			Map<String, Object> map = (Map<String, Object>) iterator.next();
			Set requestSet = map.entrySet();
			Iterator requestIterator = requestSet.iterator();
			while (requestIterator.hasNext()) {
				Map.Entry mapEntry = (Map.Entry) requestIterator.next();
				
				if(mapEntry.getKey().equals("locationId")) {
					LocationDTO location = new LocationDTO();
					location.setId(Integer.parseInt(mapEntry.getValue().toString()));
					requestRequisitionSkillDTO.setLocation(location);
				}else if(mapEntry.getKey().equals("requiredFor")){
					requestRequisitionDTO.setRequiredFor(mapEntry.getValue().toString());
				}
				
				if (mapEntry.getKey().equals("skillRequestId")) {
					// Integer.parseInt(skillRequestId)>0
					requestRequisitionSkillDTO.setId(Integer.parseInt(mapEntry.getValue().toString()));
				}
				else if (mapEntry.getKey().equals("additionalComment")) {
					
					requestRequisitionDTO.setComments(mapEntry.getValue().toString());
					
				} else if (mapEntry.getKey().equals("skill")) {
					
					Skills skill = skillsService.find(Integer.parseInt(mapEntry.getValue().toString()));
					SkillsDTO skillsDTO = new SkillsDTO();
					mapper.map(skill, skillsDTO);
					requestRequisitionSkillDTO.setSkill(skillsDTO);
					
				} else if (mapEntry.getKey().equals("positions")) {
					requestRequisitionSkillDTO.setNoOfResources(Integer.parseInt(mapEntry.getValue().toString()));
				} else if (mapEntry.getKey().equals("designationId")) {
					Designation designation = designationService.findById(Integer.parseInt(mapEntry.getValue().toString()));
					DesignationDTO designationDTO = new DesignationDTO();
					mapper.map(designation, designationDTO);
					requestRequisitionSkillDTO.setDesignation(designationDTO);
				} else if (mapEntry.getKey().equals("experienceId")) {
					/*Experience experience = new Experience();
					experience = experienceService.getExperienceById(Integer.parseInt());*/
					requestRequisitionSkillDTO.setExperience(mapEntry.getValue().toString());
				} else if (mapEntry.getKey().equals("allocationTypeId")) {
					AllocationTypeDTO allocationType = new AllocationTypeDTO();
					allocationType.setId(Integer.parseInt(mapEntry.getValue().toString()));
					requestRequisitionSkillDTO.setAllocationType(allocationType);

				} /*else if (mapEntry.getKey().equals("type")) {
					requestRequisitionSkillDTO.setType(Integer.parseInt(mapEntry.getValue().toString()));

				} */else if (mapEntry.getKey().equals("reqId")) {
					requestRequisitionSkillDTO.setRequirementId(mapEntry.getValue().toString());
					
				} else if (mapEntry.getKey().equals("requirementName")) {
					requestRequisitionSkillDTO.setRequirementId(mapEntry.getValue().toString());

				}else if (mapEntry.getKey().equals("hold")) {

					if (!mapEntry.getValue().toString().isEmpty()) {

						requestRequisitionSkillDTO.setHold(Integer.parseInt(mapEntry.getValue().toString()));

					} else {

						requestRequisitionSkillDTO.setHold(0);
					}

				} else if (mapEntry.getKey().equals("lost")) {

					if (!mapEntry.getValue().toString().isEmpty()) {

						requestRequisitionSkillDTO.setLost(Integer.parseInt(mapEntry.getValue().toString()));

					} else {

						requestRequisitionSkillDTO.setLost(0);
					}

				} else if (mapEntry.getKey().equals("bgbu")) {
					requestRequisitionDTO.setProjectBU((mapEntry.getValue().toString()));
				}else if (mapEntry.getKey().equals("deliveryPOCId")) {
					Resource deliveryPOC = new Resource();
					deliveryPOC.setEmployeeId(Integer.parseInt(mapEntry.getValue().toString()));
					requestRequisitionDTO.setDeliveryPOC(deliveryPOC);
				}else if (mapEntry.getKey().equals("requestorID")) {
					Resource requestor = new Resource();
					requestor.setEmployeeId(Integer.parseInt(mapEntry.getValue().toString()));
					requestRequisitionDTO.setRequestor(requestor);
				} else if (mapEntry.getKey().equals("clientType")) {
					requestRequisitionDTO.setClientType(mapEntry.getValue().toString());
				}else if (mapEntry.getKey().equals("clientId")) {
					Customer customer = new Customer();
					customer.setId(Integer.parseInt(mapEntry.getValue().toString()));
					requestRequisitionDTO.setCustomer(customer);
				}else if (mapEntry.getKey().equals("customerGrpId")) {
				
					String customerGrpInfo = mapEntry.getValue().toString();
					if (!("Select".equalsIgnoreCase(customerGrpInfo) || customerGrpInfo.isEmpty()  ) ){
						CustomerGroup customerGrp = customerGroupService.findByGroupId(Integer.parseInt(customerGrpInfo));
						requestRequisitionDTO.setGroup(customerGrp);
					}
					
					
				}else if (mapEntry.getKey().equals("projectType")) {
					requestRequisitionDTO.setProjectType(mapEntry.getValue().toString());
				}else if (mapEntry.getKey().equals("projectID")) {
					Project project = projectService.findProject(Integer.parseInt(mapEntry.getValue().toString()));
					project.setId(Integer.parseInt(mapEntry.getValue().toString()));
					requestRequisitionDTO.setProject(project);
				}else if (mapEntry.getKey().equals("shiftTypeId")) {
					ShiftTypes shiftTypes = new ShiftTypes();
					shiftTypes.setId(Integer.parseInt(mapEntry.getValue().toString()));
					requestRequisitionDTO.setShiftType(shiftTypes);
				}else if (mapEntry.getKey().equals("projectDuration")) {
					requestRequisitionDTO.setProjectDuration(Integer.parseInt(mapEntry.getValue().toString()));
				}else if (mapEntry.getKey().equals("requestedDate")) {
					requestRequisitionDTO.setDate(mapEntry.getValue().toString()); 
				}else if(mapEntry.getKey().equals("amJobCode")){
					requestRequisitionDTO.setAmJobCode(mapEntry.getValue().toString());
				}
				else if (mapEntry.getKey().equals("resourceRequiredDate")) {
					requestRequisitionDTO.setResourceRequiredDate(mapEntry.getValue().toString());
				}else if (mapEntry.getKey().equals("projectShiftDetails")) {
					requestRequisitionDTO.setProjectShiftOtherDetails(mapEntry.getValue().toString());
				}else if (mapEntry.getKey().equals("clientInterviewRequired")) {
					requestRequisitionDTO.setIsClientInterviewRequired(mapEntry.getValue().toString());
				}else if (mapEntry.getKey().equals("bgvRequired")) {
					requestRequisitionDTO.setIsBGVrequired(mapEntry.getValue().toString());
				}else if (mapEntry.getKey().equals("round1empId")) {
					requestRequisitionSkillDTO.setKeyInterviewersOne(mapEntry.getValue().toString());
				}else if (mapEntry.getKey().equals("round2empId")) {
					requestRequisitionSkillDTO.setKeyInterviewersTwo(mapEntry.getValue().toString());
				}else if (mapEntry.getKey().equals("skill1name") ) {
					/*if(!requestRequisitionSkillDTO.getSkillsToEvaluate().isEmpty()){
						requestRequisitionSkillDTO.setSkillsToEvaluate(requestRequisitionSkillDTO.getSkillsToEvaluate()+ "," + mapEntry.getValue().toString());
					}else*/{
						requestRequisitionSkillDTO.setSkillsToEvaluate(mapEntry.getValue().toString());
					}
				}else if (mapEntry.getKey().equals("primarySkills")) {
					requestRequisitionSkillDTO.setPrimarySkills((mapEntry.getValue().toString()));
				}else if (mapEntry.getKey().equals("additionalSkills")) {
					requestRequisitionSkillDTO.setDesirableSkills(mapEntry.getValue().toString());
				}else if (mapEntry.getKey().equals("rolesAndResponsibilities")) {
					requestRequisitionSkillDTO.setResponsibilities(mapEntry.getValue().toString());
				}else if (mapEntry.getKey().equals("mailTo")) {
					String mailsIdsString =mapEntry.getValue().toString();
					if(!mailsIdsString.isEmpty()  && null != mailsIdsString){
						String [] mailsIds = mailsIdsString.split(","); 
						
						for (int i=0; i<mailsIds.length ; i++) {
							mailsIds[i] = mailsIds[i].replaceAll("\\[", "").replaceAll("\\]", "").trim();
							System.out.println("mail ids from front end :"+mailsIds[i]);
						}
						
						ArrayList<String> toMailIdsInStringFormat = new ArrayList<String>();
						ArrayList<Integer> toMailIdsIninteger = new ArrayList<Integer>();
						for (String string : mailsIds) {
							toMailIdsIninteger.add(Integer.parseInt(string.trim()));
							toMailIdsInStringFormat.add(string);
						}
					
						List<String> emailsForToMail = resourceService.findEmailById(toMailIdsIninteger);
						emailsForToMail.add(sendMailFrom);
						String[] emailsArrayForMail = new String[emailsForToMail.size()];
						
						for (int i=0; i<emailsForToMail.size(); i++) {
							emailsArrayForMail[i] = emailsForToMail.get(i);
						}
						
						requestRequisitionDTO.setToMailIds(emailsArrayForMail); //for mail content
						
						HashSet<String> hashSet = new HashSet<String>(toMailIdsInStringFormat);
						String finalToMailIds = hashSet.toString();
						finalToMailIds = finalToMailIds.replaceAll("\\[", "").replaceAll("\\]", "").trim();
						requestRequisitionDTO.setSentMailTo(finalToMailIds); //for saving in database.
					}else{
						String[] ccmail= new String[1];
						ccmail[0] = sendMailFrom;
						requestRequisitionDTO.setToMailIds(ccmail);
						requestRequisitionDTO.setSentMailTo("");
					}
					
					
				}else if (mapEntry.getKey().equals("notifyMailIds")) {
					System.out.println("testing notify to");
					String[] notifyIds = mapEntry.getValue().toString().split(",");
					if(notifyIds.length > 0 && !notifyIds[0].isEmpty()){
						for (int i=0; i<notifyIds.length ; i++) {
							notifyIds[i] = notifyIds[i].replaceAll("\\[", "").replaceAll("\\]", "").trim();
							System.out.println("mail ids from front end for notify:"+notifyIds[i]);
						}
						
						ArrayList<String> notifyInintegers = new ArrayList<String>();
						ArrayList<Integer> notifyInintegersFormatInteger = new ArrayList<Integer>();
						
						
						
						Resource deliveryPOC =   requestRequisitionDTO.getDeliveryPOC();
						System.out.println("delivery POC emp ID : "+deliveryPOC.getEmployeeId());
						notifyInintegersFormatInteger.add(deliveryPOC.getEmployeeId());
						notifyInintegers.add(deliveryPOC.getEmployeeId().toString());
						
						for (String id : notifyIds) {
							notifyInintegersFormatInteger.add(Integer.parseInt(id));
							notifyInintegers.add(id);
						}
						List<String> emailsForCCMail = resourceService.findEmailById(notifyInintegersFormatInteger);
						String[] emailsArrayForCCMail = new String[emailsForCCMail.size()];
						
						for (int i=0; i<emailsForCCMail.size(); i++) {
							emailsArrayForCCMail[i] = emailsForCCMail.get(i);
						}
						
						requestRequisitionDTO.setCcMailIds(emailsArrayForCCMail); //for mail
						
						HashSet<String> hashSet = new HashSet<String>(notifyInintegers);
						String finalNotifyIds = hashSet.toString();
						finalNotifyIds = finalNotifyIds.replaceAll("\\[", "").replaceAll("\\]", "").trim();
						requestRequisitionDTO.setNotifyMailTo(finalNotifyIds); //for saving in database
					}
					else{
						Resource deliveryPOC =   requestRequisitionDTO.getDeliveryPOC();
						System.out.println("delivery POC emp ID : "+deliveryPOC.getEmployeeId());
						ArrayList<Integer> notifyInintegersFormatInteger = new ArrayList<Integer>();
						notifyInintegersFormatInteger.add(deliveryPOC.getEmployeeId());
						List<String> deliveryPOCEmail = resourceService.findEmailById(notifyInintegersFormatInteger);

						String[] ccmail= new String[2];
						ccmail[0] = sendMailFrom;
						ccmail[1] = deliveryPOCEmail.get(0);
						requestRequisitionDTO.setCcMailIds(ccmail); 
						requestRequisitionDTO.setNotifyMailTo(deliveryPOC.getEmployeeId().toString());
					}
					
					
				}else if (mapEntry.getKey().equals("pdlIds")) {
					
					String pdlIds = mapEntry.getValue().toString();
					if(!pdlIds.isEmpty() && null!=pdlIds){
						String[] pdlArray = pdlIds.split(",");
						ArrayList<Integer> pdlInintegers = new ArrayList<Integer>();
						ArrayList<String> pdlIDsInString = new ArrayList<String>();
					for (String string : pdlArray) {
						System.err.println("RRF SAVE string......"+string);
						if(string!=null && !string.contains("null"))
						{
							String s1=string.replaceAll("\\[", "").replaceAll("\\]", "").trim();
							if(!s1.equalsIgnoreCase("null"))
							{
						pdlInintegers.add(Integer.parseInt(string.replaceAll("\\[", "").replaceAll("\\]", "").trim()));
						pdlIDsInString.add(string.replaceAll("\\[", "").replaceAll("\\]", "").trim());
							}
						}
					}
					
					System.err.println("pdlInintegers is "+pdlInintegers);
					
					List<String> pdlMailsTo = pdlEmailGroupService.findPdlByIds(pdlInintegers);
					String[] pdlMailsInArrayFormat = new String[pdlMailsTo.size()];
					//pdlMailsTo.addAll((pdlMailsTo));
				
					for(int i=0; i < pdlMailsInArrayFormat.length; i++){
						pdlMailsInArrayFormat[i] = pdlMailsTo.get(i);
					}
					
					String[] ccMailsList = new String[requestRequisitionDTO.getCcMailIds().length + pdlMailsInArrayFormat.length] ;
					String[] ccMailsArray = requestRequisitionDTO.getCcMailIds();
					
					if(requestRequisitionDTO.getCcMailIds().length!= 0 ){
						
						for(int k = 0; k< ccMailsArray.length; k++){
							ccMailsList[k] = ccMailsArray[k];
						}
						
						int j=0;
						for (int i = ccMailsArray.length ; i<ccMailsList.length; i++) {
							ccMailsList[i] = pdlMailsInArrayFormat[j];
							j++;
						}
					}
						
					requestRequisitionDTO.setCcMailIds(ccMailsList);
					 
					HashSet<String> hashSet = new HashSet<String>(pdlIDsInString);
						 finalMailPDLTo = hashSet.toString();
						
					finalMailPDLTo = finalMailPDLTo.replaceAll("\\[", "").replaceAll("\\]", "");
					
					requestRequisitionDTO.setPdlEmailIds(finalMailPDLTo); //TODO : Samiksha email ids required
					}else{
						requestRequisitionDTO.setPdlEmailIds("");
					}
				}else if (mapEntry.getKey().equals("resourceTypeId")) {
					String resourceType = mapEntry.getValue().toString();
					requestRequisitionSkillDTO.setType(Integer.parseInt(resourceType.trim()));
				}else if (mapEntry.getKey().equals("requirementAreaId")) {
					//Code To set selected RequirementArea of RRF - Start
					requestRequisitionSkillDTO.setRequirementArea(mapEntry.getValue().toString());
					//Code To set selected RequirementArea of RRF - End
				}else if(mapEntry.getKey().equals("expOtherDetails")){
					requestRequisitionDTO.setExpOtherDetails(mapEntry.getValue().toString());
				} else if(mapEntry.getKey().equals("hiringBgBu")){
					requestRequisitionDTO.setHiringBGBU(mapEntry.getValue().toString());
				} else if(mapEntry.getKey().equals("resourceReplacement")){
					Resource resource = new Resource();
					resource.setEmployeeId(Integer.parseInt(mapEntry.getValue().toString()));
					requestRequisitionDTO.setReplacementResource(resource);
				} else if(mapEntry.getKey().equals("reason")){
					ReasonForReplacementDTO reasonForReplacementDTO = new ReasonForReplacementDTO();
					reasonForReplacementDTO.setId(Integer.parseInt(mapEntry.getValue().toString()));
					requestRequisitionDTO.setReason(reasonForReplacementDTO);
				} else if(mapEntry.getKey().equals("priority")){
					PriorityDTO priorityDTO = new PriorityDTO();
					priorityDTO.setId(1);
					requestRequisitionDTO.setPriority(priorityDTO);
				} 
				else if(mapEntry.getKey().equals("successFactorId")) {
					requestRequisitionDTO.setSuccessFactorId(mapEntry.getValue().toString());
				}
				else if(mapEntry.getKey().equals("rmgPoc")) {
					requestRequisitionDTO.setRmgPoc(mapEntry.getValue().toString());
				}
				
				else if(mapEntry.getKey().equals("tecTeamPoc")) {
					requestRequisitionDTO.setTechTeamPoc(mapEntry.getValue().toString());
				}
				
			}
			
		}
		
		requestRequisitionDTO.setResource(currentResource);
		
		//requestRequisitionDTO.setToMailIds(ArrayUtils.add(requestRequisitionDTO.getToMailIds(), sendMailFrom));
		//requestRequisitionDTO.setSentMailTo(sentMailTo); // TODO: Samiksha
		Date date = new Date();
		Integer requestId = requestRequisitionSkillDTO.getId();
		RequestRequisitionSkillDTO req = new RequestRequisitionSkillDTO();
			if (requestId != null && requestId > 0) { // record for edit
					 req = requestRequisitionService
							.getRequestRecordForEdit(requestId);
					currentResource = req.getRequestRequisition().getResource();
					sender = currentResource.getFirstName() + " " + currentResource.getLastName();
					requestRequisitionDTO.setId(req.getRequestRequisition().getId());
					requestRequisitionDTO.setResource(req.getRequestRequisition().getResource());
					if(null == requestRequisitionDTO.getSentMailTo() || requestRequisitionDTO.getSentMailTo().isEmpty() ){
						if(!req.getRequestRequisition().getSentMailTo().isEmpty()){
							requestRequisitionDTO.setSentMailTo(req.getRequestRequisition().getSentMailTo()); // for db save
							String [] sendMailtoIds = req.getRequestRequisition().getSentMailTo().split(",");
							
							ArrayList<Integer> sendMailInintegersFormatInteger = new ArrayList<Integer>();
							
							for (String id : sendMailtoIds) {
								sendMailInintegersFormatInteger.add(Integer.parseInt(id.replaceAll("\\[", "").replaceAll("\\]", "").trim()));
							}
						
							List<String> emailsForCCMail = resourceService.findEmailById(sendMailInintegersFormatInteger);
							emailsForCCMail.add(sendMailFrom);
							String[] emailsArrayForCCMail = new String[emailsForCCMail.size()];
							
							for (int i=0; i<emailsForCCMail.size(); i++) {
								emailsArrayForCCMail[i] = emailsForCCMail.get(i);
							}
							
							requestRequisitionDTO.setToMailIds(emailsArrayForCCMail); //for mail
						}
						
						
					}
					if(null == requestRequisitionDTO.getNotifyMailTo() || requestRequisitionDTO.getNotifyMailTo().isEmpty() ){
						if(!req.getRequestRequisition().getNotifyMailTo().isEmpty()){
						requestRequisitionDTO.setNotifyMailTo(req.getRequestRequisition().getNotifyMailTo()); //for DB
						
						String [] NotifyMailtoIds = req.getRequestRequisition().getNotifyMailTo().split(",");
						
						ArrayList<Integer> notifyMailInintegersFormatInteger = new ArrayList<Integer>();
						
						for (String id : NotifyMailtoIds) {
							notifyMailInintegersFormatInteger.add(Integer.parseInt(id.replaceAll("\\[", "").replaceAll("\\]", "").trim()));
						}
					
						List<String> emailsForCCMail = resourceService.findEmailById(notifyMailInintegersFormatInteger);
						String[] emailsArrayForCCMail = new String[emailsForCCMail.size()];
						
						for (int i=0; i<emailsForCCMail.size(); i++) {
							emailsArrayForCCMail[i] = emailsForCCMail.get(i);
						}
						
						requestRequisitionDTO.setCcMailIds(emailsArrayForCCMail); //for mail
						}
					}
					if(null == requestRequisitionDTO.getPdlEmailIds()  || requestRequisitionDTO.getPdlEmailIds().isEmpty()){
						if(!req.getRequestRequisition().getPdlEmailIds().isEmpty()){
						requestRequisitionDTO.setPdlEmailIds(req.getRequestRequisition().getPdlEmailIds());
						
						String [] pdlMailtoIds = req.getRequestRequisition().getPdlEmailIds().split(",");
						
						ArrayList<Integer> pdlMailInintegersFormatInteger = new ArrayList<Integer>();
						
						for (String id : pdlMailtoIds) {
							pdlMailInintegersFormatInteger.add(Integer.parseInt(id.replaceAll("\\[", "").replaceAll("\\]", "").trim()));
						}
					
						List<String> pdlMailsForCCMail = pdlEmailGroupService.findPdlByIds(pdlMailInintegersFormatInteger);
						
						
						List<String> ccMailsInList =  new ArrayList<String>();
								
						ccMailsInList.addAll(Arrays.asList(requestRequisitionDTO.getCcMailIds()));
						
						ccMailsInList.addAll(pdlMailsForCCMail);
						
						
						String[] arrayForCCMail = new String[ccMailsInList.size()];
						
						for (int i=0; i<ccMailsInList.size(); i++) {
							arrayForCCMail[i] = ccMailsInList.get(i);
						}
						requestRequisitionDTO.setCcMailIds(arrayForCCMail); //for mail
						}
					}
					if(null == requestRequisitionSkillDTO.getKeyInterviewersOne()){
						requestRequisitionSkillDTO.setKeyInterviewersOne(req.getKeyInterviewersOne());
					}
					if(null == requestRequisitionSkillDTO.getKeyInterviewersTwo()){
						requestRequisitionSkillDTO.setKeyInterviewersTwo(req.getKeyInterviewersTwo());
					}
					
					if(bghFile!=null && bghFile.length!=0){
						int bghLength = bghFile.length;
						requestRequisitionDTO.setbGHFile(bghFile[bghLength-1]);
						requestRequisitionDTO.setbGHApprovalFileName(bghFile[bghLength-1].getOriginalFilename());
					} else{
						if(req.getRequestRequisition().getbGHFile()!=null){
							requestRequisitionDTO.setbGHFile(req.getRequestRequisition().getbGHFile());
							requestRequisitionDTO.setbGHApprovalFileName(req.getRequestRequisition().getbGHFile().getOriginalFilename());
						}
					}
					
					if(buhFile!=null  && buhFile.length!=0){
						int buhLength = buhFile.length;
						requestRequisitionDTO.setbUHFile(buhFile[buhLength-1]);
						requestRequisitionDTO.setbUHApprovalFileName(buhFile[buhLength-1].getOriginalFilename());
					} else{
						if(req.getRequestRequisition().getbUHFile()!=null){
							requestRequisitionDTO.setbUHFile(req.getRequestRequisition().getbUHFile());
							requestRequisitionDTO.setbUHApprovalFileName(req.getRequestRequisition().getbUHFile().getOriginalFilename());
						}
					}
					
					/*if(bghFile == null && req.getRequestRequisition().getbGHFile()!=null ){
						requestRequisitionDTO.setbGHFile(req.getRequestRequisition().getbGHFile());
						requestRequisitionDTO.setbGHApprovalFileName(req.getRequestRequisition().getbGHFile().getOriginalFilename());
					}else if(bghFile != null && req.getRequestRequisition().getbGHFile()==null) {
						int bghLength = bghFile.length;
						requestRequisitionDTO.setbGHFile(bghFile[bghLength-1]);
						requestRequisitionDTO.setbGHApprovalFileName(bghFile[bghLength-1].getOriginalFilename());
					} 
					
					if(buhFile == null && req.getRequestRequisition().getbUHFile()!=null ){
						requestRequisitionDTO.setbUHFile(req.getRequestRequisition().getbUHFile());
						requestRequisitionDTO.setbUHApprovalFileName(req.getRequestRequisition().getbUHFile().getOriginalFilename());
					}else if (buhFile != null && req.getRequestRequisition().getbUHFile()==null){
						int buhLength = buhFile.length;
						requestRequisitionDTO.setbUHFile(buhFile[buhLength-1]);
						requestRequisitionDTO.setbUHApprovalFileName(buhFile[buhLength-1].getOriginalFilename());
					} */
					
			}
		

		
		String mailId = "";
		String empId = "";
		String pdlEmailIds = "";
		String[] sentEmpId = null;
		String[] sentMailTo = null;
		/*if (tomailIDs != null && tomailIDs.length > 0) {

			for (String st : tomailIDs) {
				String[] str = st.split("-");
				if (str[1] != null) {
					if(!"0".equals(str[0])){
						empId += str[0] + ",";
					}
					if(!"no".equals(str[1])){
						mailId += str[1] + ",";
					}
				}
			
			}
			mailId+=sendMailFrom + ","; //mail to requester
			empId+=currentResource.getEmployeeId() + ",";
			for (String st : pdlList) {
				
				String str = st.substring(st.indexOf("-")+1, st.length()); 
				System.out.println("******************email id for pdl****************"+str);
				if (str != null) {
				//	empId += str[0] + ",";   //commented by Samiksha
					mailId += str + ",";
				}
			}
		}
		if (mailId != null && mailId != "" && empId != null && empId != "") {
			int lenMail = mailId.length();
			int lenemp = empId.length();
			String mailStr = mailId.substring(0, lenMail - 1);
			String empStr = empId.substring(0, lenemp - 1);
			sentMailTo = mailStr.split(",");
			sentEmpId = empStr.split(",");
		}
		// for Mail Sent to End
		// for CC Mail Start
		String mailCCId = "";
		String empCCId = "";
		String[] sentMailCCTo = null;
		String[] sentEmpCCId = null;
		if (ccmailIDs != null && ccmailIDs.length > 0) {
			for (String st : ccmailIDs) {
				String[] str = st.split("-");
				if (str[1] != null) {
					if (!str[0].equals("0")) {
						empCCId += str[0] + ",";
					}
					mailCCId += str[1] + ",";
				}
			}
		}
		if (mailCCId != null && mailCCId != "" && empCCId != null && empCCId != "") {
			int lenCCMail = mailCCId.length();
			int lenCCemp = empCCId.length();
			String mailCCStr = mailCCId.substring(0, lenCCMail - 1);
			String empCCStr = empCCId.substring(0, lenCCemp - 1);
			sentMailCCTo = mailCCStr.split(",");
			sentEmpCCId = empCCStr.split(",");
		}

		if (pdlList != null && pdlList.length > 0) {
			for (String pdl : pdlList) {
				if (null != pdl && !"".equals(pdl.trim())) {
					pdlEmailIds += pdl.trim() + ",";
				}
			}
			pdlEmailIds = pdlEmailIds.substring(0, pdlEmailIds.length() - 1);
		}
		// for CC Mail End
		// for check no duplicate Emp Id
		ArrayList<String> sentTo = new ArrayList<String>();
		ArrayList<String> notifyTo = new ArrayList<String>();
		if (sentEmpId != null) {
			List<String> empSentList = Arrays.asList(sentEmpId);
			sentTo.addAll(empSentList);
		}
		if (sentEmpCCId != null) {
			List<String> sentEmpCCList = Arrays.asList(sentEmpCCId);
			notifyTo.addAll(sentEmpCCList);
		} else{
			notifyTo.add(currentResource.getEmployeeId().toString());
		}
		HashSet<String> hashSet = new HashSet<String>(sentTo);
		String finalMailSentTo = hashSet.toString();
		finalMailSentTo = finalMailSentTo.replaceAll("\\[", "").replaceAll("\\]", "");

		hashSet = new HashSet<String>(notifyTo);
		String finalMailNotifyTo = hashSet.toString();
		finalMailNotifyTo = finalMailNotifyTo.replaceAll("\\[", "").replaceAll("\\]", "");

		String tomailIDsList[] = new String[(sentMailTo.length) + 1];
		for (int i = 0; i < sentMailTo.length; i++) {
			tomailIDsList[i] = new String();
			tomailIDsList[i] = sentMailTo[i];
		}
		tomailIDsList[sentMailTo.length] = sendMailFrom;

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yy");
		requestRequisitionDTO.setNotifyMailTo(finalMailNotifyTo);
		requestRequisitionDTO.setSentMailTo(finalMailSentTo);
		requestRequisitionDTO.setDate(simpleDateFormat.format(date));
		requestRequisitionDTO.setProjectBU(currentBU);
		requestRequisitionDTO.setResource(currentResource);// Indenter
		requestRequisitionDTO.setPdlEmailIds(pdlEmailIds);
		Project project = new Project();
		project.setId(projectId);
		Customer cust = new Customer();
		cust.setId(customerId);
		CustomerGroup custGrp = new CustomerGroup();
		// New Table(customer_grp) hence no data. Until group not created for all
		// clients
		if ("Select".equals(groupId) || "NA".equals(groupName)) {
			custGrp = customerGroupService.findById(0).get(0);
		} else {
			custGrp.setGroupId(Integer.parseInt(groupId));
			custGrp.setCustomerGroupName(groupName);
		}
		requestRequisitionDTO.setGroup(custGrp);
		requestRequisitionDTO.setCustomer(cust);
		requestRequisitionDTO.setProject(project);
		
*/		
	/*	
		if(null != bghFile ){
			if(requestRequisitionSkillDTO.getAllocationType().getAllocationType().contains("Non")){
				int bghLength = bghFile.length;
				requestRequisitionDTO.setbGHFile(bghFile[bghLength-1]);
				requestRequisitionDTO.setbGHApprovalFileName(bghFile[bghLength-1].getOriginalFilename());
			}
			
		}else if(requestId != 0){
			requestRequisitionDTO.setbGHFile(req.getRequestRequisition().getbGHFile());
			requestRequisitionDTO.setbGHApprovalFileName(req.getRequestRequisition().getbGHFile().getOriginalFilename());
		}*/
		
		if(null != bghFile && bghFile.length!=0 && requestId == 0 ){
			int bghLength = bghFile.length;
			requestRequisitionDTO.setbGHFile(bghFile[bghLength-1]);
			requestRequisitionDTO.setbGHApprovalFileName(bghFile[bghLength-1].getOriginalFilename());
		} 
		
		
		
		//allocatin type if non billable -> getbuh else if edit
		
		
		if(buhFile != null && buhFile.length!=0 && requestId == 0){
			int buhFileLength = buhFile.length;
			requestRequisitionDTO.setbUHFile(buhFile[buhFileLength-1]);
			requestRequisitionDTO.setbUHApprovalFileName(buhFile[buhFileLength-1].getOriginalFilename());
		}/*else{
				requestRequisitionDTO.setbUHFile(req.getRequestRequisition().getbUHFile());
				requestRequisitionDTO.setbUHApprovalFileName(req.getRequestRequisition().getbUHFile().getOriginalFilename());
		}*/
		
		RequestRequisitionFormDTO requestRequisitionFormDTO = new RequestRequisitionFormDTO();
		requestRequisitionFormDTO.setRequestRequisitionDto(requestRequisitionDTO);
		requestRequisitionFormDTO.setRequestRequisitionSkillDto(Arrays.asList(requestRequisitionSkillDTO));
		
		//requestRequisitionDTO.setToMailIds(tomailIDsList);
		//requestRequisitionDTO.setCcMailIds(sentMailCCTo);
		requestRequisitionDTO.setSender(sender);
		requestRequisitionDTO.setCurrentBU(currentBU);
		requestRequisitionDTO.setSendMailFrom(sendMailFrom);
		//requestRequisitionDTO.setProjectName(projectName);
		//requestRequisitionDTO.setGroupName(groupName);
		//requestRequisitionDTO.setCustomerName(customerName);

		return requestRequisitionFormDTO;

		
	}
	
	/*private RequestRequisitionFormDTO convertmethod(List<Map<String, Object>> requestRequisitionSkillList, MultipartFile[] bghFile, MultipartFile[] buhFile){
		
		Integer currentLoggedInUserId = UserUtil.getCurrentResource().getEmployeeId();
		Resource currentResource = resourceService.find(currentLoggedInUserId);
		String currentBU = currentResource.getCurrentBuId().getParentId().getName() + "-"
				+ currentResource.getCurrentBuId().getName();
		String sendMailFrom = currentResource.getEmailId();
		String sender = currentResource.getFirstName() + " " + currentResource.getLastName();

		ListIterator<Map<String, Object>> iterator = requestRequisitionSkillList.listIterator();
		String comments = null;
		List<Integer> skillList = new ArrayList<Integer>();
		String finalMailPDLTo = new String();
		List<RequestRequisitionSkillDTO> requestRequisitionSkillDTOs = new ArrayList();
		RequestRequisitionSkillDTO requestRequisitionSkillDTO =  new RequestRequisitionSkillDTO();
		RequestRequisitionDTO requestRequisitionDTO = new RequestRequisitionDTO();
		while (iterator.hasNext()) {
			Map<String, Object> map = (Map<String, Object>) iterator.next();
			Set requestSet = map.entrySet();
			Iterator requestIterator = requestSet.iterator();
			while (requestIterator.hasNext()) {
				Map.Entry mapEntry = (Map.Entry) requestIterator.next();
				
				if(mapEntry.getKey().equals("locationId")) {
					LocationDTO location = new LocationDTO();
					location.setId(Integer.parseInt(mapEntry.getValue().toString()));
					requestRequisitionSkillDTO.setLocation(location);
				}else if(mapEntry.getKey().equals("requiredFor")){
					requestRequisitionDTO.setRequiredFor(mapEntry.getValue().toString());
				}
				
				if (mapEntry.getKey().equals("skillRequestId")) {
					// Integer.parseInt(skillRequestId)>0
					requestRequisitionSkillDTO.setId(Integer.parseInt(mapEntry.getValue().toString()));
				}
				else if (mapEntry.getKey().equals("additionalComment")) {
					requestRequisitionDTO.setComments(mapEntry.getValue().toString());
				} else if (mapEntry.getKey().equals("skill")) {
					CompetencyDTO skill = new CompetencyDTO();
					skill.setId(Integer.parseInt(mapEntry.getValue().toString()));
					requestRequisitionSkillDTO.setSkill(skill);
				} else if (mapEntry.getKey().equals("positions")) {
					requestRequisitionSkillDTO.setNoOfResources(Integer.parseInt(mapEntry.getValue().toString()));
				} else if (mapEntry.getKey().equals("designationId")) {
					DesignationDTO designation = new DesignationDTO();
					designation.setId(Integer.parseInt(mapEntry.getValue().toString()));
					requestRequisitionSkillDTO.setDesignation(designation);
				} else if (mapEntry.getKey().equals("experienceId")) {
					Experience experience = new Experience();
					experience = experienceService.getExperienceById(Integer.parseInt());
					requestRequisitionSkillDTO.setExperience(mapEntry.getValue().toString());
				} else if (mapEntry.getKey().equals("allocationTypeId")) {
					AllocationTypeDTO allocationType = new AllocationTypeDTO();
					allocationType.setId(Integer.parseInt(mapEntry.getValue().toString()));
					requestRequisitionSkillDTO.setAllocationType(allocationType);

				} else if (mapEntry.getKey().equals("type")) {
					requestRequisitionSkillDTO.setType(Integer.parseInt(mapEntry.getValue().toString()));

				} else if (mapEntry.getKey().equals("reqId")) {
					requestRequisitionSkillDTO.setRequirementId(mapEntry.getValue().toString());
					
				} else if (mapEntry.getKey().equals("requirementName")) {
					requestRequisitionSkillDTO.setRequirementId(mapEntry.getValue().toString());

				}else if (mapEntry.getKey().equals("hold")) {

					if (!mapEntry.getValue().toString().isEmpty()) {

						requestRequisitionSkillDTO.setHold(Integer.parseInt(mapEntry.getValue().toString()));

					} else {

						requestRequisitionSkillDTO.setHold(0);
					}

				} else if (mapEntry.getKey().equals("lost")) {

					if (!mapEntry.getValue().toString().isEmpty()) {

						requestRequisitionSkillDTO.setLost(Integer.parseInt(mapEntry.getValue().toString()));

					} else {

						requestRequisitionSkillDTO.setLost(0);
					}

				} else if (mapEntry.getKey().equals("bgbu")) {
					requestRequisitionDTO.setProjectBU((mapEntry.getValue().toString()));
				}else if (mapEntry.getKey().equals("deliveryPOCId")) {
					Resource deliveryPOC = new Resource();
					deliveryPOC.setEmployeeId(Integer.parseInt(mapEntry.getValue().toString()));
					requestRequisitionDTO.setDeliveryPOC(deliveryPOC);
				}else if (mapEntry.getKey().equals("requestorID")) {
					Resource requestor = new Resource();
					requestor.setEmployeeId(Integer.parseInt(mapEntry.getValue().toString()));
					requestRequisitionDTO.setRequestor(requestor);
				} else if (mapEntry.getKey().equals("clientType")) {
					requestRequisitionDTO.setClientType(mapEntry.getValue().toString());
				}else if (mapEntry.getKey().equals("clientId")) {
					Customer customer = new Customer();
					customer.setId(Integer.parseInt(mapEntry.getValue().toString()));
					requestRequisitionDTO.setCustomer(customer);
				}else if (mapEntry.getKey().equals("customerGrpId")) {
					CustomerGroup customerGrp = new CustomerGroup();
					String customerGrpInfo = mapEntry.getValue().toString();
					if ("Select".equals(customerGrpInfo) || customerGrpInfo.isEmpty()  ) {
						customerGrp = customerGroupService.findById(0).get(0);
					} else {
						customerGrp.setGroupId(Integer.parseInt(customerGrpInfo));
					}
					
					requestRequisitionDTO.setGroup(customerGrp);
				}else if (mapEntry.getKey().equals("projectType")) {
					requestRequisitionDTO.setProjectType(mapEntry.getValue().toString());
				}else if (mapEntry.getKey().equals("projectID")) {
					Project project = projectService.findProject(Integer.parseInt(mapEntry.getValue().toString()));
					project.setId(Integer.parseInt(mapEntry.getValue().toString()));
					requestRequisitionDTO.setProject(project);
				}else if (mapEntry.getKey().equals("shiftTypeId")) {
					ShiftTypes shiftTypes = new ShiftTypes();
					shiftTypes.setId(Integer.parseInt(mapEntry.getValue().toString()));
					requestRequisitionDTO.setShiftType(shiftTypes);
				}else if (mapEntry.getKey().equals("projectDuration")) {
					requestRequisitionDTO.setProjectDuration(Integer.parseInt(mapEntry.getValue().toString()));
				}else if (mapEntry.getKey().equals("requestedDate")) {
					requestRequisitionDTO.setDate(mapEntry.getValue().toString()); 
				}else if (mapEntry.getKey().equals("resourceRequiredDate")) {
					requestRequisitionDTO.setResourceRequiredDate(mapEntry.getValue().toString());
				}else if (mapEntry.getKey().equals("projectShiftDetails")) {
					requestRequisitionDTO.setProjectShiftOtherDetails(mapEntry.getValue().toString());
				}else if (mapEntry.getKey().equals("clientInterviewRequired")) {
					requestRequisitionDTO.setIsClientInterviewRequired(mapEntry.getValue().toString());
				}else if (mapEntry.getKey().equals("bgvRequired")) {
					requestRequisitionDTO.setIsBGVrequired(mapEntry.getValue().toString());
				}else if (mapEntry.getKey().equals("round1empId")) {
					requestRequisitionSkillDTO.setKeyInterviewersOne(mapEntry.getValue().toString());
				}else if (mapEntry.getKey().equals("round2empId")) {
					requestRequisitionSkillDTO.setKeyInterviewersTwo(mapEntry.getValue().toString());
				}else if (mapEntry.getKey().equals("skill1name") ) {
					if(!requestRequisitionSkillDTO.getSkillsToEvaluate().isEmpty()){
						requestRequisitionSkillDTO.setSkillsToEvaluate(requestRequisitionSkillDTO.getSkillsToEvaluate()+ "," + mapEntry.getValue().toString());
					}else{
						requestRequisitionSkillDTO.setSkillsToEvaluate(mapEntry.getValue().toString());
					}
				}else if (mapEntry.getKey().equals("primarySkills")) {
					requestRequisitionSkillDTO.setPrimarySkills((mapEntry.getValue().toString()));
				}else if (mapEntry.getKey().equals("additionalSkills")) {
					requestRequisitionSkillDTO.setDesirableSkills(mapEntry.getValue().toString());
				}else if (mapEntry.getKey().equals("rolesAndResponsibilities")) {
					requestRequisitionSkillDTO.setResponsibilities(mapEntry.getValue().toString());
				}else if (mapEntry.getKey().equals("mailTo")) {
					System.out.println("testing mail to");
					String mailsIdsString =mapEntry.getValue().toString();
					if(!mailsIdsString.isEmpty()  && null != mailsIdsString){
						String [] mailsIds = mailsIdsString.split(","); 
						
						for (int i=0; i<mailsIds.length ; i++) {
							mailsIds[i] = mailsIds[i].replaceAll("\\[", "").replaceAll("\\]", "").trim();
							System.out.println("mail ids from front end :"+mailsIds[i]);
						}
						
						ArrayList<String> toMailIdsInStringFormat = new ArrayList<String>();
						ArrayList<Integer> toMailIdsIninteger = new ArrayList<Integer>();
						for (String string : mailsIds) {
							toMailIdsIninteger.add(Integer.parseInt(string.trim()));
							toMailIdsInStringFormat.add(string);
						}
					
						List<String> emailsForToMail = resourceService.findEmailById(toMailIdsIninteger);
						emailsForToMail.add(sendMailFrom);
						String[] emailsArrayForMail = new String[emailsForToMail.size()];
						
						for (int i=0; i<emailsForToMail.size(); i++) {
							emailsArrayForMail[i] = emailsForToMail.get(i);
						}
						
						requestRequisitionDTO.setToMailIds(emailsArrayForMail); //for mail content
						
						HashSet<String> hashSet = new HashSet<String>(toMailIdsInStringFormat);
						String finalToMailIds = hashSet.toString();
						finalToMailIds = finalToMailIds.replaceAll("\\[", "").replaceAll("\\]", "").trim();
						requestRequisitionDTO.setSentMailTo(finalToMailIds); //for saving in database.
					}else{
						String[] ccmail= new String[1];
						ccmail[0] = sendMailFrom;
						requestRequisitionDTO.setToMailIds(ccmail);
						requestRequisitionDTO.setSentMailTo("");
					}
					
					
				}else if (mapEntry.getKey().equals("notifyMailIds")) {
					System.out.println("testing notify to");
					String[] notifyIds = mapEntry.getValue().toString().split(",");
					if(notifyIds.length > 0 && !notifyIds[0].isEmpty()){
						for (int i=0; i<notifyIds.length ; i++) {
							notifyIds[i] = notifyIds[i].replaceAll("\\[", "").replaceAll("\\]", "").trim();
							System.out.println("mail ids from front end for notify:"+notifyIds[i]);
						}
						
						ArrayList<String> notifyInintegers = new ArrayList<String>();
						ArrayList<Integer> notifyInintegersFormatInteger = new ArrayList<Integer>();
						
						for (String id : notifyIds) {
							notifyInintegersFormatInteger.add(Integer.parseInt(id));
							notifyInintegers.add(id);
						}
					
						List<String> emailsForCCMail = resourceService.findEmailById(notifyInintegersFormatInteger);
						String[] emailsArrayForCCMail = new String[emailsForCCMail.size()];
						
						for (int i=0; i<emailsForCCMail.size(); i++) {
							emailsArrayForCCMail[i] = emailsForCCMail.get(i);
						}
						
						requestRequisitionDTO.setCcMailIds(emailsArrayForCCMail); //for mail
						
						HashSet<String> hashSet = new HashSet<String>(notifyInintegers);
						String finalNotifyIds = hashSet.toString();
						finalNotifyIds = finalNotifyIds.replaceAll("\\[", "").replaceAll("\\]", "").trim();
						requestRequisitionDTO.setNotifyMailTo(finalNotifyIds); //for saving in database
					}
					else{
						String[] ccmail= new String[1];
						ccmail[0] = sendMailFrom;
						requestRequisitionDTO.setCcMailIds(ccmail); 
						requestRequisitionDTO.setNotifyMailTo("");
					}
					
					
				}else if (mapEntry.getKey().equals("pdlIds")) {
					
					String pdlIds = mapEntry.getValue().toString();
					if(!pdlIds.isEmpty() && null!=pdlIds){
						String[] pdlArray = pdlIds.split(",");
						ArrayList<Integer> pdlInintegers = new ArrayList<Integer>();
						ArrayList<String> pdlIDsInString = new ArrayList<String>();
					for (String string : pdlArray) {
						pdlInintegers.add(Integer.parseInt(string.replaceAll("\\[", "").replaceAll("\\]", "").trim()));
						pdlIDsInString.add(string.replaceAll("\\[", "").replaceAll("\\]", "").trim());
					}
					
					List<String> pdlMailsTo = pdlEmailGroupService.findPdlByIds(pdlInintegers);
					String[] pdlMailsInArrayFormat = new String[pdlMailsTo.size()];
					//pdlMailsTo.addAll((pdlMailsTo));
				
					for(int i=0; i < pdlMailsInArrayFormat.length; i++){
						pdlMailsInArrayFormat[i] = pdlMailsTo.get(i);
					}
					
					String[] ccMailsList = new String[requestRequisitionDTO.getCcMailIds().length];
					
					if(requestRequisitionDTO.getCcMailIds().length!= 0 ){
						ccMailsList = requestRequisitionDTO.getCcMailIds();
						for (int i = ccMailsList.length ; i<pdlMailsInArrayFormat.length; i++) {
							ccMailsList[i] = pdlMailsInArrayFormat[i];
						}
					}
						
					requestRequisitionDTO.setCcMailIds(ccMailsList);
					 
					HashSet<String> hashSet = new HashSet<String>(pdlIDsInString);
						 finalMailPDLTo = hashSet.toString();
						
					finalMailPDLTo = finalMailPDLTo.replaceAll("\\[", "").replaceAll("\\]", "");
					
					requestRequisitionDTO.setPdlEmailIds(finalMailPDLTo); //TODO : Samiksha email ids required
					}else{
						requestRequisitionDTO.setPdlEmailIds("");
					}
				}else if (mapEntry.getKey().equals("resourceTypeId")) {
					String resourceType = mapEntry.getValue().toString();
					requestRequisitionSkillDTO.setType(Integer.parseInt(resourceType.trim()));
				}
				
			}
			
		}
		
		
		
		requestRequisitionDTO.setResource(currentResource);
		
		//requestRequisitionDTO.setToMailIds(ArrayUtils.add(requestRequisitionDTO.getToMailIds(), sendMailFrom));
		//requestRequisitionDTO.setSentMailTo(sentMailTo); // TODO: Samiksha
		Date date = new Date();
		Integer requestId = requestRequisitionSkillDTO.getId();
		RequestRequisitionSkillDTO req = new RequestRequisitionSkillDTO();
			if (requestId != null && requestId > 0) { // record for edit
					 req = requestRequisitionService
							.getRequestRecordForEdit(requestId);
					currentResource = req.getRequestRequisition().getResource();
					sender = currentResource.getFirstName() + " " + currentResource.getLastName();
					requestRequisitionDTO.setId(req.getRequestRequisition().getId());
					
					if(null == requestRequisitionDTO.getSentMailTo() || requestRequisitionDTO.getSentMailTo().isEmpty() ){
						requestRequisitionDTO.setSentMailTo(req.getRequestRequisition().getSentMailTo()); // for db save
						String [] sendMailtoIds = req.getRequestRequisition().getSentMailTo().split(",");
						
						ArrayList<Integer> sendMailInintegersFormatInteger = new ArrayList<Integer>();
						
						for (String id : sendMailtoIds) {
							sendMailInintegersFormatInteger.add(Integer.parseInt(id.replaceAll("\\[", "").replaceAll("\\]", "").trim()));
						}
					
						List<String> emailsForCCMail = resourceService.findEmailById(sendMailInintegersFormatInteger);
						emailsForCCMail.add(sendMailFrom);
						String[] emailsArrayForCCMail = new String[emailsForCCMail.size()];
						
						for (int i=0; i<emailsForCCMail.size(); i++) {
							emailsArrayForCCMail[i] = emailsForCCMail.get(i);
						}
						
						requestRequisitionDTO.setToMailIds(emailsArrayForCCMail); //for mail
						
					}
					if(null == requestRequisitionDTO.getNotifyMailTo() || requestRequisitionDTO.getNotifyMailTo().isEmpty() ){
						requestRequisitionDTO.setNotifyMailTo(req.getRequestRequisition().getNotifyMailTo()); //for DB
						
						String [] NotifyMailtoIds = req.getRequestRequisition().getNotifyMailTo().split(",");
						
						ArrayList<Integer> notifyMailInintegersFormatInteger = new ArrayList<Integer>();
						
						for (String id : NotifyMailtoIds) {
							notifyMailInintegersFormatInteger.add(Integer.parseInt(id.replaceAll("\\[", "").replaceAll("\\]", "").trim()));
						}
					
						List<String> emailsForCCMail = resourceService.findEmailById(notifyMailInintegersFormatInteger);
						String[] emailsArrayForCCMail = new String[emailsForCCMail.size()];
						
						for (int i=0; i<emailsForCCMail.size(); i++) {
							emailsArrayForCCMail[i] = emailsForCCMail.get(i);
						}
						
						requestRequisitionDTO.setCcMailIds(emailsArrayForCCMail); //for mail
						
					}
					if(null == requestRequisitionDTO.getPdlEmailIds()  || requestRequisitionDTO.getPdlEmailIds().isEmpty()){
						requestRequisitionDTO.setPdlEmailIds(req.getRequestRequisition().getPdlEmailIds());
						
						String [] pdlMailtoIds = req.getRequestRequisition().getPdlEmailIds().split(",");
						
						ArrayList<Integer> pdlMailInintegersFormatInteger = new ArrayList<Integer>();
						
						for (String id : pdlMailtoIds) {
							pdlMailInintegersFormatInteger.add(Integer.parseInt(id.replaceAll("\\[", "").replaceAll("\\]", "").trim()));
						}
					
						List<String> pdlMailsForCCMail = pdlEmailGroupService.findPdlByIds(pdlMailInintegersFormatInteger);
						
						
						List<String> ccMailsInList =  new ArrayList<String>();
								
						ccMailsInList.addAll(Arrays.asList(requestRequisitionDTO.getCcMailIds()));
						
						ccMailsInList.addAll(pdlMailsForCCMail);
						
						
						String[] arrayForCCMail = new String[ccMailsInList.size()];
						
						for (int i=0; i<ccMailsInList.size(); i++) {
							arrayForCCMail[i] = ccMailsInList.get(i);
						}
						requestRequisitionDTO.setCcMailIds(arrayForCCMail); //for mail
					}
					if(null == requestRequisitionSkillDTO.getKeyInterviewersOne() || requestRequisitionSkillDTO.getKeyInterviewersOne().isEmpty()  ){
						requestRequisitionSkillDTO.setKeyInterviewersOne(req.getKeyInterviewersOne());
					}
					if(null == requestRequisitionSkillDTO.getKeyInterviewersTwo() || requestRequisitionSkillDTO.getKeyInterviewersTwo().isEmpty()){
						requestRequisitionSkillDTO.setKeyInterviewersTwo(req.getKeyInterviewersTwo());
					}
					
			}
		

		
		String mailId = "";
		String empId = "";
		String pdlEmailIds = "";
		String[] sentEmpId = null;
		String[] sentMailTo = null;
		if (tomailIDs != null && tomailIDs.length > 0) {

			for (String st : tomailIDs) {
				String[] str = st.split("-");
				if (str[1] != null) {
					if(!"0".equals(str[0])){
						empId += str[0] + ",";
					}
					if(!"no".equals(str[1])){
						mailId += str[1] + ",";
					}
				}
			
			}
			mailId+=sendMailFrom + ","; //mail to requester
			empId+=currentResource.getEmployeeId() + ",";
			for (String st : pdlList) {
				
				String str = st.substring(st.indexOf("-")+1, st.length()); 
				System.out.println("******************email id for pdl****************"+str);
				if (str != null) {
				//	empId += str[0] + ",";   //commented by Samiksha
					mailId += str + ",";
				}
			}
		}
		if (mailId != null && mailId != "" && empId != null && empId != "") {
			int lenMail = mailId.length();
			int lenemp = empId.length();
			String mailStr = mailId.substring(0, lenMail - 1);
			String empStr = empId.substring(0, lenemp - 1);
			sentMailTo = mailStr.split(",");
			sentEmpId = empStr.split(",");
		}
		// for Mail Sent to End
		// for CC Mail Start
		String mailCCId = "";
		String empCCId = "";
		String[] sentMailCCTo = null;
		String[] sentEmpCCId = null;
		if (ccmailIDs != null && ccmailIDs.length > 0) {
			for (String st : ccmailIDs) {
				String[] str = st.split("-");
				if (str[1] != null) {
					if (!str[0].equals("0")) {
						empCCId += str[0] + ",";
					}
					mailCCId += str[1] + ",";
				}
			}
		}
		if (mailCCId != null && mailCCId != "" && empCCId != null && empCCId != "") {
			int lenCCMail = mailCCId.length();
			int lenCCemp = empCCId.length();
			String mailCCStr = mailCCId.substring(0, lenCCMail - 1);
			String empCCStr = empCCId.substring(0, lenCCemp - 1);
			sentMailCCTo = mailCCStr.split(",");
			sentEmpCCId = empCCStr.split(",");
		}

		if (pdlList != null && pdlList.length > 0) {
			for (String pdl : pdlList) {
				if (null != pdl && !"".equals(pdl.trim())) {
					pdlEmailIds += pdl.trim() + ",";
				}
			}
			pdlEmailIds = pdlEmailIds.substring(0, pdlEmailIds.length() - 1);
		}
		// for CC Mail End
		// for check no duplicate Emp Id
		ArrayList<String> sentTo = new ArrayList<String>();
		ArrayList<String> notifyTo = new ArrayList<String>();
		if (sentEmpId != null) {
			List<String> empSentList = Arrays.asList(sentEmpId);
			sentTo.addAll(empSentList);
		}
		if (sentEmpCCId != null) {
			List<String> sentEmpCCList = Arrays.asList(sentEmpCCId);
			notifyTo.addAll(sentEmpCCList);
		} else{
			notifyTo.add(currentResource.getEmployeeId().toString());
		}
		HashSet<String> hashSet = new HashSet<String>(sentTo);
		String finalMailSentTo = hashSet.toString();
		finalMailSentTo = finalMailSentTo.replaceAll("\\[", "").replaceAll("\\]", "");

		hashSet = new HashSet<String>(notifyTo);
		String finalMailNotifyTo = hashSet.toString();
		finalMailNotifyTo = finalMailNotifyTo.replaceAll("\\[", "").replaceAll("\\]", "");

		String tomailIDsList[] = new String[(sentMailTo.length) + 1];
		for (int i = 0; i < sentMailTo.length; i++) {
			tomailIDsList[i] = new String();
			tomailIDsList[i] = sentMailTo[i];
		}
		tomailIDsList[sentMailTo.length] = sendMailFrom;

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yy");
		requestRequisitionDTO.setNotifyMailTo(finalMailNotifyTo);
		requestRequisitionDTO.setSentMailTo(finalMailSentTo);
		requestRequisitionDTO.setDate(simpleDateFormat.format(date));
		requestRequisitionDTO.setProjectBU(currentBU);
		requestRequisitionDTO.setResource(currentResource);// Indenter
		requestRequisitionDTO.setPdlEmailIds(pdlEmailIds);
		Project project = new Project();
		project.setId(projectId);
		Customer cust = new Customer();
		cust.setId(customerId);
		CustomerGroup custGrp = new CustomerGroup();
		// New Table(customer_grp) hence no data. Until group not created for all
		// clients
		if ("Select".equals(groupId) || "NA".equals(groupName)) {
			custGrp = customerGroupService.findById(0).get(0);
		} else {
			custGrp.setGroupId(Integer.parseInt(groupId));
			custGrp.setCustomerGroupName(groupName);
		}
		requestRequisitionDTO.setGroup(custGrp);
		requestRequisitionDTO.setCustomer(cust);
		requestRequisitionDTO.setProject(project);
		
		
		
		if(null != bghFile){
			requestRequisitionDTO.setbGHFile(bghFile[0]);
			requestRequisitionDTO.setbGHApprovalFileName(bghFile[0].getOriginalFilename());
		}else{
			requestRequisitionDTO.setbGHFile(req.getRequestRequisition().getbGHFile());
			requestRequisitionDTO.setbGHApprovalFileName(req.getRequestRequisition().getbGHFile().getOriginalFilename());
		}
		
		if(buhFile != null){
			requestRequisitionDTO.setbUHFile(buhFile[0]);
			requestRequisitionDTO.setbUHApprovalFileName(buhFile[0].getOriginalFilename());
		}else{
			if(requestRequisitionDTO.getRequiredFor().equalsIgnoreCase("odc")){
				requestRequisitionDTO.setbUHFile(req.getRequestRequisition().getbUHFile());
				requestRequisitionDTO.setbUHApprovalFileName(req.getRequestRequisition().getbUHFile().getOriginalFilename());
			}
		}
		
		RequestRequisitionFormDTO requestRequisitionFormDTO = new RequestRequisitionFormDTO();
		requestRequisitionFormDTO.setRequestRequisitionDto(requestRequisitionDTO);
		requestRequisitionFormDTO.setRequestRequisitionSkillDto(Arrays.asList(requestRequisitionSkillDTO));
		
		//requestRequisitionDTO.setToMailIds(tomailIDsList);
		//requestRequisitionDTO.setCcMailIds(sentMailCCTo);
		requestRequisitionDTO.setSender(sender);
		requestRequisitionDTO.setCurrentBU(currentBU);
		requestRequisitionDTO.setSendMailFrom(sendMailFrom);
		//requestRequisitionDTO.setProjectName(projectName);
		//requestRequisitionDTO.setGroupName(groupName);
		//requestRequisitionDTO.setCustomerName(customerName);

		return requestRequisitionFormDTO;

		
	}*/
	
	@RequestMapping(value = "getProjects/{clientId}", method = RequestMethod.GET)
	@ResponseBody
	public List<Project> getProjectByClient(@PathVariable("clientId") Integer clientId) {
		return projectService.findAllProjectByClientId(clientId);
	}

	@RequestMapping(value = "getGroupBasedOnCustomerId/{clientId}", method = RequestMethod.GET)
	@ResponseBody
	public List<CustomerGroup> getGroupBasedOnCustomerId(@PathVariable("clientId") Integer clientId) {
		return customerGroupService.findCustomerGroupByCustomerId(clientId);
	}
	
	@RequestMapping(value = "getEmployeeDetailsById/{emplID}", method = RequestMethod.GET)
	@ResponseBody
	public EditProfileDTO getEmployeeDetailsById(@PathVariable("emplID") Integer emplID) {
		return resourceService.getResourceDetailsByEmployeeId(emplID);
	}
	

	@RequestMapping(value = "getBUHByProjectId/{projectId}", method = RequestMethod.GET)
	@ResponseBody
	public EditProfileDTO getBUHeadByProjectId(@PathVariable("projectId") Integer projectId) {
		EditProfileDTO resource = projectService.getBUHeadByProjectId(projectId);
		return resource;
	}

	@RequestMapping(value = "getBGHByProjectId/{projectId}", method = RequestMethod.GET)
	@ResponseBody
	public EditProfileDTO getBGHeadByProjectId(@PathVariable("projectId") Integer projectId) {
		EditProfileDTO resource = projectService.getBGHeadByProjectID(projectId);
		return resource;
	}
	
	//Method to fetch active users
	@RequestMapping(value="/activeUserList", method = RequestMethod.GET)
	public ResponseEntity<String> getActiveUserList(@RequestParam(value="userInput", required=false) String userInput)  {
		logger.info("--------getActiveUserList method starts--------");
		
	
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		JSONObject resultJSON = new JSONObject();
		
		List<Resource> resourceList = genericSearch.getObjectsWithSearchAndPaginationForResource(userInput);
		resultJSON.put("activeUserList", Resource.toJsonString(resourceList));
		
        logger.info("--------getActiveUserList method ends--------");
        return new ResponseEntity<String>(resultJSON.toString(), headers, HttpStatus.OK);
		
	}
}
