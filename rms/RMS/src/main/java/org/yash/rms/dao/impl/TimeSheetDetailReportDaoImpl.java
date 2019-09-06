package org.yash.rms.dao.impl;

import java.util.ArrayList;
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
import org.yash.rms.dao.TimeSheetDetailReportDao;
import org.yash.rms.report.dto.TimeSheetDetailReport;

@Repository("timeSheetDetailReportDao")
public class TimeSheetDetailReportDaoImpl implements TimeSheetDetailReportDao {

	private static final Logger LOGGER = LoggerFactory.getLogger(TimeSheetDetailReportDaoImpl.class);

	@PersistenceContext(name = "mysql", type = PersistenceContextType.TRANSACTION)
	private EntityManager entityManager;
	
	
	public EntityManager getEntityManager() {
		return entityManager;
	}
	
	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public List<TimeSheetDetailReport> getTimeSheetDetailReport(List<Integer> orgIdList, List<Integer> locIdList,
			List<Integer> projIdList, Date endDate, List<Integer> employeeIdList, Date startDate) {

		LOGGER.debug("-------TimeSheetReportDaoImpl getTimeSheetDetailReport method start-------");

		Session session = (Session) getEntityManager().getDelegate();
		Query query = session.createSQLQuery(
				"SELECT  re.yash_emp_id, CONCAT(re.first_name, \\\" \\\" , re.last_name) AS Employee_name ,re.email_id,"
						+ " CONCAT( re1.first_name, \" \" , re1.last_name) AS Reporting_Manager ,"
						+ " L.location ,P.Project_name, CONCAT(Bu.`BgName`, \"-\", Bu.`BuName`)  BU  FROM  resource_allocation ra  "
						+ " INNER JOIN resource re "
						+ " ON ra.employee_id=re.employee_id "
						+ " LEFT OUTER  JOIN resource re1  "
						+ " ON (re.current_reporting_manager=re1.employee_id  ) "
						+ " INNER JOIN  location L  ON re.location_id=l.id INNER JOIN  Project P ON ra.project_id=p.id INNER JOIN bu bu ON p.bu_id=bu.id"
						+ " WHERE :endDate  BETWEEN ra.alloc_start_date AND IFNULL(ra.alloc_end_date ,LAST_DAY_OF_WEEK(:endDate)) AND LAST_DAY_OF_WEEK(IFNULL(re.release_date, :endDate)) >= :endDate AND p.bu_id IN (:orgIdList)"
						+ " AND ra.project_id IN (:projIdList) AND ra.curproj=1"
						+ " AND ra.employee_id NOT IN ( SELECT DISTINCT ua.employee_id FROM vw_user_activity  ua INNER JOIN resource_allocation ra ON ra.employee_id= ua.employee_id AND ra.id = ua.resource_alloc_id INNER JOIN resource re "
						+ " ON ra.employee_id=re.employee_id INNER JOIN  location L ON re.location_id=l.id INNER JOIN  Project P ON ra.project_id=p.id INNER JOIN bu bu ON p.bu_id=bu.id "
						+ " WHERE :endDate BETWEEN  ra.alloc_start_date AND  LAST_DAY_OF_WEEK(IFNULL(ra.alloc_end_date ,:endDate)) AND LAST_DAY_OF_WEEK(IFNULL(re.release_date,:endDate)) >= :endDate  AND week_start_date>= FIRST_DAY_OF_WEEK(:endDate)  AND  week_end_date <= LAST_DAY_OF_WEEK(:endDate) AND p.bu_id IN (:orgIdList)"
						+ " AND ra.project_id IN (:projIdList) ) "
						+ " ORDER BY  CONCAT(re.first_name ,' ' , re.last_name, '-' + re.yash_emp_id) " );
		
		query.setParameter("endDate", endDate);
		//query.setParameter("startDate", startDate);
		query.setParameterList("orgIdList", orgIdList);
		query.setParameterList("projIdList", projIdList);

		System.out.println("############ enddate" + endDate);
		System.out.println("############ orgIdList" + orgIdList);
		System.out.println("############ projIdList" + projIdList);

		List<TimeSheetDetailReport> timeSheetDetailReportList = new ArrayList<TimeSheetDetailReport>();
		try {
			List<Object[]> timeSheetUsersList = query.list();
			if (!timeSheetUsersList.isEmpty()) {
				int sNo = 1;
				for (Object[] timeSheetUser : timeSheetUsersList) {
					TimeSheetDetailReport timeSheetDetailReport = new TimeSheetDetailReport();
					timeSheetDetailReport.setEmpId(String.valueOf(timeSheetUser[0]));
					timeSheetDetailReport.setName((String) timeSheetUser[1]);
					timeSheetDetailReport.setEmailId((String) timeSheetUser[2]);
					timeSheetDetailReport.setReportingMgr((String) timeSheetUser[3]);
					timeSheetDetailReport.setLocation((String) timeSheetUser[4]);
					timeSheetDetailReport.setProject((String) timeSheetUser[5]);
					timeSheetDetailReport.setBusinessUnit((String) timeSheetUser[6]);

					timeSheetDetailReportList.add(timeSheetDetailReport);
				}
			}
		} catch (HibernateException e) {
			LOGGER.debug(e.getMessage());
		}
		LOGGER.debug("--------TimeSheetReportDaoImpl getTimeSheetDetailReport method end-------");
		return timeSheetDetailReportList;
	}
}
