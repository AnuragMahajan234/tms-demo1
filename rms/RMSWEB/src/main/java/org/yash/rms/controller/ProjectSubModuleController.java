package org.yash.rms.controller;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.apache.poi.ss.formula.functions.T;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.yash.rms.domain.AllocationType;
import org.yash.rms.domain.OrgHierarchy;
import org.yash.rms.domain.ProjectSubModule;
import org.yash.rms.domain.Resource;
import org.yash.rms.dto.ProjectSubModuleActiveInActiveListDTO;
import org.yash.rms.dto.ProjectSubModuleDTO;
import org.yash.rms.json.mapper.JsonObjectMapper;
import org.yash.rms.json.mapper.ProjectSubModuleMapper;
import org.yash.rms.service.OrgHierarchyService;
import org.yash.rms.service.ProjectAllocationService;
import org.yash.rms.service.ProjectSubModuleService;
import org.yash.rms.service.ResourceService;
import org.yash.rms.service.RmsCRUDService;
import org.yash.rms.util.Constants;
import org.yash.rms.util.ProjectSubModuleSearchCriteria;
import org.yash.rms.util.UserUtil;

@Controller
@RequestMapping("/subModules")
public class ProjectSubModuleController {

	@Autowired
	private JsonObjectMapper<ProjectSubModuleDTO> jsonMapper;
	@Autowired
	private ProjectSubModuleMapper projectSubModuleMapper;

	@Autowired
	@Qualifier("ProjectAllocationService")
	private ProjectAllocationService projectAllocationService;

	@Autowired
	@Qualifier("projectSubModuleService")
	private ProjectSubModuleService projectSubModuleService;

	@Autowired
	@Qualifier("ResourceService")
	private ResourceService resourceServiceImpl;

	@Autowired
	private UserUtil userUtil;
	
	@Autowired
	@Qualifier("OrgHierarchyService")
	OrgHierarchyService buService;

	@Autowired
	@Qualifier("allocatioTypeService")
	RmsCRUDService<AllocationType> allocationService;

	private static final Logger logger = LoggerFactory.getLogger(ProjectSubModuleController.class);

	@RequestMapping(value = "/listModule", method = RequestMethod.GET)
	public String listModules(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) throws Exception {

		logger.debug("----------Inside ProjectSubModuleController start getReportForm-----------");
		Resource resource = userUtil.getLoggedInResource();

		// Set Header values...

		try {

			if (resource.getUploadImage() != null && resource.getUploadImage().length > 0) {

				byte[] encodeBase64UserImage = Base64.encodeBase64(resource.getUploadImage());

				String base64EncodedUser = new String(encodeBase64UserImage, "UTF-8");

				uiModel.addAttribute("UserImage", base64EncodedUser);

			} else {

				uiModel.addAttribute("UserImage", "");
			}

		} catch (Exception exception) {

			logger.error(
		            "RuntimeException occured in listProjects method of module controller:" + exception);
		        throw exception;
		}

		 uiModel.addAttribute("firstName", resource.getFirstName() + " "
		          + resource.getLastName());
		      uiModel.addAttribute("designationName",
		          resource.getDesignationId().getDesignationName());
		      Calendar cal = Calendar.getInstance();
		      cal.setTime(resource.getDateOfJoining());
		      int m = cal.get(Calendar.MONTH) + 1;
		      String months = new DateFormatSymbols().getMonths()[m - 1];
		      int year = cal.get(Calendar.YEAR);
		      uiModel.addAttribute("DOJ", months + "-" + year);
		      uiModel.addAttribute("ROLE", resource.getUserRole());
		      // End
		logger.debug("----------Inside PLReportsController end getReportForm-----------");

		return "projectSubmodule/ProjectSubModules";
	}

	@RequestMapping(value = "/list/{status}", method = RequestMethod.GET, headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> listModule(@PathVariable("status") String status, Model uiModel, HttpServletRequest request) throws Exception {

		logger.info("------ProjectAllocationController list method start------");
		long totalCount = 0;
		Integer page = Integer.parseInt(request.getParameter("iDisplayStart"));
		Integer size = Integer.parseInt(request.getParameter("iDisplayLength"));
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		String iSortCol = request.getParameter("iSortCol_0");

        // for sorting direction
        String sSortDir = request.getParameter("sSortDir_0");
        String columnName;

        Integer iColumns = Integer.parseInt(request.getParameter("iColumns"));
        ProjectSubModuleSearchCriteria projectSubModuleSearchCriteria = new ProjectSubModuleSearchCriteria();
        String[] searchColumns = projectSubModuleSearchCriteria.getCols();
        String sSearch = "";

        if ((iSortCol != null || iSortCol != "") && (sSortDir != null || sSortDir != "")) {
            projectSubModuleSearchCriteria.setOrderStyle(sSortDir);

            projectSubModuleSearchCriteria.setOrederBy(projectSubModuleSearchCriteria.getCols()[Integer.parseInt(iSortCol)]);

            projectSubModuleSearchCriteria.setSortTableName(projectSubModuleSearchCriteria.getColandTableNameMap().get(projectSubModuleSearchCriteria.getCols()[Integer.parseInt(iSortCol)]));

            projectSubModuleSearchCriteria.setRefTableName(projectSubModuleSearchCriteria.getColandTableNameMap().get(projectSubModuleSearchCriteria.getCols()[Integer.parseInt(iSortCol)]));

            projectSubModuleSearchCriteria.setRefColName(projectSubModuleSearchCriteria.getCols()[Integer.parseInt(iSortCol)]);
        }

        for (int i = 0; i < iColumns; i++) {
            sSearch = request.getParameter("sSearch_" + i);
            if (sSearch.length() > 0) {
                columnName = searchColumns[i];
                projectSubModuleSearchCriteria.setRefTableName(projectSubModuleSearchCriteria.getColandTableNameMap().get(columnName));
                projectSubModuleSearchCriteria.setRefColName(columnName);
                projectSubModuleSearchCriteria.setSearchValue(sSearch);
                break;
            }
        }
		JSONObject resultJSON = new JSONObject();
		try {
		    List<ProjectSubModule> projectSubModuleList = projectSubModuleService.findActiveModuleList(page, size, projectSubModuleSearchCriteria, status, sSearch);
		    if (sSearch.length() > 0) {
		      totalCount = projectSubModuleService.totalCountOfSubModule(status, projectSubModuleSearchCriteria, sSearch);
		    }
		    else
		    totalCount = projectSubModuleService.totalCountOfSubModule(status);
			resultJSON.put("aaData", projectSubModuleMapper.toJsonArray(projectSubModuleList));
			resultJSON.put("iTotalRecords", totalCount);
			resultJSON.put("iTotalDisplayRecords", totalCount);
		} catch (Exception e) {

		}

		return new ResponseEntity<String>(resultJSON.toString(), headers, HttpStatus.OK);

	}

	@RequestMapping(value = "subModules/customProjectSubmodules", produces = "text/html")
	public String customProjectSubModules(@RequestParam("moduleId") Integer moduleId, Model uiModel) throws Exception {
		logger.info("------SubModuleController list method start------");
		try {
			uiModel.addAttribute(Constants.ACTIVITYS, projectSubModuleService.findAllProjectSubModule(moduleId));
		} catch (RuntimeException runtimeException) {
			logger.error("RuntimeException occured in list method of UserActivityController controller:" + runtimeException);
			throw runtimeException;
		} catch (Exception exception) {
			logger.error("Exception occured in list method of UserActivityController controller:" + exception);
			throw exception;
		}
		logger.info("------SubModuleController list method end------");
		return "projectSubmodule/CustomProjectSubModule";
	}

	@RequestMapping(value = "subModules/populateActiveInActiveSubModules/{moduleId}", method = RequestMethod.GET, headers = "Accept=application/json", produces = "application/json")
	@ResponseBody
	public ResponseEntity<String> populateActiveInActiveSubModules(@PathVariable("moduleId") Integer moduleId) throws Exception {
		logger.info("------ModuleController showInactiveActivities method start------");
		List<ProjectSubModuleActiveInActiveListDTO> projectSubModulesDTO = projectSubModuleService.findAllProjectSubModulesByModuleId(moduleId);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");

		try {
			if (projectSubModulesDTO == null) {
				return new ResponseEntity<String>("{ \"status\":\"false\"}", headers, HttpStatus.NOT_FOUND);
			}
		} catch (RuntimeException runtimeException) {
			logger.error("RuntimeException occured in populateActiveModules method of ModuleController:" + runtimeException);
			throw runtimeException;
		} catch (Exception exception) {
			logger.error("Exception occured in populateActiveModules method of ModuleController:" + exception);
			throw exception;
		}
		logger.info("------ModuleController showInactiveActivities method end------");

		String projectSubModuleList = projectSubModuleMapper.objectToJsonForDTO(projectSubModulesDTO);
		return new ResponseEntity<String>(projectSubModuleList, headers, HttpStatus.OK);
	}

	@RequestMapping(value = "projectSubModule/save", method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<String> createProjectModuleFromJson(@RequestBody String json, HttpServletRequest request) throws Exception {
		
		logger.info("------ModuleController createFromJson method start------");
		
		ServletContext context = request.getSession().getServletContext();
		context.getAttribute("userName");
		ProjectSubModule projectSubModule = projectSubModuleMapper.fromJsonToObjectDomain(json, ProjectSubModule.class);


		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		try {
			if (!projectSubModuleService.saveOrupdate(projectSubModule)) {
				return new ResponseEntity<String>("{ \"status\":\"false\"}", headers, HttpStatus.EXPECTATION_FAILED);
			}
		} catch (RuntimeException runtimeException) {
			logger.error("RuntimeException occured in createProjectModuleFromJson method of Module Controller:" + runtimeException);
			throw runtimeException;

		} catch (Exception exception) {
			logger.error("Exception occured in createProjectModuleFromJson method of Module Controller:" + exception);
			throw exception;
		}
		logger.info("------ModuleController createFromJson method end------");
		return new ResponseEntity<String>("{ \"status\":\"true\"}", headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "projectSubModules/update", method = RequestMethod.PUT, headers = "Accept=application/json")
	public ResponseEntity<String> updateFromJson(@RequestBody String json, HttpServletRequest request) throws Exception {
		
		logger.info("------Module controller updateFromJson method start------");
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		ServletContext context = request.getSession().getServletContext();
		context.getAttribute("userName");
		
		ProjectSubModule projectSubModule = projectSubModuleMapper.fromJsonToObjectDomain(json, ProjectSubModule.class);
		
		try {
			if (!projectSubModuleService.saveOrupdate(projectSubModule)) {
				return new ResponseEntity<String>("{ \"status\":\"false\"}", headers, HttpStatus.EXPECTATION_FAILED);
			}
		} catch (RuntimeException runtimeException) {
			logger.error("RuntimeException occured in updateFromJson method of Module controller:" + runtimeException);
			throw runtimeException;
		} catch (Exception exception) {
			logger.error("Exception occured in updateFromJson method of Module controller:" + exception);
			throw exception;
		}
		logger.info("------ModuleController updateFromJson method end------");
		return new ResponseEntity<String>("{ \"status\":\"true\"}", headers, HttpStatus.OK);
	}

	@RequestMapping(value = "subModules/changeProjectModuleStatus", method = RequestMethod.GET, headers = "Accept=application/json", produces = "application/json")
	@ResponseBody
	public ResponseEntity<String> changeProjectModuleStatus(@RequestParam(value = "moduleId") Integer moduleId, @RequestParam(value = "projectModuleMapped") String projectModuleMapped,
			@RequestParam(value = "projectModuleNotMapped") String projectModuleNotMapped) throws Exception {

		logger.info("------ModuleController changeProjectModuleStatus method start------");
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		String[] modulesMappedIds = null;
		String[] projectModulesNotMapped = null;
		List<Integer> projectModuleMappedIds = new ArrayList<Integer>();
		if (!projectModuleMapped.isEmpty())
			modulesMappedIds = projectModuleMapped.split(",");
		if (!projectModuleNotMapped.isEmpty()) {
			projectModulesNotMapped = projectModuleNotMapped.split(",");
		}
		List<Integer> projectModuleNotMappedIds = new ArrayList<Integer>();
		if (modulesMappedIds != null) {
			for (String str : modulesMappedIds) {
				projectModuleMappedIds.add(Integer.parseInt(str));
			}
		}
		for (Integer id : projectModuleMappedIds) {
			ProjectSubModule projectSubModule = projectSubModuleService.findProjectSubModuleById(id);
			if (projectSubModule.getActive().equalsIgnoreCase("0")) {
				projectSubModule.setActive("1");
				projectSubModule.setLastupdatedTimestamp(new Date());
				projectSubModuleService.saveOrupdate(projectSubModule);
			}

		}

		if (projectModulesNotMapped != null) {
			for (String str : projectModulesNotMapped) {
				projectModuleNotMappedIds.add(Integer.parseInt(str));
			}
		}

		for (Integer id : projectModuleNotMappedIds) {
			ProjectSubModule projectSubModule = projectSubModuleService.findProjectSubModuleById(id);
			if (projectSubModule.getActive().equalsIgnoreCase("1")) {
				projectSubModule.setActive("0");
				projectSubModule.setLastupdatedTimestamp(new Date());
				projectSubModuleService.saveOrupdate(projectSubModule);
			}

		}

		return new ResponseEntity<String>("{ \"status\":\"true\"}", headers, HttpStatus.OK);

	}

}
