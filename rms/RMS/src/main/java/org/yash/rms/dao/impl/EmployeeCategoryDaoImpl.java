package org.yash.rms.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.yash.rms.dao.EmployeeCategoryDao;
import org.yash.rms.domain.EmployeeCategory;
import org.yash.rms.util.UserUtil;

@Repository("EmployeeCategoryDao")
@Transactional
public class EmployeeCategoryDaoImpl implements EmployeeCategoryDao{
	
	@PersistenceContext(name = "mysql", type = PersistenceContextType.TRANSACTION)
	private EntityManager entityManager;
	
	
	public EntityManager getEntityManager() {
		return entityManager;
	}
	
	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	private static final Logger logger = LoggerFactory.getLogger(EmployeeCategoryDaoImpl.class);

	public boolean delete(int id) {
		logger.info("------------EmployeeCategoryDaoImpl delete method end------------");
		boolean isSuccess=true;
		try {
			 Query query = ((Session) getEntityManager().getDelegate()).getNamedQuery(EmployeeCategory.DELETE_EmployeeCategory_BASED_ON_ID).setInteger("id", id);
			 int totalRowsDeleted = query.executeUpdate();
			System.out.println("Total Rows Deleted::" + totalRowsDeleted);
		} catch (HibernateException e) {
			isSuccess = false;
			logger.error("HibernateException occured in delete method at DAO layer:-"+e);
			throw e;
		} 
		return isSuccess;
	}

	public boolean saveOrupdate(EmployeeCategory employeeCategory) {
		logger.info("----------EmployeeCategoryDaoImpl saveOrupdate method end------------");
		if(null == employeeCategory) return false;
		
		Session currentSession = (Session) getEntityManager().getDelegate();
		boolean isSuccess=true;
		try {
			if(employeeCategory.getId()==0)
			{
				employeeCategory.setCreationTimestamp(new Date());
				employeeCategory.setCreatedId(UserUtil.getCurrentResource().getUsername());
				
			}
			employeeCategory.setLastUpdatedId(UserUtil.getCurrentResource().getUsername());
			currentSession.saveOrUpdate(employeeCategory);
		} catch (HibernateException e) {
			isSuccess = false;
			logger.error("HibernateException occured in saveOrupdate method at DAO layer:-"+e);
			throw e;
		}
		return isSuccess;
	}

	public List<EmployeeCategory> findAll() {
		logger.info("------------EmployeeCategoryDaoImpl findAll method end------------");
		List<EmployeeCategory> employeeCategoryList = new ArrayList<EmployeeCategory>();
		Session session=(Session) getEntityManager().getDelegate();
		try {
			employeeCategoryList = session.createQuery("FROM EmployeeCategory").list(); 
		} catch (HibernateException e) {
			logger.error("HibernateException occured in findAll method at DAO layer:-"+e);
			throw e; 
	      }
		return employeeCategoryList ;
	}

	public List<EmployeeCategory> findByEntries(int firstResult, int sizeNo) {
		logger.info("------------EmployeeCategoryDaoImpl findByEntries method end------------");
		// TODO Auto-generated method stub
		Session session=(Session) getEntityManager().getDelegate();
		List<EmployeeCategory> employeeCategoryList = new ArrayList<EmployeeCategory>();
		try {
			employeeCategoryList = session.createQuery("FROM EmployeeCategory").setFirstResult(firstResult).setMaxResults(sizeNo).list(); 
		} catch (HibernateException e) {
	         e.printStackTrace(); 
	         logger.error("HibernateException occured in findByEntries method at DAO layer:-"+e);
			 throw e;
	      }
		return employeeCategoryList;
	}

	public long countTotal() {
		logger.info("------------EmployeeCategoryDaoImpl countTotal method end------------");
		// TODO Auto-generated method stub
		Session session=(Session) getEntityManager().getDelegate();
		Long count=0L;
		try{
			session.createQuery("SELECT COUNT(o) FROM EmployeeCategory o").list(); 
			 count= (Long) session.createCriteria("EmployeeCategory").setProjection(Projections.rowCount()).uniqueResult();
		}
		catch (HibernateException e) {
	         e.printStackTrace(); 
	         logger.error("HibernateException occured in countTotal method at DAO layer:-"+e);
			 throw e;
	      }
		return count;
	}

	

	public EmployeeCategory findById(int id) {
		// TODO Auto-generated method stub
		EmployeeCategory employeeCategory = null;
		logger.info("--------ActivityDaoImpl findById method start-------");
		Session session=(Session) getEntityManager().getDelegate();
		try {
			employeeCategory = (EmployeeCategory)session.createCriteria(EmployeeCategory.class).add(Restrictions.eq("id", id)).uniqueResult(); 
		} catch (HibernateException e) {
	         e.printStackTrace(); 
	         logger.error("EXCEPTION CAUSED IN GETTING EmployeeCategory By Id " +id+ e.getMessage());
	         throw e;
	     }finally {
	         //session.close(); 
	      }
		logger.info("------OWNERSHIPDaoImpl findById method end-----");
		return employeeCategory ;
	}

	public EmployeeCategory getEmployeeCategoryByName(String employeeType) {
		EmployeeCategory employeeCategory = null;
		logger.info("--------EmployeeCategoryDaoImpl getEmployeeCategoryByName method start-------");
		Session session=(Session) getEntityManager().getDelegate();
		try {
			employeeCategory = (EmployeeCategory)session.createCriteria(EmployeeCategory.class).add(Restrictions.ilike("employeecategoryName", employeeType)).uniqueResult(); 
		} catch (HibernateException e) {
	         e.printStackTrace(); 
	         logger.error("EXCEPTION CAUSED IN GETTING EmployeeCategory By Name " + e.getMessage());
	         throw e;
	     }
		logger.info("------OWNERSHIPDaoImpl findById method end-----");
		return employeeCategory ;
	}
	}


