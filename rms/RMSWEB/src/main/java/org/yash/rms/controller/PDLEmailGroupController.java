package org.yash.rms.controller;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
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
import org.yash.rms.domain.Resource;
import org.yash.rms.dto.PDLEmailGroupDTO;
import org.yash.rms.json.mapper.JsonObjectMapper;
import org.yash.rms.service.PDLEmailGroupService;
import org.yash.rms.util.UserUtil;

@Controller
@RequestMapping(value = "/pdls")
public class PDLEmailGroupController {

	@Autowired
	PDLEmailGroupService pdlEmailGroupService;

	@Autowired
	JsonObjectMapper<PDLEmailGroupDTO> jsonMapper;

	@Autowired
	private UserUtil userUtil;

	private static final Logger logger = LoggerFactory
			.getLogger(PDLEmailGroupController.class);

	@RequestMapping(method = RequestMethod.GET)
	public String list(Model uiModel) throws Exception {
		logger.info("------PDLEmailGroupController  getPDLList method start------");
		Resource resource = userUtil.getLoggedInResource();
		try {
			if (resource.getUploadImage() != null
					&& resource.getUploadImage().length > 0) {
				byte[] encodeBase64UserImage = Base64.encodeBase64(resource.getUploadImage());
				String base64EncodedUser = new String(encodeBase64UserImage,
						"UTF-8");
				uiModel.addAttribute("UserImage", base64EncodedUser);
			} else {
				uiModel.addAttribute("UserImage", "");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		uiModel.addAttribute("firstName", resource
				.getFirstName()
				+ " "
				+ resource.getLastName());
		uiModel.addAttribute("designationName", resource
				.getDesignationId().getDesignationName());
		uiModel.addAttribute("ROLE", resource.getUserRole());
		try {

			uiModel.addAttribute("pdlList", pdlEmailGroupService.findAll());

		} catch (RuntimeException runtimeException) {
			logger.error("RuntimeException occured in list method of ownership controller:"
					+ runtimeException);
			throw runtimeException;
		} catch (Exception e) {
			logger.error("Exception occured in getPDLList method of PDLEmailGroupController :"
					+ e);
			throw e;
		}

		logger.info("------PDLEmailGroupController  getPDLList  method end------");
		return "pdls/list";
	}

	@RequestMapping(value = "{activate}/{id}", headers = "Accept=application/json")
	public ResponseEntity<String> deleteFromJson(
			@PathVariable("id") Integer id,
			@PathVariable("activate") String activateStatus) throws Exception {
		logger.info("------PDLEmailGroupController deleteFromJson method start------");
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		try {

			if (!pdlEmailGroupService.activateOrDeactivatePDLEmailGroup(id,
					activateStatus)) {
				return new ResponseEntity<String>("{ \"status\":\"FALSE\"}",
						headers, HttpStatus.EXPECTATION_FAILED);
			}

		} catch (RuntimeException runtimeException) {
			logger.error("RuntimeException occured in deleteFromJson method of PDLEmailGroupController:"
					+ runtimeException);
			return new ResponseEntity<String>("{ \"status\":\"FALSE\"}",
					headers, HttpStatus.EXPECTATION_FAILED);
			// throw runtimeException;
		} catch (Exception exception) {
			logger.error("Exception occured in deleteFromJson method of PDLEmailGroupController:"
					+ exception);
			return new ResponseEntity<String>("{ \"status\":\"FALSE\"}",
					headers, HttpStatus.EXPECTATION_FAILED);
			// throw exception;
		}
		logger.info("------PDLEmailGroupController deleteFromJson method end------");
		return new ResponseEntity<String>("{ \"status\":\"true\"}", headers,
				HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.PUT, headers = "Accept=application/json")
	public ResponseEntity<String> updateFromJson(@RequestBody String json,
			HttpServletRequest request) throws Exception {

		// System.out.println("Put Json--------->"+json);

		logger.info("------PDLEmailGroupController updateFromJson method start------");
		System.out.println("pdl update json " + json);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		ServletContext context = request.getSession().getServletContext();
		context.getAttribute("userName");
		// Convert JSon Object to Domain Object
		try {
			PDLEmailGroupDTO pdlEmailGroupDTO = jsonMapper.fromJsonToObject(
					json, PDLEmailGroupDTO.class);

			if (!pdlEmailGroupService.saveOrUpdate(pdlEmailGroupDTO)) {
				return new ResponseEntity<String>("{ \"status\":\"FALSE\"}",
						headers, HttpStatus.NOT_FOUND);
			}
		} catch (RuntimeException runtimeException) {
			logger.error("RuntimeException occured in updateFromJson method of PDLEmailGroupController:"
					+ runtimeException);
			throw runtimeException;
		} catch (Exception exception) {
			logger.error("Exception occured in updateFromJson method of PDLEmailGroupController:"
					+ exception);
			throw exception;
		}
		logger.info("------PDLEmailGroupController updateFromJson method end------");

		return new ResponseEntity<String>("{ \"status\":\"TRUE\"}", headers,
				HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<String> createFromJson(@RequestBody String json,
			HttpServletRequest request) throws Exception {

		logger.info("------PDLEmailGroupController createFromJson method start------");

		ServletContext context = request.getSession().getServletContext();
		context.getAttribute("userName");
		// Convert JSon Object to Domain Object

		PDLEmailGroupDTO pdlEmailGroupDTO = jsonMapper.fromJsonToObject(json,
				PDLEmailGroupDTO.class);
		// pdlEmailGroupDTO.setCreatedId((String)context.getAttribute("userName"));
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		try {
			if (!pdlEmailGroupService.saveOrUpdate(pdlEmailGroupDTO)) {
				return new ResponseEntity<String>("{ \"status\":\"FALSE\"}",
						headers, HttpStatus.EXPECTATION_FAILED);
			}
		} catch (RuntimeException runtimeException) {
			logger.error("RuntimeException occured in createFromJson method of PDLEmailGroupController:"
					+ runtimeException);
			throw runtimeException;
		} catch (Exception exception) {
			logger.error("Exception occured in createFromJson method of PDLEmailGroupController:"
					+ exception);
			throw exception;
		}
		logger.info("------PDLEmailGroupController createFromJson method end------");
		return new ResponseEntity<String>("{ \"status\":\"TRUE\"}", headers,
				HttpStatus.CREATED);
	}

}
