package org.yash.rms.controller;

import java.io.IOException;
import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.yash.rms.domain.InfogramActiveResource;
import org.yash.rms.domain.Resource;
import org.yash.rms.dto.InfogramActiveResourceDTO;
import org.yash.rms.exception.ControllerException;
import org.yash.rms.exception.RMSServiceException;
import org.yash.rms.helper.InfogramResourceHelper;
import org.yash.rms.service.InfogramActiveResourceService;
import org.yash.rms.util.ExceptionConstant;
import org.yash.rms.util.UserUtil;

import flexjson.JSONDeserializer;

/**
 * This controller helps to map front end with backend. Hence all the requests
 * related to infogram data transformation takes place from here.
 * 
 * @author samiksha.sant
 *
 */
@Controller
//@RequestMapping("/infogram")
public class InfogramActiveResourceController {

	private static final Logger logger = LoggerFactory.getLogger(InfogramActiveResourceController.class);

	@Autowired
	InfogramActiveResourceService infogramActiveResourceService;
	
	@Autowired
	InfogramResourceHelper infogramResourceHelper;
	
	@Autowired
	private UserUtil userUtil;

	@RequestMapping(value = "/getallinfogramactiveresources", method = RequestMethod.GET)
	public String getAllInfogramActiveResources(Model uiModel) {

		logger.info("--------getAllInfogramActiveResources method starts--------");
		/*Integer page = Integer.parseInt(request.getParameter("iDisplayStart"));
		Integer size = Integer.parseInt(request.getParameter("iDisplayLength"));*/
	

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
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(resource.getDateOfJoining());
		int m = cal.get(Calendar.MONTH) + 1;
		String months = new DateFormatSymbols().getMonths()[m - 1];
		int year = cal.get(Calendar.YEAR);
		uiModel.addAttribute("DOJ", months + "-" + year);
		uiModel.addAttribute("ROLE", resource.getUserRole());
		uiModel.addAttribute("firstName", resource.getFirstName()+ " "+ resource.getLastName());
		uiModel.addAttribute("designationName", resource.getDesignationId().getDesignationName());
		
	/*	List<InfogramActiveResourceDTO> activeResourceDTOs = infogramActiveResourceService.getAllInfogramActiveResource(page,size);
		 JSONObject resultJSON = new JSONObject();
		
		 	resultJSON.put("aaData", activeResourceDTOs);
			resultJSON.put("iTotalRecords", 100);
			resultJSON.put("iTotalDisplayRecords", 10);*/
			
       // uiModel.addAttribute("infogramList", activeResourceDTOs);
        logger.info("--------getAllInfogramActiveResources method ends--------");
        return "infogram/list";

	}
	
	@RequestMapping(value = "/saveinfogramactiveresource/{infoId}", method = RequestMethod.POST,produces = "application/json")
	@ResponseBody
	public ResponseEntity<String> saveInfogramActiveResource(@PathVariable("infoId") Integer id) {
		
		ControllerException controllerException = new ControllerException();
		String response = null;
		Boolean isSuccess=false;
		Resource resource=null;
		try
		{
			resource=userUtil.getLoggedInResource();
			isSuccess= infogramActiveResourceService.saveInfogramActiveResource(id, resource);
			if (isSuccess) {
				controllerException.setErrCode("200");
				controllerException.setErrMsg(ExceptionConstant.OK);
				response = ControllerException.toJsonArray(controllerException);
				return new ResponseEntity<String>(response, HttpStatus.OK);
			}
		}
		catch(Exception exception) {
			exception.printStackTrace();
            if(exception instanceof RMSServiceException) {
                  controllerException.setErrCode(((RMSServiceException) exception).getErrCode());
            }else {
            	controllerException.setErrCode("422");
            }
            controllerException.setErrMsg(exception.getMessage());
            logger.error("Exception occured InfogramActiveResourceController saveInfogramActiveResource Not successfully Moved in RMS:" + controllerException.getMessage());
            response = ControllerException.toJsonArray(controllerException);
            return new ResponseEntity<String>(response, HttpStatus.EXPECTATION_FAILED);
         }
		return new ResponseEntity<String>(response, HttpStatus.OK);
     }

	
	//not to use for now. 
	@RequestMapping(value = "/saveeditedinfogramactiveresource", method = RequestMethod.POST, headers = "Accept=application/json")
	public void saveEditedInfogramActiveResource(@RequestBody String requestJson) {
		ObjectMapper objectMapper = new ObjectMapper();
		List<InfogramActiveResourceDTO> jsonString = new ArrayList<InfogramActiveResourceDTO>();
		ControllerException controllerException = new ControllerException(); 
		String response = null;
		try {
			jsonString = objectMapper.readValue(requestJson,  new TypeReference<List<InfogramActiveResourceDTO>>() { });
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		List items = new JSONDeserializer<Map<String, List<Map<String, Object>>>>()
				.use("values.values", InfogramActiveResourceDTO.class).deserialize(requestJson, String.class)
				.get("parameters");
		
		InfogramActiveResourceDTO activeResourceDTO = infogramResourceHelper.convert(items);
		try {
			infogramActiveResourceService.saveEditedInfogramActiveResource(activeResourceDTO);
		} catch (Exception exception) {
			if(exception instanceof RMSServiceException) {
				controllerException.setErrCode(((RMSServiceException) exception).getErrCode());
          }else {
        	  controllerException.setErrCode("422");
          }
			controllerException.setErrMsg(exception.getMessage());
          logger.error("Exception occured saveEditedInfogramActiveResource :" + exception.getMessage());
          response = ControllerException.toJsonArray(controllerException);
          //return new ResponseEntity<String>(response,headers, HttpStatus.INTERNAL_SERVER_ERROR);
          }

		
	}
	/**
	 * Discard API will update the infogram process status with Discarded Status. 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/discardinfogramresource/{infoId}", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public ResponseEntity<String> discardInfogramResource(@PathVariable("infoId") Integer id) throws ControllerException {
		HttpHeaders headers = new HttpHeaders();
		ControllerException controllerException=new ControllerException();
		String response = null;
		try {
			Resource resource = userUtil.getLoggedInResource();
			infogramActiveResourceService.discardInfogramResourceById(id, resource);
			controllerException.setErrCode("200");
			controllerException.setErrMsg(ExceptionConstant.OK);
			response = ControllerException.toJsonArray(controllerException);
			return new ResponseEntity<String>(response,headers, HttpStatus.OK);
			
		}catch(Exception exception) {
            if(exception instanceof RMSServiceException) {
                controllerException.setErrCode(((RMSServiceException) exception).getErrCode());
          }else {
          	controllerException.setErrCode("422");
          }
          controllerException.setErrMsg(exception.getMessage());
          logger.error("Exception occured InfogramActiveResourceController discardInfogramResource Not successfully discarded in Infogram:" + controllerException.getMessage());
          response = ControllerException.toJsonArray(controllerException);
          return new ResponseEntity<String>(response,headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }
						
	}
	
	@RequestMapping(value = "/getallinfogramactiveresourcesList/{allExistNewVar}/{allDiscardSuccessVar}/{allOkVar}",method = RequestMethod.GET)	
	public ResponseEntity<String> getAllInfogramActiveResourcesList(@PathVariable(value="allExistNewVar") String resourceType,@PathVariable(value="allDiscardSuccessVar") String processStatus, @PathVariable(value="allOkVar") String recordStatus,@RequestParam(value="sEcho") String sEcho,@RequestParam(value="iDisplayStart", defaultValue="0") Integer page, @RequestParam(value="iDisplayLength", defaultValue="10") Integer size,HttpServletRequest request) {

		logger.info("--------getAllInfogramActiveResources method starts--------");
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		long totalCount=0;
		JSONObject resultJSON = new JSONObject();
		
		List<InfogramActiveResource> activeResourceList = infogramActiveResourceService.getAllInfogramActiveResource(resourceType, processStatus, recordStatus, request);

		totalCount = totalCountForDataTableList(resourceType,processStatus,request).longValue();
		resultJSON.put("sEcho", sEcho);
		resultJSON.put("iTotalRecords", totalCount);
		resultJSON.put("iTotalDisplayRecords", totalCount);
		
		resultJSON.put("aaData", activeResourceList);

        logger.info("--------getAllInfogramActiveResourcesTest method ends--------");
        return new ResponseEntity<String>(resultJSON.toString(), headers, HttpStatus.OK);

	}
	private Long totalCountForDataTableList(String status,String processStatus,HttpServletRequest request){
		HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        Long totalCount = 0L;
        try {
			totalCount = infogramActiveResourceService.getTotalCountInfogramResource(status, processStatus, request);
		} catch (Exception e) {			
			e.printStackTrace();
		}
        //totalCount = 50L;
        //String temp = "{\"count\":"+totalCount+"}";        
        return totalCount;

	}
	

	
	@RequestMapping(value = "/infogramActiveResourceReport", method = RequestMethod.GET)
	public ModelAndView getInfogramActiveResourceReport(Model uiModel) {
		logger.info("--------infogramActiveResourceReport method starts--------");
	
		HttpHeaders headers = new HttpHeaders();
	
		headers.add("Content-Type", "application/json; charset=utf-8");
		
		List<InfogramActiveResourceDTO> activeResourceList = infogramActiveResourceService.getInfogramActiveResourceReport();
	
		uiModel.addAttribute("activeResourceList", activeResourceList);
		logger.info("--------infogramActiveResourceReport method ends--------");
		return new ModelAndView("infogramActiveResourceListExcelView", "model", uiModel);
	}


}
