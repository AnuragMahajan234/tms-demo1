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
import org.yash.rms.dao.ExperienceDao;
import org.yash.rms.domain.Experience;

@Repository("ExperienceDao")
public class ExperienceDaoImpl implements ExperienceDao {


@PersistenceContext(name = "mysql", type = PersistenceContextType.TRANSACTION)
	private EntityManager entityManager;
	
	
	public EntityManager getEntityManager() {
		return entityManager;
	}
	
	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	private static final Logger logger = LoggerFactory.getLogger(ExperienceDaoImpl.class);
	
	public List<Experience> getAllExperience() {
		logger.info("-------getAllExperience method start------------------");
		
		Session session = (Session) getEntityManager().getDelegate();
		Criteria criteria = session.createCriteria(Experience.class);
		List<Experience> experiences = criteria.list();
		
		logger.info("-------getAllExperience method end------------------");
		getExperienceById(experiences.get(0).getId());
		return experiences;
	}

	public Experience getExperienceById(Integer id) {
		logger.info("-------getExperienceById method start------------------");
		
		Session session = (Session) getEntityManager().getDelegate();
		Criteria criteria = session.createCriteria(Experience.class);
		criteria.add(Restrictions.eq("id", id));
		Experience result = (Experience) criteria.uniqueResult();
		
		logger.info("-------getExperienceById method end------------------");
		
		return result;
	}

}
