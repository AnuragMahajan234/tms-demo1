/**
 * 
 */
package org.yash.rms.controller;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
import org.yash.rms.dto.BillingScaleDTO;
import org.yash.rms.json.mapper.JsonObjectMapper;
import org.yash.rms.service.RmsCRUDService;
//import org.yash.rms.util.Constants;

/**
 * @author arpan.badjatiya
 *
 */
@Controller
@RequestMapping ("/rateids")
public class BillingScaleController {
	
	
	@Autowired @Qualifier("BillingScaleService")
	RmsCRUDService<BillingScaleDTO> billingScaleService ;
	
	@Autowired
	JsonObjectMapper<BillingScaleDTO> jsonMapper;
	
	private static final Logger logger = LoggerFactory.getLogger(BillingScaleController.class);
	
	/*@RequestMapping (method = RequestMethod.GET)
	public String list(@RequestParam(value = "page", required = false) Integer page,@RequestParam(value = "size", required = false) Integer size,Model uiModel) throws Exception {
		logger.info("------BillingScaleDTOController list method start------");
		
		try{
		if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute(Constants.BILLING_SCALES, BillingScaleDTOService.findByEntries(firstResult, sizeNo));
            float nrOfPages = (float) BillingScaleDTOService.countTotal() / sizeNo;
            uiModel.addAttribute(Constants.MAX_PAGES, (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute(Constants.BILLING_SCALES, BillingScaleDTOService.findAll());
        }}catch(RuntimeException runtimeException)
		{				
			logger.error("RuntimeException occured in list method of BillingScaleDTO controller:"+runtimeException);				
			throw runtimeException;
		}catch(Exception exception){
			logger.error("Exception occured in list method of BillingScaleDTO controller:"+exception);				
			throw exception;
		}
		logger.info("------BillingScaleDTOController list method end------");
       
        return "rateids/list";
	}*/
	
	@RequestMapping(method = RequestMethod.PUT, headers = "Accept=application/json")
	public ResponseEntity<String> updateFromJson(@RequestBody String json,HttpServletRequest request) throws Exception {
		logger.info("------BillingScaleDTOController updateFromJson method start------");
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		ServletContext context=request.getSession().getServletContext();
    	context.getAttribute("userName");
		//Convert JSon Object to Domain Object
    	try{
		BillingScaleDTO billingScale = jsonMapper.fromJsonToObject(json,BillingScaleDTO.class);
		billingScale.setLastUpdatedId((String)context.getAttribute("userName"));
		// Update the 
		if (!billingScaleService.saveOrupdate(billingScale)) {
			return new ResponseEntity<String>(headers, HttpStatus.EXPECTATION_FAILED);
		}}
    	catch(RuntimeException runtimeException)
		{				
			logger.error("RuntimeException occured in updateFromJson method of BillingScale controller:"+runtimeException);				
			throw runtimeException;
		}catch(Exception exception){
			logger.error("Exception occured in updateFromJson method of BillingScale controller:"+exception);				
			throw exception;
		}
		logger.info("------BillingScaleController updateFromJson method end------");
       
		return new ResponseEntity<String>(headers, HttpStatus.OK);
	}
	
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
    public ResponseEntity<String> deleteFromJson(@PathVariable("id") Integer id) throws Exception {
    	logger.info("------BillingScaleController deleteFromJson method start------");
    	HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        //Delete based on ID
        try{
        if (!billingScaleService.delete(id.intValue())) {
            return new ResponseEntity<String>(headers, HttpStatus.EXPECTATION_FAILED);
        }}catch(RuntimeException runtimeException)
		{				
			logger.error("RuntimeException occured in deleteFromJson method of BillingScale controller:"+runtimeException);				
			throw runtimeException;
		}catch(Exception exception){
			logger.error("Exception occured in deleteFromJson method of BillingScale controller:"+exception);				
			throw exception;
		}
		logger.info("------BillingScaleController deleteFromJson method end------");
       
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }
    
    @RequestMapping(method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> createFromJson(@RequestBody String json,HttpServletRequest request) throws Exception {
    	logger.info("------BillingScaleController createFromJson method start------");
    	ServletContext context=request.getSession().getServletContext();
    	HttpHeaders headers = new HttpHeaders();
    	context.getAttribute("userName");
		//Convert JSon Object to Domain Object
    	try{
		BillingScaleDTO billingScale = jsonMapper.fromJsonToObject(json,BillingScaleDTO.class);
		billingScale.setCreatedId((String)context.getAttribute("userName"));
        
        headers.add("Content-Type", "application/json");
        if(!billingScaleService.saveOrupdate(billingScale)){
        	return new ResponseEntity<String>(headers, HttpStatus.EXPECTATION_FAILED);
        }
    	}
    	catch(RuntimeException runtimeException)
		{				
			logger.error("RuntimeException occured in createFromJson method of BillingScale controller:"+runtimeException);				
			throw runtimeException;
		}catch(Exception exception){
			logger.error("Exception occured in createFromJson method of BillingScale controller:"+exception);				
			throw exception;
		}
		logger.info("------BillingScaleController createFromJson method end------");
       
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }

	public RmsCRUDService<BillingScaleDTO> getRmsService() {
		return billingScaleService;
	}

	public void setRmsService(RmsCRUDService<BillingScaleDTO> rmsService) {
		this.billingScaleService = rmsService;
	}

}
