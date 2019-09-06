package org.yash.rms.controller;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.yash.rms.domain.OrgHierarchy;
import org.yash.rms.domain.Resource;
import org.yash.rms.service.OrgHierarchyService;
import org.yash.rms.service.RMGReportService;
import org.yash.rms.service.RequestRequisitionReportService;
import org.yash.rms.service.ResourceService;
import org.yash.rms.util.UserUtil;

@Controller
@RequestMapping("/rmgReport")
public class RMGReportController {
	//for get resourceDetails
	@Autowired
	@Qualifier("ResourceService")
	ResourceService resourceService;
	
	@Autowired
	HttpSession httpSession;

	@Autowired
	@Qualifier("RequestRequisitionReportService")
	RequestRequisitionReportService requestRequisitionReportService;
	
	@Autowired
	@Qualifier("OrgHierarchyService")
	OrgHierarchyService buService;

	@Autowired
	private UserUtil userUtil;
	
	@Autowired
	RMGReportService rmgReportService;

	private static final Logger logger = LoggerFactory.getLogger(RMGReportController.class);

	@RequestMapping(value = "/")
	public String getReportForm(Model uiModel) throws Exception {

		logger.debug("----------Inside RMGReportController getReportForm START-----------");
		
		Resource currentLoggedInResource=userUtil.getLoggedInResource();
		List<OrgHierarchy> allBuTypes = buService.findAllBusForBGADMIN();
		uiModel.addAttribute("orgHierarchyList", allBuTypes);
		
		Collections.sort(allBuTypes, new OrgHierarchy.OrgHierarchyComparator());
		
		try {
			
			if (currentLoggedInResource.getUploadImage() != null && currentLoggedInResource.getUploadImage().length > 0) {

				byte[] encodeBase64UserImage = Base64.encodeBase64(currentLoggedInResource.getUploadImage());

				String base64EncodedUser = new String(encodeBase64UserImage, "UTF-8");

				uiModel.addAttribute("UserImage", base64EncodedUser);
				
			} else {

				uiModel.addAttribute("UserImage", "");
			}

		} catch (Exception e) {

			e.printStackTrace();
			logger.debug(e.getMessage());
		}

		uiModel.addAttribute("firstName", currentLoggedInResource.getFirstName() + " " + currentLoggedInResource.getLastName());
		uiModel.addAttribute("designationName", currentLoggedInResource.getDesignationId().getDesignationName());

		uiModel.addAttribute("ROLE", currentLoggedInResource.getUserRole());

		logger.debug("----------Inside RMGReportController getReportForm END-----------");

		return "report/rmgReport";

	}
	
	@RequestMapping(value = "/showPDFReport")
	public ModelAndView saveReportPDF(Model model, @RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate, @RequestParam("selectedBG_BU") List<String> selectedBG_BU) {
	
		String fileName = "RequestReport_" + new Timestamp(new Date().getTime());
		
		String startDateArray[] = startDate.split("-");
		String endDateArray[] = endDate.split("-");
		
		String startDay = startDateArray[0];
		String endDay = endDateArray[0];
		String startMonth = startDateArray[1];
		String endMonth = endDateArray[1];
		String startYear = startDateArray[2];
		String endYear = endDateArray[2];
		
		Map<String, Object> modelMap = new HashMap<String, Object>();
			modelMap.put("resourceMetricsList", rmgReportService.getResourceMetricsTable(startDay, endDay, startMonth, endMonth, startYear, endYear));
			modelMap.put("RRFMetricsList", rmgReportService.getRRFMetricsTable(startDay, endDay, startMonth, endMonth, startYear, endYear));
//			modelMap.put("resourceMetricsByBGList", rmgReportService.getResourceMetricsByBGTable(startDay, endDay, startMonth, endMonth, startYear, endYear));
			modelMap.put("resourceMetricsByBGBUList", rmgReportService.getResourceMetricsByBGBUTable(startDay, endDay, startMonth, endMonth, startYear, endYear));
			modelMap.put("gradewiseReportList", rmgReportService.getGradewiseReportTable(startDay, endDay, startMonth, endMonth, startYear, endYear));
			modelMap.put("BGBUMetricesList", rmgReportService.getBGBUMatricesTable(startDay, endDay, startMonth, endMonth, startYear, endYear));
			modelMap.put("benchGradeReportList", rmgReportService.getBenchGradeReportTable(startDay, endDay, startMonth, endMonth, startYear, endYear));
			modelMap.put("benchGradeDaysWiseReportList", rmgReportService.getBenchGradeDaysWiseReportTable(startDay, endDay, startMonth, endMonth, startYear, endYear));
			modelMap.put("benchSkillReportList", rmgReportService.getBenchSkillReportTable(startDay, endDay, startMonth, endMonth, startYear, endYear));
			modelMap.put("projectsClosingIn_3_MonthsReportList", rmgReportService.getProjectsClosingIn_3_MonthsReportTable(startDay, endDay, startMonth, endMonth, startYear, endYear));
			modelMap.put("skillsReleasingIn_3_MonthsReportList", rmgReportService.getSkillsReleasingIn_3_MonthsReportTable(startDay, endDay, startMonth, endMonth, startYear, endYear));
			modelMap.put("fileName", fileName);
			modelMap.put("startDate", startDate);
			modelMap.put("endDate", endDate);
			modelMap.put("year", startYear);
			modelMap.put("monthName", startMonth);
			modelMap.put("selectedBG_BU", selectedBG_BU);
		
		return new ModelAndView("pdfRMGReportView", modelMap);
	}

}
