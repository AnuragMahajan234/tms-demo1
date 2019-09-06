package org.yash.rms.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.yash.rms.dao.PhaseDao;
import org.yash.rms.domain.Phase;

@Repository("PhaseDao")
@Transactional
public class PhaseDaoImpl implements PhaseDao {

	private static final Logger logger = LoggerFactory
			.getLogger(PhaseDaoImpl.class);

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

		return false;
	}

	public boolean saveOrupdate(Phase phase) {

		/*
		 * logger.info("----PhaseDaoImpl start saveOrupdate method-------------")
		 * ; Session currentSession = (Session) getEntityManager().getDelegate();
		 * if(currentSession==null) throw new
		 * RuntimeException("Session Object Cannot be null");
		 * currentSession.saveOrUpdate(phase);
		 * logger.info("----PhaseDaoImpl end saveOrupdate method-------------");
		 */
		return false;
	}

	public List<Phase> findAll() {

		logger.info("----PhaseDaoImpl start findall method-------------");
		Session currentSession = (Session) getEntityManager().getDelegate();
		if (currentSession == null)
			throw new RuntimeException("Session Object Cannot be null");
		List<Phase> phasesList = (List<Phase>) currentSession.createQuery(
				"from Phase").list();
		logger.info("----PhaseDaoImpl end findall method-------------");
		return (((phasesList != null) && (!phasesList.isEmpty()) ? phasesList
				: new ArrayList<Phase>()));
	}

	public List<Phase> findByEntries(int firstResult, int sizeNo) {
		// TODO Auto-generated method stub
		return null;
	}

	public long countTotal() {
		// TODO Auto-generated method stub
		return 0;
	}

	public Phase getPhase(int id) {

		logger.info("----PhaseDaoImpl start getPhase method-------------");
		Session currentSession = (Session) getEntityManager().getDelegate();
		if (currentSession == null)
			throw new RuntimeException("Session Object Cannot be null");
		Phase phase = (Phase) currentSession.get(Phase.class, id);
		logger.info("----PhaseDaoImpl end getPhase method-------------");
		return ((phase != null) ? phase : new Phase());

	}

	public Serializable save(Phase phase) {

		logger.info("----PhaseDaoImpl start save method-------------");
		Serializable id = null;
		Session currentSession = (Session) getEntityManager().getDelegate();
		if (currentSession == null)
			throw new RuntimeException("Session Object Cannot be null");

		try {
			// Criteria criteria =
			// currentSession.createCriteria(Phase.class).add(
			// Restrictions.eq("phasesName", phase.getPhasesName()));
			// List<Phase> list = criteria.list();
			// if (list.size() <= 0) {
			id = currentSession.save(phase);
			return id;// return false;
			// }

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("EXCEPTION CAUSED IN Saving Phase " + e.getMessage());
			currentSession.close();
			return id;
		} finally {
			// currentSession.close();
			currentSession.flush();
		}

	}

	public void delete(Phase phase) {

		logger.info("----PhaseDaoImpl start delete method-------------");
		Session currentSession = (Session) getEntityManager().getDelegate();
		if (currentSession == null)
			throw new RuntimeException("Session Object Cannot be null");

		try {
			currentSession.delete(phase);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("EXCEPTION CAUSED IN Deleting Phase " + e.getMessage());
			currentSession.close();
		} finally {
			currentSession.flush();
		}
		logger.info("----PhaseDaoImpl end delete method-------------");
	}

	public void update(Phase phase) {
		// TODO Auto-generated method stub
		logger.info("----PhaseDaoImpl start update method-------------");
		Session currentSession = (Session) getEntityManager().getDelegate();
		if (currentSession == null)
			throw new RuntimeException("Session Object Cannot be null");
		try {
			// String hql = "from Phase p where p.phasesName = :phasename";
			// Query query = currentSession.createQuery(hql);
			// query.setString("phasename", phase.getPhasesName());
			// Phase p = (Phase) query.uniqueResult();
			// if (p != null) {
			// System.out.println("Already Exists");
			// // a Phase with the same name as phase already exists
			// return false;
			// } else {
			currentSession.update(phase);
			// System.out.println("Already not Exists");
			// return true;
			// }

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("EXCEPTION CAUSED IN update Phase " + e.getMessage());
			currentSession.close();
		} finally {
			currentSession.flush();
		}
	}

	public boolean isAlreadyExist(String phaseName) {
		Session currentSession = (Session) getEntityManager().getDelegate();
		if (currentSession == null)
			throw new RuntimeException("Session Object Cannot be null");
		boolean isDuplicate = false;
		try {
			String hql = "from Phase p where p.phasesName = :phasename";
			Query query = currentSession.createQuery(hql);
			query.setString("phasename", phaseName);
			Phase p = (Phase) query.uniqueResult();
			if (p != null) {
				System.out.println("Already Exists");
				// a Phase with the same name as phase already exists
				isDuplicate = true;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("EXCEPTION CAUSED IN update Phase " + e.getMessage());
			currentSession.close();
			return false;
		} finally {
			currentSession.flush();
		}
		return isDuplicate;

	}

}
