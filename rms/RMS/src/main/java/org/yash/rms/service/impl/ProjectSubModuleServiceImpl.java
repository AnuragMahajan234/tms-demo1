package org.yash.rms.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yash.rms.dao.ProjectSubModuleDAO;
import org.yash.rms.dao.ResourceAllocationDao;
import org.yash.rms.domain.ProjectModule;
import org.yash.rms.domain.ProjectSubModule;
import org.yash.rms.dto.ProjectSubModuleDTO;
import org.yash.rms.dto.ProjectSubModuleActiveInActiveListDTO;
import org.yash.rms.helper.ProjectSubModulesHelper;
import org.yash.rms.service.ProjectSubModuleService;
import org.yash.rms.util.Constants;
import org.yash.rms.util.ProjectSubModuleSearchCriteria;

@Service("projectSubModuleService")
@Transactional
public class ProjectSubModuleServiceImpl implements ProjectSubModuleService {

	@Autowired
	@Qualifier("projectSubModuleDAO")
	ProjectSubModuleDAO projectSubModuleDao;

	@Autowired
	@Qualifier("ProjectSubModulesHelper")
	ProjectSubModulesHelper projectSubModulesHelper;
	
	@Autowired
	private ResourceAllocationDao resourceAllocDao;

	@Transactional

	public List<ProjectSubModuleActiveInActiveListDTO> findAllProjectSubModulesByModuleId(Integer moduleId) {
		List<ProjectSubModule> projectSubModules = projectSubModuleDao.findAllProjectSubModuleByModuleId(moduleId);

		ListIterator<ProjectSubModule> subModulesListIterator = projectSubModules.listIterator();

		ProjectSubModuleActiveInActiveListDTO projectSubModulesDTOList = new ProjectSubModuleActiveInActiveListDTO();

		while (subModulesListIterator.hasNext()) {

			ProjectSubModuleDTO projectSubModuleDTO = new ProjectSubModuleDTO();
			ProjectSubModule projectSubModule = subModulesListIterator.next();

			ProjectSubModuleDTO projectSubModulesDTO = projectSubModulesHelper.fromProjectSubModuleDomainToDto(projectSubModule, projectSubModuleDTO);

			if (Constants.INACTIVE.equalsIgnoreCase(projectSubModule.getActive())) {

				projectSubModulesDTOList.getInactiveProjectSubModules().add(projectSubModulesDTO);
			} else {

				projectSubModulesDTOList.getActiveProjectSubModules().add(projectSubModulesDTO);
			}

		}

		return Arrays.asList(projectSubModulesDTOList);
	}

	@Transactional
	public List<ProjectSubModule> findProjectSubModuleByModuleIdAndStatus(Integer moduleId, String status) {
		return projectSubModuleDao.findProjectSubModuleByModuleIdAndStatus(moduleId, status);
	}

	@Transactional
	public ProjectSubModule findProjectSubModuleById(Integer id) {
		return projectSubModuleDao.findProjectSubModuleById(id);
	}
	
public List<ProjectSubModuleDTO> findActiveSubModulesByModuleId(String moduleName,Integer resAllocId){
		
		List<ProjectSubModuleDTO> activeSubModulesDTO = new ArrayList<ProjectSubModuleDTO>();
		
		List<Object[]> activeProjectSubModules = projectSubModuleDao.findActiveProjectSubModulesByModuleId(moduleName,resAllocId);
		
		ProjectSubModuleDTO projectSubModuleDTO;
		
		for(Object[] row : activeProjectSubModules){
			
			projectSubModuleDTO = new ProjectSubModuleDTO();
				projectSubModuleDTO.setSubModuleId((Integer)row[0]);
				projectSubModuleDTO.setSubModuleName((String)row[1]);
			
			activeSubModulesDTO.add(projectSubModuleDTO);
		}
			
		return activeSubModulesDTO;
	}

	@Transactional
	public boolean delete(int id) {
		return false;
	}

	public long countTotal() {
		return 0;
	}

	public boolean saveOrupdateProjectModule(ProjectSubModule projectModule) {
		return false;
	}

	public boolean saveOrupdate(ProjectModule t) {

		return false;
	}

	public List<ProjectModule> findAll() {

		return null;
	}

	public List<ProjectModule> findByEntries(int firstResult, int sizeNo) {

		return null;
	}

	public boolean saveOrupdate(ProjectSubModule projectSubModule) {

		return projectSubModuleDao.saveOrupdate(projectSubModule);
	}

	public List<ProjectSubModule> findActiveModuleList(int page, int size, ProjectSubModuleSearchCriteria projectSubModuleSearchCriteria, String status, String sSearch) {

		return projectSubModuleDao.findActiveModuleList(page, size, projectSubModuleSearchCriteria, status,  sSearch);
	}

	@Transactional
	public List<ProjectSubModule> findAllProjectSubModule(Integer moduleId) {
		return projectSubModuleDao.findAllProjectSubModuleByModuleId(moduleId);
	}

	public long totalCountOfSubModule(String status) {
		
		return projectSubModuleDao.totalCountOfsubModule(status);
	}

  public long totalCountOfSubModule(String status,
      ProjectSubModuleSearchCriteria projectSubModuleSearchCriteria, String sSearch) {
    return projectSubModuleDao.totalCountOfSubModule(status, projectSubModuleSearchCriteria, sSearch);
  }
  
  public List<ProjectSubModule> findActiveProjectSubModulesByModuleNameAndProjectId(String moduleName,Integer projectId) throws Exception{
	  return projectSubModuleDao.findActiveProjectSubModulesByModuleNameAndProjectId(moduleName, projectId);
  }
}
