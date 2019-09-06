
package org.yash.rms.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.yash.rms.domain.Location;
import org.yash.rms.domain.OrgHierarchy;
import org.yash.rms.domain.Project;
import org.yash.rms.domain.Resource;
import org.yash.rms.report.dto.MuneerReport;
import org.yash.rms.report.dto.MuneerReportDto;
import org.yash.rms.service.LocationService;
import org.yash.rms.service.MuneerReportService;
import org.yash.rms.service.OrgHierarchyService;
import org.yash.rms.service.ProjectService;
import org.yash.rms.service.ResourceService;
import org.yash.rms.util.UserUtil;

@Controller
@RequestMapping("/muneerReports")
public class MuneerReportController {

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
	@Qualifier("muneerReportService")
	MuneerReportService muneerReportService;

	@Autowired
	HttpSession httpSession;

	@Autowired
	UserUtil userUtil;

	Set<MuneerReport> muneerReportsList = new HashSet<MuneerReport>();

	Map<String, MuneerReportDto> muneerClientReport = new HashMap<String, MuneerReportDto>();

	private static final Logger logger = LoggerFactory.getLogger(MuneerReportController.class);

	@RequestMapping(value = "/")
	public String getMuneerReport(Model uiModel) {

		logger.debug("----------Inside MuneerReportsController start getMuneerReport-----------");

		List<OrgHierarchy> allBuTypes = buService.findAllBusForBGADMIN();

		Collections.sort(allBuTypes, new OrgHierarchy.OrgHierarchyComparator());
		
		Resource resource = userUtil.getLoggedInResource();

		uiModel.addAttribute("orgHierarchy", allBuTypes);

		try {

			if (resource.getUploadImage() != null && resource.getUploadImage().length > 0) {

				byte[] encodeBase64UserImage = Base64.encodeBase64(resource.getUploadImage());

				String base64EncodedUser = new String(encodeBase64UserImage, "UTF-8");

				uiModel.addAttribute("UserImage", base64EncodedUser);

			} else {

				uiModel.addAttribute("UserImage", "");
			}

		}
		catch (Exception e) {

			e.printStackTrace();
			logger.debug(e.getMessage());
		}

		uiModel.addAttribute("firstName", resource.getFirstName() + " " + resource.getLastName());
		uiModel.addAttribute("designationName", resource.getDesignationId().getDesignationName());

		uiModel.addAttribute("ROLE", resource.getUserRole());

		logger.debug("----------Inside MuneerReportsController end getMuneerReport-----------");
		// End

		return "report/MuneerReport";
	}

	@RequestMapping(value = "getLocation/{ids}", method = RequestMethod.GET, headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> getMuneerReportLocation(@PathVariable("ids") List<Integer> ids) {

		logger.debug("----------Inside MuneerReportController start getMuneerReportLocation-----------");

		Set<Location> locationList = resourceService.getLocationListByBusinessGroupAndProjectIds(ids);

		HttpHeaders headers = new HttpHeaders();

		headers.add("Content-Type", "application/json; charset=utf-8");

		if (locationList.isEmpty() || locationList == null) {

			return new ResponseEntity<String>("{ \"status\":\"false\"}", headers, HttpStatus.NOT_FOUND);
		}

		logger.info("------Inside MuneerReportController getMuneerReportLocation method end------");

		return new ResponseEntity<String>(Location.toJsonArray(locationList), headers, HttpStatus.OK);

	}

	@RequestMapping(value = "getProject/{bids}", method = RequestMethod.GET, headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> getMuneerReportProject(@PathVariable("bids") List<Integer> bids) {

		logger.debug("----------Inside MuneerReportController start getMuneerReportProject-----------");

		Set<Project> projectList = projectService.findAllProjectByBUIds(bids);

		HttpHeaders headers = new HttpHeaders();

		headers.add("Content-Type", "application/json; charset=utf-8");

		if (projectList.isEmpty() || projectList == null) {

			return new ResponseEntity<String>("{ \"status\":\"false\"}", headers, HttpStatus.NOT_FOUND);
		}

		logger.info("------Inside MuneerReportController getMuneerReportProject method end------");

		return new ResponseEntity<String>(Project.toJsonArray(projectList), headers, HttpStatus.OK);

	}

	@RequestMapping(value = "getMuneerReport", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	/*public ResponseEntity<String> getMuneerReport(@RequestParam(value = "orgIdList") List<Integer> orgIdList, @RequestParam(value = "locIdList") List<Integer> locIdList,
			@RequestParam(value = "projIdList") List<Integer> projIdList, @RequestParam(value = "roleId") String roleId, @RequestParam(value = "reportId") String reportId,
			@RequestParam("weekDate") @DateTimeFormat(style = "M-") Date weekDate,

			HttpServletRequest request, Model model) throws Exception {*/
	public ResponseEntity<String> getMuneerReport(@RequestParam(value = "orgIdList") List<Integer> orgIdList, @RequestParam(value = "locIdList") List<Integer> locIdList,
			@RequestParam(value = "projIdList") List<Integer> projIdList, @RequestParam(value = "roleId") String roleId, @RequestParam(value = "reportId") String reportId,
			String weekDate,

			HttpServletRequest request, Model model) throws Exception {

		logger.debug("----------Inside MuneerReportController start getMuneerReport-----------");
		
		HttpHeaders headers = new HttpHeaders();
		
		headers.add("Content-Type", "application/json; charset=utf-8");
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date weekEndDate = format.parse(weekDate);

		muneerReportsList = muneerReportService.getMuneerReport(orgIdList, locIdList, projIdList, roleId, reportId, weekEndDate);

		muneerClientReport = muneerReportService.getMuneerClientReport(orgIdList, locIdList, projIdList, roleId, reportId, weekEndDate);

		if (muneerReportsList.isEmpty() || muneerReportsList == null) {
			
			return new ResponseEntity<String>("{ \"status\":\"false\"}", headers, HttpStatus.NOT_FOUND);
		}

		logger.debug("----------Inside MuneerReportController End getMuneerReport-----------");
		
		return new ResponseEntity<String>(MuneerReport.toJsonArray(muneerReportsList), headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/exportToExcel", method = RequestMethod.GET)
	public ModelAndView getExcel() {

		httpSession.setAttribute("MuneerClientData", muneerClientReport);
		return new ModelAndView("muneerExcelView", "rmReportsList", muneerReportsList);

	}

}
