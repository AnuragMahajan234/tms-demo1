/**
 * 
 */
package org.yash.rms.controller;

import java.text.SimpleDateFormat;
import java.util.List;

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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.yash.rms.domain.Currency;
import org.yash.rms.domain.ReleaseNotes;
import org.yash.rms.json.mapper.JsonObjectMapper;
import org.yash.rms.service.RmsCRUDService;
import org.yash.rms.util.Constants;

/**
 * @author arpan.badjatiya
 *
 */
@Controller
@RequestMapping ("/releasenotes")
public class ReleaseNotesController {
	
	
	@Autowired 
	@Qualifier("ReleaseNoteService")
	RmsCRUDService<org.yash.rms.dto.ReleaseNotes> releaseNoteService;
	
	@Autowired
	JsonObjectMapper<ReleaseNotes> jsonMapper;
	
	private static final Logger logger = LoggerFactory.getLogger(ReleaseNotesController.class);
	
	@RequestMapping (method = RequestMethod.GET)
	public String list(@RequestParam(value = "page", required = false) Integer page,@RequestParam(value = "size", required = false) Integer size,Model uiModel) throws Exception {
		logger.info("------CurrencyController list method start------");
		 
        	try{
          
        		List list=releaseNoteService.findAll();
        		
            uiModel.addAttribute(Constants.ALL_RELASENOTES, releaseNoteService.findAll());
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
        
		logger.info("------CurrencyController list method end------");
        return "releasenote/list";
	}
	
	 
}
