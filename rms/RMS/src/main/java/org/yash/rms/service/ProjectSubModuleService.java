package org.yash.rms.service;

import java.util.List;

import org.yash.rms.domain.ProjectModule;
import org.yash.rms.domain.ProjectSubModule;
import org.yash.rms.dto.ProjectSubModuleActiveInActiveListDTO;
import org.yash.rms.dto.ProjectSubModuleDTO;
import org.yash.rms.util.ProjectSubModuleSearchCriteria;

public interface ProjectSubModuleService extends RmsCRUDService<ProjectModule> {
	
	public List<ProjectSubModuleDTO> findActiveSubModulesByModuleId(String moduleName, Integer resAllocId);

	public List<ProjectSubModuleActiveInActiveListDTO> findAllProjectSubModulesByModuleId(Integer moduleId);

	public List<ProjectSubModule> findProjectSubModuleByModuleIdAndStatus(Integer moduleId, String status);

	public ProjectSubModule findProjectSubModuleById(Integer id);

	public boolean saveOrupdate(ProjectSubModule projectSubModule);

	public List<ProjectSubModule> findActiveModuleList(int page, int size, ProjectSubModuleSearchCriteria projectSubModuleSearchCriteria, String status, String sSearch);
	
	public List<ProjectSubModule> findAllProjectSubModule(Integer moduleId);
	
	public long totalCountOfSubModule(String status);

    public long totalCountOfSubModule(String status,ProjectSubModuleSearchCriteria projectSubModuleSearchCriteria, String sSearch);
    
    public List<ProjectSubModule> findActiveProjectSubModulesByModuleNameAndProjectId(String moduleName,Integer projectId) throws Exception;
      
}
