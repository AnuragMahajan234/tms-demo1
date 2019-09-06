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
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.yash.rms.dao.TeamDao;
import org.yash.rms.domain.Team;
import org.yash.rms.domain.TeamViewRightEmbedded;
import org.yash.rms.util.HibernateUtil;
import org.yash.rms.util.UserUtil;

/**
 * @author bhakti.barve
 *
 */
@Transactional
@Repository("TeamDao")
public class TeamDaoImpl implements TeamDao<Team> {
	private static final Logger logger = LoggerFactory
			.getLogger(TeamDaoImpl.class);

	@PersistenceContext(name = "mysql", type = PersistenceContextType.TRANSACTION)
	private EntityManager entityManager;
	
	
	public EntityManager getEntityManager() {
		return entityManager;
	}
	
	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	

	@SuppressWarnings("unused")
	public boolean delete(int id) {
		logger.info("------TeamDaoImpl delete method start------");
		boolean isSuccess = true;
		try {

			Query query = ((Session) getEntityManager().getDelegate())
					.getNamedQuery(Team.DELETE_TEAM_BASED_ON_ID)
					.setInteger("id", id);
			int totalRowsDeleted = query.executeUpdate();

		} catch (HibernateException e) {

			isSuccess = false;
			logger.error("HibernateException occured in delete method at DAO layer:-"
					+ e);
			throw e;
		}
		logger.info("------TeamDaoImpl delete method end------");
		return isSuccess;
	}

	@SuppressWarnings("unused")
	public boolean saveOrupdate(Team team) {
		logger.info("------TeamDaoImpl saveOrUpdate method start------");
		if (null == team)
			return false;

		Session currentSession = (Session) getEntityManager().getDelegate();
		Transaction transaction = null;
		boolean isSuccess = true;
		try {

			if (team.getId() == 0) {

				team.setCreatedId(UserUtil.getUserContextDetails()
						.getUserName());
				team.setCreationTimeStamp(new Date());

			}

			team.setLastUpdatedId(UserUtil.getUserContextDetails()
					.getUserName());

			isSuccess = currentSession.merge(team) != null ? true : false;
		} catch (HibernateException e) {
			if (transaction != null) {
			}
			isSuccess = false;
			logger.error("HibernateException occured in saveOrupdate method at DAO layer:-"
					+ e);

			e.printStackTrace();
		} catch (Exception ex) {
			if (transaction != null) {
			}
			isSuccess = false;
			logger.error("HibernateException occured in saveOrupdate method at DAO layer:-"
					+ ex);

			ex.printStackTrace();
		} finally {

		}
		logger.info("------TeamDaoImpl saveOrUpate method end-----");
		return isSuccess;
	}

	@SuppressWarnings("unchecked")
	public List<Team> findAll() {
		logger.info("--------TeamDaoImpl findAll method start-------");

		List<Team> teamList = new ArrayList<Team>();

		Session session = (Session) getEntityManager().getDelegate();

		try {

			teamList = session.createQuery("FROM Team ORDER BY teamName ASC").list();

		} catch (HibernateException e) {
			logger.error("Exception occured in findAll method at DAO layer:-"
					+ e);
			e.printStackTrace();
		} finally {

		}
		logger.info("------------TeamDaoImpl findAll method end------------");

		return teamList;
	}

	public List<Team> findByEntries(int firstResult, int sizeNo) {

		return null;
	}

	public long countTotal() {
		// TODO Auto-generated method stub
		return 0;
	}

	@SuppressWarnings("unchecked")
	public List<TeamViewRightEmbedded> teamViewList(int teamId) {
		logger.info("--------TeamDaoImpl teamViewList method start-------");
		List<TeamViewRightEmbedded> teamList = new ArrayList<TeamViewRightEmbedded>();

		Session session = (Session) getEntityManager().getDelegate();

		try {
			Query query = session
					.createQuery("FROM TeamViewRightEmbedded t where t.teamViewRight.teamId =:teamId order by t.teamViewRight.creationTime asc");
			query.setParameter("teamId", teamId);
			teamList = query.list();

		} catch (HibernateException e) {
			logger.error("Exception occured in teamViewList method at TeamDaoImpl :-"
					+ e);
			e.printStackTrace();
		} finally {

		}
		logger.info("--------TeamDaoImpl teamViewList method end-------");
		return teamList;
	}

	public boolean saveTeamViewRight(TeamViewRightEmbedded teamAccessDto) {
		logger.info("------TeamDaoImpl saveOrUpdate method start------");
		if (null == teamAccessDto)
			return false;

		Session currentSession = (Session) getEntityManager().getDelegate();
		Transaction transaction = null;
		boolean isSuccess = true;
		try {

			teamAccessDto.getTeamViewRight().setCreatedBy(
					UserUtil.getUserContextDetails().getUserName());
			teamAccessDto.getTeamViewRight().setCreationTime(new Date());

			isSuccess = currentSession.merge(teamAccessDto) != null ? true
					: false;
		} catch (HibernateException e) {
			if (transaction != null) {
			}
			isSuccess = false;
			logger.error("HibernateException occured in saveTeamViewRight() method at DAO layer:-"
					+ e);

			e.printStackTrace();
		} catch (Exception ex) {
			if (transaction != null) {
			}
			isSuccess = false;
			logger.error("HibernateException occured in saveTeamViewRight() method at DAO layer:-"
					+ ex);

			ex.printStackTrace();
		} finally {

		}
		logger.info("------TeamDaoImpl saveTeamViewRight() method end-----");
		return isSuccess;
	}

	public boolean deleteResource(int teamId, int resourceId) {
		logger.info("------TeamDaoImpl delete method start------");
		boolean isSuccess = true;
		try {

			Query query = ((Session) getEntityManager().getDelegate())
					.createQuery(
							"DELETE TeamViewRightEmbedded b where b.teamViewRight.teamId=:teamId and b.teamViewRight.resourceId=:resourceId");
			query.setParameter("teamId", teamId);
			query.setParameter("resourceId", resourceId);
			int totalRowsDeleted = query.executeUpdate();

		} catch (HibernateException e) {

			isSuccess = false;
			logger.error("HibernateException occured in delete method at DAO layer:-"
					+ e);
			throw e;
		}
		logger.info("------TeamDaoImpl delete method end------");
		return isSuccess;
	}

	public List<TeamViewRightEmbedded> getTeamAccessListByResourceId(
			int resourceId) {
		logger.info("--------TeamDaoImpl getTeamAccessListByResourceId method start-------");
		List<TeamViewRightEmbedded> teamList = new ArrayList<TeamViewRightEmbedded>();

		Session session = (Session) getEntityManager().getDelegate();

		try {
			Query query = session
					.createQuery("FROM TeamViewRightEmbedded t where t.teamViewRight.resourceId =:resourceId order by t.teamViewRight.creationTime asc");
			query.setParameter("resourceId", resourceId);
			teamList = query.list();

		} catch (HibernateException e) {
			logger.error("Exception occured in getTeamAccessListByResourceId method at TeamDaoImpl :-"
					+ e);
			e.printStackTrace();
		} finally {

		}
		logger.info("--------TeamDaoImpl getTeamAccessListByResourceId method end-------");
		return teamList;

	}

	@SuppressWarnings("unchecked")
	public List<Team> getTeamNameById(List<Integer> teamIds) {
		logger.info("--------TeamDaoImpl getTeamNameById method start-------");
		List<Team> teamList = null;
		Session session = (Session) getEntityManager().getDelegate();
		try {
			Query query = session
					.createQuery("FROM  Team t WHERE t.id IN (:ids) ORDER BY teamName ASC");
			query.setParameterList("ids", teamIds);
			teamList = query.list();

		} catch (HibernateException e) {
			e.printStackTrace();
			logger.error("EXCEPTION CAUSED IN GETTING TeamList By Id "
					+ teamIds + e.getMessage());
			throw e;
		} finally {
			// session.close();
		}
		return teamList;
	}

}
