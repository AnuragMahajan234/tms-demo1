package org.yash.rms.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.yash.rms.dao.RmsCRUDDAO;
import org.yash.rms.domain.ProjectMethodology;
import org.yash.rms.util.UserUtil;
@Transactional
@Repository("ProjectMethodologyDao")
public class ProjectMethodologyDaoImpl implements RmsCRUDDAO<ProjectMethodology> {
	private static final Logger logger = LoggerFactory.getLogger(ProjectMethodologyDaoImpl.class);
	
	@PersistenceContext(name = "mysql", type = PersistenceContextType.TRANSACTION)
	private EntityManager entityManager;
	
	
	public EntityManager getEntityManager() {
		return entityManager;
	}
	
	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	public boolean saveOrupdate(ProjectMethodology projectMethodology) {
		
		logger.info("------------ProjectMethodologyDaoImpl saveOrupdate method end------------");
		
 if(null == projectMethodology) return false;
		
		Session currentSession = (Session) getEntityManager().getDelegate();
		Transaction transaction=null;
		boolean isSuccess=true;
		try {
			
			if(projectMethodology.getId()== null)
			{
				projectMethodology.setCreationTimestamp(new Date());
				projectMethodology.setCreatedId(UserUtil.getCurrentResource().getUsername());
				
			}
			projectMethodology.setLastUpdatedId(UserUtil.getCurrentResource().getUsername());
			isSuccess = currentSession.merge(projectMethodology)!=null?true:false;
		}catch (HibernateException e) {
			isSuccess = false;
			logger.error("HibernateException occured in saveOrupdate method at DAO layer:-"+e);
			throw e;
		}  
		logger.info("------------ProjectMethodologyDaoImpl saveOrupdate method end------------");
		return isSuccess;
	}

	public List<ProjectMethodology> findAll() {
		logger.info("------------ProjectMethodologyDaoImpl List method end------------");

		List<ProjectMethodology> methodologyList = new ArrayList<ProjectMethodology>();
		Session session=(Session) getEntityManager().getDelegate();
		try {
			methodologyList = session.createQuery("FROM ProjectMethodology").list(); 
		} catch (HibernateException e) {
		 
			logger.error("HibernateException occured in List method at DAO layer:-"+e);
			throw e;
		}  
		logger.info("------------ProjectMethodologyDaoImpl List method end------------");
		return methodologyList ;
	}

	public List<ProjectMethodology> findProjectMethodologyEntries(int firstResult,
			int sizeNo) {
		logger.info("------------ProjectMethodologyDaoImpl findProjectMethodologyEntries method end------------");

		Session currentSession = (Session) getEntityManager().getDelegate();
		List<ProjectMethodology> methodologyList = new ArrayList<ProjectMethodology>();
		try {
			 
			methodologyList = currentSession.createQuery("FROM ProjectMethodology").setFirstResult(firstResult).setMaxResults(sizeNo).list(); 
			 
			 
		} catch (HibernateException e) {
		 
			logger.error("HibernateException occured in findProjectMethodologyEntries method at DAO layer:-"+e);
			throw e;
		}  
		logger.info("------------ProjectMethodologyDaoImpl findProjectMethodologyEntries method end------------");
		return methodologyList ;
	}

	public long countTotal() {
		logger.info("------------ProjectMethodologyDaoImpl countTotal method end------------");

		Session currentSession = (Session) getEntityManager().getDelegate();
		Long count=0L;
		try{
			currentSession.createQuery("SELECT COUNT(o) FROM ProjectMethodology o").list(); 
			 count= (Long) currentSession.createCriteria("ProjectMethodology").setProjection(Projections.rowCount()).uniqueResult();
		}
		catch (HibernateException e) {
			 
			logger.error("HibernateException occured in countTotal method at DAO layer:-"+e);
			throw e;
		}  
		logger.info("------------ProjectMethodologyDaoImpl countTotal method end------------");
		return count;
	}

	public boolean delete(int id) {
		logger.info("------------ProjectMethodologyDaoImpl delete method end------------");

		boolean isSuccess=true;
		try {
			 Query query = ((Session) getEntityManager().getDelegate()).getNamedQuery(ProjectMethodology.DELETE_PROJECTMETHODOLOGY_BASED_ON_ID).setInteger("id", id);
			 int totalRowsDeleted = query.executeUpdate();
			System.out.println("Total Rows Deleted::" + totalRowsDeleted);
		} catch (HibernateException e) {
			isSuccess = false;
			logger.error("HibernateException occured in delete method at DAO layer:-"+e);
			throw e;
		}  
		logger.info("------------ProjectMethodologyDaoImpl delete method end------------");
		return isSuccess;
	}

	public boolean create(ProjectMethodology projectMethodology) {
		// TODO Auto-generated method stub
		logger.info("------------ProjectMethodologyDaoImpl create method end------------");

if(null == projectMethodology) return false;
		
		Session currentSession = (Session) getEntityManager().getDelegate();
 
		boolean isSuccess=true;
		try {
			 if (projectMethodology.getCreationTimestamp() == null) {
				 projectMethodology.setCreationTimestamp(new Date());
				   }
			currentSession.saveOrUpdate(projectMethodology);
 
		} catch (HibernateException e) {
			isSuccess = false;
			logger.error("HibernateException occured in create method at DAO layer:-"+e);
			throw e;
		}  
		logger.info("------------ProjectMethodologyDaoImpl create method end------------");
		return isSuccess;
	}

	
		
	public List<ProjectMethodology> findByEntries(int firstResult, int sizeNo) {
		return null;
	}
}
