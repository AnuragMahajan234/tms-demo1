package org.yash.rms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yash.rms.dao.ProjectModuleDAO;
import org.yash.rms.domain.ProjectModule;
import org.yash.rms.service.ProjectModuleService;
/*User Story #4422(Create a new screen : Configure Project Module under configuration link)*/
@Service("projectModuleService")
@Transactional
public class ProjectModuleServiceImpl implements ProjectModuleService{
  @Autowired
  @Qualifier("projectModuleDAOImpl")
  ProjectModuleDAO projectModuleDao;
  @Transactional
  public List<ProjectModule> findActiveProjectModuleByProjectId(Integer projectId) {
    return projectModuleDao.findActiveProjectModuleByProjectId(projectId);
  }
  @Transactional
  public List<ProjectModule> findAllProjectModule(Integer projectId) {
    return projectModuleDao.findAllProjectModuleByProjectId(projectId);
  }
  @Transactional
  public List<ProjectModule> findProjectModuleByProjectIdAndStatus(Integer projectId, String status) {
    return projectModuleDao.findProjectModuleByProjectIdAndStatus(projectId,status);
  }
  @Transactional
  public ProjectModule findProjectModuleById(Integer id) {
    return projectModuleDao.findProjectModuleById(id);
  }
  @Transactional
  public boolean delete(int id) {
    return false;
  }
  @Transactional
  public boolean saveOrupdate(ProjectModule projectModule) {
    return projectModuleDao.saveOrupdate(projectModule);
  }

  public List<ProjectModule> findAll() {
    return null;
  }

  public List<ProjectModule> findByEntries(int firstResult, int sizeNo) {
    return null;
  }

  public long countTotal() {
    return 0;
  }

  public boolean saveOrupdateProjectModule(ProjectModule projectModule) {
    return false;
  }

}
