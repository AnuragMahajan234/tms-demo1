
package org.yash.rms.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.sql.Blob;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
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
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.yash.rms.domain.AllocationType;
import org.yash.rms.domain.Competency;
import org.yash.rms.domain.PDLEmailGroup;
import org.yash.rms.domain.Project;
import org.yash.rms.domain.RequestRequisition;
import org.yash.rms.domain.RequestRequisitionResource;
import org.yash.rms.domain.RequestRequisitionResourceStatus;
import org.yash.rms.domain.RequestRequisitionSkill;
import org.yash.rms.domain.Resource;
import org.yash.rms.domain.ResourceAllocation;
import org.yash.rms.domain.ResourceComment;
import org.yash.rms.dto.CustomerDTO;
import org.yash.rms.dto.ForwardRRFDTO;
import org.yash.rms.dto.RequestRequisitionDashboardInputDTO;
import org.yash.rms.dto.RequestRequisitionResourceFormDTO;
import org.yash.rms.dto.RequestRequisitionSkillDTO;
import org.yash.rms.dto.RequisitionResourceDTO;
import org.yash.rms.dto.ResourceAndPDLInputDTO;
import org.yash.rms.dto.ResourceCommentDTO;
import org.yash.rms.dto.ResourceStatusDTO;
import org.yash.rms.exception.ControllerException;
import org.yash.rms.exception.DAOException;
import org.yash.rms.exception.RMSServiceException;
import org.yash.rms.forms.UpdateResourceStatusForm;
import org.yash.rms.json.mapper.JsonObjectMapper;
import org.yash.rms.mapper.RequestRequisitionSkillMapper;
import org.yash.rms.report.dto.RequestRequisitionReport;
import org.yash.rms.service.PDLEmailGroupService;
import org.yash.rms.service.RequestRequisitionDashboardService;
import org.yash.rms.service.RequestRequisitionReportService;
import org.yash.rms.service.RequestRequisitionResourceService;
import org.yash.rms.service.RequestRequisitionService;
import org.yash.rms.service.ResourceAllocationService;
import org.yash.rms.service.ResourceService;
import org.yash.rms.service.RmsCRUDService;
import org.yash.rms.util.Constants;
import org.yash.rms.util.DateUtil;
import org.yash.rms.util.ExceptionConstant;
import org.yash.rms.util.GeneralFunction;
import org.yash.rms.util.GenericSearch;
import org.yash.rms.util.ResourceSearchCriteria;
import org.yash.rms.util.UserUtil;
import org.yash.rms.wrapper.RequestRequisitionWrapper;
import org.yash.rms.wrapper.ResourceInterviewWrapper;

import com.google.gson.Gson;

import flexjson.JSONSerializer;
import flexjson.transformer.DateTransformer;
import freemarker.core.ParseException;

@Controller
@RequestMapping("/requestsReports")
public class RequestRequisitionDashboardController {

	@Autowired
	RequestRequisitionDashboardService requestRequisitionDashboardService;
	
	@Autowired
	PDLEmailGroupService pdlEmailGroupService;

	@Autowired
	@Qualifier("ResourceService")
	ResourceService resourceService;

	@Autowired
	private UserUtil userUtil;

	@Autowired
	@Qualifier("ResourceAllocationService")
	ResourceAllocationService resourceAllocationService;

	@Autowired
	@Qualifier("CustomerService")
	RmsCRUDService<CustomerDTO> customerService;
	@Autowired
	@Qualifier("RequestRequisitionReportService")
	private RequestRequisitionReportService requestRequisitionReportService;
	
	@Autowired
	@Qualifier("requestRequisitionResourceService")
	private RequestRequisitionResourceService requestRequisitionResourceService;
	
	@Autowired
	@Qualifier("RequestRequisitionService")
	RequestRequisitionService requestRequisitionService;
	
	@Autowired
	JsonObjectMapper<ResourceInterviewWrapper> jsonObjectMapper;
	
	@Autowired
	RequestRequisitionSkillMapper requestRequisitionSkillMapper;
	
	@Autowired
	GenericSearch genericSearch;
	
	private static final Logger logger = LoggerFactory.getLogger(RequestRequisitionDashboardController.class);
	
	List<ResourceStatusDTO> resourceStatuslistOld = new ArrayList<ResourceStatusDTO>();	

	@RequestMapping(method = RequestMethod.GET)
	public String list(Model uiModel) throws Exception {
		logger.info("------RequestReportController list method start------");
		try {
			Integer userId = userUtil.getCurrentLoggedInUseId();
			Resource resource = userUtil.getLoggedInResource();
			
			
			if (resource.getUploadImage() != null && resource.getUploadImage().length > 0) {
				byte[] encodeBase64UserImage = Base64.encodeBase64(resource.getUploadImage());
				String base64EncodedUser = new String(encodeBase64UserImage, "UTF-8");
				uiModel.addAttribute("UserImage", base64EncodedUser);
			} else {
				uiModel.addAttribute("UserImage", "");
			}
			
			//uiModel.addAttribute(Constants.RepotList, requestRequisitionDashboardService.getSkillRequestReport(userId, resource.getUserRole()));
			
			
			uiModel.addAttribute("requestRequisitionDashboardDTO", requestRequisitionDashboardService.getRequestRequisitionDashboard(userId, resource.getUserRole()));
			uiModel.addAttribute("resourceAndPDLInputDTO", new ResourceAndPDLInputDTO());
			//  uiModel.addAttribute(Constants.MAIL_LIST, mailList);
			 // uiModel.addAttribute("skillResStatusList", skillResStatusList);
			 
			uiModel.addAttribute("firstName", resource.getFirstName() + " " + resource.getLastName());
			uiModel.addAttribute("designationName", resource.getDesignationId().getDesignationName());
			Calendar cal = Calendar.getInstance();
			cal.setTime(resource.getDateOfJoining());
			int m = cal.get(Calendar.MONTH) + 1;
			String months = new DateFormatSymbols().getMonths()[m - 1];
			int year = cal.get(Calendar.YEAR);
			uiModel.addAttribute("DOJ", months + "-" + year);
			
			uiModel.addAttribute("ROLE", resource.getUserRole());
			System.out.println("user role for Dashboard : "+ resource.getUserRole());
			
			uiModel.addAttribute("resumes", new ArrayList<MultipartFile>());
			
			logger.info("------RequestReportController list method end------");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "requestsReports/RequestResourceDashboard";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/findActiveOrAll")
	public ResponseEntity<String> findActiveOrAll(Model uiModel, @RequestParam("option") String opt) throws Exception {
		logger.info("------RequestRequisitionDashboardController list method start------");
		List<RequestRequisitionSkill> reqList = new ArrayList<RequestRequisitionSkill>();
		Integer userId = userUtil.getCurrentLoggedInUseId();
		Resource resource = userUtil.getLoggedInResource();
		try {
			reqList = requestRequisitionDashboardService.getReport(opt, userId, resource.getUserRole());
			logger.info("------RequestRequisitionDashboardController list method end------");
		} catch (Exception e) {
			e.printStackTrace();
		}
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		// convert your list to json
		ResponseEntity<String> entity = new ResponseEntity<String>(
				new JSONSerializer().include("requestRequisition.date", "requestRequisition.id", "requestRequisition.projectBU", "requestRequisition.resource.userName", "requestRequisition.projectName", "id", "skill.skill",
						"designation.designationName", "experience", "allocationType.allocationType", "type", "timeFrame", "noOfResources", "fulfilled", "remaining", "comments", "requestRequisition.comments",
						"requestRequisition.project.projectName", "requestRequisition.project.id").exclude("*").transform(new DateTransformer(Constants.DATE_PATTERN), Date.class).serialize(reqList),
				headers, HttpStatus.OK);

		return entity;
	}

/*	@RequestMapping(value = "/saveRequest1", method = RequestMethod.POST)
	public ResponseEntity<String> saveRequest(@RequestParam(value ="resumeNames" ,required=false) String[] resumeNames, 
			@RequestParam(value ="reqID" ,required=false) String extraField, @RequestParam(value ="resumes" ,required=false) MultipartFile[] files,
			@RequestParam(value ="skillReqId" ,required=false) String skillReqId, @RequestParam(value = "fullfillResLen" ,required=false) Integer fullfillResLen,
			@RequestParam(value = "comments" ,required=false) String comments, @RequestParam(value = "resourceId" ,required=false) String resourceIds, 
			@RequestParam(value = "mailTo" ,required=false) String[] mailTo, @RequestParam(value = "extraMailTo" ,required=false) String[] extraMailTo, 
			@RequestParam(value = "externalNames" ,required=false) String[] externalNames) throws Exception {
		
		HttpHeaders headers = new HttpHeaders();
		logger.info("------RequestRequisitionDashboardController saveRequest method start------");
		String[] ids = (extraField.split(","));
		int index = ids.length;
		try{
			requestRequisitionDashboardService.saveSkillRequestReport(ids[index - 1], files, skillReqId, fullfillResLen, comments, resourceIds, externalNames, resumeNames, mailTo, extraMailTo);
			logger.info("------RequestRequisitionDashboardController saveRequest method end------");
			headers.add("Content-Type", "application/json; charset=utf-8");
		return new ResponseEntity<String>(headers, HttpStatus.OK);
		} catch (Exception exception){
			return new ResponseEntity<String>(headers, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	*/
	@RequestMapping(value ="/saveRequest" , method= RequestMethod.POST)
	public ResponseEntity<String> saveRequest(@ModelAttribute RequestRequisitionDashboardInputDTO dto, BindingResult result){
		HttpHeaders headers = new HttpHeaders();
		logger.info("------RequestRequisitionDashboardController saveRequest method start------");
	try{	
	    List<ResourceStatusDTO> previousResourcelist=new ArrayList<ResourceStatusDTO>();
		    List<ResourceStatusDTO> existingResourceList = requestRequisitionResourceService.getRequestRequisitionResources(null, null, dto.getSkillReqId());
			for(ResourceStatusDTO resourceDto:existingResourceList){
				if(null!=resourceDto.getEmployeeId()){
					Resource resource=resourceService.find(resourceDto.getEmployeeId());
					resourceDto.setTotalExperience(Double.toString(resource.getTotalExper()));
					resourceDto.setEmailId(resource.getEmailId());
					resourceDto.setContactNumber(resource.getContactNumber());
					resourceDto.setSkills(resource.getCompetency().getSkill());
				}
				previousResourcelist.add(resourceDto);
			}
		    Integer skillRequestId =requestRequisitionDashboardService.saveSkillRequestReport(dto);		
			/* Anjana */
			List<ResourceStatusDTO> resourceStatuslistNew = new ArrayList<ResourceStatusDTO>();			
			RequestRequisitionResourceFormDTO requestRequisitionResourceFormdto = requestRequisitionDashboardService.getRequestRequisitionResourceDTO(dto.getSkillReqId());
			UpdateResourceStatusForm statusFormform = getUpdateResourceStatusForm(requestRequisitionResourceFormdto,dto.getSkillReqId());
			resourceStatuslistNew= statusFormform.getResourceStatuslist();	
			/* Anjana */
			
			requestRequisitionDashboardService.sendEmailForSkillRequestReport(dto, skillRequestId, previousResourcelist, resourceStatuslistNew, requestRequisitionResourceFormdto);
			logger.info("------RequestRequisitionDashboardController saveRequest method end------");
			headers.add("Content-Type", "application/json; charset=utf-8");
			return new ResponseEntity<String>(headers, HttpStatus.OK);
		} catch (Exception exception){
			logger.error("Exception occured :" + exception.getMessage());
			exception.printStackTrace();
			return new ResponseEntity<String>(headers, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value="/",headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> mail(@RequestParam("a") String a,@RequestParam("abc") String b){
		HttpHeaders headers = new HttpHeaders();
		logger.info("------RequestRequisitionDashboardController saveRequest method start------");
		try{
			//Integer skillRequestId =requestRequisitionDashboardService.saveSkillRequestReport(dto);
			//requestRequisitionDashboardService.sendEmailForSkillRequestReport(dto, skillRequestId);
			logger.info("------RequestRequisitionDashboardController saveRequest method end------");
			System.out.println(a+b);
			headers.add("Content-Type", "application/json; charset=utf-8");
			return new ResponseEntity<String>(headers, HttpStatus.OK);
		} catch (Exception exception){
			return new ResponseEntity<String>(headers, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/fileUpload", method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<String> fileUpload(@RequestParam("tracks") MultipartFile[] file) throws IOException {
		// Save file on system
		/*
		 * if (!file.getOriginalFilename().isEmpty()) { BufferedOutputStream
		 * outputStream = new BufferedOutputStream( new FileOutputStream(new
		 * File("D:/Upload", file.getOriginalFilename())));
		 * outputStream.write(file.getBytes()); outputStream.flush();
		 * outputStream.close(); } else { return new ResponseEntity<String>(
		 * "Invalid file.", HttpStatus.BAD_REQUEST); }
		 */

		return new ResponseEntity<String>("File Uploaded Successfully.", HttpStatus.OK);
	}

	@RequestMapping(value = "/updateResourceRequestWithStatus1", method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<String> updateResourceRequestWithStatus(@RequestBody String requestJSON) throws Exception {
		logger.info("------RequestRequisitionDashboardController saveRequest method start------");

		List<RequestRequisitionResource> list = requestRequisitionDashboardService.updateResourceRequestWithStatus(requestJSON);

		if (list != null && list.size() > 0) {
			Integer projectId = null, allocationId = null;
			Integer skillResourceId;
			for (int n = 0; n < list.size(); n++) {
				RequestRequisitionResource object = list.get(n);
				AllocationType allocationTypeId = new AllocationType();
				allocationTypeId = object.getAllocation();
				skillResourceId = (Integer) object.getId();

				ResourceAllocation resourceAllocation = new ResourceAllocation();

				resourceAllocation.setAllocStartDate(object.getAllocationDate());

				resourceAllocation.setAllocationTypeId(allocationTypeId);

				Project project = new Project();
				project.setId(object.getId());// now i have add Project Id in
												// SkillResource
				resourceAllocation.setProjectId(project);
				Resource resource1 = new Resource();
				resource1 = object.getResource();
				// resource1.setEmployeeId(resource1.getEmployeeId());
				resourceAllocation.setEmployeeId(resource1);

				// here projectId,ResourceId,AllocationId,AllocationDate are
				// available
				boolean flag = true;
				try {
					List<org.yash.rms.domain.ResourceAllocation> allResourceAllocations = resourceAllocationService.findResourceAllocationsByEmployeeId(resourceAllocation.getEmployeeId());
					/** Start - Modified the code to fix defect #133 */
					/*
					 * Check if any existing project allocation for employee
					 * overlaps with this new allocation of same project
					 */
					for (org.yash.rms.domain.ResourceAllocation allocation : allResourceAllocations) {
						Date newstartdate = resourceAllocation.getAllocStartDate();
						Date newEndDate = resourceAllocation.getAllocEndDate();
						if ((!(allocation.getId().equals(resourceAllocation.getId())) && allocation.getProjectId().getId().equals(resourceAllocation.getProjectId().getId()))
								&& allocation.isOverlaps(newstartdate, newEndDate)) {
							flag = false;
							break;
						}
					}
					for (org.yash.rms.domain.ResourceAllocation allocation : allResourceAllocations) {
						if (!(allocation.getId().equals(resourceAllocation.getId()))) {

							if (allocation.getCurProj() != null && allocation.getCurProj() == true && resourceAllocation.getCurProj() != null && resourceAllocation.getCurProj() == true) {

								allocation.setCurProj(false);

								resourceAllocationService.saveOrupdate(allocation);
							}
						}
					}

					if (flag) {

						if (resourceAllocation.getId() != null) {

							ResourceAllocation resAlloc = resourceAllocationService.findResourceAllocation(resourceAllocation.getId());

							if (resAlloc.getDesignation() != null) {
								resourceAllocation.setDesignation(resAlloc.getDesignation());
							}
							if (resAlloc.getGrade() != null) {
								resourceAllocation.setGrade(resAlloc.getGrade());

							}

							/*
							 * if (resAlloc.getTeam() != null) {
							 * resourceAllocation.setTeam(resAlloc.getTeam());
							 * 
							 * }
							 */
							if (resAlloc.getLocation() != null) {
								resourceAllocation.setLocation(resAlloc.getLocation());

							}
							if (resAlloc.getBuId() != null) {
								resourceAllocation.setBuId(resAlloc.getBuId());
							}
							if (resAlloc.getCurrentBuId() != null) {
								resourceAllocation.setCurrentBuId(resAlloc.getCurrentBuId());
							}
							if (resAlloc.getOwnershipTransferDate() != null) {
								resourceAllocation.setOwnershipTransferDate(resAlloc.getOwnershipTransferDate());
							}
							if (resAlloc.getCurrentReportingManager() != null) {
								resourceAllocation.setCurrentReportingManager(resAlloc.getCurrentReportingManager());
							}
							if (resAlloc.getCurrentReportingManagerTwo() != null) {
								resourceAllocation.setCurrentReportingManagerTwo(resAlloc.getCurrentReportingManagerTwo());
							}
							resourceAllocation.setUpdatedBy(UserUtil.userContextDetailsToResource(UserUtil.getCurrentResource()));

							resourceAllocation.setLastupdatedTimestamp(new Date());

							resourceAllocationService.saveOrupdate(resourceAllocation);
						} else {
							resourceAllocation.setAllocatedBy(UserUtil.userContextDetailsToResource(UserUtil.getCurrentResource()));
							resourceAllocation.setUpdatedBy(UserUtil.userContextDetailsToResource(UserUtil.getCurrentResource()));

							Resource resource = resourceService.find(resourceAllocation.getEmployeeId().getEmployeeId());
							resourceAllocation.setDesignation(resource.getDesignationId());
							resourceAllocation.setGrade(resource.getGradeId());
							resourceAllocation.setBuId(resource.getBuId());
							resourceAllocation.setCurrentBuId(resource.getCurrentBuId());
							resourceAllocation.setLocation(resource.getLocationId());
							resourceAllocation.setCurrentReportingManager(resource.getCurrentReportingManager());
							resourceAllocation.setCurrentReportingManagerTwo(resource.getCurrentReportingManagerTwo());
							resourceAllocation.setOwnershipTransferDate(resource.getTransferDate());

							resourceAllocationService.saveOrupdate(resourceAllocation);
						}
						updateAllocationSeqForResourceAllocations(resourceAllocation.getEmployeeId());

					}
				} catch (RuntimeException exception) {
					logger.error("RuntimeException occured in createFromJson method of Resource allocation controller:" + exception);
					throw exception;
				} catch (Exception e) {
					logger.error("Exception occured in createFromJson method of Resource allocation controller:" + e);
					throw e;
				}

				// }
			}
		}
		logger.info("------RequestRequisitionDashboardController saveRequest method end------");
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		return new ResponseEntity<String>(headers, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/updateResourceRequestWithStatus", method = RequestMethod.POST)
	public String updateResourceRequestWithStatus(@ModelAttribute UpdateResourceStatusForm updateResourceStatusForm ) throws Exception {
	
		requestRequisitionDashboardService.updateResourceRequestWithStatus(updateResourceStatusForm.getRequestRequisitionSkillId(),updateResourceStatusForm.getResourceStatuslist() );
		
		return "redirect:/requestsReports/resources?requestRequisitionId="+updateResourceStatusForm.getRequestRequisitionSkillId();
		
	}
	
	
	@RequestMapping(value="/updateResourcesWithStatus", consumes="application/json", method= RequestMethod.POST)
	@ResponseBody
	@ResponseStatus(value=HttpStatus.OK)
	public String updateResourcesWithStatus(@RequestBody String jsonString) throws Exception {
					
		ObjectMapper mapper= new ObjectMapper();
		
		List<ResourceStatusDTO> statusDTOs =mapper.readValue(jsonString,  new TypeReference<List<ResourceStatusDTO>>() { });
		requestRequisitionDashboardService.updateResourceRequestWithStatus(Integer.parseInt(statusDTOs.get(0).getSkillReqId()),statusDTOs );
		
		List<RequestRequisitionSkill> skillReuestList=requestRequisitionDashboardService.getRequestRequisitionSkillByReqSkillId(Integer.parseInt(statusDTOs.get(0).getSkillReqId()));
				
		String jsonInString = RequestRequisitionSkill.toJsonArray(skillReuestList);
		
		return jsonInString;
	
	}

	@RequestMapping(value = "/saveReport")
	public ModelAndView saveReport(Model model, @RequestParam("option") String opt) {
		String fileName = "RequestReport_" + new Timestamp(new Date().getTime());
		List<String> colNames = new ArrayList<String>();
		Integer userId = userUtil.getCurrentLoggedInUseId();
		Resource resource = userUtil.getLoggedInResource();
		colNames.add("Date");
		colNames.add("BU");
		colNames.add("Indentor's Name");
		colNames.add("Project Name");
		colNames.add("Skills");
		colNames.add("Designation");
		colNames.add("Experience");
		colNames.add("Allocation Type");
		colNames.add("Type");
		colNames.add("Time-Frame");
		colNames.add("Resource(s) Req");
		colNames.add("Req fulfilled");
		colNames.add("Need To Fulfill");
		colNames.add("Comments");
		colNames.add("Indentor's Comments");

		Map<String, Object> modelMap = new HashMap<String, Object>();
		modelMap.put("dataList", requestRequisitionDashboardService.getReport(opt, userId, resource.getUserRole()));
		modelMap.put("fileName", fileName);
		modelMap.put("colNames", colNames);

		return new ModelAndView("excelView", modelMap);

	}

	@RequestMapping(value = "/showPDFReport")
	public ModelAndView saveReport(Model model, @RequestParam("reqId") Integer reqId) {
		String fileName = "RequestReport_" + new Timestamp(new Date().getTime());
		Map<String, Object> modelMap = new HashMap<String, Object>();
		modelMap.put("dataList", requestRequisitionDashboardService.getRequestRequisitionSkillBySkillId(reqId));
		modelMap.put("fileName", fileName);
		return new ModelAndView("pdfRRFlView", modelMap);
	}

	@RequestMapping(value = "/deleteSkillRequestResource", method = RequestMethod.POST)
	public ResponseEntity<String> deleteSkillRequestResource(@RequestParam(value = "id") int id, @RequestParam(value = "skillId") int skillId) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		boolean isDeletedSuccess = requestRequisitionDashboardService.deleteSkillRequestResource(id,skillId);
		if (isDeletedSuccess) {
			boolean b = requestRequisitionDashboardService.reduceFullfilledSkillRequest(skillId);
			return new ResponseEntity<String>(headers, HttpStatus.OK);
		} else {
			return new ResponseEntity<String>(headers, HttpStatus.EXPECTATION_FAILED);
		}
	}

	@RequestMapping(value = "/updateSkillRequestStatus", method = RequestMethod.POST)
	public ResponseEntity<String> updateSkillRequestStatus(@RequestParam(value = "id") Integer id, @RequestParam(value = "statusId") Integer statusId) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		boolean isDeletedSuccess = requestRequisitionDashboardService.updateSkillRequestStatus(id, statusId);
		if (isDeletedSuccess) {

			return new ResponseEntity<String>(headers, HttpStatus.OK);
		} else {
			return new ResponseEntity<String>(headers, HttpStatus.EXPECTATION_FAILED);
		}
	}

	@RequestMapping(value = "/addResourceWithSkillReqId", method = RequestMethod.POST)
	@ResponseBody
	public  ResponseEntity<String> addResourceWithSkillReqId(@RequestParam(value = "id") Integer id, Model uiModel) throws Exception {
		HttpHeaders headers = new HttpHeaders();
		ResponseEntity response1 = null;
		headers.add("Content-Type", "application/json; charset=utf-8");
		try {
			List<RequestRequisitionSkill> skillReuestList = requestRequisitionDashboardService.addResourceWithSkillReqId(id);
			//uiModel.addAttribute("requestRequisitionResourceList", requestRequisitionResourceList);
			
			response1 = new ResponseEntity<String>(RequestRequisitionSkill.toJsonArray(skillReuestList), headers, HttpStatus.OK);
			System.out.println(response1.toString());
			/*
			 * List<Resource> bgAdmin = resourceService
			 * .findResourcesByBGADMINROLE(); List<Resource> Admin =
			 * resourceService.findResourcesByADMINROLE(); List<Resource>
			 * mailList = new ArrayList<Resource>(); mailList.addAll(bgAdmin);
			 * mailList.addAll(Admin); uiModel.addAttribute(Constants.MAIL_LIST,
			 * mailList);
			 */
		} catch (RuntimeException exception) {
			logger.error("RuntimeException occured in loadResourceData method of ResourceLoanAndTransferController:" + exception);
			throw exception;
		} catch (Exception e) {
			logger.error("Exception occured in loadResourceData method of ResourceLoanAndTransferController:" + e);
			throw e;
		}
		 return response1;
	}

	@RequestMapping(value = "/skillRequestReportPDF", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> skillRequestReportPDF(@RequestParam(value = "id") Integer id, Model uiModel) throws Exception {
		HttpHeaders headers = new HttpHeaders();
		ResponseEntity response1 = null;
		headers.add("Content-Type", "application/pdf; charset=utf-8");

		try {
			List<RequestRequisitionSkill> skillReuestList = requestRequisitionDashboardService.addResourceWithSkillReqId(id);
			response1 = new ResponseEntity<String>(RequestRequisitionSkill.toJsonArray(skillReuestList), headers, HttpStatus.OK);
			System.out.println(response1.toString());

		} catch (RuntimeException exception) {
			logger.error("RuntimeException occured in loadResourceData method of ResourceLoanAndTransferController:" + exception);
			throw exception;
		} catch (Exception e) {
			logger.error("Exception occured in loadResourceData method of ResourceLoanAndTransferController:" + e);
			throw e;
		}
		return response1;
	}

	public void updateAllocationSeqForResourceAllocations(Resource employeeId) throws Exception {
		logger.info("------ResourceAllocationController updateAllocationSeqForResourceAllocations method start------");
		try {
			List<org.yash.rms.domain.ResourceAllocation> resourceAllocation = resourceAllocationService.findResourceAllocationsByEmployeeId(employeeId);
			if (resourceAllocation != null && !resourceAllocation.isEmpty()) {
				Collections.sort(resourceAllocation, new org.yash.rms.domain.ResourceAllocation.ResourceAllocationTimeComparator());
				for (int i = 1; i <= resourceAllocation.size(); i++) {
					org.yash.rms.domain.ResourceAllocation raObj = resourceAllocation.get(i - 1);
					raObj.setAllocationSeq(i);
					resourceAllocationService.saveOrupdate(raObj);

				}

			}
		} catch (RuntimeException e) {
			logger.error("RuntimeException occured in updateAllocationSeqForResourceAllocations method of Resource allocation controller:" + e);
			throw e;
		} catch (Exception exception) {
			logger.error("RuntimeException occured in updateAllocationSeqForResourceAllocations method of Resource allocation controller:" + exception);
			throw exception;
		}
		logger.info("------ResourceAllocationController updateAllocationSeqForResourceAllocations method end------");
	}

	@RequestMapping(value = "/deleteSkillRequest", method = RequestMethod.POST)
	public ResponseEntity<String> deleteSkillRequest(@RequestParam(value = "requestId") int id, @RequestParam(value = "skillRequestId") int skillId) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		logger.info("------RequestRequisitionDashboardController deleteSkillRequest Method Start ------");
		ControllerException controllerException = new ControllerException();
		boolean isSuccess= false;
		Boolean isEmailSent= false;
		String response = null;
		//Call service method to set email content
		try {
				
			isSuccess = requestRequisitionDashboardService.delete(skillId);
			
			//Code - if successfully deleted and email content is set properly then send email to all stakeholders of RRF - start
			if(isSuccess) {
				//send email
				isEmailSent = requestRequisitionDashboardService.sendDeleteRRFEmail(id, skillId);
			}
			//Code - if successfully deleted then send email to all stakeholders of RRF - end
			if(isSuccess && isEmailSent) {
				logger.info("------RequestRequisitionDashboardController deleteSkillRequest Successfully deleted RRF ------");
				controllerException.setErrCode("200");
				controllerException.setErrMsg(ExceptionConstant.OK);
				response = ControllerException.toJsonArray(controllerException);
			}else if(isSuccess) {
				logger.info("------RequestRequisitionDashboardController deleteSkillRequest Successfully deleted RRF BUT Problem sending email ------");
				controllerException.setErrCode("422");
				controllerException.setErrMsg("RRF Deleted But Problem while sending email");
				response = ControllerException.toJsonArray(controllerException);
			}
			
		}catch(Exception exception) {
            if(exception instanceof RMSServiceException) {
                controllerException.setErrCode(((RMSServiceException) exception).getErrCode());
          }else {
          	controllerException.setErrCode("422");
          }
          controllerException.setErrMsg(exception.getMessage());
          logger.error("Exception occured RequestRequisitionDashboardController deleteSkillRequest Not successfully deleted RRF::" + controllerException.getMessage());
          response = ControllerException.toJsonArray(controllerException);
          //return new ResponseEntity<String>(response,headers, HttpStatus.EXPECTATION_FAILED);
          return new ResponseEntity<String>(response, HttpStatus.EXPECTATION_FAILED);
        }
		return new ResponseEntity<String>(response, HttpStatus.OK);
	}

	/**
	 * Upload single file using Spring Controller
	 */
	@RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
	public @ResponseBody String uploadFileHandler(@RequestParam("name") String name, @RequestParam("file") MultipartFile file) {

		if (!file.isEmpty()) {
			try {
				byte[] bytes = file.getBytes();

				// Creating the directory to store file
				String rootPath = System.getProperty("catalina.home");
				File dir = new File(rootPath + File.separator + "tmpFiles");
				if (!dir.exists())
					dir.mkdirs();

				// Create the file on server
				File serverFile = new File(dir.getAbsolutePath() + File.separator + name);
				BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
				stream.write(bytes);
				stream.close();

				logger.info("Server File Location=" + serverFile.getAbsolutePath());

				return "You successfully uploaded file=" + name;
			} catch (Exception e) {
				return "You failed to upload " + name + " => " + e.getMessage();
			}
		} else {
			return "You failed to upload " + name + " because the file was empty.";
		}
	}

	@RequestMapping(value = "/downloadfiles")
	public ResponseEntity<String> downloadResumeFile(HttpServletRequest request, @RequestParam("id") Integer id,@RequestParam("resourceType") String resourceType, HttpServletResponse response) {
		ResponseEntity<String> jspResponse = null;
		HttpHeaders headers = new HttpHeaders();
		Blob blobUploadFile = null;

		Map<String, Object> map = requestRequisitionDashboardService.downloadSelectedResume(id,resourceType);
		blobUploadFile = (Blob) map.get("file");
		response.setHeader("Content-Disposition", "attachment;filename=" + map.get("fileName"));
		response.setHeader("Content-Transfer-Encoding", "binary");
		response.setContentType("application/force-download");
		try {
			if (null != blobUploadFile) {
				InputStream inStream = blobUploadFile.getBinaryStream();
				FileCopyUtils.copy(inStream, response.getOutputStream());
			} else {
				return jspResponse = new ResponseEntity<String>("File Not Found", headers, HttpStatus.EXPECTATION_FAILED);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return jspResponse;
	}
	
	@RequestMapping(value = "/downloadTac")
	public ResponseEntity<String> downloadTacFile(HttpServletRequest request, @RequestParam("id") Integer id, HttpServletResponse response) throws Exception {
		ResponseEntity<String> jspResponse = null;
		HttpHeaders headers = new HttpHeaders();
		Blob blobUploadFile = null;

		Map<String, Object> map = requestRequisitionDashboardService.downloadSelectedTac(id);
		blobUploadFile = (Blob) map.get("file");
		response.setHeader("Content-Disposition", "attachment;filename=" + map.get("fileName"));
		response.setHeader("Content-Transfer-Encoding", "binary");
		response.setContentType("application/force-download");
		try {
			if (null != blobUploadFile) {
				InputStream inStream = blobUploadFile.getBinaryStream();
				FileCopyUtils.copy(inStream, response.getOutputStream());
			} else {
				return jspResponse = new ResponseEntity<String>("File Not Found", headers, HttpStatus.EXPECTATION_FAILED);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return jspResponse;
	}
	
	@RequestMapping(value = "/resources", method = RequestMethod.GET)
	public String getResourcesForRequestRequisition(@RequestParam(value = "requestRequisitionId") int requestRequisitionSkillId, Model uiModel) {
		Resource resource = userUtil.getLoggedInResource();
		RequestRequisitionResourceFormDTO requestRequisitionResourceFormDTO = requestRequisitionDashboardService.getRequestRequisitionResourceDTO(requestRequisitionSkillId);
		
		UpdateResourceStatusForm form = getUpdateResourceStatusForm(requestRequisitionResourceFormDTO,requestRequisitionSkillId);
		//For show user image in header after Submit Resources on Resource Request page -> Arun
		try {
		if (resource.getUploadImage() != null && resource.getUploadImage().length > 0) {
			byte[] encodeBase64UserImage = Base64.encodeBase64(resource.getUploadImage());
			String base64EncodedUser = new String(encodeBase64UserImage, "UTF-8");
			uiModel.addAttribute("UserImage", base64EncodedUser);
		} else {
			uiModel.addAttribute("UserImage", "");
		}
		}catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		uiModel.addAttribute("firstName", resource.getFirstName() + " " + resource.getLastName());
		uiModel.addAttribute("designationName", resource.getDesignationId().getDesignationName());
		uiModel.addAttribute("ROLE", resource.getUserRole());
		//uiModel.addAttribute("activeUserList",resourceService.findActiveResources());
		
		uiModel.addAttribute("requestRequisitionResourceFormDTO",requestRequisitionResourceFormDTO);
		//uiModel.addAttribute("requestRequisitionResourceFormDTO",requestRequisitionResourceFormDTO);
		uiModel.addAttribute("requestRequisitionDashboardInputDTO",new RequestRequisitionDashboardInputDTO());
		
		uiModel.addAttribute("requestStatusForm",form);
		//resourceStatuslistOld= form.getResourceStatuslist();

		return "requestsReports/AddRemoveResourceToRequisition";
	}

	private UpdateResourceStatusForm getUpdateResourceStatusForm(
			RequestRequisitionResourceFormDTO requestRequisitionResourceFormDTO, int requestRequisitionSkillId) {
		UpdateResourceStatusForm form = new UpdateResourceStatusForm();
		List<ResourceStatusDTO> resourceStatuslist = new ArrayList<ResourceStatusDTO>();
				for(RequestRequisitionResource resource :requestRequisitionResourceFormDTO.getRequisitionResourceList()){
					ResourceStatusDTO  dto = new ResourceStatusDTO();
					if(null!=resource.getExternalResourceName()){
						dto.setResourceType("External");
						dto.setEmailId(resource.getEmailId());
						dto.setTotalExperience(resource.getTotalExperience());
						dto.setContactNumber(resource.getContactNumber());
						dto.setSkills(resource.getSkills());
						dto.setResourceName(resource.getExternalResourceName());
					}else{
						Resource resourceInternal = resource.getResource();
						
						dto.setResourceType("Internal");
						
						dto.setEmployeeId(null!= resourceInternal ? resource.getResource().getEmployeeId(): 0);
						dto.setResourceName(resourceInternal.getFirstName() + " " + resourceInternal.getLastName());
						
						//Resource resourceInternal = resourceService.findResourcesByYashEmpIdEquals(resource.getResource().getYashEmpId().toString());
						if(!StringUtils.isEmpty(resourceInternal.getEmailId()))
							dto.setEmailId(resourceInternal.getEmailId());
						if(resourceInternal.getTotalExper()!=0)
							dto.setTotalExperience(Double.toString(resourceInternal.getTotalExper()));
						if(!StringUtils.isEmpty(resourceInternal.getContactNumber()));
							dto.setContactNumber(resourceInternal.getContactNumber());
							Competency competency=resourceInternal.getCompetency();
						if(competency!=null)
						{
							dto.setSkills(competency.getSkill());
						}
						}
					
					
					
					dto.setLocation(resource.getLocation()!=null ? resource.getLocation() : "NA");
					dto.setNoticePeriod(resource.getNoticePeriod()!=null ? resource.getNoticePeriod() : "NA");
					dto.setResourceId(resource.getId()!=null ? resource.getId() : 0);
					dto.setAllocationType(null != resource.getAllocation() ? resource.getAllocation().getId(): 0);
					dto.setProfileStaus(null !=resource.getRequestRequisitionResourceStatus() ? resource.getRequestRequisitionResourceStatus().getId() : 0);
					dto.setAllocationStartDate(DateUtil.getDateInDD_MMM_yyyyFormat(resource.getAllocationDate()));
					dto.setInterviewDate(DateUtil.getDateInDD_MMM_yyyyFormat(resource.getInterviewDate()));

					resourceStatuslist.add(dto);
				}
				form.setResourceStatuslist(resourceStatuslist);
				form.setRequestRequisitionSkillId(requestRequisitionSkillId);
		return form;
	}
	
	@RequestMapping(value="/forwardRRF" , method=RequestMethod.POST, consumes="application/json")
	@ResponseStatus(value=HttpStatus.OK, reason="Successfully RRF forwarded.")
	public void forwardRRFToPDLsAndResources(@RequestBody ForwardRRFDTO forwardRRFDTO){
		logger.info("------RequestRequisitionDashboardController forwardRRFToPDLsAndResources method start------");

		String[] pdlIds=null;
		String[] resourceIds=null;
		
		if(forwardRRFDTO.getPdlList()!=null && !forwardRRFDTO.getPdlList().isEmpty())
			pdlIds=forwardRRFDTO.getPdlList().split(",");
		
		if(forwardRRFDTO.getUserIdList()!=null && !forwardRRFDTO.getUserIdList().isEmpty())
			resourceIds=forwardRRFDTO.getUserIdList().split(",");
		Resource resource = resourceService.getCurrentResource(userUtil.getLoggedInResource().getUserName());
		requestRequisitionDashboardService.sendEmail(resourceIds,pdlIds,forwardRRFDTO.getSkillReqId(),resource, Constants.FORWARD_RRF, forwardRRFDTO.getCommentForwardRRF());
		logger.info("------RequestRequisitionDashboardController forwardRRFToPDLsAndResources method end------");
	}

	// Shankar
		@ResponseBody()
		@RequestMapping(value = "/loadResourceCommentByResourceId", method = RequestMethod.GET)
		public List<ResourceCommentDTO> loadResourceCommentByResourceId(
				@RequestParam(value = "resourceId") int resourceId)
				throws ParseException {
			System.out.println(resourceId);
			List<ResourceCommentDTO> allResourceCommentByResourceId = requestRequisitionDashboardService
					.getAllResourceCommentByResourceId(resourceId);

			return allResourceCommentByResourceId;
		}

		@RequestMapping(value = "/postResourceComment", method = RequestMethod.POST)
		public ResponseEntity<String> postResourceComment(
				@RequestBody ResourceComment resourceComment) {
			System.out.println(resourceComment.getResourceComment() != null);
			System.out.println(resourceComment.getResourceComment().equals(""));
			if (resourceComment.getResourceComment() != null && !resourceComment.getResourceComment().equals("")){
				resourceComment.setCommentBy(userUtil.getLoggedInResource().getEmployeeName());
				
				try {
			
					resourceComment.setComment_Date(new Date());
				} catch (Exception e) {
					e.printStackTrace();
				}

				requestRequisitionDashboardService.saveResourceComment(resourceComment);
			}
			return new ResponseEntity<String>(HttpStatus.OK);
		}
		// Shankar
		/*@RequestMapping(value="/positionDashboard", method = RequestMethod.GET)
		public String getPositionDashboard(Model uiModel) throws Exception {
			logger.info("----------Inside RequestRequisitionReport start getPositionDashboard-----------");
			Integer userId = userUtil.getCurrentLoggedInUseId();
			Resource loggedInResource = userUtil.getLoggedInResource();
			
			if (loggedInResource.getUploadImage() != null && loggedInResource.getUploadImage().length > 0) {
				byte[] encodeBase64UserImage = Base64.encodeBase64(loggedInResource.getUploadImage());
				String base64EncodedUser = new String(encodeBase64UserImage, "UTF-8");
				uiModel.addAttribute("UserImage", base64EncodedUser);
			} else {
				uiModel.addAttribute("UserImage", "");
			}
			uiModel.addAttribute("firstName",
					loggedInResource.getFirstName() + " " + loggedInResource.getLastName());
			uiModel.addAttribute("designationName", loggedInResource.getDesignationId().getDesignationName());
			uiModel.addAttribute("firstName", loggedInResource.getFirstName() + " " + loggedInResource.getLastName());
			
			Calendar cal = Calendar.getInstance();
				cal.setTime(loggedInResource.getDateOfJoining());
			int m = cal.get(Calendar.MONTH) + 1;
			String months = new DateFormatSymbols().getMonths()[m - 1];
			int year = cal.get(Calendar.YEAR);
			uiModel.addAttribute("DOJ", months + "-" + year);
			uiModel.addAttribute("ROLE", loggedInResource.getUserRole());
			
			uiModel.addAttribute("customerList", requestRequisitionService.getRequestRequisitionsCustomer(userId,loggedInResource.getUserRole()));
			uiModel.addAttribute("activeUserList", resourceService.findActiveResources());
			uiModel.addAttribute("pdlGroups",pdlEmailGroupService.getPdlEmails());
			List<RequestRequisitionReport> report = requestRequisitionReportService.
					getRequestRequisitionReport(userId, loggedInResource.getUserRole(),null,null,null,false, null, null);
			uiModel.addAttribute("reportDataList",report);
			uiModel.addAttribute("reportStatusList",requestRequisitionDashboardService.getSkillResourceStatusList());
			logger.info("----------Inside RequestRequisitionReport Ended getPositionDashboard-----------");
			return "requestsReports/positionDashboard";
		}*/	
		@ResponseBody
		@RequestMapping(value="/requestRequisitionSkills/{skillResourcerRequisitionId}/resources", method=RequestMethod.GET)
		public List<RequisitionResourceDTO> getResources(@PathVariable("skillResourcerRequisitionId") Integer skillResourcerRequisitionId,
				@RequestParam("resourceStatusIds") String resourceStatusIds){
			logger.info("----------Entered into getResources of RequestRequisitionDashboardController -----------");
			Integer userId = userUtil.getCurrentLoggedInUseId();
			Resource resource = userUtil.getLoggedInResource();
			List<RequisitionResourceDTO> list = null;
			list = requestRequisitionResourceService.getRequestRequisitionResources(userId, resource.getUserRole() ,skillResourcerRequisitionId, resourceStatusIds);
			if(list == null || list.size() <=0 ){
				list = new ArrayList<RequisitionResourceDTO>();
				logger.error("----------list size is null from getResources of RequestRequisitionDashboardController -----------");
			}
			logger.info("----------Exit from getResources of RequestRequisitionDashboardController -----------");
			return list;
		}

		@ResponseBody
		@RequestMapping(value = "/getLatestCommentOnResource", method = RequestMethod.GET)
		public List<ResourceCommentDTO> getLatestCommentOnResource(@RequestParam(value = "id") int skillRequestId) {
			logger.info("----------Entered into getLatestCommentOnResource of RequestRequisitionDashboardController -----------");
			List<ResourceCommentDTO> resourceCommentList = 
						requestRequisitionResourceService.getLatestCommentOnResource(skillRequestId);
			
			logger.info("----------Exit from getLatestCommentOnResource of RequestRequisitionDashboardController -----------");
			
			return resourceCommentList;
		}
		
		@RequestMapping(value = "/downloadBUHApproval")
		public ResponseEntity<String> downloadBUHApprovalFile(HttpServletRequest request, @RequestParam("reqid") Integer id, HttpServletResponse response) {
			ResponseEntity<String> jspResponse = null;
			HttpHeaders headers = new HttpHeaders();
			Blob blobUploadFile = null;

			Map<String, Object> map = requestRequisitionDashboardService.downloadSelectedBUHApproval(id); //requestId
			blobUploadFile = (Blob) map.get("file"); 
			response.setHeader("Content-Disposition", "attachment;filename=" + map.get("fileName"));
			response.setHeader("Content-Transfer-Encoding", "binary");
			response.setContentType("application/force-download");
			try {
				if (null != blobUploadFile) {
					InputStream inStream = blobUploadFile.getBinaryStream();
					FileCopyUtils.copy(inStream, response.getOutputStream());
				} else {
					return jspResponse = new ResponseEntity<String>("File Not Found", headers, HttpStatus.EXPECTATION_FAILED);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return jspResponse;
		}
		
		@RequestMapping(value = "/downloadBGHApproval")
		public ResponseEntity<String> downloadBGHApprovalFile(HttpServletRequest request, @RequestParam("reqid") Integer id, HttpServletResponse response) {
			ResponseEntity<String> jspResponse = null;
			HttpHeaders headers = new HttpHeaders();
			Blob blobUploadFile = null;

			Map<String, Object> map = requestRequisitionDashboardService.downloadSelectedBGHApproval(id); //requestId
			blobUploadFile = (Blob) map.get("file");
			response.setHeader("Content-Disposition", "attachment;filename=" + map.get("fileName"));
			response.setHeader("Content-Transfer-Encoding", "binary");
			response.setContentType("application/force-download");
			try {
				if (null != blobUploadFile) {
					InputStream inStream = blobUploadFile.getBinaryStream();
					FileCopyUtils.copy(inStream, response.getOutputStream());
				} else {
					return jspResponse = new ResponseEntity<String>("File Not Found", headers, HttpStatus.EXPECTATION_FAILED);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return jspResponse;
		}
		
		@ResponseBody
		@RequestMapping(value = "/getAllComments")
		public List<ResourceCommentDTO> getAllResourceCommentsBySkillId(@RequestParam("id") Integer skillid){
			List<ResourceCommentDTO> comments =requestRequisitionDashboardService.getAllResourceCommentsBySkillRequestRequisitionID(skillid);
			return comments;
		}

		@ResponseBody
		@RequestMapping(value = "getSkillRequestRequisitionbyskillid/{id}" , method = RequestMethod.GET)
		public RequestRequisitionSkill getRequestRequisitionSkillBySkillId (@PathVariable("id") Integer skillId) {
			RequestRequisitionSkill requestRequisitionSkill  =  requestRequisitionDashboardService.getRequestRequisitionSkillByReqSkillId(skillId).get(0);
			return requestRequisitionSkill;
		}
		
		@ResponseBody
		@RequestMapping(value = "/sendemailforinterview" , method = RequestMethod.POST)
		public ResponseEntity<String> sendEmailForInterview(@RequestBody String requestJson){
			try{
				ResourceInterviewWrapper resourceInterviewWrapper = jsonObjectMapper.fromJsonToObject(requestJson, ResourceInterviewWrapper.class);
				Resource resource = userUtil.getLoggedInResource();
				requestRequisitionDashboardService.sendEmailForInterview(resourceInterviewWrapper, resource);
			} catch (Exception exception) {
				return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
			return new ResponseEntity<String>(HttpStatus.OK);
		}
		
		@ResponseBody
		@RequestMapping(value = "/getDataForScheduleInterview/{skillid}/{skillResourceId}")
		public  ResponseEntity<String> getDataForScheduleInterview(@PathVariable("skillid") String skillid, @PathVariable("skillResourceId") Integer skillResourceId){
		 RequestRequisitionWrapper requestRequisitionWrapper = new RequestRequisitionWrapper();
			try{
				requestRequisitionWrapper =   requestRequisitionDashboardService.getDataForScheduleInterviewByskillId(skillid, skillResourceId);
				
			} catch (Exception exception) {
				exception.printStackTrace();
				return new ResponseEntity<String>("Issue in RMS", HttpStatus.INTERNAL_SERVER_ERROR);
			}
			return new ResponseEntity<String>(requestRequisitionWrapper.toJSONString(requestRequisitionWrapper), HttpStatus.OK);
		}
		
		@ResponseBody
		@RequestMapping(value = "/getKeyInterviewPanels", method = RequestMethod.GET)
		public ResponseEntity<String> getKeyInterviewPanels(@RequestParam(value = "skillRequestId", required = true) int skillRequestId) {
			logger.info("----------Entered into GetKeyInterviewPanels-----------");
			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-Type", "application/json; charset=utf-8");
			try {
				if (skillRequestId > 0) {
					
					RequestRequisitionSkill requestRequisitionSkills = requestRequisitionDashboardService
							.findRequestRequisitionSkillBySkillId(skillRequestId);
					String requiredFor = requestRequisitionSkills.getRequestRequisition().getRequiredFor();
					List<Resource> resourcesKeyInterviewerOne  = new ArrayList<Resource>();
					List<Resource> resourcesKeyInterviewerTwo  = new ArrayList<Resource>();
					if (requestRequisitionSkills != null) {
						resourcesKeyInterviewerOne = requestRequisitionSkillMapper.convertKeyInterviewToResourceObject(requestRequisitionSkills.getKeyInterviewersOne());
						resourcesKeyInterviewerTwo = requestRequisitionSkillMapper.convertKeyInterviewToResourceObject(requestRequisitionSkills.getKeyInterviewersTwo());
					}

					JSONObject jsonObject = new JSONObject();
					jsonObject.put("keyInterviewPanelOne", Resource.toJsonString(resourcesKeyInterviewerOne));
					jsonObject.put("keyInterviewPanelTwo", Resource.toJsonString(resourcesKeyInterviewerTwo));
					jsonObject.put("skillRequestId", skillRequestId);
					jsonObject.put("requiredFor", requiredFor);
					logger.info("------Exit from GetKeyInterviewPanels--------");
					return new ResponseEntity<String>(jsonObject.toString(), headers, HttpStatus.OK);
				}
				else{
					return new ResponseEntity<String>("Skill Id not Found", headers, HttpStatus.NOT_FOUND);
				}
			} catch (Exception exception) {
				logger.error("Exception occured :" + exception.getMessage());
				exception.printStackTrace();
				return new ResponseEntity<String>("Something went wrong !", headers, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		
		@RequestMapping(value = "/saveInterviewPanel", method = RequestMethod.POST, produces = "application/json")
		public ResponseEntity<String> saveInterviewPanel(@RequestBody String requestJson) {
			logger.info("------Entered into SaveInterviewPanel -------");
			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-Type", "application/json; charset=utf-8");
			try {
				requestRequisitionDashboardService.updateInterviewPanels(requestJson);
				logger.info("------Exited From SaveInterviewPanel -------");
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("message", "Success");
				jsonObject.put("errCode", "200");
				return new ResponseEntity<String>(jsonObject.toString(), headers, HttpStatus.CREATED);
			}
			catch (Exception exception) {
				RMSServiceException rmsServiceException = new RMSServiceException();
				if (exception instanceof RMSServiceException) {
					rmsServiceException.setMessage(((DAOException) exception).getErrCode());
					rmsServiceException.setErrCode(exception.getMessage());
				} else {
					logger.error("--------Exception while UpdateInterviewPanels-------- >" + exception.getMessage());
					rmsServiceException.setMessage("Something Went Wrong !");
					rmsServiceException.setErrCode("417");
					exception.printStackTrace();
				}	
				logger.info("------Exited From SaveInterviewPanel -------");
				System.out.println("RMSServiceException.toJsonArray(rmsServiceException) " +RMSServiceException.toJsonArray(rmsServiceException));
				return new ResponseEntity<String>(RMSServiceException.toJsonArray(rmsServiceException), headers, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}



		/*
		 * New RRF Code - Start
		 */
		private Long totalCountForDataTableList(Integer userId, String userRole, HttpServletRequest request){
			HttpHeaders headers = new HttpHeaders();
	        headers.add("Content-Type", "application/json; charset=utf-8");
	        Long totalCount = 0L;
	        try {
	        	totalCount = requestRequisitionDashboardService.getTotalCountRRF( userId,  userRole,request);
			} catch (Exception e) {			
				e.printStackTrace();
			}       
	        return totalCount;

		}
		
		@RequestMapping(value="/populatedRRFTable", method = RequestMethod.GET)
		public ResponseEntity<String> getNewPositionDashboard(@RequestParam(value="sEcho") String sEcho,@RequestParam(value="iDisplayStart", defaultValue="0") Integer page, @RequestParam(value="iDisplayLength", defaultValue="10") Integer size,HttpServletRequest request)  {
			logger.info("--------getNewPositionDashboard method starts--------");
			
			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-Type", "application/json; charset=utf-8");
			long totalCount=0;
			JSONObject resultJSON = new JSONObject();
			
			
			
			Integer userId = userUtil.getCurrentLoggedInUseId();
			Resource loggedInResource = userUtil.getLoggedInResource();
			String userRole = userUtil.getLoggedInResource().getUserRole();
			
			
			List<RequestRequisitionSkill> rrfList = requestRequisitionDashboardService.getRequestRequisitionList(userId,userRole,request);
			totalCount = totalCountForDataTableList(userId,userRole,request).longValue();
			resultJSON.put("sEcho", sEcho);
			resultJSON.put("iTotalRecords", totalCount);
			resultJSON.put("iTotalDisplayRecords", totalCount);
			resultJSON.put("aaData", RequestRequisitionSkill.toJsonArrayRequestRequisitionSkill(rrfList));
			//resultJSON.put("reportDataList", RequestRequisitionSkill.toJsonArrayRequestRequisitionSkill(rrfList));
			//resultJSON.put("aaData", rrfList);
			
	        logger.info("--------getNewPositionDashboard method ends--------");
	        return new ResponseEntity<String>(resultJSON.toString(), headers, HttpStatus.OK);
			
		}

		//@RequestMapping(value = "/mergePositionDashboard", method = RequestMethod.GET)
		@RequestMapping(value="/positionDashboard", method = RequestMethod.GET)
		public String getRRFMergedLoad(Model uiModel) throws Exception {

			logger.info("--------getRRFLoad method starts--------");
			Integer userId = userUtil.getCurrentLoggedInUseId();
			Resource loggedInResource = userUtil.getLoggedInResource();
			
			if (loggedInResource.getUploadImage() != null && loggedInResource.getUploadImage().length > 0) {
				byte[] encodeBase64UserImage = Base64.encodeBase64(loggedInResource.getUploadImage());
				String base64EncodedUser = new String(encodeBase64UserImage, "UTF-8");
				uiModel.addAttribute("UserImage", base64EncodedUser);
			} else {
				uiModel.addAttribute("UserImage", "");
			}
			uiModel.addAttribute("firstName",
					loggedInResource.getFirstName() + " " + loggedInResource.getLastName());
			uiModel.addAttribute("designationName", loggedInResource.getDesignationId().getDesignationName());
			uiModel.addAttribute("firstName", loggedInResource.getFirstName() + " " + loggedInResource.getLastName());
			
			Calendar cal = Calendar.getInstance();
				cal.setTime(loggedInResource.getDateOfJoining());
			int m = cal.get(Calendar.MONTH) + 1;
			String months = new DateFormatSymbols().getMonths()[m - 1];
			int year = cal.get(Calendar.YEAR);
			uiModel.addAttribute("DOJ", months + "-" + year);
			uiModel.addAttribute("ROLE", loggedInResource.getUserRole());
			
		
	        logger.info("--------getRRFLoad method ends--------");
	        //return "requestsReports/mergePositionDashboard";
	        return "requestsReports/positionDashboard";

		}
		// New RRF Code - End
		
		@RequestMapping(value="/activeUserList", method = RequestMethod.GET)
		public ResponseEntity<String> getActiveUserList(@RequestParam(value="userInput", required=false) String userInput)  {
			logger.info("--------getActiveUserList method starts--------");
			System.out.println("-userInput - " + userInput + "-----------");
			
		
			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-Type", "application/json; charset=utf-8");
			JSONObject resultJSON = new JSONObject();
			
			List<Resource> resourceList = genericSearch.getObjectsWithSearchAndPaginationForResource(userInput);
			resultJSON.put("activeUserList", Resource.toJsonString(resourceList));
			
	        logger.info("--------getActiveUserList method ends--------");
	        return new ResponseEntity<String>(resultJSON.toString(), headers, HttpStatus.OK);
			
		}
		@RequestMapping(value="/activeRRFsList", method = RequestMethod.GET)
		public ResponseEntity<String> getActiveRRFsList(@RequestParam(value="userInput", required=false) String userInput)  {
			logger.info("--------getActiveRRFsList method starts--------");
			System.out.println("-userInput - " + userInput + "-----------");
			
		
			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-Type", "application/json; charset=utf-8");
			JSONObject resultJSON = new JSONObject();
			
			List<RequestRequisitionSkill> requestRequisitionSkills = genericSearch.getObjectsWithSearchAndPaginationForRRFName(userInput);
			resultJSON.put("activeUserList", RequestRequisitionSkill.toJsonArray(requestRequisitionSkills));
			
	        logger.info("--------getActiveUserList method ends--------");
	        return new ResponseEntity<String>(resultJSON.toString(), headers, HttpStatus.OK);
			
		}
		
		
		@RequestMapping(value="/pdlUserList", method = RequestMethod.GET)
		public ResponseEntity<String> getPdlUserList(HttpServletRequest request, Model uiModel)  {
			logger.info("--------getPdlUserList method starts--------");
		
			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-Type", "application/json; charset=utf-8");
			JSONObject resultJSON = new JSONObject();
			List<PDLEmailGroup> pdlUserList = null;
			try {
				pdlUserList = pdlEmailGroupService.getPdlEmails();
			} catch (Exception e) {
				e.printStackTrace();
			}
			resultJSON.put("pdlGroups", PDLEmailGroup.toJsonString(pdlUserList));
			
	        logger.info("--------getPdlUserList method ends--------");
	        return new ResponseEntity<String>(resultJSON.toString(), headers, HttpStatus.OK);
			
		}
		
		@RequestMapping(value="/customerList", method = RequestMethod.GET)
		public ResponseEntity<String> getCustomerList(HttpServletRequest request, Model uiModel)  {
			logger.info("--------getCustomerList method starts--------");
			Integer userId = userUtil.getCurrentLoggedInUseId();
			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-Type", "application/json; charset=utf-8");
			JSONObject resultJSON = new JSONObject();
			Resource loggedInResource = userUtil.getLoggedInResource();
			List<CustomerDTO> customerList = null;
			try {
				customerList = requestRequisitionService.getRequestRequisitionsCustomer(userId,loggedInResource.getUserRole());
			} catch (Exception e) {
				e.printStackTrace();
			}
			//resultJSON.put("customerList", CustomerDTO.toJsonString(customerList));
			resultJSON.put("customerList", customerList);
			
	        logger.info("--------getCustomerList method ends--------");
	        return new ResponseEntity<String>(resultJSON.toString(), headers, HttpStatus.OK);
			
		}
		@RequestMapping(value="/reportStatusList", method = RequestMethod.GET)
		public ResponseEntity<String> getReportStatusList(HttpServletRequest request, Model uiModel)  {
			logger.info("--------getReportStatusList method starts--------");
			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-Type", "application/json; charset=utf-8");
			JSONObject resultJSON = new JSONObject();
			List<RequestRequisitionResourceStatus> reportStatusList = null;
			try {
				reportStatusList = requestRequisitionDashboardService.getSkillResourceStatusList();
			} catch (Exception e) {
				e.printStackTrace();
			}
			resultJSON.put("reportStatusList", RequestRequisitionResourceStatus.toJsonString(reportStatusList));
			
	        logger.info("--------getReportStatusList method ends--------");
	        return new ResponseEntity<String>(resultJSON.toString(), headers, HttpStatus.OK);
			
		}
		
		@RequestMapping(value = "/copyRRFBySkillID", method = RequestMethod.GET)
		public ResponseEntity<String> copyRRFBySkillId(@RequestParam(value = "id") int skillRequestId) {
			logger.info("----------copyRRFBySkillId of RequestRequisitionDashboardController Start-----------");
			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-Type", "application/json; charset=utf-8");
			RequestRequisitionSkillDTO requestRequisitionSkill = new RequestRequisitionSkillDTO();
			Integer requestRequisitionSkillId = 0;
			try {
				logger.info(String.format("Copy RRF for skill Id %s", skillRequestId));
				requestRequisitionSkill = requestRequisitionDashboardService.copyRRFBySkillId(skillRequestId);
				requestRequisitionSkillId = requestRequisitionSkill.getId(); 
			} catch(Exception exception) {
				logger.info("----------Exception occured while copying RRF -----------" + exception.getMessage());
				exception.printStackTrace();
				return new ResponseEntity<String>(headers, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			Map<String, String> responseData = new HashMap<String, String>();
			
			responseData.put("requestId" ,requestRequisitionSkillId.toString());
			responseData.put("copyFlag", "true");
			JSONObject jsonObject = new JSONObject(responseData);
			jsonObject.toString();
			logger.info("----------copyRRFBySkillId of RequestRequisitionDashboardController End-----------");
			
			return new ResponseEntity<String>( jsonObject.toString(), HttpStatus.OK);
		}
		
		@RequestMapping(value = "/hardDeleteSkillRequest", method = RequestMethod.POST)
		public ResponseEntity<String> hardDeleteSkillRequest(@RequestParam(value = "skillRequestId") Integer skillId) {
			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-Type", "application/json; charset=utf-8");
			boolean isDeleted = requestRequisitionDashboardService.hardDeleteSkillRequest(skillId);
			if (isDeleted) {
				return new ResponseEntity<String>(headers, HttpStatus.OK);
			} else {
				return new ResponseEntity<String>(headers, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		
		@ResponseBody
		@RequestMapping(value = "/getStakeHolder/{requestRequisitionId}", method = RequestMethod.GET)
		public ResponseEntity<String> getStackHolders(@PathVariable("requestRequisitionId") int requestRequisitionId) {
		
			logger.info("----------Entered into @GetStakeHolders-----------");
			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-Type", "application/json; charset=utf-8");
			
			JSONObject jsonObject = new JSONObject();
			try {
				if (requestRequisitionId > 0) {
					RequestRequisition requestRequisition =requestRequisitionService.findRequestRequisitionById(requestRequisitionId);
					String notifyToResouces = requestRequisition.getNotifyMailTo();
					List<Resource> resources  = new ArrayList<Resource>();
					if (requestRequisition != null) {
						resources = requestRequisitionSkillMapper.convertKeyInterviewToResourceObject(notifyToResouces);
					}

					jsonObject.put("stakeHolders", Resource.toJsonString(resources));
					jsonObject.put("requestRequisitionId", requestRequisitionId);
					jsonObject.put("errorCode", "200");
					jsonObject.put("message","Success");
					logger.info("------Exit from @GetStakeHolders--------");
					return new ResponseEntity<String>(jsonObject.toString(), headers, HttpStatus.OK);
				}
				else {
					
					jsonObject.put("errorCode", "416");
					jsonObject.put("message", "Request Requisition Id Invalid");
					
					return new ResponseEntity<String>(jsonObject.toString(), headers, HttpStatus.OK);
				}
			} catch (Exception exception) {
				logger.error("Exception occured :" + exception.getMessage());
				exception.printStackTrace();
				jsonObject.put("errorCode", "500");
				jsonObject.put("message", exception.getMessage());
				return new ResponseEntity<String>(jsonObject.toString(), headers, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		
		@RequestMapping(value = "/saveStakeHolder", method = RequestMethod.PUT, produces = "application/json")
		public ResponseEntity<String> saveStackHolder(@RequestBody String requestJson) {
			logger.info("------Entered into @SaveStakeHolder -------");
			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-Type", "application/json; charset=utf-8");
			JSONObject responseJson = new JSONObject();
			try {
				JSONObject jsonObject = new JSONObject(requestJson);
				int requestRequisitionId = jsonObject.getInt("requestRequisitionId");
				String notifyMailTo = ObjectUtils.defaultIfNull(jsonObject.getString("notifyMailValue").trim(), "");
				RequestRequisition requestRequisition = requestRequisitionService.findRequestRequisitionById(requestRequisitionId);
				requestRequisition.setNotifyMailTo(notifyMailTo);
				
				Resource currentResource = UserUtil.userContextDetailsToResource(UserUtil.getCurrentResource());
			
				requestRequisition.setLastupdatedBy(currentResource.getUserName());
				requestRequisition.setLastupdatedTimestamp(new Date());
				boolean isUpdated = requestRequisitionService.saveOrupdate(requestRequisition);
				
				if(isUpdated){
					responseJson.put("message", "Successfully Updated");
					responseJson.put("errCode", "200");
				}
				else{
					responseJson.put("message", "Stake Holder Updation Failed");
					responseJson.put("errCode", "417");	
				}
				logger.info("------Exited From @SaveStakeHolder -------");
				return new ResponseEntity<String>(responseJson.toString(), headers, HttpStatus.OK);
			}
			catch (Exception exception) {
				RMSServiceException rmsServiceException = new RMSServiceException();
				if (!(exception instanceof RMSServiceException)) {
					exception.printStackTrace();
					rmsServiceException.setErrCode("417");
				} else {
					rmsServiceException.setErrCode(((RMSServiceException)exception).getErrCode());
				}	
				rmsServiceException.setMessage(exception.getMessage());
				logger.error("--------Exception while Updating StakeHolder-------- >" + exception.getMessage());
				String errorJson =  RMSServiceException.toJsonArray(rmsServiceException);
				System.out.println("RMSServiceException.toJsonArray(rmsServiceException) " +errorJson);
				return new ResponseEntity<String>(errorJson, headers, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		
		@ResponseBody
		@RequestMapping(value = "/getSFId/{requestRequisitionId}", method = RequestMethod.GET)
		public ResponseEntity<String> getSFId(@PathVariable("requestRequisitionId") int requestRequisitionId) {
		
			logger.info("----------Entered into @GetSFId-----------");
			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-Type", "application/json; charset=utf-8");
			
			JSONObject jsonObject = new JSONObject();
			try {
				if (requestRequisitionId > 0) {
					RequestRequisition requestRequisition =requestRequisitionService.findRequestRequisitionById(requestRequisitionId);
					String sfId = requestRequisition.getSuccessFactorId();
					sfId = StringUtils.trimToEmpty(sfId);

					jsonObject.put("sfId", sfId);
					jsonObject.put("requestRequisitionId", requestRequisitionId);
					jsonObject.put("errorCode", "200");
					jsonObject.put("message","Success");
					logger.info("----------Exit from @GetSFId--------");
					return new ResponseEntity<String>(jsonObject.toString(), headers, HttpStatus.OK);
				}
				else {
					
					jsonObject.put("errorCode", "416");
					jsonObject.put("message", "Request Requisition Id Invalid");
					
					return new ResponseEntity<String>(jsonObject.toString(), headers, HttpStatus.OK);
				}
			} catch (Exception exception) {
				jsonObject.put("errorCode", "500");
				jsonObject.put("message", exception.getMessage());
				logger.error("Exception occured :" + exception.getMessage());
				exception.printStackTrace();
				return new ResponseEntity<String>(jsonObject.toString(), headers, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		
		@RequestMapping(value = "/saveSFId", method = RequestMethod.PUT, produces = "application/json")
		public ResponseEntity<String> saveSFId(@RequestBody String requestJson) {
			logger.info("------Entered into @SaveSFId -------");
			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-Type", "application/json; charset=utf-8");
			JSONObject responseJson = new JSONObject();
			try {
				JSONObject jsonObject = new JSONObject(requestJson);
				int requestRequisitionId = jsonObject.getInt("requestRequisitionId");
				String sfID = jsonObject.optString("sfID","");
				RequestRequisition requestRequisition = requestRequisitionService.findRequestRequisitionById(requestRequisitionId);
				requestRequisition.setSuccessFactorId(sfID);
				
				Resource currentResource = UserUtil.userContextDetailsToResource(UserUtil.getCurrentResource());
			
				requestRequisition.setLastupdatedBy(currentResource.getUserName());
				requestRequisition.setLastupdatedTimestamp(new Date());
				boolean isUpdated = requestRequisitionService.saveOrupdate(requestRequisition);
				
				if(isUpdated){
					responseJson.put("message", "Successfully Updated");
					responseJson.put("errCode", "200");
				}
				else{
					responseJson.put("message", "SF Id Updation Failed");
					responseJson.put("errCode", "417");	
				}
				logger.info("------Exited From @SaveSFId -------");
				return new ResponseEntity<String>(responseJson.toString(), headers, HttpStatus.OK);
			}
			catch (Exception exception) {
				RMSServiceException rmsServiceException = new RMSServiceException();
				if (!(exception instanceof RMSServiceException)) {
					exception.printStackTrace();
					rmsServiceException.setErrCode("417");
				} else {
					rmsServiceException.setErrCode(((RMSServiceException)exception).getErrCode());
				}	
				rmsServiceException.setMessage(exception.getMessage());
				logger.error("--------Exception while Updating SF Id-------- >" + exception.getMessage());
				String errorJson =  RMSServiceException.toJsonArray(rmsServiceException);
				System.out.println("RMSServiceException.toJsonArray(rmsServiceException) " +errorJson);
				return new ResponseEntity<String>(errorJson, headers, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		
		@RequestMapping(value = "/mapResourceToRRF", method = RequestMethod.POST)
		public ResponseEntity<String> mapResourceToRRF (@RequestBody String requestJson){

			logger.info("------mapResourceToRRF method Start -------");
			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-Type", "application/json; charset=utf-8");
			JSONObject responseJson = new JSONObject();
			try {
				JSONObject jsonObject = new JSONObject(requestJson);
				int oldSkillRequestId = jsonObject.getInt("skillRequestId");
				int newskillRequestId = jsonObject.getInt("newRRFId");
				String resourceIdToMap = jsonObject.getString("resourceIdToMap");
				
				requestRequisitionResourceService.mapResourceToNewRRF(oldSkillRequestId, newskillRequestId, resourceIdToMap);
				
				logger.info("-------mapResourceToRRF method end-------");
				return new ResponseEntity<String>(responseJson.toString(), headers, HttpStatus.OK);
			}
			catch (Exception exception) {
				RMSServiceException rmsServiceException = new RMSServiceException();
				if (!(exception instanceof RMSServiceException)) {
					exception.printStackTrace();
					rmsServiceException.setErrCode("417");
				} else {
					rmsServiceException.setErrCode(((RMSServiceException)exception).getErrCode());
				}	
				rmsServiceException.setMessage(exception.getMessage());
				logger.error("--------Exception while Updating SF Id-------- >" + exception.getMessage());
				String errorJson =  RMSServiceException.toJsonArray(rmsServiceException);
				System.out.println("RMSServiceException.toJsonArray(rmsServiceException) " +errorJson);
				return new ResponseEntity<String>(errorJson, headers, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		
		}
}
