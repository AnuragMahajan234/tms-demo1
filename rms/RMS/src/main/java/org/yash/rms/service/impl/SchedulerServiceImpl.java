package org.yash.rms.service.impl;

import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.mail.MessagingException;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yash.rms.dao.AllocationTypeDao;
import org.yash.rms.dao.DefaultProjectDao;
import org.yash.rms.dao.PDLEmailGroupDao;
import org.yash.rms.dao.ProjectDao;
import org.yash.rms.dao.RequestRequisitionDao;
import org.yash.rms.dao.ResourceAllocationDao;
import org.yash.rms.dao.ResourceDao;
import org.yash.rms.domain.InfogramActiveResource;
import org.yash.rms.domain.InfogramInactiveResource;
import org.yash.rms.domain.OrgHierarchy;
import org.yash.rms.domain.Ownership;
import org.yash.rms.domain.Project;
import org.yash.rms.domain.RequestRequisition;
import org.yash.rms.domain.RequestRequisitionSkill;
import org.yash.rms.domain.Resource;
import org.yash.rms.domain.ResourceAllocation;
import org.yash.rms.helper.EmailHelper;
import org.yash.rms.helper.ResourceHelper;
import org.yash.rms.service.InfogramActiveResourceService;
import org.yash.rms.service.InfogramInactiveResourceService;
import org.yash.rms.service.OrgHierarchyService;
import org.yash.rms.service.OwnershipService;
import org.yash.rms.service.ProjectService;
import org.yash.rms.service.RequestRequisitionService;
import org.yash.rms.service.ResourceAllocationService;
import org.yash.rms.service.ResourceService;
import org.yash.rms.service.SchedulerService;
import org.yash.rms.util.AppContext;
import org.yash.rms.util.BlockedResourceExcelUtility;
import org.yash.rms.util.Constants;
import org.yash.rms.util.DateUtil;



@Component
@Service("SchedulerService")
public class SchedulerServiceImpl implements SchedulerService {
	
	@Autowired
	InfogramActiveResourceService infogramActiveResourceService;
	
	@Autowired
	InfogramInactiveResourceService infogramInactiveResourceService;
	
	@Autowired
	ResourceAllocationService resourceAllocationService ;
	
	@Autowired
	@Qualifier("defaultProjectDao")
	private DefaultProjectDao defaultProjectDao;
	
	@Autowired
	private ResourceAllocationDao resourceAllocationDao;
	
	@Autowired
	@Qualifier("projectDaoImpl")
	ProjectDao projectDao;
	
	@Autowired
	@Qualifier("allocationTypeDao")
	private AllocationTypeDao allocationTypeDao;
	
	@Autowired
	ResourceDao resourceDao;
	
	@Autowired
	private OrgHierarchyService orgHierarchyService ;
	
	@Autowired
	EmailHelper emailHelper;
	
	@Autowired
	ResourceHelper resourceHelper;
	
	@Autowired
	PDLEmailGroupDao pDLEmailGroupDao;
	
	@Autowired
	private RequestRequisitionDao requestRequisitionDao;
	
	@Autowired
	ResourceService resourceService;
	
	@Autowired
	OwnershipService ownershipService;
	
	@Autowired
	ProjectService projectService ;
	
	@Autowired
	RequestRequisitionService requestRequisitionService;
	
	private static final Logger logger = LoggerFactory.getLogger(SchedulerServiceImpl.class);

	//@Scheduled(cron=Constants.SCHEDULER_TIMER)
	@Transactional
	public void getAllInfoActiveResourceScheduler() throws Exception {
		// TODO Auto-generated method stub
		logger.info("getAllInfoActiveResourceScheduler method Started ");
		

		 List<InfogramActiveResource> list= infogramActiveResourceService.getAllInfogramActiveResources();
		 logger.info("list.............                     "+list.size());
	        List<String> empIds=new ArrayList<String>();
	        for (InfogramActiveResource infoResource : list) { 
	        	  
	            // If this element is not present in newList 
	            // then add it 
	            if (!empIds.contains(infoResource.getEmployeeId())) { 
	  
	            	empIds.add(infoResource.getEmployeeId()); 
	            	
	            } 
	            else
	            {
	            	infogramActiveResourceService.updateProcessStatus(infoResource.getId());
	            	logger.info("Duplicate records updated with Id "+infoResource.getId());
	            }
	        }
	    	logger.info("getAllInfoActiveResourceScheduler method End ");
		
	}
	//@Scheduled(cron=Constants.UPDATE_REC_STARUS_SCHEDULER)
	@Transactional
	public void  checkingAllResStatusScheduler()
	{
		// TODO Auto-generated method stub
		logger.info("checkingAllInfoRescsStatus method Started ");


		String infoPrjBGBU="";
		String rmsPrjBGBU="";
		String infoIRMBGBU="";
		String infoSRMBGBU="";
		
		String rmsIRMBGBU="";
		String rmsSRMBGBU="";
		
		String rmsData="";
		String infoData="";
		boolean bgbuFlag=false;
		 List<InfogramActiveResource> list= infogramActiveResourceService.getAllInfogramActiveResources();
		 logger.info("list............."+list.size());
		 for(InfogramActiveResource activeResource:list)
		 {
			 bgbuFlag=false;
			 if(activeResource.getResourceType().equalsIgnoreCase(Constants.RESOURCE_TYPE_EXISTING)){
				 infoPrjBGBU=activeResource.getInfoProjectBgBu();
				 rmsPrjBGBU=activeResource.getRMSProjectBgBu();
				
				 infoIRMBGBU=activeResource.getInfoIRMBgBu();
				 infoSRMBGBU=activeResource.getInfoSRMBgBu();
				 
				 rmsIRMBGBU=activeResource.getIRMBgBu();
				 rmsSRMBGBU=activeResource.getSRMBgBu();
				 
				 infoData=(activeResource.getDesignation().replaceAll("\\s+","").trim()+activeResource.getBaseLocation()+activeResource.getCurrentLocation()).replaceAll("\\s+","");
				 rmsData=(activeResource.getRmsDesignation().replaceAll("\\s+","").trim()+activeResource.getLocationInRMS()+activeResource.getCurrentLocInRMS()).replaceAll("\\s+","");			 
				 
			
				 if(CompareBGBU( infoPrjBGBU,  rmsPrjBGBU,  infoIRMBGBU,  infoSRMBGBU, rmsIRMBGBU,  rmsSRMBGBU ) && infoData.equalsIgnoreCase(rmsData)){
					 bgbuFlag=true;
				 }
			 }
			activeResource.setRecordStatus(bgbuFlag);
			infogramActiveResourceService.updateInfogramObject(activeResource);
			
		 }
		
	}
	
	//@Scheduled(cron=Constants.INFOINACTIV_SCHEDULER_TIMER)
	@Transactional
	public void getAllInfoInactiveResourceScheduler() throws Exception {
		// TODO Auto-generated method stub
		logger.info("getAllInfoInactiveResourceScheduler method Started ");
		

		 List<InfogramInactiveResource> list= infogramInactiveResourceService.getAllInfogramInactiveResources();
		 logger.info("list............."+list.size());
	        List<String> empIds=new ArrayList<String>();
	        for (InfogramInactiveResource infoInactResource : list) { 
	        	  
	            // If this element is not present in newList 
	            // then add it 
	            if (!empIds.contains(infoInactResource.getEmployeeId())) { 
	  
	            	empIds.add(infoInactResource.getEmployeeId()); 
	            } 
	            else
	            {
	            	infogramInactiveResourceService.updateProcessStatus(infoInactResource.getId());
	            	logger.info("Duplicate records updated with Id "+infoInactResource.getId());
	            }
	        }
	    	logger.info("getAllInfoInactiveResourceScheduler method End ");
		
	}
	
	/*@Transactional
	public void setDefaultProjectforBlockedResource() {
		List<ResourceAllocation> resourceAllocations =	resourceAllocationDao.getAllocationBlockedResourceWithThirtyDaysMore();
		if(resourceAllocations != null && !resourceAllocations.isEmpty()){
			Iterator<ResourceAllocation> iterator = resourceAllocations.iterator();
			
			while (iterator.hasNext()) {
				Date now = new Date();
				boolean isResourceHasDefaultPoject = false ;
				ResourceAllocation resourceAllocation = (ResourceAllocation) iterator.next();
				Resource resource  = resourceAllocation.getEmployeeId();
				
				DefaultProject defaultProjects = defaultProjectDao.getDefaultProjectbyProjectForBU(resource.getCurrentBuId());
				if (defaultProjects != null && defaultProjects.getProjectId().getId() == resourceAllocation.getProjectId().getId()) {
					isResourceHasDefaultPoject = true;
				}
				
				if(!isResourceHasDefaultPoject) {
					resourceAllocation.setAllocEndDate(now);
					resourceAllocation.setLastupdatedTimestamp(now);
					resourceAllocation.setCurProj(false);
					//resourceAllocation.setAllocRemarks(Constants.ALLOCATIONREMARKS_FOR_BLOCKED_RESOURCE);
					try{
						Boolean isUpdated = resourceAllocationDao.saveOrupdate(resourceAllocation);
						if(isUpdated){
							assignDefalutProjectToBenchResource(resourceAllocation,resource,defaultProjects);
						}
					}
					 catch(Exception ex) {
						 logger.error("Exceptio occured -------" + ex.getMessage());
						 ex.printStackTrace();
					 }
				}
				
			}
		}
	}*/

	
	public Boolean CompareBGBU(String infoPrjBGBU, String rmsPrjBGBU, String infoIRMBGBU, String infoSRMBGBU, String rmsIRMBGBU,String rmsSRMBGBU ){
		Boolean bgbuFlag=false;
		
		
		if(!InfogramActiveResource.BGBU.equalsIgnoreCase(infoPrjBGBU)||!InfogramActiveResource.BGBU.equalsIgnoreCase(rmsPrjBGBU)||!InfogramActiveResource.BGBU.equalsIgnoreCase(infoIRMBGBU))
			return bgbuFlag;
		
		if(!InfogramActiveResource.BGBU.equalsIgnoreCase(infoSRMBGBU)||!InfogramActiveResource.BGBU.equalsIgnoreCase(rmsIRMBGBU)||!InfogramActiveResource.BGBU.equalsIgnoreCase(rmsSRMBGBU))
			return bgbuFlag;
		
		
		return true;
	}
	/*private void assignDefalutProjectToBenchResource(ResourceAllocation resourceAllocation, Resource resource,DefaultProject defaultProject) {
		ResourceAllocation newResourceAllocation = new ResourceAllocation();
		 if(defaultProject != null) {
			 newResourceAllocation.setProjectId(defaultProject.getProjectId());
		 }
		 else{
			 try{
				 Project defaultNonSapProject =  projectDao.findProjectByProjectName(Constants.DEFAULNONSAPPROJECT);
				 if(defaultNonSapProject != null)
					 newResourceAllocation.setProjectId(defaultNonSapProject);
				 else
					 return;
			 }
			 catch(Exception ex) {
				 logger.error("Exceptio occured -------" + ex.getMessage());
				 ex.printStackTrace();
				 return;
			 }
		 }
		 final Calendar calendar = Calendar.getInstance();
		 Date currentDate = new Date();
		 calendar.setTime(currentDate);
		 calendar.add(Calendar.DAY_OF_YEAR, 1);
		 Date nextDay = calendar.getTime();
		 try{
			 AllocationType allocationType =  allocationTypeDao.getAllocationTypeByType(Constants.NONBILLABLE_UNALLOCATED);
			 if(allocationType == null){
				 logger.error(Constants.NONBILLABLE_UNALLOCATED + " not found");
				 return;
			 }
			 newResourceAllocation.setAllocationTypeId(allocationType);
		 }
		 catch(Exception ex){
			 logger.error("Exceptio occured -------" + ex.getMessage());
			 ex.printStackTrace();
			 return;
		 }

		 resource.setCurrentProjectId(newResourceAllocation.getProjectId());
		 try{
			 boolean isResoucePrjoectUpdated = resourceDao.saveOrupdate(resource);
			 if(isResoucePrjoectUpdated) {
				 newResourceAllocation.setEmployeeId(resource);
				 newResourceAllocation.setAllocStartDate(nextDay);
				 Resource currentResource = UserUtil.userContextDetailsToResource(UserUtil.getCurrentResource());
//				  if currentResource found null then it means its system updated.
				 newResourceAllocation.setAllocatedBy(currentResource);
				 newResourceAllocation.setUpdatedBy(currentResource);
				 newResourceAllocation.setDesignation(resource.getDesignationId());
				 newResourceAllocation.setGrade(resource.getGradeId());
				 newResourceAllocation.setBuId(resource.getBuId());
				 newResourceAllocation.setCurrentBuId(resource.getCurrentBuId());
				 newResourceAllocation.setLocation(resource.getLocationId());
				 newResourceAllocation.setCurrentReportingManager(resource.getCurrentReportingManager());
				 newResourceAllocation.setCurrentReportingManagerTwo(resource.getCurrentReportingManagerTwo());
				 newResourceAllocation.setOwnershipTransferDate(resource.getTransferDate());
				 newResourceAllocation.setCurProj(true);
				 newResourceAllocation.setLastupdatedTimestamp(currentDate);
				 newResourceAllocation.setProjectEndRemarks("NA");
				 newResourceAllocation.setResourceType(Constants.INTERNAL_USER);
				 
				 boolean isSuccess =  resourceAllocationDao.saveOrupdate(newResourceAllocation);
				 if(isSuccess)
					 resourceAllocationService.updateAllocationSeqForResourceAllocations(resource);
			 }
			 else{
				 logger.info("Resource update failed , could not update project for resource " +resource.getEmailId());
				 throw new RuntimeException("Resource update failed , could not update project for resource " +resource.getEmailId());
			 }
		 }
		 catch(Exception ex){
			 logger.error("Exceptio occured -------" + ex.getMessage());
			 ex.printStackTrace();
			 throw new RuntimeException(ex.getMessage());
		 }
	}
	*/
	
	// this method is similar to @thirtyDaysblockedResourceReportEmail and primarily designed to run from controller,
	// for testing purpose.

	public String thirtyDaysblockedResourceReportEmailFromController(Integer days, Date date) {
	
		logger.info("-----------------------------Inside @ThirtyDaysblockedResourceReportEmailByController Start-------------------------------");
		String commaSepratedBGBUName = StringUtils.trimToEmpty(Constants.BGBU_NONSEP_BENCHPROJECT);
		List<Integer> orgIds  = this.getBUIdByBGBUName(commaSepratedBGBUName);
		
		/* isTraineeProject = false states that Trainee project's resources will not be included to come on bench. */
		Boolean isTraineeProject = false;
		if(days == null || days < 1)
		   days = Integer.parseInt(StringUtils.trimToEmpty(Constants.BLOCKEDRESOURCE_DAYS));
		
		Date nexteDay = (date == null) ? DateUtil.getNextOrBackDate(1) : date;
		
		List<ResourceAllocation> resourceAllocations = resourceAllocationDao.getAllocationBlockedResourceWithThirtyDaysMore(orgIds,isTraineeProject, days,nexteDay);
		String totalBlockedResource = String.valueOf(resourceAllocations.size());
		logger.info("Total Blocked Resource -------- :" +totalBlockedResource);
		
		boolean isResourceExist = (resourceAllocations != null && !resourceAllocations.isEmpty()) ? true : false;
		
		File tempFile = null;
		
		boolean isManagerRequired = true;
		boolean isDelManagerRequired = true ;
		
		if(isResourceExist) {
			
			List<Map<String, Object>> resourceList = this.prepareReportDateforBlockedResource(resourceAllocations);
			
			if(!resourceList.isEmpty()) {
				
				Set<String> keySet = null ;
				keySet = resourceList.get(0).keySet();
				
				String[] headerNames = Arrays.copyOf(keySet.toArray(), keySet.size(), String[].class);
				
				// Blank workbook. 
				HSSFWorkbook workbook = new HSSFWorkbook(); 
				
				// Create a blank sheet. 
				HSSFSheet sheet = BlockedResourceExcelUtility.getHSSFSheet(workbook, "BlockedResourceReport");
				
				// Create a cell style for header.
				HSSFCellStyle hssfCellStyle = BlockedResourceExcelUtility.getCellStyleForHeaders(workbook);
				
				// create headers in excel file.
				BlockedResourceExcelUtility.createHeadersForExcel(sheet, hssfCellStyle, headerNames);
				
				// put all data in excel file.
				
				BlockedResourceExcelUtility.putBlockedResourceDatainReportFile(workbook, sheet, resourceList);
				
				String fileName = "BlockedResourceReport_";
				
				fileName = fileName.concat(DateUtil.getCurrentDateTime(new Date(),Constants.DATE_PATTERN_6));
				fileName = fileName.replaceAll("[^a-zA-Z0-9]+","_");
				String fileExt = ".xls";
				
				tempFile = BlockedResourceExcelUtility.writeOutputFile(workbook, fileName, fileExt);
			}
		}
		

		Set<String> buHeadEmailId = new HashSet<String>();
		Set<String> managersEmailId = new HashSet<String>();
		
		try {
			String needToSendBUHead = StringUtils.trimToEmpty(Constants.RESOURCE_BUUNITHEAD);
			if (needToSendBUHead.equalsIgnoreCase("true")) {
				buHeadEmailId = this.getBlockedResourceManagerEmail(resourceAllocations, Constants.BUHEAD);
			}
			
			if(isManagerRequired) {
				managersEmailId.addAll(this.getBlockedResourceManagerEmail(resourceAllocations, Constants.MANAGER));
			}
			
			if(isDelManagerRequired) {
				managersEmailId.addAll(this.getBlockedResourceManagerEmail(resourceAllocations, Constants.DELIVERYMANAGER));
			}
			
			this.preapreEmailForBlocedResource(tempFile, buHeadEmailId, managersEmailId, isResourceExist);
		}
		catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured ------" + e.getMessage());
		}
		
		logger.info("-----------------------------Inside @ThirtyDaysblockedResourceReportEmail End-------------------------------");
		
		return totalBlockedResource ;
	}
	
	// this is a scheduler method for which cron job is defined in task_schedulaer.xml file. 
	
	@Transactional
	public void thirtyDaysblockedResourceReportEmail() {

		logger.info("-------------------Inside @ThirtyDaysblockedResourceReportEmail Start-----------------------");
	

		String commaSepratedBGBUName = StringUtils.trimToEmpty(Constants.BGBU_NONSEP_BENCHPROJECT);
		List<Integer> orgIds = this.getBUIdByBGBUName(commaSepratedBGBUName);

		/*
		 * isTraineeProject = false states that Trainee project's resources will
		 * not be included to come on bench.
		 */
		Boolean isTraineeProject = false;
		int days = Integer.parseInt(StringUtils.trimToEmpty(Constants.BLOCKEDRESOURCE_DAYS));

		Date nexteDay = DateUtil.getNextOrBackDate(1);

		List<ResourceAllocation> resourceAllocations = resourceAllocationDao.getAllocationBlockedResourceWithThirtyDaysMore(orgIds, isTraineeProject, days, nexteDay);
		logger.info("Total Blocked Resource -------- :" + resourceAllocations.size());

		boolean isResourceExist = (resourceAllocations != null && !resourceAllocations.isEmpty()) ? true : false;

		File tempFile = null;
		
		// this variable notifies whether email is required to send project manager and delivery manager of the resource who are going on bench
		boolean isManagerRequired = true;
		boolean isDelManagerRequired = true ;

		if (isResourceExist) {

			List<Map<String, Object>> resourceList = prepareReportDateforBlockedResource(resourceAllocations);

			if (!resourceList.isEmpty()) {

				Set<String> keySet = null;
				keySet = resourceList.get(0).keySet();

				String[] headerNames = Arrays.copyOf(keySet.toArray(), keySet.size(), String[].class);

				// Blank workbook.
				HSSFWorkbook workbook = new HSSFWorkbook();

				// Create a blank sheet.
				HSSFSheet sheet = BlockedResourceExcelUtility.getHSSFSheet(workbook, "BlockedResourceReport");

				// Create a cell style for header.
				HSSFCellStyle hssfCellStyle = BlockedResourceExcelUtility.getCellStyleForHeaders(workbook);

				// create headers in excel file.
				BlockedResourceExcelUtility.createHeadersForExcel(sheet, hssfCellStyle, headerNames);

				// put all data in excel file.

				BlockedResourceExcelUtility.putBlockedResourceDatainReportFile(workbook, sheet, resourceList);

				String fileName = "BlockedResourceReport_";

				fileName = fileName.concat(DateUtil.getCurrentDateTime(new Date(), Constants.DATE_PATTERN_6));
				fileName = fileName.replaceAll("[^a-zA-Z0-9]+", "_");
				String fileExt = ".xls";

				tempFile = BlockedResourceExcelUtility.writeOutputFile(workbook, fileName, fileExt);
			}
		}

		Set<String> buHeadEmailId = new HashSet<String>();
		Set<String> managersEmailId = new HashSet<String>();
		
		try {
			String needToSendBUHead = StringUtils.trimToEmpty(Constants.RESOURCE_BUUNITHEAD);
			if (needToSendBUHead.equalsIgnoreCase("true")) {
				buHeadEmailId = this.getBlockedResourceManagerEmail(resourceAllocations, Constants.BUHEAD);
			}
			
			if(isManagerRequired) {
				managersEmailId.addAll(this.getBlockedResourceManagerEmail(resourceAllocations, Constants.MANAGER));
			}
			
			if(isDelManagerRequired) {
				managersEmailId.addAll(this.getBlockedResourceManagerEmail(resourceAllocations, Constants.DELIVERYMANAGER));
			}
			
			preapreEmailForBlocedResource(tempFile, buHeadEmailId, managersEmailId, isResourceExist);
		}
		catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured ------" + e.getMessage());
		}
		
		logger.info("-----------------------------Inside @ThirtyDaysblockedResourceReportEmail End-------------------------------");
	}
	
	public void preapreEmailForBlocedResource(File file, Set<String> BUHeadEmailIds, Set<String> managersEmailId, boolean isResourceExist) throws Exception {

		Map<String, Object> model = new HashMap<String, Object>();

		model.put(Constants.SUBJECT, Constants.BLOCKEDRESOURCE_SUBJECT);
		model.put(Constants.SENDEMAILID, Constants.FROM_MAIL);

		Set<String> toMailList = new HashSet<String>();
		toMailList.add(Constants.RMG_PDL_EMAIL);

		String toMail = StringUtils.trimToEmpty(Constants.BLOCKEDRESOURCE_TOEMAIL);
		if (!toMail.isEmpty()) {
			toMailList.addAll(Arrays.asList(toMail.split(",")));
		}

		if (!BUHeadEmailIds.isEmpty())
			toMailList.addAll(BUHeadEmailIds);

		Set<String> ccMailList = new HashSet<String>();
		ccMailList.addAll(managersEmailId);
		String ccMail = StringUtils.trimToEmpty(Constants.BLOCKEDRESOURCE_CCMAIL);
		if (!ccMail.isEmpty()) {
			ccMailList.addAll(Arrays.asList(ccMail.split(",")));
		}

		Set<String> bccMailList = new HashSet<String>();
		String bccMail = StringUtils.trimToEmpty(Constants.BLOCKEDRESOURCE_BCCMAIL);
		if (!bccMail.isEmpty()) {
			bccMailList.addAll(Arrays.asList(bccMail.split(",")));
		}

		try {
			String[] toMailArray = Arrays.copyOf(toMailList.toArray(), toMailList.size(), String[].class);
			String[] ccMailArray = Arrays.copyOf(ccMailList.toArray(), ccMailList.size(), String[].class);
			String[] bccMailArray = Arrays.copyOf(bccMailList.toArray(), bccMailList.size(), String[].class);

			String emailText = (isResourceExist) ? Constants.BLOCKEDRESOURCE_EMAILTEXT : Constants.NOBLOCKEDRESOURCE_EMAILTEXT;
			emailText = ObjectUtils.defaultIfNull(emailText,StringUtils.EMPTY);
			
			if (!Constants.ENV_VARIABLE.equalsIgnoreCase("PROD")) {
				Set<String> emailIdSet = new HashSet<String>();
				emailIdSet.addAll(toMailList);
				emailIdSet.addAll(ccMailList);
				emailIdSet.addAll(bccMailList);
				emailText = String.format(emailText, "Email ids for production Environment are-", emailIdSet.toString());
			}
			model.put(Constants.EMAIL_TEXT, emailText);
			
			emailHelper.sendEmailforBlockedResource(model, toMailArray, ccMailArray, bccMailArray, file);
		} 
		catch (Exception ex) {
			ex.printStackTrace();
			logger.error("-------Exception occured in @PreapreEmailForBlocedResource method-----" + ex.getMessage());
			if (!(ex instanceof MessagingException))
				throw ex;
		}
	}

	private List<Integer> getBUIdByBGBUName(String commaSepratedBGBUName)
	{
		if(commaSepratedBGBUName.isEmpty())
			return new ArrayList<Integer>();
		
		List<Integer> orgIds = new ArrayList<Integer>();
		try{
			String [] BGBUUnitArray = StringUtils.split(commaSepratedBGBUName, ",");
		
			if(BGBUUnitArray.length > 0) {
				for(int index = 0; index < BGBUUnitArray.length;index++){
				 String [] BGBU = StringUtils.split(BGBUUnitArray[index], "-");
				 orgIds.add(orgHierarchyService.findBUIdByBGBUName(BGBU[0], BGBU[1]));
				}
			}
		}
		catch (ArrayIndexOutOfBoundsException ex) {
			logger.error("Exceptio occured -------" + ex.getMessage());
			ex.printStackTrace();
			orgIds = null;
		}
		return orgIds;
	}

	private List<Map<String, Object>> prepareReportDateforBlockedResource(List<ResourceAllocation> resourceAllocations) {

		List<Map<String, Object>> resourceList = new ArrayList<Map<String, Object>>();
		if (resourceAllocations != null && !resourceAllocations.isEmpty()) {

			for (int index = 0; index < resourceAllocations.size(); index++) {
				Map<String, Object> dataMap = new LinkedHashMap<String, Object>();
				ResourceAllocation allocation = resourceAllocations.get(index);
				Resource resource = allocation.getEmployeeId();
				dataMap.put("yash id", resource.getYashEmpId());
				dataMap.put("employee name", resource.getEmployeeName());
				
				Project project = projectService.findProject(allocation.getProjectId().getId());
				dataMap.put("project name", project != null ? project.getProjectName() : "NA");
				
				Resource manager = null;
				Resource delManager = null;
				if(project != null) {
					manager = resourceService.find(project.getOffshoreDelMgr().getEmployeeId());
					delManager = resourceService.find(project.getDeliveryMgr().getEmployeeId());
				}
				dataMap.put("manager", manager != null ? manager.getEmployeeName() : "NA");
				dataMap.put("delivery manager", delManager != null ? delManager.getEmployeeName() : "NA");
				
				dataMap.put("allocation type", allocation.getAllocationTypeId().getAllocationType());
				String allocStartDate = Constants.DATE_FORMAT_NEW.format(allocation.getAllocStartDate());
				String allocEndDate = "";
				if (allocation.getAllocEndDate() != null) {
					allocEndDate = Constants.DATE_FORMAT_NEW.format(allocation.getAllocEndDate());
				}
				dataMap.put("allocation start date", allocStartDate);
				dataMap.put("allocation end date", allocEndDate);
				dataMap.put("allocation remarks", ObjectUtils.defaultIfNull(allocation.getAllocRemarks(), "NA"));
				
				Ownership ownership = allocation.getOwnershipId() ;
				if(ownership != null) {
					ownership = ownershipService.findById(ownership.getId());
				}
				dataMap.put("status", (ownership != null) ? ownership.getOwnershipName() : "NA");
				
				resourceList.add(dataMap);
			}

		}
		return resourceList;
	}
	
	public Set<String> getBlockedResourceManagerEmail(List<ResourceAllocation> allocations, final String mappingParamter) {
		
		if(!mappingParamter.equalsIgnoreCase(Constants.BUHEAD) && !mappingParamter.equalsIgnoreCase(Constants.MANAGER) && !mappingParamter.equalsIgnoreCase(Constants.DELIVERYMANAGER))
		{
			List<String> mappingParamSet = Arrays.asList(new String[] { Constants.BUHEAD , Constants.MANAGER, Constants.DELIVERYMANAGER });
			String errorMessage = "mapping parameter not mached with any defined One" + mappingParamSet.toString();
			logger.error(errorMessage);
			throw new IllegalArgumentException(errorMessage);
		}
		
		Set<String> emailIds = new HashSet<String>(allocations.size());
		for (Iterator<ResourceAllocation> iterator = allocations.iterator(); iterator.hasNext();) {
			ResourceAllocation allocation = iterator.next();
			Project project = projectService.findProject(allocation.getProjectId().getId());
			
			if(mappingParamter.equalsIgnoreCase(Constants.BUHEAD)) {
				Resource resource = resourceService.find(allocation.getEmployeeId().getEmployeeId());
				OrgHierarchy orgHierarchy = orgHierarchyService.fingOrgHierarchiesById(resource.getCurrentBuId().getId()); 
				Resource buHead = orgHierarchy.getEmployeeId();
				if (buHead != null) {
					emailIds.add(buHead.getEmailId());
				}
			}
			
			else if(mappingParamter.equalsIgnoreCase(Constants.MANAGER)) {
				Resource manager = resourceService.find(project.getOffshoreDelMgr().getEmployeeId());
				emailIds.add(manager.getEmailId());
			}
			
			else if(mappingParamter.equalsIgnoreCase(Constants.DELIVERYMANAGER)) {
				Resource delManager = resourceService.find(project.getDeliveryMgr().getEmployeeId());
				emailIds.add(delManager.getEmailId());
			}
		}	
		return emailIds;
	}

	@Transactional
	public void runProjectGoingtoCloseScheduler() {
		// TODO Auto-generated method stub
		logger.info("-------------runProjectGoingtoCloseScheduler method of SchedulerServiceImpl Start--------");
		try{
		List<RequestRequisitionSkill> list=requestRequisitionDao.getRRFDetailsForClosingProject();
		
		if(list!=null && list.size()>0){
			//calling method for sending mails
			sendMails(list);
			}
		else{
			sendMails();
		}
		}
		catch(Exception ex){
			logger.error("Exception occured in runProjectGoingtoCloseScheduler method-------" + ex.getMessage());
			ex.printStackTrace();
		}
		logger.info("-------------------runProjectGoingtoCloseScheduler method of SchedulerServiceImpl End-----");
		
	}
	//methods for getting Email's id
	private List<String> getMailList(String mailString, Boolean pdlFlag){
		logger.info("------------------- getMailList method of SchedulerServiceImpl Start-------");
		String[] pdlArrys;
		ArrayList<Integer> mailIds=new ArrayList<Integer>();
		List<String> finalCCList=new ArrayList<String>();
		
		if(mailString!=null && mailString.length()>0){
			
			pdlArrys =mailString.split(",");
			for(int i=0;i<=pdlArrys.length-1;i++)
			{
				mailIds.add(Integer.parseInt(pdlArrys[i].trim()));
			}
			if(pdlFlag)
				finalCCList=pDLEmailGroupDao.findPdlByIds(mailIds);
			else
				finalCCList=resourceService.findEmailById(mailIds);
				
		}
		logger.info("------------------- getMailList method of SchedulerServiceImpl End-------------");
		return finalCCList;
		
	}

	//method for sending mail foe RRF
	private void sendMails(List<RequestRequisitionSkill> list){
		
		logger.info("-------------------prepareMail() method of SchedulerServiceImpl Start----------------------------");
				
			for (RequestRequisitionSkill requestRequisitionSkill : list) {
			Map<String, Object> model = new HashMap<String, Object>();
			String pdlString="";
			String toMailIds="";
			String notifyMailIds="";
			String projectName="";
			String comments="";
			String customerGroupName="";
			String indentBy="";
			String statusRRF="";
			
			try{
			
			statusRRF=	requestRequisitionService.findRRFStatus(requestRequisitionSkill);
			
			if(!statusRRF.equalsIgnoreCase(Constants.CLOSED))
			{
				requestRequisitionSkill.setStatus(statusRRF);
				RequestRequisition  requestRequisition=requestRequisitionSkill.getRequestRequisition();
				pdlString=requestRequisition.getPdlEmailIds();
				toMailIds=requestRequisition.getSentMailTo();
				notifyMailIds=requestRequisition.getNotifyMailTo();
			
				List<String> finalCCList=new ArrayList<String>();
				List<String> finalToList=new ArrayList<String>();
				
				//Getting mailList for To
				finalToList=getMailList(toMailIds, false);
				for (String tomaIld : finalToList) {
						logger.info("-----------------Tolist after adding tomail email ids----------------------------"+tomaIld);
				}
							
				//Getting mailList for CC
				finalCCList=getMailList(pdlString, true);
				for (String pdlId : finalCCList) {
					logger.info("-----------------CClist after adding pdl email ids----------------------------"+pdlId);
				}
		
				//adding more mailids to CC	
				finalCCList.addAll(getMailList(notifyMailIds, false));
				finalCCList.add(Constants.CLOSING_PROJECT_RRF_CCMAIL);
				
				for (String notifymailId : finalCCList) {
						logger.info("-----------------CClist after adding notify email ids----------------------------"+notifymailId);
					}
				
			
				projectName=requestRequisition.getProject().getProjectName();
				comments=requestRequisition.getComments();
				indentBy=requestRequisition.getRequestor().getEmployeeName();
			
				if(requestRequisition.getGroup()!=null )
					customerGroupName=requestRequisition.getGroup().getCustomerGroupName();
		
				String[] sentMailCCIds = Arrays.copyOf(finalCCList.toArray(), finalCCList.toArray().length,String[].class); 
				String[] tomailIDsList=Arrays.copyOf(finalToList.toArray(), finalToList.toArray().length,String[].class); 
				  
				//preparing content for mail
				resourceHelper.setEmailContentForGoingtoCloseProject(model,  tomailIDsList, sentMailCCIds, projectName, comments, requestRequisition.getCustomer().getCustomerName(),requestRequisitionSkill.getRequirementId() , requestRequisitionSkill.getNoOfResources(), requestRequisitionSkill.getDesignation().getDesignationName(), 
						indentBy, requestRequisition.getAmJobCode(), customerGroupName,requestRequisition.getProjectBU(), requestRequisition.getSuccessFactorId(), requestRequisitionSkill.getRequirementArea());
				
				//Using emailHelper Utility to send mail
				emailHelper.sendEmail(model, tomailIDsList, sentMailCCIds, null);
			}
			logger.info("-------------------prepareMail() method of SchedulerServiceImpl End-------------------------------");
			
			}
			catch(Exception ex)
			{
				logger.error("Exceptio occured -------" + ex.getMessage());
				ex.printStackTrace();
			}
			
		}
		
	}
	private void sendMails(){
		List<String> finalToList=new ArrayList<String>();
		Map<String, Object> model = new HashMap<String, Object>();
	
		finalToList.add(Constants.CLOSING_PROJECT_RRF_CCMAIL);
	
		model.put(Constants.FILE_NAME, Constants.Project_GoingTo_Closed_HTML);
		model.put("subject", Constants.CLOSING_PROJECT_RRF_SUBJECT);
		model.put("sendMailFrom",  Constants.RMG_PDL_EMAIL); 
		
		String currentDate=org.yash.rms.util.DateUtil.getStringDate(new Date());
		String currentTime=org.yash.rms.util.DateUtil.getStringTime(new Date());
		model.put("currentDate", currentDate);
		model.put("currentTime", currentTime);
	
		try{
						
			String[] tomailIDsList=Arrays.copyOf(finalToList.toArray(), finalToList.toArray().length,String[].class); 
		
			emailHelper.sendEmail(model, tomailIDsList, null,null);
		}
		catch(Exception ex){
			logger.error("Exceptio occured -------" + ex.getMessage());
			ex.printStackTrace();
		}
	}
	public void schedularTest() {
		Timestamp currentTime = new Timestamp(System.currentTimeMillis());
		logger.info("Test Schedular start --> "+currentTime);
		RequestRequisitionDao requestRequisitionDao= AppContext.getApplicationContext().getBean(RequestRequisitionDao.class);
		List<RequestRequisitionSkill> list=requestRequisitionDao.getRRFDetailsForClosingProject();
	}
	
}
