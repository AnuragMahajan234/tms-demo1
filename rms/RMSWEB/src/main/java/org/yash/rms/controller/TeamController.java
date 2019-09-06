/**
 * 
 */
package org.yash.rms.controller;

import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.yash.rms.domain.Resource;
import org.yash.rms.domain.Team;
import org.yash.rms.dto.TeamAccessDto;
import org.yash.rms.json.mapper.JsonObjectMapper;
import org.yash.rms.json.mapper.TeamResourceJsonMapper;
import org.yash.rms.service.ResourceService;
import org.yash.rms.service.TeamService;
import org.yash.rms.util.Constants;
import org.yash.rms.util.UserUtil;

/**
 * @author apurva.sinha
 *
 */

@Controller
@RequestMapping(value="/teams")  
public class TeamController {
	@Autowired
	JsonObjectMapper<Team> jsonMapper;
	
	@Autowired
	TeamResourceJsonMapper<TeamAccessDto> jsonTeamMapper;
	
	
	@Autowired
	@Qualifier("teamService")
	TeamService teamServiceImpl;
	
	@Autowired
	@Qualifier("ResourceService")
	ResourceService resourceService;
	
	private static final Logger logger = LoggerFactory.getLogger(TeamController.class);
	
	@Autowired
	private UserUtil userUtil;
	
	  @RequestMapping(method = RequestMethod.POST, headers = "Accept=application/json")
	    public ResponseEntity<String> createFromJson(@RequestBody String json,HttpServletRequest request) throws Exception {
		  logger.info("------TeamController createFromJsonArray method start------");

	    	//Convert JSon Object to Domain Object
	    				        HttpHeaders headers = new HttpHeaders();
	        headers.add("Content-Type", "application/json");
	        ServletContext context = request.getSession().getServletContext();

	      	try{
	Team team=jsonMapper.fromJsonToObject(json, Team.class);

	team.setCreatedId((String) context.getAttribute("userName"));


	if (!teamServiceImpl.saveOrupdate(team)) {
		return new ResponseEntity<String>("{ \"status\":\"FALSE\"}",headers, HttpStatus.EXPECTATION_FAILED);
	}

}catch(RuntimeException runtimeException)
{				
	logger.error("RuntimeException occured in createFromJsonArray method of Team controller:"+runtimeException);				
	if(runtimeException instanceof DataIntegrityViolationException){
		return new ResponseEntity<String>("{ \"status\":\"FALSE\"}",headers, HttpStatus.OK);
	}
}catch(Exception exception){
	logger.error("Exception occured in createFromJsonArray method of Team controller:"+exception);				
	throw exception;
} 
	
logger.info("------TeamController createFromJson  method end------");
return new ResponseEntity<String>("{ \"status\":\"TRUE\"}",headers, HttpStatus.CREATED);

}
	  
	  @RequestMapping(method = RequestMethod.GET)
		public String listOfTeam(Model uiModel) throws Exception {
			logger.info("------TeamController list method start------");
			Resource resource = userUtil.getLoggedInResource();
			//ModelAndView modelAndView = new ModelAndView("team/list");
	        try{
	        	List<Team> teamList = teamServiceImpl.findAll();
	    		uiModel.addAttribute(Constants.TEAM, teamList);
	    		uiModel.addAttribute(Constants.RESOURCES_RM, resourceService.findAll());
	    		
	    		// Set Header values...
	    		
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
	    		// End 
	    		
	        }catch(Exception exception){
	        	logger.error("RuntimeException occured in list method of Team controller:"+exception);				
				throw exception;
	        }
			
	        logger.info("------TeamController list method end------");
			return "teams/list";
		}
		
	  
	  @RequestMapping(method = RequestMethod.PUT, headers = "Accept=application/json")
		public ResponseEntity<String> updateFromJson(@RequestBody String json,HttpServletRequest request) throws Exception {
		 logger.info("------TeamController updateFromJson method start------");

			HttpHeaders headers = new HttpHeaders();
			ServletContext context=request.getSession().getServletContext();
	    	context.getAttribute("userName");
			headers.add("Content-Type", "application/json");
			//Convert JSon Object to Domain Object
			Team team =jsonMapper.fromJsonToObject(json, Team.class);
			team.setLastUpdatedId((String)context.getAttribute("userName"));
			
			try{		
				// Update the Team 
				if (!teamServiceImpl.saveOrupdate(team)) {
					return new ResponseEntity<String>("{ \"status\":\"FALSE\"}",headers, HttpStatus.EXPECTATION_FAILED);
				}			
			}catch(RuntimeException runtimeException)
			{				
				logger.error("RuntimeException occured in updateFromJson method of Team controller:"+runtimeException);				
				throw runtimeException;
			}catch(Exception exception){
				logger.error("Exception occured in updateFromJson method of Team controller:"+exception);				
				throw exception;
			}  
			logger.info("------TeamController updateFromJson method end------");
			return new ResponseEntity<String>("{ \"status\":\"TRUE\"}",headers, HttpStatus.OK);
		}
	 
	 @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	    public ResponseEntity<String> deleteFromJson(@PathVariable("id") Integer id) throws Exception {
		 logger.info("------TeamController createFromJsonArray method start------");
	        HttpHeaders headers = new HttpHeaders();
	        headers.add("Content-Type", "application/json");
	        //Delete based on ID
	        try{
	        	
	        	 if (!teamServiceImpl.delete(id.intValue())) {
	 	            return new ResponseEntity<String>("{ \"status\":\"FALSE\"}",headers, HttpStatus.NOT_FOUND);
	 	        }}catch(RuntimeException runtimeException)
	 			{				
	 				logger.error("RuntimeException occured in deleteFromJson method of Team controller:"+runtimeException);				
	 				return new ResponseEntity<String>("{ \"status\":\"FALSE\"}",headers, HttpStatus.EXPECTATION_FAILED);
//	 				throw runtimeException;
	 			       	
	        }catch(Exception exception){
	        	logger.error("Exception occured in createFromJsonArray method of Team controller:"+exception);				
	        	return new ResponseEntity<String>("{ \"status\":\"FALSE\"}",headers, HttpStatus.EXPECTATION_FAILED);
//	        	throw exception;
	        	
	        }
	        logger.info("------TeamController deleteFromJson  method end------");
	       	        return new ResponseEntity<String>("{ \"status\":\"TRUE\"}",headers, HttpStatus.OK);
	    }
  
 

@RequestMapping(value = "teamViewAccessConfiguratoin/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
public ResponseEntity<String> teamViewAccessConfiguratoin(@PathVariable("id") Integer teamId) throws Exception {
 logger.info("------TeamController teamViewAccessConfiguratoin method start------");
    HttpHeaders headers = new HttpHeaders();
    headers.add("Content-Type", "application/json");
    List<TeamAccessDto> temaccessList = null;
    //Delete based on ID
    try{
    	 temaccessList = teamServiceImpl.getTeamAccessList( teamId);
    	 if (null ==temaccessList) {
	            return new ResponseEntity<String>("{ \"status\":\"FALSE\"}",headers, HttpStatus.NOT_FOUND);
	        }}catch(RuntimeException runtimeException)
			{				
				logger.error("RuntimeException occured in teamViewAccessConfiguratoin method of Team controller:"+runtimeException);				
				return new ResponseEntity<String>("{ \"status\":\"FALSE\"}",headers, HttpStatus.EXPECTATION_FAILED);
//				throw runtimeException;
			       	
    }catch(Exception exception){
    	logger.error("Exception occured in teamViewAccessConfiguratoin method of Team controller:"+exception);				
    	return new ResponseEntity<String>("{ \"status\":\"FALSE\"}",headers, HttpStatus.EXPECTATION_FAILED);
//    	throw exception;
    	
    }
    logger.info("------TeamController teamViewAccessConfiguratoin  method end------");
   	        return new ResponseEntity<String>(jsonTeamMapper.toJsonArray(temaccessList),headers, HttpStatus.OK);
}


@RequestMapping(value = "configureResourceForTeamFromJson", method = RequestMethod.POST, headers = "Accept=application/json")
public ResponseEntity<String> configureResourceForTeamFromJson(@RequestBody String json,HttpServletRequest request) throws Exception {
  logger.info("------TeamController configureResourceForTeamFromJson method start------");

	HttpHeaders headers = new HttpHeaders();
    headers.add("Content-Type", "application/json");
    ServletContext context = request.getSession().getServletContext();

  	try{
TeamAccessDto team=jsonTeamMapper.fromJsonToObject(json, TeamAccessDto.class);

team.setCreatedBy((String) context.getAttribute("userName"));


if (!teamServiceImpl.saveTeamViewRight(team)) {
return new ResponseEntity<String>("{ \"status\":\"FALSE\"}",headers, HttpStatus.EXPECTATION_FAILED);
}

}catch(RuntimeException runtimeException)
{				
logger.error("RuntimeException occured in configureResourceForTeamFromJson method of Team controller:"+runtimeException);				
if(runtimeException instanceof DataIntegrityViolationException){
return new ResponseEntity<String>("{ \"status\":\"FALSE\"}",headers, HttpStatus.OK);
}
}catch(Exception exception){
logger.error("Exception occured in configureResourceForTeamFromJson method of Team controller:"+exception);				
throw exception;
} 

logger.info("------TeamController configureResourceForTeamFromJson  method end------");
return new ResponseEntity<String>("{ \"status\":\"TRUE\"}",headers, HttpStatus.CREATED);

}


@RequestMapping(value = "deleteAccessConfiguratoin/{teamid}/{resourceid}", method = RequestMethod.DELETE, headers = "Accept=application/json")
public ResponseEntity<String> deleteAccessConfiguratoin(@PathVariable("teamid") Integer teamId,@PathVariable("resourceid") Integer resourceId) throws Exception {
 logger.info("------TeamController deleteAccessConfiguratoin method start------");
    HttpHeaders headers = new HttpHeaders();
    headers.add("Content-Type", "application/json");

    //Delete based on ID
    try{
    	  boolean success = teamServiceImpl.deleteResource( teamId,resourceId);
    	 if (!success) {
	            return new ResponseEntity<String>("{ \"status\":\"FALSE\"}",headers, HttpStatus.NOT_FOUND);
	        }}catch(RuntimeException runtimeException)
			{				
				logger.error("RuntimeException occured in deleteAccessConfiguratoin method of Team controller:"+runtimeException);				
				return new ResponseEntity<String>("{ \"status\":\"FALSE\"}",headers, HttpStatus.EXPECTATION_FAILED);
//				throw runtimeException;
			       	
    }catch(Exception exception){
    	logger.error("Exception occured in deleteAccessConfiguratoin method of Team controller:"+exception);				
    	return new ResponseEntity<String>("{ \"status\":\"FALSE\"}",headers, HttpStatus.EXPECTATION_FAILED);
//    	throw exception;
    	
    }
    logger.info("------TeamController deleteAccessConfiguratoin  method end------");
    return new ResponseEntity<String>("{ \"status\":\"TRUE\"}",headers, HttpStatus.OK);
}

}
