package org.yash.rms.controller;

import javax.naming.AuthenticationException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.yash.rms.domain.Resource;
import org.yash.rms.service.PdlSyncService;
import org.yash.rms.util.UserUtil;

import waffle.util.AuthorizationHeader;

@Controller
@RequestMapping("/process")
public class ProcessController{
	
	@Autowired
	@Qualifier("pdlSyncService")
	private PdlSyncService pdlSyncService;
	
	private static final Logger logger = LoggerFactory.getLogger(ProcessController.class);
	
	@Autowired
	private UserUtil userUtil;
	
	@RequestMapping(value ={"/",""},method=RequestMethod.GET)
	public String processForm(ModelMap uiModel){
		logger.debug("----------Entered into processForm of ProcessController-----------");
		Resource resource = userUtil.getLoggedInResource();
		try {
			if (resource.getUploadImage() != null
					&& resource.getUploadImage().length > 0) {
				byte[] encodeBase64UserImage = Base64.encodeBase64(resource.getUploadImage());
				String base64EncodedUser = new String(encodeBase64UserImage, "UTF-8");
				uiModel.addAttribute("UserImage", base64EncodedUser);
			} else {
				uiModel.addAttribute("UserImage", "");
			}
			uiModel.addAttribute("username", resource.getUserName());
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug(e.getMessage());
		}
		uiModel.addAttribute("firstName",
				resource.getFirstName() + " " + resource.getLastName());
		uiModel.addAttribute("designationName", resource.getDesignationId().getDesignationName());
		logger.debug("----------Exited from processForm of ProcessController-----------");
		return "process/process";
	}

	@ResponseBody
	@RequestMapping(value = "/synch/pdl",method=RequestMethod.POST,
	produces=MediaType.APPLICATION_JSON_VALUE, consumes=MediaType.APPLICATION_JSON_VALUE)
	public String synchPdl(@RequestBody String jsonString){
		
		logger.info("----------Entered into  synchPdl of ProcessController-----------");
		Integer userId = userUtil.getCurrentLoggedInUseId();
		Resource resource = userUtil.getLoggedInResource();
		String responseString = "";
		try{
			ObjectNode node = new ObjectMapper().readValue(jsonString, ObjectNode.class);
			String username = node.get("username").getTextValue();
			String password = node.get("password").getTextValue();
			if(username !=null && username.trim().equalsIgnoreCase(resource.getUserName())){
				if(pdlSyncService.syncPdl(userId, username, password)){
					responseString = "{\"status\":\"success\"}";
				}else{
					responseString = "{\"status\":\"error\"}";
				}
			}else{
				responseString = "{\"status\":\"unauthenticate\"}";
				logger.error("----------Exited synchPdl of ProcessController with invalid credential-----------");
			}
		}catch (AuthenticationException ex) {
			responseString = "{\"status\":\"unauthenticate\"}";
			logger.error("----------Invalid user credential to run synchPdl-----------");
		}catch (Exception e) {
			logger.error("----------Exception in synchPdl of ProcessController-----------");
			e.printStackTrace();
		}
		logger.info("----------Exited from synchPdl of ProcessController-----------");
		return responseString;
	}
}
