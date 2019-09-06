/**
 * 
 */
package org.yash.rms.controller;

import java.io.PrintWriter;
import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.yash.rms.domain.Activity;
import org.yash.rms.domain.ActivityNameComparator;
import org.yash.rms.domain.ProjectModule;
import org.yash.rms.domain.Resource;
import org.yash.rms.domain.ResourceAllocation;
import org.yash.rms.domain.UserActivity;
import org.yash.rms.dto.ProjectDTO;
import org.yash.rms.dto.ProjectSubModuleDTO;
import org.yash.rms.dto.TimesheetSubmissionDTO;
import org.yash.rms.dto.UserActivityViewDTO;
import org.yash.rms.form.NewTimeSheet;
import org.yash.rms.form.UserActivityForm;
import org.yash.rms.helper.EmailHelper;
import org.yash.rms.helper.TimeHourEntryHelper;
import org.yash.rms.helper.UserActivityHelper;

import org.yash.rms.json.mapper.ActivityJsonMapper;
import org.yash.rms.json.mapper.ProjectModuleJsonMapper;
import org.yash.rms.json.mapper.ProjectSubModuleMapper;
import org.yash.rms.json.mapper.UserActivityJsonMapper;
import org.yash.rms.service.ActivityService;
import org.yash.rms.service.MailConfigurationService;
import org.yash.rms.service.ProjectModuleService;
import org.yash.rms.service.ProjectService;
import org.yash.rms.service.ProjectSubModuleService;
import org.yash.rms.service.ResourceAllocationService;
import org.yash.rms.service.ResourceService;
import org.yash.rms.service.RmsCRUDService;
import org.yash.rms.service.UserActivityService;
import org.yash.rms.util.Constants;
import org.yash.rms.util.DozerMapperUtility;
import org.yash.rms.util.UserUtil;

/**
 * @author arpan.badjatiya
 * 
 */
@Controller
@RequestMapping("/useractivitys")
public class UserActivityController {

	@Autowired
	@Qualifier("ActivityService")
	private RmsCRUDService<Activity> activityService;

	@Autowired
	@Qualifier("ActivityService")
	private ActivityService objActivityService;

	@Autowired
	@Qualifier("ResourceAllocationService")
	private ResourceAllocationService resourceAllocationService;

	@Autowired
	@Qualifier("ResourceService")
	private ResourceService resourceService;

	@Autowired
	@Qualifier("UserActivityService")
	private UserActivityService userActivityService;

	@Autowired
	@Qualifier("ProjectService")
	private ProjectService projectService;

	@Autowired
	@Qualifier("projectModuleService")
	private ProjectModuleService projectModuleService;

	@Autowired
	private ProjectSubModuleService projectSubModuleService;

	@Autowired
	@Qualifier("UserActivityJsonMapper")
	private UserActivityJsonMapper jsonObjectMapper;

	@Autowired
	@Qualifier("ProjectModuleJsonMapper")
	private ProjectModuleJsonMapper projectModulejsonMapper;

	@Autowired
	private ProjectSubModuleMapper projectSubModuleMapper;

	@Autowired
	@Qualifier("ActivityJsonMapper")
	private ActivityJsonMapper activityJsonObjectMapper;

	@Autowired
	private EmailHelper emailHelper;

	@Autowired
	private UserActivityHelper userActivityHelper;

	@Autowired
	private TimeHourEntryHelper timeHourEntryHelper;

	@Autowired
	private UserUtil userUtil;

	@Autowired
	private DozerMapperUtility mapper;
	@Autowired
	@Qualifier("MailConfigurationService")
	MailConfigurationService mailConfgService;

	private static final Logger logger = LoggerFactory.getLogger(UserActivityController.class);

	@RequestMapping(params = "find=ByWeekStartDateBetweenAndEmployeeIdView", produces = "text/html")
	public String findUserActivitysByWeekStartDateBetweenAndEmployeeIdView(@RequestParam("minWeekStartDate") @DateTimeFormat(style = "S-") Date minWeekStartDate,
			@RequestParam("maxWeekStartDate") @DateTimeFormat(style = "S-") Date maxWeekStartDate, @RequestParam(value = "employeeId", required = false) Integer employeeId, Integer i, Model uiModel,
			HttpSession session) {

		logger.info("START METHOD " + "findUserActivitysByWeekStartDateBetweenAndEmployeeIdView");
		logger.debug("minWeekStartDate :: " + minWeekStartDate);
		logger.debug("maxWeekStartDate :: " + maxWeekStartDate);
		logger.debug("employeeId :: " + employeeId);

		try {
			if ((session.getAttribute(Constants.USER_PROFILE) != null) && ((!session.getAttribute(Constants.USER_PROFILE).equals("")))) {
				session.removeAttribute("editProfile");
				return "redirect:editProfile/showProfile";
			}

			Resource currentLoggedInUser = userUtil.getLoggedInResource();

			Integer empId = employeeId == null ? currentLoggedInUser.getEmployeeId() : employeeId;

			List<TimesheetSubmissionDTO> timesheetSubmissionDTOs = new ArrayList<TimesheetSubmissionDTO>();

			timesheetSubmissionDTOs = userActivityService.findUserActivitysByEmployeeIdAndWeekStartDateBetween(minWeekStartDate, maxWeekStartDate, empId, false);

			uiModel.addAttribute(Constants.USER_ACTIVITY_VIEWS, timesheetSubmissionDTOs);
			uiModel.addAttribute("minWeekStartDate", minWeekStartDate);
			uiModel.addAttribute("maxWeekStartDate", maxWeekStartDate);
			uiModel.addAttribute("maxWeekStartDatee", maxWeekStartDate);
				
			List<ProjectDTO> projectDTO = userActivityService.findProjectsByEmployeeIdWithActiveActivityModuleAndSubModule(employeeId);
			
			uiModel.addAttribute(Constants.PROJECTS, projectDTO);
			
			List<ResourceAllocation> resList = resourceAllocationService.findResourceAllocations(currentLoggedInUser);
			if (resList.size() > 0) {
				uiModel.addAttribute(Constants.ACTIVITIES, objActivityService.findActiveActivitysByResourceAllocationId(resList.get(0).getId()));

			}
			uiModel.addAttribute(Constants.RESOURCE_ALLOCATION, resList);
			Date dt = new Date();
			uiModel.addAttribute(Constants.TODAY, dt.getTime());
			uiModel.addAttribute(Constants.TIME_SHEET_COMMENT_OPTIONAL, resourceService.getTimesheetComment(empId));

			// Set Headers

			try {
				byte[] uploadedImage = currentLoggedInUser.getUploadImage();

				if (uploadedImage != null && uploadedImage.length > 0) {
					byte[] encodeBase64UserImage = Base64.encodeBase64(uploadedImage);
					String base64EncodedUser = new String(encodeBase64UserImage, "UTF-8");
					uiModel.addAttribute("UserImage", base64EncodedUser);
				} else {
					uiModel.addAttribute("UserImage", "");
				}
			} catch (Exception e) {
				logger.error("Exception occured in findUserActivitysByWeekStartDateBetweenAndEmployeeIdView" + e);
				logger.info("Exception occured in findUserActivitysByWeekStartDateBetweenAndEmployeeIdView" + e);

			}

			uiModel.addAttribute("firstName", currentLoggedInUser.getFirstName() + " " + currentLoggedInUser.getLastName());
			uiModel.addAttribute("designationName", currentLoggedInUser.getDesignationId().getDesignationName());
			Calendar cal = Calendar.getInstance();
			cal.setTime(currentLoggedInUser.getDateOfJoining());
			int m = cal.get(Calendar.MONTH) + 1;
			String months = new DateFormatSymbols().getMonths()[m - 1];
			int year = cal.get(Calendar.YEAR);
			uiModel.addAttribute("DOJ", months + "-" + year);
			uiModel.addAttribute("ROLE", currentLoggedInUser.getUserRole());
		} catch (Exception ex) {

			logger.error("Exception occured in findUserActivitysByWeekStartDateBetweenAndEmployeeIdView" + ex);
			logger.info("Exception occured in findUserActivitysByWeekStartDateBetweenAndEmployeeIdView" + ex);
		}

		logger.info("EXIT METHOD " + "findUserActivitysByWeekStartDateBetweenAndEmployeeIdView");
		return "useractivitys/list";
	}

	@RequestMapping(params = "find=ActiveActivityByProjectId", headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> findUserActivitysByProjectId(@RequestParam("projectId") Integer projectId) throws Exception {

		logger.info("User Logged In---" + UserUtil.getCurrentResource().getEmployeeName());
		logger.info("START METHOD :: findUserActivitysByProjectId");
		logger.debug("resourceAllocationId :: " + projectId);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		String jsonArray = "";
		try {
			List<Activity> findUserActivitysByProjectId = objActivityService.findActiveActivitysByResourceAllocationId(projectId);
			Collections.sort(findUserActivitysByProjectId, new ActivityNameComparator());
			for (Activity activity : findUserActivitysByProjectId) {
				if (activity.getActivityType().equalsIgnoreCase("SEPG")) {
					activity.setActivityType("D");
				} else if (activity.getActivityType().equalsIgnoreCase("Default")) {
					activity.setActivityType("D");
				} else if (activity.getActivityType().equalsIgnoreCase("Custom")) {
					activity.setActivityType("C");
				}
			}
			jsonArray = activityJsonObjectMapper.toJsonArray(findUserActivitysByProjectId);
		} catch (RuntimeException runtimeException) {
			logger.error("RuntimeException occured in findUserActivitysByProjectId method of UserActivityController controller:" + runtimeException);
			logger.info("RuntimeException occured in findUserActivitysByProjectId method of UserActivityController controller:" + runtimeException);
			throw runtimeException;
		} catch (Exception exception) {
			logger.error("Exception occured in findUserActivitysByProjectId method of ActivityController controller:" + exception);
			logger.info("Exception occured in findUserActivitysByProjectId method of ActivityController controller:" + exception);
			throw exception;
		}
		logger.debug("JSON ARRAY TO SEND TO VIEW LAYER::" + jsonArray);
		logger.info("END METHOD :: jsonFindUserActivitysByWeekStartDateBetweenAndEmployeeId");
		return new ResponseEntity<String>(jsonArray, headers, HttpStatus.OK);
	}

	@RequestMapping(params = "find=AllActivityByProjectId", headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> findAllUserActivitysByProjectId(@RequestParam("projectId") Integer projectId, @RequestParam("activityId") String activityId) throws Exception {

		logger.info("User Logged In---" + UserUtil.getCurrentResource().getEmployeeName());
		logger.info("START METHOD :: findUserActivitysByProjectId");
		logger.debug("projectId :: " + projectId);

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		String jsonArray = "";
		try {
			List<Activity> findUserActivitysByProjectId = objActivityService.findAllActivitysByProjectId(projectId, activityId);
			for (Activity activity : findUserActivitysByProjectId) {
				if (activity.getActivityType().equalsIgnoreCase("SEPG")) {
					activity.setActivityType("D");
				} else if (activity.getActivityType().equalsIgnoreCase("Default")) {
					activity.setActivityType("D");
				} else if (activity.getActivityType().equalsIgnoreCase("Custom")) {
					activity.setActivityType("C");
				}
			}
			jsonArray = activityJsonObjectMapper.toJsonArray(findUserActivitysByProjectId);
		} catch (RuntimeException runtimeException) {
			logger.error("RuntimeException occured in findUserActivitysByProjectId method of UserActivityController controller:" + runtimeException);
			logger.info("RuntimeException occured in findUserActivitysByProjectId method of UserActivityController controller:" + runtimeException);
			throw runtimeException;
		} catch (Exception exception) {
			logger.error("Exception occured in findUserActivitysByProjectId method of UserActivityController controller:" + exception);
			logger.info("Exception occured in findUserActivitysByProjectId method of UserActivityController controller:" + exception);
			throw exception;
		}
		logger.debug("JSON ARRAY TO SEND TO VIEW LAYER::" + jsonArray);
		logger.info("END METHOD :: jsonFindUserActivitysByWeekStartDateBetweenAndEmployeeId");
		return new ResponseEntity<String>(jsonArray, headers, HttpStatus.OK);
	}

	/*
	 * User Story #4422(Create a new screen : Configure Project Module under
	 * configuration link)-change start
	 */
	@RequestMapping(params = "find=ActiveModulesByProjectId", headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> findAllModulesByProjectId(@RequestParam("projectId") Integer projectId) throws Exception {

		logger.info("User Logged In---" + UserUtil.getCurrentResource().getEmployeeName());
		logger.info("START METHOD :: findAllModulesByProjectId");
		logger.debug("projectId :: " + projectId);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		String jsonArray = "";
		try {
			List<ProjectModule> findActiveProjectModuleByProjectId = projectModuleService.findActiveProjectModuleByProjectId(projectId);
			jsonArray = projectModulejsonMapper.toJsonArray(findActiveProjectModuleByProjectId);
		} catch (RuntimeException runtimeException) {
			logger.error("RuntimeException occured in findUserActivitysByProjectId method of UserActivityController controller:" + runtimeException);
			logger.info("RuntimeException occured in findUserActivitysByProjectId method of UserActivityController controller:" + runtimeException);
			throw runtimeException;
		} catch (Exception exception) {
			logger.error("Exception occured in findUserActivitysByProjectId method of UserActivityController controller:" + exception);
			logger.info("Exception occured in findUserActivitysByProjectId method of UserActivityController controller:" + exception);
			throw exception;
		}
		logger.debug("JSON ARRAY TO SEND TO VIEW LAYER::" + jsonArray);
		logger.info("END METHOD :: jsonFindUserActivitysByWeekStartDateBetweenAndEmployeeId");
		return new ResponseEntity<String>(jsonArray, headers, HttpStatus.OK);
	}

	@RequestMapping(params = "find=activeSubModulesByModuleId", headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> findActiveSubModulesByModuleId(@RequestParam("moduleName") String moduleName, @RequestParam("resAllocId") Integer resAllocId) throws Exception {

		logger.info("START METHOD :: findActiveSubModulesByModuleId");
		logger.debug("moduleName :: " + moduleName);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		String jsonArray = "";
		try {

			List<ProjectSubModuleDTO> activeSubModulesDTO = projectSubModuleService.findActiveSubModulesByModuleId(moduleName, resAllocId);

			jsonArray = projectSubModuleMapper.toJSONArrayForDropDown(activeSubModulesDTO);

		} catch (RuntimeException runtimeException) {
			logger.error("RuntimeException occured in findUserActivitysByProjectId method of ActivityController controller:" + runtimeException);
			throw runtimeException;
		} catch (Exception exception) {
			logger.error("Exception occured in findUserActivitysByProjectId method of ActivityController controller:" + exception);
			throw exception;
		}
		logger.debug("JSON ARRAY TO SEND TO VIEW LAYER::" + jsonArray);
		logger.info("END METHOD :: jsonFindUserActivitysByWeekStartDateBetweenAndEmployeeId");
		return new ResponseEntity<String>(jsonArray, headers, HttpStatus.OK);
	}

	/*
	 * User Story #4422(Create a new screen : Configure Project Module under
	 * configuration link)-change start
	 */

	@RequestMapping(value = "/delete", method = RequestMethod.DELETE, headers = "Accept=application/json")
	public ResponseEntity<String> deleteFromJson(@RequestParam("minStartDate") @DateTimeFormat(style = "S-") Date minStartDate,
			@RequestParam("maxStartDate") @DateTimeFormat(style = "S-") Date maxStartDate, @RequestParam(value = "employeeId", required = false) Integer employeeId,
			@RequestParam(value = "ID", required = false) Integer id) throws Exception {

		logger.info("User Logged In---" + UserUtil.getCurrentResource().getEmployeeName());
		logger.info("------UserActivityController deleteFromJson method start------");
		boolean isDelete = userActivityService.delete(employeeId, id);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		Resource resource = new Resource();
		resource.setEmployeeId(employeeId);
		String jsonArray = "";
		try {
			if (!isDelete) {
				logger.info("------UserActivityController deleteFromJson method end------");
				return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
			}
		} catch (RuntimeException runtimeException) {
			logger.error("RuntimeException occured in deleteFromJson method of UserActivity controller:" + runtimeException);
			logger.info("RuntimeException occured in deleteFromJson method of UserActivity controller:" + runtimeException);
			throw runtimeException;
		} catch (Exception exception) {
			logger.error("Exception occured in deleteFromJson method of UserActivity controller:" + exception);
			logger.info("Exception occured in deleteFromJson method of UserActivity controller:" + exception);
			throw exception;
		}
		logger.info("------UserActivityController deleteFromJson method end------");
		List<NewTimeSheet> findUserActivitysByWeekStartDateBetweenAndEmployeeId = null;

		jsonArray = jsonObjectMapper.toJsonArray(findUserActivitysByWeekStartDateBetweenAndEmployeeId);
		return new ResponseEntity<String>(jsonArray, headers, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, produces = "text/html", params = "userAction=update")
	public String saveOrUpdate(@ModelAttribute UserActivityForm form, Model uiModel, HttpServletRequest httpServletRequest, @RequestParam(value = "employeeId", required = false) Integer employeeId,
			@RequestParam(value = "apprValsStatusArray", required = false) String uaPKandStatusArray[]) throws Exception {

		logger.info("User Logged In---" + UserUtil.getCurrentResource().getEmployeeName());

		logger.info("------UserActivityController saveOrUpdate method start------");

		userActivityService.saveOrupdate(employeeId, form.getEntries(), form.getSubmitStatus(), uaPKandStatusArray, form.getWeekStartDate(), form.getWeekEndDate(), httpServletRequest, false);

		logger.info("------UserActivityController saveOrUpdate method end------");

		DateTime dateTime = new DateTime(form.getWeekEndDate());

		System.out.println("First date of month: " + dateTime.dayOfMonth().withMinimumValue());

		System.out.println("Last date of month: " + dateTime.dayOfMonth().withMaximumValue());

		return "redirect:/useractivitys?employeeId=" + employeeId + "&minWeekStartDate=" + Constants.DATE_FORMAT.format(dateTime.dayOfMonth().withMinimumValue().toDate()) + "&maxWeekStartDate="
				+ Constants.DATE_FORMAT.format(dateTime.dayOfMonth().withMaximumValue().toDate()) + "&find=ByWeekStartDateBetweenAndEmployeeIdView";

	}

	@RequestMapping(produces = "text/html")
	public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) throws Exception {

		logger.info("User Logged In---" + UserUtil.getCurrentResource().getEmployeeName());
		logger.info("------UserActivityController list method start------");
		try {
			if (page != null || size != null) {
				int sizeNo = size == null ? 10 : size.intValue();
				final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
				uiModel.addAttribute(Constants.USER_ACTIVITYS, userActivityService.findByEntries(firstResult, sizeNo));
				float nrOfPages = (float) userActivityService.countTotal() / sizeNo;
				uiModel.addAttribute(Constants.MAX_PAGES, (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
			} else {
				uiModel.addAttribute(Constants.USER_ACTIVITYS, userActivityService.findAll());
			}
		} catch (RuntimeException runtimeException) {
			logger.error("RuntimeException occured in list method of UserActivityController controller:" + runtimeException);
			logger.info("RuntimeException occured in list method of UserActivityController controller:" + runtimeException);
			throw runtimeException;
		} catch (Exception exception) {
			logger.error("Exception occured in list method of UserActivityController controller:" + exception);
			logger.info("Exception occured in list method of UserActivityController controller:" + exception);
			throw exception;
		}
		logger.info("------UserActivityController list method end------");

		return "useractivitys/list";
	}

	/**
	 * For Read Only List for Managers
	 * 
	 * @param resourceAllocId
	 * @param month
	 * @param day
	 * @param year
	 * @param uiModel
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/readonlyUserActivity/{resourceAllocId}/{month}/{day}/{year}", produces = "text/html")
	public String list(@PathVariable("resourceAllocId") Integer resourceAllocId, @PathVariable("month") String month, @PathVariable("day") String day, @PathVariable("year") String year, Model uiModel)
			throws Exception {

		logger.info("------UserActivityController list method start------");
		logger.info("User Logged In---" + UserUtil.getCurrentResource().getEmployeeName());
		try {

			if (StringUtils.isNotEmpty(month) && StringUtils.isNotEmpty(day) && StringUtils.isNotEmpty(year)) {
				String dateStr = month + "/" + day + "/" + year;
				Date weekEndDate = Constants.DATE_FORMAT.parse(dateStr);
				Calendar weekStartCal = Calendar.getInstance();
				weekStartCal.setTime(weekEndDate);
				weekStartCal.add(Calendar.DATE, -6);
				ResourceAllocation resourceAllocation = resourceAllocationService.findById(resourceAllocId);
				List<UserActivity> userActivities = userActivityService.findUserActivitysByResourceAllocIdAndWeekStartDateEquals(resourceAllocation, weekStartCal.getTime());
				List<UserActivity> userActivities2 = new ArrayList<UserActivity>();
				UserActivityHelper.prepareTotalWeekHours(userActivities );
				for (UserActivity activity : userActivities) {
					UserActivity userActivity = timeHourEntryHelper.prepareUserActivity(activity);
					userActivities2.add(userActivity);
				}
				uiModel.addAttribute(Constants.READ_ONLY_USER_ACTIVITIES, userActivities2);
				uiModel.addAttribute(Constants.EMP_NAME, resourceAllocation.getEmployeeId().getEmployeeName());
				uiModel.addAttribute(Constants.DESIGNATION, resourceAllocation.getEmployeeId().getDesignationId().getDesignationName());
				uiModel.addAttribute(Constants.WEEK_END_DATE, dateStr);
				uiModel.addAttribute(Constants.D1_HEADER, Constants.DF_READONLY_USER_ACTIVITY.format(weekStartCal.getTime()));
				weekStartCal.add(Calendar.DATE, 1);
				uiModel.addAttribute(Constants.D2_HEADER, Constants.DF_READONLY_USER_ACTIVITY.format(weekStartCal.getTime()));
				weekStartCal.add(Calendar.DATE, 1);
				uiModel.addAttribute(Constants.D3_HEADER, Constants.DF_READONLY_USER_ACTIVITY.format(weekStartCal.getTime()));
				weekStartCal.add(Calendar.DATE, 1);
				uiModel.addAttribute(Constants.D4_HEADER, Constants.DF_READONLY_USER_ACTIVITY.format(weekStartCal.getTime()));
				weekStartCal.add(Calendar.DATE, 1);
				uiModel.addAttribute(Constants.D5_HEADER, Constants.DF_READONLY_USER_ACTIVITY.format(weekStartCal.getTime()));
				weekStartCal.add(Calendar.DATE, 1);
				uiModel.addAttribute(Constants.D6_HEADER, Constants.DF_READONLY_USER_ACTIVITY.format(weekStartCal.getTime()));
				weekStartCal.add(Calendar.DATE, 1);
				uiModel.addAttribute(Constants.D7_HEADER, Constants.DF_READONLY_USER_ACTIVITY.format(weekStartCal.getTime()));
				uiModel.addAttribute(Constants.PROJECT_NAME, resourceAllocation.getProjectId().getProjectName());
				uiModel.addAttribute(Constants.TOTAL_NON_PRODUCTIVE_HOURS, UserActivityHelper.getNonProductiveHours(userActivities));
				uiModel.addAttribute(Constants.TOTAL_PRODUCTIVE_HOURS, UserActivityHelper.getProductiveHours(userActivities));
				uiModel.addAttribute(Constants.TOTAL_VACATION_HOURS, UserActivityHelper.getVacationHours(userActivities));

			}
		} catch (RuntimeException runtimeException) {
			logger.error("RuntimeException occured in list method of UserActivityController controller:" + runtimeException);
			logger.info("RuntimeException occured in list method of UserActivityController controller:" + runtimeException);
			throw runtimeException;
		} catch (Exception exception) {
			logger.error("Exception occured in list method of UserActivityController controller:" + exception);
			logger.info("Exception occured in list method of UserActivityController controller:" + exception);
			throw exception;
		}
		logger.info("------UserActivityController list method end------");
		return "useractivitys/readonlylist";
	}

	@RequestMapping(value = "/{id}", produces = "text/html")
	public String show(@PathVariable("id") Integer id, Model uiModel) throws Exception {

		logger.info("User Logged In---" + UserUtil.getCurrentResource().getEmployeeName());
		logger.info("------UserActivityController show method start------");
		try {
			uiModel.addAttribute(Constants.USER_ACTIVITY, userActivityService.findByUserActivityId(id));
			uiModel.addAttribute(Constants.ITEM_ID, id);
		} catch (RuntimeException runtimeException) {
			logger.error("RuntimeException occured in show method of UserActivityController controller:" + runtimeException);
			throw runtimeException;
		} catch (Exception exception) {
			logger.error("Exception occured in show method of UserActivityController controller:" + exception);
			logger.info("Exception occured in show method of UserActivityController controller:" + exception);
			throw exception;
		}
		logger.info("------UserActivityController show method end------");

		return "useractivitys/show";
	}

	@RequestMapping(params = "find=ByEmployeeId", produces = "text/html")
	public String findUserActivitysByEmployeeId(@RequestParam(value = "employeeId", required = false) Integer employeeId, Model uiModel) throws Exception {

		logger.info("User Logged In---" + UserUtil.getCurrentResource().getEmployeeName());
		logger.info("------UserActivityController findUserActivitysByEmployeeId method start------");
		try {
			List<Integer> employeeIdList=new ArrayList<Integer>();
				employeeIdList.add(employeeId);
			
			List<UserActivity> user = userActivityService.findUserActivitysByEmployeeId(' ', userUtil.getUserContextDetails().getEmployeeId(),employeeIdList);
			List<NewTimeSheet> list = new ArrayList<NewTimeSheet>();
			list.add(new NewTimeSheet());
			UserActivityForm entries = new UserActivityForm();
			entries.setEntries(list);
			uiModel.addAttribute(Constants.LIST, entries);
			uiModel.addAttribute(Constants.USER_ACTIVITYS, user);
			uiModel.addAttribute(Constants.PROJECTS, projectService.findAll());
			uiModel.addAttribute(Constants.ACTIVITIES, activityService.findAll());
		} catch (RuntimeException runtimeException) {
			logger.error("RuntimeException occured in findUserActivitysByEmployeeId method of UserActivityController controller:" + runtimeException);
			logger.info("RuntimeException occured in findUserActivitysByEmployeeId method of UserActivityController controller:" + runtimeException);
			throw runtimeException;
		} catch (Exception exception) {
			logger.error("Exception occured in findUserActivitysByEmployeeId method of UserActivityController controller:" + exception);
			logger.info("Exception occured in findUserActivitysByEmployeeId method of UserActivityController controller:" + exception);
			throw exception;
		}
		logger.info("------UserActivityController findUserActivitysByEmployeeId method end------");

		return "useractivitys/list";
	}

	// TODO : REMOVE not used anywhere
	/*
	 * @RequestMapping(params = "find=ByWeekStartDateBetweenAndEmployeeId",
	 * produces = "text/html") public String
	 * findUserActivitysByWeekStartDateBetweenAndEmployeeId(
	 * 
	 * @RequestParam("minWeekStartDate") @DateTimeFormat(style = "M-") Date
	 * minWeekStartDate,
	 * 
	 * @RequestParam("maxWeekStartDate") @DateTimeFormat(style = "M-") Date
	 * maxWeekStartDate, Model uiModel) throws Exception {
	 * 
	 * logger.info("User Logged In---" +
	 * UserUtil.getCurrentResource().getEmployeeName()); logger.info(
	 * "START METHOD :: findUserActivitysByWeekStartDateBetweenAndEmployeeId");
	 * logger.debug("minWeekStartDate ::" + minWeekStartDate);
	 * logger.debug("maxWeekStartDate ::" + maxWeekStartDate); try { Resource
	 * resource = userUtil.getLoggedInResource(); List<UserActivity>
	 * userActivities = userActivityService
	 * .findUserActivitysByWeekStartDateBetweenAndEmployeeId( minWeekStartDate,
	 * maxWeekStartDate, resource.getEmployeeId(), true);
	 * uiModel.addAttribute(Constants.USER_ACTIVITIES, userActivities);
	 * uiModel.addAttribute(Constants.PROJECTS, projectService.findAll());
	 * uiModel.addAttribute(Constants.ACTIVITIES, activityService.findAll());
	 * List<ResourceAllocation> resList = resourceAllocationService
	 * .findResourceAllocationsByEmployeeId(resource);
	 * uiModel.addAttribute(Constants.RESOURCE_ALLOCATION, resList); Date dt =
	 * new Date(); uiModel.addAttribute(Constants.TODAY, dt.getTime()); } catch
	 * (RuntimeException runtimeException) { logger.error(
	 * "RuntimeException occured in findUserActivitysByWeekStartDateBetweenAndEmployeeId method of UserActivityController controller:"
	 * + runtimeException); logger.info(
	 * "RuntimeException occured in findUserActivitysByWeekStartDateBetweenAndEmployeeId method of UserActivityController controller:"
	 * + runtimeException); throw runtimeException; } catch (Exception
	 * exception) { logger.error(
	 * "Exception occured in findUserActivitysByWeekStartDateBetweenAndEmployeeId method of UserActivityController controller:"
	 * + exception); logger.info(
	 * "Exception occured in findUserActivitysByWeekStartDateBetweenAndEmployeeId method of UserActivityController controller:"
	 * + exception); throw exception; }
	 * 
	 * logger.info(
	 * "END METHOD :: findUserActivitysByWeekStartDateBetweenAndEmployeeId");
	 * return "useractivitys/list"; }
	 */

	@RequestMapping(params = "find=ByWeekStartDateBetweenAndEmployeeId_json", headers = "Accept=application/json")
	@ResponseBody
	/*public ResponseEntity<String> jsonFindUserActivitysByWeekStartDateBetweenAndEmployeeId(@RequestParam("minWeekStartDate") @DateTimeFormat(style = "M-") Date minWeekStartDate,
			@RequestParam("maxWeekStartDate") @DateTimeFormat(style = "M-") Date maxWeekStartDate, @RequestParam("employeeId") Integer employeeId) throws Exception {
*/public ResponseEntity<String> jsonFindUserActivitysByWeekStartDateBetweenAndEmployeeId(@RequestParam("minWeekStartDate") @DateTimeFormat(style = "S-") Date minWeekStartDate,
		@RequestParam("maxWeekStartDate") @DateTimeFormat(style = "S-") Date maxWeekStartDate, @RequestParam("employeeId") Integer employeeId) throws Exception {

		logger.info("User Logged In---" + UserUtil.getCurrentResource().getEmployeeName());
		logger.info("START METHOD :: jsonFindUserActivitysByWeekStartDateBetweenAndEmployeeId");
		logger.debug("minWeekStartDate :: " + minWeekStartDate);
		logger.debug("maxWeekStartDate :: " + maxWeekStartDate);
		logger.debug("employeeId :: " + employeeId);
		Resource resource = new Resource();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		String jsonArray = "";
		List<UserActivityViewDTO> activityViewDTOs = new ArrayList<UserActivityViewDTO>();
		try {
			resource.setEmployeeId(employeeId);

			List<NewTimeSheet> findUserActivitysByWeekStartDateBetweenAndEmployeeId = userActivityService.findUserActivitysByWeekStartDateBetweenAndEmployeeId(minWeekStartDate, maxWeekStartDate,
					employeeId, true);
			activityViewDTOs = mapper.convertNewTimeSheetToUserActivityViewDTOList(findUserActivitysByWeekStartDateBetweenAndEmployeeId);

			jsonArray = jsonObjectMapper.toUserActicityViewDTOJsonArray(activityViewDTOs);
		} catch (RuntimeException runtimeException) {
			logger.error("RuntimeException occured in jsonFindUserActivitysByWeekStartDateBetweenAndEmployeeId method of UserActivityController controller:" + runtimeException);
			logger.info("RuntimeException occured in jsonFindUserActivitysByWeekStartDateBetweenAndEmployeeId method of UserActivityController controller:" + runtimeException);
			throw runtimeException;
		} catch (Exception exception) {
			logger.error("Exception occured in jsonFindUserActivitysByWeekStartDateBetweenAndEmployeeId method of UserActivityController controller:" + exception);
			logger.info("Exception occured in jsonFindUserActivitysByWeekStartDateBetweenAndEmployeeId method of UserActivityController controller:" + exception);
			throw exception;
		}
		logger.debug("JSON ARRAY TO SEND TO VIEW LAYER::" + jsonArray);
		logger.info("END METHOD :: jsonFindUserActivitysByWeekStartDateBetweenAndEmployeeId");
		return new ResponseEntity<String>(jsonArray, headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/validate/{id}", method = RequestMethod.POST, produces = "text/html")
	public ResponseEntity<String> validateUserActivity(@PathVariable("id") Integer id, HttpServletRequest request, HttpServletResponse response) throws Exception {

		logger.info("User Logged In---" + UserUtil.getCurrentResource().getEmployeeName());
		logger.info("END METHOD :: validateUserActivity");
		logger.debug("ID :: " + id);
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		try {
			if (userActivityService.validateDeleteForUserActivity(id)) {
				out.print("Pass");
			} else {
				out.print("Fail");
			}
		} catch (RuntimeException runtimeException) {
			logger.error("RuntimeException occured in validateUserActivity method of UserActivityController :" + runtimeException);
			logger.info("RuntimeException occured in validateUserActivity method of UserActivityController :" + runtimeException);
			throw runtimeException;
		} catch (Exception exception) {
			logger.error("Exception occured in validateUserActivity method of UserActivityController :" + exception);
			throw exception;
		}
		return null;
	}

	/**
	 * Method to show unique Project and week start date entry in User Activity
	 * section
	 * 
	 * @throws Exception
	 */
	@RequestMapping(params = "find=ByWeekStartDateBetweenAndEmployeeIdUpdated", headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> jsonFindUserActivitysByWeekStartDateBetweenAndEmployeeIdUpdated(@RequestParam("minWeekStartDate") @DateTimeFormat(style = "S-") Date minWeekStartDate,
			@RequestParam("maxWeekStartDate") @DateTimeFormat(style = "S-") Date maxWeekStartDate, @RequestParam(value = "employeeId", required = false) Integer employeeId) throws Exception {

		logger.info("------UserActivityController jsonFindUserActivitysByWeekStartDateBetweenAndEmployeeIdUpdated method start------");
		logger.info("User Logged In---" + UserUtil.getCurrentResource().getEmployeeName());
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		List<TimesheetSubmissionDTO> timesheetSubmissionDTOs = new ArrayList<TimesheetSubmissionDTO>();
		try {
			timesheetSubmissionDTOs = userActivityService.findUserActivitysByEmployeeIdAndWeekStartDateBetween(minWeekStartDate, maxWeekStartDate, employeeId, false);
		} catch (RuntimeException runtimeException) {
			logger.error("RuntimeException occured in jsonFindUserActivitysByWeekStartDateBetweenAndEmployeeIdUpdated method of UserActivityController :" + runtimeException);
			logger.info("RuntimeException occured in jsonFindUserActivitysByWeekStartDateBetweenAndEmployeeIdUpdated method of UserActivityController :" + runtimeException);
			throw runtimeException;
		} catch (Exception exception) {
			logger.error("Exception occured in jsonFindUserActivitysByWeekStartDateBetweenAndEmployeeIdUpdated method of UserActivityController :" + exception);
			logger.info("Exception occured in jsonFindUserActivitysByWeekStartDateBetweenAndEmployeeIdUpdated method of UserActivityController :" + exception);
			throw exception;
		}
		logger.info("------UserActivityController jsonFindUserActivitysByWeekStartDateBetweenAndEmployeeIdUpdated method end------");
		return new ResponseEntity<String>(jsonObjectMapper.toTimesheetJsonArray(timesheetSubmissionDTOs), headers, HttpStatus.OK);
	}

	@RequestMapping(params = "find=findProjectsByEmployeeIdWithActiveActivityModuleAndSubModule", headers = "Accept=application/json")
	public @ResponseBody
	ResponseEntity<List<ProjectDTO>> findProjectsByEmployeeIdWithActiveActivityModuleAndSubModule(@RequestParam("employeeId") Integer employeeId) throws Exception {

		logger.info("START METHOD :: findProjectsByEmployeeIDJson");
		logger.debug("employeeId :: " + employeeId);
		List<ProjectDTO> projectDTO = null;
		try {
			// TODO: rename method
			projectDTO = userActivityService.findProjectsByEmployeeIdWithActiveActivityModuleAndSubModule(employeeId);
		} catch (RuntimeException runtimeException) {
			logger.error("RuntimeException occured in findProjectsByEmployeeIDJson method of UserActivityController controller:" + runtimeException);
			throw runtimeException;
		} catch (Exception exception) {
			logger.error("Exception occured in findProjectsByEmployeeIDJson method of UserActivityController controller:" + exception);
			throw exception;
		}
		logger.info("END METHOD :: findProjectsByEmployeeIDJson");
		return new ResponseEntity<List<ProjectDTO>>(projectDTO, HttpStatus.OK);
	}
}
