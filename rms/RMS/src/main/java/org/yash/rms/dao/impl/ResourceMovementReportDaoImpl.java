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
import org.yash.rms.dao.ResourceMovementReportDao;
import org.yash.rms.report.dto.ResourceMovementReport;

@Repository("resourceMovementReportDao")
public class ResourceMovementReportDaoImpl implements ResourceMovementReportDao {

	private static final Logger logger = LoggerFactory.getLogger(ResourceMovementReportDaoImpl.class);




@PersistenceContext(name = "mysql", type = PersistenceContextType.TRANSACTION)
	private EntityManager entityManager;
	
	
	public EntityManager getEntityManager() {
		return entityManager;
	}
	
	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	public List<ResourceMovementReport> getResourceMovementReport(List<Integer> orgIdList, List<Integer> locIdList, List<Integer> projIdList, Date startDate, Date endDate,
			List<Integer> allocationIDList) {

		logger.debug("--------RMReportDaoImpl getRMReport method start-------");

		Session session = (Session) getEntityManager().getDelegate();

		List<ResourceMovementReport> resourceMovementReportsList = new ArrayList<ResourceMovementReport>();

		try {

			Query query = session.createSQLQuery("SELECT `project`.`project_name`,allocationtype.`allocationtype`, resource.`first_name`,resource.`last_name`,"
					+ " resource.`yash_emp_id`, bu.`name` , resource_allocation.`alloc_start_date` , resource.`date_of_joining` FROM  resource_allocation "
					+ "LEFT JOIN `allocationtype` ON resource_allocation.`allocation_type_id`=allocationtype.`id`" + "LEFT JOIN `project` ON resource_allocation.`project_id`=project.`id`"
					+ "LEFT JOIN `resource` ON resource_allocation.`employee_id`=resource.`employee_id`" + "LEFT JOIN `bu` ON bu.`id`=resource.`bu_id`"
					+ "WHERE resource_allocation.`alloc_start_date`>= (:date) AND  resource_allocation.`alloc_start_date`<=(:endDate)"
					+ "AND project.`bu_id` IN (:bu_id) and resource.`location_id` IN (:location_id) AND project.id IN (:projIdList)" + "AND allocationtype.`id` IN (:allocationID)"
					+ "ORDER BY resource.`yash_emp_id` ASC, resource_allocation.`allocation_seq` DESC, resource_allocation.`alloc_start_date` DESC ");

			query.setParameter("date", startDate);
			query.setParameterList("bu_id", orgIdList);
			query.setParameterList("location_id", locIdList);
			query.setParameterList("projIdList", projIdList);
			query.setParameterList("allocationID", allocationIDList);

			if (endDate == null) {

				query.setParameter("endDate", new Date());

			} else {

				query.setParameter("endDate", endDate);
			}

			List<Object[]> reportList = query.list();

			if (!reportList.isEmpty()) {

				for (Object[] row : reportList) {

					ResourceMovementReport resourceMovementReport = new ResourceMovementReport();
					resourceMovementReport.setProjectName((String) row[0]);
					resourceMovementReport.setAllocationType((String) row[1]);
					resourceMovementReport.setEmployeeName((String) row[2] + " " + (String) row[3]);
					resourceMovementReport.setYashEmpID((String) row[4]);
					resourceMovementReport.setBGBUName((String) row[5]);
					resourceMovementReport.setStartDate((Date) row[6]);
					resourceMovementReport.setDateOfJoining((Date) row[7]);

					resourceMovementReportsList.add(resourceMovementReport);
				}
			}
		} catch (HibernateException e) {

			e.printStackTrace();

			logger.debug(e.getMessage());
		}

		logger.debug("--------RMReportDaoImpl getRMReport method start-------");

		return resourceMovementReportsList;
	}

	public List<ResourceMovementReport> getAnalysisResourceMovementReport(List<Integer> orgIdList, List<Integer> locIdList, List<Integer> projIdList, Date startDate, Date endDate,
			List<Integer> allocationIDList) {

		logger.debug("--------getAnalysisResourceMovementReport method start-------");
		Session session = (Session) getEntityManager().getDelegate();
		List<ResourceMovementReport> analysisResourceMovementReportsList = new ArrayList<ResourceMovementReport>();

		try {

			Query query = session.createSQLQuery("SELECT project_name,allocationtype,first_name,last_name,yash_emp_id,`name`,tab_comments.alloc_start_date,tab_comments.date_of_joining,tab_comments.`Previous_Allocation_Type`, "
					+ "(SELECT CASE "
					+ " WHEN (tab_comments.`Previous_Allocation_Type` IS NULL  OR (tab_comments.`Previous_Allocation_Type`=tab_comments.allocationtype )) "
					+ " AND DATEDIFF(tab_comments.`alloc_start_date`,tab_comments.date_of_joining)<=45 THEN 'New Hiring'  "
					+ " WHEN tab_comments.`Previous_Allocation_Type`!='Non-Billable (Investment)' AND  tab_comments.allocationtype !='Non-Billable (Investment)' "
					+ " AND (tab_comments.`Previous_Allocation_Type` LIKE '%Non-Billable%'  AND tab_comments.allocationtype LIKE '%Non-Billable%')  "
					+ " AND DATEDIFF(tab_comments.`alloc_start_date`,tab_comments.date_of_joining)<=45 THEN 'New Hiring' "
					+ " WHEN tab_comments.`Previous_Allocation_Type` LIKE '%Non-Billable (Investment)%' AND tab_comments.allocationtype LIKE '%Billable (Partial / Shared Pool / Fix Bid Projects)%' THEN 'Investment To Partial Billable' "
					+ " WHEN tab_comments.`Previous_Allocation_Type` LIKE '%Non-Billable (Investment)%' AND tab_comments.allocationtype LIKE '%Billable (Full Time (FTE))%' THEN 'Investment To Billable' "
					+ " WHEN tab_comments.`Previous_Allocation_Type` LIKE '%Non-Billable (Investment)%' AND tab_comments.allocationtype LIKE '%Non-Billable (Investment)%' THEN 'Investment' "
					+ " WHEN tab_comments.`Previous_Allocation_Type` LIKE '%Non-Billable (Investment)%' AND (tab_comments.allocationtype LIKE '%Non-Billable%' OR tab_comments.allocationtype LIKE '%PIP%' ) THEN 'Investment To NonBillable' " 
					+ " WHEN (tab_comments.`Previous_Allocation_Type` LIKE '%Non-Billable%' OR tab_comments.`Previous_Allocation_Type` LIKE '%PIP%' )  AND tab_comments.allocationtype LIKE '%Billable (Full Time (FTE))%' THEN 'NonBillable To Billable' "
					+ " WHEN (tab_comments.`Previous_Allocation_Type` LIKE '%Non-Billable%' OR tab_comments.`Previous_Allocation_Type` LIKE '%PIP%' )  AND tab_comments.allocationtype LIKE '%Non-Billable (Investment)%' THEN 'NonBillable To Investment' "
					+ " WHEN (tab_comments.`Previous_Allocation_Type` LIKE '%Non-Billable%' OR tab_comments.`Previous_Allocation_Type` LIKE '%PIP%' )  AND tab_comments.allocationtype LIKE '%Billable (Partial / Shared Pool / Fix Bid Projects)%' THEN 'NonBillable To Partial Billable' "
					+ " WHEN (tab_comments.`Previous_Allocation_Type` LIKE '%Non-Billable%' OR tab_comments.`Previous_Allocation_Type` LIKE '%PIP%')  AND tab_comments.allocationtype LIKE '%Non-Billable%' THEN 'Non-Billable' "
					+ " WHEN tab_comments.`Previous_Allocation_Type` LIKE '%Billable (Partial / Shared Pool / Fix Bid Projects)%' AND tab_comments.allocationtype LIKE '%Non-Billable (Investment)%' THEN 'Partial Billable To Investment' "
					+ " WHEN tab_comments.`Previous_Allocation_Type` LIKE '%Billable (Partial / Shared Pool / Fix Bid Projects)%' AND tab_comments.allocationtype LIKE '%Billable (Full Time (FTE))%' THEN 'Partial Billable To Billable' "
					+ " WHEN tab_comments.`Previous_Allocation_Type` LIKE '%Billable (Partial / Shared Pool / Fix Bid Projects)%' AND (tab_comments.allocationtype LIKE '%Non-Billable%' OR tab_comments.allocationtype LIKE '%PIP%' )  THEN 'Partial Billable To NonBillable' "
					+ " WHEN (tab_comments.`Previous_Allocation_Type` LIKE '%Billable (Partial / Shared Pool / Fix Bid Projects)%') AND tab_comments.allocationtype LIKE '%Billable (Partial / Shared Pool / Fix Bid Projects)%' THEN 'Partial Billable'"
					+ " WHEN tab_comments.`Previous_Allocation_Type` LIKE '%Billable%' AND tab_comments.allocationtype LIKE '%Billable (Partial / Shared Pool / Fix Bid Projects)%' THEN 'Billable To Partial Billable' "
					+ " WHEN tab_comments.`Previous_Allocation_Type` LIKE '%Billable%' AND tab_comments.allocationtype LIKE '%Non-Billable (Investment)%' THEN 'Billable To Investment' "
					+ " WHEN tab_comments.`Previous_Allocation_Type` LIKE '%Billable%' AND (tab_comments.allocationtype LIKE '%Non-Billable%' OR tab_comments.allocationtype LIKE '%PIP%' )  THEN 'Billable To NonBillable' "
					+ " WHEN (tab_comments.`Previous_Allocation_Type` LIKE '%Billable%') AND tab_comments.allocationtype LIKE '%Billable%' THEN 'Billable' "					
					+ " ELSE '' END 'Comments') AS 'Comments', "
					+ " (SELECT CASE   "
					+ " WHEN (tab_comments.`Previous_Allocation_Type` IS NULL OR tab_comments.`Previous_Allocation_Type`=tab_comments.allocationtype) "
					+ " AND DATEDIFF(tab_comments.`alloc_start_date`,tab_comments.date_of_joining)<=15 THEN 'External' "
					+ " WHEN tab_comments.`Previous_Allocation_Type`!='Non-Billable (Investment)' AND  tab_comments.allocationtype !='Non-Billable (Investment)' "
					+ " AND (tab_comments.`Previous_Allocation_Type` LIKE '%Non-Billable%'  AND tab_comments.allocationtype LIKE '%Non-Billable%')  "
					+ " AND DATEDIFF(tab_comments.`alloc_start_date`,tab_comments.date_of_joining)<=15 THEN 'External' "
					+ " ELSE 'Internal'  END Resource  ) AS 'Resource_Type' "
					+ " FROM ( SELECT (SELECT allocationtype.`allocationtype` FROM resource_allocation   "
					+ " LEFT JOIN `allocationtype` ON resource_allocation.`allocation_type_id`=allocationtype.`id`  "
					+ " LEFT JOIN `resource` ON resource_allocation.`employee_id`=resource.`employee_id`  "
					+ " WHERE resource_allocation.`alloc_start_date`<= PAT.alloc_start_date AND  resource.`yash_emp_id`=PAT.yash_emp_id  "
					+ " AND resource_allocation.`allocation_seq`<PAT.allocation_seq  "
					+ " ORDER BY resource_allocation.`alloc_start_date` DESC,resource_allocation.`allocation_seq` DESC LIMIT 1 )  AS 'Previous_Allocation_Type' ,PAT.*  FROM ( "
					+ " SELECT * FROM( SELECT   `project`.`project_name`,   allocationtype.`allocationtype`,   resource.`first_name`,   resource.`last_name`,   resource.`yash_emp_id`,bu.name, "
					+ " resource_allocation.`alloc_start_date`, resource_allocation.`allocation_seq`,   resource.`date_of_joining` FROM  resource_allocation "
					+ " LEFT JOIN `allocationtype` ON resource_allocation.`allocation_type_id` = allocationtype.`id` "
					+ " LEFT JOIN `project` ON resource_allocation.`project_id` = project.`id` "
					+ " LEFT JOIN `resource`  ON resource_allocation.`employee_id` = resource.`employee_id` "
					+ " LEFT JOIN `bu` ON bu.`id` = resource.`bu_id` "
					+ " WHERE resource_allocation.`alloc_start_date` >= (:startDate)   AND resource_allocation.`alloc_start_date` <= (:endDate) "
					+ " AND project.`bu_id` IN (:bu_id) and resource.`location_id` IN (:location_id) AND project.id IN (:projIdList)" + "AND allocationtype.`id` IN (:allocationID)"
					+ " GROUP BY  resource.`yash_emp_id` ,resource_allocation.`alloc_start_date`, resource_allocation.`allocation_seq` "
					+ " HAVING   COUNT(*) >= 1  "
					+ " ) tab  "
					+ " WHERE  (tab.alloc_start_date,tab.`allocation_seq`) = (SELECT MAX(resource_allocation.`alloc_start_date`),MAX(resource_allocation.`allocation_seq`) FROM resource_allocation  "
				    + " LEFT JOIN `allocationtype` ON resource_allocation.`allocation_type_id` = allocationtype.`id` "
				    + " LEFT JOIN `project` ON resource_allocation.`project_id` = project.`id` "
				    + " LEFT JOIN `resource` ON resource_allocation.`employee_id` = resource.`employee_id` "
				    + " LEFT JOIN `bu` ON bu.`id` = resource.`bu_id` "
					+ " WHERE resource_allocation.`alloc_start_date` >= (:startDate)   AND resource_allocation.`alloc_start_date` <= (:endDate) "
					+ " AND  resource.`yash_emp_id`=tab.yash_emp_id "
					+ " AND project.`bu_id` IN (:bu_id) and resource.`location_id` IN (:location_id) AND project.id IN (:projIdList)" + "AND allocationtype.`id` IN (:allocationID)"
					+ " GROUP BY  resource.`yash_emp_id` ,resource_allocation.`alloc_start_date`, resource_allocation.`allocation_seq` "
					+ " HAVING   COUNT(*) >= 1  "
					+ " ORDER BY resource.`yash_emp_id` ASC,resource_allocation.`alloc_start_date` DESC,resource_allocation.`allocation_seq` DESC LIMIT 1) "
					+ " ) PAT ) tab_comments " );
					
			query.setParameter("startDate", startDate);
			query.setParameter("endDate", endDate);
			query.setParameterList("bu_id", orgIdList);
			query.setParameterList("location_id", locIdList);
			query.setParameterList("projIdList", projIdList);
			query.setParameterList("allocationID", allocationIDList);

			if (endDate == null) {

				query.setParameter("endDate", new Date());

			} else {

				query.setParameter("endDate", endDate);
			}

			List<Object[]> reportList = query.list();

			if (!reportList.isEmpty()) {

				for (Object[] row : reportList) {

					ResourceMovementReport resourceMovementReport = new ResourceMovementReport();
					resourceMovementReport.setProjectName((String) row[0]);
					resourceMovementReport.setAllocationType((String) row[1]);
					resourceMovementReport.setEmployeeName((String) row[2] + " " + (String) row[3]);
					resourceMovementReport.setYashEmpID((String) row[4]);
					resourceMovementReport.setBGBUName((String) row[5]);
					resourceMovementReport.setStartDate((Date) row[6]);
					resourceMovementReport.setDateOfJoining((Date) row[7]);
					resourceMovementReport.setPreviousAllocationType((String) row[8]);
					resourceMovementReport.setAllocationChange((String) row[9]);
					resourceMovementReport.setResourceType((String) row[10]);

					analysisResourceMovementReportsList.add(resourceMovementReport);
				}
			}
		} catch (HibernateException e) {

			e.printStackTrace();

			logger.debug(e.getMessage());
		}
		logger.debug("--------getAnalysisResourceMovementReport method end-------");

		return analysisResourceMovementReportsList;
	}
	
}