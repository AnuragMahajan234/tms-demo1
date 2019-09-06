package org.yash.rms.controller;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.json.JSONObject;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.yash.rms.domain.Project;
import org.yash.rms.domain.Resource;
import org.yash.rms.report.dto.ModuleReport;
import org.yash.rms.service.ModuleReportService;
import org.yash.rms.service.ProjectService;
import org.yash.rms.service.ResourceService;
import org.yash.rms.util.UserUtil;

@Controller
@RequestMapping("/moduleReports")
public class ModuleReportController {

	@Autowired
	@Qualifier("ProjectService")
	ProjectService projectService;

	@Autowired
	@Qualifier("moduleReportService")
	ModuleReportService moduleReportService;
	
	@Autowired
	@Qualifier("ResourceService")
	ResourceService resourceService;

	@Autowired
	private UserUtil userUtil;

	private static final Logger logger = LoggerFactory.getLogger(PLReportController.class);

	List<Project> projectList = new ArrayList<Project>();

	Map<String, Map<String, List<ModuleReport>>> moduleReport = new HashMap<String, Map<String, List<ModuleReport>>>();

	@RequestMapping(value = "/")
	public String getReportForm(Model uiModel) throws Exception {
		Resource resource = resourceService.getCurrentResource(userUtil.getLoggedInResource().getUserName()) ;
		try {
			
			if (resource.getUploadImage() != null && resource.getUploadImage().length > 0) {
				byte[] encodeBase64UserImage = Base64.encodeBase64(resource.getUploadImage());
				String base64EncodedUser = new String(encodeBase64UserImage, "UTF-8");
				uiModel.addAttribute("UserImage", base64EncodedUser);
			} else {
				uiModel.addAttribute("UserImage", "");
			}

			
			boolean isCurrentUserAdmin = UserUtil.isCurrentUserIsAdmin();

			if (isCurrentUserAdmin) {
				projectList = projectService.findAll();
			} else {
				projectList = projectService.findAllProjectByBUid(resource.getBuId().getId());
			}
				//For Prachi Rao, mayank and Zoaib(Indore)
			if ((resource.getEmployeeId() == 454 || resource.getEmployeeId() == 4334 || resource.getEmployeeId() == 627 )) {
				      
			  projectList.add(projectService.findProject(985));
			  projectList.add(projectService.findProject(859));
			  projectList.add(projectService.findProject(982));
			  projectList.add(projectService.findProject(852));
			  projectList.add(projectService.findProject(992));
			  projectList.add(projectService.findProject(1061));
			  projectList.add(projectService.findProject(854));
			  projectList.add(projectService.findProject(976));
			  projectList.add(projectService.findProject(1058));
			  projectList.add(projectService.findProject(1023));
			  projectList.add(projectService.findProject(1018));
			  projectList.add(projectService.findProject(1049));
			  projectList.add(projectService.findProject(1091));
			  projectList.add(projectService.findProject(1380));
			  projectList.add(projectService.findProject(1379));
			}
			
			uiModel.addAttribute("projectList", projectList);

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

		return "reports/moduleReport";
	}

	@RequestMapping(value = "/{projectId}", params = "find=getModuleReport", headers = "Accept=application/json")
	public ResponseEntity<String> getModuleReport(@PathVariable("projectId") List<Integer> projectId,
			// @RequestParam(value = "teamId") List<Integer> teamId,
			@RequestParam("weekStartDate") @DateTimeFormat(style = "M-") Date weekStartDate,
			@RequestParam("weekEndDate") @DateTimeFormat(style = "M-") Date weekEndDate, HttpServletRequest request, Model uiModel) throws Exception {

		logger.debug("----------Inside ModuleReportController start getModuleReport-----------");

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");

		try {

			moduleReport = moduleReportService.getModuleReport(projectId, weekStartDate, weekEndDate);

		} catch (Exception e) {
			e.printStackTrace();
		}

		JSONObject jsonObject = new JSONObject(moduleReport);

		logger.debug("----------Inside ModuleReportController end getModuleReport-----------");

		return new ResponseEntity<String>(jsonObject.toString(), headers, HttpStatus.OK);

	}

	@RequestMapping(value = "/exportToExcel", method = RequestMethod.GET)
	public ModelAndView getExcel(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "plannedHours") boolean plannedHours, @RequestParam(value = "billedHours") boolean billedHours,
			@RequestParam(value = "productiveHours") boolean productiveHours, @RequestParam(value = "nonProductiveHours") boolean nonProductiveHours) {

		request.setAttribute("plannedHours", plannedHours);
		request.setAttribute("billedHours", billedHours);
		request.setAttribute("productiveHours", productiveHours);
		request.setAttribute("nonProductiveHours", nonProductiveHours);

		return new ModelAndView("moduleExcelView", "moduleReportsList", moduleReport);

	}
	
	@RequestMapping(value = "/customModuleReport/projectIds/{projectIds}", headers = "Accept=application/json", method = RequestMethod.GET)
	public String getCustomizedModuleReport(@PathVariable("projectIds") List<Integer> projectIds, @RequestParam("weekStartDate") @DateTimeFormat(style = "M-") Date weekStartDate, @RequestParam("weekEndDate") @DateTimeFormat(style = "M-") Date weekEndDate, HttpServletRequest request, Model uiModel) throws Exception {

		logger.debug("----------Inside ModuleReportController start getCustomizedModuleReport-----------");

		try {

			List<ModuleReport> moduleReportList = moduleReportService.getCustomizedModuleReport(projectIds, weekStartDate, weekEndDate);

			uiModel.addAttribute("moduleReportList", moduleReportList);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.debug("----------Inside ModuleReportController end getCustomizedModuleReport-----------");

		return "reports/customizedModuleReport";
	}

}
