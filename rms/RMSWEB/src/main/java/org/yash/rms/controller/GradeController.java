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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.yash.rms.domain.Grade;
import org.yash.rms.domain.Resource;
import org.yash.rms.json.mapper.JsonObjectMapper;
import org.yash.rms.service.RmsCRUDService;
import org.yash.rms.util.Constants;
import org.yash.rms.util.UserUtil;

@Controller
@RequestMapping(value="/grades")  
public class GradeController {
	
	@Autowired @Qualifier("gradeService")
	RmsCRUDService<Grade> gradeServiceImpl;
	
	@Autowired
	JsonObjectMapper<Grade> jsonMapper;
	
	private static final Logger logger = LoggerFactory.getLogger(GradeController.class);
	
	@Autowired
	private UserUtil userUtil;
	
	  @RequestMapping(method = RequestMethod.POST, headers = "Accept=application/json")
	    public ResponseEntity<String> createFromJson(@RequestBody String json,HttpServletRequest request) throws Exception {
		  logger.info("------GradeController createFromJsonArray method start------");

	    	//Convert JSon Object to Domain Object
	    				        HttpHeaders headers = new HttpHeaders();
	        headers.add("Content-Type", "application/json");
	        ServletContext context = request.getSession().getServletContext();

	       /* if(!gradeServiceImpl.saveOrupdate(grade)){
	        	return new ResponseEntity<String>(headers, HttpStatus.EXPECTATION_FAILED);
	        }
	        
	        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
*/	try{
	Grade grade=jsonMapper.fromJsonToObject(json, Grade.class);

	grade.setCreatedId((String) context.getAttribute("userName"));


	if (!gradeServiceImpl.saveOrupdate(grade)) {
		return new ResponseEntity<String>("{ \"status\":\"FALSE\"}",headers, HttpStatus.EXPECTATION_FAILED);
	}

}catch(RuntimeException runtimeException)
{				
	logger.error("RuntimeException occured in createFromJsonArray method of Grade controller:"+runtimeException);				
	if(runtimeException instanceof DataIntegrityViolationException){
		return new ResponseEntity<String>("{ \"status\":\"FALSE\"}",headers, HttpStatus.OK);
	}
}catch(Exception exception){
	logger.error("Exception occured in createFromJsonArray method of Grade controller:"+exception);				
	throw exception;
} 
	
logger.info("------GradeController createFromJson  method end------");
return new ResponseEntity<String>("{ \"status\":\"TRUE\"}",headers, HttpStatus.CREATED);

}
    
	        
	  

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView listOfGrade() throws Exception {
		logger.info("------GradeController list method start------");
		ModelAndView modelAndView = new ModelAndView("grades/list");
        try{
        	List<Grade> gradeList = gradeServiceImpl.findAll();
    		modelAndView.addObject(Constants.GRADE, gradeList);
        	
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
			
        }catch(Exception exception){
        	logger.error("RuntimeException occured in list method of Grade controller:"+exception);				
			throw exception;
        }
		
        logger.info("------GradeController list method end------");
		return modelAndView;
	}
	
	 
	 @RequestMapping(method = RequestMethod.PUT, headers = "Accept=application/json")
		public ResponseEntity<String> updateFromJson(@RequestBody String json,HttpServletRequest request) throws Exception {
		 logger.info("------GradeController updateFromJson method start------");

			HttpHeaders headers = new HttpHeaders();
			ServletContext context=request.getSession().getServletContext();
	    	context.getAttribute("userName");
			headers.add("Content-Type", "application/json");
			//Convert JSon Object to Domain Object
			Grade grade =jsonMapper.fromJsonToObject(json, Grade.class);
			grade.setLastUpdatedId((String)context.getAttribute("userName"));
			// Update the 
			/*if (!gradeServiceImpl.saveOrupdate(grade)) {
				return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<String>(headers, HttpStatus.OK);*/
			try{		
				// Update the Grade 
				if (!gradeServiceImpl.saveOrupdate(grade)) {
					return new ResponseEntity<String>("{ \"status\":\"FALSE\"}",headers, HttpStatus.EXPECTATION_FAILED);
				}			
			}catch(RuntimeException runtimeException)
			{				
				logger.error("RuntimeException occured in updateFromJson method of Grade controller:"+runtimeException);				
				throw runtimeException;
			}catch(Exception exception){
				logger.error("Exception occured in updateFromJson method of Grade controller:"+exception);				
				throw exception;
			}  
			logger.info("------GradeController updateFromJson method end------");
			return new ResponseEntity<String>("{ \"status\":\"TRUE\"}",headers, HttpStatus.OK);
		}
	 
	 @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	    public ResponseEntity<String> deleteFromJson(@PathVariable("id") Integer id) throws Exception {
		 logger.info("------GradeController createFromJsonArray method start------");
	        HttpHeaders headers = new HttpHeaders();
	        headers.add("Content-Type", "application/json");
	        //Delete based on ID
	        try{
	        	
	        	 if (!gradeServiceImpl.delete(id.intValue())) {
	 	            return new ResponseEntity<String>("{ \"status\":\"FALSE\"}",headers, HttpStatus.NOT_FOUND);
	 	        }

	        	
	        }catch(Exception exception){
	        	logger.error("Exception occured in createFromJsonArray method of Grade controller:"+exception);				
	        	return new ResponseEntity<String>("{ \"status\":\"FALSE\"}",headers, HttpStatus.EXPECTATION_FAILED);
//	        	throw exception;
	        	
	        }
	        logger.info("------GradeController deleteFromJson  method end------");
	       	        return new ResponseEntity<String>("{ \"status\":\"TRUE\"}",headers, HttpStatus.OK);
	    }

}
