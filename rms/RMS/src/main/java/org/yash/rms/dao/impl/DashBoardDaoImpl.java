package org.yash.rms.dao.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.yash.rms.dao.DashBoardDao;
import org.yash.rms.dao.ResourceDao;
import org.yash.rms.domain.DashboardComplianceWidget;
import org.yash.rms.domain.DashboardWidgets;
import org.yash.rms.domain.Dashboard_temp;
import org.yash.rms.domain.Dashboardtempsecond;
import org.yash.rms.domain.Resource;
import org.yash.rms.namedquery.RmsNamedQuery;

@Repository("DashBoardDao")
@Transactional
public class DashBoardDaoImpl implements DashBoardDao {

	private static final Logger logger = LoggerFactory.getLogger(DashBoardDaoImpl.class);
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
	@Qualifier("ResourceDao")
	ResourceDao resourceDao;

	public List<Object[]> findUserdashboard(int empID) {
		Session currentSession = (Session) getEntityManager().getDelegate();
		Query query;

		query = currentSession.createSQLQuery(RmsNamedQuery.getallUserInfoStatus()).setParameter("empID", empID);

		return query.list();

	}

	public List<Object[]> findUserTimeSheet(int empID) {
		Session currentSession = (Session) getEntityManager().getDelegate();
		Query query;

		query = currentSession.createSQLQuery(RmsNamedQuery.getallTimeSheetStatus()).setParameter("empID", empID);

		return query.list();

	}

	public List<Object[]> findUserstatusoflastFourMonth(int empID) {
		Session currentSession = (Session) getEntityManager().getDelegate();
		Query query;

		query = currentSession.createSQLQuery(RmsNamedQuery.getallstatusoflastfourMonth()).setParameter("empID", empID);

		return query.list();

	}

	public List<Object[]> findUsertodolist(int empID) {
		Session currentSession = (Session) getEntityManager().getDelegate();
		Query query;

		query = currentSession.createSQLQuery(RmsNamedQuery.getalltodolist()).setParameter("empID", empID);
		return query.list();
	}

	public List<Object[]> findManagerBillingStatusList(int empID) {

		Session currentSession = (Session) getEntityManager().getDelegate();
		Query query;

		query = currentSession.createSQLQuery(RmsNamedQuery.getManagerBillingStatus()).setParameter("empID", empID);

		return query.list();
	}

	public List<Object[]> findDeliveryManagerBillingStatusList(List<String> projectName) {
		Session currentSession = (Session) getEntityManager().getDelegate();
		Query query;

		query = currentSession.createSQLQuery(RmsNamedQuery.getDeliveryManagerBillingStatus()).setParameterList("projectName", projectName);

		return query.list();
	}

	public List<Object[]> findDeliveryManagerResourceBillingStatus(List<String> projectName) {
		Session currentSession = (Session) getEntityManager().getDelegate();
		Query query;
		query = currentSession.createSQLQuery(RmsNamedQuery.getDeliveryManagerResourceBillingStatus()).setParameterList("projectName", projectName);

		return query.list();
	}

	public List<Object[]> findDeliveryManagerResourceBillingStatusList(int empID) {
		Session currentSession = (Session) getEntityManager().getDelegate();
		Query query;
		query = currentSession.createSQLQuery(RmsNamedQuery.getDeliveryManagerResourceBillingStatusList()).setParameter("empID", empID);

		return query.list();
	}

	public List<Object[]> findManagerTimeSheetStatus(List<Integer> empIds, Integer employeeId) {
		List<Object[]> list = new ArrayList<Object[]>();
		Session currentSession = (Session) getEntityManager().getDelegate();
		Query query;

		Resource resource = resourceDao.findByEmployeeId(employeeId);
		
		String userRole = resource.getUserRole();

		if (empIds.size() > 0) {
			if (userRole.equals("ROLE_DEL_MANAGER") || userRole.equals("ROLE_MANAGER")) {

				query = currentSession.createSQLQuery(RmsNamedQuery.getDeliveryManagerTimesheetStatusForDeliveryManager()).setParameterList("empIds", empIds).setParameter("currentUser", employeeId);

			} else {
				query = currentSession.createSQLQuery(RmsNamedQuery.getDeliveryManagerTimesheetStatus()).setParameterList("empIds", empIds).setParameter("currentUser", employeeId);
			}
			list = query.list();
		}
		return list;
	}

	public List<Object[]> findManagerTimeSheetCompliance(List<Integer> empIds) {
		List<Object[]> list = new ArrayList<Object[]>();
		Session currentSession = (Session) getEntityManager().getDelegate();
		Date date = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int dayOfWeek = c.get(Calendar.DAY_OF_WEEK) - c.getFirstDayOfWeek();
		c.add(Calendar.DAY_OF_MONTH, -dayOfWeek);
		c.add(Calendar.DAY_OF_MONTH, 6);
		Date weekEnd = c.getTime();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String newdate = formatter.format(weekEnd);
		Query query;
		if (empIds.size() > 0) {

			query = currentSession.createSQLQuery(RmsNamedQuery.getTimesheetcompliance()).setParameter("date", newdate).setParameterList("empIds", empIds);
			list = query.list();

		}
		return list;
	}

	public List<Object[]> findBGAdminTimeSheetCompliance(List<Integer> empIds) {
		List<Object[]> list = new ArrayList<Object[]>();
		Session currentSession = (Session) getEntityManager().getDelegate();
		Query query;
		Date date = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int dayOfWeek = c.get(Calendar.DAY_OF_WEEK) - c.getFirstDayOfWeek();
		c.add(Calendar.DAY_OF_MONTH, -dayOfWeek);
		c.add(Calendar.DAY_OF_MONTH, 6);
		Date weekEnd = c.getTime();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String newdate = formatter.format(weekEnd);
		if (empIds.size() > 0) {
			query = currentSession.createSQLQuery(RmsNamedQuery.getTimesheetcompliance()).setParameterList("empIds", empIds).setParameter("date", newdate);
			list = query.list();

		}
		return list;
	}

	public List<Dashboard_temp> getDatabyBGBU(List<String> project_bg_bu) {
		Session currentSession = (Session) getEntityManager().getDelegate();
		List<Dashboard_temp> projectData = null;
		List<Dashboard_temp> projectDataFinal = new ArrayList<Dashboard_temp>();
		try {
			Criteria criteria = currentSession.createCriteria(Dashboard_temp.class);
			projectData = criteria.add(Restrictions.in("project_bg_bu", project_bg_bu)).list();
			projectDataFinal.addAll(projectData);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return projectDataFinal;
	}

	public List<Dashboard_temp> getDatabyProjectName(List<String> projectNameList) {
		Session currentSession = (Session) getEntityManager().getDelegate();
		List<Dashboard_temp> projectData = new ArrayList<Dashboard_temp>();

		try {
			Criteria criteria = currentSession.createCriteria(Dashboard_temp.class);
if(projectNameList==null || projectNameList.isEmpty())
{
	projectNameList.add("No Project Assigned");
	
	}
			projectData = criteria.add(Restrictions.in("project_name", projectNameList)).list();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return projectData;
	}

	public List<Dashboardtempsecond> getBillingStatusListbyBGBU(List<String> project_bg_bu) {
		Session currentSession = (Session) getEntityManager().getDelegate();
		List<Dashboardtempsecond> projectData = null;
		List<Dashboardtempsecond> projectDataFinal = new ArrayList<Dashboardtempsecond>();
		try {
			Criteria criteria = currentSession.createCriteria(Dashboardtempsecond.class);
			projectData = criteria.add(Restrictions.in("project_bg_bu", project_bg_bu)).list();
			projectDataFinal.addAll(projectData);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return projectDataFinal;
	}

	public List<Dashboardtempsecond> getBillingStatusListbyProjectName(List<String> projectNameList) {
		Session currentSession = (Session) getEntityManager().getDelegate();
		List<Dashboardtempsecond> projectData = null;
		List<Dashboardtempsecond> projectDataFinal = new ArrayList<Dashboardtempsecond>();
		try {
			Criteria criteria = currentSession.createCriteria(Dashboardtempsecond.class);
			if(projectNameList.isEmpty())
			{
				projectNameList.add("No Project Assigned");
			}
			projectData = criteria.add(Restrictions.in("project_name", projectNameList)).list();
			projectDataFinal.addAll(projectData);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return projectDataFinal;
	}

	public List<Dashboardtempsecond> getBillingStatusListbyManagerID(int deliveryManagerID) {
		Session currentSession = (Session) getEntityManager().getDelegate();
		List<Dashboardtempsecond> projectData = null;
		List<Dashboardtempsecond> projectDataFinal = new ArrayList<Dashboardtempsecond>();
		try {
			Criteria criteria = currentSession.createCriteria(Dashboardtempsecond.class);

			projectData = criteria.add(Restrictions.eq("manager_id", deliveryManagerID)).list();
			projectDataFinal.addAll(projectData);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return projectDataFinal;
	}

	@SuppressWarnings("unchecked")
	public List<DashboardWidgets> getWidgetDataByEmployeeID(List<Integer> empIDList) {
		Session currentSession = (Session) getEntityManager().getDelegate();
		List<DashboardWidgets> projectData = new ArrayList<DashboardWidgets>();
		try {

			Criteria criteriaP = currentSession.createCriteria(DashboardWidgets.class);
			Criteria criteriaA = currentSession.createCriteria(DashboardWidgets.class);
			Criteria criteriaR = currentSession.createCriteria(DashboardWidgets.class);

			if (empIDList.size() > 0) {
				List<DashboardWidgets> listStatusP = criteriaP.add(Restrictions.in("employee_id", empIDList)).add(Restrictions.eq("status", "P")).addOrder(Order.desc("week_end_date"))
						.setMaxResults(4).list();
				List<DashboardWidgets> listStatusA = criteriaA.add(Restrictions.in("employee_id", empIDList)).add(Restrictions.eq("status", "A")).addOrder(Order.desc("week_end_date"))
						.setMaxResults(4).list();
				List<DashboardWidgets> listStatusR = criteriaR.add(Restrictions.in("employee_id", empIDList)).add(Restrictions.eq("status", "R")).addOrder(Order.desc("week_end_date"))
						.setMaxResults(4).list();

				listStatusR.addAll(listStatusP);
				listStatusA.addAll(listStatusR);
				projectData.addAll(listStatusA);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return projectData;
	}

	public List<DashboardComplianceWidget> getComplianceWidgetDataByEmployeeID(List<Integer> empIDList) {
		Session currentSession = (Session) getEntityManager().getDelegate();
		List<DashboardComplianceWidget> projectData = null;
		try {
			Criteria criteria = currentSession.createCriteria(DashboardComplianceWidget.class);
			criteria.add(Restrictions.in("employee_id", empIDList));

			projectData = criteria.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return projectData;
	}
}
