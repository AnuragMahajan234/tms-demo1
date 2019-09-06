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
import org.yash.rms.domain.Currency;
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
@RequestMapping ("/currencys")
public class CurrencyController {
	
	
	@Autowired @Qualifier("CurrencyService")
	RmsCRUDService<Currency> currencyService;
	
	@Autowired
	JsonObjectMapper<Currency> jsonMapper;
	
	private static final Logger logger = LoggerFactory.getLogger(CurrencyController.class);
	
	@Autowired
	private UserUtil userUtil;
	
	@RequestMapping (method = RequestMethod.GET)
	public String list(@RequestParam(value = "page", required = false) Integer page,@RequestParam(value = "size", required = false) Integer size,Model uiModel) throws Exception {
		logger.info("------CurrencyController list method start------");
		if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute(Constants.ALL_CURRENCY, currencyService.findByEntries(firstResult, sizeNo));
            float nrOfPages = (float) currencyService.countTotal() / sizeNo;
            uiModel.addAttribute(Constants.MAX_PAGES, (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
        	try{
            uiModel.addAttribute(Constants.ALL_CURRENCY, currencyService.findAll());
        	}
        	catch(RuntimeException runtimeException)
        	{
        		logger.error("RuntimeException occured in list method of Currencycontroller:"+runtimeException);
        		throw runtimeException;
        	}
        	catch(Exception exception){
        		logger.error("Exception occured in list method of Currencycontroller:"+exception);	
        		throw exception;
        	}
        }
		
		Resource resource = userUtil.getLoggedInResource();
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
		
		logger.info("------CurrencyController list method end------");
        return "currencys/list";
	}
	
	@RequestMapping(method = RequestMethod.PUT, headers = "Accept=application/json")
	public ResponseEntity<String> updateFromJson(@RequestBody String json,HttpServletRequest request) throws Exception{
		logger.info("------CurrencyController updateFromJson method start------");
		HttpHeaders headers = new HttpHeaders();
				ServletContext context=request.getSession().getServletContext();
    	context.getAttribute("userName");
		headers.add("Content-Type", "application/json");
		//Convert JSon Object to Domain Object
		Currency currency = jsonMapper.fromJsonToObject(json,Currency.class);
		currency.setLastUpdatedId((String)context.getAttribute(Constants.USER_NAME));
		// Update the 
		try{
		if (!currencyService.saveOrupdate(currency)) {
			return new ResponseEntity<String>("{ \"status\":\"false\"}",headers, HttpStatus.EXPECTATION_FAILED);
		}
		}
		catch(RuntimeException runtimeException)
    	{
    		logger.error("RuntimeException occured in updateFromJson method of Currency controller:"+runtimeException);
    		throw runtimeException;
    	}
    	catch(Exception exception){
    		logger.error("Exception occured in updateFromJson method of Currency controller:"+exception);	
    		throw exception;
    	}
		logger.info("------CurrencyController updateFromJson method end-----");
		return new ResponseEntity<String>("{ \"status\":\"true\"}",headers, HttpStatus.OK);
	}
	
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
    public ResponseEntity<String> deleteFromJson(@PathVariable("id") Integer id) throws Exception{
		logger.info("------CurrencyController deleteFromJson method start------");
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        //Delete based on ID
        try{
        if (!currencyService.delete(id.intValue())) {
            return new ResponseEntity<String>("{ \"status\":\"false\"}",headers, HttpStatus.EXPECTATION_FAILED);
        }
        }
        catch(RuntimeException runtimeException)
    	{
    		logger.error("RuntimeException occured in deleteromJson method of Currency controller:"+runtimeException);
    		return new ResponseEntity<String>("{ \"status\":\"FALSE\"}",headers, HttpStatus.EXPECTATION_FAILED);
//    		throw runtimeException;
    	}
    	catch(Exception exception){
    		logger.error("Exception occured in deleteFromJson method of Currency controller:"+exception);	
    		return new ResponseEntity<String>("{ \"status\":\"FALSE\"}",headers, HttpStatus.EXPECTATION_FAILED);
//    		throw exception;
    	}
		logger.info("------CurrencyController deleteFromJson method end------");
        return new ResponseEntity<String>("{ \"status\":\"true\"}",headers, HttpStatus.OK);
    }
    
    @RequestMapping(method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> createFromJson(@RequestBody String json,HttpServletRequest request) throws Exception{
    	logger.info("------CurrencyController createFromJson method start------");
    	ServletContext context=request.getSession().getServletContext();
    	context.getAttribute("userName");
		//Convert JSon Object to Domain Object
    	Currency currency = jsonMapper.fromJsonToObject(json,Currency.class);
    	currency.setCreatedId((String)context.getAttribute(Constants.USER_NAME));
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        try{
        if(!currencyService.saveOrupdate(currency)){
        	return new ResponseEntity<String>("{ \"status\":\"false\"}",headers, HttpStatus.EXPECTATION_FAILED);
        }
        }
        catch(RuntimeException runtimeException)
    	{
    		logger.error("RuntimeException occured in createromJson method of Currency controller:"+runtimeException);
    		throw runtimeException;
    	}
    	catch(Exception exception){
    		logger.error("Exception occured in createFromJson method of Currency controller:"+exception);	
    		throw exception;
    	}
        logger.info("------CurrencyController createFromJson method end------");
        return new ResponseEntity<String>("{ \"status\":\"true\"}",headers, HttpStatus.CREATED);
    }

}
