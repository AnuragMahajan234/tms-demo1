package org.yash.rms.dao.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.servlet.http.HttpServletRequest;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.yash.rms.dao.RequestRequisitionSkillDao;
import org.yash.rms.domain.RequestRequisition;
import org.yash.rms.domain.RequestRequisitionSkill;
import org.yash.rms.exception.DAOException;
import org.yash.rms.util.Constants;
import org.yash.rms.util.GenericCriteria;
import org.yash.rms.util.SearchCriteriaGeneric;

@Repository("requestRequisitionSkillDao")
public class RequestRequisitionSkillDaoImpl implements RequestRequisitionSkillDao {

	private static final Logger logger = LoggerFactory.getLogger(RequestRequisitionSkillDaoImpl.class);
	

@PersistenceContext(name = "mysql", type = PersistenceContextType.TRANSACTION)
	private EntityManager entityManager;
	
	
	public EntityManager getEntityManager() {
		return entityManager;
	}
	
	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	@Transactional(propagation = Propagation.SUPPORTS, isolation = Isolation.READ_COMMITTED)
	public RequestRequisitionSkill findRequestRequisitionSkillBySkillId(Integer requestRequisitionSkillId) {
		Session session = (Session) getEntityManager().getDelegate();
		RequestRequisitionSkill requestRequisitionSkill = null;
		try {
			session.flush();
			Query query = session.getNamedQuery("RequestRequisitionSkill.findByRequestRequisitionSkillId");
			query.setParameter("id", requestRequisitionSkillId);
			requestRequisitionSkill = (RequestRequisitionSkill) query.uniqueResult();
		} catch (HibernateException e) {
			e.printStackTrace();
			throw e;
		} finally {
			session.flush();
		}

		return requestRequisitionSkill;
	}

	@Transactional(propagation = Propagation.SUPPORTS, isolation = Isolation.READ_COMMITTED)
	public List<RequestRequisitionSkill> findRequestRequisitionSkillsByRequestRequisitionSkillIds(List<Integer> requestRequisitionSkillsIds) {

		List<RequestRequisitionSkill> requestRequisitionSkillList = new ArrayList<RequestRequisitionSkill>();
		Session session = (Session) getEntityManager().getDelegate();
		try {
			session.flush();
			Query query = session.getNamedQuery("RequestRequisitionSkill.FIND_SKILLRESOURCES");
			query.setParameterList("id", requestRequisitionSkillsIds);
			requestRequisitionSkillList = query.list();
		} catch (HibernateException e) {

			e.printStackTrace();
			throw e;

		} finally {
			session.flush();
		}

		return requestRequisitionSkillList;
	}

	public List<RequestRequisitionSkill> findRequestRequisitionSkillsByRequestRequisitionId(int requestRequisitionId) {
		List<RequestRequisitionSkill> requestRequisitionSkillList = new ArrayList<RequestRequisitionSkill>();
		Session session = (Session) getEntityManager().getDelegate();
		Query query = session.createQuery("FROM RequestRequisitionSkill requestRequisitionSkill WHERE requestRequisitionSkill.requestRequisition.id = :requestRequisition");
		query.setParameter("requestRequisition", requestRequisitionId);
		requestRequisitionSkillList = query.list();
		return requestRequisitionSkillList;
	}

	public List<RequestRequisitionSkill> editReqId(Integer id) {

		List<RequestRequisitionSkill> requestReportList = new ArrayList<RequestRequisitionSkill>();
		Session session = (Session) getEntityManager().getDelegate();
		try {

			Criteria criteria = session.createCriteria(RequestRequisitionSkill.class);
			Criteria criteriaForRequestObject = session.createCriteria(RequestRequisition.class)
					.setProjection(Projections.projectionList().add(Projections.property("id"), "id").add(Projections.property("Name"), "emp_id"));
			criteriaForRequestObject.add(Restrictions.eq("id", id));
			criteria.add(Restrictions.eq("id", id));
			requestReportList = criteria.list();

		} catch (HibernateException e) {
			e.printStackTrace();
			throw e;
		}
		return requestReportList;

	}

	@Transactional(propagation = Propagation.SUPPORTS)
	public String save(RequestRequisitionSkill requestRequisitionSkill) {

		Session currentSession = (Session) getEntityManager().getDelegate();
		
		try {
			
			
			if (requestRequisitionSkill.getId() > 0) {
				RequestRequisitionSkill requestRequisitionSkillTemp =  findRequestRequisitionSkillBySkillId(requestRequisitionSkill.getId());
				if(!requestRequisitionSkillTemp.getRequirementId().contains(requestRequisitionSkill.getRequirementId().trim())){
					String maxRequirementID = getMaxRequirementId(requestRequisitionSkill.getRequirementId());
					if(maxRequirementID != null){
						String test = appendCountToRequirementId(maxRequirementID);
						System.out.println(test);
						requestRequisitionSkill.setRequirementId(test);
					} else {
						String reqId=requestRequisitionSkill.getRequirementId();
						String reqNameWithID = reqId+"001";
						requestRequisitionSkill.setRequirementId(reqNameWithID);
					}
				}else{
					requestRequisitionSkill.setRequirementId(requestRequisitionSkillTemp.getRequirementId());
				}
				currentSession.merge(requestRequisitionSkill);
			} else {
				
				String maxRequirementID = getMaxRequirementId(requestRequisitionSkill.getRequirementId());
				
				if(maxRequirementID != null){
					String test = appendCountToRequirementId(maxRequirementID);
					requestRequisitionSkill.setRequirementId(test);
					
				} else {
					String reqId=requestRequisitionSkill.getRequirementId();
					String reqNameWithID = reqId+"001";
					requestRequisitionSkill.setRequirementId(reqNameWithID);
				}

				currentSession.merge(requestRequisitionSkill);
			}
		} catch (HibernateException e) {

			e.printStackTrace();
			throw e;
		} finally {
			currentSession.flush();
		}
		return requestRequisitionSkill.getRequirementId();
	}
	
	
	/*@Transactional(propagation = Propagation.SUPPORTS)
	public String save(RequestRequisitionSkill requestRequisitionSkill) {
		Session currentSession = (Session) getEntityManager().getDelegate();
		try {
			// update RRF Case
			if (requestRequisitionSkill.getId() > 0) {
				String exstingReqID = this.findRequirementIdBySkillId(requestRequisitionSkill.getId());
				if (!exstingReqID.trim().equals(requestRequisitionSkill.getRequirementId().trim())) {
					requestRequisitionSkill.setRequirementId(exstingReqID.trim());
				}
				currentSession.merge(requestRequisitionSkill);
			} else {
				// create RRF Case
				currentSession.merge(requestRequisitionSkill);
			}
		} catch (HibernateException e) {
			e.getMessage();
			e.printStackTrace();
			throw e;
		} finally {
			currentSession.flush();
		}
		return requestRequisitionSkill.getRequirementId();
	}*/
	
	private String appendCountToRequirementId(String reqID){
		int lastIndexOfHyphen =  reqID.lastIndexOf("-");
		int lastCount = Integer.parseInt(reqID.substring(lastIndexOfHyphen+1));
		lastCount++;
		String stringFormatOfLastCount = String.format("%03d", lastCount);
		
		
		String binaIDwalaId = reqID.substring(0, lastIndexOfHyphen+1);
		binaIDwalaId = binaIDwalaId+""+stringFormatOfLastCount;
		
		return binaIDwalaId;
		
		
	}
	
	private String getMaxRequirementId(String reqId){
		
		Session session = (Session) getEntityManager().getDelegate();

		/*Query query = session.createQuery("select requirementId from RequestRequisitionSkill  where req_id LIKE :reqID ORDER BY requirementId DESC LIMIT 1");*/
		Query query = session.createQuery("select requirementId from RequestRequisitionSkill  where req_id LIKE :reqID ORDER BY requirementId DESC");
		query.setParameter("reqID", reqId+"%");
		query.setMaxResults(1);
		String reqID = (String) query.uniqueResult();
		return reqID;
		
		
	}



	@Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED)
	
	public RequestRequisitionSkill updateRequestRequisitionSkillBySkillId(Integer requestRequisitionSkillId, String[] sent, String [] pdl, String rrfForwardComment) {

		Session session = (Session) getEntityManager().getDelegate();

		 RequestRequisitionSkill requestRequisitionSkill = null;
		 String sentList="";
		 String pdlList="";

		 try {

			 session.flush();
  
			 Query query = session.getNamedQuery("RequestRequisitionSkill.findByRequestRequisitionSkillId");
			 	query.setParameter("id", requestRequisitionSkillId);
   
			 requestRequisitionSkill = (RequestRequisitionSkill) query.uniqueResult();
			 if(sent != null && sent.length > 0) {
				 for(int i=0; i<sent.length; i++)
				 {
					 sentList+=sent[i] + ",";
				 }
			 }
			 System.out.println("-----------------------updateRequestRequisitionSkillBySkillId---------------------: " + sentList);
			 if(pdl!=null&&pdl.length>0){
				 for(int i=0;i<pdl.length;i++)
				 {
					 pdlList+=pdl[i]+",";
				 }
			 }
	  requestRequisitionSkill.setSent_req_id(sentList);
	  requestRequisitionSkill.setPdl_list(pdlList);
	  requestRequisitionSkill.setRrfForwardComment(rrfForwardComment);
	  
	  session.merge(requestRequisitionSkill);
	  System.out.println("----------------------request ids : ------------------"+requestRequisitionSkill.getSent_req_id());
 } catch (HibernateException e) {
  e.printStackTrace();
  throw e;
 } finally {
  session.flush();
 }

 return requestRequisitionSkill;
}

	public String findRequirementIdBySkillId(final int id) {

		Session session = (Session) getEntityManager().getDelegate();
		try {
			session.flush();
			Criteria criteria = session.createCriteria(RequestRequisitionSkill.class);
			criteria.add(Restrictions.eq("id", id));
			criteria.setProjection(Projections.projectionList().add(Projections.property("requirementId"), "requirementId"));
			return (String) criteria.uniqueResult();
		} catch (HibernateException e) {
			e.printStackTrace();
			throw e;
		} finally {
			session.flush();
		}
	}

	public boolean saveOrUpdate(RequestRequisitionSkill requisitionSkill)  {
		logger.info("--------RequestRequisitionSkillDaoImpl saveOrUpdate method start-------");
		boolean isSuccess = false;
		if (null == requisitionSkill || requisitionSkill.getId() <= 0 )
			return false;
		try {
			Session currentSession = (Session) getEntityManager().getDelegate();
			currentSession.saveOrUpdate(requisitionSkill);
			isSuccess = true;
		} catch (HibernateException hex) {
			isSuccess = false;
			logger.error("Exception occured in RequestRequisitionSkillDaoImpl saveOrUpdate method at DAO layer:-"
					+ hex);
			hex.printStackTrace();
			throw new DAOException(DAOException._SQL_ERROR, hex.getMessage());
		} catch (Exception ex) {
			isSuccess = false;
			logger.error("Exception occured in RequestRequisitionSkillDaoImpl saveOrUpdate method at DAO layer:-"
					+ ex);
			ex.printStackTrace();
			throw new DAOException(DAOException._UPDATE_FAILED, ex.getMessage());
		}
		logger.info("--------RequestRequisitionSkillDaoImpl saveOrUpdate method End-------");
		return isSuccess;
	}

	public List<RequestRequisitionSkill> getRequestRequisitionList(Integer userId, String userRole, List<Integer> projectList,HttpServletRequest httpRequest,SearchCriteriaGeneric searchCriteriaGeneric) {
		logger.info("-----------getRequestRequisitionList DaoImpl method Start-----------------------");
		
		List<RequestRequisitionSkill> rrfList = new ArrayList<RequestRequisitionSkill>();
		Session session = (Session) getEntityManager().getDelegate();
		Criteria criteria = session.createCriteria(RequestRequisitionSkill.class);
		
		enableFilter(projectList,userId,userRole);
		
		try {
			//String sortColumnName = searchCriteriaGeneric.getISortColumn();
			String sortColumnName = RequestRequisitionSkill.RRFID;
			searchCriteriaGeneric.setISortColumn(sortColumnName);
			searchCriteriaGeneric.setiSortDir(Constants.DESC);
			criteria=GenericCriteria.createCriteria(searchCriteriaGeneric,criteria);
			rrfList = criteria.list();
		}catch(Exception e) {
			logger.info("Exception occured in getRequestRequisitionList", e);
		}
		logger.info("-----------getRequestRequisitionList DaoImpl method Start-----------------------");
		return rrfList;
		
	}
	private void enableFilter(List<Integer> projectList,Integer userId, String userRole) {
		logger.info("-----------enableFilter DaoImpl method Start-----------------------");
		List<Integer> prjList = new ArrayList<Integer>();
		Session session = (Session) getEntityManager().getDelegate();
		session.enableFilter(RequestRequisitionSkill.IS_DELETED).setParameter(Constants.IS_DELETED, false);
		if(projectList == null || projectList.isEmpty()){
			prjList.add(0);
		}else {
			prjList.addAll(projectList);
		}
		//filter for project List Check
		if(!userRole.equalsIgnoreCase(Constants.ROLE_ADMIN)){
			
			//session.enableFilter(RequestRequisitionSkill.SENDEMAILID).setParameter(Constants.SENDEMAILID, userId);
			session.enableFilter(RequestRequisitionSkill.COMMON_OR_CONDITION_WITH_SENTEMAIL_IN_AND).setParameterList(Constants.REQ_PROJ_ID, prjList).setParameter(Constants.SENDEMAILID, userId).setParameter(Constants.TONOTIFYID, userId).setParameter(Constants.RRFEMPLOYEEID, userId);
		}
		
		
		logger.info("-----------enableFilter DaoImpl method End-----------------------");
	}


	public Long getTotalCountRRF(Integer userId,String userRole,List<Integer> projectList,SearchCriteriaGeneric searchCriteriaGeneric) {
		logger.info("-----------getTotalCountRRF method start");
		Long totalCount=0L;
		Session session = (Session) getEntityManager().getDelegate();
		Criteria criteria = session.createCriteria(RequestRequisitionSkill.class);
		enableFilter(projectList,userId,userRole);
		
		try {
	 		searchCriteriaGeneric.setPage(null);
	 		searchCriteriaGeneric.setSize(null);
	 		searchCriteriaGeneric.setISortColumn(null);
	 		searchCriteriaGeneric.setiSortDir(null);
			criteria=GenericCriteria.createCriteria(searchCriteriaGeneric,criteria);

		} catch (ParseException e1) {
			logger.error("Exception Occurred while counting RRF in RequestRequisition "+ e1.getMessage());
			return totalCount;
		}
		try {
			criteria.setProjection(Projections.rowCount());
			totalCount = (Long)criteria.uniqueResult();
		}catch (HibernateException e) {

			totalCount = 0L;
			e.printStackTrace();
			logger.error("Exception Occurred while counting RRF in RequestRequisition "+ e.getMessage());
			return totalCount;
		}
		logger.info("-----------getTotalCountRRF method End");
		return totalCount;
	}

	public RequestRequisitionSkill getRequestRequisitionSkillByRRFId(String requirementId) {
		logger.info("-------get getRequestRequisitionSkillByRRFId method start ------- Requirement Id is "+requirementId);
		Session session = (Session) getEntityManager().getDelegate();
		Criteria criteria = session.createCriteria(RequestRequisitionSkill.class);
		RequestRequisitionSkill requestRequisitionSkill  = new RequestRequisitionSkill(); 
		try {
			 requestRequisitionSkill = (RequestRequisitionSkill) criteria.add(Restrictions.eq("requirementId", requirementId)).uniqueResult();
		} catch (Exception exception) {
			logger.error("Error while retrieving RequestRequisitionSkill object by RRF Id - "+requirementId);
			exception.printStackTrace();
		}
		
		return requestRequisitionSkill;
	}
}
