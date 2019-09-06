/**
 * 
 */
package org.yash.rms.dao;

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


/**
 * @author arpan.badjatiya
 *
 */
public interface ResourceAllocationDao extends RmsCRUDDAO<ResourceAllocation> {
List<ResourceAllocation> findResourceAllocationByActiveTypeEmployeeId(Resource resource);
	List<ResourceAllocation> findResourceAllocationsByProjectId(Integer projectId);
List<ResourceAllocation> findActiveResourceAllocationsByProjectId(Integer projectId);

	 List<Object[]> findLastUseractivity(Integer resAlloc);
	
	 List<Object[]> findFirstUseractivity(Integer resAlloc);
	 
	 List<ResourceAllocation> findResourceAllocationByEmployeeId(Integer employeeId);
	
	 ResourceAllocation findResourceAllocation(Integer id);
	
	 ResourceAllocation findResourceAllocationsByEmployeeIdAndProjectId(
			Integer employeeId, Integer projectId) ;

	 List<ResourceAllocation> findResourceAllocationsByEmployeeId(Resource resource);

	 public List<Integer> findAllocatedResourceEmployeeIdByProjectIds(List<Integer> projectIds,String active);
	

	 public ResourceAllocation findResourceAllocationsByEmployeeIdAndProjectId(Resource employeeId, Project projectId);
	 
 
	 public List<Integer> findAllocatedResourceEmployeeId(Integer projectId,String active);
	 
	 ResourceAllocation findById(int id);
	 
	public List<Integer> findProjectIdsByEmployeeIdAndIsBehalfManager(Integer employeeId);
	
	public boolean isResourceBehalfManager(int employeeId);
	
	public List<Object[]> findMinMaxUseractivity(Integer resAlloc);
	
	public List<ResourceAllocation> findResourceAllocationsforManager(Resource resource,Resource manager,Date weekEndDate);
	
	public List<ResourceAllocation> findResourceAllocationsByEmployeeIdforTimeHours(
			Resource resource,Date weekEndDate);
	
	
	public List<Integer> findAllocatedResourceEmployeeIdByProjectIdsPagination(int firstResult, int maxResults,List projectIds,String active,ResourceAllocationSearchCriteria resourceAllocationSearchCriteria,boolean search,Integer employeeId);
	public long countAllocatedResourceEmployeeIdByProjectIdsPagination(List projectIds,String active,ResourceAllocationSearchCriteria resourceAllocationSearchCriteria,boolean search,Integer employeeId) ;
	List<ResourceAllocation> findResourceAllocations(Resource resource);
	
	public List<Integer> findAllocatedResourceEmployeeIdByProjectIdsDashboard(List projectIds,Integer employeeId);
	
	public List<UserActivity> isFutureTimesheetpresent(Integer resourceAllocId , Date allocWeekEndDate);
	public boolean deleteFutureTimesheet(List<Integer> userActivityIdlist,String resourceAllocId, String weekEndDate);
	public boolean checkIfAllocationIsOpen(Integer resourceAllocId,
			Date allocWeekEndDate);
	
	 int findPrimaryProjectCount(int employeeId);
	 public ResourceAllocation findPrimaryProject(Integer employeeId);
		public List<Integer> findAllocatedActiveProjectByEmployeeId(List employeeId,Boolean active);
		public List findAllocatedEmployeeInProjectByCurrentUser(Integer employeeId,Boolean active);
		
	public List<ResourceAllocation> getAllocationBlockedResourceWithThirtyDaysMore(List<Integer> orgIds ,Boolean isTraineeProject,int days,Date lastDateToCompare);	
	
	public List<String> findProjectNamesByEmployeeId(Integer employeeId);
	
	
	public List<ResourceAllocation> findActiveResourceAllcoation_ByProjectEngagementModelName(int employeeId, String engagementModelName);
}
