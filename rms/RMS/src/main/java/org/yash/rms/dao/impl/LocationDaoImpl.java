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
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.yash.rms.dao.LocationDao;
import org.yash.rms.domain.Location;
import org.yash.rms.util.UserUtil;

/**
 * @author ankita.shukla
 *
 */
@Repository("LocationDao")
@Transactional
public class LocationDaoImpl implements LocationDao {
	

@PersistenceContext(name = "mysql", type = PersistenceContextType.TRANSACTION)
	private EntityManager entityManager;
	
	
	public EntityManager getEntityManager() {
		return entityManager;
	}
	
	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	private static final Logger logger = LoggerFactory.getLogger(LocationDaoImpl.class);

	public boolean delete(int id) {
		logger.info("------------LocationDaoImpl delete method start------------");
		boolean isSuccess=true;
		try {
			 Query query = ((Session) getEntityManager().getDelegate()).getNamedQuery(Location.DELETE_LOCATION_BASED_ON_ID).setInteger("id", id);
			 int totalRowsDeleted = query.executeUpdate();
			System.out.println("Total Rows Deleted::" + totalRowsDeleted);
		} catch (HibernateException e) {
			isSuccess = false;
			logger.error("HibernateException occured in delete method at DAO layer:-"+e);
			throw e;
		}  
		logger.info("------------LocationDaoImpl delete method end------------");
		return isSuccess;
	}

	public boolean saveOrupdate(Location location) {
		logger.info("------------LocationDaoImpl saveOrupdate method start------------");
		if(null == location) return false;
		
		Session currentSession = (Session) getEntityManager().getDelegate();
		boolean isSuccess=true;
		try { 
			
			if(location.getId()==0)
			{
				location.setCreationTimestamp(new Date());
				location.setCreatedId(UserUtil.getCurrentResource().getUsername());
				
			}
			 location.setLastUpdatedId(UserUtil.getCurrentResource().getUsername());
			currentSession.saveOrUpdate(location);
		} catch (HibernateException e) {
			isSuccess = false;
			logger.error("HibernateException occured in saveOrupdate method at DAO layer:-"+e);
			throw e;
		}   
		logger.info("------------LocationDaoImpl saveOrupdate method end------------");
		return isSuccess;
	}

	public List<Location> findAll() {
		logger.info("--------LocationDaoImpl findAll method start-------");
		List<Location> locationList = new ArrayList<Location>();
		Session session=(Session) getEntityManager().getDelegate();
		try {
			locationList = session.createQuery("FROM Location").list(); 
		} catch (HibernateException e) {
			 logger.error("Exception occured in findAll method at DAO layer:-"+e.getMessage());
	         throw e ;
	     } 
		logger.info("------------LocationDaoImpl findAll method end------------");
		return locationList ;
	}

	public List<Location> findByEntries(int firstResult, int sizeNo) {
		logger.info("--------LocationDaoImpl findByEntries method start-------");
		Session session=(Session) getEntityManager().getDelegate();
		List<Location> locationList = new ArrayList<Location>();
		try {
			locationList = session.createQuery("FROM Location").setFirstResult(firstResult).setMaxResults(sizeNo).list(); 
		} catch (HibernateException e) {
			logger.error("Exception occured in findAll method at DAO layer:-"+e.getMessage());
	        throw e ; 
	      } 
		logger.info("------------LocationDaoImpl findByEntries method end------------");
		return locationList;
	}

	public long countTotal() {
		// TODO Auto-generated method stub
		logger.info("------------LocationDaoImpl countTotal method start------------");
		Session session=(Session) getEntityManager().getDelegate();
		Long count=0L;
		try{
			session.createQuery("SELECT COUNT(o) FROM Location o").list(); 
			 count= (Long) session.createCriteria("Location").setProjection(Projections.rowCount()).uniqueResult();
		}
		catch (HibernateException e) {
			logger.error("Exception occured in countTotal method at DAO layer:-"+e.getMessage());
	        throw e ; 
	      } 
		logger.info("------------LocationDaoImpl countTotal method end------------");
		return count;
	}



	public Location findById(int id) {
		// TODO Auto-generated method stub
		Location location = null;
		logger.info("--------LocationDaoImpl findById method start-------");
		Session session=(Session) getEntityManager().getDelegate();
		try {
			location = (Location)session.createCriteria(Location.class).add(Restrictions.eq("id", id)).uniqueResult(); 
		} catch (HibernateException e) {
	         e.printStackTrace(); 
	         logger.error("EXCEPTION CAUSED IN GETTING Location By Id " +id+ e.getMessage());
	         throw e;
	     }finally {
	         //session.close(); 
	      }
		logger.info("------LocationDaoImpl findById method end-----");
		return location ;
	}

	/**
	 * This method should be used exclusively for infogram data. As RMS data and infogram location data does not match. 
	 */
	public Location findLocationByName(String locationName) {
		
		logger.info("--------LocationDaoImpl findLocationByName method start-------");
		Location location = null;
		Session session=(Session) getEntityManager().getDelegate();
		try {
				
				location = (Location)session.createCriteria(Location.class).add(Restrictions.eq("location", locationName)).uniqueResult(); 
			
		} catch (HibernateException e) {
	         e.printStackTrace(); 
	         logger.error("EXCEPTION CAUSED IN GETTING Location By Name " + e.getMessage());
	         throw e;
	     }
		logger.info("------LocationDaoImpl findLocationByName method end-----");
		return location ;
	}

}
