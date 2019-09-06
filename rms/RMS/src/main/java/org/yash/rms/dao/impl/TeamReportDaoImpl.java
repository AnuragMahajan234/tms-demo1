package org.yash.rms.dao.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
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
import org.yash.rms.dao.TeamReportDAO;
import org.yash.rms.namedquery.RmsNamedQuery;
import org.yash.rms.report.dto.TeamReport;
import org.yash.rms.report.dto.WeeklyData;
import org.yash.rms.util.StartAndEndDateOfWeekUtility;

@Repository("teamReportDao")
public class TeamReportDaoImpl implements TeamReportDAO {

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
	public Map<String, Map<String, List<TeamReport>>> getTeamReport(
			List<Integer> teamId, Date weekStartDate, Date weekEndDate) {

		logger.info("--------TeamReportDaoImpl getTeamReport method start-------");

		List<TeamReport> teamReportList = new ArrayList<TeamReport>();
		Session session = (Session) getEntityManager().getDelegate();
		Map<String, Map<String, List<TeamReport>>> teamMap = new HashMap<String, Map<String, List<TeamReport>>>();
		try {

			Query teamReportQuery = session
					.createSQLQuery(RmsNamedQuery.getTeamReportQuery())
					.setParameterList("teamId", teamId)
					.setParameter("weekStartDate", weekStartDate)
					.setParameter("weekEndDate", weekEndDate);

			List<Object[]> reportList = new ArrayList<Object[]>();
			List<Date> weekEndDateList = new ArrayList<Date>();
			reportList = teamReportQuery.list();
			Set<String> teamNameSet = new HashSet<String>();
			Set<String> projectNameSet = new HashSet<String>();
			Set<TeamReport> teamReportSet = new HashSet<TeamReport>();
			Set<String> weeks = new HashSet<String>();
			TeamReport teamReportObj = null;
			
			Query resourceDataByTeamQuery = session
					.createSQLQuery(RmsNamedQuery.getResourceDataByTeamQuery())
					.setParameterList("teamId", teamId);
			
			List<TeamReport> resourceListByTeam = new ArrayList<TeamReport>();
			List<Object[]> resourceDataByTeam = new ArrayList<Object[]>();
			resourceDataByTeam = resourceDataByTeamQuery.list();
			
			for(Object[] resourceData :  resourceDataByTeam){
				TeamReport teamReport= new TeamReport();
			
				teamReport.setYashEmpId(String.valueOf(resourceData[0]));
				teamReport.setTeamId((Integer.parseInt(String.valueOf(resourceData[1]))));
				teamReport.setTeamName(String.valueOf(resourceData[2]));
				teamReport.setProjectId((Integer.parseInt(String.valueOf(resourceData[3]))));
				teamReport.setProjectName(String.valueOf(resourceData[4]));
				teamReport.setEmployeeName(String.valueOf(resourceData[5]));
				resourceListByTeam.add(teamReport);
			}

			for (Object[] teamReport : reportList) {
				teamReportObj = new TeamReport();
				teamReportObj.setTeamName(String.valueOf(teamReport[0]));
				teamReportObj.setProjectId((Integer.parseInt(String
						.valueOf(teamReport[1]))));
				teamReportObj.setTeamId((Integer.parseInt(String
						.valueOf(teamReport[2]))));
				teamReportObj.setProjectName((String.valueOf(teamReport[3])));
				teamReportObj.setWeekStartDate((Date) teamReport[4]);
				teamReportObj.setWeekEndDate((Date) teamReport[5]);
				if (!weekEndDateList.contains(teamReportObj.getWeekEndDate())) {
					weekEndDateList.add(teamReportObj.getWeekEndDate());
				}

				teamReportObj.setEmployeeName(String.valueOf(teamReport[6]));
				teamReportObj.setYashEmpId(String.valueOf(teamReport[7]));
				teamReportObj.setNonProductiveHours((Double.parseDouble((String
						.valueOf(teamReport[8])))));
				teamReportObj.setProductiveHours((Double.parseDouble((String
						.valueOf(teamReport[9])))));
				if (teamReport[10] != null) {
					teamReportObj.setPlannedHours((Double.parseDouble((String
							.valueOf(teamReport[10])))));
				}
				if (teamReport[11] != null) {
					teamReportObj.setBilledHours((Double.parseDouble((String
							.valueOf(teamReport[11])))));
				}

				teamNameSet.add(teamReportObj.getTeamName());
				projectNameSet.add(teamReportObj.getProjectName());
				teamReportList.add(teamReportObj);

			}

			List<TeamReport> copyOfTeamReportList = teamReportList;

			Iterator<TeamReport> teamIterator = teamReportList.iterator();
			StringBuilder weekDate = null;
			List<Date> endDatelist = null;
			while (teamIterator.hasNext()) {
				WeeklyData weeklyData = new WeeklyData();
				endDatelist = new ArrayList<Date>();
				TeamReport teamReport = teamIterator.next();
				weeklyData.setBilledHours(teamReport.getBilledHours());
				weeklyData.setNonProductiveHours(teamReport
						.getNonProductiveHours());
				weeklyData.setPlannedHours(teamReport.getPlannedHours());
				weeklyData.setProductiveHours(teamReport.getProductiveHours());
				weeklyData.setWeekStartDate(teamReport.getWeekStartDate());
				weeklyData.setWeekEndDate(teamReport.getWeekEndDate());
				endDatelist.add(teamReport.getWeekEndDate());
				teamReport.getWeeklyData().add(weeklyData);
				weekDate = new StringBuilder();
				weekDate.append(
						new SimpleDateFormat("dd/MM/yyyy").format(teamReport
								.getWeekStartDate()))
						.append("-")
						.append(new SimpleDateFormat("dd/MM/yyyy")
								.format(teamReport.getWeekEndDate()));
				weeks.add(weekDate.toString());
				for (TeamReport copyOfteamReport : copyOfTeamReportList) {
					if (teamReport.equals(copyOfteamReport)) {
						if (teamReport.getWeekEndDate() != copyOfteamReport
								.getWeekEndDate()
								&& teamReport.getWeekStartDate() != copyOfteamReport
										.getWeekStartDate()) {
							weeklyData = new WeeklyData();
							weeklyData.setBilledHours(copyOfteamReport
									.getBilledHours());
							weeklyData.setNonProductiveHours(copyOfteamReport
									.getNonProductiveHours());
							weeklyData.setPlannedHours(copyOfteamReport
									.getPlannedHours());
							weeklyData.setProductiveHours(copyOfteamReport
									.getProductiveHours());
							weeklyData.setWeekStartDate(copyOfteamReport
									.getWeekStartDate());
							weeklyData.setWeekEndDate(copyOfteamReport
									.getWeekEndDate());
							endDatelist.add(copyOfteamReport.getWeekEndDate());
							teamReport.getWeeklyData().add(weeklyData);
							weekDate = new StringBuilder();
							weekDate.append(
									new SimpleDateFormat("dd/MM/yyyy")
											.format(teamReport
													.getWeekStartDate()))
									.append("-")
									.append(new SimpleDateFormat("dd/MM/yyyy")
											.format(teamReport.getWeekEndDate()));
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
						// weeklyData1.setWeekStartDate(null);
						weeklyData1.setWeekEndDate(endDate);
						teamReport.getWeeklyData().add(weeklyData1);
						// weeklyData1 = null;
					}
				}
				teamReportSet.add(teamReport);
			}

			Map<String, List<TeamReport>> projectMap = null;
			copyOfTeamReportList = null;
			if (null != projectNameSet) {
				projectMap = new HashMap<String, List<TeamReport>>();
				for (String projectName : projectNameSet) {

					copyOfTeamReportList = new ArrayList<TeamReport>();
					for (TeamReport teamObject : teamReportSet) {

						if (projectName.equalsIgnoreCase(teamObject
								.getProjectName())) {
							copyOfTeamReportList.add(teamObject);
						}
					}
					projectMap.put(projectName, copyOfTeamReportList);
				}
			}

			for (String teamName : teamNameSet) {
				Set<Entry<String, List<TeamReport>>> reportSet = projectMap
						.entrySet();
				Iterator<Entry<String, List<TeamReport>>> iterator = reportSet
						.iterator();
				Map<String, List<TeamReport>> copyOfprojectMap = new HashMap<String, List<TeamReport>>();
				while (iterator.hasNext()) {
					Entry<String, List<TeamReport>> entrySet = iterator.next();
					TeamReport report = entrySet.getValue().get(0);
					if (report.getTeamName().equalsIgnoreCase(teamName)) {
						copyOfprojectMap.put(entrySet.getKey(),
								entrySet.getValue());

					}
				}
				teamMap.put(teamName, copyOfprojectMap);
				for(TeamReport teamData : resourceListByTeam){
					if(teamData.getTeamName().equalsIgnoreCase(teamName)){
						List<TeamReport> teamReportListByProject = copyOfprojectMap.get(teamData.getProjectName());
						if(teamReportListByProject != null && !teamReportListByProject.isEmpty() ){
							boolean isResourcePresent = false;
							List<Date> weekEndList = new ArrayList<Date>();
							for(TeamReport teamReport : teamReportListByProject ){
								List<WeeklyData> weekDataList = teamReport.getWeeklyData();
								for(WeeklyData weekData : weekDataList ){
									if (!weekEndList.contains(weekData.getWeekEndDate())) {
										weekEndList.add(weekData.getWeekEndDate());
									}
								}
								
								if(teamReport.getYashEmpId().equals(teamData.getYashEmpId())){
									isResourcePresent=true;
									break;
								}
							}
							if(!isResourcePresent){
								
								WeeklyData weeklyData1 = null;
								for (Date endDate : weekEndList) {
								//	if (!endDatelist.contains(endDate)) {
										weeklyData1 = new WeeklyData();
										weeklyData1.setBilledHours(0.0);
										weeklyData1.setNonProductiveHours(0.0);
										weeklyData1.setPlannedHours(0.0);
										weeklyData1.setProductiveHours(0.0);
										// weeklyData1.setWeekStartDate(null);
										weeklyData1.setWeekEndDate(endDate);
										teamData.getWeeklyData().add(weeklyData1);
										// weeklyData1 = null;
							//		}
								}
								teamReportListByProject.add(teamData);
							}
						}
				
					}
				}
				
			}

			System.out.println(teamMap);

		} catch (HibernateException e) {
			e.printStackTrace();
			logger.error("Exception occured in getTeamReport method at DAO layer:-"
					+ e);
			throw e;
		}

		logger.info("--------TeamReportDaoImpl getTeamReport method end-------");

		return teamMap;
	}

	@SuppressWarnings("unchecked")
	public Map<String, Map<String, List<TeamReport>>> getNewTeamReport(
			List<Integer> teamId, Date weekStartDate, Date weekEndDate) {

		logger.info("--------TeamReportDaoImpl getTeamReport method start-------");

		List<TeamReport> teamReportList = new ArrayList<TeamReport>();
		Session session = (Session) getEntityManager().getDelegate();
		Map<String, Map<String, List<TeamReport>>> teamMap = new HashMap<String, Map<String, List<TeamReport>>>();
		try {

			Query teamReportQuery = session
					.createSQLQuery(RmsNamedQuery.getNewTeamReportQuery())
					.setParameterList("teamId", teamId)
					.setParameter("weekStartDate", weekStartDate)
					.setParameter("weekEndDate", weekEndDate);

			List<Object[]> reportList = new ArrayList<Object[]>();
			List<Date> weekEndDateList = new ArrayList<Date>();
			Map<Date, Date> weekStartAndEndDateMap = StartAndEndDateOfWeekUtility.getStartAndEndDateMap(weekStartDate, weekEndDate);
			weekEndDateList = new ArrayList<Date>(weekStartAndEndDateMap.keySet());
			reportList = teamReportQuery.list();
			Set<String> teamNameSet = new HashSet<String>();
			Set<String> projectNameSet = new HashSet<String>();
			Set<TeamReport> teamReportSet = new HashSet<TeamReport>();
			Set<String> weeks = new HashSet<String>();
			TeamReport teamReportObj = null;
			StringBuilder weekDate = null;
			
			for (Object[] teamReport : reportList) {
				teamReportObj = new TeamReport();
				teamReportObj.setTeamName(String.valueOf(teamReport[0]));
				teamReportObj.setProjectId((Integer.parseInt(String
						.valueOf(teamReport[1]))));
				teamReportObj.setTeamId((Integer.parseInt(String
						.valueOf(teamReport[2]))));
				teamReportObj.setProjectName((String.valueOf(teamReport[3])));
				if(teamReport[4] != null)
					teamReportObj.setWeekStartDate((Date) teamReport[4]);
				if(teamReport[5] != null)
					teamReportObj.setWeekEndDate((Date) teamReport[5]);
				/*if (!weekEndDateList.contains(teamReportObj.getWeekEndDate())) {
					weekEndDateList.add(teamReportObj.getWeekEndDate());
				}*/

				teamReportObj.setEmployeeName(String.valueOf(teamReport[6]));
				teamReportObj.setYashEmpId(String.valueOf(teamReport[7]));
				if (teamReport[8] != null) {
					teamReportObj.setNonProductiveHours((Double.parseDouble((String
						.valueOf(teamReport[8])))));
				}
				if (teamReport[9] != null) {
					teamReportObj.setProductiveHours((Double.parseDouble((String
						.valueOf(teamReport[9])))));
				}
				if (teamReport[10] != null) {
					teamReportObj.setPlannedHours((Double.parseDouble((String
							.valueOf(teamReport[10])))));
				}
				if (teamReport[11] != null) {
					teamReportObj.setBilledHours((Double.parseDouble((String
							.valueOf(teamReport[11])))));
				}

				teamNameSet.add(teamReportObj.getTeamName());
				projectNameSet.add(teamReportObj.getProjectName());
				teamReportList.add(teamReportObj);

			}
		
			Map<String, List<TeamReport>> teamReportMap= new HashMap<String, List<TeamReport>>();
			for(TeamReport teamReport : teamReportList){
				String key = teamReport.getTeamId()+""+teamReport.getProjectId()+""+teamReport.getYashEmpId()+"";
				if(!teamReportMap.containsKey(key)){
					teamReportMap.put(key, new ArrayList<TeamReport>());
					teamReportMap.get(key).add(teamReport);
				}
				else{
					teamReportMap.get(key).add(teamReport);
				}
			}
			
			//weekEndDateList
			
			for ( String key : teamReportMap.keySet()){
				List<Date> weekEndDatesTobeRemoved = new ArrayList<Date>();
				List<TeamReport> teamReports = teamReportMap.get(key);
				TeamReport newTeamReport = new TeamReport();
				List<WeeklyData> WeeklyDataList = new ArrayList<WeeklyData>();
				for(TeamReport teamReport : teamReports){
					newTeamReport = teamReport;
					
					if(teamReport.getWeekEndDate() != null && teamReport.getWeekStartDate() != null ){
								
						WeeklyData weeklyData = new WeeklyData();
						weeklyData.setBilledHours(teamReport
								.getBilledHours());
						weeklyData.setNonProductiveHours(teamReport
								.getNonProductiveHours());
						weeklyData.setPlannedHours(teamReport
								.getPlannedHours());
						weeklyData.setProductiveHours(teamReport
								.getProductiveHours());
						weeklyData.setWeekStartDate(teamReport
								.getWeekStartDate());
						weeklyData.setWeekEndDate(teamReport
								.getWeekEndDate());
						WeeklyDataList.add(weeklyData);
						/*weekDate = new StringBuilder();
						weekDate.append(
								new SimpleDateFormat("dd/MM/yyyy").format(teamReport
										.getWeekStartDate()))
								.append("-")
								.append(new SimpleDateFormat("dd/MM/yyyy")
										.format(teamReport.getWeekEndDate()));
						weeks.add(weekDate.toString());*/
						
						weekEndDatesTobeRemoved.add(teamReport.getWeekEndDate());						
					}
				}
				
				WeeklyData weeklyData1 = null;
				for (Date endDate : weekEndDateList) {
					if (!weekEndDatesTobeRemoved.contains(endDate)) {
						weeklyData1 = new WeeklyData();
						weeklyData1.setBilledHours(0.0);
						weeklyData1.setNonProductiveHours(0.0);
						weeklyData1.setPlannedHours(0.0);
						weeklyData1.setProductiveHours(0.0);
						weeklyData1.setWeekStartDate(weekStartAndEndDateMap.get(endDate));
						weeklyData1.setWeekEndDate(endDate);
						/*weekDate = new StringBuilder();
						weekDate.append(
								new SimpleDateFormat("dd/MM/yyyy").format(weeklyData1
										.getWeekStartDate()))
								.append("-")
								.append(new SimpleDateFormat("dd/MM/yyyy")
										.format(weeklyData1.getWeekEndDate()));
						weeks.add(weekDate.toString());*/
						
						WeeklyDataList.add(weeklyData1);
						// weeklyData1 = null;
					}
				}
				
				newTeamReport.setWeeklyData(WeeklyDataList);
				teamReportSet.add(newTeamReport);
				
				
			
			}
			for(TeamReport tr : teamReportSet){
				if(tr.getWeeklyData().size() > 8){
					System.out.println(" Yahs Emp Id : " + tr.getYashEmpId() + " Project Id " + tr.getProjectId());
				}
				
			}
			
			Map<String, List<TeamReport>> projectMap = null;

			List<TeamReport> copyOfTeamReportList = null;
	
			if (null != projectNameSet) {
				projectMap = new HashMap<String, List<TeamReport>>();
				for (String projectName : projectNameSet) {

					copyOfTeamReportList = new ArrayList<TeamReport>();
					for (TeamReport teamObject : teamReportSet) {

						if (projectName.equalsIgnoreCase(teamObject
								.getProjectName())) {
							copyOfTeamReportList.add(teamObject);
						}
					}
					projectMap.put(projectName, copyOfTeamReportList);
				}
			}

			for (String teamName : teamNameSet) {
				Set<Entry<String, List<TeamReport>>> reportSet = projectMap
						.entrySet();
				Iterator<Entry<String, List<TeamReport>>> iterator = reportSet
						.iterator();
				Map<String, List<TeamReport>> copyOfprojectMap = new HashMap<String, List<TeamReport>>();
				while (iterator.hasNext()) {
					Entry<String, List<TeamReport>> entrySet = iterator.next();
					TeamReport report = entrySet.getValue().get(0);
					if (report.getTeamName().equalsIgnoreCase(teamName)) {
						copyOfprojectMap.put(entrySet.getKey(),
								entrySet.getValue());

					}
				}
				teamMap.put(teamName, copyOfprojectMap);
			}

			System.out.println(teamMap);

		} catch (HibernateException e) {
			e.printStackTrace();
			logger.error("Exception occured in getTeamReport method at DAO layer:-"
					+ e);
			throw e;
		}

		logger.info("--------TeamReportDaoImpl getTeamReport method end-------");

		return teamMap;
	}

}
