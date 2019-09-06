package org.yash.rms.service;

import java.util.List;

@SuppressWarnings("rawtypes")
public interface RMGReportService {
	
	public List getResourceMetricsTable(String startDay, String endDay, String startMonth, String endMonth, String startYear, String endYear);
	
	public List getRRFMetricsTable(String startDay, String endDay, String startMonth, String endMonth, String startYear, String endYear);
	
	public List getResourceMetricsByBGTable(String startDay, String endDay, String startMonth, String endMonth, String startYear, String endYear);
	
	public List getResourceMetricsByBGBUTable(String startDay, String endDay, String startMonth, String endMonth, String startYear, String endYear);
	
	public List getGradewiseReportTable(String startDay, String endDay, String startMonth, String endMonth, String startYear, String endYear);
	
	public List getBGBUMatricesTable(String startDate, String endDate, String startMonth, String endMonth, String startYear, String endYear);
	
	public List getBenchGradeReportTable(String startDay, String endDay, String startMonth, String endMonth, String startYear, String endYear);
	
	public List getBenchGradeDaysWiseReportTable(String startDay, String endDay, String startMonth, String endMonth, String startYear, String endYear);
	
	public List getBenchSkillReportTable(String startDay, String endDay, String startMonth, String endMonth, String startYear, String endYear);
	
	public List getProjectsClosingIn_3_MonthsReportTable(String startDay, String endDay, String startMonth, String endMonth, String startYear, String endYear);
	
	public List getSkillsReleasingIn_3_MonthsReportTable(String startDay, String endDay, String startMonth, String endMonth, String startYear, String endYear);

}
