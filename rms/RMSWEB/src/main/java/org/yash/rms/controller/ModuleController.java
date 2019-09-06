/**
 * 
 */
package org.yash.rms.controller;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.json.JSONArray;
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
import org.yash.rms.domain.Currency;
import org.yash.rms.domain.EngagementModel;
import org.yash.rms.domain.InvoiceBy;
import org.yash.rms.domain.Module;
import org.yash.rms.domain.ProjectCategory;
import org.yash.rms.domain.ProjectMethodology;
import org.yash.rms.domain.ProjectModule;
import org.yash.rms.domain.Resource;
import org.yash.rms.domain.Team;
import org.yash.rms.helper.ProjectHelper;
import org.yash.rms.json.mapper.JsonObjectMapper;
import org.yash.rms.json.mapper.ProjectJasonMapper;
import org.yash.rms.service.CustomerService;
import org.yash.rms.service.OrgHierarchyService;
import org.yash.rms.service.ProjectAllocationService;
import org.yash.rms.service.ProjectModuleService;
import org.yash.rms.service.ProjectService;
import org.yash.rms.service.ResourceService;
import org.yash.rms.service.RmsCRUDService;
import org.yash.rms.util.Constants;
import org.yash.rms.util.UserUtil;

/**
 * @author deepti.gupta
 * 
 */
@Controller
@RequestMapping("/modules")
public class ModuleController {


  @Autowired
  @Qualifier("ModuleService")
  private RmsCRUDService<Module> moduleService;
  @Autowired
  private JsonObjectMapper<Module> jsonMapper;
  @Autowired
  private JsonObjectMapper<ProjectModule> projectModuleJsonMapper;
  @Autowired
  private ResourceService resourceService;
  @Autowired
  private CustomerService customerService;
 
  @Autowired
  @Qualifier("ProjectService")
  private ProjectService projectService;
  @Autowired
  @Qualifier("projectModuleService")
  private ProjectModuleService projectModuleService;
  @Autowired
  @Qualifier("CurrencyService")
  private RmsCRUDService<Currency> currencyService;
  @Autowired
  @Qualifier("EngagementModelService")
  private RmsCRUDService<EngagementModel> engagementModelServiceImpl;

  @Autowired
  @Qualifier("ProjectCategoryService")
  private RmsCRUDService<ProjectCategory> projectCategoryServiceImpl;

  @Autowired
  @Qualifier("InvoiceService")
  private RmsCRUDService<InvoiceBy> invoiceServiceImpl;

  @Autowired
  @Qualifier("ProjectMethodologyService")
  private RmsCRUDService<ProjectMethodology> projectMethodologyDaoImpl;

  @Autowired
  @Qualifier("OrgHierarchyService")
  private OrgHierarchyService orgHierarchyService;
  @Autowired
  ProjectAllocationService projectAllocationService;

  @Autowired
  @Qualifier("teamService")
  private RmsCRUDService<Team> teamService;

  @Autowired
  @Qualifier("ResourceService")
  ResourceService resourceServiceImpl;

  private static final Logger logger = LoggerFactory.getLogger(ModuleController.class);

  @Autowired
  private UserUtil userUtil;

  /*@Autowired
  private HttpServletRequest request;*/

  @RequestMapping(method = RequestMethod.GET)
  public String list(@RequestParam(value = "page", required = false) Integer page,
      @RequestParam(value = "size", required = false) Integer size, Model uiModel)
          throws Exception {
    logger.info("------moduleController list method start------");
    if (page != null || size != null) {
      int sizeNo = size == null ? 10 : size.intValue();
      final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
      uiModel.addAttribute(Constants.MODULES, moduleService.findByEntries(firstResult, sizeNo));
      float nrOfPages = (float) moduleService.countTotal() / sizeNo;
      uiModel.addAttribute(Constants.MAX_PAGES,
          (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
    } else {

      try {
        uiModel.addAttribute(Constants.MODULES, moduleService.findAll());
      } catch (RuntimeException runtimeException) {
        logger.error(
            "RuntimeException occured in list method of module controller:" + runtimeException);
        throw runtimeException;
      } catch (Exception exception) {
        logger.error("Exception occured in list method of module controller:" + exception);
        throw exception;
      }

    }
    // Set Header values...
    Resource resource = userUtil.getLoggedInResource();
    try {
      if (resource.getUploadImage() != null
          && resource.getUploadImage().length > 0) {
        byte[] encodeBase64UserImage =
            Base64.encodeBase64(resource.getUploadImage());
        String base64EncodedUser = new String(encodeBase64UserImage, "UTF-8");
        uiModel.addAttribute("UserImage", base64EncodedUser);
      } else {
        uiModel.addAttribute("UserImage", "");
      }
    } catch (Exception exception) {
      logger.error("Exception occured in list method of module controller:" + exception);
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
    logger.info("------moduleController list method end------");
    return "modules/list";
  }

  @RequestMapping(value = "/{id}", method = RequestMethod.DELETE,
      headers = "Accept=application/json")
  public ResponseEntity<String> deleteFromJson(@PathVariable("id") Integer id) throws Exception {
    logger.info("------moduleController deleteFromJson method start------");

    JSONArray jsonArray = new JSONArray();
    JSONObject jsonObject = new JSONObject();

    HttpHeaders headers = new HttpHeaders();
    headers.add("Content-Type", "application/json");
    // Delete based on ID
    try {
      if (!moduleService.delete(id.intValue())) {
        jsonObject.put("status", "false");
        jsonArray.put(jsonObject);
        return new ResponseEntity<String>(jsonArray.toString(), headers, HttpStatus.NOT_FOUND);
      }
    } catch (RuntimeException runtimeException) {
      logger.error("RuntimeException occured in deleteFromJson method of module controller:"
          + runtimeException);
      return new ResponseEntity<String>("{ \"status\":\"FALSE\"}", headers,
          HttpStatus.EXPECTATION_FAILED);
    } catch (Exception exception) {
      logger.error("Exception occured in deleteFromJson method of module controller:" + exception);
      return new ResponseEntity<String>("{ \"status\":\"FALSE\"}", headers,
          HttpStatus.EXPECTATION_FAILED);
    }
    logger.info("------moduleController deleteFromJson method end------");
    jsonObject.put("status", "true");
    jsonArray.put(jsonObject);
    return new ResponseEntity<String>(jsonArray.toString(), headers, HttpStatus.OK);
  }

  @RequestMapping(method = RequestMethod.POST, headers = "Accept=application/json")
  public ResponseEntity<String> createFromJson(@RequestBody String json, HttpServletRequest request)
      throws Exception {
    logger.info("------moduleController createFromJson method start------");
    ServletContext context = request.getSession().getServletContext();
    context.getAttribute("userName");

    // Convert JSon Object to Domain Object
    Module module = jsonMapper.fromJsonToObject(json, Module.class);
    module.setCreatedId((String) context.getAttribute("userName"));

    JSONArray jsonArray = new JSONArray();
    JSONObject jsonObject = new JSONObject();

    HttpHeaders headers = new HttpHeaders();
    headers.add("Content-Type", "application/json");
    try {
      if (!moduleService.saveOrupdate(module)) {
        jsonObject.put("status", "false");
        jsonArray.put(jsonObject);
        return new ResponseEntity<String>(jsonArray.toString(), headers,
            HttpStatus.EXPECTATION_FAILED);
      }
    } catch (RuntimeException runtimeException) {
      logger.error("RuntimeException occured in createFromJson method of module controller:"
          + runtimeException);
      throw runtimeException;
    } catch (Exception exception) {
      logger.error("Exception occured in createFromJson method of module controller:" + exception);
      throw exception;
    }
    logger.info("------moduleController createFromJson method end------");
    jsonObject.put("status", "true");
    jsonArray.put(jsonObject);
    return new ResponseEntity<String>(jsonArray.toString(), headers, HttpStatus.CREATED);
  }

  /*
   * User Story #4422(Create a new screen : Configure Project Module under configuration link-change
   * start)
   */
  @RequestMapping(value = "/listProject", method = RequestMethod.GET)
  public String listProjects(@RequestParam(value = "page", required = false) Integer page,
      @RequestParam(value = "size", required = false) Integer size, Model uiModel)
          throws Exception {

    logger.info("------ModuleController list method start------");
    try {
   /*   uiModel.addAttribute(Constants.RESOURCES, resourceService.findAll());
      uiModel.addAttribute(Constants.ALL_CUSTOMERS, customerService.findAll());
      uiModel.addAttribute(Constants.ALL_CURRENCY, currencyService.findAll());
      uiModel.addAttribute(Constants.ENGAGEMENTMODEL, engagementModelServiceImpl.findAll());
      uiModel.addAttribute(Constants.PROJECT_CATEGORY, projectCategoryServiceImpl.findAll());
      uiModel.addAttribute(Constants.INVOICES_BY, invoiceServiceImpl.findAll());
      uiModel.addAttribute(Constants.PROJECT_METHODOLOGYS, projectMethodologyDaoImpl.findAll());
      uiModel.addAttribute(Constants.BUS, orgHierarchyService.findAllBu());
      uiModel.addAttribute(Constants.TEAMS, teamService.findAll());
      uiModel.addAttribute(Constants.OFFSHORE_DEL_MGR, "abc");*/

      // Set Header values...
      Resource resource = userUtil.getLoggedInResource();
      try {
        if (resource.getUploadImage() != null
            && resource.getUploadImage().length > 0) {
          byte[] encodeBase64UserImage =
              Base64.encodeBase64(resource.getUploadImage());
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

      logger.info("------ModuleController list method end------");
      return "projectmodule/projectmodules";
    } catch (RuntimeException runtimeException) {
      logger
          .error("RuntimeException occured in list method of ModuleController:" + runtimeException);
      throw runtimeException;
    } catch (Exception exception) {
      logger.error("Exception occured in list method of ModuleController:" + exception);
      throw exception;
    }

  }

  @RequestMapping(value = "projects/projectmodules", produces = "text/html")
  public String listModules(@RequestParam("projectId") Integer projectId, Model uiModel)
      throws Exception {
    logger.info("------ModuleController list method start------");
    try {
      uiModel.addAttribute(Constants.ACTIVITYS,
          projectModuleService.findAllProjectModule(projectId));
    } catch (RuntimeException runtimeException) {
      logger.error("RuntimeException occured in list method of UserActivityController controller:"
          + runtimeException);
      throw runtimeException;
    } catch (Exception exception) {
      logger.error(
          "Exception occured in list method of UserActivityController controller:" + exception);
      throw exception;
    }
    logger.info("------ModuleController list method end------");
    return "projectmodule/custommodulelist";
  }

  @RequestMapping(value = "projects/save", method = RequestMethod.POST,
      headers = "Accept=application/json")
  public ResponseEntity<String> createProjectModuleFromJson(@RequestBody String json,
      HttpServletRequest request) throws Exception {
    logger.info("------ModuleController createFromJson method start------");
    ServletContext context = request.getSession().getServletContext();
    context.getAttribute("userName");
    ProjectModule projectModule =
        projectModuleJsonMapper.fromJsonToObject(json, ProjectModule.class);

    HttpHeaders headers = new HttpHeaders();
    headers.add("Content-Type", "application/json");
    try {
      if (!projectModuleService.saveOrupdate(projectModule)) {
        return new ResponseEntity<String>("{ \"status\":\"false\"}", headers,
            HttpStatus.EXPECTATION_FAILED);
      }
    } catch (RuntimeException runtimeException) {
      logger.error(
          "RuntimeException occured in createProjectModuleFromJson method of Module Controller:"
              + runtimeException);
      throw runtimeException;

    } catch (Exception exception) {
      logger.error("Exception occured in createProjectModuleFromJson method of Module Controller:"
          + exception);
      throw exception;
    }
    logger.info("------ModuleController createFromJson method end------");
    return new ResponseEntity<String>("{ \"status\":\"true\"}", headers, HttpStatus.CREATED);
  }

  @RequestMapping(value = "projects/update", method = RequestMethod.PUT,
      headers = "Accept=application/json")
  public ResponseEntity<String> updateFromJson(@RequestBody String json, HttpServletRequest request)
      throws Exception {
    logger.info("------Module controller updateFromJson method start------");
    HttpHeaders headers = new HttpHeaders();
    headers.add("Content-Type", "application/json");
    ServletContext context = request.getSession().getServletContext();
    context.getAttribute("userName");
    ProjectModule projectModule =
        projectModuleJsonMapper.fromJsonToObject(json, ProjectModule.class);

    try {
      if (!projectModuleService.saveOrupdate(projectModule)) {
        return new ResponseEntity<String>("{ \"status\":\"false\"}", headers,
            HttpStatus.EXPECTATION_FAILED);
      }
    } catch (RuntimeException runtimeException) {
      logger.error("RuntimeException occured in updateFromJson method of Module controller:"
          + runtimeException);
      throw runtimeException;
    } catch (Exception exception) {
      logger.error("Exception occured in updateFromJson method of Module controller:" + exception);
      throw exception;
    }
    logger.info("------ModuleController updateFromJson method end------");
    return new ResponseEntity<String>("{ \"status\":\"true\"}", headers, HttpStatus.OK);
  }


  @RequestMapping(value = "projects/populateInactiveModules/{projectId}",
      method = RequestMethod.GET, headers = "Accept=application/json",
      produces = "application/json")
  @ResponseBody
  public ResponseEntity<String> populateInactiveModules(
      @PathVariable("projectId") Integer projectId) throws Exception {
    logger.info("------ModuleController populateInactiveModules method start------");
    List<ProjectModule> projectModules =
        projectModuleService.findProjectModuleByProjectIdAndStatus(projectId, Constants.INACTIVE);
    HttpHeaders headers = new HttpHeaders();
    headers.add("Content-Type", "application/json; charset=utf-8");
    try {
      if (projectModules == null) {
        return new ResponseEntity<String>("{ \"status\":\"false\"}", headers, HttpStatus.NOT_FOUND);
      }
    } catch (RuntimeException runtimeException) {
      logger.error("RuntimeException occured in populateInactiveModules method of ModuleController:"
          + runtimeException);
      throw runtimeException;
    } catch (Exception exception) {
      logger.error(
          "Exception occured in populateInactiveModules method of ModuleController:" + exception);
      throw exception;
    }
    logger.info("------ModuleController populateInactiveModules method end------");

    String jsonString = projectModuleJsonMapper.toJsonArrayModule(projectModules);

    return new ResponseEntity<String>(jsonString, headers, HttpStatus.OK);
  }

  @RequestMapping(value = "projects/populateActiveModules/{projectId}", method = RequestMethod.GET,
      headers = "Accept=application/json", produces = "application/json")
  @ResponseBody
  public ResponseEntity<String> populateActiveModules(@PathVariable("projectId") Integer projectId)
      throws Exception {
    logger.info("------ModuleController showInactiveActivities method start------");
    List<ProjectModule> projectModules =
        projectModuleService.findProjectModuleByProjectIdAndStatus(projectId, Constants.ACTIVE);
    HttpHeaders headers = new HttpHeaders();
    headers.add("Content-Type", "application/json; charset=utf-8");
    try {
      if (projectModules == null) {
        return new ResponseEntity<String>("{ \"status\":\"false\"}", headers, HttpStatus.NOT_FOUND);
      }
    } catch (RuntimeException runtimeException) {
      logger.error("RuntimeException occured in populateActiveModules method of ModuleController:"
          + runtimeException);
      throw runtimeException;
    } catch (Exception exception) {
      logger.error(
          "Exception occured in populateActiveModules method of ModuleController:" + exception);
      throw exception;
    }
    logger.info("------ModuleController showInactiveActivities method end------");

    String jsonString = projectModuleJsonMapper.toJsonArrayModule(projectModules);

    return new ResponseEntity<String>(jsonString, headers, HttpStatus.OK);
  }

  @RequestMapping(value = "projects/changeProjectModuleStatus", method = RequestMethod.GET,
      headers = "Accept=application/json", produces = "application/json")
  @ResponseBody
  public ResponseEntity<String> changeProjectModuleStatus(
      @RequestParam(value = "projectId") Integer projectId,
      @RequestParam(value = "projectModuleMapped") String projectModuleMapped,
      @RequestParam(value = "projectModuleNotMapped") String projectModuleNotMapped)
          throws Exception {

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
      ProjectModule projectModule = projectModuleService.findProjectModuleById(id);
      if (!projectModule.isActive()) {
        projectModule.setActive(true);
        projectModule.setLastupdatedTimestamp(new Date());
        projectModuleService.saveOrupdate(projectModule);
      }

    }

    if (projectModulesNotMapped != null) {
      for (String str : projectModulesNotMapped) {
        projectModuleNotMappedIds.add(Integer.parseInt(str));
      }
    }

    for (Integer id : projectModuleNotMappedIds) {
      ProjectModule projectModule = projectModuleService.findProjectModuleById(id);
      if (projectModule.isActive()) {
        projectModule.setActive(false);
        projectModule.setLastupdatedTimestamp(new Date());
        projectModuleService.saveOrupdate(projectModule);
      }

    }

    return new ResponseEntity<String>("{ \"status\":\"true\"}", headers, HttpStatus.OK);

  }

  /*
   * User Story #4422(Create a new screen : Configure Project Module under configuration
   * link)-change end
   */

}
