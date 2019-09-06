package org.yash.rms.controller;

import java.sql.Timestamp;
import java.text.DateFormatSymbols;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
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
import org.yash.rms.domain.Resource;
import org.yash.rms.domain.Team;
import org.yash.rms.dto.ProjectTicketPriorityDTO;
import org.yash.rms.json.mapper.JsonObjectMapper;
import org.yash.rms.service.ActivityService;
import org.yash.rms.service.CustomerService;
import org.yash.rms.service.OrgHierarchyService;
import org.yash.rms.service.ProjectService;
import org.yash.rms.service.ProjectTicketsService;
import org.yash.rms.service.ResourceService;
import org.yash.rms.service.RmsCRUDService;
import org.yash.rms.util.Constants;
import org.yash.rms.util.UserUtil;

import flexjson.JSONSerializer;

/**
 * @author sarang.patil
 *
 */
@Controller
@RequestMapping("/projectTickets")
public class ProjectTicketsController {

  private static final Logger logger = LoggerFactory.getLogger(ProjectTicketsController.class);

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
  @Qualifier("teamService")
  private RmsCRUDService<Team> teamService;
  
  @Autowired
  @Qualifier("ActivityService")
  private ActivityService activityService;
  
  @Autowired
  private JsonObjectMapper<ProjectTicketPriority> jsonProjectTicketPriorityMapper;
  
  @Autowired
  @Qualifier("ProjectTicketsService")
  private ProjectTicketsService projectTicketsService;

  @Autowired
  private UserUtil userUtil;

  @RequestMapping(method = RequestMethod.GET)
  public String getProjectTickets(@RequestParam(value = "page", required = false) Integer page,
      @RequestParam(value = "size", required = false) Integer size, Model uiModel)
          throws Exception {
	  Resource resource = userUtil.getLoggedInResource();
    logger.info("------ProjectTicketsController getProjectTickets method start ------" + new Timestamp(System.currentTimeMillis()));
    try {
    	
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
        logger.error("exception occured in list method of ProjectTicketsController:" + exception);
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
      String endTime = new Timestamp(System.currentTimeMillis()).toString();
      logger.info("------ProjectTicketsController list method end------" + endTime);
      return "projectTicket/projectTickets";
    } catch (RuntimeException runtimeException) {
      logger.error(
          "RuntimeException occured in getProjectTickets method of ProjectTicketsController:"
              + runtimeException);
      throw runtimeException;
    } catch (Exception exception) {
      logger.error("Exception occured in getProjectTickets method of ProjectTicketsController:"
          + exception);
      throw exception;
    }

  }

  @RequestMapping(value = "/ticketPriorities/{projectId}", headers = "Accept=application/json")
  @ResponseBody
  public ResponseEntity<String> showConfiguredPriorities(@PathVariable("projectId") Integer projectId)
      throws Exception {
    logger.info("------ProjectTicketsController showConfiguredPriorities method start------");
    
    List<ProjectTicketPriority> prioritiesList = projectTicketsService.getConfiguredTicketPriorities(projectId);
    
    HttpHeaders headers = new HttpHeaders();
    headers.add("Content-Type", "application/json; charset=utf-8");
    
    try {
      if (prioritiesList == null) {
        return new ResponseEntity<String>("{ \"status\":\"false\"}", headers, HttpStatus.NOT_FOUND);
      }
    } catch (RuntimeException runtimeException) {
      logger.error("RuntimeException occured in showConfiguredPriorities method of ProjectTicketsController:"
          + runtimeException);
      throw runtimeException;
    } catch (Exception exception) {
      logger.error(
          "Exception occured in showConfiguredPriorities method of ProjectTicketsController:" + exception);
      throw exception;
    }
    

    String jsonString = jsonProjectTicketPriorityMapper.toJsonTicketPriority(prioritiesList);
    
    logger.info("------ProjectTicketsController showConfiguredPriorities method end------");
    return new ResponseEntity<String>(jsonString, headers, HttpStatus.OK);
  }
  
  @RequestMapping(value = "/selectedTicketPriorities/{projectId}", headers = "Accept=application/json")
  @ResponseBody
  public ResponseEntity<String> showSelectedConfiguredPriorities(@PathVariable("projectId") Integer projectId)
      throws Exception {
    logger.info("------ProjectTicketsController showConfiguredPriorities method start------");
    
    List<ProjectTicketPriority> prioritiesList = projectTicketsService.getSelectedConfiguredTicketPriorities(projectId);
    
    HttpHeaders headers = new HttpHeaders();
    headers.add("Content-Type", "application/json; charset=utf-8");
    
    try {
      if (prioritiesList == null) {
        return new ResponseEntity<String>("{ \"status\":\"false\"}", headers, HttpStatus.NOT_FOUND);
      }
    } catch (RuntimeException runtimeException) {
      logger.error("RuntimeException occured in showConfiguredPriorities method of ProjectTicketsController:"
          + runtimeException);
      throw runtimeException;
    } catch (Exception exception) {
      logger.error(
          "Exception occured in showConfiguredPriorities method of ProjectTicketsController:" + exception);
      throw exception;
    }
    logger.info("------ProjectTicketsController showConfiguredPriorities method end------");

    String jsonString = jsonProjectTicketPriorityMapper.toJsonTicketPriority(prioritiesList);
    //String json =  new JSONSerializer().exclude("*.class").serialize(prioritiesList);
    
    return new ResponseEntity<String>(jsonString, headers, HttpStatus.OK);
  }
  
  @RequestMapping(value = "/customTicketPriority", produces = "text/html")
  public String customTicketPriority(@RequestParam("projectId") Integer projectId, Model uiModel) throws Exception {
    logger.info("------ ProjectTicketsController customTicketPriority method start------");
    try {
      uiModel.addAttribute(Constants.ACTIVITYS, projectTicketsService.findById(projectId, null));
    } catch (RuntimeException runtimeException) {
      logger.error("RuntimeException occured in customTicketPriority method of ProjectTicketsController:"
          + runtimeException);
      throw runtimeException;
    } catch (Exception exception) {
      logger.error(
          "Exception occured in customTicketPriority method of ProjectTicketsController:" + exception);
      throw exception;
    }
    logger.info("------ProjectTicketsController customTicketPriority method end------");
    return "projectTicket/projectTicketPriority";
  }
  
  @RequestMapping(method = RequestMethod.PUT, headers = "Accept=application/json")
  public ResponseEntity<String> updateProjectPriorityFromJson(@RequestBody String json, HttpServletRequest request) throws Exception {
    logger.info("------ProjectTicketsController updateProjectActivityFromJson method start------");
    HttpHeaders headers = new HttpHeaders();
    headers.add("Content-Type", "application/json");
    ServletContext context = request.getSession().getServletContext();
    context.getAttribute("userName");
    ProjectTicketPriority priority = jsonProjectTicketPriorityMapper.fromJsonToObject(json, ProjectTicketPriority.class);

    try {
      if (!projectTicketsService.saveOrupdate(priority)) {
        return new ResponseEntity<String>("{ \"status\":\"false\"}", headers, HttpStatus.EXPECTATION_FAILED);
      }
    } catch (RuntimeException runtimeException) {
      logger.error("RuntimeException occured in updateProjectPriorityFromJson method of ProjectTicketsController:" + runtimeException);
      throw runtimeException;
    } catch (Exception exception) {
      logger.error("Exception occured in updateProjectPriorityFromJson method of ProjectTicketsController:"+ exception);
      throw exception;
    }
    logger.info("------ ProjectTicketsController updateProjectPriorityFromJson method end------");
    return new ResponseEntity<String>("{ \"status\":\"true\"}", headers, HttpStatus.OK);
  }
  
  @RequestMapping(method = RequestMethod.POST, headers = "Accept=application/json")
  public ResponseEntity<String> createProjectPriorityFromJson(@RequestBody String json, HttpServletRequest request) throws Exception {
	    logger.info("------ProjectTicketsController createProjectPriorityFromJson method start------");
	    HttpHeaders headers = new HttpHeaders();
	    headers.add("Content-Type", "application/json");
	    ServletContext context = request.getSession().getServletContext();
	    context.getAttribute("userName");
	    ProjectTicketPriority priority = jsonProjectTicketPriorityMapper.fromJsonToObject(json, ProjectTicketPriority.class);

	    try {
	      if (!projectTicketsService.saveOrupdate(priority)) {
	        return new ResponseEntity<String>("{ \"status\":\"false\"}", headers, HttpStatus.EXPECTATION_FAILED);
	      }
	    } catch (RuntimeException runtimeException) {
	      logger.error("RuntimeException occured in createProjectPriorityFromJson method of ProjectTicketsController:" + runtimeException);
	      throw runtimeException;
	    } catch (Exception exception) {
	      logger.error("Exception occured in createProjectPriorityFromJson method of ProjectTicketsController:"+ exception);
	      throw exception;
	    }
	    logger.info("------ ProjectTicketsController createProjectPriorityFromJson method end------");
	    return new ResponseEntity<String>("{ \"status\":\"true\"}", headers, HttpStatus.OK);
  }
  
  @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json", produces = "application/json")
	  public ResponseEntity<String> deleteProjectPriorityFromJson(@PathVariable("id") Integer id) throws Exception {
	    logger.info("------ProjectTicketsController deleteProjectPriorityFromJson method start------");
	    try {
	      HttpHeaders headers = new HttpHeaders();
	      headers.add("Content-Type", "application/json");
	      // Delete based on ID
	      if (!projectTicketsService.delete(id.intValue())) {
	        return new ResponseEntity<String>("{ \"status\":\"false\"}", headers, HttpStatus.NOT_FOUND);
	      }
	      logger.info("------ProjectTicketsController deleteProjectPriorityFromJson method end------");
	      return new ResponseEntity<String>("{ \"status\":\"true\"}", headers, HttpStatus.OK);
	    } catch (RuntimeException runtimeException) {
	      logger.error("RuntimeException occured in deleteProjectPriorityFromJson method of ProjectTicketsController:"+ runtimeException);
	      throw runtimeException;
	    } catch (Exception exception) {
	      logger.error("Exception occured in deleteProjectPriorityFromJson method of ProjectTicketsController:"+ exception);
	      throw exception;
	    }
  }
  
  @RequestMapping(value = "/saveProjectPriorities",method = RequestMethod.GET,headers = "Accept=application/json",produces = "application/json" )
  @ResponseBody
  public ResponseEntity<String> saveProjectPriorities(@RequestParam(value="projectPrioritiesList") String projectPrioritiesList, @RequestParam(value="projectId") Integer projectId) throws Exception {
			logger.info("------ProjectTicketsController saveProjectPriorities method start------");
			try {

				HttpHeaders headers = new HttpHeaders();
				headers.add("Content-Type", "application/json; charset=utf-8");
				
				projectTicketsService.saveProjectPriorities(projectPrioritiesList, projectId);
								
				logger.info("------ProjectTicketsController saveProjectPriorities method end------");
				return new ResponseEntity<String>("{ \"response\":\"Priorities\"}",headers, HttpStatus.OK); 
				
			} catch (RuntimeException runtimeException) {
				logger.error("RuntimeException occured in saveProjectPriorities method of ProjectTicketsController:"
						+ runtimeException);
				throw runtimeException;
			} catch (Exception exception) {
				logger.error("Exception occured in saveProjectPriorities method of ProjectTicketsController:"
						+ exception);
				throw exception;
			}
  }

  @RequestMapping(value = "/getActiveTicketPrioritiesByProjectId/{projectId}", headers = "Accept=application/json")
  @ResponseBody
  public ResponseEntity<String> getActiveTicketPrioritiesByProjectId(@PathVariable("projectId") Integer projectId)
      throws Exception {
    logger.info("Inside getActiveTicketPrioritiesByProjectId for projectId "+projectId+" on "+this.getClass().getCanonicalName());
    
    List<ProjectTicketPriority> prioritiesList = projectTicketsService.getActiveProjectTicketPriorityForProjectId(projectId);
    List<ProjectTicketPriorityDTO> projectTicketPriorityDTOList=projectTicketsService.convertProjectTicketPriorityListToDTOList(prioritiesList);
    HttpHeaders headers = new HttpHeaders();
    headers.add("Content-Type", "application/json; charset=utf-8");
    
    try {
      if (projectTicketPriorityDTOList == null) {
        return new ResponseEntity<String>("{ \"status\":\"false\"}", headers, HttpStatus.NOT_FOUND);
      }
    } catch (RuntimeException runtimeException) {
      logger.error("RuntimeException occured in getActiveTicketPrioritiesByProjectId method of ProjectTicketsController:"
          + runtimeException);
      throw runtimeException;
    } catch (Exception exception) {
      logger.error(
          "Exception occured in getActiveTicketPrioritiesByProjectId method of "+this.getClass().getCanonicalName()+":-" + exception);
      throw exception;
    }
    logger.info("Inside getActiveTicketPrioritiesByProjectId method end on "+this.getClass().getCanonicalName());

    String jsonString = ProjectTicketPriorityDTO.toJsonArray(projectTicketPriorityDTOList);
    
    return new ResponseEntity<String>(jsonString, headers, HttpStatus.OK);
  }
  
  
  @RequestMapping(value = "/getActiveTicketPrioritiesByResourceAllocationId/{resourceAllocationId}", headers = "Accept=application/json")
  @ResponseBody
  public ResponseEntity<String> getActiveTicketPrioritiesByResourceAllocationId(@PathVariable("resourceAllocationId") Integer resourceAllocationId)
      throws Exception {
    logger.info("Inside getActiveTicketPrioritiesByResourceAllocationId for projectId "+resourceAllocationId+" on "+this.getClass().getCanonicalName());
    
    List<ProjectTicketPriority> prioritiesList = projectTicketsService.getActiveTicketPrioritiesByResourceAllocationId(resourceAllocationId);
    List<ProjectTicketPriorityDTO> projectTicketPriorityDTOList=projectTicketsService.convertProjectTicketPriorityListToDTOList(prioritiesList);
    HttpHeaders headers = new HttpHeaders();
    headers.add("Content-Type", "application/json; charset=utf-8");
    
    try {
      if (projectTicketPriorityDTOList == null) {
        return new ResponseEntity<String>("{ \"status\":\"false\"}", headers, HttpStatus.NOT_FOUND);
      }
    } catch (RuntimeException runtimeException) {
      logger.error("RuntimeException occured in getActiveTicketPrioritiesByResourceAllocationId method of ProjectTicketsController:"
          + runtimeException);
      throw runtimeException;
    } catch (Exception exception) {
      logger.error(
          "Exception occured in getActiveTicketPrioritiesByResourceAllocationId method of "+this.getClass().getCanonicalName()+":-" + exception);
      throw exception;
    }
    logger.info("Inside getActiveTicketPrioritiesByResourceAllocationId method end on "+this.getClass().getCanonicalName());

    String jsonString = ProjectTicketPriorityDTO.toJsonArray(projectTicketPriorityDTOList);
    
    return new ResponseEntity<String>(jsonString, headers, HttpStatus.OK);
  }
  
  
}
