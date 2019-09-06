package org.yash.rms.dao.impl;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.yash.rms.dao.CATicketDao;
import org.yash.rms.domain.CATicket;
import org.yash.rms.domain.CATicketProcess;
import org.yash.rms.domain.CATicketSubProcess;
import org.yash.rms.domain.Crop;
import org.yash.rms.domain.DefectLog;
import org.yash.rms.domain.Landscape;
import org.yash.rms.domain.Project;
import org.yash.rms.domain.ReasonForSLAMissed;
import org.yash.rms.domain.ReasonHopping;
import org.yash.rms.domain.ReasonReopen;
import org.yash.rms.domain.Region;
import org.yash.rms.domain.Resource;
import org.yash.rms.domain.Rework;
import org.yash.rms.domain.RootCause;
import org.yash.rms.domain.SolutionReview;
import org.yash.rms.domain.T3Contribution;
import org.yash.rms.domain.Unit;
import org.yash.rms.namedquery.RmsNamedQuery;
import org.yash.rms.report.dto.dashboardFilter;
import org.yash.rms.util.HibernateUtil;

@Repository("CATicketDao")
@Transactional
public class CATicketDaoImpl implements CATicketDao {

	private static final Logger logger = LoggerFactory
			.getLogger(ResourceDaoImpl.class);
	@PersistenceContext(name = "mysql", type = PersistenceContextType.TRANSACTION)
	private EntityManager entityManager;
	
	
	public EntityManager getEntityManager() {
		return entityManager;
	}
	
	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	List<CATicketProcess> caticket = null;

	public Serializable save(CATicket caTicket) {
		Serializable id=null;
		System.out.println("Saving");
		Session currentSession = (Session) getEntityManager().getDelegate();
		boolean isSuccess = true;
		try {
			id=currentSession.save(caTicket);
		} catch (HibernateException e) {
			isSuccess = false;
			e.printStackTrace();
			logger.error("Exception occured in copyUserProfileToResource method at DAO layer:-"
					+ e);

		}
		logger.info("--------ResourceDaoImpl copyUserProfileToResource method end-------");
		return id;
	}

	public CATicket saveCATicket(CATicket ticket, Integer discrepencyId) {
		CATicket caTicket = null;
		Session currentSession = (Session) getEntityManager().getDelegate();
		Serializable id = currentSession.save(ticket);
		System.out.println("Id: " + id);
		if (id != null) {
			if (discrepencyId != null) {
				String hql = "delete from CATicketDiscrepencies where id= :discrepencyId";
				Query query = currentSession.createQuery(hql);
				System.out.println("discrepencyId: " + discrepencyId);
				query.setInteger("discrepencyId", discrepencyId);
				int resultDelete = query.executeUpdate();
				System.out.println("resultDelete: " + resultDelete);
			}
			caTicket = (CATicket) currentSession.get(CATicket.class, id);
		}
		return caTicket;
	}

	public boolean editCATicket(CATicket ticket) {
		boolean isUpdated = true;
		Session currentSession = (Session) getEntityManager().getDelegate();
		
		/*
		 * Updated on 18/Nov/2015
		 * Using merge instead of update method because of an exception "org.hibernate.nonuniqueobjectexception"
		 */
		
		currentSession.merge(ticket);
		return isUpdated;
	}

	@SuppressWarnings("unchecked")
	public List<CATicket> findAllTickets() {
		Session currentSession = (Session) getEntityManager().getDelegate();
		Criteria criteria = currentSession.createCriteria(CATicket.class);
		List<CATicket> list = criteria.list();
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<CATicket> getTicketsByPhase() {
		Session currentSession = (Session) getEntityManager().getDelegate();
		Criteria criteria = currentSession.createCriteria(CATicket.class);
		criteria.add(Restrictions.in("caTicketNo", new BigInteger[] {
				BigInteger.valueOf(5120624), BigInteger.valueOf(5120708),
				BigInteger.valueOf(5121108), BigInteger.valueOf(5121206),
				BigInteger.valueOf(5121232) }));
		List<CATicket> list = criteria.list();
		return list;
	}

	public CATicket findTicketById(int id) {
		Session currentSession = (Session) getEntityManager().getDelegate();
		Criteria criteria = currentSession.createCriteria(CATicket.class).add(
				Restrictions.eq("id", id));
		CATicket caTicket = (CATicket) criteria.uniqueResult();
		return caTicket;
	}

	@SuppressWarnings("unchecked")
	public List<CATicketProcess> findProcess() {
		List<CATicketProcess> process = new ArrayList<CATicketProcess>();
		Session session = (Session) getEntityManager().getDelegate();
		Criteria criteria = session.createCriteria(CATicketProcess.class);
		process = criteria.list();
		return process;
	}

	public List<Project> findModuleNameProjects() {
		logger.info("--------ProjectDaoImpl findAll method start-------");
		List<Project> projects = null;
		try {
			Session session = (Session) getEntityManager().getDelegate();
			Date date = new Date(System.currentTimeMillis());
			// projects =
			Criteria criteria = session.createCriteria(Project.class);
			criteria.add(Restrictions.disjunction()
					.add(Restrictions.isNotNull("moduleName"))
					.add(Restrictions.not(Restrictions.eq("moduleName", ""))));
			criteria.add(Restrictions.disjunction()
					.add(Restrictions.ge("projectEndDate", date))
					.add(Restrictions.isNull("projectEndDate")));
			projects = criteria.list();

		} catch (HibernateException ex) {
			ex.printStackTrace();
			logger.error("EXCEPTION :: IN GETTING PROJECT LIST "
					+ ex.getMessage());
			throw ex;

		} finally {
			if (null == projects) {
				projects = new ArrayList<Project>();
			}
		}
		logger.info("--------ProjectDaoImpl findAll method start-------");
		return projects;

	}

	public List<CATicketProcess> findProcessByModuleId(int moduleid) {

		try {

			Criteria criteria = ((Session) getEntityManager().getDelegate())
					.createCriteria(CATicketProcess.class);
			criteria.add(Restrictions.eq("moduleId.id", moduleid));
			caticket = criteria.list();
		} catch (HibernateException ex) {
			ex.printStackTrace();
			logger.error("EXCEPTION :: IN GETTING PROJECT LIST "
					+ ex.getMessage());
			throw ex;

		} finally {
			if (null == caticket) {
				caticket = new ArrayList<CATicketProcess>();
			}
		}
		logger.info("--------ProjectDaoImpl findAll method start-------");
		return caticket;
	}

	public List<CATicket> findUserWiseTicket(int currentUser) {

		Session currentSession = (Session) getEntityManager().getDelegate();
		Criteria criteria = currentSession.createCriteria(CATicket.class).add(
				Restrictions.eq("employeeId.id", currentUser));

		List<CATicket> list = criteria.list();
		return list;
	}

	public List<T3Contribution> saveOrUpdateT3(T3Contribution t3Contribution) {
		logger.info("--------ResourceDaoImpl saveT3 method start-------");
		Session currentSession = (Session) getEntityManager().getDelegate();
		// currentSession.saveOrUpdate(t3Contribution);
		List<T3Contribution> t3Contributions = new ArrayList<T3Contribution>();
		T3Contribution contribution = null;
		try {
			if (t3Contribution.getId() != null) {
				currentSession.saveOrUpdate(t3Contribution);
				contribution = (T3Contribution) currentSession.get(
						T3Contribution.class, t3Contribution.getId());
				t3Contributions.add(contribution);
			} else {
				Serializable id = currentSession.save(t3Contribution);
				if (id != null) {
					contribution = (T3Contribution) currentSession.get(
							T3Contribution.class, id);
					t3Contributions.add(contribution);
				}
			}

		} catch (HibernateException e) {
			e.printStackTrace();
			logger.error("Exception occured in saveT3 method at DAO layer:-"
					+ e);
		}
		logger.info("--------ResourceDaoImpl saveT3 method end-------");
		return t3Contributions;
	}

	public Boolean saveSolReview(SolutionReview review) {
		logger.info("--------ResourceDaoImpl saveSolReview method start-------");
		Session currentSession = (Session) getEntityManager().getDelegate();
		currentSession.saveOrUpdate(review);
		boolean isSuccess = true;
		try {
			currentSession.saveOrUpdate(review);
		} catch (HibernateException e) {
			isSuccess = false;
			e.printStackTrace();
			logger.error("Exception occured in SolutionReview method at DAO layer:-"
					+ e);
		}
		logger.info("--------ResourceDaoImpl saveSolReview method end-------");
		return isSuccess;
	}

	public List<DefectLog> saveOrUpdateDefectLog(DefectLog log) {

		logger.info("--------ResourceDaoImpl saveT3 method start-------");
		Session currentSession = (Session) getEntityManager().getDelegate();
		// currentSession.saveOrUpdate(t3Contribution);
		List<DefectLog> defectLogList = new ArrayList<DefectLog>();
		DefectLog defectLog = null;
		try {
			if (log.getId() != null) {
				currentSession.saveOrUpdate(log);
				defectLog = (DefectLog) currentSession.get(DefectLog.class,
						log.getId());
				defectLogList.add(defectLog);
			} else {
				Serializable id = currentSession.save(log);
				if (id != null) {
					defectLog = (DefectLog) currentSession.get(DefectLog.class,
							id);
					defectLogList.add(defectLog);
				}
			}

		} catch (HibernateException e) {
			e.printStackTrace();
			logger.error("Exception occured in saveT3 method at DAO layer:-"
					+ e);
		}
		logger.info("--------ResourceDaoImpl saveT3 method end-------");
		return defectLogList;

	}

	public List<Crop> saveOrUpdateCrop(Crop crop) {
		logger.info("--------ResourceDaoImpl saveT3 method start-------");
		Session currentSession = (Session) getEntityManager().getDelegate();
		// currentSession.saveOrUpdate(t3Contribution);
		List<Crop> cropList = new ArrayList<Crop>();
		Crop crops = null;
		try {
			if (crop.getId() != null) {
				currentSession.saveOrUpdate(crop);
				crops = (Crop) currentSession.get(Crop.class, crop.getId());
				cropList.add(crops);
			} else {
				Serializable id = currentSession.save(crop);
				if (id != null) {
					crops = (Crop) currentSession.get(Crop.class, id);
					cropList.add(crops);
				}
			}

		} catch (HibernateException e) {
			e.printStackTrace();
			logger.error("Exception occured in saveT3 method at DAO layer:-"
					+ e);
		}
		logger.info("--------ResourceDaoImpl saveT3 method end-------");
		return cropList;
	}

	public List<Rework> saveOrUpdateRework(Rework rework) {

		logger.info("--------ResourceDaoImpl saveT3 method start-------");
		Session currentSession = (Session) getEntityManager().getDelegate();
		// currentSession.saveOrUpdate(t3Contribution);
		List<Rework> reworkList = new ArrayList<Rework>();
		Rework rew = null;
		try {
			if (rework.getId() != null) {
				currentSession.saveOrUpdate(rework);
				rew = (Rework) currentSession.get(Rework.class, rework.getId());
				reworkList.add(rew);
			} else {
				Serializable id = currentSession.save(rework);
				if (id != null) {
					rew = (Rework) currentSession.get(Rework.class, id);
					reworkList.add(rew);
				}
			}

		} catch (HibernateException e) {
			e.printStackTrace();
			logger.error("Exception occured in saveT3 method at DAO layer:-"
					+ e);
		}
		logger.info("--------ResourceDaoImpl saveT3 method end-------");
		return reworkList;
	}

	public List<CATicket> getEmployeeProjectName(List<Integer> projectId,
			int employeeId) {
		Session currentSession = (Session) getEntityManager().getDelegate();
		Criterion criterion = null;
		List<CATicket> list = new ArrayList<CATicket>();
		try {
			Criteria criteria = ((Session) getEntityManager().getDelegate())
					.createCriteria(CATicket.class);
			if (projectId != null && employeeId != 0) {
				criteria.add(Restrictions.in("moduleId.id", projectId));
				criteria.add(Restrictions.eq("assigneeId.employeeId",
						employeeId));
				list = criteria.list();
			}
		} catch (HibernateException e) {
			e.printStackTrace();
			logger.error("Exception occured in saveRework method at DAO layer:-"
					+ e);
		}

		return list;

	}

	public List<CATicket> getReveiwerProjectName(List<Integer> projectId,
			int employeeId) {
		Session currentSession = (Session) getEntityManager().getDelegate();
		List<CATicket> list = new ArrayList<CATicket>();
		try {
			Criteria criteria = currentSession
					.createCriteria(CATicket.class);
			if (projectId != null && employeeId != 0) {
				criteria.add(Restrictions.in("moduleId.id", projectId));
				criteria.add(Restrictions.eq("reviewer.employeeId", employeeId));
				list = criteria.list();
			}
		} catch (HibernateException e) {
			e.printStackTrace();
			logger.error("Exception occured in saveRework method at DAO layer:-"
					+ e);
		}

		return list;

	}

	@SuppressWarnings("unchecked")
	public List<CATicket> getTicketDetailsbyPhase(int empId, int moduleId,
			String phase, int loggedInId) {
		Session currentSession = (Session) getEntityManager().getDelegate();
		// Criteria criteria = currentSession.createCriteria(CATicket.class);
		// criteria.add(Restrictions.in("caTicketNo", new BigInteger[] {
		// BigInteger.valueOf(5120624), BigInteger.valueOf(5120708),
		// BigInteger.valueOf(5121108), BigInteger.valueOf(5121206),
		// BigInteger.valueOf(5121232) }));
		// criteria.add(Restrictions.eq("", value))
		// List<CATicket> list = criteria.list();
		String hqlQuery = "";
		if (phase.equals("formyreview")) {
			hqlQuery += "from CATicket WHERE reviewer =:assigneeId AND assigneeId!=:assigneeId AND moduleId=:moduleId AND solutionReadyForReview='Yes' AND solutionreViewDate IS NULL AND groupName='Tier 2'";
		} else {
			hqlQuery += "from CATicket where assigneeId=:assigneeId AND moduleId=:moduleId";
			if (phase.equals("inacknowledged")) {
				hqlQuery += " AND acknowledgedDate is null AND groupName = 'Tier 2'";
			} else if (phase.equals("inclose")) {
				hqlQuery += " AND (customerApprovalFlag = 'No' AND (solutionAcceptedFlag = 'Yes' OR solutionAcceptedFlag = 'N.A.') AND groupName = 'Tier 2')";
			} else if (phase.equals("inanalysis")) {
				hqlQuery += " AND (analysisCompleteFlag = 'No' AND (reqCompleteFlag = 'Yes' OR reqCompleteFlag = 'N.A.') AND groupName = 'Tier 2')";
			} else if (phase.equals("inproblemmanagement")) {
				hqlQuery += " AND problemManagementFlag = 'No' AND groupName = 'Tier 2'";
			} else if (phase.equals("inrequirement")) {
				hqlQuery += " AND (reqCompleteFlag = 'No' AND acknowledgedDate is not null AND groupName = 'Tier 2')";
			} else if (phase.equals("inreview")) {
				hqlQuery += " AND (solutionreViewDate is null AND solutionReviewFlag != 'N.A.' AND groupName = 'Tier 2')";
			} else if (phase.equals("intesting")) {
				hqlQuery += " AND (solutionAcceptedFlag = 'No' AND (solutionReadyForReview = 'Yes' OR solutionReadyForReview = 'N.A.') AND groupName = 'Tier 2')";
			} else if (phase.equals("infollowUp")) {
				hqlQuery += " AND groupName != 'Tier 2'";
			} else if (phase.equals("forreview")) {
				hqlQuery += " AND solutionreViewDate is null AND solutionReadyForReview = 'Yes' AND groupName = 'Tier 2'";
			} else if (phase.equals("formyreview")) {
				hqlQuery += " AND reviewer =:assigneeId AND solutionreViewDate IS NULL AND solutionReadyForReview = 'Yes' AND groupName = 'Tier 2'";
			} else if (phase.equals("sentforreview")) {
				hqlQuery += " AND solutionreViewDate IS NULL AND solutionReadyForReview = 'Yes' AND groupName = 'Tier 2'";
			} else {
				hqlQuery += "";
			}
		}
		
		Query query = currentSession.createQuery(hqlQuery);
		
		if (phase.equals("formyreview")) {
			query.setInteger("assigneeId", loggedInId);
		}else{
			query.setInteger("assigneeId", empId);
		}
		System.out.println("hqlQuery: " + hqlQuery);
		query.setInteger("moduleId", moduleId);
		List<CATicket> list = query.list();
		return list;
	}

	/**** My Ticket Pending Status ****/
	@SuppressWarnings({ "unchecked", "null" })
	public List<Object[]> findTicketStatusCountByEmployeeId(String employeeId,
			dashboardFilter dashboardFilter) {
		// Session currentSession = (Session) getEntityManager().getDelegate();
		// Criteria criteria =
		// currentSession.createCriteria(CATicket.class).add(
		// Restrictions.eq("employeeId", employeeId));
		// List<CATicket> results = criteria.list();
		/*
		 * = currentSession
		 * .createSQLQuery(RmsNamedQuery.getMyPerformanceCount())
		 * .setParameter("employee_id", employeeId).list(); return
		 * performanceMapping;
		 */

		Session currentSession = (Session) getEntityManager().getDelegate();
		List<Object[]> performanceMapping = null;
		String assignee = "";
		String landscape = "";
		String module = "";
		String priority = "";
		String region = "";
		String reviewer = "";
		Date toTime = null;
		Date fromTime = null;

		if (dashboardFilter != null) {

			/* if (dashboardFilter.getModule() != null) { */
			assignee = dashboardFilter.getAssignee();
			landscape = dashboardFilter.getLandscape();
			priority = dashboardFilter.getPriority();
			region = dashboardFilter.getRegion();
			module = dashboardFilter.getModule();
			reviewer = dashboardFilter.getReviewer();
			toTime = dashboardFilter.getToTime();
			fromTime = dashboardFilter.getFromTime();

			List<Integer> regionIDsInt = new ArrayList<Integer>();
			List<Integer> regionIDs = new ArrayList<Integer>();
			if (region != null || region != "") {
				if (!(region.equals("-1"))) {
					regionIDs = getAllRegionIDs(region);
					/*
					 * if (regionIDs.size() != 0) { for
					 * (org.yash.rms.domain.Region regions : regionIDs) {
					 * regionIDsInt.add(regions.getId()); } }
					 */
				}
			}
			System.out.println("get ALl Region IDzzz ::" + regionIDs);

			Query query = currentSession
					.createSQLQuery(
							RmsNamedQuery
									.getMyPerformanceCountFiltered(regionIDs))
					.setParameter("employee_id", employeeId)
					.setParameter("assignee", assignee)
					.setParameter("landscape", landscape)
					.setParameter("module", module)
					.setParameter("priority", priority)
					// .setParameterList("region", Arrays.asList(regionIDs))
					.setParameter("reviewer", reviewer)
					.setParameter("toTime", toTime)
					.setParameter("fromTime", fromTime);
			if (regionIDs.size() > 0) {
				// 26, 43, 55, 56, 80, 81, 111, 116, 117, 118
				query.setParameterList("region", regionIDs);
			}

			performanceMapping = query.list();
			/*
			 * else if (dashboardFilter.getLandscape() != null) { landscape =
			 * dashboardFilter.getLandscape(); performanceMapping =
			 * currentSession .createSQLQuery(
			 * RmsNamedQuery.getMyTeamPerformanceCount())
			 * .setParameter("employee_id", employeeId)
			 * .setParameter("landscape", landscape).list(); } else if
			 * (dashboardFilter.getModule() != null) { module =
			 * dashboardFilter.getModule(); performanceMapping = currentSession
			 * .createSQLQuery( RmsNamedQuery.getMyTeamPerformanceCount())
			 * .setParameter("employee_id", employeeId) .setParameter("module",
			 * module).list(); } else if (dashboardFilter.getPriority() != null)
			 * { priority = dashboardFilter.getPriority(); performanceMapping =
			 * currentSession .createSQLQuery(
			 * RmsNamedQuery.getMyTeamPerformanceCount())
			 * .setParameter("employee_id", employeeId)
			 * .setParameter("priority", priority).list(); } else if
			 * (dashboardFilter.getRegion() != null) { region =
			 * dashboardFilter.getRegion(); performanceMapping = currentSession
			 * .createSQLQuery( RmsNamedQuery.getMyTeamPerformanceCount())
			 * .setParameter("employee_id", employeeId) .setParameter("region",
			 * region).list(); } else if (dashboardFilter.getReviewer() != null)
			 * { reviewer = dashboardFilter.getReviewer(); performanceMapping =
			 * currentSession .createSQLQuery(
			 * RmsNamedQuery.getMyTeamPerformanceCount())
			 * .setParameter("employee_id", employeeId)
			 * .setParameter("reviewer", reviewer).list(); } else if
			 * ((dashboardFilter.getToTime() != null) &&
			 * (dashboardFilter.getFromTime() != null)) { toTime =
			 * dashboardFilter.getToTime(); fromTime =
			 * dashboardFilter.getFromTime(); performanceMapping =
			 * currentSession .createSQLQuery(
			 * RmsNamedQuery.getMyTeamPerformanceCount())
			 * .setParameter("employee_id", employeeId) .setParameter("toTime",
			 * toTime) .setParameter("fromTime", fromTime).list(); }
			 */
		} else {
			performanceMapping = currentSession
					.createSQLQuery(RmsNamedQuery.getMyPerformanceCount())
					.setParameter("employee_id", employeeId).list();
		}
		System.out.println("performanceMapping My Ticket Pending Status"
				+ performanceMapping);
		return performanceMapping;
	}

	/**** My Ticket Pending Status (For My Review Count) ****/
	@SuppressWarnings({ "unchecked", "null" })
	public List<Object[]> findForMyReviewTicketStatusCountByEmployeeId(
			String employeeId, dashboardFilter dashboardFilter) {

		Session currentSession = (Session) getEntityManager().getDelegate();
		List<Object[]> performanceMapping = null;
		String assignee = "";
		String landscape = "";
		String module = "";
		String priority = "";
		String region = "";
		String reviewer = "";
		Date toTime = null;
		Date fromTime = null;

		if (dashboardFilter != null) {

			/* if (dashboardFilter.getModule() != null) { */
			assignee = dashboardFilter.getAssignee();
			landscape = dashboardFilter.getLandscape();
			priority = dashboardFilter.getPriority();
			region = dashboardFilter.getRegion();
			module = dashboardFilter.getModule();
			reviewer = dashboardFilter.getReviewer();
			toTime = dashboardFilter.getToTime();
			fromTime = dashboardFilter.getFromTime();

			List<Integer> regionIDsInt = new ArrayList<Integer>();
			List<Integer> regionIDs = new ArrayList<Integer>();
			if (region != null || region != "") {
				if (!(region.equals("-1"))) {
					regionIDs = getAllRegionIDs(region);
					/*
					 * if (regionIDs.size() != 0) { for
					 * (org.yash.rms.domain.Region regions : regionIDs) {
					 * regionIDsInt.add(regions.getId()); } }
					 */
				}
			}
			System.out.println("get ALl Region IDzzz ::" + regionIDs);

			Query query = currentSession
					.createSQLQuery(
							RmsNamedQuery
									.getForMyReviewCountFiltered(regionIDs))
					.setParameter("employeeId", employeeId)
					.setParameter("assignee", assignee)
					.setParameter("landscape", landscape)
					.setParameter("module", module)
					.setParameter("priority", priority)
					// .setParameterList("region", Arrays.asList(regionIDs))
					.setParameter("reviewer", reviewer)
					.setParameter("toTime", toTime)
					.setParameter("fromTime", fromTime);
			if (regionIDs.size() > 0) {
				// 26, 43, 55, 56, 80, 81, 111, 116, 117, 118
				query.setParameterList("region", regionIDs);
			}
			performanceMapping = query.list();
		} else {
			System.out.println("Without filter");
			performanceMapping = currentSession
					.createSQLQuery(RmsNamedQuery.getForMyReviewCount())
					.setParameter("employeeId", employeeId).list();
		}
		System.out.println("For my review count query: " + performanceMapping);
		return performanceMapping;
	}

	public List<Landscape> getAllLandscape() {
		List<Landscape> list = new ArrayList<Landscape>();
		try {

			Criteria criteria =((Session) getEntityManager().getDelegate())
					.createCriteria(Landscape.class);
			list = criteria.list();
		} catch (HibernateException ex) {
			ex.printStackTrace();
			logger.error("EXCEPTION :: IN GETTING PROJECT LIST "
					+ ex.getMessage());
			throw ex;

		}
		logger.info("--------ProjectDaoImpl findAll method start-------");
		return list;
	}

	public List<RootCause> getAllRootCause() {
		List<RootCause> list = new ArrayList<RootCause>();
		try {

			Criteria criteria = ((Session) getEntityManager().getDelegate())
					.createCriteria(RootCause.class);
			list = criteria.list();
		} catch (HibernateException ex) {
			ex.printStackTrace();
			logger.error("EXCEPTION :: IN GETTING PROJECT LIST "
					+ ex.getMessage());
			throw ex;

		}
		return list;
	}

	public List<Region> getAllRegion() {
		logger.info("--------CATicketDaoImpl getAllRegion method start-------");
		List<Region> list = new ArrayList<Region>();
		try {

			Criteria criteria = ((Session) getEntityManager().getDelegate())
					.createCriteria(Region.class);
			/*
			 * ProjectionList p1 = Projections.projectionList();
			 * p1.add(Projections.distinct(Projections.property("regionName")));
			 * p1.add(Projections.property("id"));
			 */
			/*
			 * criteria.setProjection(Projections.property("id"));
			 * criteria.setProjection(Projections.distinct(Projections
			 * .property("regionName")));
			 */

			/*
			 * .setProjection(Projections.property("id")) .setProjection(
			 * Projections.distinct(Projections .property("regionName")));
			 */
			/* criteria.setProjection(p1); */
			list = criteria.list();
			System.out.println("region List :::" + list);
		} catch (HibernateException ex) {
			ex.printStackTrace();
			logger.error("EXCEPTION :: IN GETTING PROJECT LIST "
					+ ex.getMessage());
			throw ex;

		}
		logger.info("--------CATicketDaoImpl getAllRegion method end-------");
		return list;
	}

	public List<Unit> getAllUnit() {
		List<Unit> list = new ArrayList<Unit>();
		try {

			Criteria criteria = ((Session) getEntityManager().getDelegate())
					.createCriteria(Unit.class);
			list = criteria.list();
		} catch (HibernateException ex) {
			ex.printStackTrace();
			logger.error("EXCEPTION :: IN GETTING PROJECT LIST "
					+ ex.getMessage());
			throw ex;

		}
		logger.info("--------ProjectDaoImpl findAll method start-------");
		return list;
	}

	/**** Team Ticket Pending Status ****/
	@SuppressWarnings({ "unchecked", "null" })
	public List<Object[]> findTeamTicketStatusCountByEmployeeId(
			String employeeId, dashboardFilter dashboardFilter) {

		Session currentSession = (Session) getEntityManager().getDelegate();
		List<Object[]> performanceMapping = null;
		String assignee = "";
		String landscape = "";
		String module = "";
		String priority = "";
		String region = "";
		String reviewer = "";
		Date toTime;
		Date fromTime;

		if (dashboardFilter != null) {
			assignee = dashboardFilter.getAssignee();
			landscape = dashboardFilter.getLandscape();
			priority = dashboardFilter.getPriority();
			region = dashboardFilter.getRegion();
			module = dashboardFilter.getModule();
			reviewer = dashboardFilter.getReviewer();
			toTime = dashboardFilter.getToTime();
			fromTime = dashboardFilter.getFromTime();

			// List<Integer> regionIDsInt = new ArrayList<Integer>();
			List<Integer> regionIDs = new ArrayList<Integer>();
			if (!(region.equals("-1"))) {
				regionIDs = getAllRegionIDs(region);
				/*
				 * if (regionIDs.size() != 0) { for (org.yash.rms.domain.Region
				 * regions : regionIDs) { regionIDsInt.add(regions.getId()); } }
				 */
			}

			Query query = currentSession
					.createSQLQuery(
							RmsNamedQuery
									.getMyTeamPerformanceCountFiltered(regionIDs))
					.setParameter("employee_id", employeeId)
					.setParameter("assignee", assignee)
					.setParameter("landscape", landscape)
					.setParameter("module", module)
					.setParameter("priority", priority)
					// .setParameter("region", region)
					// .setParameterList("region", Arrays.asList(regionIDs))
					.setParameter("reviewer", reviewer)
					.setParameter("toTime", toTime)
					.setParameter("fromTime", fromTime);
			if (regionIDs.size() > 0) {
				// 26, 43, 55, 56, 80, 81, 111, 116, 117, 118
				query.setParameterList("region", regionIDs);
			}
			performanceMapping = query.list();

			/*
			 * else if (dashboardFilter.getLandscape() != null) { landscape =
			 * dashboardFilter.getLandscape(); performanceMapping =
			 * currentSession .createSQLQuery(
			 * RmsNamedQuery.getMyTeamPerformanceCount())
			 * .setParameter("employee_id", employeeId)
			 * .setParameter("landscape", landscape).list(); } else if
			 * (dashboardFilter.getModule() != null) { module =
			 * dashboardFilter.getModule(); performanceMapping = currentSession
			 * .createSQLQuery( RmsNamedQuery.getMyTeamPerformanceCount())
			 * .setParameter("employee_id", employeeId) .setParameter("module",
			 * module).list(); } else if (dashboardFilter.getPriority() != null)
			 * { priority = dashboardFilter.getPriority(); performanceMapping =
			 * currentSession .createSQLQuery(
			 * RmsNamedQuery.getMyTeamPerformanceCount())
			 * .setParameter("employee_id", employeeId)
			 * .setParameter("priority", priority).list(); } else if
			 * (dashboardFilter.getRegion() != null) { region =
			 * dashboardFilter.getRegion(); performanceMapping = currentSession
			 * .createSQLQuery( RmsNamedQuery.getMyTeamPerformanceCount())
			 * .setParameter("employee_id", employeeId) .setParameter("region",
			 * region).list(); } else if (dashboardFilter.getReviewer() != null)
			 * { reviewer = dashboardFilter.getReviewer(); performanceMapping =
			 * currentSession .createSQLQuery(
			 * RmsNamedQuery.getMyTeamPerformanceCount())
			 * .setParameter("employee_id", employeeId)
			 * .setParameter("reviewer", reviewer).list(); } else if
			 * ((dashboardFilter.getToTime() != null) &&
			 * (dashboardFilter.getFromTime() != null)) { toTime =
			 * dashboardFilter.getToTime(); fromTime =
			 * dashboardFilter.getFromTime(); performanceMapping =
			 * currentSession .createSQLQuery(
			 * RmsNamedQuery.getMyTeamPerformanceCount())
			 * .setParameter("employee_id", employeeId) .setParameter("toTime",
			 * toTime) .setParameter("fromTime", fromTime).list(); }
			 */
		} else {
			System.out.println("ooolllddd");
			performanceMapping = currentSession
					.createSQLQuery(RmsNamedQuery.getMyTeamPerformanceCount())
					.setParameter("employee_id", employeeId).list();
		}
		System.out.println("performanceMapping Team Ticket Pending Status "
				+ performanceMapping);
		return performanceMapping;
	}

	public List<Object[]> findMyPerformance(String employeeId) {
		Session currentSession = (Session) getEntityManager().getDelegate();
		Criteria criteria = currentSession.createCriteria(CATicket.class).add(
				Restrictions.eq("assigneeId.employeeId",
						Integer.parseInt(employeeId)));
		return criteria.list();
	}

	public List<Object[]> findMyTeamPerformance(String employeeId) {
		Session currentSession = (Session) getEntityManager().getDelegate();

		/* method to be added by Sonam */
		/*
		 * Criteria criteria =
		 * currentSession.createCriteria(CATicket.class).add(
		 * Restrictions.eq("assigneeId", employeeId));
		 */
		return null;
	}

	public List<CATicket> getIRMReveiwerProjectName(List<Integer> projectId,
			List<Integer> employeeIdList) {
		Session currentSession = (Session) getEntityManager().getDelegate();
		List<CATicket> list = new ArrayList<CATicket>();
		Criterion criterion = null;
		try {
			Criteria criteria = currentSession
					.createCriteria(CATicket.class);
			if (employeeIdList != null) {
				criterion = Restrictions.or(Restrictions.in(
						"reviewer.employeeId", employeeIdList), Restrictions
						.in("assigneeId.employeeId", employeeIdList));
				criteria.add(criterion);
				list = criteria.list();
			}
		} catch (HibernateException e) {
			e.printStackTrace();
			logger.error("Exception occured in saveRework method at DAO layer:-"
					+ e);
		}

		return list;

	}

	public List<CATicket> getIRMAssigneeReviewerList(List<Integer> projectIds) {
		Session currentSession = (Session) getEntityManager().getDelegate();
		List<CATicket> list = new ArrayList<CATicket>();
		Criterion criterion = null;
		try {
			Criteria criteria = currentSession
					.createCriteria(CATicket.class);
			if (projectIds != null) {
				criteria.add(Restrictions.in("moduleId.id", projectIds));
				list = criteria.list();
			}
		} catch (HibernateException e) {
			e.printStackTrace();
			logger.error("Exception occured in saveRework method at DAO layer:-"
					+ e);
		}

		return list;

	}

	public List<CATicketSubProcess> findSubProcesses(int processId) {
		List<CATicketSubProcess> subProcesses = null;
		try {

			Criteria criteria = ((Session) getEntityManager().getDelegate())
					.createCriteria(CATicketSubProcess.class);
			criteria.add(Restrictions.eq("processId.id", processId));
			subProcesses = criteria.list();
		} catch (HibernateException ex) {
			ex.printStackTrace();
			logger.error("EXCEPTION :: IN GETTING PROJECT LIST "
					+ ex.getMessage());
			throw ex;

		} finally {
			if (null == subProcesses) {
				subProcesses = new ArrayList<CATicketSubProcess>();
			}
		}
		logger.info("--------ProjectDaoImpl findAll method start-------");
		return subProcesses;
	}

	public List<Region> findRegionByUnit(int unitId) {
		List<Region> region = null;
		try {

			Criteria criteria = ((Session) getEntityManager().getDelegate())
					.createCriteria(Region.class);
			criteria.add(Restrictions.eq("unitId.id", unitId));
			region = criteria.list();
		} catch (HibernateException ex) {
			ex.printStackTrace();
			logger.error("Exception in Regoin List : " + ex.getMessage());
			throw ex;

		} finally {
			if (null == region) {
				region = new ArrayList<Region>();
			}
		}
		logger.info("--------ProjectDaoImpl findAll method start-------");
		return region;
	}

	/******** My Performance *************/
	@SuppressWarnings("null")
	public List<CATicket> findMyTickets(int employeeId,
			dashboardFilter dashboardFilter) {
		List<CATicket> caTickets = null;
		try {

			Criteria criteria = ((Session) getEntityManager().getDelegate())
					.createCriteria(CATicket.class);

			if (dashboardFilter != null) {

				String assignee = "";
				String landscape = "";
				String module = "";
				String priority = "";
				String region = "";
				String reviewer = "";
				String groupName = "Tier 2";
				Date toTime;
				Date fromTime;

				assignee = dashboardFilter.getAssignee();
				landscape = dashboardFilter.getLandscape();
				priority = dashboardFilter.getPriority();
				region = dashboardFilter.getRegion();
				module = dashboardFilter.getModule();
				reviewer = dashboardFilter.getReviewer();
				toTime = dashboardFilter.getToTime();
				fromTime = dashboardFilter.getFromTime();

				List<Integer> regionIDsInt = new ArrayList<Integer>();
				List<Integer> regionIDs = new ArrayList<Integer>();
				if (region != null || region != "") {
					if (!(region.equals("-1"))) {
						regionIDs = getAllRegionIDs(region);
						/*
						 * if (regionIDs.size() != 0) { for
						 * (org.yash.rms.domain.Region regions : regionIDs) {
						 * regionIDsInt.add(regions.getId()); } }
						 */
					}
				}

				if (assignee != null && !(assignee.equals(""))
						&& !(assignee.equals("-1"))) {
					System.out.println("checkin Values :: " + assignee);
					criteria.add(Restrictions.eq("assigneeId.id",
							Integer.parseInt(assignee)));
				}
				if (landscape != null && !(landscape.equals(""))
						&& !(landscape.equals("-1"))) {
					System.out.println("checkin Values :: " + landscape);
					criteria.add(Restrictions.eq("landscapeId.id",
							Integer.parseInt(landscape)));
				}
				if (priority != null && !(priority.equals(""))
						&& !(priority.equals("-1"))) {
					System.out.println("checkin Values :: " + priority);
					criteria.add(Restrictions.eq("priority", priority));
				}
				if (module != null && !(module.equals(""))
						&& !(module.equals("-1"))) {
					System.out.println("checkin Values :: " + module);
					criteria.add(Restrictions.eq("moduleId.id",
							Integer.parseInt(module)));
				}
				if (region != null && !(region.equals(""))
						&& !(region.equals("-1"))) {
					System.out.println("checkin Values :: " + region);
					criteria.add(Restrictions.in("region.id", regionIDs));
				}
				if (reviewer != null && !(reviewer.equals(""))
						&& !(reviewer.equals("-1"))) {
					System.out.println("checkin Values :: " + reviewer);
					criteria.add(Restrictions.eq("reviewer.employeeId",
							Integer.parseInt(reviewer)));
				}
				if ((toTime != null && !(toTime.equals("")) && !(toTime
						.equals("-1")))
						&& (fromTime != null && !(fromTime.equals("")) && !(fromTime
								.equals("-1")))) {
					System.out.println("checkin Values :: " + reviewer);
					criteria.add(Restrictions.ge("creationDate", fromTime))
							.add(Restrictions.lt("creationDate", toTime));
				}
				criteria.add(Restrictions.eq("groupName", groupName));
			}
			criteria.add(Restrictions.eq("assigneeId.id", employeeId));
			caTickets = criteria.list();
		} catch (HibernateException ex) {
			ex.printStackTrace();
			logger.error("Exception in findMyTickets : " + ex.getMessage());
			throw ex;

		} finally {
			if (null == caTickets) {
				caTickets = new ArrayList<CATicket>();
			}
		}
		logger.info("--------ProjectDaoImpl findAll method start-------");
		return caTickets;
	}

	/******** My Team Performance *************/
	public List<CATicket> findMyTeamTickets(int employeeId,
			dashboardFilter dashboardFilter) {
		List<CATicket> caTickets = null;
		try {

			Session currentSession = (Session) getEntityManager().getDelegate();
			Query query;

			if (dashboardFilter != null) {

				String assignee = "";
				String landscape = "";
				String module = "";
				String priority = "";
				String region = "";
				String reviewer = "";
				Date toTime;
				Date fromTime;

				assignee = dashboardFilter.getAssignee();
				landscape = dashboardFilter.getLandscape();
				priority = dashboardFilter.getPriority();
				region = dashboardFilter.getRegion();
				module = dashboardFilter.getModule();
				reviewer = dashboardFilter.getReviewer();
				toTime = dashboardFilter.getToTime();
				fromTime = dashboardFilter.getFromTime();

				List<Integer> regionIDsInt = new ArrayList<Integer>();
				List<Integer> regionIDs = new ArrayList<Integer>();
				if (region != null || region != "") {
					if (!(region.equals("-1"))) {
						regionIDs = getAllRegionIDs(region);
					}
				}

				query = currentSession
						.createSQLQuery(
								RmsNamedQuery
										.getMyTeamPerformanceStatusFiltered(regionIDs))
						.addEntity(CATicket.class)
						.setParameter("currentReportingManager", employeeId)
						.setParameter("currentReportingManagerTwo", employeeId)
						.setParameter("offshoreDelMgr", employeeId)
						.setParameter("assignee", assignee)
						.setParameter("landscape", landscape)
						.setParameter("priority", priority)
						// .setParameter("region",
						// "1, 2, 3, 4, 8, 18, 19, 20, 21, 22, 23, 39, 40, 42, 44, 45, 46, 47, 48, 58, 59, 60, 64, 65, 66, 67, 76, 77, 79, 83, 84, 85, 86, 87, 88, 89, 92, 95, 102, 103, 104, 106, 107, 108, 109, 110, 119, 121, 122")
						// .setParameterList("region", regionIDs)
						// .setParameterList("region", Arrays.asList(regionIDs))
						.setParameter("module", module)
						.setParameter("reviewer", reviewer)
						.setParameter("toTime", toTime)
						.setParameter("fromTime", fromTime);
				if (regionIDs.size() > 0) {
					// 26, 43, 55, 56, 80, 81, 111, 116, 117, 118
					query.setParameterList("region", regionIDs);
				}
			} else {
				query = currentSession
						.createSQLQuery(
								RmsNamedQuery.getMyTeamPerformanceStatus())
						.addEntity(CATicket.class)
						.setParameter("currentReportingManager", employeeId)
						.setParameter("currentReportingManagerTwo", employeeId)
						.setParameter("offshoreDelMgr", employeeId);
				// .setParameter("employee_id", employeeId);
			}
			caTickets = query.list();

		} catch (HibernateException ex) {
			ex.printStackTrace();
			logger.error("Exception in findMyTeamTickets : " + ex.getMessage());
			throw ex;

		} finally {
			if (null == caTickets) {
				caTickets = new ArrayList<CATicket>();
			}
		}
		logger.info("--------ProjectDaoImpl findAll method start-------");
		return caTickets;
	}

	public List<SolutionReview> saveOrUpdateSolutionReview(
			SolutionReview solutionReview) {

		logger.info("--------ResourceDaoImpl saveT3 method start-------");
		Session currentSession = (Session) getEntityManager().getDelegate();
		// currentSession.saveOrUpdate(t3Contribution);
		List<SolutionReview> solutionReviewList = new ArrayList<SolutionReview>();
		SolutionReview review = null;
		try {
			if (solutionReview.getId() != null) {
				currentSession.saveOrUpdate(solutionReview);
				review = (SolutionReview) currentSession.get(
						SolutionReview.class, solutionReview.getId());
				solutionReviewList.add(review);
			} else {
				Serializable id = currentSession.save(solutionReview);
				if (id != null) {
					review = (SolutionReview) currentSession.get(
							SolutionReview.class, id);
					solutionReviewList.add(review);
				}
			}

		} catch (HibernateException e) {
			e.printStackTrace();
			logger.error("Exception occured in saveT3 method at DAO layer:-"
					+ e);
		}
		return solutionReviewList;
	}

	public void saveChangeFunctionality(int ticketId, int assigneeName,
			String priority, String reviewer) {
		SQLQuery query = ((Session) getEntityManager().getDelegate())
				.createSQLQuery(
						"UPDATE ca_ticket SET reviewer_id=?,emp_id=?,priority=? WHERE id=?");
		query.setInteger(0, Integer.parseInt(reviewer));
		query.setInteger(1, assigneeName);
		query.setString(2, priority);
		query.setInteger(3, ticketId);
		query.executeUpdate();
	}

	public void saveTransferFunctionality(int ticketId, String assigneeName,
			int moduleId, String iRMId, String justified, String reason) {
		SQLQuery query = ((Session) getEntityManager().getDelegate())
				.createSQLQuery(
						"UPDATE ca_ticket SET reviewer_id=?,emp_id=?,module=?,reason_for_hopping=?,justified_hopping=? WHERE id=?");
		query.setInteger(0, Integer.parseInt(iRMId));
		query.setInteger(1, Integer.parseInt(assigneeName));
		query.setInteger(2, moduleId);
		query.setString(3, reason);
		query.setString(4, justified);
		query.setInteger(5, ticketId);
		query.executeUpdate();

	}

	public List<Project> findModuleByEmployee(int employeeId) {

		List<Project> projectList = null;
		try {

			Session currentSession = (Session) getEntityManager().getDelegate();
			Query query = currentSession
					.createSQLQuery(RmsNamedQuery.findModuleByEmployee())
					.addEntity(Project.class)
					.setParameter("employee_id", employeeId);
			projectList = query.list();

		} catch (HibernateException ex) {
			ex.printStackTrace();
			logger.error("Exception in findModuleByEmployee List : "
					+ ex.getMessage());
			throw ex;

		} finally {
			if (null == projectList) {
				projectList = new ArrayList<Project>();
			}
		}
		logger.info("--------ProjectDaoImpl findAll method start-------");
		return projectList;

	}

	public SolutionReview getLatestReviewDateById(int caTicketId) {
		Session currentSession = (Session) getEntityManager().getDelegate();
		Query query;

		query = currentSession
				.createSQLQuery(
						"SELECT * FROM solution_review_ost WHERE ca_ticket_id=:caTicketId ORDER BY review_date DESC LIMIT 1")
				.addEntity(SolutionReview.class)
				.setParameter("caTicketId", caTicketId);

		SolutionReview solutionReview = (SolutionReview) query.uniqueResult();
		return solutionReview;
	}

	public Crop getLatestCropById(int caTicketId) {
		Session currentSession = (Session) getEntityManager().getDelegate();
		Query query;

		query = currentSession
				.createSQLQuery(
						"SELECT * FROM crop_ost WHERE ca_ticket_id=:caTicketId ORDER BY updated_date DESC LIMIT 1")
				.addEntity(Crop.class).setParameter("caTicketId", caTicketId);

		Crop crop = (Crop) query.uniqueResult();
		return crop;
	}

	public boolean deleteT3(int id) {
		boolean isDeleted = false;
		try {

			Session currentSession = (Session) getEntityManager().getDelegate();
			Query query = currentSession
					.createQuery("delete T3Contribution where id =:id");
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

	public boolean deleteSolutionReview(int id) {
		boolean isDeleted = false;
		try {

			Session currentSession = (Session) getEntityManager().getDelegate();
			Query query = currentSession
					.createQuery("delete SolutionReview where id =:id");
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

	public boolean deleteDefectLog(int id) {
		boolean isDeleted = false;
		try {

			Session currentSession = (Session) getEntityManager().getDelegate();
			Query query = currentSession
					.createQuery("delete DefectLog where id =:id");
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

	public boolean deleteCrop(int id) {
		boolean isDeleted = false;
		try {

			Session currentSession = (Session) getEntityManager().getDelegate();
			Query query = currentSession
					.createQuery("delete Crop where id =:id");
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

	public boolean deleteRework(int id) {
		boolean isDeleted = false;
		try {

			Session currentSession = (Session) getEntityManager().getDelegate();
			Query query = currentSession
					.createQuery("delete Rework where id =:id");
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

	public List<CATicket> getTicketsForReview(int currentLoggedInUserId) {
		Session currentSession = (Session) getEntityManager().getDelegate();
		Query query;

		query = currentSession
				.createSQLQuery(RmsNamedQuery.getTicketsForReview())
				.addEntity(CATicket.class)
				.setParameter("employeeId", currentLoggedInUserId);
		return query.list();
	}

	public CATicket getTicketByTicketNumber(int ticketId) {
		Session currentSession = (Session) getEntityManager().getDelegate();
		Criteria criteria = currentSession.createCriteria(CATicket.class).add(
				Restrictions.eq("id", ticketId));
		return (CATicket) criteria.uniqueResult();
	}

	public boolean isTicketAlreadyExist(BigInteger ticketNumber) {
		logger.info("--------CATicketDaoImpl isTicketAlreadyExist method start-------");
		boolean isAlreadyExist = false;
		Session currentSession = (Session) getEntityManager().getDelegate();
		Criteria criteria = currentSession.createCriteria(CATicket.class).add(
				Restrictions.eq("caTicketNo", ticketNumber));
		List<CATicket> list = criteria.list();
		if (list.size() > 0) {
			int reopenFrequency = list.get(0).getReopenFrequency();
			if (reopenFrequency > 0) {
				isAlreadyExist = false;
			} else {
				isAlreadyExist = true;
			}

		} else {
			isAlreadyExist = false;
		}
		logger.info("--------CATicketDaoImpl getEmployisTicketAlreadyExist method end-------");
		return isAlreadyExist;
	}

	public List<Resource> getEmployeeByModule(int projectId) {
		logger.info("--------CATicketDaoImpl getEmployeeByModule method start-------");
		Session currentSession = (Session) getEntityManager().getDelegate();
		Query query = currentSession
				.createSQLQuery(RmsNamedQuery.getEmployeeByModuleQuery())
				.addEntity(Resource.class).setParameter("projectId", projectId);
		logger.info("--------CATicketDaoImpl getEmployeeByModule method nd-------");
		return query.list();
	}

	public CATicket getTicketByTicketNumber(BigInteger caticketNumber) {
		Session currentSession = (Session) getEntityManager().getDelegate();
		Criteria criteria = currentSession.createCriteria(CATicket.class).add(
				Restrictions.eq("caTicketNo", caticketNumber));
		return (CATicket) criteria.uniqueResult();
	}

	public List<Integer> getAllRegionIDs(String regionName) {
		Session currentSession = (Session) getEntityManager().getDelegate();
		// Query query = currentSession.createSQLQuery(
		// RmsNamedQuery.getAllRegionIdsfromRegionName()).setParameter(
		// "regionName", regionName);

		Query query = currentSession.createQuery(
				"SELECT id FROM Region WHERE regionName =:regionName")
				.setParameter("regionName", regionName);
		/*
		 * ProjectionList p1 = Projections.projectionList();
		 * p1.add(Projections.distinct(Projections.property("id")));
		 * criteria.setProjection(p1);
		 */

		System.out.println("Lisst off getAllRegionIDs:::" + query.list());
		return query.list();
	}

	public List<String> getDistinctRegionNames() {
		logger.info("--------CATicketDaoImpl getDistinctRegionNames method start-------");
		Criteria criteria = ((Session) getEntityManager().getDelegate())
				.createCriteria(Region.class);

		ProjectionList p1 = Projections.projectionList();
		p1.add(Projections.distinct(Projections.property("regionName")));

		criteria.setProjection(p1);
		logger.info("--------CATicketDaoImpl getDistinctRegionNames method end-------");
		return criteria.list();
	}

	public Unit getUnit(int id) {
		return (Unit)((Session) getEntityManager().getDelegate())
				.createCriteria(Unit.class).add(Restrictions.eq("id", id))
				.uniqueResult();
	}

	public Region getRegion(int id) {
		return (Region) ((Session) getEntityManager().getDelegate())
				.createCriteria(Region.class).add(Restrictions.eq("id", id))
				.uniqueResult();
	}

	public Landscape getLandscape(int id) {
		return (Landscape) ((Session) getEntityManager().getDelegate())
				.createCriteria(Landscape.class).add(Restrictions.eq("id", id))
				.uniqueResult();
	}

	public String getSolutionReadyReviewFlag(BigInteger caTicketNo) {
		Session currentSession = (Session) getEntityManager().getDelegate();
		return (String) currentSession
				.createSQLQuery(
						"SELECT solution_ready_for_review FROM ca_ticket WHERE ticket_number=? ")
				.setBigInteger(0, caTicketNo).uniqueResult();

	}

	public String getDescription(BigInteger caTicketNo) {
		Session currentSession = (Session) getEntityManager().getDelegate();
		return (String) currentSession
				.createSQLQuery(
						"SELECT description FROM ca_ticket WHERE ticket_number=? ")
				.setBigInteger(0, caTicketNo).uniqueResult();

	}

	public int getAssingeeId(BigInteger caTicketNo) {
		Session currentSession = (Session) getEntityManager().getDelegate();
		return (Integer) currentSession
				.createSQLQuery(
						"SELECT emp_id FROM ca_ticket WHERE ticket_number=? ")
				.setBigInteger(0, caTicketNo).uniqueResult();
	}

	public int getReviewerId(BigInteger caTicketNo) {
		Session currentSession = (Session) getEntityManager().getDelegate();
		return (Integer) currentSession
				.createSQLQuery(
						"SELECT reviewer_id FROM ca_ticket WHERE ticket_number=? ")
				.setBigInteger(0, caTicketNo).uniqueResult();
	}

	public String getAging(BigInteger caTicketNo) {
		Session currentSession = (Session) getEntityManager().getDelegate();
		return (String) currentSession
				.createSQLQuery(
						"SELECT aging FROM ca_ticket WHERE ticket_number=? ")
				.setBigInteger(0, caTicketNo).uniqueResult();
	}

	public Date getCreationDate(BigInteger caTicketNo) {
		Session currentSession = (Session) getEntityManager().getDelegate();
		return (Date) currentSession
				.createSQLQuery(
						"SELECT creation_date FROM ca_ticket WHERE ticket_number=? ")
				.setBigInteger(0, caTicketNo).uniqueResult();
	}

	public int getModule(BigInteger caTicketNo) {
		Session currentSession = (Session) getEntityManager().getDelegate();
		return (Integer) currentSession
				.createSQLQuery(
						"SELECT module FROM ca_ticket WHERE ticket_number=? ")
				.setBigInteger(0, caTicketNo).uniqueResult();
	}

	public int getUnitId(BigInteger caTicketNo) {
		Session currentSession = (Session) getEntityManager().getDelegate();
		return (Integer) currentSession
				.createSQLQuery(
						"SELECT unit_id FROM ca_ticket WHERE ticket_number=? ")
				.setBigInteger(0, caTicketNo).uniqueResult();
	}

	public int getLandscapeId(BigInteger caTicketNo) {
		Session currentSession = (Session) getEntityManager().getDelegate();
		return (Integer) currentSession
				.createSQLQuery(
						"SELECT landscape_id FROM ca_ticket WHERE ticket_number=? ")
				.setBigInteger(0, caTicketNo).uniqueResult();
	}

	public int getRegionId(BigInteger caTicketNo) {
		Session currentSession = (Session) getEntityManager().getDelegate();
		return (Integer) currentSession
				.createSQLQuery(
						"SELECT region FROM ca_ticket WHERE ticket_number=? ")
				.setBigInteger(0, caTicketNo).uniqueResult();
	}

	public List<ReasonHopping> getAllReasonForHopping() {
		List<ReasonHopping> list = new ArrayList<ReasonHopping>();
		try {

			Criteria criteria = ((Session) getEntityManager().getDelegate())
					.createCriteria(ReasonHopping.class);
			list = criteria.list();
		} catch (HibernateException ex) {
			ex.printStackTrace();
			logger.error("EXCEPTION :: IN GETTING PROJECT LIST "
					+ ex.getMessage());
			throw ex;

		}
		logger.info("--------ProjectDaoImpl findAll method start-------");
		return list;
	}

	public List<ReasonReopen> getAllReasonForReopen() {
		List<ReasonReopen> list = new ArrayList<ReasonReopen>();
		try {

			Criteria criteria = ((Session) getEntityManager().getDelegate())
					.createCriteria(ReasonReopen.class);
			list = criteria.list();
		} catch (HibernateException ex) {
			ex.printStackTrace();
			logger.error("EXCEPTION :: IN GETTING PROJECT LIST "
					+ ex.getMessage());
			throw ex;

		}
		logger.info("--------ProjectDaoImpl findAll method start-------");
		return list;
	}

	public List<ReasonForSLAMissed> getAllReasonForSLAMissed() {
		List<ReasonForSLAMissed> list = new ArrayList<ReasonForSLAMissed>();
		try {

			Criteria criteria = ((Session) getEntityManager().getDelegate())
					.createCriteria(ReasonForSLAMissed.class);
			list = criteria.list();
		} catch (HibernateException ex) {
			ex.printStackTrace();
			logger.error("EXCEPTION :: IN GETTING PROJECT LIST "
					+ ex.getMessage());
			throw ex;

		}
		logger.info("--------ProjectDaoImpl findAll method start-------");
		return list;
	}

}
