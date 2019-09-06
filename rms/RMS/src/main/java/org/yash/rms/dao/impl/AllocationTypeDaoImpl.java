package org.yash.rms.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.acls.model.NotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.yash.rms.dao.AllocationTypeDao;
import org.yash.rms.domain.AllocationType;
import org.yash.rms.util.HibernateUtil;
import org.yash.rms.util.UserUtil;

@Transactional
@Repository("allocationTypeDao")
public class AllocationTypeDaoImpl implements AllocationTypeDao {

	private static final Logger logger = LoggerFactory
			.getLogger(AllocationType.class);
	@PersistenceContext(name = "mysql", type = PersistenceContextType.TRANSACTION)
	private EntityManager entityManager;
	
	
	public EntityManager getEntityManager() {
		return entityManager;
	}
	
	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public void saveGrade(AllocationType allocationType) throws Exception {
		logger.info("------------AllocationTypeDaoImpl saveGrade method start------------");
		try {
			((Session) getEntityManager().getDelegate()).saveOrUpdate(
					allocationType);
		} catch (HibernateException hibernateException) {
			logger.error("hibernateException occured in saveGrade method at AllocationTypeDaoImpl DAO layer:-"
					+ hibernateException);
			throw hibernateException;
		} catch (Exception e) {
			logger.error("Exception occured in saveGrade method at AllocationTypeDaoImpl DAO layer:-"
					+ e);
			throw e;
		}
		logger.info("------------AllocationTypeDaoImpl saveGrade method end------------");

	}



	public boolean delete(int id) {

		boolean isSuccess = true;
		logger.info("------------AllocationTypeDaoImpl delete method start------------");
		try {

			Query query = ((Session) getEntityManager().getDelegate())
					.getNamedQuery(
							AllocationType.DELETE_ALLOCATIONTYPE_BASED_ON_ID)
					.setInteger("id", id);
			int totalRowsDeleted = query.executeUpdate();
			System.out.println("Total Rows Deleted::" + totalRowsDeleted);

		} catch (HibernateException e) {

			isSuccess = false;
			e.printStackTrace();
			logger.error("Exception Occurred while deleting AllocationType "
					+ id + e.getMessage());
			throw e;
		} finally {

		}
		logger.info("------------AllocationTypeDaoImpl delete method end------------");
		return isSuccess;
	}

	public List<AllocationType> findAll() {
		// TODO Auto-generated method stub
		logger.info("--------AllocationTypeDaoImpl findAll method start-------");
		List<AllocationType> allocationTypeList = new ArrayList<AllocationType>();

		Session session = (Session) getEntityManager().getDelegate();

		try {

			allocationTypeList = session.createQuery("FROM AllocationType")
					.list();

		} catch (HibernateException e) {
			e.printStackTrace();
			logger.error("EXCEPTION CAUSED IN GETTING ALLOCATIONTYPE LIST "
					+ e.getMessage());
			throw e;

		} finally {

		}
		logger.info("------------AllocationTypeDaoImpl findAll method end------------");
		return allocationTypeList;
	}

	@SuppressWarnings("unused")
	public boolean saveOrupdate(AllocationType allocationType) {
		// TODO Auto-generated method stub
		logger.info("--------AllocationTypeDaoImpl saveOrUpdate method start-------");
		if (null == allocationType)
			return false;

		Session currentSession = (Session) getEntityManager().getDelegate();
		Transaction transaction = null;
		boolean isSuccess = true;
		try {

			if (allocationType.getId() == 0) {

				allocationType.setCreatedId(UserUtil.getUserContextDetails()
						.getUserName());
				allocationType.setCreationTimeStamp(new Date());

			}

			allocationType.setLastUpdatedId(UserUtil.getUserContextDetails()
					.getUserName());
			isSuccess = currentSession.merge(allocationType) != null ? true
					: false;
		} catch (HibernateException e) {
			if (transaction != null) {
			}
			isSuccess = false;
			e.printStackTrace();
			logger.error("Exception Occurred while saving AllocationType "
					+ allocationType.getAllocationType() + e.getMessage());
			throw e;
		} finally {

		}
		logger.info("------AllocationTypeDaoImpl saveOrUpate method end-----");
		return isSuccess;
	}

	public List<AllocationType> findByEntries(int firstResult, int sizeNo) {
		return null;
	}

	public long countTotal() {
		return 0;
	}
	
	public List<AllocationType> getActiveAllocationTypesForRRF(){
		logger.info("--------getActiveAllocationTypesForRRF findAll method start-------");
		List<AllocationType> allocationTypeList = new ArrayList<AllocationType>();

		Session session = (Session) getEntityManager().getDelegate();
		Criteria criteria = session.createCriteria(AllocationType.class);
		criteria.add(Restrictions.ilike("isActiveForRRF", "Y"));
		
		allocationTypeList = criteria.list();
		
		return allocationTypeList;
	}

	public AllocationType getAllocationTypeById(Integer id) {
		Session session = (Session) getEntityManager().getDelegate();
		Criteria criteria = session.createCriteria(AllocationType.class);
		criteria.add(Restrictions.eq("id", id));
		AllocationType allocationType = (AllocationType) criteria.uniqueResult();
		return allocationType;
	}

	public AllocationType getAllocationTypeByType(final String allocationType) {
		AllocationType type = null;
		if(!StringUtils.trimToEmpty(allocationType).isEmpty()){
			try{
				Session session = (Session) getEntityManager().getDelegate();
				if(session != null){
					Criteria criteria = session.createCriteria(AllocationType.class);
					criteria.add(Restrictions.eq("allocationType", allocationType).ignoreCase());
					type = (AllocationType) criteria.uniqueResult();
					return type;
				}
				else{
					logger.error("Session not found or expired ");
					return type;
				}
			}
			catch (HibernateException e) {
				logger.error("HibernateException in GetAllocationTypeByType is -------------- "+ e.getMessage());
				e.printStackTrace();
			}
			catch (Exception e) {
				logger.error("Exception in GetAllocationTypeByType is -------------------------" +e.getMessage());
				e.printStackTrace();
			}
		}
		else
		{
			logger.error("AllocationType not found -------------- "+ allocationType);
			throw new NotFoundException("AllocationType not found");
		}
		return type;
	}

}
