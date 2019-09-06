package org.yash.rms.dao;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestParam;
import org.yash.rms.domain.OrgHierarchy;
import org.yash.rms.domain.Project;
import org.yash.rms.domain.Resource;
import org.yash.rms.domain.Timehrs;
import org.yash.rms.domain.TimehrsView;
import org.yash.rms.domain.UserActivity;
import org.yash.rms.dto.UserContextDetails;
import org.yash.rms.util.ResourceAllocationSearchCriteria;
import org.yash.rms.util.SearchCriteriaGeneric;


public interface TimeHoursDAO extends RmsCRUDDAO<Timehrs>{
	public List<TimehrsView> findTimehrsEntries(int firstResult, int maxResults);
	
//	public List<List> adminView(String activeOrAll);
	
	public List<List> adminView(boolean requireCurrentProject,String activeOrAll,List<Integer> empIds, Character timeSheetStatus,UserContextDetails userContextDetails);
	
//	public List<List> managerView(String resourceIdList);
	
	public List<List> managerView(String resourceIdList, boolean requireCurrentProject);
	
	/**
	 * To see performance improvement
	 * @param project
	 * @return
	 */
	public List<Timehrs> findTimeHrsForResources(Integer resourceId);
	
	public UserActivity mapToUserActivity(Timehrs th);
	
	public List resourceAllocationPagination(int firstResult, int maxResults,
			ResourceAllocationSearchCriteria resourceAllocationSearchCriteria ,String activeOrAll , boolean search);
	
	public List managerViewPagination(List<Integer> resourceIdList,
			int firstResult, int maxResults,
			ResourceAllocationSearchCriteria resourceAllocationSearchCriteria , boolean search);
	
	public long countSearch(ResourceAllocationSearchCriteria resourceAllocationSearchCriteria ,String activeOrAll , List<OrgHierarchy> resourceIds , List<Integer> projectIdList ,Integer employeeId ,  boolean manager , boolean search);
	
	public Timehrs findByResAllocId(int timehrsId, Date weekEndDate);
	
	public List<List> managerViewForDelManager(List<Integer>  resourceIdList, boolean requireCurrentProject,List<Project> projectNameList);
	
	public List<TimehrsView> getAllTimehrsViewList(String activeOrAll, Character timeSheetStatus, List<Integer> empIds, List<String> projectNames, UserContextDetails userContextDetails , HttpServletRequest request, SearchCriteriaGeneric SearchCriteriaGeneric);
	 
	public long totalCount(String activeOrAll, Character timeSheetStatus, List<Integer> empIds,  List<String> projectNames, UserContextDetails userContextDetails, SearchCriteriaGeneric SearchCriteriaGeneric);
	
	public List<TimehrsView> managerViewWithPagination(List<Integer> resourceIdList, boolean requireCurrentProject, HttpServletRequest request, SearchCriteriaGeneric searchCriteriaGeneric);
	
	public Long totalCount(List<Integer> empsId, boolean requireCurrentProject, SearchCriteriaGeneric searchCriteriaGeneric);
}
