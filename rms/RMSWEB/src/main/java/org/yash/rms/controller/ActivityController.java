/**
 * 
 */
package org.yash.rms.controller;

import java.text.DateFormatSymbols;
import java.util.Calendar;

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
import org.yash.rms.domain.Activity;
import org.yash.rms.domain.Resource;
import org.yash.rms.json.mapper.ActivityJsonMapper;
import org.yash.rms.service.ActivityService;
import org.yash.rms.service.RmsCRUDService;
import org.yash.rms.util.Constants;
import org.yash.rms.util.UserUtil;

/**
 * @author arpan.badjatiya
 *
 */
@Controller
@RequestMapping ("/activitys")
public class ActivityController {
	
	// controller for activity
	@Autowired @Qualifier("ActivityService")
	RmsCRUDService<Activity> activityService;

	
	private static final Logger logger = LoggerFactory.getLogger(ActivityController.class);
	
	@Autowired
	private UserUtil userUtil;
	
	@RequestMapping (method = RequestMethod.GET)
	public String list(@RequestParam(value = "page", required = false) Integer page,@RequestParam(value = "size", required = false) Integer size,Model uiModel) throws Exception {
		logger.info("------ActivityController list method start------");
		try{
		if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute(Constants.ACTIVITYS, activityService.findByEntries(firstResult, sizeNo));
            float nrOfPages = (float) activityService.countTotal() / sizeNo;
            uiModel.addAttribute(Constants.MAX_PAGES, (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
        	
            uiModel.addAttribute(Constants.ACTIVITYS, activityService.findAll());
            //uiModel.addAttribute("activityTypes", activityService.findAllActivityTypes());
            
        	} 
		
		// Set Header values...
		Resource resource= userUtil.getLoggedInResource();
		
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
        		logger.error("RuntimeException occured in list method of Customer controller:"+runtimeException);
        		throw runtimeException;
        	}
        	catch(Exception exception){
        		logger.error("Exception occured in list method of Customer controller:"+exception);	
        		throw exception;
        	}
		return "activitys/list";
	}
 
	@RequestMapping(method = RequestMethod.PUT, headers = "Accept=application/json")
	public @ResponseBody ResponseEntity<String> updateFromJson(@RequestBody String json,HttpServletRequest request) throws Exception {
		logger.info("------CustomerController updateFromJson method start------");
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		ServletContext context=request.getSession().getServletContext();
    	context.getAttribute("userName");
		//Convert JSon Object to Domain Object
    	ActivityJsonMapper jsonMapper= new ActivityJsonMapper();
		Activity activity= jsonMapper.fromJsonToObject(json,Activity.class);
		
		JSONArray jsonArray=new JSONArray();
		JSONObject jsonObject=new JSONObject();
		
//		activity.setLastUpdatedId((String)context.getAttribute("userName"));
		// Update the 
		try{
		if (!activityService.saveOrupdate(activity)) {
			jsonObject.put("status", "false");
			jsonArray.put(jsonObject);
			return new ResponseEntity<String>(jsonArray.toString(),headers, HttpStatus.EXPECTATION_FAILED);
		}
		}
		catch(RuntimeException runtimeException)
		{				
			logger.error("RuntimeException occured in updateFromJson method of Activity controller:"+runtimeException);				
			throw runtimeException;
		}catch(Exception exception){
			logger.error("Exception occured in updateFromJson method of Activity controller:"+exception);				
			throw exception;
		}  
		logger.info("------ActivityController updateFromJson method end------");
		jsonObject.put("status", "true");
		jsonArray.put(jsonObject);
		return new ResponseEntity<String>(jsonArray.toString(),headers, HttpStatus.OK);
	}
	
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
    public ResponseEntity<String> deleteFromJson(@PathVariable("id") Integer id) throws Exception{
    	logger.info("------ActivityController deleteFromJson method start------");
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        
        JSONArray jsonArray=new JSONArray();
		JSONObject jsonObject=new JSONObject();
		
        //Delete based on ID
        try{
        if (!activityService.delete(id.intValue())) {
        	jsonObject.put("status", "false");
			jsonArray.put(jsonObject);
            return new ResponseEntity<String>(jsonArray.toString(),headers, HttpStatus.EXPECTATION_FAILED);
        }
        }
        catch(RuntimeException runtimeException)
        {
        	logger.error("RuntimeException occured in deleteFromJson method of Activity controller:"+runtimeException);		
        	jsonObject.put("status", "false");
			jsonArray.put(jsonObject);
            return new ResponseEntity<String>(jsonArray.toString(),headers, HttpStatus.EXPECTATION_FAILED);
//			throw runtimeException;
        	
        }
        catch(Exception exception)
        {
        	logger.error("Exception occured in updateFromJson method of Activity controller:"+exception);		
        	jsonObject.put("status", "false");
			jsonArray.put(jsonObject);
            return new ResponseEntity<String>(jsonArray.toString(),headers, HttpStatus.EXPECTATION_FAILED);
//			throw exception;
        }
        logger.info("------ActivityController deleteFromJson method end------");
        jsonObject.put("status", "true");
		jsonArray.put(jsonObject);
        return new ResponseEntity<String>(jsonArray.toString(),headers, HttpStatus.OK);
    }
    
    @RequestMapping(method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> createFromJson(@RequestBody String json,HttpServletRequest request) throws Exception {
		//Convert JSon Object to Domain Object
    	logger.info("------ActivityController createFromJson method end------");
    	ServletContext context=request.getSession().getServletContext();
    	context.getAttribute("userName");
    
		Activity activity= new ActivityJsonMapper().fromJsonToObject(json,Activity.class);
//		activity.setCreatedId((String)context.getAttribute("userName"));
		JSONArray jsonArray=new JSONArray();
		JSONObject jsonObject=new JSONObject();
		
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        try{
        if(!activityService.saveOrupdate(activity)){
        	jsonObject.put("status", "true");
    		jsonArray.put(jsonObject);
        	return new ResponseEntity<String>(jsonArray.toString(),headers, HttpStatus.EXPECTATION_FAILED);
        }
        }
        catch(RuntimeException runtimeException)
        {
        	logger.error("RuntimeException occured in createFromJson method of Activity controller:"+runtimeException);				
			throw runtimeException;
        	
        }
        catch(Exception exception)
        {
        	logger.error("Exception occured in createFromJson method of Activity controller:"+exception);				
			throw exception;
        }
        logger.info("------ActivityController createFromJson method end------");
        jsonObject.put("status", "true");
		jsonArray.put(jsonObject);
        return new ResponseEntity<String>(jsonArray.toString(),headers, HttpStatus.CREATED);
    }



@RequestMapping (value="/sepgActivity",method = RequestMethod.GET)
public String sepgActivity(@RequestParam(value = "page", required = false) Integer page,@RequestParam(value = "size", required = false) Integer size,Model uiModel) throws Exception {
	logger.info("------ActivityController SEPG list method start------");
	try{
	if (page != null || size != null) {
        int sizeNo = size == null ? 10 : size.intValue();
        final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
        uiModel.addAttribute(Constants.ACTIVITYS, activityService.findByEntries(firstResult, sizeNo));
        float nrOfPages = (float) activityService.countTotal() / sizeNo;
        uiModel.addAttribute(Constants.MAX_PAGES, (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
    } else {
    	
        uiModel.addAttribute(Constants.ACTIVITYS, ((ActivityService) activityService).findSepgActivity("SEPG"));
        //uiModel.addAttribute("activityTypes", activityService.findAllActivityTypes());
        
    	} 
	
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
    		logger.error("RuntimeException occured in list method of Customer controller:"+runtimeException);
    		throw runtimeException;
    	}
    	catch(Exception exception){
    		logger.error("Exception occured in list method of Customer controller:"+exception);	
    		throw exception;
    	}
	return "activitys/sepgActivity";
}
}
