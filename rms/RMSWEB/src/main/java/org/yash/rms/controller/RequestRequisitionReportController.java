package org.yash.rms.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;
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
import org.yash.rms.domain.CustomerGroup;
import org.yash.rms.domain.OrgHierarchy;
import org.yash.rms.domain.Resource;
import org.yash.rms.dto.CustomerGroupDTO;
import org.yash.rms.report.dto.RequestRequisitionReport;
import org.yash.rms.report.dto.ResourceRequisitionProfileStatusReport;
import org.yash.rms.service.CustomerGroupService;
import org.yash.rms.service.CustomerService;
import org.yash.rms.service.OrgHierarchyService;
import org.yash.rms.service.RequestRequisitionReportService;
import org.yash.rms.service.RequestRequisitionService;
import org.yash.rms.util.UserUtil;

@Controller
@RequestMapping("/requestRequisitionReport")
public class RequestRequisitionReportController {

	@Autowired
	HttpSession httpSession;

	@Autowired
	@Qualifier("RequestRequisitionReportService")
	RequestRequisitionReportService requestRequisitionReportService;
	
	@Autowired
	@Qualifier("CustomerService")
	CustomerService customerService;

	@Autowired
	private UserUtil userUtil;
	
	@Autowired
	@Qualifier("RequestRequisitionService")
	RequestRequisitionService requestRequisitionService;
	
	@Autowired
	@Qualifier("OrgHierarchyService")
	OrgHierarchyService buService;

	private static final Logger logger = LoggerFactory.getLogger(RequestRequisitionReportController.class);
	List<RequestRequisitionReport> report = new ArrayList<RequestRequisitionReport>();
	List<RequestRequisitionReport> rrReportDBList = new ArrayList<RequestRequisitionReport>();

	@RequestMapping(value = "/")
	public String getReportForm(Model uiModel) throws Exception {
		Resource resource = userUtil.getLoggedInResource();
		logger.debug("----------Inside RequestRequisitionReport start getReportForm-----------");
		try {
			Integer userId = userUtil.getCurrentLoggedInUseId();
			List<OrgHierarchy> allBuTypes = buService.findAllBusForBGADMIN();
			Collections.sort(allBuTypes, new OrgHierarchy.OrgHierarchyComparator());
			
			if (resource.getUploadImage() != null
					&&resource.getUploadImage().length > 0) {
				byte[] encodeBase64UserImage = Base64.encodeBase64(resource.getUploadImage());
				String base64EncodedUser = new String(encodeBase64UserImage, "UTF-8");
				uiModel.addAttribute("UserImage", base64EncodedUser);
			} else {
				uiModel.addAttribute("UserImage", "");
			}
			// customer list 
			uiModel.addAttribute("hiringUnit", allBuTypes);
			uiModel.addAttribute("requestorUnit", allBuTypes);
			uiModel.addAttribute("customerList", requestRequisitionService.getRequestRequisitionsCustomer(userId,resource.getUserRole()));
		} catch (Exception e) {

			e.printStackTrace();
			logger.debug(e.getMessage());
		}

		uiModel.addAttribute("firstName",
				resource.getFirstName() + " " +resource.getLastName());
		uiModel.addAttribute("designationName",resource.getDesignationId().getDesignationName());

		uiModel.addAttribute("ROLE",resource.getUserRole());

		logger.debug("----------Inside RequestRequisitionReport end getReportForm-----------");

		return "report/requestRequisitionReport";
	}

	@RequestMapping(value = "getRequestRequisitionReport", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody ResponseEntity<String> getrequestRequisitionReport(
			 @RequestParam(value = "groupIds") String groupIds,	@RequestParam(value = "customerIds") String customerIds,
			 @RequestParam(value = "statusIds") String statusIds,	 @RequestParam(value = "hiringUnits") String hiringUnits,
			 @RequestParam(value = "reqUnits") String reqUnits, HttpServletRequest request, Model model) throws Exception {

		logger.debug("----------Inside RequestRequisitionReport start getrequestRequisitionReport-----------");

		HttpHeaders headers = new HttpHeaders();

		headers.add("Content-Type", "application/json; charset=utf-8");
		Integer userId = userUtil.getCurrentLoggedInUseId();
		Resource resource = userUtil.getLoggedInResource();

		report = requestRequisitionReportService.getRequestRequisitionReport(userId, resource.getUserRole(),customerIds,
				groupIds, statusIds, true, hiringUnits, reqUnits);
		rrReportDBList=requestRequisitionReportService.getDashBoardDataReport(userId,  resource.getUserRole(), customerIds, 
				groupIds, statusIds,  hiringUnits, reqUnits);

		if ( report == null || report.isEmpty()) {
			return new ResponseEntity<String>("{ \"status\":\"false\"}", headers, HttpStatus.NOT_FOUND);
		}

		logger.debug("----------Inside RequestRequisitionReport End getrequestRequisitionReport-----------");

		return new ResponseEntity<String>(RequestRequisitionReport.toJson(report), headers, HttpStatus.OK);
	}
	
	
	@RequestMapping(value="requestRequisitionExcelReport",method=RequestMethod.GET)
    public ModelAndView getRequestRequisitionExcelReport(
    		@RequestParam(value = "startDate") String startDate, @RequestParam(value = "endDate") String endDate,
    		@RequestParam(value = "groupIds") String groupIds, @RequestParam(value = "customerIds") String customerIds,
    		@RequestParam(value = "statusIds") String statusIds, @RequestParam(value = "reportType") String reportType, HttpServletRequest request, Model model) {
		logger.debug("----------Entered into getRequestRequisitionExcelReport of RequestRequisitionReportController ----------");

		Integer userId = userUtil.getCurrentLoggedInUseId();
		Resource resource = userUtil.getLoggedInResource();

		try{
			model.addAttribute("startDate", startDate);
			model.addAttribute("endDate", endDate);
			model.addAttribute("statusIds", statusIds);
			if (reportType.equalsIgnoreCase("W")) {
				List<ResourceRequisitionProfileStatusReport> profileStatusReportList = requestRequisitionReportService.getResourceRequisitionProfileStatusReport(userId, resource.getUserRole(), startDate, endDate, customerIds);
				System.out.println(" Total Client :: " + profileStatusReportList.size());
				model.addAttribute("profileStatusReportList", profileStatusReportList);
			} 
			else 
			{
				model.addAttribute("rrReportDBList", rrReportDBList);
				model.addAttribute("rrReportList", report);

				logger.debug("----------Exit successfully from getRequestRequisitionExcelReport of RequestRequisitionReportController ----------");
			}
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		if (reportType.equalsIgnoreCase("W")) {
			return new ModelAndView("requestRequisitionExcelWeeklyReportView", "model", model);
		} else if(reportType.equalsIgnoreCase("D")) {
			return new ModelAndView("requestRequisitionExcelReportView","model",model);
		}
		else{
			return new ModelAndView("rrfSubmissionExcelReportView","model",model);
		}
	}
}
