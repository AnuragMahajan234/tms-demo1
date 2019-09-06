package org.yash.rms.dao.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
import org.yash.rms.dao.UtilizationDetailReportDao;
import org.yash.rms.report.dto.UtilizationDetailReport;
import org.yash.rms.report.dto.WeeklyData;

@Repository("utilizationDetailReportDao")
public class UtilizationDetailReportDaoImpl implements
		UtilizationDetailReportDao {

	private static final Logger logger = LoggerFactory
			.getLogger(UtilizationDetailReportDaoImpl.class);

	@PersistenceContext(name = "mysql", type = PersistenceContextType.TRANSACTION)
	private EntityManager entityManager;
	
	
	public EntityManager getEntityManager() {
		return entityManager;
	}
	
	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public List<UtilizationDetailReport> getUtilizationDetailReport(
			List<Integer> orgIdList, List<Integer> locIdList,
			List<Integer> projIdList, Date startDate, Date endDate,
			List<Integer> employeeIdList) {

		logger.debug("-------UtilizationReportDaoImpl getUtilizationDetailReport method start-------");

		Session session = (Session) getEntityManager().getDelegate();

		List<UtilizationDetailReport> utilizationDetailReportList = new ArrayList<UtilizationDetailReport>();
		
		Calendar c = Calendar.getInstance();
		c.setTime(endDate);
		c.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
		endDate=c.getTime(); // => Date of this coming Saturday.

		try {

			Query query = session
					.createSQLQuery("SELECT DISTINCT CONCAT(resource.first_name, resource.last_name, '-', resource.yash_emp_id) AS resource_yash_emp_id,"
							+ "DATE_ADD(week_start_date, INTERVAL 0 DAY) AS weekdate, "
							+ "week_start_date,week_end_date, t.week_ending_date, IFNULL(t.planned_hrs, 0) AS planned_hrs, "
							+ "IFNULL(t.billed_hrs, 0) AS billed_hrs,"
							+ "CASE IFNULL(activity.productive, 0) + IFNULL(c.productive, 0) "
							+ "WHEN 0 THEN IFNULL(d1_hours, 0) + IFNULL(d2_hours, 0) + IFNULL(d3_hours, 0) + IFNULL(d4_hours, 0) + IFNULL(d5_hours, 0) + IFNULL(d6_hours, 0) + IFNULL( "
							+ "d7_hours, 0) ELSE 0 END NonProductiveHrs,"
							+ "CASE IFNULL(activity.productive, 0) + IFNULL(c.productive, 0) "
							+ "WHEN 1 THEN IFNULL(d1_hours, 0) + IFNULL(d2_hours, 0) + IFNULL(d3_hours, 0) + IFNULL(d4_hours, 0) + IFNULL(d5_hours, 0) + IFNULL(d6_hours, 0) + IFNULL( "
							+ "d7_hours, 0) ELSE 0 END ProductiveHrs,  project_name, status,"
							+ "d1_hours,d2_hours,d3_hours,d4_hours,d5_hours,d6_hours,d7_hours, "
							+ "d1_comment,d2_comment,d3_comment,d4_comment,d5_comment,d6_comment,d7_comment, activity.activity_name, module,"
							+ " CONCAT ( IFNULL (inv.name,'') , IFNULL (org.name, ''), IFNULL (cust.code,''), project.id) AS project_code, c.activity_name AS custom_activity "
							+ "FROM vw_user_activity AS vw_user_activity "
							+ "INNER JOIN resource_allocation AS resource_allocation ON resource_allocation.id = vw_user_activity.resource_alloc_id "
							+ "INNER JOIN resource AS resource ON resource_allocation.employee_id = resource.employee_id "
							+ "LEFT JOIN activity ON vw_user_activity.activity_id = activity.id "
							+ "LEFT JOIN custom_activity c ON vw_user_activity.custom_activity_id = c.id INNER JOIN project AS project "
							+ "ON resource_allocation.project_id = project.id "
							+ "INNER JOIN invoice_by AS inv ON project.`invoice_by_id`= inv.id INNER JOIN `org_hierarchy` AS org ON project.`bu_id`= org.id "
							+ "INNER JOIN `customer` AS cust ON project.`customer_name_id`= cust.id "
							+ "INNER JOIN designations AS designations "
							+ "ON resource.designation_id = designations.id INNER JOIN location AS L ON resource.location_id = L.id "
							+ "INNER JOIN bu AS bu ON project.bu_id = Bu.id LEFT OUTER JOIN timehrs AS t ON week_end_date = t.week_ending_date "
							+ "AND resource.employee_id = t.resource_id AND resource_allocation.id = t.resource_alloc_id "
							+ "WHERE week_end_date >= (:startDate) AND week_end_date <= (:endDate)  "
							+ "AND resource_allocation.project_id IN (:projIdList) "
							+ "AND bu.id IN (:buIdList) "
							+ "AND L.id IN (:location_id) "
							+ "AND vw_user_activity.employee_id IN (:employeeIdList) ORDER BY resource.first_name");
			query.setParameter("startDate", startDate);
			query.setParameter("endDate", endDate);
			query.setParameterList("buIdList", orgIdList);
			query.setParameterList("location_id", locIdList);
			query.setParameterList("projIdList", projIdList);
			query.setParameterList("employeeIdList", employeeIdList);
			
			List<Object[]> reportList = query.list();
			if (!reportList.isEmpty()) {

				for (Object[] row : reportList) {

					UtilizationDetailReport utilizationDetailReport = new UtilizationDetailReport();
					WeeklyData weeklyData = new WeeklyData();
					DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

					if (row[0] != null) {
						utilizationDetailReport
								.setEmployeeNameId((String) row[0]);
					}
					if (row[2] != null && row[3] != null) {
						utilizationDetailReport.setWeekEndDate((dateFormat
								.format((Date) row[2]))
								+ "-"
								+ (dateFormat.format((Date) row[3])));
						weeklyData.setWeekStartDate((Date) row[2]);
						weeklyData.setWeekEndDate((Date) row[3]);
					}
					if (row[5] != null) {
						weeklyData.setPlannedHours((Double) row[5]);
					} else {
						weeklyData.setPlannedHours(0.0);
					}
					if (row[6] != null) {
						weeklyData.setBilledHours((Double) row[6]);
					} else {
						weeklyData.setBilledHours(0.0);
					}
					if (row[7] != null) {
						weeklyData.setNonProductiveHours((Double) row[7]);
					} else {
						weeklyData.setNonProductiveHours(0.0);
					}
					if (row[8] != null) {
						weeklyData.setProductiveHours((Double) row[8]);
					} else {
						weeklyData.setProductiveHours(0.0);
					}
					if (row[9] != null) {
						weeklyData.setProjectName((String) row[9]);
					} else {
						weeklyData.setProjectName("");
					}
					if (row[10] != null) {
						weeklyData.setStatus((Character) row[10]);
					} else {
						weeklyData.setStatus('N');
					}
					if (row[11] != null) {
						weeklyData.setD1_hours((Double) row[11]);
					} 
					if (row[12] != null) {
						weeklyData.setD2_hours((Double) row[12]);
					} 
					if (row[13] != null) {
						weeklyData.setD3_hours((Double) row[13]);
					} 
					if (row[14] != null) {
						weeklyData.setD4_hours((Double) row[14]);
					} 
					if (row[15] != null) {
						weeklyData.setD5_hours((Double) row[15]);
					} 
					if (row[16] != null) {
						weeklyData.setD6_hours((Double) row[16]);
					}
					if (row[17] != null) {
						weeklyData.setD7_hours((Double) row[17]);
					} 

					if (row[18] != null) {
						weeklyData.setD1_comment((String) row[18]);
					} else {
						weeklyData.setD1_comment("");
					}
					if (row[19] != null) {
						weeklyData.setD2_comment((String) row[19]);
					} else {
						weeklyData.setD2_comment("");
					}
					if (row[20] != null) {
						weeklyData.setD3_comment((String) row[20]);
					} else {
						weeklyData.setD3_comment("");
					}
					if (row[21] != null) {
						weeklyData.setD4_comment((String) row[21]);
					} else {
						weeklyData.setD4_comment("");
					}
					if (row[22] != null) {
						weeklyData.setD5_comment((String) row[22]);
					} else {
						weeklyData.setD5_comment("");
					}
					if (row[23] != null) {
						weeklyData.setD6_comment((String) row[23]);
					} else {
						weeklyData.setD6_comment("");
					}
					if (row[24] != null) {
						weeklyData.setD7_comment((String) row[24]);
					} else {
						weeklyData.setD7_comment("");
					}
					if (row[25] != null) {

						weeklyData.setActivity_name((String) row[25]);
					} else {
						if(row[28] != null){
							weeklyData.setActivity_name((String) row[28]);
						}else
						weeklyData.setActivity_name("");
					}

					if (row[26] != null) {
						weeklyData.setModule((String) row[26]);
					} else {
						weeklyData.setModule("");
					}
					
					if (row[27] != null) {
						String projectCode=null;
						if(row[27] instanceof String){
							projectCode= (String)row[27];
						}else{
						byte[] bytes = (byte[])row[27];
						projectCode= new String(bytes);
						}
						weeklyData.setProjectCode(projectCode);
					} else {
						weeklyData.setProjectCode("");
					}
					if(row[28] != null){
						weeklyData.setCustom_activity_name((String) row[28]);
					}
					utilizationDetailReport.setWeeklyData(weeklyData);

					utilizationDetailReportList.add(utilizationDetailReport);
				}
			}
		} catch (HibernateException e) {

			e.printStackTrace();

			logger.debug(e.getMessage());
		}

		logger.debug("--------UtilizationReportDaoImpl getUtilizationDetailReport method end-------");

		return utilizationDetailReportList;
	}

}