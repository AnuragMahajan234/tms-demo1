/**
 * 
 */
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
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.yash.rms.dao.DesignationDao;
import org.yash.rms.domain.Designation;
import org.yash.rms.util.UserUtil;

/**
 * @author arpan.badjatiya
 *
 */
@Repository("DesignationDao")
@Transactional
public class DesignationDaoImpl implements DesignationDao {
	
	@PersistenceContext(name = "mysql", type = PersistenceContextType.TRANSACTION)
	private EntityManager entityManager;
	
	
	public EntityManager getEntityManager() {
		return entityManager;
	}
	
	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	private static final Logger logger = LoggerFactory.getLogger(DesignationDaoImpl.class);

	public boolean saveOrupdate(Designation designation) {
		logger.info("------DesignationDaoImpl saveOrUpdate method start------");
		if(null == designation) return false;
		
		Session currentSession = (Session) getEntityManager().getDelegate();
		boolean isSuccess=true;
		try {
			
			if(designation.getId()==0)
			{
				
				designation.setCreatedId( UserUtil.getUserContextDetails().getUserName());
				designation.setCreationTimestamp(new Date());
				
			}
			
			designation.setLastUpdatedId( UserUtil.getUserContextDetails().getUserName());
		
		/*	 if (designation.getCreationTimestamp() == null) {
				 designation.setCreationTimestamp(new Date());
				   }*/
			currentSession.saveOrUpdate(designation);
		} catch (HibernateException e) {
			logger.error("HibernateException occured in saveOrupdate method at DAO layer:-"+e);
			isSuccess = false;
			e.printStackTrace();
		} catch (Exception ex) {
			logger.error("HibernateException occured in saveOrupdate method at DAO layer:-"+ex);
			isSuccess = false;
			ex.printStackTrace();
		} finally {
//			currentSession.close();
		}
		logger.info("------DesignationDaoImpl saveOrUpate method end-----");
		return isSuccess;
	}
	

	public boolean delete(int id) {
		logger.info("------DesignationDaoImpl delete method start------");
		boolean isSuccess=true;
		try {
			 Query query = ((Session) getEntityManager().getDelegate()).getNamedQuery(Designation.DELETE_DESIGNATION_BASED_ON_ID).setInteger("id", id);
			 int totalRowsDeleted = query.executeUpdate();
			System.out.println("Total Rows Deleted::" + totalRowsDeleted);
		} catch (HibernateException e) {
			logger.error("HibernateException occured in saveOrupdate method at DAO layer:-"+e);

			isSuccess = false;
			// Added for task # 386 - Start
			//e.printStackTrace();
			// Added for task # 386 - End
			throw e;
		} catch (Exception ex) {
			isSuccess = false;
			logger.error("HibernateException occured in saveOrupdate method at DAO layer:-"+ex);

			ex.printStackTrace();
		} finally {
//			currentSession.close();
		}
		logger.info("------DesignationDaoImpl delete method ends------");

		return isSuccess;
	}
	
	@SuppressWarnings("unchecked")
	public List<Designation> findAll() {
		logger.info("--------DesignationDaoImpl findAll method start-------");
		List<Designation> designationList = new ArrayList<Designation>();
		Session session=(Session) getEntityManager().getDelegate();
		try {
			designationList = session.createCriteria(Designation.class).list(); 
		} catch (HibernateException e) {
			logger.error("Exception occured in findAll method at DAO layer:-"+e);
	         e.printStackTrace(); 
	     }finally {
	         //session.close(); 
	      }
		logger.info("------------DesignationDaoImpl findAll method end------------");
		return designationList ;
	}

	public List<Designation> findByEntries(int firstResult, int sizeNo) {
		return null;
	}




	public long countTotal() {
		// TODO Auto-generated method stub
		return 0;
	}


	public Designation findById(int id) {
		// TODO Auto-generated method stub
		Designation designation = null;
		logger.info("-------- DesignationDaoImpl findById method start-------");
		Session session=(Session) getEntityManager().getDelegate();
		try {
			designation = (Designation)session.createCriteria(Designation.class).add(Restrictions.eq("id", id)).uniqueResult(); 
		} catch (HibernateException e) {
	         e.printStackTrace(); 
	         logger.error("EXCEPTION CAUSED IN GETTING Designation By Id " +id+ e.getMessage());
	         throw e;
	     }finally {
	         //session.close(); 
	      }
		logger.info("------DesignationDaoImpl findById method end-----");
		return designation ;
	}


	public Designation findByNameIgnoreSpace(String name) {
		Designation designation = new Designation();
		
		logger.info("-------- DesignationDaoImpl findByName method start-------");
		Session session=(Session) getEntityManager().getDelegate();
		try {
			Query query = session.createQuery("  FROM Designation WHERE LOWER(REPLACE(designationName, ' ', '')) = LOWER(REPLACE(:designationname, ' ', ''))");
			query.setParameter("designationname", name);
			designation = (Designation) query.uniqueResult();
		} catch (HibernateException e) {
	       
	         logger.error("EXCEPTION CAUSED IN GETTING Designation By Name " + e.getMessage());
	         throw e;
	     }
		logger.info("------DesignationDaoImpl findById method end-----");
		
		return designation ;
	}
}
