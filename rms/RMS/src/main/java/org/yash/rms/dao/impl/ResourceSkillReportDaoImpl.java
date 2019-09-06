package org.yash.rms.dao.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.yash.rms.dao.ResourceSkillReportDao;
import org.yash.rms.report.dto.ResourceSkillReport;

@Repository("ResourceSkillReportDao")
public class ResourceSkillReportDaoImpl implements ResourceSkillReportDao{

	@PersistenceContext(name = "mysql", type = PersistenceContextType.TRANSACTION)
	private EntityManager entityManager;
	
	
	public EntityManager getEntityManager() {
		return entityManager;
	}
	
	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	private static final Logger logger = LoggerFactory.getLogger(ResourceSkillReportDaoImpl.class);
	
	public Set<ResourceSkillReport> getResourceSkillReport(List<Integer> orgIdList, List<Integer> allocationTypes) {
		logger.debug("--------ResourceSkillReportDaoImpl::getResourceSkillReport [START] -------");

		Session session = (Session) getEntityManager().getDelegate();
		Set<ResourceSkillReport> resourceSkillReportSet = new HashSet<ResourceSkillReport>();
	
		String coulmnName =	"yash_emp_id, Employee_Name, Emp_Email_id, Designation,Grade, Date_Of_Joining, Release_date, Base_location, " +
							"Current_location, Parent_Bu, Currnet_Bu, Ownership, Current_RM1, Current_RM2, Primary_Project, Current_Project_Flag, Customer_Name, " +
							"Project_Bu, Allocation_Start_date, Allocation_Type, resource_type, Transfer_date, Visa,  competency_skill," +
							"GROUP_CONCAT(DISTINCT primary_skill, '[', (SELECT   CASE WHEN (primaryExperience IS NULL ) THEN 'NA' WHEN (primaryExperience IS NOT NULL ) "+
							" THEN primaryExperience END FROM skill_resource_primary WHERE resource_id = RES_EMP_ID AND skill_id = Primary_skill_id LIMIT 1 ),'-' , " +
							" (SELECT   CASE WHEN (rating IS NULL ) THEN 'NA' WHEN (rating IS NOT NULL )  THEN rating END  FROM skill_resource_primary WHERE resource_id = RES_EMP_ID AND skill_id = Primary_skill_id LIMIT 1  ),']' ) " +
							" AS 'Primary_skill',  GROUP_CONCAT(DISTINCT secondary_skill, '[', (SELECT CASE WHEN (secondaryExperience IS NULL ) THEN 'NA' "+
							" WHEN (secondaryExperience IS NOT NULL ) THEN secondaryExperience END  FROM skill_resource_secondary WHERE resource_id = RES_EMP_ID AND " +
							" skill_id = Secondary_skill_id LIMIT 1 ),'-', (SELECT   CASE WHEN (rating IS NULL ) THEN 'NA' WHEN (rating IS NOT NULL ) THEN rating END  " +
							" FROM skill_resource_secondary WHERE resource_id = RES_EMP_ID AND skill_id = Secondary_skill_id LIMIT 1  ),']' )  AS 'Secondary_Skill', " +
							" Customer_Id, lastupdated_id, last_timestamp, resource_allocation_end_date,  ResumeYN, TefYN, Project_Mgr ";
		Query query=null;
		
		query = session.createSQLQuery("SELECT " + coulmnName + " FROM vw_resource_skill_report WHERE  (Bu_Id IN (:orgIdList) OR Current_Bu_Id IN (:orgIdList) OR Project_Bu_ID IN (:orgIdList))  " +
			" AND Allocation_Type_Id IN (:allocationTypeIdList)   AND   Current_Project_Flag IN (1) AND  (Release_Date IS NULL OR Release_Date > CURDATE()) " +
			" GROUP BY Yash_Emp_Id" );
			
		
		query.setParameterList("orgIdList", orgIdList);
		query.setParameterList("allocationTypeIdList", allocationTypes);
		
		List<Object[]> objectList = query.list();
		if (!objectList.isEmpty()) {
			for (Object[] obj : objectList) {
				ResourceSkillReport resourceSkillReport = new ResourceSkillReport();
				int i = 0;
				resourceSkillReport.setYashEmpID((String) obj[i]);
				resourceSkillReport.setEmployeeName((String) obj[++i]);
				resourceSkillReport.setEmailId((String) obj[++i]);
				resourceSkillReport.setDesignation((String) obj[++i]);
				resourceSkillReport.setGrade((String) obj[++i]);
				resourceSkillReport.setDateOfJoining((Date) obj[++i]);
				resourceSkillReport.setReleaseDate((Date) obj[++i]);
				resourceSkillReport.setBaseLocation((String) obj[++i]);
				resourceSkillReport.setCurrentLocation((String) obj[++i]);
				resourceSkillReport.setParentBu((String) obj[++i]);
				resourceSkillReport.setCurrentBu((String) obj[++i]);
				resourceSkillReport.setOwnership((String) obj[++i]);
				resourceSkillReport.setCurrentRM1((String) obj[++i]);
				resourceSkillReport.setCurrentRM2((String) obj[++i]);
				resourceSkillReport.setPrimaryProject((String) obj[++i]);
				Object isCurrProjectFlag = (Object) obj[++i];
				if (isCurrProjectFlag instanceof Boolean) {
					Boolean currProjectFlag = (Boolean) isCurrProjectFlag;
					if (currProjectFlag != null) {
						if (currProjectFlag == true) {
							resourceSkillReport.setCurrentProjectIndicator("true");
						} else if (currProjectFlag == false) {
							resourceSkillReport.setCurrentProjectIndicator("false");
						}
					}
				} else if (isCurrProjectFlag instanceof Byte) {
					Byte currProjectFlag = (Byte) isCurrProjectFlag;
					if (currProjectFlag != null) {
						if (currProjectFlag == 1) {
							resourceSkillReport.setCurrentProjectIndicator("true");
						} else if (currProjectFlag == 0) {
							resourceSkillReport.setCurrentProjectIndicator("false");
						}
					}
				} else {
					resourceSkillReport.setCurrentProjectIndicator(null);
				}
				
				resourceSkillReport.setCustomerName((String) obj[++i]);
				resourceSkillReport.setProjectBu((String) obj[++i]);
				resourceSkillReport.setAllocationStartDate((Date) obj[++i]);
				resourceSkillReport.setAllocationType((String) obj[++i]);	
				resourceSkillReport.setResourceType((String) obj[++i]);
				resourceSkillReport.setTransferDate((Date) obj[++i]);
				resourceSkillReport.setVisa((String) obj[++i]);
				resourceSkillReport.setCompetency((String) obj[++i]);
				resourceSkillReport.setPrimarySkill((String) obj[++i]);
				resourceSkillReport.setSecondarySkill((String) obj[++i]);
				
				resourceSkillReport.setCustomerId((String) obj[++i]);
				resourceSkillReport.setLastUpdateBy((String) obj[++i]);
				resourceSkillReport.setLastupdateTimeStamp((Timestamp) obj[++i]);					
				resourceSkillReport.setAllocationEndDate((Date) obj[++i]);
			
				resourceSkillReport.setResumeYN((String) obj[++i]);
				resourceSkillReport.setTefYN((String) obj[++i]);
				
				resourceSkillReport.setProjectManager((String) obj[++i]);
				resourceSkillReportSet.add(resourceSkillReport);
			}
		}
		
		logger.debug("--------ResourceSkillReportDaoImpl::getResourceSkillReport [END] -------");
		
		return resourceSkillReportSet;
	}

}
