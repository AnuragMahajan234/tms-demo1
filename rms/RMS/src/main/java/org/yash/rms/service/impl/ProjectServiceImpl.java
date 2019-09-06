package org.yash.rms.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yash.rms.dao.ProjectActivityDao;
import org.yash.rms.dao.ProjectDao;
import org.yash.rms.domain.CustomActivity;
import org.yash.rms.domain.OrgHierarchy;
import org.yash.rms.domain.Project;
import org.yash.rms.domain.Resource;
import org.yash.rms.dto.EditProfileDTO;
import org.yash.rms.service.ProjectService;
import org.yash.rms.service.ResourceService;
import org.yash.rms.util.DozerMapperUtility;

@Service("ProjectService")
public class ProjectServiceImpl implements ProjectService {

	@Autowired
	@Qualifier("projectDaoImpl")
	ProjectDao projectDao;

	@Autowired
	@Qualifier("ProjectActivityDao")
	ProjectActivityDao projectActivityDao;
	
	@Autowired
	ResourceService resourceService;
	

	@Autowired
	private DozerMapperUtility mapper;

	public List<Project> findAll() {
		return projectDao.findAll();
	}

	public boolean delete(int id) {

		return projectDao.delete(id);
	}

	public boolean saveOrupdate(Project t) {

		return projectDao.saveOrupdate(t);
	}

	public List<Project> findByEntries(int firstResult, int sizeNo) {

		return null;
	}

	public long countTotal() {

		return 0;
	}

	public Project findProject(int id) {

		return projectDao.findProject(id);
	}

	// For US 3119
	public List<Project> findAllProjectByBUid(Integer id) {
		return projectDao.findAllProjectByBUid(id);
	}

	public List<Project> findProjectsByOffshoreDelMgr(Integer offshoreDelMgr, String activeOrAll) {
		return projectDao.findProjectsByOffshoreDelMgr(offshoreDelMgr, activeOrAll);
	}

	public List<Project> findProjectsForBGAdmin(List<OrgHierarchy> businessGroup) {

		return projectDao.findProjectsForBGAdmin(businessGroup);
	}

	public List<Project> findActiveProjectsForBGAdmin(List<OrgHierarchy> businessGroup) {

		return projectDao.findActiveProjectsForBGAdmin(businessGroup);
	}

	public List<Project> findAllActiveProjects() {

		return projectDao.findAllActiveProjects();
	}

	public List<Integer> findProjectsByBehalfManagar(Integer employeeId, String activeOrAll) {

		return projectDao.findProjectsByBehalfManagar(employeeId, activeOrAll);
	}

	public boolean saveProjectActivity(List<CustomActivity> activities) {

		return projectActivityDao.save(activities);
	}

	public List<Integer> findProjectID(String projects) {

		return projectDao.findProjectID(projects);
	}

	public boolean isKickOffDateGreater(int projectId, Date kickOffDate) {

		return projectDao.isKickOffDateGreater(projectId, kickOffDate);
	}

	public List<Project> findActiveProjectsNamesForBGAdmin(List<OrgHierarchy> buList) {

		return projectDao.findActiveProjectsNamesForBGAdmin(buList);
	}

	public Set<Project> findAllProjectByBUIds(List<Integer> bIds) {

		return projectDao.findAllProjectByBUIds(bIds);
	}

	public Set<Project> findAllProjectByBUAndLocationIdsForPWRReport(List<Integer> bIds, List<Integer> lIds) {

		return projectDao.findAllProjectByBUAndLocationIdsForPWRReport(bIds, lIds);
	}
	
	public Set<Project> findAllProjectsByBUIds(List<Integer> bIds) {

		return projectDao.findAllProjectsByBUIds(bIds);
	}
	
	public List<Project> findAllProjectByClientId(Integer id) {
		return projectDao.findAllProjectByClientId(id);
	}
	
	public List<Project> findProjectNamesByClientId(Integer id) {
		
		return projectDao.findProjectNamesByClientId(id);
	}

	public String findReportAccessForEmployee(Integer empId) {
		return null;
		//	return projectDao.findProjectIdsForReportAccess(empId);
	}

	public EditProfileDTO getBUHeadByProjectId(Integer id){
		EditProfileDTO editProfileDTO = new EditProfileDTO();
		OrgHierarchy hierarchy = projectDao.getBUHeadByProjectID(id);
		if(hierarchy.getEmployeeId() != null){
			Resource BUHead = hierarchy.getEmployeeId();
			 editProfileDTO = resourceService.getResourceDetailsByEmployeeId(BUHead.getEmployeeId());
		}
		return editProfileDTO;
	}
	public EditProfileDTO getBGHeadByProjectID (Integer id){
		OrgHierarchy hierarchy = projectDao.getBUHeadByProjectID(id);
		OrgHierarchy parentOrg = hierarchy.getParentId();
		EditProfileDTO bgh = new EditProfileDTO();
		if(parentOrg.getEmployeeId() != null){
			Integer bghEmpId = parentOrg.getEmployeeId().getEmployeeId();
			 bgh = resourceService.getResourceDetailsByEmployeeId(bghEmpId);
		}
		return bgh;
	}
	
	
	//Project Count here
	
	
	public Long getProjectCount() {

		return projectDao.getProjectCount();
	}
	
	
	
}
