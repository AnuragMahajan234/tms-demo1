package org.yash.rms.dao.impl;

import java.text.SimpleDateFormat;
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
import org.yash.rms.dao.CurrentWeekAllocationReportDao;

@Repository("currentWeekAllocationReportDao")
public class CurrentWeekAllocationReportDaoImpl implements	CurrentWeekAllocationReportDao {

	private static final Logger logger = LoggerFactory
			.getLogger(CurrentWeekAllocationReportDaoImpl.class);


	@PersistenceContext(name = "mysql", type = PersistenceContextType.TRANSACTION)
	private EntityManager entityManager;
	
	
	public EntityManager getEntityManager() {
		return entityManager;
	}
	
	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}


	public List<Object[]> getCurrentWeekAllocationReport(List<Integer> buIds, List<Integer> locationIds, List<Integer> projIds, List<Integer> ownerShipIds, Date weekEndDate,  Date weekStartDate, List<Integer> currentBUIds) {

		logger.debug("-------CurrentWeekAllocationReportDaoImpl getCurrentWeekAllocationReport method start-------");

		Session session = (Session) getEntityManager().getDelegate();

		List<Object[]> reportList = new ArrayList<Object[]>();		
		try {

			Query query = session
					.createSQLQuery( " SELECT alctyp.`allocationtype`, "
							+" r.`yash_emp_id`,  "
							+" CONCAT( TRIM(r.first_name), ' ', TRIM(r.last_name) ) AS NAME, "
							+" p.project_name, "
							+" d.designation_name, "
							+" g.grade, "
							+" l.location AS base_location, "
							+" b.name AS bu_name "
							+" FROM `resource` r "
							+" JOIN `resource_allocation` ra ON r.`employee_id` = ra.`employee_id` "
							+" JOIN `location` l ON l.`id` = r.`location_id` "
							+" JOIN `grade` g ON g.`id` = r.`grade_id` "
							+" JOIN `designations` d ON d.`id` = r.`designation_id` "
							+" JOIN `allocationtype` alctyp ON alctyp.`id` = ra.`allocation_type_id` "
							+" JOIN project p ON p.`id` = ra.`project_id` "
							+" JOIN bu b ON b.`id` = r.`bu_id` "
							+" WHERE ra.`curProj` = 1  "
							+" AND r.`bu_id` IN (:buIds) "
							+" AND r.`current_bu_id` IN (:currentBUIds) "
							+" AND p.`id` IN (:projIds) "
							+" AND l.`id` IN (:locationIds) "
							+" AND r.`ownership` IN (:ownerShipIds) "
							+" AND ra.`alloc_start_date` >= :startDate AND ra.`alloc_start_date` <= :endDate  "
							+" ORDER BY alctyp.`allocationtype` DESC,NAME ASC  ");
			
			query.setParameter("startDate", new SimpleDateFormat("yyyy-MM-dd").format(weekStartDate));
			query.setParameter("endDate", new SimpleDateFormat("yyyy-MM-dd").format(weekEndDate));
			query.setParameterList("buIds", buIds);
			query.setParameterList("locationIds", locationIds);
			query.setParameterList("projIds", projIds);
			query.setParameterList("ownerShipIds", ownerShipIds);
			query.setParameterList("currentBUIds", currentBUIds);

			reportList = query.list();

		} catch (HibernateException e) {
			e.printStackTrace();
			logger.debug(e.getMessage());
		}

		logger.debug("--------CurrentWeekAllocationReportDaoImpl getCurrentWeekAllocationReport method end-------");

		return reportList;
	}


}