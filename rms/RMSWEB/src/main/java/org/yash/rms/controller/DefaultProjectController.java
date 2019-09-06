
package org.yash.rms.controller;


import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.yash.rms.domain.DefaultProject;
import org.yash.rms.domain.OrgHierarchy;
import org.yash.rms.domain.Resource;
import org.yash.rms.form.DefaultProjectForm;
import org.yash.rms.json.mapper.DefaultProjectJsonMapper;
import org.yash.rms.json.mapper.JsonObjectMapper;
import org.yash.rms.service.DefaultProjectService;
import org.yash.rms.service.OrgHierarchyService;
import org.yash.rms.util.Constants;
import org.yash.rms.util.UserUtil;
/**
 * 
 * @author purva.bhate
 *
 */

@Controller
@RequestMapping ("/defaultprojects")
public class DefaultProjectController {

	@Autowired
	@Qualifier("DefaultProjectService")
	DefaultProjectService defaultProjectService;
	
	
	
	@Autowired
	@Qualifier("OrgHierarchyService")
	OrgHierarchyService buService;
	
	@Autowired
	JsonObjectMapper<OrgHierarchy> orgHierarchyjsonMapper;
	@Autowired
	DefaultProjectJsonMapper jsonObjectMapper;
	@Autowired
	private UserUtil userUtil;
	
	private static final Logger logger = LoggerFactory.getLogger(DefaultProject.class);
	

	// For US3119: method to find all default project list 
	@RequestMapping( method = RequestMethod.GET)
	@Transactional(readOnly = true)
	public String getAllDefaultProjectsList(Model uiModel) throws Exception {
		uiModel.addAttribute(Constants.DEFFAULT_PROJECT_LIST, defaultProjectService.findAll());
		
		// Set Header values...
		Resource resource = userUtil.getLoggedInResource();
		
		try{
			if(resource
				      .getUploadImage()!=null && resource.getUploadImage().length>0){
				byte[] encodeBase64UserImage = Base64.encodeBase64(resource.getUploadImage());
				String base64EncodedUser = new String(encodeBase64UserImage, "UTF-8");
				uiModel.addAttribute("UserImage", base64EncodedUser);
			}else{
				uiModel.addAttribute("UserImage", "");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		uiModel.addAttribute("firstName", resource.getFirstName()
				+ " " + resource.getLastName());
		uiModel.addAttribute("designationName", resource.getDesignationId().getDesignationName());
		Calendar cal = Calendar.getInstance();
		cal.setTime(resource.getDateOfJoining());
		int m = cal.get(Calendar.MONTH) + 1;
		String months = new DateFormatSymbols().getMonths()[m - 1];
		int year = cal.get(Calendar.YEAR);
		uiModel.addAttribute("DOJ", months + "-" + year);
		uiModel.addAttribute("ROLE", resource.getUserRole());
		
		return "mailconfiguration/defaultProject";
	}
	
	// For US3119: method to check if the bG-BU has already a default project set  
@RequestMapping(value="/getAllExistingEntries/{buId}", method = RequestMethod.GET)
@Transactional(readOnly = true)
	public  ResponseEntity<String>  getAllExistingEntries(Model uiModel,@PathVariable("buId") Integer buId) throws Exception {
	String jsonArray = "";
	HttpHeaders headers = new HttpHeaders();
	headers.add("Content-Type", "application/json; charset=utf-8");
	List<DefaultProject> defaultProjects=defaultProjectService.findAll();
	Iterator<DefaultProject> iterator=defaultProjects.iterator();
	while(iterator.hasNext()){
		Integer id=iterator.next().getOrgHierarchy().getId();
		if(buId.equals(id)){
			return new ResponseEntity<String>("{ \"status\":\"false\"}",headers,
					HttpStatus.EXPECTATION_FAILED);
		}
		
	}
		//uiModel.addAttribute(Constants.DEFFAULT_PROJECT_LIST, defaultProjectService.findAll());
	jsonArray = jsonObjectMapper.toJsonArray(defaultProjects);
		return new ResponseEntity<String>("{ \"status\":\"true\"}",headers, HttpStatus.OK);
	}


//For US3119: Method to find all bg-bu present
	@RequestMapping(value="/getAll", method = RequestMethod.GET)
	@Transactional(readOnly = true)
	public  ResponseEntity<String> getAllBU() throws Exception {
	
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		String jsonArray = "";
  		List<OrgHierarchy> allBuTypes=	buService.findAllBusForBGADMIN();
  		jsonArray = orgHierarchyjsonMapper.toJsonArrayOrg(allBuTypes);
  		return new ResponseEntity<String>(jsonArray,headers, HttpStatus.OK);
	}
	

//	For US3119: Method to save new default project 

	@RequestMapping(value="/saveProject",method = RequestMethod.POST)
	@Transactional(readOnly = false)
	public String createFromForm(@ModelAttribute DefaultProjectForm form,Model model)
			throws Exception {
		boolean result=false;
		// Convert JSon Object to Domain Object
		logger.info("------DefaultProjectController createFromJson method start------");
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-Type", "application/json");
		
			if (null !=form) {
		 result=	defaultProjectService.saveOrupdate(form);
			}
			//if(result){
			//	model.addAttribute("result", result);
			//	return "redirect:/defaultprojects";//}
			//return new ResponseEntity<String>("{ \"status\":\"true\"}",headers, HttpStatus.CREATED);
		} catch (RuntimeException runtimeException) {
			logger.error("RuntimeException occured in createFromJson method of ProjectController:"
					+ runtimeException);
			throw runtimeException;
		} catch (Exception exception) {
			logger.error("Exception occured in createFromJson method of ProjectController:"
					+ exception);
			throw exception;
		}
		return "redirect:/defaultprojects";
	}


	
	
	
	
}
