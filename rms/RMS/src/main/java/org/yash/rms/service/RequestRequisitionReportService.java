package org.yash.rms.service;

import java.util.List;

import org.yash.rms.report.dto.RequestRequisitionReport;
import org.yash.rms.report.dto.ResourceRequisitionProfileStatusReport;

public interface RequestRequisitionReportService {

	public List<RequestRequisitionReport> getRequestRequisitionReportForDateCriteria(Integer userId, String userRole,
			String startDate, String endDate);

	public List<RequestRequisitionReport> getRequestRequisitionReport(Integer userId, String userRole,
			String customerIds, String groupIds, String statusIds,boolean isLoadSubTable, String hiringUnits, String reqUnits);

	public List<RequestRequisitionReport> getDashBoardDataReport(Integer userId, String userRole,
			String customerIds, String groupIds, String statusIds, String hiringUnits, String reqUnits);

	public List<ResourceRequisitionProfileStatusReport> getResourceRequisitionProfileStatusReport(Integer userId, String userRole,  String startDate,
			String endDate, String customerIds);
}
