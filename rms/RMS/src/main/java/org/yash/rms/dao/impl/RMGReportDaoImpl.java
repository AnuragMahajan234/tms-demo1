package org.yash.rms.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.yash.rms.dao.RMGReportDao;

@SuppressWarnings("rawtypes")
@Repository("ResourceMetricsDao")
@Transactional
public class RMGReportDaoImpl implements RMGReportDao {

	@PersistenceContext(name = "mysql", type = PersistenceContextType.TRANSACTION)
	private EntityManager entityManager;
	
	
	public EntityManager getEntityManager() {
		return entityManager;
	}
	
	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	private static final Logger logger = LoggerFactory.getLogger(RMGReportDaoImpl.class);

	public List getResourceMetricsTable(String startDay, String endDay, String startMonth, String endMonth,
			String startYear, String endYear) {

		logger.info("-------- RMGReportDaoImpl getResourceMetricsTable method START -------");

		List resourceMetricsList = new ArrayList();

		Session session = (Session) getEntityManager().getDelegate();

		try {

			Query query = session
					.createSQLQuery(
							"CALL RMG_Resource_Metrics(:start_date, :end_date, :start_month, :end_month, :start_year, :end_year)")
					.setParameter("start_date", startDay).setParameter("end_date", endDay)
					.setParameter("start_month", startMonth).setParameter("end_month", endMonth)
					.setParameter("start_year", startYear).setParameter("end_year", endYear);

			resourceMetricsList = query.list();

		} catch (HibernateException hibernateException) {
			logger.error(
					"HibernateException occured in getResourceMetricsTable method at DAO layer:-" + hibernateException);
			hibernateException.printStackTrace();
		}
		logger.info("------------RMGReportDaoImpl getResourceMetricsTable method end------------");

		return resourceMetricsList;
	}

	public List getRRFMetricsTable(String startDay, String endDay, String startMonth, String endMonth, String startYear,
			String endYear) {

		logger.info("--------RMGReportDaoImpl getRRFMetricsTable method START -------");

		List rrfMetricsList = new ArrayList();

		Session session = (Session) getEntityManager().getDelegate();
		String startDate = new StringBuilder().append(startYear).append("-").append(startMonth).append("-")
				.append(startDay).toString();
		String endDate = new StringBuilder().append(endYear).append("-").append(endMonth).append("-").append(endDay)
				.toString();
		try {

			StringBuilder sqlQuery = new StringBuilder();
			sqlQuery.append(
					"SELECT rr.hiring_bgbu AS 'BG_BU',SUM(srr.req_resources) AS 'total position', SUM(srr.open) AS 'open', ");
			sqlQuery.append("SUM(srr.closed) AS 'closed', SUM(srr.not_fit_for_requirement) AS 'not_fit', ");
			sqlQuery.append(
					"SUM(srr.on_hold) AS 'hold', SUM(srr.lost) AS 'lost', SUM(srr.submissions) AS 'submission' FROM ");
			sqlQuery.append("request_requisition rr, skill_request_requisition srr ");
			sqlQuery.append(
					"WHERE rr.id = srr.request_id AND DATE(rr.creation_timestamp) BETWEEN STR_TO_DATE(:startDate,'%Y-%m-%d') ");
			sqlQuery.append("AND STR_TO_DATE(:endDate,'%Y-%m-%d') GROUP BY rr.hiring_bgbu ");

			Query query = session.createSQLQuery(sqlQuery.toString());
			query.setParameter("startDate", startDate);
			query.setParameter("endDate", endDate);
			rrfMetricsList = query.list();

		} catch (HibernateException e) {
			logger.error("Exception occured in getRRFMetricsTable method at DAO layer:-" + e);
			e.printStackTrace();
		}
		logger.info("------------RMGReportDaoImpl getRRFMetricsTable method end------------");

		return rrfMetricsList;
	}

	public List getResourceMetricsByBGTable(String startDay, String endDay, String startMonth, String endMonth,
			String startYear, String endYear) {

		logger.info("--------RMGReportDaoImpl getResourceMetricsByBGTable method START -------");

		List resourceMtricsByBGList = new ArrayList();

		Session session = (Session) getEntityManager().getDelegate();

		try {

			Query query = session
					.createSQLQuery(
							"CALL RMG_Resource_Metrics_byBG(:start_date, :end_date, :start_month, :end_month, :start_year, :end_year)")
					.setParameter("start_date", startDay).setParameter("end_date", endDay)
					.setParameter("start_month", startMonth).setParameter("end_month", endMonth)
					.setParameter("start_year", startYear).setParameter("end_year", endYear);

			resourceMtricsByBGList = query.list();

		} catch (HibernateException e) {
			logger.error("Exception occured in getResourceMetricsByBGTable method at DAO layer:-" + e);
			e.printStackTrace();
		}
		logger.info("------------RMGReportDaoImpl getResourceMetricsByBGTable method end------------");

		return resourceMtricsByBGList;
	}

	public List getResourceMetricsByBGBUTable(String startDay, String endDay, String startMonth, String endMonth,
			String startYear, String endYear) {

		logger.info("--------RMGReportDaoImpl getResourceMetricsByBGBUTable method start-------");

		List resourceMtricsByBGBUList = new ArrayList();

		Session session = (Session) getEntityManager().getDelegate();

		try {

			Query query = session
					.createSQLQuery(
							"CALL RMG_Resource_Metrics_by_BGBU_Report(:start_date, :end_date, :start_month, :end_month, :start_year, :end_year)")
					.setParameter("start_date", startDay).setParameter("end_date", endDay)
					.setParameter("start_month", startMonth).setParameter("end_month", endMonth)
					.setParameter("start_year", startYear).setParameter("end_year", endYear);

			resourceMtricsByBGBUList = query.list();

		} catch (HibernateException e) {
			logger.error("Exception occured in getResourceMetricsByBGBUTable method at DAO layer:-" + e);
			e.printStackTrace();
		}
		logger.info("------------RMGReportDaoImpl getResourceMetricsByBGBUTable method end------------");

		return resourceMtricsByBGBUList;
	}

	public List getGradewiseReportTable(String startDay, String endDay, String startMonth, String endMonth,
			String startYear, String endYear) {

		logger.info("--------RMGReportDaoImpl getGradewiseReportTable method start-------");

		List gradewiseReportList = new ArrayList();

		Session session = (Session) getEntityManager().getDelegate();

		try {

			Query query = session
					.createSQLQuery(
							"CALL RMG_GradeWise_Report(:start_date, :end_date, :start_month, :end_month, :start_year, :end_year)")
					.setParameter("start_date", startDay).setParameter("end_date", endDay)
					.setParameter("start_month", startMonth).setParameter("end_month", endMonth)
					.setParameter("start_year", startYear).setParameter("end_year", endYear);

			gradewiseReportList = query.list();

		} catch (HibernateException e) {
			logger.error("Exception occured in getGradewiseReportTable method at DAO layer:-" + e);
			e.printStackTrace();
		}

		logger.info("------------RMGReportDaoImpl getGradewiseReportTable method end------------");

		return gradewiseReportList;
	}

	public List getBGBUMatricesTable(String startDay, String endDay, String startMonth, String endMonth,
			String startYear, String endYear) {

		logger.info("--------RMGReportDaoImpl getBGBUMatricesTable method start-------");

		List bgbuMatricesList = new ArrayList();

		Session session = (Session) getEntityManager().getDelegate();

		try {

			Query query = session
					.createSQLQuery(
							"CALL RMG_BGBU_Metrics(:start_date, :end_date, :start_month, :end_month, :start_year, :end_year)")
					.setParameter("start_date", startDay).setParameter("end_date", endDay)
					.setParameter("start_month", startMonth).setParameter("end_month", endMonth)
					.setParameter("start_year", startYear).setParameter("end_year", endYear);

			bgbuMatricesList = query.list();

		} catch (HibernateException e) {
			logger.error("Exception occured in getBGBUMatricesTable method at DAO layer:-" + e);
			e.printStackTrace();
		}
		logger.info("------------RMGReportDaoImpl getBGBUMatricesTable method end------------");

		return bgbuMatricesList;
	}

	public List getBenchGradeReportTable(String startDay, String endDay, String startMonth, String endMonth,
			String startYear, String endYear) {

		logger.info("--------RMGReportDaoImpl getBenchGradeReportTable method start-------");

		List benchGradeReportList = new ArrayList();

		Session session = (Session) getEntityManager().getDelegate();

		try {

			Query query = session
					.createSQLQuery(
							"CALL RMG_Bench_Grade_Report(:start_date, :end_date, :start_month, :end_month, :start_year, :end_year)")
					.setParameter("start_date", startDay).setParameter("end_date", endDay)
					.setParameter("start_month", startMonth).setParameter("end_month", endMonth)
					.setParameter("start_year", startYear).setParameter("end_year", endYear);

			benchGradeReportList = query.list();

		} catch (HibernateException e) {
			logger.error("Exception occured in getBenchGradeReportTable method at DAO layer:-" + e);
			e.printStackTrace();
		}
		logger.info("------------RMGReportDaoImpl getBenchGradeReportTable method end------------");

		return benchGradeReportList;
	}

	public List getBenchGradeDaysWiseReportTable(String startDay, String endDay, String startMonth, String endMonth,
			String startYear, String endYear) {

		logger.info("--------RMGReportDaoImpl getBenchGradeDaysWiseReportTable method start-------");

		List benchGradeDaysWiseReportList = new ArrayList();

		Session session = (Session) getEntityManager().getDelegate();
		try {

			Query query = session
					.createSQLQuery(
							"CALL RMG_Bench_Grade_DaysWise_Report(:start_date, :end_date, :start_month, :end_month, :start_year, :end_year)")
					.setParameter("start_date", startDay).setParameter("end_date", endDay)
					.setParameter("start_month", startMonth).setParameter("end_month", endMonth)
					.setParameter("start_year", startYear).setParameter("end_year", endYear);

			benchGradeDaysWiseReportList = query.list();

		} catch (HibernateException e) {
			logger.error("Exception occured in getBenchGradeDaysWiseReportTable method at DAO layer:-" + e);
			e.printStackTrace();
		}
		logger.info("------------RMGReportDaoImpl getBenchGradeDaysWiseReportTable method end------------");

		return benchGradeDaysWiseReportList;
	}

	public List getBenchSkillReportTable(String startDay, String endDay, String startMonth, String endMonth,
			String startYear, String endYear) {

		logger.info("--------RMGReportDaoImpl getBenchSkillReportTable method start-------");

		List benchSkillReportList = new ArrayList();

		Session session = (Session) getEntityManager().getDelegate();
		try {

			Query query = session
					.createSQLQuery(
							"CALL RMG_Bench_Skill_Report(:start_date, :end_date, :start_month, :end_month, :start_year, :end_year)")
					.setParameter("start_date", startDay).setParameter("end_date", endDay)
					.setParameter("start_month", startMonth).setParameter("end_month", endMonth)
					.setParameter("start_year", startYear).setParameter("end_year", endYear);

			benchSkillReportList = query.list();

		} catch (HibernateException e) {
			logger.error("Exception occured in getBenchSkillReportTable method at DAO layer:-" + e);
			e.printStackTrace();
		}
		logger.info("------------RMGReportDaoImpl getBenchSkillReportTable method end------------");

		return benchSkillReportList;
	}

	public List getProjectsClosingIn_3_MonthsReportTable(String startDay, String endDay, String startMonth,
			String endMonth, String startYear, String endYear) {

		logger.info("--------RMGReportDaoImpl getProjectsClosingIn_3_MonthsReportTable method start-------");

		List projectsClosingIn_3_MonthsReportList = new ArrayList();

		Session session = (Session) getEntityManager().getDelegate();
		try {

			Query query = session
					.createSQLQuery(
							"CALL RMG_Projects_Closing_In_3_Months(:start_date, :end_date, :start_month, :end_month, :start_year, :end_year)")
					.setParameter("start_date", startDay).setParameter("end_date", endDay)
					.setParameter("start_month", startMonth).setParameter("end_month", endMonth)
					.setParameter("start_year", startYear).setParameter("end_year", endYear);

			projectsClosingIn_3_MonthsReportList = query.list();

		} catch (HibernateException e) {
			logger.error("Exception occured in getProjectsClosingIn_3_MonthsReportTable method at DAO layer:-" + e);
			e.printStackTrace();
		}
		logger.info("------------RMGReportDaoImpl getProjectsClosingIn_3_MonthsReportTable method end------------");

		return projectsClosingIn_3_MonthsReportList;
	}

	public List getSkillsReleasingIn_3_MonthsReportTable(String startDay, String endDay, String startMonth,
			String endMonth, String startYear, String endYear) {

		logger.info("--------RMGReportDaoImpl getSkillsReleasingIn_3_MonthsReportTable method start-------");

		List skillsReleasingIn_3_MonthsReportList = new ArrayList();

		Session session = (Session) getEntityManager().getDelegate();

		try {

			Query query = session
					.createSQLQuery(
							"CALL RMG_Skills_Releasing_In_3_Months(:start_date, :end_date, :start_month, :end_month, :start_year, :end_year)")
					.setParameter("start_date", startDay).setParameter("end_date", endDay)
					.setParameter("start_month", startMonth).setParameter("end_month", endMonth)
					.setParameter("start_year", startYear).setParameter("end_year", endYear);

			skillsReleasingIn_3_MonthsReportList = query.list();

		} catch (HibernateException e) {
			logger.error("Exception occured in getSkillsReleasingIn_3_MonthsReportTable method at DAO layer:-" + e);
			e.printStackTrace();
		}
		logger.info("------------RMGReportDaoImpl getSkillsReleasingIn_3_MonthsReportTable method end------------");

		return skillsReleasingIn_3_MonthsReportList;
	}

}
