package org.yash.rms.controller;

import java.sql.Date;
//import java.util.Date;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;
import org.joda.time.DateTime;
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
import org.yash.rms.domain.Location;
import org.yash.rms.domain.OrgHierarchy;
import org.yash.rms.domain.Ownership;
import org.yash.rms.domain.Project;
import org.yash.rms.domain.Resource;
import org.yash.rms.dto.CustomerDTO;
import org.yash.rms.report.dto.CurrentWeekAllocation;
import org.yash.rms.report.dto.UtilizationDetailReport;
import org.yash.rms.report.dto.WeeklyData;
import org.yash.rms.service.CustomerService;
import org.yash.rms.service.LocationService;
import org.yash.rms.service.OrgHierarchyService;
import org.yash.rms.service.OwnershipService;
import org.yash.rms.service.ProjectService;
import org.yash.rms.service.ResourceService;
import org.yash.rms.service.CurrentWeekAllocationReportService;
import org.yash.rms.util.Constants;
import org.yash.rms.util.DateUtil;
import org.yash.rms.util.UserUtil;

@Controller
@RequestMapping("/currentWeekAllocation")
public class CurrentWeekAllocationReportController {

	 @Autowired
	 @Qualifier("ResourceService")
	 private ResourceService resourceService;
	 
	 @Autowired
	 @Qualifier("CustomerService")
	 private CustomerService customerService;

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
	HttpSession httpSession;

	@Autowired
	UserUtil userUtil;
	
	@Autowired
	@Qualifier("currentWeekAllocationReportService")
	CurrentWeekAllocationReportService currentWeekAllocationReportService;
	
	@Autowired
	@Qualifier("OwnershipService")
	OwnershipService ownershipService;
	
	Map<String, Object> cwaReportMap = new HashMap<String, Object>();

	private static final Logger logger = LoggerFactory.getLogger(CurrentWeekAllocationReportController.class);

	@RequestMapping(value = "/")
	public String currentWeekAllocationReport(Model uiModel) {

		logger.debug("----------Inside ReportsController start currentWeekAllocationReport-----------");

		List<OrgHierarchy> allBuTypes = buService.findAllBu();

		List<Resource> resourceList = null;
		List<CustomerDTO> customerList = null;
		List<Location> locationList = null;
		List<Project> projectList = null;
		
		resourceList = resourceService.findActiveResources();
		locationList = locationService.findAll();
		projectList = projectService.findAllActiveProjects();
		List<Ownership> allownership = ownershipService.findAll();
		Collections.sort(locationList, new Location.LocationNameComparator());
		Collections.sort(projectList, new Project.ProjectNameComparator());
		Collections.sort(allBuTypes, new OrgHierarchy.OrgHierarchyComparator());
		
		uiModel.addAttribute("orgHierarchy", allBuTypes);
		uiModel.addAttribute("resourceOrgHierarchy", allBuTypes);
		uiModel.addAttribute(Constants.OWNERSHIP, allownership);
		uiModel.addAttribute(Constants.RESOURCES, resourceList);
		uiModel.addAttribute(Constants.ALL_CUSTOMERS, customerList);
		uiModel.addAttribute(Constants.LOCATION, locationList);
		uiModel.addAttribute(Constants.PROJECTS, projectList);

		uiModel.addAttribute("ROLE", userUtil.getLoggedInResource().getUserRole());
		logger.debug("----------Inside ReportsController end currentWeekAllocationReport-----------");
		return "report/currentWeekAllocationReport";
	}
	
	//orgHierarch
	
	@RequestMapping(value = "getOrgHierarchy/{bids}", method = RequestMethod.GET, headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> getOrgHierarchy(@PathVariable("bids") List<Integer> bids) {

		logger.debug("----------Inside UtilizationDetailReportController start getOrgHierarchy-----------");

		List<Project> projectList = projectService.findAllActiveProjects();

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");

		if (projectList.isEmpty() || projectList == null) {

			return new ResponseEntity<String>("{ \"status\":\"false\"}", headers, HttpStatus.NOT_FOUND);
		}

		logger.info("------Inside UtilizationDetailReportController getOrgHierarchy method end------");

		return new ResponseEntity<String>(Project.toJsonArray(projectList), headers, HttpStatus.OK);
	}
	

	@RequestMapping(value = "getLocation/{ids}", method = RequestMethod.GET, headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> getLocation(@PathVariable("ids") List<Integer> ids) {

		logger.debug("----------Inside UtilizationDetailReportController start getLocation-----------");

		Set<Location> locationList = resourceService.getLocationListByBusinessGroup(ids);

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");

		if (locationList.isEmpty() || locationList == null) {

			return new ResponseEntity<String>("{ \"status\":\"false\"}", headers, HttpStatus.NOT_FOUND);
		}

		logger.info("------Inside UtilizationDetailReportController getLocation method end------");

		return new ResponseEntity<String>(Location.toJsonArray(locationList), headers, HttpStatus.OK);
	}

	@RequestMapping(value = "getProject/{bids}", method = RequestMethod.GET, headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> getProject(@PathVariable("bids") List<Integer> bids) {

		logger.debug("----------Inside UtilizationDetailReportController start getProject-----------");

		Set<Project> projectList = projectService.findAllProjectByBUIds(bids);

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");

		if (projectList.isEmpty() || projectList == null) {

			return new ResponseEntity<String>("{ \"status\":\"false\"}", headers, HttpStatus.NOT_FOUND);
		}

		logger.info("------Inside UtilizationDetailReportController getProject method end------");

		return new ResponseEntity<String>(Project.toJsonArray(projectList), headers, HttpStatus.OK);
	}

	@RequestMapping(value = "getResource/{bids}/{projIdList}/{locIdList}", method = RequestMethod.GET, headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> getEmployee(@PathVariable("bids") List<Integer> bids, @PathVariable(value = "projIdList") List<Integer> projIdList,@PathVariable("locIdList") List<Integer> locIdList) {

		logger.debug("----------Inside getEmployee UtilizationDetailReportController start getProject-----------");
		JSONObject resultJSON = new JSONObject();
	
		Set<Resource> resourceList  = resourceService.findActiveReourceIdsByBusinessGroupAndProjectId(bids, projIdList,locIdList);


		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");

		if (resourceList.isEmpty() || resourceList == null) {

			return new ResponseEntity<String>("{ \"status\":\"false\"}", headers, HttpStatus.NOT_FOUND);
		}

		logger.info("------Inside UtilizationDetailReportController getProject method end------");
		
		return new ResponseEntity<String>(Resource.toJsonString(resourceList), headers, HttpStatus.OK);
		
	}
			
			
	@RequestMapping(value = "/getCurrentWeekAllocationReport", method = RequestMethod.GET, headers = "Accept=text/html")
	public ModelAndView getUtilizationReportExcel(@RequestParam(value = "endDate", required = false) Date endDate,
			 @RequestParam(value = "orgIdList") List<Integer> buIds,
			@RequestParam(value = "locIdList") List<Integer> locationIds, @RequestParam(value = "projIdList") List<Integer> projIds,
			@RequestParam(value = "ownershipList") List<Integer> ownerShipIds,@RequestParam(value = "resourceBUList") List<Integer> currentBUIds, Model model) {
		if (endDate == null) {
			endDate = DateUtil.getTodaysDate();
		}
		
		Map<String, List<Object []>> cwaMap = currentWeekAllocationReportService.getCurrentWeekAllocationReport(buIds, locationIds, projIds, ownerShipIds, endDate, currentBUIds);
		List<Object[]> inputList = new ArrayList<Object[]>(); 
		Object[] item = {endDate, buIds, locationIds, projIds, ownerShipIds, currentBUIds};
		inputList.add(item);
		cwaMap.put("inputItem", inputList);
		return new ModelAndView("currentWeekAllocationReportExcel", "cwaMap", cwaMap);
	}
}
