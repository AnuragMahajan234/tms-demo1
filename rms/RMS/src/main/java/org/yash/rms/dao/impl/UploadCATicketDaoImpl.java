package org.yash.rms.dao.impl;

import java.io.Serializable;
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
import org.yash.rms.dao.UploadCATicketDao;
import org.yash.rms.domain.CATicketDiscrepencies;
import org.yash.rms.domain.CATicketProcess;
import org.yash.rms.domain.Landscape;
import org.yash.rms.domain.Project;
import org.yash.rms.domain.Region;
import org.yash.rms.domain.Resource;
import org.yash.rms.domain.RootCause;
import org.yash.rms.domain.Unit;

@Repository("UploadCATicketDao")
@Transactional
public class UploadCATicketDaoImpl implements UploadCATicketDao {

	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory
			.getLogger(ResourceDaoImpl.class);

	
	@PersistenceContext(name = "mysql", type = PersistenceContextType.TRANSACTION)
	private EntityManager entityManager;
	
	
	public EntityManager getEntityManager() {
		return entityManager;
	}
	
	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	List<CATicketProcess> caticket = null;

	public Unit getUnitByUnitName(String unitName) {
		Session currentSession = (Session) getEntityManager().getDelegate();
		String hql = "from Unit where unitName=:unitName";
		Query query = currentSession.createQuery(hql);
		query.setParameter("unitName", unitName);
		query.setMaxResults(1);
		Unit unit = (Unit) query.uniqueResult();
		return unit;
	}

	public Landscape getLandscapeByName(String landscapeName) {
		Session currentSession = (Session) getEntityManager().getDelegate();
		String hql = "from Landscape where landscapeName=:landscapeName";
		Query query = currentSession.createQuery(hql);
		query.setParameter("landscapeName", landscapeName);
		// Criteria criteria =
		// currentSession.createCriteria(Landscape.class).add(
		// Restrictions.eq("landscapeName", landscapeName));
		query.setMaxResults(1);
		Landscape landscape = (Landscape) query.uniqueResult();
		return landscape;
	}

	public Project getModuleByModuleName(String moduleName) {
		Session currentSession = (Session) getEntityManager().getDelegate();
		String hql = "from Project where moduleName=:moduleName";
		Query query = currentSession.createQuery(hql);
		query.setParameter("moduleName", moduleName);
		// Criteria criteria = currentSession.createCriteria(Project.class).add(
		// Restrictions.eq("moduleName", moduleName));
		query.setMaxResults(1);
		Project project = (Project) query.uniqueResult();
		return project;
	}

	public Resource getEmployeeByRECFId(String recfId) {
		Resource resource = null;
		if (recfId != null && !recfId.equals("")) {
			Session currentSession = (Session) getEntityManager().getDelegate();
			String hql = "from Resource where customer_id_detail=:recfId";
			Query query = currentSession.createQuery(hql);
			query.setParameter("recfId", recfId);
			// Criteria criteria =
			// currentSession.createCriteria(Resource.class).add(
			// Restrictions.eq("customer_id_detail", recfId));
			query.setMaxResults(1);
			resource = (Resource) query.uniqueResult();
		}
		return resource;
	}

	public RootCause getRootCauseByRootCauseName(String rootCauseName) {
		Session currentSession = (Session) getEntityManager().getDelegate();
		String hql = "from RootCause where rootCause=:rootCauseName";
		Query query = currentSession.createQuery(hql);
		query.setParameter("rootCauseName", rootCauseName);
		// Criteria criteria =
		// currentSession.createCriteria(RootCause.class).add(
		// Restrictions.eq("rootCause", rootCauseName));
		query.setMaxResults(1);
		RootCause rootCause = (RootCause) query.uniqueResult();
		return rootCause;
	}

	public Serializable saveDiscrepencies(
			CATicketDiscrepencies caTicketDiscrepencies) {
		System.out.println("Saving CATicketDiscrepencies");
		Session currentSession = (Session) getEntityManager().getDelegate();
		Serializable id = null;
		try {
			id = currentSession.save(caTicketDiscrepencies);
		} catch (HibernateException e) {
			e.printStackTrace();
			logger.error("Exception occured in copyUserProfileToResource method at DAO layer:-"
					+ e);

		}
		logger.info("--------ResourceDaoImpl copyUserProfileToResource method end-------");
		return id;
	}
	
	@SuppressWarnings("unchecked")
	public List<CATicketDiscrepencies> findAllDiscrepencyTickets() {
		Session currentSession = (Session) getEntityManager().getDelegate();
		Criteria criteria = currentSession.createCriteria(CATicketDiscrepencies.class);
		List<CATicketDiscrepencies> list = criteria.list();
		return list;
	}

	public CATicketDiscrepencies findDiscrepenciesTicketById(int id) {
		Session currentSession = (Session) getEntityManager().getDelegate();
		Criteria criteria = currentSession.createCriteria(CATicketDiscrepencies.class).add(
				Restrictions.eq("id", id));
		CATicketDiscrepencies ticketDiscrepencies = (CATicketDiscrepencies) criteria.uniqueResult();
		return ticketDiscrepencies;
	}

	public Region findRegionByUnit(int unitId) {
		Session currentSession = (Session) getEntityManager().getDelegate();
		Criteria criteria = currentSession.createCriteria(Region.class).add(
				Restrictions.eq("unitId.id", unitId));
		Region region = (Region) criteria.uniqueResult();
		return region;
	}
}
