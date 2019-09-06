package org.yash.rms.dao.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.cxf.jaxrs.ext.search.SearchContext;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.yash.rms.dao.InfogramActiveResourceDao;
import org.yash.rms.domain.InfogramActiveResource;
import org.yash.rms.domain.Resource;
import org.yash.rms.dto.InfogramActiveResourceDTO;
import org.yash.rms.exception.DAOException;
import org.yash.rms.exception.DaoRestException;
import org.yash.rms.namedquery.RmsNamedQuery;
import org.yash.rms.rest.dao.generic.impl.HibernateGenericDao;
import org.yash.rms.rest.utils.ExceptionUtil;
import org.yash.rms.util.Constants;
import org.yash.rms.util.GenericCriteria;
import org.yash.rms.util.HibernateUtil;
import org.yash.rms.util.InfogramProcessStatusConstants;
import org.yash.rms.util.SearchCriteriaGeneric;

@Repository

public class InfogramActiveResourceDaoImpl extends HibernateGenericDao<Integer, InfogramActiveResource> implements InfogramActiveResourceDao {

	public InfogramActiveResourceDaoImpl( ) {
		super(InfogramActiveResource.class);
	}


	

	private static final Logger logger = LoggerFactory.getLogger(InfogramActiveResourceDaoImpl.class);

	/**
	 * Will return only pending infogram resources. 
	 */
	public List<InfogramActiveResource> getAllInfogramActiveResources() {
		logger.info("-----------getAllInfogramActiveResources method Start-----------------------");

		List<InfogramActiveResource> infoActiveResourceList = new ArrayList<InfogramActiveResource>();
		Session session = (Session) getEntityManager().getDelegate();
		try {
			Criteria criteria = session.createCriteria(InfogramActiveResource.class);
			 Criterion pending=Restrictions.ilike("processStatus", InfogramProcessStatusConstants.PENDING.toString());
			 Criterion failure=Restrictions.ilike("processStatus", InfogramProcessStatusConstants.FAILURE.toString());
			 criteria.add(Restrictions.or(pending,failure));
			 criteria.addOrder(Order.asc("employeeId"));
			 criteria.addOrder(Order.desc(InfogramActiveResource.CREATEDTIMESTAMP));
			 
			 infoActiveResourceList = criteria.list();
		} catch (Exception e) {
			logger.info("Exception occured in getAllInfogramActiveResources", e);
		}

		logger.info("-----------getAllInfogramActiveResources method End-----------------------");
		return infoActiveResourceList;
	}
	public List<InfogramActiveResource> getAllInfogramActiveResources(String resourceType, String processStatus,String recordStatus,SearchCriteriaGeneric searchCriteriaGeneric) {
		logger.info("-----------getAllInfogramActiveResources method Start-----------------------");

		List<InfogramActiveResource> infoActiveResourceList = new ArrayList<InfogramActiveResource>();
		Session session = (Session) getEntityManager().getDelegate();
		try {
			String sortColumnName = searchCriteriaGeneric.getISortColumn();
			if(sortColumnName.equalsIgnoreCase(InfogramActiveResource.INFOID)) {
				sortColumnName = InfogramActiveResource.CREATEDTIMESTAMP;
				searchCriteriaGeneric.setiSortDir(Constants.DESC);
			}
			searchCriteriaGeneric.setISortColumn(sortColumnName);
			
			Criteria criteria = session.createCriteria(InfogramActiveResource.class);
			
			// Filter for processStatus - Discard and Success
			if (processStatus.equalsIgnoreCase(Constants.ALL)) {
				Criterion pending = Restrictions.ilike(Constants.PROCESSSTATUS,
						InfogramProcessStatusConstants.PENDING.toString());
				Criterion failure = Restrictions.ilike(Constants.PROCESSSTATUS,
						InfogramProcessStatusConstants.FAILURE.toString());
				criteria.add(Restrictions.or(pending, failure));

			} else {
				session.enableFilter(InfogramActiveResource.DISCARD_SUCCESS_PENDING_FAILURE).setParameter(Constants.PROCESSSTATUS, processStatus);
			}
			
			 criteria=GenericCriteria.createCriteria(searchCriteriaGeneric,criteria);
			 
			 if(resourceType.equalsIgnoreCase(Constants.RESOURCE_TYPE_NEW)) {
					
					 session.enableFilter(InfogramActiveResource.NEWRESOURCE);
					
				}else if(resourceType.equalsIgnoreCase(Constants.RESOURCE_TYPE_EXISTING)) {
					
					session.enableFilter(InfogramActiveResource.EXISTINGRESOURCE);
					}
			if(recordStatus.equalsIgnoreCase(Constants.RESOURCE_STATUS_ALLOK)){
				
				session.enableFilter(InfogramActiveResource.RESOURCE_STATUS_OK).setParameter(Constants.RECORDSTATUS, true);
			}
			 
			 
			 infoActiveResourceList = criteria.list();
		} catch (Exception e) {
			logger.info("Exception occured in getAllInfogramActiveResources", e);
		}

		logger.info("-----------getAllInfogramActiveResources method End-----------------------");
		return infoActiveResourceList;
	}

	public InfogramActiveResource getInfogramActiveResourceById(Integer id) throws DAOException {
		logger.info("-----------getInfogramActiveResourceById method Start-----------------------");

		Session session = (Session) getEntityManager().getDelegate();
		InfogramActiveResource infogramActiveResource = new InfogramActiveResource();
		try {
			Criteria criteria = session.createCriteria(InfogramActiveResource.class);
			criteria.add(Restrictions.idEq(id));
			
			infogramActiveResource = (InfogramActiveResource) criteria.uniqueResult();
		} catch (Exception ex) {
			logger.error("----------Exception in getInfogramActiveResourceById----------------", ex.getMessage());
			throw new DAOException("512", ex.getMessage());
			
		}
		logger.info("-----------getInfogramActiveResourceById method End-----------------------");

		return infogramActiveResource;
	}


	public void updateInfogramObject(InfogramActiveResource infogramActiveResource) throws DAOException {
		
		logger.info("-----------updateInfogramObject method Start-----------------------");
		Session session = (Session) getEntityManager().getDelegate();
		try {
			
			session.saveOrUpdate(infogramActiveResource);
		} catch (Exception e) {
			logger.error("----------Exception in updateInfogramObject----------------", e);
			throw new DAOException("512", e.getMessage());
		}
		logger.info("-----------updateInfogramObject method End-----------------------");

	}

	
	@Override
	public InfogramActiveResource create(InfogramActiveResource infogramActiveResource) throws DaoRestException{
		
		
		return super.create(infogramActiveResource);
	}
	
	
	
	@Override
	public InfogramActiveResource update(InfogramActiveResource infogramActiveResource) throws DaoRestException{
		
		
		return super.update(infogramActiveResource);
	}
	
	
	public void updateInfogramResourceToDiscarded(String id, Resource resource) throws DAOException {
		logger.info("-----------updateInfogramResourceToDiscarded method Start-----------------------");
		Session session = (Session) getEntityManager().getDelegate();
		String middleName=resource.getMiddleName().length()>0?resource.getMiddleName()+" ":"";
		String name = resource.getFirstName()+" "+middleName+resource.getLastName();
		Date timeStamp = new Date();
		try {
			Query query =session.createQuery("update InfogramActiveResource iar SET processStatus= :status, modifiedName=:modifiedName, modifiedBy=:modifiedBy, modifiedTime=:modifiedTime where id=" + id);
			query.setParameter("status", InfogramProcessStatusConstants.DISCARD.toString());
			query.setParameter("modifiedName", name);
			query.setParameter("modifiedBy", resource.getEmployeeId()+"");
			query.setParameter("modifiedTime",timeStamp);
			query.executeUpdate();
		}catch (Exception e) {
			logger.error("----------Exception in getInfogramActiveResourceById----------------", e.getMessage());
			throw new DAOException("512", e.getMessage());
			
		}
		logger.info("-----------updateInfogramResourceToDiscarded method End-----------------------");

	}
	
	public Long getTotalCountInfogramResource(String status, String processStatus,SearchCriteriaGeneric searchCriteriaGeneric) {
		Long totalCount=0L;
		//Query query =null;
		Session session = (Session) getEntityManager().getDelegate();
		Criteria criteria = session.createCriteria(InfogramActiveResource.class);
		
		// Filter for processStatus - Discard and Success		
		if (processStatus.equalsIgnoreCase(Constants.ALL)) {
			Criterion pending = Restrictions.ilike(Constants.PROCESSSTATUS,
					InfogramProcessStatusConstants.PENDING.toString());
			Criterion failure = Restrictions.ilike(Constants.PROCESSSTATUS,
					InfogramProcessStatusConstants.FAILURE.toString());
			criteria.add(Restrictions.or(pending, failure));

		} else {
			session.enableFilter(InfogramActiveResource.DISCARD_SUCCESS_PENDING_FAILURE).setParameter(Constants.PROCESSSTATUS, processStatus);
		}
	 	try {
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
			if (status.equalsIgnoreCase(Constants.RESOURCE_TYPE_NEW)) {
				session.enableFilter(InfogramActiveResource.NEWRESOURCE);

			} else if (status.equalsIgnoreCase(Constants.RESOURCE_TYPE_EXISTING)) {

				session.enableFilter(InfogramActiveResource.EXISTINGRESOURCE);
			}
			
				criteria.setProjection(Projections.rowCount());
				totalCount = (Long)criteria.uniqueResult();
		}catch (HibernateException e) {

			totalCount = 0L;
			e.printStackTrace();
			logger.error("Exception Occurred while counting resources in infogram and yash "+ e.getMessage());
			return totalCount;
		}
		return totalCount;
	}
	
	public List<InfogramActiveResourceDTO> getAllInfogramActiveResourcesReport() {
		logger.info("-----------getInfogramActiveResourcesReport method Start-----------------------");
		Session session = (Session) getEntityManager().getDelegate();
		List<InfogramActiveResourceDTO> infogramActiveResourceList = new ArrayList<InfogramActiveResourceDTO>();

		
		Date currentDate = new Date();
		Query query=null;
		
		
		try {

			String queryString=RmsNamedQuery.getInfoReportQuery();
			query =session.createSQLQuery(queryString);

			List<Object[]> objectList = query.list();

			if (!objectList.isEmpty()) {
				for (Object[] obj : objectList) {
					InfogramActiveResourceDTO activeResource = new InfogramActiveResourceDTO();
					int i = 0;
					
					activeResource.setEmployeeId((String) obj[i]);
					activeResource.setName((String) obj[++i]);
					activeResource.setProcessStatus((String) obj[++i]);
					activeResource.setStatus((String) obj[++i]);
					activeResource.setDateOfJoining((Date) obj[++i]);
					
					activeResource.setEmailId((String) obj[++i]);
					activeResource.setDesignation((String) obj[++i]);
					activeResource.setRmsDesignation((String) obj[++i]);
					activeResource.setBaseLocation((String) obj[++i]);
					activeResource.setLocationInRMS((String) obj[++i]);
					
					activeResource.setCurrentLocation((String) obj[++i]);
					activeResource.setCurrentLocationInRMS((String) obj[++i]);
					activeResource.setBusinessGroup((String) obj[++i]);
					activeResource.setBusinessUnit((String) obj[++i]);
					activeResource.setIrmName((String) obj[++i]);
					
					activeResource.setIrmInRMS((String) obj[++i]);
					activeResource.setSrmName((String) obj[++i]);
					activeResource.setSrmInRMS((String) obj[++i]);
					activeResource.setCreationTimestamp((Date) obj[++i]);
					activeResource.setModifiedBy((String) obj[++i]);
					
					activeResource.setIrmEmployeeId((String) obj[++i]);
					activeResource.setSrmEmployeeId((String) obj[++i]);
					activeResource.setRmsIrmEmployeeId((String) obj[++i]);
					activeResource.setRmsSrmEmployeeId((String) obj[++i]);
					
					activeResource.setProjectBGBU((String) obj[++i]);
					activeResource.setInfoResourceBGBU((String) obj[++i]);
					activeResource.setRmsResourceBGBU((String) obj[++i]);
					activeResource.setInfoIRMBGBU((String) obj[++i]);
					activeResource.setInfoSRMBGBU((String) obj[++i]);
					
					activeResource.setRmsIRMBGBU((String) obj[++i]);
					activeResource.setRmsSRMBGBU((String) obj[++i]);
					activeResource.setRmsBg((String) obj[++i]);
					activeResource.setRmsBu((String) obj[++i]);
			
					
					infogramActiveResourceList.add(activeResource);

				}
			}
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.debug(e.getMessage());
		}
		
		logger.info("-----------getInfogramActiveResourcesReport method End-----------------------");
		return infogramActiveResourceList;
	}

	public List<InfogramActiveResource> searchWithLimitAndOrderBy(SearchContext ctx, Integer maxLimit, Integer minLimit,
			String orderby, String orderType) throws DaoRestException {
	try {
		return super.search(ctx, maxLimit, minLimit, orderby, orderType);
		
		
	}catch(Exception e) {
		logger.error("Error Inside  @class :"+this.getClass().getName()+" @Method :searchWithLimitAndOrderBy "+e.getMessage());
		throw new DaoRestException(ExceptionUtil.generateExceptionCode("Dao","InfogramActiveResource",e));
	}
	}
	
	public Long count(SearchContext ctx) throws DaoRestException {
	try {
		return super.count(ctx);
		
		
	}catch(Exception e) {
		logger.error("Error Inside  @class :"+this.getClass().getName()+" @Method :count "+e.getMessage());
		throw new DaoRestException(ExceptionUtil.generateExceptionCode("Dao","InfogramActiveResource",e));
	}
	}
	public List<InfogramActiveResource> searchWithLimit(SearchContext context, Integer maxLimit, Integer minLimit) throws DaoRestException {
		try {
			return super.search(context, maxLimit, minLimit);
		}catch(Exception e) {
			logger.error("Error Inside  @class :"+this.getClass().getName()+" @Method :searchWithLimit"+e.getMessage());
			throw new DaoRestException(ExceptionUtil.generateExceptionCode("Dao","InfogramActiveResource",e));
		}
	}
	
	/**
	 * Delete by pk.
	 *
	 * @param entityPk the entity pk
	 * @throws DaoException the dao exception
	 * @see com.inn.decent.dao.generic.impl.JPABaseDao.deleteByPk(java.lang.Object)
	 */
	@Override
	public void deleteByPk(Integer entityPk) throws DaoRestException{
		logger.debug("deleting Folder by pk : "+entityPk);
		try {
			super.deleteByPk(entityPk);
		} catch (DaoRestException e) {
			logger.error("Error  occurred  @class"   + this.getClass().getName()  , e);
			throw new DaoRestException(e);
	
		}
	}

}
