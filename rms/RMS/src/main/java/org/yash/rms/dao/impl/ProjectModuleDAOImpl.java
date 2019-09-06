package org.yash.rms.dao.impl;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.yash.rms.dao.ProjectModuleDAO;
import org.yash.rms.domain.ProjectModule;
import org.yash.rms.domain.ResourceAllocation;
import org.yash.rms.util.UserUtil;
/*User Story #4422(Create a new screen : Configure Project Module under configuration link)*/
@Repository("projectModuleDAOImpl")
public class ProjectModuleDAOImpl implements ProjectModuleDAO {

  private static final Logger logger = LoggerFactory.getLogger(ProjectDaoImpl.class);

  @PersistenceContext(name = "mysql", type = PersistenceContextType.TRANSACTION)
	private EntityManager entityManager;
	
	
	public EntityManager getEntityManager() {
		return entityManager;
	}
	
	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
 
  @Transactional(propagation = Propagation.MANDATORY)
  public List<ProjectModule> findActiveProjectModuleByProjectId(Integer resourceAllocId) {
    Session currentSession = (Session) getEntityManager().getDelegate();
    Query query = null;
    boolean isSuccess = true;
    query = currentSession.createSQLQuery("SELECT * FROM resource_allocation WHERE id=:id")
        .addEntity(ResourceAllocation.class).setParameter("id", resourceAllocId);
    ResourceAllocation resourceAllocation = (ResourceAllocation) query.uniqueResult();
    query = currentSession
        .createSQLQuery("SELECT * FROM project_module WHERE project_id=:projectId and active=1 ORDER BY module_name")
        .addEntity(ProjectModule.class)
        .setParameter("projectId", resourceAllocation.getProjectId().getId());
    List<ProjectModule> projectModule = (List<ProjectModule>) query.list();
    return projectModule;

  }
  
  @Transactional(propagation = Propagation.MANDATORY)
  public List<ProjectModule> findAllProjectModuleByProjectId(Integer projectId) {
    Session currentSession = (Session) getEntityManager().getDelegate();
    Query query = null;
    boolean isSuccess = true;
    query =
        currentSession.createSQLQuery("SELECT * FROM project_module WHERE project_id=:projectId ORDER BY module_name")
            .addEntity(ProjectModule.class).setParameter("projectId", projectId);
    List<ProjectModule> projectModule = (List<ProjectModule>) query.list();
    return projectModule;
  }

  @Transactional(propagation = Propagation.MANDATORY)
  public List<ProjectModule> findProjectModuleByProjectIdAndStatus(Integer projectId,
      String status) {
    Session currentSession = (Session) getEntityManager().getDelegate();
    Query query = null;
    query = currentSession
        .createSQLQuery(
            "SELECT * FROM project_module WHERE project_id=:projectId and active=:status ORDER BY module_name")
        .addEntity(ProjectModule.class).setParameter("projectId", projectId)
        .setParameter("status", status);
    List<ProjectModule> projectModule = (List<ProjectModule>) query.list();
    return projectModule;
  }
  
  @Transactional(propagation = Propagation.MANDATORY)
  public ProjectModule findProjectModuleById(Integer id) {
    Session currentSession = (Session) getEntityManager().getDelegate();
    Query query = null;
    query = currentSession.createSQLQuery("SELECT * FROM project_module WHERE id=:id")
        .addEntity(ProjectModule.class).setParameter("id", id);
    ProjectModule projectModule = (ProjectModule) query.uniqueResult();
    return projectModule;
  }
  
  @Transactional(propagation = Propagation.MANDATORY)
  public boolean delete(int id) {
    return false;
  }
 
  @Transactional(propagation = Propagation.MANDATORY)
  public boolean saveOrupdate(ProjectModule projectModule) {
    if(null == projectModule) return false;
    
    Session currentSession = (Session) getEntityManager().getDelegate();
    boolean isSuccess=true;
    try {
        if(projectModule.getId()==null)
        {
            
            projectModule.setCreatedId( UserUtil.getUserContextDetails().getUserName());
            projectModule.setCreationTimestamp(new Date());
            
        }
        
    currentSession.saveOrUpdate(projectModule);
        
    } catch (HibernateException e) {
        isSuccess = false;
        e.printStackTrace();
        logger.error("Exception Occurred while saving Activity "+projectModule.getModuleName()+e.getMessage());
         throw e;
    }   finally {
    }
    logger.info("------ProjectActivityDaoImpl saveOrUpate method end-----");
    return isSuccess;
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
  

}
