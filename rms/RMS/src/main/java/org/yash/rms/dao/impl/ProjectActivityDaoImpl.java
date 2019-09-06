/**
 * 
 */
package org.yash.rms.dao.impl;

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
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.yash.rms.dao.ProjectActivityDao;
import org.yash.rms.domain.AllProjectActivities;
import org.yash.rms.domain.CustomActivity;
import org.yash.rms.domain.UserActivity;
import org.yash.rms.util.UserUtil;

@Repository("ProjectActivityDao")

@Transactional
public class ProjectActivityDaoImpl implements ProjectActivityDao{
	
	private static final Logger logger = LoggerFactory.getLogger(ProjectActivityDaoImpl.class);
	
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
		logger.info("------------ProjectActivityDaoImpl delete method start------------");
		boolean isSuccess=true;
		try {
			List <UserActivity> timesheet = checkTimesheetForID(id);
			if(timesheet.size()>0){
				System.out.println("Activity cannot be deleted, as timesheet for this activity is filled. ");
				return false;
			}else{
			 Query query = ((Session) getEntityManager().getDelegate()).getNamedQuery(CustomActivity.DELETE_CUSTOM_ACTIVITY_BASED_ON_ID).setInteger("id", id);
			 int totalRowsDeleted = query.executeUpdate();
			}
		} catch (HibernateException e) {
			isSuccess = false;
			e.printStackTrace();
			logger.error("Exception Occurred while deleting Activity "+id+e.getMessage());
			 throw e;
		}  finally {
		}
		logger.info("------------ProjectActivityDaoImpl delete method end------------");
		return isSuccess;
	}

	
	@SuppressWarnings("unchecked")
	public List<CustomActivity> findAll() {
		logger.info("--------ProjectActivityDaoImpl findAll method start-------");
		List<CustomActivity> activityList = null;
		try {
			Session session=(Session) getEntityManager().getDelegate();
			if(null==session) throw new RuntimeException("Session Object Cannot be null");
			activityList = session.createCriteria(CustomActivity.class).add(Restrictions.or(Restrictions.like("type","Default"),Restrictions.like("type","SEPG"))).addOrder(Order.asc("activityName")).list(); 
		}catch (HibernateException e) {
	         e.printStackTrace(); 
	         logger.error("EXCEPTION CAUSED IN GETTING ACTIVITY LIST " + e.getMessage());
	         throw e;
	    } finally {
			if (null == activityList) {
				activityList = new ArrayList<CustomActivity>();
			}
		}
		logger.info("------------ProjectActivityDaoImpl findAll method end------------");
		return activityList ;
	}

	public List<CustomActivity> findByEntries(int firstResult, int sizeNo) {
		// TODO Auto-generated method stub
		return null;
	}

	public long countTotal() {
		// TODO Auto-generated method stub
		return 0;
	}



	public List<CustomActivity> findById(int id,List<Integer> activityIds) {
		List<CustomActivity> activity = null;
		logger.info("--------ProjectActivityDaoImpl findById method start-------");
		Session session=(Session) getEntityManager().getDelegate();
		try {
			activity = new ArrayList<CustomActivity>();
			Criteria criteria =  session.createCriteria(CustomActivity.class).add(Restrictions.eq("projectId.id", id)); 
			if(activityIds!=null && activityIds.size()>0)
			criteria.add(Restrictions.not(Restrictions.in("id", activityIds)));
			activity = criteria.list();
			
		} catch (HibernateException e) {
	         e.printStackTrace(); 
	         logger.error("EXCEPTION CAUSED IN GETTING ACTIVITY By Id " +id+ e.getMessage());
	         throw e;	
	     }finally {
	         //session.close(); 
	      }
		logger.info("------ProjectActivityDaoImpl findById method end-----");
		return activity ;
	}

	public boolean saveOrupdate(CustomActivity activity) {
		// TODO Auto-generated method stub
if(null == activity) return false;
		
		Session currentSession = (Session) getEntityManager().getDelegate();
		boolean isSuccess=true;
		try {
			if(activity.getId()==0)
			{
				
				activity.setCreatedId( UserUtil.getUserContextDetails().getUserName());
				activity.setCreationTimestamp(new Date());
				
			}
			
			//commented below
	//	 activity.setLastUpdatedId( UserUtil.getUserContextDetails().getUserName());
		//	Criteria criteria=currentSession.createCriteria(CustomActivity.class);
//	*		criteria.add(Restrictions.eq("activityName", activity.getActivityName()));
		//	criteria.add(Restrictions.and(Restrictions.eq("activityName", activity.getActivityName()), Restrictions.eq("projectId.id", activity.getProjectId().getId())));
			//* added for task #2017 by Isha
			//*criteria.add(Restrictions.eq("projectId", activity.getProjectId()));
			//*............//
		//	List<CustomActivity> list=criteria.list();
		//	if(list.size()==0){
				currentSession.saveOrUpdate(activity);
		//	}else {
				//isSuccess = false;
		//	}
			
		} catch (HibernateException e) {
			isSuccess = false;
			e.printStackTrace();
			logger.error("Exception Occurred while saving Activity "+activity.getActivityName()+e.getMessage());
			 throw e;
		}   finally {
//			currentSession.close();
		}
		logger.info("------ProjectActivityDaoImpl saveOrUpate method end-----");
		return isSuccess;
	}

	public boolean save(List<CustomActivity> list) {
		// TODO Auto-generated method stub
		Session currentSession = (Session) getEntityManager().getDelegate();
		if (list != null && list.size() > 0) {
			for (CustomActivity confg : list) {
			 	currentSession.merge(confg);
				
		}
		
	}
		return true;
	}

	public List<Integer> findCustomIds(List<Integer> defaultids, String type,List<Integer> customIds, Integer projectId) {
		// TODO Auto-generated method stub
		Session currentSession = (Session) getEntityManager().getDelegate();
		Criteria projectActivityCriteria= null;
		if(customIds!=null && customIds.size()>0){
			projectActivityCriteria = currentSession.createCriteria(AllProjectActivities.class).setProjection(Projections.property("customActivityid.id"));
			projectActivityCriteria.add(Restrictions.or(Restrictions.like("activityType",type)));
			projectActivityCriteria.add(Restrictions.not(Restrictions.in("customActivityid.id", customIds)));
			projectActivityCriteria.add(Restrictions.or(Restrictions.eq("projectid.id", projectId)));
		}else{
			projectActivityCriteria = currentSession.createCriteria(AllProjectActivities.class).setProjection(Projections.property("defaultActivityid.id"));
			projectActivityCriteria.add(Restrictions.or(Restrictions.like("activityType",type)));
			projectActivityCriteria.add(Restrictions.not(Restrictions.in("defaultActivityid.id", defaultids)));
			projectActivityCriteria.add(Restrictions.or(Restrictions.eq("projectid.id", projectId)));
		}
		
		return projectActivityCriteria.list();
	}


	public List<Integer> findSEPGActivities(Integer project, List<String> sepglist, List<Integer> sepg) {
		// TODO Auto-generated method stub
		List<Integer> activity = null;
		
		logger.info("--------ProjectActivityDaoImpl findById method start-------");
		Session currentSession = (Session) getEntityManager().getDelegate();
		org.hibernate.Query projects= null;
		try {
			if(sepg!=null && sepg.size()>0){
				 projects = currentSession.createSQLQuery("SELECT ac.id "
					+ " FROM PROJECT p "
					+ " INNER JOIN ENGAGEMENTMODEL pem"
					+ " ON p.engagement_model_id = pem.id"
					+ " INNER JOIN phaseengactvty_mapping  pp"
					+ " ON pem.id = pp.engmt_id"
					+ " INNER JOIN Activity ac"
					+ " ON pp.acty_id = ac.id WHERE p.id=:project and pp.acty_id NOT IN (:sepg)");
		  projects.setParameter("project", project);
		  projects.setParameterList("sepg", sepg);
		  }else{
			  projects = currentSession.createSQLQuery("SELECT ac.id "
						+ " FROM PROJECT p "
						+ " INNER JOIN ENGAGEMENTMODEL pem"
						+ " ON p.engagement_model_id = pem.id"
						+ " INNER JOIN phaseengactvty_mapping  pp"
						+ " ON pem.id = pp.engmt_id"
						+ " INNER JOIN Activity ac"
						+ " ON pp.acty_id = ac.id WHERE p.id=:project");
			  projects.setParameter("project", project);
			  
		  }
		  activity=	projects.list();
			
		} catch (HibernateException e) {
	         e.printStackTrace(); 
	         logger.error("EXCEPTION CAUSED IN GETTING ACTIVITY By Id "+ e.getMessage());
	         throw e;
	     }finally {
	         //session.close(); 
	      }
		logger.info("------ProjectActivityDaoImpl findById method end-----");
		return activity ;
	}


	public CustomActivity findByActivityId(int activityId,int projectId) {
		// TODO Auto-generated method stub
		CustomActivity activity = null;
		logger.info("--------ProjectActivityDaoImpl findById method start-------");
		Session session=(Session) getEntityManager().getDelegate();
		try {
		 
			Criteria criteria =  session.createCriteria(CustomActivity.class).add(Restrictions.eq("projectId.id", projectId)).add(Restrictions.eq("id", activityId)); 
			List list =criteria.list();
			if(list!=null && list.size()>0)
			activity = (CustomActivity) criteria.list().get(0);
			
		} catch (HibernateException e) {
	         e.printStackTrace(); 
	         logger.error("EXCEPTION CAUSED IN GETTING ACTIVITY By Id "  + e.getMessage());
	         throw e;
	     }finally {
	         //session.close(); 
	      }
		logger.info("------ProjectActivityDaoImpl findById method end-----");
		return activity ;
	}


	public boolean updateDefaultToInActive(List<Integer> ids,List<Integer> customIds,  Integer projectId) {
		// TODO Auto-generated method stub
		logger.info("------------ProjectActivityDaoImpl delete method start------------");
		boolean isSuccess=true;
		try {
			Session session=(Session) getEntityManager().getDelegate();
			if(ids.size()>0){
			 Query query =session.createQuery("update AllProjectActivities set deactiveFlag = 0 where defaultActivityid.id in(:ids) and projectid.id = :projectId");
			 query.setParameterList("ids", ids);
			 query.setParameter("projectId", projectId);
			 int totalRowsDeleted = query.executeUpdate();
			 System.out.println("Total Rows Deleted::" + totalRowsDeleted);
			}
			if(customIds.size()>0){
			Query query = session.createQuery("update AllProjectActivities set deactiveFlag = 1 where defaultActivityid.id in(:customIds) and projectid.id = :projectId");
			 query.setParameterList("customIds", customIds);
			 query.setParameter("projectId", projectId);
			 
			 int totalRowsUpdated = query.executeUpdate();
			 System.out.println("Total Rows Updated::" + totalRowsUpdated);
			}
			
		} catch (HibernateException e) {
			isSuccess = false;
			e.printStackTrace();
			logger.error("Exception Occurred while deleting Activity " +e.getMessage());
			 throw e;
		}  finally {
//			currentSession.close();
		}
		logger.info("------------ProjectActivityDaoImpl delete method end------------");
		return isSuccess;
	}


	public boolean updateCustomToInActive(List<Integer> ids,List<Integer> customIds,  Integer projectId) {
		// TODO Auto-generated method stub
		logger.info("------------ProjectActivityDaoImpl delete method start------------");
		boolean isSuccess=true;
		try {
			Session session=(Session) getEntityManager().getDelegate();
			if(ids.size()>0){
			 Query query =session.createQuery("update AllProjectActivities set deactiveFlag = 0 where customActivityid.id in(:ids) and projectid.id = :projectId");
			 query.setParameterList("ids", ids);
			 query.setParameter("projectId", projectId);
			 int totalRowsDeleted = query.executeUpdate();
			 System.out.println("Total Rows Deleted::" + totalRowsDeleted);
			}
			if(customIds.size()>0){
			 Query query = session.createQuery("update AllProjectActivities set deactiveFlag = 1 where customActivityid.id in(:customIds) and projectid.id = :projectId");
			 query.setParameterList("customIds", customIds);
			 query.setParameter("projectId", projectId);
			
			 int totalRowsUpdated = query.executeUpdate();
			 System.out.println("Total Rows Updated::" + totalRowsUpdated);
			}
			
		} catch (HibernateException e) {
			isSuccess = false;
			e.printStackTrace();
			logger.error("Exception Occurred while deleting Activity "+e.getMessage());
			 throw e;
		}  finally {
//			currentSession.close();
		}
		logger.info("------------ProjectActivityDaoImpl delete method end------------");
		return isSuccess;
	}
	 
	public boolean saveActivity(List<AllProjectActivities> list){
		Session currentSession = (Session) getEntityManager().getDelegate();
		if (list != null && list.size() > 0) {
			for (AllProjectActivities confg : list) {
			 	currentSession.saveOrUpdate(confg);
				
		}
		}
	return true;
		}
	
	public List<Integer> projectTypeActivities (String type, int projectId){
		
		Session session=(Session) getEntityManager().getDelegate();
		Criteria criteria =  session.createCriteria(AllProjectActivities.class).add(Restrictions.eq("projectid.id", projectId)).add(Restrictions.eq("activityType", type));
		criteria.add(Restrictions.or(Restrictions.like("deactiveFlag",1)));
		if(type=="Custom"){
			criteria.createAlias("customActivityid","id" );
			criteria.setProjection(Projections.property("customActivityid.id"));
		}else{
		criteria.createAlias("defaultActivityid","id" );
		criteria.setProjection(Projections.property("defaultActivityid.id"));
		}
		List<Integer> list =criteria.list();
		 
		
		
		return list;
		
	}
	
	public List<CustomActivity> findBySelectedCustomId(List<Integer> id) {
		List<CustomActivity> activity = null;
		logger.info("--------ProjectActivityDaoImpl findById method start-------");
		Session session=(Session) getEntityManager().getDelegate();
		try {
			activity = new ArrayList<CustomActivity>();
			Criteria criteria =  session.createCriteria(CustomActivity.class).add(Restrictions.in("id", id)); 
			activity = criteria.list();
			
		} catch (HibernateException e) {
	         e.printStackTrace(); 
	         logger.error("EXCEPTION CAUSED IN GETTING ACTIVITY By Id " +id+ e.getMessage());
	         throw e;
	     }finally {
	         //session.close(); 
	      }
		logger.info("------ProjectActivityDaoImpl findById method end-----");
		return activity ;
	}
	
	public List<AllProjectActivities> findBySelectedActivityId(String [] activityList,int projectId,String [] customactivitiesList ){
		
		List<AllProjectActivities> activity = null;
		logger.info("--------ProjectActivityDaoImpl findById method start-------");
		Session session=(Session) getEntityManager().getDelegate();
		Query query=null;
		 int i=0;
	
		try {
			activity = new ArrayList<AllProjectActivities>();
			if(customactivitiesList!=null){
				Integer[] customArray=new Integer[customactivitiesList.length];
			    for(String str:customactivitiesList){
			    	customArray[i]=Integer.parseInt(str);
			          i++;
			          }
				query =session.createQuery(" FROM AllProjectActivities WHERE customActivityid.id  IN  :customArray AND projectid.id =:projectId ");
				query.setParameterList("customArray", customArray);
			}else{
				Integer[] intarray=new Integer[activityList.length];
			    for(String str:activityList){
			           intarray[i]=Integer.parseInt(str);
			          i++;
			          }
				query =session.createQuery(" FROM AllProjectActivities WHERE defaultActivityid.id  IN  :intarray AND projectid.id =:projectId ");
				query.setParameterList("intarray", intarray);
			}
			query.setParameter("projectId", projectId);
			activity = query.list();
			
		} catch (HibernateException e) {
	         e.printStackTrace(); 
	         logger.error("EXCEPTION CAUSED IN GETTING ACTIVITY By Id " +activityList+ e.getMessage());
	         throw e;
	     }finally {
	       }
		logger.info("------ProjectActivityDaoImpl findById method end-----");
		
		return activity;
		
	}
	
	public List<UserActivity> checkTimesheetForID(Integer id){
		logger.info("------ProjectActivityDaoImpl checkTimesheetForID method start-----");
		Session session = (Session) getEntityManager().getDelegate();
		Criteria criteria = session.createCriteria(UserActivity.class).add(Restrictions.eq("customActivityId.id", id));
		logger.info("------ProjectActivityDaoImpl checkTimesheetForID method end-----");
		return criteria.list();
	}


	public List<UserActivity> findCustomIDsInTimsheet() {
		logger.info("------ProjectActivityDaoImpl findCustomIDsInTimsheet method start-----");
		Session session = (Session) getEntityManager().getDelegate();
		Criteria criteria =  session.createCriteria(UserActivity.class).setProjection(Projections.distinct(Projections.property("customActivityId.id"))); 
		logger.info("------ProjectActivityDaoImpl findCustomIDsInTimsheet method end-----");
		return criteria.list();
	}

}
