package org.yash.rms.service.impl;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.yash.rms.dao.UserProfileDao;
import org.yash.rms.domain.UserProfile;

@Repository("userProfileDao")
public class UserProfileDaoImpl implements UserProfileDao{
	
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

	public boolean saveOrupdate(UserProfile userProfile) {
		
		boolean isSuccess = true;
		
		if (null == userProfile)
			return false;
		Session session = (Session) getEntityManager().getDelegate();

		try {
			if(null!=userProfile.getId()){
				isSuccess=null!=session.merge(userProfile)?true:false;
			}else{
				Serializable id = session.save(userProfile);
				isSuccess=null!=id?true:false;
				if(isSuccess){
					userProfile.setId((Integer)id);
				}
			}
//			session.saveOrUpdate(userProfile);
		}catch (HibernateException e) {
			isSuccess = false;
			e.printStackTrace();
		} catch (Exception ex) {
			isSuccess = false;
			ex.printStackTrace();
		} finally {
			// currentSession.close();
		}
		return isSuccess;
		// TODO Auto-generated method stub
	}

	public List<UserProfile> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<UserProfile> findByEntries(int firstResult, int sizeNo) {
		// TODO Auto-generated method stub
		return null;
	}

	public long countTotal() {
		// TODO Auto-generated method stub
		return 0;
	}


	/*public UserProfile merge(UserProfile userProfile) {
		UserProfile userProfile1 = (UserProfile) ((Session) getEntityManager().getDelegate()).merge(userProfile);
		return userProfile1;
	}
*/
	
	public UserProfile findByYashId(String yashId) {
		// TODO Auto-generated method stub
		UserProfile profile = null;
		
		Session session=(Session) getEntityManager().getDelegate();
		try {
			profile = (UserProfile)session.createCriteria(UserProfile.class).add(Restrictions.eq("yashEmpId", yashId)).uniqueResult(); 
		} catch (HibernateException e) {
	         e.printStackTrace(); 
	         throw e;
	     }finally {
	         //session.close(); 
	      }
		return profile ;
	}


}
