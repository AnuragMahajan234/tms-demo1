package org.yash.rms.dao;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.yash.rms.domain.OrgHierarchy;
import org.yash.rms.domain.Project;
import org.yash.rms.domain.Resource;
import org.yash.rms.domain.ResourceAllocation;
import org.yash.rms.util.ProjectAllocationSearchCriteria;
import org.yash.rms.util.ResourceAllocationSearchCriteria;

public interface ProjectAllocationDao extends RmsCRUDDAO<Project> {

	List<Project> findProjectsByProjectNameEquals(String projectName);

	List<Project> findActiveProjectsByProjectNameEquals(String projectName,String sSearch, ProjectAllocationSearchCriteria projectAllocationSearchCriteria,String activeOrAll);

	List<Project> managerView(Resource currentLoggedInUserId,String activeOrAll);

	List<Project> findAllActiveProjects(Integer page,Integer size, ProjectAllocationSearchCriteria searchCriteria, String activeOrAll, String sSearch,
			List<OrgHierarchy> businessGroup, Integer currentLoggedInUserId);
	
	public List<Integer> getProjectIdsForManager(Integer currentLoggedInUserId,String activeOrAll);
	
	public Long countActive(String activeOrAll, boolean isCurrentUserAdmin, List<OrgHierarchy> businessGroup,Integer currentLoggedInUserId);
	
	public long countSearch(ProjectAllocationSearchCriteria projectAllocationSearchCriteria ,String activeOrAll ,String search,List<OrgHierarchy> businessGroup,
			Integer currentLoggedInUserId);
	
	public List<Project> findCountProjectName(List<Integer> empIdList, Integer page, Integer size,String activeOrAll,List<String> projectNameList,Integer currentLoggedInUserId);
	
	public List<Project> findAllActiveProjectsByManager( Integer currentLoggedInUserId);
	
	public List<String> getProjectNameForManager(String activeOrAll , Integer currentLoggedInUserId);
	
	public Set<Project> getProjectNameForManagerOnProject(String activeOrAll , Integer currentLoggedInUserId);
	
	public List<String> findCountProjectNameForDashboardBillingStatus(List<Integer> empIdList,String activeOrAll,List<String> projectId);
	
	public List<String> findAllActiveProjectsForBGAdminDashboard(List<OrgHierarchy> businessGroup, Integer currentLoggedInUserId);
	
	public List<Integer> getProjectIdsForBGAdmin(List<OrgHierarchy> businessGroup);
	
	// US3091/US3092: STAR
	public List<ResourceAllocation> findOpenAllocationsOfResource(Integer empId, Date startDate, OrgHierarchy currentBuId);
	// US3091/US3092: END
	
	public  List<ResourceAllocation>  findOpenAllocationsOfCopiedResource(Integer[] empId);
	
	List<Project> getProjectsForUser( List<OrgHierarchy> businessGroup, Integer currentLoggedInUserId, String activeOrAll);
	public List<Integer> getProjectListByOffshoreAndDelManager(Boolean isActive);
}	
