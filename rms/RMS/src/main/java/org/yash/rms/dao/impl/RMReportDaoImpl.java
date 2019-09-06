package org.yash.rms.dao.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.yash.rms.dao.RMReportDao;
import org.yash.rms.domain.Resource;
import org.yash.rms.namedquery.RmsNamedQuery;
import org.yash.rms.report.dto.RMReport;
import org.yash.rms.util.Constants;

@Repository("rmReportDao")
public class RMReportDaoImpl implements RMReportDao {

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
			.getLogger(RMReportDaoImpl.class);

	public Set<RMReport> getRMReport(List<Integer> orgIdList,
			List<Integer> locIdList, List<Integer> projIdList, String roleId,
			String reportId) {

		logger.debug("--------RMReportDaoImpl getRMReport method start-------");

		Session session = (Session) getEntityManager().getDelegate();
		Set<RMReport> rmReportsSet= new HashSet<RMReport>();
		 Map<Integer, RMReport> rmReportMap = new HashMap<Integer, RMReport>();
		StringBuilder releaseDateCondition = new StringBuilder();
		List<Integer> reportIdList = new ArrayList<Integer>();
		ListIterator<RMReport> it =null;
		Map<String, RMReport> rmMap = new HashMap<String, RMReport>();
		Date currentDate = new Date();
		String coulmnName =	"yash_emp_id, Employee_Name, Emp_Email_id, Designation,Grade, Date_Of_Joining, Release_date, Base_location, " +
							"Current_location, Parent_Bu, Currnet_Bu, Ownership, Current_RM1, Current_RM2, Primary_Project, Current_Project_Flag, Customer_Name, " +
							"Project_Bu, Allocation_Start_date, Allocation_Type, Transfer_date, Visa, GROUP_CONCAT(DISTINCT primary_skill) Primary_skill," +
							" GROUP_CONCAT(DISTINCT secondary_skill) Secondary_skill, Customer_Id, lastupdated_id, last_timestamp, Bu_id, Current_bu_id, "+
							" current_project_id, location_id, project_id, project_bu_id, resource_allocation_end_date, allocation_type_id, employee_id, "+
							" allocation_priority, res_emp_id, alloc_id,  competency_skill, resource_type, ResumeYN, TefYN, Project_Mgr, Total_Exp, Rel_Exp, Difference, Project_Delivery_Mgr ";
		Query query=null;
		if (reportId != null) {

			if (reportId.equalsIgnoreCase("Primary")) {
				reportIdList.add(1);
				
			} else if(reportId.equalsIgnoreCase("Multiple")) {
				reportIdList.add(0);
				//reportIdList.add(1);
			}else{
				reportIdList.add(0);
				reportIdList.add(1);
			}

		}

		if (roleId.equalsIgnoreCase("Release_Date_IS_NULL")) {
			releaseDateCondition.append("(").append(Constants.Release_Date_IS_NULL).append(" OR ").append(Constants.Future_Release_Date).append(")");
		 
		} else if (roleId.equalsIgnoreCase("Release_Date_IS_Not_NULL")) {
			releaseDateCondition.append("(").append(Constants.Release_Date_IS_Not_NULL).append(" AND ").append(Constants.Is_Released).append(")");
			 
		} else {
			releaseDateCondition.append(Constants.Release_Date_IS_Not_NULL_OR_Null);
		}

		try {
			
			if (reportId.equalsIgnoreCase("Primary")) {
				
			
				query = session.createSQLQuery("SELECT " + coulmnName + " FROM vw_rm_report WHERE  Project_ID IN (:projIdList) AND  (Bu_Id IN (:orgIdList) OR Current_Bu_Id IN (:orgIdList) OR Project_Bu_ID IN (:orgIdList))  AND   Current_Project_Flag IN (:reportIdList) AND "
						+ releaseDateCondition.toString()+" AND (Resource_Allocation_End_Date IS NUll OR Resource_Allocation_End_Date>=CURDATE()) GROUP BY Yash_Emp_Id" +
						" UNION SELECT " + coulmnName + " FROM vw_discrepancy_rm_report WHERE  (Bu_Id IN (:orgIdList) OR Current_Bu_Id IN (:orgIdList) ) AND " +releaseDateCondition.toString() +" GROUP BY Yash_Emp_Id ");
					
					
			query.setParameterList("orgIdList", orgIdList);
			query.setParameterList("projIdList", projIdList);
			query.setParameterList("reportIdList", reportIdList);
			//query.setParameterList("locIdList", locIdList);
			}
			
			else if(reportId.equalsIgnoreCase("Multiple")){
				
				query = session.createSQLQuery("SELECT " + coulmnName + " FROM vw_rm_report WHERE Project_ID IN (:projIdList)   AND Current_Project_Flag IN (:reportIdList) AND "
						+ releaseDateCondition+" AND (Resource_Allocation_End_Date IS NUll OR Resource_Allocation_End_Date>=CURDATE()) GROUP BY YASH_EMP_ID,EMPLOYEE_NAME,Emp_Email_ID,`designation`,`grade`,`Date_Of_Joining`,`Release_Date`"
						+" ,Base_Location,Current_Location,Parent_Bu,`Currnet_Bu`,Ownership,Current_RM1,Current_RM2,Primary_Project"
						+" ,Current_Project_Flag,Customer_Name,Project_Bu,Allocation_Start_Date,`Allocation_Type`,`Transfer_Date`"
						+" ,Visa,Customer_Id,LastUpdated_ID,Last_TimeStamp,Bu_Id,Current_Bu_Id"
						+" ,Current_Project_Id,Location_id,Project_ID,Project_Bu_ID,Resource_Allocation_End_Date,Allocation_Type_Id");
						
				//query.setParameterList("orgIdList", orgIdList);
				query.setParameterList("projIdList", projIdList);
				query.setParameterList("reportIdList", reportIdList);
				//query.setParameterList("locIdList", locIdList);
				
			}else if(reportId.equalsIgnoreCase("Discrepancy")){
				query = session
						.createSQLQuery(
								RmsNamedQuery
										.getDiscrepancyRMReportResource(releaseDateCondition.toString(), coulmnName))
						.setParameterList("orgIdList", orgIdList)
						.setParameterList("projIdList", projIdList);
				
			}else{
				
				query = session
						.createSQLQuery(
								RmsNamedQuery
										.getUnAllocatedRMReportResource(releaseDateCondition.toString(), coulmnName))
						.setParameterList("orgIdList", orgIdList)
						.setParameterList("projIdList", projIdList);
				
				
				
			}
			
			List<Object[]> objectList = query.list();
           
			if (!objectList.isEmpty()) {
				for (Object[] obj : objectList) {
					RMReport rmReport = new RMReport();
					int i = 0;
					rmReport.setYashEmpID((String) obj[i]);
					rmReport.setEmployeeName((String) obj[++i]);
					rmReport.setEmailId((String) obj[++i]);
					rmReport.setDesignation((String) obj[++i]);
					rmReport.setGrade((String) obj[++i]);
					rmReport.setDateOfJoining((Date) obj[++i]);
					rmReport.setReleaseDate((Date) obj[++i]);
					rmReport.setBaseLocation((String) obj[++i]);
					rmReport.setCurrentLocation((String) obj[++i]);
					rmReport.setParentBu((String) obj[++i]);
					rmReport.setCurrentBu((String) obj[++i]);
					rmReport.setOwnership((String) obj[++i]);
					rmReport.setCurrentRM1((String) obj[++i]);
					rmReport.setCurrentRM2((String) obj[++i]);
					rmReport.setPrimaryProject((String) obj[++i]);
					Object isCurrProjectFlag = (Object) obj[++i];
					if (isCurrProjectFlag instanceof Boolean) {
						Boolean currProjectFlag = (Boolean) isCurrProjectFlag;
						if (currProjectFlag != null) {
							if (currProjectFlag == true) {
								rmReport.setCurrentProjectIndicator("true");
							} else if (currProjectFlag == false) {
								rmReport.setCurrentProjectIndicator("false");
							}
						}
					} else if (isCurrProjectFlag instanceof Byte) {
						Byte currProjectFlag = (Byte) isCurrProjectFlag;
						if (currProjectFlag != null) {
							if (currProjectFlag == 1) {
								rmReport.setCurrentProjectIndicator("true");
							} else if (currProjectFlag == 0) {
								rmReport.setCurrentProjectIndicator("false");
							}
						}
					} else {
						rmReport.setCurrentProjectIndicator(null);
					}
					
					rmReport.setCustomerName((String) obj[++i]);
					rmReport.setProjectBu((String) obj[++i]);
					Object val =  obj[++i];
					if(val instanceof Date)
						rmReport.setAllocationStartDate((Date) val);
					rmReport.setAllocationType((String) obj[++i]);	
					rmReport.setTransferDate((Date) obj[++i]);
					rmReport.setVisa((String) obj[++i]);
					Object pSkills =  obj[++i];
					
					rmReport.setPrimarySkill((String) obj[22]);
					rmReport.setSecondarySkill((String) obj[23]);
					
					if(rmReport.getSecondarySkill()==null){
						rmReport.setSecondarySkill("");
					}if(rmReport.getPrimarySkill()==null){
						rmReport.setPrimarySkill("");
					}
					/*if (pSkills == null) {
						rmReport.setPrimarySkill("");
					} else {
						rmReport.setPrimarySkill((String) pSkills);
						
					}*/
				
					Object sSkills =  obj[++i];
					//rmReport.setSecondarySkill("");
					/*if(sSkills==null){
						rmReport.setSecondarySkill("");
					}else{
						rmReport.setSecondarySkill((String) sSkills);
					}*/
					if (!rmReportsSet.isEmpty()) {
						RMReport preRmReport = null;
						String preSkill = (String) pSkills;
						String secSkill = (String) sSkills;
						if (rmReportsSet.contains(rmReport)) {
							preRmReport = (RMReport) rmReportMap.get(rmReport.hashCode());
							if (preRmReport != null) {
								/*if (preRmReport.getPrimarySkill() != null) {
									if (preSkill !=null && !(preRmReport.getPrimarySkill().contains(preSkill))) {
										preSkill += ","+ preRmReport.getPrimarySkill();
										rmReport.setPrimarySkill(preSkill);
									} else {
										rmReport.setPrimarySkill((String) preRmReport.getPrimarySkill());
									}

								}*/
								/*if (preRmReport.getSecondarySkill() != null) {
									
									if (secSkill !=null && !(preRmReport.getSecondarySkill().contains(secSkill))) {
										secSkill += ","+ preRmReport.getSecondarySkill();
										rmReport.setSecondarySkill(secSkill);
									} else {
										rmReport.setSecondarySkill((String) preRmReport.getSecondarySkill());
									}
								}*/
                                //removing previous & add new object with change value of Primary & Secondary field
								rmReportsSet.remove(preRmReport);
							}
						}
					}
					
				
					
					rmReport.setCustomerId((String) obj[++i]);
					rmReport.setLastUpdateBy((String) obj[++i]);
					rmReport.setLastupdateTimeStamp((Timestamp) obj[++i]);	
					Object val1 =  obj[i+7];
					if(val1 instanceof Date)
						rmReport.setAllocationEndDate((Date) val1);
					rmReport.setCompetency((String) obj[i+13]);
					rmReport.setResourceType((String) obj[i+14]);
					rmReport.setResumeYN((String) obj[i+15]);
					rmReport.setTefYN((String) obj[i+16]);
					
					rmReport.setProjectManager((String) obj[i+17]);
					rmReport.setTotalExp(String.valueOf(obj[i+18]));
					rmReport.setRelExp(String.valueOf(obj[i+19]));
					rmReport.setYashExp(Resource.getCalYearDiff(rmReport.getDateOfJoining(), rmReport.getReleaseDate()));
					rmReport.setAllocatedSince(String.valueOf(obj[i+20]));
					rmReport.setDeliveryManager(String.valueOf(obj[i+21]));
					
					//Anjana
					if(reportId.equalsIgnoreCase("Multiple")){
					rmReportMap.put(rmReport.hashCode(), rmReport);
					rmMap.put(rmReport.getYashEmpID(), rmReport);
					//Anjana
					}
					//Add unique Object in HashSet 
					if (reportId.equalsIgnoreCase("Primary")){
						rmReportsSet.add(rmReport);
					}//Add Duplicate Object in HashSet 
					else if(reportId.equalsIgnoreCase("Multiple")){
						if(!rmReportMap.isEmpty()){
							RMReport preRmReport =null;
							preRmReport = rmMap.get(rmReport.getYashEmpID());
							if(preRmReport!=null){
							if((preRmReport.getYashEmpID()).equalsIgnoreCase(rmReport.getYashEmpID())){
								rmReportsSet.add(rmReport);
								rmReportsSet.add(preRmReport);
								
							}
						}
					}
					}else{
						if((rmReport.getAllocationStartDate()==null && (rmReport.getPrimaryProject()==null)) ||(rmReport.getAllocationEndDate()!=null && currentDate.after(rmReport.getAllocationStartDate()) && currentDate.after(rmReport.getAllocationEndDate()))){
							rmReport.setPrimaryProject("No Project Allocated");
						}
						rmReportsSet.add(rmReport);
					}
					
					//unique Object object having almost same hascode value
					if(!reportId.equalsIgnoreCase("Multiple")){
						rmReportMap.put(rmReport.hashCode(), rmReport);
						rmMap.put(rmReport.getYashEmpID(), rmReport);
					}
				}
			}
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.debug(e.getMessage());
		}
		logger.debug("--------RMReportDaoImpl getRMReport method start-------");
		return rmReportsSet;
	}

	

}
