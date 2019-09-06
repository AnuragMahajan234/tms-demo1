/**
 * 
 */
package org.yash.rms.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.yash.rms.dao.ProjectAllocationDao;
import org.yash.rms.dao.ProjectDao;
import org.yash.rms.dao.ResourceDao;
import org.yash.rms.dao.impl.ProjectAllocationDaoImpl;
import org.yash.rms.domain.DefaultProject;
import org.yash.rms.domain.OrgHierarchy;
import org.yash.rms.domain.Project;
import org.yash.rms.domain.Resource;
import org.yash.rms.domain.ResourceAllocation;
import org.yash.rms.dto.ProjectDTO;
import org.yash.rms.dto.UserContextDetails;
import org.yash.rms.service.ProjectAllocationService;
import org.yash.rms.service.ResourceService;
import org.yash.rms.util.Constants;
import org.yash.rms.util.DozerMapperUtility;
import org.yash.rms.util.ProjectAllocationSearchCriteria;
import org.yash.rms.util.UserUtil;

/**
 * @author apurva.sinha
 * 
 */
@Service("ProjectAllocationService")
public class ProjectAllocationServiceImpl implements ProjectAllocationService {

	private static final Logger logger = LoggerFactory.getLogger(ProjectAllocationServiceImpl.class);
	@Autowired
	@Qualifier("ProjectAllocationDao")
	ProjectAllocationDao projectAllocationDao;

	@Autowired
	@Qualifier("ResourceDao")
	ResourceDao resourceDao;
	@Autowired
	@Qualifier("projectDaoImpl")
	ProjectDao projectDao;

	@Autowired
	private DozerMapperUtility mapperUtility;
	@Autowired
	@Qualifier("ResourceService")
	private ResourceService resourceServiceImpl;

	public boolean delete(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean saveOrupdate(ProjectDTO project) {
		// TODO Auto-generated method stub
		return false;
	}

	public List<ProjectDTO> findAll() {
		// TODO Auto-generated method stub
		logger.info("------ProjectAllocationServiceImpl  findAll method start------");
		List<org.yash.rms.domain.Project> projects = projectAllocationDao.findAll();
		List<ProjectDTO> list = new ArrayList<ProjectDTO>();

		// List<DefaultProject> defaultProjectList = new
		// ArrayList<DefaultProject>();//
		// defaultProjectList=projectDao.findAll();//
		if (null != projects && projects.size() > 0) {
			list = mapperUtility.convertMgrViewDomaintoDto(projects);
		}
		// else{
		// list =
		// mapperUtility.convertDefaultProjectFormTODefaultProjectDomain(form));
		// return defaultProjectList;
		// }//

		return list;
	}

	public List<ProjectDTO> findByEntries(int firstResult, int sizeNo) {
		// TODO Auto-generated method stub
		logger.info("------ProjectAllocationServiceImpl  findByEntries method start------");
		List<org.yash.rms.domain.Project> projects = projectAllocationDao.findByEntries(firstResult, sizeNo);
		List<ProjectDTO> list = new ArrayList<ProjectDTO>();
		if (null != projects && projects.size() > 0) {
			for (org.yash.rms.domain.Project project : projects) {

				ProjectDTO project2 = mapperUtility.convertProjectDomaintoDto(project);
				list.add(project2);
			}
		}
		return list;
	}

	public long countTotal() {
		// TODO Auto-generated method stub
		return 0;
	}

	public boolean saveOrupdate(org.yash.rms.domain.Project t) {
		// TODO Auto-generated method stub
		return false;
	}

	public List<ProjectDTO> findProjectsByProjectNameEquals(String projectName) {
		logger.info("------ProjectAllocationServiceImpl  findProjectsByProjectNameEquals method start------");
		List<org.yash.rms.domain.Project> projects = projectAllocationDao.findProjectsByProjectNameEquals(projectName);
		List<ProjectDTO> list = new ArrayList<ProjectDTO>();
		if (null != projects && projects.size() > 0) {
			for (org.yash.rms.domain.Project project : projects) {

				ProjectDTO project2 = mapperUtility.convertProjectDomaintoDto(project);
				list.add(project2);
			}
		}
		return list;

	}

	public List<ProjectDTO> managerView(Integer currentLoggedInUserId, String activeOrAll) {
		logger.info("------ProjectAllocationServiceImpl  managerView method start------");
		Resource resource = resourceDao.findByEmployeeId(currentLoggedInUserId);
		List<org.yash.rms.domain.Project> projects = projectAllocationDao.managerView(resource, activeOrAll);
		List<ProjectDTO> list = new ArrayList<ProjectDTO>();
		if (null != projects && projects.size() > 0) {

			list = mapperUtility.convertMgrViewDomaintoDto(projects);

		}
		return list;

	}

	public List<ProjectDTO> findProjectsForBGAdmin(List<OrgHierarchy> businessGroup, String activeOrAll) {
		// TODO Auto-generated method stub
		List<org.yash.rms.domain.Project> projectAllocationList = new ArrayList<org.yash.rms.domain.Project>();
		if (activeOrAll.equalsIgnoreCase("all"))
			projectAllocationList = projectDao.findProjectsForBGAdmin(businessGroup);
		else {
			projectAllocationList = projectDao.findActiveProjectsForBGAdmin(businessGroup);
		}
		List<ProjectDTO> list = new ArrayList<ProjectDTO>();
		if (projectAllocationList != null && projectAllocationList.size() > 0) {
			for (org.yash.rms.domain.Project project : projectAllocationList) {

				ProjectDTO project2 = mapperUtility.convertProjectDomaintoDto(project);
				list.add(project2);
			}
		}
		return list;
	}

	public List<ProjectDTO> findActiveProjectsByProjectNameEquals(String projectName, String sSearch, ProjectAllocationSearchCriteria projectAllocationSearchCriteria, String activeOrAll) {
		// TODO Auto-generated method stub
		logger.info("------ProjectAllocationServiceImpl  findProjectsByProjectNameEquals method start------");
		List<org.yash.rms.domain.Project> projects = projectAllocationDao.findActiveProjectsByProjectNameEquals(projectName, sSearch, projectAllocationSearchCriteria, activeOrAll);
		List<ProjectDTO> list = new ArrayList<ProjectDTO>();
		UserContextDetails currentResource = UserUtil.getCurrentResource();
		Integer id=0;
		if(currentResource!=null)
		{
		id = currentResource.getEmployeeId();
		}
		if (null != projects && projects.size() > 0) {
			for (org.yash.rms.domain.Project project : projects) {
				if (project.getOffshoreDelMgr().getEmployeeId().equals(id) || project.getDeliveryMgr().getEmployeeId().equals(id) ) { //enable all project for delivery manager instead of one
					project.setManagerReadonly(false);
				}else
				{
					project.setManagerReadonly(true);
				}
				ProjectDTO project2 = mapperUtility.convertProjectDomaintoDto(project);
				list.add(project2);
			}
		}
		return list;

	}

	public List<ProjectDTO> findAllActiveProjects(Integer page, Integer size, ProjectAllocationSearchCriteria searchCriteria, String activeOrAll, String sSearch, List<OrgHierarchy> businessGroup,
			Integer currentLoggedInUserId) {
		logger.info("------ProjectAllocationServiceImpl  findAll method start------");
		List<org.yash.rms.domain.Project> projects = projectAllocationDao.findAllActiveProjects(page, size, searchCriteria, activeOrAll, sSearch, businessGroup, currentLoggedInUserId);
		List<ProjectDTO> list = new ArrayList<ProjectDTO>();
		if (null != projects && projects.size() > 0) {
			list = mapperUtility.convertMgrViewDomaintoDto(projects);
		}

		return list;
	}

	public List<Integer> getProjectIdsForManager(Integer currentLoggedInUserId, String activeOrAll) {
		// TODO Auto-generated method stub
		return projectAllocationDao.getProjectIdsForManager(currentLoggedInUserId, activeOrAll);
	}

	public Long countActive(String activeOrAll, boolean isCurrentUserAdmin, List<OrgHierarchy> businessGroup, Integer currentLoggedInUserId) {

		return projectAllocationDao.countActive(activeOrAll, isCurrentUserAdmin, businessGroup, currentLoggedInUserId);
	}

	public long countSearch(ProjectAllocationSearchCriteria projectAllocationSearchCriteria, String activeOrAll, String search, List<OrgHierarchy> businessGroup, Integer currentLoggedInUserId) {

		return projectAllocationDao.countSearch(projectAllocationSearchCriteria, activeOrAll, search, businessGroup, currentLoggedInUserId);
	}

	public List<org.yash.rms.domain.Project> findCountProjectName(List<Integer> empIdList, Integer page, Integer size, String activeOrAll, List<String> projectIdList, Integer currentLoggedInUserId) {

		return projectAllocationDao.findCountProjectName(empIdList, page, size, activeOrAll, projectIdList, currentLoggedInUserId);
	}

	public List<org.yash.rms.domain.Project> findAllActiveProjectsByManager(Integer currentLoggrdInUser) {
		// TODO Auto-generated method stub
		return projectAllocationDao.findAllActiveProjectsByManager(currentLoggrdInUser);
	}

	public List<String> getProjectNameForManager(String activeOrAll, Integer currentLoggedInUserId) {

		return projectAllocationDao.getProjectNameForManager(activeOrAll, currentLoggedInUserId);
	}

	public List<String> findCountProjectNameForDashboardBillingStatus(List<Integer> empIdList, String activeOrAll, List<String> projectId) {
		// TODO Auto-generated method stub
		return projectAllocationDao.findCountProjectNameForDashboardBillingStatus(empIdList, activeOrAll, projectId);
	}

	public List<String> findAllActiveProjectsForBGAdminDashboard(List<OrgHierarchy> businessGroup, Integer currentLoggedInUserId) {
		// TODO Auto-generated method stub
		return projectAllocationDao.findAllActiveProjectsForBGAdminDashboard(businessGroup, currentLoggedInUserId);
	}

	public List<Integer> getProjectIdsForBGAdmin(List<OrgHierarchy> businessGroup) {
		return projectAllocationDao.getProjectIdsForBGAdmin(businessGroup);
	}

	// US3091/US3092: START
	public List<ResourceAllocation> findOpenAllocationsOfResource(Integer empId, Date startDate, OrgHierarchy currentBuId) {
		return projectAllocationDao.findOpenAllocationsOfResource(empId, startDate, currentBuId);
	}

	// US3091/US3092: END

	public List<ResourceAllocation> findOpenAllocationsOfCopiedResource(Integer[] empId) {
		return projectAllocationDao.findOpenAllocationsOfCopiedResource(empId);
	}

	public List<ProjectDTO> getProjectsForUser(Integer userId, String userRole,  String activeOrAll) {
		List<OrgHierarchy> buList = null;
		List<ProjectDTO> projectDisplay = new ArrayList<ProjectDTO>();
		/*  As discussed with Milind and Ankita , Implemented by Vikas-   
			User is Project Manager of Project and project should in active status except Admin and ROLE_BG_ADMIN. 
		*/
		if(userRole !=null && userRole.equalsIgnoreCase(Constants.ROLE_ADMIN)){
			return null;
		}else if(userRole !=null && userRole.equalsIgnoreCase(Constants.ROLE_BG_ADMIN)){
			buList = UserUtil.getCurrentResource().getAccessRight();
			projectDisplay = getProjectsForUser(buList,null, activeOrAll);
		}else {
			projectDisplay = getProjectsForUser(null,userId, activeOrAll);
		}
	  /* List<OrgHierarchy> buList = null;
	    if (userRole.equalsIgnoreCase(Constants.ROLE_DEL_MANAGER)) {
			projectDisplay = getProjectsForUser(null,userId, activeOrAll);
		} else if (userRole.equalsIgnoreCase(Constants.ROLE_BG_ADMIN)) {
			buList = UserUtil.getCurrentResource().getAccessRight();
			projectDisplay = getProjectsForUser(buList,null, activeOrAll);
		} else if (userRole.equalsIgnoreCase(Constants.ROLE_ADMIN)) {
			return null;
		}else {
			projectDisplay = getProjectsForUser(null,userId, activeOrAll);
		}
		*/
		return projectDisplay;
	}
	public List<ProjectDTO> getProjectsForUser( List<OrgHierarchy> businessGroup, Integer currentLoggedInUserId, String activeOrAll) {
		logger.info("------ProjectAllocationServiceImpl  findAll method start------");
		List<org.yash.rms.domain.Project> projects = projectAllocationDao.getProjectsForUser( businessGroup, currentLoggedInUserId,activeOrAll);
		List<ProjectDTO> list = new ArrayList<ProjectDTO>();
		if (null != projects && projects.size() > 0) {
			list = mapperUtility.convertMgrViewDomaintoDto(projects);
		}
		return list;
	}
	public List<Integer> getProjectsIdsForUser(Integer userId, String userRole,  String activeOrAll) {
		List<OrgHierarchy> buList = null;
		List<Integer> projectDisplay = new ArrayList<Integer>();
		
		if(userRole !=null && userRole.equalsIgnoreCase(Constants.ROLE_ADMIN)){
			return null;
		}else if(userRole !=null && (userRole.equalsIgnoreCase(Constants.ROLE_BG_ADMIN)||userRole.equalsIgnoreCase(Constants.ROLE_HR))){
			buList = UserUtil.getCurrentResource().getAccessRight();
			projectDisplay = getProjectsIdsForUser(buList,userId, activeOrAll);
		}else {
			projectDisplay = getProjectsIdsForUser(null,userId, activeOrAll);
		}
	  
		return projectDisplay;
	}
	public List<Integer> getProjectsIdsForUser( List<OrgHierarchy> businessGroup, Integer currentLoggedInUserId, String activeOrAll) {
		logger.info("------ProjectAllocationServiceImpl  findAll method start------");
		List<org.yash.rms.domain.Project> projects = projectAllocationDao.getProjectsForUser( businessGroup, currentLoggedInUserId,activeOrAll);
List<Integer> projectIds= new ArrayList<Integer>();
			Iterator<Project> proItr=projects.iterator();
			while(proItr.hasNext())
			{
				Project project=proItr.next();
				projectIds.add(project.getId());
			}
		
		//		List<ProjectDTO> list = new ArrayList<ProjectDTO>();
//		if (null != projects && projects.size() > 0) {
//			list = mapperUtility.convertMgrViewDomaintoDto(projects);
//		}
//		return list;
			return projectIds;
	}
}
