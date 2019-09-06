package org.yash.rms.controller;

import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.yash.rms.domain.AllocationType;
import org.yash.rms.domain.Resource;
import org.yash.rms.report.dto.MuneerReport;
import org.yash.rms.report.dto.PLReport;
import org.yash.rms.service.AllocationTypeService;
import org.yash.rms.service.CustomizeReportService;
import org.yash.rms.service.OrgHierarchyService;
import org.yash.rms.service.RmsCRUDService;
import org.yash.rms.util.UserUtil;

@Controller
@RequestMapping("/customizeReport")
public class CustomizeReportController {

	@Autowired
	HttpSession httpSession;

	@Autowired
	@Qualifier("CustomizeReportService")
	CustomizeReportService customizeReportService;

	@Autowired
	@Qualifier("OrgHierarchyService")
	OrgHierarchyService buService;

	@Autowired
	@Qualifier("allocatioTypeService")
	RmsCRUDService<AllocationType> allocationService;

	@Autowired
	private UserUtil userUtil;

	Map<String, Object> report = new HashMap<String, Object>();

	private static final Logger logger = LoggerFactory.getLogger(CustomizeReportController.class);
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/")
	public String getCustomizedReport(Model uiModel) throws Exception {

		logger.debug("----------Inside CustomizedReportController start getReportForm-----------");
		Resource resource = userUtil.getLoggedInResource();
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
		
		logger.debug("----------Inside CustomizedReportReportsController end getReportForm-----------");

		return "report/CustomizeReport";
	}

	@RequestMapping(value = "/customizeReportExcel", method = RequestMethod.GET)
	public ModelAndView getCustomizeReportExcel(@RequestParam(value = "year") Integer year) {
		
		logger.debug("----------Inside customizeReportController start getCustomizeReportExcel-----------");

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");

		try {

			report = customizeReportService.getResourceForCustomizeReport(year);

		} catch (Exception e) {

			e.printStackTrace();
			logger.debug(e.getMessage());
		}

		logger.debug("----------Inside customizedReportController end getCustomizeReportExcel-----------");
		
		return new ModelAndView("customizeReportExcelView", "report", report);
	}

	
	
}
