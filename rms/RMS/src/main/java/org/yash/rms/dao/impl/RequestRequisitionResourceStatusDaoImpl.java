package org.yash.rms.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.yash.rms.dao.RequestRequisitionResourceStatusDao;
import org.yash.rms.domain.RequestRequisitionResourceStatus;

@Repository("requestRequisitionResourceStatusDao")
public class RequestRequisitionResourceStatusDaoImpl implements RequestRequisitionResourceStatusDao{

	private static final Logger logger = LoggerFactory.getLogger(RequestRequisitionResourceStatusDaoImpl.class);
	
	@PersistenceContext(name = "mysql", type = PersistenceContextType.TRANSACTION)
	private EntityManager entityManager;
	
	
	public EntityManager getEntityManager() {
		return entityManager;
	}
	
	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public RequestRequisitionResourceStatus findById(Integer id) {
		
		logger.info("RequestRequisitionResourceStatusDaoImpl:findById Starts");
		RequestRequisitionResourceStatus requestRequisitionResourceStatus = null;	
		Session session = (Session) getEntityManager().getDelegate();
		
		try {
			Criteria criteria = session.createCriteria(RequestRequisitionResourceStatus.class);
			criteria.add(Restrictions.eq("id", id.intValue()));
			requestRequisitionResourceStatus = (RequestRequisitionResourceStatus) criteria.uniqueResult();
		}catch (HibernateException e) {
			e.printStackTrace();
			logger.error("RequestRequisitionResourceStatusDaoImpl:findById Error"+e.getMessage());
		}
		logger.info("RequestRequisitionResourceStatusDaoImpl:findById Ends");
		return requestRequisitionResourceStatus;
		}
	}


