package org.yash.rms.dao;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.yash.rms.domain.OrgHierarchy;
import org.yash.rms.domain.Project;

public interface ProjectDao extends RmsCRUDDAO<Project> {

	public List<Project> findAll();

	public Project findProject(int id);

	public List<Project> findProjectsByOffshoreDelMgr(Integer offshoreDelMgr, String activeOrAll);

	public List<Project> findProjectsForBGAdmin(List<OrgHierarchy> businessGroup);

	public List<Project> findActiveProjectsForBGAdmin(List<OrgHierarchy> businessGroup);

	public List<Project> findAllActiveProjects();

	public List<Integer> findProjectsByBehalfManagar(Integer employeeId, String activeOrAll);

	public List<Integer> findProjectID(String projects);

	public boolean isKickOffDateGreater(int projectId, Date kicjOffDate);

	// For US3119
	public List<Project> findAllProjectByBUid(Integer id);

	public List<Project> findActiveProjectsNamesForBGAdmin(List<OrgHierarchy> buList);

	public Set<Project> findAllProjectByBUIds(List<Integer> bIds);

	public Set<Project> findAllProjectByBUAndLocationIdsForPWRReport(List<Integer> bIds, List<Integer> lIds);

	public Set<Project> findAllProjectsByBUIds(List<Integer> bIds);
	
	public List<Project> findAllProjectByClientId(Integer id);

	public List<Project> findProjectsbyid(List<Integer> myList);

	public List<Project> findProjectNamesByClientId(Integer id);
	
	public OrgHierarchy getBUHeadByProjectID (Integer prjId);

	public Long getProjectCount();
	
	public Project findProjectByProjectName(final String projectName) throws Exception;
	
}
