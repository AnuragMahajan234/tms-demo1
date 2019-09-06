package org.yash.rms.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.yash.rms.dao.UserNotificationDAO;
import org.yash.rms.domain.UserNotification;

@Repository("UserNotificationDAO")
@Transactional
public class UserNotificationDaoImpl implements UserNotificationDAO {

	@PersistenceContext(name = "mysql", type = PersistenceContextType.TRANSACTION)
	private EntityManager entityManager;
	
	
	public EntityManager getEntityManager() {
		return entityManager;
	}
	
	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	private static final Logger logger = LoggerFactory.getLogger(UserNotificationDaoImpl.class);
	
	public boolean delete(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean saveOrupdate(UserNotification userNotification) {
		logger.info("------------UserNotificationDaoImpl saveOrupdate method start------------");
		if (null == userNotification)
			return false;

		boolean isSuccess = true;
		try {
			 ((Session) getEntityManager().getDelegate()).saveOrUpdate(userNotification);
		}catch (HibernateException e) {
			isSuccess = false;
			logger.error("HibernateException occured in saveOrupdate method at DAO layer:-"+e);
		} catch (Exception ex) {
			isSuccess = false;
			ex.printStackTrace();
		} finally {
			// currentSession.close();
		}
		logger.info("------------UserNotificationDaoImpl saveOrupdate method end------------");
		return isSuccess;
	}

	public List<UserNotification> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<UserNotification> findByEntries(int firstResult, int sizeNo) {
		// TODO Auto-generated method stub
		return null;
	}

	public long countTotal() {
		// TODO Auto-generated method stub
		return 0;
	}

	@SuppressWarnings("unchecked")
	public List<UserNotification> findUserNotification(int employeeId,boolean isRead) {
		logger.info("------------UserNotificationDaoImpl findUserNotification method start------------");
		Session session = (Session) getEntityManager().getDelegate();
		Criteria criteria = session.createCriteria(UserNotification.class);
		criteria.add(Restrictions.eq("employeeId.employeeId", employeeId)).add(Restrictions.eq("isRead", isRead));
		List<UserNotification> userNotifications = new ArrayList<UserNotification>();
		try{
			userNotifications = (List<UserNotification>)criteria.list();
		}catch(HibernateException hibernateException){
			logger.error("HibernateException occured in findUserNotification method at DAO layer:-"+hibernateException);
			hibernateException.printStackTrace();
			throw hibernateException;
		}		
		logger.info("------------UserNotificationDaoImpl findUserNotification method end------------");
		return userNotifications;
	}
	
	
	public void updateUserNotification(String notificationId){
		
		Session session = (Session) getEntityManager().getDelegate();
		Query query = session.createQuery("UPDATE UserNotification set is_read=true where id in (" + notificationId + ")");
		try{
			query.executeUpdate();
		}catch(HibernateException hibernateException){
			hibernateException.printStackTrace();
		}
		
	}

}
