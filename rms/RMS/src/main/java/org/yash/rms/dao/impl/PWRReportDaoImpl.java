package org.yash.rms.dao.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.yash.rms.dao.PWRReportDao;
import org.yash.rms.report.dto.PWRReport;
import org.yash.rms.util.Constants;

@SuppressWarnings({ "unchecked", "rawtypes" })
@Repository("pwrReportDao")
public class PWRReportDaoImpl implements PWRReportDao {

	private static final Logger logger = LoggerFactory.getLogger(PWRReportDaoImpl.class);

	@PersistenceContext(name = "mysql", type = PersistenceContextType.TRANSACTION)
	private EntityManager entityManager;
	
	
	public EntityManager getEntityManager() {
		return entityManager;
	}
	
	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	public Map<String, List<PWRReport>> getPWRReportView2(List<Integer> orgIdList, List<Integer> locIdList, List<Integer> projIdList, List<Integer> ownership, Collection date, int view) {

		logger.debug("--------PWRReportDaoImpl getPWRReportView2 method start-------");

		Session session = (Session) getEntityManager().getDelegate();

		Map<String, List<PWRReport>> projectAllocaionMap = null;

		try {

			Query query = session.createSQLQuery("SELECT p.`project_name`,alt.`allocationtype`,r.`first_name`,r.`last_name` "
					+ "FROM `resource_allocation` ra,`resource` r,`project` p,`allocationtype` alt "
					+ "WHERE ra.`employee_id` = r.`employee_id` AND ra.`project_id` = p.`id` AND ra.`allocation_type_id` = alt.`id` "
					+ "AND (ra.`alloc_end_date` IS NULL OR ra.`alloc_end_date` >= :date) AND ra.`alloc_start_date` <= :date " + "AND ra.`project_id` IN (:projIdList) AND p.`bu_id` IN (:bu_id) "
					+ "AND r.`location_id` IN (:location_id) AND r.`ownership` IN (:ownership)");

			query.setParameterList("bu_id", orgIdList);
			query.setParameterList("location_id", locIdList);
			query.setParameterList("projIdList", projIdList);
			query.setParameterList("ownership", ownership);
			query.setParameterList("date", date);

			List<Object[]> pwrReportList = query.list();

			List<PWRReport> pwrReportProjectList = new ArrayList<PWRReport>();

			List<String> projectNameList = new ArrayList<String>();

			List<Object> projectNameCount = new ArrayList<Object>();

			if (!pwrReportList.isEmpty()) {

				projectAllocaionMap = new HashMap<String, List<PWRReport>>();

				for (Object[] rowPWRReport : pwrReportList) {

					PWRReport pwrReport = new PWRReport();

					pwrReport.setProjectName((String) rowPWRReport[0]);

					pwrReport.setAllocationType((String) rowPWRReport[1]);

					pwrReport.setEmployeeName((String) rowPWRReport[2] + " " + (String) rowPWRReport[3]);

					pwrReportProjectList.add(pwrReport);

				}

				for (PWRReport pwReportObject : pwrReportProjectList) {

					projectNameList.add(pwReportObject.getProjectName());

				}

				projectNameCount = projectNameList.stream().distinct().collect(Collectors.toList());

				int count = 0;

				List<PWRReport> pwrReportListTotalCount = null;

				for (Object projectName : projectNameCount) {

					pwrReportListTotalCount = new ArrayList<PWRReport>();

					for (PWRReport pwreportObject : pwrReportProjectList) {

						if (pwreportObject.getProjectName().equals(projectName)) {

							pwreportObject.setTotal((long) count++);
							pwrReportListTotalCount.add(pwreportObject);

						}
					}

					projectAllocaionMap.put(String.valueOf(projectName), pwrReportListTotalCount);

					pwrReportListTotalCount = null;

				}

			}

		} catch (HibernateException e) {

			e.printStackTrace();
			logger.debug(e.getMessage());
		}

		logger.debug("--------PWRReportDaoImpl getPWRReportView2 method start-------");

		return projectAllocaionMap;
	}

	public Set<PWRReport> getPWRReportView1(List<Integer> orgIdList, List<Integer> locIdList, List<Integer> projIdList, List<Integer> ownership, Collection date, int view) {

		logger.debug("--------PWRReportDaoImpl getPWRReportView1 method start-------");

		Session session = (Session) getEntityManager().getDelegate();

		Set<PWRReport> pwrReportList = new HashSet<PWRReport>();

		Map<String, PWRReport> projectAllocaionMap = null;

		try {

			Query query = session.createSQLQuery("SELECT p.`project_name`,alt.`allocationtype`,COUNT(alt.`id`) " + "FROM `resource_allocation` ra,`resource` r,`project` p,`allocationtype` alt "
					+ "WHERE ra.`employee_id` = r.`employee_id` AND ra.`project_id` = p.`id` AND ra.`allocation_type_id` = alt.`id` "
					+ "AND (ra.`alloc_end_date` IS NULL OR ra.`alloc_end_date` >= :date) AND ra.`alloc_start_date` <= :date " + "AND ra.`project_id` IN (:projIdList) AND p.`bu_id` IN (:bu_id) "
					+ "AND r.`location_id` IN (:location_id) AND r.`ownership` IN (:ownership) GROUP BY p.`project_name`, alt.`allocationtype`");

			query.setParameterList("bu_id", orgIdList);
			query.setParameterList("location_id", locIdList);
			query.setParameterList("projIdList", projIdList);
			query.setParameterList("ownership", ownership);
			query.setParameterList("date", date);

			List<Object[]> pwrReportProjectList = query.list();

			projectAllocaionMap = new HashMap<String, PWRReport>();

			if (!pwrReportProjectList.isEmpty()) {

				for (Object[] rowPWRReport : pwrReportProjectList) {

					BigInteger allocationType;

					PWRReport pwrReport = new PWRReport();

					pwrReport.setProjectName((String) rowPWRReport[0]);

					if (Constants.BILLABLE_FULL_TIME.equalsIgnoreCase((String) rowPWRReport[1])) {
						pwrReport.setBillableFullTime((String) rowPWRReport[1]);
						allocationType = (BigInteger) rowPWRReport[2];
						pwrReport.setBillableFullTimeCount((allocationType.longValue()));

					}
					if (Constants.BILLABLE_PARTIA_SHAREDPOOL_FIXBIDPROJECTS.equalsIgnoreCase((String) rowPWRReport[1])) {
						pwrReport.setBillablePartiaSharedpoolFixbidprojects(((String) rowPWRReport[1]));
						allocationType = (BigInteger) rowPWRReport[2];
						pwrReport.setBillablePartiaSharedpoolFixbidprojectsCount(allocationType.longValue());

					}
					if (Constants.NONBILLABLE_ABSCONDING.equalsIgnoreCase((String) rowPWRReport[1])) {
						pwrReport.setNonBillableAbsconding(((String) rowPWRReport[1]));
						allocationType = (BigInteger) rowPWRReport[2];
						pwrReport.setNonBillableAbscondingCount(allocationType.longValue());

					}
					if (Constants.NONBILLABLE_ACCOUNT_MANAGEMENT.equalsIgnoreCase((String) rowPWRReport[1])) {
						pwrReport.setNonBillableAccountManagement(((String) rowPWRReport[1]));
						allocationType = (BigInteger) rowPWRReport[2];
						pwrReport.setNonBillableAccountManagementCount(allocationType.longValue());

					}
					if (Constants.NONBILLABLE_BLOCKED.equalsIgnoreCase((String) rowPWRReport[1])) {
						pwrReport.setNonBillableBlocked(((String) rowPWRReport[1]));
						allocationType = (BigInteger) rowPWRReport[2];
						pwrReport.setNonBillableBlockedCount(allocationType.longValue());

					}
					if (Constants.NONBILLABLE_CONTINGENT.equalsIgnoreCase((String) rowPWRReport[1])) {
						pwrReport.setNonBillableContingent(((String) rowPWRReport[1]));
						allocationType = (BigInteger) rowPWRReport[2];
						pwrReport.setNonBillableContingentCount(allocationType.longValue());

					}
					if (Constants.NONBILLABLE_DELIVERY_MANAGEMENT.equalsIgnoreCase((String) rowPWRReport[1])) {
						pwrReport.setNonBillableDeliveryManagement(((String) rowPWRReport[1]));
						allocationType = (BigInteger) rowPWRReport[2];
						pwrReport.setNonBillableDeliveryManagementCount(allocationType.longValue());

					}
					if (Constants.NONBILLABLE_EXITRELEASE.equalsIgnoreCase((String) rowPWRReport[1])) {
						pwrReport.setNonBillableExitrelease(((String) rowPWRReport[1]));
						allocationType = (BigInteger) rowPWRReport[2];
						pwrReport.setNonBillableExitreleaseCount(allocationType.longValue());

					}
					if (Constants.NONBILLABLE_INSIDESALE.equalsIgnoreCase((String) rowPWRReport[1])) {
						pwrReport.setNonBillableInsidesale(((String) rowPWRReport[1]));
						allocationType = (BigInteger) rowPWRReport[2];
						pwrReport.setNonBillableInsidesaleCount(allocationType.longValue());

					}
					if (Constants.NONBILLABLE_INVESTMENT.equalsIgnoreCase((String) rowPWRReport[1])) {
						pwrReport.setNonBillableInvestment(((String) rowPWRReport[1]));
						allocationType = (BigInteger) rowPWRReport[2];
						pwrReport.setNonBillableInvestmentCount(allocationType.longValue());

					}
					if (Constants.NONBILLABLE_LONGLEAVE.equalsIgnoreCase((String) rowPWRReport[1])) {
						pwrReport.setNonBillableLongleave(((String) rowPWRReport[1]));
						allocationType = (BigInteger) rowPWRReport[2];
						pwrReport.setNonBillableLongleaveCount(allocationType.longValue());

					}
					if (Constants.NONBILLABLE_MANAGEMENT.equalsIgnoreCase((String) rowPWRReport[1])) {
						pwrReport.setNonBillableManagement(((String) rowPWRReport[1]));
						allocationType = (BigInteger) rowPWRReport[2];
						pwrReport.setNonBillableManagementCount(allocationType.longValue());

					}
					if (Constants.NONBILLABLE_OPERATIONS.equalsIgnoreCase((String) rowPWRReport[1])) {
						pwrReport.setNonBillableOperations(((String) rowPWRReport[1]));
						allocationType = (BigInteger) rowPWRReport[2];
						pwrReport.setNonBillableOperationsCount(allocationType.longValue());

					}
					if (Constants.NONBILLABLE_PMO.equalsIgnoreCase((String) rowPWRReport[1])) {
						pwrReport.setNonBillablePmo(((String) rowPWRReport[1]));
						allocationType = (BigInteger) rowPWRReport[2];
						pwrReport.setNonBillablePmoCount(allocationType.longValue());

					}
					if (Constants.NONBILLABLE_PRESALE.equalsIgnoreCase((String) rowPWRReport[1])) {
						pwrReport.setNonBillablePresale(((String) rowPWRReport[1]));
						allocationType = (BigInteger) rowPWRReport[2];
						pwrReport.setNonBillablePresaleCount(allocationType.longValue());

					}
					if (Constants.NONBILLABLE_SALES.equalsIgnoreCase((String) rowPWRReport[1])) {
						pwrReport.setNonBillableSales(((String) rowPWRReport[1]));
						allocationType = (BigInteger) rowPWRReport[2];
						pwrReport.setNonBillableSalesCount(allocationType.longValue());

					}
					if (Constants.NONBILLABLE_SHADOW.equalsIgnoreCase((String) rowPWRReport[1])) {
						pwrReport.setNonBillableShadow(((String) rowPWRReport[1]));
						allocationType = (BigInteger) rowPWRReport[2];
						pwrReport.setNonBillableShadowCount(allocationType.longValue());

					}
					if (Constants.NONBILLABLE_TRAINEE.equalsIgnoreCase((String) rowPWRReport[1])) {
						pwrReport.setNonBillableTrainee(((String) rowPWRReport[1]));
						allocationType = (BigInteger) rowPWRReport[2];
						pwrReport.setNonBillableTraineeCount(allocationType.longValue());

					}
					if (Constants.NONBILLABLE_TRANSITION.equalsIgnoreCase((String) rowPWRReport[1])) {
						pwrReport.setNonBillableTransition(((String) rowPWRReport[1]));
						allocationType = (BigInteger) rowPWRReport[2];
						pwrReport.setNonBillableTransitionCount(allocationType.longValue());

					}
					if (Constants.NONBILLABLE_UNALLOCATED.equalsIgnoreCase((String) rowPWRReport[1])) {
						pwrReport.setNonBillableUnallocated(((String) rowPWRReport[1]));
						allocationType = (BigInteger) rowPWRReport[2];
						pwrReport.setNonBillableUnallocatedCount(allocationType.longValue());

					}
					if (Constants.PIP.equalsIgnoreCase((String) rowPWRReport[1])) {
						pwrReport.setPip(((String) rowPWRReport[1]));
						allocationType = (BigInteger) rowPWRReport[2];
						pwrReport.setPipCount(allocationType.longValue());
					}

					if (!pwrReportList.isEmpty()) {

						if (pwrReportList.contains(pwrReport)) {

							PWRReport pwrReportProjectAllocationType = (PWRReport) projectAllocaionMap.get(pwrReport.getProjectName());

							if (Constants.BILLABLE_FULL_TIME.equalsIgnoreCase(pwrReportProjectAllocationType.getBillableFullTime())) {

								pwrReport.setBillableFullTime(pwrReportProjectAllocationType.getBillableFullTime());
								pwrReport.setBillableFullTimeCount(pwrReportProjectAllocationType.getBillableFullTimeCount());

							}
							if (Constants.BILLABLE_PARTIA_SHAREDPOOL_FIXBIDPROJECTS.equalsIgnoreCase(pwrReportProjectAllocationType.getBillablePartiaSharedpoolFixbidprojects())) {

								pwrReport.setBillablePartiaSharedpoolFixbidprojects(pwrReportProjectAllocationType.getBillablePartiaSharedpoolFixbidprojects());
								pwrReport.setBillablePartiaSharedpoolFixbidprojectsCount(pwrReportProjectAllocationType.getBillablePartiaSharedpoolFixbidprojectsCount());

							}
							if (Constants.NONBILLABLE_ABSCONDING.equalsIgnoreCase(pwrReportProjectAllocationType.getNonBillableAbsconding())) {

								pwrReport.setNonBillableAbsconding(pwrReportProjectAllocationType.getNonBillableAbsconding());
								pwrReport.setNonBillableAbscondingCount(pwrReportProjectAllocationType.getNonBillableAbscondingCount());

							}
							if (Constants.NONBILLABLE_ACCOUNT_MANAGEMENT.equalsIgnoreCase(pwrReportProjectAllocationType.getNonBillableAccountManagement())) {

								pwrReport.setNonBillableAccountManagement(pwrReportProjectAllocationType.getNonBillableAccountManagement());
								pwrReport.setNonBillableAccountManagementCount(pwrReportProjectAllocationType.getNonBillableAccountManagementCount());

							}
							if (Constants.NONBILLABLE_BLOCKED.equalsIgnoreCase(pwrReportProjectAllocationType.getNonBillableBlocked())) {

								pwrReport.setNonBillableBlocked((pwrReportProjectAllocationType.getNonBillableBlocked()));
								pwrReport.setNonBillableBlockedCount(pwrReportProjectAllocationType.getNonBillableBlockedCount());

							}
							if (Constants.NONBILLABLE_CONTINGENT.equalsIgnoreCase(pwrReportProjectAllocationType.getNonBillableContingent())) {

								pwrReport.setNonBillableContingent(pwrReportProjectAllocationType.getNonBillableContingent());
								pwrReport.setNonBillableContingentCount(pwrReportProjectAllocationType.getNonBillableContingentCount());

							}
							if (Constants.NONBILLABLE_DELIVERY_MANAGEMENT.equalsIgnoreCase(pwrReportProjectAllocationType.getNonBillableDeliveryManagement())) {

								pwrReport.setNonBillableDeliveryManagement(pwrReportProjectAllocationType.getNonBillableDeliveryManagement());
								pwrReport.setNonBillableDeliveryManagementCount(pwrReportProjectAllocationType.getNonBillableDeliveryManagementCount());

							}
							if (Constants.NONBILLABLE_EXITRELEASE.equalsIgnoreCase(pwrReportProjectAllocationType.getNonBillableExitrelease())) {

								pwrReport.setNonBillableExitrelease(pwrReportProjectAllocationType.getNonBillableExitrelease());
								pwrReport.setNonBillableExitreleaseCount(pwrReportProjectAllocationType.getNonBillableExitreleaseCount());

							}
							if (Constants.NONBILLABLE_INSIDESALE.equalsIgnoreCase(pwrReportProjectAllocationType.getNonBillableInsidesale())) {

								pwrReport.setNonBillableInsidesale(pwrReportProjectAllocationType.getNonBillableInsidesale());
								pwrReport.setNonBillableInsidesaleCount(pwrReportProjectAllocationType.getNonBillableInsidesaleCount());

							}
							if (Constants.NONBILLABLE_INVESTMENT.equalsIgnoreCase(pwrReportProjectAllocationType.getNonBillableInvestment())) {

								pwrReport.setNonBillableInvestment(pwrReportProjectAllocationType.getNonBillableInvestment());
								pwrReport.setNonBillableInvestmentCount(pwrReportProjectAllocationType.getNonBillableInvestmentCount());

							}
							if (Constants.NONBILLABLE_LONGLEAVE.equalsIgnoreCase(pwrReportProjectAllocationType.getNonBillableLongleave())) {

								pwrReport.setNonBillableLongleave(pwrReportProjectAllocationType.getNonBillableLongleave());
								pwrReport.setNonBillableLongleaveCount(pwrReportProjectAllocationType.getNonBillableLongleaveCount());

							}
							if (Constants.NONBILLABLE_MANAGEMENT.equalsIgnoreCase(pwrReportProjectAllocationType.getNonBillableManagement())) {

								pwrReport.setNonBillableManagement(pwrReportProjectAllocationType.getNonBillableManagement());
								pwrReport.setNonBillableManagementCount(pwrReportProjectAllocationType.getNonBillableManagementCount());

							}
							if (Constants.NONBILLABLE_OPERATIONS.equalsIgnoreCase(pwrReportProjectAllocationType.getNonBillableOperations())) {

								pwrReport.setNonBillableOperations(pwrReportProjectAllocationType.getNonBillableOperations());
								pwrReport.setNonBillableOperationsCount(pwrReportProjectAllocationType.getNonBillableOperationsCount());

							}
							if (Constants.NONBILLABLE_PMO.equalsIgnoreCase(pwrReportProjectAllocationType.getNonBillablePmo())) {

								pwrReport.setNonBillablePmo(pwrReportProjectAllocationType.getNonBillablePmo());
								pwrReport.setNonBillablePmoCount(pwrReportProjectAllocationType.getNonBillablePmoCount());

							}
							if (Constants.NONBILLABLE_PRESALE.equalsIgnoreCase(pwrReportProjectAllocationType.getNonBillablePresale())) {

								pwrReport.setNonBillablePresale(pwrReportProjectAllocationType.getNonBillablePresale());
								pwrReport.setNonBillablePresaleCount(pwrReportProjectAllocationType.getNonBillablePresaleCount());

							}
							if (Constants.NONBILLABLE_SALES.equalsIgnoreCase(pwrReportProjectAllocationType.getNonBillableSales())) {

								pwrReport.setNonBillableSales(pwrReportProjectAllocationType.getNonBillableSales());
								pwrReport.setNonBillableSalesCount(pwrReportProjectAllocationType.getNonBillableSalesCount());

							}
							if (Constants.NONBILLABLE_SHADOW.equalsIgnoreCase(pwrReportProjectAllocationType.getNonBillableShadow())) {

								pwrReport.setNonBillableShadow(pwrReportProjectAllocationType.getNonBillableShadow());
								pwrReport.setNonBillableShadowCount(pwrReportProjectAllocationType.getNonBillableShadowCount());

							}
							if (Constants.NONBILLABLE_TRAINEE.equalsIgnoreCase(pwrReportProjectAllocationType.getNonBillableTrainee())) {

								pwrReport.setNonBillableTrainee(pwrReportProjectAllocationType.getNonBillableTrainee());
								pwrReport.setNonBillableTraineeCount(pwrReportProjectAllocationType.getNonBillableTraineeCount());

							}
							if (Constants.NONBILLABLE_TRANSITION.equalsIgnoreCase(pwrReportProjectAllocationType.getNonBillableTransition())) {

								pwrReport.setNonBillableTransition(pwrReportProjectAllocationType.getNonBillableTransition());
								pwrReport.setNonBillableTransitionCount(pwrReportProjectAllocationType.getNonBillableTransitionCount());

							}
							if (Constants.NONBILLABLE_UNALLOCATED.equalsIgnoreCase(pwrReportProjectAllocationType.getNonBillableUnallocated())) {

								pwrReport.setNonBillableUnallocated(pwrReportProjectAllocationType.getNonBillableUnallocated());
								pwrReport.setNonBillableUnallocatedCount(pwrReportProjectAllocationType.getNonBillableUnallocatedCount());

							}
							if (Constants.PIP.equalsIgnoreCase(pwrReportProjectAllocationType.getPip())) {

								pwrReport.setPip(pwrReportProjectAllocationType.getPip());
								pwrReport.setPipCount(pwrReportProjectAllocationType.getPipCount());

							}

							pwrReportList.remove(pwrReportProjectAllocationType);

						}

						pwrReportList.add(pwrReport);

					} else {

						pwrReportList.add(pwrReport);

					}

					long totalCount = 0;

					totalCount = pwrReport.getBillableFullTimeCount() + pwrReport.getBillablePartiaSharedpoolFixbidprojectsCount() + pwrReport.getNonBillableAbscondingCount()
							+ pwrReport.getNonBillableAccountManagementCount() + pwrReport.getNonBillableBlockedCount() + pwrReport.getNonBillableContingentCount()
							+ pwrReport.getNonBillableDeliveryManagementCount() + pwrReport.getNonBillableExitreleaseCount() + pwrReport.getNonBillableInsidesaleCount()
							+ pwrReport.getNonBillableInvestmentCount() + pwrReport.getNonBillableLongleaveCount()

							+ pwrReport.getNonBillableManagementCount() + pwrReport.getNonBillableOperationsCount() + pwrReport.getNonBillablePmoCount() + pwrReport.getNonBillablePresaleCount()
							+ pwrReport.getNonBillableSalesCount() + pwrReport.getNonBillableShadowCount() + pwrReport.getNonBillableTraineeCount() + pwrReport.getNonBillableTransitionCount()
							+ pwrReport.getNonBillableUnallocatedCount()

							+ pwrReport.getPipCount();

					pwrReport.setTotal(totalCount);

					projectAllocaionMap.put(pwrReport.getProjectName(), pwrReport);
				}

			}

		} catch (HibernateException e) {

			e.printStackTrace();
			logger.debug(e.getMessage());
		}

		logger.debug("--------PWRReportDaoImpl getRMReport method start-------");
		return pwrReportList;
	}

}
