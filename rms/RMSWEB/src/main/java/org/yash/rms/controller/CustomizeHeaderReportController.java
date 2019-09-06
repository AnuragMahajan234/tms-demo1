package org.yash.rms.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.yash.rms.domain.Location;
import org.yash.rms.domain.OrgHierarchy;
import org.yash.rms.domain.Project;
import org.yash.rms.report.dto.MuneerReport;
import org.yash.rms.report.dto.MuneerReportDto;
import org.yash.rms.service.CustomizedHeaderReportService;
import org.yash.rms.service.LocationService;
import org.yash.rms.service.OrgHierarchyService;
import org.yash.rms.service.ProjectService;
import org.yash.rms.service.ResourceService;
import org.yash.rms.util.Constants;
import org.yash.rms.util.UserUtil;

@Controller
@RequestMapping("/customizedHeaderReport")
public class CustomizeHeaderReportController {

	@Autowired
	ResourceService resourceService;

	@Autowired
	@Qualifier("OrgHierarchyService")
	OrgHierarchyService buService;

	@Autowired
	@Qualifier("LocationService")
	LocationService locationService;

	@Autowired
	@Qualifier("ProjectService")
	ProjectService projectService;

	@Autowired
	@Qualifier("customizedHeaderReportService")
	CustomizedHeaderReportService customizedHeaderReportService;

	@Autowired
	HttpSession httpSession;

	@Autowired
	UserUtil userUtil;

	private static final Logger logger = LoggerFactory.getLogger(CustomizeHeaderReportController.class);
	Set<MuneerReport> customizedHeaderReport = new HashSet<MuneerReport>();

	Map<String, MuneerReportDto> customizedHeaderClientReport = new HashMap<String, MuneerReportDto>();

	@RequestMapping(value = "/")
	public String getCustomizedHeaderReport(Model uiModel) throws Exception {

		logger.debug("----------Inside CustomizeHeaderReportController start getReportForm-----------");


		List<Location> locationList = null;
		List<Project> projectList = null;

		List<OrgHierarchy> allBuTypes = buService.findAllBu();
		locationList = locationService.findAll();
		projectList = projectService.findAllActiveProjects();
		Collections.sort(locationList, new Location.LocationNameComparator());
		Collections.sort(projectList, new Project.ProjectNameComparator());
		Collections.sort(allBuTypes, new OrgHierarchy.OrgHierarchyComparator());

		uiModel.addAttribute("orgHierarchy", allBuTypes);
		uiModel.addAttribute(Constants.LOCATION, locationList);
		uiModel.addAttribute(Constants.PROJECTS, projectList);

		logger.debug("----------Inside CustomizedReportReportsController end getReportForm-----------");

		return "report/customizedHeaderReport";
	}


	@RequestMapping(value = "/report", method = RequestMethod.GET)
	public ModelAndView getCustomizedHeaderReport(@RequestParam(value = "orgIdList") List<Integer> orgIdList, @RequestParam(value = "locIdList") List<Integer> locIdList,
			@RequestParam(value = "projIdList") List<Integer> projIdList, @RequestParam(value = "reportTypeId") String reportId, @RequestParam(value = "roleId") String roleId,
			@RequestParam(value = "endDate") String endDate, @RequestParam(value = "sortableList") List<String> sortableList,HttpServletRequest request, Model model) throws Exception {

		logger.debug("----------Inside MuneerReportController End getMuneerReport-----------");
		Map<String, Object> report = new HashMap<String, Object>();

		Date weekEndDate = null;

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		try {
			weekEndDate = formatter.parse(endDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		customizedHeaderReport = customizedHeaderReportService.getSelectedHeaderReport(orgIdList, locIdList, projIdList, roleId, reportId, weekEndDate,sortableList);
		logger.debug("----------Inside MuneerReportController End getMuneerReport-----------");

		return new ModelAndView("customizedHeaderExcelView", "report", customizedHeaderReport);

	}



}
