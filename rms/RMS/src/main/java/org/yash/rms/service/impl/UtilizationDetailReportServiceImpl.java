package org.yash.rms.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.yash.rms.dao.UtilizationDetailReportDao;
import org.yash.rms.report.dto.DayData;
import org.yash.rms.report.dto.ModuleReport;
import org.yash.rms.report.dto.UtilizationDetailReport;
import org.yash.rms.report.dto.WeeklyData;
import org.yash.rms.service.UtilizationDetailReportService;

@Service("utilizationDetailReportService")
public class UtilizationDetailReportServiceImpl implements
		UtilizationDetailReportService {

	@Autowired
	@Qualifier("utilizationDetailReportDao")
	UtilizationDetailReportDao utilizationDetailReportDao;

	private static final Logger logger = LoggerFactory
			.getLogger(UtilizationDetailReportServiceImpl.class);

	public Map<String,  TreeMap<Date, WeeklyData>> getUtilizationDetailReport(
			List<Integer> orgIdList, List<Integer> locIdList,
			List<Integer> projIdList, Date startDate, Date endDate,
			 List<Integer> employeeIdList) {
		logger.info("--------utilizationDetailReportServiceImpl getUtilizationReport method start-------");
		
		boolean flag=false;
		List<UtilizationDetailReport> utilizationDetailReportList = new ArrayList<UtilizationDetailReport>();
		
		TreeMap<Date, WeeklyData> listOfWeeklyDataMap = new TreeMap<Date,WeeklyData>();
		
		List<DayData> dayDataList = new ArrayList<DayData>();

		Map<String, TreeMap<Date, WeeklyData>> utilizationDetailReport = new TreeMap<String, TreeMap<Date, WeeklyData>>();

		Map<String, WeeklyData> utilizationReport = new TreeMap<String, WeeklyData>();
		
		utilizationDetailReportList = utilizationDetailReportDao
				.getUtilizationDetailReport(orgIdList, locIdList, projIdList,
						startDate, endDate, employeeIdList);
		int i = 0;
		
		for (UtilizationDetailReport utilization : utilizationDetailReportList) {

			String nameId = utilization.getEmployeeNameId();

			if (!utilizationReport.containsKey(utilization.getEmployeeNameId())) {
				if (listOfWeeklyDataMap.size() > 0) {
					utilizationDetailReport.put(utilizationDetailReportList
							.get(i - 1).getEmployeeNameId(),
							listOfWeeklyDataMap);
				}
				listOfWeeklyDataMap = new TreeMap<Date, WeeklyData>();
				
				utilizationReport.put(nameId, utilization.getWeeklyData());
				WeeklyData weeklyData =(WeeklyData)utilization.getWeeklyData();
				dayDataList = getListOfDayData(weeklyData);
				weeklyData.setDayData(dayDataList);
				listOfWeeklyDataMap.put(utilization.getWeeklyData()
						.getWeekEndDate(), weeklyData);

			} else {
				if (listOfWeeklyDataMap.containsKey(utilization.getWeeklyData()
						.getWeekEndDate())) {
					WeeklyData weekdata1 = listOfWeeklyDataMap.get(utilization
							.getWeeklyData().getWeekEndDate());
					List<DayData> dayDatalist1= weekdata1.getDayData();
					List<DayData> dayDatalist2= new ArrayList<DayData>();
					WeeklyData weekdata2 = utilization.getWeeklyData();
					weekdata2.setProductiveHours(weekdata1.getProductiveHours()+weekdata2.getProductiveHours());
					if(weekdata2.getCustom_activity_name()!=null)
						weekdata2.setNonProductiveHours(0.0);
					else
					weekdata2.setNonProductiveHours(weekdata1.getNonProductiveHours()+weekdata2.getNonProductiveHours());
					
					if(!weekdata1.getProjectName().equals(weekdata2.getProjectName())){
						weekdata2.setBilledHours(weekdata1.getBilledHours()+weekdata2.getBilledHours());
						weekdata2.setPlannedHours(weekdata1.getPlannedHours()+weekdata2.getPlannedHours());
						flag=true;
						
					}
					dayDatalist2 = getListOfDayData(weekdata2);
					
					if(flag){
						flag=false;
						dayDatalist2.get(0).setProjectName(weekdata2.getProjectName());
						dayDatalist2.get(0).setProjectCode(weekdata2.getProjectCode());
						dayDatalist2.get(0).setStatus(weekdata2.getStatus());
						weekdata2.setProjectName(weekdata1.getProjectName());
						weekdata2.setProjectCode(weekdata1.getProjectCode());	
					}
					
					dayDatalist1.addAll(dayDatalist2);
					listOfWeeklyDataMap.remove(utilization.getWeeklyData()
							.getWeekEndDate());
					weekdata2.setDayData(dayDatalist1);
					listOfWeeklyDataMap.put(weekdata2.getWeekEndDate(),
							weekdata2);

				} else {

					WeeklyData wd =(WeeklyData)utilization.getWeeklyData();
					dayDataList = getListOfDayData(wd);
					
					wd.setDayData(dayDataList);
					listOfWeeklyDataMap.put(utilization.getWeeklyData()
							.getWeekEndDate(), utilization.getWeeklyData());
					
				}

			}
			i++;

		}
		if(i>0){
		utilizationDetailReport.put(utilizationDetailReportList.get(i - 1)
				.getEmployeeNameId(), listOfWeeklyDataMap);
		}
		
		
		
		logger.info("--------utilizationDetailReportServiceImpl getUtilizationReport method end-------");

		return utilizationDetailReport;

	}
	
	private List<DayData> getListOfDayData(WeeklyData wd){
		List<DayData> dayDataList= new  ArrayList<DayData>();
				
		if(wd.getD1_hours()!=null){
			DayData day= new DayData();	
			Date date = wd.getWeekStartDate();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			c.add(Calendar.DATE, 0); // number of days to add
			String dayDate = sdf.format(c.getTime());
			day.setDate(dayDate);
			day.setActualHours(wd.getD1_hours());
			day.setActivity(wd.getActivity_name());
			day.setModule(wd.getModule());
			day.setComment(wd.getD1_comment());
			dayDataList.add(day);
			}
		if(wd.getD2_hours()!=null){
			DayData day= new DayData();
			Date date = wd.getWeekStartDate();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			c.add(Calendar.DATE, 1); // number of days to add
			String dayDate = sdf.format(c.getTime());
			day.setDate(dayDate);
			day.setActualHours(wd.getD2_hours());
			day.setActivity(wd.getActivity_name());
			day.setModule(wd.getModule());
			day.setComment(wd.getD2_comment());
			dayDataList.add(day);
			}
		if(wd.getD3_hours()!=null){
			DayData day= new DayData();
			Date date = wd.getWeekStartDate();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			c.add(Calendar.DATE, 2); // number of days to add
			String dayDate = sdf.format(c.getTime());
			day.setDate(dayDate);
			day.setActualHours(wd.getD3_hours());
			day.setActivity(wd.getActivity_name());
			day.setModule(wd.getModule());
			day.setComment(wd.getD3_comment());
			dayDataList.add(day);
			}
		if(wd.getD4_hours()!=null){
			DayData day= new DayData();
			Date date = wd.getWeekStartDate();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			c.add(Calendar.DATE, 3); // number of days to add
			String dayDate = sdf.format(c.getTime());		
			day.setDate(dayDate);
			day.setActualHours(wd.getD4_hours());
			day.setActivity(wd.getActivity_name());
			day.setModule(wd.getModule());
			day.setComment(wd.getD4_comment());
			dayDataList.add(day);
			}
		if(wd.getD5_hours()!=null){
			DayData day= new DayData();
			Date date = wd.getWeekStartDate();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			c.add(Calendar.DATE, 4); // number of days to add
			String dayDate = sdf.format(c.getTime());
			day.setDate(dayDate);
			day.setActualHours(wd.getD5_hours());
			day.setActivity(wd.getActivity_name());
			day.setModule(wd.getModule());
			day.setComment(wd.getD5_comment());
			dayDataList.add(day);
			}
		if(wd.getD6_hours()!=null){
			DayData day= new DayData();
			Date date = wd.getWeekStartDate();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			c.add(Calendar.DATE, 5); // number of days to add
			String dayDate = sdf.format(c.getTime());
			day.setDate(dayDate);
			day.setActualHours(wd.getD6_hours());
			day.setActivity(wd.getActivity_name());
			day.setModule(wd.getModule());
			day.setComment(wd.getD6_comment());
			dayDataList.add(day);
			}
		if(wd.getD7_hours()!=null){
			DayData day= new DayData();
			Date date = wd.getWeekStartDate();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			c.add(Calendar.DATE, 6); // number of days to add
			String dayDate = sdf.format(c.getTime());
			day.setDate(dayDate);
			day.setActualHours(wd.getD7_hours());
			day.setActivity(wd.getActivity_name());
			day.setModule(wd.getModule());
			day.setComment(wd.getD7_comment());
			dayDataList.add(day);
			}
					
		return dayDataList;
	}

}
