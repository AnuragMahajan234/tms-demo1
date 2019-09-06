package org.yash.rms.dao.impl;

import java.text.ParseException;
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
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.yash.rms.dao.InfogramInactiveResourceDao;
import org.yash.rms.domain.InfogramInactiveResource;
import org.yash.rms.dto.InfogramInactiveResourceDTO;
import org.yash.rms.exception.DAOException;
import org.yash.rms.util.Constants;
import org.yash.rms.util.GenericCriteria;
import org.yash.rms.util.InfogramProcessStatusConstants;
import org.yash.rms.util.SearchCriteriaGeneric;

/**
 * Dao implementation for InfogramInactiveResourceDao.
 * 
 * @author samiksha.sant
 *
 */
@Repository
public class InfogramInactiveResourceDaoImpl implements InfogramInactiveResourceDao {

@PersistenceContext(name = "mysql", type = PersistenceContextType.TRANSACTION)
	private EntityManager entityManager;
	
	
	public EntityManager getEntityManager() {
		return entityManager;
	}
	
	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	private static final Logger logger = LoggerFactory.getLogger(InfogramInactiveResourceDaoImpl.class);

	public List<InfogramInactiveResource> getAllInfogramInactiveResources(SearchCriteriaGeneric searchCriteriaGeneric) {
		logger.info("---------------getAllInfogramInactiveResources method start-------------------");

		Session session = (Session) getEntityManager().getDelegate();
		List<InfogramInactiveResource> inactiveResources =null;
		try{
			if(searchCriteriaGeneric!=null){
			String sortColumnName = searchCriteriaGeneric.getISortColumn();
			if(sortColumnName.equalsIgnoreCase(InfogramInactiveResource.INFOID)) {
				sortColumnName = InfogramInactiveResource.CREATEDTIME;
				searchCriteriaGeneric.setiSortDir(Constants.DESC);
			}
			String sortedDir=searchCriteriaGeneric.getISortDir();
			searchCriteriaGeneric.setISortColumn(sortColumnName);
		
		Criteria criteria = session.createCriteria(InfogramInactiveResource.class);
		Criterion pending=Restrictions.ilike("processStatus", InfogramProcessStatusConstants.PENDING.toString());
		Criterion failure=Restrictions.ilike("processStatus", InfogramProcessStatusConstants.FAILURE.toString());
		//criteria.add(Restrictions.or(pending,failure));		
		criteria=GenericCriteria.createCriteria(searchCriteriaGeneric,criteria);
		
		inactiveResources = criteria.list();
	}
	} catch (Exception e) {
		logger.info("Exception occured in getAllInfogramActiveResources", e);
	}

		logger.info("_---------------getAllInfogramInactiveResources method end-------------------");

		return inactiveResources;
	}

	public InfogramInactiveResource getInfogramInactiveResourceById(Integer id) {
		logger.info("---------------getInfogramInactiveResourceById method start-------------------");

		Session session = (Session) getEntityManager().getDelegate();
		Criteria criteria = session.createCriteria(InfogramInactiveResource.class);
		criteria.add(Restrictions.idEq(id));
		InfogramInactiveResource inactiveResource = (InfogramInactiveResource) criteria.uniqueResult();

		logger.info("_---------------getInfogramInactiveResourceById method end-------------------");

		return inactiveResource;
	}

	//@Transactional
	public void updateInfogramInactiveResource(InfogramInactiveResource inactiveResource) {
		logger.info("---------------updateInfogramInactiveResource method start-------------------");

		Session session = (Session) getEntityManager().getDelegate();
		
		try {

			session.saveOrUpdate(inactiveResource);
		} catch (Exception e) {
			logger.error("----------Exception in updateInfogramInactiveResource----------------", e);
			throw new DAOException("512", e.getMessage());
		}

		logger.info("---------------updateInfogramInactiveResource method end-------------------");

	}
	
	public Long getTotalCountInactResources(SearchCriteriaGeneric searchCriteriaGeneric) {
		Long totalCount=0L;
		Session session = (Session) getEntityManager().getDelegate();
		Criteria criteria = session.createCriteria(InfogramInactiveResource.class);
		Criterion pending=Restrictions.ilike(Constants.PROCESSSTATUS, InfogramProcessStatusConstants.PENDING.toString());
	 	Criterion failure=Restrictions.ilike(Constants.PROCESSSTATUS, InfogramProcessStatusConstants.FAILURE.toString());
	 	//criteria.add(Restrictions.or(pending,failure));	 	
	 	try{
	 		searchCriteriaGeneric.setPage(null);
	 		searchCriteriaGeneric.setSize(null);
	 		searchCriteriaGeneric.setISortColumn(null);
	 		searchCriteriaGeneric.setiSortDir(null);
	 		criteria=GenericCriteria.createCriteria(searchCriteriaGeneric,criteria);	
	 	} catch (ParseException e1) {
		logger.error("Exception Occurred while counting resources in infogram and yash "+ e1.getMessage());
		return totalCount;
	 	}
	
		try {
		 		
				criteria.setProjection(Projections.rowCount());
				totalCount = (Long)criteria.uniqueResult();

		}catch (HibernateException e) {

			totalCount = 0L;
			e.printStackTrace();
			logger.error("Exception Occurred while counting Infogram Inactive resources in infogram "+ e.getMessage());
			throw e;
		}
		return totalCount;
	}
	
	public List<InfogramInactiveResource> getAllInfogramInactiveResources() {
		logger.info("-----------getAllInfogramInactiveResources method Start-----------------------");

		List<InfogramInactiveResource> infoInactiveResourceList = new ArrayList<InfogramInactiveResource>();
		Session session = (Session) getEntityManager().getDelegate();
		try {
						
			Criteria criteria = session.createCriteria(InfogramInactiveResource.class);
			session.enableFilter(InfogramInactiveResource.PENDING_FAILURE).setParameter(Constants.PROCESSSTATUS1,  InfogramProcessStatusConstants.PENDING.toString()).setParameter(Constants.PROCESSSTATUS2,  InfogramProcessStatusConstants.FAILURE.toString());
			criteria.addOrder(Order.asc("employeeId"));
			criteria.addOrder(Order.desc("createdTime"));
		
			 
			 
			infoInactiveResourceList = criteria.list();
		} catch (Exception e) {
			logger.info("Exception occured in getAllInfogramInactiveResources", e);
		}

		logger.info("-----------getAllInfogramInactiveResources method End-----------------------");
		return infoInactiveResourceList;
	}
	
	public List<InfogramInactiveResourceDTO> getAllInfogramInActiveResourcesReport() {

		logger.info("-----------getAllInfogramInActiveResourcesReport method Start-----------------------");

		Session session = (Session) getEntityManager().getDelegate();
		List<InfogramInactiveResourceDTO> infogramInActiveResourceList = new ArrayList<InfogramInactiveResourceDTO>();
		
		Date currentDate = new Date();
		Query query=null;
		
		try {

			query = session.createSQLQuery("  SELECT emp_id, emp_name, process_status, resigned_date, relieving_date FROM info_inactive_employee ");
					

			List<Object[]> objectList = query.list();

			if (!objectList.isEmpty()) {
				for (Object[] obj : objectList) {
					InfogramInactiveResourceDTO inActiveResource = new InfogramInactiveResourceDTO();
					int i = 0;
					inActiveResource.setEmployeeId((String) obj[i]);
					inActiveResource.setEmployeeName((String) obj[++i]);
					inActiveResource.setProcessStatus((String) obj[++i]);
					inActiveResource.setResignedDate((Date) obj[++i]);
					inActiveResource.setReleasedDate((Date) obj[++i]);
					infogramInActiveResourceList.add(inActiveResource);

				}
			}
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.debug(e.getMessage());
		}
		
		logger.info("-----------getAllInfogramInActiveResourcesReport method End-----------------------");
		return infogramInActiveResourceList;
	
	}
	


}
