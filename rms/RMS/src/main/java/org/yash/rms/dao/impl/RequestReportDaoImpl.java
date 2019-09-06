package org.yash.rms.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StringType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.yash.rms.dao.PDLAndResourceMappingDao;
import org.yash.rms.dao.RequestReportDao;
import org.yash.rms.dao.RequestRequisitionResourceDao;
import org.yash.rms.domain.AllocationType;
import org.yash.rms.domain.RequestRequisition;
import org.yash.rms.domain.RequestRequisitionResource;
import org.yash.rms.domain.RequestRequisitionResourceStatus;
import org.yash.rms.domain.RequestRequisitionSkill;
import org.yash.rms.domain.Resource;
import org.yash.rms.domain.ResourceComment;
import org.yash.rms.exception.DAOException;
import org.yash.rms.helper.ResourceResumeHelper;
import org.yash.rms.namedquery.RmsNamedQuery;
import org.yash.rms.report.dto.RequestRequisitionReport;
import org.yash.rms.service.PDLAndResourceMappingService;
import org.yash.rms.service.PDLEmailGroupService;
import org.yash.rms.service.RequestRequisitionService;
import org.yash.rms.util.Constants;

@Repository("RequestReportDao")
@Transactional

public class RequestReportDaoImpl implements RequestReportDao {

	@PersistenceContext(name = "mysql", type = PersistenceContextType.TRANSACTION)
	private EntityManager entityManager;
	
	
	public EntityManager getEntityManager() {
		return entityManager;
	}
	
	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	private static final Logger logger = LoggerFactory.getLogger(ResourceDaoImpl.class);
	
	@Autowired
	PDLAndResourceMappingDao pDLAndResourceMappingDao;


	@Autowired
	PDLEmailGroupService pdlEmailGroupService;
	
	@Autowired
	@Qualifier("resumeUploadPath")
	private ResourceResumeHelper resumeHelper;
	
	@Autowired
	PDLAndResourceMappingService pDLAndResourceMappingService;

	@Autowired
	@Qualifier("RequestRequisitionService")
	RequestRequisitionService requestRequisitionService;
	
	@Autowired
	RequestRequisitionResourceDao requestRequisitionResourceDao;

	public List<RequestRequisition> getReport() {
		logger.info("--------ResourceDaoImpl findAll method start-------");
		List<RequestRequisition> requestReportList = new ArrayList<RequestRequisition>();
		Session session = (Session) getEntityManager().getDelegate();
		try {

			Criteria criteria = session.createCriteria(RequestRequisition.class);
			criteria.createAlias("requestRequisitionSkill", "sr", CriteriaSpecification.LEFT_JOIN).setFetchMode("sr", FetchMode.JOIN).add(Restrictions.ne("sr.remaining", "0"));
			criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			requestReportList = criteria.list();

		} catch (HibernateException e) {
			e.printStackTrace();
			logger.error("Exception occured in findAll method at DAO layer:-" + e);
			throw e;
		}
		logger.info("--------ResourceDaoImpl findAll method end-------");
		for (RequestRequisition request : requestReportList) {
			if (requestRequisitionService.findRequestRequisitionSkillsByRequestRequisitionId(request.getId()).size() > 0) {
				RequestRequisitionSkill requestRequisitionSkill = requestRequisitionService.findRequestRequisitionSkillsByRequestRequisitionId(request.getId()).get(0);
				if (requestRequisitionResourceDao.getRequestRequisitionResourceList(requestRequisitionSkill.getId()).size() > 0)
					System.out.println("Name: " + requestRequisitionResourceDao.getRequestRequisitionResourceList(requestRequisitionSkill.getId()).get(0).getExternalResourceName());
			}
		}
		return requestReportList;
	}

	@Transactional
	public boolean saveReport(RequestRequisition request, int skillId) {
		logger.info("--------ResourceDaoImpl findAll method start-------");
		Session session = (Session) getEntityManager().getDelegate();

		List<RequestRequisitionSkill> skillRequest1 = requestRequisitionService.findRequestRequisitionSkillsByRequestRequisitionId(request.getId());

		for (int i = 0; i < skillRequest1.size(); i++) {
			RequestRequisitionSkill request2 = skillRequest1.get(i);
			skillId = request2.getId();
			List<RequestRequisitionResource> skillResources = (List<RequestRequisitionResource>) requestRequisitionResourceDao.getRequestRequisitionResourceList(request2.getId());

			try {
				Criteria criteria = session.createCriteria(RequestRequisition.class).add(Restrictions.eq("id", request.getId()));
				criteria.createAlias("requestRequisitionSkill", "sr", CriteriaSpecification.LEFT_JOIN).setFetchMode("sr", FetchMode.JOIN).add(Restrictions.eq("sr.id", skillId));
				criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
				RequestRequisition loadedRequest = (RequestRequisition) criteria.uniqueResult();
				RequestRequisition finalRequest = mergeRequest(request, loadedRequest);
				// session.load(Request.class, request.getId());

				session.merge(finalRequest);
				/*
				 * request.setProjectName("abcd"); session.update(request);
				 */
			} catch (HibernateException e) {
				e.printStackTrace();
				logger.error("Exception occured in findAll method at DAO layer:-" + e);
				throw e;
			}
		}

		logger.info("--------ResourceDaoImpl findAll method end-------");

		return true;

	}

	private RequestRequisition mergeRequest(RequestRequisition requestRequisition, RequestRequisition loadedRequest) {

		// loadedRequest.setEmployeeId(request.getEmployeeId());
		List<RequestRequisitionSkill> requestlist = requestRequisitionService.findRequestRequisitionSkillsByRequestRequisitionId(requestRequisition.getId());
		List<RequestRequisitionSkill> loadedlist = requestRequisitionService.findRequestRequisitionSkillsByRequestRequisitionId(loadedRequest.getId());
		List<RequestRequisitionSkill> finalSkillRequests = new ArrayList<RequestRequisitionSkill>();
		List<RequestRequisitionResource> skillResourceList = new ArrayList<RequestRequisitionResource>();
		List<RequestRequisitionResource> loadedSkillres = new ArrayList<RequestRequisitionResource>();
		Integer skillFullDBCount = 0;
		Integer fullfillCount = 0;
		for (int i = 0; i < loadedlist.size(); i++) {

			RequestRequisitionSkill loadedSkillreq = loadedlist.get(i);
			RequestRequisitionSkill skillreq = requestlist.get(i);
			// Integer skillFullfill =
			// Integer.parseInt(skillreq.getFulfilled());
			skillFullDBCount = (loadedSkillreq.getFulfilled());

			loadedSkillreq.setRemaining(skillreq.getRemaining());
			loadedSkillreq.setComments(skillreq.getComments());
			if (requestRequisitionResourceDao.getRequestRequisitionResourceList(skillreq.getId()).size() > 0) {
				boolean isExist;
				for (int j = 0; j < requestRequisitionResourceDao.getRequestRequisitionResourceList(skillreq.getId()).size(); j++) {
					RequestRequisitionResource skRes = requestRequisitionResourceDao.getRequestRequisitionResourceList(skillreq.getId()).get(j);
					loadedSkillres = requestRequisitionResourceDao.getRequestRequisitionResourceList(loadedSkillreq.getId());
					isExist = false;
					for (int k = 0; k < loadedSkillres.size(); k++) {
						if (loadedSkillres.get(k).getResource() != null && skRes.getResource() != null) {
							if ((loadedSkillres.get(k).getResource().getEmployeeId().intValue() == skRes.getResource().getEmployeeId().intValue()
									&& loadedSkillres.get(k).getRequestRequisitionSkill().getId() == skRes.getRequestRequisitionSkill().getId())
							/*
							 * ||
							 * (loadedSkillres.get(k).getExternalResourceName()
							 * .equals(skRes.getExternalResourceName()) &&
							 * loadedSkillres .get(k).getSkillRequestId() ==
							 * skRes .getSkillRequestId())
							 */) {
								isExist = true;
								break;
							}
						}

					}

					if (!isExist) {

						RequestRequisitionResource skillResource = new RequestRequisitionResource();
						if (skRes.getResource() != null) {
							skillResource.setResource(skRes.getResource());
						}
						if (skRes.getExternalResourceName() != null && skRes.getExternalResourceName() != "") {
							skillResource.setExternalResourceName(skRes.getExternalResourceName());
						}
						skillResource.setId(skRes.getId());
						skillResource.setRequestRequisitionSkill(skillreq);
						skillResourceList.add(skillResource);
						fullfillCount++;

					}

				}
				loadedSkillreq.setFulfilled((skillFullDBCount + fullfillCount));
			}
			// end if size of skillrequesRequerce size false
			else {
				loadedSkillreq.setFulfilled((skillFullDBCount));
			}
		//	loadedSkillreq.setRequestRequisitionResource(skillResourceList);
			finalSkillRequests.add(loadedSkillreq);

		}
	//	loadedRequest.setRequestRequisitionSkill(finalSkillRequests);

		return loadedRequest;

	}

	private RequestRequisitionResource addResourceForResourceAllocation(RequestRequisitionResource skillResourceRequest, RequestRequisitionResource loadedRequest, int projectId) {
		// Integer reqAllocationId=skillResourceRequest.getAllocationId();
		List<RequestRequisitionResource> list = new ArrayList<RequestRequisitionResource>();
		// form Request start
		RequestRequisitionResource sendRequest = new RequestRequisitionResource();
		RequestRequisitionResourceStatus resourceStatusType = new RequestRequisitionResourceStatus();
		AllocationType allocationType = new AllocationType();
		allocationType = skillResourceRequest.getAllocation();
		resourceStatusType = skillResourceRequest.getRequestRequisitionResourceStatus();
		Date allocationDate = skillResourceRequest.getAllocationDate();
		Integer skillResourceId = skillResourceRequest.getId();
		RequestRequisitionSkill skillRequestId = new RequestRequisitionSkill();
		skillRequestId = skillResourceRequest.getRequestRequisitionSkill();
		// end form request

		// for load request
		RequestRequisitionResourceStatus loadResourceStatusType = new RequestRequisitionResourceStatus();
		AllocationType loadAllocationType = new AllocationType();
		loadAllocationType = loadedRequest.getAllocation();
		loadResourceStatusType = loadedRequest.getRequestRequisitionResourceStatus();
		// Date loadAllocationDate=loadedRequest.getAllocationDate();
		Resource resourceId = new Resource();
		resourceId = loadedRequest.getResource();
		// End Load Request
		RequestRequisitionResource resource = null;
		if (skillResourceId != null) {

			if (allocationType != loadAllocationType && allocationType.getId() != 0) {
				if (loadAllocationType != null) {
					if (allocationType.getId() != loadAllocationType.getId()) {
						if (resourceId != null) {
							resource = new RequestRequisitionResource();
							resource.setId(projectId);// here i am assign
														// projectId in Id
							resource.setResource(resourceId);
							resource.setAllocation(allocationType);
							resource.setAllocationDate(allocationDate);
							resource.setRequestRequisitionResourceStatus(resourceStatusType);

						}
					}
				} else {
					if (resourceId != null) {
						resource = new RequestRequisitionResource();
						resource.setId(projectId);// here i am assign projectId
													// in Id
						resource.setResource(resourceId);
						resource.setAllocation(allocationType);
						resource.setAllocationDate(allocationDate);
						resource.setRequestRequisitionResourceStatus(resourceStatusType);
					}
				}
			}

		}
		return resource;

	}

	@Transactional
	public Integer saveSkillRequestReport(RequestRequisitionSkill skillRequest, int skillId, List<RequestRequisitionResource> requestRequisitionResource)throws Exception {
		logger.info("--------saveSkillRequestReport findAll method start-------");
		Session session = (Session) getEntityManager().getDelegate();
		try {

			Criteria criteria = session.createCriteria(RequestRequisitionSkill.class).add(Restrictions.eq("id", skillId));
			criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			RequestRequisitionSkill loadedRequest = (RequestRequisitionSkill) criteria.uniqueResult();

			RequestRequisitionSkill finalRequest = mergeSkillRequest(skillRequest, loadedRequest, skillId);

			//List<RequestRequisitionResource> listSRs = requestRequisitionResourceDao.getRequestRequisitionResourceList(finalRequest.getId());

			session.merge(finalRequest);
			logger.info("--------saveSkillRequestReport findAll method end-------");
			return finalRequest.getId();

		} catch (HibernateException e) {
			e.printStackTrace();
			logger.error("Exception occured in findAll method at DAO layer:-" + e);
			throw e;
		}

	}

	private RequestRequisitionSkill mergeSkillRequest(RequestRequisitionSkill skillRequest, RequestRequisitionSkill loadedRequest, int skillId) {

		// loadedRequest.setEmployeeId(request.getEmployeeId());
		Integer fullfillResLen = 0;
		List<RequestRequisitionResource> skillRequestResource = requestRequisitionResourceDao.getRequestRequisitionResourceList(skillRequest.getId());
		List<RequestRequisitionResource> loadedRequestResourcelist = requestRequisitionResourceDao.getRequestRequisitionResourceList(loadedRequest.getId());
		List<RequestRequisitionResource> skillResourceList = new ArrayList<RequestRequisitionResource>();
		List<RequestRequisitionSkill> finList = new ArrayList<RequestRequisitionSkill>();
		if (skillRequest.getFulfilled() != null && !"null".equals(skillRequest.getFulfilled()) ) {
			fullfillResLen = (skillRequest.getFulfilled());
		}

		RequestRequisitionSkill skillRequest2 = new RequestRequisitionSkill();
		// skillRequest2.setComments(skillRequest.getComments());
		// 1,2,3,9,10 here RequestResource id
		// 3, 316 RequestResource employee id
		boolean isExist;
		int count = 0;
		int presentCountDB = 0;
		for (int i = 0; i < skillRequestResource.size(); i++) {
			RequestRequisitionResource setSkillRequestResource = skillRequestResource.get(i);

			isExist = false;
			// 1,2,3,9,10 here RequestResource id
			// 3, 316, 433,436
			for (int j = 0; j < loadedRequestResourcelist.size(); j++) {
				RequestRequisitionResource loadSkillRequestResource = loadedRequestResourcelist.get(j);

				if (setSkillRequestResource.getResource() != null && loadSkillRequestResource.getResource() != null) {
					if ((setSkillRequestResource.getResource().getEmployeeId().intValue() == loadSkillRequestResource.getResource().getEmployeeId().intValue())) {
						presentCountDB++;
						isExist = true;
						break;
					}
				}

			}
			if (!isExist) {

				// RequestRequisitionResourceStatus statusId = new
				// RequestRequisitionResourceStatus();
				RequestRequisitionResource skillResource = new RequestRequisitionResource();
				if (setSkillRequestResource.getResource() != null) {
					count++;
					skillResource.setResource(setSkillRequestResource.getResource());
					skillResource.setResumeFileName(setSkillRequestResource.getResumeFileName());
					skillResource.setUploadResume(setSkillRequestResource.getUploadResume());
				}
				if (setSkillRequestResource.getExternalResourceName() != null && setSkillRequestResource.getExternalResourceName() != "") {
					skillResource.setExternalResourceName(setSkillRequestResource.getExternalResourceName());
					skillResource.setResumeFileName(setSkillRequestResource.getResumeFileName());
					skillResource.setUploadResume(setSkillRequestResource.getUploadResume());
				}

				// skillResource.setStatusId(setSkillRequestResource.getStatusId());
				skillRequest2.setId(skillId);
				skillResource.setId(setSkillRequestResource.getId());
				skillResource.setRequestRequisitionSkill(skillRequest2);
				skillResourceList.add(skillResource);
			}
		}
		int totalfullFIll = fullfillResLen + count;
		System.out.println("totalfullFIll===" + totalfullFIll);
		// loadedRequest.setFulfilled("0");// first time added Resource that
		// time full filled count will be 0; because here status Type based on
		// full fill
		loadedRequest.setComments(skillRequest.getComments());
	//	loadedRequest.setRequestRequisitionResource(skillResourceList);

		return loadedRequest;

	}

	public List<RequestRequisitionSkill> getReport(String opt, Integer id, String role) {
		logger.info("--------ResourceDaoImpl findAll method start-------");
		List<RequestRequisitionSkill> requestReportList = new ArrayList<RequestRequisitionSkill>();
		Session session = (Session) getEntityManager().getDelegate();
		try {
			// change from start
			Criteria criteria = session.createCriteria(RequestRequisitionSkill.class, "sr");
			criteria.createAlias("requestRequisition", "re");
			criteria.setFetchMode("requestRequisition", FetchMode.JOIN);
			criteria.addOrder(Order.desc("id"));
			try {
				if (opt.equalsIgnoreCase("active")) {
					criteria.add(Restrictions.gt("remaining", "0"));
					criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
				}
				if (!role.equalsIgnoreCase(Constants.ROLE_ADMIN)) {
					Criterion c1 = Restrictions.like("re.sentMailTo", "%," + id.toString() + ",%");
					Criterion c2 = Restrictions.like("re.sentMailTo", "%" + id.toString() + ",%");
					Criterion c3 = Restrictions.like("re.sentMailTo", "%" + id.toString() + " ,%");
					Criterion c4 = Restrictions.like("re.sentMailTo", "%, " + id.toString() + ",%");
					Criterion c6 = Restrictions.eq("re.employeeId.employeeId", id);
					Criterion c5 = Restrictions.or(c1, c2, c3, c4, c6);
					// criteria.add(Restrictions.like("", "",
					// MatchMode.ANYWHERE));
					criteria.add(c5);
				}
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			requestReportList = criteria.list();

		} catch (HibernateException e) {
			e.printStackTrace();
			logger.error("Exception occured in findAll method at DAO layer:-" + e);
			throw e;
		}
		logger.info("--------ResourceDaoImpl findAll method end-------");

		return requestReportList;
	}

	public boolean deleteSkillRequestResource(Integer id) {
		boolean isDeleted = false;
		try {
			Session currentSession = (Session) getEntityManager().getDelegate();
			Query query = currentSession.createQuery("delete RequestRequisitionResource where id =:id");
			query.setParameter("id", id);
			int result = query.executeUpdate();
			if (result > 0) {
				isDeleted = true;
			} else {
				isDeleted = false;
			}
		} catch (Exception e) {
			isDeleted = false;
		}
		return isDeleted;
	}

	
	
	
	public List<RequestRequisitionSkill> getRequestRequisitionSkillById(Integer id) {
		logger.info("--------ResourceReportDaoImpl getRequestRequisitionSkillById method start-------");
		List<RequestRequisitionSkill> requestReportList = new ArrayList<RequestRequisitionSkill>();
		Session session = (Session) getEntityManager().getDelegate();
		try {
			Criteria criteria = session.createCriteria(RequestRequisitionSkill.class);
			criteria.add(Restrictions.eq("id", id));
			requestReportList = criteria.list();
			//Change Status after view all resources update
			StringBuilder sqlQuery = new StringBuilder();
                	sqlQuery.append(" SELECT ");
					sqlQuery.append(" (CASE WHEN (skr.req_resources - (skr.on_hold+skr.lost+skr.shortlisted+skr.closed))>0 THEN 'OPEN' ");
					sqlQuery.append(" WHEN (skr.req_resources - skr.lost)=0 THEN 'LOST' ");
					sqlQuery.append(" WHEN (((skr.req_resources - skr.on_hold) = 0 ) OR ((skr.req_resources - (skr.on_hold+skr.lost)) = 0 AND (skr.on_hold != 0))) THEN 'HOLD' ");
					sqlQuery.append(" WHEN (skr.req_resources - (skr.on_hold+skr.lost+skr.closed))<=0 THEN 'CLOSED' ");
					sqlQuery.append(" WHEN (skr.req_resources - (skr.on_hold+skr.lost+skr.shortlisted + skr.closed))<=0  THEN 'FULLFILLED' END ) report_status");
					sqlQuery.append(" FROM skill_request_requisition skr  WHERE skr.id = '"+id+"'");
					
					Query query = session.createSQLQuery(sqlQuery.toString());
                	String status = (String) query.uniqueResult();
			
					if(requestReportList != null){
						for(RequestRequisitionSkill skill : requestReportList){
							if(skill.getId() == id){
								skill.setStatus(status);
							}
						}
					}
					
		} catch (HibernateException e) {
			e.printStackTrace();
			logger.error("Exception occured in getRequestRequisitionSkillById method at DAO layer:-" + e);
			throw e;
		}
		logger.info("--------ResourceReportDaoImpl getRequestRequisitionSkillById method end-------");

		return requestReportList;
	}

	@SuppressWarnings("unchecked")
	public List<RequestRequisitionSkill> getSkillRequestReport(Integer resourceId, String role) {
		logger.info("--------ResourceReportDaoImpl getSkillRequestReport method start-------");
		
		List<RequestRequisitionSkill> requestReportList = new ArrayList<RequestRequisitionSkill>();
		Session session = (Session) getEntityManager().getDelegate();
		
		try {
			
			if (null != role && !role.isEmpty()) {
				if (role.equals(Constants.ROLE_ADMIN)) {
					
					requestReportList = session.createQuery("from RequestRequisitionSkill").list();
					
				} else {
					List<Integer> pdlIds = pDLAndResourceMappingDao.getPDLIdbyResourceId(resourceId);
					
					List<String> pdlEmailList = new ArrayList<String>();
					
					String pdlQueryClause = "";
					StringBuilder pdlQueryClauseBuilder = new StringBuilder();
					
					if (!pdlIds.isEmpty() && pdlIds.size() > 0) {
						pdlEmailList = pdlEmailGroupService.findPdlByIds(new ArrayList<Integer>(pdlIds));
						
						for (int i = 0 ; i < pdlEmailList.size() ; i++) {
							pdlQueryClauseBuilder.append("OR rr.pdlEmailIds LIKE ('%" + pdlEmailList.get(i) + "%') ");
						}
					}
						
					if (pdlQueryClauseBuilder.length() > 0) {
						pdlQueryClause = pdlQueryClauseBuilder.toString();
					}
					
					Query query = session.createQuery("From RequestRequisitionSkill rrs LEFT JOIN rrs.requestRequisition rr WHERE "
							+ "rr.resource.employeeId = :createdBy "
							+ "OR rr.sentMailTo = :sentMailTo OR rr.sentMailTo LIKE ('%, " + resourceId + ",%') OR rr.sentMailTo LIKE ('" + resourceId + ",%') OR rr.sentMailTo LIKE ('%, " + resourceId + "') "
							+ " OR rr.notifyMailTo = :notifyTo OR rr.notifyMailTo LIKE ('%, " + resourceId + ",%') OR rr.notifyMailTo LIKE ('" + resourceId + ",%') OR rr.notifyMailTo LIKE ('%, " + resourceId + "') "
							+ pdlQueryClause );
					
					query.setParameter("sentMailTo", ""+resourceId);
					query.setParameter("notifyTo", ""+resourceId);
					query.setParameter("createdBy", resourceId);
					
					//System.out.println(query.toString());
					
					List<Object[]> requestReports = query.list();
					
					mapResultToRequestReports(requestReportList, requestReports);
				}
			} 
			
		} catch (HibernateException e) {
			e.printStackTrace();
			logger.error("Exception occured in getSkillRequestReport method at DAO layer:-" + e);
			throw e;
		}
		logger.info("--------ResourceReportDaoImpl getSkillRequestReport method end-------");

		//for (RequestRequisitionSkill requestRequisitionSkill : requestReportList) {
		//	System.out.println("requirement name : " + requestRequisitionSkill.getRequirementId());
			//System.out.println("requirement created by : " + requestRequisitionSkill.getRequestRequisition().getResource().getFirstName());
		//}
		
		
		return requestReportList;
	}

	private void mapResultToRequestReports(List<RequestRequisitionSkill> requestReportList, List<Object[]> requestReports) {
		
		if (requestReports != null) {
			for (Object[] objects : requestReports) {
				
				RequestRequisition requestRequisition = (RequestRequisition) objects[1];
				
				RequestRequisitionSkill requestRequisitionSkill = (RequestRequisitionSkill) objects[0];
					
					requestRequisitionSkill.setRequestRequisition(requestRequisition);
					
				requestReportList.add(requestRequisitionSkill);
			}
		}
		
	}
	
	@Transactional
	public boolean reduceFullfilledSkillRequest(Integer skillResId) {
		logger.info("--------ResourceDaoImpl findAll method start-------");
		Session session = (Session) getEntityManager().getDelegate();
		boolean isDeleted = false;
		try {
			Query query = session.createQuery(" select count(*) from  SkillResource  where skill_request_id =:id AND status_id IN(4,12,22)");
			query.setParameter("id", skillResId);
			Long count1 = (Long) query.uniqueResult();
			if (count1 > 0) {
				Criteria criteria = session.createCriteria(RequestRequisitionSkill.class).add(Restrictions.eq("id", skillResId));
				List<RequestRequisitionSkill> list = criteria.list();
				if (list != null) {
					RequestRequisitionSkill str = (RequestRequisitionSkill) list.get(0);
					Integer ResourceRequestcount = (str.getNoOfResources());
					Integer Remcount = (int) (ResourceRequestcount - count1);
					Query query1 = session.createQuery("update SkillRequest set fulfilled = '" + count1 + "',remaining='" + Remcount + "' where id ='" + skillResId + "'");
					int result = query1.executeUpdate();
					if (result > 0) {
						isDeleted = true;
					} else {
						isDeleted = false;
					}
				}
			}
		} catch (Exception e) {
			System.out.println();
		}
		logger.info("--------ResourceDaoImpl findAll method end-------");

		return isDeleted;
	}

	public List<RequestRequisitionResourceStatus> getSkillResourceStatusList() {
		// TODO Auto-generated method stub
		logger.info("--------RequestRequisitionResourceStatus findAll method start-------");
		List<RequestRequisitionResourceStatus> internalResTypeList = new ArrayList<RequestRequisitionResourceStatus>();

		Session session = (Session) getEntityManager().getDelegate();

		try {

			internalResTypeList = session.createQuery("FROM RequestRequisitionResourceStatus").list();

		} catch (HibernateException e) {
			e.printStackTrace();
			logger.error("EXCEPTION CAUSED IN GETTING RequestRequisitionResourceStatus LIST " + e.getMessage());
			throw e;

		} finally {

		}
		logger.info("------------RequestRequisitionResourceStatus findAll method end------------");
		return internalResTypeList;
	}

	@Transactional
	public boolean updateSkillRequestStatus(Integer id, Integer statusId) {
		logger.info("--------ResourceDaoImpl findAll method start-------");
		Session session = (Session) getEntityManager().getDelegate();
		boolean isDeleted = false;
		try {

			Query query = session.createQuery("update SkillResource set statusId = :statusId where id =:id");
			query.setParameter("id", statusId);
			query.setParameter("id", id);
			int result = query.executeUpdate();
			if (result > 0) {
				isDeleted = true;
			} else {
				isDeleted = false;
			}

		} catch (HibernateException e) {
			e.printStackTrace();
			logger.error("Exception occured in findAll method at DAO layer:-" + e);
			throw e;
		}
		logger.info("--------ResourceDaoImpl findAll method end-------");

		return isDeleted;
	}

	public boolean delete(int id) throws DAOException{
		logger.info("--------Skill Request delete method start-------");

		boolean isSuccess = true;
		try {
			Session session = (Session) getEntityManager().getDelegate();
			RequestRequisitionSkill skillRequest = (RequestRequisitionSkill) session.get(RequestRequisitionSkill.class, id);
			skillRequest.setIsDeleted(true);
			session.saveOrUpdate(skillRequest);
			//session.delete(skillRequest);
//			session.flush();
		} catch (HibernateException e) {
			isSuccess = false;
			logger.error("Exception occured in delete method at DAO layer:-" + e);
			throw new DAOException("512", e.getMessage());
		} catch (Exception ex) {
			logger.error("Exception occured in delete method at DAO layer:-" + ex);
			throw new DAOException("512", ex.getMessage());
		}
		logger.info("--------ResourceAllocationDaoImpl delete method End-------");
		return isSuccess;

	}

	public Object getResumeById(Integer id) {
		Object file = new Object();
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Session currentSession = (Session) getEntityManager().getDelegate();
		
			Query	query = currentSession.createQuery("select uploadResume, resumeFileName from RequestRequisitionResource where id =:id");
			
			query.setParameter("id", id);

			List<Object[]> remumes = (List<Object[]>) query.list();
			for (Object[] resume : remumes) {
				map.put("file", (byte[]) resume[0]);
				map.put("fileName", (String) resume[1]);
			}
			// map.put("file", value) = (byte[]) query.uniqueResult();

		} catch (Exception e) {
			logger.error("Exception occured in getResumeById method at DAO layer:-" + e);
		}
		logger.info("--------RequestReportDaoImpl getResumeById method end-------");
		return map;

	}
	
	public Object getResumeFromResourceById(Integer id) {
		Object file = new Object();
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Session currentSession = (Session) getEntityManager().getDelegate();
			
			Query query  = currentSession.createQuery("select uploadResume, uploadResumeFileName from Resource where employeeId =:id");
			
			query.setParameter("id", id);

			List<Object[]> remumes = (List<Object[]>) query.list();
			for (Object[] resume : remumes) {
				map.put("file", (byte[]) resume[0]);
				map.put("fileName", (String) resume[1]);
			}
			// map.put("file", value) = (byte[]) query.uniqueResult();

		} catch (Exception e) {
			logger.error("Exception occured in getResumeById method at DAO layer:-" + e);
		}
		logger.info("--------RequestReportDaoImpl getResumeById method end-------");
		return map;

	}

	public Map<String, Object> getTacById(Integer id) throws Exception {
		Object file = new Object();
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Session currentSession = (Session) getEntityManager().getDelegate();
		
			Query	query = currentSession.createQuery("select uploadTacFile, uploadTacFileName from RequestRequisitionResource where id =:id");
			
			query.setParameter("id", id);

			List<Object[]> remumes = (List<Object[]>) query.list();
			for (Object[] resume : remumes) {
				map.put("file", (byte[]) resume[0]);
				map.put("fileName", (String) resume[1]);
			}

		} catch (Exception e) {
			logger.error("Exception occured in getTacById method at DAO layer:-" + e);
		}
		logger.info("--------RequestReportDaoImpl getTacById method end-------");
		return map;
	}

	public List<RequestRequisitionSkill> getSkillRequestReport(Integer id, String role, Date startDate, Date endDate) {
		logger.info("--------getSkillRequestReport of RequestReportDaoImpl method start-------");
		List<RequestRequisitionSkill> requestReportList = new ArrayList<RequestRequisitionSkill>();
		Session session = (Session) getEntityManager().getDelegate();
		try {
			Criteria criteria = session.createCriteria(RequestRequisitionSkill.class);
				criteria.createAlias("requestRequisition", "re");
				criteria.setFetchMode("requestRequisition", FetchMode.JOIN);
				criteria.addOrder(Order.desc("id"));
				criteria.add(Restrictions.gt("remaining", "0"));
				criteria.add(Restrictions.ge("re.date",startDate));
				criteria.add(Restrictions.le("re.date",endDate));
			
			if (!role.equalsIgnoreCase(Constants.ROLE_ADMIN)) {
				criteria.add(Restrictions.le("re.resource.id",id));
			}
			criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			requestReportList = criteria.list();
		} catch (HibernateException e) {
			e.printStackTrace();
			logger.error("Exception occured in findAll method at DAO layer:-" + e);
			throw e;
		}
		logger.info("--------getSkillRequestReport of RequestReportDaoImpl method ended-------");
			return requestReportList;
	}
	
	public List<RequestRequisitionSkill> getSkillRequestReport(Integer id, String role, Date startDate, Date endDate,
			List<Integer> customerList, List<Integer> groupList, String status) {
		logger.info("--------getSkillRequestReport of RequestReportDaoImpl method start-------");
		List<RequestRequisitionSkill> requestReportList = new ArrayList<RequestRequisitionSkill>();
		Session session = (Session) getEntityManager().getDelegate();
		try {
			Criteria criteria = session.createCriteria(RequestRequisitionSkill.class);
				criteria.createAlias("requestRequisition", "re");
				criteria.setFetchMode("requestRequisition", FetchMode.JOIN);
				if(startDate!=null){
					criteria.add(Restrictions.ge("re.date",startDate));
				}
				if(endDate != null ){
					criteria.add(Restrictions.le("re.date",endDate));
				}
				if(customerList!=null){
					criteria.add(Restrictions.in("re.customer.id",customerList));
				}
				if(groupList!=null){
					criteria.add(Restrictions.in("re.group.groupId",groupList));
				}
				if(status==null || status.equalsIgnoreCase("A")){
					Criterion criterion1 =  Restrictions.isNull("status");
					Criterion criterion2=  Restrictions.ne("status","Closed");
					Criterion criterion3=Restrictions.ne("status", "Hold");
					criteria.add(Restrictions.or(criterion1, Restrictions.and( criterion2,criterion3)));
				}else{
					//criteria.add(Restrictions.eq("status","Closed"));
					Criterion criterion1=  Restrictions.eq("status","Closed");
					Criterion criterion2=Restrictions.eq("status", "Hold");
					criteria.add(Restrictions.or( criterion1,criterion2));
				}
				if (!role.equalsIgnoreCase("ROLE_ADMIN")) {
					criteria.add(Restrictions.eq("re.resource.employeeId",id));
				}
				
				criteria.addOrder(Order.asc("requirementId"));
				
			criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			requestReportList = criteria.list();
		} catch (HibernateException e) {
			e.printStackTrace();
			logger.error("Exception occured in findAll method at DAO layer:-" + e);
			throw e;
		}
		logger.info("--------getSkillRequestReport of RequestReportDaoImpl method ended-------");
	 return requestReportList;
	}

	public List<RequestRequisitionReport> getSkillRequestDBTabReport(Integer id, String role, List<Integer> customerList, 
			List<Integer> groupList, String status, List<String> hiringUnits, List<String> reqUnits) {
		logger.info("--------getSkillRequestReport of RequestReportDaoImpl method start-------");
		List<RequestRequisitionReport> requestReportList = new ArrayList<RequestRequisitionReport>();
		
		try {
				Session session = (Session) getEntityManager().getDelegate();
				String sqlQuery=RmsNamedQuery.getDBReportData(id, role, customerList, groupList, status, hiringUnits, reqUnits);
				
				Query query=session.createSQLQuery(sqlQuery).addScalar("open", new StringType())
						.addScalar("closed", new StringType())
						.addScalar("clientName", new StringType())
						.addScalar("status", new StringType())
						.addScalar("noOfResources", new StringType())
					
						.setResultTransformer(Transformers.aliasToBean(RequestRequisitionReport.class));
			/*	
				if(startDate!=null){
					query.setParameter("startDate", startDate);
				}
				
				if(endDate != null ){
					query.setParameter("endDate", endDate);
				}
				*/
				if(customerList!=null){
					query.setParameterList("customerList", customerList);
					
				}
				if(groupList!=null){
					query.setParameterList("groupList", groupList);
					
				}
				if(hiringUnits!=null){
					query.setParameterList("hiringUnits",hiringUnits);
				}
				if(reqUnits!=null){
					query.setParameterList("reqUnits",  reqUnits);
					
				}
				if (!role.equalsIgnoreCase("ROLE_ADMIN")) {
					query.setParameter("empid", id);
				}
				requestReportList=query.list();
								
		} catch (HibernateException e) {
			e.printStackTrace();
			logger.error("Exception occured in getSkillRequestReport of RequestReportDaoImpl method at DAO layer:-" + e);
			throw e;
		}
		logger.info("--------getSkillRequestReport of RequestReportDaoImpl method ended-------");
	 return requestReportList;
	}
	public void saveResourceComment(ResourceComment resourceComment) {
		Session session = (Session) getEntityManager().getDelegate();
		try{
			session.saveOrUpdate(resourceComment);
		}catch(DataAccessException e ){
			e.printStackTrace();
		}
	}

	public List<ResourceComment> getAllResourceCommentByResourceId(
			int resourceId) {
		Session session = (Session) getEntityManager().getDelegate();
		List<ResourceComment> list = new ArrayList<ResourceComment>();
		try{
			String SQL_QUERY = "FROM ResourceComment rc WHERE rc.resourceId = "+resourceId;
			System.out.println(SQL_QUERY);
			Query createQuery = session.createQuery(SQL_QUERY);
			list = createQuery.list();
		}catch(DataAccessException e ){
			e.printStackTrace();
		}
		return list;
	}
	
	public List<Object[]> getSkillRequestReport(Integer userId, String role, List<Integer> customerList, 
			List<Integer> groupList, List<Integer> projectList, String status, List<String> hiringUnits, List<String> reqUnits) {
		logger.info("--------getSkillRequestReport of RequestReportDaoImpl method start-------");
		List<RequestRequisitionSkill> requestReportList = new ArrayList<RequestRequisitionSkill>();
		Session session = (Session) getEntityManager().getDelegate();
		List<Object[]> reportList = null;
		try {
				StringBuilder sqlQuery = new StringBuilder();
				HashMap<String,Object> parameterList = new HashMap<String, Object>();
				
				sqlQuery.append(" SELECT * FROM ( SELECT distinct skr.id,skr.req_id,skr.type, skr.`status`,skr.req_resources,skr.remaining,skr.`open`,skr.closed,skr.on_hold,"); 
				sqlQuery.append(" skr.`lost`,skr.`submissions`, skr.`shortlisted`,skr.`not_fit_for_requirement`, ");
				sqlQuery.append(" req.date_of_Indent, req.`am_job_code`, (SELECT GROUP_CONCAT(CONCAT(first_name,' ',last_name)) FROM resource WHERE (FIND_IN_SET(employee_id,req.`rmg_poc`)))  ,(SELECT GROUP_CONCAT(CONCAT(first_name,' ',last_name)) FROM resource WHERE (FIND_IN_SET(employee_id,req.`tec_team_poc`)))  , skr.`requirementArea`, cus.`customer_name`,  alt.`allocationtype`, alt.`id` as allocationId, alt.`alias_allocation_name`, ");
				sqlQuery.append(" loc.`location`,des.`designation_name`,CONCAT(requestor.`first_name`,' ',requestor.last_name) requestedBy,  req.indentor_comments ,");
				sqlQuery.append(" (SELECT group_name FROM customer_group WHERE group_id = req.group_id) group_name, ");
				sqlQuery.append(" CONCAT(par_org.name,'-',org.`name`) business_pract, skill.`skill`, req.id as req_res_id, skr.sent_req_to, skr.pdl_list, ");
				sqlQuery.append(" (CASE WHEN (skr.req_resources - (skr.on_hold+skr.lost+skr.shortlisted+skr.closed))>0 THEN 'OPEN' ");
				sqlQuery.append(" WHEN (skr.req_resources - skr.lost)=0 THEN 'LOST' ");
				sqlQuery.append(" WHEN (((skr.req_resources - skr.on_hold) = 0 ) OR ((skr.req_resources - (skr.on_hold+skr.lost)) = 0 AND (skr.on_hold != 0))) THEN 'HOLD' ");
				sqlQuery.append(" WHEN (skr.req_resources - (skr.on_hold+skr.lost+skr.closed))<=0 THEN 'CLOSED' ");
				sqlQuery.append(" WHEN (skr.req_resources - (skr.on_hold+skr.lost+skr.shortlisted + skr.closed))<=0  THEN 'FULLFILLED' END ) report_status, skr.closure_date, ");
				sqlQuery.append(" req.hiring_bgbu, req.project_bu ");
				sqlQuery.append(" FROM skill_request_requisition skr,  request_requisition req, project prj, location loc, ");
				sqlQuery.append(" allocationtype alt, resource res, resource requestor, customer cus, DESIGNATIONS des, skills skill,  org_hierarchy org, org_hierarchy par_org");
				sqlQuery.append(" WHERE skr.request_id = req.id AND req.`project_id` = prj.`id` ");
				sqlQuery.append(" AND req.`emp_id` = res.`employee_id` AND req.`customer_id` = cus.`id`");
				sqlQuery.append(" AND req.`requestor` = requestor.`employee_id`  ");
				sqlQuery.append(" AND skr.`location_id` = loc.`id` AND skr.`designation` = des.`id` ");
				sqlQuery.append(" AND skr.`skills` = skill.`id` AND skr.`billable` =  alt.`id` ");
				sqlQuery.append(" AND res.`current_bu_id` = org.`id` AND org.`parent_id` = par_org.`id` ");
				sqlQuery.append(" AND skr.`is_deleted` = 0 ");

			
				if(customerList!=null){
					sqlQuery.append("  AND req.`customer_id` IN (:customerIds) ");
					parameterList.put("customerIds",customerList);
				}
				if(groupList!=null){
					sqlQuery.append(" AND req.`group_id` IN (:groupIds) ");
					parameterList.put("groupIds",groupList);
				}
			
				if(!role.equalsIgnoreCase(Constants.ROLE_ADMIN)){
					sqlQuery.append(" AND (EXISTS (SELECT FIND_IN_SET(:sendEmailId, REPLACE(req1.`sent_mail`,' ','')) AS result ");
					sqlQuery.append(" FROM  request_requisition req1 WHERE req1.id = req.`id` HAVING result >0) ");
					sqlQuery.append(" OR EXISTS (SELECT FIND_IN_SET(:toNotifyId, REPLACE(req2.notify_to,' ','')) AS result ");
					sqlQuery.append(" FROM  request_requisition req2 WHERE req2.id = req.`id` HAVING result >0) ");
					//sqlQuery.append(" OR EXISTS( SELECT 1 FROM `pdl_resource_mapping` prm WHERE prm.pdl_id IN (req.`pdl_email`) AND prm.`resource_id`=:pdlEmailId)  ");
					sqlQuery.append(" OR EXISTS( SELECT 1 FROM `pdl_resource_mapping` prm WHERE prm.pdl_id IN (");//req.`pdl_email`)
					sqlQuery.append(" SUBSTRING_INDEX(SUBSTRING_INDEX(req.pdl_email,',','1' ),',',-1),"+  
					 "SUBSTRING_INDEX(SUBSTRING_INDEX(req.pdl_email,',','2' ),',',-1),  SUBSTRING_INDEX(SUBSTRING_INDEX(req.pdl_email,',','3' ),',',-1),"+
					   "SUBSTRING_INDEX(SUBSTRING_INDEX(req.pdl_email,',','4' ),',',-1), SUBSTRING_INDEX(SUBSTRING_INDEX(req.pdl_email,',','5' ),',',-1),"+
					   "SUBSTRING_INDEX(SUBSTRING_INDEX(req.pdl_email,',','6' ),',',-1), SUBSTRING_INDEX(SUBSTRING_INDEX(req.pdl_email,',','7' ),',',-1),"+
					   "SUBSTRING_INDEX(SUBSTRING_INDEX(req.pdl_email,',','8' ),',',-1), SUBSTRING_INDEX(SUBSTRING_INDEX(req.pdl_email,',','9' ),',',-1),"+
							 "SUBSTRING_INDEX(SUBSTRING_INDEX(req.pdl_email,',','10' ),',',-1), SUBSTRING_INDEX(SUBSTRING_INDEX(req.pdl_email,',','11' ),',',-1))");
					
					sqlQuery.append("AND prm.`resource_id`=:pdlEmailId) ");
					
					
					
					
					
					
					
					sqlQuery.append(" OR req.`emp_id` = :employeeId ");
					parameterList.put("sendEmailId",userId);
					parameterList.put("toNotifyId",userId);
					parameterList.put("pdlEmailId",userId);
					parameterList.put("employeeId",userId);
					
					if(projectList != null && projectList.size()>0){
						sqlQuery.append(" OR req.`project_id` in (:projectId) ");
						parameterList.put("projectId",projectList);
					}
					sqlQuery.append(" ) ");
					
					
					
				}
				sqlQuery.append(" ) tmp " );
				if(status != null && !status.isEmpty()){
					sqlQuery.append(" WHERE report_status IN ("+ status + ") "); 
				}
				if(hiringUnits != null && !hiringUnits.isEmpty()){
					sqlQuery.append(" AND hiring_bgbu IN ( :hiringUnits) "); 
					parameterList.put("hiringUnits",hiringUnits);
				}
				if(reqUnits != null && !reqUnits.isEmpty()){
					sqlQuery.append(" AND project_bu IN ( :reqUnits) "); 
					parameterList.put("reqUnits",reqUnits);
					
				}
				
				sqlQuery.append("  ORDER BY req_id ");
				System.out.println("SQL Query :: "+ sqlQuery.toString());
				Query query = session.createSQLQuery(sqlQuery.toString());
				query.setProperties(parameterList);
				
				reportList = query.list();
		} catch (HibernateException e) {
			e.printStackTrace();
			logger.error("Exception occured in findAll method at DAO layer:-" + e);
			throw e;
		}
		logger.info("--------getSkillRequestReport of RequestReportDaoImpl method ended-------");
	 return reportList;
	}

	@SuppressWarnings("unchecked")
	public List<ResourceComment> getAllResourceCommentsBySkillRequestRequisitionID(Integer skillId) {
		Session session = (Session) getEntityManager().getDelegate();
		List<Object[]> list = new ArrayList<Object[]>();
		List<ResourceComment> domainList = new ArrayList<ResourceComment>();
		
		try{
			StringBuilder sqlQuery = new StringBuilder();
			HashMap<String,Object> parameterList = new HashMap<String, Object>();
			//sqlQuery.append("FROM ResourceComment where resourceId IN (id from RequestRequisitionResource where requestRequisitionSkill= :skillId )");
			
			
			sqlQuery.append("SELECT rc.`resource_Comment_id`,rc.`resource_id`, rc.`comment_date`,rc.`resource_comment`, rc.`comment_by`");
			sqlQuery.append("FROM ");
			sqlQuery.append("resource_comment rc ");
			sqlQuery.append("WHERE rc.`resource_id` IN ");
			sqlQuery.append("( ");
			sqlQuery.append("SELECT srr.`id` FROM ");
			sqlQuery.append("`skill_request_resource` srr ");
			sqlQuery.append("WHERE srr.`skill_request_id` = :skillId ");
			sqlQuery.append(")");
			
			//Criteria criteria = session.createCriteria(ResourceComment.class);
			//criteria.add(Subqueries.)
			
			Query query = session.createSQLQuery(sqlQuery.toString());
	
			query.setParameter("skillId", skillId);
			list = query.list();
			
			Iterator it = list.iterator();
			while(it.hasNext()){
			     Object[] line = (Object[]) it.next();
			     ResourceComment eq = new ResourceComment();
			     eq.setResourceCommentId((Integer) line[0]);
			     eq.setResourceId((Integer) line[1]);
			     eq.setComment_Date((Date) line[2]);
			     eq.setResourceComment((String) line[3]);
			     eq.setCommentBy((String) line[4]);

			     domainList.add(eq);
			}
			
		/*	DetachedCriteria bolgsEntries = DetachedCriteria.forClass(RequestRequisitionResource.class)
				      .add(  Property.forName("requestRequisitionSkill").eq(skillId) );
				 
			list = 	      session.createCriteria(ResourceComment.class)
					.add( Subqueries.in("resourceId", bolgsEntries) )
				          .list();*/
			
			
		}catch (HibernateException e) {
			// TODO: handle exception
		}
		
		
		return domainList;
	}

	public boolean hardDeleteSkillRequest(Integer skillId) {
		boolean isDeleted = false;
		Session currentSession = (Session) getEntityManager().getDelegate();
		Query query = currentSession.createQuery("delete RequestRequisitionSkill where id =:id");
		query.setParameter("id", skillId);
		int result = query.executeUpdate();
		if (result > 0) {
			isDeleted = true;
		}
		
		return isDeleted;
	}


}
