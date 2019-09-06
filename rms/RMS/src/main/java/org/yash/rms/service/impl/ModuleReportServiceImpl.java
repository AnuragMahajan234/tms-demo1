/**
 * 
 */
package org.yash.rms.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.yash.rms.dao.ModuleReportDao;
import org.yash.rms.report.dto.ModuleReport;
import org.yash.rms.report.dto.WeeklyData;
import org.yash.rms.service.ModuleReportService;
import org.yash.rms.util.DateUtil;

/**
 * @author bhakti.barve
 *
 */

@Service("moduleReportService")
public class ModuleReportServiceImpl implements ModuleReportService {

	@Autowired
	@Qualifier("moduleReportDao")
	ModuleReportDao moduleReportDao;
	
	private static final Logger logger = LoggerFactory.getLogger(ModuleReportServiceImpl.class);
	
	
	public Map<String, Map<String, List<ModuleReport>>> getModuleReport(List<Integer> projectId, Date startDate, Date endDt) {
		logger.info("--------ModuleReportServiceImpl getModuleReport method start-------");
		Map<String, Map<String, List<ModuleReport>>> moduleMap = new HashMap<String, Map<String, List<ModuleReport>>>();
		Map<String, Date> dates = DateUtil.calculateCompleteWeek(startDate, endDt);  // method to calculate complete weeks between selected dates 
		List<Date> weekEndDateList = new ArrayList<Date>();
		List<Date> weekStartDateList = new ArrayList<Date>();
		Set<String> projectNameSet = new HashSet<String>();
		Set<String> moduleNameSet = new HashSet<String>();
		List<ModuleReport> moduleReportList = new ArrayList<ModuleReport>();
		Set<ModuleReport> moduleReportSet = new HashSet<ModuleReport>();
		Set<String> weeks = new HashSet<String>();
		
		// getting list for complete weeks 
		moduleReportList = moduleReportDao.getModuleReport(projectId, dates);
		
		for (ModuleReport moduleReport : moduleReportList) {
			projectNameSet.add(moduleReport.getProjectName());
			moduleNameSet.add(moduleReport.getModuleName());
			if (!weekEndDateList.contains(moduleReport.getWeekEndDate())) {
				weekEndDateList.add(moduleReport.getWeekEndDate());
				weekStartDateList.add(moduleReport.getWeekStartDate());
			}
		}
		
		List<ModuleReport> copyOfModuleReportList = moduleReportList;
		//populaing weekly data 
		Iterator<ModuleReport> moduleIterator = moduleReportList.iterator();
		StringBuilder weekDate = null;
		List<Date> endDatelist = null;
		while (moduleIterator.hasNext()) {
			WeeklyData weeklyData = new WeeklyData();
			endDatelist = new ArrayList<Date>();
			ModuleReport moduleReport = moduleIterator.next();
			weeklyData.setBilledHours(moduleReport.getBilledHours());
			weeklyData.setNonProductiveHours(moduleReport.getNonProductiveHours());
			weeklyData.setPlannedHours(moduleReport.getPlannedHours());
			weeklyData.setProductiveHours(moduleReport.getProductiveHours());
			weeklyData.setWeekStartDate(moduleReport.getWeekStartDate());
			weeklyData.setWeekEndDate(moduleReport.getWeekEndDate());
			endDatelist.add(moduleReport.getWeekEndDate());
			moduleReport.getWeeklyData().add(weeklyData);
			weekDate = new StringBuilder();
			weekDate.append(new SimpleDateFormat("dd/MM/yyyy").format(moduleReport.getWeekStartDate())).append("-")
					.append(new SimpleDateFormat("dd/MM/yyyy").format(moduleReport.getWeekEndDate()));
			weeks.add(weekDate.toString());
			for (ModuleReport copyOfModuleReport : copyOfModuleReportList) {
				if (moduleReport.equals(copyOfModuleReport)) {
					if (moduleReport.getWeekEndDate() != copyOfModuleReport.getWeekEndDate()
							&& moduleReport.getWeekStartDate() != copyOfModuleReport.getWeekStartDate()) {
						weeklyData = new WeeklyData();
						weeklyData.setBilledHours(copyOfModuleReport.getBilledHours());
						weeklyData.setNonProductiveHours(copyOfModuleReport.getNonProductiveHours());
						weeklyData.setPlannedHours(copyOfModuleReport.getPlannedHours());
						weeklyData.setProductiveHours(copyOfModuleReport.getProductiveHours());
						weeklyData.setWeekStartDate(copyOfModuleReport.getWeekStartDate());
						weeklyData.setWeekEndDate(copyOfModuleReport.getWeekEndDate());
						endDatelist.add(copyOfModuleReport.getWeekEndDate());

						moduleReport.getWeeklyData().add(weeklyData);
						weekDate = new StringBuilder();
						weekDate.append(new SimpleDateFormat("dd/MM/yyyy").format(moduleReport.getWeekStartDate())).append("-")
								.append(new SimpleDateFormat("dd/MM/yyyy").format(moduleReport.getWeekEndDate()));
						weeks.add(weekDate.toString());

					}

				}

			}
			WeeklyData weeklyData1 = null;
			for (Date endDate : weekEndDateList) {
				if (!endDatelist.contains(endDate)) {
					weeklyData1 = new WeeklyData();
					weeklyData1.setBilledHours(0.0);
					weeklyData1.setNonProductiveHours(0.0);
					weeklyData1.setPlannedHours(0.0);
					weeklyData1.setProductiveHours(0.0);
					weeklyData1.setWeekStartDate(weekStartDateList.get(weekEndDateList.indexOf(endDate)));
					weeklyData1.setWeekEndDate(endDate);
					moduleReport.getWeeklyData().add(weeklyData1);
					// weeklyData1 = null;
				}
			}
			moduleReportSet.add(moduleReport);
		}

		HashMap<String, List<ModuleReport>> projectMap = new HashMap<String, List<ModuleReport>>();
		// creating map module wise
		for (String moduleName : moduleNameSet) {
			// creating map project wise 
			for (String projectName : projectNameSet) {
				
				List<ModuleReport> resourceList = new ArrayList<ModuleReport>();
				for (ModuleReport moduleReport : moduleReportSet) {
					if (moduleReport.getModuleName().equalsIgnoreCase(moduleName) && projectName.equalsIgnoreCase(moduleReport.getProjectName())) {
						resourceList.add(moduleReport);

					}
				}

				if (!resourceList.isEmpty()) {
					projectMap.put(projectName, resourceList);
				}

			}
			moduleMap.put(moduleName, projectMap);
			projectMap = new HashMap<String, List<ModuleReport>>();
		}

		System.out.println(moduleMap);

	 

	logger.info("--------ModuleReportServiceImpl getModuleReport method end-------");

	return moduleMap;
		
	}

	public List<ModuleReport> getCustomizedModuleReport(List<Integer> projectIds, Date startDate, Date endDate) {
		
		List<Object[]> moduleReportObjectList = moduleReportDao.getCustomizedModuleReport(projectIds, startDate, endDate);
		
		List<ModuleReport> moduleReportList = formatObjectListIntoModuleReport(moduleReportObjectList, startDate, endDate);
		
		return moduleReportList;
	}

	private List<ModuleReport> formatObjectListIntoModuleReport(List<Object[]> moduleReportObjectList, Date startDate, Date endDate) {

		List<ModuleReport> moduleReportList = new ArrayList<ModuleReport>();
		
		for (Object[] moduleReportObject : moduleReportObjectList) {
			
			ModuleReport moduleReport = new ModuleReport();
				moduleReport.setModuleName(String.valueOf(moduleReportObject[0]));
				moduleReport.setProjectName((String.valueOf(moduleReportObject[1])));
				moduleReport.setEmployeeName(String.valueOf(moduleReportObject[4]));
				moduleReport.setYashEmpId(String.valueOf(moduleReportObject[5]));
				
			if (moduleReportObject[6] != null) {
				moduleReport.setNonProductiveHours((Double.parseDouble((String.valueOf(moduleReportObject[6])))));
			} else moduleReport.setNonProductiveHours(0.0);
			
			if (moduleReportObject[7] != null) {
				moduleReport.setProductiveHours((Double.parseDouble((String.valueOf(moduleReportObject[7])))));
			} else moduleReport.setProductiveHours(0.0);
				
			if (moduleReportObject[8] != null) {
				moduleReport.setPlannedHours((Double.parseDouble((String.valueOf(moduleReportObject[8])))));
			} else moduleReport.setPlannedHours(0.0);

			if (moduleReportObject[9] != null) {
				moduleReport.setBilledHours((Double.parseDouble((String.valueOf(moduleReportObject[9])))));
			} moduleReport.setBilledHours(0.0);
			
			java.sql.Date weekStartDate = new java.sql.Date(startDate.getTime());
			java.sql.Date weekEndDate = new java.sql.Date(endDate.getTime());    
				
			if (((Date) moduleReportObject[2]).before(startDate)) {
				moduleReport.setWeekStartDate(weekStartDate);
			} else {
				moduleReport.setWeekStartDate((Date) moduleReportObject[2]);
			}
				
			if(((Date) moduleReportObject[3]).after(endDate)) {
				moduleReport.setWeekEndDate(weekEndDate);
			} else {
				moduleReport.setWeekEndDate((Date) moduleReportObject[3]);
			}
			
			moduleReportList.add(moduleReport);
		}
		
		return moduleReportList;
	}
	
}
