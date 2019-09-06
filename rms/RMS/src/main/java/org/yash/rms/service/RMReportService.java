package org.yash.rms.service;

import java.util.List;
import java.util.Set;

import org.yash.rms.report.dto.RMReport;

public interface RMReportService {

Set<RMReport> getRMReport(List<Integer> orgIdList, List<Integer> locIdList, List<Integer> projIdList, String roleId, String reportId);
}
