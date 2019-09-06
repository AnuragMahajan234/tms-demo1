package org.yash.rms.controller;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.yash.rms.domain.CustomerGroup;
import org.yash.rms.domain.Resource;
//import org.yash.rms.dto.ResourceAndPDLInputDTO;
import org.yash.rms.json.mapper.JsonObjectMapper;
import org.yash.rms.service.CustomerGroupService;
import org.yash.rms.service.impl.CustomerGroupServiceImpl;
import org.yash.rms.util.UserUtil;

@Controller
@RequestMapping(value = "/custgroups")
public class CustomerGroupController {

	@Autowired
	@Qualifier("CustomerGroupService")
	private CustomerGroupService cusGroupService;
	@Autowired
	private UserUtil userUtil;
	@Autowired
	JsonObjectMapper<CustomerGroup> jsonMapper;
	
	private static final Logger logger = LoggerFactory.getLogger(CustomerGroupController.class);

	@RequestMapping(method = RequestMethod.GET)
	public String list(Model uiModel) throws Exception {
		logger.info("------CustGroupController  getCustGroup method start------");

		try {
			Resource resource = userUtil.getLoggedInResource();

			if (resource.getUploadImage() != null
					&& resource.getUploadImage().length > 0) {
				byte[] encodeBase64UserImage = Base64.encodeBase64(resource.getUploadImage());
				String base64EncodedUser = new String(encodeBase64UserImage, "UTF-8");
				uiModel.addAttribute("UserImage", base64EncodedUser);
			} else {
				uiModel.addAttribute("UserImage", "");
			}

			uiModel.addAttribute("groupList", cusGroupService.findAll());

			// uiModel.addAttribute("requestRequisitionDashboardDTO",
			// requestRequisitionDashboardService.getRequestRequisitionDashboard(userId,
			// resource.getUserRole()));
			// uiModel.addAttribute("resourceAndPDLInputDTO", new
			// ResourceAndPDLInputDTO());
			// uiModel.addAttribute(Constants.MAIL_LIST, mailList);
			// uiModel.addAttribute("skillResStatusList", skillResStatusList);

			uiModel.addAttribute("firstName",
					resource.getFirstName() + " " + resource.getLastName());
			uiModel.addAttribute("designationName",
					resource.getDesignationId().getDesignationName());
			Calendar cal = Calendar.getInstance();
			cal.setTime(resource.getDateOfJoining());
			int m = cal.get(Calendar.MONTH) + 1;
			String months = new DateFormatSymbols().getMonths()[m - 1];
			int year = cal.get(Calendar.YEAR);
			uiModel.addAttribute("DOJ", months + "-" + year);
			uiModel.addAttribute("ROLE", resource.getUserRole());

			uiModel.addAttribute("resumes", new ArrayList<MultipartFile>());

			logger.info("------RequestReportController list method end------");
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("------CustGroupController getCustGroup  method end------");
		return "custgroups/list";
	}

	/*@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	public ResponseEntity<String> deleteFromJson(@PathVariable("id") Integer id) throws Exception {
		logger.info("------CustGroupController deleteFromJsonArray method start------");

		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObject = new JSONObject();

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		// Delete based on ID
		try {

			if (!cusGroupService.delete(id.intValue())) {
				jsonObject.put("status", "false");
				jsonArray.put(jsonObject);
				return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
			}

		} catch (Exception exception) {
			logger.error("Exception occured in createFromJsonArray method of event controller:" + exception);
			return new ResponseEntity<String>("{ \"status\":\"FALSE\"}", headers, HttpStatus.EXPECTATION_FAILED);
			// throw exception;

		}
		logger.info("------CustGroupController deleteFromJson  method end------");
		jsonObject.put("status", "true");
		jsonArray.put(jsonObject);
		return new ResponseEntity<String>(jsonArray.toString(), headers, HttpStatus.OK);
	}*/

	@RequestMapping(value = "/custedit", method = RequestMethod.PUT, headers = "Accept=application/json")
	public ResponseEntity<String> updateFromJson(@RequestBody String json, HttpServletRequest request)
			throws Exception {
		logger.info("------CustGroupController updateFromJson method start------");
		HttpHeaders headers = new HttpHeaders();
		ServletContext context = request.getSession().getServletContext();
		context.getAttribute("userName");
		headers.add("Content-Type", "application/json");
		// Convert JSon Object to Domain Object
		CustomerGroup customerGroup = jsonMapper.fromJsonToObject(json, CustomerGroup.class);
		customerGroup.setLastUpdatedId((String) context.getAttribute("userName"));
		CustomerGroup custGroupStatus=cusGroupService.findByGroupId(customerGroup.getGroupId());
		customerGroup.setActive(custGroupStatus.getActive());
		try {
			// Update the CustomerGroup
			if (!cusGroupService.saveOrupdate(customerGroup)) {
				return new ResponseEntity<String>("{ \"status\":\"FALSE\"}", headers, HttpStatus.EXPECTATION_FAILED);
			}
		} catch (RuntimeException runtimeException) {
			logger.error("RuntimeException occured in updateFromJson method of CustGroupController controller:" + runtimeException);
			throw runtimeException;
		} catch (Exception exception) {
			logger.error("Exception occured in updateFromJson method of CustGroupController controller:" + exception);
			throw exception;
		}
		logger.info("------CustGroupController updateFromJson method end------");
		return new ResponseEntity<String>("{ \"status\":\"TRUE\"}", headers, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<String> createFromJson(@RequestBody String json, HttpServletRequest request)
			throws Exception {
		logger.info("------CustGroupController createFromJsonArray method start------");
		// Convert JSon Object to Domain Object
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		ServletContext context = request.getSession().getServletContext();

		try {
			CustomerGroup customerGroup = jsonMapper.fromJsonToObject(json, CustomerGroup.class);
			//customerGroup.setCreatedId((String) context.getAttribute("userName"));
			
			if (!cusGroupService.saveOrupdate(customerGroup)) {
				return new ResponseEntity<String>("{ \"status\":\"FALSE\"}", headers, HttpStatus.EXPECTATION_FAILED);
			}

		} catch (RuntimeException runtimeException) {
			logger.error(
					"RuntimeException occured in createFromJsonArray method of CustGroupController controller:" + runtimeException);
			if (runtimeException instanceof DataIntegrityViolationException) {
				return new ResponseEntity<String>("{ \"status\":\"FALSE\"}", headers, HttpStatus.OK);
			}
		} catch (Exception exception) {
			logger.error("Exception occured in createFromJsonArray method of CustGroupController controller:" + exception);
			throw exception;
		}

		logger.info("------CustGroupController createFromJson  method end------");
		return new ResponseEntity<String>("{ \"status\":\"TRUE\"}", headers, HttpStatus.CREATED);

	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
	public ResponseEntity<String> custGroupListByCustId(@PathVariable("id") Integer custId) throws Exception {
	 logger.info("------CustGroupController custGroupListByCustId method start------");
	    HttpHeaders headers = new HttpHeaders();
	    headers.add("Content-Type", "application/json");
	    List<CustomerGroup> custGroupList = null;
	    try{
	    	custGroupList = cusGroupService.findCustGroupByCustomerId(custId);
	    	System.out.println("CustGroup List size "+custGroupList.size());
	    	 if (null==custGroupList) {
		            return new ResponseEntity<String>("{ \"status\":\"FALSE\"}",headers, HttpStatus.NOT_FOUND);
		        }}catch(RuntimeException runtimeException)
				{				
					logger.error("RuntimeException occured in custGroupListByCustId method of CustomerGroup controller:"+runtimeException);				
					return new ResponseEntity<String>("{ \"status\":\"FALSE\"}",headers, HttpStatus.EXPECTATION_FAILED);
//					throw runtimeException;
				       	
	    }catch(Exception exception){
	    	logger.error("Exception occured in custGroupListByCustId method of CustomerGroup controller:"+exception);				
	    	return new ResponseEntity<String>("{ \"status\":\"FALSE\"}",headers, HttpStatus.EXPECTATION_FAILED);
//	    	throw exception;
	    	
	    }
	    logger.info("------CustGroupController custGroupListByCustId method end------");
	    logger.info("List of CustGroup : "+jsonMapper.toJsonArray(custGroupList));
	   	return new ResponseEntity<String>(jsonMapper.toJsonArray(custGroupList),headers, HttpStatus.OK);
	}
	//The below code is commented because we have implement single code for both Active/DeActive customerGroup 
	/*@RequestMapping(value = "{activate}/{id}",  headers = "Accept=application/json")
    public ResponseEntity<String> deleteFromJson(@PathVariable("id") Integer id,@PathVariable("activate") String activateStatus) throws Exception{
    	logger.info("------CustGroupController deleteFromJson method start------");
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
    	try{ 
      
      if (!cusGroupService.activateOrDeactivateCustomerGroup(id, activateStatus)) {
          return new ResponseEntity<String>("{ \"status\":\"FALSE\"}",headers, HttpStatus.EXPECTATION_FAILED);
      }*/
      
    //The below code for Active/DeActive customerGroup --(Arun)
	@RequestMapping(value = "{groupStatus}/{id}",  headers = "Accept=application/json", method = RequestMethod.GET)
		@ResponseBody
		public ResponseEntity<String> deleteFromJson(@PathVariable("id") Integer id,@PathVariable("groupStatus") String groupStatus,@RequestParam(value = "custId") Integer custId,@RequestParam(value = "custGroupName") String custGroupName) throws Exception{
		
		logger.info("------CustGroupController deleteFromJson method start------");
	        HttpHeaders headers = new HttpHeaders();
	        headers.add("Content-Type", "application/json");
	    	try{ 
	          
	    	 if (!cusGroupService.activateOrDeactivateCustomerGroup(id, groupStatus,custId,custGroupName)) {
	    	          return new ResponseEntity<String>("{ \"status\":\"FALSE\"}",headers, HttpStatus.EXPECTATION_FAILED);
	    	      }
      
      }
    	catch(RuntimeException runtimeException)
      	{
      		logger.error("RuntimeException occured in deleteFromJson method of CustGroupController:"+runtimeException);
      		return new ResponseEntity<String>("{ \"status\":\"FALSE\"}",headers, HttpStatus.EXPECTATION_FAILED);
//      		throw runtimeException;
      	}
      	catch(Exception exception){
      		logger.error("Exception occured in deleteFromJson method of CustGroupController:"+exception);	
      		return new ResponseEntity<String>("{ \"status\":\"FALSE\"}",headers, HttpStatus.EXPECTATION_FAILED);
//      		throw exception;
      	}
    	logger.info("------CustGroupController deleteFromJson method end------");
      return new ResponseEntity<String>("{ \"status\":\"true\"}",headers, HttpStatus.OK);
    }
	
	 @RequestMapping(value = "/validate", method = RequestMethod.GET)
		@ResponseBody
		public ResponseEntity<String> validateCustGroupName(
				@RequestParam(value = "custGroupName") String custGroupName,
				@RequestParam(value = "custId") Integer custId, 				
				HttpServletResponse response) throws Exception {
			 
		 logger.info("------CustGroupController validate method start------");
				HttpHeaders headers = new HttpHeaders();
			    boolean result  =	cusGroupService.validateCustGroupName(custId, custGroupName);
			    logger.info("------CustGroupController validate method end------");
			 return new ResponseEntity<String>("{ \"result\":" + result + "}",
						headers, HttpStatus.OK);
			 
		 }
	 //The below code for saveOrUpdate customerGroup name
	 @RequestMapping(value = "/saveUpdateCustomer", method = RequestMethod.GET)
		@ResponseBody
		public ResponseEntity<String> saveUpdateCustomer(
				@RequestParam(value = "poGroupId") String poGroupId,
				@RequestParam(value = "poGroupName") String poGroupName,
				@RequestParam(value = "mCusotmerId") String mCusotmerId,
				HttpServletResponse response, HttpServletRequest request) throws Exception {
		       HttpHeaders headers = new HttpHeaders();
		      headers.add("Content-Type", "application/json");
			 logger.info("------CustGroupController saveUpdateCustomer method start------");
			boolean result  =	cusGroupService.saveUpdateCustomer(poGroupId, poGroupName,mCusotmerId);
			    logger.info("------CustGroupController saveUpdateCustomer method end------");
			    return new ResponseEntity<String>("{ \"result\":" + result + "}",
						headers, HttpStatus.OK);
		 }
}
