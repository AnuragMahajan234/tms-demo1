
package org.yash.rms.service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.yash.rms.report.dto.MuneerReport;
import org.yash.rms.report.dto.MuneerReportDto;

public interface CustomizedHeaderReportService {

	public Set<MuneerReport> getSelectedHeaderReport(List<Integer> orgIdList, List<Integer> locIdList, List<Integer> projIdList, String roleId, String reportId, Date weekDate, List<String> sortableList);

}
