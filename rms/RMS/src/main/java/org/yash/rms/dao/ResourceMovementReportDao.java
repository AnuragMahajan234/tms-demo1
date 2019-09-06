package org.yash.rms.dao;

import java.util.Date;
import java.util.List;

import org.yash.rms.report.dto.ResourceMovementReport;

public interface ResourceMovementReportDao {

	List<ResourceMovementReport> getResourceMovementReport(List<Integer> orgIdList, List<Integer> locIdList, List<Integer> projIdList, Date date, Date endDate, List<Integer> allocationIDList);

	List<ResourceMovementReport> getAnalysisResourceMovementReport(List<Integer> orgIdList, List<Integer> locIdList, List<Integer> projIdList, Date startDate, Date endDate,
			List<Integer> allocationIDList);
}
