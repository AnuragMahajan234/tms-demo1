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
import org.springframework.web.bind.annotation.RequestParam;
import org.yash.rms.domain.EngagementModel;
import org.yash.rms.domain.Resource;
import org.yash.rms.json.mapper.JsonObjectMapper;
import org.yash.rms.service.EngagementModelService;
import org.yash.rms.service.RmsCRUDService;
import org.yash.rms.util.Constants;
import org.yash.rms.util.UserUtil;

@Controller
@RequestMapping ("/engagementmodels")
public class EngagementModelController {
	
	
	private static final Logger logger = LoggerFactory.getLogger(EngagementModelController.class);

	@Autowired @Qualifier("EngagementModelService")
	RmsCRUDService<EngagementModel> engagementModelService;
	
	@Autowired
	JsonObjectMapper<EngagementModel> jsonMapper;
	
	@Autowired
	EngagementModelService engagementModelServices;
	
	@Autowired
	private UserUtil userUtil;
	
	@RequestMapping (method = RequestMethod.GET)
	public String list(@RequestParam(value = "page", required = false) Integer page,@RequestParam(value = "size", required = false) Integer size,Model uiModel) throws Exception {
		logger.info("------EngagementModelController list method start------");

		System.out.println("Shikhi List  :: "+engagementModelServices.getSEPGIdsWithEngagementModel()); 
		uiModel.addAttribute(Constants.SEPG_ID, engagementModelServices.getSEPGIdsWithEngagementModel());
		if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute(Constants.ENGAGEMENTMODEL, engagementModelService.findByEntries(firstResult, sizeNo));
            float nrOfPages = (float) engagementModelService.countTotal() / sizeNo;
            uiModel.addAttribute(Constants.MAX_PAGES, (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {/*{
            uiModel.addAttribute(ENGAGEMENTMODEL, engagementModelService.findAll());
        }*/
        	try{
				uiModel.addAttribute(Constants.ENGAGEMENTMODEL, engagementModelService.findAll());
			}catch(RuntimeException runtimeException)
			{				
				logger.error("RuntimeException occured in list method of EngagementModel controller:"+runtimeException);				
				throw runtimeException;
			}catch(Exception exception){
				logger.error("Exception occured in list method of EngagementModel controller:"+exception);				
				throw exception;
			}
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
				
		logger.info("------EngagementModelController list method end------");
        return "engagementmodels/list";
	}
	
	@RequestMapping(method = RequestMethod.PUT, headers = "Accept=application/json")
	public ResponseEntity<String> updateFromJson(@RequestBody String json,HttpServletRequest request) throws Exception {
		logger.info("------EngagementModelController updateFromJson method start------");

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		ServletContext context=request.getSession().getServletContext();
    	context.getAttribute("userName");
		//Convert JSon Object to Domain Object
		EngagementModel engagementModel = jsonMapper.fromJsonToObject(json, EngagementModel.class);
//		engagementModel.setLastUpdatedId((String)context.getAttribute("userName"));
		// Update the 
		try{		
			// Update the EngagementModel 
			if (!engagementModelService.saveOrupdate(engagementModel)) {
				return new ResponseEntity<String>("{ \"status\":\"false\"}",headers, HttpStatus.EXPECTATION_FAILED);
			}			
		}catch(RuntimeException runtimeException)
		{				
			logger.error("RuntimeException occured in updateFromJson method of EngagementModel controller:"+runtimeException);				
			throw runtimeException;
		}catch(Exception exception){
			logger.error("Exception occured in updateFromJson method of EngagementModel controller:"+exception);				
			throw exception;
		}  
		logger.info("------EngagementModelController updateFromJson method end------");
		return new ResponseEntity<String>("{ \"status\":\"true\"}",headers, HttpStatus.OK);
	}
	
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
    public ResponseEntity<String> deleteFromJson(@PathVariable("id") Integer id) throws Exception {
    	logger.info("------EngagementModelController deleteFromJson method start------");
    	HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        //Delete based on ID
        try {
			if (!engagementModelService.delete(id.intValue())) {
			    return new ResponseEntity<String>("{ \"status\":\"false\"}",headers, HttpStatus.NOT_FOUND);
			}
		}catch(RuntimeException e){
			logger.error("RuntimeException occured in deleteFromJson method of EngagementModel controller:"+e);				
			return new ResponseEntity<String>("{ \"status\":\"FALSE\"}",headers, HttpStatus.EXPECTATION_FAILED);
//			throw e;
		} catch (Exception e) {
			logger.error("Exception occured in deleteFromJson method of EngagementModel controller:"+e);				
			return new ResponseEntity<String>("{ \"status\":\"FALSE\"}",headers, HttpStatus.EXPECTATION_FAILED);
//			throw e;
		}
        
        logger.info("------EngagementModelController deleteFromJson method end------");
        return new ResponseEntity<String>("{ \"status\":\"true\"}",headers, HttpStatus.OK);
    }
    
    @RequestMapping(method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> createFromJson(@RequestBody String json,HttpServletRequest request) throws Exception {
    	logger.info("------EngagementModelController createFromJsonArray method start------");

    	ServletContext context=request.getSession().getServletContext();
    	context.getAttribute("userName");
    	//Convert JSon Object to Domain Object
    	EngagementModel engagementModel = jsonMapper.fromJsonToObject(json, EngagementModel.class);
//    	engagementModel.setCreatedId((String)context.getAttribute("userName"));
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        try{
        	 if(!engagementModelService.saveOrupdate(engagementModel)){
             	return new ResponseEntity<String>("{ \"status\":\"false\"}",headers, HttpStatus.EXPECTATION_FAILED);
             }
        	
        }catch(RuntimeException exception){
        	logger.error("RuntimeException occured in createFromJsonArray method of EngagementModel controller:"+exception);				
			if(exception instanceof DataIntegrityViolationException){
        		return new ResponseEntity<String>("{ \"status\":\"FALSE\"}",headers, HttpStatus.OK);
        	
        }
        }catch(Exception exception){
			logger.error("Exception occured in createFromJsonArray method of EngagementModel controller:"+exception);				
			throw exception;
		} 
       
        logger.info("------EngagementModelController createFromJson  method end------");
        return new ResponseEntity<String>("{ \"status\":\"true\"}",headers, HttpStatus.CREATED);
    }

}
