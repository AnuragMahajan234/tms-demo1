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
import org.yash.rms.domain.Currency;
import org.yash.rms.util.UserUtil;

/**
 * @author arpan.badjatiya
 *
 */
@Repository("CurrencyDao")
@Transactional
public class CurrencyDaoImpl implements RmsCRUDDAO<Currency> {
	
	@PersistenceContext(name = "mysql", type = PersistenceContextType.TRANSACTION)
	private EntityManager entityManager;
	
	
	public EntityManager getEntityManager() {
		return entityManager;
	}
	
	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	private static final Logger logger = LoggerFactory.getLogger(CurrencyDaoImpl.class);

	public boolean saveOrupdate(Currency currency) {
		logger.info("--------CurrencyDaoImpl saveOrUpdate method start-------");
		if(null == currency) return false;
		if(currency.getId()==0)
		{
			
			currency.setCreatedId( UserUtil.getUserContextDetails().getUserName());
			currency.setCreationTimestamp(new Date());
			
		}
		
		currency.setLastUpdatedId( UserUtil.getUserContextDetails().getUserName());
	
		Session currentSession = (Session) getEntityManager().getDelegate();
		boolean isSuccess=true;
		try {
			isSuccess = currentSession.merge(currency)!=null?true:false;
		} catch (HibernateException e) {
			isSuccess = false;
			e.printStackTrace();
			logger.error("Exception Occurred while saving Currency "+ currency.getCurrencyName()+e.getMessage());
			throw e;
		}  finally {
//			currentSession.close();
		}
		logger.info("--------CurrencyDaoImpl saveOrUpdate method end-------");

		return isSuccess;
	}
	

	public boolean delete(int id) {
		logger.info("------------CurrencyDaoImpl delete method start------------");
		boolean isSuccess=true;
		try {
			 Query query = ((Session) getEntityManager().getDelegate()).getNamedQuery(Currency.DELETE_CURRENCY_BASED_ON_ID).setInteger("id", id);
			 int totalRowsDeleted = query.executeUpdate();
			System.out.println("Total Rows Deleted::" + totalRowsDeleted);
		} catch (HibernateException e) {
			isSuccess = false;
			e.printStackTrace();
			logger.error("Exception Occurred while deleting Currency "+id+e.getMessage());
			throw e;
		}   finally {
//			currentSession.close();
		}
		logger.info("------------CurrencyDaoImpl delete method end------------");
		return isSuccess;
		
	}
	
	public boolean create(Currency invoice) {
		if(null == invoice) return false;
		logger.info("--------CurrencyDaoImpl create method start-------");
		Session currentSession = (Session) getEntityManager().getDelegate();
		boolean isSuccess=true;
		try {
			 if (invoice.getCreationTimestamp() == null) {
				 invoice.setCreationTimestamp(new Date());
				   }
			currentSession.saveOrUpdate(invoice);
		} catch (HibernateException e) {
			isSuccess = false;
			e.printStackTrace();
			logger.error("Exception Occurred while saving Currency "+ invoice.getCurrencyName()+e.getMessage());
			throw e;
		}  finally {
//			currentSession.close();
		}
		logger.info("--------CurrencyDaoImpl create method end-------");
		return isSuccess;
	}

	@SuppressWarnings("unchecked")
	public List<Currency> findAll() {
		logger.info("--------CurrencyDaoImpl findAll method start-------");
		List<Currency> currencyList = new ArrayList<Currency>();
		Session session=(Session) getEntityManager().getDelegate();
		try {
			currencyList = session.createQuery("FROM Currency").list(); 
		} catch (HibernateException e) {
	         e.printStackTrace(); 
	         logger.error("EXCEPTION CAUSED IN GETTING CURRENCY LIST " + e.getMessage());
	         throw e;
	     }finally {
	         //session.close(); 
	      }
		return currencyList ;
	}

	public List<Currency> findByEntries(int firstResult, int sizeNo) {
		return null;
	}




	public long countTotal() {
		// TODO Auto-generated method stub
		return 0;
	}
}
