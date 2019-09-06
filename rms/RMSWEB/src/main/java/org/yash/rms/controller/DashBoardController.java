package org.yash.rms.controller;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.yash.rms.domain.BGAdmin_Access_Rights;
import org.yash.rms.domain.Dashboard_temp;
import org.yash.rms.domain.Dashboardtempsecond;
import org.yash.rms.domain.MessageBoard;
import org.yash.rms.domain.OrgHierarchy;
import org.yash.rms.domain.Resource;
import org.yash.rms.domain.ResourceAllocation;
import org.yash.rms.domain.SkillsMapping;
import org.yash.rms.dto.ProjectDTO;
import org.yash.rms.dto.ResourceDashBoardDTO;
import org.yash.rms.helper.ResourceHelper;
import org.yash.rms.namedquery.RmsNamedQuery;
import org.yash.rms.report.dto.ManagerTimeSheetGraphDTO;
import org.yash.rms.report.dto.UserTimeSheetGraphDTO;
import org.yash.rms.service.CustomerService;
import org.yash.rms.service.DashBoardService;
import org.yash.rms.service.EditProfileService;
import org.yash.rms.service.ProjectAllocationService;
import org.yash.rms.service.MessageBoardService;
import org.yash.rms.service.ProjectService;
import org.yash.rms.service.ReportService;
import org.yash.rms.service.ResourceAllocationService;
import org.yash.rms.service.ResourceService;
import org.yash.rms.service.RmsCRUDService;
import org.yash.rms.service.UserActivityService;
import org.yash.rms.service.impl.ProjectAllocationServiceImpl;
import org.yash.rms.util.Constants;
import org.yash.rms.util.ProjectAllocationSearchCriteria;
import org.yash.rms.util.UserUtil;

@Controller
@RequestMapping("/dashboard")
public class DashBoardController {

	@Autowired
	@Qualifier("ResourceAllocationService")
	private ResourceAllocationService resourceAllocationService;

	@Autowired
	@Qualifier("ResourceService")
	private ResourceService resourceService;
	
	//For get customer count 
	@Autowired
	@Qualifier("CustomerService")
	private CustomerService customerService;
	
	//For get report count
	@Autowired
	@Qualifier("ReportService")
	private ReportService reportService;
	
	@Autowired
	private UserUtil userUtil;

	@Autowired
	@Qualifier("editProfileService")
	EditProfileService editProfileService;

	@Autowired	
	private DashBoardService dashBoardService;

	@Autowired
	private ProjectAllocationService projectAllocationService;

	@Autowired
	@Qualifier("ProjectService")
	private ProjectService projectService;

	@Autowired
	@Qualifier("UserActivityService")
	private UserActivityService userActivityService;
	
	@Autowired
	MessageBoardService messageBoardService;

	// Enviornment variable
	public static String ENV_VARIABLE = Constants.getPropertyVariable("/spring-configuration/rms.properties", "IafConfigSuffix");

	private static final Logger logger = LoggerFactory.getLogger(DashBoardController.class);

	@RequestMapping(value = "/userdashboard")
	@ResponseBody
	public ModelAndView userDash(HttpServletRequest httpServletRequest) {
		ModelAndView model = new ModelAndView();
		List<org.yash.rms.domain.ResourceAllocation> resAlloc = null;
		List<String> myprojects = new ArrayList<String>();
		Integer employeeId = userUtil.getCurrentLoggedInUseId();
		String loggedInResource=userUtil.getLoggedInResource().getUserName();
		System.err.println("loggedInResource is========="+loggedInResource);
		Resource currentLoggedInResource=resourceService.getCurrentResource(loggedInResource);
		Resource resource = new Resource();
		resource.setEmployeeId(employeeId);
		System.err.println("currentLoggedInResource is "+currentLoggedInResource);
		Resource resources = currentLoggedInResource.getCurrentReportingManager();
	
		if (resources != null) {
			model.addObject("EmployeeID", employeeId);
			model.addObject("IRM", currentLoggedInResource.getCurrentReportingManager().getFirstName() + " " + currentLoggedInResource.getCurrentReportingManager().getLastName());
			model.addObject("IRMDesignation", currentLoggedInResource.getCurrentReportingManager().getDesignationId().getDesignationName());
			model.addObject("SRM", currentLoggedInResource.getCurrentReportingManagerTwo().getFirstName() + " " + currentLoggedInResource.getCurrentReportingManagerTwo().getLastName());
			model.addObject("SRMDesignation", currentLoggedInResource.getCurrentReportingManagerTwo().getDesignationId().getDesignationName());
			try {
	
				// IRM Image
				if (currentLoggedInResource.getCurrentReportingManager().getUploadImage() != null && currentLoggedInResource.getCurrentReportingManager().getUploadImage().length > 0) {
					byte[] encodeBase64IRMImage = Base64.encodeBase64(currentLoggedInResource.getCurrentReportingManager().getUploadImage());
					String base64EncodedIRM = new String(encodeBase64IRMImage, "UTF-8");
					model.addObject("IRMImage", base64EncodedIRM);
				} else {
					model.addObject("IRMImage", "");
				}
	
				// SRM Image
				if (currentLoggedInResource.getCurrentReportingManagerTwo().getUploadImage() != null && currentLoggedInResource.getCurrentReportingManagerTwo().getUploadImage().length > 0) {
					byte[] encodeBase64SRMImage = Base64.encodeBase64(currentLoggedInResource.getCurrentReportingManagerTwo().getUploadImage());
					String base64EncodedSRM = new String(encodeBase64SRMImage, "UTF-8");
					model.addObject("SRMImage", base64EncodedSRM);
				} else {
					model.addObject("SRMImage", "");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			model.addObject("CurrentBU", currentLoggedInResource.getCurrentBuId().getParentId().getName() + "-" + currentLoggedInResource.getCurrentBuId().getName());
			model.addObject("ParentBU", currentLoggedInResource.getCurrentBuId().getParentId().getName() + "-" + currentLoggedInResource.getCurrentBuId().getName());
			model.addObject("CurrentLocation", currentLoggedInResource.getDeploymentLocation().getLocation());
			model.addObject("ParentLocation", currentLoggedInResource.getLocationId().getLocation());
		} else {
			model.addObject("IRM", "No Information available");
			model.addObject("IRMDesignation", "");
			model.addObject("SRM", "No Information available");
			model.addObject("SRMDesignation", "");
			model.addObject("CurrentBU", "No Information available");
			model.addObject("ParentBU", "No Information available");
			model.addObject("CurrentLocation", "No Information available");
			model.addObject("ParentLocation", "No Information available");
		}
	
		resAlloc = resourceAllocationService.findResourceAllocationByActiveTypeEmployeeId(resource);
	
		if (resAlloc != null && resAlloc.size() > 0) {
			for (ResourceAllocation userActivityView : resAlloc) {
				myprojects.add(userActivityView.getProjectId().getProjectName() + ":" + userActivityView.getProjectId().getOffshoreDelMgr().getEmployeeName());
			}
	
			if (myprojects.isEmpty()) {
				myprojects.add("RMS" + ":" + "Milind Rumale");
			}
			model.addObject("myProject", new HashSet<String>(myprojects));
			model.addObject("firstName", resAlloc.get(0).getEmployeeId().getFirstName() + " " + resAlloc.get(0).getEmployeeId().getLastName());
			model.addObject("fname", resAlloc.get(0).getEmployeeId().getFirstName());
			model.addObject("designationName", resAlloc.get(0).getEmployeeId().getDesignationId().getDesignationName());
			model.addObject("emailid", resAlloc.get(0).getEmployeeId().getBuId().getId());
		}
	
		try {
			if (currentLoggedInResource.getUploadImage() != null && currentLoggedInResource.getUploadImage().length > 0) {
				byte[] encodeBase64UserImage = Base64.encodeBase64(currentLoggedInResource.getUploadImage());
				String base64EncodedUser = new String(encodeBase64UserImage, "UTF-8");
				model.addObject("UserImage", base64EncodedUser);
			} else {
				model.addObject("UserImage", "");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		model.addObject("designation", currentLoggedInResource.getDesignationId().getDesignationName());
		model.addObject("BGU", currentLoggedInResource.getBuId().getParentId().getName() + "-" + currentLoggedInResource.getCurrentBuId().getDescription());
		model.addObject("CurrentBU", currentLoggedInResource.getCurrentBuId().getParentId().getName() + "-" + currentLoggedInResource.getCurrentBuId().getName());
		model.addObject("ParentBU", currentLoggedInResource.getCurrentBuId().getParentId().getName() + "-" + currentLoggedInResource.getCurrentBuId().getName());
		model.addObject("CurrentLocation", currentLoggedInResource.getDeploymentLocation().getLocation());
		model.addObject("ParentLocation", currentLoggedInResource.getLocationId().getLocation());
	
		List<Object[]> primarySkillsMappingList = editProfileService.getSkillsMapping(currentLoggedInResource.getEmployeeId() + "", RmsNamedQuery.getPrimarySkillByResourceQuery());
		if (primarySkillsMappingList != null && primarySkillsMappingList.size() > 0) {
			List<String> primaryskillss = new ArrayList<String>();
			List<SkillsMapping> skillsMappingList = ResourceHelper.getPrimarySkillSMapping(primarySkillsMappingList);
			for (SkillsMapping skillsMapping : skillsMappingList) {
				primaryskillss.add(skillsMapping.getSkill_Name());
			}
			model.addObject("primaryskills", primaryskillss);
		} else {
			model.addObject("primaryskills", "No data available");
		}
	
		List<Object[]> secondarySkillsMappingList = editProfileService.getSkillsMapping(currentLoggedInResource.getEmployeeId() + "", RmsNamedQuery.getSecondarySkillByResourceQuery());
		if (secondarySkillsMappingList != null && secondarySkillsMappingList.size() > 0) {
			List<SkillsMapping> skillsMappingList = ResourceHelper.getSecondarySkillSMapping(secondarySkillsMappingList);
			List<String> secondaryskillss = new ArrayList<String>();
			for (SkillsMapping skillsMapping : skillsMappingList) {
				secondaryskillss.add(skillsMapping.getSkill_Name());
			}
			model.addObject("secondarySkills", secondaryskillss);
		} else {
			model.addObject("secondarySkills", "No data available");
		}
	
		ResourceDashBoardDTO resourceDashboardDTO = dashBoardService.findTimeSheet(employeeId);
	
		model.addObject("pendingtimesheet", resourceDashboardDTO.getPending());
		model.addObject("approvedsheet", resourceDashboardDTO.getApproved());
		model.addObject("rejectedsheet", resourceDashboardDTO.getRejected());
	
		model.addObject("tododatehyperlink", resourceDashboardDTO.getNotSubmitted());
		// format
	
		// Set Header values...
		model.addObject("firstName", currentLoggedInResource.getFirstName() + " " + currentLoggedInResource.getLastName());
		model.addObject("designationName", currentLoggedInResource.getDesignationId().getDesignationName());
		Calendar cal = Calendar.getInstance();
		cal.setTime(currentLoggedInResource.getDateOfJoining());
		int m = cal.get(Calendar.MONTH) + 1;
		String months = new DateFormatSymbols().getMonths()[m - 1];
		int year = cal.get(Calendar.YEAR);
		model.addObject("DOJ", months + "-" + year);
		model.addObject("ROLE", currentLoggedInResource.getUserRole());
		// End
	
		//Dashboard count
	
		//PROJECT COUNT
		Long projectCount = projectService.getProjectCount();
		model.addObject("projectCount",projectCount);
		
		//RESOURCE COUNT
		Long resourceCount = resourceService.getResourceCount();
		model.addObject("resourceCount",resourceCount);
		
		//CUSTOMER COUNT
		Long customerCount = customerService.getcustomerCount();
		model.addObject("customerCount",customerCount);
		
		//MY PROJECT COUNT
		model.addObject("myProjectCount",myprojects.size());
		
		//MY SKILL COUNT
		int priSkills=primarySkillsMappingList.size();
		int secSkills=secondarySkillsMappingList.size();
		int totalSkillCount=priSkills+secSkills;
		model.addObject("totalSkillCount",totalSkillCount);
		
		//TOTAL TIMESHEET COUNT
		int pendingTimeSheet=resourceDashboardDTO.getPending().size();
		int approvedTimeSheet=resourceDashboardDTO.getApproved().size();
		int rejectTimeSheet= resourceDashboardDTO.getRejected().size();
	//	int notSubmittedTimeSheet= resourceDashboardDTO.getNotSubmitted().size();
		int totalTimeSheet=pendingTimeSheet+approvedTimeSheet+rejectTimeSheet;  //+notSubmittedTimeSheet;
		model.addObject("totalTimeSheet",totalTimeSheet);
		
		//APPROVED TIMESHEET COUNT
		model.addObject("approvedTimeSheet",approvedTimeSheet);
		
		//REPORT COUNT 
		Integer reportCount=reportService.getReportCount();
		model.addObject("reportCount",reportCount);
		
		//message board list
		List<MessageBoard> myMessages = new ArrayList<MessageBoard>();
		myMessages = messageBoardService.getApprovedMessages();
		if(myMessages!=null && !myMessages.isEmpty()) {
			model.addObject("myMessages", new HashSet<MessageBoard>(myMessages));
		}else {
			model.addObject("myMessages", null);
		}
		
		model.setViewName("dashboard/dashboard");
		return model;
	
	}

	@RequestMapping(value = "/loadUserGraph", method = RequestMethod.GET)
	public ResponseEntity<String> loadUserGraph() throws Exception {

		Integer employeeId = userUtil.getCurrentLoggedInUseId();

		HttpHeaders headers = new HttpHeaders();

		List<UserTimeSheetGraphDTO> userTimeSheetGraphDTOs = dashBoardService.findUserstatusoflastFourMonth(employeeId);

		return new ResponseEntity<String>(UserTimeSheetGraphDTO.toJsonArray(userTimeSheetGraphDTOs), headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/admindashboard")
	public ModelAndView adminDashboard() {

		ModelAndView modelAndView = new ModelAndView();

		List<String> myProjects = new ArrayList<String>();

		Resource currentLoggedInResource = resourceService.getCurrentResource(userUtil.getLoggedInResource().getUserName());
		
		Integer employeeId = currentLoggedInResource.getEmployeeId();

		Resource resourceCurrentReportingManager = currentLoggedInResource.getCurrentReportingManager();
		if (resourceCurrentReportingManager != null) {
			modelAndView.addObject("IRM", resourceCurrentReportingManager.getFirstName() + " " + resourceCurrentReportingManager.getLastName());
			modelAndView.addObject("IRMDesignation", resourceCurrentReportingManager.getDesignationId().getDesignationName());
			modelAndView.addObject("SRM", currentLoggedInResource.getCurrentReportingManagerTwo().getFirstName() + " " + currentLoggedInResource.getCurrentReportingManagerTwo().getLastName());
			modelAndView.addObject("SRMDesignation", currentLoggedInResource.getCurrentReportingManagerTwo().getDesignationId().getDesignationName());
			modelAndView.addObject("CurrentBU", currentLoggedInResource.getCurrentBuId().getParentId().getName() + "-" + currentLoggedInResource.getCurrentBuId().getName());
			modelAndView.addObject("ParentBU", currentLoggedInResource.getCurrentBuId().getParentId().getName() + "-" + currentLoggedInResource.getCurrentBuId().getName());
			modelAndView.addObject("CurrentLocation", currentLoggedInResource.getDeploymentLocation().getLocation());
			modelAndView.addObject("ParentLocation", currentLoggedInResource.getLocationId().getLocation());
		} else {
			modelAndView.addObject("IRM", "No Information available");
			modelAndView.addObject("IRMDesignation", "");
			modelAndView.addObject("SRM", "No Information available");
			modelAndView.addObject("SRMDesignation", "");
			modelAndView.addObject("CurrentBU", "No Information available");
			modelAndView.addObject("ParentBU", "No Information available");
			modelAndView.addObject("CurrentLocation", "No Information available");
			modelAndView.addObject("ParentLocation", "No Information available");
		}

		List<ResourceAllocation> resourceAllocations = resourceAllocationService.findResourceAllocationByActiveTypeEmployeeId(currentLoggedInResource);

		ResourceDashBoardDTO resourceDashboardDTO = new ResourceDashBoardDTO();

		if (resourceAllocations != null && resourceAllocations.size() > 0) {

			for (ResourceAllocation resourceAllocation : resourceAllocations) {

				myProjects.add(resourceAllocation.getProjectId().getProjectName() + " : " + resourceAllocation.getProjectId().getOffshoreDelMgr().getEmployeeName());
			}
			modelAndView.addObject("myProject", new HashSet<String>(myProjects));

			modelAndView.addObject("emailid", resourceAllocations.get(0).getEmployeeId().getBuId().getId());
		}

		// User Image
		try {
			if (currentLoggedInResource.getUploadImage() != null && currentLoggedInResource.getUploadImage().length > 0) {
				byte[] encodeBase64UserImage = Base64.encodeBase64(currentLoggedInResource.getUploadImage());
				String base64EncodedUser = new String(encodeBase64UserImage, "UTF-8");
				modelAndView.addObject("UserImage", base64EncodedUser);
			} else {
				modelAndView.addObject("UserImage", "");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		modelAndView.addObject("designation", currentLoggedInResource.getDesignationId().getDesignationName());
		modelAndView.addObject("BGU", currentLoggedInResource.getBuId().getParentId().getName() + "-" + currentLoggedInResource.getCurrentBuId().getDescription());

		// My Skills

		List<Object[]> primarySkillsMappingList = editProfileService.getSkillsMapping(currentLoggedInResource.getEmployeeId() + "", RmsNamedQuery.getPrimarySkillByResourceQuery());
		if (primarySkillsMappingList != null && primarySkillsMappingList.size() > 0) {
			List<String> primaryskillss = new ArrayList<String>();
			List<SkillsMapping> skillsMappingList = ResourceHelper.getPrimarySkillSMapping(primarySkillsMappingList);
			for (SkillsMapping skillsMapping : skillsMappingList) {
				primaryskillss.add(skillsMapping.getSkill_Name());
			}
			modelAndView.addObject("primaryskills", primaryskillss);
		} else {
			modelAndView.addObject("primaryskills", "No data available");
		}

		List<Object[]> secondarySkillsMappingList = editProfileService.getSkillsMapping(currentLoggedInResource.getEmployeeId() + "", RmsNamedQuery.getSecondarySkillByResourceQuery());
		if (secondarySkillsMappingList != null && secondarySkillsMappingList.size() > 0) {
			List<SkillsMapping> skillsMappingList = ResourceHelper.getSecondarySkillSMapping(secondarySkillsMappingList);
			List<String> secondaryskillss = new ArrayList<String>();
			for (SkillsMapping skillsMapping : skillsMappingList) {
				secondaryskillss.add(skillsMapping.getSkill_Name());
			}
			modelAndView.addObject("secondarySkills", secondaryskillss);
		} else {
			modelAndView.addObject("secondarySkills", "No data available");
		}

		modelAndView.addObject("env", ENV_VARIABLE);

		resourceDashboardDTO = dashBoardService.findTimeSheetForUserRole(employeeId);

		modelAndView.addObject("pendingtimesheet", resourceDashboardDTO.getPending());
		modelAndView.addObject("approvedsheet", resourceDashboardDTO.getApproved());
		modelAndView.addObject("rejectedsheet", resourceDashboardDTO.getRejected());
		modelAndView.addObject("timesheetcompliance", resourceDashboardDTO.getTimeSheetComplaince());
		modelAndView.addObject("firstName", currentLoggedInResource.getFirstName() + " " + currentLoggedInResource.getLastName());
		modelAndView.addObject("designationName", currentLoggedInResource.getDesignationId().getDesignationName());
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(currentLoggedInResource.getDateOfJoining());
		
		int month = calendar.get(Calendar.MONTH) + 1;
		String months = new DateFormatSymbols().getMonths()[month - 1];
		
		int year = calendar.get(Calendar.YEAR);
		
		modelAndView.addObject("DOJ", months + "-" + year);
		modelAndView.addObject("ROLE", currentLoggedInResource.getUserRole());

		try {
			// IRM Image
			if (resourceCurrentReportingManager.getUploadImage() != null && resourceCurrentReportingManager.getUploadImage().length > 0) {
				byte[] encodeBase64IRMImage = Base64.encodeBase64(resourceCurrentReportingManager.getUploadImage());
				String base64EncodedIRM = new String(encodeBase64IRMImage, "UTF-8");
				modelAndView.addObject("IRMImage", base64EncodedIRM);
			} else {
				modelAndView.addObject("IRMImage", "");
			}

			// SRM Image
			if (currentLoggedInResource.getCurrentReportingManagerTwo().getUploadImage() != null && currentLoggedInResource.getCurrentReportingManagerTwo().getUploadImage().length > 0) {
				byte[] encodeBase64SRMImage = Base64.encodeBase64(currentLoggedInResource.getCurrentReportingManagerTwo().getUploadImage());
				String base64EncodedSRM = new String(encodeBase64SRMImage, "UTF-8");
				modelAndView.addObject("SRMImage", base64EncodedSRM);
			} else {
				modelAndView.addObject("SRMImage", "");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		Set<BGAdmin_Access_Rights> bgAdminAccessRightSelectedlist = resourceService.getSavedPreferences(employeeId);
		Set<String> bgAdminSelectedList = new HashSet<String>();
		if (null != bgAdminAccessRightSelectedlist && !bgAdminAccessRightSelectedlist.isEmpty()) {
			Iterator<BGAdmin_Access_Rights> iterator = bgAdminAccessRightSelectedlist.iterator();
			while (iterator.hasNext()) {
				BGAdmin_Access_Rights access_Rights = iterator.next();
				if (!(access_Rights.getOrgId().getParentId().getName().equalsIgnoreCase("ORGANIZATION"))) {
					bgAdminSelectedList.add(access_Rights.getOrgId().getId() + ":" + access_Rights.getOrgId().getName() + ":" + access_Rights.getOrgId().getParentId().getName());
				}
			}
		}
		modelAndView.addObject("bgAdminSelectedList", bgAdminSelectedList);

		//message board list
				List<MessageBoard> myMessages = new ArrayList<MessageBoard>();
				myMessages = messageBoardService.getApprovedMessages();
				if(myMessages!=null && !myMessages.isEmpty()) {
					modelAndView.addObject("myMessages", new HashSet<MessageBoard>(myMessages));
				}else {
					modelAndView.addObject("myMessages", null);
				}
		modelAndView.setViewName("dashboard/list");
		return modelAndView;
	}

	@RequestMapping(value = "/loadManagerBillingStatus", method = RequestMethod.GET)
	public ResponseEntity<String> loadManagerBillingStatus() throws Exception {

		List<Dashboardtempsecond> managerBillingStatus = null;

		Integer employeeId = userUtil.getCurrentLoggedInUseId();

		HttpHeaders headers = new HttpHeaders();

		JSONArray obj = new JSONArray();

		List<OrgHierarchy> buList = null;

		List<String> listOfProject = null;

		List<ProjectDTO> projectDisplay = new ArrayList<ProjectDTO>();

		boolean isCurrentUserBusinessGroupAdmin = UserUtil.isCurrentUserIsBusinessGroupAdmin();
		boolean isCurrentUserDelManager = UserUtil.isCurrentUserIsDeliveryManager();

		if (isCurrentUserDelManager) {

			List<Integer> empIdList = new ArrayList<Integer>();

			List<String> projectNameList = new ArrayList<String>();

			projectNameList = projectAllocationService.getProjectNameForManager("active", employeeId);

			listOfProject = projectAllocationService.findCountProjectNameForDashboardBillingStatus(empIdList, "active", projectNameList);

			managerBillingStatus = dashBoardService.getBillingStatusListbyProjectName(listOfProject);

		} else if (isCurrentUserBusinessGroupAdmin) {

			buList = UserUtil.getCurrentResource().getAccessRight();

			listOfProject = projectAllocationService.findAllActiveProjectsForBGAdminDashboard(buList, null);

			managerBillingStatus = dashBoardService.getBillingStatusListbyProjectName(listOfProject);

		} else {

			listOfProject = new ArrayList<String>();

			ProjectAllocationSearchCriteria projectSearchCriteria = new ProjectAllocationSearchCriteria();

			projectSearchCriteria.setOrderStyle("asc");
			projectSearchCriteria.setOrederBy("id");
			projectSearchCriteria.setRefColName("id");
			projectDisplay = projectAllocationService.findAllActiveProjects(0, 50, projectSearchCriteria, "active", "", null, employeeId);
			Iterator itrx = projectDisplay.iterator();
			while (itrx.hasNext()) {
				ProjectDTO listProject = (ProjectDTO) itrx.next();
				listOfProject.add(listProject.getProjectName());

			}

			managerBillingStatus = dashBoardService.getBillingStatusListbyProjectName(listOfProject);
		}
		if (managerBillingStatus != null && managerBillingStatus.size() > 0) {
			Iterator itr = managerBillingStatus.iterator();
			try {

				List<String> newProjectList = new ArrayList<String>();
				while (itr.hasNext()) {

					JSONObject list = new JSONObject();
					Dashboardtempsecond result = (Dashboardtempsecond) itr.next();
					list.put("projectName", result.getProject_name());
					if (result.getActual_hrs() != null)
						list.put("Reported", result.getActual_hrs());
					else
						list.put("Reported", 0);
					if (result.getBilling_hrs() != null)
						list.put("Billable", result.getBilling_hrs());
					else
						list.put("Billable", 0);
					if (result.getNon_billing_hrs() != null)
						list.put("Non billable", result.getNon_billing_hrs());
					else
						list.put("Non billable", 0);
					obj.put(list);
				}

				for (int i = 0; i < listOfProject.size(); i++) {
					for (int j = 0; j < newProjectList.size(); j++) {
						if (newProjectList.contains(listOfProject.get(i))) {
							break;
						} else {
							JSONObject list1 = new JSONObject();
							list1.put("projectName", listOfProject.get(i));
							list1.put("Reported", 0.00);
							list1.put("Billable", 0.00);
							list1.put("Non billable", 0.00);
							obj.put(list1);
							break;
						}

					}

				}

			} catch (JSONException e1) {
				e1.printStackTrace();
			}
		} else {
			JSONObject list = new JSONObject();
			list.put("projectName", "None");
			list.put("Reported", 0);
			list.put("Billable", 0);
			list.put("Non billable", 0);
			obj.put(list);
		}
		return new ResponseEntity<String>(obj.toString(), headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/loadDeliveryManagerResourceBillingStatus", method = RequestMethod.GET)
	public ResponseEntity<String> loadDeliveryManagerBillingStatus() throws Exception {

		Integer employeeId = userUtil.getCurrentLoggedInUseId();
		HttpHeaders headers = new HttpHeaders();

		boolean isCurrentUserBusinessGroupAdmin = UserUtil.isCurrentUserIsBusinessGroupAdmin();
		boolean isCurrentUserDelManager = UserUtil.isCurrentUserIsDeliveryManager();

		String userRole = Constants.ROLE_MANAGER;

		if (isCurrentUserDelManager) {
			userRole = Constants.ROLE_DEL_MANAGER;

		} else if (isCurrentUserBusinessGroupAdmin) {
			userRole = Constants.ROLE_BG_ADMIN;
		}
		List<ManagerTimeSheetGraphDTO> managerTimeSheetGraphList = dashBoardService.getGraphDataForManager(userRole, employeeId);

		return new ResponseEntity<String>(ManagerTimeSheetGraphDTO.toJsonArray(managerTimeSheetGraphList), headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/submitLoadDeliveryManagerResourceBillingStatus", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<String> submitLoadDeliveryManagerBillingStatus(@RequestParam(value = "bgAdminListOfBu1", required = false) String bgAdminListOfBu1) throws Exception {

		List<Dashboard_temp> deliveryManagerBillingStatus = null;
		Integer employeeId = userUtil.getCurrentLoggedInUseId();

		HttpHeaders headers = new HttpHeaders();

		JSONArray obj = new JSONArray();

		List<OrgHierarchy> buList = null;

		List<String> listOfProject = null;
		List<String> newProjectList = new ArrayList<String>();
		List<ProjectDTO> projectDisplay = new ArrayList<ProjectDTO>();

		boolean isCurrentUserBusinessGroupAdmin = UserUtil.isCurrentUserIsBusinessGroupAdmin();
		boolean isCurrentUserDelManager = UserUtil.isCurrentUserIsDeliveryManager();

		if (isCurrentUserDelManager) {

			List<Integer> empIdList = new ArrayList<Integer>();
			List<String> projectNameList = new ArrayList<String>();

			empIdList = resourceService.findResourceIdByRM2RM1(employeeId);
			projectNameList = projectAllocationService.getProjectNameForManager("active", employeeId);
			listOfProject = projectAllocationService.findCountProjectNameForDashboardBillingStatus(empIdList, "active", projectNameList);

			deliveryManagerBillingStatus = dashBoardService.getDatabyProjectName(listOfProject);

		} else if (isCurrentUserBusinessGroupAdmin) {

			buList = UserUtil.getCurrentResource().getAccessRight();

			List<String> selectbuList = new ArrayList<String>();
			Iterator<OrgHierarchy> itrs = buList.iterator();

			while (itrs.hasNext()) {

				OrgHierarchy org = itrs.next();
				List<String> items = Arrays.asList(bgAdminListOfBu1.split("\\s*,\\s*"));
				Iterator iteratorItems = items.iterator();

				while (iteratorItems.hasNext()) {
					String x = (String) iteratorItems.next();
					System.out.println("bgAdminListOfBu1: " + x);
					if (x.equals(org.getId().toString())) {
						selectbuList.add(org.getParentId().getName() + "-" + org.getName());
					}
				}
			}

			deliveryManagerBillingStatus = dashBoardService.getDatabyBGBU(selectbuList);

		} else {

			listOfProject = new ArrayList<String>();

			ProjectAllocationSearchCriteria projectSearchCriteria = new ProjectAllocationSearchCriteria();

			projectSearchCriteria.setOrderStyle("asc");
			projectSearchCriteria.setOrederBy("id");
			projectSearchCriteria.setRefColName("id");

			projectDisplay = projectAllocationService.findAllActiveProjects(0, 50, projectSearchCriteria, "active", "", null, employeeId);

			Iterator itrx = projectDisplay.iterator();

			while (itrx.hasNext()) {
				ProjectDTO listProject = (ProjectDTO) itrx.next();
				listOfProject.add(listProject.getProjectName());

			}
			deliveryManagerBillingStatus = dashBoardService.getDatabyProjectName(listOfProject);
		}

		if (deliveryManagerBillingStatus != null && deliveryManagerBillingStatus.size() > 0) {
			Iterator itr = deliveryManagerBillingStatus.iterator();
			try {

				while (itr.hasNext()) {
					JSONObject list = new JSONObject();
					Dashboard_temp result = (Dashboard_temp) itr.next();
					newProjectList.add(result.getProject_name());
					list.put("projectName", result.getProject_name());
					if (result.getBillable_resources() != null)
						list.put("billable", result.getBillable_resources());
					else
						list.put("billable", 0);
					if (result.getNon_billable_resources() != null)
						list.put("others", result.getNon_billable_resources());
					else
						list.put("others", 0);
					obj.put(list);
				}

			} catch (JSONException e1) {
				e1.printStackTrace();
			}
		} else {
			JSONObject list = new JSONObject();
			list.put("projectName", "None");
			list.put("billable", 0);
			list.put("nonbillable", 0);
			obj.put(list);
		}
		System.out.println("value inside second" + obj.toString());
		return new ResponseEntity<String>(obj.toString(), headers, HttpStatus.OK);

	}

	@RequestMapping(value = "/submitLoadManagerBillingStatus", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<String> submitLoadManagerBillingStatus(@RequestParam(value = "bgAdminListOfBu", required = false) String bgAdminListOfBu) throws Exception {

		List<Dashboardtempsecond> managerBillingStatus = null;

		Integer employeeId = userUtil.getCurrentLoggedInUseId();

		HttpHeaders headers = new HttpHeaders();

		JSONArray obj = new JSONArray();
		List<OrgHierarchy> buList = null;

		boolean isCurrentUserBusinessGroupAdmin = UserUtil.isCurrentUserIsBusinessGroupAdmin();
		boolean isCurrentUserDelManager = UserUtil.isCurrentUserIsDeliveryManager();

		if (isCurrentUserDelManager) {

			List<Integer> empIdList = new ArrayList<Integer>();
			List<String> projectNameList = new ArrayList<String>();

			empIdList = resourceService.findResourceIdByRM2RM1(employeeId);
			projectNameList = projectAllocationService.getProjectNameForManager("active", employeeId);

			List<String> listOfProject = projectAllocationService.findCountProjectNameForDashboardBillingStatus(empIdList, "active", projectNameList);

			managerBillingStatus = dashBoardService.getBillingStatusListbyProjectName(listOfProject);

		} else if (isCurrentUserBusinessGroupAdmin) {

			buList = UserUtil.getCurrentResource().getAccessRight();

			List<String> selectbuList = new ArrayList<String>();
			buList = UserUtil.getCurrentResource().getAccessRight();

			Iterator<OrgHierarchy> itrs = buList.iterator();

			while (itrs.hasNext()) {
				OrgHierarchy org = itrs.next();
				List<String> items = Arrays.asList(bgAdminListOfBu.split("\\s*,\\s*"));
				Iterator iteratorItems = items.iterator();
				while (iteratorItems.hasNext()) {
					String x = (String) iteratorItems.next();
					if (x.equals(org.getId().toString())) {
						selectbuList.add(org.getParentId().getName() + "-" + org.getName());
					}
				}
			}

			managerBillingStatus = dashBoardService.getBillingStatusListbyBGBU(selectbuList);

		} else {
			managerBillingStatus = dashBoardService.getBillingStatusListbyManagerID(employeeId);
		}
		if (managerBillingStatus != null && managerBillingStatus.size() > 0) {
			Iterator itr = managerBillingStatus.iterator();
			try {
				while (itr.hasNext()) {

					JSONObject list = new JSONObject();
					Dashboardtempsecond result = (Dashboardtempsecond) itr.next();
					list.put("projectName", result.getProject_name());
					if (result.getActual_hrs() != null)
						list.put("Reported", result.getActual_hrs());
					else
						list.put("Reported", 0);
					if (result.getBilling_hrs() != null)
						list.put("Billable", result.getBilling_hrs());
					else
						list.put("Billable", 0);
					if (result.getNon_billing_hrs() != null)
						list.put("Non billable", result.getNon_billing_hrs());
					else
						list.put("Non billable", 0);
					obj.put(list);
				}
			} catch (JSONException e1) {
				e1.printStackTrace();
			}
		} else {
			JSONObject list = new JSONObject();
			list.put("projectName", "None");
			list.put("actual hrs", 0);
			list.put("billing hrs", 0);
			list.put("non billing hrs", 0);
			obj.put(list);
		}
		return new ResponseEntity<String>(obj.toString(), headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/savePreferences", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<String> savePreferences(@RequestParam(value = "bgbuid", required = false) String bgbuid) throws Exception {
		HttpHeaders headers = new HttpHeaders();
		JSONArray obj = new JSONArray();
		List<OrgHierarchy> selectbuList = new ArrayList<OrgHierarchy>();
		List<OrgHierarchy> buList = null;
		Integer employeeId = userUtil.getCurrentLoggedInUseId();
		boolean isCurrentUserBusinessGroupAdmin = UserUtil.isCurrentUserIsBusinessGroupAdmin();

		if (isCurrentUserBusinessGroupAdmin) {
			buList = UserUtil.getCurrentResource().getAccessRight();
			Iterator<OrgHierarchy> itrs = buList.iterator();
			while (itrs.hasNext()) {
				OrgHierarchy org = itrs.next();
				List<String> items = Arrays.asList(bgbuid.split("\\s*,\\s*"));
				Iterator iteratorItems = items.iterator();
				while (iteratorItems.hasNext()) {
					String x = (String) iteratorItems.next();
					if (x.equals(org.getId().toString())) {
						System.out.println("value of x inside submitLoadCustomWidgetsStatus" + x);
						selectbuList.add(org);
					}
				}

			}
		}

		if (selectbuList != null && selectbuList.size() > 0) {
			try {
				Iterator iter = selectbuList.iterator();
				JSONObject list = new JSONObject();
				while (iter.hasNext()) {
					String id = selectbuList.get(0).getId().toString();

					list.put("id", id);
					iter.next();

				}
				obj.put(list);
			} catch (JSONException e1) {
				e1.printStackTrace();
			}
		} else {
			JSONObject list_ = new JSONObject();
			list_.put("id", 0);

			obj.put(list_);
		}

		resourceService.savePreference(selectbuList, employeeId);
		return new ResponseEntity<String>(obj.toString(), headers, HttpStatus.OK);
	}

	//TODO : will be added after 9th August 2017 deployment.
	/*@RequestMapping(value = "/findPlatformDetails/platform/{platform}", method = RequestMethod.GET)
	public ResponseEntity<String> findPlatformDetails(@PathVariable("platform") String platformName) throws Exception {
		
		logger.info("------DashBoardController findPlatformDetails method Start------");		
		JSONObject jsonObject = new JSONObject();
		
		PlatformDetails platformDetails = dashBoardService.findPlatformDetails(platformName);
		
		if (platformDetails != null) {
			jsonObject.put("id", platformDetails.getId());
			jsonObject.put("osType", platformDetails.getOsType());
			if (platformDetails.getBrowserType() != null) {
				jsonObject.put("browserType", platformDetails.getBrowserType());
			}
			jsonObject.put("urlGooglePlayStore", platformDetails.getUrlGooglePlayStore());
			jsonObject.put("urlAppStore", platformDetails.getUrlAppStore());
			jsonObject.put("iconUrl", platformDetails.getIconUrl());			
		}
		logger.info("------DashBoardController findPlatformDetails method end------");
		return new ResponseEntity<String>(jsonObject.toString(), HttpStatus.OK);
	}*/
}
