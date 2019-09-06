package org.yash.rms.dao.impl;

import java.util.ArrayList;
/**
 * @author purva.bhate
 */
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.yash.rms.dao.DefaultProjectDao;
import org.yash.rms.domain.DefaultProject;
import org.yash.rms.domain.OrgHierarchy;
import org.yash.rms.domain.Project;
import org.yash.rms.util.UserUtil;
@Repository("defaultProjectDao")
public class DefaultProjectDaoImpl implements DefaultProjectDao {

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
		
		
	
	public boolean delete(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	// added by purva For US3119 to save a default project
	@Transactional(propagation = Propagation.MANDATORY)
	public boolean saveOrupdate(DefaultProject defaultProject) {

		logger.info("--------DefaultProjectDaoImpl saveOrUpdate method start-------");
		if(null == defaultProject) return false;
		
		Session currentSession = (Session) getEntityManager().getDelegate();
		boolean isSuccess=true;
		try {
			if(null==defaultProject.getId())
			{
				
				defaultProject.setCreatedId( UserUtil.getUserContextDetails().getUserName());
				defaultProject.setCreationTimestamp(new Date());
				
			}else{
			
			defaultProject.setLastUpdatedId( UserUtil.getUserContextDetails().getUserName());
			defaultProject.setLastupdatedTimestamp(new Date());
			
			}
			currentSession.saveOrUpdate(defaultProject);
			//currentSession.flush();
		} catch (HibernateException e) {
			isSuccess = false;
			e.printStackTrace();
			logger.error("Exception Occurred while saving DefaultProject "+defaultProject+e.getMessage());
			 throw e;
		}   finally {
			currentSession.flush();
		}
		logger.info("------DefaultProjectDaoImpl saveOrUpate method end-----");
		return isSuccess;}

	// added by purva For US3119 Find all default project list
	@Transactional(propagation = Propagation.MANDATORY)
	public List findAll() {
		List<DefaultProject> defaultProjectList = new ArrayList<DefaultProject>();
		logger.info("--------DefaultProjectDaoImpl findAll method start-------");
		try {
		
		Session session = (Session) getEntityManager().getDelegate();
		
			////////////////////
	
		
			if (UserUtil.isCurrentUserIsBusinessGroupAdmin()) {
				List<OrgHierarchy> buList = UserUtil.getCurrentResource().getAccessRight();

				Criteria criteria = session.createCriteria(DefaultProject.class);
				criteria.add(Restrictions.in("orgHierarchy", buList));
				defaultProjectList=criteria.list(); 
			}else{
				Query query = session.createQuery("FROM DefaultProject");
				defaultProjectList = query.list();
			}

			
		
		//////////////////////////
		

		
		} catch (HibernateException e) {
			e.printStackTrace();
			logger.error("Exception occured in findAll method at DAO layer:-"
					+ e);
			throw e;
		}
		logger.info("--------DefaultProjectDaoImpl findAll method end-------");
		return defaultProjectList;
	
	}

	public List findByEntries(int firstResult, int sizeNo) {
		// TODO Auto-generated method stub
		return null;
	}

	public long countTotal() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public DefaultProject getDefaultProjectbyProjectId(Project projectId){
		Session session= (Session) getEntityManager().getDelegate();
		Query query= session.createQuery("From DefaultProject where  projectId=:id");
		query.setParameter("id", projectId);
		List<DefaultProject> projectList= query.list();
		DefaultProject project= null;
		if(null!= projectList && !projectList.isEmpty()){
		project= projectList.get(0);
		}
		return project;

	}
	public DefaultProject getDefaultProjectbyProjectForBU(OrgHierarchy currentBuId){
		Session session = (Session) getEntityManager().getDelegate();
		Query query = session
				.createQuery("FROM DefaultProject where orgHierarchy.id=:id");
		query.setParameter("id", currentBuId.getId());
		List<DefaultProject> defaultProjectList = query.list();
		DefaultProject defaultProject = null;
		if (null != defaultProjectList && !defaultProjectList.isEmpty()) {
			defaultProject = defaultProjectList.get(0);
		}
		return defaultProject;

	}
	
	
}
