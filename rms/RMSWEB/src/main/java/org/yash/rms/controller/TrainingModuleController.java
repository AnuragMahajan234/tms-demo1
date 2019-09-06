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
public class TrainingModuleController {

	private static final Logger logger = LoggerFactory.getLogger(TrainingModuleController.class);


	
	@Autowired
	private UserUtil userUtil;

	@RequestMapping(value = "/trainingModule", method = RequestMethod.GET)
	public String getAllInfogramActiveResources(Model uiModel) {

		logger.info("--------TrainingModuleController get method starts--------");
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
		

        logger.info("--------TrainingModuleController get method ends--------");
        return "trainingModule/list";

	}
	
	
}
