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
import org.yash.rms.dao.OwnershipDao;
import org.yash.rms.domain.Ownership;
import org.yash.rms.util.UserUtil;

@Repository("OwnershipDao")
@Transactional
public class OwnershipDaoImpl implements OwnershipDao{
	
	@PersistenceContext(name = "mysql", type = PersistenceContextType.TRANSACTION)
	private EntityManager entityManager;
	
	
	public EntityManager getEntityManager() {
		return entityManager;
	}
	
	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	private static final Logger logger = LoggerFactory.getLogger(OwnershipDaoImpl.class);

	public boolean delete(int id) {
		logger.info("------------ownershipDaoImpl delete method end------------");
		boolean isSuccess=true;
		try {
			 Query query = ((Session) getEntityManager().getDelegate()).getNamedQuery(Ownership.DELETE_OWNERSHIP_BASED_ON_ID).setInteger("id", id);
			 int totalRowsDeleted = query.executeUpdate();
			System.out.println("Total Rows Deleted::" + totalRowsDeleted);
		} catch (HibernateException e) {
			isSuccess = false;
			logger.error("HibernateException occured in delete method at DAO layer:-"+e);
			throw e;
		} 
		return isSuccess;
	}

	public boolean saveOrupdate(Ownership ownership) {
		logger.info("------------ownershipDaoImpl saveOrupdate method end------------");
		if(null == ownership) return false;
		
		Session currentSession = (Session) getEntityManager().getDelegate();
		boolean isSuccess=true;
		try {
			if(ownership.getId()==0)
			{
				ownership.setCreationTimestamp(new Date());
				ownership.setCreatedId(UserUtil.getCurrentResource().getUsername());
				
			}
			ownership.setLastUpdatedId(UserUtil.getCurrentResource().getUsername());
			currentSession.saveOrUpdate(ownership);
		} catch (HibernateException e) {
			isSuccess = false;
			logger.error("HibernateException occured in saveOrupdate method at DAO layer:-"+e);
			throw e;
		}
		return isSuccess;
	}

	public List<Ownership> findAll() {
		logger.info("------------ownershipDaoImpl findAll method end------------");
		List<Ownership> ownershipList = new ArrayList<Ownership>();
		Session session=(Session) getEntityManager().getDelegate();
		try {
			ownershipList = session.createQuery("FROM Ownership").list(); 
		} catch (HibernateException e) {
			logger.error("HibernateException occured in findAll method at DAO layer:-"+e);
			throw e; 
	      }
		return ownershipList ;
	}

	public List<Ownership> findByEntries(int firstResult, int sizeNo) {
		logger.info("------------ownershipDaoImpl findByEntries method end------------");
		// TODO Auto-generated method stub
		Session session=(Session) getEntityManager().getDelegate();
		List<Ownership> ownershipList = new ArrayList<Ownership>();
		try {
			ownershipList = session.createQuery("FROM Ownership").setFirstResult(firstResult).setMaxResults(sizeNo).list(); 
		} catch (HibernateException e) {
	         e.printStackTrace(); 
	         logger.error("HibernateException occured in findByEntries method at DAO layer:-"+e);
			 throw e;
	      }
		return ownershipList;
	}

	public long countTotal() {
		logger.info("------------ownershipDaoImpl countTotal method end------------");
		// TODO Auto-generated method stub
		Session session=(Session) getEntityManager().getDelegate();
		Long count=0L;
		try{
			session.createQuery("SELECT COUNT(o) FROM Ownership o").list(); 
			 count= (Long) session.createCriteria("Ownership").setProjection(Projections.rowCount()).uniqueResult();
		}
		catch (HibernateException e) {
	         e.printStackTrace(); 
	         logger.error("HibernateException occured in countTotal method at DAO layer:-"+e);
			 throw e;
	      }
		return count;
	}



	public Ownership findById(int id) {
		// TODO Auto-generated method stub
		Ownership ownership = null;
		logger.info("--------ActivityDaoImpl findById method start-------");
		Session session=(Session) getEntityManager().getDelegate();
		try {
			ownership = (Ownership)session.createCriteria(Ownership.class).add(Restrictions.eq("id", id)).uniqueResult(); 
		} catch (HibernateException e) {
	         e.printStackTrace(); 
	         logger.error("EXCEPTION CAUSED IN GETTING OWNERSHIP By Id " +id+ e.getMessage());
	         throw e;
	     }finally {
	         //session.close(); 
	      }
		logger.info("------OWNERSHIPDaoImpl findById method end-----");
		return ownership ;
	}
	}


