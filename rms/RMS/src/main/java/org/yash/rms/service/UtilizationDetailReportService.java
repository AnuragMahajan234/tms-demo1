/**
 * 
 */
package org.yash.rms.service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.yash.rms.report.dto.UtilizationDetailReport;
import org.yash.rms.report.dto.WeeklyData;

public interface UtilizationDetailReportService {
		public  Map<String,  TreeMap<Date, WeeklyData>>  getUtilizationDetailReport
		(List<Integer> orgIdList, List<Integer> locIdList, List<Integer> projIdList, Date startDate, Date endDate, 
							 List<Integer> employeeIdList);
}
