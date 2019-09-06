package org.yash.rms.controller;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.codec.binary.Base64;
import org.joda.time.format.DateTimeFormat;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.yash.rms.domain.ApproveStatus;
import org.yash.rms.domain.MailConfiguration;
import org.yash.rms.domain.Resource;
import org.yash.rms.domain.ResourceAllocation;
import org.yash.rms.domain.Timehrs;
import org.yash.rms.domain.TimehrsView;
import org.yash.rms.domain.UserActivity;
import org.yash.rms.domain.UserTimeSheet;
import org.yash.rms.dto.TimeSheetApprovalDTO;
import org.yash.rms.dto.UserActivityViewDTO;
import org.yash.rms.dto.UserContextDetails;
import org.yash.rms.form.NewTimeSheet;
import org.yash.rms.form.TimeSheet;
import org.yash.rms.helper.EmailHelper;
import org.yash.rms.helper.TimeHourEntryHelper;
import org.yash.rms.helper.UserActivityHelper;
import org.yash.rms.json.mapper.JsonObjectMapper;
import org.yash.rms.service.MailConfigurationService;
import org.yash.rms.service.ProjectService;
import org.yash.rms.service.ResourceAllocationService;
import org.yash.rms.service.ResourceService;
import org.yash.rms.service.RmsCRUDService;
import org.yash.rms.service.TimeHoursService;
import org.yash.rms.service.UserActivityService;
import org.yash.rms.util.Constants;
import org.yash.rms.util.DozerMapperUtility;
import org.yash.rms.util.UserUtil;

@Controller
@RequestMapping("/timehrses")
public class TimeHourEntryController {

	@Autowired
	@Qualifier("ResourceAllocationService")
	private ResourceAllocationService resourceAllocationService;

	@Autowired
	@Qualifier("ResourceService")
	private ResourceService resourceService;

	@Autowired
	@Qualifier("TimeHoursService")
	private TimeHoursService timeHoursService;

	@SuppressWarnings("rawtypes")
	@Autowired
	@Qualifier("DesignationService")
	private RmsCRUDService designationService;

	@SuppressWarnings("rawtypes")
	@Autowired
	@Qualifier("gradeService")
	private RmsCRUDService gradeService;

	@Autowired
	@Qualifier("UserActivityService")
	private UserActivityService userActivityService;

	@Autowired
	private TimeHourEntryHelper timeHourEntryHelper;

	@Autowired
	private JsonObjectMapper<ResourceAllocation> jsonMapper;

	@Autowired
	private DozerMapperUtility dozerMapperUtility;

	@Autowired
	@Qualifier("ProjectService")
	private ProjectService projectService;

	@Autowired
	private UserActivityHelper userActivityHelper;

	@Autowired
	private UserUtil userUtil;

	@Autowired
	private EmailHelper emailHelper;

	@Autowired
	@Qualifier("MailConfigurationService")
	private MailConfigurationService mailConfgService;

	private static final Logger logger = LoggerFactory.getLogger(TimeHourEntryController.class);

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(produces = "text/html")
	public String list(Model uiModel, @RequestParam(value = "active", defaultValue = "active", required = false) String activeOrAll,
			@RequestParam(value = "newTimeSheetStatus", defaultValue = "M", required = false) Character timeSheetStatus, @RequestParam(value = "empId", required = false) Integer empId,
			@RequestParam(value = "yashEmpId", required = false) String yashEmpId, @RequestParam(value = "projectId", required = false) Integer projectId,
			@RequestParam(value = "projectAllocStatus", required = false) String projectAllocStatus, @RequestParam(value = "weekEndDate", required = false) String weekEndDate) throws Exception {
		logger.info("------TimeHourEntryController list method start------");
		boolean checkAllocForManager = false;
		Resource currentLoggedInResource=userUtil.getLoggedInResource();
		if (empId != null && yashEmpId != null) {
			uiModel.addAttribute("employeeId", empId);
			uiModel.addAttribute("yashEmployeeId", yashEmpId);
			uiModel.addAttribute("navigationFlag", true);
		} else {
			uiModel.addAttribute("navigationFlag", false);
		}

		if (projectId != null) {
			uiModel.addAttribute("project_Id", projectId);
			uiModel.addAttribute("projectAllocStatus", projectAllocStatus);
			uiModel.addAttribute("yashEmployeeId", yashEmpId);
			uiModel.addAttribute("projectAllocNavigationFlag", true);
			uiModel.addAttribute("weekEndDate", weekEndDate);

		} else {
			uiModel.addAttribute("projectAllocNavigationFlag", false);
		}

		/*TimeSheetApprovalDTO sheetApprovalDTO = new TimeSheetApprovalDTO();

		sheetApprovalDTO = timeHoursService.getTimeSheetApprovalList(true, activeOrAll, timeSheetStatus, null);
		uiModel.addAttribute(Constants.TIME_HRS, sheetApprovalDTO.getTimehrsViews());
		uiModel.addAttribute("activeOrAll", sheetApprovalDTO.getResourceStatus());
		uiModel.addAttribute("newTimeSheetStatus", sheetApprovalDTO.getTimeSheetStatus());
		uiModel.addAttribute("checkAllocForManager", sheetApprovalDTO.isCheckAllocForManager());*/

		addDateTimeFormatPatterns(uiModel);
		// Set Header values...

		try {
			if (currentLoggedInResource.getUploadImage() != null && currentLoggedInResource.getUploadImage().length > 0) {
				byte[] encodeBase64UserImage = Base64.encodeBase64(currentLoggedInResource.getUploadImage());
				String base64EncodedUser = new String(encodeBase64UserImage, "UTF-8");
				uiModel.addAttribute("UserImage", base64EncodedUser);
			} else {
				uiModel.addAttribute("UserImage", "");
			}
		} catch (Exception exception) {
			logger.error("Exception occured in list method of TimeHourEntry controller:" + exception);
			throw exception;
		}

		uiModel.addAttribute("firstName", currentLoggedInResource.getFirstName() + " " + currentLoggedInResource.getLastName());
		uiModel.addAttribute("designationName", currentLoggedInResource.getDesignationId().getDesignationName());
        
		Calendar cal = Calendar.getInstance();
		cal.setTime(currentLoggedInResource.getDateOfJoining());
		int m = cal.get(Calendar.MONTH) + 1;
		String months = new DateFormatSymbols().getMonths()[m - 1];
		int year = cal.get(Calendar.YEAR);
		uiModel.addAttribute("DOJ", months + "-" + year);
		uiModel.addAttribute("ROLE", currentLoggedInResource.getUserRole());
		// End

		addDateTimeFormatPatterns(uiModel);
		logger.info("------TimeHourEntryController list method end------");
		if (projectId != null)
			return "timehrses/timehrses";
		else
			return "timehrses/list";
			//return "timehrses/timesheet";
	}

	public void addDateTimeFormatPatterns(Model uiModel) {
		uiModel.addAttribute(Constants.TIME_HRS_DATE_FORMAT, DateTimeFormat.patternForStyle("M-", LocaleContextHolder.getLocale()));
	}
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/list/{ActiveOrAll}/{timeSheetStatus}", method = RequestMethod.GET)
	public ResponseEntity<String> list(@PathVariable("ActiveOrAll") String activeOrAll,@PathVariable("timeSheetStatus") Character timeSheetStatus, @RequestParam(value="sEcho") String sEcho,@RequestParam(value="iDisplayStart", defaultValue="0") Integer page, @RequestParam(value="iDisplayLength", defaultValue="10") Integer size,HttpServletRequest request) throws Exception {
		logger.info("------TimeSheetController list method start------");
		JSONObject resultJSON = new JSONObject();
		HttpHeaders headers = new HttpHeaders();
		long totalCount = 0;

		Map<String, Object> resultMap=null;
		headers.add("Content-Type", "application/json; charset=utf-8");
		try {
			
				resultMap=timeHoursService.getTimeSheetApprovalListWithPagination(true, activeOrAll,timeSheetStatus,null, request);
			
				if(resultMap.get("resultData")==null)
					resultJSON.put("aaData","");
				else
					resultJSON.put("aaData",resultMap.get("resultData"));
				
				totalCount=(Long) resultMap.get("totalCount");
				resultJSON.put("sEcho", sEcho);
				resultJSON.put("activeOrAll", activeOrAll);
				resultJSON.put("checkAllocForManager", resultMap.get("checkAllocForManager"));
				
				resultJSON.put("timeSheetStatus", timeSheetStatus);
				resultJSON.put("iTotalRecords", totalCount);
				resultJSON.put("iTotalDisplayRecords", totalCount);
				
		
		} catch (RuntimeException e) {
			logger.error("RuntimeException occured in list method of TimeSheetController:" + e);
			throw e;
		} catch (Exception e) {
			logger.error("RuntimeException occured in list method of TimeSheetController:" + e);
			throw e;
		}

		logger.info("------TimeSheetController list method end------");

		return new ResponseEntity<String>(resultJSON.toString(), headers, HttpStatus.OK);

	} 

	@RequestMapping(value = "/{id}/{isSubmit}/{remarks}/{reject}", method = RequestMethod.POST, headers = "Accept=applicationfind/json")
	public ResponseEntity<String> rejectTimesheet(@PathVariable("id") Integer id, @PathVariable("isSubmit") Character submitStatus, @PathVariable("remarks") String remarks) throws Exception {

		logger.info("------TimeHourEntryController rejectTimesheet method start------");

		Boolean isSuccess = null;

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");

		Integer currentLoggedInUserId = UserUtil.getCurrentResource().getEmployeeId();
		logger.info("aproved/RejectedBy" + currentLoggedInUserId);

		try {
			if (submitStatus.equals(new Character('P')) || submitStatus.equals(new Character('A')) || new Character('R').equals(submitStatus)) {

				isSuccess = userActivityService.findByUserActivityId(id, submitStatus, null, null, remarks, currentLoggedInUserId, null, null, false);

				if (isSuccess == null) {
					return new ResponseEntity<String>("{ \"status\":\"false\"}", headers, HttpStatus.NOT_FOUND);
				}
			}

		} catch (RuntimeException e) {
			logger.error("RuntimeException occured in rejectTimesheet method of TimeHourEntry controller:" + e);
			throw e;
		} catch (Exception e) {
			logger.error("Exception occured in rejectTimesheet method of TimeHourEntry controller:" + e);
			throw e;
		}
		logger.info("------TimeHourEntryController rejectTimesheet method end------");
		return new ResponseEntity<String>("{ \"status\":\"true\"}", headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}/{isApprove}/{isSubmit}/{billedhrs}/{plannedhrs}/{remarks}/{resourceEmployeeId}/{resourceAllocId}", method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<String> approveReviewEntryFromJson(@PathVariable("id") Integer id, @PathVariable(value = "isApprove") Character isApprove,
			@PathVariable(value = "isSubmit") boolean submitStatus, @PathVariable(value = "billedhrs") Double billedhrs, @PathVariable(value = "plannedhrs") Double plannedhrs,
			@PathVariable("remarks") String remarks, @PathVariable("resourceEmployeeId") Integer resourceEmployeeId, @PathVariable("resourceAllocId") Integer resourceAllocId) throws Exception {
		logger.info("------TimeHourEntryController approveReviewEntryFromJson method start------");

		Boolean isSuccess = null;

		Integer currentLoggedInUserId = UserUtil.getCurrentResource().getEmployeeId();

		isSuccess = userActivityService.findByUserActivityId(id, isApprove, billedhrs, plannedhrs, remarks, currentLoggedInUserId, resourceEmployeeId, resourceAllocId, false);

		HttpHeaders headers = new HttpHeaders();
		if (isSuccess == null) {
			return new ResponseEntity<String>("{ \"status\":\"false\"}", headers, HttpStatus.NOT_FOUND);
		}

		logger.info("------TimeHourEntryController approveReviewEntryFromJson method end------");
		return new ResponseEntity<String>("{ \"status\":\"true\"}", headers, HttpStatus.OK);
	}

	@RequestMapping(params = "find=approveAll", headers = "Accept=application/json")
	public ResponseEntity<String> approveAll(@RequestParam("resourceId") Integer resourceId, @RequestParam("plannedHours") String plannedHours, @RequestParam("billedHours") String billedHours,
			@RequestParam("weekendDate") String weekendDates, @RequestParam("resourceAllocId") String resourceAllocId, @RequestParam("remarks") String remarks, @RequestParam(value = "projectId", required = false) Integer projectId, @RequestParam(value = "latestWeekEndDate", required = false) Date latestWeekEndDate, @RequestParam(value = "allocationStatus", required = false) String allocationStatus) throws Exception {
		logger.info("------TimeHourEntryController approveAll method start------");
		logger.info("User Logged In---" + UserUtil.getCurrentResource().getEmployeeName());
		
		Integer loggedInUserId = userUtil.getUserContextDetails().getEmployeeId();
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");

		try {
			List<Integer> employeeIdList=new ArrayList<Integer>();
				employeeIdList.add(resourceId);

			timeHoursService.approveAll(resourceId,loggedInUserId,plannedHours,billedHours,weekendDates,resourceAllocId, remarks,projectId,latestWeekEndDate,allocationStatus,employeeIdList,null, false);

		} catch (RuntimeException runtimeException) {
			logger.error("RuntimeException occured in approveAll method of TimeHourEntry controller:" + runtimeException);
			logger.info("RuntimeException occured in approveAll method of TimeHourEntry controller:" + runtimeException);
			throw runtimeException;
		} catch (Exception exception) {
			logger.error("Exception occured in approveAll method of TimeHourEntry controller:" + exception);
			logger.info("Exception occured in approveAll method of TimeHourEntry controller:" + exception);
			throw exception;
		}

		logger.info("------TimeHourEntryController approveAll method end------");
		return new ResponseEntity<String>("{ \"status\":\"true\"}", headers, HttpStatus.OK);
	}

	/*
	 * @RequestMapping(params = "find=approveAll", headers =
	 * "Accept=application/json") public ResponseEntity<String> approveAll(
	 * 
	 * @RequestParam("resourceId") Integer resourceId) throws Exception {
	 * logger.
	 * info("------TimeHourEntryController approveAll method start------");
	 * 
	 * List<UserActivity> ua = userActivityService
	 * .findUserActivitysByEmployeeId(resourceId, 'P'); // this new list is
	 * created to distinguish the user activities for which // mail has to be
	 * sent on approval List<UserActivity> listForApprovalMail = new
	 * ArrayList<UserActivity>(); HttpHeaders headers = new HttpHeaders(); if
	 * (ua == null) { return new
	 * ResponseEntity<String>("{ \"status\":\"false\"}",headers,
	 * HttpStatus.NOT_FOUND); }
	 * 
	 * try {
	 * 
	 * headers.add("Content-Type", "application/json"); Character status =
	 * ApproveStatus.NOT_APPROVED; int CurrentLoggedInUserId =
	 * userUtil.getCurrentLoggedInUseId(); //added for #2992 UserContextDetails
	 * contextDetails= UserUtil.getCurrentResource(); approvedBy =
	 * CurrentLoggedInUserId
	 * 
	 * //Added for issue of multiple approval emails Date oldWeekEndDate=null;
	 * Date newWeekEndDate=null;
	 * 
	 * if (ua != null && !ua.isEmpty()) { for (UserActivity uaObj : ua) {
	 * logger.info("Behalf Manager: " +
	 * uaObj.getResourceAllocId().getBehalfManager()); logger.info("User Role: "
	 * + userUtil.getLoggedInResource().getUserRole()); //added for #2992 if
	 * (contextDetails.isBehalfManager()) { if (ua != null &&
	 * (uaObj.getResourceAllocId().getProjectId()
	 * .getOffshoreDelMgr().getEmployeeId() == CurrentLoggedInUserId ||
	 * contextDetails.isBehalfManager() == true || userUtil
	 * .getLoggedInResource().getUserRole() .equalsIgnoreCase("ROLE_ADMIN"))) {
	 * 
	 * status = ApproveStatus.APPROVED;
	 * userActivityService.saveUserActivityStatus(uaObj .getId(), status,
	 * userUtil .getUserContextDetails().getUserName(), null,
	 * CurrentLoggedInUserId);
	 * 
	 * //Added for issue of multiple approval emails
	 * newWeekEndDate=uaObj.getWeekEndDate(); if(oldWeekEndDate!=null){
	 * if(newWeekEndDate.getTime()!=oldWeekEndDate.getTime())
	 * listForApprovalMail.add(uaObj); }else{ listForApprovalMail.add(uaObj); }
	 * oldWeekEndDate=newWeekEndDate;
	 * 
	 * }//added for #2992 } else if (ua != null &&
	 * (uaObj.getResourceAllocId().getProjectId()
	 * .getOffshoreDelMgr().getEmployeeId() == CurrentLoggedInUserId || userUtil
	 * .getLoggedInResource().getUserRole()
	 * .equalsIgnoreCase("ROLE_ADMIN")||userUtil
	 * .getLoggedInResource().getUserRole()
	 * .equalsIgnoreCase("ROLE_BG_ADMIN")||userUtil
	 * .getLoggedInResource().getUserRole()
	 * .equalsIgnoreCase("ROLE_DEL_MANAGER"))) { status =
	 * ApproveStatus.APPROVED; userActivityService.saveUserActivityStatus(uaObj
	 * .getId(), status, userUtil .getUserContextDetails().getUserName(), null,
	 * CurrentLoggedInUserId);
	 * 
	 * //Added for issue of multiple approval emails
	 * newWeekEndDate=uaObj.getWeekEndDate(); if(oldWeekEndDate!=null){
	 * if(newWeekEndDate.getTime()!=oldWeekEndDate.getTime())
	 * listForApprovalMail.add(uaObj); }else{ listForApprovalMail.add(uaObj); }
	 * oldWeekEndDate=newWeekEndDate;
	 * 
	 * }
	 * 
	 * if(listForApprovalMail.isEmpty()){
	 * 
	 * }else{ if(!listForApprovalMail.contains(uaObj)){
	 * listForApprovalMail.add(uaObj);
	 * 
	 * } }
	 * 
	 * } }
	 * 
	 * if (!listForApprovalMail.isEmpty()) { for (UserActivity activity :
	 * listForApprovalMail) { Map<String, Object> model = new HashMap<String,
	 * Object>();
	 * 
	 * userActivityHelper.setEmailContentForTimeSheetApproval( model,
	 * activity.getEmployeeId(), activity.getWeekStartDate(),
	 * activity.getWeekEndDate(), status, activity
	 * .getResourceAllocId().getProjectId() .getOffshoreDelMgr().getEmailId(),
	 * "", activity); int confgId = Integer.parseInt(Constants
	 * .getProperty("Timesheet_Approval")); List<MailConfiguration> mailConfg =
	 * mailConfgService .findByProjectId(activity.getResourceAllocId()
	 * .getProjectId().getId(), confgId); if (mailConfg != null &&
	 * mailConfg.size() > 0) { emailHelper.sendEmail(model, mailConfg); }
	 * 
	 * } } } catch (RuntimeException runtimeException) { logger.error(
	 * "RuntimeException occured in approveReviewEntryFromJson method of TimeHourEntry controller:"
	 * + runtimeException); throw runtimeException; } catch (Exception
	 * exception) { logger.error(
	 * "Exception occured in approveReviewEntryFromJson method of TimeHourEntry controller:"
	 * + exception); throw exception; }
	 * 
	 * logger.info(
	 * "------TimeHourEntryController approveReviewEntryFromJson method end------"
	 * ); return new ResponseEntity<String>("{ \"status\":\"true\"}",headers,
	 * HttpStatus.OK); }
	 */

	/*@RequestMapping(value = "/{id}", produces = "text/html")
	public String show(@PathVariable("id") Integer id, Model uiModel) {
		return "";
	}

	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
	public String update(@Valid Timehrs timehrs, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
		return "";
	}*/

	@SuppressWarnings("static-access")
	@RequestMapping(method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<String> createFromJson(@RequestBody String json) throws Exception {
		logger.info("------TimeHourEntryController createFromJson method start------");
		logger.info("User Logged In---" + UserUtil.getCurrentResource().getEmployeeName());
		try {

			Timehrs timehrs = Timehrs.fromJsonToTimehrs(json);
			String userName = UserUtil.getUserContextDetails().getUserName();
			Date currentDate = new Date();
			
			if (timehrs.getId() != null) {
				timehrs.setLastUpdatedId(userName);
				timehrs.setLastupdatedTimestamp(currentDate);
				
				timeHoursService.saveOrupdate(timehrs);

			} else {
				UserActivity ua = timeHoursService.mapToUserActivity(timehrs);
				ua.setRemarks(timehrs.getRemarks());
				//TODO : not of any use, can be removed.
			//	userActivityService.saveOrupdate(ua);
				TimeSheet timeSheet = timeHourEntryHelper.covertUserActivityToTimeSheet(ua);
				if (ua != null) {
					UserTimeSheet userTimeSheet = new UserTimeSheet();
						userTimeSheet.setStatus(timeSheet.getApproveStatus());
						
					NewTimeSheet newTimeSheet = timeHourEntryHelper.convertTimeSheetToNewTimeSheet(timeSheet);
					
					userActivityHelper.populateBeanFromTimeSheet(newTimeSheet, userUtil.getLoggedInResource(), userTimeSheet, ua.getWeekStartDate(), ua.getWeekEndDate(), false);
					
					if (ua.getApproveCode() != null && !(ua.getApproveCode().isEmpty())) {
						userTimeSheet.setApproveCode(ua.getApproveCode());
					}
					
					if (ua.getRejectCode() != null && !(ua.getRejectCode().isEmpty())) {
						userTimeSheet.setRejectCode(ua.getRejectCode());
					}
					userActivityService.saveOrupdate(userTimeSheet);
				}
				timehrs.setCreatedId(userName);
				timehrs.setCreationTimestamp(currentDate);
				timehrs.setLastUpdatedId(userName);
				timehrs.setLastupdatedTimestamp(currentDate);
				timeHoursService.saveOrupdate(timehrs);
			}

		} catch (RuntimeException runtimeException) {
			logger.error("RuntimeException occured in createFromJson method of TimeHourEntry controller:" + runtimeException);
			logger.info("RuntimeException occured in createFromJson method of TimeHourEntry controller:" + runtimeException);
			throw runtimeException;
		} catch (Exception exception) {
			logger.error("Exception occured in createFromJson method of TimeHourEntry controller:" + exception);
			logger.info("Exception occured in createFromJson method of TimeHourEntry controller:" + exception);
			throw exception;
		}

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		logger.info("------TimeHourEntryController createFromJson method end------");
		return new ResponseEntity<String>("{ \"status\":\"true\"}", headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/validate", method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<String> validateTimeHrs(@RequestBody String jsonData) throws Exception {
		logger.info("------TimeHourEntryController validateTimeHrs method start------");
		logger.info("User Logged In---" + UserUtil.getCurrentResource().getEmployeeName());
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		List<UserActivity> userList = new ArrayList<UserActivity>();
		try {
			Timehrs timehrs = Timehrs.fromJsonToTimehrs(jsonData);
			Calendar weekStartDate = Calendar.getInstance();
			weekStartDate.setTime(timehrs.getWeekEndingDate());
			weekStartDate.add(Calendar.DATE, -6);
			userList = userActivityService.findUserActivitysByResourceAllocIdAndWeekStartDateEquals(timehrs.getResourceAllocId(), weekStartDate.getTime());
		} catch (RuntimeException runtimeException) {
			logger.error("RuntimeException occured in createFromJson method of TimeHourEntry controller:" + runtimeException);
			logger.info("RuntimeException occured in createFromJson method of TimeHourEntry controller:" + runtimeException);
			throw runtimeException;
		} catch (Exception exception) {
			logger.error("Exception occured in createFromJson method of TimeHourEntry controller:" + exception);
			logger.info("Exception occured in createFromJson method of TimeHourEntry controller:" + exception);
			throw exception;
		}
		logger.info("------TimeHourEntryController validateTimeHrs method end------");
		return new ResponseEntity<String>(UserActivity.toJsonArray(userList), headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	public ResponseEntity<String> deleteFromJson(@PathVariable("id") Integer id) {
		return null;
	}

	@RequestMapping(params = "find=FindResourceAllocationForResource", headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> findResourceAllocation(@RequestParam("resourceId") Integer resourceId, @RequestParam("weekEndDate") Date weekEndDate) throws Exception {
		logger.info("------TimeHourEntryController findResourceAllocation method start------");
		logger.info("User Logged In---" + UserUtil.getCurrentResource().getEmployeeName());
		HttpHeaders headers = new HttpHeaders();
		Resource resource = new Resource();
		resource.setEmployeeId(resourceId);
		headers.add("Content-Type", "application/json; charset=utf-8");
		Resource resource2 = userUtil.getLoggedInResource();
		Boolean isCurrentLoggedInResourceManager = resource2.getUserRole().equalsIgnoreCase(Constants.ROLE_MANAGER);
		List<ResourceAllocation> allocatedResources = null;
		try {

			if (isCurrentLoggedInResourceManager) {
				allocatedResources = resourceAllocationService.findResourceAllocationsforManager(resource, resource2, weekEndDate);
			} else {
				allocatedResources = resourceAllocationService.findResourceAllocationsByEmployeeIdforTimeHours(resource, weekEndDate);

			}
			logger.info("------TimeHourEntryController findResourceAllocation method end------");
			return new ResponseEntity<String>(ResourceAllocation.toJsonArray(allocatedResources), headers, HttpStatus.OK);
		} catch (RuntimeException runtimeException) {
			logger.error("RuntimeException occured in findResourceAllocation method of TimeHourEntry controller:" + runtimeException);
			logger.info("RuntimeException occured in findResourceAllocation method of TimeHourEntry controller:" + runtimeException);
			throw runtimeException;
		} catch (Exception exception) {
			logger.error("Exception occured in findResourceAllocation method of TimeHourEntry controller:" + exception);
			logger.info("Exception occured in findResourceAllocation method of TimeHourEntry controller:" + exception);
			throw exception;
		}

	}

	@RequestMapping(params = "find=ByResourceIdUpdated", headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> findTimeHours(@RequestParam("resourceId") Integer resourceId, @RequestParam(value = "timeSheetStatus", defaultValue = "P") Character timeSheetStatus,
			@RequestParam(value = "activeOrAll", defaultValue = "active") String activeOrAll) throws Exception {
		HttpHeaders headers = new HttpHeaders();
		logger.info("------TimeHourEntryController findTimeHours method start------");
		headers.add("Content-Type", "application/json; charset=utf-8");
		List<UserActivityViewDTO> activities = new ArrayList<UserActivityViewDTO>();
		UserContextDetails contextDetails = UserUtil.getUserContextDetails();

		try {
			activities = timeHoursService.getTimeHrsEntriesForResource(resourceId, timeSheetStatus, activeOrAll, contextDetails.getEmployeeId(), null, null);
		} catch (RuntimeException runtimeException) {
			logger.error("RuntimeException occured in findTimeHours method of TimeHourEntry controller:" + runtimeException);
			throw runtimeException;
		} catch (Exception exception) {
			logger.error("Exception occured in findTimeHours method of TimeHourEntry controller:" + exception);
			throw exception;
		}
		logger.info("------TimeHourEntryController findTimeHours method end------");
		return new ResponseEntity<String>(UserActivityViewDTO.toJsonArray(activities), headers, HttpStatus.OK);
	}

	@RequestMapping(params = "find=ByResourceIdUpdatedHyper", headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> findTimeHoursHyper(@RequestParam("resourceId") Integer resourceId, @RequestParam("weekEndDate") String weekEndDate,
			@RequestParam(value = "timeSheetStatus", defaultValue = "P") Character timeSheetStatus) throws Exception {
		
		HttpHeaders headers = new HttpHeaders();
		logger.info("------TimeHourEntryController findTimeHours method start------");
		headers.add("Content-Type", "application/json; charset=utf-8");
		
		List<UserActivityViewDTO> activities = new ArrayList<UserActivityViewDTO>();
		UserContextDetails contextDetails = UserUtil.getUserContextDetails();
		
		try {
			activities = timeHoursService.getTimeHrsEntriesForResourceHyper(resourceId, timeSheetStatus, weekEndDate, contextDetails.getEmployeeId());
			
		} catch (RuntimeException runtimeException) {
			logger.error("RuntimeException occured in findTimeHours method of TimeHourEntry controller:" + runtimeException);
			throw runtimeException;
		} catch (Exception exception) {
			logger.error("Exception occured in findTimeHours method of TimeHourEntry controller:" + exception);
			throw exception;
		}
		logger.info("------TimeHourEntryController findTimeHours method end------");
		return new ResponseEntity<String>(UserActivityViewDTO.toJsonArray(activities), headers, HttpStatus.OK);
	}

	@RequestMapping(params = "find=approveTimesheetFromMail")
	public String approveTimesheetFromMail(@RequestParam("resourceAllocId") Integer resourceAllocId, @RequestParam("resourceId") Integer resourceId, @RequestParam("reportedHrs") Double reportedHrs,
			@RequestParam("userActivityId") Integer userActivityId, @RequestParam("timeSheetStatus") Character timeSheetStatus, @RequestParam("code") String code,
			@RequestParam("weekEndDate") String weekEndDate,
			// @RequestParam("fullUrl") String fullUrl,
			Model model) throws Exception {
		logger.info("------TimeHourEntryController approveTimesheetFromMail method start------");
		logger.info("User Logged In---" + UserUtil.getCurrentResource().getEmployeeName());
		// get the record from db...
		Resource resource = userUtil.getLoggedInResource();
		UserActivity userActivity = new UserActivity();
		Timehrs timehrs;
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date endDate = formatter.parse(weekEndDate);
		// Model modelAndView =
		List<UserActivity> userActivitiyList = new ArrayList<UserActivity>();

		timehrs = timeHoursService.findByResAllocId(resourceAllocId, endDate);

		if (timehrs == null) {
			// first save timehrs in db
			timehrs = populateTimeHrs(endDate, resourceAllocId, resourceId, timeSheetStatus);

			UserActivity ua = timeHoursService.mapToUserActivity(timehrs);
				ua.setRemarks(timehrs.getRemarks());
			//TODO : not of any use. can be removed. 
		//	userActivityService.saveOrupdate(ua);
			TimeSheet timeSheet = dozerMapperUtility.covertUserActivityToTimeSheet(ua);
			if (ua != null) {
				
				UserTimeSheet userTimeSheet = new UserTimeSheet();
					userTimeSheet.setStatus(timeSheet.getApproveStatus());
				
				NewTimeSheet newTimeSheet = timeHourEntryHelper.convertTimeSheetToNewTimeSheet(timeSheet);
				
				userActivityHelper.populateBeanFromTimeSheet(newTimeSheet, resource, userTimeSheet, ua.getWeekStartDate(), ua.getWeekEndDate(), false);
				userActivityService.saveOrupdate(userTimeSheet);
			}
			timehrs.setCreatedId(resource.getUsername());
			timehrs.setCreationTimestamp(new Date(System.currentTimeMillis()));
			timehrs.setLastUpdatedId(resource.getUsername());
			timehrs.setLastupdatedTimestamp(new Date(System.currentTimeMillis()));
			timeHoursService.saveOrupdate(timehrs);
		}
		// save billed and planned hrs default to 40 each
		else if (timehrs != null) {
			if (timeSheetStatus == 'A') {
				if (timehrs.getBilledHrs() == null)
					timehrs.setBilledHrs(40.00);
				if (timehrs.getPlannedHrs() == null)
					timehrs.setPlannedHrs(40.00);
			}
			// save these modified timehrs in db
			timehrs.setLastUpdatedId(resource.getUsername());
			timehrs.setLastupdatedTimestamp(new Date(System.currentTimeMillis()));
			timeHoursService.saveOrupdate(timehrs);
		}
		// now change its status to approved
		// check status and code
		userActivity = userActivityService.findByUserActivityId(userActivityId);
		if (userActivity != null) {
			userActivitiyList.add(userActivity);

			if (timeSheetStatus == 'A') {
				if (userActivity.getApproveCode().equalsIgnoreCase(code)) {
					approveReviewEntryFromJson(userActivityId, timeSheetStatus, true, -1.0, -1.0, null, null, null);
					userActivity.setStatus('A');
					model.addAttribute("timesheetStatus", "Approved");
					// findTimeHours(userActivity.getEmployeeId().getEmployeeId(),
					// 'P');
					// model.addAttribute("user", userActivity);

				} else {
					model.addAttribute("status", Constants.TIMESHEET_MAIL_DENY_STATUS);
					// model.addAttribute("user", userActivity);
					// return "timehrses/acknowledge";
				}
			} else {
				if (timeSheetStatus == 'R') {
					if (userActivity.getRejectCode().equalsIgnoreCase(code)) {
						approveReviewEntryFromJson(userActivityId, timeSheetStatus, true, -1.0, -1.0, null, null, null);
						userActivity.setStatus('R');
						model.addAttribute("timesheetStatus", "Rejected");
					} else {
						model.addAttribute("status", Constants.TIMESHEET_MAIL_DENY_STATUS);
					}
				}
			}
			model.addAttribute("user", userActivity);
			return "timehrses/acknowledge";
		} else {
			return null;
		}
	}

	@RequestMapping(value = "/timehrsByProject", method = RequestMethod.GET, headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> findTimeHoursByProjectId(@RequestParam("projectId") Integer projectId, @RequestParam(value = "timeSheetStatus", defaultValue = "P") Character timeSheetStatus,
			@RequestParam(value = "projectAllocStatus") String projectAllocStatus, @RequestParam(value = "weekEndDate") Date weekEndDate) throws Exception {
		HttpHeaders headers = new HttpHeaders();
		logger.info("------TimeHourEntryController findTimeHours method start------");
		headers.add("Content-Type", "application/json; charset=utf-8");
		List<UserActivityViewDTO> activities = new ArrayList<UserActivityViewDTO>();
        try {
			activities = timeHoursService.getTimeHrsEntriesForResource(null, timeSheetStatus, projectAllocStatus, userUtil.getUserContextDetails().getEmployeeId(), projectId, weekEndDate);
			activities = filterUserActivitiesForTheLatestWeekEndDate(activities);
		} catch (RuntimeException runtimeException) {
			logger.error("RuntimeException occured in findTimeHours method of TimeHourEntry controller:" + runtimeException);
			throw runtimeException;
		} catch (Exception exception) {
			logger.error("Exception occured in findTimeHours method of TimeHourEntry controller:" + exception);
			throw exception;
		}
		logger.info("------TimeHourEntryController findTimeHours method end------");
		return new ResponseEntity<String>(UserActivityViewDTO.toJsonArray(activities), headers, HttpStatus.OK);
	}

	private List<UserActivityViewDTO> filterUserActivitiesForTheLatestWeekEndDate(List<UserActivityViewDTO> activities) {
		List<UserActivityViewDTO> filteredUserActivities;
		HashMap filteredUserActivitiesMap = new HashMap<Integer, UserActivity>();
		for (UserActivityViewDTO ua : activities) {
			if (!filteredUserActivitiesMap.containsKey(ua.getEmployeeId().getEmployeeId())) {
				filteredUserActivitiesMap.put(ua.getEmployeeId().getEmployeeId(), ua);
			} else {
			  UserActivityViewDTO u = (UserActivityViewDTO) filteredUserActivitiesMap.get(ua.getEmployeeId().getEmployeeId());
				if (ua.getWeekEndDate().after(u.getWeekEndDate())) {
					filteredUserActivitiesMap.put(ua.getEmployeeId().getEmployeeId(), ua);
				}
			}
		}
		filteredUserActivities = new ArrayList<UserActivityViewDTO>(filteredUserActivitiesMap.values());
		return filteredUserActivities;
	}

	private Timehrs populateTimeHrs(Date weekEndDate, Integer resourceAllocId, Integer resourceId, Character timeSheetStatus) {
		Timehrs timehrs = new Timehrs();
		timehrs.setWeekEndingDate(weekEndDate);
		ResourceAllocation resourceAllocation = new ResourceAllocation();
		resourceAllocation.setId(resourceAllocId);
		timehrs.setResourceAllocId(resourceAllocation);
		Resource resource = new Resource();
		resource.setEmployeeId(resourceId);
		timehrs.setResourceId(resource);
		if (timeSheetStatus == 'A') {
			timehrs.setPlannedHrs(40.00);
			timehrs.setBilledHrs(40.00);
		}
		return timehrs;
	}

}
