package org.yash.rms.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.yash.rms.dao.SkillProfileSecondaryDao;
import org.yash.rms.domain.SkillProfileSecondary;
import org.yash.rms.util.HibernateUtil;

@Repository("skillProfileSecondaryDao")
@Transactional
public class SkillProfileSecondaryDaoImpl implements SkillProfileSecondaryDao {

	@PersistenceContext(name = "mysql", type = PersistenceContextType.TRANSACTION)
	private EntityManager entityManager;
	
	
	public EntityManager getEntityManager() {
		return entityManager;
	}
	
	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	private static final Logger logger = LoggerFactory
			.getLogger(SkillProfileSecondaryDaoImpl.class);

	public boolean delete(int id) {
		logger.info("--------SkillProfileSecondaryDaoImpl delete method start-------");
		boolean isSuccess = true;
		try {
			Query query = ((Session) getEntityManager().getDelegate())
					.getNamedQuery(
							SkillProfileSecondary.DELETE_SKILLPROFILESECONDARY_BASED_ON_ID)
					.setInteger("id", id);
			int totalRowsDeleted = query.executeUpdate();
			System.out.println("Total Rows Deleted::" + totalRowsDeleted);
		} catch (HibernateException e) {
			isSuccess = false;
			logger.error("HibernateException occured in delete method at DAO layer:-"
					+ e);
		} catch (Exception ex) {
			isSuccess = false;
			ex.printStackTrace();
		} finally {
			// currentSession.close();
		}
		logger.info("--------SkillProfileSecondaryDaoImpl delete method end-------");
		return isSuccess;
	}

	public boolean saveOrupdate(SkillProfileSecondary skillProfileSecondary) {
		logger.info("--------SkillProfileSecondaryDaoImpl saveOrupdate method start-------");
		if (null == skillProfileSecondary)
			return false;

		boolean isSuccess = true;
		try {
			((Session) getEntityManager().getDelegate()).saveOrUpdate(
					skillProfileSecondary);
		} catch (HibernateException e) {
			isSuccess = false;
			logger.error("HibernateException occured in delete method at DAO layer:-"
					+ e);
		} catch (Exception ex) {
			isSuccess = false;
			ex.printStackTrace();
		} finally {
			// currentSession.close();
		}

		logger.info("--------SkillProfileSecondaryDaoImpl saveOrupdate method end-------");
		return isSuccess;
	}

	public List<SkillProfileSecondary> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<SkillProfileSecondary> findByEntries(int firstResult, int sizeNo) {
		// TODO Auto-generated method stub
		return null;
	}

	public long countTotal() {
		// TODO Auto-generated method stub
		return 0;
	}

}
