/**
 * 
 */
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
import org.yash.rms.domain.Designation;
import org.yash.rms.domain.Resource;
import org.yash.rms.json.mapper.JsonObjectMapper;
import org.yash.rms.service.RmsCRUDService;
import org.yash.rms.util.Constants;
import org.yash.rms.util.UserUtil;

/**
 * @author arpan.badjatiya
 *
 */
@Controller
@RequestMapping ("/designationses")
public class DesignationController {
	
	
	@Autowired @Qualifier("DesignationService")
	RmsCRUDService<Designation> designationService;
	
	@Autowired
	JsonObjectMapper<Designation> jsonMapper;

	
	private static final Logger logger = LoggerFactory.getLogger(DesignationController.class);
	
	@Autowired
	private UserUtil userUtil;
	
	@RequestMapping (method = RequestMethod.GET)
	public String list(@RequestParam(value = "page", required = false) Integer page,@RequestParam(value = "size", required = false) Integer size,Model uiModel) {
		logger.info("------DesignationController list method start------");
		if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute(Constants.DESGINATIONS, designationService.findByEntries(firstResult, sizeNo));
            float nrOfPages = (float) designationService.countTotal() / sizeNo;
            uiModel.addAttribute(Constants.MAX_PAGES, (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
        	try{
        		 uiModel.addAttribute(Constants.DESGINATIONS, designationService.findAll());
        	}catch(Exception runtimeException){
        	logger.error("RuntimeException occured in list method of Designation controller:"+runtimeException);				
        }
        	
        }
		
		Resource resource = userUtil.getLoggedInResource();
		// Set Header values...
		
		try{
			if(resource
				      .getUploadImage()!=null &&resource.getUploadImage().length>0){
				byte[] encodeBase64UserImage = Base64.encodeBase64(resource.getUploadImage());
				String base64EncodedUser = new String(encodeBase64UserImage, "UTF-8");
				uiModel.addAttribute("UserImage", base64EncodedUser);
			}else{
				uiModel.addAttribute("UserImage", "");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		uiModel.addAttribute("firstName",resource.getFirstName()
				+ " " +resource.getLastName());
		uiModel.addAttribute("designationName",resource.getDesignationId().getDesignationName());
		Calendar cal = Calendar.getInstance();
		cal.setTime(resource.getDateOfJoining());
		int m = cal.get(Calendar.MONTH) + 1;
		String months = new DateFormatSymbols().getMonths()[m - 1];
		int year = cal.get(Calendar.YEAR);
		uiModel.addAttribute("DOJ", months + "-" + year);
		uiModel.addAttribute("ROLE",resource.getUserRole());
		// End 
		
		logger.info("------DesignationController list method end------");
        return "designationses/list";
	}
	
	@RequestMapping(method = RequestMethod.PUT, headers = "Accept=application/json")
	public ResponseEntity<String> updateFromJson(@RequestBody String json,HttpServletRequest request) throws Exception {
		logger.info("------DesignationController updateFromJson method start------");
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		ServletContext context=request.getSession().getServletContext();
    	context.getAttribute("userName");
		//Convert JSon Object to Domain Object
		Designation designation= jsonMapper.fromJsonToObject(json,Designation.class);
//		designation.setLastUpdatedId((String)context.getAttribute("userName"));
		// Update the 
				try{		
			if (!designationService.saveOrupdate(designation)) {
				return new ResponseEntity<String>("{ \"status\":\"FALSE\"}",headers, HttpStatus.EXPECTATION_FAILED);
			}			
		}catch(RuntimeException runtimeException)
		{				
			logger.error("RuntimeException occured in updateFromJson method of Designation controller:"+runtimeException);				
			throw runtimeException;
		}catch(Exception exception){
			logger.error("Exception occured in updateFromJson method of Designation controller:"+exception);				
			throw exception;
		}  
		logger.info("------DesignationController updateFromJson method end------");
		return new ResponseEntity<String>("{ \"status\":\"TRUE\"}",headers, HttpStatus.OK);
	}
	
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
    public ResponseEntity<String> deleteFromJson(@PathVariable("id") Integer id) {
    	logger.info("------DesignationController deleteFromJson method start------");
    	Boolean status= true;
    	HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        //Delete based on ID
        try{
        if (!designationService.delete(id.intValue())) {
        
        	logger.info("------DesignationController deleteFromJson method end------");
        	//Added for task # 386- Start
        	 status= false;
         //   return new ResponseEntity<String>(headers, HttpStatus.EXPECTATION_FAILED);
        	
        	 
    		return new ResponseEntity<String>("{ \"status\":\"false\"}",headers, HttpStatus.FAILED_DEPENDENCY);
        }
	    }catch(RuntimeException runtimeException)
		{				
			logger.error("RuntimeException occured in deleteFromJson method of ProjectMethodology controller:"+runtimeException);				
			return new ResponseEntity<String>("{ \"status\":\"FALSE\"}",headers, HttpStatus.EXPECTATION_FAILED);
	//		throw runtimeException;
		}catch(Exception exception){
			logger.error("Exception occured in deleteFromJson method of ProjectMethodology controller:"+exception);				
			return new ResponseEntity<String>("{ \"status\":\"FALSE\"}",headers, HttpStatus.EXPECTATION_FAILED);
	//		throw exception;
		}
        logger.info("------DesignationController deleteFromJson method end------");
        return new ResponseEntity<String>("{ \"status\":\"true\"}",headers, HttpStatus.OK);
   	 //Added for task # 386- End
    }
    
    @RequestMapping(method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> createFromJson(@RequestBody String json,HttpServletRequest request)  {
    	logger.info("------DesignationController createFromJsonArray method start------");
    	//Convert JSon Object to Domain Object
    	Designation designation= jsonMapper.fromJsonToObject(json,Designation.class);
    	ServletContext context=request.getSession().getServletContext();
    	context.getAttribute("userName");
//    	designation.setCreatedId((String)context.getAttribute("userName"));
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        try{
        	if(!designationService.saveOrupdate(designation)){
            	return new ResponseEntity<String>("{ \"status\":\"false\"}",headers, HttpStatus.EXPECTATION_FAILED);
            }        }catch(RuntimeException exception) {
            	logger.error("RuntimeException occured in createFromJsonArray method of Designation controller:"+exception);				
    			if(exception instanceof DataIntegrityViolationException){
            		return new ResponseEntity<String>("{ \"status\":\"false\"}",headers, HttpStatus.OK);
            	}

        	
        } catch(Exception ex){
			logger.error("Exception occured in createFromJsonArray method of Designation controller:"+ex);				
		} 
        
        logger.info("------DesignationController createFromJson  method end------");
        return new ResponseEntity<String>("{ \"status\":\"true\"}",headers, HttpStatus.CREATED);
    }

}
