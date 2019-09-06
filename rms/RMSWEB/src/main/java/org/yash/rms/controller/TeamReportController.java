package org.yash.rms.controller;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.yash.rms.domain.Resource;
import org.yash.rms.domain.Team;
import org.yash.rms.report.dto.TeamReport;
import org.yash.rms.service.OrgHierarchyService;
import org.yash.rms.service.TeamReportService;
import org.yash.rms.service.TeamService;
import org.yash.rms.util.UserUtil;

@Controller
@RequestMapping("/teamReports")
public class TeamReportController {

	@Autowired
	@Qualifier("teamReportService")
	TeamReportService teamReportService;

	@Autowired
	@Qualifier("teamService")
	TeamService teamService;

	@Autowired
	private UserUtil userUtil;

	@Autowired
	@Qualifier("OrgHierarchyService")
	OrgHierarchyService buService;

	private static final Logger logger = LoggerFactory
			.getLogger(PLReportController.class);

	Map<String, Map<String, List<TeamReport>>> teamReport = new HashMap<String, Map<String, List<TeamReport>>>();

	List<Team> teamList = new ArrayList<Team>();

	@RequestMapping(value = "/{teamId}", params = "find=getTeamReport", headers = "Accept=application/json")
	public ResponseEntity<String> getTeamReport(
			@PathVariable("teamId") List<Integer> teamId,
			// @RequestParam(value = "teamId") List<Integer> teamId,
			@RequestParam("weekStartDate") @DateTimeFormat(style = "M-") Date weekStartDate,
			@RequestParam("weekEndDate") @DateTimeFormat(style = "M-") Date weekEndDate,
			HttpServletRequest request, Model uiModel) throws Exception {

		logger.debug("----------Inside TeamReportController start getTeamReport-----------");

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");

		try {

			teamReport = teamReportService.getTeamReport(teamId, weekStartDate,
					weekEndDate);

		} catch (Exception e) {
			e.printStackTrace();
		}

		JSONObject jsonObject = new JSONObject(teamReport);

		logger.debug("----------Inside TeamReportController end getTeamReport-----------");

		return new ResponseEntity<String>(jsonObject.toString(), headers,
				HttpStatus.OK);

	}

	@RequestMapping(value = "/")
	public String getReportForm(Model uiModel) throws Exception {

		// Set Header values...
		Resource resource = userUtil.getLoggedInResource();
		try {
			if (resource.getUploadImage() != null
					&& resource.getUploadImage().length > 0) {
				byte[] encodeBase64UserImage = Base64.encodeBase64(userUtil
						.getLoggedInResource().getUploadImage());
				String base64EncodedUser = new String(encodeBase64UserImage,
						"UTF-8");
				uiModel.addAttribute("UserImage", base64EncodedUser);
			} else {
				uiModel.addAttribute("UserImage", "");
			}
			boolean isCurrentUserAdmin = UserUtil.isCurrentUserIsAdmin();
			if (isCurrentUserAdmin) {
				teamList = teamService.findAll();
			} else {
				teamList = teamService.getTeamListByResourceId(userUtil
						.getLoggedInResource().getEmployeeId());
			}
			//Collections.sort(teamList,new OrgHierarchy.OrgHierarchyComparator());
			uiModel.addAttribute("teamList", teamList);
		} catch (Exception e) {
			e.printStackTrace();
		}

		uiModel.addAttribute("firstName", resource
				.getFirstName()
				+ " "
				+ resource.getLastName());
		uiModel.addAttribute("designationName", resource
				.getDesignationId().getDesignationName());
		Calendar cal = Calendar.getInstance();
		cal.setTime(resource.getDateOfJoining());
		int m = cal.get(Calendar.MONTH) + 1;
		String months = new DateFormatSymbols().getMonths()[m - 1];
		int year = cal.get(Calendar.YEAR);
		uiModel.addAttribute("DOJ", months + "-" + year);
		uiModel.addAttribute("ROLE", resource
				.getUserRole());

		return "reports/TeamReport";

	}

	@RequestMapping(value = "/exportToExcel", method = RequestMethod.GET)
	public ModelAndView getExcel() {

		return new ModelAndView("teamExcelView", "teamReportsList", teamReport);

	}

}
