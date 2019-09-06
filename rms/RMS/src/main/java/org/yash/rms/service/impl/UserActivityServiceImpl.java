/**
 * 
 */
package org.yash.rms.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yash.rms.dao.ActivityDao;
import org.yash.rms.dao.ResourceAllocationDao;
import org.yash.rms.dao.ResourceDao;
import org.yash.rms.dao.TimeHoursDAO;
import org.yash.rms.dao.TimeSheetDao;
import org.yash.rms.dao.UserActivityDao;
import org.yash.rms.dao.UserNotificationDAO;
import org.yash.rms.domain.Activity;
import org.yash.rms.domain.ApproveStatus;
import org.yash.rms.domain.CustomActivity;
import org.yash.rms.domain.MailConfiguration;
import org.yash.rms.domain.ProjectModule;
import org.yash.rms.domain.ProjectSubModule;
import org.yash.rms.domain.ProjectTicketPriority;
import org.yash.rms.domain.ProjectTicketStatus;
import org.yash.rms.domain.Resource;
import org.yash.rms.domain.ResourceAllocation;
import org.yash.rms.domain.Timehrs;
import org.yash.rms.domain.UserActivity;
import org.yash.rms.domain.UserActivityView;
import org.yash.rms.domain.UserNotification;
import org.yash.rms.domain.UserTimeSheet;
import org.yash.rms.dto.ActivitiesDTO;
import org.yash.rms.dto.ModuleDTO;
import org.yash.rms.dto.ProjectDTO;
import org.yash.rms.dto.ProjectTicketPriorityDTO;
import org.yash.rms.dto.ProjectTicketStatusDTO;
import org.yash.rms.dto.SubModuleDTO;
import org.yash.rms.dto.TimesheetSubmissionDTO;
import org.yash.rms.dto.UserContextDetails;
import org.yash.rms.form.NewTimeSheet;
import org.yash.rms.helper.EmailHelper;
import org.yash.rms.helper.TimeHourEntryHelper;
import org.yash.rms.helper.UserActivityHelper;
import org.yash.rms.helper.UserNotificationHelper;
import org.yash.rms.service.ActivityService;
import org.yash.rms.service.MailConfigurationService;
import org.yash.rms.service.ProjectStatusService;
import org.yash.rms.service.ProjectSubModuleService;
import org.yash.rms.service.ProjectTicketsService;
import org.yash.rms.service.ResourceAllocationService;
import org.yash.rms.service.TimeHoursService;
import org.yash.rms.service.UserActivityService;
import org.yash.rms.util.Constants;
import org.yash.rms.util.DozerMapperUtility;
import org.yash.rms.util.UserUtil;

/**
 * @author arpan.badjatiya
 *
 */
@Service("UserActivityService")
public class UserActivityServiceImpl implements UserActivityService {

	@Autowired
	@Qualifier("UserActivityDao")
	private UserActivityDao userActivityDao;

	@Autowired
	@Qualifier("UserNotificationDAO")
	private UserNotificationDAO userNotificationDAO;

	@Autowired
	@Qualifier("ActivityDao")
	private ActivityDao activityDao;

	@Autowired
	@Qualifier("TimeSheetDao")
	private TimeSheetDao timeSheetDao;

	@Autowired
	@Qualifier("ResourceDao")
	private ResourceDao resourceDao;

	@Autowired
	@Qualifier("TimeHoursDao")
	private TimeHoursDAO timeHoursDAO;
	
	@Autowired
	@Qualifier("ActivityService")
	private ActivityService activityService;
	
	@Autowired
	@Qualifier("projectSubModuleService")
	private ProjectSubModuleService projectSubModuleService;
	
	@Autowired
	private UserUtil userUtil;
	@Autowired
	private ResourceAllocationDao resourceAllocDao;

	@Autowired
	private UserActivityHelper userActivityHelper;

	@Autowired
	private MailConfigurationService mailConfigurationService;

	@Autowired
	private EmailHelper emailHelper;

	@Autowired
	private TimeHourEntryHelper timeHourEntryHelper;

	@Autowired
	@Qualifier("TimeHoursService")
	private TimeHoursService timeHoursService;
	@Autowired
	@Qualifier("MailConfigurationService")
	private MailConfigurationService mailConfgService;
	
	@Autowired
	@Qualifier("ResourceAllocationService")
	private ResourceAllocationService resourceAllocationService;
	
	@Autowired
	@Qualifier("ActivityService")
	private ActivityService objActivityService;

	@Autowired
	private ProjectTicketsService projectTicketsService;

	@Autowired
	private ProjectStatusService projectStatusService;

	
	private static final Logger logger = LoggerFactory.getLogger(UserActivityService.class);
	
	@Transactional
	public boolean delete(Integer employeeId, int id) {

		UserActivity userActivity = userActivityDao.findById(id);

		boolean isSuccess = delete(id);

		System.out.println("User Activity deleted ::" + isSuccess);

		Resource resource = resourceDao.findByEmployeeId(employeeId);

		UserNotification userNotification = new UserNotification();

		UserNotificationHelper.populateBeanFromForm(userNotification, resource.getEmployeeName(), resource, userActivity.getWeekEndDate(), true);

		boolean result = userNotificationDAO.saveOrupdate(userNotification);

		System.out.println("User Notification Saved ::" + result);

		return isSuccess;

	}
	
	@Transactional
	public boolean delete(int id) {

		return userActivityDao.delete(id);
	}
	
	
	public boolean saveOrupdate(UserActivity t) {

		return false;
	}

	@Transactional
	public List<UserActivity> findAll() {

		return userActivityDao.findAll();
	}
	
	@Transactional
	public List<UserActivity> findByEntries(int firstResult, int sizeNo) {

		return userActivityDao.findByEntries(firstResult, sizeNo);
	}
	
	@Transactional
	public long countTotal() {

		return userActivityDao.countTotal();
	}
	
	@Transactional
	public List<UserActivityView> getUserActivityForEmployeeBetweenDate(Integer empid, Date minWeekStartDate, Date maxWeekStartDate) {

		return userActivityDao.getUserActivityForEmployeeBetweenDate(empid, minWeekStartDate, maxWeekStartDate);
	}
	
	@Transactional
	public List<UserActivity> findUserActivitysByResourceAllocId(ResourceAllocation resourceAllocId) {

		return userActivityDao.findUserActivitysByResourceAllocId(resourceAllocId);
	}
	
	@Transactional
	public List<TimesheetSubmissionDTO> findUserActivitysByEmployeeIdAndWeekStartDateBetween(Date minWeekStartDate, Date maxWeekStartDate, Integer employeeId, boolean isDetailedTimesheet) {

		List<UserActivity> userActivityList = userActivityDao.findUserActivitysByWeekStartDateBetweenAndEmployeeId(minWeekStartDate, maxWeekStartDate, employeeId);
        if(userActivityList != null)
		UserActivityHelper.prepareTotalWeekHours(userActivityList );
		if (!isDetailedTimesheet)
			userActivityList = UserActivityHelper.getUniqueUserActivityBasedOnWeekEndDateAndProject(userActivityList);

		return DozerMapperUtility.convertUserActivityDomainToTimesheetSubmissionDTO(userActivityList, employeeId);
	}

	
	public List<NewTimeSheet> findUserActivitysByWeekStartDateBetweenAndEmployeeId(Date minWeekStartDate, Date maxWeekStartDate, Integer employeeId, boolean isDetailedTimesheet) {

		List<UserActivity> userActivityList = userActivityDao.findUserActivitysByWeekStartDateBetweenAndEmployeeId(minWeekStartDate, maxWeekStartDate, employeeId);
		UserActivityHelper.prepareTotalWeekHours(userActivityList );
		if (!isDetailedTimesheet) {

			userActivityList = UserActivityHelper.getUniqueUserActivityBasedOnWeekEndDateAndProject(userActivityList);
		} else {

			userActivityList = UserActivityHelper.getUserActivityBasedOnActiveProjectModule(userActivityList);
			setActivityAndCustomActivity(userActivityList);
		}

		return DozerMapperUtility.convertUserActivityDomainToNewTimeSheet(userActivityList, minWeekStartDate, maxWeekStartDate, employeeId);
	}
	
	@Transactional
	public List<UserActivity> findUserActivitysByResourceAllocIdAndWeekStartDateEquals(ResourceAllocation resourceAllocId, Date weekStartDate) {
		return userActivityDao.findUserActivitysByResourceAllocIdAndWeekStartDateEquals(resourceAllocId, weekStartDate);
	}
	
	@Transactional
	public boolean validateDeleteForUserActivity(Integer id) {
		UserActivity ua = userActivityDao.findById(id);
		if (ua != null) {
			Date wed = ua.getWeekEndDate();
			Calendar weekStartCal = Calendar.getInstance();
			weekStartCal.setTime(wed);
			weekStartCal.add(Calendar.DATE, -6);
			ResourceAllocation ra = ua.getResourceAllocId();
			if (ra != null) {
				List<UserActivity> userActivities = userActivityDao.findUserActivitysByResourceAllocIdAndWeekStartDateEquals(ra, weekStartCal.getTime());
				if (userActivities != null && !userActivities.isEmpty()) {

					for (UserActivity userActivity : userActivities) {
						if (userActivity.getApproveStatus() == 'A') {
							return false;
						}
					}
				}

			}
		}
		return true;
	}

	@Transactional
	public void saveOrupdate(Integer employeeId, List<NewTimeSheet> entries, String submitStatus, String uaPKandStatusArray[], Date weekStartDate, Date weekEndDate, HttpServletRequest httpServletRequest, boolean fromRestService) {

		Resource currentResource = resourceDao.findByEmployeeId(employeeId);
		boolean submitted = false;
		Double totalHours = 0.0;
		Integer resAllocId = 0;
		UserTimeSheet userTimeSheet = null;

		for (NewTimeSheet timesheet : entries) {
			if (timesheet.getResourceAllocId() != null) {
				resAllocId = timesheet.getResourceAllocId();
				break;
			}
		}

		ResourceAllocation resourceAllocation = resourceAllocDao.findById(resAllocId);

		Integer offDelMgrEmpId = resourceAllocation.getProjectId().getOffshoreDelMgr().getEmployeeId();

		List<UserTimeSheet> userTimeSheetList = new ArrayList<UserTimeSheet>();

		Integer count = 0;
		for (NewTimeSheet timeSheet : entries) {
			if (timeSheet.getResourceAllocId() == null)
				continue;
			// Bug fix (Redmine #28)
			if (timeSheet.getApproveStatus() != null) {
				if (timeSheet.getApproveStatus().equals('A')) {
					count++;
					continue;
				}
				if (timeSheet.getApproveStatus().equals('R')) {
					timeSheet.setApproveStatus('N');
				}
			}

			userTimeSheet = new UserTimeSheet();

			userActivityHelper.updateAppStatus(timeSheet, userTimeSheet, uaPKandStatusArray, count, submitStatus, offDelMgrEmpId);

			userActivityHelper.populateBeanFromTimeSheet(timeSheet, currentResource, userTimeSheet, weekStartDate, weekEndDate, fromRestService);

			totalHours = totalHours + UserActivityHelper.getTotalHours(timeSheet);

			count++;
			// Persist the User Time Sheet
			userActivityDao.saveOrupdate(userTimeSheet);

			if (timeSheet.getApproveStatus() != null) {

				if (!(timeSheet.getApproveStatus().equals('P') || timeSheet.getApproveStatus().equals('A'))) {
					userTimeSheetList.add(userTimeSheet);
				}
			}

			if (timeSheet.getApproveStatus() == null) {
				userTimeSheetList.add(userTimeSheet);
			}
		}

		UserNotification userNotification;

		if (currentResource.getCurrentReportingManager() != null) {

			Resource resourceManager = resourceDao.findByEmployeeId(currentResource.getCurrentReportingManager().getEmployeeId());

			userNotification = new UserNotification();

			UserNotificationHelper.populateBeanForSubmitting(userNotification, currentResource.getEmployeeName(), resourceManager, weekEndDate, submitted);

			userNotificationDAO.saveOrupdate(userNotification);
		}

		if (submitStatus.equalsIgnoreCase(Constants.SUBMIT)) {

			sendMailForSubmittedTimesheet(currentResource, userTimeSheetList, submitStatus, weekStartDate, weekEndDate, httpServletRequest);
		}

	}
	
	
	public UserActivity findByUserActivityId(Integer id) {

		return userActivityDao.findById(id);
	}
	
	@Transactional
	public List<UserActivity> findUserActivitysByEmployeeId(Character timeSheetStatus, Integer loggedInUserId,List<Integer> employeeIdList) {

		return userActivityDao.findUserActivitysByEmployeeId(timeSheetStatus, loggedInUserId,employeeIdList);
	}
	
	@Transactional
	public void saveUserActivityStatus(Integer id, Character status, String userName, String remarks, int approvedBy, boolean fromRestService) {

		userActivityDao.saveUserActivityStatus(id, status, userName, remarks, approvedBy, fromRestService);
	}
	
	@Transactional
	public boolean saveOrupdate(UserTimeSheet userTimeSheet) {

		return userActivityDao.saveOrupdate(userTimeSheet);
	}

	@Transactional
	public List<Integer> findUserActivitysByStatus(Character timeSheetStatus, String activeOrAll, List<Integer> projectIds, UserContextDetails userContextDetails) {

		return userActivityDao.findUserActivitysByStatus(timeSheetStatus, activeOrAll, projectIds, userContextDetails);
	}
	
	public List<Integer> findEmployeeUderIRMSRM(Resource loggedInUser, Character timeSheetStatus) {

		return userActivityDao.findEmployeeUderIRMSRM(loggedInUser, timeSheetStatus);
	}

	@Transactional
	public List<UserActivity> findUserActivitysByEmployeeIdHyper(Integer employeeId, Character timeSheetStatus, String weekEndDate) {

		return userActivityDao.findUserActivitysByEmployeeIdHyper(employeeId, timeSheetStatus, weekEndDate);
	}

	
	private boolean sendMailForSubmittedTimesheet(Resource resource, List<UserTimeSheet> userTimesheetList, String submitStatus, Date weekStartDate, Date weekEndDate, HttpServletRequest httpServletRequest) {

		List<UserActivity> userActivities = new ArrayList<UserActivity>();

		for (UserTimeSheet userTimeSheet : userTimesheetList) {

			userActivities.add(findByUserActivityId(userTimeSheet.getId().intValue()));
		}
		UserActivityHelper.prepareTotalWeekHours(userActivities );
		DateTime dt = new DateTime().withDayOfMonth(1);
		Map<String, Object> model = new HashMap<String, Object>();

		Map<String, Object> modelUser = new HashMap<String, Object>();

		List<Integer> resourceAllocations = new ArrayList<Integer>();

		for (UserActivity activity : userActivities) {

			if (resourceAllocations.isEmpty()) {

				resourceAllocations.add(activity.getResourceAllocId().getId());
			} else {

				if (!resourceAllocations.contains(activity.getResourceAllocId().getId())) {

					resourceAllocations.add(activity.getResourceAllocId().getId());
				}
			}
		}

		for (Integer resourceAllocation : resourceAllocations) {

			List<UserActivity> userActivities2 = new ArrayList<UserActivity>();

			for (UserActivity activity : userActivities) {

				if (activity.getResourceAllocId().getId().equals(resourceAllocation)) {

					UserActivity userActivity = timeHourEntryHelper.prepareUserActivity(activity);

					userActivities2.add(userActivity);
				}
			}

			int confgId = Integer.parseInt(Constants.getProperty("Timesheet_Submission"));

			List<MailConfiguration> mailConfg = mailConfigurationService.findByProjectId(userActivities2.get(0).getResourceAllocId().getProjectId().getId(), confgId);

			if (mailConfg != null && mailConfg.size() > 0) {

				userActivityHelper.setEmailContent(model, resource, weekStartDate, weekEndDate, userActivities2, submitStatus, httpServletRequest, "approver");

				try {

					emailHelper.sendEmail(model, mailConfg);

				} catch (Exception ex) {

					logger.error("Could not Send Mail to " + resource.getUsername() + "due to :: " + ex.getMessage());
				}

				userActivityHelper.setEmailContent(modelUser, resource, weekStartDate, weekEndDate, userActivities2, submitStatus, httpServletRequest, "user");

				try {

					emailHelper.sendEmail(modelUser, mailConfg);

				} catch (Exception ex) {

					logger.error("Could not Send Mail to " + resource.getUsername() + "due to :: " + ex.getMessage());

					logger.info("Could not Send Mail to " + resource.getUsername() + "due to :: " + ex.getMessage());
				}
			}
		}

		return true;
	}

	@Transactional
	public List<UserActivity> findUserActivitiesByProjectId(Integer projectId, Character timeSheetStatus, String status, Date weekEndDate) {
		return userActivityDao.findUserActivitiesByProjectId(projectId, timeSheetStatus, status, weekEndDate);
	}
	
	/**
	 * The method returns a list of Projects, Activities, Modules and SubModules based on EmployeeId provided.
	 * 
	 * @author rahul.mathur
	 * @param employeeId
	 * @return List<ProjectDTO>
	 * @throws Exception
	 */
	@Transactional
	public List<ProjectDTO> findProjectsByEmployeeIdWithActiveActivityModuleAndSubModule(Integer employeeId) throws Exception{
			
			List<Activity> activeActivities = null;
			ProjectDTO projectDTO=null;
			List<ProjectSubModule> submodulesList= null;
			List<ProjectDTO> projectDTOList=new ArrayList<ProjectDTO>();
			ActivitiesDTO acivitydto = null;
			List<ActivitiesDTO> activitesDTOList= null;
			ModuleDTO moduledto=null;
			List<ModuleDTO> moduleDTOList=new ArrayList<ModuleDTO>();
			SubModuleDTO submoduleDTO = null;
			List<SubModuleDTO> subModuleDTOList=new ArrayList<SubModuleDTO>();


			List<ResourceAllocation> resourceAllocList = resourceAllocDao.findResourceAllocationByEmployeeId(employeeId);

			if(null != resourceAllocList)
				for (ResourceAllocation resourceAllocation : resourceAllocList) {
					projectDTO=new ProjectDTO();
					
						projectDTO.setProjectId(resourceAllocation.getProjectId().getId());
						projectDTO.setProjectName(resourceAllocation.getProjectId().getProjectName());

					activeActivities  = activityService.findActiveActivitysByResourceAllocationId(resourceAllocation.getId());		
					
					if(null != activeActivities){ 
						activitesDTOList = new ArrayList<ActivitiesDTO>();
						for (Activity activity : activeActivities) {

							acivitydto = new ActivitiesDTO();
								acivitydto.setActivityId(activity.getId());
								acivitydto.setActivityName(activity.getActivityName());
								acivitydto.setActivityType(activity.getActivityType());
								acivitydto.setType(activity.getType());
								acivitydto.setMandatory(activity.isMandatory());
								acivitydto.setProductive(activity.isProductive());
								acivitydto.setCreatedId(activity.getCreatedId());
								acivitydto.setCreationTimestamp(activity.getCreationTimestamp());
								acivitydto.setLastUpdatedId(activity.getLastUpdatedId());
								acivitydto.setLastUpdatedTimestamp(activity.getLastupdatedTimestamp());
								acivitydto.setMax(activity.getMax());
								acivitydto.setFormat(activity.getFormat());
								
							activitesDTOList.add(acivitydto);
						}
					}
					projectDTO.setActivities(activitesDTOList);
					//activitesDTOList=new ArrayList<ActivitiesDTO>();

					Set<ProjectModule> modules = resourceAllocation.getProjectId().getModule();

					if(null != modules) {
						
						for (ProjectModule projectModule : modules) {
							moduledto = new ModuleDTO();
								moduledto.setModuleId(projectModule.getId());
								moduledto.setModuleName(projectModule.getModuleName());
							
							submodulesList= projectSubModuleService.findActiveProjectSubModulesByModuleNameAndProjectId(projectModule.getModuleName(), projectModule.getProjectId().getId());
							if(null != submodulesList) {
								//TODO: move this mapping to DozerMapper.
								for (ProjectSubModule subModule  : submodulesList) {
									submoduleDTO = new SubModuleDTO();
										submoduleDTO.setSubModuleId(subModule.getId());
										submoduleDTO.setSubModuleName(subModule.getSubModuleName());
									subModuleDTOList.add(submoduleDTO);
								}
							}
							moduledto.setSubModuleDTO(subModuleDTOList);
							moduleDTOList.add(moduledto);
							subModuleDTOList=new ArrayList<SubModuleDTO>();
						}
					}
					projectDTO.setModuleDTO(moduleDTOList);
					moduleDTOList=new ArrayList<ModuleDTO>();
					List<ProjectTicketPriority> projectTicketPrioritieList=projectTicketsService.getActiveProjectTicketPriorityForProjectId(resourceAllocation.getProjectId().getId());
					List<ProjectTicketPriorityDTO> projectTicketPriorityDTOList=projectTicketsService.convertProjectTicketPriorityListToDTOList(projectTicketPrioritieList);
					projectDTO.setTicketPriorities(projectTicketPriorityDTOList);
					
					List<ProjectTicketStatus> projectTicketStatusList=projectStatusService.getActiveProjectTicketStatusForProjectId(resourceAllocation.getProjectId().getId());
					List<ProjectTicketStatusDTO> projectTicketStatusDTOList=projectStatusService.convertProjectTicketStatusListToDTOList(projectTicketStatusList);
					projectDTO.setTicketStatus(projectTicketStatusDTOList);					
					projectDTOList.add(projectDTO);

				}
			return projectDTOList;

		}	
	
	
	private void setActivityAndCustomActivity(List<UserActivity> userActivityList) {

		for (UserActivity userActivity : userActivityList) {
			if (userActivity.getActivityId() != null) {
				userActivity.getActivityId().setActivityType("D");
			} else if (userActivity.getCustomActivityId() != null) {
				CustomActivity customActivity = userActivity.getCustomActivityId();
				Activity activity = new Activity();
				activity.setId(customActivity.getId());
				activity.setActivityType("C");
				userActivity.setActivityId(activity);
			}
		}
	}

	@Transactional
	public Boolean findByUserActivityId(Integer userActivityId, Character isApprove, Double billedhrs, Double plannedhrs, String remarks, Integer currentLoggedInUserId, Integer resourceEmployeeId, Integer resourceAllocId, boolean fromRestService) throws Exception {

		Boolean isSuccess = null;

		List<UserActivity> userActivityList = null;

		UserActivity userActivities = userActivityDao.findById(userActivityId);

		Resource currentLoggedInResource = resourceDao.findByEmployeeId(currentLoggedInUserId);

		try {

			String userName = currentLoggedInResource.getUserName();

			userActivityList = userActivityDao.findUserActivitysByResourceAllocIdAndWeekStartDateEquals(userActivities.getResourceAllocId(), userActivities.getWeekStartDate());

			Character status = null;

			if (userActivityList != null && !userActivityList.isEmpty()) {

				for (UserActivity userActivity : userActivityList) {
					
					if (userActivities != null) {

						if (isApprove.equals(ApproveStatus.NOT_APPROVED)) {

							status = ApproveStatus.NOT_APPROVED;
							userActivityDao.saveUserActivityStatus(userActivity.getId(), status, userName, null, currentLoggedInUserId, fromRestService);
						} else if (isApprove.equals(ApproveStatus.APPROVED)) {

							status = ApproveStatus.APPROVED;
							userActivityDao.saveUserActivityStatus(userActivity.getId(), status, userName, null, currentLoggedInUserId, fromRestService);
						} else {

							status = ApproveStatus.REJECTED;
							userActivityDao.saveUserActivityStatus(userActivity.getId(), status, userName, remarks, currentLoggedInUserId, fromRestService);
						}

					}

				}

				if (status.equals('A')) {

					if (plannedhrs != -1 && billedhrs != -1) {

						Timehrs timehrs = new Timehrs();
							
						if (fromRestService) {
							timehrs.setLastUpdatedId(userName+"_REST_API");
						} else {
							timehrs.setLastUpdatedId(userName);
						}
							timehrs.setLastupdatedTimestamp(new Date(System.currentTimeMillis()));
						
						if (plannedhrs == null || billedhrs == null) {
							timehrs.setPlannedHrs(0.0);
							timehrs.setBilledHrs(0.0);
						} else {
							timehrs.setPlannedHrs(plannedhrs);
							timehrs.setBilledHrs(billedhrs);
						}	
						
						timehrs.setWeekEndingDate(userActivities.getWeekEndDate());
						
						if (!("null").equalsIgnoreCase(remarks)) {
							timehrs.setRemarks(remarks);
						}

						ResourceAllocation resourceAllocation = new ResourceAllocation();
							resourceAllocation.setId(resourceAllocId);

						Resource resource = new Resource();
							resource.setEmployeeId(resourceEmployeeId);

						resourceAllocation.setEmployeeId(resource);

						timehrs.setResourceAllocId(resourceAllocation);
						timehrs.setResourceId(resource);
						
						if (timehrs.getId() == null) {
							timehrs.setCreatedId(userName);
							timehrs.setCreationTimestamp(new Date());
						}
						
						if (fromRestService) {
							timehrs.setLastUpdatedId(userName+"_REST_API");
						} else {
							timehrs.setLastUpdatedId(userName);
						}

						timeHoursDAO.saveOrupdate(timehrs);
					}
				}
				
				isSuccess = true;

			}

			if (isApprove.equals(ApproveStatus.APPROVED) || isApprove.equals(ApproveStatus.REJECTED) || isApprove.equals(ApproveStatus.SUBMITTED)) {

				Map<String, Object> model = new HashMap<String, Object>();

				userActivityHelper.setEmailContentForTimeSheetApproval(model, userActivityList.get(0).getEmployeeId(), userActivityList.get(0).getWeekStartDate(), userActivityList.get(0)
						.getWeekEndDate(), status, userActivityList.get(0).getResourceAllocId().getProjectId().getOffshoreDelMgr().getEmailId(), "", userActivityList.get(0), userName);

				List<MailConfiguration> mailConfigurations = null;
				int confgId = Integer.parseInt(Constants.getProperty("Timesheet_Rejection"));

				if (isApprove.equals(ApproveStatus.APPROVED)) {
					mailConfigurations = mailConfgService.findByProjectId(userActivityList.get(0).getResourceAllocId().getProjectId().getId(), 3);
				}

				if (isApprove.equals(ApproveStatus.REJECTED)) {
					mailConfigurations = mailConfgService.findByProjectId(userActivityList.get(0).getResourceAllocId().getProjectId().getId(), 2);
				}

				if (isApprove.equals(ApproveStatus.SUBMITTED)) {
					mailConfigurations = mailConfgService.findByProjectId(userActivityList.get(0).getResourceAllocId().getProjectId().getId(), confgId);
				}

				if (mailConfigurations != null && !mailConfigurations.isEmpty()) {
					emailHelper.sendEmail(model, mailConfigurations);
				}

			}

		} catch (RuntimeException runtimeException) {
			logger.error("RuntimeException occured in approveReviewEntryFromJson method of TimeHourEntry controller:" + runtimeException);
			throw runtimeException;
		} catch (Exception exception) {
			logger.error("Exception occured in approveReviewEntryFromJson method of TimeHourEntry controller:" + exception);
			throw exception;
		}

		return isSuccess;

	}

	@Transactional
	public List<UserActivity> findUserActivitiesForOwnedProjectOfLoggedInUser(Integer employeeId, Character timeSheetStatus, String weekEndDate, Integer loggedInUserId) {
		return userActivityDao.findUserActivitiesForOwnedProjectOfLoggedInUser(employeeId, timeSheetStatus, weekEndDate, loggedInUserId);
	}
}
