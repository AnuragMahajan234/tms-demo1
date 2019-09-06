/**
 * 
 */
package org.yash.rms.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.yash.rms.dao.RmsCRUDDAO;
import org.yash.rms.domain.ReleaseNotes;

/**
 * @author deepti.gupta
 *
 */
@Repository("ReleaseNoteDao")
@Transactional
public class ReleaseNotesDaoImpl implements RmsCRUDDAO<ReleaseNotes> {
	
	@PersistenceContext(name = "mysql", type = PersistenceContextType.TRANSACTION)
	private EntityManager entityManager;
	
	
	public EntityManager getEntityManager() {
		return entityManager;
	}
	
	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	private static final Logger logger = LoggerFactory.getLogger(ReleaseNotesDaoImpl.class);

	public boolean saveOrupdate(ReleaseNotes releaseNote) {
		logger.info("--------CurrencyDaoImpl saveOrUpdate method start-------");
		if(null == releaseNote) return false;
	 
		
	 
	
		Session currentSession = (Session) getEntityManager().getDelegate();
		boolean isSuccess=true;
		try {
			isSuccess = currentSession.merge(releaseNote)!=null?true:false;
		} catch (HibernateException e) {
			isSuccess = false;
			e.printStackTrace();
			logger.error("Exception Occurred while saving Currency "+ releaseNote.getReleaseNumber()+e.getMessage());
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
		/*try {
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
		;*/
		return isSuccess;
		
	}
	
	public boolean create(ReleaseNotes releaseNote) {
		if(null == releaseNote) return false;
		logger.info("--------CurrencyDaoImpl create method start-------");
		Session currentSession = (Session) getEntityManager().getDelegate();
		boolean isSuccess=true;
		try {
			  
			currentSession.saveOrUpdate(releaseNote);
		} catch (HibernateException e) {
			isSuccess = false;
			e.printStackTrace();
			logger.error("Exception Occurred while saving Currency "+ releaseNote.getReleaseNumber()+e.getMessage());
			throw e;
		}  finally {
//			currentSession.close();
		}
		logger.info("--------CurrencyDaoImpl create method end-------");
		return isSuccess;
	}

	@SuppressWarnings("unchecked")
	public List<ReleaseNotes> findAll() {
		logger.info("--------CurrencyDaoImpl findAll method start-------");
		List<ReleaseNotes> releaseNotesList = new ArrayList<ReleaseNotes>();
		Session session=(Session) getEntityManager().getDelegate();
		try {
			releaseNotesList = session.createQuery("FROM ReleaseNotes ORDER BY releaseDate DESC,type DESC").list(); 
			 
		} catch (HibernateException e) {
	         e.printStackTrace(); 
	         logger.error("EXCEPTION CAUSED IN GETTING CURRENCY LIST " + e.getMessage());
	         throw e;
	     }finally {
	         //session.close(); 
	      }
		return releaseNotesList ;
	}

	public List<ReleaseNotes> findByEntries(int firstResult, int sizeNo) {
		return null;
	}




	public long countTotal() {
		// TODO Auto-generated method stub
		return 0;
	}
}
