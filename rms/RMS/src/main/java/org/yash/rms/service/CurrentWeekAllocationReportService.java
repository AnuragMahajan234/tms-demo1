/**
 * 
 */
package org.yash.rms.service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.yash.rms.report.dto.CurrentWeekAllocation;
import org.yash.rms.report.dto.UtilizationDetailReport;
import org.yash.rms.report.dto.WeeklyData;

public interface CurrentWeekAllocationReportService {
		public  Map<String, List<Object[]>>  getCurrentWeekAllocationReport (List<Integer> buIds, List<Integer> locationIds, List<Integer> projIds, List<Integer> ownerShipIds, Date endDate,  List<Integer> currentBUIds);
}
