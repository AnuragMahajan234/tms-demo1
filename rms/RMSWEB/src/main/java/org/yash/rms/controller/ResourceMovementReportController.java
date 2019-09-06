package org.yash.rms.controller;

import java.sql.Date;
import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
import org.yash.rms.report.dto.ResourceMovementReport;
import org.yash.rms.report.dto.ResourceMovementReportGraphs;
import org.yash.rms.service.LocationService;
import org.yash.rms.service.OrgHierarchyService;
import org.yash.rms.service.ProjectService;
import org.yash.rms.service.ResourceMovementReportService;
import org.yash.rms.service.ResourceService;
import org.yash.rms.service.RmsCRUDService;
import org.yash.rms.util.DateUtil;
import org.yash.rms.util.UserUtil;

@Controller
@RequestMapping("/ResourceMovementReports")
public class ResourceMovementReportController {

	List<ResourceMovementReport> resourceMovementReportList = null;

	ResourceMovementReportGraphs resourceMovementReportGraphs;

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
	@Qualifier("resourceMovementReportService")
	ResourceMovementReportService resourceMovementReportService;

	@Autowired
	HttpSession httpSession;

	@Autowired
	UserUtil userUtil;

	private static final Logger logger = LoggerFactory.getLogger(ResourceMovementReportController.class);

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/")
	public String getResourceMovementReport(Model uiModel) {
		Resource resource = userUtil.getLoggedInResource();
		logger.debug("----------Inside ReportsController start getResourceMovementReport-----------");

		List<OrgHierarchy> allBuTypes = buService.findAllBu();

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
		// End
		return "report/ResourceMovementReport";
	}

	@RequestMapping(value = "getLocation/{ids}", method = RequestMethod.GET, headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> getLocation(@PathVariable("ids") List<Integer> ids) {

		logger.debug("----------Inside RMReportController start getLocation-----------");

		Set<Location> locationList = resourceService.getLocationListByBusinessGroup(ids);

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");

		if (locationList.isEmpty() || locationList == null) {

			return new ResponseEntity<String>("{ \"status\":\"false\"}", headers, HttpStatus.NOT_FOUND);
		}

		logger.info("------Inside RMReportController getLocation method end------");

		return new ResponseEntity<String>(Location.toJsonArray(locationList), headers, HttpStatus.OK);
	}

	@RequestMapping(value = "getProject/{bids}", method = RequestMethod.GET, headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> getProject(@PathVariable("bids") List<Integer> bids) {

		logger.debug("----------Inside RMReportController start getProject-----------");

		Set<Project> projectList = projectService.findAllProjectByBUIds(bids);

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");

		if (projectList.isEmpty() || projectList == null) {

			return new ResponseEntity<String>("{ \"status\":\"false\"}", headers, HttpStatus.NOT_FOUND);
		}

		logger.info("------Inside RMReportController getProject method end------");

		return new ResponseEntity<String>(Project.toJsonArray(projectList), headers, HttpStatus.OK);
	}

	@RequestMapping(value = "getResourceMovementReport", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody ResponseEntity<String> getResourceMovementReport(@RequestParam(value = "orgIdList") List<Integer> orgIdList, @RequestParam(value = "locIdList") List<Integer> locIdList,
			@RequestParam(value = "projIdList") List<Integer> projIdList, @RequestParam(value = "AllocationTypeID") List<Integer> allocationIDList, @RequestParam(value = "date") Date startDate,
			@RequestParam(value = "endDate", required = false) Date endDate, HttpServletRequest request, Model model) throws Exception {

		logger.debug("----------Inside RMReportController start getResourceMovementReport-----------");

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");

		resourceMovementReportList = resourceMovementReportService.getResourceMovementReport(orgIdList, locIdList, projIdList, startDate, endDate, allocationIDList);

		if (resourceMovementReportList == null) {

			return new ResponseEntity<String>("{ \"status\":\"false\"}", headers, HttpStatus.NOT_FOUND);
		}

		if (httpSession != null) {

			httpSession = null;
			httpSession = request.getSession(true);
			httpSession.setAttribute("RMReport", resourceMovementReportList);

		} else {

			httpSession = request.getSession(true);
			httpSession.setAttribute("RMReport", resourceMovementReportList);
		}

		logger.debug("----------Inside RMReportController End getResourceMovementReport-----------");

		System.out.println("JSON::" + ResourceMovementReport.toJsonArray(resourceMovementReportList));

		return new ResponseEntity<String>(ResourceMovementReport.toJsonArray(resourceMovementReportList), headers, HttpStatus.OK);

	}

	@RequestMapping(value = "/getResourceMovementReportExcel", method = RequestMethod.GET, headers = "Accept=text/html")
	public ModelAndView getResourceMovementReportExcel(@RequestParam(value = "endDate", required = false) Date endDate, @RequestParam(value = "startDate") Date startDate,
			@RequestParam(value = "exportToExcelRequired") Boolean exportToExcelRequired, @RequestParam(value = "orgIdList") List<Integer> orgIdList,
			@RequestParam(value = "locIdList") List<Integer> locIdList, @RequestParam(value = "projIdList") List<Integer> projIdList,
			@RequestParam(value = "AllocationTypeID") List<Integer> allocationIDList, Model model) {

		if (endDate == null) {
			endDate = DateUtil.getTodaysDate();
		}

		if (exportToExcelRequired) {
			resourceMovementReportGraphs = resourceMovementReportService.getResourceMovementAnalysisReport(resourceMovementReportList, orgIdList, locIdList, projIdList, startDate, endDate,
					allocationIDList);
		}
		model.addAttribute("resourceMovementReportList", resourceMovementReportList);
		model.addAttribute("resourceMovementReportGraphs", resourceMovementReportGraphs);
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);

		return new ModelAndView("resourceMovementReportExcel", "model", model);
	}
}