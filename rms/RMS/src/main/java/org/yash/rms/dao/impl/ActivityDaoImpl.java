/**
 * 
 */
package org.yash.rms.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.yash.rms.dao.ActivityDao	;
import org.yash.rms.domain.Activity;
import org.yash.rms.domain.AllProjectActivities;
import org.yash.rms.domain.ResourceAllocation;
import org.yash.rms.util.HibernateUtil;
import org.yash.rms.util.UserUtil;

/**
 * @author arpan.badjatiya
 *
 */
@Repository("ActivityDao")
/**
 * Methods with default of no impletenation are not in use as of now. In future if they are getting used then implement the same.
 * @author arpan.badjatiya
 *
 */
@Transactional
public class ActivityDaoImpl implements ActivityDao{
	
	private static final Logger logger = LoggerFactory.getLogger(ActivityDaoImpl.class);
	
	@PersistenceContext(name = "mysql", type = PersistenceContextType.TRANSACTION)
	private EntityManager entityManager;
	
	
	public EntityManager getEntityManager() {
		return entityManager;
	}
	
	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public boolean delete(int id) {
		logger.info("------------ActivityDaoImpl delete method start------------");
		boolean isSuccess=true;
		try {
			 Query query = ((Session) getEntityManager().getDelegate()).getNamedQuery(Activity.DELETE_ACTIVITY_BASED_ON_ID).setInteger("id", id);
			 int totalRowsDeleted = query.executeUpdate();
			System.out.println("Total Rows Deleted::" + totalRowsDeleted);
		} catch (HibernateException e) {
			isSuccess = false;
			e.printStackTrace();
			logger.error("Exception Occurred while deleting Activity "+id+e.getMessage());
			 throw e;
		}  finally {
//			currentSession.close();
		}
		logger.info("------------ActivityDaoImpl delete method end------------");
		return isSuccess;
	}
//Simran
	public boolean saveOrupdate(Activity activity) {
		logger.info("--------ActivityDaoImpl saveOrUpdate method start-------");
		if(null == activity) return false;
		
		Session currentSession = (Session) getEntityManager().getDelegate();
		boolean isSuccess=true;
		try {
			if(activity.getId()==0)
			{
				
				activity.setCreatedId( UserUtil.getUserContextDetails().getUserName());
				activity.setCreationTimestamp(new Date());
				
			}
			
		 activity.setLastUpdatedId( UserUtil.getUserContextDetails().getUserName());
				
			currentSession.saveOrUpdate(activity);
		} catch (HibernateException e) {
			isSuccess = false;
			e.printStackTrace();
			logger.error("Exception Occurred while saving Activity "+activity.getActivityName()+e.getMessage());
			 throw e;
		}   finally {
//			currentSession.close();
		}
		logger.info("------ActivityDaoImpl saveOrUpate method end-----");
		return isSuccess;
	}

	@SuppressWarnings("unchecked")
	public List<Activity> findAll() {
		logger.info("--------ActivityDaoImpl findAll method start-------");
		List<Activity> activityList = null;
		try {
			Session session=(Session) getEntityManager().getDelegate();
			if(null==session) throw new RuntimeException("Session Object Cannot be null");
			Criteria criteria =  session.createCriteria(Activity.class);
			criteria.addOrder(Order.asc("activityName")).list();
			criteria. add(Restrictions.not(Restrictions.in("type",new String []{"SEPG"})));
			activityList =criteria.list();
		}catch (HibernateException e) {
	         e.printStackTrace(); 
	         logger.error("EXCEPTION CAUSED IN GETTING ACTIVITY LIST " + e.getMessage());
	         throw e;
	    } finally {
			if (null == activityList) {
				activityList = new ArrayList<Activity>();
			}
		}
		logger.info("------------ActivityDaoImpl findAll method end------------");
		return activityList ;
	}

	public List<Activity> findByEntries(int firstResult, int sizeNo) {
		// TODO Auto-generated method stub
		return null;
	}

	public long countTotal() {
		// TODO Auto-generated method stub
		return 0;
	}



	public Activity findById(int id) {
		Activity activity = null;
		logger.info("--------ActivityDaoImpl findById method start-------");
		Session session=(Session) getEntityManager().getDelegate();
		try {
			activity = (Activity)session.createCriteria(Activity.class).add(Restrictions.eq("id", id)).uniqueResult(); 
		} catch (HibernateException e) {
	         e.printStackTrace(); 
	         logger.error("EXCEPTION CAUSED IN GETTING ACTIVITY By Id " +id+ e.getMessage());
	         throw e;
	     }finally {
	         //session.close(); 
	      }
		logger.info("------ActivityDaoImpl findById method end-----");
		return activity ;
	}

	@SuppressWarnings("unchecked")
	public List<Activity> findAllActivitysByProjectId(int projectId, String activityId) {
		List<Activity> activityList = null;
		logger.info("--------ActivityDaoImpl findActivitysByProjectId method start-------");
		Session session=(Session) getEntityManager().getDelegate();
		try {
			activityList = new ArrayList<Activity>();
			
			Criteria objCriteria = session.createCriteria(ResourceAllocation.class);
			objCriteria.add(Restrictions.eq("id", projectId));
			ProjectionList objProjectionList = Projections.projectionList();
			objProjectionList.add(Projections.property("projectId"));
			ResourceAllocation resourceAllocation = (ResourceAllocation)objCriteria.uniqueResult();
			
			Criteria criteria = session.createCriteria(AllProjectActivities.class);
			criteria.createAlias("defaultActivityid", "activity", JoinType.LEFT_OUTER_JOIN);
			criteria.createAlias("customActivityid", "customActivity", JoinType.LEFT_OUTER_JOIN);
			criteria.add(Restrictions.eq("projectid.id", resourceAllocation.getProjectId().getId()));
			if(!StringUtils.isEmpty(activityId) && !activityId.matches("[0-9]+")){
				String [] actList = activityId.split("_");
				Criterion criterion = Restrictions.eq("deactiveFlag", 1);
				if(actList[1].equalsIgnoreCase("D")){
					Criterion objCriterion = Restrictions.eq("defaultActivityid.id", Integer.parseInt(actList[0]));
					criteria.add(Restrictions.or(criterion, objCriterion));
				}else if(actList[1].equalsIgnoreCase("C")){
					Criterion objCriterion = Restrictions.eq("customActivityid.id", Integer.parseInt(actList[0]));
					criteria.add(Restrictions.or(criterion, objCriterion));
				}
			}
			ProjectionList projectionList = Projections.projectionList();
			projectionList.add(Projections.property("activity.id"));
			projectionList.add(Projections.property("activity.activityName"));
			projectionList.add(Projections.property("activity.mandatory")); //sarang added
			projectionList.add(Projections.property("customActivity.id"));
			projectionList.add(Projections.property("customActivity.activityName"));
			projectionList.add(Projections.property("customActivity.mandatory")); //sarang added
			projectionList.add(Projections.property("activityType"));
			
			criteria.setProjection(projectionList);
			
			List list = criteria.list();
			Iterator it = list.iterator();
			while(it.hasNext()){
				Object [] obj = (Object[])it.next();
				Activity activity = new Activity();
				if(null!=obj[0]){
					activity.setId((Integer)obj[0]);
					activity.setActivityName((String)obj[1]);
					activity.setMandatory((Boolean)obj[2]); //alter
				}
				if(null!=obj[3]){ //alter
					activity.setId((Integer)obj[3]); //alter
					activity.setActivityName((String)obj[4]); //alter
					activity.setMandatory((Boolean)obj[5]); //alter
				}
				if(null!=obj[6]){ //alter
					activity.setActivityType((String)obj[6]); //alter
				}
				
				activityList.add(activity);
			}
		} catch (HibernateException e) {
	         e.printStackTrace(); 
	         logger.error("EXCEPTION CAUSED IN GETTING ACTIVITY By Id " +projectId+ e.getMessage());
	         throw e;
	     }finally {
	         //session.close(); 
	      }
		logger.info("------ActivityDaoImpl findActivitysByProjectId method end-----");
		return activityList;
	}
	
	@SuppressWarnings("unchecked")
	public List<Activity> findActiveActivitysByResourceAllocationId(int resourceAllocationId) {
		List<Activity> activityList = null;
		logger.info("--------ActivityDaoImpl findActivitysByProjectId method start-------");
		Session session=(Session) getEntityManager().getDelegate();
		try {
			activityList = new ArrayList<Activity>();
			
			Criteria objCriteria = session.createCriteria(ResourceAllocation.class);
			objCriteria.add(Restrictions.eq("id", resourceAllocationId));
			ProjectionList objProjectionList = Projections.projectionList();
			objProjectionList.add(Projections.property("projectId"));
			ResourceAllocation resourceAllocation = (ResourceAllocation)objCriteria.uniqueResult();
			
			Criteria criteria = session.createCriteria(AllProjectActivities.class);
			criteria.createAlias("defaultActivityid", "activity", JoinType.LEFT_OUTER_JOIN);
			criteria.createAlias("customActivityid", "customActivity", JoinType.LEFT_OUTER_JOIN);
			criteria.add(Restrictions.eq("projectid.id", resourceAllocation.getProjectId().getId()));
			criteria.add(Restrictions.eq("deactiveFlag", 1));
			ProjectionList projectionList = Projections.projectionList();
			projectionList.add(Projections.property("activity.id"));
			projectionList.add(Projections.property("activity.activityName"));
			projectionList.add(Projections.property("customActivity.id"));
			projectionList.add(Projections.property("customActivity.activityName"));
			projectionList.add(Projections.property("activityType"));
			projectionList.add(Projections.property("activity.productive"));
			projectionList.add(Projections.property("customActivity.productive"));
			projectionList.add(Projections.property("activity.mandatory"));
			projectionList.add(Projections.property("customActivity.mandatory"));
			
			criteria.setProjection(projectionList);
			
			List list = criteria.list();
			Iterator it = list.iterator();
			while(it.hasNext()){
				Object [] obj = (Object[])it.next();
				Activity activity = new Activity();
				if(null!=obj[0]){
					activity.setId((Integer)obj[0]);
					activity.setActivityName((String)obj[1]);
					activity.setMandatory((Boolean)obj[7]);
				}
				if(null!=obj[2]){
					activity.setId((Integer)obj[2]);
					activity.setActivityName((String)obj[3]);
					activity.setMandatory((Boolean)obj[8]);
				}
				if(null!=obj[4]){
					activity.setActivityType((String)obj[4]);
				}
				if(null!=obj[5]){
					activity.setProductive((Boolean)obj[5]);
				}
				if(null!=obj[6]){
					boolean val=(Boolean)obj[6];
					if(val==true)
					activity.setProductive(val);
				}
				
				activityList.add(activity);
			}
		} catch (HibernateException e) {
	         e.printStackTrace(); 
	         logger.error("EXCEPTION CAUSED IN GETTING ACTIVITY By Id " +resourceAllocationId+ e.getMessage());
	         throw e;
	     }finally {
	         //session.close(); 
	      }
		logger.info("------ActivityDaoImpl findActivitysByProjectId method end-----");
		return activityList;
	}

	public List<Activity> findAllByIds(List<Integer> activityIds,List tpye) {
		// TODO Auto-generated method stub
		logger.info("--------ActivityDaoImpl findAll method start-------");
		List<Activity> activityList = null;
		try {
			Session session=(Session) getEntityManager().getDelegate();
			if(null==session) throw new RuntimeException("Session Object Cannot be null");
			
			Criteria criteria =  session.createCriteria(Activity.class);
			if(activityIds!=null && activityIds.size()>0)
			criteria.add(Restrictions.not(Restrictions.in("id", activityIds)));
			criteria.add(Restrictions.in("type",tpye));
		    criteria .addOrder(Order.asc("activityName")); 
			activityList = criteria.list();
		}catch (HibernateException e) {
	         e.printStackTrace(); 
	         logger.error("EXCEPTION CAUSED IN GETTING ACTIVITY LIST " + e.getMessage());
	         throw e;
	    } finally {
			if (null == activityList) {
				activityList = new ArrayList<Activity>();
			}
		}
		logger.info("------------ActivityDaoImpl findAll method end------------");
		return activityList ;
	}
	
	public List<Activity> findAllBySepgActivity(List<Integer> ids) {
		// TODO Auto-generated method stub
		logger.info("--------ActivityDaoImpl findAll method start-------");
		List<Activity> activityList = null;
		try {
			Session session=(Session) getEntityManager().getDelegate();
			if(null==session) throw new RuntimeException("Session Object Cannot be null");
			
			if(ids!=null && ids.size()>0){
			Criteria criteria =  session.createCriteria(Activity.class);
			criteria.add(Restrictions.in("id", ids));
		    criteria .addOrder(Order.asc("activityName")); 
			activityList = criteria.list();
			}
		}catch (HibernateException e) {
	         e.printStackTrace(); 
	         logger.error("EXCEPTION CAUSED IN GETTING ACTIVITY LIST " + e.getMessage());
	         throw e;
	    } finally {
			if (null == activityList) {
				activityList = new ArrayList<Activity>();
			}
		}
		logger.info("------------ActivityDaoImpl findAll method end------------");
		return activityList ;
	}

	public List<Activity> findSepgActivity(String type) {
		logger.info("--------ActivityDaoImpl findSepgActivity method start-------");
			Session session=(Session) getEntityManager().getDelegate();
			if(null==session) throw new RuntimeException("Session Object Cannot be null");
			Criteria criteria =  session.createCriteria(Activity.class);
			List<Activity> activityList = criteria.add(Restrictions.like("type", type)).list();
			logger.info("--------ActivityDaoImpl findSepgActivity method end-------");
		return (((activityList!=null) && (!activityList.isEmpty())?activityList:new ArrayList<Activity>()));
	}
	
	public List<Activity> findLeftSepgActivity(String type,List<Integer> ids) {
		logger.info("--------ActivityDaoImpl findLeftSepgActivity method start-------");
			Session session=(Session) getEntityManager().getDelegate();
			if(null==session) throw new RuntimeException("Session Object Cannot be null");
			Criteria criteria =  session.createCriteria(Activity.class);
			criteria.add(Restrictions.and(Restrictions.like("type", type), Restrictions.not(Restrictions.in("id", ids))));
			criteria .addOrder(Order.asc("activityName")); 
			 List<Activity> activityList = (List<Activity>)criteria.list();
			logger.info("--------ActivityDaoImpl findLeftSepgActivity method end-------");
		   return (((activityList!=null) && (!activityList.isEmpty())?activityList:new ArrayList<Activity>()));
	}
	
	public List<Activity> findSelectSepgActivity(List<Integer> ids) {
		logger.info("--------ActivityDaoImpl findSelectSepgActivity method start-------");
			Session session=(Session) getEntityManager().getDelegate();
			if(null==session) throw new RuntimeException("Session Object Cannot be null");
			Criteria criteria =  session.createCriteria(Activity.class);
			                    criteria.add((Restrictions.in("id", ids)));
			                    criteria .addOrder(Order.asc("activityName")); 
			 List<Activity> activityList = (List<Activity>)criteria.list();
			logger.info("--------ActivityDaoImpl findSelectSepgActivity method end-------");
		   return (((activityList!=null) && (!activityList.isEmpty())?activityList:new ArrayList<Activity>()));
	}
	
	

}
