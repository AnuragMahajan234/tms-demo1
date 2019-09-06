package org.yash.rms.controller;

import java.sql.Date;
//import java.util.Date;
import java.text.DateFormatSymbols;
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
import org.yash.rms.domain.Project;
import org.yash.rms.domain.Resource;
import org.yash.rms.dto.CustomerDTO;
import org.yash.rms.report.dto.UtilizationDetailReport;
import org.yash.rms.report.dto.WeeklyData;
import org.yash.rms.service.CustomerService;
import org.yash.rms.service.LocationService;
import org.yash.rms.service.OrgHierarchyService;
import org.yash.rms.service.ProjectService;
import org.yash.rms.service.ResourceService;
import org.yash.rms.service.UtilizationDetailReportService;
import org.yash.rms.util.Constants;
import org.yash.rms.util.DateUtil;
import org.yash.rms.util.UserUtil;

@Controller
@RequestMapping("/UtilizationReport")
public class UtilizationDetailReportController {
	

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
	@Qualifier("utilizationDetailReportService")
	UtilizationDetailReportService utilizationDetailReportService;
	
	


	private static final Logger logger = LoggerFactory.getLogger(UtilizationDetailReportController.class);

	@RequestMapping(value = "/")
	public String getUtilizationReport(Model uiModel) {

		logger.debug("----------Inside ReportsController start getUtilizationReport-----------");

		List<OrgHierarchy> allBuTypes = buService.findAllBu();

		List<Resource> resourceList = null;
		List<CustomerDTO> customerList = null;
		List<Location> locationList = null;
		List<Project> projectList = null;


		Collections.sort(allBuTypes, new OrgHierarchy.OrgHierarchyComparator());
		//Collections.sort(allocationType, new AllocationType.AllocationTypeComparator());

		uiModel.addAttribute("orgHierarchy", allBuTypes);
		//uiModel.addAttribute("AllocationType", allocationType);

		logger.debug("----------Inside ReportsController end getUtilizationReport-----------");
		// Set Header values...

		try {

			if (userUtil.getLoggedInResource().getUploadImage() != null && userUtil.getLoggedInResource().getUploadImage().length > 0) {

				byte[] encodeBase64UserImage = Base64.encodeBase64(userUtil.getLoggedInResource().getUploadImage());

				String base64EncodedUser = new String(encodeBase64UserImage, "UTF-8");

				uiModel.addAttribute("UserImage", base64EncodedUser);

			} else {

				uiModel.addAttribute("UserImage", "");
			}

		} catch (Exception e) {

			e.printStackTrace();
		}
		//  boolean isCurrentUserAdmin = UserUtil.isCurrentUserIsAdmin();
         // if (isCurrentUserAdmin) {
          resourceList = resourceService.findActiveResources();
          customerList = customerService.findAll();
          locationList = locationService.findAll();
  		  projectList = projectService.findAllActiveProjects();
  		  
  		//Collections.sort(resourceList, new Resource().);
  		//Collections.sort(customerList, new Customer().);
  		Collections.sort(locationList, new Location.LocationNameComparator());
  		Collections.sort(projectList, new Project.ProjectNameComparator());

       //   } 
        uiModel.addAttribute(Constants.RESOURCES, resourceList);
        uiModel.addAttribute(Constants.ALL_CUSTOMERS, customerList);
        uiModel.addAttribute(Constants.LOCATION, locationList);
        uiModel.addAttribute(Constants.PROJECTS, projectList);
		uiModel.addAttribute("firstName", userUtil.getLoggedInResource().getFirstName() + " " + userUtil.getLoggedInResource().getLastName());
		uiModel.addAttribute("designationName", userUtil.getLoggedInResource().getDesignationId().getDesignationName());
		Calendar cal = Calendar.getInstance();
		cal.setTime(userUtil.getLoggedInResource().getDateOfJoining());
		int m = cal.get(Calendar.MONTH) + 1;
		String months = new DateFormatSymbols().getMonths()[m - 1];
		int year = cal.get(Calendar.YEAR);
		uiModel.addAttribute("DOJ", months + "-" + year);
		uiModel.addAttribute("ROLE", userUtil.getLoggedInResource().getUserRole());
		
		return "report/UtilizationReport";
	}
	
	
	// new customized utilization details reports
	
	
	@RequestMapping(value = "/Customized")
	public String getCustomizedUtilizationReport(Model uiModel) {
		Resource resource = userUtil.getLoggedInResource();
		logger.debug("----------Inside ReportsController start getUtilizationReport-----------");

		List<OrgHierarchy> allBuTypes = buService.findAllBu();

		List<Resource> resourceList = null;
		List<CustomerDTO> customerList = null;
		List<Location> locationList = null;
		List<Project> projectList = null;


		Collections.sort(allBuTypes, new OrgHierarchy.OrgHierarchyComparator());
		//Collections.sort(allocationType, new AllocationType.AllocationTypeComparator());

		uiModel.addAttribute("orgHierarchy", allBuTypes);
		//uiModel.addAttribute("AllocationType", allocationType);

		logger.debug("----------Inside ReportsController end getUtilizationReport-----------");
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
		//  boolean isCurrentUserAdmin = UserUtil.isCurrentUserIsAdmin();
         // if (isCurrentUserAdmin) {
          resourceList = resourceService.findActiveResources();
          customerList = customerService.findAll();
          locationList = locationService.findAll();
  		  projectList = projectService.findAllActiveProjects();
  		  
  		//Collections.sort(resourceList, new Resource().);
  		//Collections.sort(customerList, new Customer().);
  		Collections.sort(locationList, new Location.LocationNameComparator());
  		Collections.sort(projectList, new Project.ProjectNameComparator());

       //   } 
        uiModel.addAttribute(Constants.RESOURCES, resourceList);
        uiModel.addAttribute(Constants.ALL_CUSTOMERS, customerList);
        uiModel.addAttribute(Constants.LOCATION, locationList);
        uiModel.addAttribute(Constants.PROJECTS, projectList);
		uiModel.addAttribute("firstName", resource.getFirstName() + " " + resource.getLastName());
		uiModel.addAttribute("designationName", resource.getDesignationId().getDesignationName());
		Calendar cal = Calendar.getInstance();
		cal.setTime(resource.getDateOfJoining());
		int m = cal.get(Calendar.MONTH) + 1;
		String months = new DateFormatSymbols().getMonths()[m - 1];
		int year = cal.get(Calendar.YEAR);
		uiModel.addAttribute("DOJ", months + "-" + year);
		uiModel.addAttribute("ROLE", resource.getUserRole());
		
		return "report/UtilizationReport/Customized";
	}
	
	
	// end 
	
	
	
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

		Set<Location> locationList = resourceService.getLocationListByBusinessGroupAndProjectIds(ids);

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
			
			
	@RequestMapping(value = "getUtilizationReport", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody ResponseEntity<String> getUtilizationReport(@RequestParam(value = "orgIdList") List<Integer> orgIdList, @RequestParam(value = "locIdList") List<Integer> locIdList,
			@RequestParam(value = "projIdList") List<Integer> projIdList, @RequestParam(value = "customerList") List<Integer> customerList,  @RequestParam(value = "date") Date startDate, @RequestParam(value = "employeeList") List<Integer> employeeList,
			@RequestParam(value = "endDate", required = false ) Date endDate, HttpServletRequest request, Model model) throws Exception {

		logger.debug("----------Inside UtilizationDetailReportController start getUtilizationReport-----------");

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");

		if (httpSession != null) {

			httpSession = null;
			httpSession = request.getSession(true);
			//httpSession.setAttribute("RMReport", UtilizationReportList);

		} else {

			httpSession = request.getSession(true);
			//httpSession.setAttribute("RMReport", UtilizationReportList);
		}

		logger.debug("----------Inside UtilizationDetailReportController End getUtilizationReport-----------");

		//return new ResponseEntity<String>(UtilizationDetailReport.toJsonArray(UtilizationDetailReportList), headers, HttpStatus.OK);
		return new ResponseEntity<String>("{ \"status\":\"false\"}", headers, HttpStatus.ACCEPTED);
	}

	@RequestMapping(value = "/getUtilizationDetailReport", method = RequestMethod.GET, headers = "Accept=text/html")
	public ModelAndView getUtilizationReportExcel(@RequestParam(value = "endDate", required = false) Date endDate, @RequestParam(value = "startDate") Date startDate,
			@RequestParam(value = "exportToExcelRequired") Boolean exportToExcelRequired, @RequestParam(value = "orgIdList") List<Integer> orgIdList,
			@RequestParam(value = "locIdList") List<Integer> locIdList, @RequestParam(value = "projIdList") List<Integer> projIdList,
			@RequestParam(value = "employeeList") List<Integer> employeeList, Model model) {

		 Map<String, TreeMap<java.util.Date, WeeklyData>>   utilizationDetailReportList = new TreeMap<String, TreeMap<java.util.Date, WeeklyData>>();
		 if (endDate == null) {
			   endDate = DateUtil.getTodaysDate();
			  }
		 
		 if(employeeList.get(0)== 0){
			
			 List<Integer> empList= resourceService.findActiveReourceIdsByPojectId(projIdList);
			utilizationDetailReportList = utilizationDetailReportService.getUtilizationDetailReport(orgIdList, locIdList, projIdList, startDate, endDate, empList);	 
		 }
		 
		 else{		 
			 utilizationDetailReportList = utilizationDetailReportService.getUtilizationDetailReport(orgIdList, locIdList, projIdList, startDate, endDate, employeeList);
		 } 
		 
		model.addAttribute("UtilizationDetailReportList", utilizationDetailReportList);
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);

		return new ModelAndView("utilizationDetailReportExcel", "model", model);
		
	}
	
	@RequestMapping(value = "/getCustUtilizationDetailReport", method = RequestMethod.GET, headers = "Accept=text/html")
	public ModelAndView getCustUtilizationDetailReport(@RequestParam(value = "endDate", required = false) Date endDate, @RequestParam(value = "startDate") Date startDate,
			@RequestParam(value = "exportToExcelRequired") Boolean exportToExcelRequired, @RequestParam(value = "orgIdList") List<Integer> orgIdList,
			@RequestParam(value = "locIdList") List<Integer> locIdList, @RequestParam(value = "projIdList") List<Integer> projIdList,
			@RequestParam(value = "employeeList") List<Integer> employeeList, Model model) {

		 Map<String, TreeMap<java.util.Date, WeeklyData>>   utilizationDetailReportList = new TreeMap<String, TreeMap<java.util.Date, WeeklyData>>();
		 if (endDate == null) {
			   endDate = DateUtil.getTodaysDate();
			  }
		 
		 if(employeeList.get(0)== 0){
			
			 List<Integer> empList= resourceService.findActiveReourceIdsByPojectId(projIdList);
			utilizationDetailReportList = utilizationDetailReportService.getUtilizationDetailReport(orgIdList, locIdList, projIdList, startDate, endDate, empList);	 
		 }
		 
		 else{		 
			 utilizationDetailReportList = utilizationDetailReportService.getUtilizationDetailReport(orgIdList, locIdList, projIdList, startDate, endDate, employeeList);
		 } 
		 
		model.addAttribute("UtilizationDetailReportList", utilizationDetailReportList);
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		return new ModelAndView("customizedUtilizationDetailReportExcel", "model", model);
		
	}
	
}