package org.yash.rms.controller;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.yash.rms.domain.Activity;
import org.yash.rms.domain.AllProjectActivities;
import org.yash.rms.domain.ConfigurationCategory;
import org.yash.rms.domain.Currency;
import org.yash.rms.domain.EngagementModel;
import org.yash.rms.domain.InvoiceBy;
import org.yash.rms.domain.MailConfiguration;
import org.yash.rms.domain.OrgHierarchy;
import org.yash.rms.domain.Project;
import org.yash.rms.domain.ProjectCategory;
import org.yash.rms.domain.ProjectMethodology;
import org.yash.rms.domain.ProjectPo;
import org.yash.rms.domain.RequestRequisitionSkill;
import org.yash.rms.domain.Resource;
import org.yash.rms.domain.Roles;
import org.yash.rms.domain.Team;
import org.yash.rms.helper.ProjectHelper;
import org.yash.rms.json.mapper.JsonObjectMapper;
import org.yash.rms.json.mapper.ProjectJasonMapper;
import org.yash.rms.service.ActivityService;
import org.yash.rms.service.CustomerService;
import org.yash.rms.service.MailConfigurationService;
import org.yash.rms.service.OrgHierarchyService;
import org.yash.rms.service.ProjectActivityService;
import org.yash.rms.service.ProjectAllocationService;
import org.yash.rms.service.ProjectService;
import org.yash.rms.service.RequestRequisitionService;
import org.yash.rms.service.ResourceService;
import org.yash.rms.service.RmsCRUDService;
import org.yash.rms.service.RoleService;
import org.yash.rms.util.Constants;
import org.yash.rms.util.DozerMapperUtility;
import org.yash.rms.util.GenericSearch;
import org.yash.rms.util.UserUtil;

@Controller
@RequestMapping("/projects")
@Transactional
public class ProjectController {
	@Autowired
	@Qualifier("ProjectService")
	ProjectService projectService;

	@Autowired
	ResourceService resourceService;

	@Autowired
	CustomerService customerService;

	@Autowired
	@Qualifier("CurrencyService")
	RmsCRUDService<Currency> currencyService;

	@Autowired
	@Qualifier("EngagementModelService")
	RmsCRUDService<EngagementModel> engagementModelServiceImpl;

	@Autowired
	@Qualifier("ProjectCategoryService")
	RmsCRUDService<ProjectCategory> projectCategoryServiceImpl;

	@Autowired
	@Qualifier("InvoiceService")
	RmsCRUDService<InvoiceBy> invoiceServiceImpl;

	@Autowired
	@Qualifier("ProjectMethodologyService")
	RmsCRUDService<ProjectMethodology> projectMethodologyDaoImpl;

	@Autowired
	@Qualifier("OrgHierarchyService")
	OrgHierarchyService orgHierarchyService;

	@Autowired
	@Qualifier("ResourceService")
	ResourceService resourceServiceImpl;

	@Autowired
	@Qualifier("teamService")
	RmsCRUDService<Team> teamService;
	@Autowired
	JsonObjectMapper<Activity> jsonactivityMapper;
	@Autowired
	ProjectJasonMapper jsonMapper;

	@Autowired
	ProjectHelper projectHelper;

	@Autowired
	ProjectAllocationService projectAllocationService;

	@Autowired
	@Qualifier("RoleService")
	RoleService roleService;

	@Autowired
	private DozerMapperUtility mapperUtility;

	@Autowired
	@Qualifier("MailConfigurationService")
	MailConfigurationService mailConfgService;

	@Autowired
	@Qualifier("ActivityService")
	ActivityService activityService;

	@Autowired
	@Qualifier("ProjectActivityService")
	ProjectActivityService projectActivityService;

	@Autowired
	private UserUtil userUtil;

	@Autowired
	GenericSearch genericSearch;
	
	@Autowired
	RequestRequisitionService requestRequisitionService;
	
	private static final Logger logger = LoggerFactory
			.getLogger(ProjectController.class);

	@RequestMapping(method = RequestMethod.GET)
	public String list(
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size,
			Model uiModel) throws Exception {
		logger.info("------ProjectController list method start------");
		Resource currentLoggedInResource=userUtil.getLoggedInResource();
		try {
			/*uiModel.addAttribute(Constants.RESOURCES,
					resourceService.findActiveResources());*/
			uiModel.addAttribute(Constants.ALL_CUSTOMERS,
					customerService.findAll());
			uiModel.addAttribute(Constants.ALL_CURRENCY,
					currencyService.findAll());
			uiModel.addAttribute(Constants.ENGAGEMENTMODEL,
					engagementModelServiceImpl.findAll());
			uiModel.addAttribute(Constants.PROJECT_CATEGORY,
					projectCategoryServiceImpl.findAll());
			uiModel.addAttribute(Constants.INVOICES_BY,
					invoiceServiceImpl.findAll());
			uiModel.addAttribute(Constants.PROJECT_METHODOLOGYS,
					projectMethodologyDaoImpl.findAll());
			uiModel.addAttribute(Constants.BUS, orgHierarchyService.findAllBu());
			uiModel.addAttribute(Constants.TEAMS, teamService.findAll());
			uiModel.addAttribute(Constants.OFFSHORE_DEL_MGR, "abc");
			
			// Set Header values...

			try {
				if (currentLoggedInResource.getUploadImage() != null
						&& currentLoggedInResource.getUploadImage().length > 0) {
					byte[] encodeBase64UserImage = Base64.encodeBase64(currentLoggedInResource.getUploadImage());
					String base64EncodedUser = new String(
							encodeBase64UserImage, "UTF-8");
					uiModel.addAttribute("UserImage", base64EncodedUser);
				} else {
					uiModel.addAttribute("UserImage", "");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			uiModel.addAttribute("firstName", currentLoggedInResource.getFirstName()+ " "+ currentLoggedInResource.getLastName());
			uiModel.addAttribute("designationName", currentLoggedInResource.getDesignationId().getDesignationName());
			
			Calendar cal = Calendar.getInstance();
			cal.setTime(currentLoggedInResource.getDateOfJoining());
			int m = cal.get(Calendar.MONTH) + 1;
			String months = new DateFormatSymbols().getMonths()[m - 1];
			int year = cal.get(Calendar.YEAR);
			uiModel.addAttribute("DOJ", months + "-" + year);
			uiModel.addAttribute("ROLE", currentLoggedInResource.getUserRole());
			// End

			logger.info("------ProjectController list method end------");
			return "projects/list";
		} catch (RuntimeException runtimeException) {
			logger.error("RuntimeException occured in list method of ProjectController:"
					+ runtimeException);
			throw runtimeException;
		} catch (Exception exception) {
			logger.error("Exception occured in list method of ProjectController:"
					+ exception);
			throw exception;
		}

	}

	@RequestMapping(method = RequestMethod.PUT, headers = "Accept=application/json")
	public ResponseEntity<String> updateFromJson(@RequestBody String json,
			HttpServletRequest request) throws Exception {

		logger.info("------ProjectController updateFromJson method start------");
		try {
			HttpHeaders headers = new HttpHeaders();
			ServletContext context = request.getSession().getServletContext();
			context.getAttribute("userName");
			headers.add("Content-Type", "application/json");

			Project project = jsonMapper.fromJsonToProject(json);
			project.setLastUpdatedId((String) context.getAttribute("userName"));
			if (project.getTeam().getId() == -1) {
				project.setTeam(null);
			}

			if (project.getProjectKickOff() != null) {
				if (!projectService.isKickOffDateGreater(project.getId(),
						project.getProjectKickOff())) {
					return new ResponseEntity<String>(
							"{ \"status\":\"false\"}", headers, HttpStatus.OK);
				}
			}

			if (!projectService.saveOrupdate(project)) {
				return new ResponseEntity<String>("{ \"status\":\"false\"}",
						headers, HttpStatus.EXPECTATION_FAILED);
			}
			logger.info("------ProjectController updateFromJson method end------");
			return new ResponseEntity<String>("{ \"status\":\"true\"}",
					headers, HttpStatus.OK);
		} catch (RuntimeException runtimeException) {
			logger.error("RuntimeException occured in updateFromJson method of ProjectController:"
					+ runtimeException);
			throw runtimeException;
		} catch (Exception exception) {
			logger.error("Exception occured in updateFromJson method of ProjectController:"
					+ exception);
			throw exception;
		}
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> showJson(@PathVariable("id") Integer id)
			throws Exception {
		logger.info("------ProjectController showJson method start------");
		try {

			Project project = projectService.findProject(id);
			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-Type", "application/json; charset=utf-8");

			if (project == null) {
				return new ResponseEntity<String>("{ \"status\":\"false\"}",
						headers, HttpStatus.NOT_FOUND);
			}
			logger.info("------ProjectController showJson method end------");
			return new ResponseEntity<String>(jsonMapper.toJson(project),
					headers, HttpStatus.OK);
		} catch (RuntimeException runtimeException) {
			logger.error("RuntimeException occured in showJson method of ProjectController:"
					+ runtimeException);
			throw runtimeException;
		} catch (Exception exception) {
			logger.error("Exception occured in showJson method of ProjectController:"
					+ exception);
			throw exception;
		}
	}

	@RequestMapping(method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<String> createFromJson(@RequestBody String json)
			throws Exception {
		logger.info("------ProjectController createFromJson method start------");
		try {

			Project project = jsonMapper.fromJsonToProject(json);
			boolean isSaved = false;
			List<Activity> activityIds = new ArrayList<Activity>();
			List<AllProjectActivities> allProjectActivities = new ArrayList<AllProjectActivities>();
			if (project.getId() != null && project.getId() > 0)
				project.setId(null);
			boolean isPoAdded = false;
			Set<ProjectPo> projectPo = null;
			if (project.getProjectPoes() != null
					&& project.getProjectPoes().size() > 0) {
				projectPo = project.getProjectPoes();
				project.setProjectPoes(null);
				isPoAdded = true;
			}

			// Added for resolving transient exception while adding poes to new
			// project
			if (isPoAdded) {
				for (ProjectPo po : projectPo) {
					po.setProjectId(project);
				}
				project.setProjectPoes(projectPo);
				if (project.getTeam().getId() == -1) {
					project.setTeam(null);
				}
				// User Story #2731
				isSaved = projectService.saveOrupdate(project);
				if (project.isDefaultActivitiesId()) {
					if (isSaved) {

						activityIds = activityService
								.findSepgActivity(Constants.Default);
						for (Activity act : activityIds) {

							AllProjectActivities projectAct = new AllProjectActivities();

							projectAct.setProjectid(project);
							projectAct.setDefaultActivityid(act);
							projectAct.setActivityType(Constants.Default);
							projectAct.setCustomActivityid(null);
							projectAct.setDeactiveFlag(1);
							allProjectActivities.add(projectAct);

						}
						projectActivityService
								.saveActivity(allProjectActivities);
					}

				} // End User Story #2731

			}

			if (project.getTeam() != null) {
				if (project.getTeam().getId() == -1) {
					project.setTeam(null);
				}
			}
			// User Story #2731
			isSaved = projectService.saveOrupdate(project);
			if (project.isDefaultActivitiesId()) {
				if (isSaved) {
					activityIds = activityService
							.findSepgActivity(Constants.Default);

					for (Activity act : activityIds) {

						AllProjectActivities projectAct = new AllProjectActivities();

						projectAct.setProjectid(project);
						projectAct.setDefaultActivityid(act);
						projectAct.setActivityType(Constants.Default);
						projectAct.setCustomActivityid(null);
						projectAct.setDeactiveFlag(1);
						allProjectActivities.add(projectAct);

					}
					projectActivityService.saveActivity(allProjectActivities);
				}

			}// End User Story #2731

			// Default mail configuration for newly created project
			int confgIdTimeSheetS = Integer.parseInt(Constants
					.getProperty("Timesheet_Submission"));
			int confgIdTimeSheetR = Integer.parseInt(Constants
					.getProperty("Timesheet_Rejection"));
			int confgIdTimeSheetA = Integer.parseInt(Constants
					.getProperty("Timesheet_Approval"));
			List<MailConfiguration> mail = new ArrayList<MailConfiguration>();
			MailConfiguration configurationS = new MailConfiguration();
			MailConfiguration configurationA = new MailConfiguration();

			MailConfiguration configurationR = new MailConfiguration();

			ConfigurationCategory cS = new ConfigurationCategory();
			cS.setId(confgIdTimeSheetS);

			ConfigurationCategory cR = new ConfigurationCategory();
			cR.setId(confgIdTimeSheetR);

			ConfigurationCategory cA = new ConfigurationCategory();
			cA.setId(confgIdTimeSheetA);

			Roles rolePM = roleService.findByRole("ProjectManager");
			Roles roleUser = roleService.findByRole("User");

			configurationS.setProjectId(project);
			configurationS.setConfgId(cS);
			configurationS.setRoleId(rolePM);
			configurationS.setTo(true);

			configurationA.setProjectId(project);
			configurationA.setConfgId(cA);
			configurationA.setRoleId(roleUser);
			configurationA.setTo(true);

			configurationR.setProjectId(project);
			configurationR.setConfgId(cR);
			configurationR.setRoleId(roleUser);
			configurationR.setTo(true);

			mail.add(configurationR);
			mail.add(configurationA);
			mail.add(configurationS);

			MailConfiguration configurationR1 = new MailConfiguration();
			configurationR1.setProjectId(project);
			configurationR1.setConfgId(cR);
			configurationR1.setRoleId(rolePM);
			configurationR1.setCc(true);
			MailConfiguration configurationS1 = new MailConfiguration();

			configurationS1.setProjectId(project);
			configurationS1.setConfgId(cS);
			configurationS1.setRoleId(roleUser);
			configurationS1.setTo(true);

			MailConfiguration configurationA1 = new MailConfiguration();
			configurationA1.setProjectId(project);
			configurationA1.setConfgId(cA);
			configurationA1.setRoleId(rolePM);
			configurationA1.setCc(true);
			mail.add(configurationR1);
			mail.add(configurationA1);
			mail.add(configurationS1);

			mailConfgService.saveConfigurations(mail);
			mailConfgService.saveDefaultConfigs(project);

			// Default mail configuration for newly created project End
			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-Type", "application/json");
			if (!projectService.saveOrupdate(project)) {
				return new ResponseEntity<String>("{ \"status\":\"false\"}",
						headers, HttpStatus.EXPECTATION_FAILED);
			}

			logger.info("------ProjectController createFromJson method end------");
			return new ResponseEntity<String>("{ \"status\":\"true\"}",
					headers, HttpStatus.CREATED);
		} catch (RuntimeException runtimeException) {
			logger.error("RuntimeException occured in createFromJson method of ProjectController:"
					+ runtimeException);
			throw runtimeException;
		} catch (Exception exception) {
			logger.error("Exception occured in createFromJson method of ProjectController:"
					+ exception);
			throw exception;
		}
	}

	@RequestMapping(value = "/updatedlist", headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> listUpdatedJson() throws Exception {
		logger.info("------ProjectController listUpdatedJson method start------");
		try {

			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-Type", "application/json; charset=utf-8");
			Integer currentLoggedInUserId = UserUtil.getUserContextDetails()
					.getEmployeeId();
			List<Project> result = new ArrayList<Project>();
			if (UserUtil.isCurrentUserIsDeliveryManager()) {
				Set<Project> projectset1 = projectHelper
						.getCurrentProjectForDeliveryManager("active",
								currentLoggedInUserId);
				result.addAll(projectset1);
			} else {
				if (UserUtil.isCurrentUserIsBusinessGroupAdmin()) {
					List<OrgHierarchy> buList = UserUtil.getCurrentResource()
							.getAccessRight();
					result.addAll(projectService
							.findActiveProjectsForBGAdmin(buList));

				}

				else {
					if (UserUtil.isCurrentUserIsAdmin())
						result = projectService.findAllActiveProjects();
					else {

						result = projectAllocationService
								.findAllActiveProjectsByManager(currentLoggedInUserId);

					}

				}
			}
			logger.info("------ProjectController listUpdatedJson method end------");
			return new ResponseEntity<String>(jsonMapper.toJsonArray(result),
					headers, HttpStatus.OK);
		} catch (RuntimeException runtimeException) {
			logger.error("RuntimeException occured in listUpdatedJson method of ProjectController:"
					+ runtimeException);
			throw runtimeException;
		} catch (Exception exception) {
			logger.error("Exception occured in listUpdatedJson method of ProjectController:"
					+ exception);
			throw exception;
		}
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	public ResponseEntity<String> deleteFromJson(@PathVariable("id") Integer id)
			throws Exception {
		logger.info("------ProjectController listUpdatedJson method start------");
		try {

			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-Type", "application/json");
			// Delete based on ID
			if (!projectService.delete(id.intValue())) {
				return new ResponseEntity<String>("{ \"status\":\"false\"}",
						headers, HttpStatus.NOT_FOUND);
			}
			logger.info("------ProjectController listUpdatedJson method end------");
			return new ResponseEntity<String>("{ \"status\":\"true\"}",
					headers, HttpStatus.OK);
		} catch (RuntimeException runtimeException) {
			logger.error("RuntimeException occured in listUpdatedJson method of ProjectController:"
					+ runtimeException);
			throw runtimeException;
		} catch (Exception exception) {
			logger.error("Exception occured in listUpdatedJson method of ProjectController:"
					+ exception);
			throw exception;
		}
	}

	@RequestMapping(value = "/validate", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<String> validateData(
			@RequestParam(value = "projectName") String projectName,
			@RequestParam(value = "projectCode") String projectCode,
			HttpServletResponse response) throws Exception {
		logger.info("------ProjectController validateData method start------");
		try {

			boolean projectResult = true;
			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-Type", "application/json; charset=utf-8");
			List<org.yash.rms.dto.ProjectDTO> projectQuery = null;
			if (projectName != null && projectName != "") {
				projectQuery = projectAllocationService
						.findProjectsByProjectNameEquals(projectName);
			}
			if (projectQuery != null) {
				@SuppressWarnings("unchecked")
				List<org.yash.rms.dto.ProjectDTO> projectList = projectQuery;
				if ((projectList != null) && (!projectList.isEmpty())) {
					org.yash.rms.dto.ProjectDTO project = projectList.get(0);
					if (projectName != null) {
						if (project.getProjectName() != null
								|| project.getProjectName() != "") {
							if (projectName.trim().equalsIgnoreCase(
									project.getProjectName().trim())) {
								projectResult = false;
								if (projectCode != null || projectCode != "") {
									if (projectCode.equals(project
											.getProjectCode())) {
										projectResult = true;
									} else {
										projectResult = false;
									}
								}

							}

						}
					} else {
						projectResult = false;
					}
				}
			} else {
				projectResult = true;
			}
			logger.info("------ProjectController validateData method end------");
			return new ResponseEntity<String>("{ \"projectResult\":"
					+ projectResult + "}", headers, HttpStatus.OK);
		} catch (RuntimeException runtimeException) {
			logger.error("RuntimeException occured in validateData method of ProjectController:"
					+ runtimeException);
			throw runtimeException;
		} catch (Exception exception) {
			logger.error("Exception occured in validateData method of ProjectController:"
					+ exception);
			throw exception;
		}
	}

	@RequestMapping(value = "/projectActive")
	@ResponseBody
	public ResponseEntity<String> projectActiveChange(
			@RequestParam("projectActive") String projectActive)
			throws Exception {
		logger.info("------ProjectController projectActiveChange method start------");
		if (projectActive.equalsIgnoreCase("active")) {

			return listUpdatedJson();
		} else {
			try {

				HttpHeaders headers = new HttpHeaders();
				headers.add("Content-Type", "application/json; charset=utf-8");
				List<Project> result = new ArrayList<Project>();
				Integer currentLoggedInUserId = UserUtil
						.getUserContextDetails().getEmployeeId();
				if (UserUtil.isCurrentUserIsDeliveryManager()) {
					Set<Project> projectset1 = projectHelper
							.getCurrentProjectForDeliveryManager("active",
									currentLoggedInUserId);
					result.addAll(projectset1);
				} else {
					if (UserUtil.isCurrentUserIsBusinessGroupAdmin()) {
						List<OrgHierarchy> buList = UserUtil
								.getCurrentResource().getAccessRight();

						result.addAll(projectService
								.findProjectsForBGAdmin(buList));

					}

					else {
						result = projectService.findAll();
					}
				}
				Collections.sort(result, new Project.ProjectNameComparator());
				logger.info("------ProjectController listUpdatedJson method end------");
				return new ResponseEntity<String>(
						jsonMapper.toJsonArray(result), headers, HttpStatus.OK);
			} catch (RuntimeException runtimeException) {
				logger.error("RuntimeException occured in listUpdatedJson method of ProjectController:"
						+ runtimeException);
				throw runtimeException;
			} catch (Exception exception) {
				logger.error("Exception occured in listUpdatedJson method of ProjectController:"
						+ exception);
				throw exception;
			}
		}
	}

	@RequestMapping(params = "find=ByResourceIdUpdated", headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> showMailConfgJson(
			@RequestParam("resourceId") Integer id) throws Exception {
		logger.info("------ProjectController showJson method start------");
		try {

			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-Type", "application/json; charset=utf-8");
			List<String> confg = mailConfgService.findById(id);
			if (confg == null) {
				return new ResponseEntity<String>("{ \"status\":\"true\"}",
						headers, HttpStatus.OK);
			}
			JSONObject resultJSON = new JSONObject();
			JSONArray object = new JSONArray();
			for (int i = 0; i < confg.size(); i++) {
				object.put(i, confg.get(i).toString());

			}
			resultJSON.put("aaData", object);
			logger.info("------ProjectController showJson method end------");
			return new ResponseEntity<String>(resultJSON.toString(), headers,
					HttpStatus.OK);
		} catch (RuntimeException runtimeException) {
			logger.error("RuntimeException occured in showJson method of ProjectController:"
					+ runtimeException);
			throw runtimeException;
		} catch (Exception exception) {
			logger.error("Exception occured in showJson method of ProjectController:"
					+ exception);
			throw exception;
		}
	}

	// For US3119 to get all the projects present
	@RequestMapping(value = "getAllProjectByBUid/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> getAllProjectByBUid(
			@PathVariable("id") Integer id) throws Exception {
		String jsonArray = "";
		logger.info("------ProjectController showJson method start------");
		try {
			List<Project> project = new ArrayList<Project>();
			project = projectService.findAllProjectByBUid(id);
			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-Type", "application/json; charset=utf-8");

			if (project == null) {
				return new ResponseEntity<String>("{ \"status\":\"false\"}",
						headers, HttpStatus.NOT_FOUND);
			}
			logger.info("------ProjectController showJson method end------");
			jsonArray = jsonMapper.toJsonArray(project);
			return new ResponseEntity<String>(jsonArray, headers, HttpStatus.OK);
		} catch (RuntimeException runtimeException) {
			logger.error("RuntimeException occured in showJson method of ProjectController:"
					+ runtimeException);
			throw runtimeException;
		} catch (Exception exception) {
			logger.error("Exception occured in showJson method of ProjectController:"
					+ exception);
			throw exception;
		}
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
	
		@RequestMapping(value = "rrfdetails/{ProjectId}", method = RequestMethod.GET, headers = "Accept=application/json")
		@ResponseBody
		public ResponseEntity<String> getRRFDetailsforProject(@PathVariable("ProjectId") Integer id )  {
			logger.info("--------getRRFDetailsforProject method starts--------");
			
		
			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-Type", "application/json; charset=utf-8");
			JSONObject resultJSON = new JSONObject();
			
			List<RequestRequisitionSkill> rrfList = requestRequisitionService.findRRFByProjectId(id);

			resultJSON.put("rrfDetails", RequestRequisitionSkill.toJsonRequestRequisitionSkill(rrfList));
			
	        logger.info("--------getRRFDetailsforProject method ends--------");
	       return new ResponseEntity<String>(resultJSON.toString(), headers, HttpStatus.OK);
			
		}

}
