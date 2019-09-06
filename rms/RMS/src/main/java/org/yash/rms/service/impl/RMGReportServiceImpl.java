package org.yash.rms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yash.rms.dao.RMGReportDao;
import org.yash.rms.service.RMGReportService;

@SuppressWarnings("rawtypes")
@Service("RMGReportService")
public class RMGReportServiceImpl implements RMGReportService{

	@Autowired
	RMGReportDao rmgReportDao;
	
	public List getResourceMetricsTable(String startDay, String endDay, String startMonth, String endMonth, String startYear, String endYear) {
		return rmgReportDao.getResourceMetricsTable(startDay, endDay, startMonth, endMonth, startYear, endYear);
	}

	public List getRRFMetricsTable(String startDay, String endDay, String startMonth, String endMonth, String startYear, String endYear) {
		return rmgReportDao.getRRFMetricsTable(startDay, endDay, startMonth, endMonth, startYear, endYear);
	}

	public List getResourceMetricsByBGTable(String startDay, String endDay, String startMonth, String endMonth, String startYear, String endYear) {
		return rmgReportDao.getResourceMetricsByBGTable(startDay, endDay, startMonth, endMonth, startYear, endYear);
	}

	public List getResourceMetricsByBGBUTable(String startDay, String endDay, String startMonth, String endMonth, String startYear, String endYear) {
		return rmgReportDao.getResourceMetricsByBGBUTable(startDay, endDay, startMonth, endMonth, startYear, endYear);
	}

	public List getGradewiseReportTable(String startDay, String endDay, String startMonth, String endMonth, String startYear, String endYear) {
		return rmgReportDao.getGradewiseReportTable(startDay, endDay, startMonth, endMonth, startYear, endYear);
	}

	public List getBGBUMatricesTable(String startDate, String endDate, String startMonth, String endMonth, String startYear, String endYear) {
		return rmgReportDao.getBGBUMatricesTable(startDate, endDate, startMonth, endMonth, startYear, endYear);
	}

	public List getBenchGradeReportTable(String startDay, String endDay, String startMonth, String endMonth, String startYear, String endYear) {
		return rmgReportDao.getBenchGradeReportTable(startDay, endDay, startMonth, endMonth, startYear, endYear);
	}

	public List getBenchGradeDaysWiseReportTable(String startDay, String endDay, String startMonth, String endMonth, String startYear, String endYear) {
		return rmgReportDao.getBenchGradeDaysWiseReportTable(startDay, endDay, startMonth, endMonth, startYear, endYear);
	}

	public List getBenchSkillReportTable(String startDay, String endDay, String startMonth, String endMonth, String startYear, String endYear) {
		return rmgReportDao.getBenchSkillReportTable(startDay, endDay, startMonth, endMonth, startYear, endYear);
	}

	public List getProjectsClosingIn_3_MonthsReportTable(String startDay, String endDay, String startMonth, String endMonth, String startYear, String endYear) {
		return rmgReportDao.getProjectsClosingIn_3_MonthsReportTable(startDay, endDay, startMonth, endMonth, startYear, endYear);
	}

	public List getSkillsReleasingIn_3_MonthsReportTable(String startDay, String endDay, String startMonth, String endMonth, String startYear, String endYear) {
		return rmgReportDao.getSkillsReleasingIn_3_MonthsReportTable(startDay, endDay, startMonth, endMonth, startYear, endYear);
	}

}
