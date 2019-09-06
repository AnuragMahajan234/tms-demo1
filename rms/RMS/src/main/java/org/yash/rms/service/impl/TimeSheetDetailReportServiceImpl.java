package org.yash.rms.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yash.rms.dao.TimeSheetDetailReportDao;
import org.yash.rms.report.dto.TimeSheetDetailReport;
import org.yash.rms.service.TimeSheetDetailReportService;

@Service("timeSheetDetailReportService")
@Transactional
public class TimeSheetDetailReportServiceImpl implements TimeSheetDetailReportService {

	@Autowired
	@Qualifier("timeSheetDetailReportDao")
	TimeSheetDetailReportDao timeShetDetailReportDao;

	private static final Logger LOGGER = LoggerFactory.getLogger(TimeSheetDetailReportServiceImpl.class);

	/**
	 * @param orgIdList
	 * @param locIdList
	 * @param projIdList
	 * @param endDate
	 * @param employeeIdList
	 * @return
	 */

	public List<TimeSheetDetailReport> getTimeSheetDetailReport(List<Integer> orgIdList, List<Integer> locIdList,
			List<Integer> projIdList, Date endDate, List<Integer> employeeIdList, Date startDate) {
		LOGGER.info("--------TimeSheetDetailReportServiceImpl getTimeSheetDetailReport method start-------");

		boolean flag = false;
		List<TimeSheetDetailReport> timeSheetDetailReportList = new ArrayList<TimeSheetDetailReport>();

		timeSheetDetailReportList = timeShetDetailReportDao.getTimeSheetDetailReport(orgIdList, locIdList, projIdList,
				endDate, employeeIdList, startDate);

		return timeSheetDetailReportList;

	}

}
