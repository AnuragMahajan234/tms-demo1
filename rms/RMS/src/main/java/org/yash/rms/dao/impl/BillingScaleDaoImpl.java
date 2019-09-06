/**
 * 
 */
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.yash.rms.dao.RmsCRUDDAO;
import org.yash.rms.domain.BillingScale;
import org.yash.rms.dto.BillingScaleDTO;
import org.yash.rms.util.UserUtil;

/**
 * @author arpan.badjatiya
 *
 */
@Repository("BillingScaleDao")
@Transactional
public class BillingScaleDaoImpl implements RmsCRUDDAO<BillingScale> {
	
	@PersistenceContext(name = "mysql", type = PersistenceContextType.TRANSACTION)
	private EntityManager entityManager;
	
	
	public EntityManager getEntityManager() {
		return entityManager;
	}
	
	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	private static final Logger logger = LoggerFactory.getLogger(BillingScaleDaoImpl.class);
	
	@SuppressWarnings("unchecked")
	public List<BillingScale> findAll() {
		logger.info("------------BillingScaleDaoImpl findAll method end------------");
		List<BillingScale> billingScaleList = new ArrayList<BillingScale>();
		Session session=(Session) getEntityManager().getDelegate();
		try {
			billingScaleList = session.createQuery("FROM BillingScale").list(); 
		} catch (HibernateException e) {
		    logger.error("HibernateException occured in findAll method at DAO layer:-"+e);
			throw e;
		}  
		logger.info("------------BillingScaleDaoImpl findAll method end------------");
		return billingScaleList ;
	}

	public List<BillingScale> findByEntries(int firstResult,int sizeNo) {
		logger.info("------------BillingScaleDaoImpl findByEntries method end------------");
		List<BillingScale> billingScales = new ArrayList<BillingScale>();
		try{
		
		billingScales =((Session) getEntityManager().getDelegate()).createQuery("SELECT o FROM BillingScale o").setFirstResult(firstResult).setMaxResults(sizeNo).list();
		}
		catch (HibernateException e) {
		    logger.error("HibernateException occured in findByEntries method at DAO layer:-"+e);
			throw e;
		}  
		logger.info("------------BillingScaleDaoImpl findByEntries method end------------");
		
		return billingScales;
	}

	public long countTotal() {
		return 0;
	}


	public boolean saveOrupdate(BillingScale billingScale) {
		logger.info("------------BillingScaleDaoImpl saveOrupdate method end------------");
		if(null == billingScale) return false;
		
		Session currentSession = (Session) getEntityManager().getDelegate();
		boolean isSuccess=true;
		try {
			if(billingScale.getId()==0)
			{
				billingScale.setCreationTimestamp(new Date());
				billingScale.setCreatedId(UserUtil.getCurrentResource().getUsername());
				
			}
			billingScale.setLastUpdatedId(UserUtil.getCurrentResource().getUsername());
			isSuccess = currentSession.merge(billingScale)!=null?true:false;
		} catch (HibernateException e) {
			isSuccess = false;
			logger.error("HibernateException occured in saveOrupdate method at DAO layer:-"+e);
			throw e;
		}  
		logger.info("------------BillingScaleDaoImpl saveOrupdate method end------------");
		return isSuccess;
	}
	

	public boolean delete(int id) {
		logger.info("------------BillingScaleDaoImpl delete method end------------");
		boolean isSuccess=true;
		try {
			 Query query = ((Session) getEntityManager().getDelegate()).getNamedQuery(BillingScale.DELETE_BILLING_SCALE_BASED_ON_ID).setInteger("id", id);
			 int totalRowsDeleted = query.executeUpdate();
			System.out.println("Total Rows Deleted::" + totalRowsDeleted);
		}catch (HibernateException e) {
			isSuccess = false;
			logger.error("HibernateException occured in delete method at DAO layer:-"+e);
			throw e;
		}  
		logger.info("------------BillingScaleDaoImpl delete method end------------");
		return isSuccess;
	}
	
	public boolean create(BillingScale billingScale) {
		logger.info("------------BillingScaleDaoImpl create method end------------");
		if(null == billingScale) return false;
		
		Session currentSession = (Session) getEntityManager().getDelegate();
		boolean isSuccess=true;
		try {
			currentSession.saveOrUpdate(billingScale);
		} catch (HibernateException e) {
			isSuccess = false;
			logger.error("HibernateException occured in create method at DAO layer:-"+e);
			throw e;
		}  
		logger.info("------------BillingScaleDaoImpl create method end------------");
		return isSuccess;
	}

	public boolean saveOrupdate(BillingScaleDTO t) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
