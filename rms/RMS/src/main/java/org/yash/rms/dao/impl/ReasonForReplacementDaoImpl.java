package org.yash.rms.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.yash.rms.dao.ReasonForReplacementDao;
import org.yash.rms.domain.ReasonForReplacement;

@Repository
public class ReasonForReplacementDaoImpl implements ReasonForReplacementDao {
	
	private static final Logger logger = LoggerFactory.getLogger(ReasonForReplacement.class);
	
	@PersistenceContext(name = "mysql", type = PersistenceContextType.TRANSACTION)
	private EntityManager entityManager;
	
	
	public EntityManager getEntityManager() {
		return entityManager;
	}
	
	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	public List<ReasonForReplacement> getAllReasons() {
		logger.info("---------------getAllReasons method start------------");
		Session  session = (Session) getEntityManager().getDelegate();
			Criteria criteria = session.createCriteria(ReasonForReplacement.class);
				List<ReasonForReplacement> reasons = criteria.list();
	    logger.info("---------------getAllReasons method end--------------");
		
	    return reasons;
	}

}
