package org.yash.rms.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.yash.rms.domain.OrgHierarchy;
import org.yash.rms.domain.Project;
import org.yash.rms.domain.Timehrs;
import org.yash.rms.domain.TimehrsView;
import org.yash.rms.domain.UserActivity;
import org.yash.rms.dto.TimeSheetApprovalDTO;
import org.yash.rms.dto.TimehrsViewDTO;
import org.yash.rms.dto.UserActivityViewDTO;
import org.yash.rms.dto.UserContextDetails;
import org.yash.rms.util.ResourceAllocationSearchCriteria;
import org.yash.rms.util.SearchCriteriaGeneric;

@Service("TimeHoursService")
public interface TimeHoursService extends RmsCRUDService<TimehrsViewDTO>{

	public TimeSheetApprovalDTO getTimeSheetApprovalList(boolean isrequireCurrentProject, String activeOrAll, Character timeSheetStatus, Integer employeeId); 
	
	public List<UserActivityViewDTO> getTimeHrsEntriesForResource(Integer employeeId, Character timeSheetStatus, String resourceStatus, Integer loggedInUserId, Integer projectId, Date weekEndDate);
	
	public UserActivity mapToUserActivity(Timehrs timehrs);
	
	public boolean saveOrupdate(Timehrs timeHrs);
 
	public List<TimehrsView> resourceAllocationPagination(int firstResult, int maxResults, ResourceAllocationSearchCriteria resourceAllocationSearchCriteria, String activeOrAll, boolean serach);
	
	public List<TimehrsView> managerViewPagination(List<Integer> resourceIdList,int firstResult, int maxResults, ResourceAllocationSearchCriteria resourceAllocationSearchCriteria, boolean search );
	
	public long countSearch(ResourceAllocationSearchCriteria resourceAllocationSearchCriteria, String activeOrAll, List<OrgHierarchy> businessGroup, List<Integer> projectIdList ,Integer resourceId, boolean manager, boolean search);
	
	public Timehrs findByResAllocId(int resourceAllocationId, Date date);
	
	public List<List> managerViewForDelManager(List<Integer> resourceIdList, boolean requireCurrentProject, List<Project> projectList);

	public List<UserActivityViewDTO> getTimeHrsEntriesForResourceHyper(Integer resourceId, Character timeSheetStatus, String weekEndDate, Integer loggedInUserId);
	
	public List<UserActivityViewDTO> getTimeHrsEntriesForResourceHyperForMobile( Character timeSheetStatus, String weekEndDate, Integer loggedInUserId);

	public void approveAll(Integer resourceId, Integer loggedInUserId, String plannedHours, String billedHours, String weekendDates, String resourceAllocId, String remarks, Integer projectId, Date latestWeekEndDate, String allocationStatus, List<Integer> employeeIdList,List<UserActivity> userActivitysList, boolean fromRestService)  throws Exception;
	
	public Map<String, Object> getTimeSheetApprovalListWithPagination(boolean requireCurrentProject, String activeOrAll, Character timeSheetStatus, Integer employeeId,  HttpServletRequest request);

	
}
