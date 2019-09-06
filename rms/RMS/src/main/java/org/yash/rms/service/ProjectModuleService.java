package org.yash.rms.service;

import java.util.List;

import org.yash.rms.domain.ProjectModule;
/*User Story #4422(Create a new screen : Configure Project Module under configuration link)*/
public interface ProjectModuleService extends RmsCRUDService<ProjectModule> {
  
  public List<ProjectModule> findActiveProjectModuleByProjectId(Integer projectId);

  public List<ProjectModule> findAllProjectModule(Integer projectId);

  public List<ProjectModule> findProjectModuleByProjectIdAndStatus(Integer projectId,String status);

  public ProjectModule findProjectModuleById(Integer id);

}
