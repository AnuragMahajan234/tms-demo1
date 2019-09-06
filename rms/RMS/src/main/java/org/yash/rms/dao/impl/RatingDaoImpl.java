package org.yash.rms.dao.impl;

import java.util.ArrayList;
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
import org.yash.rms.dao.RatingDao;
import org.yash.rms.domain.Rating;

@Repository("ratingDao")
@Transactional
public class RatingDaoImpl implements RatingDao{
	@PersistenceContext(name = "mysql", type = PersistenceContextType.TRANSACTION)
	private EntityManager entityManager;
	
	
	public EntityManager getEntityManager() {
		return entityManager;
	}
	
	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	private static final Logger logger = LoggerFactory.getLogger(RatingDaoImpl.class);
	public List<Rating> findAllRatings() {
		logger.info("----------RatingDaoImpl findAllRatings method start--------");
		List<Rating> ratingList = new ArrayList<Rating>();
		try {
			Session session = (Session) getEntityManager().getDelegate();
			ratingList = session.createCriteria(Rating.class).list();
		} catch (HibernateException e) {
			logger.error("Hibernate Exception in findAllRatings at RatingDaoImpl DAO layer"+e);
			throw e;
		}
		logger.info("----------RatingDaoImpl findAllRatings method end--------");
		return ratingList;
	}

}
