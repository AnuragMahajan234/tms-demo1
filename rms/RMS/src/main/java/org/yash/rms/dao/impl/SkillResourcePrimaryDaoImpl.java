package org.yash.rms.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.yash.rms.dao.SkillResourcePrimaryDao;
import org.yash.rms.domain.SkillResourcePrimary;
import org.yash.rms.util.HibernateUtil;

@Repository("skillResourcePrimary")
@Transactional
public class SkillResourcePrimaryDaoImpl implements SkillResourcePrimaryDao {
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
			.getLogger(SkillResourcePrimaryDaoImpl.class);

	public boolean delete(int id) {
		logger.info("--------SkillResourcePrimaryDaoImpl delete method start-------");
		boolean isSuccess = true;
		try {
			Query query = ((Session) getEntityManager().getDelegate())
					.getNamedQuery(
							SkillResourcePrimary.DELETE_SKILLRESOURCEPRIMARY_BASED_ON_ID)
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
		logger.info("--------SkillResourcePrimaryDaoImpl delete method end-------");
		return isSuccess;
	}

	public boolean saveOrupdate(SkillResourcePrimary t) {
		// TODO Auto-generated method stub
		return false;
	}

	public List<SkillResourcePrimary> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<SkillResourcePrimary> findByEntries(int firstResult, int sizeNo) {
		// TODO Auto-generated method stub
		return null;
	}

	public long countTotal() {
		// TODO Auto-generated method stub
		return 0;
	}

	public List<SkillResourcePrimary> findSkillResourcesByResourceId(int resourcId) {
		logger.info("--------FindSkillResourcesByResourceId delete method start-------");
		List<SkillResourcePrimary> list = null;
		try {
		Session session =  (Session) getEntityManager().getDelegate();
		 if(session != null) {
			Criteria criteria = (Criteria) session.createCriteria(SkillResourcePrimary.class);
			criteria.add(Restrictions.eq("resourceId.employeeId", resourcId));
			list = criteria.list();
		}
		}catch (Exception e) {
			logger.error("HibernateException occured in findSkillResourcesByResourceId method at DAO layer:-"
					+ e);
		}
		return list;
	}
	
	public SkillResourcePrimary findById(int id) {
		org.hibernate.Session session =  (Session) getEntityManager().getDelegate();
		return (SkillResourcePrimary) session.get(SkillResourcePrimary.class, id);
		}
}
