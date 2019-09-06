/**
 * 
 */
package org.yash.rms.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.yash.rms.dao.ModuleReportDao;
import org.yash.rms.namedquery.RmsNamedQuery;
import org.yash.rms.report.dto.ModuleReport;

/**
 * @author bhakti.barve
 *
 */

@Repository("moduleReportDao")
public class ModuleReportDaoImpl implements ModuleReportDao{
	
	private static final Logger logger = LoggerFactory
			.getLogger(ReportDaoImpl.class);



@PersistenceContext(name = "mysql", type = PersistenceContextType.TRANSACTION)
	private EntityManager entityManager;
	
	
	public EntityManager getEntityManager() {
		return entityManager;
	}
	
	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	@SuppressWarnings("unchecked")
	public List<ModuleReport> getModuleReport(List<Integer> projectId, Map<String, Date> dates) {
		logger.info("--------ModuleReportDaoImpl getModuleReport method start-------");

		List<ModuleReport> moduleReportList = new ArrayList<ModuleReport>();
		Session session = (Session) getEntityManager().getDelegate();
		
		Date weekStartDate = dates.get("startDate");
		Date weekEndDate = dates.get("endDate");
		
		
		try {

			Query moduleReportQuery = session.createSQLQuery(RmsNamedQuery.getModuleReportQuery()).setParameterList("projectId", projectId)
					.setParameter("weekStartDate", weekStartDate).setParameter("weekEndDate", weekEndDate);

			List<Object[]> reportList = new ArrayList<Object[]>();
			reportList = moduleReportQuery.list();
			
			
			ModuleReport moduleReportObj = null;

			for (Object[] moduleReport : reportList) {

				moduleReportObj = new ModuleReport();
				moduleReportObj.setModuleName(String.valueOf(moduleReport[0]));
				moduleReportObj.setProjectId(Integer.parseInt(String.valueOf(moduleReport[10])));

				moduleReportObj.setProjectName((String.valueOf(moduleReport[1])));
				
				java.sql.Date startDate = new java.sql.Date(weekStartDate.getTime());
				java.sql.Date endDate = new java.sql.Date(weekEndDate.getTime());    
				
				if(((Date) moduleReport[2]).before(weekStartDate)){
					moduleReportObj.setWeekStartDate(startDate);
				}else{
					moduleReportObj.setWeekStartDate((Date) moduleReport[2]);
				}
				
				
				if(((Date) moduleReport[3]).after(weekEndDate)){
					moduleReportObj.setWeekEndDate(endDate);
				}else{
					moduleReportObj.setWeekEndDate((Date) moduleReport[3]);
				}
				
				moduleReportObj.setEmployeeName(String.valueOf(moduleReport[4]));
				moduleReportObj.setYashEmpId(String.valueOf(moduleReport[5]));
				if (moduleReport[6] != null) {
					moduleReportObj.setPlannedHours((Double.parseDouble((String.valueOf(moduleReport[6])))));
				}

				moduleReportObj.setNonProductiveHours((Double.parseDouble((String.valueOf(moduleReport[7])))));
				moduleReportObj.setProductiveHours((Double.parseDouble((String.valueOf(moduleReport[8])))));

				if (moduleReport[9] != null) {
					moduleReportObj.setBilledHours((Double.parseDouble((String.valueOf(moduleReport[9])))));
				}

				/*projectNameSet.add(moduleReportObj.getProjectName());
				moduleNameSet.add(moduleReportObj.getModuleName());*/
				moduleReportList.add(moduleReportObj);

			}
			
		} catch (HibernateException e) {
			e.printStackTrace();
			logger.error("Exception occured in getModuleReport method at DAO layer:-" + e);
			throw e;
		}
		logger.info("--------ModuleReportDaoImpl getModuleReport method end-------");

		return moduleReportList;
	}

	@SuppressWarnings("unchecked")
	public List<Object[]> getCustomizedModuleReport(List<Integer> projectId, Date startDate, Date endDate) {
		
		logger.info("--------ModuleReportDaoImpl getCustomizedModuleReport method start-------");
		
		Session session = (Session) getEntityManager().getDelegate();
		
		java.sql.Date sqlStartDate = new java.sql.Date(startDate.getTime());
		java.sql.Date sqlEndDate = new java.sql.Date(endDate.getTime());
		
		Query customizedModuleQuery = session.createSQLQuery(RmsNamedQuery.getCustomizedModuleQuery()).setParameterList("projectId", projectId).setParameter("weekStartDate", sqlStartDate).setParameter("weekEndDate", sqlEndDate);
		
		List<Object[]> moduleReportObjectList = new ArrayList<Object[]>();
		
		System.out.println("customizedModuleQuery: " + customizedModuleQuery);
		
		moduleReportObjectList = customizedModuleQuery.list();
		
		logger.info("--------ModuleReportDaoImpl getCustomizedModuleReport method End-------");
		
		return moduleReportObjectList;
	}

}
