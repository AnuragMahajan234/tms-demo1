package org.yash.rms.controller;

import java.sql.Date;
import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.apache.poi.ss.formula.functions.T;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.yash.rms.domain.AllocationType;
import org.yash.rms.domain.Location;
import org.yash.rms.domain.OrgHierarchy;
import org.yash.rms.domain.Project;
import org.yash.rms.domain.Resource;
import org.yash.rms.report.dto.RMReport;
import org.yash.rms.service.LocationService;
import org.yash.rms.service.OrgHierarchyService;
import org.yash.rms.service.ProjectService;
import org.yash.rms.service.RMReportService;
import org.yash.rms.service.ResourceService;
import org.yash.rms.service.RmsCRUDService;
import org.yash.rms.util.UserUtil;

@Controller
@RequestMapping("/rmReports")
public class RMReportController {

	@Autowired
	ResourceService resourceService;

	@Autowired
	@Qualifier("OrgHierarchyService")
	OrgHierarchyService buService;

	@Autowired
	@Qualifier("allocatioTypeService")
	RmsCRUDService<AllocationType> allocationService;

	@Autowired
	@Qualifier("LocationService")
	LocationService locationService;

	@Autowired
	@Qualifier("ProjectService")
	ProjectService projectService;

	@Autowired
	@Qualifier("rmReportService")
	RMReportService reportService;

	@Autowired
	UserUtil userUtil;

	Set<RMReport> rmReportsList = new HashSet<RMReport>();

	private static final Logger logger = LoggerFactory.getLogger(RMReportController.class);
	String roleIdGlobal = new String();
	String reportIdGlobal = new String();

	@RequestMapping(value = "/")
	public String getRMReport(Model uiModel) {

		logger.debug("----------Inside RMReportController start getRMReport-----------");
		Resource resource = userUtil.getLoggedInResource();
		List<OrgHierarchy> allBuTypes = buService.findAllBusForBGADMIN();

		@SuppressWarnings("unchecked")
		List<AllocationType> allocationType = (List<AllocationType>) (List<?>) allocationService.findAll();

		Collections.sort(allBuTypes, new OrgHierarchy.OrgHierarchyComparator());
		Collections.sort(allocationType, new AllocationType.AllocationTypeComparator());

		uiModel.addAttribute("orgHierarchy", allBuTypes);
		uiModel.addAttribute("AllocationType", allocationType);

		logger.debug("----------Inside ReportsController end getResourceMovementReport-----------");
		// Set Header values...

		try {

			if (resource.getUploadImage() != null && resource.getUploadImage().length > 0) {

				byte[] encodeBase64UserImage = Base64.encodeBase64(resource.getUploadImage());

				String base64EncodedUser = new String(encodeBase64UserImage, "UTF-8");

				uiModel.addAttribute("UserImage", base64EncodedUser);

			} else {

				uiModel.addAttribute("UserImage", "");
			}

		} catch (Exception e) {

			e.printStackTrace();
		}

		uiModel.addAttribute("firstName", resource.getFirstName() + " " + resource.getLastName());
		uiModel.addAttribute("designationName", resource.getDesignationId().getDesignationName());

		Calendar cal = Calendar.getInstance();
		cal.setTime(resource.getDateOfJoining());

		int m = cal.get(Calendar.MONTH) + 1;
		String months = new DateFormatSymbols().getMonths()[m - 1];

		int year = cal.get(Calendar.YEAR);

		uiModel.addAttribute("DOJ", months + "-" + year);
		uiModel.addAttribute("ROLE", resource.getUserRole());

		return "report/RMReport";
	}

	@RequestMapping(value = "getLocation/{ids}", method = RequestMethod.GET, headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> getLocation(@PathVariable("ids") List<Integer> ids) {

		logger.debug("----------Inside RMReportController start getRMReportLocation-----------");

		Set<Location> locationList = resourceService.getLocationListByBusinessGroupAndProjectIds(ids);

		HttpHeaders headers = new HttpHeaders();

		headers.add("Content-Type", "application/json; charset=utf-8");

		if (locationList.isEmpty() || locationList == null) {
			return new ResponseEntity<String>("{ \"status\":\"false\"}", headers, HttpStatus.NOT_FOUND);
		}

		logger.info("------Inside RMReportController getRMReportLocation method end------");

		return new ResponseEntity<String>(Location.toJsonArray(locationList), headers, HttpStatus.OK);

	}

	@RequestMapping(value = "getProject/{bids}", method = RequestMethod.GET, headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> getProject(@PathVariable("bids") List<Integer> bids) {

		logger.debug("----------Inside RMReportController start getRMReportProject-----------");

		Set<Project> projectList = projectService.findAllProjectByBUIds(bids);

		HttpHeaders headers = new HttpHeaders();

		headers.add("Content-Type", "application/json; charset=utf-8");

		if (projectList.isEmpty() || projectList == null) {
			return new ResponseEntity<String>("{ \"status\":\"false\"}", headers, HttpStatus.NOT_FOUND);
		}

		logger.info("------Inside RMReportController getRMReportProject method end------");

		return new ResponseEntity<String>(Project.toJsonArray(projectList), headers, HttpStatus.OK);

	}

	@RequestMapping(value = "getRMReport", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody ResponseEntity<String> getRMReport(@RequestParam(value = "orgIdList") List<Integer> orgIdList, @RequestParam(value = "locIdList") List<Integer> locIdList,
			@RequestParam(value = "projIdList") List<Integer> projIdList, @RequestParam(value = "roleId") String roleId, @RequestParam(value = "reportId") String reportId, HttpServletRequest request,
			Model model) throws Exception {

		logger.debug("----------Inside RMReportController start getRMReport-----------");

		HttpHeaders headers = new HttpHeaders();
		roleIdGlobal=roleId;
		reportIdGlobal = reportId;
		headers.add("Content-Type", "application/json; charset=utf-8");

		rmReportsList = reportService.getRMReport(orgIdList, locIdList, projIdList, roleId, reportId);

		if (rmReportsList.isEmpty() || rmReportsList == null) {
			return new ResponseEntity<String>("{ \"status\":\"false\"}", headers, HttpStatus.NOT_FOUND);
		}

		logger.debug("----------Inside RMReportController End getRMReport-----------");

		return new ResponseEntity<String>(RMReport.toJsonArray(rmReportsList), headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/exportToExcel", method = RequestMethod.GET)
	public ModelAndView getRMReportExcel(Model model) {

		model.addAttribute("rmReportsList", rmReportsList);
		model.addAttribute("role", roleIdGlobal);	
		model.addAttribute("reportId", reportIdGlobal);
		return new ModelAndView("rmExcelView", "model", model);

	}

}
