package org.yash.rms.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.yash.rms.dao.ShiftTypeDao;
import org.yash.rms.domain.ShiftTypes;

@Repository("ShiftTypeDao")
public class ShiftTypeDaoImpl implements ShiftTypeDao {

	@PersistenceContext(name = "mysql", type = PersistenceContextType.TRANSACTION)
	private EntityManager entityManager;
	
	
	public EntityManager getEntityManager() {
		return entityManager;
	}
	
	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	private static final Logger logger = LoggerFactory.getLogger(ShiftTypeDaoImpl.class);

	public List<ShiftTypes> getAllShiftTypes() {
		logger.info("-------getAllShiftTypes method start------------------");

		Session session = (Session) getEntityManager().getDelegate();
		Criteria criteria = session.createCriteria(ShiftTypes.class);
		List<ShiftTypes> shiftTypes = criteria.list();

		logger.info("-------getAllShiftTypes method end------------------");
		// TODO Auto-generated method stub
		return shiftTypes;
	}

	public ShiftTypes getShiftTypeById(Integer id) {
		logger.info("-------getShiftTypeById method start------------------");

		Session session = (Session) getEntityManager().getDelegate();
		Criteria criteria = session.createCriteria(ShiftTypes.class);
		criteria.add(Restrictions.eq("id", id));
		ShiftTypes result = (ShiftTypes) criteria.uniqueResult();

		logger.info("-------getShiftTypeById method end------------------");

		return result;
	}

}
