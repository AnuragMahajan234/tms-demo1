/**
 * 
 */
package org.yash.rms.dao;

import java.util.Date;
import java.util.List;

import org.yash.rms.domain.Project;
import org.yash.rms.domain.Resource;
import org.yash.rms.domain.ResourceAllocation;
import org.yash.rms.domain.UserActivity;
import org.yash.rms.dto.ResourceOnBenchDto;
import org.yash.rms.util.ResourceAllocationSearchCriteria;
import org.yash.rms.util.ResourceOnBenchSearchCriteria;


/**
 * @author arpan.badjatiya
 *
 */
public interface ResourceOnBenchDao extends RmsCRUDDAO<ResourceAllocation> {
List<ResourceAllocation> findResourceAllocationByActiveTypeEmployeeId(Resource resource, java.sql.Date allocationStartDate);
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
	public List<Object[]> resourceAllocationPagination(int firstResult,int maxResults,ResourceOnBenchSearchCriteria searchCriteria,	String activeOrAll, boolean search);
	public Integer getBenchResourchCount(Integer firstResult, Integer maxResults,ResourceOnBenchSearchCriteria searchCriteria, String activeOrAll,	boolean search);
	public List<ResourceAllocation> findResourceAllocationByActiveTypeEmployeeId(Resource resource);
}
