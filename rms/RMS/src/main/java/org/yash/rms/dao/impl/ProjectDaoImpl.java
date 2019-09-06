package org.yash.rms.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.yash.rms.dao.ProjectDao;
import org.yash.rms.domain.OrgHierarchy;
import org.yash.rms.domain.Project;
import org.yash.rms.domain.ProjectPo;
import org.yash.rms.domain.Resource;
import org.yash.rms.domain.ResourceAllocation;
import org.yash.rms.rest.dao.generic.impl.HibernateGenericDao;
import org.yash.rms.util.UserUtil;

import javassist.NotFoundException;

@Repository("projectDaoImpl")
public class ProjectDaoImpl extends HibernateGenericDao<Integer,Project> implements ProjectDao {

	public ProjectDaoImpl() {
		super(Project.class);
		// TODO Auto-generated constructor stub
	}

	private static final Logger logger = LoggerFactory.getLogger(ProjectDaoImpl.class);

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
		logger.info("--------ProjectDaoImpl delete method start-------");
		boolean isSuccess = true;
		try {

			Query query = ((Session) getEntityManager().getDelegate()).getNamedQuery(ProjectPo.DELETE_ProjectPo_BASED_ON_ID).setInteger("id", id);
			int totalRowsDeleted = query.executeUpdate();

		} catch (HibernateException e) {

			isSuccess = false;
			e.printStackTrace();
			logger.error("Exception occured in delete method at DAO layer:-" + e);
			throw e;
		}
		logger.info("--------ProjectDaoImpl delete method start-------");
		return isSuccess;
	}

	@Transactional
	public boolean saveOrupdate(Project project) {
		logger.info("--------ProjectDaoImpl saveOrupdate method start-------");
		if (null == project)
			return false;
		if (project.getProjectCategoryId() != null) {

			if (project.getProjectCategoryId().getId() == -1) {
				project.setProjectCategoryId(null);

			}
		}
		
		if(project.getCustomerGroup()!=null && project.getCustomerGroup().getGroupId()==0){
			project.setCustomerGroup(null);
		}

		if (project.getId() == null) {

			project.setCreatedId(UserUtil.getUserContextDetails().getUserName());
			project.setCreationTimestamp(new Date());

		} else {
			project.setLastUpdatedId(UserUtil.getUserContextDetails().getUserName());
			project.setLastupdatedTimestamp(new Date());
		}
		Session currentSession = (Session) getEntityManager().getDelegate();
		boolean isSuccess = true;

		try {
			if (project.getId() == null) {

				project.setCreatedId(UserUtil.getUserContextDetails().getUserName());
				project.setCreationTimestamp(new Date());

			} else {
				project.setLastUpdatedId(UserUtil.getUserContextDetails().getUserName());
			}
			if (null == project.getId()) {
				Serializable id = currentSession.save(project);
				if (null != id) {
					project.setId((Integer) id);
				}
			} else {
				Object mergedTimeSheet = currentSession.merge(project);
				if (null == mergedTimeSheet) {
					return false;
				}
			}
			if (project.getProjectPoes() != null) {

				// Iterating the UserTimeSheet Detail and setting the
				// Usertimesheet id which is acting as foregin key here
				for (ProjectPo po : project.getProjectPoes()) {
					po.setProjectId(project);
					if (null != po.getId()) {
						isSuccess = null != currentSession.merge(po) ? true : false;
					} else {
						isSuccess = null != currentSession.save(po) ? true : false;
					}
				}
			}
		} catch (HibernateException e) {
			isSuccess = false;
			e.printStackTrace();
			logger.error("Exception occured in saveOrupdate method at DAO layer:-" + e);
			throw e;
		}
		logger.info("--------ProjectDaoImpl saveOrupdate method start-------");
		return isSuccess;
	}

	@SuppressWarnings("unchecked")
	public List<Project> findAll() {
		logger.info("--------ProjectDaoImpl findAll method start-------");
		List<Project> projects = null;
		try {
			Session session = (Session) getEntityManager().getDelegate();
			projects = session.createCriteria(Project.class).list();

		} catch (HibernateException ex) {
			ex.printStackTrace();
			logger.error("EXCEPTION :: IN GETTING PROJECT LIST " + ex.getMessage());
			throw ex;

		} finally {
			if (null == projects) {
				projects = new ArrayList<Project>();
			}
		}
		logger.info("--------ProjectDaoImpl findAll method start-------");
		return projects;
	}

	@SuppressWarnings("unchecked")
	public List<Project> findAllActiveProjects() {
		logger.info("--------ProjectDaoImpl findAll method start-------");
		List<Project> projects = null;
		try {
			Session session = (Session) getEntityManager().getDelegate();
			Date date = new Date(System.currentTimeMillis());
			// projects =
			Criteria criteria = session.createCriteria(Project.class);
			criteria.add(Restrictions.disjunction().add(Restrictions.ge("projectEndDate", date)).add(Restrictions.isNull("projectEndDate")));
			projects = criteria.list();
			// .add(Restrictions.disjunction().add(Restrictions.ge("projectEndDate",
			// date)).add(Restrictions.isNull("projectEndDate"))).list();

		} catch (HibernateException ex) {
			ex.printStackTrace();
			logger.error("EXCEPTION :: IN GETTING PROJECT LIST " + ex.getMessage());
			throw ex;

		} finally {
			if (null == projects) {
				projects = new ArrayList<Project>();
			}
		}
		logger.info("--------ProjectDaoImpl findAll method start-------");
		return projects;
	}

	public Project findProject(int id) {
		logger.info("--------ProjectDaoImpl findProject method start-------");
		try {
			Criteria criteria = ((Session) getEntityManager().getDelegate()).createCriteria(Project.class);
			criteria.add(Restrictions.eq("id", id));
			Project project = (Project) criteria.uniqueResult();
			logger.info("--------ProjectDaoImpl findProject method end-------");
			return project;
		} catch (HibernateException exception) {
			exception.printStackTrace();
			logger.error("Exception occured in findProject method at DAO layer:-" + exception);
			throw exception;

		}

		// return null;
	}


	public List<Project> findByEntries(int firstResult, int sizeNo) {
		return null;
	}

	public long countTotal() {
		return 0;
	}

	@SuppressWarnings("unchecked")
	public List<Project> findProjectsByOffshoreDelMgr(Integer offshoreDelMgr, String activeOrAll) {
		logger.info("--------ProjectDaoImpl findProjectsByOffshoreDelMgr method start-------");
		Session session = (Session) getEntityManager().getDelegate();
		List<Project> projects = new ArrayList<Project>();

		try {
			Criteria criteria = session.createCriteria(Project.class);
			//added as per timesheet requirment
			Criterion c1=Restrictions.or(Restrictions.eq("offshoreDelMgr.employeeId", offshoreDelMgr), Restrictions.eq("deliveryMgr.employeeId", offshoreDelMgr));
			criteria.add(c1);
			if (activeOrAll.equalsIgnoreCase("active")) {
				criteria.add(Restrictions.disjunction().add(Restrictions.ge("projectEndDate", new Date())).add(Restrictions.isNull("projectEndDate")));
			}
			projects = criteria.list();
		} catch (HibernateException hibernateException) {
			// logger.error("HibernateException occured in findProjectsByOffshoreDelMgr method:-"+hibernateException);
			logger.error("Exception occured in findProjectsByOffshoreDelMgr method at DAO layer:-" + hibernateException);
			throw hibernateException;
		}
		logger.info("--------ProjectDaoImpl findProjectsByOffshoreDelMgr method start-------");
		return projects;

	}

	@SuppressWarnings("unchecked")
	public List<Project> findProjectsForBGAdmin(List<OrgHierarchy> businessGroup) {
		logger.info("--------ProjectDaoImpl findProjectsForBGAdmin method start-------");
		Session session = (Session) getEntityManager().getDelegate();
		List<Project> projects = new ArrayList<Project>();
		try {
			Criteria criteria = session.createCriteria(Project.class);
			criteria.add(Restrictions.in("orgHierarchy", businessGroup));
			projects = criteria.list();
		} catch (HibernateException hibernateException) {
			logger.error("HibernateException occured in findProjectsForBGAdmin method at ProjectDaoImpl:-" + hibernateException);
			throw hibernateException;
		}
		logger.info("--------ProjectDaoImpl findProjectsForBGAdmin method end-------");
		return projects;
	}

	@SuppressWarnings("unchecked")
	public List<Project> findActiveProjectsForBGAdmin(List<OrgHierarchy> businessGroup) {
		logger.info("--------ProjectDaoImpl findProjectsForBGAdmin method start-------");
		Session session = (Session) getEntityManager().getDelegate();
		List<Project> projects = new ArrayList<Project>();
		try {
			Criteria criteria = session.createCriteria(Project.class);
			if (businessGroup != null && !businessGroup.isEmpty())
				criteria.add(Restrictions.in("orgHierarchy", businessGroup));
			criteria.add(Restrictions.disjunction().add(Restrictions.ge("projectEndDate", new Date(System.currentTimeMillis()))).add(Restrictions.isNull("projectEndDate")));
			projects = criteria.list();
		} catch (HibernateException hibernateException) {
			logger.error("HibernateException occured in findProjectsForBGAdmin method at ProjectDaoImpl:-" + hibernateException);
			throw hibernateException;
		}
		logger.info("--------ProjectDaoImpl findProjectsForBGAdmin method end-------");
		return projects;
	}

	public List<Integer> findProjectsByBehalfManagar(Integer employeeId, String activeOrAll) {

		logger.info("--------ProjectDaoImpl findProjectsForBGAdmin method start-------");
		Query query = null;
		List l;
		Session session = (Session) getEntityManager().getDelegate();
		try {
			if (activeOrAll.equals("all")) {
				query = session.createQuery("SELECT projectId.id FROM ResourceAllocation WHERE behalfManager =1 AND employeeId.employeeId=:employeeId");
			} else {
				query = session
						.createQuery("SELECT projectId.id FROM ResourceAllocation WHERE behalfManager =1 AND employeeId.employeeId=:employeeId AND (allocEndDate>CURRENT_DATE or allocEndDate IS NULL)");
			}
			query.setParameter("employeeId", employeeId);
			l = query.list();
		} catch (HibernateException hibernateException) {
			logger.error("HibernateException occured in findProjectsForBGAdmin method at ProjectDaoImpl:-" + hibernateException);
			throw hibernateException;
		}
		return l;
	}

	public List<Integer> findProjectID(String projects) {
		logger.info("--------ProjectDaoImpl findProjectID method starts-------");

		Session session = (Session) getEntityManager().getDelegate();
		org.hibernate.Query projectID = session.createSQLQuery("SELECT id FROM Project WHERE project_NAME = :projects");

		projectID.setParameter("projects", projects);

		logger.info("--------ProjectDaoImpl findProjectID method ends-------");

		return projectID.list();
	}

	// added by Neha for kickOffDate issue -start
	public boolean isKickOffDateGreater(int projectId, Date kickOffDate) {
		Session currentSession = (Session) getEntityManager().getDelegate();
		Query query = null;
		boolean isSuccess = true;
		query = currentSession.createSQLQuery("SELECT * FROM resource_allocation WHERE project_id=:projectId ORDER BY alloc_start_date ASC LIMIT 1").addEntity(ResourceAllocation.class)
				.setParameter("projectId", projectId);
		ResourceAllocation resAlloc = (ResourceAllocation) query.uniqueResult();
		if (resAlloc != null && kickOffDate.after(resAlloc.getAllocStartDate())) {
			isSuccess = false;
		}
		return isSuccess;
	}

	// added by Neha for kickOffDate issue - end

	// added by purva FOR US3119
	public List<Project> findAllProjectByBUid(Integer id) {
		logger.info("--------ResourceDaoImpl findResourcesByHRROLE method start-------");
		try {

			Criteria criteria = ((Session) getEntityManager().getDelegate()).createCriteria(Project.class);

			criteria.add(Restrictions.eq("orgHierarchy.id", id)).add(Restrictions.isNull("projectEndDate")).add(Restrictions.ne("projectName", ""));

			logger.info("--------ResourceDaoImpl findResourcesByHRROLE method end-------");
			return criteria.list();
		} catch (HibernateException ex) {
			ex.printStackTrace();
			logger.error("Exception occured in findResourcesByHRROLE method at DAO layer:-" + ex);
			throw ex;
		}
	}

	@SuppressWarnings("unchecked")
	public Set<Project> findAllProjectByBUIds(List<Integer> bIds) {

		logger.info("--------ResourceDaoImpl findAllProjectByBUids method start-------");

		Set<Project> projectList = new TreeSet<Project>(new Project.ProjectNameComparator());

		Session session = (Session) getEntityManager().getDelegate();

		try {

			Query query = session.createSQLQuery("SELECT DISTINCT p.id,p.project_name FROM project p " + "LEFT JOIN resource_allocation ra ON p.id = ra.project_id "
					+ "LEFT JOIN resource r ON r.employee_id = ra.employee_id " + "WHERE (r.bu_id IN (:bIds) OR r.current_bu_id IN (:bIds) OR p.bu_id IN (:bIds)) "
					+ "AND (p.project_end_date IS NULL OR p.project_end_date>=CURRENT_DATE) AND (ra.alloc_end_date IS NULL OR ra.alloc_end_date>=CURRENT_DATE) ");

			query.setParameterList("bIds", bIds);

			List<Object[]> objectList1 = query.list();

			if (!objectList1.isEmpty()) {

				for (Object[] obj : objectList1) {

					Project project = new Project();
					project.setId((Integer) obj[0]);
					project.setProjectName((String) obj[1]);
					projectList.add(project);
				}
			}

		} catch (HibernateException e) {

			e.printStackTrace();

			logger.error("EXCEPTION CAUSED IN GETTING OrgID By Id " + bIds + e.getMessage());

			throw e;
		}

		logger.info("------ResourceDaoImpl findAllProjectByBUids method end-----");

		return projectList;
	}

	public Set<Project> findAllProjectByBUAndLocationIdsForPWRReport(List<Integer> bIds, List<Integer> lIds) {

		logger.info("--------ResourceDaoImpl findAllProjectByBUidsForPWRReport method start-------");

		Set<Project> projectList = new TreeSet<Project>(new Project.ProjectNameComparator());

		Session session = (Session) getEntityManager().getDelegate();

		try {

			Query query = session.createSQLQuery("SELECT DISTINCT p.id,p.project_name FROM project p " + "LEFT JOIN resource_allocation ra ON p.id = ra.project_id "
					+ "LEFT JOIN resource r ON r.employee_id = ra.employee_id " + "WHERE (p.bu_id IN (:bIds)) "
					+ "AND (p.project_end_date IS NULL OR p.project_end_date>=CURRENT_DATE) AND (ra.alloc_end_date IS NULL OR ra.alloc_end_date>=CURRENT_DATE) " + "AND r.`location_id` IN (:lIds)");

			query.setParameterList("bIds", bIds);
			query.setParameterList("lIds", lIds);

			List<Object[]> objectList1 = query.list();

			if (!objectList1.isEmpty()) {

				for (Object[] obj : objectList1) {

					Project project = new Project();
					project.setId((Integer) obj[0]);
					project.setProjectName((String) obj[1]);
					projectList.add(project);
				}
			}

		} catch (HibernateException e) {

			e.printStackTrace();

			logger.error("EXCEPTION CAUSED IN GETTING OrgID By Id " + bIds + "EXCEPTION CAUSED IN GETTING Location By Id" + lIds + e.getMessage());

			throw e;
		}

		logger.info("------ResourceDaoImpl findAllProjectByBUidsForPWRReport method end-----");

		return projectList;
	}

	public List<Project> findActiveProjectsNamesForBGAdmin(List<OrgHierarchy> buList) {
		logger.info("--------ProjectDaoImpl findProjectsForBGAdmin method start-------");
		Session session = (Session) getEntityManager().getDelegate();
		List<Object[]> projects = new ArrayList<Object[]>();
		List<Project> projectList = new ArrayList<Project>();
		try {
			Criteria criteria = session.createCriteria(Project.class);
			if (buList != null && !buList.isEmpty())
				criteria.add(Restrictions.in("orgHierarchy", buList));
			criteria.add(Restrictions.disjunction().add(Restrictions.ge("projectEndDate", new Date(System.currentTimeMillis()))).add(Restrictions.isNull("projectEndDate"))).setProjection(
					Projections.projectionList().add(Projections.property("projectName")).add(Projections.property("id")));
			projects = criteria.list();
			Project project = null;
			if (!projects.isEmpty()) {
				for (Object[] object : projects) {
					project = new Project();
					project.setProjectName(object[0].toString());
					project.setId((Integer) object[1]);
					projectList.add(project);
					project = null;
				}
			}

		} catch (HibernateException hibernateException) {
			logger.error("HibernateException occured in findProjectsForBGAdmin method at ProjectDaoImpl:-" + hibernateException);
			throw hibernateException;
		}
		logger.info("--------ProjectDaoImpl findProjectsForBGAdmin method end-------");
		return projectList;
	}

	@SuppressWarnings("unchecked")
	public Set<Project> findAllProjectsByBUIds(List<Integer> bIds) {

		logger.info("--------ResourceDaoImpl findAllProjectByBUids method start-------");

		Set<Project> projectList = new TreeSet<Project>(new Project.ProjectNameComparator());

		Session session = (Session) getEntityManager().getDelegate();

		try {

			Query query = session.createSQLQuery("SELECT DISTINCT p.id, p.project_name FROM  project p WHERE p.bu_id IN (:bIds)  AND ( p.project_end_date IS NULL OR p.project_end_date >= CURRENT_DATE) ");

			query.setParameterList("bIds", bIds);

			List<Object[]> objectList1 = query.list();

			if (!objectList1.isEmpty()) {

				for (Object[] obj : objectList1) {

					Project project = new Project();
					project.setId((Integer) obj[0]);
					project.setProjectName((String) obj[1]);
					projectList.add(project);
				}
			}

		} catch (HibernateException e) {

			e.printStackTrace();

			logger.error("EXCEPTION CAUSED IN GETTING OrgID By Id " + bIds + e.getMessage());

			throw e;
		}

		logger.info("------ResourceDaoImpl findAllProjectByBUids method end-----");

		return projectList;
	}
	
	public List<Project> findAllProjectByClientId(Integer id) {
		Session session = (Session) getEntityManager().getDelegate();

		try {
			Query query = session.createSQLQuery("SELECT * FROM  project p where (ISNULL(p.project_end_date) || p.project_end_date >= CURRENT_DATE) && customer_name_id = :customer_name_id");
			query.setParameter("customer_name_id", id);
			List<Project> projects = query.list();
			return projects;
		} catch (HibernateException exception) {
			exception.printStackTrace();
			logger.error("Exception occured in findProject method at DAO layer:-" + exception);
			throw exception;
		}
	}
	public List<Project> findProjectNamesByClientId(Integer id) {
		Session session = (Session) getEntityManager().getDelegate();

		try {
			List<Object[]> projects = new ArrayList<Object[]>();
			List<Project> projectList = new ArrayList<Project>();
			Query query = session.createSQLQuery("SELECT project_name , id FROM  project p where customer_name_id = :customer_name_id");
			query.setParameter("customer_name_id", id);

			projects = query.list();
			Project project = null;
			if (!projects.isEmpty()) {
				for (Object[] object : projects) {
					project = new Project();
					project.setProjectName(object[0].toString());
					project.setId((Integer) object[1]);
					projectList.add(project);
					project = null;
				}
			}
			return projectList;
		} catch (HibernateException exception) {
			exception.printStackTrace();
			logger.error("Exception occured in findProject method at DAO layer:-" + exception);
			throw exception;
		}
	}

	public List<Project> findProjectsbyid(List<Integer> myList) {
		// TODO Auto-generated method stub
		return null;
	}
	public OrgHierarchy getBUHeadByProjectID(Integer prjId)  {
		Session session = (Session) getEntityManager().getDelegate();
		OrgHierarchy orgHierarchy = new OrgHierarchy();
		try{
			Criteria criteria = session.createCriteria(Project.class);
			criteria.add(Restrictions.eq("id", prjId));
			Project project = (Project) criteria.uniqueResult();
			orgHierarchy = project.getOrgHierarchy();
		} catch (Exception e) {
			logger.error("exception in getBUHeadByProjectID in DAO " + e);
		}
		return orgHierarchy;
	}
	
	//Project count here
	@Transactional
	public Long getProjectCount() {
		logger.info("--------ProjectDaoImpl getProjectCount method start-------");
		Long count = 0L;
		try {
			
			
			
			Session session = (Session) this.entityManager.unwrap(Session.class);
			Criteria criteria = session.createCriteria(Project.class);
			criteria.add(Restrictions.disjunction().add(Restrictions.ge("projectEndDate", new Date()))
		              .add(Restrictions.isNull("projectEndDate")));
			criteria.setProjection(Projections.rowCount());
			count = (Long)criteria.uniqueResult();
		
		} catch (HibernateException exception) {
			exception.printStackTrace();
			logger.error("Exception occured in getProjectCount method at DAO layer:-" + exception);
			throw exception;

		}
		return count;
	}

	public Project findProjectByProjectName(final String projectName) throws Exception {
		Project project = null;
		if (!StringUtils.trimToEmpty(projectName).isEmpty()) {
			try {
				Session session = (Session) getEntityManager().getDelegate();
				if (session != null) {
					Criteria criteria = session.createCriteria(Project.class);
					criteria.add(Restrictions.eq("projectName", projectName).ignoreCase());
					project = (Project) criteria.uniqueResult();
				}
				else{
					logger.error("Session not found or session expired");
				}
			} catch (HibernateException exception) {
				logger.error("Exception occured in FindProjectByProjectName method at DAO layer:-" + exception);
				exception.printStackTrace();
				throw exception;
			}
			catch (Exception exception) {
				logger.error("Exception occured in FindProjectByProjectName method at DAO layer:-" + exception);
				exception.printStackTrace();
				throw exception;
			}
		}
		else{
			logger.error("ProjectName not found");
			throw new NotFoundException("ProjectName not found");
		}
		return project;
	}

	
	
}
