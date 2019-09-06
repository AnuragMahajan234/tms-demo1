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
import org.yash.rms.dao.ConfigurationDao;
import org.yash.rms.domain.ConfigurationCategory;

@Repository("configurationDao")
@Transactional
public class configurationDaoImpl implements ConfigurationDao {

	
	private static final Logger logger = LoggerFactory.getLogger(configurationDaoImpl.class);
	@PersistenceContext(name = "mysql", type = PersistenceContextType.TRANSACTION)
	private EntityManager entityManager;
	
	
	public EntityManager getEntityManager() {
		return entityManager;
	}
	
	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	public boolean delete(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean saveOrupdate(ConfigurationCategory t) {
		// TODO Auto-generated method stub
		return false;
	}

	public List<ConfigurationCategory> findAll() {
		// TODO Auto-generated method stub
		
		
		logger.info("--------DesignationDaoImpl findAll method start-------");
		List<ConfigurationCategory> configurationList = new ArrayList<ConfigurationCategory>();
		Session session=(Session) getEntityManager().getDelegate();
		try {
			configurationList = session.createCriteria(ConfigurationCategory.class).list(); 
		} catch (HibernateException e) {
			logger.error("Exception occured in findAll method at DAO layer:-"+e);
	         e.printStackTrace(); 
	     }finally {
	         //session.close(); 
	      }
		logger.info("------------RoleDaoImpl findAll method end------------");
		return configurationList ;
		 
	}

	public List<ConfigurationCategory> findByEntries(int firstResult, int sizeNo) {
		// TODO Auto-generated method stub
		return null;
	}

	public long countTotal() {
		// TODO Auto-generated method stub
		return 0;
	}

	public ConfigurationCategory findById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

}
