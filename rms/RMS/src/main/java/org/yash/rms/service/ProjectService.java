package org.yash.rms.service;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.yash.rms.domain.OrgHierarchy;
import org.yash.rms.domain.Project;
import org.yash.rms.domain.Resource;
import org.yash.rms.dto.EditProfileDTO;

public interface ProjectService extends RmsCRUDService<Project> {

	public Project findProject(int id);

	public List<Project> findProjectsByOffshoreDelMgr(Integer employeeId, String activeOrAll);

	public List<Project> findProjectsForBGAdmin(List<OrgHierarchy> businessGroup);

	public List<Project> findActiveProjectsForBGAdmin(List<OrgHierarchy> businessGroup);

	public List<Project> findAllActiveProjects();

	public List<Integer> findProjectsByBehalfManagar(Integer employeeId, String activeOrAll);

	public List<Integer> findProjectID(String projects);

	public boolean isKickOffDateGreater(int projectId, Date kicjOffDate);

	// For US3119
	public List<Project> findAllProjectByBUid(Integer id);

	public List<Project> findActiveProjectsNamesForBGAdmin(List<OrgHierarchy> buList);

	public Set<Project> findAllProjectByBUIds(List<Integer> bids);

	public Set<Project> findAllProjectByBUAndLocationIdsForPWRReport(List<Integer> bids, List<Integer> lIds);

	public List<Project> findAllProjectByClientId(Integer id);
	public List<Project> findProjectNamesByClientId(Integer id);
	public String findReportAccessForEmployee(Integer empId);
	public EditProfileDTO getBUHeadByProjectId(Integer id);
	
	public EditProfileDTO getBGHeadByProjectID (Integer id);

	public Long getProjectCount();
}
