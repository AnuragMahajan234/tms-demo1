package org.yash.rms.dao.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.servlet.http.HttpServletRequest;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yash.rms.dao.ActivityDao;
import org.yash.rms.dao.ResourceDao;
import org.yash.rms.dao.TimeHoursDAO;
import org.yash.rms.dao.UserActivityDao;
import org.yash.rms.domain.Activity;
import org.yash.rms.domain.ApproveStatus;
import org.yash.rms.domain.BgBuView;
import org.yash.rms.domain.OrgHierarchy;
import org.yash.rms.domain.Project;
import org.yash.rms.domain.Resource;
import org.yash.rms.domain.ResourceAllocation;
import org.yash.rms.domain.Timehrs;
import org.yash.rms.domain.TimehrsEmployeeIdProjectView;
import org.yash.rms.domain.TimehrsView;
import org.yash.rms.domain.UserActivity;
import org.yash.rms.dto.UserContextDetails;
import org.yash.rms.util.ConcatenableIlikeCriterion;
import org.yash.rms.util.GenericCriteria;
import org.yash.rms.util.ResourceAllocationSearchCriteria;
import org.yash.rms.util.ResourceSearchCriteria;
import org.yash.rms.util.SearchCriteriaGeneric;


@Service("TimeHoursDao")
@Transactional
public class TimeHoursDaoImpl implements TimeHoursDAO{

	
	@PersistenceContext(name = "mysql", type = PersistenceContextType.TRANSACTION)
	private EntityManager entityManager;
	
	
	public EntityManager getEntityManager() {
		return entityManager;
	}
	
	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	
	@Autowired @Qualifier("UserActivityDao")
	private UserActivityDao userActivityDao;
	@Autowired @Qualifier("ResourceDao")
	private ResourceDao resourceDao;
	private static final Logger logger = LoggerFactory.getLogger(TimeHoursDaoImpl.class);
	
	@Autowired @Qualifier("ActivityDao")
	private ActivityDao activityDao;
	
	public boolean delete(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean saveOrupdate(Timehrs timehours) {
		logger.info("-------TimeHoursDaoImpl saveOrUpdate method start -------- ");
		Session session = (Session) getEntityManager().getDelegate();
		boolean isSuccess = false;

		try {
			if (timehours.getId() != null) {

				isSuccess = session.merge(timehours) != null ? true : false;
			} else {
				isSuccess = session.save(timehours) != null ? true : false;
			
			}
		} catch (HibernateException hibernateException) {
			logger.error("Error occured in saveOrUpdate method:" + hibernateException);
		} finally {
			session.flush();
		}
		logger.info("-------TimeHoursDaoImpl saveOrUpdate method end -------- ");
		return isSuccess;
	}

	public List<Timehrs> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Timehrs> findByEntries(int firstResult, int sizeNo) {
		// TODO Auto-generated method stub
		return null;
	}

	public long countTotal() {
		// TODO Auto-generated method stub
		return 0;
	}

	public List<TimehrsView> findTimehrsEntries(int firstResult, int maxResults) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	@SuppressWarnings({ "rawtypes" })
	@Transactional
	public List<List> adminView(String activeOrAll) {
		// TODO Auto-generated method stub
		return this.adminView(false,activeOrAll,null,null,null);
	}
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Transactional
	public List<List> adminView(boolean requireCurrentProject,String activeOrAll,List<Integer> empIds,Character timeSheetStatus,UserContextDetails userContextDetails) {
		logger.info("-------TimeHoursDaoImpl adminView method start -------- ");
		List<org.yash.rms.domain.TimehrsView> adminList = new ArrayList<TimehrsView>();
    	List<TimehrsEmployeeIdProjectView> projectAllocatedList = null;
    	Session session = (Session) getEntityManager().getDelegate();
    	Query query=null;
    	List<org.yash.rms.domain.TimehrsView> adminList1 = null;
    	List<String> projectIds1 = null;
    	if(activeOrAll.equalsIgnoreCase("active")){
    	    query = session
    	        .createQuery("Select projectName FROM Project WHERE offshoreDelMgr.employeeId=:resourceId AND (projectEndDate >(:currentDate) or projectEndDate IS NULL)");
    	    query.setParameter("resourceId", userContextDetails.getEmployeeId());
    	    query.setParameter("currentDate", new Date());
    	    }
    	    else{
    	      query = session
    	          .createQuery("Select projectName FROM Project WHERE offshoreDelMgr.employeeId=:resourceId");
    	      query.setParameter("resourceId", userContextDetails.getEmployeeId());
    	    }
    	
        projectIds1=query.list();
        query=null;
    	try{   
    		if(activeOrAll.equalsIgnoreCase("active")){
    			if(timeSheetStatus.compareTo('L')==0){
    				query = session.createQuery("FROM TimehrsView where employeeId in (select employeeId from Resource where releaseDate >(:currentdate) or releaseDate is null)");
    				query.setParameter("currentdate", new Date());
    				adminList1 = query.list();
    				if(activeOrAll.equalsIgnoreCase("active")){
    					List<Resource> resources =  resourceDao.findActiveResources();
    					for(Resource resource:resources){
    						for(TimehrsView view:adminList1){
    							if(view.getEmployeeId().equals(resource.getEmployeeId())){
    								adminList.add(view);
    							}
    						}
    					}
    				}
    			}else if(timeSheetStatus.compareTo('P')==0){
	    	 query = session.createQuery(" FROM TimehrsView  where employeeId IN (:empIds)"
	    			+ " and employeeId in (select employeeId from Resource where releaseDate >(:currentdate) or releaseDate is null)");
	    	query.setParameter("currentdate", new Date());
	    	query.setParameterList("empIds", empIds);
	    	adminList = query.list();
	    	}
    	     else{
    			  if(empIds!= null){
    			  if(!empIds.isEmpty() && !projectIds1.isEmpty()){
    	            query = session.createQuery(
    	                "FROM TimehrsView where employeeId IN (:empIds) and currentProject in(:projectIds1)");
    	            query.setParameterList("empIds", empIds);
    	            query.setParameterList("projectIds1", projectIds1); 
    	            adminList = query.list();
    	            }
    			}
    			}
	    	}else{
	    		if(timeSheetStatus.compareTo('L')==0){
	    			query = session.createQuery("FROM TimehrsView");
	    		}else if(timeSheetStatus.compareTo('P')==0){
	    		 if(empIds!= null && !empIds.isEmpty()){
	    		 query = session.createQuery("FROM TimehrsView where employeeId IN (:empIds)");
	    		 query.setParameterList("empIds", empIds);
	    		 }}
	    		
	    		else{
	              query=null;
	              if(!projectIds1.isEmpty()){
	                query = session.createQuery("FROM TimehrsView where currentProject in(:projectIds1) ");
	                query.setParameterList("projectIds1", projectIds1);
	              }
	    		}
	    		 //query = session.createQuery("FROM TimehrsView o where o.employeeId.employeeId IN (:empIds)");
	    		 //query.setParameterList("empIds", empIds);
	    		if(query!=null)
	            {
	    		adminList1 = query.list();
	    		}
                
	    		if(activeOrAll.equalsIgnoreCase("all")){
                    List<Integer> empIdsList =  resourceDao.findAllEmployeeIds();
                    for(Integer empId:empIdsList){
                      if(adminList1!=null && !adminList1.isEmpty()){   
                      for(TimehrsView view:adminList1){
                            if(view.getEmployeeId().equals(empId)){
                                adminList.add(view);
                            }
                        }
                      }
                    }
                }
	    	}
    		
    		Query query1 = null;
			if(requireCurrentProject) {
				query1 = (Query) session.createQuery("FROM TimehrsEmployeeIdProjectView WHERE allocEndDate IS NULL OR allocEndDate >= CURRENT_DATE");
			} else {
				query1 = (Query) session.createQuery("FROM TimehrsEmployeeIdProjectView");
			}
			projectAllocatedList = (List<TimehrsEmployeeIdProjectView>)query1.list();
			
    	}catch(HibernateException hibernateException){
    		logger.error("HibernateException occured in adminView of TimeHoursDao:-"+hibernateException);
			logger.info("HibernateException occured in adminView of TimeHoursDao:-"+hibernateException);
    		throw hibernateException;
    	}
		List<List> listOfList = new ArrayList<List>();
		listOfList.add(adminList);
		listOfList.add(projectAllocatedList);
		logger.info("-------TimeHoursDaoImpl adminView method end -------- ");
   		return listOfList;
	}

	@SuppressWarnings({ "rawtypes" })
	public List<List> managerView(String resourceIdList) {
		logger.info("-------TimeHoursDaoImpl managerView method  -------- ");
		return this.managerView(resourceIdList, false);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<List> managerView(String resourceIdList, boolean requireCurrentProject) {
		logger.info("-------TimeHoursDaoImpl managerView method start -------- ");
		List<org.yash.rms.domain.TimehrsView> adminList = null;
		List<TimehrsEmployeeIdProjectView> projectAllocatedList = null;
    	if(resourceIdList == null || resourceIdList.isEmpty()) {
        	adminList = new ArrayList();
        	projectAllocatedList = new ArrayList();
    	}else {
    		
    		try{
    		
	    		Session session = (Session) getEntityManager().getDelegate();
	        	Query query = (Query) session.createQuery("FROM TimehrsView WHERE employeeId IN ("+ resourceIdList+")");
	    		adminList = query.list();	
	    		Query query1 = null;
	    		if (requireCurrentProject) {
	    				query1 = (Query) session.createQuery("FROM TimehrsEmployeeIdProjectView WHERE employeeId IN ("+ resourceIdList+")  AND (allocEndDate IS NULL OR allocEndDate >= CURRENT_DATE)");
	    		} else {
	    			query1 = (Query) session.createQuery("select distinct s.projectName FROM TimehrsEmployeeIdProjectView s  WHERE s.employeeId IN ("+ resourceIdList+")");
	    		}
	    		projectAllocatedList =query1.list();
    		}catch(HibernateException hibernateException){
    			logger.error("HibernateException occured in managerView of TimeHoursDao:-"+hibernateException);
				logger.info("HibernateException occured in managerView of TimeHoursDao:-"+hibernateException);
    			hibernateException.printStackTrace();
        		throw hibernateException;
    		}
    	}
			List<List> listOfList = new ArrayList<List>();
			listOfList.add(adminList);
			listOfList.add(projectAllocatedList);
			logger.info("-------TimeHoursDaoImpl managerView method end -------- ");
	   		return listOfList;
       }
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<TimehrsView> managerViewWithPagination(List<Integer> resourceIdList, boolean requireCurrentProject, HttpServletRequest request, SearchCriteriaGeneric searchCriteriaGeneric) {
		logger.info("-------TimeHoursDaoImpl managerView method start -------- ");
		List<org.yash.rms.domain.TimehrsView> adminList =  new ArrayList();
		
    		try{
    			
        		
	    		Session session = (Session) getEntityManager().getDelegate();
	    		Criteria criteria  =	 session.createCriteria(TimehrsView.class);
	    		if(resourceIdList!=null && resourceIdList.size()>0){
	    		criteria.add(Restrictions.in("employeeId", resourceIdList));
	    		criteria=GenericCriteria.createCriteria(searchCriteriaGeneric,criteria);
	     		adminList = criteria.list();	
	     		}
	    	
    		}catch(HibernateException hibernateException){
    			logger.error("HibernateException occured in managerView of TimeHoursDao:-"+hibernateException);
				logger.info("HibernateException occured in managerView of TimeHoursDao:-"+hibernateException);
    			hibernateException.printStackTrace();
        		throw hibernateException;
    		}
    		catch(Exception hibernateException){
    			logger.error("HibernateException occured in managerView of TimeHoursDao:-"+hibernateException);
				logger.info("HibernateException occured in managerView of TimeHoursDao:-"+hibernateException);
    			hibernateException.printStackTrace();
        		
    		}
    	
			logger.info("-------TimeHoursDaoImpl managerView method end -------- ");
	   		return adminList;
       }

	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Long totalCount(List<Integer> empsId, boolean requireCurrentProject, SearchCriteriaGeneric searchCriteriaGeneric) {
		logger.info("-------TimeHoursDaoImpl managerView method start -------- ");
		long totalCount=0;
    	
    		try{
    		
    			if(empsId!=null && empsId.size()>0){
	    		Session session = (Session) getEntityManager().getDelegate();
	    		Criteria criteria  =	 session.createCriteria(TimehrsView.class);
	    		criteria.setProjection(Projections.rowCount());
	    		criteria.add(Restrictions.in("employeeId", empsId));
	        	//Query query = (Query) session.createQuery("FROM TimehrsView WHERE employeeId IN ("+ resourceIdList+")");
	    		searchCriteriaGeneric.setPage(null);
		 		searchCriteriaGeneric.setSize(null);
		 		searchCriteriaGeneric.setISortColumn(null);
		 		searchCriteriaGeneric.setiSortDir(null);
				criteria=GenericCriteria.createCriteria(searchCriteriaGeneric,criteria);
	    		totalCount = (Long) criteria.uniqueResult();}
	    	
    		}catch(HibernateException hibernateException){
    			logger.error("HibernateException occured in managerView of TimeHoursDao:-"+hibernateException);
				logger.info("HibernateException occured in managerView of TimeHoursDao:-"+hibernateException);
    			hibernateException.printStackTrace();
        		throw hibernateException;
    		}
    		catch(Exception Exception){
    			logger.error("HibernateException occured in managerView of TimeHoursDao:-"+Exception);
				logger.info("HibernateException occured in managerView of TimeHoursDao:-"+Exception);
				Exception.printStackTrace();
        
    		}
    	
			
			logger.info("-------TimeHoursDaoImpl managerView method end -------- ");
	   		return totalCount;
       }
	
	
	
	/**
	 * To see performance improvement
	 * @param project
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List managerViewPagination(List<Integer> resourceIdList, int firstResult, int maxResults, ResourceAllocationSearchCriteria resourceAllocationSearchCriteria, boolean search) {
		logger.info("-------TimeHoursDaoImpl managerViewPagination method start -------- ");
		List<org.yash.rms.domain.TimehrsView> adminList = null;
		List<TimehrsEmployeeIdProjectView> projectAllocatedList = null;
    	if(resourceIdList == null || resourceIdList.isEmpty()) {
        	adminList = new ArrayList();
        	projectAllocatedList = new ArrayList();
    	}else {
    		
    		try{
    		
	    		Session session = (Session) getEntityManager().getDelegate();
	    	
	    		Criteria criteria = session.createCriteria(TimehrsView.class);
	    		
	     
	    		
	    		
	    		
	    		//criteria.setFirstResult(firstResult).setMaxResults(maxResults);
	    		 
	    		criteria.add(Restrictions.in("employeeId", resourceIdList));
	    		if (resourceAllocationSearchCriteria.getColandTableNameMap().get(
	    				resourceAllocationSearchCriteria.getRefColName()) == null) {
	    			
	    			// check for current reporting manager
	    	 
	    			if (resourceAllocationSearchCriteria.getOrderStyle().equals("asc")) {
	    				criteria.addOrder(Order.asc(
	    						 resourceAllocationSearchCriteria.getOrederBy()));
	    			} else {
	    				criteria.addOrder(Order.desc(
	    						 resourceAllocationSearchCriteria.getOrederBy()));
	    			}
	    		}
	    		else
	    		{
	    			if (resourceAllocationSearchCriteria.getColandTableNameMap().get(
	        				resourceAllocationSearchCriteria.getRefColName())=="designationName")
	    			{
	    				if (resourceAllocationSearchCriteria.getOrderStyle().equals("asc")) {
	        				criteria.addOrder(Order.asc(
	        						"designation_name"));
	        			} else {
	        				criteria.addOrder(Order.desc(
	        						 "designation_name"));
	        			}
	    			}
	    			else{
	    				if (resourceAllocationSearchCriteria.getOrderStyle().equals("asc")) {
	        				criteria.addOrder(Order.asc(
	        						"grade"));
	        			} else {
	        				criteria.addOrder(Order.desc(
	        						 "grade"));
	        			}
	    				
	    			}
	    		}
	        	//Query query = (Query) session.createQuery("FROM TimehrsView WHERE employeeId IN ("+ resourceIdList+")");
	    		adminList = criteria.list();	
	    		
    		}catch(HibernateException hibernateException){
    			logger.error("HibernateException occured in managerViewPagination of TimeHoursDao:-"+hibernateException);
				logger.info("HibernateException occured in managerViewPagination of TimeHoursDao:-"+hibernateException);
    			hibernateException.printStackTrace();
        		throw hibernateException;
    		}
    	}
			
			logger.info("-------TimeHoursDaoImpl managerViewPagination method end -------- ");
	   		return adminList;
       }
	
	
	
	/**
	 * To see performance improvement
	 * @param project
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Timehrs> findTimeHrsForResources(Integer resourceId){
		logger.info("-------TimeHoursDaoImpl findTimeHrsForResources method start -------- ");
		List<Timehrs> timehrs = null;
		
		try{
			Session session = (Session) getEntityManager().getDelegate();
			timehrs = session.createCriteria(Timehrs.class).add(Restrictions.eq("resourceId.employeeId", resourceId)).list();
		}catch(HibernateException hibernateException){
			logger.error("HibernateException occured in findTimeHrsForResources of TimeHoursDao:-"+hibernateException);
			logger.info("HibernateException occured in findTimeHrsForResources of TimeHoursDao:-"+hibernateException);
    		throw hibernateException;
		}
		logger.info("-------TimeHoursDaoImpl findTimeHrsForResources method end-------- ");
		return timehrs;
	}
	
	
	public UserActivity mapToUserActivity(Timehrs timeHours){
		logger.info("-------TimeHoursDaoImpl mapToUserActivity method start-------- ");
		
    	if(timeHours == null)
    		return null;
    	else {
    		
    		try{
    		
	    		if(timeHours.getWeekEndingDate() != null && timeHours.getResourceAllocId() != null) {
	        		Calendar weekStartDate = Calendar.getInstance();
	        		weekStartDate.setTime(timeHours.getWeekEndingDate());
	        		weekStartDate.add(Calendar.DATE,-6);
	        		List<UserActivity> userList = userActivityDao.findUserActivitysByResourceAllocIdAndWeekStartDateEquals(timeHours.getResourceAllocId(), weekStartDate.getTime());
	        		
	        		if(userList != null && !userList.isEmpty()) {
	        			return userList.get(0);
	        		}    			
	    		}
	    		
	    		UserActivity ua = new UserActivity();
	    		ua.setResourceAllocId(timeHours.getResourceAllocId());
	    		Activity activity = activityDao.findById(1);
	    		ua.setActivityId(activity);
	    		//This flow is for the case when manager make entry for resource in time hrs section    		
	    		ua.setModule("Development");    		
	    		ua.setStatus(new Character(ApproveStatus.SUBMITTED));
	    		ua.setEmployeeId(timeHours.getResourceId());
	    		if(timeHours.getBilledHrs() != null) {
	    			ua.setD2Hours(0.0);
	    			ua.setD3Hours(0.0);
	    			ua.setD4Hours(0.0);
	    			ua.setD5Hours(0.0);
	    			ua.setD6Hours(0.0);
	    		}
	    		if(timeHours.getWeekEndingDate() != null) {
	    			Calendar startDate = Calendar.getInstance();
	    			startDate.setTime(timeHours.getWeekEndingDate());
	    			startDate.add(Calendar.DATE, -6);
	        		ua.setWeekEndDate(timeHours.getWeekEndingDate());    	
	    			ua.setWeekStartDate(startDate.getTime());
	    		 }
	    		logger.info("-------TimeHoursDaoImpl mapToUserActivity method end-------- ");
	    		return ua;
    		}catch(HibernateException hibernateException){
    			logger.error("HibernateException occured in mapToUserActivity of TimeHoursDao:-"+hibernateException);
				logger.info("HibernateException occured in mapToUserActivity of TimeHoursDao:-"+hibernateException);
        		throw hibernateException;
    		}

    		
	}
    	
  }

	@SuppressWarnings({ "unchecked" })
	@Transactional
	public List<TimehrsView>  resourceAllocationPagination(int firstResult, int maxResults, ResourceAllocationSearchCriteria resourceAllocationSearchCriteria, String activeOrAll, boolean search) {
		logger.info("-------TimeHoursDaoImpl resourceAllocationPagination method start -------- ");
		List<org.yash.rms.domain.TimehrsView> adminList = new ArrayList<TimehrsView>();
    	List<TimehrsEmployeeIdProjectView> projectAllocatedList = null;
    	
    	Session session = (Session) getEntityManager().getDelegate();
    	ResourceSearchCriteria resourceSearchCriteria = new ResourceSearchCriteria();
    	resourceSearchCriteria.setRefTableName(resourceAllocationSearchCriteria.getRefTableName());
    	resourceSearchCriteria.setRefColName(resourceAllocationSearchCriteria.getRefColName());
    	resourceSearchCriteria.setSortTableName(resourceAllocationSearchCriteria.getSortTableName());
    	resourceSearchCriteria.setOrderStyle(resourceAllocationSearchCriteria.getOrderStyle());
    	resourceSearchCriteria.setOrederBy(resourceAllocationSearchCriteria.getOrederBy());
    	Criteria criteria = session.createCriteria(TimehrsView.class);
    	Criteria resourceCriteria = session.createCriteria(Resource.class).setProjection(Projections.property("employeeId"));
    	resourceCriteria.setFirstResult(firstResult).setMaxResults(maxResults);
		// criteria.add(Restrictions.ge("releaseDate", new Date()));
    	if(activeOrAll.equalsIgnoreCase("active")){
    	resourceCriteria.add(Restrictions.disjunction()
				.add(Restrictions.ge("releaseDate", new Date()))
				.add(Restrictions.isNull("releaseDate")));
    	}
    	try{    	
	    	//Query query = session.createQuery("FROM TimehrsView");
    		if(resourceAllocationSearchCriteria.getOrederBy() == "totalPlannedHrs" || resourceAllocationSearchCriteria.getOrederBy() == "totalReportedHrs" || resourceAllocationSearchCriteria.getOrederBy() == "totalBilledHrs")
			{criteria.setFirstResult(firstResult).setMaxResults(maxResults);
				if (resourceAllocationSearchCriteria.getOrderStyle().equals("asc")) {
					criteria.addOrder(Order.asc(resourceAllocationSearchCriteria.getOrederBy()));
				} else {
					criteria.addOrder(Order.desc(resourceAllocationSearchCriteria.getOrederBy()));
				}
			}
    	else{
    		if(search){
    		if (resourceAllocationSearchCriteria.getColandTableNameMap().get(resourceAllocationSearchCriteria.getRefColName()) == null) {
    			
    			    			
    			// check for user name
    			if (resourceAllocationSearchCriteria.getRefColName().equals("employeeName")) {
    				String searchInput = resourceAllocationSearchCriteria.getSearchValue();
    				searchInput = searchInput.replace(" " , "%");
    				resourceCriteria.add(ConcatenableIlikeCriterion.ilike(searchInput,
    						MatchMode.ANYWHERE, "firstName", "middleName","lastName"));
    			} else {
    				if (resourceAllocationSearchCriteria.getIntegerValue() != 0) {
    					resourceCriteria.add(Restrictions.like(	resourceAllocationSearchCriteria.getRefColName(),resourceAllocationSearchCriteria.getIntegerValue()));
    				}

    				else {
    					resourceCriteria.add(Restrictions.like(	resourceAllocationSearchCriteria.getRefColName(), "%"+ resourceAllocationSearchCriteria.getSearchValue()+ "%"));
    				}
    			}

    			if (resourceSearchCriteria.getColandTableNameMap().get(resourceSearchCriteria.getRefColName()) == null) {
	    			applySort(resourceSearchCriteria, resourceCriteria);
    		 	}
    		}
    		else {
    			
    			if (resourceAllocationSearchCriteria.getBaseTableCol().equals("currentBuId")) {
    				
    				List<OrgHierarchy> bgBuId = searchByBgBu(resourceAllocationSearchCriteria.getSearchValue());
					/*
					 * criteria.createAlias(resourceSearchCriteria.getBaseTableCol
					 * (), "table");
					 */
					if (!bgBuId.isEmpty()) 
						resourceCriteria.add(Restrictions.in(resourceAllocationSearchCriteria.getBaseTableCol(), bgBuId));
					else
						return adminList;
					
				} else {
					resourceCriteria.createAlias(resourceSearchCriteria.getBaseTableCol(),	"table");
	    			resourceCriteria.add(Restrictions.like("table."
	    						+ resourceSearchCriteria.getRefColName(), "%"
	    						+ resourceAllocationSearchCriteria.getSearchValue() + "%"));
	    		}
    			
    			
    			 

    			/*resourceCriteria.createAlias(resourceSearchCriteria.getBaseTableCol(),	"table");
    			resourceCriteria.add(Restrictions.like("table."
    						+ resourceSearchCriteria.getRefColName(), "%"
    						+ resourceAllocationSearchCriteria.getSearchValue() + "%"));*/
    		 

    			if (resourceSearchCriteria.getRefColName().equals(
    					resourceSearchCriteria.getOrederBy())) {
    				sortCriteria(resourceSearchCriteria, resourceCriteria);
    			} else {
    				applySort(resourceSearchCriteria, resourceCriteria);
    			}

    		}
	
    		}
    		else{
    		
    	    	tableSort(resourceSearchCriteria, resourceCriteria);
    	    	
    		
    		
	    	 
    		}
    		List<Integer> resources = resourceCriteria.list();
        	
    if (resourceAllocationSearchCriteria.getColandTableNameMap().get(
    				resourceAllocationSearchCriteria.getRefColName()) == null) {
    			
    			// check for current reporting manager
          if (resourceAllocationSearchCriteria.getOrederBy().equals("projectName")) {
            if (resourceAllocationSearchCriteria.getOrderStyle().equals("asc")) {
              criteria.addOrder(Order.asc("currentProject"));
            } else {
              criteria.addOrder(Order.desc("currentProject"));
            }
          } else if (resourceAllocationSearchCriteria.getOrderStyle().equals("asc")) {
            criteria.addOrder(Order.asc(resourceAllocationSearchCriteria.getOrederBy()));
          } else {
            criteria.addOrder(Order.desc(resourceAllocationSearchCriteria.getOrederBy()));
          }
        }
    		else
    		{
        		
    		  if (resourceAllocationSearchCriteria.getOrederBy().equals("projectName")) {
                if (resourceAllocationSearchCriteria.getOrderStyle().equals("asc")) {
                    criteria.addOrder(Order.asc("currentProject"));
                } else {
                    criteria.addOrder(Order.desc("currentProject"));
                }
            } 
    		  
    		  
    		}
        
    if(resources != null && resources.size()>0)
	{
		criteria.add(Restrictions.in("employeeId", resources));
    	adminList = (List<org.yash.rms.domain.TimehrsView>)criteria.list();
	
	}
        	
    	}
    		
    }catch(HibernateException hibernateException){
    		logger.error("HibernateException occured in resourceAllocationPagination of TimeHoursDao:-"+hibernateException);
			logger.info("HibernateException occured in resourceAllocationPagination of TimeHoursDao:-"+hibernateException);
    		throw hibernateException;
    	}
		 
		logger.info("-------TimeHoursDaoImpl resourceAllocationPagination method end -------- ");
   		return adminList;
	}
	
	private List<OrgHierarchy> searchByBgBu(String resourceSearchCriteria) {
		// TODO Auto-generated method stub
		Session session = (Session) getEntityManager().getDelegate();
		List<BgBuView> bgBuIdFromView = new ArrayList<BgBuView>();
		List<OrgHierarchy> bgBuIdList = new ArrayList<OrgHierarchy>();

		Criteria criteria = session.createCriteria(BgBuView.class);
		criteria.add(Restrictions.like("name", "%" + resourceSearchCriteria + "%"));
		bgBuIdFromView = criteria.list();

		if (bgBuIdFromView != null) {
			for (BgBuView bgBuView : bgBuIdFromView) {
				OrgHierarchy hierarchy = new OrgHierarchy();
				hierarchy.setId(bgBuView.getId());
				bgBuIdList.add(hierarchy);
			}
		}

		return bgBuIdList;

	}

	private void tableSort(ResourceSearchCriteria resourceallocationSearchCriteria,
			Criteria criteria) {



		// check whether it is reference column or not
		if (resourceallocationSearchCriteria.getColandTableNameMap().get(resourceallocationSearchCriteria.getRefColName()) != null) {
		 
			criteria.createAlias(resourceallocationSearchCriteria.getBaseTableCol(),"table");
		  	sortCriteria(resourceallocationSearchCriteria, criteria);
		} else {
			if (resourceallocationSearchCriteria.getRefColName().equals("employeeName")) {
				if (resourceallocationSearchCriteria.getOrderStyle().equals("asc")) {
					criteria.addOrder(Order.asc("userName"));
				} else {
					criteria.addOrder(Order.desc("userName"));
				}
			} 
			
		}
	}


	private void sortCriteria(ResourceSearchCriteria resourceSearchCriteria, Criteria criteria) {
		if (resourceSearchCriteria.getOrderStyle().equals("asc")) {
			criteria.addOrder(Order.asc("table."
					+ resourceSearchCriteria.getOrederBy()));
		} else {
			criteria.addOrder(Order.desc("table."
					+ resourceSearchCriteria.getOrederBy()));
		}
	}
	
	
	// method called when sorting is required on searched result
		private void applySort(ResourceSearchCriteria resourceAllocationSearchCriteria,
				Criteria criteria) {

			// check whether it is reference column or not
			if (resourceAllocationSearchCriteria.getColandTableNameMap().get(
					resourceAllocationSearchCriteria.getOrederBy()) != null) {
				// check for current reporting manager
			 
					criteria.createAlias(
							resourceAllocationSearchCriteria.getTableColumnForSort(),
							"table1");
					if (resourceAllocationSearchCriteria.getOrderStyle().equals("asc")) {
					criteria.addOrder(Order.asc("table1."
							+ resourceAllocationSearchCriteria.getOrederBy()));
				} else {
					criteria.addOrder(Order.desc("table1."
							+ resourceAllocationSearchCriteria.getOrederBy()));
				}

			} else {
				// check for user name
				if (resourceAllocationSearchCriteria.getOrederBy().equals("employeeName")) {
					if (resourceAllocationSearchCriteria.getOrderStyle().equals("asc")) {
						criteria.addOrder(Order.asc("userName"));
					} else {
						criteria.addOrder(Order.desc("userName"));
					}
				} 
			}
		}

		public long countSearch(ResourceAllocationSearchCriteria resourceAllocationSearchCriteria ,String activeOrAll , List<OrgHierarchy> businessGroup ,List<Integer> projectIdList  ,Integer resourceId , boolean manager , boolean search) {
			// TODO Auto-generated method stub
			List  adminList = new ArrayList ();
	    	List<TimehrsEmployeeIdProjectView> projectAllocatedList = null;
			List<Integer> employeeIds = new ArrayList<Integer>();
	    	Long count = 0l;
	    	Session session = (Session) getEntityManager().getDelegate();
	    	Criteria criteria = session.createCriteria(TimehrsView.class);
	    	Criterion c1 = null;
	    	Criterion c2 = null;
	    	Criterion c3 = null;
	    	Criterion c4 = null;
	    	try{ 
	    	Criteria resourceCriteria = session.createCriteria(Resource.class).setProjection(Projections.property("employeeId"));
	    	
	    	if(businessGroup != null && businessGroup.size()>0)
	    	{
	    		 //c1=(Criterion) resourceCriteria.add(Restrictions.disjunction().add(Restrictions.in("currentBuId", businessGroup)));
	    		 c1=(Criterion) Restrictions.in("currentBuId", businessGroup);
	    		
	    	}
	    	if (projectIdList != null && !projectIdList.isEmpty()) {
				Query query = session.createSQLQuery("Select employee_id from resource_allocation where project_id in (:projectIdList) and (alloc_end_date is null or alloc_end_date>=CURRENT_DATE)");
				query.setParameterList("projectIdList", projectIdList);
				employeeIds = query.list();
			}
	    	
	    	if(employeeIds!=null && !employeeIds.isEmpty())
	    		c4=Restrictions.in("employeeId", employeeIds);
	    	
	    	if(resourceId !=null)
	    	{
	    		 
	    		 c2= (Criterion) Restrictions.disjunction().add(Restrictions
								.eq("currentReportingManagerTwo.employeeId",
										resourceId))
						.add(Restrictions.eq("currentReportingManager.employeeId",
								resourceId));
	    	}
	    
	    	if(activeOrAll.equalsIgnoreCase("active")){
	    		
	    	    		
	    		c3=  (Criterion)Restrictions.disjunction()
	    				.add(Restrictions.ge("releaseDate", new Date()))
	    				.add(Restrictions.isNull("releaseDate"));
	    	}
	    	if(c1!=null && c4!=null&&c3!=null&&c4!=null)
	    		resourceCriteria.add(Restrictions.and(Restrictions.or(c1,c2,c4),c3));
	    	else if(c1==null && c4!=null&&c3!=null)
	    		resourceCriteria.add(Restrictions.and(Restrictions.or(c4,c2),c3));
	    	else if(c1!=null && c4==null&&c3!=null)
	    		resourceCriteria.add(Restrictions.and(Restrictions.or(c1,c2),c3));
	    	else if(c1!=null && c4!=null&&c3==null)
	    		resourceCriteria.add(Restrictions.or(c1,c2, c4));
	    	else if(c1==null && c4==null&&c3!=null)
	    		resourceCriteria.add(Restrictions.and(Restrictions.or(c3)));
	    	
	       	
		    	//Query query = session.createQuery("FROM TimehrsView");
	    		if(search){
	    		if (resourceAllocationSearchCriteria.getColandTableNameMap().get(
	    				resourceAllocationSearchCriteria.getRefColName()) == null) {
	    			// check for user name
	    			if (resourceAllocationSearchCriteria.getRefColName().equals("employeeName")) {
	    				String searchInput = resourceAllocationSearchCriteria.getSearchValue();
	    				searchInput = searchInput.replace(" " , "%");
	    				resourceCriteria.add(ConcatenableIlikeCriterion.ilike(searchInput,
	    						MatchMode.ANYWHERE, "firstName", "middleName","lastName"));
	    			} else {
	    				if (resourceAllocationSearchCriteria.getIntegerValue() != 0) {
	    					resourceCriteria.add(Restrictions.like(
	    							resourceAllocationSearchCriteria.getRefColName(),
	    							resourceAllocationSearchCriteria.getIntegerValue()));
	    				}

	    				else {
	    					resourceCriteria.add(Restrictions.like(
	    							resourceAllocationSearchCriteria.getRefColName(), "%"
	    									+ resourceAllocationSearchCriteria.getSearchValue()
	    									+ "%"));
	    				}
	    			}

	    			//applySort(resourceAllocationSearchCriteria, criteria);
	    		}
	    	
	      		else {
	      			
	      			
	      			if (resourceAllocationSearchCriteria.getBaseTableCol().equals("currentBuId")) {
	    				
	    				List<OrgHierarchy> bgBuId = searchByBgBu(resourceAllocationSearchCriteria.getSearchValue());
						if (!bgBuId.isEmpty()) 
							resourceCriteria.add(Restrictions.in(resourceAllocationSearchCriteria.getBaseTableCol(), bgBuId));
						else
							return count;
					}
	      			else{

	      			resourceCriteria.createAlias(resourceAllocationSearchCriteria.getBaseTableCol(),
	    						"table");
	      			resourceCriteria.add(Restrictions.like("table."
	    						+ resourceAllocationSearchCriteria.getRefColName(), "%"
	    						+ resourceAllocationSearchCriteria.getSearchValue() + "%"));
	    		 
	      			}
	    		

	    		}
	    	}
	    		//criteria.setFirstResult(firstResult).setMaxResults(maxResults);
	    	//	tableSort(resourceAllocationSearchCriteria, criteria);
	    		count =	(Long) resourceCriteria.setProjection(Projections.rowCount()).uniqueResult();
		      // adminList =  resourceCriteria.list();
		     
			 
	    	}catch(HibernateException hibernateException){
				logger.error("HibernateException occured in countSearch of TimeHoursDao:-"+hibernateException);
				logger.info("HibernateException occured in countSearch of TimeHoursDao:-"+hibernateException);
	    		throw hibernateException;
			}
			logger.info("-------TimeHoursDaoImpl countSearch method end-------- ");
		return count;
	    	}

		public Timehrs findByResAllocId(int resourceAllocId, Date weekEndDate) {
			logger.info("-------TimeHoursDaoImpl findByTimehrsId method start -------- ");
			List<Timehrs> timehrsList = null;
			Timehrs timehrs = null;
			try{
				ResourceAllocation allocation = new ResourceAllocation();
				allocation.setId(resourceAllocId);

				Session session = (Session) getEntityManager().getDelegate();
				Criteria criteria = session.createCriteria(Timehrs.class);
				
				criteria.add(Restrictions.eq("weekEndingDate", weekEndDate));
				criteria.add(Restrictions.eq("resourceAllocId", allocation));
				timehrsList = criteria.list();
				
				if (timehrsList != null && timehrsList.size()>0) {
					timehrs= timehrsList.get(0);
				}
			}catch(HibernateException hibernateException){
				logger.error("HibernateException occured in findByTimehrsId of TimeHoursDao:-"+hibernateException);
				logger.info("HibernateException occured in findByTimehrsId of TimeHoursDao:-"+hibernateException);
	    		throw hibernateException;
			}
			logger.info("-------TimeHoursDaoImpl findByTimehrsId method end-------- ");
			return timehrs;
		}	
		
		/*@SuppressWarnings({ "rawtypes", "unchecked" })
		public List<List> managerViewForDelManager(List<Integer>  resourceIdList, boolean requireCurrentProject,List<Project> projectNameList) {
			logger.info("-------TimeHoursDaoImpl managerView method start -------- ");
			List<org.yash.rms.domain.TimehrsView> adminList = null;
			List<TimehrsEmployeeIdProjectView> projectAllocatedList = null;
	    	if(resourceIdList == null || resourceIdList.isEmpty()) {
	        	adminList = new ArrayList();
	        	projectAllocatedList = new ArrayList();
	    	}else {
	    		
	    		try{
	    		
		    		Session session = (Session) getEntityManager().getDelegate();
		    		Criterion criterion= null;
		    		Criteria criteria = session.createCriteria(TimehrsEmployeeIdProjectView.class);
		    		Criteria criteria1 = session.createCriteria(TimehrsView.class);
		        	criteria1.add(Restrictions.in("employeeId", resourceIdList));
		    		adminList = criteria1.list();	
		    		
		    		
		    		if (requireCurrentProject) {
		    			if(projectNameList!=null){
		    				criterion=	Restrictions.or(Restrictions.in("employeeId", resourceIdList),Restrictions.in("projectName",projectNameList));
		    				criteria.add(Restrictions.or(Restrictions.isNull("allocEndDate"), Restrictions.ge( "allocEndDate", new Date())));
		    				criteria.add(criterion);
		    			}
		    			
		    		} else {
		    			criterion=	(Criterion) session.createCriteria(TimehrsView.class).setProjection( Projections.projectionList().add( Projections.distinct(Projections.property("projectName")))).list();
		    			criterion=	Restrictions.or(Restrictions.in("employeeId", resourceIdList),Restrictions.in("projectName",projectNameList));
		    			criteria.add(criterion);
		    		}
		    		projectAllocatedList =criteria.list();
	    		}catch(HibernateException hibernateException){
	    			logger.error("HibernateException occured in managerView of TimeHoursDao:-"+hibernateException);
	    			hibernateException.printStackTrace();
	        		throw hibernateException;
	    		}
	    	}
				List<List> listOfList = new ArrayList<List>();
				listOfList.add(adminList);
				listOfList.add(projectAllocatedList);
				logger.info("-------TimeHoursDaoImpl managerView method end -------- ");
		   		return listOfList;
	       }*/
		
		@SuppressWarnings({ "rawtypes", "unchecked" })
		public List<List> managerViewForDelManager(List<Integer> resourceIdList, boolean requireCurrentProject, List<Project> projectNameList) {
			logger.info("-------TimeHoursDaoImpl managerView method start -------- ");
			List<org.yash.rms.domain.TimehrsView> adminList = new ArrayList<TimehrsView>();;
			List<TimehrsEmployeeIdProjectView> projectAllocatedList = new ArrayList<TimehrsEmployeeIdProjectView>();
	    		
	    		try{
	    		
		    		Session session = (Session) getEntityManager().getDelegate();
		    		Criterion criterion= null;
		    		Criteria criteria = session.createCriteria(TimehrsEmployeeIdProjectView.class);
		    		Criteria criteria1 = session.createCriteria(TimehrsView.class);
		    		
		    		if(resourceIdList!=null && resourceIdList.size()>0){
		    			criteria1.add(Restrictions.in("employeeId", resourceIdList));
			    		adminList = criteria1.list();	
	    			}
		        	
		    		
		    		
		    		if (requireCurrentProject) {
		    			if(projectNameList!=null){
		    				if(resourceIdList!=null && resourceIdList.size()>0){
		    				criterion=	Restrictions.or(Restrictions.in("employeeId", resourceIdList),Restrictions.in("projectName",projectNameList));
		    				criteria.add(Restrictions.or(Restrictions.isNull("allocEndDate"), Restrictions.ge( "allocEndDate", new Date())));
		    				criteria.add(criterion);
		    				}else{
		    					criterion=	Restrictions.in("projectName",projectNameList);
			    				criteria.add(Restrictions.or(Restrictions.isNull("allocEndDate"), Restrictions.ge( "allocEndDate", new Date())));
			    				criteria.add(criterion);
		    				}
		    			}
		    			
		    		} else {
		    			
		    			if((resourceIdList!=null && resourceIdList.size()>0) && (projectNameList!=null && projectNameList.size()>0)){
		    				criterion=	(Criterion) session.createCriteria(TimehrsView.class).setProjection( Projections.projectionList().add( Projections.distinct(Projections.property("projectName")))).list();
			    			criterion=	Restrictions.or(Restrictions.in("employeeId", resourceIdList),Restrictions.in("projectName",projectNameList));
			    			criteria.add(criterion);
		    			}else if(resourceIdList!=null && resourceIdList.size()>0){
		    				criterion=	(Criterion) session.createCriteria(TimehrsView.class).setProjection( Projections.projectionList().add( Projections.distinct(Projections.property("projectName")))).list();
			    			criterion=	Restrictions.in("employeeId", resourceIdList);
			    			criteria.add(criterion);
		    			}else if(projectNameList!=null && projectNameList.size()>0){
		    				criterion=	(Criterion) session.createCriteria(TimehrsView.class).setProjection( Projections.projectionList().add( Projections.distinct(Projections.property("projectName")))).list();
			    			criterion=	Restrictions.in("projectName",projectNameList);
			    			criteria.add(criterion);
		    			}
		    			
		    		}
		    		projectAllocatedList =criteria.list();
	    		}catch(HibernateException hibernateException){
	    			logger.error("HibernateException occured in managerView of TimeHoursDao:-"+hibernateException);
					logger.info("HibernateException occured in managerView of TimeHoursDao:-"+hibernateException);
	    			hibernateException.printStackTrace();
	        		throw hibernateException;
	    		}
//	    	}
				List<List> listOfList = new ArrayList<List>();
				listOfList.add(adminList);
				listOfList.add(projectAllocatedList);
				logger.info("-------TimeHoursDaoImpl managerView method end -------- ");
		   		return listOfList;
	       }
		@Transactional
		public List<TimehrsView> getAllTimehrsViewList(String activeOrAll, Character timeSheetStatus, List<Integer> empIds,List<String> projectName, UserContextDetails userContextDetails , HttpServletRequest request, SearchCriteriaGeneric searchCriteriaGeneric)
		 {
			 logger.info("--------TimeHoursDaoImpl getAllTimehrsViewList method start-------");
				List<TimehrsView> list = null;
				
				Session session = (Session) getEntityManager().getDelegate();
		    	Query query=null;
		    	List<org.yash.rms.domain.TimehrsView> adminList1 = null;
		    	/*List<String> projectNames = null;
		    	if(activeOrAll.equalsIgnoreCase("active")){
		    	    query = session
		    	        .createQuery("Select projectName FROM Project WHERE offshoreDelMgr.employeeId=:resourceId AND (projectEndDate >(:currentDate) or projectEndDate IS NULL)");
		    	    query.setParameter("resourceId", userContextDetails.getEmployeeId());
		    	    query.setParameter("currentDate", new Date());
		    	    }
		    	    else{
		    	      query = session
		    	          .createQuery("Select projectName FROM Project WHERE offshoreDelMgr.employeeId=:resourceId");
		    	      query.setParameter("resourceId", userContextDetails.getEmployeeId());
		    	    }
		    	
		    	projectNames=query.list();*/
				
				
				
				
				
				try {
					//Criteria criteria = HibernateUtil.getSession(sessionFactory)
							//.createCriteria(TimehrsView.class);
				//criteria.add(Restrictions.or(Restrictions.isNull("allocEndDate"), Restrictions.ge( "allocEndDate", new Date()))).addOrder(Order.desc("allocationSeq"));//Order changed as per the Task# 185
				Criteria criteria=null;
				
				if(activeOrAll.equalsIgnoreCase("active"))
				{
					if (timeSheetStatus.compareTo('L')==0)
					{
						criteria=session.createCriteria(TimehrsView.class);
						criteria.add(Restrictions.disjunction().add(Restrictions.isNull("releaseDate")).add(Restrictions.gt("releaseDate", new Date())));
					}
					else if(timeSheetStatus.compareTo('P')==0){
						//criteria.createAlias("employeeId", "empId");
						criteria=session.createCriteria(TimehrsView.class);
						criteria.add(Restrictions.disjunction().add(Restrictions.isNull("releaseDate")).add(Restrictions.gt("releaseDate", new Date())));
						criteria.add(Restrictions.in("employeeId", empIds));
						
					}
					else
					{
						if(projectName!=null && !projectName.isEmpty() &&empIds!=null &&!empIds.isEmpty())
						{
							//criteria.createAlias("employeeId", "empId");
							criteria=session.createCriteria(TimehrsView.class);
							criteria.add(Restrictions.disjunction().add(Restrictions.isNull("releaseDate")).add(Restrictions.gt("releaseDate", new Date())));
							criteria.add(Restrictions.in("employeeId", empIds)).add(Restrictions.in("currentProject", projectName));
							
						}
						
					}
					
				}
				else 
				{
					if (timeSheetStatus.compareTo('L')==0)
					{
						//criteria.createAlias("employeeId", "empId");
						criteria=session.createCriteria(TimehrsView.class);
						System.out.println("I m in Inactive: all");
					}
					else if(timeSheetStatus.compareTo('P')==0){
						//criteria.createAlias("employeeId", "empId");
						criteria=session.createCriteria(TimehrsView.class);
						criteria.add(Restrictions.in("employeeId", empIds));
					}
					else
					{
						if(projectName!=null && ! projectName.isEmpty()){
						//criteria.createAlias("employeeId", "empId");
						criteria=session.createCriteria(TimehrsView.class);
						criteria.add(Restrictions.in("currentProject", projectName));
						
						}
					}
				}
				
				if(criteria!=null){
					 criteria=GenericCriteria.createCriteria(searchCriteriaGeneric,criteria);
					list=criteria.list();
				}
				
				} catch (HibernateException e) {
					logger.error("Exception occured in getAllTimehrsViewList method at DAO layer:-"
							+ e);
					throw e;
				}
				 catch (Exception e) {
						logger.info("Exception occured in getAllTimehrsViewList method at DAO layer:-", e);
					}
				logger.info("--------TimeHoursDaoImpl getAllTimehrsViewList method end-------");
				return list;
			 
		 }
		@Transactional
		public long totalCount(String activeOrAll, Character timeSheetStatus, List<Integer> empIds,List<String> projectName, UserContextDetails userContextDetails, SearchCriteriaGeneric searchCriteriaGeneric){
			
			
			 logger.info("--------TimeHoursDaoImpl totalCount method start-------");
			
				
				
				Session session = (Session) getEntityManager().getDelegate();
		    	Query query=null;
		    	long totalCount=0;
		    /*	List<String> projectNames = null;
		    	if(activeOrAll.equalsIgnoreCase("active")){
		    	    query = session
		    	        .createQuery("Select projectName FROM Project WHERE offshoreDelMgr.employeeId=:resourceId AND (projectEndDate >(:currentDate) or projectEndDate IS NULL)");
		    	    query.setParameter("resourceId", userContextDetails.getEmployeeId());
		    	    query.setParameter("currentDate", new Date());
		    	    }
		    	    else{
		    	      query = session
		    	          .createQuery("Select projectName FROM Project WHERE offshoreDelMgr.employeeId=:resourceId");
		    	      query.setParameter("resourceId", userContextDetails.getEmployeeId());
		    	    }
		    	
		    	projectNames=query.list();*/
				
				
				
				
				
				try {
					/*Criteria criteria = HibernateUtil.getSession(sessionFactory)
							.createCriteria(TimehrsView.class);*/
				//criteria.add(Restrictions.or(Restrictions.isNull("allocEndDate"), Restrictions.ge( "allocEndDate", new Date()))).addOrder(Order.desc("allocationSeq"));//Order changed as per the Task# 185
					Criteria criteria=null;
				
				if(activeOrAll.equalsIgnoreCase("active"))
				{
					if (timeSheetStatus.compareTo('L')==0)
					{
						criteria=session.createCriteria(TimehrsView.class);
						criteria.add(Restrictions.disjunction().add(Restrictions.isNull("releaseDate")).add(Restrictions.gt("releaseDate", new Date())));
					}
					else if(timeSheetStatus.compareTo('P')==0){
						//criteria.createAlias("employeeId", "empId");
						criteria=session.createCriteria(TimehrsView.class);
						criteria.add(Restrictions.disjunction().add(Restrictions.isNull("releaseDate")).add(Restrictions.gt("releaseDate", new Date())));
						criteria.add(Restrictions.in("employeeId", empIds));
						
					}
					else
					{
						if(projectName!=null && !projectName.isEmpty() &&empIds!=null &&!empIds.isEmpty())
						{
							//criteria.createAlias("employeeId", "empId");
							criteria=session.createCriteria(TimehrsView.class);
							criteria.add(Restrictions.disjunction().add(Restrictions.isNull("releaseDate")).add(Restrictions.gt("releaseDate", new Date())));
							criteria.add(Restrictions.in("employeeId", empIds)).add(Restrictions.in("currentProject", projectName));
							
						}
						
					}
					
				}
				else 
				{
					if (timeSheetStatus.compareTo('L')==0)
					{
						//criteria.createAlias("employeeId", "empId");
						criteria=session.createCriteria(TimehrsView.class);
						System.out.println("I m in Inactive: all");
					}
					else if(timeSheetStatus.compareTo('P')==0){
						//criteria.createAlias("employeeId", "empId");
						criteria=session.createCriteria(TimehrsView.class);
						criteria.add(Restrictions.in("employeeId", empIds));
					}
					else
					{
						if(projectName!=null && ! projectName.isEmpty()){
					//	criteria.createAlias("employeeId", "empId");
							criteria=session.createCriteria(TimehrsView.class);
							//criteria.add(Restrictions.in("currentProject", projectNames));
							criteria.add(Restrictions.in("employeeId", empIds));
						}
					}
				}
				
				if(criteria!=null){
					searchCriteriaGeneric.setPage(null);
			 		searchCriteriaGeneric.setSize(null);
			 		searchCriteriaGeneric.setISortColumn(null);
			 		searchCriteriaGeneric.setiSortDir(null);
					criteria=GenericCriteria.createCriteria(searchCriteriaGeneric,criteria);
				criteria.setProjection(Projections.rowCount());
			
				totalCount=(Long) criteria.uniqueResult();
				}
		
				} catch (HibernateException e) {
					logger.error("Exception occured in totalCount method at DAO layer:-"
							+ e);
					throw e;
				}catch (Exception e) {
					logger.error("Exception occured in totalCount method at DAO layer:-"
							+ e);
				
				}
				
				logger.info("--------TimeHoursDaoImpl totalCount method end-------");
				return totalCount;
			
		
		}
		

}
	


