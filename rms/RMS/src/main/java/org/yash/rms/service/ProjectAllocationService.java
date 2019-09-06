package org.yash.rms.service;

import java.util.Date;
import java.util.List;
import org.yash.rms.domain.OrgHierarchy;
import org.yash.rms.domain.Project;
import org.yash.rms.domain.ResourceAllocation;
import org.yash.rms.dto.ProjectDTO;
import org.yash.rms.util.ProjectAllocationSearchCriteria;

public interface ProjectAllocationService extends RmsCRUDService<org.yash.rms.dto.ProjectDTO> {

	List<org.yash.rms.dto.ProjectDTO> findProjectsByProjectNameEquals(String projectName);

	List<org.yash.rms.dto.ProjectDTO> managerView(Integer currentLoggedInUserId, String activeOrAll);

	List<org.yash.rms.dto.ProjectDTO> findProjectsForBGAdmin(List<OrgHierarchy> businessGroup, String activeOrAll);

	List<org.yash.rms.dto.ProjectDTO> findActiveProjectsByProjectNameEquals(String projectName, String sSearch, ProjectAllocationSearchCriteria projectAllocationSearchCriteria, String activeOrAll);

	List<org.yash.rms.dto.ProjectDTO> findAllActiveProjects(Integer page, Integer size, ProjectAllocationSearchCriteria searchCriteria, String activeOrAll, String sSearch,
			List<OrgHierarchy> businessGroup, Integer currentLoggedInUserId);

	public List<Integer> getProjectIdsForManager(Integer currentLoggedInUserId, String activeOrAll);

	public Long countActive(String activeOrAll, boolean isCurrentUserAdmin, List<OrgHierarchy> businessGroup, Integer currentLoggedInUserId);

	public long countSearch(ProjectAllocationSearchCriteria projectAllocationSearchCriteria, String activeOrAll, String search, List<OrgHierarchy> businessGroup, Integer currentLoggedInUserId);

	public List<Project> findCountProjectName(List<Integer> resourceid, Integer page, Integer size, String activeOrAll, List<String> projectIdList, Integer currentLoggedInUserId);

	public List<Project> findAllActiveProjectsByManager(Integer currentLoggrdInUser);

	public List<String> getProjectNameForManager(String activeOrAll, Integer currentLoggedInUserId);

	public List<String> findCountProjectNameForDashboardBillingStatus(List<Integer> empIdList, String activeOrAll, List<String> projectId);

	public List<String> findAllActiveProjectsForBGAdminDashboard(List<OrgHierarchy> businessGroup, Integer currentLoggedInUserId);

	public List<Integer> getProjectIdsForBGAdmin(List<OrgHierarchy> businessGroup);

	// US3091/US3092: START
	public List<ResourceAllocation> findOpenAllocationsOfResource(Integer empId, Date startDate, OrgHierarchy currentBuId);

	// US3091/US3092: END

	public List<ResourceAllocation> findOpenAllocationsOfCopiedResource(Integer[] empId);
	
	public List<ProjectDTO> getProjectsForUser(Integer userId, String userRole, String activeOrAll);

	List<Integer> getProjectsIdsForUser(Integer userId, String userRole, String string);
}
