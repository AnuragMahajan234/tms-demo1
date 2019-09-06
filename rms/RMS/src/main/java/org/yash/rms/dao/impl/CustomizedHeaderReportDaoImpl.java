package org.yash.rms.dao.impl;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
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
import org.yash.rms.dao.CustomizedHeaderReportDao;
import org.yash.rms.namedquery.RmsNamedQuery;
import org.yash.rms.report.dto.MuneerReport;
import org.yash.rms.report.dto.MuneerReportDto;
import org.yash.rms.util.Constants;

@Repository("customizedHeaderReportDao")
public class CustomizedHeaderReportDaoImpl implements CustomizedHeaderReportDao {

	@PersistenceContext(name = "mysql", type = PersistenceContextType.TRANSACTION)
	private EntityManager entityManager;
	
	
	public EntityManager getEntityManager() {
		return entityManager;
	}
	
	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	private static final Logger logger = LoggerFactory
			.getLogger(CustomizedHeaderReportDaoImpl.class);


	public Set<MuneerReport> getMuneerReport(List<Integer> orgIdList,
			List<Integer> locIdList, List<Integer> projIdList, String roleId,
			String reportId, Date weekDate) {

		logger.debug("--------MuneerReportDaoImpl getMuneerReport method start-------");

		Session session = (Session) getEntityManager().getDelegate();

		Set<MuneerReport> muneerReportSet = new HashSet<MuneerReport>();

		Map<Integer, MuneerReport> muneerReportMap = new HashMap<Integer, MuneerReport>();

		List<Integer> reportIdList = new ArrayList<Integer>();

		Map<String, MuneerReport> mapMuneerReport = new HashMap<String, MuneerReport>();

		Date currentDate = new Date();

		Query query = null;

		if (reportId != null) {

			if (reportId.equalsIgnoreCase("1")) {
				reportIdList.add(1);

			} else if (reportId.equalsIgnoreCase("2")) {

				reportIdList.add(0);
				// reportIdList.add(1);
			} else {

				reportIdList.add(0);
				reportIdList.add(1);
			}

		}

		try {

			if (roleId.equalsIgnoreCase("Release_Date_IS_NULL")) {

				if (reportId.equalsIgnoreCase("1")) {

					query = session.createSQLQuery(RmsNamedQuery
							.getMuneerReportResource());

					query.setParameterList("orgIdList", orgIdList);
					query.setParameterList("projIdList", projIdList);
					query.setParameterList("reportIdList", reportIdList);
					query.setParameter("weekDate", weekDate);
				}

				else {

					query = session.createSQLQuery(RmsNamedQuery
							.getUnAllocatedMuneerReportResource());

					query.setParameterList("orgIdList", orgIdList);
					query.setParameterList("projIdList", projIdList);
					query.setParameter("weekDate", weekDate);
				}

			}

			else if (roleId.equalsIgnoreCase("Release_Date_IS_Not_NULL")) {

				query = session.createSQLQuery(RmsNamedQuery
						.getReleasedMuneerReportResource());
				query.setParameterList("orgIdList", orgIdList);
				query.setParameterList("projIdList", projIdList);

			}

			List<Object[]> objectList = query.list();

			if (!objectList.isEmpty()) {

				for (Object[] obj : objectList) {

					MuneerReport muneerReport = new MuneerReport();
					int i = 0;
					muneerReport.setYashEmpID((String) obj[i]);
					muneerReport.setEmployeeName((String) obj[++i]);
					muneerReport.setEmailId((String) obj[++i]);
					muneerReport.setDesignation((String) obj[++i]);
					muneerReport.setGrade((String) obj[++i]);
					muneerReport.setDateOfJoining((Date) obj[++i]);
					muneerReport.setReleaseDate((Date) obj[++i]);
					muneerReport.setBaseLocation((String) obj[++i]);
					muneerReport.setCurrentLocation((String) obj[++i]);
					muneerReport.setParentBu((String) obj[++i]);
					muneerReport.setCurrentBu((String) obj[++i]);
					muneerReport.setOwnership((String) obj[++i]);
					muneerReport.setCurrentRM1((String) obj[++i]);
					muneerReport.setCurrentRM2((String) obj[++i]);
					muneerReport.setPrimaryProject((String) obj[++i]);

					Object isCurrProjectFlag = (Object) obj[++i];

					if (isCurrProjectFlag instanceof Boolean) {

						Boolean currProjectFlag = (Boolean) isCurrProjectFlag;

						if (currProjectFlag != null) {

							if (currProjectFlag == true) {

								muneerReport.setCurrentProjectIndicator("1");

							} else if (currProjectFlag == false) {

								muneerReport.setCurrentProjectIndicator("0");
							}
						}
					} else if (isCurrProjectFlag instanceof Byte) {

						Byte currentProjectFlag = (Byte) isCurrProjectFlag;

						if (currentProjectFlag != null) {

							if (currentProjectFlag == 1) {

								muneerReport
								.setCurrentProjectIndicator(isCurrProjectFlag
										.toString());

							} else if (currentProjectFlag == 0) {

								muneerReport
								.setCurrentProjectIndicator(isCurrProjectFlag
										.toString());
							}
						}
					} else {

						muneerReport.setCurrentProjectIndicator(null);
					}

					muneerReport.setCustomerName((String) obj[++i]);
					muneerReport.setProjectBu((String) obj[++i]);
					muneerReport.setAllocationStartDate((Date) obj[++i]);
					muneerReport.setAllocationType((String) obj[++i]);
					muneerReport.setTransferDate((Date) obj[++i]);
					muneerReport.setVisa((String) obj[++i]);

					Object pSkills = obj[++i];

					if (pSkills == null) {

						muneerReport.setPrimarySkill("");
					} else {

						muneerReport.setPrimarySkill((String) pSkills);

					}

					Object sSkills = obj[++i];

					if (sSkills == null) {

						muneerReport.setSecondarySkill("");
					} else {

						muneerReport.setSecondarySkill((String) sSkills);
					}

					if (!muneerReportSet.isEmpty()) {

						MuneerReport preMuneerReport = null;

						String preSkill = (String) pSkills;

						String secSkill = (String) sSkills;

						if (muneerReportSet.contains(muneerReport)) {

							preMuneerReport = (MuneerReport) muneerReportMap
									.get(muneerReport.hashCode());

							if (preMuneerReport != null) {

								if (preMuneerReport.getPrimarySkill() != null) {

									if (preSkill != null
											&& !(preMuneerReport
													.getPrimarySkill()
													.contains(preSkill))) {

										preSkill += ","
												+ preMuneerReport
												.getPrimarySkill();
										muneerReport.setPrimarySkill(preSkill);

									} else {

										muneerReport
										.setPrimarySkill((String) preMuneerReport
												.getPrimarySkill());
									}

								}
								if (preMuneerReport.getSecondarySkill() != null) {

									if (secSkill != null
											&& !(preMuneerReport
													.getSecondarySkill()
													.contains(secSkill))) {

										secSkill += ","
												+ preMuneerReport
												.getSecondarySkill();
										muneerReport
										.setSecondarySkill(secSkill);

									} else {

										muneerReport
										.setSecondarySkill((String) preMuneerReport
												.getSecondarySkill());
									}
								}

								muneerReportSet.remove(preMuneerReport);
							}
						}
					}

					muneerReport.setCustomerId((String) obj[++i]);
					muneerReport.setLastUpdateBy((String) obj[++i]);
					muneerReport.setLastupdateTimeStamp((Timestamp) obj[++i]);
					muneerReport.setTeamName((String) obj[++i]);
					muneerReport.setRemarks((String) obj[++i]);
					muneerReport.setCompetency((String) obj[++i]);
					muneerReport.setAllocationEndDate((Date) obj[i + 8]);

					if (obj[i + 1] != null) {

						if (obj[i + 1] instanceof BigInteger) {

							muneerReport
							.setAllocatedSince(((BigInteger) obj[++i])
									.intValue());
						}

						else {

							muneerReport.setAllocatedSince(BigInteger.valueOf(
									(Integer) obj[++i]).intValue());
						}
					}

					String[] bgBu = muneerReport.getParentBu().split("-");
					muneerReport.setBg(bgBu[0]);
					muneerReport.setBu(bgBu[1]);
					String[] currentBgBu = muneerReport.getCurrentBu().split(
							"-");
					muneerReport.setCurrentBgName(currentBgBu[0]);
					muneerReport.setCurrentBuName(currentBgBu[1]);

					if (muneerReport.getAllocationType() != null) {

						if (muneerReport.getAllocationType().equalsIgnoreCase(
								Constants.BILLABLE_FULL_TIME)
								|| muneerReport
								.getAllocationType()
								.equalsIgnoreCase(
										Constants.BILLABLE_PARTIA_SHAREDPOOL_FIXBIDPROJECTS)) {

							muneerReport.setBillable("Yes");

						} else {

							muneerReport.setBillable("No");
						}
					}
					// Add unique Object in HashSet
					if (reportId.equalsIgnoreCase("1")) {

						muneerReportSet.add(muneerReport);
					} else {

						if ((muneerReport.getAllocationStartDate() == null && muneerReport
								.getPrimaryProject() == null)
								|| (muneerReport.getAllocationEndDate() != null
								&& currentDate.after(muneerReport
										.getAllocationStartDate()) && currentDate
										.after(muneerReport
												.getAllocationEndDate()))) {

							muneerReport
							.setPrimaryProject("No Project Allocated");
						}
						// changes for discrepancy report
						String empID = muneerReport.getYashEmpID();
						if (muneerReport.getCurrentProjectIndicator() == null) {
							muneerReport.setCurrentProjectIndicator("0");
						}
						if (reportId.equals("2")
								&& (muneerReport.getCurrentProjectIndicator()
										.equals("0"))) {
							query = session
									.createSQLQuery("Select * FROM resource_allocation resalloc WHERE resalloc.curProj= 1 AND resalloc.alloc_start_date<:weekDate AND (resalloc.alloc_end_date>=:weekDate OR resalloc.alloc_end_date IS NULL)  AND resalloc.employee_id = (SELECT res.employee_id FROM resource res WHERE res.yash_emp_id =:empid)");
							query.setParameter("empid", empID);
							query.setParameter("weekDate", weekDate);
							List<Object[]> list = query.list();
							if (list.isEmpty())
								muneerReportSet.add(muneerReport);
						} else if (reportId.equals("2")
								&& muneerReport.getCurrentProjectIndicator()
								.equals("1")) {
							try{
								if (muneerReport.getAllocationEndDate().before(
										weekDate)) {
									muneerReportSet.add(muneerReport);
								}
							}
							catch(NullPointerException ex){
								muneerReportSet.add(muneerReport);
							}
							try {
								if (((muneerReport.getAllocationStartDate()
										.after(weekDate) && muneerReport
										.getAllocationEndDate() == null) || (muneerReport
												.getAllocationStartDate().after(
														weekDate) && muneerReport
														.getAllocationEndDate().after(weekDate)))) {
									muneerReportSet.add(muneerReport);
								}

							} catch (NullPointerException ex) {
								System.out
								.println("end date not allocated to the resource");
								muneerReport.setAllocationEndDate(weekDate);
							}

						}

					}
					// unique Object object having almost same hascode value
					muneerReportMap.put(muneerReport.hashCode(), muneerReport);
					mapMuneerReport.put(muneerReport.getYashEmpID(),
							muneerReport);
				}

			}

		} catch (HibernateException e) {

			e.printStackTrace();
			logger.debug(e.getMessage());
		}
		logger.debug("--------MuneerReportDaoImpl getMuneerReport method start-------");

		return muneerReportSet;
	}

	@SuppressWarnings({ "unchecked" })
	public Map<String, MuneerReportDto> getMuneerClientReport(
			List<Integer> orgIdList, List<Integer> locIdList,
			List<Integer> projIdList, String roleId, String reportId,
			Date weekDate) {

		logger.debug("--------MuneerReportDaoImpl getMuneerClientReport method start-------");

		Map<String, MuneerReportDto> clientData = new HashMap<String, MuneerReportDto>();

		Session session = (Session) getEntityManager().getDelegate();

		// StringBuilder releaseDateCondition = new StringBuilder();

		List<Integer> reportIdList = new ArrayList<Integer>();

		Query query = null;

		if (reportId != null) {

			if (reportId.equalsIgnoreCase("1")) {
				reportIdList.add(1);

			} else if (reportId.equalsIgnoreCase("2")) {

				reportIdList.add(0);
				reportIdList.add(1);
			} else {

				reportIdList.add(0);
				reportIdList.add(1);
			}

		}

		try {

			if (roleId.equalsIgnoreCase("Release_Date_IS_NULL")) {

				if (reportId.equalsIgnoreCase("1")) {

					query = session.createSQLQuery(RmsNamedQuery
							.getMuneerReportResourceClient());

					query.setParameterList("orgIdList", orgIdList);
					query.setParameterList("projIdList", projIdList);
					// query.setParameterList("reportIdList", reportIdList);
					query.setParameter("weekDate", weekDate);
				}

				else {

					query = session.createSQLQuery(RmsNamedQuery
							.getCountForUnAllocatedMuneerReportResource());

					query.setParameterList("orgIdList", orgIdList);
					query.setParameterList("projIdList", projIdList);
					query.setParameter("weekDate", weekDate);
				}

			}

			else if (roleId.equalsIgnoreCase("Release_Date_IS_Not_NULL")) {

				query = session.createSQLQuery(RmsNamedQuery
						.getReleasedMuneerReportResourceClient());
				query.setParameterList("orgIdList", orgIdList);
				query.setParameterList("projIdList", projIdList);

			}

			List<Object[]> objectList = query.list();

			List<String> customerNameList = new ArrayList<String>();

			if (!objectList.isEmpty()) {
				for (Object[] obj : objectList) {
					if (null != obj[0]) {
						String customer = String.valueOf(obj[0]);
						customerNameList.add(customer);
					}

				}

				List<MuneerReportDto> muneerReportList = new ArrayList<MuneerReportDto>();

				List<Object> listDistinct = customerNameList.stream()
						.distinct().collect(Collectors.toList());

				Set<String> allocationTypeSet = new HashSet<String>();

				for (Object customer : listDistinct) {

					MuneerReportDto muneerReport = new MuneerReportDto();

					for (Object[] obj : objectList) {

						if (obj[0] != null
								&& String.valueOf(obj[0]).equals(customer)) {

							String allocType = String.valueOf(obj[1]);

							Integer count = Integer.parseInt(String
									.valueOf(obj[2]));

							if (allocType.equals("Billable (Full Time (FTE))")) {
								muneerReport.setBillableCount(count);
								allocationTypeSet.add(allocType);
							}
							if (allocType
									.equals("Billable (Partial / Shared Pool / Fix Bid Projects)")) {
								muneerReport.setBillablePartialCount(count);
								allocationTypeSet.add(allocType);
							}
							if (allocType.equals("Non-Billable (Blocked)")) {
								muneerReport.setNonBillableBlocked(count);
								allocationTypeSet.add(allocType);
							}
							if (allocType.equals("Non-Billable (Contingent)")) {
								muneerReport.setNonBillableContigent(count);
								allocationTypeSet.add(allocType);
							}
							if (allocType
									.equals("Non-Billable (Delivery Management)")) {
								muneerReport.setNonBillableDelMngmt(count);
								allocationTypeSet.add(allocType);
							}
							if (allocType.equals("Non-Billable (Operations)")) {
								muneerReport.setNonBillableOperations(count);
								allocationTypeSet.add(allocType);
							}
							if (allocType.equals("Non-Billable (Shadow)")) {
								muneerReport.setNonBillableShadow(count);
								allocationTypeSet.add(allocType);
							}
							if (allocType.equals("Non-Billable (Trainee)")) {
								muneerReport.setNonBillableTrainee(count);
								allocationTypeSet.add(allocType);
							}
							if (allocType.equals("Non-Billable (Transition)")) {
								muneerReport.setNonBillableTranstion(count);
								allocationTypeSet.add(allocType);
							}
							if (allocType.equals("Non-Billable (Unallocated)")) {
								muneerReport.setNonBillableUnAllocated(count);
								allocationTypeSet.add(allocType);
							}
							if (allocType.equals("Non-Billable (Absconding)")) {
								muneerReport.setNonBillableAbsconding(count);
								allocationTypeSet.add(allocType);
							}
							if (allocType
									.equals("Non-Billable (Account Management)")) {
								muneerReport.setNonBillableAccunMgmt(count);
								allocationTypeSet.add(allocType);
							}
							if (allocType.equals("Non-Billable (InsideSale)")) {
								muneerReport.setNonBillableInsideSale(count);
								allocationTypeSet.add(allocType);
							}
							if (allocType.equals("Non-Billable (Investment)")) {
								muneerReport.setNonBillableInvstment(count);
								allocationTypeSet.add(allocType);
							}
							if (allocType.equals("Non-Billable (Long leave)")) {
								muneerReport.setNonBillableLongLeave(count);
								allocationTypeSet.add(allocType);
							}
							if (allocType.equals("Non-Billable (Management)")) {
								muneerReport.setNonBillableMngmnt(count);
								allocationTypeSet.add(allocType);
							}
							if (allocType.equals("Non-Billable (PMO)")) {
								muneerReport.setNonBillablePMO(count);
								allocationTypeSet.add(allocType);
							}
							if (allocType.equals("Non-Billable (PreSale)")) {
								muneerReport.setNonBillablePreSale(count);
								allocationTypeSet.add(allocType);
							}
							if (allocType.equals("Non-Billable (Sales)")) {
								muneerReport.setNonBillableSale(count);
								allocationTypeSet.add(allocType);
							}
							if (allocType.equals("OUTBOUND (Exit/Release)")) {
								muneerReport.setOutBoundExitrelease(count);
								allocationTypeSet.add(allocType);
							}
							if (allocType.equals("PIP")) {
								muneerReport.setPip(count);
								allocationTypeSet.add(allocType);
							}

						}
					}

					muneerReportList.add(muneerReport);
					clientData.put(String.valueOf(customer), muneerReport);
				}

				if (!allocationTypeSet.contains(Constants.BILLABLE_FULL_TIME)) {
					for (MuneerReportDto dto : muneerReportList) {
						dto.setBillableCount(-1);
					}
				}
				if (!allocationTypeSet
						.contains(Constants.BILLABLE_PARTIA_SHAREDPOOL_FIXBIDPROJECTS)) {
					for (MuneerReportDto dto : muneerReportList) {
						dto.setBillablePartialCount(-1);
					}

				}
				if (!allocationTypeSet.contains(Constants.NONBILLABLE_BLOCKED)) {
					for (MuneerReportDto dto : muneerReportList) {
						dto.setNonBillableBlocked(-1);
					}
				}
				if (!allocationTypeSet
						.contains(Constants.NONBILLABLE_CONTINGENT)) {
					for (MuneerReportDto dto : muneerReportList) {
						dto.setNonBillableContigent(-1);
					}

				}

				if (!allocationTypeSet
						.contains(Constants.NONBILLABLE_DELIVERY_MANAGEMENT)) {
					for (MuneerReportDto dto : muneerReportList) {
						dto.setNonBillableDelMngmt(-1);
					}

				}
				if (!allocationTypeSet
						.contains(Constants.NONBILLABLE_OPERATIONS)) {
					for (MuneerReportDto dto : muneerReportList) {
						dto.setNonBillableOperations(-1);
					}
				}
				if (!allocationTypeSet.contains(Constants.NONBILLABLE_SHADOW)) {
					for (MuneerReportDto dto : muneerReportList) {
						dto.setNonBillableShadow(-1);
					}
				}
				if (!allocationTypeSet.contains(Constants.NONBILLABLE_TRAINEE)) {
					for (MuneerReportDto dto : muneerReportList) {
						dto.setNonBillableTrainee(-1);
					}

				}
				if (!allocationTypeSet
						.contains(Constants.NONBILLABLE_TRANSITION)) {
					for (MuneerReportDto dto : muneerReportList) {
						dto.setNonBillableTranstion(-1);
					}
				}
				if (!allocationTypeSet
						.contains(Constants.NONBILLABLE_UNALLOCATED)) {
					for (MuneerReportDto dto : muneerReportList) {
						dto.setNonBillableUnAllocated(-1);
					}
				}

				if (!allocationTypeSet
						.contains(Constants.NONBILLABLE_ABSCONDING)) {
					for (MuneerReportDto dto : muneerReportList) {
						dto.setNonBillableAbsconding(-1);
					}
				}
				if (!allocationTypeSet
						.contains(Constants.NONBILLABLE_ACCOUNT_MANAGEMENT)) {
					for (MuneerReportDto dto : muneerReportList) {
						dto.setNonBillableAccunMgmt(-1);
					}
				}
				if (!allocationTypeSet
						.contains(Constants.NONBILLABLE_INSIDESALE)) {
					for (MuneerReportDto dto : muneerReportList) {
						dto.setNonBillableInsideSale(-1);
					}
				}
				if (!allocationTypeSet
						.contains(Constants.NONBILLABLE_INVESTMENT)) {
					for (MuneerReportDto dto : muneerReportList) {
						dto.setNonBillableInvstment(-1);
					}
				}
				if (!allocationTypeSet
						.contains(Constants.NONBILLABLE_LONGLEAVE)) {
					for (MuneerReportDto dto : muneerReportList) {
						dto.setNonBillableLongLeave(-1);
					}
				}
				if (!allocationTypeSet
						.contains(Constants.NONBILLABLE_MANAGEMENT)) {
					for (MuneerReportDto dto : muneerReportList) {
						dto.setNonBillableMngmnt(-1);
					}
				}
				if (!allocationTypeSet.contains(Constants.NONBILLABLE_PMO)) {
					for (MuneerReportDto dto : muneerReportList) {
						dto.setNonBillablePMO(-1);
					}
				}
				if (!allocationTypeSet.contains(Constants.NONBILLABLE_PRESALE)) {
					for (MuneerReportDto dto : muneerReportList) {
						dto.setNonBillablePreSale(-1);
					}
				}
				if (!allocationTypeSet.contains(Constants.NONBILLABLE_SALES)) {
					for (MuneerReportDto dto : muneerReportList) {
						dto.setNonBillableSale(-1);
					}
				}

				if (!allocationTypeSet
						.contains(Constants.NONBILLABLE_EXITRELEASE)) {
					for (MuneerReportDto dto : muneerReportList) {
						dto.setOutBoundExitrelease(-1);
					}
				}
				if (!allocationTypeSet.contains(Constants.PIP)) {
					for (MuneerReportDto dto : muneerReportList) {
						dto.setPip(-1);
					}
				}

				List<String> customerKey = new ArrayList<String>();

				Iterator<Entry<String, MuneerReportDto>> entries = clientData
						.entrySet().iterator();

				while (entries.hasNext()) {
					Map.Entry<String, MuneerReportDto> entry = (Map.Entry<String, MuneerReportDto>) entries
							.next();
					String key = (String) entry.getKey();
					customerKey.add(key);
				}

				clientData = new HashMap<String, MuneerReportDto>();

				for (int k = 0; k < listDistinct.size(); k++) {

					clientData.put(listDistinct.get(k).toString(),
							muneerReportList.get(k));

				}

			}

		} catch (HibernateException e) {

			e.printStackTrace();
			logger.debug(e.getMessage());
		}

		logger.debug("--------MuneerReportDaoImpl getMuneerClientReport method end-------");

		return clientData;

	}

	public Set<MuneerReport> getHeaderReport(List<Integer> orgIdList,
			List<Integer> locIdList, List<Integer> projIdList, String roleId,
			String reportId, Date weekDate, List<String> sortableList) {

		logger.info("--------CustomizeReportImpl getResourceTransferFromBG4BU5 method start-------");
		String str = "56,47,57,58,59,60,16,86,61,62,6,17,7,4,39,63,26,64,65,66,54,67,68,87,88,89,90,91,18,92,93,69,70,71,72,73,74,75,76,55,50,53,2,49,19,20,9,15,48,21,22,13,24,25,51,1,8,10,12,52,14,100,11,77,78";
		List<String> myList = new ArrayList<String>(Arrays.asList(str.split(","))); 
		
		Set<MuneerReport> muneerReportSet = new HashSet<MuneerReport>();
		Query buResourcesQuery;
		List<String> orderList = new ArrayList<String>(sortableList);
		Session session = (Session) getEntityManager().getDelegate();
		   Collections.sort(orderList, Collections.reverseOrder());
		String selectedHeaders = String.join(", ", orderList);
		try {
			buResourcesQuery = session
					.createSQLQuery(RmsNamedQuery.getResourceReportBySelectedHeaders(selectedHeaders))
					//.setParameter("orgIdList", orgIdList)
					//.setParameter("locIdList", locIdList)
					//.setParameter("projIdList", projIdList)
					//.setParameter("roleId", roleId)
					//.setParameter("reportId", reportId)
					//.setParameter("weekDate", new SimpleDateFormat("yyyy-MM-dd").format(weekDate))
					; 

			@SuppressWarnings("unchecked")
			List<Object[]> objectList = buResourcesQuery.list();

			if (!objectList.isEmpty()) {

				for (Object[] obj : objectList) {

					MuneerReport muneerReport = new MuneerReport();
					int i = 0;
					if(sortableList.contains("Yash_Emp_Id"))
						muneerReport.setYashEmpID((String) obj[i]);
					if(sortableList.contains("Resource_Allocation_End_Date"))
						muneerReport.setAllocationEndDate((Date) obj[++i]);
					if(sortableList.contains("Release_Date"))
						muneerReport.setReleaseDate((Date) obj[++i]);
					if(sortableList.contains("Primary_skill"))
						muneerReport.setPrimarySkill((String) obj[++i]);
					if(sortableList.contains("Primary_Project"))
						muneerReport.setPrimaryProject((String) obj[++i]);
					if(sortableList.contains("Grade"))
						muneerReport.setGrade((String) obj[++i]);
					if(sortableList.contains("Employee_Name"))
						muneerReport.setEmployeeName((String) obj[++i]);
					if(sortableList.contains("Designation"))
						muneerReport.setDesignation((String) obj[++i]);
					if(sortableList.contains("Date_Of_Joining"))
						muneerReport.setDateOfJoining((Date) obj[++i]);
					if(sortableList.contains("Currnet_Bu"))
						muneerReport.setCurrentBu((String) obj[++i]);
					if(sortableList.contains("Current_Location"))
						muneerReport.setCurrentLocation((String) obj[++i]);
					if(sortableList.contains("Base_Location"))
						muneerReport.setBaseLocation((String) obj[++i]);
					if(sortableList.contains("Allocation_Type"))
						muneerReport.setAllocationType((String) obj[++i]);
					if(sortableList.contains("Allocation_Start_Date"))
						muneerReport.setAllocationStartDate((Date) obj[++i]);
					muneerReport.setSelectedHeaders( String.join(", ", sortableList));
					muneerReportSet.add(muneerReport); 
				}
			}
		} catch (HibernateException e) {
			e.printStackTrace();
			logger.error("Exception occured in getResourceTransferFromBG4BU5 method at DAO layer:-"
					+ e);
			throw e;
		}
		logger.info("--------CustomizeReportImpl getResourceTransferFromBG4BU5 method end-------");
		if(!muneerReportSet.isEmpty())
			System.out.println();
		return muneerReportSet;
	}

}
