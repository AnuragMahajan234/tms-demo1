/**
 * 
 */
package org.yash.rms.controller;

import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.codehaus.jackson.impl.JsonGeneratorBase;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.yash.rms.domain.Activity;
import org.yash.rms.domain.Designation;
import org.yash.rms.domain.EmployeeCategory;
import org.yash.rms.domain.Event;
import org.yash.rms.domain.Grade;
import org.yash.rms.domain.Location;
import org.yash.rms.domain.OrgHierarchy;
import org.yash.rms.domain.Ownership;
import org.yash.rms.domain.Resource;
import org.yash.rms.domain.ResourceLoanTransfer;
import org.yash.rms.helper.EmailHelper;
import org.yash.rms.helper.UserActivityHelper;
import org.yash.rms.json.mapper.JsonObjectMapper;
import org.yash.rms.service.DesignationService;
import org.yash.rms.service.EmployeeCategoryService;
import org.yash.rms.service.EventService;
import org.yash.rms.service.GradeService;
import org.yash.rms.service.LocationService;
import org.yash.rms.service.OrgHierarchyService;
import org.yash.rms.service.OwnershipService;
import org.yash.rms.service.ResourceLoanAndTransferService;
import org.yash.rms.service.ResourceService;
import org.yash.rms.service.RmsCRUDService;
import org.yash.rms.util.Constants;
import org.yash.rms.util.GenericSearch;
import org.yash.rms.util.UserUtil;

/**
 * @author varun.haria
 *
 */
@Controller
@RequestMapping ("/loanAndTransfer")
public class ResourceLoanAndTransferController {
	
	@Autowired  
	LocationService locationService;
	
	@Autowired @Qualifier("ActivityService")
	RmsCRUDService<Activity> activityService;
	
	@Autowired  @Qualifier("ResourceService")
	ResourceService resourceService;
	
	@Autowired  
	DesignationService designationService;
	
	@Autowired  
	GradeService gradeService;
	
	@Autowired  
	EventService eventService;
	
	@Autowired @Qualifier("OrgHierarchyService")
	OrgHierarchyService buService;
	
	@Autowired  
	OwnershipService ownershipService;
	
	@Autowired
	EmployeeCategoryService employeeCategoryService ;
	
	@Autowired @Qualifier("ResourceLoanAndTransferService")
	ResourceLoanAndTransferService resourceLoanAndTransferService;
	
	@Autowired
	JsonObjectMapper<Activity> jsonMapper;
	
	@Autowired
	private UserUtil userUtil;
	@Autowired
	private EmailHelper emailHelper;
	@Autowired
	private UserActivityHelper userActivityHelper;
	
	@Autowired
	GenericSearch genericSearch;
	
	private static final Logger logger = LoggerFactory.getLogger(ResourceLoanAndTransferController.class);
	@InitBinder
	protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
		logger.info("------ResourceController initBinder method start------");
		
		try {
			binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat(Constants.DATE_PATTERN_4), true));
		} catch (RuntimeException runtimeException) {
			logger.error("RuntimeException occured in initBinder method of ResourceController:"
					+ runtimeException);
			throw runtimeException;
		} catch (Exception exception) {
			logger.error("Exception occured in initBinder method of ResourceController:"
					+ exception);
			throw exception;
		}
		logger.info("------ResourceController initBinder method end------");
	}


	@RequestMapping (method = RequestMethod.GET)
	public String getResource(@RequestParam(value = "page", required = false) Integer page,@RequestParam(value = "size", required = false) Integer size,
			@RequestParam(value="isRedirectedFromResource",required=false) Boolean isRedirectedFromResource, Model uiModel,HttpServletRequest request) throws Exception {
		logger.info("------ResourceLoanAndTransferController getResource method start------");		
			/*List<Resource> allResources = new ArrayList<Resource>();
			Integer currentLoggedInUserId = UserUtil.getCurrentResource().getEmployeeId();
			boolean isCurrentUserAdmin = UserUtil.isCurrentUserIsAdmin();
			boolean isCurrentUserHr = UserUtil.isCurrentUserIsHr();
			boolean isCurrentUserBusinessGroupAdmin=UserUtil.isCurrentUserIsBusinessGroupAdmin();*/
			Resource loggedInResource = userUtil.getLoggedInResource();
			try{
				if(request.getParameter("employeeId")!=null && !request.getParameter("employeeId").isEmpty()){															
					Resource resource = resourceService.find(Integer.parseInt(request.getParameter("employeeId")));
					uiModel.addAttribute(Constants.RESOURCEDATA,resource);
				}
			
			/*if(isCurrentUserAdmin||isCurrentUserHr)
			{
	            //uiModel.addAttribute(Constants.RESOURCES, resourceService.findAll());
	          //show only active resource --- Madhuri
				 allResources = resourceService.findAll();
				allResources = resourceService.findActiveResources();
				uiModel.addAttribute(Constants.RESOURCES, allResources);
	            uiModel.addAttribute(Constants.RESOURCES_RM,allResources);

			} 
			//TODO : Add role of BGADMIN 
			//new requirement: BGAdmin will view all resources on loan and transfer screen- this req. canceled 
			else if (isCurrentUserBusinessGroupAdmin) { 
				//Requirement: HR & BG_ADMIN will be seeing data of only those units he is assigned to handle --------START--------
				List<OrgHierarchy> buList = UserUtil.getCurrentResource().getAccessRight();
			
				// uiModel.addAttribute(Constants.RESOURCES, resourceService.findAll());
		            allResources = resourceService.findResourcesByBusinessGroup(buList, false,false);
					uiModel.addAttribute(Constants.RESOURCES, allResources);
		            uiModel.addAttribute(Constants.RESOURCES_RM,allResources);
			}	*/     
			   
			   uiModel.addAttribute(Constants.DESIGNATION,    designationService.findAll());
	           uiModel.addAttribute(Constants.GRADE,gradeService.findAll());
	           uiModel.addAttribute(Constants.EVENT,eventService.findAll());
	           uiModel.addAttribute(Constants.BUS,buService.findAllBu());	          
	           uiModel.addAttribute(Constants.LOCATIONS, locationService.findAll());
	           uiModel.addAttribute(Constants.OWNER, ownershipService.findAll());
	           uiModel.addAttribute(Constants.EMPLOYEECATEGORY, employeeCategoryService.findAll());
	        
	          /* List<Resource> list =  resourceService.findResourcesByBGADMINROLE();
	           uiModel.addAttribute(Constants.HR_NAME, resourceService.findResourceByHRROLE());
	           uiModel.addAttribute(Constants.BGH_NAME,list);
	           uiModel.addAttribute(Constants.BUH_NAME,list);*/
	           
	        // Set Header values...
	           
	           try{
					if(loggedInResource
						      .getUploadImage()!=null && loggedInResource.getUploadImage().length>0){
						byte[] encodeBase64UserImage = Base64.encodeBase64(loggedInResource.getUploadImage());
						String base64EncodedUser = new String(encodeBase64UserImage, "UTF-8");
						uiModel.addAttribute("UserImage", base64EncodedUser);
					}else{
						uiModel.addAttribute("UserImage", "");
					}
				}catch(Exception e){
					e.printStackTrace();
				}
	           
				uiModel.addAttribute("firstName", loggedInResource.getFirstName() + " " + loggedInResource.getLastName());
				uiModel.addAttribute("designationName", loggedInResource.getDesignationId().getDesignationName());
				Calendar cal = Calendar.getInstance();
				cal.setTime(loggedInResource.getDateOfJoining());
				int m = cal.get(Calendar.MONTH) + 1;
				String months = new DateFormatSymbols().getMonths()[m - 1];
				int year = cal.get(Calendar.YEAR);
				uiModel.addAttribute("DOJ", months + "-" + year);
				uiModel.addAttribute("ROLE", loggedInResource.getUserRole());
				uiModel.addAttribute("isRedirectedFromResource",isRedirectedFromResource);
				
	        }catch(RuntimeException runtimeException)
			{				
				logger.error("RuntimeException occured in list method of ResourceLoanAndTransferController:"+runtimeException);				
				throw runtimeException;
			}catch(Exception exception){
				logger.error("Exception occured in list method of ResourceLoanAndTransferController:"+exception);				
				throw exception;
			}
	    		logger.info("------ResourceLoanAndTransferController getResource method end------");	
	        return "loanandtrasnfer/create";
		}
	
	@RequestMapping(value = "/loadResourceData")
	@ResponseBody	
	 public ResponseEntity<String> loadResourceData(@RequestParam(value = "resourceid") int resourceid,HttpServletResponse response) throws Exception{
		 logger.info("------ResourceLoanAndTransferController loadResourceData method start------");
		 HttpHeaders headers = new HttpHeaders();
		 headers.add("Content-Type", "application/json; charset=utf-8");	                  
		 try {
              Resource currentResource = resourceService.find(resourceid);
              String resourceJson = currentResource.toJson();
              JSONObject json = new JSONObject(resourceJson);
              try{
            	  JSONObject jsonObject = new JSONObject();
            	  jsonObject.put("id", currentResource.getCurrentProjectId().getId());
            	  jsonObject.put("customerName", currentResource.getCurrentProjectId().getCustomerNameId().getCustomerName());
            	  json.put("currentProjectId",jsonObject);
              }catch(NullPointerException exception){
            	logger.warn("ResourceLoanAndTransferController: there is no project allocated to resource "+ currentResource.getEmployeeName());
              }
              
              JSONObject jsonObject = new JSONObject();
              jsonObject.put("employeeId", currentResource.getCurrentReportingManager().getEmployeeId());
              jsonObject.put("employeeName",  currentResource.getCurrentReportingManager().getEmployeeName());
              jsonObject.put("yashEmpId",  currentResource.getCurrentReportingManager().getYashEmpId());
              
              json.put("currentReportingManager", jsonObject);
              
              jsonObject = new JSONObject();
              jsonObject.put("employeeId", currentResource.getCurrentReportingManagerTwo().getEmployeeId());
              jsonObject.put("employeeName", currentResource.getCurrentReportingManagerTwo().getEmployeeName());
              jsonObject.put("yashEmpId", currentResource.getCurrentReportingManagerTwo().getYashEmpId());
              
              json.put("currentReportingManagerTwo",jsonObject);
              
              JSONObject currentBUJson = new JSONObject();
              currentBUJson.put("id", currentResource.getCurrentBuId().getId());
              currentBUJson.put("currentBuName", currentResource.getCurrentBuId().getParentId().getName());
             
              JSONObject parentBUJson = new JSONObject();
              parentBUJson.put("name",currentResource.getCurrentBuId().getParentId().getId());
              
              currentBUJson.put("parentId", parentBUJson);
              json.put("currentBuId",currentBUJson);
              
              if (currentResource.getCurrentReportingManager() != null) {
                if (currentResource.getEmployeeId() == currentResource.getCurrentReportingManager().getEmployeeId()) {
                	 JSONObject jsonObj =  new JSONObject();
                	 jsonObj.put("employeeId",currentResource.getEmployeeId());
                	 jsonObj.put("employeeName", currentResource.getEmployeeName());
                	 jsonObj.put("yashEmpId", currentResource.getYashEmpId());
                	
                	 if (currentResource.getEmployeeId().equals(currentResource.getCurrentReportingManager().getEmployeeId())) {
                		 json.put("currentReportingManager", jsonObj);
                  }
                }
              
              }
        
              if (currentResource.getCurrentReportingManagerTwo() != null) {
                if (currentResource.getEmployeeId() == currentResource.getCurrentReportingManagerTwo().getEmployeeId()) {
                  
                	 JSONObject jsonObj =  new JSONObject();
                	 jsonObj.put("employeeId",currentResource.getEmployeeId());
                	 jsonObj.put("employeeName", currentResource.getEmployeeName());
                	 jsonObj.put("yashEmpId", currentResource.getYashEmpId());
                	 
                	 if (currentResource.getEmployeeId().equals(currentResource.getCurrentReportingManagerTwo().getEmployeeId())) {
                      json.put("currentReportingManagerTwo", jsonObj);
                  }
                }
        
              }
        
              if (json != null) {
                resourceJson = json.toString();
              }
			 return new ResponseEntity<String>(resourceJson, headers,
						HttpStatus.OK);
		}catch(RuntimeException exception){
			logger.error("RuntimeException occured in loadResourceData method of ResourceLoanAndTransferController:"+exception);				
			throw exception;
		} catch (Exception e) {
			logger.error("Exception occured in loadResourceData method of ResourceLoanAndTransferController:"+e);				
			throw e;
		}
	 }	
	//TODO: Needs optimization
	 @RequestMapping(method = RequestMethod.POST, headers = "Accept=text/html")
	    public String create(@ModelAttribute org.yash.rms.form.ResourceLoanTransferForm resourceLoanTransferForm,HttpServletRequest request,Model uiModel,BindingResult bindingResult) throws Exception {
	    	logger.info("------ResourceLoanAndTransferController create method start------");
	    	try{
	    		if(null==resourceLoanTransferForm){
	    			throw new IllegalArgumentException("ResourceLoanAndTransferController Form Object Cannot be null");
	    		}	    	
	    	if (bindingResult.hasErrors()) {
				 throw new RuntimeException("Cannot Save ResourceLoanAndTransferController due to "+bindingResult.getAllErrors());
			}	     
	        HttpHeaders headers = new HttpHeaders();
	        headers.add("Content-Type", "application/json");	        	       	        	        
	        Date dt = new Date();			
	       // resourceLoanTransferForm.setTransferDate(dt);
	                
			resourceLoanTransferForm.setCreatedId(userUtil.getUserContextDetails().getUserName());	
			 List<ResourceLoanTransfer> list=null;
			 Resource currentResource=null;
	        if(resourceLoanTransferForm.getResourceId().getEmployeeId()==null){
	    	resourceLoanTransferForm.getResourceId().setEmployeeId(Integer.parseInt(resourceLoanTransferForm.getRefResourceId()));
	     }
	     list= 	resourceLoanAndTransferService.find(resourceLoanTransferForm.getResourceId().getEmployeeId());
		 currentResource=resourceService.find(resourceLoanTransferForm.getResourceId().getEmployeeId());
		 ResourceLoanTransfer loanTransfer=null;
		 String previousRM2EmailId=null;
		// Resource currentResource=resourceService.find(resourceLoanTransferForm.getResourceId().getEmployeeId());
		 Resource resourceRM2=null;
		 Resource bghName = null;
		 Resource buhName = null;
		 Resource hrName = null;
		 Event event = null;
		 String[] ccAddressHR = null;
		 List<String> ccAddressList = new ArrayList<String>();
		 String ccAddressListHR[] = new String[2];
		 String loggedInResourceEmail= userUtil.getLoggedInResource().getEmailId();
		 if(resourceLoanTransferForm.getCurrentReportingManagerTwo()!=null && resourceLoanTransferForm.getCurrentReportingManagerTwo().getEmployeeId()!=null)
		 resourceRM2= resourceService.find(resourceLoanTransferForm.getCurrentReportingManagerTwo().getEmployeeId());
		 if (resourceLoanTransferForm.getbGHName() != null && resourceLoanTransferForm.getbGHName().getEmployeeId() != null) {
			 bghName= resourceService.find(resourceLoanTransferForm.getbGHName().getEmployeeId());
		}
		 if (resourceLoanTransferForm.getbUHName()!= null && resourceLoanTransferForm.getbUHName().getEmployeeId() != null) {
			 buhName= resourceService.find(resourceLoanTransferForm.getbUHName().getEmployeeId());
		}
		 if (resourceLoanTransferForm.gethRName()!= null && resourceLoanTransferForm.gethRName().getEmployeeId() != null) {
			 hrName= resourceService.find(resourceLoanTransferForm.gethRName().getEmployeeId());
		}
		
		 
		 Resource resourceRM1=null;
		 if(resourceLoanTransferForm.getCurrentReportingManager()!=null && resourceLoanTransferForm.getCurrentReportingManager().getEmployeeId()!=null)
		 resourceRM1= resourceService.find(resourceLoanTransferForm.getCurrentReportingManager().getEmployeeId());
		 String RM2 =null;
		 if(resourceRM2!=null)
		 RM2= resourceRM2.getEmailId();
		  Resource previousRM2=null;
		  Map<String,List<String>> information= new LinkedHashMap<String, List<String>>();
		  Map<String,List<String>> hrInfromation= new LinkedHashMap<String, List<String>>();
		  List<String> updatedList= null;
		  List<String> hrupdatedList= null;
		  boolean hrFlag= false;
		  boolean baselocationchange =false;
		  
		// if(list.size()>0&&!list.isEmpty()){
			// loanTransfer=list.get(0);
		// if(currentResource.getYashEmpId().equals(resourceLoanTransferForm.getYashEmpId()))
					// {
				/* String previousYashEmpId=currentResource.getYashEmpId();
				 String newValueYashEmpId=resourceLoanTransferForm.getYashEmpId();
				 updatedList= new ArrayList<String>(); 
				 updatedList.add(previousYashEmpId);
				 updatedList.add(newValueYashEmpId);
				 information.put("EmployeeId",updatedList);*/
			// }
			// if(!currentResource.getEmailId().equals(resourceLoanTransferForm.getEmailId()))
			// {
			/*	 String previousEmailId=currentResource.getEmailId();
				 String newValueEmailId=resourceLoanTransferForm.getEmailId();
				 updatedList= new ArrayList<String>(); 
				 updatedList.add(previousEmailId);
				 updatedList.add(newValueEmailId);
				 information.put("EmailId",updatedList);*/
			// }
			 
		
	    	
				 String previousOwnership=currentResource.getOwnership().getOwnershipName();
				 Ownership ownership=  ownershipService.findById(resourceLoanTransferForm.getOwnership().getId());
				 String newValueOwnership= ownership.getOwnershipName();
						 
				 updatedList= new ArrayList<String>(); 
				 updatedList.add(previousOwnership);
				 updatedList.add(newValueOwnership);
				 information.put("Ownership Status",updatedList);
				
				 if(currentResource.getOwnership().getId()!=resourceLoanTransferForm.getOwnership().getId())
				 {
					 hrFlag= true;
					 hrInfromation.put("Ownership Status",updatedList);
				 }else{
					 hrInfromation.put("Ownership Status",updatedList);
				 }
			
				 String previousEmployeeCategory = "";
				 String newValueEmployeeCategory ="";
				 EmployeeCategory employeeCategory=  employeeCategoryService.findById(resourceLoanTransferForm.getEmployeeCategory().getId());
				  newValueEmployeeCategory = employeeCategory.getEmployeecategoryName();
				 if(currentResource.getEmployeeCategory()!=null)
				 {
				  previousEmployeeCategory=currentResource.getEmployeeCategory().getEmployeecategoryName();
				 
				
				 }	 
				 
				 updatedList= new ArrayList<String>(); 
				 updatedList.add(previousEmployeeCategory);
				 updatedList.add(newValueEmployeeCategory);
				 information.put("Employee Category Status",updatedList);
			 
			// if(currentResource.getDesignationId().getId()!=resourceLoanTransferForm.getDesignationId().getId())
			// {
				 String previousDesignationId=currentResource.getDesignationId().getDesignationName();
				 Designation designation=  designationService.findById(resourceLoanTransferForm.getDesignationId().getId());
				 String newValueDesignationId= designation.getDesignationName();
						 
				 updatedList= new ArrayList<String>(); 
				 updatedList.add(previousDesignationId);
				 updatedList.add(newValueDesignationId);
				 information.put("Designation",updatedList);
			// }
			 
			// if(currentResource.getGradeId().getId()!=resourceLoanTransferForm.getGradeId().getId())
			// {
				 String previousGradeId=currentResource.getGradeId().getGrade();
				 Grade grade=  gradeService.findById(resourceLoanTransferForm.getGradeId().getId());
				 String newValueGradeId= grade.getGrade();
						 
				 updatedList= new ArrayList<String>(); 
				 updatedList.add(previousGradeId);
				 updatedList.add(newValueGradeId);
				 information.put("Grade",updatedList);
			// }
			 
			
				 String previousLocationId = "";
				 String newValueLocationId= "";
				 if (currentResource.getLocationId() != null &&currentResource.getLocationId().getLocation()!= null ) {
					 previousLocationId=currentResource.getLocationId().getLocation();
				}
				Location location= locationService.findById(resourceLoanTransferForm.getLocationId().getId());
				if (location.getLocation() != null) {
					newValueLocationId=location.getLocation();
				}
				 updatedList= new ArrayList<String>(); 
				 updatedList.add(previousLocationId);
				 updatedList.add(newValueLocationId);
				 information.put("Base Location",updatedList);
				
			 
				 if (currentResource.getLocationId()!= null ) {
						if (currentResource.getLocationId() != resourceLoanTransferForm.getLocationId()) {
							
							if (currentResource.getLocationId().getId()!=resourceLoanTransferForm.getLocationId().getId()){
								baselocationchange = true;	
							}
							
						}
					}
				 String previousDeploymentLocation= "";
				 String newValueDeploymentLocation= "";
				 if (currentResource.getDeploymentLocation() != null && currentResource.getDeploymentLocation().getLocation() != null) {
					 previousDeploymentLocation=currentResource.getDeploymentLocation().getLocation();
				}else{
					previousDeploymentLocation= "None";
				}
				 Location deploymentLocation= locationService.findById(resourceLoanTransferForm.getDeploymentLocationId().getId());
				if (deploymentLocation != null && deploymentLocation.getLocation() != null ) {
					newValueDeploymentLocation=deploymentLocation.getLocation();
				}
				 updatedList= new ArrayList<String>(); 
				 updatedList.add(previousDeploymentLocation);
				 updatedList.add(newValueDeploymentLocation);
				 information.put("Current Location",updatedList);
				 
				 	if (currentResource.getDeploymentLocation() != null && currentResource.getDeploymentLocation().getLocation() != null) {
				 if(currentResource.getDeploymentLocation().getId()!=resourceLoanTransferForm.getDeploymentLocationId().getId())
				 {
					 hrFlag= true;
					 hrInfromation.put("Current Location ", updatedList);
				 }else{
					 hrInfromation.put("Current Location ", updatedList);
				 }
				 }else{
					 hrupdatedList= new ArrayList<String>(); 
					 hrupdatedList.add(previousDeploymentLocation);
					 hrupdatedList.add(newValueDeploymentLocation);
					 hrFlag = true;
					 hrInfromation.put("Current Location",hrupdatedList);
				 }
			// if(currentResource.getBuId().getId().intValue()!=resourceLoanTransferForm.getBuId().getId().intValue())
			// {
				 String previousBuId=currentResource.getBuId().getParentId().getName()+"-"+currentResource.getBuId().getName();
				 OrgHierarchy parentBu=buService.fingOrgHierarchiesById(resourceLoanTransferForm.getBuId().getId());
				 String newValueBuId=parentBu.getParentId().getName()+"-"+parentBu.getName();
						 
				 updatedList= new ArrayList<String>(); 
				 updatedList.add(previousBuId);
				 updatedList.add(newValueBuId);
				 information.put("Parent BG-BU",updatedList);
			// }
			 
				 
				 String previousCurrentBuId=currentResource.getCurrentBuId().getParentId().getName()+"-"+currentResource.getCurrentBuId().getName();
				 OrgHierarchy currentBu=buService.fingOrgHierarchiesById(resourceLoanTransferForm.getCurrentBuId().getId());
				 String newValueCurrentBuId=currentBu.getParentId().getName()+"-"+currentBu.getName();
						 
				 updatedList= new ArrayList<String>(); 
				 updatedList.add(previousCurrentBuId);
				 updatedList.add(newValueCurrentBuId);
				 information.put("Current BG-BU",updatedList);
				 
				 if(currentResource.getCurrentBuId().getId().intValue()!=resourceLoanTransferForm.getCurrentBuId().getId().intValue())
				 {
					 hrFlag= true;
					 hrInfromation.put("Current BG-BU",updatedList);
				 }else{
					 hrInfromation.put("Current BG-BU",updatedList);
				 }
			
			/* if((currentResource.getCurrentReportingManager()!=null && currentResource.getCurrentReportingManager().getEmployeeId()!=null && 
					 (resourceLoanTransferForm.getCurrentReportingManager() ==null||resourceLoanTransferForm.getCurrentReportingManager().getEmployeeId()==null) ) || 
					 ((currentResource.getCurrentReportingManager()==null || currentResource.getCurrentReportingManager().getEmployeeId()==null) && 
							 (resourceLoanTransferForm.getCurrentReportingManager() !=null||resourceLoanTransferForm.getCurrentReportingManager().getEmployeeId()!=null)) || 
							 (currentResource.getCurrentReportingManager().getEmployeeId().intValue()!=resourceLoanTransferForm.getCurrentReportingManager().getEmployeeId().intValue()))
			 {*/
				 
				
				 String previousRm1=null;
				 String newValueRm1=null;
				 if(currentResource.getCurrentReportingManager()!=null && currentResource.getCurrentReportingManager().getEmployeeId()!=null)
					 previousRm1=currentResource.getCurrentReportingManager().getFirstName().concat(" ").concat(currentResource.getCurrentReportingManager().getLastName());
				 else
					 previousRm1="None";
				 if (resourceRM1!=null)
					 newValueRm1=resourceRM1.getFirstName().concat(" ").concat(resourceRM1.getLastName());
				 else
					 newValueRm1="None";	 
				 updatedList= new ArrayList<String>(); 
				 updatedList.add(previousRm1);
				 updatedList.add(newValueRm1);
				 information.put("RM1",updatedList);
			// }
				 if(currentResource.getCurrentReportingManager()!=null && currentResource.getCurrentReportingManager().getEmployeeId()!=null){
					 if(!currentResource.getCurrentReportingManager().getEmployeeId().equals(resourceLoanTransferForm.getCurrentReportingManager().getEmployeeId())){
						 hrFlag = true;
						 hrInfromation.put("RM1",updatedList);
					 }else{
						 hrInfromation.put("RM1",updatedList);
					 }
				 }else{
					 hrupdatedList= new ArrayList<String>(); 
					 hrupdatedList.add(previousRm1);
					 hrupdatedList.add(newValueRm1);
					 hrFlag = true;
					 hrInfromation.put("RM1",hrupdatedList);
				 }
				 

			/* if((currentResource.getCurrentReportingManagerTwo()!=null && currentResource.getCurrentReportingManagerTwo().getEmployeeId()!=null && 
					 (resourceLoanTransferForm.getCurrentReportingManagerTwo() ==null||resourceLoanTransferForm.getCurrentReportingManagerTwo().getEmployeeId()==null) ) || 
					 ((currentResource.getCurrentReportingManagerTwo()==null || currentResource.getCurrentReportingManagerTwo().getEmployeeId()==null) && 
							 (resourceLoanTransferForm.getCurrentReportingManagerTwo() !=null||resourceLoanTransferForm.getCurrentReportingManagerTwo().getEmployeeId()!=null)) || 
							 (currentResource.getCurrentReportingManagerTwo().getEmployeeId().intValue()!=resourceLoanTransferForm.getCurrentReportingManagerTwo().getEmployeeId().intValue()))
			 {*/
				 String previousRm2=null;
				 String newValueRm2=null;
				 if(currentResource.getCurrentReportingManagerTwo()!=null && currentResource.getCurrentReportingManagerTwo().getEmployeeId()!=null)
					 previousRm2=currentResource.getCurrentReportingManagerTwo().getFirstName().concat(" ").concat(currentResource.getCurrentReportingManagerTwo().getLastName());
				 else
					 previousRm2="None";
				 if (resourceRM2!=null)
					 newValueRm2=resourceRM2.getFirstName().concat(" ").concat(resourceRM2.getLastName());
				 else
					 newValueRm2="None";
				 updatedList= new ArrayList<String>(); 
				 updatedList.add(previousRm2);
				 updatedList.add(newValueRm2);
				 information.put("RM2",updatedList);
			// }
				 if(currentResource.getCurrentReportingManagerTwo()!=null && currentResource.getCurrentReportingManagerTwo().getEmployeeId()!=null){
					 if(!currentResource.getCurrentReportingManagerTwo().getEmployeeId().equals(resourceLoanTransferForm.getCurrentReportingManagerTwo().getEmployeeId())){
						 hrFlag = true;
						 hrInfromation.put("RM2",updatedList);
					 }else{
						 hrInfromation.put("RM2",updatedList);
					 }
				
				 }else{
					 hrupdatedList= new ArrayList<String>(); 
					 hrupdatedList.add(previousRm1);
					 hrupdatedList.add(newValueRm1);
					 hrFlag = true;
					 hrInfromation.put("RM2",hrupdatedList);
				 }
				
					 
			 DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			 
			// if(!currentResource.getDateOfJoining().equals(resourceLoanTransferForm.getDateOfJoining()))
			// {
				 String previousDateOfJoining=df.format(currentResource.getDateOfJoining());
				 String newValueDateOfJoining=df.format(resourceLoanTransferForm.getDateOfJoining());
						 
				 updatedList= new ArrayList<String>(); 
				 updatedList.add(previousDateOfJoining);
				 updatedList.add(newValueDateOfJoining);
				 information.put("DOJ",updatedList);
			// }
				 
			 
			 if(currentResource.getConfirmationDate()!=null || resourceLoanTransferForm.getConfirmationDate()!=null)
			 {
				 String previous="";
				 String newValue="";
				//String value = checkValue(currentResource.getConfirmationDate());
			// if(!currentResource.getConfirmationDate().equals(resourceLoanTransferForm.getConfirmationDate()))
			 //{
				 if (currentResource.getConfirmationDate() != null) {
					  previous=df.format(currentResource.getConfirmationDate());
				}
				 if (resourceLoanTransferForm.getConfirmationDate() != null) {
					  newValue=df.format(resourceLoanTransferForm.getConfirmationDate());
				}
				 	 
				 updatedList= new ArrayList<String>(); 
				 updatedList.add(previous);
				 updatedList.add(newValue);
				 information.put("Confirmation Date",updatedList);
			// }
			 }
			 if(currentResource.getLastAppraisal()!=null || resourceLoanTransferForm.getLastAppraisal()!=null)
			 {
			// if(!currentResource.getLastAppraisal().equals(resourceLoanTransferForm.getLastAppraisal()))
			// {
				 String previousLastAppraisal= "";
				 String newValueLastAppraisal="";
				 if (currentResource.getLastAppraisal() != null) {
					 previousLastAppraisal=df.format(currentResource.getLastAppraisal());
				}
				 if (resourceLoanTransferForm.getLastAppraisal() != null) {
					 newValueLastAppraisal=df.format(resourceLoanTransferForm.getLastAppraisal());
				}
				 updatedList= new ArrayList<String>(); 
				 updatedList.add(previousLastAppraisal);
				 updatedList.add(newValueLastAppraisal);
				 information.put("Appraisal Date1",updatedList);
			// }
			 }
			 
			 if(currentResource.getPenultimateAppraisal()!=null || resourceLoanTransferForm.getPenultimateAppraisal()!=null)
			 {
			// if(!currentResource.getPenultimateAppraisal().equals(resourceLoanTransferForm.getPenultimateAppraisal()))
			// {
				 String previousPenultimateAppraisal="";
				 String newValuePenultimateAppraisal= "";
				 if (currentResource.getPenultimateAppraisal() != null) {
					 previousPenultimateAppraisal=df.format(currentResource.getPenultimateAppraisal());
				}
				 if (resourceLoanTransferForm.getPenultimateAppraisal() != null) {
					 newValuePenultimateAppraisal=df.format(resourceLoanTransferForm.getPenultimateAppraisal());
				} 
				 updatedList= new ArrayList<String>(); 
				 updatedList.add(previousPenultimateAppraisal);
				 updatedList.add(newValuePenultimateAppraisal);
				 information.put("Appraisal Date2",updatedList);
			// }
			 }
			 
			 
			 if(currentResource.getReleaseDate()!=null || resourceLoanTransferForm.getReleaseDate()!=null)
			 {
			// if(!currentResource.getReleaseDate().equals(resourceLoanTransferForm.getReleaseDate()))
			// {
				 String previousReleaseDate="";
				 String newValueReleaseDate="";
				 if (currentResource.getReleaseDate() != null) {
					 previousReleaseDate=df.format(currentResource.getReleaseDate());
				}
				  if (resourceLoanTransferForm.getReleaseDate() != null ) {
					  newValueReleaseDate=df.format(resourceLoanTransferForm.getReleaseDate());
				}
				 updatedList= new ArrayList<String>(); 
				 updatedList.add(previousReleaseDate);
				 updatedList.add(newValueReleaseDate);
				 information.put("Release Date",updatedList);
			// }
			 }
			 
			 if(currentResource.getTransferDate()!=null || resourceLoanTransferForm.getTransferDate()!=null)
			 {
			// if(!currentResource.getTransferDate().equals(resourceLoanTransferForm.getTransferDate()))
			 //{
				 String previousTransferDate="";
				 String newValueTransferDate= "";
				 if (currentResource.getTransferDate() != null) {
					 previousTransferDate=df.format(currentResource.getTransferDate());
				}
				if (resourceLoanTransferForm.getTransferDate() != null) {
					 newValueTransferDate=df.format(resourceLoanTransferForm.getTransferDate());
				}
				 updatedList= new ArrayList<String>(); 
				 updatedList.add(previousTransferDate);
				 updatedList.add(newValueTransferDate);
				 information.put("Loan/Transfer Date From",updatedList);
			// }
			// else {
			  if(currentResource.getTransferDate()!=null && resourceLoanTransferForm.getTransferDate()!=null){
				 if(currentResource.getTransferDate().equals(resourceLoanTransferForm.getTransferDate())){
				//If transfer date is not changed then the entry should not go in resourceLoanTransfer table
				 resourceLoanTransferForm.setTransferDate(null);
			}
			 } 
			 }
			 
			 // added loan/transfer Date to
			 
			 if(currentResource.getEndTransferDate()!=null || resourceLoanTransferForm.getEndTransferDate()!=null)
			 {
				 String previousTransferEndDate = "";
				 String newEndTransferDate = "";
				 if(currentResource.getEndTransferDate()!=null)
				 {
					 previousTransferEndDate = df.format(currentResource.getEndTransferDate());
				 }
				 if(resourceLoanTransferForm.getEndTransferDate()!=null)
				 {
					 newEndTransferDate = df.format(resourceLoanTransferForm.getEndTransferDate());
				 }
				 updatedList= new ArrayList<String>();
				 updatedList.add(previousTransferEndDate);
				 updatedList.add(newEndTransferDate);
				 information.put("Loan/Transfer Date To",updatedList);
				 
				 if(currentResource.getEndTransferDate()!=null && resourceLoanTransferForm.getEndTransferDate()!=null){
					 if(currentResource.getEndTransferDate().equals(resourceLoanTransferForm.getEndTransferDate())){
					//If transfer date is not changed then the entry should not go in resourceLoanTransfer table
					 resourceLoanTransferForm.setEndTransferDate(null);
				}
				 } 
			 }
			 
			 //adding all the rest fields
			 //adding BGH Name Field
//			 if((resourceLoanTransferForm.getbGHName()!=null && resourceLoanTransferForm.getbGHName().getEmployeeId()!= null )|| 
//					 (currentResource.getbGHName()!=null && currentResource.getbGHName().getEmployeeId()!= null))
//			 {
				 String previousbGHName="";
				 String newValuebGHName="";
				 
				 if (bghName != null) {
					 newValuebGHName=bghName.getEmployeeName();
				}
				 if (currentResource.getbGHName() != null ) {
					 Resource previousbGH = resourceService.find(currentResource.getbGHName().getEmployeeId());
					 if (previousbGH != null) {
						 previousbGHName= previousbGH.getEmployeeName();
					}
				} else if(currentResource.getCurrentBuId()!=null) {
						if( currentResource.getCurrentBuId().getParentId()!=null) {
							if(currentResource.getCurrentBuId().getParentId().getEmployeeId()!=null) {
								if(currentResource.getCurrentBuId().getParentId().getEmployeeId().getEmployeeName()!=null) {
									previousbGHName =	currentResource.getCurrentBuId().getParentId().getEmployeeId().getEmployeeName();
								}
							}
						}
				}
				 
				 if(previousbGHName.isEmpty()) {
					 previousbGHName = Constants.NOT_AVAILABLE;
				 }
				 
				 if(newValuebGHName.isEmpty()) {
					 newValuebGHName = previousbGHName;
				 }
					 
				 updatedList= new ArrayList<String>(); 
				 updatedList.add(previousbGHName);
				 updatedList.add(newValuebGHName);
				 information.put("BGH Name",updatedList);
			// }
			 
			 
			 //adding BGH Comments Field
			 if((resourceLoanTransferForm.getbGHComments()!=null && !resourceLoanTransferForm.getbGHComments().equals(""))
					 ||(currentResource.getbGHComments()!= null && !currentResource.getbGHComments().equals("")))
			 {
				 String previousbGHComments="";
				 String newValuebGHComments="";
				 if (currentResource.getbGHComments() != null && !currentResource.getbGHComments().equals("")) {
					 previousbGHComments=currentResource.getbGHComments();
				}
				 if (resourceLoanTransferForm.getbGHComments() != null && !resourceLoanTransferForm.getbGHComments().equals("")) {
					  newValuebGHComments=resourceLoanTransferForm.getbGHComments();
				}
				 updatedList= new ArrayList<String>(); 
				 updatedList.add(previousbGHComments);
				 updatedList.add(newValuebGHComments);
				 information.put("BGH Comments",updatedList);
			 
			}
			 
			 //adding BGH Comments Date Field
			 if(resourceLoanTransferForm.getbGCommentsTimestamp()!=null || currentResource.getbGCommentsTimestamp()!= null)
			 {
				 String previousbGCommentsTimestamp="";
				 String newValuebGCommentsTimestamp="";
				 if (resourceLoanTransferForm.getbGCommentsTimestamp() != null) {
					 newValuebGCommentsTimestamp=df.format(resourceLoanTransferForm.getbGCommentsTimestamp());
				}
				 if (currentResource.getbGCommentsTimestamp() != null) {
					 previousbGCommentsTimestamp=df.format(currentResource.getbGCommentsTimestamp());
				}
						 
				 updatedList= new ArrayList<String>(); 
				 updatedList.add(previousbGCommentsTimestamp);
				 updatedList.add(newValuebGCommentsTimestamp);
				 information.put("BGH Comments Date",updatedList);
			 }
			 
			 
			//adding BUH Name Field
			 /*if((resourceLoanTransferForm.getbUHName()!=null && resourceLoanTransferForm.getbUHName().getEmployeeId() != null )|| 
					 (currentResource.getbUHName()!= null && currentResource.getbUHName().getEmployeeId()!= null) )
			 {*/
				 String previousbUHName="";
				 String newValuebUHName="";
				 if (buhName != null ) {
					 newValuebUHName=buhName.getEmployeeName();
				}
				 if (currentResource.getbUHName() != null ) {
					 Resource previousbUH = resourceService.find(currentResource.getbUHName().getEmployeeId());
					 if (previousbUH != null) {
						 previousbUHName= previousbUH.getEmployeeName();
					}
				} else if(currentResource.getCurrentBuId()!=null) {
					if(currentResource.getCurrentBuId().getEmployeeId()!=null) {
						if(currentResource.getCurrentBuId().getEmployeeId().getEmployeeName()!=null) {
							previousbUHName = currentResource.getCurrentBuId().getEmployeeId().getEmployeeName();
						}
					}
				}
				 
				 if(previousbUHName.isEmpty()) {
					 previousbUHName = Constants.NOT_AVAILABLE;
				 }
				 
				 if(newValuebUHName.isEmpty()) {
					 newValuebUHName = previousbUHName; 
				 }
					
				 updatedList= new ArrayList<String>(); 
				 updatedList.add(previousbUHName);
				 updatedList.add(newValuebUHName);
				 information.put("BUH Name",updatedList);
			// }
				
			
			 //adding BUH Comments Field
			 if((resourceLoanTransferForm.getbUHComments()!=null && !resourceLoanTransferForm.getbUHComments().equals(""))||
					 (currentResource.getbUHComments()!= null &&!currentResource.getbUHComments().equals("") ) )
			 {
			 
				 String previousbUHComments="";
			 String newValuebUHComments="";
				 
				 if (currentResource.getbUHComments() != null && !currentResource.getbUHComments().equals("")) {
					 previousbUHComments=currentResource.getbUHComments();
				}
				 if (resourceLoanTransferForm.getbUHComments() != null && !resourceLoanTransferForm.getbUHComments().equals("")) {
					  newValuebUHComments=resourceLoanTransferForm.getbUHComments();
				}	 
				 updatedList= new ArrayList<String>(); 
				 updatedList.add(previousbUHComments);
				 updatedList.add(newValuebUHComments);
				 information.put("BUH Comments",updatedList);
			 
			 }
			
			 //adding BUH Comments Date Field
			 if(resourceLoanTransferForm.getbUCommentsTimestamp()!=null || currentResource.getbGCommentsTimestamp()!= null)
			 {
				 String previousbUCommentsTimestamp="";
			 String newValuebUCommentsTimestamp="";
			 
			 if (resourceLoanTransferForm.getbGCommentsTimestamp() != null) {
				 newValuebUCommentsTimestamp=df.format(resourceLoanTransferForm.getbUCommentsTimestamp());
			}
			 if (currentResource.getbGCommentsTimestamp() != null) {
				 previousbUCommentsTimestamp=df.format(currentResource.getbGCommentsTimestamp());
			}
						 
				 updatedList= new ArrayList<String>(); 
				 updatedList.add(previousbUCommentsTimestamp);
				 updatedList.add(newValuebUCommentsTimestamp);
				 information.put("BUH Comments Date",updatedList);
			 }
			/* else {
				//If transfer date is not changed then the entry should not go in resourceLoanTransfer table
				 resourceLoanTransferForm.setbUCommentsTimestamp(null);
			 }*/
			
			//adding HR Name Field
			 if((resourceLoanTransferForm.gethRName()!=null && resourceLoanTransferForm.gethRName().getEmployeeId()!= null )||
					 (currentResource.gethRName() != null && currentResource.gethRName().getEmployeeId()!= null))
			 {
				 String previoushRName="";
			 String newValuehRName="";
				 if (hrName != null) {
					 newValuehRName=hrName.getEmployeeName();
				 }
				 if (currentResource.gethRName() != null ) {
					 Resource previousHr = resourceService.find(currentResource.gethRName().getEmployeeId());
					 if (previousHr != null) {
						 previoushRName= previousHr.getEmployeeName();
					}
				}
					 updatedList= new ArrayList<String>(); 
					 updatedList.add(previoushRName);
					 updatedList.add(newValuehRName);
					 information.put("HR Name",updatedList);
			 
			 } 
			
			 //adding HR Comments Field
			 if((resourceLoanTransferForm.gethRComments()!=null && !resourceLoanTransferForm.gethRComments().equals("")) ||
					(currentResource.gethRComments()!= null && !currentResource.gethRComments().equals("")))
			 {
				 String previoushRComments="";
			 String newValuehRComments="";
				
				 if (currentResource.gethRComments() != null && !currentResource.gethRComments().equals("")) {
					 previoushRComments=currentResource.gethRComments();
				}
				 if (resourceLoanTransferForm.gethRComments() != null && !resourceLoanTransferForm.gethRComments().equals("")) {
					 newValuehRComments=resourceLoanTransferForm.gethRComments();
				}			 
				 updatedList= new ArrayList<String>(); 
				 updatedList.add(previoushRComments);
				 updatedList.add(newValuehRComments);
				 information.put("HR Comments",updatedList);
			 }
			  
			 //adding HR Comments Date Field
			 if(resourceLoanTransferForm.gethRCommentsTimestamp()!=null || currentResource.gethRCommentTimestamp()!=null)
			 {
				 String previoushRCommentsTimestamp="";
				 String newValuehRCommentsTimestamp="";
				 
				 if (resourceLoanTransferForm.gethRCommentsTimestamp() != null) {
					 newValuehRCommentsTimestamp=df.format(resourceLoanTransferForm.gethRCommentsTimestamp());
				}
				 if (currentResource.gethRCommentTimestamp() != null) {
					 previoushRCommentsTimestamp=df.format(currentResource.gethRCommentTimestamp());
				} 
				 updatedList= new ArrayList<String>(); 
				 updatedList.add(previoushRCommentsTimestamp);
				 updatedList.add(newValuehRCommentsTimestamp);
				 information.put("HR Comments Date",updatedList);
			 }
			/* else {
				//If transfer date is not changed then the entry should not go in resourceLoanTransfer table
				 resourceLoanTransferForm.sethRCommentsTimestamp(null);
			 }*/
			 
			
			 
				if(list.size()>0&&!list.isEmpty()){
					 loanTransfer=list.get(0);
					 if(loanTransfer.getCurrentReportingManagerTwo()!=null && !loanTransfer.getCurrentReportingManagerTwo().getEmailId().equals(RM2))
						{
							//previousRM2EmailId=loanTransfer.getCurrentReportingManagerTwo().getEmailId();
							previousRM2EmailId=loanTransfer.getCurrentReportingManagerTwo().getEmailId();
						}
				} else{
				 //previousRM2=	 resourceService.find(resourceLoanTransferForm.getResourceId().getEmployeeId());
				 if(currentResource.getCurrentReportingManagerTwo()!=null){
				 if(!currentResource.getCurrentReportingManagerTwo().getEmailId().equals(RM2))
					{
						//previousRM2EmailId=loanTransfer.getCurrentReportingManagerTwo().getEmailId();
						previousRM2EmailId=currentResource.getCurrentReportingManagerTwo().getEmailId();
					}
				 }
				  
				 }
			
				// }
			 
				//#816 - receivers in CC, parent and current BGBU, RMG, Central HR and, location HR (basis on location per below)
       			//ccAddressListHR.add(Constants.CENTRAL_HR);
       			//get previous and current RM1 and RM2 fields to be copied for mail.
       			if (resourceLoanTransferForm.getCurrentReportingManager() != null ) {
       				ccAddressList.add(resourceRM1.getEmailId());
				}
       			if (resourceLoanTransferForm.getCurrentReportingManagerTwo() != null) {
       				if (resourceLoanTransferForm.getCurrentReportingManagerTwo() != resourceLoanTransferForm.getCurrentReportingManager()) {
       					ccAddressList.add(RM2);
					}
				}
       			//check if the previous and current are not same then copy them too
       			if (currentResource.getCurrentReportingManager() != null) {
       				if (resourceLoanTransferForm.getCurrentReportingManager() != null) {
       					if (currentResource.getCurrentReportingManager() != resourceLoanTransferForm.getCurrentReportingManager() || 
       							currentResource.getCurrentReportingManager() != resourceLoanTransferForm.getCurrentReportingManagerTwo()) {
       						Resource previousRM1=resourceService.find(currentResource.getCurrentReportingManager().getEmployeeId());
       						if(!ccAddressList.contains(previousRM1.getEmailId())){
       							ccAddressList.add(previousRM1.getEmailId());
       						}
       						
    					}
					}
       				
       			//	if(!ccAddressList.contains(previousResourceRM2.getEmailId()))
       				//	ccAddressList.add(previousResourceRM2.getEmailId());
				}
       			if(resourceLoanTransferForm.getEmailId()!=null){
       				if(!(resourceLoanTransferForm.getEventId().getId()==10)){
       				  ccAddressList.add(resourceLoanTransferForm.getEmailId());
       				}
       			  
       			}
       			
       			if (currentResource.getCurrentReportingManagerTwo() != null) {
       				if (resourceLoanTransferForm.getCurrentReportingManager() != null) {
       					if (currentResource.getCurrentReportingManagerTwo() != resourceLoanTransferForm.getCurrentReportingManager() || 
       							currentResource.getCurrentReportingManagerTwo() != resourceLoanTransferForm.getCurrentReportingManagerTwo()) {
       						Resource previousResourceRM2=resourceService.find(currentResource.getCurrentReportingManagerTwo().getEmployeeId());
       						if (!ccAddressList.contains(previousResourceRM2.getEmailId())) {
       							ccAddressList.add(previousResourceRM2.getEmailId());
							}
       						
    					}
					}
       			if (!ccAddressList.contains(currentResource.getCurrentReportingManagerTwo().getEmailId()))
       				ccAddressList.add(currentResource.getCurrentReportingManagerTwo().getEmailId());
				}
       			//check Location change and add HR accordingly
       			if (currentResource.getLocationId()!= null ) {
					if (currentResource.getLocationId() != resourceLoanTransferForm.getLocationId()) {
						String currentResourcelocation = currentResource.getLocationId().getLocationHrEmailId();
						String  loanFormLocation = resourceLoanTransferForm.getDeploymentLocationId().getLocationHrEmailId();
						deploymentLocation.getLocationHrEmailId();
						//if (!ccAddressListHR.contains(currentResourcelocation) || !ccAddressListHR.contains(loanFormLocation)){
							ccAddressListHR[0]=currentResource.getLocationId().getLocationHrEmailId();
							if(baselocationchange== true)
							{
								//ccAddressListHR[1] = resourceLoanTransferForm.getLocationId().getLocationHrEmailId();
								ccAddressListHR[1] = deploymentLocation.getLocationHrEmailId();
							}
							 
						}
						
					}
			 
       		/*	if (currentResource.getDeploymentLocation()!= null ) {
					if (currentResource.getDeploymentLocation() != resourceLoanTransferForm.getDeploymentLocationId() ||
							currentResource.getDeploymentLocation() != resourceLoanTransferForm.getLocationId()) {
						String currentResourceDeploymentLocation = Integer.toString(currentResource.getDeploymentLocation().getId());
						String  loanFormLocation = Integer.toString(resourceLoanTransferForm.getDeploymentLocationId().getId());
						if (!ccAddressListHR.contains(currentResourceDeploymentLocation) || !ccAddressListHR.contains(loanFormLocation)){
							ccAddressListHR.add(Constants.getProperty(currentResourceDeploymentLocation));
							if(baselocationchange== true)
							{
							ccAddressListHR.add(Constants.getProperty(loanFormLocation));
							}
						}
						
					}
				}*/
       			
       			String[] toAddresscc = new String[1];
       			int size= ccAddressList.size();
       			toAddresscc = new String[size];
       			for (int i = 0; i < size ; i++) {
       				toAddresscc[i]=ccAddressList.get(i);
				
       		}
       			
       			/*int hrSize= ccAddressListHR.size();
       			if(hrFlag){
       			ccAddressHR = new String[hrSize];
       			for (int i = 0; i < size ; i++) {
       				if(!ccAddressListHR.get(i).equals(""))
       				ccAddressHR[i]=ccAddressListHR.get(i);
				}
       		}*/
   	
			   boolean success=resourceLoanAndTransferService.save(resourceLoanTransferForm);
			   Map<String, Object> model = new HashMap<String, Object>();
			   
			 String subject = "-"+currentResource.getEmployeeName()+"["+currentResource.getYashEmpId()+"]";
			 if (resourceLoanTransferForm.getEventId() != null ) {
				event= eventService.findById(resourceLoanTransferForm.getEventId().getId());
				 subject= subject + "-"+event.getEvent().toString()+" in RMS";
				
			}
			  if(hrFlag){
				userActivityHelper.setEmailContentForLoanTransfer(model, currentResource.getFirstName().concat(" ").concat(currentResource.getLastName()) ,hrInfromation,subject,currentResource,hrFlag);
				Set<String> toAddressHR = new HashSet<String>();
				//toAddressHR [2]= "";
   				//toAddressHR.add(Constants.CENTRAL_HR);
				//List<String> arrOfStr = new ArrayList<String>();
   				int j ;
   				for(int i =0 ; i<ccAddressListHR.length ; i++){
   						j = i+1;
   				if(ccAddressListHR[i]!=null){
   					if(ccAddressListHR[i].contains(";") /*|| ccAddressListHR[i].contains(";") || ccAddressListHR[i].contains(" ")*/) {
   			        String[] arrOfStr = ccAddressListHR[i].split(";"); 
   			       /* String[] arrOfStrSemiColon = null;
   			        String[] arrOfStrSpace = null;
   			        for(int k =0 ; k<arrOfStrComma.length ; k++) {
   			        arrOfStrSemiColon = arrOfStrComma[k].split(";");
   			        }
   			        for(int l =0 ; l<arrOfStrSemiColon.length ; l++) {
   			        	arrOfStrSpace = arrOfStrSemiColon[l].split(" ");
    			        }
   			       
   			        arrOfStr.addAll(Arrays.asList(arrOfStrComma));
   			        arrOfStr.addAll(Arrays.asList(arrOfStrSemiColon));
   			        arrOfStr.addAll(Arrays.asList(arrOfStrSpace));*/
   			        for (String a : arrOfStr) 
   			        	toAddressHR.add(a);
   					}else {
	   						toAddressHR.add(ccAddressListHR[i]);
	   					}
   				}}
   				emailHelper.sendEmailNotification(model, toAddressHR,null);
			   }
			   
				userActivityHelper.setEmailContentForLoanTransfer(model, currentResource.getFirstName().concat(" ").concat(currentResource.getLastName()) ,information,subject,currentResource,false);
				Set<String> toAddress = new HashSet<String>();
				toAddress.add(loggedInResourceEmail);
				
    			emailHelper.sendEmailNotification(model, toAddress,toAddresscc);
    			
       		 //}
       		/* else{
       			String[] toAddress = new String[1];
       		//#816-email should be as : one who did transaction 
       			toAddress[0]= loggedInResourceEmail;
       			emailHelper.sendEmailNotification(model, toAddress);
       		 }*/
       		 //Add Central HR in CC field for EMail Notification
    			
	        System.out.println(success);
	    	}catch(RuntimeException runtimeException)
			{				
				logger.error("RuntimeException occured in create method of ResourceLoanAndTransferController:"+runtimeException);				
				throw runtimeException;
			}catch(Exception exception){
				logger.error("Exception occured in create method of ResourceLoanAndTransferController:"+exception);				
				throw exception;
			}
	    	String redirectUrl="";
	    	if(request.getParameter("isRedirectedFromResource") != null && request.getParameter("isRedirectedFromResource").equalsIgnoreCase("true"))
	    		redirectUrl="redirect:/resources";
	    	else
	    		redirectUrl="redirect:/loanAndTransfer?employeeId="+resourceLoanTransferForm.getResourceId().getEmployeeId();
	        logger.info("------ResourceLoanAndTransferController create method end------");
	        return redirectUrl;
	    }
	    
	private String checkValue(Object value) {
		// TODO Auto-generated method stub
		 String val="";
		 if (value == null) {
			 val="";
		}
		return val;
	}


	@RequestMapping(value = "/validate", method = RequestMethod.GET)
		@ResponseBody
		public ResponseEntity<String> validateData(
				@RequestParam(value = "value") String yashEmpId,
				@RequestParam(value = "employeeId") int employeeId,
				HttpServletResponse response) throws Exception {
			logger.info("------ResourceLoanAndTransferController validateData method start----");
			boolean result = true;
			HttpHeaders headers = new HttpHeaders();
			Resource resource = null;
			Resource resource1=null;
			try {
				headers.add("Content-Type", "application/json; charset=utf-8");
 

				 
						resource = resourceService
								.findResourcesByYashEmpIdEquals(yashEmpId);

					 

					if (resource != null) {
						if (employeeId > 0) {

							if (employeeId != resource.getEmployeeId().intValue()) {
								result = false;

							}

						} else 
							result = true;
					}

				else {
					System.out.println("resource not found");
				}

			} catch (RuntimeException runtimeException) {
				logger.error("RuntimeException occured in validateData method of ResourceLoanAndTransferController:"
						+ runtimeException);
				throw runtimeException;
			} catch (Exception exception) {
				logger.error("Exception occured in validateData method of ResourceLoanAndTransferController:"
						+ exception);
				throw exception;
			}
			logger.info("------ResourceLoanAndTransferController validateData method end----");
			return new ResponseEntity<String>("{ \"result\":" + result + "}",
					headers, HttpStatus.OK);
		}
	
	//Method to fetch active users
		@RequestMapping(value="/activeUserList", method = RequestMethod.GET)
		public ResponseEntity<String> getActiveUserList(@RequestParam(value="userInput", required=false) String userInput)  {
			logger.info("--------getActiveUserList method starts--------");

			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-Type", "application/json; charset=utf-8");
			JSONObject resultJSON = new JSONObject();
			List<Resource> resourceList = null ;
			boolean isCurrentUserAdmin = UserUtil.isCurrentUserIsAdmin();
			boolean isCurrentUserHr = UserUtil.isCurrentUserIsHr();
			boolean isCurrentUserBusinessGroupAdmin=UserUtil.isCurrentUserIsBusinessGroupAdmin();
			
			if(isCurrentUserAdmin || isCurrentUserHr){
				
				resourceList = genericSearch.getObjectsWithSearchAndPaginationForResource(userInput);
			}
			else if (isCurrentUserBusinessGroupAdmin){
				List<OrgHierarchy> buList = UserUtil.getCurrentResource().getAccessRight();
				resourceList = genericSearch.getObjectsWithSearchAndPaginationForBusinessGroupAdmin(userInput, buList);
			}
			
			resultJSON.put("activeUserList", Resource.toJsonString(resourceList));
			
	        logger.info("--------getActiveUserList method ends--------");
	        return new ResponseEntity<String>(resultJSON.toString(), headers, HttpStatus.OK);
			
		}
		
	//Method to fetch active users
			@RequestMapping(value="/activeUserListByBGADMINROLE", method = RequestMethod.GET)
			public ResponseEntity<String> getActiveUserListByBGADMINROLE(@RequestParam(value="userInput", required=false) String userInput)  {
				logger.info("--------getActiveUserListByBGADMINROLE method starts--------");

				HttpHeaders headers = new HttpHeaders();
				headers.add("Content-Type", "application/json; charset=utf-8");
				JSONObject resultJSON = new JSONObject();
				List<Resource> resourceList = genericSearch.getObjectsWithSearchAndPaginationForResourceByRole(userInput,Constants.ROLE_BG_ADMIN);
				resultJSON.put("activeUserList", Resource.toJsonString(resourceList));
				
		        logger.info("--------getActiveUserListByBGADMINROLE method ends--------");
		        return new ResponseEntity<String>(resultJSON.toString(), headers, HttpStatus.OK);
				
			}
			
			//Method to fetch active users
			@RequestMapping(value="/activeUserListByHRROLE", method = RequestMethod.GET)
			public ResponseEntity<String> getActiveUserListByHRROLE(@RequestParam(value="userInput", required=false) String userInput)  {
				logger.info("--------getActiveUserListByHRROLE method starts--------");

				HttpHeaders headers = new HttpHeaders();
				headers.add("Content-Type", "application/json; charset=utf-8");
				JSONObject resultJSON = new JSONObject();
				List<Resource> resourceList = genericSearch.getObjectsWithSearchAndPaginationForResourceByRole(userInput,Constants.ROLE_HR);
				resultJSON.put("activeUserList", Resource.toJsonString(resourceList));
				
		        logger.info("--------getActiveUserListByHRROLE method ends--------");
		        return new ResponseEntity<String>(resultJSON.toString(), headers, HttpStatus.OK);
				
			}
}
