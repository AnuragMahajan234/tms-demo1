package org.yash.rms.controller;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
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
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.yash.rms.domain.Customer;
import org.yash.rms.domain.CustomerGroup;
import org.yash.rms.domain.PDLEmailGroup;
import org.yash.rms.domain.Resource;
import org.yash.rms.dto.CustomerDTO;
import org.yash.rms.dto.CustomerGroupDTO;
import org.yash.rms.dto.PDLEmailGroupDTO;
import org.yash.rms.json.mapper.CustomerJsonMapper;
import org.yash.rms.json.mapper.JsonObjectMapper;
import org.yash.rms.service.CustomerGroupService;
import org.yash.rms.service.CustomerService;
import org.yash.rms.service.PDLEmailGroupService;
import org.yash.rms.util.Constants;
import org.yash.rms.util.UserUtil;


/**
 * @author sumit.paul
 *
 */

@Controller
@RequestMapping("/customers")
public class CustomerController {

	

	@Autowired
	@Qualifier("CustomerService")
	CustomerService customerService;
	
	@Autowired
	@Qualifier("CustomerGroupService")
	CustomerGroupService customerGroupService;
	@Autowired
	PDLEmailGroupService pdlEmailGroupService;

	@Autowired
	JsonObjectMapper<CustomerDTO> jsonMapper;
	
	@Autowired
	CustomerJsonMapper custJsonMapper;
	
	@Autowired
	private UserUtil userUtil;
		
	private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);
	
	/**
	 * This method is used to fetch customer, based on customer id.
	 * @param id
	 *       <code>Integer</code> value corresponds to customer id.
	 *  
	 * @return
	 *   <code>ResponseEntity</code> having customer information and status.
	 */
	@RequestMapping(value = "/{id}", headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<String> showJson(@PathVariable("id") Integer id) throws Exception {
		logger.info("------CustomerController showJson method start------");
		
		CustomerDTO customer = null;
		HttpHeaders headers = new HttpHeaders();
		
		try{			
			customer = customerService.findCustomer(id);		       
	        headers.add("Content-Type", "application/json; charset=utf-8");
	        if (customer == null) {
	            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
	        }
	        
		}catch(RuntimeException runtimeException)
		{				
			logger.error("RuntimeException occured in showJson method of Customer controller:"+runtimeException);				
			throw runtimeException;
		}catch(Exception exception){
			logger.error("Exception occured in showJson method of Customer controller:"+exception);				
			throw exception;
		}   
        
        logger.info("------CustomerController list method end------");
        //return new ResponseEntity<String>(customer.toJson(), headers, HttpStatus.OK);
        return new ResponseEntity<String>(custJsonMapper.toJson(customer), headers, HttpStatus.OK);
        
    }
	

	/**
	 * This method is used to fetch all the customers which are shown on the list tab of the page.
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String list(
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size,
			Model uiModel) throws Exception{
		
		logger.info("------CustomerController list method start------");
		if (page != null || size != null) {
			int sizeNo = size == null ? 10 : size.intValue();
			final int firstResult = page == null ? 0 : (page.intValue() - 1)
					* sizeNo;
			uiModel.addAttribute(Constants.ALL_CUSTOMERS,
					customerService.findByEntries(firstResult, sizeNo));
			float nrOfPages = (float) customerService.countTotal() / sizeNo;
			uiModel.addAttribute(
					Constants.MAX_PAGES,
					(int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1
							: nrOfPages));
		} else {
			
			try{
				uiModel.addAttribute(Constants.ALL_CUSTOMERS, customerService.findAll());
			}catch(RuntimeException runtimeException)
			{				
				logger.error("RuntimeException occured in list method of Customer controller:"+runtimeException);				
				throw runtimeException;
			}catch(Exception exception){
				logger.error("Exception occured in list method of Customer controller:"+exception);				
				throw exception;
			}
		}
		
		// Set Header values...
		Resource resource = userUtil.getLoggedInResource();
		
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
					
		logger.info("------CustomerController list method end------");
		return "customers/list";
	}

	
	/**
	 * This method is used to update the customer information. 
	 * @param json
	 *      <code>String</code> value of customer, passed from front end.
	 * @param request
	 * 		<code>HttpServletRequest</code> object having all the request parameters.
	 * @return
	 * 		<code>ResponseEntity</code> having customer information and status.	
	 */
	@RequestMapping(method = RequestMethod.PUT, headers = "Accept=application/json")
	public ResponseEntity<String> updateFromJson(@RequestBody String json,HttpServletRequest request) throws Exception {
		
		logger.info("------CustomerController updateFromJson method start------");
		System.out.println("Customer update json "+json);
		HttpHeaders headers = new HttpHeaders();		
		ServletContext context=request.getSession().getServletContext();
    	context.getAttribute("userName");
		headers.add("Content-Type", "application/json");
        
		CustomerDTO customer = jsonMapper.fromJsonToObject(json,CustomerDTO.class);
		// customer.setLastUpdatedId((String)context.getAttribute("userName"));
		List<PDLEmailGroup> pdlEmailGroups = pdlEmailGroupService.getPdlEmails();

		if (customer.getId() == -1) {
			if (customer.getCustomerEmail() != null) {

				int newEntry = 1;
				for (PDLEmailGroup pdlEmailGroup : pdlEmailGroups) {
					if (pdlEmailGroup.getPdlEmailId().trim()
							.equals(customer.getCustomerEmail().trim())) {
						newEntry = newEntry - 1;
					}
				}
				if (newEntry != 0) {
					PDLEmailGroupDTO pdlEmailGroupDTO = new PDLEmailGroupDTO();
					pdlEmailGroupDTO.setActive("Y");
					pdlEmailGroupDTO.setPdlEmailId(customer.getCustomerEmail());
					int index = customer.getCustomerEmail().indexOf('@');
					String pdl_name = customer.getCustomerEmail().substring(0,
							index);
					pdlEmailGroupDTO.setPdlName(pdl_name);
					pdlEmailGroupService.saveOrUpdate(pdlEmailGroupDTO);
				}
			}
		}

		else if ((customer.getId() != -1 || customer.getId() == -1)&& customer.getCustomerEmail() == null) {
			
		} else if (customer.getId() != -1 && customer.getCustomerEmail() != null) {
			int custId = customer.getId();
			CustomerDTO customerGroupList = null;
			customerGroupList = customerService.findCustomer(custId);
			String custEmail = customerGroupList.getCustomerEmail();
			if(custEmail == null){
			}
			else{
			String NewcustEmail = customer.getCustomerEmail();
			for (PDLEmailGroup pdlEmailGroup : pdlEmailGroups) {
				if (pdlEmailGroup.getPdlEmailId().trim().equals(custEmail.trim())) {
					pdlEmailGroupService.updatePdlEmail(custEmail, NewcustEmail);
				}
			}
		}
		}
		try{		
			//pdlEmailGroupService.saveOrUpdateIfNew(pdlEmailGroupDTO);
			// Update the customer 
			if (!customerService.saveOrupdate(customer)) {
				return new ResponseEntity<String>("{ \"status\":\"FALSE\"}",headers, HttpStatus.EXPECTATION_FAILED);
			}			
		}catch(RuntimeException runtimeException)
		{				
			logger.error("RuntimeException occured in updateFromJson method of Customer controller:"+runtimeException);	
			if(runtimeException instanceof DataIntegrityViolationException){
        		return new ResponseEntity<String>("{ \"status\":\"FALSE\"}",headers, HttpStatus.OK);
        	}
		}catch(Exception exception){
			logger.error("Exception occured in updateFromJson method of Customer controller:"+exception);				
			throw exception;
		}  
		logger.info("------CustomerController updateFromJson method end------");
		return new ResponseEntity<String>("{ \"status\":\"true\"}",headers, HttpStatus.OK);
	}
	
	
	
	
	/**
	 * This method is used to create new customer in the system.	
	 * @param json
	 *      <code>String</code> value of customer, passed from front end.
	 * @param request
	 * 		<code>HttpServletRequest</code> object having all the request parameters.
	 * @return
	 * 		<code>ResponseEntity</code> having customer information and status.	
	 */
	@RequestMapping(method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<String> createFromJsonArray(@RequestBody String json,
			HttpServletRequest request) throws Exception{
	
		logger.info("------CustomerController createFromJsonArray method start------");
		System.out.println("Create Customer json "+json);
		CustomerDTO customer = null;
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		
		ServletContext context = request.getSession().getServletContext();
		
		try{
			customer = jsonMapper.fromJsonToObject(json, CustomerDTO.class);
			customer.setCreatedId((String) context.getAttribute("userName"));
			PDLEmailGroupDTO pdlEmailGroupDTO =new PDLEmailGroupDTO();
			pdlEmailGroupDTO.setActive("Y");
			pdlEmailGroupDTO.setPdlEmailId(customer.getCustomerEmail());
			int index = customer.getCustomerEmail().indexOf('@');
			String pdl_name = customer.getCustomerEmail().substring(0,index);
			pdlEmailGroupDTO.setPdlName(pdl_name);
		
			if (!customerService.saveOrupdate(customer) && (!pdlEmailGroupService.saveOrUpdate(pdlEmailGroupDTO))) {
				return new ResponseEntity<String>("{ \"status\":\"FALSE\"}",headers, HttpStatus.EXPECTATION_FAILED);
			}

		}catch(RuntimeException runtimeException)
		{				
			logger.error("RuntimeException occured in createFromJsonArray method of Customer controller:"+runtimeException);				
			if(runtimeException instanceof DataIntegrityViolationException){
        		return new ResponseEntity<String>("{ \"status\":\"FALSE\"}",headers, HttpStatus.OK);
        	}
		}catch(Exception exception){
			logger.error("Exception occured in createFromJsonArray method of Customer controller:"+exception);				
			throw exception;
		} 
			
		logger.info("------CustomerController createFromJson  method end------");
		return new ResponseEntity<String>("{ \"status\":\"true\"}",headers, HttpStatus.CREATED);

	}
	
	@RequestMapping(value = "/{customerIds}/groups/", method = RequestMethod.GET)
	@ResponseBody
	public List<CustomerGroupDTO> getGroups(@PathVariable("customerIds") String customerIds) {
		ArrayList<Integer> list = new ArrayList<Integer>();
		List<CustomerGroupDTO> customerGroupList = null;
		logger.info("------getGroups of CustomerController method start------");
		try{
			for (String customerId : customerIds.split(",")) {
			    list.add(Integer.parseInt(customerId));
			}
			customerGroupList = customerGroupService.findCustomerGroupByCustomerIds(list);
			if(customerGroupList==null){
				customerGroupList = new ArrayList<CustomerGroupDTO>();
			}
		}catch (Exception ex) {
			logger.error(ex.getMessage());
		}	
		logger.info("------getGroups of CustomerController method ended------");
		return customerGroupList;
	}
}
