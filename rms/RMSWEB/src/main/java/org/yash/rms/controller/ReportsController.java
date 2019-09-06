package org.yash.rms.controller;

import java.math.BigInteger;
import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
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
import org.yash.rms.domain.CustomerGroup;
import org.yash.rms.domain.Resource;
import org.yash.rms.json.mapper.JsonObjectMapper;
import org.yash.rms.report.dto.PLReport;
import org.yash.rms.service.OrgHierarchyService;
import org.yash.rms.service.ReportService;
import org.yash.rms.service.ResourceService;
import org.yash.rms.util.Constants;
import org.yash.rms.util.UserUtil;

@Controller
@RequestMapping("/reports")
public class ReportsController {
	@Autowired
	ResourceService resourceService;

	@Autowired
	private UserUtil userUtil;
	@Autowired
	JsonObjectMapper<PLReport> mapperPL;
	@Autowired
	@Qualifier("ReportService")
	ReportService reportService;

	@Autowired
	@Qualifier("OrgHierarchyService")
	OrgHierarchyService buService;

	@RequestMapping(method = RequestMethod.GET)
	public String list(Model model) throws Exception {
		
		Resource resource = userUtil.getLoggedInResource();
		model.addAttribute("reportPath", Constants.REPORT_PATH);
		model.addAttribute("reportPathSEPG", Constants.REPORT_PATH_SEPG);
		model.addAttribute("reportPathCMMI", Constants.REPORT_PATH_CMMI);
		Integer currentLoggedInUserId = UserUtil.getCurrentResource()
				.getEmployeeId();
		Resource reportUser = resourceService
				.getReportUserId(currentLoggedInUserId);
		model.addAttribute("reportUser", reportUser.getReportUserId());

		// Set Header values...

		try {

			if (resource.getUploadImage() != null
					&& resource.getUploadImage().length > 0) {
				byte[] encodeBase64UserImage = Base64.encodeBase64(resource.getUploadImage());
				String base64EncodedUser = new String(encodeBase64UserImage,
						"UTF-8");
				model.addAttribute("UserImage", base64EncodedUser);
			} else {
				model.addAttribute("UserImage", "");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		model.addAttribute("firstName", resource
				.getFirstName()
				+ " "
				+ resource.getLastName());
		model.addAttribute("designationName", resource
				.getDesignationId().getDesignationName());
		Calendar cal = Calendar.getInstance();
		cal.setTime(resource.getDateOfJoining());
		int m = cal.get(Calendar.MONTH) + 1;
		String months = new DateFormatSymbols().getMonths()[m - 1];
		int year = cal.get(Calendar.YEAR);
		model.addAttribute("DOJ", months + "-" + year);
		model.addAttribute("ROLE", resource.getUserRole());
		// End

		return "reports/list";
	}
	
	@RequestMapping("/javaReports")
	public String listJavaReports(Model model) throws Exception {
		
		Resource currentLoggedInResource=userUtil.getLoggedInResource();
		model.addAttribute("reportPath", Constants.REPORT_PATH);
		model.addAttribute("reportPathSEPG", Constants.REPORT_PATH_SEPG);
		model.addAttribute("reportPathCMMI", Constants.REPORT_PATH_CMMI);
		Integer currentLoggedInUserId = UserUtil.getCurrentResource()
				.getEmployeeId();
		Resource reportUser = resourceService
				.getReportUserId(currentLoggedInUserId);
		model.addAttribute("reportUser", reportUser.getReportUserId());

		// Set Header values...

		try {

			if (currentLoggedInResource.getUploadImage() != null
					&& currentLoggedInResource.getUploadImage().length > 0) {
				byte[] encodeBase64UserImage = Base64.encodeBase64(currentLoggedInResource.getUploadImage());
				String base64EncodedUser = new String(encodeBase64UserImage,
						"UTF-8");
				model.addAttribute("UserImage", base64EncodedUser);
			} else {
				model.addAttribute("UserImage", "");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		model.addAttribute("firstName", currentLoggedInResource.getFirstName()+ " "+ currentLoggedInResource.getLastName());
		model.addAttribute("designationName", currentLoggedInResource.getDesignationId().getDesignationName());
		
		//ADD CurrentBU, ParentBU, CurrentLocation, ParentLocation
		model.addAttribute("CurrentBU", currentLoggedInResource.getCurrentBuId().getParentId().getName() + "-" + currentLoggedInResource.getCurrentBuId().getName());
		model.addAttribute("ParentBU", currentLoggedInResource.getCurrentBuId().getParentId().getName() + "-" + currentLoggedInResource.getCurrentBuId().getName());
		model.addAttribute("CurrentLocation", currentLoggedInResource.getDeploymentLocation().getLocation());
		model.addAttribute("ParentLocation", currentLoggedInResource.getLocationId().getLocation());

		
		Calendar cal = Calendar.getInstance();
		cal.setTime(currentLoggedInResource.getDateOfJoining());
		int m = cal.get(Calendar.MONTH) + 1;
		String months = new DateFormatSymbols().getMonths()[m - 1];
		int year = cal.get(Calendar.YEAR);
		model.addAttribute("DOJ", months + "-" + year);
		model.addAttribute("ROLE", currentLoggedInResource.getUserRole());
		// End
		//For Get Java Reports
		
		Integer reportCount=reportService.getReportCount();
		model.addAttribute("reportCount",reportCount);
		return "reports/javaReportList";
	}
	
	
	//Update Report Count 
	@RequestMapping(value = "/javaReports/update", method = RequestMethod.GET)
			@ResponseBody
	public boolean updateJavaReportsCount(@RequestParam(value ="countId") Integer countId) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		try {
			 boolean update = reportService.updateJavaReportsCount(countId);

		} catch (Exception exception) {
		}
		return true;
		
	}

	
}
