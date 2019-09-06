package org.yash.rms.controller;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.yash.rms.domain.ConfigurationCategory;
import org.yash.rms.domain.Currency;
import org.yash.rms.domain.EngagementModel;
import org.yash.rms.domain.InvoiceBy;
import org.yash.rms.domain.MailConfiguration;
import org.yash.rms.domain.Project;
import org.yash.rms.domain.ProjectCategory;
import org.yash.rms.domain.ProjectMethodology;
import org.yash.rms.domain.ProjectPo;
import org.yash.rms.domain.Resource;
import org.yash.rms.domain.Roles;
import org.yash.rms.domain.Team;
import org.yash.rms.helper.ProjectHelper;
import org.yash.rms.json.mapper.JsonObjectMapper;
import org.yash.rms.json.mapper.ProjectJasonMapper;
import org.yash.rms.service.CustomerService;
import org.yash.rms.service.MailConfigurationService;
import org.yash.rms.service.OrgHierarchyService;
import org.yash.rms.service.ProjectAllocationService;
import org.yash.rms.service.ProjectService;
import org.yash.rms.service.ResourceService;
import org.yash.rms.service.RmsCRUDService;
import org.yash.rms.util.Constants;
import org.yash.rms.util.DozerMapperUtility;
import org.yash.rms.util.UserUtil;

import flexjson.JSONDeserializer;

@Controller
@RequestMapping("/mailConfiguration")
@Transactional
public class MailConfigurationController {

  private static final Logger logger = LoggerFactory.getLogger(ProjectController.class);
  
  @Autowired
  @Qualifier("MailConfigurationService")
  private MailConfigurationService mailConfgService;

  @Autowired
  @Qualifier("ProjectService")
  private ProjectService projectService;

  @Autowired
  @Qualifier("ResourceService")
  private ResourceService resourceServiceImpl;

  @Autowired
  private ResourceService resourceService;

  @Autowired
  private CustomerService customerService;

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
  @Qualifier("RoleService")
  private RmsCRUDService<Roles> roleService;

  @Autowired
  @Qualifier("ConfigurationService")
  private RmsCRUDService<ConfigurationCategory> configurationService;

  @Autowired
  @Qualifier("teamService")
  private RmsCRUDService<Team> teamService;

  @Autowired
  private ProjectJasonMapper jsonMapper;

  @Autowired
  private JsonObjectMapper<MailConfiguration> confgJsonMapper;

  @Autowired
  private ProjectHelper projectHelper;

  @Autowired
  private ProjectAllocationService projectAllocationService;

  @Autowired
  private DozerMapperUtility mapperUtility;

  @Autowired
  private UserUtil userUtil;

  @RequestMapping(method = RequestMethod.GET)
  public String getMailConfigurations(@RequestParam(value = "page", required = false) Integer page,
      @RequestParam(value = "size", required = false) Integer size, Model uiModel)
          throws Exception {
    logger.info("------MailConfigurationController getMailConfigurations method start------");
    try {
     
      uiModel.addAttribute("roleList", roleService.findAll());
      uiModel.addAttribute("confgList", configurationService.findAll());
     
      Resource resource = userUtil.getLoggedInResource();
      
      if (resource.getUploadImage() != null
          && resource.getUploadImage().length > 0) {
        byte[] encodeBase64UserImage =
            Base64.encodeBase64(resource.getUploadImage());
        String base64EncodedUser = new String(encodeBase64UserImage, "UTF-8");
        uiModel.addAttribute("UserImage", base64EncodedUser);
      } else {
        uiModel.addAttribute("UserImage", "");
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

      logger.info("------Mail Configuration Controller getMailConfigurations method end------");
      return "mailconfiguration/mailconfigurations";
    } catch (RuntimeException runtimeException) {
      logger.error(
          "RuntimeException occured in getMailConfigurations method of MailConfigurationController:" + runtimeException);
      throw runtimeException;
    } catch (Exception exception) {
      logger.error("Exception occured in getMailConfigurations method of MailConfigurationController:" + exception);
      throw exception;
    }

  }

  @RequestMapping(method = RequestMethod.PUT, headers = "Accept=application/json")
  public ResponseEntity<String> updateMailConfigurationFromJson(@RequestBody String json, HttpServletRequest request)
      throws Exception {

    logger.info("------MailConfigurationController updateMailConfigurationFromJson method start------");
    try {
      HttpHeaders headers = new HttpHeaders();
      ServletContext context = request.getSession().getServletContext();
      context.getAttribute("userName");
      headers.add("Content-Type", "application/json");
      List<MailConfiguration> mail = new ArrayList<MailConfiguration>();

      List items = new JSONDeserializer<Map<String, List<Map<String, Object>>>>()
          .use("values.values", MailConfiguration.class).deserialize(json, String.class)
          .get("accounting");

      ConfigurationCategory configurtionCatgry = new ConfigurationCategory();
      Project projectId = new Project();
      ListIterator<Map<String, Object>> iterator = items.listIterator();
      while (iterator.hasNext()) {
        Map<String, Object> map = iterator.next();
        MailConfiguration mailConfiguration = new MailConfiguration();

        MailConfiguration configuration =
            mailConfgService.getMailConfg(Integer.parseInt(map.get("projectId").toString()),
                Integer.parseInt(map.get("confgId").toString()),
                Integer.parseInt(map.get("roleId").toString()));

        if (configuration != null)
          mailConfiguration.setId(configuration.getId());
        Project p = new Project();

        p.setId(Integer.parseInt(map.get("projectId").toString()));
        projectId.setId(Integer.parseInt(map.get("projectId").toString()));
        mailConfiguration.setProjectId(p);
        Roles r = new Roles();
        r.setId(Integer.parseInt(map.get("roleId").toString()));
        ConfigurationCategory c = new ConfigurationCategory();
        c.setId(Integer.parseInt(map.get("confgId").toString()));
        configurtionCatgry.setId(Integer.parseInt(map.get("confgId").toString()));
        mailConfiguration.setConfgId(c);
        mailConfiguration.setRoleId(r);
        mailConfiguration.setTo(Boolean.parseBoolean(map.get("to").toString()));
        mailConfiguration.setCc(Boolean.parseBoolean(map.get("cc").toString()));
        mailConfiguration.setBcc(Boolean.parseBoolean(map.get("bcc").toString()));
        mail.add(mailConfiguration);

      }

      List list = mailConfgService.findByProjectId(projectId.getId(), configurtionCatgry.getId());
      Iterator element = list.iterator();

      while (element.hasNext()) {
        MailConfiguration mailConfigurationObj = (MailConfiguration) element.next();
        boolean found = true;
        for (int i = 0; i < mail.size(); i++) {
          if (mailConfigurationObj.getRoleId().getId()!= mail.get(i).getRoleId().getId()) {
            found = false;
            continue;

          } else {
            found = true;
            break;

          }

        }
        if (!found) {
          mailConfgService.delete(mailConfigurationObj.getId());
        }

      }

      mailConfgService.saveConfigurations(mail);
      logger.info("------ProjectController updateMailConfigurationFromJson method end------");
      return new ResponseEntity<String>("{ \"status\":\"true\"}", headers, HttpStatus.OK);
    } catch (RuntimeException runtimeException) {
      logger.error("RuntimeException occured in updateMailConfigurationFromJson method of MailConfigurationController:"
          + runtimeException);
      throw runtimeException;
    } catch (Exception exception) {
      logger.error("Exception occured in updateMailConfigurationFromJson method of MailConfigurationController:" + exception);
      throw exception;
    }
  }

  @RequestMapping(value = "/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
  @ResponseBody
  public ResponseEntity<String> showJson(@PathVariable("id") Integer id) throws Exception {
    logger.info("------ProjectController showJson method start------");
    try {
      HttpHeaders headers = new HttpHeaders();
      headers.add("Content-Type", "application/json; charset=utf-8");
      List<String> configuration = mailConfgService.findById(id);
      JSONObject resultJSON = new JSONObject();
      JSONArray object = new JSONArray();
      for (int i = 0; i < configuration.size(); i++) {
        object.put(i, configuration.get(i).toString());

      }
      resultJSON.put("aaData", object);
      if (configuration == null) {
        return new ResponseEntity<String>("{ \"status\":\"false\"}", headers, HttpStatus.OK);
      }
      logger.info("------ProjectController showJson method end------");
      return new ResponseEntity<String>(resultJSON.toString(), headers, HttpStatus.OK);
    } catch (RuntimeException runtimeException) {
      logger.error(
          "RuntimeException occured in showJson method of ProjectController:" + runtimeException);
      throw runtimeException;
    } catch (Exception exception) {
      logger.error("Exception occured in showJson method of ProjectController:" + exception);
      throw exception;
    }
  }

  @RequestMapping(method = RequestMethod.POST, headers = "Accept=application/json")
  public ResponseEntity<String> createFromJson(@RequestBody String json) throws Exception {
    logger.info("------ProjectController createFromJson method start------");
    try {

      Project project = jsonMapper.fromJsonToProject(json);
      if (project.getId() != null && project.getId() > 0)
        project.setId(null);
      boolean isPoAdded = false;
      Set<ProjectPo> projectPo = null;
      if (project.getProjectPoes() != null && project.getProjectPoes().size() > 0) {
        projectPo = project.getProjectPoes();
        project.setProjectPoes(null);
        isPoAdded = true;
      }

      if (isPoAdded) {
        for (ProjectPo po : projectPo) {
          po.setProjectId(project);
        }
        project.setProjectPoes(projectPo);

        projectService.saveOrupdate(project);
      }

      if (project.getTeam().getId() == -1) {
        project.setTeam(null);
      }

      projectService.saveOrupdate(project);

      HttpHeaders headers = new HttpHeaders();
      headers.add("Content-Type", "application/json");
      if (!projectService.saveOrupdate(project)) {
        return new ResponseEntity<String>("{ \"status\":\"false\"}", headers,
            HttpStatus.EXPECTATION_FAILED);
      }

      logger.info("------ProjectController createFromJson method end------");
      return new ResponseEntity<String>("{ \"status\":\"true\"}", headers, HttpStatus.CREATED);
    } catch (RuntimeException runtimeException) {
      logger.error("RuntimeException occured in createFromJson method of ProjectController:"
          + runtimeException);
      throw runtimeException;
    } catch (Exception exception) {
      logger.error("Exception occured in createFromJson method of ProjectController:" + exception);
      throw exception;
    }
  }

  @RequestMapping(value = "/{projectId}/{confgId}", method = RequestMethod.DELETE,
      headers = "Accept=application/json")
  public ResponseEntity<String> deleteMailConfigurationFromJson(@PathVariable("projectId") Integer projectId,
      @PathVariable("confgId") Integer confgId) throws Exception {
    logger.info("------ProjectController listUpdatedJson method start------");
    try {

      HttpHeaders headers = new HttpHeaders();
      headers.add("Content-Type", "application/json");
      if (!mailConfgService.delete(projectId.intValue(), confgId.intValue())) {
        return new ResponseEntity<String>("{ \"status\":\"false\"}", headers, HttpStatus.NOT_FOUND);
      }
      logger.info("------ProjectController listUpdatedJson method end------");
      return new ResponseEntity<String>("{ \"status\":\"true\"}", headers, HttpStatus.OK);
    } catch (RuntimeException runtimeException) {
      logger.error("RuntimeException occured in listUpdatedJson method of ProjectController:"
          + runtimeException);
      throw runtimeException;
    } catch (Exception exception) {
      logger.error("Exception occured in listUpdatedJson method of ProjectController:" + exception);
      throw exception;
    }
  }

  @RequestMapping(value = "/validate", method = RequestMethod.GET)
  @ResponseBody
  public ResponseEntity<String> validateData(
      @RequestParam(value = "projectName") String projectName,
      @RequestParam(value = "projectCode") String projectCode, HttpServletResponse response)
          throws Exception {
    logger.info("------ProjectController validateData method start------");
    try {

      boolean projectResult = true;
      HttpHeaders headers = new HttpHeaders();
      headers.add("Content-Type", "application/json; charset=utf-8");
      List<org.yash.rms.dto.ProjectDTO> projectQuery = null;
      if (projectName != null && projectName != "") {
        projectQuery = projectAllocationService.findProjectsByProjectNameEquals(projectName);
      }
      if (projectQuery != null) {
        List<org.yash.rms.dto.ProjectDTO> projectList = projectQuery;
        if ((projectList != null) && (!projectList.isEmpty())) {
          org.yash.rms.dto.ProjectDTO project = projectList.get(0);
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
      logger.info("------ProjectController validateData method end------");
      return new ResponseEntity<String>("{ \"projectResult\":" + projectResult + "}", headers,
          HttpStatus.OK);
    } catch (RuntimeException runtimeException) {
      logger.error("RuntimeException occured in validateData method of ProjectController:"
          + runtimeException);
      throw runtimeException;
    } catch (Exception exception) {
      logger.error("Exception occured in validateData method of ProjectController:" + exception);
      throw exception;
    }
  }

}
