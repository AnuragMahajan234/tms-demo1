package org.yash.rms.controller;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
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
import org.yash.rms.domain.InvoiceBy;
import org.yash.rms.domain.OrgHierarchy;
import org.yash.rms.domain.Resource;
import org.yash.rms.json.mapper.JsonObjectMapper;
import org.yash.rms.json.mapper.OrgHierarchyMapper;
import org.yash.rms.service.OrgHierarchyService;
 
import org.yash.rms.service.ResourceService;
import org.yash.rms.util.Constants;
import org.yash.rms.util.UserUtil;
@Controller
@RequestMapping("/orghierarchys")
public class OrgHierarchyController {
	
	@Autowired
	OrgHierarchyService orgHierarchyService;
	
	@Autowired
	JsonObjectMapper<OrgHierarchy> jsonMapper;
	
	/*sarang added start*/
	@Autowired @Qualifier("ResourceService")
	ResourceService resourceServiceImpl;
	/*sarang added end*/
	private static final Logger logger = LoggerFactory.getLogger(OrgHierarchyController.class);
	
	@Autowired
	private UserUtil userUtil;
	
	@RequestMapping(method = RequestMethod.GET, headers = "Accept=application/json")
	public String list(Model uiModel) throws Exception
	 {
		logger.info("------OrgHierarchyController list method start------");
		try{
			//OrgHierarchy orgHierarchy= orgHierarchyService.fingOrgHierarchiesById(1);
			OrgHierarchy orgHierarchy= orgHierarchyService.findOrgHierarchyList();
			 uiModel.addAttribute(Constants.ORG_LIST, orgHierarchy);
			 /*sarang added start*/
			 uiModel.addAttribute(Constants.RESOURCES, resourceServiceImpl.findAllHeads());
			 /*sarang added end*/
			 // Set Header values...
			 
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
				// End 
				
		}
		catch(RuntimeException runtimeException)
    	{
    		logger.error("RuntimeException occured in list method of OrgHierarchyController:"+runtimeException);
    		throw runtimeException;
    	}
    	catch(Exception exception){
    		logger.error("Exception occured in list method of OrgHierarchyController:"+exception);	
    		throw exception;
    	}
		logger.info("------OrgHierarchyController list method end------");
			 return "orghierarchys/list";
		 }
	@RequestMapping(method = RequestMethod.PUT, headers = "Accept=application/json")
	   public ResponseEntity<String> updateFromJson(@RequestBody String json) throws Exception {
		logger.info("------OrgHierarchyController updateFromJson method start------");
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        
       // OrgHierarchy orgHierarchy = OrgHierarchyMapper.fromJsonToOrgHierarchy(json);
        OrgHierarchy orgHierarchy = jsonMapper.fromJsonToObject(json,OrgHierarchy.class);
        try{
        if (!orgHierarchyService.saveOrupdate(orgHierarchy)) {
        	return new ResponseEntity<String>("{ \"status\":\"FALSE\"}",headers, HttpStatus.EXPECTATION_FAILED);
        }
        }
        catch(RuntimeException runtimeException)
    	{
    		logger.error("RuntimeException occured in list method of OrgHierarchyController:"+runtimeException);
    		return new ResponseEntity<String>("{ \"status\":\"FALSE\"}",headers, HttpStatus.EXPECTATION_FAILED);
//    		throw runtimeException;
    	}
    	catch(Exception exception){
    		logger.error("Exception occured in list method of OrgHierarchyController:"+exception);	
    		return new ResponseEntity<String>("{ \"status\":\"FALSE\"}",headers, HttpStatus.EXPECTATION_FAILED);
//    		throw exception;
    	}
        logger.info("------OrgHierarchyController updateFromJson method end------");
        return new ResponseEntity<String>("{ \"status\":\"true\"}",headers, HttpStatus.OK);
    }
	
	
	
	@RequestMapping(method = RequestMethod.POST, headers = "Accept=application/json")
	   public ResponseEntity<String> createFromJson(@RequestBody String json) throws Exception {
		logger.info("------OrgHierarchyController createFromJson method start------");
		
     HttpHeaders headers = new HttpHeaders();
     headers.add("Content-Type", "application/json");
     OrgHierarchy orgHierarchy = OrgHierarchyMapper.fromJsonToOrgHierarchy(json);
      try{
     if(!orgHierarchyService.saveOrupdate(orgHierarchy)){
     	return new ResponseEntity<String>("{ \"status\":\"FALSE\"}",headers, HttpStatus.EXPECTATION_FAILED);
     }
      }
      catch(RuntimeException runtimeException)
  	{
  		logger.error("RuntimeException occured in createFromJson method of OrgHierarchyController:"+runtimeException);
  		return new ResponseEntity<String>("{ \"status\":\"FALSE\"}",headers, HttpStatus.EXPECTATION_FAILED);
//  		throw runtimeException;
  	}
  	catch(Exception exception){
  		logger.error("Exception occured in createFromJson method of OrgHierarchyController:"+exception);	
  		return new ResponseEntity<String>("{ \"status\":\"FALSE\"}",headers, HttpStatus.EXPECTATION_FAILED);
//  		throw exception;
  	}
      logger.info("------OrgHierarchyController createFromJson method end------");

      
     return new ResponseEntity<String>("{ \"status\":\"true\"}",headers, HttpStatus.CREATED);
 }
	
  
    
    @RequestMapping(value = "/{id}", headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<String> showJson(@PathVariable("id") Integer id) throws Exception {
    	logger.info("------OrgHierarchyController showJson method start------");
        OrgHierarchy orgHierarchy = orgHierarchyService.fingOrgHierarchiesById(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        try{
        if (orgHierarchy == null) {
            return new ResponseEntity<String>("{ \"status\":\"FALSE\"}",headers, HttpStatus.NOT_FOUND);
        }
        }
        catch(RuntimeException runtimeException)
      	{
      		logger.error("RuntimeException occured in showJson method of OrgHierarchyController:"+runtimeException);
      		throw runtimeException;
      	}
      	catch(Exception exception){
      		logger.error("Exception occured in showJson method of OrgHierarchyController:"+exception);	
      		throw exception;
      	}
          logger.info("------OrgHierarchyController showJson method end------");

        String jsonString = OrgHierarchyMapper.toJson(orgHierarchy);
        System.out.println("yes"+jsonString);
        return new ResponseEntity<String>(OrgHierarchyMapper.toJson(orgHierarchy), headers, HttpStatus.OK);
    }
    
    
    @RequestMapping(value = "{activate}/{id}",  headers = "Accept=application/json")
    public ResponseEntity<String> deleteFromJson(@PathVariable("id") Integer id,@PathVariable("activate") boolean activateStatus) throws Exception{
    	logger.info("------OrgHierarchyController deleteFromJson method start------");
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
  
     
    	try{ 
      
      if (!orgHierarchyService.activateOrDeactivateOrgHierarchyChild(id, activateStatus)) {
          return new ResponseEntity<String>("{ \"status\":\"FALSE\"}",headers, HttpStatus.EXPECTATION_FAILED);
      }
 
    	}
    	catch(RuntimeException runtimeException)
      	{
      		logger.error("RuntimeException occured in deleteFromJson method of OrgHierarchyController:"+runtimeException);
      		return new ResponseEntity<String>("{ \"status\":\"FALSE\"}",headers, HttpStatus.EXPECTATION_FAILED);
//      		throw runtimeException;
      	}
      	catch(Exception exception){
      		logger.error("Exception occured in deleteFromJson method of OrgHierarchyController:"+exception);	
      		return new ResponseEntity<String>("{ \"status\":\"FALSE\"}",headers, HttpStatus.EXPECTATION_FAILED);
//      		throw exception;
      	}
    	
      return new ResponseEntity<String>("{ \"status\":\"true\"}",headers, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/moveOrgTo", method = RequestMethod.GET)
    public String moveOrg(HttpServletRequest request,Model uiModel) throws Exception
    {
    	logger.info("------OrgHierarchyController moveOrg method start------");
    	try {
			Integer itemid=Integer.parseInt(request.getParameter("itemid"));
			Integer newParentId = Integer.parseInt(request.getParameter("newParentid"));
			 
			orgHierarchyService.move(itemid, newParentId);
		} catch (NumberFormatException e) {
			logger.error("RuntimeException occured in moveOrg method of OrgHierarchyController:"+e);
      		throw e;
		}catch (Exception e) {
			logger.error("Exception occured in moveOrg method of OrgHierarchyController:"+e);
      		throw e;
		}
    	     	 
    	logger.info("------OrgHierarchyController moveOrg method end------");
    	
    	return "orghierarchys/list";
    }
    @RequestMapping(value = "showList", method = RequestMethod.GET)
    public String showList(Model uiModel,@RequestParam Integer id,@RequestParam String orgName) throws Exception
    {logger.info("------OrgHierarchyController showList method start------");
    	
    	System.out.println(id);
    	String oldParentName= orgHierarchyService.fingOrgHierarchiesById(id).getParentId().getName();
    	
    	 try{
    	 uiModel.addAttribute(Constants.ORG_MAP, orgHierarchyService.showHierarhicalPath(id));
    	 
    	uiModel.addAttribute(Constants.MOVE_ID,id);
    	uiModel.addAttribute(Constants.MOVE_NAME,orgName);
    	uiModel.addAttribute(Constants.OLD_PARENT,oldParentName);
    	 }
    	 catch(RuntimeException runtimeException)
       	{
       		logger.error("RuntimeException occured in deleteFromJson method of OrgHierarchyController:"+runtimeException);
       		throw runtimeException;
       	}
       	catch(Exception exception){
       		logger.error("Exception occured in deleteFromJson method of OrgHierarchyController:"+exception);	
       		throw exception;
       	}
    	 logger.info("------OrgHierarchyController showList method end------");
    	return "orghierarchys/showlist";
    }
    
    
	 @RequestMapping(value = "/validate", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<String> validateBG(
			@RequestParam(value = "name") String BGName,
			@RequestParam(value = "parentId") Integer parentId,

			HttpServletResponse response) throws Exception {
		 
		
			HttpHeaders headers = new HttpHeaders();
		// if(orgHierarchyService.validateBG(parentId, BGName));
			 boolean result  =	orgHierarchyService.validateBG(parentId, BGName.toUpperCase());
		 return new ResponseEntity<String>("{ \"result\":" + result + "}",
					headers, HttpStatus.OK);
		 
	 }
	 
	 @RequestMapping(value = "/findResourceList/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<String> findResourceList(
			@PathVariable("id") Integer id,

			HttpServletResponse response) throws Exception {
		 
		
			HttpHeaders headers = new HttpHeaders();
		 
			
			 
		 
			Long count =orgHierarchyService.countResource(id);
			 
		 return new ResponseEntity<String>("{ \"result\":" + count + "}",
					headers, HttpStatus.OK);
		 
	 }
	 
  
}
