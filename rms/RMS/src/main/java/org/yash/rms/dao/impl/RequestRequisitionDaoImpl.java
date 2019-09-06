package org.yash.rms.dao.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.yash.rms.dao.RequestRequisitionDao;
import org.yash.rms.domain.Customer;
import org.yash.rms.domain.RequestRequisition;
import org.yash.rms.domain.RequestRequisitionSkill;
import org.yash.rms.exception.DAOException;
import org.yash.rms.util.Constants;

@Repository("RequestRequisitionDao")
public class RequestRequisitionDaoImpl implements RequestRequisitionDao{

	@PersistenceContext(name = "mysql", type = PersistenceContextType.TRANSACTION)
	private EntityManager entityManager;
	
	
	public EntityManager getEntityManager() {
		return entityManager;
	}
	
	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	  private static final Logger logger = LoggerFactory.getLogger(RequestRequisitionDaoImpl.class);
	  
	public RequestRequisition findRequestRequisitionById(Integer requestRequisitionId) {
		RequestRequisition requestRequisition = new RequestRequisition();
		 Session session = (Session) getEntityManager().getDelegate();
         try {
                         Criteria criteria = session.createCriteria(RequestRequisition.class);
                         criteria.add(Restrictions.eq("id", requestRequisitionId.intValue()));
                         requestRequisition = (RequestRequisition) criteria.uniqueResult();
         } catch (HibernateException e) {
                         e.printStackTrace();
                         throw e;
         }
		    return requestRequisition;
	}

	@Transactional(isolation=Isolation.READ_COMMITTED)
	public void save(RequestRequisition requestRequisition) {
		
		Session currentSession = (Session) getEntityManager().getDelegate();
			currentSession.saveOrUpdate(requestRequisition);
	}
	
	public boolean delete(int id) {
		return true;
	}

	public boolean saveOrupdate(RequestRequisition requestRequisition) {
	
		logger.info("--------RequestRequisitionSkillDaoImpl saveOrUpdate method start-------");
		boolean isSuccess = false;
		if (null == requestRequisition )
			return false;
		try {
			Session currentSession = (Session) getEntityManager().getDelegate();
			currentSession.saveOrUpdate(requestRequisition);
			isSuccess = true;
		} catch (HibernateException hex) {
			isSuccess = false;
			logger.error("Exception occured in RequestRequisitionDaoImpl saveOrUpdate method at DAO layer:-"+ hex);
			hex.printStackTrace();
			throw new DAOException(DAOException._SQL_ERROR, hex.getMessage());
		} 
		logger.info("--------RequestRequisitionDaoImpl saveOrUpdate method End-------");
		
		return isSuccess;
	}

	public List<RequestRequisition> findAll() {
		return null;
	}

	public List<RequestRequisition> findByEntries(int firstResult, int sizeNo) {
		// TODO Auto-generated method stub
		return null;
	}

	public long countTotal() {
		// TODO Auto-generated method stub
		return 0;
	}

	public List<Customer> getRequestRequisitionsCustomer(Integer userId, String userRole, List<Integer> projectList) {
		logger.info("--------getRequestRequisitionsGroup of RequestRequisitionDaoImpl method start-------");
		Session session = (Session) getEntityManager().getDelegate();
		List<Object[]> objectList = null;
		List<Customer> customerList= new ArrayList<Customer>();
		try{
			Query query =  null;
			
			StringBuilder sqlQuery = new StringBuilder();
			HashMap<String,Object> parameterList = new HashMap<String, Object>();
			sqlQuery.append(" select distinct cus.id, cus.customer_name ");
			sqlQuery.append(" from skill_request_requisition skr, request_requisition req, customer cus ");
			sqlQuery.append(" WHERE skr.request_id = req.id and req.`customer_id` = cus.`id` ");
			
			if(!userRole.equalsIgnoreCase(Constants.ROLE_ADMIN)){
				sqlQuery.append(" AND (EXISTS (SELECT FIND_IN_SET(:sendEmailId, REPLACE(req1.`sent_mail`,' ','')) AS result ");
				sqlQuery.append(" FROM  request_requisition req1 WHERE req1.id = req.`id` HAVING result >0) ");
				sqlQuery.append(" OR EXISTS (SELECT FIND_IN_SET(:toNotifyId, REPLACE(req2.notify_to,' ','')) AS result ");
				sqlQuery.append(" FROM  request_requisition req2 WHERE req2.id = req.`id` HAVING result >0) ");
				sqlQuery.append(" OR EXISTS( SELECT 1 FROM `pdl_resource_mapping` prm WHERE prm.pdl_id IN (req.`pdl_email`) AND prm.`resource_id`=:pdlEmailId)  ");
				sqlQuery.append(" OR req.`emp_id` = :employeeId ");
				
				parameterList.put("sendEmailId",userId);
				parameterList.put("toNotifyId",userId);
				parameterList.put("pdlEmailId",userId);
				parameterList.put("employeeId",userId);
				
				if(projectList != null && projectList.size()>0){
					sqlQuery.append(" OR req.`project_id` in (:projectId) ");
					parameterList.put("projectId",projectList);
				}
				sqlQuery.append(" )");
			}
			sqlQuery.append(" order by cus.customer_name ");
			query = session.createSQLQuery(sqlQuery.toString());
			query.setProperties(parameterList);
			
			objectList = query.list();
			if(objectList !=null && objectList.size()>0){
				Customer customer = null;
				int columnIndex = 0; 
				for(Object[] row : objectList){
					customer = new Customer();
					columnIndex = 0;
					customer.setId((Integer)row[columnIndex]);
					columnIndex++;
					customer.setCustomerName((String)row[columnIndex]);
					customerList.add(customer);
				}
			}
		}catch (HibernateException he) {
			he.printStackTrace();
			logger.error("--------getRequestRequisitionsGroup of RequestRequisitionDaoImpl method ended with error-------");
		}
		logger.info("--------getRequestRequisitionsGroup of RequestRequisitionDaoImpl method ended-------");
		return customerList;
	}
	
	public Object getBUHApprovalById(Integer requestID) {
		Object file = new Object();
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Session currentSession = (Session) getEntityManager().getDelegate();
			
			Query query  = currentSession.createQuery("select BUHApprovalFile, BUHApprovalFileName from RequestRequisition where id =:id");
			
			query.setParameter("id", requestID);

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
	
	public Object getBGHApprovalById(Integer requestID) {
		Object file = new Object();
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Session currentSession = (Session) getEntityManager().getDelegate();
			
			Query query  = currentSession.createQuery("select BGHApprovalFile, BGHApprovalFileName from RequestRequisition where id =:id");
			
			query.setParameter("id", requestID);

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
	//method for providing RRF with Open status details for future closing project as per specified days in xml
	public List<RequestRequisitionSkill> getRRFDetailsForClosingProject(){
		logger.info("--------getRRFDetailsForClosingProject method of RequestRequisitionDaoImpl start-------");
		List<RequestRequisitionSkill> list=new ArrayList<RequestRequisitionSkill>();

		Calendar c = Calendar.getInstance();    
		c.add(Calendar.DATE, Integer.parseInt(Constants.CLOSING_PROJECT_RRF_DAYS));
		Date date=c.getTime();
		try{
			
			Session currentSession = (Session) getEntityManager().getDelegate();
			Criteria criteria=currentSession.createCriteria(RequestRequisitionSkill.class,"rrs");
			//criteria.add(Restrictions.eq("rrs.status",Constants.OPEN));
			//criteria.add(Restrictions.not(Restrictions.ilike("rrs.status", "%"+Constants.CLOSED+"%")));
			criteria.add(Restrictions.eq("rrs.isDeleted",false));
			Criteria rrCriteria=criteria.createAlias("rrs.requestRequisition", "rr");
			Criteria prCriteria=rrCriteria.createAlias("rr.project", "project");
			
			prCriteria.add(Restrictions.eq("project.projectEndDate", date));
		
			list=prCriteria.list();
			
		}
		catch(HibernateException he){
			logger.error("Some database error occured in getRRFDetailsForClosingProject method of RequestRequisitionDaoImpl  ");
			he.printStackTrace();
			throw he;
		}
		catch(Exception e)
		{
			logger.error("Some error occured in getRRFDetailsForClosingProject method of RequestRequisitionDaoImpl  ");
			e.printStackTrace();
		}
		logger.info("--------getRRFDetailsForClosingProject method of RequestRequisitionDaoImpl end-------");
		return list;
	}
	
	public List<RequestRequisitionSkill> findRRFByProjectId(Integer proejectId)
	{
		logger.info("--------findRRFByProjectId method of RequestRequisitionDaoImpl start-------");
		List<RequestRequisitionSkill> list=new ArrayList<RequestRequisitionSkill>();
		try{
			
			Session currentSession = (Session) getEntityManager().getDelegate();
			Criteria criteria=currentSession.createCriteria(RequestRequisitionSkill.class,"rrs");
			//criteria.add(Restrictions.ne("rrs.status",Constants.CLOSED));
			//criteria.add(Restrictions.not(Restrictions.ilike("rrs.status", "%"+Constants.CLOSED+"%")));
			criteria.add(Restrictions.eq("rrs.isDeleted",false));
			Criteria rrCriteria=criteria.createAlias("rrs.requestRequisition", "rr");
			Criteria prCriteria=rrCriteria.createAlias("rr.project", "project");
			rrCriteria.add(Restrictions.eq("project.id", proejectId));
			list=criteria.list();
			
		}catch(HibernateException he){
			logger.error("Some database error occured in findRRFByProjectId method of RequestRequisitionDaoImpl  ");
			he.printStackTrace();
			throw he;
		}catch(Exception ex)
		{
			logger.error("Some error occured in findRRFByProjectId method of RequestRequisitionDaoImpl  ");
			ex.printStackTrace();
		}
		return list;
		
	}

}
