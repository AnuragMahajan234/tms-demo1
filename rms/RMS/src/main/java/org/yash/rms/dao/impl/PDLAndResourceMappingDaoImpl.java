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
import org.yash.rms.dao.PDLAndResourceMappingDao;
import org.yash.rms.domain.PDLAndResourceMapping;

@Repository("pDLAndResourceMappingDao")
public class PDLAndResourceMappingDaoImpl implements PDLAndResourceMappingDao {

	@PersistenceContext(name = "mysql", type = PersistenceContextType.TRANSACTION)
	private EntityManager entityManager;
	
	
	public EntityManager getEntityManager() {
		return entityManager;
	}
	
	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	private static final Logger logger = LoggerFactory.getLogger(PDLAndResourceMappingDaoImpl.class);

	public Integer getResourceIdByPDLId(Integer pdl_id) {

		Session session = (Session) getEntityManager().getDelegate();
		
		Query query =  session.createQuery("SELECT employeeId FROM Resource "
				+ "WHERE employeeId IN "
				+ "( SELECT resourceID FROM PDLAndResourceMapping "
				+ "WHERE pdl_id LIKE :id)");
		
		query.setParameter("id", pdl_id );
		
		Integer resourceId = (Integer) query.uniqueResult();
		return resourceId;
	}

	public List<Integer> getPDLIdbyResourceId(Integer resourceId) {
		
		List<Integer> pdlsByResId = new ArrayList<Integer>();
		Session session = (Session) getEntityManager().getDelegate();
		
		Criteria criteria = session.createCriteria(PDLAndResourceMapping.class);
			criteria.add(Restrictions.eq("resourceID.employeeId", resourceId));
		
		List<PDLAndResourceMapping> pdlsList = criteria.list();
		
		if (pdlsList != null && !pdlsList.isEmpty()) {
			for (PDLAndResourceMapping pdlAndResourceMapping : pdlsList) {
				pdlsByResId.add(pdlAndResourceMapping.getPdl_id());
			}
		}
		
		return pdlsByResId;
	}
	
	public void deleteAll() {
		logger.info("--------PDLAndResourceMappingDaoImpl deleteAll method start-------");
		Session session = (Session) getEntityManager().getDelegate();
		try{
			Query query = session.createQuery("delete FROM PDLAndResourceMapping");
			query.executeUpdate();
		}catch (HibernateException ex) {
			logger.error("-------- Exception in PDLAndResourceMappingDaoImpl deleteAll method-------");
			ex.printStackTrace();
		}
		logger.info("--------PDLAndResourceMappingDaoImpl deleteAll method ended-------");
	}

	public void addResourcesInPDL(List<PDLAndResourceMapping> pdlResourceList) {
		logger.info("--------PDLAndResourceMappingDaoImpl addResourcesInPDL method start-------");
		Session session = (Session) getEntityManager().getDelegate();
		try{
			for(PDLAndResourceMapping pDLAndResourceMapping : pdlResourceList){
				session.merge(pDLAndResourceMapping);
			}
		}catch (Exception e) {
			logger.error("--------Excepion occured in PDLAndResourceMappingDaoImpl addResourcesInPDL -------");
			e.printStackTrace();
		}
		logger.info("--------PDLAndResourceMappingDaoImpl addResourcesInPDL method ended-------");
	}

}
