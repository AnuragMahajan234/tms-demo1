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

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.yash.rms.dao.EngagementModelDao;
import org.yash.rms.domain.EngagementModel;
import org.yash.rms.domain.SEPGPhases;
import org.yash.rms.util.HibernateUtil;
import org.yash.rms.util.UserUtil;

/**
 * @author ankita.shukla
 * 
 */
@Repository("EngagementModelDao")
@Transactional
public class EngagementModelDaoImpl implements EngagementModelDao {

	@PersistenceContext(name = "mysql", type = PersistenceContextType.TRANSACTION)
	private EntityManager entityManager;
	
	
	public EntityManager getEntityManager() {
		return entityManager;
	}
	
	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	private static final Logger logger = LoggerFactory
			.getLogger(EngagementModelDaoImpl.class);

	public boolean delete(int id) {
		logger.info("------EngagementModelDaoImpl delete method start------");

		boolean isSuccess = true;
		try {
			/** Added for task#2111 **/
			Criteria criteria = ((Session) getEntityManager().getDelegate())
					.createCriteria(SEPGPhases.class)
					.add(Restrictions.eq("engagementModel.id", id));
			List<SEPGPhases> list = criteria.list();
			if (list.size() > 0) {
				System.out.println("Row cannot be deleted, as reference exists. ");
				return false;
			} else { 
			
				Query query = ((Session) getEntityManager().getDelegate())
						.getNamedQuery(
								EngagementModel.DELETE_ENGAGEMENTMODEL_BASED_ON_ID)
						.setInteger("id", id);
				int totalRowsDeleted = query.executeUpdate();
				System.out.println("Total Rows Deleted::" + totalRowsDeleted);
			}
		} catch (HibernateException e) {
			logger.error("HibernateException occured in delete method at DAO layer:-"
					+ e);

			isSuccess = false;
			e.printStackTrace();
		} catch (Exception ex) {
			isSuccess = false;
			ex.printStackTrace();
		} finally {
			// currentSession.close();
		}
		logger.info("------EngagementModelDaoImpl delete method end-----");

		return isSuccess;
	}

	public boolean saveOrupdate(EngagementModel engagementModel) {
		logger.info("------EngagementModelDaoImpl saveOrUpdate method start------");

		if (null == engagementModel)
			return false;

		Session currentSession = (Session) getEntityManager().getDelegate();
		boolean isSuccess = true;
		try {

			if (engagementModel.getId() == 0) {

				engagementModel.setCreatedId(UserUtil.getUserContextDetails()
						.getUserName());
				engagementModel.setCreationTimestamp(new Date());

			}

			engagementModel.setLastUpdatedId(UserUtil.getUserContextDetails()
					.getUserName());

			/*
			 * if (engagementModel.getCreationTimestamp() == null) {
			 * engagementModel.setCreationTimestamp(new Date()); }
			 */
			currentSession.saveOrUpdate(engagementModel);
		} catch (HibernateException e) {
			logger.error("HibernateException occured in saveOrupdate method at DAO layer:-"
					+ e);

			isSuccess = false;
			e.printStackTrace();
		} catch (Exception ex) {
			isSuccess = false;
			ex.printStackTrace();
		} finally {
			// currentSession.close();
		}
		logger.info("------EngagementModelDaoImpl saveOrUpate method end-----");

		return isSuccess;
	}

	public List<EngagementModel> findAll() {
		logger.info("--------EngagementModelDaoImpl findAll method start-------");

		List<EngagementModel> engagementModelList = new ArrayList<EngagementModel>();
		Session session = (Session) getEntityManager().getDelegate();
		try {
			engagementModelList = session.createQuery("FROM EngagementModel")
					.list();
		} catch (HibernateException hibernateException) {
			logger.error("Exception occured in findAll method at DAO layer:-"
					+ hibernateException);

			hibernateException.printStackTrace();
		} finally {
			// session.close();
		}
		logger.info("------------EngagementModelDaoImpl findAll method end------------");

		return engagementModelList;
	}

	public List<EngagementModel> findByEntries(int firstResult, int sizeNo) {
		logger.info("-----------EngagementModelDaoImpl findByEntries method start---------");

		Session session = (Session) getEntityManager().getDelegate();
		List<EngagementModel> engagementModelList = new ArrayList<EngagementModel>();
		try {
			engagementModelList = session.createQuery("FROM EngagementModel")
					.setFirstResult(firstResult).setMaxResults(sizeNo).list();
		} catch (HibernateException e) {
			logger.error("Exception occured in findByEntries method at DAO layer:-"
					+ e);

			e.printStackTrace();
		} finally {
			// session.close();
		}
		logger.info("------------EngagementModelDaoImpl findByEntries method end------------");

		return engagementModelList;
	}

	public long countTotal() {
		logger.info("-----------EngagementModelDaoImpl countTotal method start---------");

		Session session = (Session) getEntityManager().getDelegate();
		Long count = 0L;
		try {
			session.createQuery("SELECT COUNT(o) FROM EngagementModel o")
					.list();
			count = (Long) session.createCriteria("EngagementModel")
					.setProjection(Projections.rowCount()).uniqueResult();
		} catch (Exception e) {
			logger.error("Exception occured in countTotal method at DAO layer:-"
					+ e);
			e.printStackTrace();
		}
		logger.info("------------EngagementModelDaoImpl countTotal method end------------");
		return count;
	}

	

	public List<EngagementModel> findLeftSepgEngagement(List<Integer> ids) {
		logger.info("--------EngagementModelDaoImpl findLeftSepgEngagement  method start-------");
		Session session = (Session) getEntityManager().getDelegate();
		if (null == session)
			throw new RuntimeException("Session Object Cannot be null");
		Criteria criteria = session.createCriteria(EngagementModel.class);
		criteria.add(Restrictions.not(Restrictions.in("id", ids)));
		criteria.addOrder(Order.asc("engagementModelName"));
		List<EngagementModel> engagementModelsList = (List<EngagementModel>) criteria
				.list();
		logger.info("--------EngagementModelDaoImpl findLeftSepgEngagement method end-------");
		return (((engagementModelsList != null)
				&& (!engagementModelsList.isEmpty()) ? engagementModelsList
				: new ArrayList<EngagementModel>()));
	}

	public List<EngagementModel> findSelectSepgEngagement(List<Integer> ids) {
		logger.info("--------EngagementModelDaoImpl findSelectSepgEngagement  method start-------");
		Session session = (Session) getEntityManager().getDelegate();
		if (null == session)
			throw new RuntimeException("Session Object Cannot be null");
		Criteria criteria = session.createCriteria(EngagementModel.class);
		criteria.add(Restrictions.in("id", ids));
		criteria.addOrder(Order.asc("engagementModelName"));
		List<EngagementModel> engagementModelsList = (List<EngagementModel>) criteria.list();
		logger.info("--------EngagementModelDaoImpl findSelectSepgEngagement method end-------");
		return (((engagementModelsList != null)
				&& (!engagementModelsList.isEmpty()) ? engagementModelsList
				: new ArrayList<EngagementModel>()));
	}

	public List<SEPGPhases> getSEPGIdsWithEngagementModel(){
		logger.info("--------EngagementModelDaoImpl getSEPGIdsWithEngagementModel  method start-------");
		Session session = (Session) getEntityManager().getDelegate();
		Criteria criteria = session.createCriteria(SEPGPhases.class).setProjection(Projections.distinct(Projections.property("engagementModel.id")));
		logger.info("--------EngagementModelDaoImpl getSEPGIdsWithEngagementModel  method end-------");
		return criteria.list();
	}

	public EngagementModel findById(Integer id) throws Exception {
			logger.info("--------EngagementModelDaoImpl findById method start-------");
			EngagementModel engagementModel = null;
			Session session = (Session) getEntityManager().getDelegate();
			if(session != null) {
				try {
					engagementModel = (EngagementModel) session.get(EngagementModel.class, id);
				}
				catch (HibernateException ex) {
					logger.error("HibernateException occured in EngagementModelDaoImpl findById method at DAO layer:-"+ ex);
					throw ex;
				}
				catch (Exception ex) {
					logger.error("Exception occured in EngagementModelDaoImpl findById method at DAO layer:-"+ ex);
					throw ex;
				}
			}

			logger.info("--------EngagementModelDaoImpl findById method end-------");
			return engagementModel;
	}
	
	public EngagementModel findEngagementModelByProjectId(Integer id) throws Exception {
		logger.info("--------EngagementModelDaoImpl findById method start-------");
		EngagementModel engagementModel = null;
		Session session = (Session) getEntityManager().getDelegate();
		if (session != null) {
			try {
				String hql = "SELECT p.engagementModelId FROM Project p WHERE p.id=:id";
				Query query = session.createQuery(hql);
				query.setParameter("id", id);
				engagementModel = (EngagementModel) query.uniqueResult();
			} catch (HibernateException ex) {
				logger.error(
						"HibernateException occured in EngagementModelDaoImpl findById method at DAO layer:-" + ex);
				throw ex;
			} catch (Exception ex) {
				logger.error("Exception occured in EngagementModelDaoImpl findById method at DAO layer:-" + ex);
				throw ex;
			}
		}
		logger.info("--------EngagementModelDaoImpl findById method end-------");

		return engagementModel;
	}
}
