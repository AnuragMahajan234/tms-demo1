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
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.yash.rms.dao.RmsCRUDDAO;
import org.yash.rms.domain.Module;
import org.yash.rms.util.UserUtil;

@Repository("ModuleDao")
@Transactional
public class ModuleDaoImpl implements RmsCRUDDAO<Module>{


@PersistenceContext(name = "mysql", type = PersistenceContextType.TRANSACTION)
	private EntityManager entityManager;
	
	
	public EntityManager getEntityManager() {
		return entityManager;
	}
	
	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	private static final Logger logger = LoggerFactory.getLogger(ModuleDaoImpl.class);

	public boolean saveOrupdate(Module module) {
		logger.info("------------moduleDaoImpl saveOrupdate method start------------");
		
     if(null == module) return false;
		
		Session currentSession = (Session) getEntityManager().getDelegate();
		Transaction transaction=null;
		boolean isSuccess=true;
		try {
 
			
			if(module.getId()==null)
			{
				module.setCreationTimestamp(new Date());
				module.setCreatedId(UserUtil.getCurrentResource().getUsername());
				
			}
			 module.setLastUpdatedId(UserUtil.getCurrentResource().getUsername());
			isSuccess = currentSession.merge(module)!=null?true:false;
 
		} catch (HibernateException e) {
			if (transaction != null) {
 
			}
			isSuccess = false;
			logger.error("HibernateException occured in saveOrupdate method at DAO layer:-"+e);
			throw e;
			
		}   finally {
 
		}
		logger.info("------------moduleDaoImpl saveOrupdate method end------------");
		return isSuccess;
		 
	}

	@SuppressWarnings("unchecked")
	public List<Module> findAll() {
		
		logger.info("------------moduleDaoImpl findAll method start------------");
		
		List<Module> moduleList = new ArrayList<Module>();
		 
		Session session=(Session) getEntityManager().getDelegate();
	 
		try {
			 
			moduleList = session.createQuery("FROM Module").list(); 
			for (Module module : moduleList) {
				 
			}
		} catch (HibernateException e) {
			 
			logger.error("HibernateException occured in findAll method at DAO layer:-"+e);
			throw e;
	      }finally {
	          
	      }
		logger.info("------------moduleDaoImpl findAll method end------------");
		return moduleList ;
		 
	}

	@SuppressWarnings("unchecked")
	public List<Module> findModuleEntries(int firstResult,
			int sizeNo) {
		logger.info("------------moduleDaoImpl findModuleEntries method start------------");
		List<Module> list=new ArrayList<Module>();
		try{
		 list= ((Session) getEntityManager().getDelegate()).createQuery("FROM Module").setFirstResult(firstResult).setMaxResults(sizeNo).list();
		}
		catch (HibernateException e) {
			logger.error("HibernateException occured in findModuleEntries method at DAO layer:-"+e);
			throw e;
		}   finally {
 
		}
		logger.info("------------moduleDaoImpl findModuleEntries method end------------");
		return list;
	}

	public Long countModule() {
		return null;
	}

	public boolean delete(int id) {
		logger.info("------------moduleDaoImpl delete method start------------");
		boolean isSuccess=true;
		try {
 
			 Query query = ((Session) getEntityManager().getDelegate()).getNamedQuery(Module.MODULE_DELETE_BASED_ON_ID).setInteger("id", id);
		 
			 int totalRowsDeleted = query.executeUpdate();
			System.out.println("Total Rows Deleted::" + totalRowsDeleted);
 
		} catch (HibernateException e) {
		 
			isSuccess = false;
			logger.error("HibernateException occured in delete method at DAO layer:-"+e);
			throw e;
		}   finally {
 
		}
		logger.info("------------moduleDaoImpl delete method end------------");
		return isSuccess;
	 
	}




	public List<Module> findByEntries(int firstResult, int sizeNo) {
		return null;
	}

	public long countTotal() {
		return 0;
	}
	
}
