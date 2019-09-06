package org.yash.rms.controller;


import org.apache.commons.codec.binary.Base64;
import org.quartz.ObjectAlreadyExistsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.yash.rms.domain.Resource;
import org.yash.rms.dto.QuartzSchedularDTO;
import org.yash.rms.quartzscheduler.utils.QuartzSchedulerUtils;
import org.yash.rms.util.UserUtil;

@Controller
@RequestMapping ("/contactus")
public class ContactUsController {
	
	private static final Logger logger = LoggerFactory.getLogger(ContactUsController.class);
	
	@Autowired
	private UserUtil userUtil;
	
	@RequestMapping(method = RequestMethod.GET)
	public String list(Model uiModel) throws Exception{
		logger.info("------Contact Us GET Method Start------");
		
		Resource currentLoggedInResource = userUtil.getLoggedInResource();
		
		uiModel.addAttribute("firstName", currentLoggedInResource.getFirstName() + " " + currentLoggedInResource.getLastName());
		uiModel.addAttribute("designationName", currentLoggedInResource.getDesignationId().getDesignationName());
		uiModel.addAttribute("ROLE", currentLoggedInResource.getUserRole());
		
			if (currentLoggedInResource.getUploadImage() != null
					&& currentLoggedInResource.getUploadImage().length > 0) {
				byte[] encodeBase64UserImage = Base64.encodeBase64(currentLoggedInResource.getUploadImage());
				String base64EncodedUser = new String(
						encodeBase64UserImage, "UTF-8");
				uiModel.addAttribute("UserImage", base64EncodedUser);
			} else {
				uiModel.addAttribute("UserImage", "");
			}
			
		return "contactus/list";
	}
	
	@RequestMapping(value = "/addSchedular", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> addSchedular(@ModelAttribute QuartzSchedularDTO quartzSchedularDto,
			BindingResult result) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");

		logger.info("------Contact Us POST Method Start------");
		QuartzSchedulerUtils qsu1 = new QuartzSchedulerUtils();
		try {
			qsu1.add(quartzSchedularDto.getSchedularName(), quartzSchedularDto.getJobName(),
					quartzSchedularDto.getGroupName(), quartzSchedularDto.getTriggerName(),
					quartzSchedularDto.getClassName(), quartzSchedularDto.getMethodName(),
					quartzSchedularDto.getCronExpression(), quartzSchedularDto.getPriority(),
					quartzSchedularDto.getExecuteClassName());
		}catch (ObjectAlreadyExistsException e) {
			// TODO Auto-generated catch block
		    e.printStackTrace();
	     return new ResponseEntity<String>("{ \"status\":\""+e.getMessage()+"\"}",headers, HttpStatus.OK);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<String>("{\"status\":\"Schedular Successfully added\"}",headers, HttpStatus.OK);

	}
	

@RequestMapping(value = "/updateSchedular", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> updateSchedular(@ModelAttribute QuartzSchedularDTO quartzSchedularDto,
			BindingResult result) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");

		logger.info("------ContactUs Update Schedular POST Method Start------");
		QuartzSchedulerUtils qsu1 = new QuartzSchedulerUtils();
		try {
			qsu1.reScheduleTrigger(quartzSchedularDto.getTriggerName(),quartzSchedularDto.getGroupName(), quartzSchedularDto.getCronExpression());
		}catch (ObjectAlreadyExistsException e) {
			// TODO Auto-generated catch block
		    e.printStackTrace();
	     return new ResponseEntity<String>("{ \"status\":\""+e.getMessage()+"\"}",headers, HttpStatus.OK);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<String>("{\"status\":\"Schedular Successfully Updated\"}",headers, HttpStatus.OK);

	}
	
}
