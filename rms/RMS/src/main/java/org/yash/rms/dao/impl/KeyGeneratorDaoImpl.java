package org.yash.rms.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.yash.rms.dao.KeyGeneratorDao;
import org.yash.rms.domain.KeyGenerator;
import org.yash.rms.exception.DAOException;
import org.yash.rms.util.Constants;

import javassist.NotFoundException;

@Repository("keyGeneratorDao")
@Component
public class KeyGeneratorDaoImpl implements KeyGeneratorDao {

	private static final Logger logger = LoggerFactory.getLogger(KeyGeneratorDaoImpl.class);

@PersistenceContext(name = "mysql", type = PersistenceContextType.TRANSACTION)
	private EntityManager entityManager;
	
	
	public EntityManager getEntityManager() {
		return entityManager;
	}
	
	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public KeyGenerator findById(final int id) {
		logger.info("--------KeyGeneratorDaoImpl findById method Start-------");
		Session session = (Session) getEntityManager().getDelegate();
		KeyGenerator keyGenerator = null;
		if (id <= 0)
			throw new IllegalArgumentException("Invalid Id found");
		try {
			keyGenerator = (KeyGenerator) session.get(KeyGenerator.class, new Integer(id));
		} catch (HibernateException e) {
			logger.error(String.format("Exception occured while getting KeyGenerator By Id=%d is ===> %s", id,
					e.getMessage()));
			e.getMessage();
			e.printStackTrace();
			throw e;
		}
		logger.info("--------KeyGeneratorDaoImpl findById method End-------");
		return keyGenerator;
	}

	public int save(KeyGenerator keyGenerator) throws NotFoundException {
		logger.info("--------KeyGeneratorDaoImpl Save method Start-------");
		Session session = (Session) getEntityManager().getDelegate();
		int id = 0;
		if (keyGenerator == null)
			throw new DAOException("404","KeyGenerator Object data not found");
		try {
			id = (Integer) session.save(keyGenerator);
		} catch (HibernateException e) {
			logger.error(String.format("Exception occured while saving KeyGenerator is ====> %s", e.getMessage()));
			e.getMessage();
			e.printStackTrace();
			throw e;
		}
		logger.info("--------KeyGeneratorDaoImpl Save method End-------");

		return id;
	}

	public KeyGenerator findKeyByStatusType(final String type, final String status) {
		logger.info("--------KeyGeneratorDaoImpl findKeyByStatusType method Start-------");
		Session session = (Session) getEntityManager().getDelegate();
		KeyGenerator keyGenerator = null;
		// to find the unassigned key for RRF, here type=RRF and status=unassigned should be.
		try {
			Criteria criteria = session.createCriteria(KeyGenerator.class);
			criteria.add(Restrictions.eq("status", status)).add(Restrictions.eq("type", type));
			keyGenerator = (KeyGenerator) criteria.addOrder(Order.asc("id")).setMaxResults(1).uniqueResult();
		} catch (HibernateException e) {
			logger.error("Exception occured while getting KeyGenerator");
			e.getMessage();
			e.printStackTrace();
			throw e;
		}
		logger.info("--------KeyGeneratorDaoImpl findKeyByStatusType method End-------");
		return keyGenerator;
	}

	public boolean saveOrupdate(KeyGenerator keyGenerator) {
		logger.info("--------KeyGeneratorDaoImpl saveOrupdate method Start-------");
		Session session = (Session) getEntityManager().getDelegate();
		boolean isSuccess = true;
		try {
			session.saveOrUpdate(keyGenerator);
		} catch (HibernateException e) {
			isSuccess = false;
			logger.error("Exception occured in saveOrupdate method at DAO layer:-" + e);
			e.getMessage();
			e.printStackTrace();
		}
		logger.info("--------ResourceDaoImpl copyUserProfileToResource method end-------");
		return isSuccess;
	}

	public boolean delete(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	public List<KeyGenerator> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<KeyGenerator> findByEntries(int firstResult, int sizeNo) {
		// TODO Auto-generated method stub
		return null;
	}

	public long countTotal() {
		// TODO Auto-generated method stub
		return 0;
	}

	public Long findMaxAssginedValue() {
		logger.info("--------KeyGeneratorDaoImpl GenerateToken findMaxAssginedValue method Start-------");
		Session session = (Session) getEntityManager().getDelegate();
		try {
			Criteria criteria = session.createCriteria(KeyGenerator.class);
			criteria.add(Restrictions.eq("status", Constants.ASSIGNED));
			criteria.add(Restrictions.eq("type", "RRF"));
			criteria.setProjection(Projections.max("value"));
			return (Long) criteria.uniqueResult();
		} catch (HibernateException e) {
			logger.error(String.format("Exception occured while saving KeyGenerator is ====> %s", e.getMessage()));
			e.getMessage();
			e.printStackTrace();
			throw e;
		}
		finally{
			logger.info("--------KeyGeneratorDaoImpl GenerateToken findMaxAssginedValue method Start-------");
		}
	}

	public void generateToken(String type, String status, Long minValue, Long MaxValue) {
		logger.info("--------KeyGeneratorDaoImpl GenerateToken findMaxAssginedValue method End-------");
		Session session = (Session) getEntityManager().getDelegate();
		try{
			
			Query query = session.createSQLQuery("CALL SP_Generate_Key_Token(:key_type,:key_status,:minimum_value,:maximum_value)")
					  .addEntity(KeyGenerator.class);
			query.setParameter("key_type", type);
			query.setParameter("key_status", status);
			query.setParameter("minimum_value", minValue);
			query.setParameter("maximum_value", MaxValue);
			query.executeUpdate();
		}
		 catch (HibernateException e) {
				logger.error(String.format("Exception occured while saving KeyGenerator is ====> %s", e.getMessage()));
				e.getMessage();
				e.printStackTrace();
				throw e;
			}
			finally{
				logger.info("--------KeyGeneratorDaoImpl GenerateToken findMaxAssginedValue method End-------");
		}
	}

}
