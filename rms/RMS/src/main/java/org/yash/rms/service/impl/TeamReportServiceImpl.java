package org.yash.rms.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.yash.rms.dao.TeamReportDAO;
import org.yash.rms.dao.impl.TeamReportDaoImpl;
import org.yash.rms.report.dto.TeamReport;
import org.yash.rms.service.TeamReportService;

@Service("teamReportService")
public class TeamReportServiceImpl implements TeamReportService {

	@Autowired
	@Qualifier("teamReportDao")
	TeamReportDAO teamReportDAO;

	private static final Logger logger = LoggerFactory
			.getLogger(TeamReportDaoImpl.class);

	public Map<String, Map<String, List<TeamReport>>> getTeamReport(
			List<Integer> teamId, Date weekStartDate, Date weekEndDate) {

		logger.info("--------TeamReportServiceImpl getTeamReport method start-------");

		/*Map<String, Map<String, List<TeamReport>>> teamReport = teamReportDAO
				.getTeamReport(teamId, weekStartDate, weekEndDate);*/
		
		Map<String, Map<String, List<TeamReport>>> teamReport = teamReportDAO
				.getNewTeamReport(teamId, weekStartDate, weekEndDate);
		

		logger.info("--------TeamReportServiceImpl getTeamReport method end-------");

		return teamReport;
	}

}
