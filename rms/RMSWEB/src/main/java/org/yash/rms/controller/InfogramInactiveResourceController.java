package org.yash.rms.controller;

import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.yash.rms.domain.InfogramInactiveResource;
import org.yash.rms.domain.Resource;
import org.yash.rms.dto.InfogramInactiveResourceDTO;
import org.yash.rms.exception.ControllerException;
import org.yash.rms.exception.RMSServiceException;
import org.yash.rms.service.InfogramInactiveResourceService;
import org.yash.rms.util.ExceptionConstant;
import org.yash.rms.util.UserUtil;

/**
 * This controller takes request from client site and sends responses which are
 * received.
 * 
 * @author samiksha.sant
 *
 */

@Controller
public class InfogramInactiveResourceController {
	
	private static final Logger logger = LoggerFactory.getLogger(InfogramInactiveResourceController.class);

	@Autowired
	InfogramInactiveResourceService infogramInactiveResourceService;
	
	@Autowired
	private UserUtil userUtil;


	@RequestMapping(value = "/getinfogramainactiveresources", method = RequestMethod.GET)
	public String list(Model uiModel) {
		
		Resource resource = userUtil.getLoggedInResource();
		try {
			if (resource.getUploadImage() != null
					&& resource.getUploadImage().length > 0) {
				byte[] encodeBase64UserImage = Base64.encodeBase64(resource.getUploadImage());
				String base64EncodedUser = new String(encodeBase64UserImage,"UTF-8");
				uiModel.addAttribute("UserImage", base64EncodedUser);
			} else {
				uiModel.addAttribute("UserImage", "");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		uiModel.addAttribute("firstName", resource.getFirstName()+ " "+ resource.getLastName());
		uiModel.addAttribute("designationName", resource.getDesignationId().getDesignationName());
		
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(resource.getDateOfJoining());
		int m = cal.get(Calendar.MONTH) + 1;
		String months = new DateFormatSymbols().getMonths()[m - 1];
		int year = cal.get(Calendar.YEAR);
		uiModel.addAttribute("DOJ", months + "-" + year);
		uiModel.addAttribute("ROLE", resource.getUserRole());
		
	/*	List<InfogramInactiveResourceDTO> infogramInactiveResourceDTOs = infogramInactiveResourceService.getAllInfogramInactiveResources();
		uiModel.addAttribute("infogramInactiveList", infogramInactiveResourceDTOs);*/
		return "infogramInact/list";
	}

	@RequestMapping(value = "/updateexitdetails/{id}", method = RequestMethod.POST,produces = "application/json")
	@ResponseBody
	public ResponseEntity<String> updateInfogramResourceDetails(@PathVariable("id") Integer id) {
		
		String response = null;
		Boolean isSuccess=false;
		Resource resource = userUtil.getLoggedInResource();
		try {
			isSuccess= infogramInactiveResourceService.updateExitDetails(id, resource);
			if (isSuccess) {
				ControllerException controllerException = new ControllerException("200",ExceptionConstant.OK);
				response = ControllerException.toJsonArray(controllerException);
				return new ResponseEntity<String>(response, HttpStatus.OK);
			}else {
				ControllerException controllerException = new ControllerException("422",ExceptionConstant.RESOURCE_NOT_FOUND);
				response = ControllerException.toJsonArray(controllerException);
				return new ResponseEntity<String>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch(Exception exception) {
			ControllerException controllerException = new ControllerException();
            if(exception instanceof RMSServiceException) {
                  controllerException.setErrCode(((RMSServiceException) exception).getErrCode());
            }else {
            	controllerException.setErrCode("422");
            }
            controllerException.setErrMsg(exception.getMessage());
            logger.error("Exception occured InfogramInactiveResourceController updateexitdetails Not successfully Moved in RMS:" + controllerException.getMessage());
            response = ControllerException.toJsonArray(controllerException);
            return new ResponseEntity<String>(response, HttpStatus.INTERNAL_SERVER_ERROR);
         }
		//return new ResponseEntity<String>(response, HttpStatus.OK);

	}
	
	@RequestMapping(value = "discardinactiveresource/{id}")
	public ResponseEntity<String> discardInfogramInactiveResource(@PathVariable("id") Integer id) {
		HttpHeaders headers = new HttpHeaders();
		try {
			Resource resource = userUtil.getLoggedInResource();
			infogramInactiveResourceService.discardInfogramInactiveResource(id, resource);
			return new ResponseEntity<String>(headers, HttpStatus.OK);
		} catch (Exception exception) {
			return new ResponseEntity<String>(headers, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	
	@RequestMapping(value = "/getinfograminactiveresources",method = RequestMethod.GET)	
	public ResponseEntity<String> getAllInfogramInactiveResources(@RequestParam(value="sEcho") String sEcho,@RequestParam(value="iDisplayStart", defaultValue="0") Integer page, @RequestParam(value="iDisplayLength", defaultValue="10") Integer size,HttpServletRequest request) {

		logger.info("--------getAllInfogramInactiveResources method starts--------");
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		long totalCount=0;
		JSONObject resultJSON = new JSONObject();
		
		if(page!=0) {
			page = (page/size)+1;
		}else {
			page =1;
		}

		List<InfogramInactiveResource> infogramInactiveResourceDTOs = infogramInactiveResourceService.getAllInfogramInactiveResources(request);
		totalCount = totalCountForInfoInactiveRes(request).longValue();

	
		resultJSON.put("sEcho", sEcho);
		resultJSON.put("iTotalRecords", totalCount);
		resultJSON.put("iTotalDisplayRecords", totalCount);
		resultJSON.put("aaData", infogramInactiveResourceDTOs);

        logger.info("--------getAllInfogramInactiveResources method ends--------");
        return new ResponseEntity<String>(resultJSON.toString(), headers, HttpStatus.OK);

	}
	
	private Long totalCountForInfoInactiveRes(HttpServletRequest request){
		HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        Long totalCount = 0L;
        try {
			totalCount = infogramInactiveResourceService.getTotalCountInactResources(request);
		} catch (Exception e) {			
			e.printStackTrace();
		}
        //totalCount = 50L;
        //String temp = "{\"count\":"+totalCount+"}";        
        return totalCount;

	}
	


	@RequestMapping(value = "/infogramInActiveResourceReport", method = RequestMethod.GET)
	public ModelAndView getInfogramInActiveResourceReport(Model uiModel) {
		logger.info("--------getInfogramInActiveResourceReport method starts--------");
		HttpHeaders headers = new HttpHeaders();	
		headers.add("Content-Type", "application/json; charset=utf-8");
		
		List<InfogramInactiveResourceDTO> inActiveResourceList = infogramInactiveResourceService.getInfogramInActiveResourceReport();
		uiModel.addAttribute("inActiveResourceList", inActiveResourceList);
		logger.info("--------infogramActiveResourceReport method ends--------");
		return new ModelAndView("infogramInActiveResourceListExcelView", "model", uiModel);
	}

}
