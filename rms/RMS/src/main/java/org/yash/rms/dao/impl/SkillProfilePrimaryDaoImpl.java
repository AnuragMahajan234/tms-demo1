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
import org.yash.rms.dao.SkillProfilePrimaryDao;
import org.yash.rms.domain.SkillProfilePrimary;

@Repository("skillProfilePrimaryDao")
@Transactional
public class SkillProfilePrimaryDaoImpl implements SkillProfilePrimaryDao {

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
			.getLogger(SkillProfilePrimaryDaoImpl.class);
	
	public boolean delete(int id) {
		logger.info("--------SkillProfilePrimaryDaoImpl delete method start-------");
		boolean isSuccess = true;
		try {
			Query query = ((Session) getEntityManager().getDelegate()).getNamedQuery(
					SkillProfilePrimary.DELETE_SKILLPRIMARYPROFILE_BASED_ON_ID)
					.setInteger("id", id);
			int totalRowsDeleted = query.executeUpdate();
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
		logger.info("--------SkillProfilePrimaryDaoImpl delete method end-------");
		return isSuccess;
	}

	public boolean saveOrupdate(SkillProfilePrimary skillProfilePrimary) {
		logger.info("--------SkillProfilePrimaryDaoImpl saveOrupdate method start-------");
		if (null == skillProfilePrimary)
			return false;

		boolean isSuccess = true;
		try {
			 ((Session) getEntityManager().getDelegate()).saveOrUpdate(skillProfilePrimary);
		}catch (HibernateException e) {
			isSuccess = false;
			logger.error("HibernateException occured in saveOrUpdate method at DAO layer:-"
					+ e);
		} catch (Exception ex) {
			isSuccess = false;
			ex.printStackTrace();
		} finally {
			// currentSession.close();
		}
		logger.info("--------SkillProfilePrimaryDaoImpl saveOrupdate method end-------");
		return isSuccess;
	}

	public List<SkillProfilePrimary> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<SkillProfilePrimary> findByEntries(int firstResult, int sizeNo) {
		// TODO Auto-generated method stub
		return null;
	}

	public long countTotal() {
		// TODO Auto-generated method stub
		return 0;
	}

	
	
}
