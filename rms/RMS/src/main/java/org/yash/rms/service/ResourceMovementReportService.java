package org.yash.rms.service;

import java.util.Date;
import java.util.List;

import org.yash.rms.report.dto.ResourceMovementReport;
import org.yash.rms.report.dto.ResourceMovementReportGraphs;

public interface ResourceMovementReportService {

	List<ResourceMovementReport> getResourceMovementReport(List<Integer> orgIdList, List<Integer> locIdList, List<Integer> projIdList, Date startDate, Date endDate, List<Integer> allocationIDList);

	ResourceMovementReportGraphs getResourceMovementAnalysisReport(List<ResourceMovementReport> resourceMovementReports, List<Integer> orgIdList, List<Integer> locIdList, List<Integer> projIdList, Date startDate, Date endDate, List<Integer> allocationIDList);
}
