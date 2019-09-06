package org.yash.rms.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yash.rms.dao.AllocationTypeDao;
import org.yash.rms.dao.DefaultProjectDao;
import org.yash.rms.dao.ProjectDao;
import org.yash.rms.dao.ResourceAllocationDao;
import org.yash.rms.dao.ResourceDao;
import org.yash.rms.domain.AllocationType;
import org.yash.rms.domain.Project;
import org.yash.rms.domain.Resource;
import org.yash.rms.domain.ResourceAllocation;
import org.yash.rms.domain.UserActivity;
import org.yash.rms.service.OrgHierarchyService;
import org.yash.rms.service.ResourceAllocationService;
import org.yash.rms.util.Constants;
import org.yash.rms.util.DateUtil;
import org.yash.rms.util.ResourceAllocationSearchCriteria;
import org.yash.rms.util.UserUtil;

@Service("ResourceAllocationService")
public class ResourceAllocationServiceImpl implements ResourceAllocationService {

	@Autowired
	@Qualifier("ResourceAllocationDao")
	ResourceAllocationDao resourceAllocationDao;
	
	//TODO : will be added after 9th August 2017 deployment.
	/*@Autowired
	@Qualifier("resourceReleaseSummaryDao")
	ResourceReleaseSummaryDao resourceReleaseSummaryDao;*/

	@Autowired
	@Qualifier("projectDaoImpl")
	ProjectDao projectDao;

	@Autowired
	ResourceDao resourceDao;
	
	@Autowired
	@Qualifier("allocationTypeDao")
	private AllocationTypeDao allocationTypeDao;
	
	@Autowired
	@Qualifier("defaultProjectDao")
	private DefaultProjectDao defaultProjectDao;
	
	@Autowired
	@Qualifier("OrgHierarchyService")
	private OrgHierarchyService orgHierarchyService ;
	
	private static final Logger logger = LoggerFactory.getLogger(ResourceAllocationServiceImpl.class);

	public boolean delete(int id) {
		logger.info("------ResourceAllocationServiceImpl  delete method start------");
		return resourceAllocationDao.delete(id);

	}

	public List<Integer> findAllocatedResourceEmployeeIdByProjectIds(List<Integer> projectIds, String active) {
		logger.info("------ResourceAllocationServiceImpl  findAllocatedResourceEmployeeIdByProjectIds method start------");
		return resourceAllocationDao.findAllocatedResourceEmployeeIdByProjectIds(projectIds, active);
	}

	public boolean saveOrupdate(ResourceAllocation resourceAllocation) {
		logger.info("------ResourceAllocationServiceImpl  saveOrupdate method start------");
		Project project = projectDao.findProject(resourceAllocation.getProjectId().getId());
		// defaultProjectList=projectDao.findAll();//

		resourceAllocation.setProjectId(project);
		// if primary Project flag is set yes then copy the projectId to
		// resource table as well.
		
		
		Resource resource = resourceDao.findByEmployeeId(resourceAllocation.getEmployeeId().getEmployeeId());
		if (resourceAllocation.getCurProj() != null) {
			if (resourceAllocation.getCurProj()) {
				resource.setCurrentProjectId(project);
				resourceDao.saveOrupdate(resource);
			}
		}
		
		if (resourceAllocation.getProjectId().getProjectEndDate() != null) {
			if (resourceAllocation.getAllocEndDate() == null || (resourceAllocation.getAllocEndDate().compareTo(resourceAllocation.getProjectId().getProjectEndDate()) > 0)) {
				resourceAllocation.setAllocEndDate(resourceAllocation.getProjectId().getProjectEndDate());
				return resourceAllocationDao.saveOrupdate(resourceAllocation);
			}
		}
		
		Boolean isUpdated = resourceAllocationDao.saveOrupdate(resourceAllocation);
		
//    Belowe code sets project id as null in resource table when there is no primary project is
//    allocated to resource in resource allocation table.	
    
		if (isUpdated) {
			int primaryProjectCount = this.findPrimaryProjectCount(resource.getEmployeeId());
			if (primaryProjectCount < 1) {
				resource.setCurrentProjectId(null);
				resourceDao.saveOrupdate(resource);
			}
		}
		
		return isUpdated;

	}

	public List<ResourceAllocation> findAll() {
		logger.info("------ResourceAllocationServiceImpl  findAll method start------");

		List<ResourceAllocation> resourceAllocations = new ArrayList<ResourceAllocation>();
		resourceAllocations = resourceAllocationDao.findAll();
		return resourceAllocations;

	}

	public List<ResourceAllocation> findByEntries(int firstResult, int sizeNo) {
		logger.info("------ResourceAllocationServiceImpl  findByEntries method start------");
		return resourceAllocationDao.findByEntries(firstResult, sizeNo);
	}

	public long countTotal() {
		logger.info("------ResourceAllocationServiceImpl countTotal method start------");
		return resourceAllocationDao.countTotal();
	}

	public List<ResourceAllocation> findResourceAllocationsByEmployeeId(Resource resource) {
		logger.info("------ResourceAllocationServiceImpl  findResourceAllocationsByEmployeeId method start------");
		return resourceAllocationDao.findResourceAllocationsByEmployeeId(resource);
	}

	public List<ResourceAllocation> findResourceAllocations(Resource resource) {
		logger.info("------ResourceAllocationServiceImpl  findResourceAllocations method start------");
		return resourceAllocationDao.findResourceAllocations(resource);
	}

	public ResourceAllocation findById(int id) {
		logger.info("------ResourceAllocationServiceImpl  findById method start------");
		return resourceAllocationDao.findById(id);
	}

	public List<ResourceAllocation> findResourceAllocationByEmployeeId(Integer employeeId) {
		logger.info("------ResourceAllocationServiceImpl  findResourceAllocationByEmployeeId method start------");
		return resourceAllocationDao.findResourceAllocationByEmployeeId(employeeId);

	}
	

	public List<String> findProjectNamesByEmployeeId(Integer employeeId) {
		logger.info("------ResourceAllocationServiceImpl  getAllResourceAllocation method start------");
		return resourceAllocationDao.findProjectNamesByEmployeeId(employeeId);

	}

	public List<ResourceAllocation> findResourceAllocationsByProjectId(Integer projectId) {
		logger.info("------ResourceAllocationServiceImpl  findResourceAllocationsByProjectId method start------");
		// projectId = projectDao.findProject(projectId.getId());
		// projectId = projectDao.findProject(projectId.getId());
		List<ResourceAllocation> allocations = resourceAllocationDao.findResourceAllocationsByProjectId(projectId);
		/*
		 * if(projectId.getProjectEndDate()!=null){ for(ResourceAllocation
		 * resourceAllocation: allocations){
		 * if(resourceAllocation.getAllocEndDate()==null){
		 * resourceAllocation.setAllocEndDate(projectId.getProjectEndDate());
		 * resourceAllocationDao.saveOrupdate(resourceAllocation); }else{
		 * if(resourceAllocation
		 * .getAllocEndDate().compareTo(projectId.getProjectEndDate())>0){
		 * resourceAllocation.setAllocEndDate(projectId.getProjectEndDate());
		 * resourceAllocationDao.saveOrupdate(resourceAllocation); } } } }
		 */
		return allocations;
	}

	public List<Object[]> findMinMaxUseractivity(Integer resAlloc) {
		logger.info("------ResourceAllocationServiceImpl  findMinMaxUseractivity method start------");
		return resourceAllocationDao.findMinMaxUseractivity(resAlloc);
	}

	public List<Object[]> findLastUseractivity(Integer resAlloc) {
		logger.info("------ResourceAllocationServiceImpl  findLastUseractivity method start------");
		return resourceAllocationDao.findLastUseractivity(resAlloc);
	}

	public List<Object[]> findFirstUseractivity(Integer resAlloc) {
		logger.info("------ResourceAllocationServiceImpl  findFirstUseractivity method start------");
		return resourceAllocationDao.findFirstUseractivity(resAlloc);
	}

	public ResourceAllocation findResourceAllocation(Integer id) {
		logger.info("------ResourceAllocationServiceImpl  findResourceAllocation method start------");
		return resourceAllocationDao.findResourceAllocation(id);
	}

	public ResourceAllocation findResourceAllocationsByEmployeeIdAndProjectId(Integer employeeId, Integer projectId) {
		logger.info("------ResourceAllocationServiceImpl  findResourceAllocationsByEmployeeIdAndProjectId method start------");
		/*
		 * Resource resource=new Resource(); Project project=new Project();
		 * project.setId(projectId); resource.setEmployeeId(employeeId);
		 */
		return resourceAllocationDao.findResourceAllocationsByEmployeeIdAndProjectId(employeeId, projectId);
	}

	public List<Integer> findAllocatedResourceEmployeeId(Integer projectId, String active) {
		logger.info("------ResourceAllocationServiceImpl  findAllocatedResourceEmployeeId method start------");
		return resourceAllocationDao.findAllocatedResourceEmployeeId(projectId, active);
	}
	
	//TODO : Dead code.
	/*public ResourceAllocation findResourceAllocationsByEmployeeIdAndProjectId(Resource employeeId, Project projectId) {
		logger.info("------ResourceAllocationServiceImpl  findResourceAllocationsByEmployeeIdAndProjectId method start------");
		return resourceAllocationDao.findResourceAllocationsByEmployeeIdAndProjectId(employeeId, projectId);

	}*/

	public List<Integer> findProjectIdsByEmployeeIdAndIsBehalfManager(Integer employeeId) {
		return resourceAllocationDao.findProjectIdsByEmployeeIdAndIsBehalfManager(employeeId);
	}

	public List<ResourceAllocation> findResourceAllocationsforManager(Resource resource, Resource manager, Date weekEndDate) {
		return resourceAllocationDao.findResourceAllocationsforManager(resource, manager, weekEndDate);

	}

	public List<ResourceAllocation> findResourceAllocationsByEmployeeIdforTimeHours(Resource resource, Date weekEndDate) {
		return resourceAllocationDao.findResourceAllocationsByEmployeeIdforTimeHours(resource, weekEndDate);
	}

	public List<ResourceAllocation> findResourceAllocationByActiveTypeEmployeeId(Resource resource) {
		logger.info("------ResourceAllocationServiceImpl  findResourceAllocationByActiveTypeEmployeeId method start------");
		return resourceAllocationDao.findResourceAllocationByActiveTypeEmployeeId(resource);
	}

	public List<ResourceAllocation> findActiveResourceAllocationsByProjectId(Integer projectId) {
		logger.info("------ResourceAllocationServiceImpl  findActiveResourceAllocationsByProjectId method start------");
		List<ResourceAllocation> allocations = resourceAllocationDao.findActiveResourceAllocationsByProjectId(projectId);

		return allocations;
	}

	public List<Integer> findAllocatedResourceEmployeeIdByProjectIdsPagination(int firstResult, int maxResults, List projectIds, String active,
			ResourceAllocationSearchCriteria resourceAllocationSearchCriteria, boolean search, Integer employeeId) {
		return resourceAllocationDao.findAllocatedResourceEmployeeIdByProjectIdsPagination(firstResult, maxResults, projectIds, active, resourceAllocationSearchCriteria, search, employeeId);
	}

	/*
	 * public List findMinMaxUserActivity(int resAllocId) {
	 * 
	 * List minMaxUserActivity =
	 * resourceAllocationDao.findMinMaxUserActivity(resAllocId); Map<Integer,
	 * List<Object>> minMaxUserActivities=new HashMap<Integer, List<Object>>();
	 * return minMaxUserActivity; }
	 */

	public long countAllocatedResourceEmployeeIdByProjectIdsPagination(List projectIds, String active, ResourceAllocationSearchCriteria resourceAllocationSearchCriteria, boolean search,
			Integer employeeId) {
		return resourceAllocationDao.countAllocatedResourceEmployeeIdByProjectIdsPagination(projectIds, active, resourceAllocationSearchCriteria, search, employeeId);
	}

	public List<ResourceAllocation> getAllocations(Integer projId) {

		return resourceAllocationDao.findActiveResourceAllocationsByProjectId(projId);
	}

	public List<Integer> findAllocatedResourceEmployeeIdByProjectIdsDashboard(List projectIds, Integer employeeId) {
		// TODO Auto-generated method stub
		return resourceAllocationDao.findAllocatedResourceEmployeeIdByProjectIdsDashboard(projectIds, employeeId);
	}

	// US3090(Added by Maya): START: Future timesheet delete functionality
	public List<UserActivity> isFutureTimesheetpresent(Integer resourceAllocId, Date allocWeekEndDate) {
		// TODO Auto-generated method stub
		return resourceAllocationDao.isFutureTimesheetpresent(resourceAllocId, allocWeekEndDate);
	}

	public boolean deleteFutureTimesheet(List<Integer> userActivityIdlist, String resourceAllocId, String weekEndDate) {

		return resourceAllocationDao.deleteFutureTimesheet(userActivityIdlist, resourceAllocId, weekEndDate);
	}

	public boolean checkIfAllocationIsOpen(Integer resourceAllocId, Date allocWeekEndDate) {
		
		return resourceAllocationDao.checkIfAllocationIsOpen(resourceAllocId, allocWeekEndDate);
	}

	public int findPrimaryProjectCount(int employeeId) {
		return resourceAllocationDao.findPrimaryProjectCount(employeeId);
	}
	
	public ResourceAllocation findPrimaryProject(Integer employeeId) {
		return resourceAllocationDao.findPrimaryProject(employeeId);
	}

	//TODO : will be added after 9th August 2017 deployment.
	/*public boolean saveResourceDetailsOfReleaseSummary(ResourcesReleaseSummary resourcesReleaseSummary) {		
		
		return resourceReleaseSummaryDao.saveOrupdate(resourcesReleaseSummary);
	}*/
	
	
	 /**
	  *  This is a task scheduler method which runs daily on a time specified in task scheduling.xml file.
	  *  The implementation of this method states that Any resource blocked for Project/POC or any other
	  *  reason would be in same status for 30 days only with proper business justification. If their blocked
	  *  status exceeds more than 30 day in same project then next day resource would have bench project.
	  * 
	  * */	
	  
		@Transactional
		public void setDefaultProjectforBlockedResource() {
			logger.info("Inside @SetDefaultProjectforBlockedResource method Start....... ");
			
			Boolean isTraineeProject = false;
			int days = Integer.parseInt(StringUtils.trimToEmpty(Constants.BLOCKEDRESOURCE_DAYS));
			
			String BGBUUnit = StringUtils.trimToEmpty(Constants.BGBU_NONSEP_BENCHPROJECT);
			if(BGBUUnit.isEmpty())
				return;
			
			List<Integer> orgIds = new ArrayList<Integer>();
			try{
				String [] BGBUUnitArray = StringUtils.split(BGBUUnit, ",");
			
				if(BGBUUnitArray.length > 0) {
					for(int index = 0; index < BGBUUnitArray.length;index++){
					 String [] BGBU = StringUtils.split(BGBUUnitArray[index], "-");
					 orgIds.add(orgHierarchyService.findBUIdByBGBUName(BGBU[0], BGBU[1]));
					}
				}
			}
			catch (ArrayIndexOutOfBoundsException ex) {
				logger.error("Exceptio occured -------" + ex.getMessage());
				ex.printStackTrace();
				return;
			}
			
			Date now = new Date();

			List<ResourceAllocation> resourceAllocations = resourceAllocationDao.getAllocationBlockedResourceWithThirtyDaysMore(orgIds,isTraineeProject, days,now);
			logger.info("Total Blocked Resource  ----------------------- :" +resourceAllocations.size());
			if(resourceAllocations != null && !resourceAllocations.isEmpty()){
				Iterator<ResourceAllocation> iterator = resourceAllocations.iterator();
				while (iterator.hasNext()) {
					
					ResourceAllocation resourceAllocation = (ResourceAllocation) iterator.next();
					
					
					resourceAllocation.setAllocEndDate(now);
					resourceAllocation.setLastupdatedTimestamp(now);
					resourceAllocation.setCurProj(false);
					
					try{
						Boolean isUpdated = resourceAllocationDao.saveOrupdate(resourceAllocation);
						if(isUpdated){
							Resource resource  = resourceAllocation.getEmployeeId();
							assignDefalutProjectToBenchResource(resource);
						}
					}
					catch(Exception ex) {
					 logger.error("Exceptio occured -------" + ex.getMessage());
					 ex.printStackTrace();
					}
				}
			}
			logger.info("Inside @SetDefaultProjectforBlockedResource method End....... ");
		}
		
		private void assignDefalutProjectToBenchResource(Resource resource) {
			logger.info("Inside @AssignDefalutProjectToBenchResource method Start....... ");
			ResourceAllocation newResourceAllocation = new ResourceAllocation();
			try {
				/*DefaultProject defaultProject = defaultProjectDao.getDefaultProjectbyProjectForBU(resource.getCurrentBuId());
				if (defaultProject != null) {
					newResourceAllocation.setProjectId(defaultProject.getProjectId());
					newResourceAllocation.setAllocationTypeId(defaultProject.getAllocationTypeId());
				} else {
					Project defaultNonSapProject = projectDao.findProjectByProjectName(Constants.DEFAULNONSAPPROJECT);
					AllocationType allocationType = allocationTypeDao.getAllocationTypeByType(Constants.NONBILLABLE_UNALLOCATED);
					if (defaultNonSapProject != null && allocationType != null) {
						newResourceAllocation.setProjectId(defaultNonSapProject);
						newResourceAllocation.setAllocationTypeId(allocationType);
					} else {
						logger.error(Constants.NONBILLABLE_UNALLOCATED + " not found or "+Constants.DEFAULNONSAPPROJECT+ "not found");
						return;
					}
				}*/
				
				
			Project defaultNonSapProject = projectDao.findProjectByProjectName(Constants.DEFAULNONSAPPROJECT);
			AllocationType allocationType = allocationTypeDao.getAllocationTypeByType(Constants.NONBILLABLE_UNALLOCATED);
			if (defaultNonSapProject != null && allocationType != null) {
				newResourceAllocation.setProjectId(defaultNonSapProject);
				newResourceAllocation.setAllocationTypeId(allocationType);
			} else {
				logger.error(Constants.NONBILLABLE_UNALLOCATED + " not found or "+Constants.DEFAULNONSAPPROJECT+"not found");
				return;
			}
		} catch (Exception ex) {
			logger.error("Exceptio occured -------" + ex.getMessage());
			ex.printStackTrace();
			return;
		}
			 
		Date currentDate = new Date();
		final Calendar calendar = Calendar.getInstance();
		calendar.setTime(currentDate);
		calendar.add(Calendar.DAY_OF_YEAR, 1);
		Date nexteDay = calendar.getTime();	
		
		try {
			resource.setCurrentProjectId(newResourceAllocation.getProjectId());
			boolean isResoucePrjoectUpdated = resourceDao.saveOrupdate(resource);
			if (isResoucePrjoectUpdated) {
				newResourceAllocation.setEmployeeId(resource);
				newResourceAllocation.setAllocStartDate(nexteDay);
				Resource currentResource = UserUtil.userContextDetailsToResource(UserUtil.getCurrentResource());
				// if currentResource found null then it means its system
				// updated.
				newResourceAllocation.setAllocatedBy(currentResource);
				newResourceAllocation.setUpdatedBy(currentResource);
				newResourceAllocation.setDesignation(resource.getDesignationId());
				newResourceAllocation.setGrade(resource.getGradeId());
				newResourceAllocation.setBuId(resource.getBuId());
				newResourceAllocation.setCurrentBuId(resource.getCurrentBuId());
				newResourceAllocation.setLocation(resource.getLocationId());
				newResourceAllocation.setCurrentReportingManager(resource.getCurrentReportingManager());
				newResourceAllocation.setCurrentReportingManagerTwo(resource.getCurrentReportingManagerTwo());
				newResourceAllocation.setOwnershipTransferDate(resource.getTransferDate());
				newResourceAllocation.setCurProj(true);
				newResourceAllocation.setLastupdatedTimestamp(currentDate);
				newResourceAllocation.setProjectEndRemarks("NA");
				newResourceAllocation.setResourceType(Constants.INTERNAL_USER);
				newResourceAllocation.setAllocRemarks(Constants.ALLOCATIONREMARKS_FOR_BENCH_RESOURCE);
				newResourceAllocation.setAllocPercentage(0);
				boolean isSuccess = resourceAllocationDao.saveOrupdate(newResourceAllocation);
				if (isSuccess)
					updateAllocationSeqForResourceAllocations(resource);
			} else {
				logger.info("Resource update failed , could not update project for resource " + resource.getEmailId());
				throw new RuntimeException(
						"Resource update failed , could not update project for resource " + resource.getEmailId());
			}
		} catch (Exception ex) {
			logger.error("Exceptio occured -------" + ex.getMessage());
			ex.printStackTrace();
			throw new RuntimeException(ex.getMessage());
		}
		logger.info("Inside @AssignDefalutProjectToBenchResource method End....... ");
		}
		
		public void updateAllocationSeqForResourceAllocations(Resource resource) throws Exception {
			logger.info("------ResourceAllocationServiceImpl updateAllocationSeqForResourceAllocations method start------");
			try {
				List<ResourceAllocation> resourceAllocation = resourceAllocationDao.findResourceAllocationsByEmployeeId(resource);
				if (resourceAllocation != null && !resourceAllocation.isEmpty()) {
					Collections.sort(resourceAllocation, new org.yash.rms.domain.ResourceAllocation.ResourceAllocationTimeComparator());
					for (int i = 1; i <= resourceAllocation.size(); i++) {
						ResourceAllocation raObj = resourceAllocation.get(i - 1);
						raObj.setAllocationSeq(i);
						resourceAllocationDao.saveOrupdate(raObj);
					}
				}
			} catch (RuntimeException e) {
				logger.error("RuntimeException occured in UpdateAllocationSeqForResourceAllocations method of Resource allocation controller:" + e);
				throw e;
			} catch (Exception exception) {
				logger.error("RuntimeException occured in UpdateAllocationSeqForResourceAllocations method of Resource allocation controller:" + exception);
				throw exception;
			}
			logger.info("------ResourceAllocationController UpdateAllocationSeqForResourceAllocations method end------");
		}

				
		public void closeResourceAllocationInPoolProject(Integer newAllocationId, Date allocStartDate, int employeeId, boolean isDeleteTimeSheet) throws Exception {

			logger.info("------ResourceAllocationServiceImpl @CloseResourceAllocationInPoolProject method Start------");
			String engagementModelName = "pool" ;
			
			try {
				Date allocEndDate = DateUtil.getNextOrBackDate(allocStartDate, -1);
				
				List<ResourceAllocation> activeAllocationInPoolProject  = resourceAllocationDao.findActiveResourceAllcoation_ByProjectEngagementModelName(employeeId, engagementModelName);
				logger.info("Allocation Opened in Pool Project is : " + activeAllocationInPoolProject.size() + " for Employee id :" + employeeId);
				
				if(activeAllocationInPoolProject != null && !activeAllocationInPoolProject.isEmpty()) {
					for(Iterator<ResourceAllocation> iterator = activeAllocationInPoolProject.iterator(); iterator.hasNext();) {
						
						ResourceAllocation currentAllocation = (ResourceAllocation) iterator.next();
						
						if(newAllocationId == currentAllocation.getId()) 
							continue ;
						
						int isPriDate = allocEndDate.compareTo(currentAllocation.getAllocStartDate());
						
						if(isPriDate <= 0) 
							currentAllocation.setAllocEndDate(currentAllocation.getAllocStartDate());
						else 
							currentAllocation.setAllocEndDate(allocEndDate);
						
						currentAllocation.setCurProj(false);
						currentAllocation.setUpdatedBy(UserUtil.userContextDetailsToResource(UserUtil.getCurrentResource()));
						currentAllocation.setLastupdatedTimestamp(new Date());

						boolean isSuccess = resourceAllocationDao.saveOrupdate(currentAllocation);
						
						if(isSuccess) {
							if(isDeleteTimeSheet) {
								List<UserActivity> userActivityList = resourceAllocationDao.isFutureTimesheetpresent(currentAllocation.getId(), currentAllocation.getAllocEndDate());
								if (userActivityList != null && !userActivityList.isEmpty()) {
									List<Integer> userActivityIdList = new ArrayList<Integer>();
									for(Iterator<UserActivity> activityIterator = userActivityList.iterator(); activityIterator.hasNext();) {
										UserActivity userActivity = (UserActivity) activityIterator.next();
										userActivityIdList.add(userActivity.getId());
									}
									
									String currentAllocEnddate = Constants.DATE_FORMAT_NEW.format(currentAllocation.getAllocEndDate());
									resourceAllocationDao.deleteFutureTimesheet(userActivityIdList, String.valueOf(currentAllocation.getId()), currentAllocEnddate);
								}	
							}
						}
					}
				}
			}
		catch (RuntimeException e) {
			logger.error("RuntimeException occured in @CloseResourceAllocationInPoolProject method of Resource allocation Service:"+ e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception exception) {
			logger.error("Exception occured in @CloseResourceAllocationInPoolProject method of Resource allocation Service:"+ exception.getMessage());
			exception.printStackTrace();
			throw exception;
		}
		logger.info("------ResourceAllocationServiceImpl @CloseResourceAllocationInPoolProject method End------");
		}
}
