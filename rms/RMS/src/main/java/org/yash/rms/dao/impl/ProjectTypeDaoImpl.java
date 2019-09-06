package org.yash.rms.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.yash.rms.dao.ProjectTypeDao;
import org.yash.rms.domain.ProjectType;

@Repository("ProjectTypeDao")
public class ProjectTypeDaoImpl implements ProjectTypeDao {

	@PersistenceContext(name = "mysql", type = PersistenceContextType.TRANSACTION)
	private EntityManager entityManager;
	
	
	public EntityManager getEntityManager() {
		return entityManager;
	}
	
	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public List<ProjectType> getAllProjectTypes() {
		Session session = (Session) getEntityManager().getDelegate();
		Criteria criteria = session.createCriteria(ProjectType.class);
		List<ProjectType> list = criteria.list();
		
		return list;
	}

}
