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
import org.yash.rms.domain.InvoiceBy;
import org.yash.rms.util.UserUtil;

/**
 * @author arpan.badjatiya
 *
 */
@Repository("InvoiceByDao")
@Transactional
public class InvoiceByDaoImpl implements RmsCRUDDAO<InvoiceBy> {
	private static final Logger logger = LoggerFactory.getLogger(InvoiceByDaoImpl.class);

@PersistenceContext(name = "mysql", type = PersistenceContextType.TRANSACTION)
	private EntityManager entityManager;
	
	
	public EntityManager getEntityManager() {
		return entityManager;
	}
	
	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public boolean saveOrupdate(InvoiceBy invoice) {
		logger.info("------InvoiceByDaoImpl saveOrUpdate method start------");
		if(null == invoice) return false;
		
		Session currentSession = (Session) getEntityManager().getDelegate();
		boolean isSuccess=true;
		try {
			if(invoice.getId()==0)
			{
				
				invoice.setCreatedId( UserUtil.getUserContextDetails().getUserName());
				invoice.setCreationTimestamp(new Date());
				
			}
			
			invoice.setLastUpdatedId( UserUtil.getUserContextDetails().getUserName());
		
			isSuccess = currentSession.merge(invoice)!=null?true:false;
		} catch (HibernateException e) {
			isSuccess = false;
			logger.error("HibernateException occured in saveOrupdate method at DAO layer:-"+e);
			e.printStackTrace();
		} catch (Exception ex) {
			logger.error("HibernateException occured in saveOrupdate method at DAO layer:-"+ex);
			isSuccess = false;
			ex.printStackTrace();
		} finally {
//			currentSession.close();
		}
		return isSuccess;
	}
	

	public boolean delete(int id) {
		logger.info("------InvoiceByDaoImpl delete method start------");
		boolean isSuccess=true;
		try {
			 Query query = ((Session) getEntityManager().getDelegate()).getNamedQuery(InvoiceBy.DELETE_INVOICE_BY_BASED_ON_ID).setInteger("id", id);
			 int totalRowsDeleted = query.executeUpdate();
			System.out.println("Total Rows Deleted::" + totalRowsDeleted);
		} catch (HibernateException e) {
			logger.error("HibernateException occured in delete method at DAO layer:-"+e);
			isSuccess = false;
			e.printStackTrace();
		} catch (Exception ex) {
			logger.error("HibernateException occured in delete method at DAO layer:-"+ex);
			isSuccess = false;
			ex.printStackTrace();
		} finally {
//			currentSession.close();
		}
		logger.info("------InvoiceByDaoImpl delete method end------");
		return isSuccess;
	}

	@SuppressWarnings("unchecked")
	public List<InvoiceBy> findAll() {
		logger.info("--------InvoiceByDaoImpl findAll method start-------");

		List<InvoiceBy> invoiceByList = new ArrayList<InvoiceBy>();
		Session session=(Session) getEntityManager().getDelegate();
		try {
			invoiceByList = session.createQuery("FROM InvoiceBy").list(); 
			for (InvoiceBy scale : invoiceByList) {
				System.out.println("InvoiceBy name :"+scale.getName());
			}
		} catch (HibernateException e) {
			logger.info("--------InvoiceByDaoImpl findAll method start-------");

	         e.printStackTrace(); 
	     }finally {
	         //session.close(); 
	      }
		logger.info("------------InvoiceByDaoImpl findAll method end------------");

		return invoiceByList ;
	}

	public List<InvoiceBy> findByEntries(int firstResult, int sizeNo) {
		return null;
	}

	public long countTotal() {
		return 0;
	}
	
	

}
