/**
 * 
 */
package org.yash.rms.dao.impl;

import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.yash.rms.dao.UserActivityDao;
import org.yash.rms.domain.Project;
import org.yash.rms.domain.Resource;
import org.yash.rms.domain.ResourceAllocation;
import org.yash.rms.domain.UserActivity;
import org.yash.rms.domain.UserActivityView;
import org.yash.rms.domain.UserTimeSheet;
import org.yash.rms.domain.UserTimeSheetDetail;
import org.yash.rms.dto.UserContextDetails;
import org.yash.rms.util.Constants;
import org.yash.rms.util.GeneralFunction;
import org.yash.rms.util.UserUtil;

/**
 * @author arpan.badjatiya
 * 
 */
@Repository("UserActivityDao")
@Transactional
public class UserActivityDaoImpl implements UserActivityDao {

	private static final Logger logger = LoggerFactory.getLogger(UserActivityDaoImpl.class);

	
	@PersistenceContext(name = "mysql", type = PersistenceContextType.TRANSACTION)
	private EntityManager entityManager;
	
	
	public EntityManager getEntityManager() {
		return entityManager;
	}
	
	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	@Autowired
	private UserUtil userUtill;

	public boolean delete(int id) {
		logger.info("------------UserActivityDaoImpl delete method start------------");
		boolean isSuccess = false;
		try {
			logger.debug("DELETE USER TIME SHEET FOR ID :: " + id);
			Session session = (Session) getEntityManager().getDelegate();
			boolean deleteUserTimeSheetDetailById = deleteUserTimeSheetDetailById(id);
			if (deleteUserTimeSheetDetailById) {
				isSuccess = true;
			}
			Query deleteQuery = session.getNamedQuery(UserTimeSheet.DELETE_USER_ACTIVITY);
			deleteQuery.setParameter("id", new BigInteger("" + id));
			int executeUpdate = deleteQuery.executeUpdate();
			if (executeUpdate > 0) {
				isSuccess = true;
			}
		} catch (HibernateException e) {
			isSuccess = false;
			logger.error("HibernateException occured in delete method at DAO layer:-" + e);
			logger.info("HibernateException occured in delete method at DAO layer:-" + e);
			throw e;
		} catch (Exception ex) {
			isSuccess = false;
			ex.printStackTrace();
			logger.error("HibernateException occured in delete method at DAO layer:-" + ex);
			logger.info("HibernateException occured in delete method at DAO layer:-" + ex);
		} finally {
			// currentSession.close();
		}
		logger.info("------------UserActivityDaoImpl delete method end------------");
		return isSuccess;
	}

	public boolean saveOrupdate(UserActivity userActivity) {
		logger.info("------------UserActivityDaoImpl saveOrupdate method start------------");
		if (null == userActivity)
			return false;

		Session currentSession = (Session) getEntityManager().getDelegate();
		boolean isSuccess = true;
		try {

			currentSession.saveOrUpdate(userActivity);
		} catch (HibernateException e) {
			isSuccess = false;
			logger.error("HibernateException occured in delete method at DAO layer:-" + e);
			throw e;
		} catch (Exception ex) {
			isSuccess = false;
			ex.printStackTrace();
			logger.error("HibernateException occured in saveOrupdate method at DAO layer:-" + ex);
			logger.info("HibernateException occured in saveOrupdate method at DAO layer:-" + ex);
		} finally {
			// currentSession.close();
		}
		logger.info("------------UserActivityDaoImpl saveOrupdate method end------------");
		return isSuccess;
	}

	@SuppressWarnings("unchecked")
	public List<UserActivity> findAll() {
		logger.info("------------UserActivityDaoImpl findAll method start------------");
		Session session = (Session) getEntityManager().getDelegate();
		List<UserActivity> userActivities = null;
		try {
			userActivities = session.createCriteria(UserActivity.class).list();
		} catch (HibernateException e) {
			logger.error("HibernateException occured in findAll method at DAO layer:-" + e);
			throw e;
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.info("HibernateException occured in findAll method at DAO layer:-" + ex);
			logger.error("HibernateException occured in findAll method at DAO layer:-" + ex);

		} finally {
			if (null == userActivities) {
				userActivities = new ArrayList<UserActivity>();
			}
		}
		logger.info("------------UserActivityDaoImpl findAll method end------------");
		return userActivities;
	}

	@SuppressWarnings("unchecked")
	public List<UserActivity> findByEntries(int firstResult, int sizeNo) {
		logger.info("------------UserActivityDaoImpl findByEntries method start------------");
		Session session = (Session) getEntityManager().getDelegate();
		List<UserActivity> userActivities = null;
		try {
			userActivities = session.createCriteria(UserActivity.class).setFirstResult(firstResult).setMaxResults(sizeNo).list();
		} catch (HibernateException e) {
			logger.error("HibernateException occured in findByEntries method at DAO layer:-" + e);
			logger.info("HibernateException occured in findByEntries method at DAO layer:-" + e);
			throw e;
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("HibernateException occured in findByEntries method at DAO layer:-" + ex);
			logger.info("HibernateException occured in findByEntries method at DAO layer:-" + ex);
		} finally {
			if (null == userActivities) {
				userActivities = new ArrayList<UserActivity>();
			}
		}
		logger.info("------------UserActivityDaoImpl findByEntries method end------------");
		return userActivities;
	}

	public long countTotal() {
		logger.info("------------UserActivityDaoImpl countTotal method start------------");
		Session session = (Session) getEntityManager().getDelegate();
		long size = 0;
		try {
			size = session.createCriteria(UserActivity.class).list().size();
		} catch (HibernateException e) {
			logger.error("HibernateException occured in countTotal method at DAO layer:-" + e);
			logger.info("HibernateException occured in countTotal method at DAO layer:-" + e);
			throw e;
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("HibernateException occured in countTotal method at DAO layer:-" + ex);
			logger.info("HibernateException occured in countTotal method at DAO layer:-" + ex);
		} finally {
		}
		logger.info("------------UserActivityDaoImpl countTotal method end------------");
		return size;
	}

	@SuppressWarnings("unchecked")
	public List<UserActivityView> getUserActivityForEmployeeBetweenDate(Integer employeeId, Date minWeekStartDate, Date maxWeekStartDate) {
		logger.info("------------UserActivityDaoImpl getUserActivityForEmployeeBetweenDate method start------------");
		List<UserActivityView> list = null;
		try {

			Query namedQuery = ((Session) getEntityManager().getDelegate()).getNamedQuery(UserActivityView.GET_USER_ACTIVITY_VIEW_BASED_ON_EMPLOYEID_AND_WEEK_START_DATE);
			namedQuery.setParameter("minWeekStartDate", minWeekStartDate);
			namedQuery.setParameter("maxWeekStartDate", maxWeekStartDate);
			namedQuery.setParameter("employeeId", employeeId);
			list = namedQuery.list();

		} catch (HibernateException e) {
			logger.error("HibernateException occured in getUserActivityForEmployeeBetweenDate method at DAO layer:-" + e);
			logger.info("HibernateException occured in getUserActivityForEmployeeBetweenDate method at DAO layer:-" + e);
			throw e;
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("ERROR OCCURRED IN FETCHING RECORDS BASED ON EMPLOYEE ID :" + employeeId + " AND WEEK START DATE BETWEEN " + minWeekStartDate + " AND " + maxWeekStartDate);
			logger.info("ERROR OCCURRED IN FETCHING RECORDS BASED ON EMPLOYEE ID :" + employeeId + " AND WEEK START DATE BETWEEN " + minWeekStartDate + " AND " + maxWeekStartDate);
		} finally {
			if (null == list) {
				list = new ArrayList<UserActivityView>();
			}
		}
		logger.info("------------UserActivityDaoImpl getUserActivityForEmployeeBetweenDate method end------------");
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<UserActivity> findUserActivitysByWeekStartDateBetweenAndEmployeeId(Date minWeekStartDate, Date maxWeekStartDate, Integer employeeId) {
		logger.info("------------UserActivityDaoImpl findUserActivitysByWeekStartDateBetweenAndEmployeeId method start------------");
		List<UserActivity> userActivities = null;

		try {
			if (minWeekStartDate == null)
				throw new IllegalArgumentException("The minWeekStartDate argument is required");
			if (maxWeekStartDate == null)
				throw new IllegalArgumentException("The maxWeekStartDate argument is required");
			if (employeeId == 0)
				throw new IllegalArgumentException("The employeeId argument is required");

			Criteria criteria = ((Session) getEntityManager().getDelegate()).createCriteria(UserActivity.class);
			criteria.add(Restrictions.between("weekEndDate", minWeekStartDate, maxWeekStartDate));
			criteria.add(Restrictions.eq("employeeId.employeeId", employeeId));
			userActivities = criteria.list();
			// Below loop is to fetch the objects required for this operation
			// which are null due to lazy
			// loading time
			/*
			 * for(UserActivity userActivity:userActivities){
			 * userActivity.getResourceAllocId
			 * ().getProjectId().getProjectName();
			 * userActivity.getActivityId().getActivityName(); }
			 */
		} catch (HibernateException e) {
			logger.error("HibernateException occured in findUserActivitysByWeekStartDateBetweenAndEmployeeId method at DAO layer:-" + e);
			logger.info("HibernateException occured in findUserActivitysByWeekStartDateBetweenAndEmployeeId method at DAO layer:-" + e);
			throw e;
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("ËXCEPTION OCCURRED IN GETTING USER ACTIVITIES LIST DUE TO " + ex.getMessage());
			logger.info("ËXCEPTION OCCURRED IN GETTING USER ACTIVITIES LIST DUE TO " + ex.getMessage());
		} finally {
			if (null == userActivities) {
				userActivities = new ArrayList<UserActivity>();
			}
		}
		logger.info("------------UserActivityDaoImpl findUserActivitysByWeekStartDateBetweenAndEmployeeId method end------------");
		return userActivities;
	}

	public List<UserActivity> findUserActivitysByResourceAllocId(ResourceAllocation resourceAllocId) {
		logger.info("------------UserActivityDaoImpl findUserActivitysByResourceAllocId method start------------");
		if (resourceAllocId == null)
			throw new IllegalArgumentException("The resourceAllocId argument is required");

		List<UserActivity> userActivities;
		try {
			Query q = ((Session) getEntityManager().getDelegate()).createQuery("FROM UserActivity WHERE resourceAllocId = :resourceAllocId");
			q.setParameter("resourceAllocId", resourceAllocId);
			userActivities = q.list();
		} catch (HibernateException e) {
			logger.error("HibernateException occured in findUserActivitysByResourceAllocId method at DAO layer:-" + e);
			logger.info("HibernateException occured in findUserActivitysByResourceAllocId method at DAO layer:-" + e);
			throw e;
		}
		logger.info("------------UserActivityDaoImpl findUserActivitysByResourceAllocId method end------------");
		return userActivities;
	}

	@SuppressWarnings("unchecked")
	public List<UserActivity> findUserActivitysByResourceAllocIdAndWeekStartDateEquals(ResourceAllocation resourceAllocId, Date weekStartDate) {
		logger.info("------------UserActivityDaoImpl findUserActivitysByResourceAllocIdAndWeekStartDateEquals method start------------");
		List<UserActivity> userActivities = null;

		try {
			if (resourceAllocId == null)
				throw new IllegalArgumentException("The resourceAllocId argument is required");
			if (weekStartDate == null)
				throw new IllegalArgumentException("The weekStartDate argument is required");

			Criteria criteria = ((Session) getEntityManager().getDelegate()).createCriteria(UserActivity.class);
			criteria.add(Restrictions.conjunction());
			criteria.add(Restrictions.eq("resourceAllocId", resourceAllocId));
			criteria.add(Restrictions.eq("weekStartDate", weekStartDate));
			userActivities = criteria.list();
		} catch (HibernateException e) {
			logger.error("HibernateException occured in findUserActivitysByResourceAllocIdAndWeekStartDateEquals method at DAO layer:-" + e);
			logger.info("HibernateException occured in findUserActivitysByResourceAllocIdAndWeekStartDateEquals method at DAO layer:-" + e);
			throw e;
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("Exception occured in findUserActivitysByResourceAllocIdAndWeekStartDateEquals method at DAO layer:-" + ex.getMessage());
			logger.info("Exception occured in findUserActivitysByResourceAllocIdAndWeekStartDateEquals method at DAO layer:-" + ex.getMessage());
		} finally {
			if (null == userActivities) {
				userActivities = new ArrayList<UserActivity>();
			}
		}
		logger.info("------------UserActivityDaoImpl findUserActivitysByResourceAllocIdAndWeekStartDateEquals method end------------");
		return userActivities;
	}

	public UserActivity findById(Integer id) {
		logger.info("------------UserActivityDaoImpl findById method start------------");
		UserActivity userActivity = new UserActivity();
		try {
			Session session = (Session) getEntityManager().getDelegate();
			userActivity = (UserActivity) session.createCriteria(UserActivity.class).add(Restrictions.eq("id", id)).uniqueResult();

		} catch (HibernateException e) {
			logger.error("HibernateException occured in findById method at DAO layer:-" + e);
			logger.info("HibernateException occured in findById method at DAO layer:-" + e);
			throw e;
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("COULD NOT FIND USER ACIVITY FOR ID :" + id);
			logger.error(ex.getMessage());
		} finally {
		}

		logger.info("------------UserActivityDaoImpl findById method end------------");
		return userActivity;
	}

  @SuppressWarnings("unchecked")
  public List<UserActivity> findUserActivitysByEmployeeId(Character timeSheetStatus, Integer loggedInUserId, List<Integer> employeeIdList) {
    
	  logger.info("------------UserActivityDaoImpl findUserActivitysByEmployeeId method start------------");
    
	  Session session = (Session) getEntityManager().getDelegate();
    
	  List<UserActivity> userActivities = null;
	  List<Integer> projectIds = null;
	  
	  try {
      
		  Criteria criteria = session.createCriteria(Project.class);
		  Criterion c1=Restrictions.or(Restrictions.eq("offshoreDelMgr.employeeId", loggedInUserId), (Restrictions.eq("deliveryMgr.employeeId", loggedInUserId)));
		  criteria.add(c1).setProjection(Projections.property("id"));
		  
		  projectIds = criteria.list();
      
		  criteria = session.createCriteria(UserActivity.class);
		  	criteria.add(Restrictions.in("employeeId.employeeId", employeeIdList));
		  	criteria.createAlias("resourceAllocId", "re");
		  	criteria.setFetchMode("resourceAllocId", FetchMode.JOIN);
      
		  if (timeSheetStatus.compareTo('P') == 0 || timeSheetStatus.compareTo('R') == 0 || timeSheetStatus.compareTo('A') == 0 || timeSheetStatus.compareTo('N') == 0) {
			  criteria.add(Restrictions.eq("status", timeSheetStatus));
			  criteria.add(Restrictions.isNotNull("weekEndDate"));
		  }
      
		  else if(timeSheetStatus.compareTo('M') == 0) {
			  criteria.add(Restrictions.isNotNull("weekEndDate"));
			  criteria.add(Restrictions.in("re.projectId.id", projectIds));
		  } 
      
      userActivities = criteria.list();
      
    } catch (HibernateException hibernateException) {
      
    	logger.error("HibernateException occured in findUserActivitysByEmployeeId method at DAO layer:-" + hibernateException);
      
    	throw hibernateException;
    	
    } catch (Exception ex) {
      ex.printStackTrace();
      
    } finally {
      
    	if (null == userActivities) {
    		userActivities = new ArrayList<UserActivity>();
    	}
    }
	  	logger.info("------------UserActivityDaoImpl findUserActivitysByEmployeeId method end------------");
    
    	return userActivities;
  	}

	@SuppressWarnings("unchecked")
	public List<UserActivity> findUserActivitysByEmployeeIdHyper(Integer employeeId, Character timeSheetStatus, String weekEndDate) {
		logger.info("------------UserActivityDaoImpl findUserActivitysByEmployeeId method start------------");
		Session session = (Session) getEntityManager().getDelegate();
		List<UserActivity> userActivities = null;
		try {
			Criteria criteria = session.createCriteria(UserActivity.class).add(Restrictions.eq("employeeId.employeeId", employeeId));
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			Date date = formatter.parse(weekEndDate);
			criteria.add(Restrictions.eq("weekEndDate", date));
			if (timeSheetStatus.compareTo('P') == 0 || timeSheetStatus.compareTo('R') == 0 || timeSheetStatus.compareTo('A') == 0 || timeSheetStatus.compareTo('N') == 0) {

				// criteria.add(Restrictions.eq("weekEndDate",date));
				criteria.add(Restrictions.eq("status", timeSheetStatus));
				// criteria.add(Restrictions.eq("weekEndDate",formatter.format(date)));

				// Date a=new Date(06,25,2016);
				// Date b=new Date(2016,6,25);

				// String b="2015/06/25";
				// criteria.add(Restrictions.eq("weekEndDate",b));
				// criteria.add(Restrictions.ge("resourceAllocId.projectId.projectEndDate",
				// new Date()));
			}
			userActivities = criteria.list();
		} catch (HibernateException e) {
			logger.error("HibernateException occured in findUserActivitysByEmployeeId method at DAO layer:-" + e);
			logger.info("HibernateException occured in findUserActivitysByEmployeeId method at DAO layer:-" + e);
			throw e;
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("HibernateException occured in findUserActivitysByEmployeeId method at DAO layer:-" + ex);
			logger.info("HibernateException occured in findUserActivitysByEmployeeId method at DAO layer:-" + ex);
		} finally {
			if (null == userActivities) {
				userActivities = new ArrayList<UserActivity>();
			}
		}
		logger.info("------------UserActivityDaoImpl findUserActivitysByEmployeeId method end------------");
		return userActivities;
	}

	public UserTimeSheetDetail findUserTimeSheetDetailBasedOnIdAndDate(Integer id, Date weekStartDate) {
		logger.info("------------UserActivityDaoImpl findUserTimeSheetDetailBasedOnIdAndDate method start------------");
		Session session = (Session) getEntityManager().getDelegate();
		Object obj = null;
		try {
			obj = session.createCriteria(UserTimeSheetDetail.class).add(Restrictions.eq("timeSheetId.id", BigInteger.valueOf(id.longValue()))).add((Restrictions.eq("weekStartDate", weekStartDate)))
					.uniqueResult();
		} catch (HibernateException e) {
			logger.error("HibernateException occured in findUserTimeSheetDetailBasedOnIdAndDate method at DAO layer:-" + e);
			logger.info("HibernateException occured in findUserTimeSheetDetailBasedOnIdAndDate method at DAO layer:-" + e);
			throw e;
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("HibernateException occured in findUserTimeSheetDetailBasedOnIdAndDate method at DAO layer:-" + ex);
			logger.info("HibernateException occured in findUserTimeSheetDetailBasedOnIdAndDate method at DAO layer:-" + ex);
		} finally {
		}
		logger.info("------------UserActivityDaoImpl findUserTimeSheetDetailBasedOnIdAndDate method end------------");
		return null != obj ? ((UserTimeSheetDetail) obj) : null;

	}

	@Transactional
	public boolean saveOrupdate(UserTimeSheet userTimeSheet) {
		logger.info("------------UserActivityDaoImpl saveOrupdate method start------------");
		if (null == userTimeSheet)
			return false;

		Session currentSession = (Session) getEntityManager().getDelegate();
		boolean isSuccess = true;
		try {

			currentSession.saveOrUpdate(userTimeSheet);
		} catch (HibernateException e) {
			isSuccess = false;
			logger.error("HibernateException occured in saveOrupdate method at DAO layer:-" + e);
			logger.info("HibernateException occured in saveOrupdate method at DAO layer:-" + e);
			throw e;
		} catch (Exception ex) {
			isSuccess = false;
			ex.printStackTrace();
			logger.error("HibernateException occured in saveOrupdate method at DAO layer:-" + ex);
			logger.info("HibernateException occured in saveOrupdate method at DAO layer:-" + ex);
		} finally {
		}

		logger.info("------------UserActivityDaoImpl saveOrupdate method end------------");
		return isSuccess;
	}

	public boolean deleteUserTimeSheetDetailById(Integer id) {
		logger.info("------------UserActivityDaoImpl deleteUserTimeSheetDetailById method start------------");
		boolean isSuccess = false;
		try {
			logger.debug("DELETE USER TIME SHEET DETAIL FOR ID :: " + id);
			Session session = (Session) getEntityManager().getDelegate();
			Query deletUserTimeSheetDetail = session.getNamedQuery(UserTimeSheet.DELETE_USER_ACTIVITY_DETAIL);
			UserTimeSheet userTimeSheet = new UserTimeSheet();
			userTimeSheet.setId(new BigInteger("" + id));
			deletUserTimeSheetDetail.setParameter("id", userTimeSheet);
			int executeDelete = deletUserTimeSheetDetail.executeUpdate();
			logger.debug("NO. OF ROWS DELETED FOR USER TIME SHEET DETAIL:: " + executeDelete);
			isSuccess = executeDelete > 0 ? true : false;
			logger.debug("IS USER TIME SHEET DETAIL DELETE SUCCESSFULL :: " + isSuccess);
		} catch (HibernateException e) {
			isSuccess = false;
			e.printStackTrace();
			logger.error("COULD NOT DELETE USER TIME SHEET DETAIL DUE TO :: " + e.getMessage());
			logger.info("COULD NOT DELETE USER TIME SHEET DETAIL DUE TO :: " + e.getMessage());
			throw e;
		} catch (Exception ex) {
			isSuccess = false;
			ex.printStackTrace();
			logger.error("COULD NOT DELETE USER TIME SHEET DETAIL DUE TO :: " + ex.getMessage());
			logger.error("COULD NOT DELETE USER TIME SHEET DETAIL DUE TO :: " + ex.getMessage());
		} finally {
		}
		logger.info("------------UserActivityDaoImpl deleteUserTimeSheetDetailById method end------------");
		return isSuccess;
	}

	public void saveUserActivityStatus(Integer id, Character status, String userName, String remarks, int approvedBy, boolean fromRestService) {
		logger.info("------------UserActivityDaoImpl saveUserActivityStatus method start------------");
		try {
			Session session = (Session) getEntityManager().getDelegate();
			Criteria criteria = session.createCriteria(UserTimeSheet.class);
			
			UserTimeSheet userTimeSheet = (UserTimeSheet) criteria.add(Restrictions.eq("id", BigInteger.valueOf(id.longValue()))).uniqueResult();
				userTimeSheet.setStatus(status);
				userTimeSheet.setRejectRemarks(remarks);
				userTimeSheet.setApprovedBy(approvedBy); 
				userTimeSheet.setApprovalPendingFrom(0);
				userTimeSheet.setApproveCode(GeneralFunction.randomString(20));
				userTimeSheet.setRejectCode(GeneralFunction.randomString(20));
				userTimeSheet.setLastupdatedTimestamp(new Date());
				if (fromRestService) {
					userTimeSheet.setLastUpdatedId(userName+"_REST_API");
				} else userTimeSheet.setLastUpdatedId(userName); 
				
			session.merge(userTimeSheet);
		} catch (HibernateException hibernateException) {
			hibernateException.printStackTrace();
			logger.error("HibernateException occured in saveUserActivityStatus method at DAO layer:-" + hibernateException);
			logger.info("HibernateException occured in saveUserActivityStatus method at DAO layer:-" + hibernateException);
			throw hibernateException;
		}

		logger.info("------------UserActivityDaoImpl saveUserActivityStatus method end------------");
	}

	@SuppressWarnings("unchecked")
	public List<Integer> findUserActivitysByStatus(Character timeSheetStatus, String activeOrAll, List<Integer> projectIds, UserContextDetails userContextDetails) {
		logger.info("------------UserActivityDaoImpl findUserActivitysByEmployeeId method start------------");
		Session session = (Session) getEntityManager().getDelegate();
		List<Integer> userActivities = new ArrayList<Integer>();
		Query query = null;
		List<Integer> projectIds1 = null;
		if (activeOrAll.equalsIgnoreCase("active")) {
			query = session.createQuery("Select id FROM Project WHERE (offshoreDelMgr.employeeId=:resourceId or deliveryMgr.employeeId=:resourceId1) AND (projectEndDate >(:currentDate) or projectEndDate IS NULL)");
			query.setParameter("resourceId", userContextDetails.getEmployeeId());
			query.setParameter("resourceId1", userContextDetails.getEmployeeId());
			query.setParameter("currentDate", new Date());
		} else {
			query = session.createQuery("Select id FROM Project WHERE offshoreDelMgr.employeeId=:resourceId or deliveryMgr.employeeId=:resourceId1");
			query.setParameter("resourceId", userContextDetails.getEmployeeId());
			query.setParameter("resourceId1", userContextDetails.getEmployeeId());
		}
		Integer empId= userContextDetails.getEmployeeId();
		projectIds1 = query.list();
		query = null;
		try {
			if (activeOrAll.equalsIgnoreCase("active")) {
				if (Constants.ROLE_MANAGER.equals(userContextDetails.getUserRole()) || Constants.ROLE_SEPG_USER.equals(userContextDetails.getUserRole())
						|| Constants.ROLE_BEHALF_MANAGER.equals(userContextDetails.getUserRole()) || Constants.ROLE_DEL_MANAGER.equals(userContextDetails.getUserRole())) {
					if (projectIds != null && projectIds.size() > 0) {
						if (timeSheetStatus.compareTo('P') == 0) {
							query = session
									.createQuery("Select ua.resourceAllocId.employeeId.employeeId ,count(ua.resourceAllocId)  from UserActivity ua, UserTimeSheetDetail uad where (status =(:status) and "
											+ "ua.resourceAllocId.id in(select id from ResourceAllocation where employeeId in(select employeeId from Resource where "
											+ "(releaseDate >(:currentDate) or releaseDate is null) ))and ua.resourceAllocId.projectId.id in(:projectIds)) and ua.id=uad.timeSheetId "
											+ " group by ua.resourceAllocId");
							query.setParameter("status", timeSheetStatus);
							query.setParameterList("projectIds", projectIds); //setting up behalf manager as well with projectIds
							query.setParameter("currentDate", new Date());
							} else if (timeSheetStatus.compareTo('L') == 0) {
							query = session
									.createQuery("Select ua.resourceAllocId.employeeId.employeeId ,count(ua.resourceAllocId) from UserActivity ua, UserTimeSheetDetail uad where "
											+ "(ua.resourceAllocId.id in(select id from ResourceAllocation where employeeId in(select employeeId from Resource where (releaseDate >(:currentDate) or releaseDate is null))) and ua.resourceAllocId.projectId.id in(:projectIds)) and ua.id=uad.timeSheetId "
											+ " group by ua.resourceAllocId");
							query.setParameterList("projectIds", projectIds); //setting up behalf manager as well with projectIds
							query.setParameter("currentDate", new Date());
						}
					}
					if (timeSheetStatus.compareTo('M') == 0) {
						if (projectIds1 != null && (!projectIds1.isEmpty())) {
							query = session
									.createQuery("Select ua.resourceAllocId.employeeId.employeeId ,count(ua.resourceAllocId) from UserActivity ua, UserTimeSheetDetail uad where "
											+ "(ua.resourceAllocId.id in(select id from ResourceAllocation where employeeId in(select employeeId from Resource where (releaseDate >(:currentDate) or releaseDate is null))) and ua.resourceAllocId.projectId.id in(:projectIds)) and ua.id=uad.timeSheetId"
											+ " group by ua.resourceAllocId");
							query.setParameterList("projectIds", projectIds1); //setitng up only offshore/delivery manager wiht projectids
							query.setParameter("currentDate", new Date());
						}
					}

				} else {
					if (timeSheetStatus.compareTo('P') == 0) {
						query = session
								.createQuery("Select ua.resourceAllocId.employeeId.employeeId ,count(ua.resourceAllocId)  from UserActivity ua, UserTimeSheetDetail uad where (ua.status =(:status) and "
										+ "ua.resourceAllocId.id in(select id from ResourceAllocation where employeeId in(select employeeId from Resource where (releaseDate >(:currentDate) or releaseDate is null)))) and ua.id=uad.timeSheetId"
										+ " group by ua.resourceAllocId");
						query.setParameter("status", timeSheetStatus);
						query.setParameter("currentDate", new Date());
					} else if (timeSheetStatus.compareTo('L') == 0) {
						query = session
								.createQuery("Select ua.resourceAllocId.employeeId.employeeId ,count(ua.resourceAllocId) from UserActivity ua, UserTimeSheetDetail uad where "
										+ "(ua.resourceAllocId.id in(select id from ResourceAllocation where employeeId in(select employeeId from Resource where (releaseDate >(:currentDate) or releaseDate is null)))) and ua.id=uad.timeSheetId"
										+ " group by ua.resourceAllocId");
						query.setParameter("currentDate", new Date());
					} else {
						if (projectIds1 != null && (!projectIds1.isEmpty())) {
							query = session
									.createQuery("Select ua.resourceAllocId.employeeId.employeeId ,count(ua.resourceAllocId) from UserActivity ua, UserTimeSheetDetail uad  where (ua.resourceAllocId.projectId.id in(:projectIds) and "
											+ "ua.resourceAllocId.id in(select id from ResourceAllocation where employeeId in(select employeeId from Resource where (releaseDate >(:currentDate) or releaseDate is null)))) and ua.id=uad.timeSheetId "
											+ " group by ua.resourceAllocId");
							query.setParameter("currentDate", new Date());
							query.setParameterList("projectIds", projectIds1);
						}
					}
				}
			} else if (activeOrAll.equalsIgnoreCase("all")) {
				if (Constants.ROLE_MANAGER.equals(userContextDetails.getUserRole()) || Constants.ROLE_SEPG_USER.equals(userContextDetails.getUserRole())
						|| Constants.ROLE_BEHALF_MANAGER.equals(userContextDetails.getUserRole()) || Constants.ROLE_DEL_MANAGER.equals(userContextDetails.getUserRole())) {
					if (projectIds != null && projectIds.size() > 0) {
						if (timeSheetStatus.compareTo('P') == 0) {
							query = session.createQuery("Select ua.resourceAllocId.employeeId.employeeId ,count(ua.resourceAllocId) from UserActivity ua, UserTimeSheetDetail uad where (ua.status =(:status) and "
									+ "ua.resourceAllocId.id in(select id from ResourceAllocation where employeeId in(select employeeId from Resource)) and ua.resourceAllocId.projectId.id in(:projectIds)) and ua.id =uad.timeSheetId "
									+ " group by ua.resourceAllocId");
							query.setParameter("status", timeSheetStatus);
							query.setParameterList("projectIds", projectIds);
						} else if (timeSheetStatus.compareTo('L') == 0) {
							query = session.createQuery("Select ua.resourceAllocId.employeeId.employeeId ,count(ua.resourceAllocId) from UserActivity ua, UserTimeSheetDetail uad  where (ua.resourceAllocId.projectId.id in(:projectIds)) and ua.id =uad.timeSheetId  "
									+ " group by ua.resourceAllocId");
							query.setParameterList("projectIds", projectIds);
						}
					}
					if (timeSheetStatus.compareTo('M') == 0) {
						if (projectIds1 != null && (!projectIds1.isEmpty())){
							query = session.createQuery("Select ua.resourceAllocId.employeeId.employeeId ,count(ua.resourceAllocId) from UserActivity ua,  UserTimeSheetDetail uad where (ua.resourceAllocId.projectId.id in(:projectIds)) and ua.id =uad.timeSheetId  "
									+ " group by ua.resourceAllocId");
						query.setParameterList("projectIds", projectIds1);}
					}
				} else {
					if (timeSheetStatus.compareTo('P') == 0) {
						query = session.createQuery("Select ua.resourceAllocId.employeeId.employeeId ,count(ua.resourceAllocId) from UserActivity ua, UserTimeSheetDetail uad where (ua.status =(:status) and "
								+ "ua.resourceAllocId.id in(select id from ResourceAllocation where employeeId in(select employeeId from Resource))) and ua.id =uad.timeSheetId " + " group by ua.resourceAllocId");
						query.setParameter("status", timeSheetStatus);
					} else if (timeSheetStatus.compareTo('L') == 0) {
						query = session.createQuery("Select ua.resourceAllocId.employeeId.employeeId ,count(ua.resourceAllocId) from UserActivity ua, UserTimeSheetDetail uad  where ua.id =uad.timeSheetId " + " group by ua.resourceAllocId");
					} else {
						if (projectIds1 != null && (!projectIds1.isEmpty())) {
							query = session.createQuery("Select ua.resourceAllocId.employeeId.employeeId ,count(ua.resourceAllocId) from UserActivity ua,  UserTimeSheetDetail uad  where ua.resourceAllocId.projectId.id in(:projectIds) and ua.id =uad.timeSheetId "
									+ " group by ua.resourceAllocId");
							query.setParameterList("projectIds", projectIds1);
						}
					}

				}
			}
			
			
			
			
			

			if (timeSheetStatus != null) {

				if (query != null) {
					List l = query.list();
					for (int i = 0; i < l.size(); i++) {
						Object o[] = (Object[]) l.get(i);
						userActivities.add(Integer.parseInt(o[0].toString()));
					}
				}

			}
			// userActivities = criteria.list();
		} catch (HibernateException e) {
			logger.error("HibernateException occured in findUserActivitysByEmployeeId method at DAO layer:-" + e);
			logger.info("HibernateException occured in findUserActivitysByEmployeeId method at DAO layer:-" + e);
			throw e;
		}

		logger.info("------------UserActivityDaoImpl findUserActivitysByEmployeeId method end------------");
		return userActivities;
	}
	
	public List<Integer> findEmployeeUderIRMSRM(Resource loggedInResource, Character timeSheetStatus)
	{
		
		logger.info("------------UserActivityDaoImpl findEmployeeUderIRMSRM method start------------");
		
		List<Integer> resourceList= new ArrayList<Integer>();
		try{
		Session session = (Session) getEntityManager().getDelegate();
		//Criteria criteria =session.createCriteria(UserActivity.class, "ua");
		Criteria criteria =session.createCriteria(UserTimeSheetDetail.class, "uad");
		Criteria uCriteria=criteria.createAlias("uad.timeSheetId", "ua");
		
		if(timeSheetStatus.compareTo('P') == 0)
			uCriteria.add(Restrictions.eq("ua.status", timeSheetStatus));
		
		Criteria raCriteria=criteria.createAlias("ua.resourceAllocId", "ra");
	//	raCriteria.add(Restrictions.disjunction().add(Restrictions.isNull("ra.allocEndDate")).add(Restrictions.ge("ra.allocEndDate", new Date())));
		Criteria rCriteria=criteria.createAlias("ra.employeeId", "r");
		rCriteria.add(Restrictions.disjunction().add(Restrictions.eq("r.currentReportingManager", loggedInResource)).add(Restrictions.eq("r.currentReportingManagerTwo", loggedInResource)));
		rCriteria.setProjection(  Projections.distinct(Projections.property("r.employeeId")));
		resourceList= (List<Integer>) rCriteria.list();
		}
		catch (HibernateException e) {
				logger.error("HibernateException occured in findEmployeeUderIRMSRM method at DAO layer:-" + e);
				logger.info("HibernateException occured in findEmployeeUderIRMSRM method at DAO layer:-" + e);
				throw e;
				
		}
		catch (Exception e) {
			logger.error("HibernateException occured in findEmployeeUderIRMSRM method at DAO layer:-" + e);
			logger.info("HibernateException occured in findEmployeeUderIRMSRM method at DAO layer:-" + e);
		
	}
	logger.info("------------UserActivityDaoImpl findEmployeeUderIRMSRM method end------------");
		
	return resourceList;
	}
	


	public List<UserActivity> findUserActivitiesByProjectId(Integer projectId, Character timeSheetStatus, String status, Date weekEndDate) {
		
		List<UserActivity> userActivities;
		
		Session session = (Session) getEntityManager().getDelegate();
		
		Criteria criteria = session.createCriteria(UserActivity.class);
			criteria.createAlias("resourceAllocId", "re");
			criteria.setFetchMode("resourceAllocId", FetchMode.JOIN);
			criteria.add(Restrictions.eq("status", timeSheetStatus));
			criteria.add(Restrictions.isNotNull("weekEndDate"));
			criteria.add(Restrictions.eq("weekEndDate", weekEndDate));
			criteria.add(Restrictions.eq("re.projectId.id", projectId));
		
		if (status.equals("Active")) {
			Criterion criterion1 = Restrictions.or(Restrictions.isNull("re.allocEndDate"), Restrictions.ge("re.allocEndDate", new Date()));
			criteria.add(criterion1);
		}
		
		userActivities = criteria.list();
		return userActivities;
	}

	@SuppressWarnings("unchecked")
	public List<UserActivity> findUserActivitiesForOwnedProjectOfLoggedInUser(Integer employeeId, Character timeSheetStatus, String weekEndDate, Integer loggedInUserId) {
		logger.info("------------UserActivityDaoImpl findUserActivitiesForOwnedProjectOfLoggedInUser method start------------");
		Session session = (Session) getEntityManager().getDelegate();
		List<UserActivity> userActivities =  new ArrayList<UserActivity>();;
		List<Integer> projectIds = null;
			Criteria criteria = session.createCriteria(Project.class).add(Restrictions.eq("offshoreDelMgr.employeeId", loggedInUserId)).setProjection(Projections.property("id"));
			projectIds = criteria.list();
			criteria = session.createCriteria(UserActivity.class).add(Restrictions.eq("employeeId.employeeId", employeeId));
			criteria.createAlias("resourceAllocId", "re");
		  	criteria.setFetchMode("resourceAllocId", FetchMode.JOIN);
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			Date date = null;
			try {
				date = formatter.parse(weekEndDate);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			criteria.add(Restrictions.eq("weekEndDate", date));
			if (timeSheetStatus.compareTo('P') == 0 || timeSheetStatus.compareTo('R') == 0 || timeSheetStatus.compareTo('A') == 0 || timeSheetStatus.compareTo('N') == 0) {
				criteria.add(Restrictions.eq("status", timeSheetStatus));
			}
			criteria.add(Restrictions.in("re.projectId.id", projectIds));
			userActivities = criteria.list();
		logger.info("------------UserActivityDaoImpl findUserActivitysByEmployeeId method end------------");
		return userActivities;
	}

	public Double getTotalHours() {
		
		Session session = (Session) getEntityManager().getDelegate();
		
		Query query = session.createQuery("select sum(IF(ua.d1Hours IS NULL,0,ua.d1Hours)+ IF(ua.d2Hours IS NULL,0,ua.d2Hours)+IF(ua.d3Hours IS NULL,0,ua.d3Hours)+IF(ua.d4Hours IS NULL,0,ua.d4Hours)+IF(ua.d5Hours IS NULL,0,ua.d5Hours)+IF(ua.d6Hours IS NULL,0,ua.d6Hours)+IF(ua.d7Hours IS NULL,0,ua.d7Hours)) from UserActivity ua where ua.employeeId=employeeId and ua.weekEndDate=weekEndDate and ua.resourceAllocId=resourceAllocId");
		Double value = (Double) query.uniqueResult();
		
		System.out.println(""+value);
		return value;
	}
}
