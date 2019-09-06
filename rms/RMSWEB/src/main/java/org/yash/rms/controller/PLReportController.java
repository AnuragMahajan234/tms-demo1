package org.yash.rms.controller;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;
import org.apache.poi.ss.formula.functions.T;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.yash.rms.domain.AllocationType;
import org.yash.rms.domain.OrgHierarchy;
import org.yash.rms.domain.Resource;
import org.yash.rms.report.dto.PLReport;
import org.yash.rms.service.OrgHierarchyService;
import org.yash.rms.service.ReportService;
import org.yash.rms.service.RmsCRUDService;
import org.yash.rms.util.UserUtil;

@Controller
@RequestMapping("/plReports")
public class PLReportController {

	@Autowired
	HttpSession httpSession;

	@Autowired
	@Qualifier("ReportService")
	ReportService reportService;

	@Autowired
	@Qualifier("OrgHierarchyService")
	OrgHierarchyService buService;

	@Autowired
	@Qualifier("allocatioTypeService")
	RmsCRUDService<AllocationType> allocationService;

	@Autowired
	private UserUtil userUtil;

	Map<String, Object> report = new HashMap<String, Object>();

	private static final Logger logger = LoggerFactory.getLogger(PLReportController.class);

	@RequestMapping(value = "getPLReport", produces = "text/html")
	public ResponseEntity<String> getPLReport(@RequestParam(value = "buid") List<Integer> buId, @RequestParam("startDate") @DateTimeFormat(style = "M-") Date startDate,
			@RequestParam("endDate") @DateTimeFormat(style = "M-") Date endDate, @RequestParam(value = "projectTypeId") Integer projectTypeId, 
			@RequestParam(value = "includeResource") boolean  includeResource,
			HttpServletRequest request, Model uiModel) throws Exception {

		logger.debug("----------Inside PLReportController start getPLReport----------- includeResource " + includeResource);

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");

		try {

			report = reportService.getPLReport(buId, new Date(startDate.getTime()), new Date(endDate.getTime()), projectTypeId, includeResource);

		} catch (Exception e) {

			e.printStackTrace();
			logger.debug(e.getMessage());
		}

		logger.debug("----------Inside PLReportController end getPLReport-----------");

		return new ResponseEntity<String>(PLReport.toJsonArray(report), headers, HttpStatus.OK);

	}

	@RequestMapping(value = "/plReportExcel", method = RequestMethod.GET)
	public ModelAndView getPLReportInExcel() {

		return new ModelAndView("plExcelView", "report", report);

	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/")
	public String getReportForm(Model uiModel) throws Exception {

		logger.debug("----------Inside PLReportsController start getReportForm-----------");
		Resource resource = userUtil.getLoggedInResource();
		List<OrgHierarchy> allBuTypes = buService.findAllBusForBGADMIN();

		List<AllocationType> allocationType = (List<AllocationType>) (List<?>) allocationService.findAll();

		Collections.sort(allBuTypes, new OrgHierarchy.OrgHierarchyComparator());
		Collections.sort(allocationType, new AllocationType.AllocationTypeComparator());

		uiModel.addAttribute("orgHierarchy", allBuTypes);
		uiModel.addAttribute("AllocationType", allocationType);

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
			logger.debug(e.getMessage());
		}

		uiModel.addAttribute("firstName", resource.getFirstName() + " " + resource.getLastName());
		uiModel.addAttribute("designationName", resource.getDesignationId().getDesignationName());

		uiModel.addAttribute("ROLE", resource.getUserRole());

		logger.debug("----------Inside PLReportsController end getReportForm-----------");

		return "reports/PLReport";

	}

}
