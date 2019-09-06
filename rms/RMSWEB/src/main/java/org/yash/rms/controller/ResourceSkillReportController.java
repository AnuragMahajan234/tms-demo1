package org.yash.rms.controller;

import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.yash.rms.domain.AllocationType;
import org.yash.rms.domain.OrgHierarchy;
import org.yash.rms.report.dto.ResourceSkillReport;
import org.yash.rms.service.OrgHierarchyService;
import org.yash.rms.service.ResourceSkillReportService;
import org.yash.rms.service.RmsCRUDService;
import org.yash.rms.util.UserUtil;

@Controller
@RequestMapping("/resourceSkillReport")
public class ResourceSkillReportController {
	
	@Autowired
	@Qualifier("OrgHierarchyService")
	OrgHierarchyService buService;
	
	@Autowired
	@Qualifier("allocatioTypeService")
	RmsCRUDService<AllocationType> allocationService;
	
	@Autowired
	@Qualifier("ResourceSkillReportService")
	ResourceSkillReportService resourceSkillReportService;
	
	@Autowired
	UserUtil userUtil;
	
	Set<ResourceSkillReport> resourceSkillReportList = new HashSet<ResourceSkillReport>();
	
	private static final Logger logger = LoggerFactory.getLogger(ResourceSkillReportController.class);
	
	@RequestMapping(value = "/")
	public String getResourceDetail(Model uiModel) {
		logger.debug("----------Inside ResourceSkillReportController::getResourceDetail [START] -----------");
		List<OrgHierarchy> allBuTypes = buService.findAllBusForBGADMIN();

		@SuppressWarnings("unchecked")
		List<AllocationType> allocationType = (List<AllocationType>) (List<?>) allocationService.findAll();

		Collections.sort(allBuTypes, new OrgHierarchy.OrgHierarchyComparator());
		Collections.sort(allocationType, new AllocationType.AllocationTypeComparator());

		uiModel.addAttribute("orgHierarchy", allBuTypes);
		uiModel.addAttribute("AllocationType", allocationType);

		logger.debug("----------Inside ResourceSkillReportController end getResourceMovementReport-----------");
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

		uiModel.addAttribute("firstName", userUtil.getLoggedInResource().getFirstName() + " " + userUtil.getLoggedInResource().getLastName());
		uiModel.addAttribute("designationName", userUtil.getLoggedInResource().getDesignationId().getDesignationName());

		Calendar cal = Calendar.getInstance();
		cal.setTime(userUtil.getLoggedInResource().getDateOfJoining());

		int m = cal.get(Calendar.MONTH) + 1;
		String months = new DateFormatSymbols().getMonths()[m - 1];

		int year = cal.get(Calendar.YEAR);

		uiModel.addAttribute("DOJ", months + "-" + year);
		uiModel.addAttribute("ROLE", userUtil.getLoggedInResource().getUserRole());

		return "report/ResourceSkillReport";
	}
	
	@RequestMapping(value = "getResourceSkillReport", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody ResponseEntity<String> getResourceSkillReport(@RequestParam(value = "orgIdList") List<Integer> orgIdList, 
			@RequestParam(value = "allocationTypeIds") List<Integer> allocationTypeIds, HttpServletRequest request,
			Model model) throws Exception {

		logger.debug("---------- ResourceSkillReportController::getResourceSkillReport[START] -----------");

		HttpHeaders headers = new HttpHeaders();
	
		headers.add("Content-Type", "application/json; charset=utf-8");
		resourceSkillReportList = resourceSkillReportService.getResourceSkillReport(orgIdList, allocationTypeIds);

		if (resourceSkillReportList.isEmpty() || resourceSkillReportList == null) {
			return new ResponseEntity<String>("{ \"status\":\"false\"}", headers, HttpStatus.NOT_FOUND);
		}

		logger.debug("---------- ResourceSkillReportController::getResourceSkillReport[END]-----------");

		return new ResponseEntity<String>(ResourceSkillReport.toJsonArray(resourceSkillReportList), headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/exportToExcel", method = RequestMethod.GET)
	public ModelAndView getRMReportExcel(Model model) {

		model.addAttribute("resourceSkillReportList", resourceSkillReportList);
		return new ModelAndView("resourceSkillReportExcelView", "model", model);

	}


}
