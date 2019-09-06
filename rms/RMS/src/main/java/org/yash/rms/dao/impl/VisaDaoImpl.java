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
import org.hibernate.criterion.Projections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.yash.rms.dao.RmsCRUDDAO;
import org.yash.rms.domain.Visa;
import org.yash.rms.util.UserUtil;

@Repository("VisaDao")
@Transactional
public class VisaDaoImpl implements RmsCRUDDAO<Visa>{
	

@PersistenceContext(name = "mysql", type = PersistenceContextType.TRANSACTION)
	private EntityManager entityManager;
	
	
	public EntityManager getEntityManager() {
		return entityManager;
	}
	
	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	private static final Logger logger = LoggerFactory.getLogger(VisaDaoImpl.class);
	public boolean delete(int id) {
		logger.info("------------VisaDaoImpl delete method start------------");
		boolean isSuccess=true;
		try {
			 Query query = ((Session) getEntityManager().getDelegate()).getNamedQuery(Visa.DELETE_VISA_BASED_ON_ID).setInteger("id", id);
			 int totalRowsDeleted = query.executeUpdate();
			System.out.println("Total Rows Deleted::" + totalRowsDeleted);
		} catch (HibernateException e) {
			isSuccess = false;
			logger.error("HibernateException occured in delete method at DAO layer:-"+e);
			throw e;
		}  
		logger.info("------------VisaDaoImpl delete method end------------");
		return isSuccess;
	}

	public boolean saveOrupdate(Visa visa) {
		logger.info("------------VisaDaoImpl saveOrupdate method start------------");
		if(null == visa) return false;
		
		Session currentSession = (Session) getEntityManager().getDelegate();
		boolean isSuccess=true;
		try { 
			if(visa.getId()==0)
			{
				visa.setCreationTimestamp(new Date());
				visa.setCreatedId(UserUtil.getCurrentResource().getUsername());
				
			}
			visa.setLastUpdatedId(UserUtil.getCurrentResource().getUsername());
			currentSession.saveOrUpdate(visa);
		} catch (HibernateException e) {
			isSuccess = false;
			logger.error("HibernateException occured in saveOrupdate method at DAO layer:-"+e);
			throw e;
		}  
		logger.info("------------VisaDaoImpl saveOrupdate method end------------");
		return isSuccess;
	}

	public List<Visa> findAll() {
		logger.info("------------VisaDaoImpl findAll method start------------");
		List<Visa> visaList = new ArrayList<Visa>();
		Session session=(Session) getEntityManager().getDelegate();
		try {
			visaList = session.createQuery("FROM Visa").list(); 
		} catch (HibernateException e) {
			 
			logger.error("HibernateException occured in findAll method at DAO layer:-"+e);
			throw e;
		}  
		logger.info("------------VisaDaoImpl findAll method end------------");
		return visaList ;
	}

	public List<Visa> findByEntries(int firstResult, int sizeNo) {
		logger.info("------------VisaDaoImpl findByEntries method start------------");
		// TODO Auto-generated method stub
		Session session=(Session) getEntityManager().getDelegate();
		List<Visa> visaList = new ArrayList<Visa>();
		try {
			visaList = session.createQuery("FROM Visa").setFirstResult(firstResult).setMaxResults(sizeNo).list(); 
		} catch (HibernateException e) {
			 
			logger.error("HibernateException occured in findByEntries method at DAO layer:-"+e);
			throw e;
		}  
		logger.info("------------VisaDaoImpl findByEntries method end------------");
		return visaList;
	}

	public long countTotal() {
		logger.info("------------VisaDaoImpl countTotal method start------------");
		// TODO Auto-generated method stub
		Session session=(Session) getEntityManager().getDelegate();
		Long count=0L;
		try{
			session.createQuery("SELECT COUNT(v) FROM Visa v").list(); 
			 count= (Long) session.createCriteria("Visa").setProjection(Projections.rowCount()).uniqueResult();
		}
		catch (HibernateException e) {
			 
			logger.error("HibernateException occured in countTotal method at DAO layer:-"+e);
			throw e;
		}  
		logger.info("------------VisaDaoImpl countTotal method end------------");
		return count;
	}



}
