package org.yash.rms.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.yash.rms.dao.EditProfileDao;
import org.yash.rms.domain.SkillResourcePrimary;
import org.yash.rms.domain.SkillResourceSecondary;
import org.yash.rms.domain.UserProfile;
import org.yash.rms.helper.ResourceResumeHelper;

@Repository("editProfileDao")
@Transactional
public class EditProfileDaoImpl implements EditProfileDao{
	@PersistenceContext(name = "mysql", type = PersistenceContextType.TRANSACTION)
	private EntityManager entityManager;
	
	
	public EntityManager getEntityManager() {
		return entityManager;
	}
	
	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Autowired
	@Qualifier("resumeUploadPath")
	private ResourceResumeHelper resourceHelper;

	private static final Logger logger = LoggerFactory
			.getLogger(EditProfileDaoImpl.class);

	@SuppressWarnings("unchecked")
	public List<UserProfile> findUserProfilesByLogicalDeleteEqualsAndYashEmpIdEquals(
			String logicalDelete, String yashEmpId) {
		logger.info("--------EditProfileDaoImpl findUserProfilesByLogicalDeleteEqualsAndYashEmpIdEquals method start-------");
		try {
			Session session = (Session) getEntityManager().getDelegate();
			List<UserProfile> profiles = new ArrayList<UserProfile>();
			profiles = session.createCriteria(UserProfile.class)
					.add(Restrictions.eq("logicalDelete", logicalDelete))
					.add(Restrictions.eq("yashEmpId", yashEmpId)).list();
			logger.info("--------EditProfileDaoImpl findUserProfilesByLogicalDeleteEqualsAndYashEmpIdEquals method end-------");
			return profiles;
		} catch (HibernateException e) {
			logger.error("Exception occured in findUserProfilesByLogicalDeleteEqualsAndYashEmpIdEquals method at DAO layer:-"
					+ e);
			throw e;
		}
	}

	public boolean delete(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean saveOrupdate(UserProfile t) {
		// TODO Auto-generated method stub
		return false;
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

	public List<UserProfile> findUserProfilesByYashEmpIdEquals(String yashEmpId) {
		// TODO Auto-generated method stub
		logger.info("--------EditProfileDaoImpl findUserProfilesByYashEmpIdEquals method start-------");
		try {

			Session session = (Session) getEntityManager().getDelegate();
			List<UserProfile> profiles = new ArrayList<UserProfile>();
			profiles = session.createCriteria(UserProfile.class)
					.add(Restrictions.eq("yashEmpId", yashEmpId)).list();
			logger.info("--------EditProfileDaoImpl findUserProfilesByYashEmpIdEquals method end-------");
			return profiles;
		} catch (HibernateException e) {

			logger.error("Exception occured in findUserProfilesByYashEmpIdEquals method at DAO layer:-"
					+ e);
			throw e;
		}
	}

	public List<Object[]> getSkillsMapping(String yashEmpId, String query) {
		logger.info("--------EditProfileDaoImpl getSkillsMapping method start-------");
		try {

			List<Object[]> skillsMappings = null;
			Session session = (Session) getEntityManager().getDelegate();
			if (query != null && query.length() > 0) {
				skillsMappings = session.createSQLQuery(query)
						.setParameter("employeeId", yashEmpId).list();
			}
			logger.info("--------EditProfileDaoImpl getSkillsMapping method end-------");
			return skillsMappings;
		} catch (HibernateException e) {

			logger.error("Exception occured in getSkillsMapping method at DAO layer:-"
					+ e);
			throw e;
		}
	}
	
	/*Resume Download on dashboard edit profile DaoImpl */
	@SuppressWarnings("unchecked")
	public Object getResumeById(Integer requestID) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Session currentSession = (Session) getEntityManager().getDelegate();
			
			Query query  = currentSession.createSQLQuery("select upload_Resume, upload_resume_file_name from resource where employee_id =:id");
			
			query.setParameter("id", requestID);
            List<Object[]> remumes = (List<Object[]>) query.list();
			for (Object[] resume : remumes) {
				map.put("file", (byte[]) resume[0]);
				map.put("fileName", (String) resume[1]);
			}
		} catch (Exception e) {
			logger.error("Exception occured in EditProfileDaoImpl getResumeById method at DAO layer:-" + e);
		}
		logger.info("--------EditProfileDaoImpl getResumeById method end-------");
		return map;}

	public boolean UpdateUserPrimarySkill(SkillResourcePrimary skillProfile) {
		
		boolean isSuccess = true;
		try {
			  ((Session) getEntityManager().getDelegate()).saveOrUpdate(skillProfile);
		}catch (HibernateException e) {
			isSuccess = false;
			logger.error("HibernateException occured in saveOrUpdate method at DAO layer:-"
					+ e);
		} catch (Exception ex) {
			isSuccess = false;
			ex.printStackTrace();
		} 
		logger.info("--------UpdateUserPrimarySkillDaoImpl saveOrupdate method end-------");
		return isSuccess;
	}
public boolean UpdateUserSecondarySkill(SkillResourceSecondary skillProfile) {
		
		boolean isSuccess = true;
		try {
			  ((Session) getEntityManager().getDelegate()).saveOrUpdate(skillProfile);
		}catch (HibernateException e) {
			isSuccess = false;
			logger.error("HibernateException occured in saveOrUpdate method at DAO layer:-"
					+ e);
		} catch (Exception ex) {
			isSuccess = false;
			ex.printStackTrace();
		} 
		logger.info("--------UpdateUserSecondarySkillDaoImpl saveOrupdate method end-------");
		return isSuccess;
	}
}
