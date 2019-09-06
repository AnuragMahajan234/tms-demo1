package org.yash.rms.service.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.yash.rms.dao.RequestReportDao;
import org.yash.rms.dao.RequestRequisitionResourceDao;
import org.yash.rms.dto.AllocationTypeDTO;
import org.yash.rms.dto.ProjectDTO;
import org.yash.rms.dto.RequestRequisitionDashboardDTO;
import org.yash.rms.dto.RequestRequisitionSkillDTO;
import org.yash.rms.report.dto.RequestRequisitionReport;
import org.yash.rms.report.dto.ResourceRequisitionProfileStatus;
import org.yash.rms.report.dto.ResourceRequisitionProfileStatusReport;
import org.yash.rms.service.ProjectAllocationService;
import org.yash.rms.service.RequestRequisitionDashboardService;
import org.yash.rms.service.RequestRequisitionReportService;
import org.yash.rms.service.RequestRequisitionResourceService;
import org.yash.rms.util.Constants;

@Service("RequestRequisitionReportService")
public class RequestRequisitionReportServiceImpl implements RequestRequisitionReportService{
	
	@Autowired	
	RequestRequisitionDashboardService requestRequisitionDashboardService;
	
	@Autowired
	private RequestReportDao requestReportDao;
	
	@Autowired 
	@Qualifier("requestRequisitionResourceService")
	private RequestRequisitionResourceService requestRequisitionResourceService;
	
	@Autowired
	@Qualifier("ProjectAllocationService")
	private ProjectAllocationService projectAllocationService;
	
	@Autowired
	@Qualifier("requestRequisitionResourceDao")
	private RequestRequisitionResourceDao requestRequisitionResourceDao;
	
	private static final Logger logger = LoggerFactory.getLogger(RequestRequisitionReportServiceImpl.class);


	private RequestRequisitionReport processRequestRequisitionData(RequestRequisitionSkillDTO skill) {
		RequestRequisitionReport requisitionReport = new RequestRequisitionReport(); 
		 requisitionReport.setClosed(getStringValue(skill.getClosed()));
		 requisitionReport.setDesignationName(skill.getDesignation().getDesignationName());
		 requisitionReport.setHold(getStringValue(skill.getHold()));
		 requisitionReport.setLost(getStringValue(skill.getLost()));
		 requisitionReport.setNotFitForRequirement(getStringValue(skill.getNotFitForRequirement()));
		 requisitionReport.setOpen(getStringValue(skill.getOpen()));
		 requisitionReport.setRemaining(skill.getRemaining());
		 requisitionReport.setRequirementId(skill.getRequirementId());
		 requisitionReport.setShortlisted(getStringValue(skill.getShortlisted()));
		 requisitionReport.setSkill(skill.getSkill().getSkill());
		 requisitionReport.setStatus(skill.getStatus());
		 requisitionReport.setSubmissions(getStringValue(skill.getSubmissions()));
		 requisitionReport.setRemaining(skill.getRemaining());
		 requisitionReport.setRequisitionResourceList(skill.getRequisitionResourceList());
		 requisitionReport.setLocationName(skill.getLocationName());
		 requisitionReport.setClientName(skill.getClientName());
		 requisitionReport.setBusinessGroupName(skill.getBusinessGroupName());
		 requisitionReport.setRequestedBy(skill.getRequestedBy());
		 requisitionReport.setNoOfResources(String.valueOf(skill.getNoOfResources()));
		 requisitionReport.setId(skill.getId());
		 requisitionReport.setAllocationDTO(skill.getAllocationType());
		 requisitionReport.setRequestedDate(skill.getRequestRequisition().getDate());
		 requisitionReport.setAllocationType(skill.getAllocationType().getAllocationType());
		 requisitionReport.setAddtionalComments(skill.getAdditionalComments());
		 requisitionReport.setRequestRequisitionId(skill.getRequestRequisition().getId());
		 if(skill.getRequirementArea()!=null) {
			 requisitionReport.setRequirementArea(skill.getRequirementArea());
		 }else {
			 requisitionReport.setRequirementArea(Constants.NON_SAP);
		 }
		return requisitionReport;
	}
	
	private String getStringValue(Integer value){
		return value != null ? String.valueOf(value) : "";
	}
	
	public List<RequestRequisitionReport> getRequestRequisitionReportForDateCriteria(Integer id, String role,
			String startDate,String endDate) {
		
		logger.info("--------getRequestRequisitionReportForDateCriteria of RequestRequisitionReportServiceImpl method start-------");
		List<RequestRequisitionReport> reportData = new ArrayList<RequestRequisitionReport>();
		try{
			Date startDt=new SimpleDateFormat("MM/dd/yyyy").parse(startDate); 
			Date endDt=new SimpleDateFormat("MM/dd/yyyy").parse(endDate); 
			RequestRequisitionDashboardDTO requestRequisitionDashboardDTO =	 
					requestRequisitionDashboardService.getRequestRequisitionReportForDateCriteria(id, role,startDt,endDt);
			for (RequestRequisitionSkillDTO skill : requestRequisitionDashboardDTO.getRequestRequisitionDashboardList()){
						 RequestRequisitionReport requisitionReport = processRequestRequisitionData(skill);
						 reportData.add(requisitionReport);
			}
		}catch(ParseException ex){
			logger.error("Start Date and End Date should be in MM/dd/yyyy format.");
		}
		logger.info("--------getRequestRequisitionReportForDateCriteria of RequestRequisitionReportServiceImpl method start-------");
		return reportData;
	}
	
	public List<RequestRequisitionReport> getRequestRequisitionReport(Integer userId, String userRole,
			 String customerIds, String groupIds, String statusIds, boolean isLoadSubTable, String hiringUnits, String reqUnits){
		List<RequestRequisitionReport> reportData = new ArrayList<RequestRequisitionReport>();
		List<Integer> customerList = null;
		List<Integer> groupList = null;
		List<String> hiringUnitList = null;
		List<String> requestorUnitList = null;
		String status = null;
		List<RequestRequisitionReport> requestRequisitionReportList= null;
		List<Object[]> requestRequisitionSkillDomainList = null;
		logger.info("--------getRequestRequisitionReport of RequestRequisitionReportServiceImpl method start-------");
	
			if(customerIds !=null && customerIds.trim().length()>0){
				customerList = new ArrayList<Integer>();
				for (String customerId : customerIds.split(",")) {
					customerList.add(Integer.parseInt(customerId));
				}
			}
			
			if(groupIds !=null && groupIds.trim().length()>0){
				groupList = new ArrayList<Integer>();
				for (String groupId : groupIds.split(",")) {
					groupList.add(Integer.parseInt(groupId));
				}
			}
			

			if(hiringUnits !=null && hiringUnits.trim().length()>0){
				hiringUnitList = new ArrayList<String>();
				for (String hiringUnit : hiringUnits.split(",")) {
					hiringUnitList.add(hiringUnit);
				}
			}

			if(reqUnits !=null && reqUnits.trim().length()>0){
				requestorUnitList = new ArrayList<String>();
				for (String reqUnit : reqUnits.split(",")) {
					requestorUnitList.add(reqUnit);
				}
			}
			
			try{
				
				List<Integer> projectList = null;
				if(userRole.equalsIgnoreCase(Constants.ROLE_DEL_MANAGER) || userRole.equalsIgnoreCase(Constants.ROLE_MANAGER)
						|| userRole.equalsIgnoreCase(Constants.ROLE_BG_ADMIN)){
					List <ProjectDTO> projectDTOList =  projectAllocationService.getProjectsForUser(userId, userRole, "active");
					if(projectDTOList != null){
						projectList = new ArrayList<Integer>();
						for(ProjectDTO projectDTO : projectDTOList){
							projectList.add(projectDTO.getProjectId());
						}
					}
				}
				
				requestRequisitionSkillDomainList = requestReportDao.getSkillRequestReport(userId, userRole, customerList, groupList, projectList,statusIds, hiringUnitList, requestorUnitList);
				
				requestRequisitionReportList =   getRequestRequisitionReportList(requestRequisitionSkillDomainList, isLoadSubTable);
			}catch (Exception e) {
				e.printStackTrace();
			}

		logger.info("--------getRequestRequisitionReport of RequestRequisitionReportServiceImpl method ended-------");
		return requestRequisitionReportList;
	}
	
	//Created by me
	public List<RequestRequisitionReport> getDashBoardDataReport(Integer userId, String userRole,
			String customerIds, String groupIds, String statusIds, String hiringUnits, String reqUnits){
		List<RequestRequisitionReport> reportData = new ArrayList<RequestRequisitionReport>();
		/*Date startDt = null;
		Date endDt = null;*/
		List<Integer> customerList = null;
		List<Integer> groupList = null;
		String status = null;
		List<String> hiringUnitList = null;
		List<String> requestorUnitList= null;
		logger.info("--------getRequestRequisitionDashBoardReport of RequestRequisitionReportServiceImpl method start-------");
	//	try{
			
			/*if(startDate!=null && startDate.trim().length()>0){
				startDt=new SimpleDateFormat("MM/dd/yyyy").parse(startDate);
			}
			
			if(endDate !=null && endDate.trim().length()>0){
				endDt=new SimpleDateFormat("MM/dd/yyyy").parse(endDate); 
			}*/
			
			if(customerIds !=null && customerIds.trim().length()>0){
				customerList = new ArrayList<Integer>();
				for (String customerId : customerIds.split(",")) {
					customerList.add(Integer.parseInt(customerId));
				}
			}
			
			if(groupIds !=null && groupIds.trim().length()>0){
				groupList = new ArrayList<Integer>();
				for (String groupId : groupIds.split(",")) {
					groupList.add(Integer.parseInt(groupId));
				}
			}
			
			if(statusIds !=null && statusIds.trim().length()>0){
				if(statusIds.split(",").length<=1){
					status = statusIds.split(",")[0];
				}
			}
			
			if(hiringUnits !=null && hiringUnits.trim().length()>0){
				hiringUnitList = new ArrayList<String>();
				for (String hiringUnit : hiringUnits.split(",")) {
					hiringUnitList.add(hiringUnit);
				}
			}

			if(reqUnits !=null && reqUnits.trim().length()>0){
				requestorUnitList = new ArrayList<String>();
				for (String reqUnit : reqUnits.split(",")) {
					requestorUnitList.add(reqUnit);
				}
			}
			
			reportData=requestRequisitionDashboardService.getDashBoardDataReport(userId, userRole, customerList, groupList, status, hiringUnitList, requestorUnitList);
			
			
		/*}catch(ParseException ex){
			logger.error("Start Date and End Date should be in MM/dd/yyyy format.");
		}*/
		logger.info("--------getRequestRequisitionDashBoardReport of RequestRequisitionReportServiceImpl method ended-------");
		return reportData;
	}
	
	private List<RequestRequisitionReport> getRequestRequisitionReportList(List<Object[]> reportList, boolean isLoadSubTable){
		List<RequestRequisitionReport> requestRequisitionReportList = null;
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		if (reportList != null && !reportList.isEmpty()) {
			requestRequisitionReportList = new ArrayList<RequestRequisitionReport>();
			int columnIndex = 0;
			for (Object[] row : reportList) {
				columnIndex = 0;
				RequestRequisitionReport requestRequisitionReport = new RequestRequisitionReport();
				requestRequisitionReport.setId((Integer)row[columnIndex]);
				columnIndex++;
				requestRequisitionReport.setRequirementId((String)row[columnIndex]);
				columnIndex++;
				requestRequisitionReport.setRequirementType(String.valueOf((Integer)row[columnIndex]));
				columnIndex++;
				requestRequisitionReport.setStatus((String)row[columnIndex]);
				columnIndex++;
				requestRequisitionReport.setNoOfResources(String.valueOf((Integer)row[columnIndex]));
				columnIndex++;
				requestRequisitionReport.setRemaining((String)row[columnIndex]);
				columnIndex++;
				requestRequisitionReport.setOpen(String.valueOf((Integer)row[columnIndex]));
				columnIndex++;
				requestRequisitionReport.setClosed(String.valueOf((Integer)row[columnIndex]));
				columnIndex++;
				requestRequisitionReport.setHold(String.valueOf((Integer)row[columnIndex]));
				columnIndex++;
				requestRequisitionReport.setLost(String.valueOf((Integer)row[columnIndex]));
				columnIndex++;
				requestRequisitionReport.setSubmissions(String.valueOf((Integer)row[columnIndex]));
				columnIndex++;
				requestRequisitionReport.setShortlisted(String.valueOf((Integer)row[columnIndex]));
				columnIndex++;
				requestRequisitionReport.setNotFitForRequirement(String.valueOf((Integer)row[columnIndex]));
				columnIndex++;
				requestRequisitionReport.setRequestedDate(dateFormat.format((Date)row[columnIndex]));
				columnIndex++;
				requestRequisitionReport.setAmJobCode((String)row[columnIndex]);
				columnIndex++;
				requestRequisitionReport.setRmgPOC((String)row[columnIndex]);
				columnIndex++;
				requestRequisitionReport.setTacTeamPOC((String)row[columnIndex]);
				columnIndex++;
				//check null
				if(null!=row[columnIndex]) {
					String value = (String)row[columnIndex];
					if(value.equals(Constants.NON_SAP)) {
						requestRequisitionReport.setRequirementArea(Constants.NON_SAP);
					}else if(value.equals(Constants.SAP)) {
						requestRequisitionReport.setRequirementArea(Constants.SAP);
					}else {
						requestRequisitionReport.setRequirementArea(Constants.NON_SAP);
					}
				}else {
					requestRequisitionReport.setRequirementArea(Constants.NON_SAP);
				}
				columnIndex++;
				requestRequisitionReport.setClientName((String)row[columnIndex]);
				columnIndex++;
				requestRequisitionReport.setAllocationType((String)row[columnIndex]);
				{ 
					AllocationTypeDTO allocationTypeDTO = new AllocationTypeDTO();
					allocationTypeDTO.setAllocationType(requestRequisitionReport.getAllocationType());
					columnIndex++;
					allocationTypeDTO.setId((Integer)row[columnIndex]);
					requestRequisitionReport.setAllocationDTO(allocationTypeDTO);
				}
				columnIndex++;
				requestRequisitionReport.setAliasAllocationName((String)row[columnIndex]);
				columnIndex++;
				requestRequisitionReport.setLocationName((String)row[columnIndex]);
				columnIndex++;
				requestRequisitionReport.setDesignationName((String)row[columnIndex]);
				columnIndex++;
				requestRequisitionReport.setRequestedBy((String)row[columnIndex]);
				columnIndex++;
					requestRequisitionReport.setAddtionalComments((String)row[columnIndex]);
				columnIndex++;
				requestRequisitionReport.setCustGroup((String)row[columnIndex]);
				columnIndex++;
				requestRequisitionReport.setBusinessGroupName((String)row[columnIndex]);
				columnIndex++;
				requestRequisitionReport.setSkill((String)row[columnIndex]);
				columnIndex++;
				requestRequisitionReport.setRequestRequisitionId((Integer)row[columnIndex]);
				columnIndex++;
				requestRequisitionReport.setSentToList((String)row[columnIndex]);
				columnIndex++;
				requestRequisitionReport.setPdlList((String)row[columnIndex]);
				columnIndex++;
				requestRequisitionReport.setReportStatus((String)row[columnIndex]);				
				columnIndex++;
				if(row[columnIndex]!=null &&row[columnIndex]!="") {
                requestRequisitionReport.setRrfCloserDate(dateFormat.format((Date)row[columnIndex]));
				}else {
	            requestRequisitionReport.setRrfCloserDate((String)row[columnIndex]);   
				}
				columnIndex++;
				requestRequisitionReport.setHiringUnit((String)row[columnIndex]);
				columnIndex++;
				requestRequisitionReport.setReqUnit((String)row[columnIndex]);	
				
				if(isLoadSubTable){
				requestRequisitionReport.setRequisitionResourceList(
						requestRequisitionResourceService.
						getRequestRequisitionResources(null, null,requestRequisitionReport.getId(), null));
				}
				
				requestRequisitionReportList.add(requestRequisitionReport);
			}
		}
		return requestRequisitionReportList;
	}
	public List<ResourceRequisitionProfileStatusReport> getResourceRequisitionProfileStatusReport(Integer userId,
			String userRole, String startDate, String endDate, String customerIds) {
		List<ResourceRequisitionProfileStatusReport> profileStatusReport = null;
		Date startDt = null;
		Date endDt = null;
		List<Integer> customerList = null;
			try{
				if(startDate!=null && startDate.trim().length()>0){
					startDt=new SimpleDateFormat("MM/dd/yyyy").parse(startDate);
				}
				if(endDate !=null && endDate.trim().length()>0){
					endDt=new SimpleDateFormat("MM/dd/yyyy").parse(endDate);
					Calendar cal = Calendar.getInstance();
					cal.setTime(endDt);
					cal.add(Calendar.DATE, 1);
					endDt = cal.getTime();
				}
				if(customerIds !=null && customerIds.trim().length()>0){
					customerList = new ArrayList<Integer>();
					for (String customerId : customerIds.split(",")) {
						customerList.add(Integer.parseInt(customerId));
					}
				}
			}catch (ParseException e) {
				logger.error("Start Date and End Date should be in MM/dd/yyyy format.");
				return null;
			}
		List<Object[]> reportList = requestRequisitionResourceDao.getResourceRequisitionProfileStatusCount(userId, userRole, startDt, endDt, customerList);
		profileStatusReport = getResourceRequisitionProfileStatusReportList(reportList);
		return profileStatusReport;
	}
	private List<ResourceRequisitionProfileStatusReport> getResourceRequisitionProfileStatusReportList(List<Object[]> reportList){
		List<ResourceRequisitionProfileStatusReport> profileStatusReport = null;
		if(reportList !=null && reportList.size()>0){
			profileStatusReport = new ArrayList<ResourceRequisitionProfileStatusReport>();
			int columnIndex = 0;
			int clientId = 0;
			int profileStatusId = 0;
			int resourceCount = 0;
			ResourceRequisitionProfileStatusReport resourceRequisitionProfileStatusReport =null;
			ResourceRequisitionProfileStatus resourceRequisitionProfileStatus = null;
			List<ResourceRequisitionProfileStatus> resourceRequisitionProfileStatusList = null;
			for (Object[] row : reportList) {
				columnIndex = 0;
				if ((Integer) row[columnIndex] != clientId || clientId == 0) {
					clientId = (Integer) row[columnIndex];
					resourceRequisitionProfileStatusReport = new ResourceRequisitionProfileStatusReport();
					profileStatusReport.add(resourceRequisitionProfileStatusReport);
					resourceRequisitionProfileStatusList = new ArrayList<ResourceRequisitionProfileStatus>();
					resourceRequisitionProfileStatusReport.setClientId((Integer) row[columnIndex]);
					columnIndex++;
					resourceRequisitionProfileStatusReport.setClientName((String) row[columnIndex]);
					columnIndex++;
					resourceRequisitionProfileStatus = new ResourceRequisitionProfileStatus();
					resourceRequisitionProfileStatus.setProfileStatusId(Integer.valueOf((String) row[columnIndex]));
					profileStatusId = Integer.valueOf((String) row[columnIndex]);
					columnIndex++;
					resourceRequisitionProfileStatus.setProfileStatusName((String) row[columnIndex]);
					columnIndex++;
					if(((BigInteger) row[columnIndex]).intValue() == 0){
						columnIndex++;
						resourceCount = ((BigDecimal)row[columnIndex]).intValue();
						resourceRequisitionProfileStatusReport.setTotalOpen(resourceCount);
					}else if (((BigInteger) row[columnIndex]).intValue() == 1) {
						columnIndex++;
						resourceCount = ((BigDecimal) row[columnIndex]).intValue();
						resourceRequisitionProfileStatus.setExternalResourceCount(resourceCount);
					} else {
						columnIndex++;
						resourceCount = ((BigDecimal) row[columnIndex]).intValue();
						resourceRequisitionProfileStatus.setInternalResourceCount(resourceCount);
					}
					resourceRequisitionProfileStatusList.add(resourceRequisitionProfileStatus);
				} else {
					columnIndex++;
					columnIndex++;
					if (profileStatusId != Integer.valueOf((String) row[columnIndex])) {
						profileStatusId = Integer.valueOf((String) row[columnIndex]);
						resourceRequisitionProfileStatus = new ResourceRequisitionProfileStatus();
						resourceRequisitionProfileStatus.setProfileStatusId(profileStatusId);
						columnIndex++;
						resourceRequisitionProfileStatus.setProfileStatusName((String) row[columnIndex]);
						columnIndex++;
						if (((BigInteger) row[columnIndex]).intValue() == 1) {
							columnIndex++;
							resourceCount = ((BigDecimal) row[columnIndex]).intValue();
							resourceRequisitionProfileStatus.setExternalResourceCount(resourceCount);
						} else {
							columnIndex++;
							resourceCount = ((BigDecimal) row[columnIndex]).intValue();
							resourceRequisitionProfileStatus.setInternalResourceCount(resourceCount);
						}
					} else {
						columnIndex++;
						columnIndex++;
						if (((BigInteger) row[columnIndex]).intValue() == 1) {
							columnIndex++;
							resourceCount = ((BigDecimal) row[columnIndex]).intValue();
							resourceRequisitionProfileStatus.setExternalResourceCount(resourceCount);
						} else {
							columnIndex++;
							resourceCount = ((BigDecimal) row[columnIndex]).intValue();
							resourceRequisitionProfileStatus.setInternalResourceCount(resourceCount);
						}
					}
					resourceRequisitionProfileStatusList.add(resourceRequisitionProfileStatus);
				}
				if(profileStatusId ==1){
					resourceRequisitionProfileStatusReport.setSubmittedProfileStatus(resourceRequisitionProfileStatus);
				}else if(profileStatusId ==2){
					resourceRequisitionProfileStatusReport.setShortlistedProfileStatus(resourceRequisitionProfileStatus);
				}else if(profileStatusId ==8){
					resourceRequisitionProfileStatusReport.setClosedProfileStatus(resourceRequisitionProfileStatus);
				}else if(profileStatusId ==3){
					resourceRequisitionProfileStatusReport.setRejectedProfileStatus(resourceRequisitionProfileStatus);
				}
			}
		}
		return profileStatusReport;
	}

}
