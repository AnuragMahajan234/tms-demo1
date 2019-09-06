package org.yash.rms.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yash.rms.dao.ResourceDao;
import org.yash.rms.dao.TimeHoursDAO;
import org.yash.rms.domain.ApproveStatus;
import org.yash.rms.domain.MailConfiguration;
import org.yash.rms.domain.OrgHierarchy;
import org.yash.rms.domain.Project;
import org.yash.rms.domain.Resource;
import org.yash.rms.domain.ResourceAllocation;
import org.yash.rms.domain.Timehrs;
import org.yash.rms.domain.TimehrsEmployeeIdProjectView;
import org.yash.rms.domain.TimehrsView;
import org.yash.rms.domain.UserActivity;
import org.yash.rms.dto.TimeSheetApprovalDTO;
import org.yash.rms.dto.TimehrsViewDTO;
import org.yash.rms.dto.UserActivityViewDTO;
import org.yash.rms.dto.UserContextDetails;
import org.yash.rms.helper.EmailHelper;
import org.yash.rms.helper.UserActivityHelper;
import org.yash.rms.service.MailConfigurationService;
import org.yash.rms.service.ProjectService;
import org.yash.rms.service.ResourceAllocationService;
import org.yash.rms.service.ResourceService;
import org.yash.rms.service.TimeHoursService;
import org.yash.rms.service.UserActivityService;
import org.yash.rms.util.Constants;
import org.yash.rms.util.DataTableParser;
import org.yash.rms.util.DozerMapperUtility;
import org.yash.rms.util.ResourceAllocationSearchCriteria;
import org.yash.rms.util.SearchCriteriaGeneric;
import org.yash.rms.util.UserUtil;

@Service("TimeHoursService")
public class TimeHoursServiceImpl implements TimeHoursService {

	@Autowired
	@Qualifier("TimeHoursDao")
	TimeHoursDAO timeHoursDao;

	@Autowired
	@Qualifier("UserActivityService")
	private UserActivityService userActivityService;

	@Autowired
	@Qualifier("ProjectService")
	ProjectService projectService;

	@Autowired
	@Qualifier("ResourceAllocationService")
	ResourceAllocationService resourceAllocationService;

	@Autowired
	private UserUtil userUtil;
	
	@Autowired
	@Qualifier("ResourceService")
	private ResourceService resourceService;
	
	@Autowired
	@Qualifier("ResourceDao")
	private ResourceDao resourceDao;
	
	@Autowired
	private UserActivityHelper userActivityHelper;
	
	@Autowired
	private EmailHelper emailHelper;
	
	@Autowired
	@Qualifier("MailConfigurationService")
	private MailConfigurationService mailConfgService;
	
	@Autowired
	private DozerMapperUtility mapper;
	
	private static final Logger logger = LoggerFactory.getLogger(TimeHoursServiceImpl.class);

	public boolean delete(int id) {
		return false;
	}

	public boolean saveOrupdate(Timehrs timeHours) {
		return timeHoursDao.saveOrupdate(timeHours);
	}

	public List<TimehrsViewDTO> findAll() {
		return null;
	}

	public List<TimehrsViewDTO> findByEntries(int firstResult, int sizeNo) {
		return null;
	}

	public long countTotal() {
		return 0;
	}

	public List<TimehrsViewDTO> findTimehrsEntries(int firstResult, int maxResults) {
		return null;
	}
	
	
	
	@Transactional
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Map<String, Object> getTimeSheetApprovalListWithPagination(boolean requireCurrentProject, String activeOrAll, Character timeSheetStatus, Integer employeeId, HttpServletRequest request) {
		// TODO Auto-generated method stub
		List<Integer> addedResourceList = new ArrayList<Integer>();
		List<Integer> resourcesListOfOwnProject = new ArrayList<Integer>();
		List<Integer> resourceList = new ArrayList<Integer>();
		Set<Integer> hs = new HashSet<Integer>();
		List<Integer> projectIds = new ArrayList<Integer>();

		boolean checkAllocForManager = false;
		UserContextDetails userContextDetails = null;
		Resource resource1 = null;
	
	
		
		List<TimehrsView> timehrsViewList = new ArrayList<TimehrsView>();
		List<String> projectNames=new ArrayList<String>();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		long totalCount=0;
		
		
		
		if (employeeId != null) { // when call from api
			resource1 = resourceService.find(employeeId);
			userContextDetails = userUtil.getCurrentResource(resource1.getUserName());
		} else { // call from web
			userContextDetails = UserUtil.getUserContextDetails();
			
		}
		Resource resource = new Resource();
		resource.setEmployeeId(userContextDetails.getEmployeeId());
		try {
			List<Integer> userActivities = null;
			List<Project> projectList;
			projectList = projectService.findProjectsByOffshoreDelMgr(userContextDetails.getEmployeeId(), activeOrAll);

			if (null != projectList && !projectList.isEmpty()) {

				for (Project project : projectList) {
					projectIds.add(project.getId());
					projectNames.add(project.getProjectName());
				}
			}

			List<Integer> listofbe = projectService.findProjectsByBehalfManagar(userContextDetails.getEmployeeId(), activeOrAll);
			projectIds.addAll(listofbe);

			if (timeSheetStatus.compareTo('P') == 0) {
				for (int i = 0; i < projectIds.size(); i++) {
					Project projects = projectService.findProject(projectIds.get(i));
					if (projects.getOffshoreDelMgr().getEmployeeId() == userContextDetails.getEmployeeId()) {
						checkAllocForManager = true;
					}
				}
			}

			List<OrgHierarchy> buList = userContextDetails.getAccessRight();
			if (timeSheetStatus.compareTo('P') == 0 || timeSheetStatus.compareTo('L') == 0 || timeSheetStatus.compareTo('M') == 0) {
				userActivities = userActivityService.findUserActivitysByStatus(timeSheetStatus, activeOrAll, projectIds, userContextDetails);
				if(timeSheetStatus.compareTo('P') == 0 || timeSheetStatus.compareTo('L') == 0)
					userActivities.addAll(userActivityService.findEmployeeUderIRMSRM(resource, timeSheetStatus));
			}
			if (Constants.ROLE_ADMIN.equals(userContextDetails.getUserRole())) {

				SearchCriteriaGeneric searchCriteriaGeneric=DataTableParser.getSerchCriteria(request,new TimehrsView());
				timehrsViewList=timeHoursDao.getAllTimehrsViewList(activeOrAll, timeSheetStatus, userActivities, projectNames, userContextDetails, request, searchCriteriaGeneric );
				totalCount=timeHoursDao.totalCount(activeOrAll,timeSheetStatus,userActivities,projectNames, userContextDetails, searchCriteriaGeneric );
				
			} else {
				//Resource resource = new Resource();
				//resource.setEmployeeId(userContextDetails.getEmployeeId());
				List<Project> currResourceProjects = null;
				if (userContextDetails.isBehalfManager()) {
					List<ResourceAllocation> resourceAllocationList = resourceAllocationService.findResourceAllocationsByEmployeeId(resource);

					logger.info("resourceAllocationList returned by findResourceAllocationsByEmployeeId:-" + resourceAllocationList);

					currResourceProjects = new ArrayList<Project>();
					if (null != resourceAllocationList && !resourceAllocationList.isEmpty()) {
						for (ResourceAllocation resourceAllocation : resourceAllocationList) {

							if (activeOrAll.equalsIgnoreCase("active")) {
								if (resourceAllocation != null && resourceAllocation.getBehalfManager() != null
										&& (resourceAllocation.getAllocEndDate() == null || resourceAllocation.getAllocEndDate().compareTo(new Date()) >= 0)) {
									currResourceProjects.add(resourceAllocation.getProjectId());
								}
							}

							else if (activeOrAll.equalsIgnoreCase("all")) {
								if (resourceAllocation != null && resourceAllocation.getBehalfManager() != null && resourceAllocation.getBehalfManager()) {
									currResourceProjects.add(resourceAllocation.getProjectId());
								}
							}
						}
					}
				}

				if (null != currResourceProjects) {
					currResourceProjects.addAll(projectService.findProjectsByOffshoreDelMgr(userContextDetails.getEmployeeId(), activeOrAll));
				} else {
					currResourceProjects = projectService.findProjectsByOffshoreDelMgr(userContextDetails.getEmployeeId(), activeOrAll);
				}

				if (currResourceProjects != null && !currResourceProjects.isEmpty()) {
					for (Project project : currResourceProjects) {
						List<Integer> allocatedResourcesEmployeeId = resourceAllocationService.findAllocatedResourceEmployeeId(project.getId(), activeOrAll);

						logger.info("allocatedResourcesEmployeeId list returned by findAllocatedResourceEmployeeId:-" + allocatedResourcesEmployeeId);

						if (allocatedResourcesEmployeeId != null && !allocatedResourcesEmployeeId.isEmpty()) {
							for (Integer resAllocEmployeeId : allocatedResourcesEmployeeId) {
								if (resAllocEmployeeId != null && resAllocEmployeeId > 0 && !addedResourceList.contains(resAllocEmployeeId)) {
									addedResourceList.add(resAllocEmployeeId);
								}

							}
						}
					}
				}

				// #55 Redmine [Start]
				if (Constants.ROLE_DEL_MANAGER.equals(userContextDetails.getUserRole())) {
					List<Integer> empIdList = new ArrayList<Integer>();
					if (activeOrAll.equalsIgnoreCase("active"))
						empIdList = resourceService.findActiveResourceIdByRM2RM1(userContextDetails.getEmployeeId()); // getting irm/srm onshore/offshore data in case of active
					else if (activeOrAll.equalsIgnoreCase("all"))
						empIdList = resourceService.findResourceIdByRM2RM1(userContextDetails.getEmployeeId()); // getting irm/srm data in case of all
					logger.info("empIdList returned by findResourceIdByRM2RM1:-" + empIdList);
					
					addedResourceList.addAll(empIdList);
					
					currResourceProjects = projectService.findProjectsByOffshoreDelMgr(userContextDetails.getEmployeeId(), activeOrAll);
					if (currResourceProjects != null && !currResourceProjects.isEmpty()) {
						for (Project project : currResourceProjects) {
							List<Integer> allocatedResourcesEmployeeId = resourceAllocationService.findAllocatedResourceEmployeeId(project.getId(), activeOrAll);

							logger.info("allocatedResourcesEmployeeId list returned by findAllocatedResourceEmployeeId:-" + allocatedResourcesEmployeeId);

							if (allocatedResourcesEmployeeId != null && !allocatedResourcesEmployeeId.isEmpty()) {
								for (Integer resAllocEmployeeId : allocatedResourcesEmployeeId) {
									if (resAllocEmployeeId != null && resAllocEmployeeId > 0 && !addedResourceList.contains(resAllocEmployeeId)) {
										resourceList.add(resAllocEmployeeId);
									}

								}
							}
						}
					}
					
					for (Integer empIdAR : addedResourceList) {

						if (userActivities != null) {
							if (userActivities.contains(empIdAR)) {
								resourceList.add(empIdAR);
							}
						}
					}

				/*	for (Integer empIdAR : addedResourceList) {
						if (userActivities.contains(empIdAR)) {
							resourceList.add(empIdAR);
						}else if(timeSheetStatus.compareTo('L')==0 || timeSheetStatus.compareTo('P') == 0){  //if block added by vikas 
							if(!resourceList.contains(empIdAR)){
								resourceList.add(empIdAR);
							}
						}
					 if(userActivities.contains(empIdAR) && timeSheetStatus.compareTo('M')==0){
							if(!resourceList.contains(empIdAR)){
								resourceList.add(empIdAR);
							}
						}
						else if(userActivities.contains(empIdAR) && timeSheetStatus.compareTo('P')==0){
							if(!resourceList.contains(empIdAR)){
								resourceList.add(empIdAR);
							}
						}
						else if(timeSheetStatus.compareTo('L')==0){ 
							if(!resourceList.contains(empIdAR)){
								resourceList.add(empIdAR);
							}
						}
					}*/

				}

				if (Constants.ROLE_BG_ADMIN.equals(userContextDetails.getUserRole())) {
					if (activeOrAll.equalsIgnoreCase("active")) {
						if ((timeSheetStatus.compareTo('L') == 0 || timeSheetStatus.compareTo('P') == 0)) {
						addedResourceList = resourceService.findActiveReourceIdsByBusinessGroup(buList, null,activeOrAll,false);
						resourcesListOfOwnProject = resourceService.findActiveResourceIdsOfOwnProject(buList, userContextDetails.getEmployeeId()); //getting offhore manager list data
						resourcesListOfOwnProject.addAll(addedResourceList);
						hs.addAll(resourcesListOfOwnProject);
						addedResourceList.clear();
						addedResourceList.addAll(hs);
						}
						 else {
								addedResourceList.clear();
								addedResourceList = resourceService.findReourceIdsOfOwnProject(userContextDetails.getEmployeeId());
							}
					}

					/*else if (activeOrAll.equalsIgnoreCase("myproject")) {
						addedResourceList.clear();
						addedResourceList = resourceService.findReourceIdsOfOwnProject(userContextDetails.getEmployeeId());
					}*/

					else if (activeOrAll.equalsIgnoreCase("all")) {
						if ((timeSheetStatus.compareTo('L') == 0 || timeSheetStatus.compareTo('P') == 0)) {
							addedResourceList = resourceService.findReourceIdsByBusinessGroup(buList);
							resourcesListOfOwnProject = resourceService.findResourceIdsOfOwnProject(buList, userContextDetails.getEmployeeId());
							resourcesListOfOwnProject.addAll(addedResourceList);
							hs.addAll(resourcesListOfOwnProject);
							addedResourceList.clear();
							addedResourceList.addAll(hs);
						} else {
							addedResourceList.clear();
							addedResourceList = resourceService.findReourceIdsOfOwnProject(userContextDetails.getEmployeeId());
						}
					}

					for (Integer empIdAR : addedResourceList) {

						if (userActivities != null) {
							if (userActivities.contains(empIdAR)) {
								resourceList.add(empIdAR);
							}
						}
					}
				} // #55 Redmine [End]
				if (Constants.ROLE_MANAGER.equals(userContextDetails.getUserRole()) || Constants.ROLE_SEPG_USER.equals(userContextDetails.getUserRole())
						|| Constants.ROLE_BEHALF_MANAGER.equals(userContextDetails.getUserRole())) {
					for (Integer empIdAR : addedResourceList) {
						if (userActivities.contains(empIdAR)) {
							resourceList.add(empIdAR);
						}
					}
				}

			
				SearchCriteriaGeneric searchCriteriaGeneric=DataTableParser.getSerchCriteria(request,new TimehrsView());
				timehrsViewList = timeHoursDao.managerViewWithPagination(resourceList, true, request, searchCriteriaGeneric);
				totalCount=timeHoursDao.totalCount(resourceList,true, searchCriteriaGeneric);
	
				

			}

		} catch (RuntimeException runtimeException) {
			logger.error("RuntimeException occured in list method of TimeHourEntry service:" + runtimeException);
			runtimeException.printStackTrace();
			throw runtimeException;
		} catch (Exception e) {
			logger.error("RuntimeException occured in list method of TimeHourEntry service:" + e);
			e.printStackTrace();
		}
		resultMap.put("resultData", timehrsViewList);
		resultMap.put("checkAllocForManager", checkAllocForManager);
		resultMap.put("totalCount", totalCount);
	
		return resultMap;
		
	}

	
	private List<TimehrsViewDTO> populateResourceWithTimeHours(List<TimehrsViewDTO> allResources, List<TimehrsEmployeeIdProjectView> projectAllocatedList, TimehrsView timehrsView) throws Exception {
		
		logger.info("------TimeHourEntryServiceImpl populateResourceWithTimeHours method start------");
		try {
			TimehrsViewDTO view = new TimehrsViewDTO();
				view.setEmployeeId(timehrsView.getEmployeeId());
				view.setYashEmpId(timehrsView.getYashEmpId());
				view.setEmployeeName(timehrsView.getEmployeeName());
				view.setDesignation_name(timehrsView.getDesignationName());
				view.setGrade(timehrsView.getGrade());

			if (null != timehrsView.getPlannedCapacity()) {
				view.setTotalPlannedHrs(timehrsView.getPlannedCapacity());
			}
			if (null != timehrsView.getTotalReportedHrs()) {
				view.setTotalReportedHrs(timehrsView.getTotalReportedHrs());
			}
			if (null != timehrsView.getTotalBilledHrs()) {
				view.setTotalBilledHrs(timehrsView.getTotalBilledHrs());
			}

			List<String> projectName = new ArrayList<String>();

			for (TimehrsEmployeeIdProjectView timehrsEmployeeIdProjectView : projectAllocatedList) {

				if (view.getEmployeeId().equals(timehrsEmployeeIdProjectView.getEmployeeId())) {

					String str = timehrsEmployeeIdProjectView.getProjectName();
					projectName.add(str);
				}
			}

			view.setProjectName(projectName);
			allResources.add(view);
		} catch (RuntimeException runtimeException) {
			logger.error("RuntimeException occured in populateResourceWithTimeHours method of TimeHourEntry Service :" + runtimeException);
			throw runtimeException;
		} catch (Exception exception) {
			logger.error("Exception occured in populateResourceWithTimeHours method of TimeHourEntry Service:" + exception);
			throw exception;
		}
		logger.info("------TimeHourEntryServiceImpl populateResourceWithTimeHours method end------");

		return allResources;
	}

	// All methods commented for API development

	/*
	 * public List<List> managerView(String resourceIdList) { // TODO
	 * Auto-generated method stub return
	 * timeHoursDao.managerView(resourceIdList); }
	 */

	/*
	 * public List<List> managerView(String resourceIdList, boolean
	 * requireCurrentProject) { // TODO Auto-generated method stub return
	 * timeHoursDao.managerView(resourceIdList, requireCurrentProject); }
	 */

	/*
	 * public List<Timehrs> findTimeHrsForResources(List<Integer> resources) {
	 * return timeHoursDao.findTimeHrsForResources(resources); }
	 */

	/**
	 * Method to display time Hours for current Users
	 * 
	 * @param resourceId
	 * @return
	 */
	@SuppressWarnings({ "unused", "unchecked", "rawtypes" })
	public List<UserActivityViewDTO> getTimeHrsEntriesForResource(Integer employeeId, Character timeSheetStatus, String status, Integer loggedInUserId, Integer projectId, Date weekEndDate) {
		List<UserActivity> userActivityList = null;
		if (employeeId != null)
		{
			List<Integer> employeeIdList=new ArrayList<Integer>();
			employeeIdList.add(employeeId);
			userActivityList = userActivityService.findUserActivitysByEmployeeId(timeSheetStatus, loggedInUserId,employeeIdList);
		}
		if (projectId != null)
			userActivityList = userActivityService.findUserActivitiesByProjectId(projectId, timeSheetStatus, status, weekEndDate);
		
		UserActivityHelper.prepareTotalWeekHours(userActivityList );
		List<UserActivity> userActivitylists = new ArrayList<UserActivity>();
		List<UserActivity> userActivityfilteredlist = null;

		Resource loggedInResource = resourceService.find(loggedInUserId);
		boolean isBehalfManager = false;
		if(null != loggedInUserId){
			isBehalfManager = resourceService.isResourceBehalfManager(loggedInUserId);
		}

		List<Integer> resourceIdList = new ArrayList<Integer>();
		Map<Integer, UserActivity> userActTimeHoursMap = new HashMap<Integer, UserActivity>();
		Map<Integer, Long> resourceAllocIdWeekEndDateMap = new HashMap<Integer, Long>();
		// Start Updated code to make code optimization

		// commented for code optimization single resource is coming in whole
		// list
		/*
		 * List<Resource> resourceList = new ArrayList<Resource>(); if
		 * (userLists != null && !userLists.isEmpty()) { for (UserActivity ua :
		 * userLists) { if (ua != null) { resourceList.add(ua.getEmployeeId());
		 * } } }
		 * 
		 * List<Integer> ids = new ArrayList<Integer>(); for (Resource resource
		 * : resourceList) { ids.add(resource.getEmployeeId()); }
		 */
		Resource res = new Resource();

		if (userActivityList != null && !userActivityList.isEmpty()) {
			res = userActivityList.get(0).getEmployeeId();
		}

		Map<Integer, List<Timehrs>> timeHrsResourceMap = new HashMap<Integer, List<Timehrs>>();

		if (res != null) {
			List<Timehrs> timeHrsList = timeHoursDao.findTimeHrsForResources(res.getEmployeeId());
			if (timeHrsList != null && !timeHrsList.isEmpty()) {
				for (Timehrs timeHr : timeHrsList) {
					if (timeHr.getResourceId() != null && timeHrsResourceMap.get(timeHr.getResourceId().getEmployeeId()) == null) {
						timeHrsResourceMap.put(timeHr.getResourceId().getEmployeeId(), new ArrayList<Timehrs>());
					}
					if (timeHr.getResourceId() != null) {
						List<Timehrs> timeHrsRetList = timeHrsResourceMap.get(timeHr.getResourceId().getEmployeeId());
						timeHrsRetList.add(timeHr);
						timeHrsResourceMap.put(timeHr.getResourceId().getEmployeeId(), timeHrsRetList);
					}
				}
			}
		}
		if (userActivityList != null && !userActivityList.isEmpty()) {
			for (UserActivity userActivity : userActivityList) {
				if (userActivity != null && userActivity.getWeekEndDate() != null) {
					// TypedQuery<Timehrs> thQuery =
					// Timehrs.findTimehrsesByResourceId(ua.getEmployeeId());
					// List<Timehrs> thList = thQuery.getResultList();
					List<Timehrs> thList = timeHrsResourceMap.get(userActivity.getEmployeeId().getEmployeeId());

					if (thList != null && thList.size() > 0) {
						for (Timehrs thObj : thList) {
							if (thObj.getResourceAllocId() != null && userActivity.getResourceAllocId() != null && thObj.getResourceAllocId().getId().equals(userActivity.getResourceAllocId().getId())
									&& thObj.getWeekEndingDate().compareTo(userActivity.getWeekEndDate()) == 0) {

								userActivity.setBilledHrs(thObj.getBilledHrs());
								userActivity.setRemarks(thObj.getRemarks());
								userActivity.setPlannedHrs(thObj.getPlannedHrs());
								userActivity.setTimeHrsId(thObj.getId());
							}
						}
					}
					if (userActivity != null && userActivity.getResourceAllocId() != null) {
						resourceIdList.add(userActivity.getResourceAllocId().getId());
						if (userActivity.getWeekEndDate() != null)
							if ((userActivity.getTimeHrsId() == null || userActTimeHoursMap.get(userActivity.getTimeHrsId()) == null)
									&& (resourceAllocIdWeekEndDateMap.get(userActivity.getResourceAllocId().getId()) == null || resourceAllocIdWeekEndDateMap.get(userActivity.getResourceAllocId().getId()) != userActivity
											.getWeekEndDate().getTime())) {

								userActivitylists.add(userActivity);

							} else {
								if ((userActivity.getTimeHrsId() == null || userActTimeHoursMap.get(userActivity.getTimeHrsId()) == null) && (resourceAllocIdWeekEndDateMap.get(userActivity.getResourceAllocId().getId()) == null)) {

									userActivitylists.add(userActivity);
								}
							}

						if (userActivity.getTimeHrsId() != null)
							userActTimeHoursMap.put(userActivity.getTimeHrsId(), userActivity);

						if (userActivity.getWeekEndDate() != null)
							resourceAllocIdWeekEndDateMap.put(userActivity.getResourceAllocId().getId(), new Long(userActivity.getWeekEndDate().getTime()));
					}
				}
			}
		}

		/*
		 * UserContextDetails userContextDetails = UserUtil
		 * .getUserContextDetails();
		 */// commented for API development
		if (Constants.ROLE_DEL_MANAGER.equals(loggedInResource.getUserRole())) {
			userActivityfilteredlist = new ArrayList<UserActivity>();
			List<Boolean> flag = new ArrayList<Boolean>();
			List<Project> currResourceProjects = projectService.findProjectsByOffshoreDelMgr(loggedInResource.getEmployeeId(), "");

			List<Integer> projectIds = new ArrayList<Integer>();

			if (null != currResourceProjects && currResourceProjects.size() > 0) {

				for (Project project : currResourceProjects) {
					projectIds.add(project.getId());
				}
			}

			List<Integer> projectId1 = resourceAllocationService.findProjectIdsByEmployeeIdAndIsBehalfManager(loggedInResource.getEmployeeId());

			projectId1.addAll(projectIds);
			for (UserActivity activitiy : userActivitylists) {
				if (activitiy.getResourceAllocId().getProjectId().getOffshoreDelMgr().equals(loggedInResource) 
					|| projectId1.contains(activitiy.getResourceAllocId().getProjectId().getId())
					|| (activitiy.getEmployeeId().getCurrentReportingManager() != null && activitiy.getEmployeeId().getCurrentReportingManager().getEmployeeId()==loggedInResource.getEmployeeId())
					|| (activitiy.getEmployeeId().getCurrentReportingManagerTwo() != null && activitiy.getEmployeeId().getCurrentReportingManagerTwo().getEmployeeId()==loggedInResource.getEmployeeId())){
						
					activitiy.calculateEditFlag(projectId1);
					userActivityfilteredlist.add(activitiy);
				}
			}

			return mapper.convertUserActivityDomainToUserActivityDTOList(userActivityfilteredlist);
		} else {

			if (Constants.ROLE_ADMIN.equals(loggedInResource.getUserRole()) || Constants.ROLE_BG_ADMIN.equals(loggedInResource.getUserRole())) {

				userActivityfilteredlist = new ArrayList<UserActivity>(userActivitylists);
			} else {
				userActivityfilteredlist = new ArrayList<UserActivity>();
				if (Constants.ROLE_MANAGER.equals(loggedInResource.getUserRole())) {
					Resource resource = new Resource();
					resource.setEmployeeId(loggedInResource.getEmployeeId());
					List<Project> currResourceProjects = projectService.findProjectsByOffshoreDelMgr(loggedInResource.getEmployeeId(), "");

					List<ResourceAllocation> resourceAllocationList = resourceAllocationService.findResourceAllocationsByEmployeeId(resource);

					for (ResourceAllocation resourceAllocation : resourceAllocationList) {
						if (resourceAllocation != null && resourceAllocation.getBehalfManager() != null && resourceAllocation.getBehalfManager()) {
							currResourceProjects.add(resourceAllocation.getProjectId());
						}
					}

					if (currResourceProjects != null) {
						for (UserActivity activitiy : userActivitylists) {
							if (activitiy != null && activitiy.getResourceAllocId() != null && activitiy.getResourceAllocId().getProjectId() != null) {
								for (Project proj : currResourceProjects) {
									if (activitiy.getResourceAllocId().getProjectId().getId() == proj.getId()) {
										userActivityfilteredlist.add(activitiy);
									}
								}
							}
						}
					}
				}

				if (isBehalfManager==true && (loggedInResource.getUserRole().equals(Constants.RESOURCE_ROLE) || loggedInResource.getUserRole().equals(Constants.ROLE_SEPG_USER) )) {
				
					Resource resource = new Resource();
					resource.setEmployeeId(loggedInResource.getEmployeeId());
					List<ResourceAllocation> resourceAllocationList = resourceAllocationService.findResourceAllocationsByEmployeeId(resource);

					List<Project> currProjects = new ArrayList<Project>();
					for (ResourceAllocation resourceAllocation : resourceAllocationList) {
						if (resourceAllocation != null && resourceAllocation.getBehalfManager() != null && resourceAllocation.getBehalfManager()) {
							currProjects.add(resourceAllocation.getProjectId());
						}
					}
					if (currProjects != null) {
						for (UserActivity activitiy : userActivitylists) {
							if (activitiy != null && activitiy.getResourceAllocId() != null && activitiy.getResourceAllocId().getProjectId() != null) {
								for (Project proj : currProjects) {
									if (activitiy.getResourceAllocId().getProjectId().getId() == proj.getId()) {
										userActivityfilteredlist.add(activitiy);
									} else if (activitiy.getResourceAllocId().getProjectId().getOffshoreDelMgr().getEmployeeId().equals(loggedInResource.getEmployeeId())) {
										userActivityfilteredlist.add(activitiy);
									}
								}
							}

						}
					}
				}
				if (Constants.ROLE_SEPG_USER.equals(loggedInResource.getUserRole())) {

					Resource resource = new Resource();
					resource.setEmployeeId(loggedInResource.getEmployeeId());
					List<ResourceAllocation> resourceAllocationList = resourceAllocationService.findResourceAllocationsByEmployeeId(resource);

					List<Project> currProjects = new ArrayList<Project>();
					for (ResourceAllocation resourceAllocation : resourceAllocationList) {
						if (resourceAllocation != null && resourceAllocation.getBehalfManager() != null && resourceAllocation.getBehalfManager()) {
							currProjects.add(resourceAllocation.getProjectId());
						}
					}
					if (currProjects != null) {
						for (UserActivity activitiy : userActivitylists) {
							if (activitiy != null && activitiy.getResourceAllocId() != null && activitiy.getResourceAllocId().getProjectId() != null) {
								for (Project proj : currProjects) {
									if (activitiy.getResourceAllocId().getProjectId().getId() == proj.getId()) {
										userActivityfilteredlist.add(activitiy);
									}
								}
							}

						}
					}
				}

			}

			if (!userActivityfilteredlist.isEmpty() && userActivityfilteredlist != null) {
				HashSet hs = new HashSet();
				hs.addAll(userActivityfilteredlist);
				userActivityfilteredlist.clear();
				userActivityfilteredlist.addAll(hs);
			}

			return mapper.convertUserActivityDomainToUserActivityDTOList(userActivityfilteredlist);

		}
	}

	@SuppressWarnings({ "unused", "unchecked", "rawtypes" })
	public List<UserActivityViewDTO> getTimeHrsEntriesForResourceHyper(Integer employeeId, Character timeSheetStatus, String weekEndDate, Integer loggedInUserId) {
		
		List<UserActivity> userActivities = userActivityService.findUserActivitysByEmployeeIdHyper(employeeId, timeSheetStatus, weekEndDate);
		
		List<UserActivity> userActivityList = new ArrayList<UserActivity>();
		List<UserActivity> userActivityFilteredList = null;
		List<Integer> resourceIdList = new ArrayList<Integer>();
		
		Map<Integer, UserActivity> userActTimeHoursMap = new HashMap<Integer, UserActivity>();
		Map<Integer, Long> resourceAllocIdWeekEndDateMap = new HashMap<Integer, Long>();

		Resource loggedInResource = resourceService.find(loggedInUserId);
		
		Resource resourceFromUserActivity = null;
		 
		if(userActivities.size() > 0) {

			resourceFromUserActivity = userActivities.get(0).getEmployeeId();
			
		} else {
			
			return null;
		}
		
		Map<Integer, List<Timehrs>> timeHrsResourceMap = new HashMap<Integer, List<Timehrs>>();

		if (resourceFromUserActivity != null) {
			
			List<Timehrs> timeHrsList = timeHoursDao.findTimeHrsForResources(resourceFromUserActivity.getEmployeeId());
			
			if (timeHrsList != null && !timeHrsList.isEmpty()) {
				
				for (Timehrs timeHr : timeHrsList) {
					
					if (timeHr.getResourceId() != null && timeHrsResourceMap.get(timeHr.getResourceId().getEmployeeId()) == null) {
						
						timeHrsResourceMap.put(timeHr.getResourceId().getEmployeeId(), new ArrayList<Timehrs>());
					}
					if (timeHr.getResourceId() != null) {
						
						List<Timehrs> timeHrsRetList = timeHrsResourceMap.get(timeHr.getResourceId().getEmployeeId());
							timeHrsRetList.add(timeHr);
						
						timeHrsResourceMap.put(timeHr.getResourceId().getEmployeeId(), timeHrsRetList);
					}
				}
			}
		}

		if (userActivities != null && !userActivities.isEmpty()) {
			
			for (UserActivity userActivity : userActivities) {
				
				if (userActivity != null) {
					
					List<Timehrs> thList = timeHrsResourceMap.get(userActivity.getEmployeeId().getEmployeeId());

					if (thList != null && thList.size() > 0) {
						for (Timehrs thObj : thList) {
							if (thObj.getResourceAllocId() != null && userActivity.getResourceAllocId() != null && thObj.getResourceAllocId().getId().equals(userActivity.getResourceAllocId().getId())
									&& thObj.getWeekEndingDate().compareTo(userActivity.getWeekEndDate()) == 0) {

								userActivity.setBilledHrs(thObj.getBilledHrs());
								userActivity.setRemarks(thObj.getRemarks());
								userActivity.setPlannedHrs(thObj.getPlannedHrs());
								userActivity.setTimeHrsId(thObj.getId());
							}
						}
					}
					if (userActivity != null && userActivity.getResourceAllocId() != null) {
						resourceIdList.add(userActivity.getResourceAllocId().getId());
						if ((userActivity.getTimeHrsId() == null || userActTimeHoursMap.get(userActivity.getTimeHrsId()) == null)
								&& (resourceAllocIdWeekEndDateMap.get(userActivity.getResourceAllocId().getId()) == null || resourceAllocIdWeekEndDateMap.get(userActivity.getResourceAllocId().getId()) != userActivity
										.getWeekEndDate().getTime())) {
							userActivityList.add(userActivity);
						}
						if (userActivity.getTimeHrsId() != null)
							userActTimeHoursMap.put(userActivity.getTimeHrsId(), userActivity);
						resourceAllocIdWeekEndDateMap.put(userActivity.getResourceAllocId().getId(), new Long(userActivity.getWeekEndDate().getTime()));
					}
				}
			}
		}

		if (Constants.ROLE_DEL_MANAGER.equals(loggedInResource.getUserRole())) {
			userActivityFilteredList = new ArrayList<UserActivity>();
			List<Boolean> flag = new ArrayList<Boolean>();
			List<Project> currResourceProjects = projectService.findProjectsByOffshoreDelMgr(loggedInResource.getEmployeeId(), "");

			List<Integer> projectIds = new ArrayList<Integer>();

			if (null != currResourceProjects && currResourceProjects.size() > 0) {

				for (Project project : currResourceProjects) {
					projectIds.add(project.getId());
				}
			}

			List<Integer> projectId = resourceAllocationService.findProjectIdsByEmployeeIdAndIsBehalfManager(loggedInResource.getEmployeeId());

			projectId.addAll(projectIds);
			for (UserActivity activitiy : userActivityList) {
				if (activitiy.getResourceAllocId().getProjectId().getOffshoreDelMgr().equals(loggedInResource) || projectId.contains(activitiy.getResourceAllocId().getProjectId().getId())
						|| (activitiy.getEmployeeId().getCurrentReportingManager() != null && activitiy.getEmployeeId().getCurrentReportingManager().equals(loggedInResource))
						|| (activitiy.getEmployeeId().getCurrentReportingManagerTwo() != null && activitiy.getEmployeeId().getCurrentReportingManagerTwo().equals(loggedInResource))) {
					activitiy.calculateEditFlag(projectId);
					userActivityFilteredList.add(activitiy);
				}
			}
			return mapper.convertUserActivityDomainToUserActivityDTOList(userActivityFilteredList);
		} else {

			if (Constants.ROLE_ADMIN.equals(loggedInResource.getUserRole()) || Constants.ROLE_BG_ADMIN.equals(loggedInResource.getUserRole())) {

				userActivityFilteredList = new ArrayList<UserActivity>(userActivityList);
			} else {
				userActivityFilteredList = new ArrayList<UserActivity>();
				if (Constants.ROLE_MANAGER.equals(loggedInResource.getUserRole())) {
					Resource resource = new Resource();
					resource.setEmployeeId(loggedInResource.getEmployeeId());
					List<Project> currResourceProjects = projectService.findProjectsByOffshoreDelMgr(loggedInResource.getEmployeeId(), "");

					List<ResourceAllocation> resourceAllocationList = resourceAllocationService.findResourceAllocationsByEmployeeId(resource);

					for (ResourceAllocation resourceAllocation : resourceAllocationList) {
						if (resourceAllocation != null && resourceAllocation.getBehalfManager() != null && resourceAllocation.getBehalfManager()) {
							currResourceProjects.add(resourceAllocation.getProjectId());
						}
					}

					if (currResourceProjects != null) {
						for (UserActivity activitiy : userActivityList) {
							if (activitiy != null && activitiy.getResourceAllocId() != null && activitiy.getResourceAllocId().getProjectId() != null) {
								for (Project proj : currResourceProjects) {
									if (activitiy.getResourceAllocId().getProjectId().getId() == proj.getId()) {
										userActivityFilteredList.add(activitiy);
									}
								}
							}
						}
					}
				}

				if (Constants.ROLE_BEHALF_MANAGER.equals(loggedInResource.getUserRole())) {

					Resource resource = new Resource();
					resource.setEmployeeId(loggedInResource.getEmployeeId());
					List<ResourceAllocation> resourceAllocationList = resourceAllocationService.findResourceAllocationsByEmployeeId(resource);

					List<Project> currProjects = new ArrayList<Project>();
					for (ResourceAllocation resourceAllocation : resourceAllocationList) {
						if (resourceAllocation != null && resourceAllocation.getBehalfManager() != null && resourceAllocation.getBehalfManager()) {
							currProjects.add(resourceAllocation.getProjectId());
						}
					}
					if (currProjects != null) {
						for (UserActivity activitiy : userActivityList) {
							if (activitiy != null && activitiy.getResourceAllocId() != null && activitiy.getResourceAllocId().getProjectId() != null) {
								for (Project proj : currProjects) {
									if (activitiy.getResourceAllocId().getProjectId().getId() == proj.getId()) {
										userActivityFilteredList.add(activitiy);
									} else if (activitiy.getResourceAllocId().getProjectId().getOffshoreDelMgr().getEmployeeId().equals(loggedInResource.getEmployeeId())) {
										userActivityFilteredList.add(activitiy);
									}
								}
							}
						}
					}
				}
				if (Constants.ROLE_SEPG_USER.equals(loggedInResource.getUserRole())) {

					Resource resource = new Resource();
						resource.setEmployeeId(loggedInResource.getEmployeeId());
					List<ResourceAllocation> resourceAllocationList = resourceAllocationService.findResourceAllocationsByEmployeeId(resource);

					List<Project> currProjects = new ArrayList<Project>();
					for (ResourceAllocation resourceAllocation : resourceAllocationList) {
						if (resourceAllocation != null && resourceAllocation.getBehalfManager() != null && resourceAllocation.getBehalfManager()) {
							currProjects.add(resourceAllocation.getProjectId());
						}
					}
					if (currProjects != null) {
						for (UserActivity activitiy : userActivityList) {
							if (activitiy != null && activitiy.getResourceAllocId() != null && activitiy.getResourceAllocId().getProjectId() != null) {
								for (Project proj : currProjects) {
									if (activitiy.getResourceAllocId().getProjectId().getId() == proj.getId()) {
										userActivityFilteredList.add(activitiy);
									}
								}
							}
						}
					}
				}
			}

			if (!userActivityFilteredList.isEmpty() && userActivityFilteredList != null) {
				HashSet hs = new HashSet();
					hs.addAll(userActivityFilteredList);
				userActivityFilteredList.clear();
				userActivityFilteredList.addAll(hs);
			}

			return mapper.convertUserActivityDomainToUserActivityDTOList(userActivityFilteredList);
		}
	}

	public UserActivity mapToUserActivity(Timehrs timeHours) {

		return timeHoursDao.mapToUserActivity(timeHours);
	}

	public boolean saveOrupdate(TimehrsViewDTO t) {

		return false;
	}

	public List<TimehrsView> resourceAllocationPagination(int firstResult, int maxResults, ResourceAllocationSearchCriteria resourceAllocationSearchCriteria, String activeOrAll, boolean search) {

		return timeHoursDao.resourceAllocationPagination(firstResult, maxResults, resourceAllocationSearchCriteria, activeOrAll, search);
	}

	public List<TimehrsView> managerViewPagination(List<Integer> resourceIdList, int firstResult, int maxResults, ResourceAllocationSearchCriteria resourceAllocationSearchCriteria, boolean search) {

		return timeHoursDao.managerViewPagination(resourceIdList, firstResult, maxResults, resourceAllocationSearchCriteria, search);
	}

	public long countSearch(ResourceAllocationSearchCriteria resourceAllocationSearchCriteria, String activeOrAll, List<OrgHierarchy> businessGroup, List<Integer> projectIdList , Integer resourceId, boolean manager, boolean search) {

		return timeHoursDao.countSearch(resourceAllocationSearchCriteria, activeOrAll, businessGroup, projectIdList, resourceId, manager, search);
	}

	public Timehrs findByResAllocId(int timehrsId, Date weekEndDate) {

		return timeHoursDao.findByResAllocId(timehrsId, weekEndDate);
	}

	public List<List> managerViewForDelManager(List<Integer> resourceIdList, boolean requireCurrentProject, List<Project> projectNameList) {

		return timeHoursDao.managerViewForDelManager(resourceIdList, requireCurrentProject, projectNameList);
	}

	public void approveAll(Integer resourceId, Integer loggedInUserId, String plannedHours, String billedHours, String weekendDates, String resourceAllocId, String remarks, Integer projectId, Date latestWeekEndDate, String allocationStatus,List<Integer> employeeIdList, List<UserActivity> userActivityList, boolean fromRestService) throws Exception {
		
		if(userActivityList == null)
		{
			userActivityList = fetchUserActivityListForAllTimeSheetApproval(resourceId, loggedInUserId, projectId, latestWeekEndDate, allocationStatus, employeeIdList);
		}
		
		Resource resourceDetails = resourceDao.findByEmployeeId(loggedInUserId);
		
		String userName = resourceDetails.getUserName();
		Date currentDate = new Date();
		
		createTimeHoursFromAllTimeSheetApproval(plannedHours, billedHours, weekendDates, resourceAllocId, remarks, userName, currentDate, employeeIdList, fromRestService);
				
		List<UserActivity> listForApprovalMail = new ArrayList<UserActivity>();
		
		Character status = ApproveStatus.NOT_APPROVED;
		
		try {

			boolean isBehalfManager = false;
			
			if(null != loggedInUserId) isBehalfManager = resourceService.isResourceBehalfManager(loggedInUserId);
			
			Date oldWeekEndDate = null;
			Date newWeekEndDate = null;
			
			if (userActivityList != null && !userActivityList.isEmpty()) {
				
				for (UserActivity uaObj : userActivityList) {
					
					logger.info("Behalf Manager: " + uaObj.getResourceAllocId().getBehalfManager());
					logger.info("User Role: " + resourceDetails.getUserRole());
					
					if(isBehalfManager == true && (resourceDetails.getUserRole().equals(Constants.RESOURCE_ROLE) || resourceDetails.getUserRole().equals(Constants.ROLE_SEPG_USER) )){
						
						if (userActivityList != null && (uaObj.getResourceAllocId().getProjectId().getOffshoreDelMgr().getEmployeeId() == loggedInUserId || (isBehalfManager==true && (resourceDetails.getUserRole().equals(Constants.RESOURCE_ROLE) || resourceDetails.getUserRole().equals(Constants.ROLE_SEPG_USER) )) || resourceDetails.getUserRole().equalsIgnoreCase("ROLE_ADMIN"))) {
							
							status = ApproveStatus.APPROVED;
							
							userActivityService.saveUserActivityStatus(uaObj.getId(), status, userName, null, loggedInUserId, fromRestService);
							
							newWeekEndDate = uaObj.getWeekEndDate();
							
							if (oldWeekEndDate != null) {
								
								if (newWeekEndDate.getTime() != oldWeekEndDate.getTime()) listForApprovalMail.add(uaObj);
								
							} else {
								listForApprovalMail.add(uaObj);
							}
							
							oldWeekEndDate = newWeekEndDate;
						} // added for #2992
					} else if (userActivityList != null && (uaObj.getResourceAllocId().getProjectId().getOffshoreDelMgr().getEmployeeId() == loggedInUserId || resourceDetails.getUserRole().equalsIgnoreCase("ROLE_ADMIN") || resourceDetails.getUserRole().equalsIgnoreCase("ROLE_BG_ADMIN") || resourceDetails.getUserRole().equalsIgnoreCase("ROLE_DEL_MANAGER") || resourceDetails.getUserRole().equalsIgnoreCase("ROLE_MANAGER"))) {
						
						status = ApproveStatus.APPROVED;
						
						userActivityService.saveUserActivityStatus(uaObj.getId(), status, userName, null, loggedInUserId, fromRestService);
						
						newWeekEndDate = uaObj.getWeekEndDate();
						
						if (oldWeekEndDate != null) {
							
							if (newWeekEndDate.getTime() != oldWeekEndDate.getTime()) listForApprovalMail.add(uaObj);
							
						} else {
							
							listForApprovalMail.add(uaObj);
						}
						
						oldWeekEndDate = newWeekEndDate;
					}
				}
			}
		} catch (RuntimeException runtimeException) {
			logger.error("RuntimeException occured in approveReviewEntryFromJson method of TimeHourEntry controller:" + runtimeException);
			logger.info("RuntimeException occured in approveReviewEntryFromJson method of TimeHourEntry controller:" + runtimeException);
			throw runtimeException;
		} catch (Exception exception) {
			logger.error("Exception occured in approveReviewEntryFromJson method of TimeHourEntry controller:" + exception);
			logger.info("Exception occured in approveReviewEntryFromJson method of TimeHourEntry controller:" + exception);
			throw exception;
		}
		
		sendEmailOnTimeSheetApproval(userName, listForApprovalMail, status);
	}
	
	@Transactional
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public TimeSheetApprovalDTO getTimeSheetApprovalList(boolean requireCurrentProject, String activeOrAll, Character timeSheetStatus, Integer employeeId) {
		// TODO Auto-generated method stub
		List<Integer> addedResourceList = new ArrayList<Integer>();
		List<Integer> resourcesListOfOwnProject = new ArrayList<Integer>();
		List<Integer> resourceList = new ArrayList<Integer>();
		Set<Integer> hs = new HashSet<Integer>();
		List<TimehrsViewDTO> allResources = new ArrayList<TimehrsViewDTO>();
		List<org.yash.rms.domain.TimehrsView> adminList = null;
		List<org.yash.rms.domain.TimehrsEmployeeIdProjectView> projectAllocatedList = null;
		List<Integer> projectIds = new ArrayList<Integer>();

		TimeSheetApprovalDTO sheetApprovalDTO = new TimeSheetApprovalDTO();
		boolean checkAllocForManager = false;
		UserContextDetails userContextDetails = null;
		Resource resource1 = null;
		if (employeeId != null) { // when call from api
			resource1 = resourceService.find(employeeId);
			userContextDetails = userUtil.getCurrentResource(resource1.getUserName());
		} else { // call from web
			userContextDetails = UserUtil.getUserContextDetails();
		}

		try {
			List<Integer> userActivities = null;
			List<Project> projectList;
			projectList = projectService.findProjectsByOffshoreDelMgr(userContextDetails.getEmployeeId(), activeOrAll);

			if (null != projectList && !projectList.isEmpty()) {

				for (Project project : projectList) {
					projectIds.add(project.getId());
				}
			}

			List<Integer> listofbe = projectService.findProjectsByBehalfManagar(userContextDetails.getEmployeeId(), activeOrAll);
			projectIds.addAll(listofbe);

			if (timeSheetStatus.compareTo('P') == 0) {
				for (int i = 0; i < projectIds.size(); i++) {
					Project projects = projectService.findProject(projectIds.get(i));
					if (projects.getOffshoreDelMgr().getEmployeeId() == userContextDetails.getEmployeeId()) {
						checkAllocForManager = true;
					}
				}
			}

			List<OrgHierarchy> buList = userContextDetails.getAccessRight();
			if (timeSheetStatus.compareTo('P') == 0 || timeSheetStatus.compareTo('L') == 0 || timeSheetStatus.compareTo('M') == 0) {
				userActivities = userActivityService.findUserActivitysByStatus(timeSheetStatus, activeOrAll, projectIds, userContextDetails);
			}
			if (Constants.ROLE_ADMIN.equals(userContextDetails.getUserRole())) {

				List<List> list = timeHoursDao.adminView(true, activeOrAll, userActivities, timeSheetStatus, userContextDetails);

				if (null != list) {
					adminList = list.get(0);
					projectAllocatedList = list.get(1);
				}

			} else {
				Resource resource = new Resource();
				resource.setEmployeeId(userContextDetails.getEmployeeId());
				List<Project> currResourceProjects = null;
				if (userContextDetails.isBehalfManager()) {
					List<ResourceAllocation> resourceAllocationList = resourceAllocationService.findResourceAllocationsByEmployeeId(resource);

					logger.info("resourceAllocationList returned by findResourceAllocationsByEmployeeId:-" + resourceAllocationList);

					currResourceProjects = new ArrayList<Project>();
					if (null != resourceAllocationList && !resourceAllocationList.isEmpty()) {
						for (ResourceAllocation resourceAllocation : resourceAllocationList) {

							if (activeOrAll.equalsIgnoreCase("active")) {
								if (resourceAllocation != null && resourceAllocation.getBehalfManager() != null
										&& (resourceAllocation.getAllocEndDate() == null || resourceAllocation.getAllocEndDate().compareTo(new Date()) >= 0)) {
									currResourceProjects.add(resourceAllocation.getProjectId());
								}
							}

							else if (activeOrAll.equalsIgnoreCase("all")) {
								if (resourceAllocation != null && resourceAllocation.getBehalfManager() != null && resourceAllocation.getBehalfManager()) {
									currResourceProjects.add(resourceAllocation.getProjectId());
								}
							}
						}
					}
				}

				if (null != currResourceProjects) {
					currResourceProjects.addAll(projectService.findProjectsByOffshoreDelMgr(userContextDetails.getEmployeeId(), activeOrAll));
				} else {
					currResourceProjects = projectService.findProjectsByOffshoreDelMgr(userContextDetails.getEmployeeId(), activeOrAll);
				}

				if (currResourceProjects != null && !currResourceProjects.isEmpty()) {
					for (Project project : currResourceProjects) {
						List<Integer> allocatedResourcesEmployeeId = resourceAllocationService.findAllocatedResourceEmployeeId(project.getId(), activeOrAll);

						logger.info("allocatedResourcesEmployeeId list returned by findAllocatedResourceEmployeeId:-" + allocatedResourcesEmployeeId);

						if (allocatedResourcesEmployeeId != null && !allocatedResourcesEmployeeId.isEmpty()) {
							for (Integer resAllocEmployeeId : allocatedResourcesEmployeeId) {
								if (resAllocEmployeeId != null && resAllocEmployeeId > 0 && !addedResourceList.contains(resAllocEmployeeId)) {
									addedResourceList.add(resAllocEmployeeId);
								}

							}
						}
					}
				}

				// #55 Redmine [Start]
				if (Constants.ROLE_DEL_MANAGER.equals(userContextDetails.getUserRole())) {
					List<Integer> empIdList = new ArrayList<Integer>();
					if (activeOrAll.equalsIgnoreCase("active"))
						empIdList = resourceService.findActiveResourceIdByRM2RM1(userContextDetails.getEmployeeId());
					else if (activeOrAll.equalsIgnoreCase("all"))
						empIdList = resourceService.findResourceIdByRM2RM1(userContextDetails.getEmployeeId());
					logger.info("empIdList returned by findResourceIdByRM2RM1:-" + empIdList);
					addedResourceList.addAll(empIdList);
					currResourceProjects = projectService.findProjectsByOffshoreDelMgr(userContextDetails.getEmployeeId(), activeOrAll);
					if (currResourceProjects != null && !currResourceProjects.isEmpty()) {
						for (Project project : currResourceProjects) {
							List<Integer> allocatedResourcesEmployeeId = resourceAllocationService.findAllocatedResourceEmployeeId(project.getId(), activeOrAll);

							logger.info("allocatedResourcesEmployeeId list returned by findAllocatedResourceEmployeeId:-" + allocatedResourcesEmployeeId);

							if (allocatedResourcesEmployeeId != null && !allocatedResourcesEmployeeId.isEmpty()) {
								for (Integer resAllocEmployeeId : allocatedResourcesEmployeeId) {
									if (resAllocEmployeeId != null && resAllocEmployeeId > 0 && !addedResourceList.contains(resAllocEmployeeId)) {
										resourceList.add(resAllocEmployeeId);
									}

								}
							}
						}
					}

					for (Integer empIdAR : addedResourceList) {
						if (userActivities.contains(empIdAR)) {
							resourceList.add(empIdAR);
						}else if(timeSheetStatus.compareTo('L')==0){ // if block added by vikas 
							if(!resourceList.contains(empIdAR)){
								resourceList.add(empIdAR);
							}
						} 
					}

				}

				if (Constants.ROLE_BG_ADMIN.equals(userContextDetails.getUserRole())) {
					if (activeOrAll.equalsIgnoreCase("active")) {
						addedResourceList = resourceService.findActiveReourceIdsByBusinessGroup(buList, null,activeOrAll,false);
						resourcesListOfOwnProject = resourceService.findActiveResourceIdsOfOwnProject(buList, userContextDetails.getEmployeeId());
						resourcesListOfOwnProject.addAll(addedResourceList);
						hs.addAll(resourcesListOfOwnProject);
						addedResourceList.clear();
						addedResourceList.addAll(hs);
					}

					else if (activeOrAll.equalsIgnoreCase("myproject")) {
						addedResourceList.clear();
						addedResourceList = resourceService.findReourceIdsOfOwnProject(userContextDetails.getEmployeeId());
					}

					else if (activeOrAll.equalsIgnoreCase("all")) {
						if ((timeSheetStatus.compareTo('L') == 0 || timeSheetStatus.compareTo('P') == 0)) {
							addedResourceList = resourceService.findReourceIdsByBusinessGroup(buList);
							resourcesListOfOwnProject = resourceService.findResourceIdsOfOwnProject(buList, userContextDetails.getEmployeeId());
							resourcesListOfOwnProject.addAll(addedResourceList);
							hs.addAll(resourcesListOfOwnProject);
							addedResourceList.clear();
							addedResourceList.addAll(hs);
						} else {
							addedResourceList.clear();
							addedResourceList = resourceService.findReourceIdsOfOwnProject(userContextDetails.getEmployeeId());
						}
					}

					for (Integer empIdAR : addedResourceList) {

						if (userActivities != null) {
							if (userActivities.contains(empIdAR)) {
								resourceList.add(empIdAR);
							}
						}
					}
				} // #55 Redmine [End]
				if (Constants.ROLE_MANAGER.equals(userContextDetails.getUserRole()) || Constants.ROLE_SEPG_USER.equals(userContextDetails.getUserRole())
						|| Constants.ROLE_BEHALF_MANAGER.equals(userContextDetails.getUserRole())) {
					for (Integer empIdAR : addedResourceList) {
						if (userActivities.contains(empIdAR)) {
							resourceList.add(empIdAR);
						}
					}
				}

				String resourceIdList = resourceList.toString();
				resourceIdList = resourceIdList.substring(1, (resourceIdList.length() - 1));
				List<List> list = timeHoursDao.managerView(resourceIdList, true);
				adminList = list.get(0);
				projectAllocatedList = list.get(1);

			}

			// common code for admin and manager
			for (org.yash.rms.domain.TimehrsView timehrsView : adminList) {
				allResources = populateResourceWithTimeHours(allResources, projectAllocatedList, timehrsView);
			}

			System.out.println("---------------logic ended-------------");
			sheetApprovalDTO.setTimehrsViews(allResources);
			sheetApprovalDTO.setResourceStatus(activeOrAll);
			sheetApprovalDTO.setCheckAllocForManager(checkAllocForManager);
			sheetApprovalDTO.setTimeSheetStatus(timeSheetStatus);

			// end of common code for admin and manager

		} catch (RuntimeException runtimeException) {
			logger.error("RuntimeException occured in list method of TimeHourEntry service:" + runtimeException);
			runtimeException.printStackTrace();
			throw runtimeException;
		} catch (Exception e) {
			logger.error("RuntimeException occured in list method of TimeHourEntry service:" + e);
			e.printStackTrace();
		}

		return sheetApprovalDTO;
	}

	
	/**
	 * this method returns list of UserActivityViewDTO for the supplied timeSheetStatus,weekEndDate and LoggedInUser.
	 * 
	 */
	public List<UserActivityViewDTO> getTimeHrsEntriesForResourceHyperForMobile(Character timeSheetStatus, String weekEndDate, Integer loggedInUserId) {
		
		List<UserActivityViewDTO> userActivityViewDTOs = new ArrayList<UserActivityViewDTO>();
		List<UserActivityViewDTO> activities = new ArrayList<UserActivityViewDTO>();
		
		TimeSheetApprovalDTO timeSheetApprovalDTO = getTimeSheetApprovalList(true, "all", 'M', loggedInUserId);
		
		List<TimehrsViewDTO> timehrsViewDTOs = timeSheetApprovalDTO.getTimehrsViews();
		
		
		for (TimehrsViewDTO timehrsViewDTO : timehrsViewDTOs) {
			
			//activities = getTimeHrsEntriesForResourceHyper(timehrsViewDTO.getEmployeeId(), timeSheetStatus, weekEndDate, loggedInUserId);
			activities  = mapper.convertUserActivityDomainToUserActivityDTOList(userActivityService.findUserActivitiesForOwnedProjectOfLoggedInUser(timehrsViewDTO.getEmployeeId(), timeSheetStatus, weekEndDate, loggedInUserId));
			if(activities != null) userActivityViewDTOs.addAll(activities);
			}
		
		return userActivityViewDTOs;
	}
	
	/**
	 * @param resourceId
	 * @param loggedInUserId
	 * @param projectId
	 * @param latestWeekEndDate
	 * @param allocationStatus
	 * @param employeeIdList
	 * @return List<UserActivity>
	 */
	private List<UserActivity> fetchUserActivityListForAllTimeSheetApproval(Integer resourceId, Integer loggedInUserId, Integer projectId, Date latestWeekEndDate, String allocationStatus, List<Integer> employeeIdList) {
		
		logger.info("Inside fetchUserActivityListForAllTimeSheetApproval for resourceId "+resourceId +" loggedInUserId "+loggedInUserId+" projectId "+projectId+" latestWeekEndDate "+latestWeekEndDate +" allocationStatus "+allocationStatus+" employeeIdList "+(employeeIdList!=null?employeeIdList.size():null));
		List<UserActivity> userActivity = null;
		
		if (projectId != null) {
			
			userActivity = userActivityService.findUserActivitiesByProjectId(projectId, 'P', allocationStatus, latestWeekEndDate);
			
		} else if(employeeIdList != null&& !employeeIdList.isEmpty()) {
			
			userActivity = userActivityService.findUserActivitysByEmployeeId('P', loggedInUserId,employeeIdList);
		} else {
			
			employeeIdList = new ArrayList<Integer>();
				employeeIdList.add(resourceId);
			
			userActivity = userActivityService.findUserActivitysByEmployeeId('P', loggedInUserId,employeeIdList);
		}
		return userActivity;
	}
	/**
	 * @param plannedHours
	 * @param billedHours
	 * @param weekendDates
	 * @param resourceAllocId
	 * @param remarks
	 * @param userName
	 * @param currentDate
	 * @param employeeIdList 
	 */
	private void createTimeHoursFromAllTimeSheetApproval(String plannedHours, String billedHours, String weekendDates, String resourceAllocId, String remarks, String userName,Date currentDate, List<Integer> employeeIdList, boolean fromRestService) {
		
		logger.info("Inside createTimeHoursFromAllTimeSheetApproval for resourceAllocId "+resourceAllocId +" plannedHours "+plannedHours+" billedHours "+billedHours+" weekendDates "+weekendDates +" resourceAllocId "+resourceAllocId+" remarks "+remarks+" userName "+userName+" currentDate "+currentDate +" employeeIdList "+(employeeIdList!=null?employeeIdList.size():null));
		
		Timehrs timehrs = null;
		String[] plannedHrs = plannedHours.split(",");
		String[] billedHrs = billedHours.split(",");
		String[] weekendDate = weekendDates.split(",");
		String[] resAllocId = resourceAllocId.split(",");
		String[] remark = remarks.split(",");

		for (int i = 0; i < plannedHrs.length; i++) {
			
			timehrs = new Timehrs();
				timehrs.setPlannedHrs(Double.parseDouble(plannedHrs[i]));
				timehrs.setBilledHrs(Double.parseDouble(billedHrs[i]));
				
			SimpleDateFormat weekEndDateParser = null;
			
			if (fromRestService) {
				weekEndDateParser = new SimpleDateFormat("yyyy-MM-dd");
			} else {
				weekEndDateParser = new SimpleDateFormat(Constants.DATE_PATTERN);
			}
			
			try {
				
				timehrs.setWeekEndingDate(weekEndDateParser.parse(weekendDate[i]));
				
			} catch (ParseException e) {
				
				logger.error("ParseException Inside createTimeHoursFromAllTimeSheetApproval Parse week end date "+weekendDate[i]);
				e.printStackTrace();
			}
			
			
			Resource resource = new Resource();
			//Change For Mobile Web to Provide Support Multiple User TimeSheet Approval
			if(employeeIdList.size() > i){
				
				resource.setEmployeeId(employeeIdList.get(i));				
			} else {
				
				resource.setEmployeeId(employeeIdList.get(0));
			}
			ResourceAllocation resourceAllocation = new ResourceAllocation();
				resourceAllocation.setId(Integer.parseInt(resAllocId[i]));
			
			timehrs.setResourceAllocId(resourceAllocation);
			timehrs.setResourceId(resource);
			
			if (remark.length > 0 && i < remark.length) {
				timehrs.setRemarks(remark[i]);
			}
			
			timehrs.setCreatedId(userName);
			timehrs.setCreationTimestamp(currentDate);
			timehrs.setLastUpdatedId(userName);
			timehrs.setLastupdatedTimestamp(currentDate);
			
			saveOrupdate(timehrs);
			
			timehrs = null;
			resource = null;
			resourceAllocation = null;
		}
	}

	/**
	 * @param userName
	 * @param listForApprovalMail
	 * @param status
	 * @throws Exception
	 */
	private void sendEmailOnTimeSheetApproval(String userName, List<UserActivity> listForApprovalMail, Character status) throws Exception {
		
		logger.info("Inside sendEmailOnTermSheetApproval for userName "+userName +" listForApprovalMail "+(listForApprovalMail!=null?listForApprovalMail.size():null) +" and Status "+status);
		
		if (!listForApprovalMail.isEmpty()) {
			
			for (UserActivity activity : listForApprovalMail) 
			{
				Map<String, Object> model = new HashMap<String, Object>();
				
				userActivityHelper.setEmailContentForTimeSheetApproval(model, activity.getEmployeeId(), activity.getWeekStartDate(), activity.getWeekEndDate(), status, activity
						.getResourceAllocId().getProjectId().getOffshoreDelMgr().getEmailId(), "", activity, userName);
				
				int confgId = Integer.parseInt(Constants.getProperty("Timesheet_Approval"));
				
				List<MailConfiguration> mailConfigurations = mailConfgService.findByProjectId(activity.getResourceAllocId().getProjectId().getId(), confgId);
				
				if (mailConfigurations != null && mailConfigurations.size() > 0) {
					emailHelper.sendEmail(model, mailConfigurations);
				}
			}
		}
	}

}
