package org.yash.rms.controller;

import java.sql.Date;
import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
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
import org.yash.rms.domain.Resource;
import org.yash.rms.dto.CustomerDTO;
import org.yash.rms.report.dto.TimeSheetDetailReport;
import org.yash.rms.service.CustomerService;
import org.yash.rms.service.LocationService;
import org.yash.rms.service.OrgHierarchyService;
import org.yash.rms.service.ProjectService;
import org.yash.rms.service.ResourceService;
import org.yash.rms.service.TimeSheetDetailReportService;
import org.yash.rms.util.Constants;
import org.yash.rms.util.DateUtil;
import org.yash.rms.util.UserUtil;

@Controller
@RequestMapping("/timeSheetReport")
public class TimeSheetReportController {

	private static final Logger LOGGER = LoggerFactory.getLogger(TimeSheetReportController.class);
	
	@Autowired
	@Qualifier("OrgHierarchyService")
	OrgHierarchyService buService;
	
	@Autowired
	UserUtil userUtil;
	
	@Autowired
	@Qualifier("ResourceService")
	private ResourceService resourceService;
	 
	@Autowired
	@Qualifier("CustomerService")
	private CustomerService customerService;

	@Autowired
	@Qualifier("LocationService")
	LocationService locationService;

	@Autowired
	@Qualifier("ProjectService")
	ProjectService projectService;
	
	@Autowired
	@Qualifier("timeSheetDetailReportService")
	TimeSheetDetailReportService timeSheetDetailReportService;
	
	
	@RequestMapping(value = "/")
	public String getTimeSheetReport(Model model) {
		LOGGER.debug("----------Inside ReportsController start getTimeSheetReport-----------");
		Resource resource = userUtil.getLoggedInResource();
		
		List<OrgHierarchy> allBuTypes = buService.findAllBu();

		List<Resource> resourceList = null;
		List<CustomerDTO> customerList = null;
		List<Location> locationList = null;
		List<Project> projectList = null;


		Collections.sort(allBuTypes, new OrgHierarchy.OrgHierarchyComparator());
		model.addAttribute("orgHierarchy", allBuTypes);
		LOGGER.debug("----------Inside ReportsController end getUtilizationReport-----------");
		// Set Header values...

		try {

			if (resource.getUploadImage() != null && resource.getUploadImage().length > 0) {

				byte[] encodeBase64UserImage = Base64.encodeBase64(resource.getUploadImage());

				String base64EncodedUser = new String(encodeBase64UserImage, "UTF-8");

				model.addAttribute("UserImage", base64EncodedUser);

			} else {

				model.addAttribute("UserImage", "");
			}

		} catch (Exception e) {

			e.printStackTrace();
		}
          //resourceList = resourceService.findActiveResources();
          customerList = customerService.findAll();
          locationList = locationService.findAll();
  		  projectList = projectService.findAllActiveProjects();
  		  
  		Collections.sort(locationList, new Location.LocationNameComparator());
  		Collections.sort(projectList, new Project.ProjectNameComparator());

       //   } 
        model.addAttribute(Constants.RESOURCES, resourceList);
        model.addAttribute(Constants.ALL_CUSTOMERS, customerList);
        model.addAttribute(Constants.LOCATION, locationList);
        model.addAttribute(Constants.PROJECTS, projectList);
		model.addAttribute("firstName", resource.getFirstName() + " " + resource.getLastName());
		model.addAttribute("designationName", resource.getDesignationId().getDesignationName());
		Calendar cal = Calendar.getInstance();
		cal.setTime(resource.getDateOfJoining());
		int m = cal.get(Calendar.MONTH) + 1;
		String months = new DateFormatSymbols().getMonths()[m - 1];
		int year = cal.get(Calendar.YEAR);
		model.addAttribute("DOJ", months + "-" + year);
		model.addAttribute("ROLE", resource.getUserRole());
		
		
		return "report/timesheetReport";
	}
	
	@RequestMapping(value = "/getTimeSheetDetailReport", method = RequestMethod.GET, headers = "Accept=text/html")
	public ModelAndView getTimeSheetDetailReport(@RequestParam(value = "endDate", required = false) Date endDate, 
			@RequestParam(value = "exportToExcelRequired") Boolean exportToExcelRequired, @RequestParam(value = "orgIdList") List<Integer> orgIdList,
			@RequestParam(value = "locIdList") List<Integer> locIdList, @RequestParam(value = "projIdList") List<Integer> projIdList,
			@RequestParam(value = "employeeList") List<Integer> employeeList, Model model) {

		List<TimeSheetDetailReport>   timeSheetDetailReportList = new ArrayList<TimeSheetDetailReport>();
		 if (endDate == null) {
			 endDate = DateUtil.getTodaysDate();
		 }
		 
		/* if(employeeList.get(0)== 0){
			 List<Integer> empList= new ArrayList<Integer>();
			 Set<Resource> resourceList  = resourceService.findActiveReourceIdsByBusinessGroupAndProjectId(orgIdList, projIdList,locIdList);
			 for(Resource resource:resourceList){
				 empList.add(resource.getEmployeeId());
			 }
			 
			timeSheetDetailReportList = timeSheetDetailReportService.getTimeSheetDetailReport(orgIdList, locIdList, projIdList, endDate, empList,startDate);	 
		 }*/
		 
		 	java.util.Date startDate = null;
		// else{		 
			 timeSheetDetailReportList = timeSheetDetailReportService.getTimeSheetDetailReport(orgIdList, locIdList, projIdList, endDate, employeeList,startDate);
		// } 
		 
		 
		 model.addAttribute("TimeSheetDetailReportList", timeSheetDetailReportList);
		 model.addAttribute("endDate", endDate);
		 model.addAttribute("startDate", startDate);

		return new ModelAndView("timeSheetDetailReportExcel", "model", model);
	}
	
}
