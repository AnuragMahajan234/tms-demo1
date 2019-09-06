/**
 * 
 */
package org.yash.rms.controller;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import org.yash.rms.domain.Activity;
import org.yash.rms.domain.AllProjectActivities;
import org.yash.rms.domain.Currency;
import org.yash.rms.domain.CustomActivity;
import org.yash.rms.domain.EngagementModel;
import org.yash.rms.domain.InvoiceBy;
import org.yash.rms.domain.Project;
import org.yash.rms.domain.ProjectCategory;
import org.yash.rms.domain.ProjectMethodology;
import org.yash.rms.domain.Resource;
import org.yash.rms.domain.Team;
import org.yash.rms.dto.ProjectDTO;
import org.yash.rms.helper.ProjectHelper;
import org.yash.rms.json.mapper.JsonObjectMapper;
import org.yash.rms.json.mapper.ProjectActivityMapper;
import org.yash.rms.json.mapper.ProjectJasonMapper;
import org.yash.rms.service.ActivityService;
import org.yash.rms.service.CustomerService;
import org.yash.rms.service.OrgHierarchyService;
import org.yash.rms.service.ProjectActivityService;
import org.yash.rms.service.ProjectAllocationService;
import org.yash.rms.service.ProjectService;
import org.yash.rms.service.ResourceService;
import org.yash.rms.service.RmsCRUDService;
import org.yash.rms.util.Constants;
import org.yash.rms.util.UserUtil;

/**
 * @author arpan.badjatiya
 *
 */
@Controller
@RequestMapping("/projectactivitys")
public class ProjectActivityController {

  private static final Logger logger = LoggerFactory.getLogger(ProjectController.class);

  @Autowired
  private JsonObjectMapper<CustomActivity> jsonMapper;

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
  private JsonObjectMapper<Activity> jsonactivityMapper;

  @Autowired
  private ProjectJasonMapper projectJsonMapper;

  @Autowired
  private ProjectHelper projectHelper;

  @Autowired
  private ProjectAllocationService projectAllocationService;

  @Autowired
  @Qualifier("ActivityService")
  private ActivityService activityService;

  @Autowired
  @Qualifier("ProjectActivityService")
  private ProjectActivityService projectActivityService;

  @Autowired
  private UserUtil userUtil;

  @RequestMapping(method = RequestMethod.GET)
  public String getProjectActivities(@RequestParam(value = "page", required = false) Integer page,
      @RequestParam(value = "size", required = false) Integer size, Model uiModel)
          throws Exception {

    logger.info("------ProjectActivityController getProjectActivities method start------");
    try {
     
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
        logger.error("exception occured in list method of ProjectActivityController:" + exception);
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
      logger.info("------ProjectController list method end------");
      return "projectactivity/projectactivities";
    } catch (RuntimeException runtimeException) {
      logger.error(
          "RuntimeException occured in getProjectActivities method of ProjectActivityController:"
              + runtimeException);
      throw runtimeException;
    } catch (Exception exception) {
      logger.error("Exception occured in getProjectActivities method of ProjectActivityController:"
          + exception);
      throw exception;
    }

  }

  @RequestMapping(method = RequestMethod.PUT, headers = "Accept=application/json")
  public ResponseEntity<String> updateProjectActivityFromJson(@RequestBody String json,
      HttpServletRequest request) throws Exception {
    logger.info("------ProjectActivityController updateProjectActivityFromJson method start------");
    HttpHeaders headers = new HttpHeaders();
    headers.add("Content-Type", "application/json");
    ServletContext context = request.getSession().getServletContext();
    context.getAttribute("userName");
    CustomActivity activity = jsonMapper.fromJsonToObject(json, CustomActivity.class);

    try {
      if (!projectActivityService.saveOrupdate(activity)) {
        return new ResponseEntity<String>("{ \"status\":\"false\"}", headers,
            HttpStatus.EXPECTATION_FAILED);
      }
    } catch (RuntimeException runtimeException) {
      logger.error(
          "RuntimeException occured in updateProjectActivityFromJson method of ProjectActivityController:"
              + runtimeException);
      throw runtimeException;
    } catch (Exception exception) {
      logger
          .error("Exception occured in updateProjectActivityFromJson method of ProjectActivityController:"
              + exception);
      throw exception;
    }
    logger.info("------ ProjectActivityController updateFromJson method end------");
    return new ResponseEntity<String>("{ \"status\":\"true\"}", headers, HttpStatus.OK);
  }

  @RequestMapping(method = RequestMethod.POST, headers = "Accept=application/json")
  public ResponseEntity<String> createProjectActivityFromJson(@RequestBody String json,
      HttpServletRequest request) throws Exception {
    logger.info("------ProjectActivityController createProjectActivityFromJson method end------");
    ServletContext context = request.getSession().getServletContext();
    context.getAttribute("userName");
    CustomActivity activity = jsonMapper.fromJsonToObject(json, CustomActivity.class);
    HttpHeaders headers = new HttpHeaders();
    headers.add("Content-Type", "application/json");
    try {
      if (!projectActivityService.saveOrupdate(activity)) {
        return new ResponseEntity<String>("{ \"status\":\"false\"}", headers,
            HttpStatus.EXPECTATION_FAILED);
      }
    } catch (RuntimeException runtimeException) {
      logger.error(
          "RuntimeException occured in createProjectActivityFromJson method of ProjectActivityController:"
              + runtimeException);
      throw runtimeException;

    } catch (Exception exception) {
      logger
          .error("Exception occured in createProjectActivityFromJson method of ProjectActivityController:"
              + exception);
      throw exception;
    }
    logger.info("------ ProjectActivityController createFromJson method end------");
    return new ResponseEntity<String>("{ \"status\":\"true\"}", headers, HttpStatus.CREATED);
  }

  @RequestMapping(value = "/{id}", method = RequestMethod.DELETE,
      headers = "Accept=application/json", produces = "application/json")
  public ResponseEntity<String> deleteProjectActivityFromJson(@PathVariable("id") Integer id)
      throws Exception {
    logger.info("------ProjectActivityController deleteProjectActivityFromJson method start------");
    try {

      HttpHeaders headers = new HttpHeaders();
      headers.add("Content-Type", "application/json");
      // Delete based on ID
      if (!projectActivityService.delete(id.intValue())) {
        return new ResponseEntity<String>("{ \"status\":\"false\"}", headers, HttpStatus.NOT_FOUND);
      }
      logger.info("------ProjectActivityController deleteProjectActivityFromJson method end------");
      return new ResponseEntity<String>("{ \"status\":\"true\"}", headers, HttpStatus.OK);
    } catch (RuntimeException runtimeException) {
      logger.error(
          "RuntimeException occured in deleteProjectActivityFromJson method of ProjectActivityController:"
              + runtimeException);
      throw runtimeException;
    } catch (Exception exception) {
      logger
          .error("Exception occured in deleteProjectActivityFromJson method of ProjectActivityController:"
              + exception);
      throw exception;
    }
  }

  @RequestMapping(value = "/validate", method = RequestMethod.GET)
  @ResponseBody
  public ResponseEntity<String> validateData(
      @RequestParam(value = "projectName") String projectName,
      @RequestParam(value = "projectCode") String projectCode, HttpServletResponse response)
          throws Exception {
    logger.info("------ProjectActivityController validateData method start------");
    try {

      boolean projectResult = true;
      HttpHeaders headers = new HttpHeaders();
      headers.add("Content-Type", "application/json; charset=utf-8");
      List<ProjectDTO> projectQuery = null;
      if (projectName != null && projectName != "") {
        projectQuery = projectAllocationService.findProjectsByProjectNameEquals(projectName);
      }
      if (projectQuery != null) {
        @SuppressWarnings("unchecked")
        List<ProjectDTO> projectList = projectQuery;
        if ((projectList != null) && (!projectList.isEmpty())) {
        	ProjectDTO project = projectList.get(0);
          if (projectName != null) {
            if (project.getProjectName() != null || project.getProjectName() != "") {
              if (projectName.trim().equalsIgnoreCase(project.getProjectName())) {
                projectResult = false;
                if (projectCode != null || projectCode != "") {
                  if (projectCode.equals(project.getProjectCode())) {
                    projectResult = true;
                  } else {
                    projectResult = false;
                  }
                }

              }

            }
          } else {
            projectResult = false;
          }
        }
      } else {
        projectResult = true;
      }
      logger.info("------ProjectActivityController validateData method end------");
      return new ResponseEntity<String>("{ \"projectResult\":" + projectResult + "}", headers,
          HttpStatus.OK);
    } catch (RuntimeException runtimeException) {
      logger.error("RuntimeException occured in validateData method of ProjectActivityController:"
          + runtimeException);
      throw runtimeException;
    } catch (Exception exception) {
      logger.error("Exception occured in validateData method of ProjectActivityController:" + exception);
      throw exception;
    }
  }

  @RequestMapping(value = "/saveProjctActivity",method = RequestMethod.GET,headers = "Accept=application/json",produces = "application/json" )
	@ResponseBody
	public ResponseEntity<String> saveProjctActivity(
			@RequestParam(value="projectActivityList") String projectActivityList,@RequestParam (value="projectActivitySEPGJson") String projectActivitySEPGJson, @RequestParam(value="projectId")Integer projectId ,@RequestParam (value="customActivity") String customActivityList)
			throws Exception {
		logger.info("------ProjectController projectActiveChange method start------");
	 
			try {

				HttpHeaders headers = new HttpHeaders();
				headers.add("Content-Type", "application/json; charset=utf-8");
				List<AllProjectActivities> projectActivityLists = new ArrayList<AllProjectActivities>();
				String[] activityList = projectActivityList.split(",");
				String[] customactivities =customActivityList.split(",");
				String[] projectActivitySEPG = projectActivitySEPGJson.split(",");
				List<Integer> defaultIds= new ArrayList<Integer>();
				List<Integer> sepgIds= new ArrayList<Integer>();
				List<Integer> customIds= new ArrayList<Integer>();
				
				List<Integer> intarray=new ArrayList<Integer>();
			    for(String str:activityList){
			           intarray.add(Integer.parseInt(str));
			         
			          }
				List<AllProjectActivities>	list=	projectActivityService.findBySelectedActivityId(activityList,projectId,null);
				
				if (null != list && list.size() > 0) {

					for (AllProjectActivities pro : list) {
						defaultIds.add(pro.getDefaultActivityid().getId());
					}
				}
				if(intarray.contains(0)){
				}else{
				for(Integer defaultId : intarray) {
				if(!defaultIds.contains(defaultId)){
					AllProjectActivities projectAct = new AllProjectActivities();
					Activity act= new Activity();
					Project project = new Project();
					act =	 activityService.findById(defaultId);
								project.setId(projectId);
								projectAct.setProjectid(project);
								projectAct.setDefaultActivityid(act);
								projectAct.setActivityType(Constants.Default);
								projectAct.setCustomActivityid(null);
								projectAct.setDeactiveFlag(1);
								projectActivityLists.add(projectAct);
						 }
				} }   
                
				
				List<Integer> intarray1=new ArrayList<Integer>();
			    for(String str:projectActivitySEPG){
			           intarray1.add(Integer.parseInt(str));
			          }
			    List<AllProjectActivities>	list1=	projectActivityService.findBySelectedActivityId(projectActivitySEPG,projectId,null);
				if (null != list1 && list1.size() > 0) {

					for (AllProjectActivities pro : list1) {
						sepgIds.add(pro.getDefaultActivityid().getId());
					}
				}
				if(intarray1.contains(0)){
				}else{
					for(Integer sepgId : intarray1) {
						if(!sepgIds.contains(sepgId)){
						AllProjectActivities projectAct = new AllProjectActivities();
						Activity act= new Activity();
						Project project = new Project();
						project.setId(projectId);
						projectAct.setProjectid(project);
						projectAct.setCustomActivityid(null);
						projectAct.setActivityType("SEPG");
						act =	 activityService.findById(sepgId);
						projectAct.setDefaultActivityid(act);
						projectAct.setDeactiveFlag(1);
						projectActivityLists.add(projectAct);
						}
					}
				}
				
				List<Integer> intarray2=new ArrayList<Integer>();
			    for(String str:customactivities){
			           intarray2.add(Integer.parseInt(str));
			         
			          }
				List<AllProjectActivities>	customList=	projectActivityService.findBySelectedActivityId(null,projectId,customactivities);
				
				if (null != customList && customList.size() > 0) {

					for (AllProjectActivities pro : customList) {
						customIds.add(pro.getCustomActivityid().getId());
					}
				}
				if(intarray2.contains(0)){
				}else{
				for (Integer customid : intarray2) {
					if(!customIds.contains(customid)){
					AllProjectActivities projectAct = new AllProjectActivities();
					Project project = new Project();
					project.setId(projectId);
					projectAct.setProjectid(project);
					projectAct.setActivityType("Custom");
					CustomActivity customActivity =	projectActivityService.findByActivityId(customid,projectId);
					projectAct.setCustomActivityid(customActivity);
				    projectAct.setDefaultActivityid(null);
				    projectAct.setDeactiveFlag(1);
					projectActivityLists.add(projectAct);
				 }
				}}
		boolean save =	projectActivityService.saveActivity(projectActivityLists);
		
		List<Integer> ids = new ArrayList<Integer>();
		List<Integer> cusids = new ArrayList<Integer>();
		List<Integer> sepgids = new ArrayList<Integer>();
		List<Integer> p = new ArrayList<Integer>();
		List<Integer> sepg = new ArrayList<Integer>();
		List<Integer> custom = new ArrayList<Integer>();
		if(activityList!=null && activityList.length>0)
		 	for (String customactivityId : activityList) {
		 		p.add(Integer.parseInt(customactivityId));
		  	}
		ids = projectActivityService.findCustomIds(p,Constants.Default,null,projectId);
		if(projectActivitySEPG!=null && projectActivitySEPG.length>0)
		 	for (String customactivityId : projectActivitySEPG) {
		 		sepg.add(Integer.parseInt(customactivityId));
			 	}
		sepgids = projectActivityService.findCustomIds(sepg,Constants.SEPG,null,projectId);
		if(customactivities!=null && customactivities.length>0)
		 	for (String customactivityId : customactivities) {
		 		custom.add(Integer.parseInt(customactivityId));
		  	}  
		cusids = projectActivityService.findCustomIds(null,Constants.CUSTOM,custom,projectId);
		
		if(ids!=null/* && ids.size()>0*/)
		{	
			projectActivityService.updateDefaultToInActive(ids,p,projectId);	 
		}
	
		if(sepgids!=null/* && sepgids.size()>0*/)
		{	
			projectActivityService.updateDefaultToInActive(sepgids,sepg,projectId);	 
		}
		
		if(cusids!=null/* && cusids.size()>0*/){
			
			projectActivityService.updateCustomToInActive(cusids,custom, projectId);	 
		
		}
				
				
			
				logger.info("------ProjectController listUpdatedJson method end------");
				return new ResponseEntity<String>("{ \"status\":\"true\"}",headers, HttpStatus.OK);
			 
			} catch (RuntimeException runtimeException) {
				logger.error("RuntimeException occured in listUpdatedJson method of ProjectController:"
						+ runtimeException);
				throw runtimeException;
			} catch (Exception exception) {
				logger.error("Exception occured in listUpdatedJson method of ProjectController:"
						+ exception);
				throw exception;
			}
		}


  @RequestMapping(value = "/customActivities", produces = "text/html")
  public String list(@RequestParam("projectId") Integer projectId, Model uiModel) throws Exception {
    logger.info("------ ProjectActivityController list method start------");
    try {
      uiModel.addAttribute(Constants.CUSTOM_ACTIVITIES_IN_TIMESHEET,
          projectActivityService.findCustomIDsInTimsheet());
      uiModel.addAttribute(Constants.ACTIVITYS, projectActivityService.findById(projectId, null));
    } catch (RuntimeException runtimeException) {
      logger.error("RuntimeException occured in list method of ProjectActivityController:"
          + runtimeException);
      throw runtimeException;
    } catch (Exception exception) {
      logger.error(
          "Exception occured in list method of ProjectActivityController:" + exception);
      throw exception;
    }
    logger.info("------ProjectActivityController list method end------");
    return "projectactivity/customactivitylist";
  }



  @RequestMapping(value = "/populateActivity/{projectId}", method = RequestMethod.GET,
      headers = "Accept=application/json", produces = "application/json")
  @ResponseBody
  public ResponseEntity<String> showCustomActivities(@PathVariable("projectId") Integer projectId)
      throws Exception {
    logger.info("------ProjectActivityController showCustomActivities method start------");
    List<Integer> activityIds = projectActivityService.projectTypeActivities("Custom", projectId);
    List<CustomActivity> orgHierarchy = projectActivityService.findById(projectId, activityIds);
    HttpHeaders headers = new HttpHeaders();
    headers.add("Content-Type", "application/json; charset=utf-8");
    try {
      if (orgHierarchy == null) {
        return new ResponseEntity<String>("{ \"status\":\"false\"}", headers, HttpStatus.NOT_FOUND);
      }
    } catch (RuntimeException runtimeException) {
      logger.error("RuntimeException occured in showCustomActivities method of ProjectActivityController:"
          + runtimeException);
      throw runtimeException;
    } catch (Exception exception) {
      logger.error(
          "Exception occured in showCustomActivities method of ProjectActivityController:" + exception);
      throw exception;
    }
    logger.info("------ProjectActivityController showCustomActivities method end------");

    String jsonString = ProjectActivityMapper.toJsonArray(orgHierarchy);

    return new ResponseEntity<String>(jsonString, headers, HttpStatus.OK);
  }



  @RequestMapping(value = "/defaultActivity/{projectId}", headers = "Accept=application/json")
  @ResponseBody
  public ResponseEntity<String> showDefaultActivities(@PathVariable("projectId") Integer projectId)
      throws Exception {
    logger.info("------ProjectActivityController showJson method start------");

    List<Integer> activityIds =
        projectActivityService.projectTypeActivities(Constants.Default, projectId);
    List<Activity> defaultActivities = new ArrayList<Activity>();

    List defaultList = new ArrayList();
    defaultList.add("Default");

    if (activityIds != null && activityIds.size() > 0)
      defaultActivities = activityService.findAllByIds(activityIds, defaultList);
    else
      defaultActivities = activityService.findAllByIds(null, defaultList);
    HttpHeaders headers = new HttpHeaders();
    headers.add("Content-Type", "application/json; charset=utf-8");
    try {
      if (defaultActivities == null) {
        return new ResponseEntity<String>("{ \"status\":\"false\"}", headers, HttpStatus.NOT_FOUND);
      }
    } catch (RuntimeException runtimeException) {
      logger.error("RuntimeException occured in showDefaultActivities method of ProjectActivityController:"
          + runtimeException);
      throw runtimeException;
    } catch (Exception exception) {
      logger.error(
          "Exception occured in showDefaultActivities method of ProjectActivityController:" + exception);
      throw exception;
    }
    logger.info("------ProjectActivityController showDefaultActivities method end------");

    String jsonString = jsonactivityMapper.toJsonArray(defaultActivities);

    return new ResponseEntity<String>(jsonString, headers, HttpStatus.OK);
  }



  @RequestMapping(value = "/sepgActivity/{projectId}", headers = "Accept=application/json")
  @ResponseBody
  public ResponseEntity<String> showSEPGActivities(@PathVariable("projectId") Integer projectId)
      throws Exception {
    logger.info("------ProjectActivityController showSEPGActivities method start------");
    List<Activity> sepgActivity = new ArrayList<Activity>();

    HttpHeaders headers = new HttpHeaders();
    headers.add("Content-Type", "application/json; charset=utf-8");
    try {
      List<String> defaultSEPG = new ArrayList<String>();
      defaultSEPG.add("SEPG");
      List<Integer> sepgIds =
          projectActivityService.projectTypeActivities(Constants.SEPG, projectId);
      List<Integer> activityIds =
          projectActivityService.findSEPGActivities(projectId, defaultSEPG, sepgIds);


      if (activityIds != null && activityIds.size() > 0)
        sepgActivity = activityService.findAllBySepgActivity(activityIds);
      else
        sepgActivity = activityService.findAllBySepgActivity(activityIds);
      if (sepgActivity == null || sepgActivity.size() == 0) {
        return new ResponseEntity<String>("{ \"status\":\"false\"}", headers, HttpStatus.NOT_FOUND);
      }
    } catch (RuntimeException runtimeException) {
      logger.error("RuntimeException occured in showSEPGActivities method of ProjectActivityController:"
          + runtimeException);
      throw runtimeException;
    } catch (Exception exception) {
      logger.error(
          "Exception occured in showSEPGActivities method of ProjectActivityController:" + exception);
      throw exception;
    }
    logger.info("------ProjectActivityController showSEPGActivities method end------");

    String jsonString = jsonactivityMapper.toJsonArray(sepgActivity);

    return new ResponseEntity<String>(jsonString, headers, HttpStatus.OK);
  }

  @RequestMapping(value = "/selectedDefaultActivity/{projectId}",
      headers = "Accept=application/json")
  @ResponseBody
  public ResponseEntity<String> showSelectedDefaultActivities(
      @PathVariable("projectId") Integer projectId) throws Exception {
    logger.info("------ProjectActivityController showJson method start------");

    List<Integer> activityIds =
        projectActivityService.projectTypeActivities(Constants.Default, projectId);
    List<Activity> sepgActivity = new ArrayList<Activity>();



    if (activityIds != null && activityIds.size() > 0)
      sepgActivity = activityService.findAllBySepgActivity(activityIds);

    HttpHeaders headers = new HttpHeaders();
    headers.add("Content-Type", "application/json; charset=utf-8");
    try {
      if (sepgActivity == null) {
        return new ResponseEntity<String>("{ \"status\":\"false\"}", headers, HttpStatus.NOT_FOUND);
      }
    } catch (RuntimeException runtimeException) {
      logger.error(
          "RuntimeException occured in showSelectedDefaultActivities method of ProjectActivityController:"
              + runtimeException);
      throw runtimeException;
    } catch (Exception exception) {
      logger
          .error("Exception occured in showSelectedDefaultActivities method of ProjectActivityController:"
              + exception);
      throw exception;
    }
    logger.info("------ ProjectActivityController showSelectedDefaultActivities method end------");

    String jsonString = jsonactivityMapper.toJsonArray(sepgActivity);

    return new ResponseEntity<String>(jsonString, headers, HttpStatus.OK);
  }

  @RequestMapping(value = "/selectedSEPGActivity/{projectId}", headers = "Accept=application/json")
  @ResponseBody
  public ResponseEntity<String> showSelectedSEPGActivities(
      @PathVariable("projectId") Integer projectId) throws Exception {
    logger.info("------ProjectActivityController showJson method start------");

    List<Integer> activityIds =
        projectActivityService.projectTypeActivities(Constants.SEPG, projectId);
    List<Activity> sepgActivity = new ArrayList<Activity>();
    if (activityIds != null && activityIds.size() > 0)
      sepgActivity = activityService.findAllBySepgActivity(activityIds);
    HttpHeaders headers = new HttpHeaders();
    headers.add("Content-Type", "application/json; charset=utf-8");
    try {
      if (sepgActivity == null) {
        return new ResponseEntity<String>("{ \"status\":\"false\"}", headers, HttpStatus.NOT_FOUND);
      }
    } catch (RuntimeException runtimeException) {
      logger.error(
          "RuntimeException occured in showSelectedSEPGActivities method of ProjectActivityController:"
              + runtimeException);
      throw runtimeException;
    } catch (Exception exception) {
      logger.error("Exception occured in showSelectedSEPGActivities method of ProjectActivityController:"
          + exception);
      throw exception;
    }
    logger.info("------ProjectActivityController showSelectedSEPGActivities method end------");

    String jsonString = jsonactivityMapper.toJsonArray(sepgActivity);

    return new ResponseEntity<String>(jsonString, headers, HttpStatus.OK);
  }



  @RequestMapping(value = "/selectedCustomActivity/{projectId}",
      headers = "Accept=application/json")
  @ResponseBody
  public ResponseEntity<String> showSelectedCustomActivities(
      @PathVariable("projectId") Integer projectId) throws Exception {
    logger.info("------ProjectActivityController showSelectedCustomActivities method start------");

    List<Integer> activityIds =
        projectActivityService.projectTypeActivities(Constants.CUSTOM, projectId);
    List<CustomActivity> customActivity = new ArrayList<CustomActivity>();



    if (activityIds != null && activityIds.size() > 0)
      customActivity = projectActivityService.findBySelectedCustomId(activityIds);
    HttpHeaders headers = new HttpHeaders();
    headers.add("Content-Type", "application/json; charset=utf-8");
    try {
      if (customActivity == null) {
        return new ResponseEntity<String>("{ \"status\":\"false\"}", headers, HttpStatus.NOT_FOUND);
      }
    } catch (RuntimeException runtimeException) {
      logger.error(
          "RuntimeException occured in showSelectedCustomActivities method of ProjectActivityController:"
              + runtimeException);
      throw runtimeException;
    } catch (Exception exception) {
      logger.error("Exception occured in showSelectedCustomActivities method of ProjectActivityController:"
          + exception);
      throw exception;
    }
    logger.info("------ProjectActivityController showJson method end------");

    String jsonString = jsonMapper.toJsonArrayModule(customActivity);

    return new ResponseEntity<String>(jsonString, headers, HttpStatus.OK);
  }

}
