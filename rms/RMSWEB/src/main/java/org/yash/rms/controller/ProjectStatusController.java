package org.yash.rms.controller;

import java.text.DateFormatSymbols;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.yash.rms.domain.Currency;
import org.yash.rms.domain.EngagementModel;
import org.yash.rms.domain.InvoiceBy;
import org.yash.rms.domain.ProjectCategory;
import org.yash.rms.domain.ProjectMethodology;
import org.yash.rms.domain.ProjectTicketPriority;
import org.yash.rms.domain.ProjectTicketStatus;
import org.yash.rms.domain.Resource;
import org.yash.rms.domain.Team;
import org.yash.rms.dto.ProjectTicketStatusDTO;
import org.yash.rms.json.mapper.JsonObjectMapper;
import org.yash.rms.service.ActivityService;
import org.yash.rms.service.CustomerService;
import org.yash.rms.service.OrgHierarchyService;
import org.yash.rms.service.ProjectService;
import org.yash.rms.service.ProjectStatusService;
import org.yash.rms.service.ResourceService;
import org.yash.rms.service.RmsCRUDService;
import org.yash.rms.util.Constants;
import org.yash.rms.util.UserUtil;

import flexjson.JSONSerializer;


@Controller
@RequestMapping("/projectStatus")
public class ProjectStatusController {

  private static final Logger logger = LoggerFactory.getLogger(ProjectStatusController.class);

  @Autowired
  @Qualifier("ProjectService")
  private ProjectService projectService;

  @Autowired
  private ResourceService resourceService;

  @Autowired
  private CustomerService customerService;

  @Autowired
  @Qualifier("CurrencyService")
  private RmsCRUDService<Currency> currencyService;

  @Autowired
  @Qualifier("ResourceService")
  private ResourceService resourceServiceImpl;

  @Autowired
  @Qualifier("EngagementModelService") private RmsCRUDService<EngagementModel> engagementModelServiceImpl;

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
  @Qualifier("teamService")
  private RmsCRUDService<Team> teamService;
  
  @Autowired
  @Qualifier("ActivityService")
  private ActivityService activityService;
  
  @Autowired
  private JsonObjectMapper<ProjectTicketStatus> jsonProjectTicketStatusMapper;
  
  @Autowired
  @Qualifier("ProjectStatusService")
  private ProjectStatusService projectStatusService;

  @Autowired
  private UserUtil userUtil;

  @RequestMapping(method = RequestMethod.GET)
  public String getProjectStatus(@RequestParam(value = "page", required = false) Integer page,
      @RequestParam(value = "size", required = false) Integer size, Model uiModel)
          throws Exception {
	  Resource resource = userUtil.getLoggedInResource();
    logger.info("------ProjectStatusController getProjectStatus method start------");
    try {
      uiModel.addAttribute(Constants.RESOURCES, resourceService.findAll());
      uiModel.addAttribute(Constants.ALL_CUSTOMERS, customerService.findAll());
      uiModel.addAttribute(Constants.ALL_CURRENCY, currencyService.findAll());
      uiModel.addAttribute(Constants.ENGAGEMENTMODEL, engagementModelServiceImpl.findAll());
      uiModel.addAttribute(Constants.PROJECT_CATEGORY, projectCategoryServiceImpl.findAll());
      uiModel.addAttribute(Constants.INVOICES_BY, invoiceServiceImpl.findAll());
      uiModel.addAttribute(Constants.PROJECT_METHODOLOGYS, projectMethodologyDaoImpl.findAll());
      uiModel.addAttribute(Constants.BUS, orgHierarchyService.findAllBu());
      uiModel.addAttribute(Constants.TEAMS, teamService.findAll());
      uiModel.addAttribute(Constants.OFFSHORE_DEL_MGR, "abc");

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
        logger.error("exception occured in list method of ProjectStatusController:" + exception);
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
      logger.info("------ProjectStatusController list method end------");
      return "projectStatus/projectStatus";
    } catch (RuntimeException runtimeException) {
      logger.error(
          "RuntimeException occured in getProjectStatus method of ProjectStatusController:"
              + runtimeException);
      throw runtimeException;
    } catch (Exception exception) {
      logger.error("Exception occured in getProjectStatus method of ProjectStatusController:"
          + exception);
      throw exception;
    }

  }

  @RequestMapping(value = "/ticketStatus/{projectId}", headers = "Accept=application/json")
  @ResponseBody
  public ResponseEntity<String> showConfiguredStatus(@PathVariable("projectId") Integer projectId)
      throws Exception {
    logger.info("------ProjectStatusController showConfiguredStatus method start------");
    
    List<ProjectTicketStatus> statusList = projectStatusService.getConfiguredTicketStatus(projectId);
    
    HttpHeaders headers = new HttpHeaders();
    headers.add("Content-Type", "application/json; charset=utf-8");
    
    try {
      if (statusList == null) {
        return new ResponseEntity<String>("{ \"status\":\"false\"}", headers, HttpStatus.NOT_FOUND);
      }
    } catch (RuntimeException runtimeException) {
      logger.error("RuntimeException occured in showConfiguredStatus method of ProjectStatusController:"
          + runtimeException);
      throw runtimeException;
    } catch (Exception exception) {
      logger.error(
          "Exception occured in showConfiguredStatus method of ProjectStatusController:" + exception);
      throw exception;
    }
    logger.info("------ProjectStatusController showConfiguredStatus method end------");

    String jsonString = jsonProjectTicketStatusMapper.toJsonTicketStatus(statusList);

    return new ResponseEntity<String>(jsonString, headers, HttpStatus.OK);
  }
  
  @RequestMapping(value = "/selectedTicketStatus/{projectId}", headers = "Accept=application/json")
  @ResponseBody
  public ResponseEntity<String> showSelectedConfiguredStatus(@PathVariable("projectId") Integer projectId)
      throws Exception {
    logger.info("------ProjectStatusController showConfiguredStatus method start------");
    
    List<ProjectTicketStatus> statusList = projectStatusService.getSelectedConfiguredTicketStatus(projectId);
    
    HttpHeaders headers = new HttpHeaders();
    headers.add("Content-Type", "application/json; charset=utf-8");
    
    try {
      if (statusList == null) {
        return new ResponseEntity<String>("{ \"status\":\"false\"}", headers, HttpStatus.NOT_FOUND);
      }
    } catch (RuntimeException runtimeException) {
      logger.error("RuntimeException occured in showConfiguredStatus method of ProjectStatusController:"
          + runtimeException);
      throw runtimeException;
    } catch (Exception exception) {
      logger.error(
          "Exception occured in showConfiguredStatus method of ProjectStatusController:" + exception);
      throw exception;
    }
    logger.info("------ProjectStatusController showConfiguredStatus method end------");

    String jsonString = jsonProjectTicketStatusMapper.toJsonTicketStatus(statusList);
    //String json =  new JSONSerializer().exclude("*.class").serialize(prioritiesList);
    
    return new ResponseEntity<String>(jsonString, headers, HttpStatus.OK);
  }
  
  @RequestMapping(value = "/customTicketStatus", produces = "text/html")
  public String customTicketStatus(@RequestParam("projectId") Integer projectId, Model uiModel) throws Exception {
    logger.info("------ ProjectStatusController customTicketStatus method start------");
    try {
      uiModel.addAttribute(Constants.ACTIVITYS, projectStatusService.findById(projectId, null));
    } catch (RuntimeException runtimeException) {
      logger.error("RuntimeException occured in customTicketService method of ProjectStatusController:"
          + runtimeException);
      throw runtimeException;
    } catch (Exception exception) {
      logger.error(
          "Exception occured in customTicketStatus method of ProjectStatusController:" + exception);
      throw exception;
    }
    logger.info("------ProjectStatusController customTicketStatus method end------");
    return "projectStatus/projectTicketStatus";
  }
  
  @RequestMapping(value = "/updateTicketStatus", method = RequestMethod.PUT, headers = "Accept=application/json")
  public ResponseEntity<String> updateProjectStatusFromJson(@RequestBody String json, HttpServletRequest request) throws Exception {
    logger.info("------ProjectStatusController updateProjectActivityFromJson method start------");
    HttpHeaders headers = new HttpHeaders();
    headers.add("Content-Type", "application/json");
    ServletContext context = request.getSession().getServletContext();
    context.getAttribute("userName");
    ProjectTicketStatus status = jsonProjectTicketStatusMapper.fromJsonToObject(json, ProjectTicketStatus.class);

    try {
      if (!projectStatusService.saveOrupdate(status)) {
        return new ResponseEntity<String>("{ \"status\":\"false\"}", headers, HttpStatus.EXPECTATION_FAILED);
      }
    } catch (RuntimeException runtimeException) {
      logger.error("RuntimeException occured in updateProjectStatusFromJson method of ProjectStatusController:" + runtimeException);
      throw runtimeException;
    } catch (Exception exception) {
      logger.error("Exception occured in updateProjectStatusFromJson method of ProjectStatusController:"+ exception);
      throw exception;
    }
    logger.info("------ ProjectStatusController updateProjectStatusFromJson method end------");
    return new ResponseEntity<String>("{ \"status\":\"true\"}", headers, HttpStatus.OK);
  }
  
  @RequestMapping(value = "/createProjectStatus", method = RequestMethod.POST, headers = "Accept=application/json")
  public ResponseEntity<String> createProjectStatusFromJson(@RequestBody String json, HttpServletRequest request) throws Exception {
	    logger.info("------ProjectStatusController createProjectStatusFromJson method start------");
	    HttpHeaders headers = new HttpHeaders();
	    headers.add("Content-Type", "application/json");
	    ServletContext context = request.getSession().getServletContext();
	    context.getAttribute("userName");
	    ProjectTicketStatus status = jsonProjectTicketStatusMapper.fromJsonToObject(json, ProjectTicketStatus.class);

	    try {
	      if (!projectStatusService.saveOrupdate(status)) {
	        return new ResponseEntity<String>("{ \"status\":\"false\"}", headers, HttpStatus.EXPECTATION_FAILED);
	      }
	    } catch (RuntimeException runtimeException) {
	      logger.error("RuntimeException occured in createProjectStatusFromJson method of ProjectStatusController:" + runtimeException);
	      throw runtimeException;
	    } catch (Exception exception) {
	      logger.error("Exception occured in createProjectStatusFromJson method of ProjectStatusController:"+ exception);
	      throw exception;
	    }
	    logger.info("------ ProjectStatusController createProjectStatusFromJson method end------");
	    return new ResponseEntity<String>("{ \"status\":\"true\"}", headers, HttpStatus.OK);
  }
  
  @RequestMapping(value = "/deleteStatus/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json", produces = "application/json")
	  public ResponseEntity<String> deleteProjectStatusFromJson(@PathVariable("id") Integer id) throws Exception {
	    logger.info("------ProjectStatusController deleteProjectStatusFromJson method start------");
	    try {
	      HttpHeaders headers = new HttpHeaders();
	      headers.add("Content-Type", "application/json");
	      // Delete based on ID
	      if (!projectStatusService.delete(id.intValue())) {
	        return new ResponseEntity<String>("{ \"status\":\"false\"}", headers, HttpStatus.NOT_FOUND);
	      }
	      logger.info("------ProjectStatusController deleteProjectStatusFromJson method end------");
	      return new ResponseEntity<String>("{ \"status\":\"true\"}", headers, HttpStatus.OK);
	    } catch (RuntimeException runtimeException) {
	      logger.error("RuntimeException occured in deleteProjectStatusFromJson method of ProjectStatusController:"+ runtimeException);
	      throw runtimeException;
	    } catch (Exception exception) {
	      logger.error("Exception occured in deleteProjectStatusFromJson method of ProjectStatusController:"+ exception);
	      throw exception;
	    }
  }
  
  @RequestMapping(value = "/saveProjectStatus",method = RequestMethod.GET,headers = "Accept=application/json",produces = "application/json" )
  @ResponseBody
  public ResponseEntity<String> saveProjectStatus(@RequestParam(value="projectStatusList") String projectStatusList, @RequestParam(value="projectId") Integer projectId) throws Exception {
			logger.info("------ProjectStatusController saveProjectStatus method start------");
			try {

				HttpHeaders headers = new HttpHeaders();
				headers.add("Content-Type", "application/json; charset=utf-8");
				
				projectStatusService.saveProjectStatus(projectStatusList, projectId);
				/*String[] statusList = projectStatusList.split(",");
				projectStatusService.saveProjectStatus(Arrays.asList(statusList), projectId);*/
				
				logger.info("------ProjectStatusController saveProjectStatus method end------");
				return new ResponseEntity<String>("{ \"response\":\"Status\"}",headers, HttpStatus.OK); 
			} catch (RuntimeException runtimeException) {
				logger.error("RuntimeException occured in saveProjectStatus method of ProjectStatusController:"
						+ runtimeException);
				throw runtimeException;
			} catch (Exception exception) {
				logger.error("Exception occured in saveProjectStatus method of ProjectStatusController:"
						+ exception);
				throw exception;
			}
  }
  
  @RequestMapping(value = "/getActiveTicketStatusByProjectId/{projectId}", headers = "Accept=application/json")
  @ResponseBody
  public ResponseEntity<String> getActiveTicketStatusByProjectId(@PathVariable("projectId") Integer projectId)
      throws Exception {
    logger.info("Inside getActiveTicketStatusByProjectId for projectId "+projectId+" on "+this.getClass().getCanonicalName());
    List<ProjectTicketStatus> ticketStatusList = projectStatusService.getActiveProjectTicketStatusForProjectId(projectId);
    List<ProjectTicketStatusDTO> ticketStatusDTO=projectStatusService.convertProjectTicketStatusListToDTOList(ticketStatusList);
    HttpHeaders headers = new HttpHeaders();
    headers.add("Content-Type", "application/json; charset=utf-8");
    try {
      if (ticketStatusDTO == null) {
        return new ResponseEntity<String>("{ \"status\":\"false\"}", headers, HttpStatus.NOT_FOUND);
      }
    } catch (RuntimeException runtimeException) {
      logger.error("RuntimeException occured in getActiveTicketStatusByProjectId method of "+this.getClass().getCanonicalName()+":"
          + runtimeException);
      throw runtimeException;
    } catch (Exception exception) {
      logger.error(
          "Exception occured in getActiveTicketStatusByProjectId method of "+this.getClass().getCanonicalName()+":"+ exception);
      throw exception;
    }
    logger.info("Inside getActiveTicketStatusByProjectId method end on "+this.getClass().getCanonicalName());

    String jsonString = ProjectTicketStatusDTO.toJsonArray(ticketStatusDTO);
    
    return new ResponseEntity<String>(jsonString, headers, HttpStatus.OK);
  }

  @RequestMapping(value = "/getActiveTicketStatusByResourceAllocationId/{resourceAllocationId}", headers = "Accept=application/json")
  @ResponseBody
  public ResponseEntity<String> getActiveTicketStatusByResourceAllocationId(@PathVariable("resourceAllocationId") Integer resourceAllocationId)
      throws Exception {
    logger.info("Inside getActiveTicketStatusByResourceAllocationId for resourceAllocationId "+resourceAllocationId+" on "+this.getClass().getCanonicalName());
    List<ProjectTicketStatus> statusList = projectStatusService.getActiveTicketStatusByResourceAllocationId(resourceAllocationId);
    List<ProjectTicketStatusDTO> projectTicketStatusDTOList=projectStatusService.convertProjectTicketStatusListToDTOList(statusList);
    HttpHeaders headers = new HttpHeaders();
    headers.add("Content-Type", "application/json; charset=utf-8");
    
    try {
      if (projectTicketStatusDTOList == null) {
        return new ResponseEntity<String>("{ \"status\":\"false\"}", headers, HttpStatus.NOT_FOUND);
      }
    } catch (RuntimeException runtimeException) {
      logger.error("RuntimeException occured in getActiveTicketStatusByResourceAllocationId method of ProjectStatusController:"
          + runtimeException);
      throw runtimeException;
    } catch (Exception exception) {
      logger.error(
          "Exception occured in getActiveTicketStatusByResourceAllocationId method of "+this.getClass().getCanonicalName()+":-" + exception);
      throw exception;
    }
    logger.info("Inside getActiveTicketStatusByResourceAllocationId method end on "+this.getClass().getCanonicalName());

    String jsonString = ProjectTicketStatusDTO.toJsonArray(projectTicketStatusDTOList);
    
    return new ResponseEntity<String>(jsonString, headers, HttpStatus.OK);
  }
  
  
}
