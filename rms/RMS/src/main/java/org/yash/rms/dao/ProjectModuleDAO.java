package org.yash.rms.dao;
import java.util.List;

import org.yash.rms.domain.ProjectModule;
/*User Story #4422(Create a new screen : Configure Project Module under configuration link)*/
public interface ProjectModuleDAO extends RmsCRUDDAO<ProjectModule>  {

  public List<ProjectModule> findActiveProjectModuleByProjectId(Integer projectId);
  public List<ProjectModule> findAllProjectModuleByProjectId(Integer projectId);
  public List<ProjectModule> findProjectModuleByProjectIdAndStatus(Integer projectId, String status);
  public ProjectModule findProjectModuleById(Integer id);

}
