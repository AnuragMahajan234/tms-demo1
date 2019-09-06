package org.yash.rms.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.yash.rms.dao.RequestRequisitionResourceDao;
import org.yash.rms.domain.AllocationType;
import org.yash.rms.domain.RequestRequisitionResource;
import org.yash.rms.domain.RequestRequisitionResourceStatus;
import org.yash.rms.domain.RequestRequisitionSkill;
import org.yash.rms.util.DateUtil;
import org.yash.rms.util.RequestRequisitionStatusConstants;

/**
 * 
 * @author samiksha.sant
 *
 */

@Repository("requestRequisitionResourceDao")
@Transactional
public class RequestRequisitionResourceDaoImpl implements RequestRequisitionResourceDao {

	@PersistenceContext(name = "mysql", type = PersistenceContextType.TRANSACTION)
	private EntityManager entityManager;
	
	
	public EntityManager getEntityManager() {
		return entityManager;
	}
	
	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	private static final Logger logger = LoggerFactory.getLogger(RequestRequisitionResourceDaoImpl.class);

	public List<RequestRequisitionResource> updateResourceRequestWithStatus( List<RequestRequisitionResource> resources) {
		Session session = (Session) getEntityManager().getDelegate();
		System.out.println(" $$$$ updateResourceRequestWithStatus $$$$" + resources.size());
		session.flush();
  
		try {
			for (RequestRequisitionResource requestRequisitionResource : resources) {
    
				System.out.println(" $$$$ updateResourceRequestWithStatus $$$$"+requestRequisitionResource.getRequestRequisitionResourceStatus().getStatusName());
				session.saveOrUpdate(requestRequisitionResource);
			}
     
		} catch (HibernateException e) {
			e.printStackTrace();
			throw e;
		} finally {
   
			session.flush();
		}

	return null;
 }

	public List<RequestRequisitionResource> getRequestRequisitionResourceList(Integer id) {
		Session session = (Session) getEntityManager().getDelegate();
		List<RequestRequisitionResource> requestRequisitionResources = new ArrayList<RequestRequisitionResource>();
		try {
			Criteria criteria = session.createCriteria(RequestRequisitionResource.class);
			criteria.add(Restrictions.eq("id", id));
			requestRequisitionResources = criteria.list();
		} catch (HibernateException e) {
			logger.error("--------- getRequestRequisitionResourceList -----", e);
		}
		return requestRequisitionResources;
	}

	public void saveRequestRequisitionResource(List<RequestRequisitionResource> requisitionResources,
			RequestRequisitionSkill skill, List<RequestRequisitionSkill> requestRequisitionResources) {

		try {
			Session session = (Session) getEntityManager().getDelegate();
			RequestRequisitionResourceStatus resourceStatus = new RequestRequisitionResourceStatus();
			Integer submissions;
			for(RequestRequisitionSkill requisitionResource :requestRequisitionResources) {
				/*if(!StringUtils.isEmpty(requisitionResource.getRequestRequisition().getRequiredFor()) && requisitionResource.getRequestRequisition().getRequiredFor().equalsIgnoreCase(Constants.STAFFING)) {
					resourceStatus = getStatusByStatusName(RequestRequisitionStatusConstants.SHARED_TO_POC.toString());
				}
				else*/ 
			resourceStatus = getStatusByStatusName(RequestRequisitionStatusConstants.SUBMITTED_TO_POC.toString()); // for first time
			}
			if (skill.getSubmissions() != null) {
				submissions = skill.getSubmissions();
			} else {
				submissions = getAllRequestRequisitionResourcesBySkillID(skill.getId()).size();
				System.out.println("submission before dao call"+submissions );
				saveSubmissionsInRequestRequisitionSkill(skill.getId(), submissions+requisitionResources.size());
			}

			for (RequestRequisitionResource requestRequisitionResource : requisitionResources) {
				requestRequisitionResource.setRequestRequisitionResourceStatus(resourceStatus);
				requestRequisitionResource.setCreationTimestamp(DateUtil.getTodaysDate());
				requestRequisitionResource.setLastupdatedTimestamp(DateUtil.getTodaysDate());
				requestRequisitionResource.setSubmittedToPocDate(new Date());
				/*
				 * submissions++; skill.setSubmissions(submissions);
				 * session.merge(skill);
				 */
				// requestRequisitionResource.setRequestRequisitionSkill(skill);
				session.merge(requestRequisitionResource);
				System.out.println(requestRequisitionResource);
			}
			
		} catch (Exception exception) {

			exception.printStackTrace();
		}

	}

	public RequestRequisitionResourceStatus getStatusByStatusName(String status) {
		Session session = (Session) getEntityManager().getDelegate();
		Criteria criteria = session.createCriteria(RequestRequisitionResourceStatus.class);

		RequestRequisitionResourceStatus requestRequisitionResourceStatus = (RequestRequisitionResourceStatus) criteria
				.add(Restrictions.ilike("statusName", status)).uniqueResult();

		return requestRequisitionResourceStatus;

	}

	public List<RequestRequisitionResource> getDataForAddDeleteResourcebySkillRequestID(
			Integer requestRequisitionSkillId) {
		logger.info("--------ResourceDaoImpl findAll method start-------");
		List<RequestRequisitionResource> requestReportList = new ArrayList<RequestRequisitionResource>();
		Session session = (Session) getEntityManager().getDelegate();
		try {
			Criteria criteria = session.createCriteria(RequestRequisitionResource.class);
			criteria.add(Restrictions.eq("requestRequisitionSkill.id", requestRequisitionSkillId));
			requestReportList = criteria.list();
		} catch (HibernateException e) {
			e.printStackTrace();
			logger.error("Exception occured in findAll method at DAO layer:-" + e);
			throw e;
		}
		logger.info("--------ResourceDaoImpl findAll method end-------");

		return requestReportList;
	}
	
	/**
	 * Created by Vidhika jain 1006922
	 * Method to get All the  RequestRequisitionResource having Status as "SELECTED"
	 */
	public List<RequestRequisitionResource> getAllSelectedRequestRequisitionResourceList() {
		Session session = (Session) getEntityManager().getDelegate();
		Query query = session.createQuery("from RequestRequisitionResource where requestRequisitionResourceStatus.statusName = :statusName");
		query.setParameter("statusName", "SELECTED");
		List<RequestRequisitionResource> requestRequisitionResourceList = query.list();
		return requestRequisitionResourceList;
	}
	
	public RequestRequisitionResourceStatus getRequestRequisitionResourceStatusById(Integer profileStaus) {
		Session session = (Session) getEntityManager().getDelegate();
		RequestRequisitionResourceStatus requestRequisitionResourceStatus = (RequestRequisitionResourceStatus) session
				.load(RequestRequisitionResourceStatus.class, profileStaus);
		return requestRequisitionResourceStatus;
	}

	public AllocationType getAllocationTypeById(Integer allocationId) {
		Session session = (Session) getEntityManager().getDelegate();
		AllocationType allocationType = (AllocationType) session.load(AllocationType.class, allocationId);
		return allocationType;
	}

	/*
	 * 
	 * public List<RequestRequisitionResource>
	 * updateResourceRequestWithStatus(List<RequestRequisitionResource>
	 * resources, int skillId, int projectId) {
	 * 
	 * 
	 * Session session = (Session) getEntityManager().getDelegate(); boolean
	 * isDeleted = false; int result = 0; List<RequestRequisitionResource> list
	 * = new ArrayList<RequestRequisitionResource>(); try {
	 * 
	 * for (int i = 0; i < resources.size(); i++) { RequestRequisitionResource
	 * request2 = resources.get(i); try { Criteria criteria =
	 * session.createCriteria(RequestRequisitionResource.class)
	 * .add(Restrictions.eq("id", request2.getId()));
	 * criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY)
	 * ; RequestRequisitionResource loadedRequest = (RequestRequisitionResource)
	 * criteria.uniqueResult(); RequestRequisitionResource finalRequest =
	 * mergeRequestSkillResource(request2, loadedRequest); //
	 * session.load(Request.class, request.getId()); RequestRequisitionResource
	 * skilRes = addResourceForResourceAllocation(request2, loadedRequest,
	 * projectId); if (skilRes != null) { list.add(skilRes); }
	 * 
	 * System.out.println("list size===" + list.size());
	 * session.merge(finalRequest);
	 * 
	 * request.setProjectName("abcd"); session.update(request);
	 * 
	 * } catch (HibernateException e) { e.printStackTrace();
	 * logger.error("Exception occured in findAll method at DAO layer:-" + e);
	 * throw e; } }
	 * 
	 * } catch (HibernateException e) { e.printStackTrace();
	 * logger.error("Exception occured in findAll method at DAO layer:-" + e);
	 * throw e; }
	 * 
	 * logger.info("--------ResourceDaoImpl findAll method end-------");
	 * 
	 * return list; }
	 * 
	 * 
	 * }
	 */
	
	public List<RequestRequisitionResource> getAllRequestRequisitionResourcesBySkillID(Integer skillId){
		Session session = (Session) getEntityManager().getDelegate();
		
		Criteria criteria = session.createCriteria(RequestRequisitionResource.class);
		
		criteria.add(Restrictions.eq("requestRequisitionSkill.id", skillId));
		
		List<RequestRequisitionResource> requestRequisitionResourceList = criteria.list();
		
		return requestRequisitionResourceList;
		
	}
	
	public void saveSubmissionsInRequestRequisitionSkill(Integer skillID, Integer submissionsCount){
		Session session = (Session) getEntityManager().getDelegate();
		
		String hql = "UPDATE RequestRequisitionSkill set submissions = :submissions "  + 
	             "WHERE id = :skillid";
		
		Query query = session.createQuery(hql);
		query.setParameter("submissions", submissionsCount);
		query.setParameter("skillid", skillID);
		
		int result = query.executeUpdate();
		System.out.println(result);
		
	}
	
	public List<RequestRequisitionResource> getSelectedResourceByRequestRequisitionSkillId(
			Integer requestRequisitionSkillId) {
		
		List<RequestRequisitionResource> requestReportList= null;
		Session session = (Session) getEntityManager().getDelegate();
		try {
			Query query = session.createQuery("from RequestRequisitionResource where requestRequisitionResourceStatus.statusName = :statusName and requestRequisitionSkill.id =:id");
			query.setParameter("statusName", "SELECTED");
			query.setParameter("id", requestRequisitionSkillId);
			requestReportList = query.list();
		} catch (HibernateException e) {
			e.printStackTrace();
			throw e;
		}
		return requestReportList;
	}
	
	/*public List<RequestRequisitionResource> getRequestRequisitionResources(Integer userId, String userRole,Integer skillResourceRequisitionId, List<Integer> resourceStatusList){
		List<RequestRequisitionResource> requestReportList= null;
		Session session = (Session) getEntityManager().getDelegate();
		logger.info("--------getRequestRequisitionResources of RequestRequisitionResourceDaoImpl method started-------");
		try {
			Criteria criteria = session.createCriteria(RequestRequisitionResource.class);
			criteria.createAlias("requestRequisitionSkill", "reqResSkill");
			criteria.createAlias("requestRequisitionResourceStatus", "resStatus");
			if(skillResourceRequisitionId !=null && skillResourceRequisitionId>=0){
				criteria.add(Restrictions.eq("reqResSkill.id",skillResourceRequisitionId));
			}
			if(resourceStatusList !=null && !resourceStatusList.contains(0)){
				criteria.add(Restrictions.in("resStatus.id", resourceStatusList));
			}
			if(!Constants.ROLE_ADMIN.equalsIgnoreCase(userRole)){
				criteria.add(Restrictions.eq("reqResSkill.requestRequisition.resource.employeeId",userId));
			}
			requestReportList = criteria.list();
		} catch (HibernateException e) {
			e.printStackTrace();
			logger.error("--------getRequestRequisitionResources of RequestRequisitionResourceDaoImpl method ended with error-------");
			throw e;
		}
		logger.info("--------getRequestRequisitionResources of RequestRequisitionResourceDaoImpl method ended successfully-------");
		return requestReportList;
	}*/
	
	public List<Object[]> getRequestRequisitionResources(Integer userId, String userRole,Integer skillResourceRequisitionId, List<Integer> resourceStatusList){
		List<Object[]> requestReportList= null;
		Session session = (Session) getEntityManager().getDelegate();
		logger.info("--------getRequestRequisitionResources of RequestRequisitionResourceDaoImpl method started-------");
		try {
			String sql =	" SELECT srr.id, srr.external_resource_name,  skillReqRqstn.req_id , srr.skill_request_id,  statusType.status_name, srr.status_id, "+
							" srr.skills, srr.email_id, srr.contact_number, srr.total_experience, srr.notice_Period,srr.location,srr.allocation_date, "+
							" srr.interview_date,srr.dt_joined, srr.dt_submitted_to_poc,srr.dt_rejection,srr.dt_shortlisted,srr.dt_submitted_to_am, " +
							" r.first_name, r.last_name,comp.skill res_comp, r.total_experience res_total_exp, r.email_id res_email_id, r.contact_number res_contact_num,"+
							" desig.designation_name, r.employee_id FROM skill_request_resource srr INNER JOIN " + 
							" skill_resource_status_type statusType ON srr.status_id=statusType.id INNER JOIN skill_request_requisition skillReqRqstn "+ 
							" ON srr.skill_request_id=skillReqRqstn.id LEFT OUTER JOIN allocationtype allocType ON skillReqRqstn.billable=allocType.id "+ 
							" LEFT OUTER JOIN request_requisition reqReqstn ON  skillReqRqstn.request_id=reqReqstn.id LEFT OUTER JOIN resource r ON r.employee_id = srr.resource_id "+
							" LEFT OUTER JOIN competency comp ON r.competency = comp.id  LEFT OUTER JOIN  DESIGNATIONS desig ON r.designation_id=desig.id "+
							"  WHERE skillReqRqstn.id= :reqId " ;
			SQLQuery query = session.createSQLQuery(sql);
			query.setParameter("reqId", skillResourceRequisitionId);
			requestReportList= query.list();
		}catch (HibernateException e) {
			e.printStackTrace();
			logger.error("--------error in getResourceByID of RequestRequisitionResourceDaoImpl method started-------");
			throw e;
		}
		logger.info("--------getRequestRequisitionResources of RequestRequisitionResourceDaoImpl method ended successfully-------");
		return requestReportList;
	}

	public List<Object[]> getLatestCommentOnResource(Integer skillRequestId) {
		
		logger.info("--------getResourceByID of RequestRequisitionResourceDaoImpl method started-------");
		List<Object[]> resourceCommentList = null;
		
		Session session = (Session) getEntityManager().getDelegate();
		try {
			String sql = "SELECT t1.* FROM  (SELECT IFNULL(s.`external_resource_name`, CONCAT(r.first_name,' ',r.last_name)) resource_name,"+
			" CASE WHEN external_resource_name IS NULL THEN 'Internal' ELSE 'External' END resource_type, " +
			"rc.resource_comment,rc.comment_date,rc.comment_by, rc.`resource_Comment_id`, rc.resource_id FROM "  +
			"resource_comment rc LEFT JOIN skill_request_resource s  ON rc.resource_id=s.id " +
			"LEFT JOIN resource r ON s.resource_id=r.employee_id  "+
		    "WHERE  s.skill_request_id= :id) t1 ORDER BY  t1.`resource_id` ,t1.`comment_date` DESC";
			SQLQuery query = session.createSQLQuery(sql);
			query.setParameter("id", skillRequestId);
			resourceCommentList= query.list();
		}catch (HibernateException e) {
			e.printStackTrace();
			logger.error("--------error in getResourceByID of RequestRequisitionResourceDaoImpl method started-------");
			throw e;
		}
		logger.info("--------getResourceByID of RequestRequisitionResourceDaoImpl method started-------");
		
	   return resourceCommentList;
	}
	
	public RequestRequisitionResource getResourceBySkillIdAndResourceId(Integer resourceId, Integer skillId) {
		Session session = (Session) getEntityManager().getDelegate();
		Criteria criteria = session.createCriteria(RequestRequisitionResource.class);
		return (RequestRequisitionResource)criteria.add(Restrictions.eq("requestRequisitionSkill.id", skillId)).add(Restrictions.eq("id", resourceId)).uniqueResult();
	}

	public List<Object[]> getResourceRequisitionProfileStatusCount(Integer userId, String role, Date startDate, Date endDate,
			List<Integer> customerList) {
		List<Object[]> resourceRequisitionProfileStatusCountList = null;
		logger.info("--------getResourceRequisitionProfileStatusCount of RequestRequisitionResourceDaoImpl method started-------");
		Session session = (Session) getEntityManager().getDelegate();
		HashMap<String,Object> parameterList = new HashMap<String, Object>();
		try {
			StringBuilder sqlQuery = new StringBuilder();
			sqlQuery.append(" SELECT cus.`id` AS customer_id, cus.`customer_name`,'1' AS id, 'SUBMITTED' AS status_name, (CASE WHEN resource_id IS NULL THEN 1 ELSE 2 END) AS resource_type, COUNT(*) AS total, 2 AS record_order ");
			sqlQuery.append(" FROM skill_request_resource skr, `skill_request_requisition` srr, `request_requisition` rr, `customer` cus ");
			sqlQuery.append(" WHERE skr.`skill_request_id` = srr.id AND srr.`request_id` = rr.id AND rr.`customer_id` = cus.`id` ");
			sqlQuery.append(" AND `dt_submitted_to_poc` >= :startDate0 AND `dt_submitted_to_poc` <= :endDate0  ");
			if(customerList != null && customerList.size()>0){
				sqlQuery.append(" AND cus.id IN (:customerIds0) ");
			}
			sqlQuery.append(" GROUP BY resource_type, cus.`id`, cus.`customer_name` ");
			sqlQuery.append(" UNION  ");
			sqlQuery.append(" SELECT cus.`id` AS customer_id, cus.`customer_name`,'2' AS id, 'SELECTED' AS status_name, (CASE WHEN resource_id IS NULL THEN 1 ELSE 2 END) AS resource_type, COUNT(*) AS total, 4 AS record_order ");
			sqlQuery.append(" FROM skill_request_resource skr, `skill_request_requisition` srr, `request_requisition` rr, `customer` cus ");
			sqlQuery.append(" WHERE skr.`skill_request_id` = srr.id AND srr.`request_id` = rr.id AND rr.`customer_id` = cus.`id` AND `dt_shortlisted` >= :startDate1 AND `dt_shortlisted` <= :endDate1 ");
			if(customerList != null && customerList.size()>0){
				sqlQuery.append(" AND cus.id IN (:customerIds1) ");
			}
			sqlQuery.append(" GROUP BY resource_type, cus.`id`, cus.`customer_name` ");
			sqlQuery.append(" UNION ");
			sqlQuery.append(" SELECT cus.`id` AS customer_id, cus.`customer_name`,'8' AS id, 'JOINED' AS status_name, (CASE WHEN resource_id IS NULL THEN 1 ELSE 2 END) AS resource_type, COUNT(*) AS total, 5 AS record_order ");
			sqlQuery.append(" FROM skill_request_resource skr, `skill_request_requisition` srr, `request_requisition` rr, `customer` cus ");
			sqlQuery.append(" WHERE skr.`skill_request_id` = srr.id AND srr.`request_id` = rr.id AND rr.`customer_id` = cus.`id` AND dt_joined >= :startDate2 AND `dt_joined` <= :endDate2 ");
			if(customerList != null && customerList.size()>0){
				sqlQuery.append(" AND cus.id IN (:customerIds2) ");
			}
			sqlQuery.append(" GROUP BY resource_type, cus.`id`, cus.`customer_name` ");
			sqlQuery.append(" UNION ");
			sqlQuery.append(" SELECT cus.`id` AS customer_id, cus.`customer_name`,'3' AS id, 'REJECTED' AS status_name, (CASE WHEN resource_id IS NULL THEN 1 ELSE 2 END) AS resource_type, COUNT(*) AS total, 6 AS record_order ");
			sqlQuery.append(" FROM skill_request_resource skr, `skill_request_requisition` srr, `request_requisition` rr, `customer` cus ");
			sqlQuery.append(" WHERE skr.`skill_request_id` = srr.id AND srr.`request_id` = rr.id AND rr.`customer_id` = cus.`id` AND dt_rejection >= :startDate3 AND `dt_rejection` <= :endDate3 ");
			if(customerList != null && customerList.size()>0){
				sqlQuery.append(" AND cus.id IN (:customerIds3) ");
			}
			sqlQuery.append(" GROUP BY resource_type, cus.`id`, cus.`customer_name` ");
			sqlQuery.append(" UNION ");
			sqlQuery.append(" SELECT cus.`id` AS customer_id ,cus.`customer_name`,'-1' AS id, 'TOTAL OPEN' AS status_name, 0 AS resource_type, SUM(srr.`req_resources`) AS total, 1 AS record_order ");
			sqlQuery.append(" FROM `request_requisition` rr, `skill_request_requisition` srr, `customer` cus ");
			sqlQuery.append(" WHERE rr.id = srr.`request_id` AND cus.id = rr.customer_id ");
			sqlQuery.append(" AND rr.`creation_timestamp` >= :startDate4 AND rr.`creation_timestamp` <= :endDate4 " );
			if(customerList != null && customerList.size()>0){
				sqlQuery.append(" AND cus.id IN (:customerIds4) ");
			}
			sqlQuery.append(" GROUP BY rr.customer_id ");
			sqlQuery.append(" ORDER BY customer_name, record_order ");
			for(int i = 0 ; i <= 4 ; i++){
				parameterList.put("startDate"+i,startDate);
				parameterList.put("endDate"+i,endDate);
				if(customerList != null && customerList.size()>0 && i<=4){
					parameterList.put("customerIds"+i,customerList);
				}
			}
			SQLQuery query = session.createSQLQuery(sqlQuery.toString());
			query.setProperties(parameterList);
			resourceRequisitionProfileStatusCountList = query.list();
		}catch (HibernateException e) {
			e.printStackTrace();
			logger.error("--------error in getResourceRequisitionProfileStatusCount of RequestRequisitionResourceDaoImpl method started-------");
			throw e;
		}
		logger.info("--------getResourceRequisitionProfileStatusCount of RequestRequisitionResourceDaoImpl method ended-------");
		return resourceRequisitionProfileStatusCountList;
	}

	public RequestRequisitionResource getRequestResourceById(Integer requestResourceId) throws Exception {
		RequestRequisitionResource requestRequisitionResource = new RequestRequisitionResource();
		try {
			Session session = (Session) getEntityManager().getDelegate();
			Criteria criteria = session.createCriteria(RequestRequisitionResource.class);
			criteria.add(Restrictions.idEq(requestResourceId));
			 requestRequisitionResource = (RequestRequisitionResource) criteria.uniqueResult();
		} catch (Exception e) { 	
			throw e;
		}
		return requestRequisitionResource;
	}
	
}
