package org.yash.rms.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.yash.rms.dao.RoleDao;
import org.yash.rms.domain.Roles;

@Repository("RoleDao")
@Transactional
public class RoleDaoImpl implements RoleDao {

	@PersistenceContext(name = "mysql", type = PersistenceContextType.TRANSACTION)
	private EntityManager entityManager;
	
	
	public EntityManager getEntityManager() {
		return entityManager;
	}
	
	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	private static final Logger logger = LoggerFactory.getLogger(RoleDaoImpl.class);
	
	public boolean delete(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean saveOrupdate(Roles t) {
		// TODO Auto-generated method stub
		return false;
	}

	public List<Roles> findAll() {
		// TODO Auto-generated method stub
		
		
		logger.info("--------DesignationDaoImpl findAll method start-------");
		List<Roles> rolesList = new ArrayList<Roles>();
		Session session=(Session) getEntityManager().getDelegate();
		try {
			rolesList = session.createCriteria(Roles.class).list(); 
		} catch (HibernateException e) {
			logger.error("Exception occured in findAll method at DAO layer:-"+e);
	         e.printStackTrace(); 
	     }finally {
	         //session.close(); 
	      }
		logger.info("------------RoleDaoImpl findAll method end------------");
		return rolesList ;
		 
	}

	public List<Roles> findByEntries(int firstResult, int sizeNo) {
		// TODO Auto-generated method stub
		return null;
	}

	public long countTotal() {
		// TODO Auto-generated method stub
		return 0;
	}

	public Roles findById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	public Roles findByRole(String roles) {
		// TODO Auto-generated method stub
		Session currentSession = (Session) getEntityManager().getDelegate();
		 
		 Roles role = null;
	List<Roles> list=currentSession.createCriteria(Roles.class).add(Restrictions.eq("role", roles)).list();
	if(list!=null && list.size()>0)
	{
		
		role =  list.get(0);
	}
    return role;

	}}
