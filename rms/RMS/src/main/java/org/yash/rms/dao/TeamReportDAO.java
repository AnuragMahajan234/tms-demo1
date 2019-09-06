package org.yash.rms.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.yash.rms.report.dto.TeamReport;

public interface TeamReportDAO {

	public Map<String, Map<String, List<TeamReport>>> getTeamReport(
			List<Integer> teamId, Date weekStartDate, Date weekEndDate);
	
	public Map<String, Map<String, List<TeamReport>>> getNewTeamReport(
			List<Integer> teamId, Date weekStartDate, Date weekEndDate);

}
