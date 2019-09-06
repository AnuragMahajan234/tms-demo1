package org.yash.rms.dao;

import java.util.Date;
import java.util.List;
import org.yash.rms.report.dto.UtilizationDetailReport;


public interface UtilizationDetailReportDao {

	List<UtilizationDetailReport> getUtilizationDetailReport(List<Integer> orgIdList, List<Integer> locIdList,
								List<Integer> projIdList, Date date, Date endDate, List<Integer> employeeIdList);

	
}
