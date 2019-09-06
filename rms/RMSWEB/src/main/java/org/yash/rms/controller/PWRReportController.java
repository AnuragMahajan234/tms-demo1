package org.yash.rms.controller;

import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;
import org.apache.poi.ss.formula.functions.T;
import org.json.JSONObject;
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
import org.yash.rms.domain.Ownership;
import org.yash.rms.domain.Project;
import org.yash.rms.domain.Resource;
import org.yash.rms.report.dto.PWRReport;
import org.yash.rms.service.LocationService;
import org.yash.rms.service.OrgHierarchyService;
import org.yash.rms.service.OwnershipService;
import org.yash.rms.service.PWRReportService;
import org.yash.rms.service.ProjectService;
import org.yash.rms.service.ResourceService;
import org.yash.rms.service.RmsCRUDService;
import org.yash.rms.util.UserUtil;

@Controller
@RequestMapping("/pwrReports")
public class PWRReportController {

	Set<PWRReport> pwrReportView1 = null;

	Map<String, List<PWRReport>> pwrReportView2 = null;

	@Autowired
	ResourceService resourceService;

	@Autowired
	@Qualifier("OrgHierarchyService")
	OrgHierarchyService buService;

	@Autowired
	@Qualifier("OwnershipService")
	OwnershipService ownershipService;

	@Autowired
	@Qualifier("LocationService")
	LocationService locationService;

	@Autowired
	@Qualifier("allocatioTypeService")
	RmsCRUDService<AllocationType> allocationService;

	@Autowired
	@Qualifier("ProjectService")
	ProjectService projectService;

	@Autowired
	@Qualifier("pwrReportService")
	PWRReportService pwrReportService;

	@Autowired
	HttpSession httpSession;

	@Autowired
	UserUtil userUtil;

	private static final Logger logger = LoggerFactory.getLogger(PWRReportController.class);

	@RequestMapping(value = "/")
	public String getPWRReport(Model uiModel) {
		Resource resource = userUtil.getLoggedInResource();
		logger.debug("----------Inside PWRReportController start getPWRReport-----------");

		List<OrgHierarchy> allBuTypes = buService.findAllBusForBGADMIN();

		List<Ownership> allownership = ownershipService.findAll();

		@SuppressWarnings("unchecked")
		List<AllocationType> allocationType = (List<AllocationType>) (List<?>) allocationService.findAll();

		Collections.sort(allBuTypes, new OrgHierarchy.OrgHierarchyComparator());
		Collections.sort(allocationType, new AllocationType.AllocationTypeComparator());

		uiModel.addAttribute("orgHierarchy", allBuTypes);
		uiModel.addAttribute("AllocationType", allocationType);
		uiModel.addAttribute("Ownership", allownership);

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
		return "report/PWRReport";
	}

	@RequestMapping(value = "getLocation/{ids}", method = RequestMethod.GET, headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> getPWRReportLocation(@PathVariable("ids") List<Integer> ids) {

		logger.debug("----------Inside PWRReportController start getPWRReportLocation-----------");

		Set<Location> locationList = resourceService.getLocationListByBusinessGroupAndProjectIds(ids);

		HttpHeaders headers = new HttpHeaders();

		headers.add("Content-Type", "application/json; charset=utf-8");

		if (locationList.isEmpty() || locationList == null) {

			return new ResponseEntity<String>("{ \"status\":\"false\"}", headers, HttpStatus.NOT_FOUND);

		}

		logger.info("------Inside PWRReportController, getPWRReportLocation method end------");

		return new ResponseEntity<String>(Location.toJsonArray(locationList), headers, HttpStatus.OK);

	}

	@RequestMapping(value = "getProject/{bids}/{lids}", method = RequestMethod.GET, headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> getPWRReportProject(@PathVariable("bids") List<Integer> bIds, @PathVariable("lids") List<Integer> lIds) {

		logger.debug("----------Inside PWRReportController start getPWRReportProject-----------");

		Set<Project> projectList = projectService.findAllProjectByBUAndLocationIdsForPWRReport(bIds, lIds);

		HttpHeaders headers = new HttpHeaders();

		headers.add("Content-Type", "application/json; charset=utf-8");

		if (projectList.isEmpty() || projectList == null) {

			return new ResponseEntity<String>("{ \"status\":\"false\"}", headers, HttpStatus.NOT_FOUND);

		}

		logger.info("------Inside PWRReportController, getPWRReportProject method end------");

		return new ResponseEntity<String>(Project.toJsonArray(projectList), headers, HttpStatus.OK);

	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "getPWRReport", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody ResponseEntity<String> getPWRReport(@RequestParam(value = "orgIdList") List<Integer> orgIdList, @RequestParam(value = "locIdList") List<Integer> locIdList,
			@RequestParam(value = "projIdList") List<Integer> projIdList, @RequestParam(value = "ownership") List<Integer> ownership, @RequestParam(value = "date") Collection date,
			@RequestParam(value = "view") Integer view, HttpServletRequest request, Model model) throws Exception {

		logger.debug("----------Inside PWRReportController start getPWRReport-----------");

		HttpHeaders headers = new HttpHeaders();

		headers.add("Content-Type", "application/json; charset=utf-8");

		if (view == 1) {

			pwrReportView1 = pwrReportService.getPWRReportVeiw1(orgIdList, locIdList, projIdList, ownership, date, view);
			pwrReportView2 = pwrReportService.getPWRReportVeiw2(orgIdList, locIdList, projIdList, ownership, date, view);

			logger.debug("----------Inside PWRReportController End getPWRReport-----------");

			return new ResponseEntity<String>(PWRReport.toJsonArray(pwrReportView1), headers, HttpStatus.OK);

		} else {

			pwrReportView1 = pwrReportService.getPWRReportVeiw1(orgIdList, locIdList, projIdList, ownership, date, view);
			pwrReportView2 = pwrReportService.getPWRReportVeiw2(orgIdList, locIdList, projIdList, ownership, date, view);

			JSONObject jsonObject = new JSONObject(pwrReportView2);

			logger.debug("----------Inside PWRReportController End getPWRReport-----------");

			return new ResponseEntity<String>(jsonObject.toString(), headers, HttpStatus.OK);

		}

	}

	@RequestMapping(value = "/exportToExcel", method = RequestMethod.GET)
	public ModelAndView getExcel() {

		Map<String, Object> model = new HashMap<String, Object>();

		model.put("pwrReportView1", pwrReportView1);
		model.put("pwrReportView2", pwrReportView2);

		return new ModelAndView("pwrExcelView", "model", model);

	}

}
