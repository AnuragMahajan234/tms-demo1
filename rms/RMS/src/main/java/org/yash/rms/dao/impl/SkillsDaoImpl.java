package org.yash.rms.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.yash.rms.dao.SkillsDao;
import org.yash.rms.domain.Skills;
import org.yash.rms.util.UserUtil;

@Repository("skillsDaoImpl")
@Transactional
public class SkillsDaoImpl implements SkillsDao {

	@PersistenceContext(name = "mysql", type = PersistenceContextType.TRANSACTION)
	private EntityManager entityManager;
	
	
	public EntityManager getEntityManager() {
		return entityManager;
	}
	
	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	private static final Logger logger = LoggerFactory.getLogger(LocationDaoImpl.class);
	
	public boolean saveOrupdate(Skills skills) {
		logger.info("------------LocationDaoImpl delete method end------------");
     if(null == skills) return false;
		
		Session currentSession = (Session) getEntityManager().getDelegate();
		Transaction transaction=null;
		boolean isSuccess=true;
		try {
			if(skills.getId()==null)
			{
				skills.setCreationTimestamp(new Date());
				skills.setCreatedId(UserUtil.getCurrentResource().getUsername());
				
			}
			skills.setLastUpdatedId(UserUtil.getCurrentResource().getUsername());
			isSuccess = currentSession.merge(skills)!=null?true:false;
 
		} catch (HibernateException e) {
			if (transaction != null) {
 
			}
			isSuccess = false;
			logger.error("HibernateException occured in saveOrupdate method at DAO layer:-"+e);
			throw e;
		}   
		logger.info("------------LocationDaoImpl saveOrupdate method end------------");
		 
		return isSuccess;
	}

	public List<Skills> findAll() {
		logger.info("------------LocationDaoImpl delete method end------------");
		List<Skills> skillsList = new ArrayList<Skills>();
		Session session=(Session) getEntityManager().getDelegate();
		try{
		skillsList = session.createQuery("FROM Skills").list();
		}
		catch (HibernateException e) {
			 
			logger.error("HibernateException occured in saveOrupdate method at DAO layer:-"+e);
			throw e;
		}   
		logger.info("------------LocationDaoImpl saveOrupdate method end------------");
		 
		return skillsList;
	}

	public List<Skills> findByEntries(int firstResult, int sizeNo) {
		logger.info("------------LocationDaoImpl delete method end------------");
		Session currentSession = (Session) getEntityManager().getDelegate();
		List<Skills> skillsList = new ArrayList<Skills>();
		try {
			 
			skillsList = currentSession.createQuery("FROM Skills").setFirstResult(firstResult).setMaxResults(sizeNo).list(); 
			 
			 
		} catch (HibernateException e) {
			 
			logger.error("HibernateException occured in saveOrupdate method at DAO layer:-"+e);
			throw e;
		}   
		logger.info("------------LocationDaoImpl saveOrupdate method end------------");
		 
		return skillsList ;
	 
	}

	public long countTotal() {
		logger.info("------------LocationDaoImpl delete method end------------");
		Session currentSession = (Session) getEntityManager().getDelegate();
		Long count=0L;
		try{
			currentSession.createQuery("SELECT COUNT(*) FROM Skills").list(); 
			 count= (Long) currentSession.createCriteria("Skills").setProjection(Projections.rowCount()).uniqueResult();
		}
		catch (HibernateException e) {
			 
			logger.error("HibernateException occured in saveOrupdate method at DAO layer:-"+e);
			throw e;
		}   
		logger.info("------------LocationDaoImpl saveOrupdate method end------------");
		 ;
		return count;
	}

	public boolean delete(int id) {
		logger.info("------------LocationDaoImpl delete method end------------");
		boolean isSuccess=true;
		try {
 		 
			 Query query = ((Session) getEntityManager().getDelegate()).getNamedQuery(Skills.DELETE_SKILLS_BASED_ON_ID).setInteger("id", id);
			 
			 int totalRowsDeleted = query.executeUpdate();
			System.out.println("Total Rows Deleted::" + totalRowsDeleted);
 
		}catch (HibernateException e) {
			isSuccess = false;
			logger.error("HibernateException occured in saveOrupdate method at DAO layer:-"+e);
			throw e;
		}   
		logger.info("------------LocationDaoImpl saveOrupdate method end------------");
		return isSuccess;
		 
	}


	public boolean create(Skills skills) {
		logger.info("------------LocationDaoImpl delete method end------------");
if(null == skills) return false;
		
		Session currentSession = (Session) getEntityManager().getDelegate();
 
		boolean isSuccess=true;
		try {
			 if (skills.getCreationTimestamp() == null) {
				 skills.setCreationTimestamp(new Date());
				   }
			currentSession.saveOrUpdate(skills);
 
		}catch (HibernateException e) {
			isSuccess = false;
			logger.error("HibernateException occured in saveOrupdate method at DAO layer:-"+e);
			throw e;
		}   
		logger.info("------------LocationDaoImpl saveOrupdate method end------------");
		return isSuccess;
		 
	}


	

	public List<Skills> getPrimarySkills() {
		logger.info("------------LocationDaoImpl delete method end------------");
		  List<Skills> primarySkillsList=null;
		try{
		Session currentSession = (Session) getEntityManager().getDelegate();
	     primarySkillsList=	currentSession.createQuery("SELECT s FROM Skills s where s.skillType='Primary'").list();
		}catch (HibernateException e) {
			 
			logger.error("HibernateException occured in saveOrupdate method at DAO layer:-"+e);
			throw e;
		}   
		logger.info("------------LocationDaoImpl saveOrupdate method end------------");
		return primarySkillsList;
	}

	public List<Skills> getSecondrySkills() {
		logger.info("------------LocationDaoImpl delete method end------------");
		List<Skills> secondarySkillsList=null;
		try{
		Session currentSession = (Session) getEntityManager().getDelegate();
	    secondarySkillsList=	currentSession.createQuery("SELECT s FROM Skills s where s.skillType='Secondary'").list();
		}catch (HibernateException e) {
			 
			logger.error("HibernateException occured in saveOrupdate method at DAO layer:-"+e);
			throw e;
		}   
		logger.info("------------LocationDaoImpl saveOrupdate method end------------");
		return secondarySkillsList;
	}
	
	
	
	public Skills find(int id) {
		logger.info("------------LocationDaoImpl delete method end------------");
		Skills skills =null;
		try{
		Criteria criteria=((Session) getEntityManager().getDelegate()).createCriteria(Skills.class);
		 skills=(Skills)criteria.add(Restrictions.eq("id", id)).uniqueResult();
		}
		catch (HibernateException e) {
			 
			logger.error("HibernateException occured in saveOrupdate method at DAO layer:-"+e);
			throw e;
		}   
		logger.info("------------LocationDaoImpl saveOrupdate method end------------");
		 
		return skills;
	}
}