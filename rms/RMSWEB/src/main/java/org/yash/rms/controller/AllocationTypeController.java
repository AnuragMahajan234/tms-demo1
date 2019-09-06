package org.yash.rms.controller;

import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.List;

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
import org.springframework.web.servlet.ModelAndView;
import org.yash.rms.domain.AllocationType;
import org.yash.rms.domain.Resource;
import org.yash.rms.json.mapper.JsonObjectMapper;
import org.yash.rms.service.RmsCRUDService;
import org.yash.rms.util.Constants;
import org.yash.rms.util.UserUtil;

@Controller
@RequestMapping ("/allocationtypes")
public class AllocationTypeController {
	@Autowired
	@Qualifier("allocatioTypeService")
	RmsCRUDService<AllocationType> allocationTypeService;
	@Autowired
	JsonObjectMapper<AllocationType> jsonMapper;
	
	private static final Logger logger = LoggerFactory.getLogger(AllocationTypeController.class);
	
	@Autowired
	private UserUtil userUtil;
	
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView allocationTypeList() throws Exception{
		logger.info("------AllocationTypeController allocationTypeList method start------");
		ModelAndView modelAndView = new ModelAndView("allocationtypes/list");

		//List<Grade> gradeList = gradeServiceImpl.listGrades();
		try{
		List<AllocationType> allocationTypes=allocationTypeService.findAll();
		modelAndView.addObject(Constants.ALLOCATION_TYPE, allocationTypes);
		// Set Header values...
		Resource resource = userUtil.getLoggedInResource();
		
		try{
			if(resource
				      .getUploadImage()!=null && resource.getUploadImage().length>0){
				byte[] encodeBase64UserImage = Base64.encodeBase64(resource.getUploadImage());
				String base64EncodedUser = new String(encodeBase64UserImage, "UTF-8");
				modelAndView.addObject("UserImage", base64EncodedUser);
			}else{
				modelAndView.addObject("UserImage", "");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		modelAndView.addObject("firstName", resource.getFirstName()
				+ " " + resource.getLastName());
		modelAndView.addObject("designationName", resource.getDesignationId().getDesignationName());
		Calendar cal = Calendar.getInstance();
		cal.setTime(resource.getDateOfJoining());
		int m = cal.get(Calendar.MONTH) + 1;
		String months = new DateFormatSymbols().getMonths()[m - 1];
		int year = cal.get(Calendar.YEAR);
		modelAndView.addObject("DOJ", months + "-" + year);
		modelAndView.addObject("ROLE", resource.getUserRole());
		// End 
		}
		catch(RuntimeException runtimeException)
    	{
    		logger.error("RuntimeException occured in allocationTypeList method of AllocationType controller:"+runtimeException);
    		throw runtimeException;
    	}
    	catch(Exception exception){
    		logger.error("Exception occured in allocationTypeList method of AllocationType controller:"+exception);	
    		throw exception;
    	}
		logger.info("------AllocationTypeController allocationTypeList method end------");
		return modelAndView;
	}
	
	@RequestMapping(method = RequestMethod.PUT, headers = "Accept=application/json")
	public ResponseEntity<String> updateFromJson(@RequestBody String json) throws Exception {
		logger.info("------AllocationTypeController updateFromJson method start------");
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		//Convert JSon Object to Domain Object
		//BillingScale billingScale = BillingScaleJsonMapper.fromJsonToBillingScale(json);
		AllocationType allocationType=jsonMapper.fromJsonToObject(json, AllocationType.class);
		
		JSONArray jsonArray=new JSONArray();
		JSONObject jsonObject=new JSONObject();
		
		// Update the 
		try{
		if (!allocationTypeService.saveOrupdate(allocationType)) {
			jsonObject.put("status", "false");
			jsonArray.put(jsonObject);
			return new ResponseEntity<String>(jsonArray.toString(),headers, HttpStatus.EXPECTATION_FAILED);
		}
		}
		catch(RuntimeException runtimeException)
    	{
    		logger.error("RuntimeException occured in updateFromJson method of AllocationType controller:"+runtimeException);
    		return new ResponseEntity<String>("{ \"status\":\"FALSE\"}",headers, HttpStatus.EXPECTATION_FAILED);
//    		throw runtimeException;
    	}
    	catch(Exception exception){
    		logger.error("Exception occured in updateFromJson method of AllocationType controller:"+exception);	
    		return new ResponseEntity<String>("{ \"status\":\"FALSE\"}",headers, HttpStatus.EXPECTATION_FAILED);
//    		throw exception;
    	}
		logger.info("------AllocationTypeController updateFromJson method end------");
		jsonObject.put("status", "true");
		jsonArray.put(jsonObject);
		return new ResponseEntity<String>(jsonArray.toString(),headers, HttpStatus.OK);
	}
	
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
    public ResponseEntity<String> deleteFromJson(@PathVariable("id") Integer id) throws Exception{
    	logger.info("------AllocationTypeController deleteFromJson method start------");
    	

		JSONArray jsonArray=new JSONArray();
		JSONObject jsonObject=new JSONObject();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        //Delete based on ID
        try{
        if (!allocationTypeService.delete(id.intValue())) {
        	jsonObject.put("status", "false");
        	jsonArray.put(jsonObject);
            return new ResponseEntity<String>(jsonArray.toString(),headers, HttpStatus.EXPECTATION_FAILED);
        }
        }
        catch(RuntimeException runtimeException)
    	{
    		logger.error("RuntimeException occured in deleteFromJson method of AllocationType controller:"+runtimeException);
    		return new ResponseEntity<String>("{ \"status\":\"FALSE\"}",headers, HttpStatus.EXPECTATION_FAILED);
//    		throw runtimeException;
    	}
    	catch(Exception exception){
    		logger.error("Exception occured in deleteFromJson method of AllocationType controller:"+exception);	
    		return new ResponseEntity<String>("{ \"status\":\"FALSE\"}",headers, HttpStatus.EXPECTATION_FAILED);
//    		throw exception;
    	}
		logger.info("------AllocationTypeController deleteFromJson method end------");
		jsonObject.put("status", "true");
		jsonArray.put(jsonObject);
        return new ResponseEntity<String>(jsonArray.toString(),headers, HttpStatus.OK);
    }
    
    @RequestMapping(method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> createFromJson(@RequestBody String json) throws Exception{
    	//Convert JSon Object to Domain Object
    			//BillingScale billingScale = BillingScaleJsonMapper.fromJsonToBillingScale(json);
    	logger.info("------AllocationTypeController createFromJson method start------");
    	AllocationType allocationType=jsonMapper.fromJsonToObject(json, AllocationType.class);
    	

    	JSONArray jsonArray=new JSONArray();
		JSONObject jsonObject=new JSONObject();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        try{
        if(!allocationTypeService.saveOrupdate(allocationType)){
        	jsonObject.put("status", "false");
        	jsonArray.put(jsonObject);
        	return new ResponseEntity<String>(jsonArray.toString(),headers, HttpStatus.EXPECTATION_FAILED);
        }
        }
        catch(RuntimeException runtimeException)
    	{
    		logger.error("RuntimeException occured in createFromJson method of AllocationType controller:"+runtimeException);
    		return new ResponseEntity<String>("{ \"status\":\"FALSE\"}",headers, HttpStatus.EXPECTATION_FAILED);
//    		throw runtimeException;
    	}
    	catch(Exception exception){
    		logger.error("Exception occured in createFromJson method of AllocationType controller:"+exception);	
    		return new ResponseEntity<String>("{ \"status\":\"FALSE\"}",headers, HttpStatus.EXPECTATION_FAILED);
//    		throw exception;
    	}
		logger.info("------AllocationTypeController createFromJson method end------");
		jsonObject.put("status", "true");
		jsonArray.put(jsonObject);
        return new ResponseEntity<String>(jsonArray.toString(),headers, HttpStatus.CREATED);
    }
    
  //For US3119 to get all allocation types present
  	@RequestMapping(value = "/allocation", method = RequestMethod.GET, headers = "Accept=application/json")
  	
  	public ResponseEntity<String> allocation() throws Exception {
  		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		String jsonArray = "";
  		List<AllocationType> allocationTypes=allocationTypeService.findAll();
  		jsonArray = jsonMapper.toJsonArray(allocationTypes);
  		return new ResponseEntity<String>(jsonArray,headers, HttpStatus.OK);
  	}
	
}
