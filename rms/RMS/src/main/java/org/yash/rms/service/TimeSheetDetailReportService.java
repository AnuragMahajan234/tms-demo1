package org.yash.rms.service;

import java.util.Date;
import java.util.List;

import org.yash.rms.report.dto.TimeSheetDetailReport;

public interface TimeSheetDetailReportService {

	public List<TimeSheetDetailReport> getTimeSheetDetailReport(List<Integer> orgIdList, List<Integer> locIdList,
			List<Integer> projIdList, Date endDate, List<Integer> employeeIdList, Date startDate);

}
