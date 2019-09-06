package org.yash.rms.controller;

import java.text.DateFormatSymbols;
import java.util.Calendar;

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
import org.yash.rms.domain.Ownership;
import org.yash.rms.domain.Resource;
import org.yash.rms.json.mapper.JsonObjectMapper;
import org.yash.rms.service.RmsCRUDService;
import org.yash.rms.util.Constants;
import org.yash.rms.util.UserUtil;

@Controller
@RequestMapping ("/ownerships")
public class OwnershipController {
	
	

	@Autowired @Qualifier("OwnershipService")
	RmsCRUDService<Ownership> ownershipService;
	
	@Autowired
	JsonObjectMapper<Ownership> jsonMapper;
	private static final Logger logger = LoggerFactory.getLogger(OwnershipController.class);
	
	@Autowired
	private UserUtil userUtil;
	
	@RequestMapping (method = RequestMethod.GET)
	public String list(@RequestParam(value = "page", required = false) Integer page,@RequestParam(value = "size", required = false) Integer size,Model uiModel) throws Exception {
		
		logger.info("------ownershipController list method start------");
		try {
			
		if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute(Constants.OWNERSHIP, ownershipService.findByEntries(firstResult, sizeNo));
            float nrOfPages = (float) ownershipService.countTotal() / sizeNo;
            uiModel.addAttribute(Constants.MAX_PAGES, (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute(Constants.OWNERSHIP, ownershipService.findAll());
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
		
		} catch(RuntimeException runtimeException)
		{				
			logger.error("RuntimeException occured in list method of ownership controller:"+runtimeException);				
			throw runtimeException;
		}catch(Exception exception){
			logger.error("Exception occured in list method of ownership controller:"+exception);				
			throw exception;
		}
		logger.info("------ownershipController list method end------");
        return "ownerships/list";
	}
	
	@RequestMapping(method = RequestMethod.PUT, headers = "Accept=application/json")
	public ResponseEntity<String> updateFromJson(@RequestBody String json,HttpServletRequest request) throws Exception {
		
		logger.info("------ownershipController updateFromJson method start------");
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		ServletContext context=request.getSession().getServletContext();
    	context.getAttribute("userName");
		//Convert JSon Object to Domain Object
    	try {
		Ownership ownership = jsonMapper.fromJsonToObject(json,Ownership.class);
		ownership.setLastUpdatedId((String)context.getAttribute("userName"));
		// Update the 
		if (!ownershipService.saveOrupdate(ownership)) {
			return new ResponseEntity<String>("{ \"status\":\"FALSE\"}",headers, HttpStatus.NOT_FOUND);
		}}catch(RuntimeException runtimeException)
		{				
			logger.error("RuntimeException occured in updateFromJson method of ownership controller:"+runtimeException);				
			throw runtimeException;
		}catch(Exception exception){
			logger.error("Exception occured in updateFromJson method of ownership controller:"+exception);				
			throw exception;
		}
		logger.info("------ownershipController updateFromJson method end------");
       
		return new ResponseEntity<String>("{ \"status\":\"TRUE\"}",headers, HttpStatus.OK);
	}
	
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
    public ResponseEntity<String> deleteFromJson(@PathVariable("id") Integer id) throws Exception {
    	
    	logger.info("------ownershipController deleteFromJson method start------");
    	
    	HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        //Delete based on ID
        try{
        if (!ownershipService.delete(id.intValue())) {
            return new ResponseEntity<String>("{ \"status\":\"FALSE\"}",headers, HttpStatus.NOT_FOUND);
        }}catch(RuntimeException runtimeException)
		{				
			logger.error("RuntimeException occured in deleteFromJson method of ownership controller:"+runtimeException);				
			return new ResponseEntity<String>("{ \"status\":\"FALSE\"}",headers, HttpStatus.EXPECTATION_FAILED);
//			throw runtimeException;
		}catch(Exception exception){
			logger.error("Exception occured in deleteFromJson method of ownership controller:"+exception);				
			return new ResponseEntity<String>("{ \"status\":\"FALSE\"}",headers, HttpStatus.EXPECTATION_FAILED);
//			throw exception;
		}
		logger.info("------ownershipController deleteFromJson method end------");
       
        return new ResponseEntity<String>("{ \"status\":\"TRUE\"}",headers, HttpStatus.OK);
    }
    
    @RequestMapping(method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> createFromJson(@RequestBody String json,HttpServletRequest request) throws Exception {
    	
    	logger.info("------ownershipController createFromJson method start------");
    	
    	ServletContext context=request.getSession().getServletContext();
    	context.getAttribute("userName");
    	//Convert JSon Object to Domain Object
    
    	Ownership ownership = jsonMapper.fromJsonToObject(json, Ownership.class);
    	ownership.setCreatedId((String)context.getAttribute("userName"));
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
    	try{
        if(!ownershipService.saveOrupdate(ownership)){
        	return new ResponseEntity<String>("{ \"status\":\"FALSE\"}",headers, HttpStatus.EXPECTATION_FAILED);
        }}catch(RuntimeException runtimeException)
		{				
			logger.error("RuntimeException occured in createFromJson method of ownership controller:"+runtimeException);				
			throw runtimeException;
		}catch(Exception exception){
			logger.error("Exception occured in createFromJson method of ownership controller:"+exception);				
			throw exception;
		}
		logger.info("------ownershipController createFromJson method end------");
        return new ResponseEntity<String>("{ \"status\":\"TRUE\"}",headers, HttpStatus.CREATED);
    }

}
