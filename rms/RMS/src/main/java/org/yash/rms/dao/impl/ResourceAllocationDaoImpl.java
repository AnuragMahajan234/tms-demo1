/**
 * 
 */
package org.yash.rms.dao.impl;

import java.util.ArrayList;
import java.util.Calendar;
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
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.DateType;
import org.hibernate.type.IntegerType;
import org.hibernate.type.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.yash.rms.dao.ResourceAllocationDao;
import org.yash.rms.domain.AllocationType;
import org.yash.rms.domain.BgBuView;
import org.yash.rms.domain.OrgHierarchy;
import org.yash.rms.domain.Ownership;
import org.yash.rms.domain.Project;
import org.yash.rms.domain.Resource;
import org.yash.rms.domain.ResourceAllocation;
import org.yash.rms.domain.TimehrsView;
import org.yash.rms.domain.UserActivity;
import org.yash.rms.dto.UserContextDetails;
import org.yash.rms.util.AppContext;
import org.yash.rms.util.ConcatenableIlikeCriterion;
import org.yash.rms.util.Constants;
import org.yash.rms.util.HibernateUtil;
import org.yash.rms.util.ResourceAllocationSearchCriteria;
import org.yash.rms.util.ResourceSearchCriteria;
import org.yash.rms.util.UserUtil;

/**
 * @author arpan.badjatiya
 * 
 */

@SuppressWarnings("rawtypes")
@Repository("ResourceAllocationDao")
@Transactional
public class ResourceAllocationDaoImpl implements ResourceAllocationDao {

	@PersistenceContext(name = "mysql", type = PersistenceContextType.TRANSACTION)
	private EntityManager entityManager;
	
	
	public EntityManager getEntityManager() {
		return entityManager;
	}
	
	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	ResourceDaoImpl resourceDao = new ResourceDaoImpl();
	private static final Logger logger = LoggerFactory
			.getLogger(ResourceAllocationDaoImpl.class);

	public boolean delete(int id) {
		logger.info("--------ResourceAllocationDaoImpl delete method start-------");

		boolean isSuccess = true;
		try {
			Session session = (Session) getEntityManager().getDelegate();
			ResourceAllocation resourceAllocation = (ResourceAllocation) session
					.get(ResourceAllocation.class, id);
			session.delete(resourceAllocation);
			session.flush();
		} catch (HibernateException e) {
			isSuccess = false;
			logger.error("Exception occured in delete method at DAO layer:-"
					+ e);
			throw e;
		} catch (Exception ex) {
			isSuccess = false;
		}
		logger.info("--------ResourceAllocationDaoImpl delete method End-------");
		return isSuccess;

	}

	public boolean saveOrupdate(ResourceAllocation resourceAllocation) {

		logger.info("--------ResourceAllocationDaoImpl saveOrUpdate method start-------");
		if (null == resourceAllocation)
			return false;

		Session currentSession = (Session) getEntityManager().getDelegate();

		UserContextDetails contextDetails = UserUtil.getUserContextDetails();
		String userName = Constants.SYSTEM_UPDATE;
		if(contextDetails != null){
			userName  = contextDetails.getUserName();
		}
		if (resourceAllocation.getId() == null) {
			resourceAllocation.setCreatedId(userName);
			resourceAllocation.setCreationTimestamp(new Date());
		}

		resourceAllocation.setLastUpdatedId(userName);
		//removed Allocation Hours so setting Allocation Hours default to 0
		resourceAllocation.setAllocHrs(0);

		boolean isSuccess = true;
		try {
			currentSession.merge(resourceAllocation);
		} catch (HibernateException e) {
			isSuccess = false;
			logger.error("Exception occured in saveOrUpdate method at DAO layer:-"
					+ e);
			throw e;
		} catch (Exception ex) {
			isSuccess = false;
			logger.error("Exception occured in saveOrUpdate method at DAO layer:-"+ ex);
		}
		logger.info("--------ResourceAllocationDaoImpl saveOrUpdate method End-------");
		return isSuccess;
	}

	@SuppressWarnings("unchecked")
	public boolean isResourceBehalfManager(int employeeId) {
		
		logger.info("--------ResourceAllocationDaoImpl isResourceBehalfManager method start-------");
		
		Criteria criteria = ((Session) getEntityManager().getDelegate()).createCriteria(ResourceAllocation.class);
			criteria.add(Restrictions.and(Restrictions.eq("employeeId.employeeId", employeeId), Restrictions.eq("behalfManager", true)));
		
		boolean isResourceBehalfManager = false;

		try {
			List<Resource> resource = criteria.list();
			if (null != resource && resource.size() > 0) {
				isResourceBehalfManager = true;
			}
		} catch (HibernateException exception) {
			logger.error("Exception occured in isResourceBehalfManager method at DAO layer:-" + exception);
			throw exception;
		}

		return isResourceBehalfManager;
	}

	public List<ResourceAllocation> findAll() {
		logger.info("--------ResourceAllocationDaoImpl findAll method start-------");
		try {
			Session session = (Session) getEntityManager().getDelegate();
			List<ResourceAllocation> resourceAllocations = session
					.createCriteria(ResourceAllocation.class).list();
			return resourceAllocations;
		} catch (HibernateException e) {
			logger.error("Exception occured in saveOrUpdate method at DAO layer:-"
					+ e);
			throw e;
		} finally {
			logger.info("--------ResourceAllocationDaoImpl findAll method end-------");
		}
	}

	public List findByEntries(int firstResult, int sizeNo) {
		return null;
	}

	public long countTotal() {
		return 0;
	}

	@SuppressWarnings("unchecked")
	public List<ResourceAllocation> findResourceAllocationsByEmployeeId(
			Resource resource) {

		logger.info("--------ResourceAllocationDaoImpl findResourceAllocationsByEmployeeId method start-------");
		List<ResourceAllocation> list = null;
		try {
			Criteria criteria = ((Session) getEntityManager().getDelegate()).createCriteria(ResourceAllocation.class);
			list = criteria.add(Restrictions.eq("employeeId", resource)).addOrder(Order.desc("allocationSeq")).list();  //Order changed as per the Task# 185
		} catch (HibernateException e) {
			logger.error("Exception occured in findResourceAllocationsByEmployeeId method at DAO layer:-"
					+ e);
			throw e;
		}
		logger.info("--------ResourceAllocationDaoImpl findResourceAllocationsByEmployeeId method end-------");
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public List<ResourceAllocation> findResourceAllocations(
			Resource resource) {

		logger.info("--------ResourceAllocationDaoImpl findResourceAllocations method start-------");
		List<ResourceAllocation> list = null;
		try {
			Criteria criteria = ((Session) getEntityManager().getDelegate())
					.createCriteria(ResourceAllocation.class);
			//Commented by Neha
			//list = criteria.add(Restrictions.eq("employeeId", resource)).add(Restrictions.or(Restrictions.isNull("allocEndDate"), Restrictions.ge( "allocEndDate", new Date()))).addOrder(Order.desc("allocationSeq")).list();//Order changed as per the Task# 185
			
			//Added by Neha
			list = criteria.add(Restrictions.eq("employeeId", resource)).addOrder(Order.desc("allocationSeq")).list();//added by neha
			//list = criteria.add(Restrictions.eq("employeeId", resource)).list(); 
		} catch (HibernateException e) {
			logger.error("Exception occured in findResourceAllocationsByEmployeeId method at DAO layer:-"
					+ e);
			throw e;
		}
		logger.info("--------ResourceAllocationDaoImpl findResourceAllocationsByEmployeeId method end-------");
		return list;
	}
	// Method added to retrieve active allocations of a resource
	 public List<ResourceAllocation> findResourceAllocationByEmployeeId(Integer employeeId){
		 logger.info("--------ResourceAllocationDaoImpl findResourceAllocationByEmployeeId method start-------");
			List<ResourceAllocation> list = null;
			try {
				Criteria criteria = ((Session) getEntityManager().getDelegate())
						.createCriteria(ResourceAllocation.class);
				list = criteria.add(Restrictions.eq("employeeId.employeeId", employeeId)).add(Restrictions.or(Restrictions.isNull("allocEndDate"), Restrictions.ge( "allocEndDate", new Date()))).addOrder(Order.desc("allocationSeq")).list();//Order changed as per the Task# 185
				//list = criteria.add(Restrictions.eq("employeeId", resource)).list(); 
			} catch (HibernateException e) {
				logger.error("Exception occured in findResourceAllocationsByEmployeeId method at DAO layer:-"
						+ e);
				throw e;
			}
			logger.info("--------ResourceAllocationDaoImpl findResourceAllocationsByEmployeeId method end-------");
			return list;
	 }
	 
	 public List<String> findProjectNamesByEmployeeId(Integer employeeId){
		 logger.info("--------ResourceAllocationDaoImpl findResourceAllocationByEmployeeId method start-------");
			List<String> list = null;
			try {
				Criteria criteria = ((Session) getEntityManager().getDelegate())
						.createCriteria(ResourceAllocation.class);
				criteria.add(Restrictions.eq("employeeId.employeeId", employeeId)).add(Restrictions.or(Restrictions.isNull("allocEndDate"), Restrictions.ge( "allocEndDate", new Date()))).addOrder(Order.desc("allocationSeq"))//Order changed as per the Task# 185
				.createAlias("projectId", "prjId");
				list=criteria.setProjection(Projections.property("prjId.projectName")).list();
				
				//list = criteria.add(Restrictions.eq("employeeId", resource)).list(); 
			} catch (HibernateException e) {
				logger.error("Exception occured in findResourceAllocationsByEmployeeId method at DAO layer:-"
						+ e);
				throw e;
			}
			logger.info("--------ResourceAllocationDaoImpl findResourceAllocationsByEmployeeId method end-------");
			return list;
	 }
	 
	

	@SuppressWarnings("unchecked")
	public List<ResourceAllocation> findResourceAllocationsByEmployeeIdforTimeHours(
			Resource resource,Date weekEndDate) {

		logger.info("--------ResourceAllocationDaoImpl findResourceAllocationsByEmployeeId method start-------");
		List<ResourceAllocation> list = null;
		Calendar c =Calendar.getInstance();
		c.setTime(weekEndDate);
		c.add( Calendar.DAY_OF_WEEK, -(c.get(Calendar.DAY_OF_WEEK)-1)); 
		Date weekStart = c.getTime();
		Resource loggedinUser=null;
		try {
			Criteria criteria = ((Session) getEntityManager().getDelegate())
					.createCriteria(ResourceAllocation.class);
			if(UserUtil.isCurrentUserIsDeliveryManager()){
				loggedinUser=UserUtil.userContextDetailsToResource(UserUtil.getUserContextDetails());
				criteria.add(Restrictions.and(
						Restrictions.eq("employeeId",loggedinUser),
						Restrictions.eq("behalfManager", true)));
				List<Project> allocationsMgr = criteria.setProjection(Projections.property("projectId")).list();
				criteria = ((Session) getEntityManager().getDelegate())
						.createCriteria(ResourceAllocation.class);
				criteria.createCriteria("projectId", "projectId");
				
				if(allocationsMgr!=null&&allocationsMgr.size()>0){
					criteria.add(Restrictions.disjunction().add(Restrictions.in("projectId", allocationsMgr)).add(Restrictions.eq("projectId.offshoreDelMgr", loggedinUser))
							.add(Restrictions.eq("projectId.deliveryMgr",loggedinUser)));
				}else{
					criteria.add(Restrictions.disjunction().add(Restrictions.eq("projectId.offshoreDelMgr", loggedinUser))
							.add(Restrictions.eq("projectId.deliveryMgr",loggedinUser)));
				}
				list = criteria.add(Restrictions.eq("employeeId", resource)).add(Restrictions.disjunction().add(Restrictions.ge("allocEndDate", weekStart)).add(Restrictions.isNull("allocEndDate"))).add(Restrictions.le("allocStartDate", weekStart)).list();
			}else{
				list = criteria.add(Restrictions.eq("employeeId", resource)).add(Restrictions.disjunction().add(Restrictions.ge("allocEndDate",weekStart)).add(Restrictions.isNull("allocEndDate"))).add(Restrictions.le("allocStartDate", weekStart)).list();
			}
		} catch (HibernateException e) {
			logger.error("Exception occured in findResourceAllocationsByEmployeeId method at DAO layer:-"
					+ e);
			throw e;
		}
		logger.info("--------ResourceAllocationDaoImpl findResourceAllocationsByEmployeeId method end-------");
		return list;
	}

	
	public List<ResourceAllocation> findResourceAllocationsforManager(Resource resource,Resource manager,Date weekEndDate){
		

		logger.info("--------ResourceAllocationDaoImpl findResourceAllocationsforManager method start-------");
		List<ResourceAllocation> list = null;
		Calendar c =Calendar.getInstance();
		c.setTime(weekEndDate);
		c.add( Calendar.DAY_OF_WEEK, -(c.get(Calendar.DAY_OF_WEEK)-1)); 
		Date weekStart = c.getTime();
		try {
			Criteria criteria = ((Session) getEntityManager().getDelegate())
					.createCriteria(ResourceAllocation.class);
			criteria.add(Restrictions.and(
					Restrictions.eq("employeeId", manager),
					Restrictions.eq("behalfManager", true)));
			List<Project> allocationsMgr = criteria.setProjection(Projections.property("projectId")).list();
			criteria = ((Session) getEntityManager().getDelegate())
					.createCriteria(ResourceAllocation.class);
			criteria.createCriteria("projectId", "projectId");
			
			if(allocationsMgr!=null&&allocationsMgr.size()>0){
				criteria.add(Restrictions.disjunction().add(Restrictions.in("projectId", allocationsMgr)).add(Restrictions.eq("projectId.offshoreDelMgr", manager))
						.add(Restrictions.eq("projectId.deliveryMgr", manager)));
			}else{
				criteria.add(Restrictions.disjunction().add(Restrictions.eq("projectId.offshoreDelMgr", manager)).add(Restrictions.eq("projectId.deliveryMgr", manager)));
			}//criteria.add(Restrictions.disjunction().add(Restrictions.eq("currentReportingManager", delMgr)).add(Restrictions.eq("currentReportingManagerTwo", delMgr)));
			criteria.add(Restrictions.eq("employeeId", resource)).add(Restrictions.disjunction().add(Restrictions.ge("allocEndDate",weekStart)).add(Restrictions.isNull("allocEndDate")));
				list =criteria.add(Restrictions.le("allocStartDate", weekStart)).list();
		
		} catch (HibernateException e) {
			logger.error("Exception occured in findResourceAllocationsforManager method at DAO layer:-"
					+ e);
			throw e;
		}
		logger.info("--------ResourceAllocationDaoImpl findResourceAllocationsforManager method end-------");
		return list;	} 
	
	@SuppressWarnings("unchecked")
	public List findAllocatedResourceEmployeeIdByProjectIds(List projectIds,String active) {
		logger.info("--------ResourceAllocationDaoImpl findAllocatedResourceEmployeeIdByProjectIds method start-------");
		if (projectIds == null || projectIds.isEmpty()) {
			return new ArrayList<Integer>();
		}
		List list = new ArrayList();
		try {
			org.hibernate.Query query = null;
			if(active.equalsIgnoreCase("active")){
				query = ((Session) getEntityManager().getDelegate())
					.getNamedQuery(
							"ResourceAllocation.FIND_ALLOCATED_RESOURCE_EMPLOYEEID_BY_PROJRCT_IDS_ACTIVE");
					}else {
						query = ((Session) getEntityManager().getDelegate())
								.getNamedQuery(
										"ResourceAllocation.FIND_ALLOCATED_RESOURCE_EMPLOYEEID_BY_PROJRCT_IDS");
					}
			query.setParameterList("projectId", projectIds);
			query.setParameter("currentDate", new Date());
			
			list = query.list();
		} catch (HibernateException e) {
			logger.error("Exception occured in findAllocatedResourceEmployeeIdByProjectIds method at DAO layer:-"
					+ e);
			throw e;
		}

		logger.info("--------ResourceAllocationDaoImpl findAllocatedResourceEmployeeIdByProjectIds method end-------");
		return list;
	}
	
	
	public List findAllocatedActiveProjectByEmployeeId(List employeeId,Boolean active) {
		logger.info("--------ResourceAllocationDaoImpl findAllocatedResourceEmployeeIdByProjectIds method start-------");
		if (employeeId == null || employeeId.isEmpty()) {
			return new ArrayList<Integer>();
		}
		List list = new ArrayList();
		try {
			org.hibernate.Query query = null;
			if(active){
				query = ((Session) getEntityManager().getDelegate())
					.getNamedQuery(
							"ResourceAllocation.FIND_ALLOCATED_ACTIVE_PROJECT_BY_RESOURCEIDS");
				query.setParameterList("employeeId", employeeId);
				query.setParameter("currentDate", new Date());
					}
			else
			{
				query =((Session) getEntityManager().getDelegate())
						.getNamedQuery(
								"ResourceAllocation.FIND_ALLOCATED_PROJECT_BY_RESOURCEIDS");
						
				query.setParameterList("employeeId", employeeId);
			
				}
	
			
			list = query.list();
		} catch (HibernateException e) {
			logger.error("Exception occured in findAllocatedResourceEmployeeIdByProjectIds method at DAO layer:-"
					+ e);
			throw e;
		}

		logger.info("--------ResourceAllocationDaoImpl findAllocatedResourceEmployeeIdByProjectIds method end-------");
		return list;
	}
	
	
	public List findAllocatedEmployeeInProjectByCurrentUser(Integer employeeId,Boolean active) {
		logger.info("--------ResourceAllocationDaoImpl findAllocatedResourceEmployeeIdByProjectIds method start-------");
		if (employeeId == null) {
			return new ArrayList<Integer>();
		}
		List list = new ArrayList();
		try {
			org.hibernate.Query query = null;
			if(active){
				query =((Session) getEntityManager().getDelegate())
					.getNamedQuery(
							"ResourceAllocation.FIND_ALLOCATED_ACTIVE_PROJECT_BY_CURRENTLOGGEDIN");
				query.setParameter("employeeId", employeeId);
				query.setParameter("currentDate", new Date());
					}
			else
			{
				query =((Session) getEntityManager().getDelegate())
						.getNamedQuery(
								"ResourceAllocation.FIND_ALLOCATED_PROJECT_BY_CURRENTLOGGEDIN");
						
				query.setParameter("employeeId", employeeId);
			
				}
	
			
			list = query.list();
		} catch (HibernateException e) {
			logger.error("Exception occured in findAllocatedResourceEmployeeIdByProjectIds method at DAO layer:-"
					+ e);
			throw e;
		}

		logger.info("--------ResourceAllocationDaoImpl findAllocatedResourceEmployeeIdByProjectIds method end-------");
		return list;
	}
	
	
	
	
	

	
	// Added for performance improvement
	
	@SuppressWarnings("unchecked")
	public List<Integer> findAllocatedResourceEmployeeIdByProjectIdsPagination(int firstResult, int maxResults,List projectIds,String active,ResourceAllocationSearchCriteria resourceAllocationSearchCriteria,boolean search,Integer employeeId) {
		logger.info("--------ResourceAllocationDaoImpl findAllocatedResourceEmployeeIdByProjectIdsPagination method start-------");
		Session session = (Session) getEntityManager().getDelegate();
		ResourceSearchCriteria resourceSearchCriteria = new ResourceSearchCriteria();
    	resourceSearchCriteria.setRefTableName(resourceAllocationSearchCriteria.getRefTableName());
    	resourceSearchCriteria.setRefColName(resourceAllocationSearchCriteria.getRefColName());
    	resourceSearchCriteria.setSortTableName(resourceAllocationSearchCriteria.getSortTableName());
    	resourceSearchCriteria.setOrderStyle(resourceAllocationSearchCriteria.getOrderStyle());
    	resourceSearchCriteria.setOrederBy(resourceAllocationSearchCriteria.getOrederBy());
		Criteria criteria = session.createCriteria(ResourceAllocation.class).setProjection(Projections.property("employeeId.employeeId")).setProjection(Projections.distinct(Projections.property("employeeId.employeeId")));
		Criteria criteriaResource = session.createCriteria(Resource.class).setProjection(Projections.property("employeeId"));
		Criterion c1 = null;
		
		List<Integer> employeeIdList=new ArrayList<Integer>();
		
		ResourceAllocationDao resourceAllocationDao=AppContext.getApplicationContext().getBean(ResourceAllocationDao.class);
		employeeIdList=resourceAllocationDao.findAllocatedEmployeeInProjectByCurrentUser(employeeId,true);
	if(employeeIdList.isEmpty())
	{
		employeeIdList.add(0);
	}
		Criterion c2 =Restrictions.or(Restrictions.disjunction()
								.add(Restrictions.eq("currentReportingManagerTwo.employeeId",
										employeeId))
								.add(Restrictions.eq("currentReportingManager.employeeId",
										employeeId)).add(Restrictions.in("employeeId", employeeIdList)));
		if(active.equals("active")){
			  c1 =(Restrictions.disjunction()
							.add(Restrictions.ge("releaseDate", new Date()))
							.add(Restrictions.isNull("releaseDate")));
			  
			  criteriaResource.add(c1).add(c2);
				 }
		
		else
			 criteriaResource.add(c2);
	
	
		 
		/*if (projectIds == null || projectIds.isEmpty()) {
			return new ArrayList<Integer>();
		}*/
		List<Integer> list = new ArrayList<Integer>();
		try {
			
			if(resourceAllocationSearchCriteria.getOrederBy() == "totalPlannedHrs" || resourceAllocationSearchCriteria.getOrederBy() == "totalReportedHrs" || resourceAllocationSearchCriteria.getOrederBy() == "totalBilledHrs")
			{Criteria timeHrscriteria = session.createCriteria(TimehrsView.class)
			.setProjection(Projections.property("employeeId"));
				timeHrscriteria.setFirstResult(firstResult).setMaxResults(maxResults);
				
				
				if (resourceAllocationSearchCriteria.getOrderStyle().equals("asc")) {
					timeHrscriteria.addOrder(Order.asc(resourceAllocationSearchCriteria.getOrederBy()));
				} else {
					timeHrscriteria.addOrder(Order.desc(resourceAllocationSearchCriteria.getOrederBy()));
				}
				list = timeHrscriteria.list();
			}
			else{
			if(search){
				Criteria resourceCriteria = session.createCriteria(Resource.class).setProjection(Projections.property("employeeId"));
				
				if(active.equals("active")){
				 resourceCriteria.add(Restrictions.disjunction()
						.add(Restrictions.ge("releaseDate", new Date()))
						.add(Restrictions.isNull("releaseDate")));
				}
				
				
		    	//resourceCriteria.setFirstResult(firstResult).setMaxResults(maxResults);
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

	    			resourceDao.applySort(resourceSearchCriteria, resourceCriteria);
	    		}
	    		else {
	    			
	    			if (resourceAllocationSearchCriteria.getBaseTableCol().equals("currentBuId")) {
	    				
	    				List<OrgHierarchy> bgBuId = searchByBgBu(resourceAllocationSearchCriteria.getSearchValue());
						
						if (!bgBuId.isEmpty()) 
							resourceCriteria.add(Restrictions.in(resourceAllocationSearchCriteria.getBaseTableCol(), bgBuId));
						else
							return list;
					}
	    			else{

	    			resourceCriteria.createAlias(resourceSearchCriteria.getBaseTableCol(),
	    						"table");
	    			resourceCriteria.add(Restrictions.like("table."
	    						+ resourceSearchCriteria.getRefColName(), "%"
	    						+ resourceAllocationSearchCriteria.getSearchValue() + "%"));
	    			}

	    			if (resourceSearchCriteria.getRefColName().equals(
	    					resourceSearchCriteria.getOrederBy())) {
	    				resourceDao.sortCriteria(resourceSearchCriteria, resourceCriteria);
	    			} else {
	    				resourceDao.applySort(resourceSearchCriteria, resourceCriteria);
	    			}

	    		}
	    		//System.out.println("resourceCriteria.list().size()" + resourceCriteria.list().size());
	    		if(resourceCriteria.list() !=null && resourceCriteria.list().size()>0 )
	    		{	criteriaResource.add(Restrictions.in("employeeId",resourceCriteria.list()));
	    			criteria.add(Restrictions.in("employeeId.employeeId",resourceCriteria.list()));
	    			criteria.setFirstResult(firstResult).setMaxResults(maxResults);		
	    			List<Integer> list1=	criteriaResource.list();
	    			Criterion criterion2 =null;
	    				//Criterion criterion1 = (Criterion) criteria.add(Restrictions.le("allocStartDate", new Date()));
	    				//Criterion criterion2 = (Criterion) criteria.add(Restrictions.ge("allocEndDate", new Date()));
	    			if(list1 != null && list1.size()>0)
	    		  criterion2  = Restrictions.in("employeeId.employeeId",list1);
	    			Criterion criterion1= null;
	    			if (projectIds != null && !projectIds.isEmpty() ) {
	    			  criterion1 = Restrictions.and(Restrictions.in("projectId.id", projectIds),Restrictions.and(Restrictions.le("allocStartDate", new Date()),Restrictions.or(Restrictions.ge("allocEndDate", new Date()), Restrictions.isNull("allocEndDate"))));
	    			  if(criterion2 !=null)
	    			criteria.add(Restrictions.or(criterion1,criterion2));
	    			  else
	    				  criteria.add(criterion1);
	    			}
	    			else
	    				if(criterion2 !=null)
	    				criteria.add(criterion2);	    			 
	    			
	    			list = criteria.list();
	    		}
		
	    		}
			else{
			List<Integer> list1=	criteriaResource.list();
				criteria.setFirstResult(firstResult).setMaxResults(maxResults);
				
		
				//Criterion criterion1 = (Criterion) criteria.add(Restrictions.le("allocStartDate", new Date()));
				//Criterion criterion2 = (Criterion) criteria.add(Restrictions.ge("allocEndDate", new Date()));

				//Restrictions.and(Restrictions.le("allocStartDate", new Date()),Restrictions.or(Restrictions.ge("allocEndDate", new Date()), Restrictions.isNull("allocEndDate")));
				Criterion criterion2  = null;
				if(list1 != null && list1.size()>0)
					  criterion2  = Restrictions.in("employeeId.employeeId",list1);
				Criterion criterion1= null;
				if (projectIds != null && !projectIds.isEmpty()) {
				  criterion1 = Restrictions.and(Restrictions.in("projectId.id", projectIds),Restrictions.and(Restrictions.le("allocStartDate", new Date()),Restrictions.or(Restrictions.ge("allocEndDate", new Date()), Restrictions.isNull("allocEndDate"))));
				
			 
				 if(criterion2 !=null)
						criteria.add(Restrictions.or(criterion1,criterion2));
						  else
							  criteria.add(criterion1);
				}
				else
					if(criterion2 !=null)
					criteria.add(criterion2);
						
				
			/*	if(active.equalsIgnoreCase("active")){
					
					criteria.createCriteria("employeeId").add(Restrictions.disjunction()
			                .add(Restrictions.ge("releaseDate",  new Date())).add(Restrictions.isNull("releaseDate")));
					 
					 
					 criteria.add(Restrictions.disjunction()
		    				.add(Restrictions.ge("employeeId.releaseDate", new Date()))
		    				.add(Restrictions.isNull("employeeId.releaseDate"))); 
						
						
						
						
										
					}*/
				 if(criterion1!=null || criterion2!=null)
				list = criteria.list();
			}
		}
			
			
		} catch (HibernateException e) {
			logger.error("Exception occured in findAllocatedResourceEmployeeIdByProjectIdsPagination method at DAO layer:-"
					+ e);
			throw e;
		}

		logger.info("--------ResourceAllocationDaoImpl findAllocatedResourceEmployeeIdByProjectIdsPagination method end-------");
		return list;
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
	
	@SuppressWarnings("unchecked")
	public List<Integer> findAllocatedResourceEmployeeIdByProjectIdsDashboard(List projectIds,Integer employeeId) {
		logger.info("--------ResourceAllocationDaoImpl findAllocatedResourceEmployeeIdByProjectIdsPagination method start-------");
		Session session = (Session) getEntityManager().getDelegate();
		Criteria criteria = session.createCriteria(ResourceAllocation.class).setProjection(Projections.property("employeeId.employeeId")).setProjection(Projections.distinct(Projections.property("employeeId.employeeId")));
		Criteria criteriaResource = session.createCriteria(Resource.class).setProjection(Projections.property("employeeId"));
		Criterion c1 = null;
	
		Criterion c2 =Restrictions.or(Restrictions.disjunction()
								.add(Restrictions.eq("currentReportingManagerTwo.employeeId",
										employeeId))
								.add(Restrictions.eq("currentReportingManager.employeeId",
										employeeId)));
			  c1 =(Restrictions.disjunction()
							.add(Restrictions.ge("releaseDate", new Date()))
							.add(Restrictions.isNull("releaseDate")));
			  
			  criteriaResource.add(c1).add(c2);
		
		/*if (projectIds == null || projectIds.isEmpty()) {
			return new ArrayList<Integer>();
		}*/
		List<Integer> list = new ArrayList<Integer>();
		try {
			
			List<Integer> list1=	criteriaResource.list();
				Criterion criterion2  = null;
				if(list1 != null && list1.size()>0)
					  criterion2  = Restrictions.in("employeeId.employeeId",list1);
				Criterion criterion1= null;
				if (projectIds != null && !projectIds.isEmpty()) {
				//  criterion1 = Restrictions.and(Restrictions.in("projectId.id", projectIds),Restrictions.and(Restrictions.le("allocStartDate", new Date()),Restrictions.or(Restrictions.ge("allocEndDate", new Date()), Restrictions.isNull("allocEndDate"))));
				  criterion1=Restrictions.in("projectId.id", projectIds);
				//  criterion1=Restrictions.le("allocStartDate", new Date());
				 if(criterion2 !=null)
						criteria.add(Restrictions.or(criterion1,criterion2));
						  else
							  criteria.add(criterion1);
				}
				else
					if(criterion2 !=null)
					criteria.add(criterion2);
						
				 if(criterion1!=null || criterion2!=null)
				list = criteria.list();
			
			
		} catch (HibernateException e) {
			logger.error("Exception occured in findAllocatedResourceEmployeeIdByProjectIdsPagination method at DAO layer:-"
					+ e);
			throw e;
		}

		logger.info("--------ResourceAllocationDaoImpl findAllocatedResourceEmployeeIdByProjectIdsPagination method end-------");
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public long countAllocatedResourceEmployeeIdByProjectIdsPagination(List projectIds,String active,ResourceAllocationSearchCriteria resourceAllocationSearchCriteria,boolean search,Integer employeeId) {
		logger.info("--------ResourceAllocationDaoImpl countAllocatedResourceEmployeeIdByProjectIdsPagination method start-------");
		Session session = (Session) getEntityManager().getDelegate();
		ResourceSearchCriteria resourceSearchCriteria = new ResourceSearchCriteria();
    	resourceSearchCriteria.setRefTableName(resourceAllocationSearchCriteria.getRefTableName());
    	resourceSearchCriteria.setRefColName(resourceAllocationSearchCriteria.getRefColName());
    	resourceSearchCriteria.setSortTableName(resourceAllocationSearchCriteria.getSortTableName());
    	resourceSearchCriteria.setOrderStyle(resourceAllocationSearchCriteria.getOrderStyle());
    	resourceSearchCriteria.setOrederBy(resourceAllocationSearchCriteria.getOrederBy());
		Criteria criteria = session.createCriteria(ResourceAllocation.class).setProjection(Projections.property("employeeId.employeeId")).setProjection(Projections.distinct(Projections.property("employeeId.employeeId")));
		
		List<Integer> employeeIdList=new ArrayList<Integer>();
		
		ResourceAllocationDao resourceAllocationDao=AppContext.getApplicationContext().getBean(ResourceAllocationDao.class);
		employeeIdList=resourceAllocationDao.findAllocatedEmployeeInProjectByCurrentUser(employeeId,true);
		if(employeeIdList.isEmpty())
		{
			employeeIdList.add(0);
		}
		List list = new ArrayList();
		try {
			Criteria criteriaResource = session.createCriteria(Resource.class).setProjection(Projections.property("employeeId"));
			Criterion c1 = null;
			Criterion c2 =Restrictions.or(Restrictions.disjunction()
					.add(Restrictions.eq("currentReportingManagerTwo.employeeId",
							employeeId))
					.add(Restrictions.eq("currentReportingManager.employeeId",
							employeeId)).add(Restrictions.in("employeeId", employeeIdList)));
			
			 if(active.equals("active")){
			  c1 =(Restrictions.disjunction()
						.add(Restrictions.ge("releaseDate", new Date()))
						.add(Restrictions.isNull("releaseDate")));
			
		
			criteriaResource.add(c1).add(c2);}
			 else {
				 criteriaResource.add(c2);
			 }
			
			if(search){
				Criteria resourceCriteria = session.createCriteria(Resource.class).setProjection(Projections.property("employeeId"));
				if(active.equals("active")){
					 resourceCriteria.add(Restrictions.disjunction()
							.add(Restrictions.ge("releaseDate", new Date()))
							.add(Restrictions.isNull("releaseDate")));
					}
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

	    			resourceDao.applySort(resourceSearchCriteria, resourceCriteria);
	    		}
	    		else {
	    			
	    				if (resourceAllocationSearchCriteria.getBaseTableCol().equals("currentBuId")) {
	    				
	    				List<OrgHierarchy> bgBuId = searchByBgBu(resourceAllocationSearchCriteria.getSearchValue());
						if (!bgBuId.isEmpty()) 
							resourceCriteria.add(Restrictions.in(resourceAllocationSearchCriteria.getBaseTableCol(), bgBuId));
						else
							return 0;
							
					}
	    			 
else{
	    			resourceCriteria.createAlias(resourceSearchCriteria.getBaseTableCol(),
	    						"table");
	    			resourceCriteria.add(Restrictions.like("table."
	    						+ resourceSearchCriteria.getRefColName(), "%"
	    						+ resourceAllocationSearchCriteria.getSearchValue() + "%"));
	    		 

	    			if (resourceSearchCriteria.getRefColName().equals(
	    					resourceSearchCriteria.getOrederBy())) {
	    				resourceDao.sortCriteria(resourceSearchCriteria, resourceCriteria);
	    			} else {
	    				resourceDao.applySort(resourceSearchCriteria, resourceCriteria);
	    			}
}

	    		}
	    		if(resourceCriteria.list() !=null && resourceCriteria.list().size()>0 ){
	    			criteriaResource.add(Restrictions.in("employeeId",resourceCriteria.list()));
	    			criteria.add(Restrictions.in("employeeId.employeeId",resourceCriteria.list()));
	    			
	    			
	    			List<Integer> list1=	criteriaResource.list();
	    			Criterion criterion2 =null;
	    				//Criterion criterion1 = (Criterion) criteria.add(Restrictions.le("allocStartDate", new Date()));
	    				//Criterion criterion2 = (Criterion) criteria.add(Restrictions.ge("allocEndDate", new Date()));
	    			if(list1 != null && list1.size()>0)
	    		  criterion2  = Restrictions.in("employeeId.employeeId",list1);
	    			Criterion criterion1= null;
	    			if (projectIds != null && !projectIds.isEmpty() ) {
	    			  criterion1 = Restrictions.and(Restrictions.in("projectId.id", projectIds),Restrictions.and(Restrictions.le("allocStartDate", new Date()),Restrictions.or(Restrictions.ge("allocEndDate", new Date()), Restrictions.isNull("allocEndDate"))));
	    			  if(criterion2 !=null)
	    			criteria.add(Restrictions.or(criterion1,criterion2));
	    			  else
	    				  criteria.add(criterion1);
	    			}
	    			else
	    				if(criterion2 !=null)
	    				criteria.add(criterion2);
	    			 
	   
	    			 
	    			
	    			list = criteria.list();
	    		}
	    		 
	    		
	    		}
			else{
			List<Integer> list1=	criteriaResource.list();
			Criterion criterion2 =null;
				//Criterion criterion1 = (Criterion) criteria.add(Restrictions.le("allocStartDate", new Date()));
				//Criterion criterion2 = (Criterion) criteria.add(Restrictions.ge("allocEndDate", new Date()));
			if(list1 != null && list1.size()>0)
		  criterion2  = Restrictions.in("employeeId.employeeId",list1);
			Criterion criterion1= null;
			if (projectIds != null && !projectIds.isEmpty() ) {
			  criterion1 = Restrictions.and(Restrictions.in("projectId.id", projectIds),Restrictions.and(Restrictions.le("allocStartDate", new Date()),Restrictions.or(Restrictions.ge("allocEndDate", new Date()), Restrictions.isNull("allocEndDate"))));
			  if(criterion2 !=null)
			criteria.add(Restrictions.or(criterion1,criterion2));
			  else
				  criteria.add(criterion1);
			}
			else
				if(criterion2 !=null)
				criteria.add(criterion2);
			 
			 
			 
			if(criterion1 !=null ||criterion2 !=null)
			list = criteria.list();
			}
		} catch (HibernateException e) {
			logger.error("Exception occured in countAllocatedResourceEmployeeIdByProjectIdsPagination method at DAO layer:-"
					+ e);
			throw e;
		}

		logger.info("--------ResourceAllocationDaoImpl countAllocatedResourceEmployeeIdByProjectIdsPagination method end-------");
		return list.size();
	}
	
	

	public List<ResourceAllocation> findResourceAllocationsByProjectId(
			Integer projectId) {
		logger.info("--------ResourceAllocationDaoImpl findResourceAllocationsByProjectId method start-------");
		try {
			Session session = (Session) getEntityManager().getDelegate();
			List<Object[]> list = new ArrayList<Object[]>();
			List<ResourceAllocation> resourceAllocationList = new ArrayList<ResourceAllocation>();
			org.hibernate.Query query = session
					.createQuery("SELECT resAlloc.projectId.projectName, resAlloc.allocationTypeId.allocationType, resAlloc.allocStartDate, resAlloc.allocEndDate, "
							+ "resAlloc.employeeId.firstName, resAlloc.employeeId.middleName, resAlloc.employeeId.lastName, resAlloc.allocRemarks "
							+ ",resAlloc.curProj , resAlloc.billable , resAlloc.behalfManager ,resAlloc.id, resAlloc.employeeId.employeeId, resAlloc.projectId.id, resAlloc.allocatedBy.employeeId,resAlloc.updatedBy.employeeId, resAlloc.employeeId.currentBuId, resAlloc.projectId.orgHierarchy,resAlloc.allocationTypeId.id  "
							+ ",resAlloc.employeeId.ownership.ownershipName, resAlloc.employeeId.ownership.id, resAlloc.employeeId.yashEmpId,resAlloc.employeeId.dateOfJoining, resAlloc.projectId.projectKickOff, resAlloc.projectId.projectEndDate, resAlloc.projectEndRemarks,resAlloc.resourceType,resAlloc.allocPercentage  "
                        	+ "FROM  ResourceAllocation resAlloc where resAlloc.projectId.id = :projectId)");
			
					query.setParameter("projectId", projectId);
		           list= query.list();
				resourceAllocationList=populateResourceAllocation(list);
				return resourceAllocationList;
		
		} catch (HibernateException e) {
			logger.error("Exception occured in findResourceAllocationsByProjectId method at DAO layer:-"
					+ e);
			throw e;
		}
	}
	
	public List<Object[]> findMinMaxUseractivity(Integer resAlloc) {
		try {
			Session session = (Session) getEntityManager().getDelegate();
			org.hibernate.Query query = session.createSQLQuery("select min(date), max(date) from user_activity m inner join user_activity_detail d on m.id = d.time_sheet_id where m.res_alloc_id = :resAlloc");
			query.setParameter("resAlloc", resAlloc);
			return query.list();
		} catch (HibernateException e) {
			logger.error("Exception occured in findLastUseractivity method at DAO layer:-"
					+ e);
			throw e;
		}
	}
	
	public List<Object[]> findLastUseractivity(Integer resAlloc) {
		logger.info("--------ResourceAllocationDaoImpl findLastUseractivity method start-------");
		ResourceAllocation resourceAllocation = new ResourceAllocation();
		resourceAllocation.setId(resAlloc);
		List<Object[]> list = new ArrayList<Object[]>();
		try {
			Session session = (Session) getEntityManager().getDelegate();
			org.hibernate.Query query = session
					.createQuery("SELECT d1Hours,d2Hours,d3Hours,d4Hours,d5Hours,d6Hours,d7Hours, MIN(weekStartDate), MAX(weekEndDate) FROM "
							+ " UserActivity WHERE resourceAllocId= :resAlloc");
			query.setParameter("resAlloc", resourceAllocation);
			list = query.list();
		} catch (HibernateException e) {
			logger.error("Exception occured in findLastUseractivity method at DAO layer:-"
					+ e);
			throw e;
		}
		logger.info("--------ResourceAllocationDaoImpl findLastUseractivity method end-------");
		return list;
	}

	public List<Object[]> findFirstUseractivity(Integer resAlloc) {
		logger.info("--------ResourceAllocationDaoImpl findLastUseractivity method start-------");
		ResourceAllocation resourceAllocation = new ResourceAllocation();
		resourceAllocation.setId(resAlloc);
		List<Object[]> list = new ArrayList<Object[]>();
		try {
			Session session = (Session) getEntityManager().getDelegate();
			org.hibernate.Query query = session
					.createQuery("SELECT d1Hours,d2Hours,d3Hours,d4Hours,d5Hours,d6Hours,d7Hours,weekStartDate FROM  UserActivity WHERE resourceAllocId= :resAlloc AND weekStartDate IN( SELECT MIN(weekStartDate) FROM UserActivity WHERE resourceAllocId= :resAlloc)");
			query.setParameter("resAlloc", resourceAllocation);
			list = query.list();
		} catch (HibernateException e) {
			logger.error("Exception occured in findFirstUseractivity method at DAO layer:-"
					+ e);
			throw e;
		}
		logger.info("--------ResourceAllocationDaoImpl findFirstUseractivity method end-------");
		return list;
	}

	public ResourceAllocation findResourceAllocation(Integer id) {
		logger.info("--------ResourceAllocationDaoImpl findResourceAllocation method start-------");
		if (id == null)
			return null;

		ResourceAllocation resourceAllocation;
		try {
			Criteria criteria = ((Session) getEntityManager().getDelegate())
					.createCriteria(ResourceAllocation.class);
			resourceAllocation = (ResourceAllocation) criteria
					.add(Restrictions.eq("id", id)).uniqueResult();
		} 
			catch (HibernateException e) {
				logger.error("Exception occured in findResourceAllocation method at DAO layer:-"
						+ e);
				throw e;
			}
		
		logger.info("--------ResourceAllocationDaoImpl findResourceAllocation method end-------");
		return resourceAllocation;

	}

	public ResourceAllocation findResourceAllocationsByEmployeeIdAndProjectId(Integer employeeId, Integer projectId) {
		
		logger.info("--------ResourceAllocationDaoImpl findResourceAllocationsByEmployeeIdAndProjectId method start-------");
		ResourceAllocation resourceAllocation;
		
		try {
			Criteria criteria = ((Session) getEntityManager().getDelegate()).createCriteria(ResourceAllocation.class);
			resourceAllocation = (ResourceAllocation) criteria.add(Restrictions.conjunction().add(Restrictions.eq("employeeId.employeeId", employeeId)).add(Restrictions.eq("projectId.id", projectId))).setFetchSize(1).uniqueResult();
		
		} catch (HibernateException e) {
			logger.error("Exception occured in findResourceAllocationsByEmployeeIdAndProjectId method at DAO layer:-"+ e);
			throw e;
		}

		logger.info("--------ResourceAllocationDaoImpl findResourceAllocationsByEmployeeIdAndProjectId method end-------");
		return resourceAllocation;
	}

	public ResourceAllocation findById(int id) {
		logger.info("--------ResourceAllocationDaoImpl findById method start-------");
		ResourceAllocation resourceAllocation = null;
		Criteria criteria = ((Session) getEntityManager().getDelegate())
				.createCriteria(ResourceAllocation.class);
		try {
			resourceAllocation = (ResourceAllocation) criteria.add(
					Restrictions.eq("id", id)).uniqueResult();
			// // Getting the Project Details
			// resourceAllocation.getProjectId().getProjectName();
			// resourceAllocation.getEmployeeId().getEmployeeName();
		} catch (HibernateException ex) {
			logger.error("Exception occured in Resource Allocation Dao findById method at DAO layer:-"
					+ ex);
			throw ex;
		}

		logger.info("--------ResourceAllocationDaoImpl findById method end-------");
		return resourceAllocation;
	}

	public ResourceAllocation findResourceAllocationsByEmployeeIdAndProjectId(
			Resource employeeId, Project projectId) {

		logger.info("--------ResourceAllocationDaoImpl findResourceAllocationsByEmployeeIdAndProjectId method start-------");
		if (employeeId == null)
			throw new IllegalArgumentException(
					"The employeeId argument is required");
		if (projectId == null)
			throw new IllegalArgumentException(
					"The projectId argument is required");

		ResourceAllocation resourceAllocation;
		try {
			org.hibernate.Query query = ((Session) getEntityManager().getDelegate())
					.createQuery(
							"FROM ResourceAllocation WHERE employeeId = :employeeId AND projectId = :projectId");
			query.setParameter("employeeId", employeeId);
			query.setParameter("projectId", projectId);

			resourceAllocation = (ResourceAllocation) query
					.uniqueResult();
		} catch (HibernateException e) {
			logger.error("Exception occured in Resource Allocation Dao findResourceAllocationsByEmployeeIdAndProjectId method at DAO layer:-"
					+ e);
			throw e;
		}
		logger.info("--------ResourceAllocationDaoImpl findResourceAllocationsByEmployeeIdAndProjectId method end-------");
		return resourceAllocation;
	}

	@SuppressWarnings("unchecked")
	public List<Integer> findAllocatedResourceEmployeeId(Integer projectId,String active) {
		logger.info("--------ResourceAllocationDaoImpl findAllocatedResourceEmployeeId method start-------");
		Session session = (Session) getEntityManager().getDelegate();

		List<Integer> employeeIds = new ArrayList<Integer>();

		try {
			Criteria criteria = session
					.createCriteria(ResourceAllocation.class).setProjection(
							Projections.property("employeeId.employeeId"));
			criteria.add(Restrictions.eq("projectId.id", projectId));
			if(active.equals("active"))
			{
				criteria.add(Restrictions.in("employeeId.employeeId", session.createCriteria(Resource.class).setProjection(
						Projections.property("employeeId")).add(Restrictions.disjunction().add(Restrictions.ge("releaseDate", new Date()))
						.add(Restrictions.isNull("releaseDate"))).list())).add(Restrictions.disjunction().add(Restrictions.isNull("allocEndDate")).add(Restrictions.ge("allocEndDate", new Date())));
						
			}
			employeeIds = criteria.list();
		} catch (HibernateException hibernateException) {
			logger.error("HibernateException occured in Resource Allocation Dao findAllocatedResourceEmployeeId method:-"
					+ hibernateException);
			throw hibernateException;
		}
		logger.info("--------ResourceAllocationDaoImpl findAllocatedResourceEmployeeId method end-------");
		return employeeIds;

	}

	@SuppressWarnings("unchecked")
	public List<Integer> findProjectIdsByEmployeeIdAndIsBehalfManager(
			Integer employeeId) {
		logger.info("--------ResourceAllocationDaoImpl findProjectIdsByEmployeeIdAndIsBehalfManager method start-------");
		Session session = (Session) getEntityManager().getDelegate();
		List<Integer> projectIds = new ArrayList<Integer>();

		try {
			Criteria criteria = session
					.createCriteria(ResourceAllocation.class).setProjection(
							Projections.property("projectId.id"));
			;
			criteria.add(Restrictions.eq("employeeId.employeeId", employeeId))
					.add(Restrictions.eq("behalfManager", true));
			projectIds = criteria.list();
		} catch (HibernateException hibernateException) {
			logger.error("HibernateException occured in Resource Allocation Dao findProjectIdsByEmployeeIdAndIsBehalfManager method:-"
					+ hibernateException);
			throw hibernateException;
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		
		logger.info("--------ResourceAllocationDaoImpl findProjectIdsByEmployeeIdAndIsBehalfManager method end-------");
		return projectIds;

	}

	@SuppressWarnings("unchecked")
	public List<ResourceAllocation> findResourceAllocationByActiveTypeEmployeeId(
			Resource resource) {
		logger.info("--------ResourceAllocationDaoImpl findResourceAllocationByActiveTypeEmployeeId method start-------");

		List<ResourceAllocation> list = null;
		try {
			Criteria criteria = ((Session) getEntityManager().getDelegate())
					.createCriteria(ResourceAllocation.class);
			
			list=criteria.add(Restrictions.eq("employeeId.employeeId", resource.getEmployeeId())).add(Restrictions.or(
			      
			        Restrictions.isNull("allocEndDate"), Restrictions.ge( "allocEndDate", new Date() ))).addOrder(Order.desc("allocationSeq")).list();//Order changed as per the Task# 185
			
			
		} catch (HibernateException e) {
			logger.error("Exception occured in findResourceAllocationsByEmployeeId method at DAO layer:-"
					+ e);
			throw e;
		}
		logger.info("--------ResourceAllocationDaoImpl findResourceAllocationByActiveTypeEmployeeId method end-------");
		
		return list;
	
		
	}

	public List<ResourceAllocation> findActiveResourceAllocationsByProjectId(
			Integer id) {
		logger.info("--------ResourceAllocationDaoImpl findActiveResourceAllocationsByProjectId method start-------");
		try {
			Session session = (Session) getEntityManager().getDelegate();
			List<Object[]> list = new ArrayList<Object[]>();
			List<ResourceAllocation> resourceAllocationList = new ArrayList<ResourceAllocation>();
			org.hibernate.Query query = session
					.createQuery("SELECT  resAlloc.projectId.projectName, resAlloc.allocationTypeId.allocationType, resAlloc.allocStartDate, resAlloc.allocEndDate, "
							+ "resAlloc.employeeId.firstName, resAlloc.employeeId.middleName, resAlloc.employeeId.lastName, resAlloc.allocRemarks "
							+ ",resAlloc.curProj , resAlloc.billable , resAlloc.behalfManager ,resAlloc.id, resAlloc.employeeId.employeeId, resAlloc.projectId.id, resAlloc.allocatedBy.employeeId,resAlloc.updatedBy.employeeId, resAlloc.employeeId.currentBuId, resAlloc.projectId.orgHierarchy,resAlloc.allocationTypeId.id,"
							+ "resAlloc.employeeId.ownership.ownershipName,resAlloc.employeeId.ownership.id, resAlloc.employeeId.yashEmpId, resAlloc.employeeId.dateOfJoining, resAlloc.projectId.projectKickOff, resAlloc.projectId.projectEndDate, resAlloc.projectEndRemarks,resAlloc.resourceType,resAlloc.allocPercentage "
							+ "FROM  ResourceAllocation resAlloc where resAlloc.projectId.id = :projectId AND (resAlloc.allocEndDate>CURRENT_DATE or resAlloc.allocEndDate IS NULL))");
			
					query.setParameter("projectId", id);
					list= query.list();
				resourceAllocationList=populateResourceAllocation(list);

				return  resourceAllocationList;
					
		
		
		} catch (HibernateException e) {
			logger.error("Exception occured in findResourceAllocationsByProjectId method at DAO layer:-"
					+ e);
			throw e;
		}
	}

	private List<ResourceAllocation> populateResourceAllocation(
			List<Object[]> list) {
		// TODO Auto-generated method stub
		List<ResourceAllocation> resourceAllocationsList= new ArrayList<ResourceAllocation>();
		
		if (list != null && list.size()>0) {
			for (Object[] objects : list) {
				ResourceAllocation resourceAllocation = new ResourceAllocation();
				
				Project project= new Project();
				Ownership ownership=new Ownership();
				Resource res = new Resource();
				Resource res1 = new Resource();
				Resource res2 = new Resource();
				
				//first obj is projectName
				if (objects[0] != null) {
					project.setProjectName(objects[0].toString());
					resourceAllocation.setProjectId(project);
				}
				if (objects[1] != null && objects[18] !=null) {
					AllocationType allocationType = new AllocationType();
					allocationType.setId(Integer.parseInt(objects[18].toString()));
					allocationType.setAllocationType(objects[1].toString());
					resourceAllocation.setAllocationTypeId(allocationType);
					}
					
				if(objects[2]!= null){
					resourceAllocation.setAllocStartDate((Date) objects[2]);
						
				}
				if(objects[3]!=null){
					 resourceAllocation.setAllocEndDate((Date) objects[3]);
				}
				
				if(objects[4]!=null){
						res.setFirstName(objects[4].toString());
						resourceAllocation.setEmployeeId(res);
				}
				if(objects[5]!=null){
					res.setMiddleName(objects[5].toString());
					resourceAllocation.setEmployeeId(res);
			}
				if(objects[6]!=null){
					res.setLastName(objects[6].toString());
					resourceAllocation.setEmployeeId(res);
			}
				if(objects[7]!=null){
					resourceAllocation.setAllocRemarks(objects[7].toString());
					}
				
				if(objects[8]!=null){
					resourceAllocation.setCurProj(Boolean.valueOf(objects[8].toString()));
				}
				 if(objects[9]!=null){
					 resourceAllocation.setBillable(Boolean.valueOf(objects[9].toString()));
				 }
				
				
				if(objects[10]!=null){
					resourceAllocation.setBehalfManager(Boolean.valueOf(objects[10].toString()));
				}
				
				if(objects[11]!=null){
					resourceAllocation.setId(Integer.parseInt(objects[11].toString()));
				}
				if(objects[12]!=null && objects[21]!=null  && objects[22]!=null){
					res.setEmployeeId(Integer.parseInt(objects[12].toString()));
					res.setYashEmpId(objects[21].toString());
					res.setDateOfJoining((Date) objects[22]);
					resourceAllocation.setEmployeeId(res);
				}
				
				if(objects[13]!=null){
					project.setId(Integer.parseInt(objects[13].toString()));
					resourceAllocation.setProjectId(project);
				}
				
				if(objects[14]!=null){
				res1.setEmployeeId(Integer.parseInt(objects[14].toString()));
				resourceAllocation.setAllocatedBy(res1);
				}
				
				if(objects[15]!=null){
					res2.setEmployeeId(Integer.parseInt(objects[15].toString()));
					resourceAllocation.setUpdatedBy(res2);
					}
					
				if(objects[16]!=null){
					res.setCurrentBuId(((OrgHierarchy)objects[16]));
					resourceAllocation.setEmployeeId(res);
				}
				
				
				if(objects[17]!=null){
					project.setOrgHierarchy((OrgHierarchy)objects[17]);
					resourceAllocation.setProjectId(project);
				}
				
				if (objects[19] != null && objects[20]!=null) {
					ownership.setOwnershipName(objects[19].toString());
					ownership.setId(Integer.parseInt(objects[20].toString()));
					resourceAllocation.setOwnershipId(ownership);
					
				}
				
				// Added by Neha
				if (objects[23] != null) {
					Date kickOffdate = (Date) objects[23];
					project.setProjectKickOff(kickOffdate);
					//resourceAllocation.setProjectId(project);;
				}
				if (objects[24] != null) {
					Date projectEndDate = (Date) objects[24];
					project.setProjectEndDate(projectEndDate);
					//resourceAllocation.setProjectId(project);;
				}
				
				if (objects[25] != null) {
				  String projectEndRemarks = objects[25].toString();
				  resourceAllocation.setProjectEndRemarks(projectEndRemarks);
               }
				if (objects[26] != null) {
					  String resourceType = objects[26].toString();
					  resourceAllocation.setResourceType(resourceType);
	           }
				if(objects[27]!=null){
			      resourceAllocation.setAllocPercentage(Integer.parseInt(objects[27].toString()));
				}
				resourceAllocationsList.add(resourceAllocation);
				}
				
			}
		
		return resourceAllocationsList;
	}
	
	// US3090(Added by Maya): START: Future timesheet delete functionality
	public List<UserActivity> isFutureTimesheetpresent(Integer resourceAllocId,
				Date allocWeekEndDate) {
			logger.info("--------ResourceAllocationDaoImpl isFutureTimesheetpresent method start-------");
			List<UserActivity> userActivities = null;
			boolean status = true;
			try {
				 Session session = (Session) getEntityManager().getDelegate();
				 userActivities = session.createQuery(" FROM  UserActivity  where resourceAllocId = :resourceAllocId AND weekEndDate > :weekEndDate ").setString("resourceAllocId",resourceAllocId.toString()).setDate("weekEndDate", allocWeekEndDate).list();
				
			}catch (HibernateException e) {
				logger.error("Exception occured in isFutureTimesheetpresent method at DAO layer:-"
						+ e);
				throw e;
			}
				logger.info("--------ResourceAllocationDaoImpl isFutureTimesheetpresent method start-------");
			return userActivities;
	}

	public boolean deleteFutureTimesheet(List<Integer> userActivityIdList,String resourceAllocId, String weekEndDate) {
		logger.info("--------ResourceAllocationDaoImpl deleteFutureTimesheet method start-------");
		
		boolean isSuccess = true;
		Integer timeSheetId;
		try {
			Session session = (Session) getEntityManager().getDelegate();
			for(int i =0; i < userActivityIdList.size(); i++ )
			{
				timeSheetId = userActivityIdList.get(i);
				String hql = "DELETE FROM  UserTimeSheetDetail  WHERE  timeSheetId=:timeSheetId";
				Query query = session.createQuery(hql);
				String hql1 = "DELETE FROM  Timehrs  WHERE  resource_alloc_id="+resourceAllocId+" "+"AND week_ending_date>'"+(weekEndDate)+"'";
				System.out.println("hql1"+hql1);
				Query query1 = session.createQuery(hql1);
				query.setInteger("timeSheetId", timeSheetId);
				//query1.setInteger("timeSheetId", timeSheetId);
				//query1.setDate("week_ending_date", weekEndDate);
				//query1.setInteger("resource_alloc_id", Integer.parseInt(resourceAllocId));
				int result = query.executeUpdate();
				int result1 = query1.executeUpdate();
			}
			
		} catch (HibernateException e) {
			isSuccess = false;
			logger.error("Exception occured in deleteFutureTimesheet method at DAO layer:-"
					+ e);
			throw e;
		} catch (Exception ex) {
			isSuccess = false;
		}
		logger.info("--------ResourceAllocationDaoImpl deleteFutureTimesheet method End-------");
		return isSuccess;
}
	// US3090(Added by Maya): END: Future timesheet delete functionality	
	
	
	//added by purva for US3088 Starts

	@SuppressWarnings("unchecked")
	public boolean checkIfAllocationIsOpen(Integer resourceAllocId,
			Date allocWeekEndDate) {
		logger.info("--------ResourceAllocationDaoImpl findResourceAllocationByActiveTypeEmployeeId method start-------");
		boolean status = false;
		List<ResourceAllocation> list = null;
		List<Object[]> list1 = null;
		try {
			Session session = (Session) getEntityManager().getDelegate();
			Criteria criteria = ((Session) getEntityManager().getDelegate())
					.createCriteria(ResourceAllocation.class);
		
			Query query = session
					.createQuery("select ra.allocEndDate,ra.projectId from ResourceAllocation ra where ra.id=:id");
			query.setParameter("id", resourceAllocId);
			list1=	query.list();
			Object[] obj = list1.get(0);
			System.out.println("List`111---------"+ obj[0]);
			if( obj[0] ==null ){
				criteria.add(Restrictions.eq("id", resourceAllocId)).add(Restrictions.le("allocStartDate", allocWeekEndDate));
			}else{
				criteria.add(Restrictions.eq("id", resourceAllocId)).add(Restrictions.le("allocStartDate", allocWeekEndDate)).add(Restrictions.ge("allocEndDate", allocWeekEndDate));
			}
			
	//	criteria.add(Restrictions.eq("id", resourceAllocId)).add(Restrictions.disjunction().add(Restrictions.isNull("allocEndDate")).add(Restrictions.ge("allocEndDate", allocWeekEndDate))).add(Restrictions.le("allocStartDate", allocWeekEndDate)).list();
			list=criteria.list();
			
			if(null!=list && list.size()>0){
				status=true;
			}
			else{
				query = session
						.createQuery("select ra.id from ResourceAllocation ra where ra.projectId=:projectid and ra.allocStartDate >=:startDate and ra.allocEndDate >=:endDate");
				
				query.setParameter("projectid", obj[1]);
				query.setParameter("startDate", obj[0]);
				query.setParameter("endDate", allocWeekEndDate);//allocWeekEndDate
				List<ResourceAllocation> result= query.list();
				if(result!=null){
					status=true;
				}
				
			}
		} catch (HibernateException e) {
			status=false;
			logger.error("Exception occured in findResourceAllocationsByEmployeeId method at DAO layer:-"
					+ e);
			throw e;
		}
		logger.info("--------ResourceAllocationDaoImpl findResourceAllocationByActiveTypeEmployeeId method end-------");
		
		return status;
		
	}

	public int findPrimaryProjectCount(final int employeeId) {
		
		logger.info("--------ResourceAllocationDaoImpl findPrimaryProjectCount method Start-------");
		int totalResult = 0;
		try {
			Criteria criteria = ((Session) getEntityManager().getDelegate()).createCriteria(ResourceAllocation.class);
			criteria.add(Restrictions.conjunction().add(Restrictions.eq("employeeId.employeeId", employeeId)).add(Restrictions.eq("curProj",true)));
		    totalResult = ((Number)criteria.setProjection(Projections.rowCount()).uniqueResult()).intValue();
		    logger.info(String.format("Primary Poject allocated to Employee Id %d is %d",employeeId,totalResult));
		}
		catch (HibernateException e) {
 			logger.error("Exception occured in findPrimaryProjectCount method at DAO layer:-"+ e);
 			return totalResult;
 		}
		logger.info("--------ResourceAllocationDaoImpl findPrimaryProjectCount method End-------");
		return totalResult;
	}
	public ResourceAllocation findPrimaryProject(Integer employeeId) {
		
		logger.info("--------ResourceAllocationDaoImpl findPrimaryProject method Start-------");  //yashEmpId
		ResourceAllocation resourceAllocation=null;
		try {
			
			Criteria criteria = ((Session) getEntityManager().getDelegate()).createCriteria(ResourceAllocation.class);
			criteria.add(Restrictions.conjunction().add(Restrictions.eq("employeeId.employeeId", employeeId)).add(Restrictions.eq("curProj",true)));
			List<ResourceAllocation> reallocList=criteria.list();
			if(!reallocList.isEmpty())
			{
			resourceAllocation = (ResourceAllocation) reallocList.get(0);
			logger.info(String.format("Primary Poject allocated to Employee is :"+resourceAllocation.getProjectId().getProjectName()));
			
			}
			
		   
		}
		catch (HibernateException e) {
 			logger.error("Exception occured in findPrimaryProject method at DAO layer:-"+ e);
 			return resourceAllocation;
 		}
		logger.info("--------ResourceAllocationDaoImpl findPrimaryProject method End-------");
		return resourceAllocation;
	}

	
	public List<ResourceAllocation> getAllocationBlockedResourceWithThirtyDaysMore(List<Integer> orgIds ,Boolean isTraineeProject,int days, Date lastDateToCompare) {
		logger.info("--------ResourceAllocationDaoImpl GetAllocationBlockedResourceWithThirtyDaysMore Method Start-------");
		List<ResourceAllocation> resourceAllocations = null;
		try {
			Session session = (Session) getEntityManager().getDelegate();
			if (session != null) {
				
				Criteria criteria = session.createCriteria(ResourceAllocation.class, "ra");
				criteria.setProjection(Projections.property("ra.id"));
				
				criteria.createAlias("ra.employeeId", "rs");
				criteria.createAlias("rs.currentBuId", "org");
				criteria.createAlias("ra.allocationTypeId", "at");
				criteria.createAlias("ra.projectId", "pr");
				
				Date currentDate = new Date();
				Criterion allocDate = Restrictions.disjunction().add(Restrictions.isNull("ra.allocEndDate"))
						.add(Restrictions.ge("ra.allocEndDate", currentDate));
				Criterion releaseDate = Restrictions.disjunction().add(Restrictions.isNull("rs.releaseDate"))
						.add(Restrictions.ge("rs.releaseDate", currentDate));

				String durationSql = " (DATEDIFF (CASE WHEN {alias}.alloc_end_date is NULL THEN ? "
						+ " WHEN {alias}.alloc_end_date > CURRENT_DATE THEN ? "
						+ " ELSE {alias}.alloc_end_date END,{alias}.alloc_start_date) + 1 ) >= ? ";
				
				Object [] sqlParameter = {lastDateToCompare,lastDateToCompare,new Integer(days)};
				Type [] valueType = {DateType.INSTANCE, DateType.INSTANCE, IntegerType.INSTANCE};
				Criterion durationCriterion = Restrictions.sqlRestriction(durationSql, sqlParameter, valueType);
				
				criteria.add(Restrictions.conjunction().add(Restrictions.eq("ra.curProj", true))
						.add(Restrictions.eq("at.allocationType", Constants.NONBILLABLE_BLOCKED))
						.add(Restrictions.lt("ra.allocStartDate", currentDate))
						.add(Restrictions.in("org.id", orgIds))
						.add(releaseDate).add(allocDate).add(durationCriterion));
				
				if(isTraineeProject != null) {
					criteria.add(Restrictions.eq("pr.traineeProject", isTraineeProject));
				}
				
				Criteria criteriaMain = session.createCriteria(ResourceAllocation.class);
				
				resourceAllocations = criteria.list();
				
				if(resourceAllocations.isEmpty())
				{
					return new ArrayList<ResourceAllocation>();
				}
				
				resourceAllocations = criteriaMain.add(Restrictions.in("id", resourceAllocations)).list();
			}
			else{
				logger.info("--------ResourceAllocationDaoImpl GetAllocationBlockedResourceWithThirtyDaysMore Session not found-------");
				return new ArrayList<ResourceAllocation>();
			}
		} catch (HibernateException ex) {
			logger.error("--------HibernateException in GetAllocationBlockedResourceWithThirtyDaysMore is-------" + ex.getMessage());
			ex.printStackTrace();

		} catch (Exception ex) {
			logger.error("--------Exception in GetAllocationBlockedResourceWithThirtyDaysMore is-------" + ex.getMessage());
			ex.printStackTrace();
		}
		logger.info("--------ResourceAllocationDaoImpl GetAllocationBlockedResourceWithThirtyDaysMore Method End-------");
		return resourceAllocations;
	}

	public List<ResourceAllocation> findActiveResourceAllcoation_ByProjectEngagementModelName(final int employeeId, String engagementModelName) {

		logger.info("--------ResourceAllocationDaoImpl @FindPoolProjects_ActiveResourceAllcoation Method Start-------");
		List<ResourceAllocation> resourceAllocations = new ArrayList<ResourceAllocation>();
		try {
			Session session = (Session) getEntityManager().getDelegate();
			if (session != null) {
				
				String hql = "SELECT ra FROM ResourceAllocation as ra INNER JOIN ra.projectId as p INNER JOIN p.engagementModelId as em "
						+ " WHERE ra.employeeId.employeeId=:employeeId AND ra.allocStartDate <= CURRENT_DATE() AND (ra.allocEndDate IS NULL OR ra.allocEndDate > CURRENT_DATE()) "
						+ " AND em.engagementModelName like :engagementModelName ";
				
				Query query = session.createQuery(hql);
				query.setParameter("employeeId", employeeId);
				query.setParameter("engagementModelName",'%' + engagementModelName + '%' );
				resourceAllocations = (List<ResourceAllocation>) query.list() ;
			}
		}
		catch (HibernateException ex) {
			logger.error("--------HibernateException in @FindPoolProjects_ActiveResourceAllcoation is-------" + ex.getMessage());
			ex.printStackTrace();
			throw ex;
		} 
		catch (Exception ex) {
			logger.error("--------Exception in @FindPoolProjects_ActiveResourceAllcoation is-------" + ex.getMessage());
			ex.printStackTrace();
		}
		logger.info("--------ResourceAllocationDaoImpl @FindPoolProjects_ActiveResourceAllcoation Method End-------");
		return resourceAllocations;
	}

	
}
