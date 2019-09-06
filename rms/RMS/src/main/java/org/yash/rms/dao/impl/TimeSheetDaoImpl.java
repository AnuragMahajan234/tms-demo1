/**
 * 
 */
package org.yash.rms.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.yash.rms.dao.TimeSheetDao;
import org.yash.rms.form.TimeSheet;

/**
 * @author arpan.badjatiya
 *
 */
@Repository("TimeSheetDao")
@Transactional
public class TimeSheetDaoImpl implements TimeSheetDao {
	
	@PersistenceContext(name = "mysql", type = PersistenceContextType.TRANSACTION)
	private EntityManager entityManager;
	
	
	public EntityManager getEntityManager() {
		return entityManager;
	}
	
	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	private static final Logger logger = LoggerFactory.getLogger(TimeSheetDaoImpl.class);
	
	public boolean delete(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean saveOrupdate(TimeSheet timeSheet) {
		logger.info("-------TimeSheetDaoImpl saveOrUpdate method start -------- ");
		if(null == timeSheet) return false;
		
		Session currentSession = (Session) getEntityManager().getDelegate();
		boolean isSuccess=true;
		try {
			
			currentSession.saveOrUpdate(timeSheet);
		} catch (HibernateException e) {
			isSuccess = false;
			logger.error("Error occured in saveOrUpdate method:"+e);
		} catch (Exception ex) {
			isSuccess = false;
			ex.printStackTrace();
		} finally {
//			currentSession.close();
		}
		logger.info("-------TimeSheetDaoImpl saveOrUpdate method end -------- ");
		return isSuccess;
	}

	public List<TimeSheet> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<TimeSheet> findByEntries(int firstResult, int sizeNo) {
		// TODO Auto-generated method stub
		return null;
	}

	public long countTotal() {
		return 0;
	}

}
