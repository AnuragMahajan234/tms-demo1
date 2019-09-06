package org.yash.rms.service;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.yash.rms.domain.Project;
import org.yash.rms.domain.Resource;
import org.yash.rms.domain.ResourceAllocation;
import org.yash.rms.domain.TimehrsView;
import org.yash.rms.domain.UserActivity;
import org.yash.rms.dto.UserContextDetails;
import org.yash.rms.util.ResourceAllocationSearchCriteria;

public interface ResourceAllocationService extends RmsCRUDService<ResourceAllocation> {
	
	public void setDefaultProjectforBlockedResource();

	public List<ResourceAllocation> findResourceAllocationsByEmployeeId(Resource resource);

	public List<ResourceAllocation> findResourceAllocationByActiveTypeEmployeeId(Resource resource);

	public List<Integer> findAllocatedResourceEmployeeIdByProjectIds(List<Integer> projectIds, String active);

	public List<ResourceAllocation> findResourceAllocationsByProjectId(Integer projectId);

	public List<ResourceAllocation> findActiveResourceAllocationsByProjectId(Integer projectId);

	public List<ResourceAllocation> findResourceAllocationByEmployeeId(Integer employeeId);
	
	public List<Object[]> findLastUseractivity(Integer resourceAllocationId);

	public List<Object[]> findFirstUseractivity(Integer resourceAllocationId);

	public ResourceAllocation findResourceAllocation(Integer resourceAllocationId);

	public ResourceAllocation findResourceAllocationsByEmployeeIdAndProjectId(Integer employeeId, Integer projectId);

	public List<Integer> findAllocatedResourceEmployeeId(Integer projectId, String active);

	public ResourceAllocation findById(int id);

	public List<Integer> findProjectIdsByEmployeeIdAndIsBehalfManager(Integer employeeId);

	public List<Object[]> findMinMaxUseractivity(Integer resAlloc);

	public List<ResourceAllocation> findResourceAllocationsforManager(Resource resource, Resource manager, Date weekEndDate);

	public List<ResourceAllocation> findResourceAllocationsByEmployeeIdforTimeHours(Resource resource, Date weekEndDate);

	public List findAllocatedResourceEmployeeIdByProjectIdsPagination(int firstResult, int maxResults, List projectIds, String active, ResourceAllocationSearchCriteria resourceAllocationSearchCriteria, boolean search, Integer employeeId);

	public long countAllocatedResourceEmployeeIdByProjectIdsPagination(List projectIds, String active, ResourceAllocationSearchCriteria resourceAllocationSearchCriteria, boolean search, Integer employeeId);

	public List<ResourceAllocation> findResourceAllocations(Resource loggedInResource);

	public List<ResourceAllocation> getAllocations(Integer projId);

	public List<Integer> findAllocatedResourceEmployeeIdByProjectIdsDashboard(List projectIds, Integer employeeId);

	public List<UserActivity> isFutureTimesheetpresent(Integer resourceAllocId, Date allocWeekEndDate);

	public boolean deleteFutureTimesheet(List<Integer> userActivityIdlist, String resourceAllocId, String weekEndDate);

	public boolean checkIfAllocationIsOpen(Integer resourceAllocId, Date allocWeekEndDate);
	
	 public int findPrimaryProjectCount(final int employeeId);
	 
	 public ResourceAllocation findPrimaryProject(Integer employeeId);
	 
	 public List<String> findProjectNamesByEmployeeId(Integer employeeId);
	 
	 public void closeResourceAllocationInPoolProject(Integer newAllocationId, Date allocStartDate, int employeeId, boolean isDeleteTimeSheet) throws Exception ; 
	 
	//TODO : will be added after 9th August 2017 deployment.
	//public boolean saveResourceDetailsOfReleaseSummary(ResourcesReleaseSummary resourcesReleaseSummary);
}
