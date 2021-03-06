package org.yash.rms.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.yash.rms.report.dto.TeamReport;

public interface TeamReportService {

	public Map<String, Map<String, List<TeamReport>>> getTeamReport(
			List<Integer> teamId, Date weekStartDate, Date weekEndDate);

}
