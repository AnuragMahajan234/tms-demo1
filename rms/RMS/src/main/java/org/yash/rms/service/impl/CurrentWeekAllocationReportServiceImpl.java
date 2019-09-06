package org.yash.rms.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.yash.rms.dao.CurrentWeekAllocationReportDao;
import org.yash.rms.report.dto.CurrentWeekAllocation;
import org.yash.rms.report.dto.WeeklyData;
import org.yash.rms.service.CurrentWeekAllocationReportService;

@Service("currentWeekAllocationReportService")
public class CurrentWeekAllocationReportServiceImpl implements
		CurrentWeekAllocationReportService {

	@Autowired
	@Qualifier("currentWeekAllocationReportDao")
	CurrentWeekAllocationReportDao currentWeekAllocationReportDao;

	private static final Logger logger = LoggerFactory
			.getLogger(CurrentWeekAllocationReportServiceImpl.class);

	public Map<String, List<Object []>> getCurrentWeekAllocationReport(List<Integer> buIds, List<Integer> locationIds, List<Integer> projIds, List<Integer> ownerShipIds, Date endDate,  List<Integer> currentBUIds) {
		logger.info("--------CurrentWeekAllocationReportServiceImpl getUtilizationReport method start-------");
		@SuppressWarnings("deprecation")
		Date startDate = new DateTime(endDate).minusDays(endDate.getDay()-1).toDate();

		List<Object[]> reportList = currentWeekAllocationReportDao.getCurrentWeekAllocationReport(buIds, locationIds, projIds, ownerShipIds, endDate,startDate, currentBUIds);
		Map<String, List<Object []>> cwaReportMap = new HashMap<String, List<Object []>>();
		if(reportList != null && !reportList.isEmpty())
			populateReportListToCWAReportMap(cwaReportMap, reportList);

		logger.info("--------CurrentWeekAllocationReportServiceImpl getUtilizationReport method end-------");

		return cwaReportMap;

	}

	private void populateReportListToCWAReportMap(	Map<String, List<Object[]>> cwaReportMap,	List<Object[]> reportList) {

		for (Object[] cwaRow : reportList) {
			if(cwaRow !=  null){
				if(!cwaReportMap.containsKey((String) cwaRow[0])){
					List<Object[]> cwaReports= new ArrayList<Object[]>();
					cwaReports.add(cwaRow);
					cwaReportMap.put((String) cwaRow[0], cwaReports);
				}else{
					List<Object[]> cwaReports = cwaReportMap.get((String) cwaRow[0]);
					cwaReports.add(cwaRow);
				}
			}
		}
	}

	
	

}
