package org.yash.rms.dao;

import java.util.List;

import org.yash.rms.domain.ProjectSubModule;
import org.yash.rms.util.ProjectSubModuleSearchCriteria;

public interface ProjectSubModuleDAO extends RmsCRUDDAO<ProjectSubModule> {
	
	public List<Object[]> findActiveProjectSubModulesByModuleId(String moduleName,Integer resAllocId);

	public List<ProjectSubModule> findAllProjectSubModuleByModuleId(Integer moduleId);

	public List<ProjectSubModule> findProjectSubModuleByModuleIdAndStatus(Integer moduleId, String status);

	public ProjectSubModule findProjectSubModuleById(Integer id);

	public List<ProjectSubModule> findActiveModuleList(int page, int size, ProjectSubModuleSearchCriteria projectSubModuleSearchCriteria, String status, String sSearch);
	
	public long totalCountOfsubModule(String status);

    public long totalCountOfSubModule(String status,
      ProjectSubModuleSearchCriteria projectSubModuleSearchCriteria, String sSearch);
    
    public List<ProjectSubModule> findActiveProjectSubModulesByModuleNameAndProjectId(String moduleName, Integer projectId) throws Exception;
    								


}
