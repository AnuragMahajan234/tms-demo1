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
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.yash.rms.dao.CompetencyDao;
import org.yash.rms.domain.Competency;
import org.yash.rms.util.UserUtil;

@Repository("CompetencyDao")
@Transactional
public class CompetencyDaoImpl implements CompetencyDao{
	@PersistenceContext(name = "mysql", type = PersistenceContextType.TRANSACTION)
	private EntityManager entityManager;
	
	
	public EntityManager getEntityManager() {
		return entityManager;
	}
	
	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	private static final Logger logger = LoggerFactory.getLogger(CompetencyDaoImpl.class);

	public boolean delete(int id) {
		logger.info("------------CompetencyDaoImpl delete method end------------");
		boolean isSuccess=true;
		try {
			 Query query = ((Session) getEntityManager().getDelegate()).getNamedQuery(Competency.DELETE_Competency_BASED_ON_ID).setInteger("id", id);
			 int totalRowsDeleted = query.executeUpdate();
			System.out.println("Total Rows Deleted::" + totalRowsDeleted);
		} catch (HibernateException e) {
			isSuccess = false;
			logger.error("HibernateException occured in delete method at DAO layer:-"+e);
			throw e;
		} 
		return isSuccess;
	}

	public boolean saveOrupdate(Competency competency) {
		logger.info("----------CompetencyDaoImpl saveOrupdate method end------------");
		if(null == competency) return false;
		
		Session currentSession = (Session) getEntityManager().getDelegate();
		boolean isSuccess=true;
		try {
			if(competency.getId()==0)
			{
				competency.setCreationTimestamp(new Date());
				competency.setCreatedId(UserUtil.getCurrentResource().getUsername());
				
			}
			competency.setLastUpdatedId(UserUtil.getCurrentResource().getUsername());
			currentSession.saveOrUpdate(competency);
		} catch (HibernateException e) {
			isSuccess = false;
			logger.error("HibernateException occured in saveOrupdate method at DAO layer:-"+e);
			throw e;
		}
		return isSuccess;
	}

	public List<Competency> findAll() {
		logger.info("------------CompetencyDaoImpl findAll method end------------");
		List<Competency> competencyList = new ArrayList<Competency>();
		Session session=(Session) getEntityManager().getDelegate();
		try {
			competencyList = session.createQuery("FROM Competency").list(); 
		} catch (HibernateException e) {
			logger.error("HibernateException occured in findAll method at DAO layer:-"+e);
			throw e; 
	      }
		return competencyList ;
	}

	public List<Competency> findByEntries(int firstResult, int sizeNo) {
		logger.info("------------competencyDaoImpl findByEntries method end------------");
		// TODO Auto-generated method stub
		Session session=(Session) getEntityManager().getDelegate();
		List<Competency> competencyList = new ArrayList<Competency>();
		try {
			competencyList = session.createQuery("FROM Competency").setFirstResult(firstResult).setMaxResults(sizeNo).list(); 
		} catch (HibernateException e) {
	         e.printStackTrace(); 
	         logger.error("HibernateException occured in findByEntries method at DAO layer:-"+e);
			 throw e;
	      }
		return competencyList;
	}

	public long countTotal() {
		logger.info("------------CompetencyDaoImpl countTotal method end------------");
		// TODO Auto-generated method stub
		Session session=(Session) getEntityManager().getDelegate();
		Long count=0L;
		try{
			session.createQuery("SELECT COUNT(o) FROM Competency o").list(); 
			 count= (Long) session.createCriteria("Competency").setProjection(Projections.rowCount()).uniqueResult();
		}
		catch (HibernateException e) {
	         e.printStackTrace(); 
	         logger.error("HibernateException occured in countTotal method at DAO layer:-"+e);
			 throw e;
	      }
		return count;
	}



	public Competency findById(int id) {
		// TODO Auto-generated method stub
		Competency competency = null;
		logger.info("--------ActivityDaoImpl findById method start-------");
		Session session=(Session) getEntityManager().getDelegate();
		try {
			competency = (Competency)session.createCriteria(Competency.class).add(Restrictions.eq("id", id)).uniqueResult(); 
		} catch (HibernateException e) {
	         e.printStackTrace(); 
	         logger.error("EXCEPTION CAUSED IN GETTING competency By Id " +id+ e.getMessage());
	         throw e;
	     }finally {
	         //session.close(); 
	      }
		logger.info("------competencyDaoImpl findById method end-----");
		return competency ;
	}
	}


