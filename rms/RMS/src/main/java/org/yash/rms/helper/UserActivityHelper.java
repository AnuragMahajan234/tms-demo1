package org.yash.rms.helper;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.yash.rms.dao.UserActivityDao;
import org.yash.rms.domain.Activity;
import org.yash.rms.domain.CustomActivity;
import org.yash.rms.domain.Project;
import org.yash.rms.domain.ProjectModule;
import org.yash.rms.domain.ProjectTicketPriority;
import org.yash.rms.domain.ProjectTicketStatus;
import org.yash.rms.domain.Resource;
//import org.yash.rms.domain.ApproveStatus;
import org.yash.rms.domain.ResourceAllocation;
import org.yash.rms.domain.UserActivity;
import org.yash.rms.domain.UserTimeSheet;
import org.yash.rms.domain.UserTimeSheetDetail;
import org.yash.rms.dto.TimesheetSubmissionDTO;
import org.yash.rms.form.NewTimeSheet;
import org.yash.rms.service.ProjectStatusService;
import org.yash.rms.service.ProjectTicketsService;
import org.yash.rms.service.UserActivityService;
import org.yash.rms.util.Constants;
import org.yash.rms.util.GeneralFunction;
//import org.yash.rms.util.UserUtil;
import org.yash.rms.util.UserUtil;

@Component("UserActivityHelper")
public class UserActivityHelper {

	@Autowired
	private UserUtil userUtil;

	@Autowired @Qualifier("UserActivityDao")
	private UserActivityDao userActivityDao;

	@Autowired
	private ProjectTicketsService projectTicketsService;

	@Autowired
	private ProjectStatusService projectStatusService;
	
	private static final Logger logger = LoggerFactory.getLogger(UserActivityHelper.class);


	public void populateBeanFromTimeSheet(NewTimeSheet timeSheet, Resource resource, UserTimeSheet userTimeSheet, Date weekStartDate, Date weekEndDate, boolean fromRestService) {
		
		if (null == timeSheet) return;
		
		Activity activity = new Activity();
		CustomActivity customActivity = new CustomActivity();

		//Setting Activity ID
		if (timeSheet.getActivityId() != null) {
			
			String activityId = timeSheet.getActivityId();
			
			
			if (!activityId.matches("[0-9]+")) {
				String [] activities = activityId.split("_");
				
				if(activities[1].equalsIgnoreCase("D")) {
						
					activity.setId(Integer.parseInt(activities[0]));
					
					userTimeSheet.setActivityId(activity);
					
				} else if (activities[1].equalsIgnoreCase("C")) {
					
					customActivity.setId(Integer.parseInt(activities[0]));
						
					userTimeSheet.setCustomActivityId(customActivity);
				}
				
			} else {
				activity.setId(Integer.parseInt(timeSheet.getActivityId()));
				userTimeSheet.setActivityId(activity);
			}
			
		} else if (timeSheet.getCustomActivityId() != null) {
			
			customActivity.setId(timeSheet.getCustomActivityId());
			userTimeSheet.setCustomActivityId(customActivity);
		}
		//Setting ResourceAllocation ID
		ResourceAllocation resourceAllocation = new ResourceAllocation();
			resourceAllocation.setId(timeSheet.getResourceAllocId());
		
		userTimeSheet.setResourceAllocId(resourceAllocation);
		userTimeSheet.setModule(timeSheet.getModule());
		userTimeSheet.setSubModule(timeSheet.getSubModule());
		userTimeSheet.setTicketNo(timeSheet.getTicketNo());
		//Set the timesheet if this is an exisiting record
		if (null != timeSheet.getId()) {
			userTimeSheet.setId(BigInteger.valueOf(timeSheet.getId().longValue()));
		}
		
		UserTimeSheetDetail userTimeSheetDetails = getUserTimeSheetDetail(timeSheet,weekStartDate,weekEndDate);
			userTimeSheetDetails.setTimeSheetId(userTimeSheet);

		userTimeSheet.setUserTimeSheetDetails(userTimeSheetDetails);
		
		if (null == timeSheet.getId()) {
			userTimeSheet.setCreationTimestamp(new Date());
			userTimeSheet.setCreatedId(resource.getUserName());

		}
		
		userTimeSheet.setTicketPriority(timeSheet.getTicketPriority());
		userTimeSheet.setTicketStatus(timeSheet.getTicketStatus());
		//Set approve and Deny code
		userTimeSheet.setApproveCode(GeneralFunction.randomString(20));
		userTimeSheet.setRejectCode(GeneralFunction.randomString(20));
		
		if (fromRestService) {
			userTimeSheet.setLastUpdatedId(resource.getUserName()+"_REST_API");
		} else { 
			userTimeSheet.setLastUpdatedId(resource.getUserName());
		}
	}

	private UserTimeSheetDetail getUserTimeSheetDetail(NewTimeSheet timeSheet, Date weekStartDate, Date weekEndDate) {
		return getUserTimeSheetDetailOfWeek(timeSheet, weekStartDate,weekEndDate);
	}

	/**
	 * This helper method evaluates the TimeSheet Object and return the UserTimeSheetDetail object which 
	 * contain details for all the days of week for which time sheet has been submitted by user  
	 * @param weekEndDate 
	 * 
	 */
	private UserTimeSheetDetail getUserTimeSheetDetailOfWeek(NewTimeSheet timeSheet, Date weekStartDate, Date weekEndDate) {
		UserTimeSheetDetail timeSheetDetailForNewOrExistingRecord = (UserTimeSheetDetail) (null!=timeSheet.getId()?getUserTimeSheetDetail(timeSheet, weekStartDate):new UserTimeSheetDetail());
		/**
		 * Integer 1 has been intentionally passed into this method so as to
		 * make use of switch case and make code readable. No Break statement is
		 * used in below case so that it can check for all the days of week for
		 * which user has submitted time sheet.
		 */
		switch (1) {
		case 1:
			timeSheetDetailForNewOrExistingRecord.setD1Hours(timeSheet.getD1Hours());
			timeSheetDetailForNewOrExistingRecord.setD1Comment(timeSheet.getD1Comment());
		case 2:
			timeSheetDetailForNewOrExistingRecord.setD2Hours(timeSheet.getD2Hours());
			timeSheetDetailForNewOrExistingRecord.setD2Comment(timeSheet.getD2Comment());
		case 3:
			timeSheetDetailForNewOrExistingRecord.setD3Hours(timeSheet.getD3Hours());
			timeSheetDetailForNewOrExistingRecord.setD3Comment(timeSheet.getD3Comment());
		case 4:
			timeSheetDetailForNewOrExistingRecord.setD4Hours(timeSheet.getD4Hours());
			timeSheetDetailForNewOrExistingRecord.setD4Comment(timeSheet.getD4Comment());
		case 5:
			timeSheetDetailForNewOrExistingRecord.setD5Hours(timeSheet.getD5Hours());
			timeSheetDetailForNewOrExistingRecord.setD5Comment(timeSheet.getD5Comment());
		case 6:
			timeSheetDetailForNewOrExistingRecord.setD6Hours(timeSheet.getD6Hours());
			timeSheetDetailForNewOrExistingRecord.setD6Comment(timeSheet.getD6Comment());
		case 7:
			timeSheetDetailForNewOrExistingRecord.setD7Hours(timeSheet.getD7Hours());
			timeSheetDetailForNewOrExistingRecord.setD7Comment(timeSheet.getD7Comment());
		}

		timeSheetDetailForNewOrExistingRecord.setWeekStartDate(weekStartDate);
		timeSheetDetailForNewOrExistingRecord.setWeekEndDate(weekEndDate);
		return timeSheetDetailForNewOrExistingRecord;
	}

	/**
	 * This Helper method will check if entry exists for following record in DB.If yes will retrhn
	 * 
	 */
	private UserTimeSheetDetail getUserTimeSheetDetail(NewTimeSheet timeSheet,Date weekStartDate) {
		UserTimeSheetDetail userTimeSheetDetail = userActivityDao.findUserTimeSheetDetailBasedOnIdAndDate(timeSheet.getId(), weekStartDate);
		return null!=userTimeSheetDetail?userTimeSheetDetail:new UserTimeSheetDetail();
	}

	public void updateAppStatus(NewTimeSheet timeSheet, UserTimeSheet activity, String uaPKandStatusArray[], Integer count, String submitStatus, Integer offDelMgrEmpId) {

		//For new row added in user activity
		if(null==timeSheet.getId() && (null!=submitStatus && submitStatus.equalsIgnoreCase("submit"))){
			activity.setStatus('P');
			activity.setApprovalPendingFrom(offDelMgrEmpId);
			return;
		}

		if (uaPKandStatusArray != null && uaPKandStatusArray.length > 0 && count < uaPKandStatusArray.length) {
			String pkandStatus[] = uaPKandStatusArray[count].split(":");
			if (pkandStatus.length == 2 && pkandStatus[0] != null && pkandStatus[1] != null) {
				if (Integer.valueOf(pkandStatus[0]).equals(timeSheet.getId())) {

					if(null!=submitStatus && submitStatus.equalsIgnoreCase("submit")){
						activity.setStatus('P');
						activity.setApprovalPendingFrom(offDelMgrEmpId);
						return;
					}else if(pkandStatus[1].equalsIgnoreCase("R")){
						activity.setStatus('N');
					}
					else{
						activity.setStatus(pkandStatus[1].charAt(0));
						return;
					}
				}
			} else {
				activity.setStatus('N');
				return;
			}

		}
		else{
			activity.setStatus('N');
			return;
		}

	}

	/**
	 * Added to display unique User Activity List on List tab of User Activity
	 * 
	 * @param userActivities
	 * @return List<UserActivity>
	 */
	public static List<UserActivity> getUniqueUserActivityBasedOnWeekEndDateAndProject(List<UserActivity> userActivities) {
		List<UserActivity> retList = new ArrayList<UserActivity>();
		if (userActivities == null || userActivities.isEmpty()) {
			return retList;
		}
		Map<Date, List<Integer>> endDateProjectMap = new HashMap<Date, List<Integer>>();
		
		for (UserActivity userActivity : userActivities) {
			if (endDateProjectMap.get(userActivity.getWeekStartDate()) == null 
					|| userActivity.getResourceAllocId() == null 
					|| (userActivity.getResourceAllocId() != null && !endDateProjectMap.get(userActivity.getWeekStartDate()).contains(userActivity.getResourceAllocId().getId()))) {
				
				retList.add(userActivity);
				
				if (endDateProjectMap.get(userActivity.getWeekStartDate()) == null) {
					List<Integer> newList = new ArrayList<Integer>();
					endDateProjectMap.put(userActivity.getWeekStartDate(), newList);
				}
				if (userActivity.getResourceAllocId() != null) {
					List<Integer> updatedList = endDateProjectMap.get(userActivity.getWeekStartDate());
					updatedList.add(userActivity.getResourceAllocId().getId());
					endDateProjectMap.put(userActivity.getWeekStartDate(), updatedList);
				}
			}
		}
		return retList;

	}

	public static Double getTotalHours(NewTimeSheet timeSheet){

		Double totalHrs = 0.0;
		totalHrs = totalHrs + (timeSheet.getD1Hours()!=null?timeSheet.getD1Hours():0.0);
		totalHrs = totalHrs + (timeSheet.getD2Hours()!=null?timeSheet.getD2Hours():0.0);
		totalHrs = totalHrs + (timeSheet.getD3Hours()!=null?timeSheet.getD3Hours():0.0);
		totalHrs = totalHrs + (timeSheet.getD4Hours()!=null?timeSheet.getD4Hours():0.0);
		totalHrs = totalHrs + (timeSheet.getD5Hours()!=null?timeSheet.getD5Hours():0.0);
		totalHrs = totalHrs + (timeSheet.getD6Hours()!=null?timeSheet.getD6Hours():0.0);
		totalHrs = totalHrs + (timeSheet.getD7Hours()!=null?timeSheet.getD7Hours():0.0);
		return totalHrs;
	} 


	/** This method is used to set the required variables for sendin Timesheet email.
	 * @param model
	 * @param weekStartDate
	 * @param weekEndDate
	 * @param userActivities
	 */
	public void setEmailContent(Map<String, Object> model,Resource resource, Date weekStartDate, Date weekEndDate, List<UserActivity> userActivities,String submitStatus,HttpServletRequest httpServletRequest,String mailFor) {
		//added for #3007  start
		String approve="";
		String reject="";
		UserActivityHelper.prepareTotalWeekHours(userActivities );
	  String ENV_VARIABLE=	Constants.getPropertyVariable(
				"/spring-configuration/rms.properties", "IafConfigSuffix");
		if("Local".equals(ENV_VARIABLE)){
			approve="http://localhost:8080/rms/resources/images/approve.png";
			reject="http://localhost:8080/rms/resources/images/reject_timesheet.png";
			
		}else if("Test".equals(ENV_VARIABLE)){
			approve="http://inidrrmstsrv01:8080/rms/resources/images/approve.png";
			reject="http://inidrrmstsrv01:8080/rms/resources/images/reject_timesheet.png";
		}else{
			approve="http://rms.yash.com/rms/resources/images/approve.png";
			reject="http://rms.yash.com/rms/resources/images/reject_timesheet.png";
		}
//added for #3007  end
		Date currentDate = new Date();		
		//HttpSession session = userUtil.getSession();
		//HttpServletRequest httpServletRequest = 
		model.put(Constants.ACTIVITY_LIST, userActivities);
		model.put(Constants.WEEK_START_DATE, weekStartDate);
		model.put(Constants.WEEK_END_DATE, weekEndDate);

		model.put(Constants.CURRENT_DATE, currentDate);
		//if mail is for manager then put approve and reject timesheet link
		if (mailFor.equalsIgnoreCase("approver")) {
			model.put(Constants.FILE_NAME, Constants.TIMESHEET_FTL);
			//put approve and reject link
			model.put(Constants.APPROVAL_URL, userUtil.getURL(httpServletRequest,userActivities.get(0),'A'));
			//added for #3007 start
			model.put(Constants.APPROVEIMAGE,approve);
			model.put(Constants.REJECTTIMESHEET,reject);
			//added for #3007  end
			model.put(Constants.DENY_URL, userUtil.getURL(httpServletRequest,userActivities.get(0),'R'));
		} else if (mailFor.equalsIgnoreCase("user")) {
			model.put(Constants.FILE_NAME, Constants.TIMESHEET_USER_FTL);
		}		

		model.put(Constants.SUBJECT, "RMS User: "+resource.getEmployeeName()+":"+resource.getYashEmpId()+" "+Constants.TIMESHEET_SUBJECT + " for " +userActivities.get(0).getResourceAllocId().getProjectId().getProjectName());
		//model.put(Constants.USER_NAME, userUtil.getCurrentUserName());
		model.put(Constants.USER_NAME, resource.getEmployeeName());
		model.put(Constants.USER_EMP_ID, resource.getYashEmpId());
		model.put(Constants.PROJECT_NAME, userActivities.get(0).getResourceAllocId().getProjectId().getProjectName());
		if(submitStatus.equalsIgnoreCase("save")){
			model.put(Constants.TIMESHEET_STATUS, "saved");
			model.put(Constants.EMAIL_ID, resource.getEmailId());

		}
		else{
			model.put(Constants.TIMESHEET_STATUS, "submitted");
			/*if (System.getProperty("IafConfigSuffix").equals("Test")) {
				model.put(Constants.EMAIL_ID, Constants.EMAIL_ID);
				model.put(Constants.CC_EMAIL_ID, Constants.EMAIL_ID);
			}else {*/
			if (mailFor.equalsIgnoreCase("approver")) {
				model.put(Constants.CC_EMAIL_ID, userActivities.get(0).getResourceAllocId().getProjectId().getOffshoreDelMgr().getEmailId());

				model.put(Constants.PROJECT_BUNAME, userActivities.get(0).getResourceAllocId().getProjectId().getOrgHierarchy().getId());

				//added for mail configuraion start
				if(userActivities.get(0).getEmployeeId().getCurrentReportingManager()!=null){
					if( userActivities.get(0).getEmployeeId().getCurrentReportingManager().getUserRole().equals("ROLE_MANAGER")||userActivities.get(0).getEmployeeId().getCurrentReportingManager().getUserRole().equals("ROLE_DEL_MANAGER"))
						model.put(Constants.MANAGER_IRM_EMAIL_ID, userActivities.get(0).getEmployeeId().getCurrentReportingManager().getEmailId());
					else
						model.put(Constants.MANAGER_IRM_EMAIL_ID, "");
					if( userActivities.get(0).getEmployeeId().getCurrentReportingManager().getUserRole().equals("ROLE_DEL_MANAGER")||userActivities.get(0).getEmployeeId().getCurrentReportingManager().getUserRole().equals("ROLE_BG_ADMIN")||userActivities.get(0).getEmployeeId().getCurrentReportingManager().getUserRole().equals("ROLE_ADMIN"))
						model.put(Constants.IRM_EMAIL_ID, userActivities.get(0).getEmployeeId().getCurrentReportingManager().getEmailId());
				}

				else
				{
					model.put(Constants.MANAGER_IRM_EMAIL_ID, "");
					model.put(Constants.IRM_EMAIL_ID, "");
				}

				if(userActivities.get(0).getEmployeeId().getCurrentReportingManagerTwo()!=null){
					if( userActivities.get(0).getEmployeeId().getCurrentReportingManagerTwo().getUserRole().equals("ROLE_MANAGER")||userActivities.get(0).getEmployeeId().getCurrentReportingManagerTwo().getUserRole().equals("ROLE_DEL_MANAGER"))
						model.put(Constants.MANAGER_SRM_EMAIL_ID, userActivities.get(0).getEmployeeId().getCurrentReportingManagerTwo().getEmailId());
					else
						model.put(Constants.MANAGER_SRM_EMAIL_ID, "");
					if( userActivities.get(0).getEmployeeId().getCurrentReportingManagerTwo().getUserRole().equals("ROLE_DEL_MANAGER")||userActivities.get(0).getEmployeeId().getCurrentReportingManagerTwo().getUserRole().equals("ROLE_BG_ADMIN")||userActivities.get(0).getEmployeeId().getCurrentReportingManagerTwo().getUserRole().equals("ROLE_ADMIN"))
						model.put(Constants.SRM_EMAIL_ID, userActivities.get(0).getEmployeeId().getCurrentReportingManagerTwo().getEmailId());
				}
				else
				{
					model.put(Constants.MANAGER_SRM_EMAIL_ID, "");
					model.put(Constants.SRM_EMAIL_ID, "");
				}
			} else if (mailFor.equalsIgnoreCase("user")) {


				model.put(Constants.EMAIL_ID, resource.getEmailId());
				if(userActivities.get(0).getEmployeeId().getCurrentReportingManager()!=null){
					if( userActivities.get(0).getEmployeeId().getCurrentReportingManager().getUserRole().equals("ROLE_USER"))
					{
						model.put(Constants.IRM_EMAIL_ID, userActivities.get(0).getEmployeeId().getCurrentReportingManager().getEmailId());
					}
				}
				else
				{
					model.put(Constants.IRM_EMAIL_ID, "");
				}
				if(userActivities.get(0).getEmployeeId().getCurrentReportingManagerTwo()!=null){
					if( userActivities.get(0).getEmployeeId().getCurrentReportingManagerTwo().getUserRole().equals("ROLE_USER"))
					{
						model.put(Constants.SRM_EMAIL_ID, userActivities.get(0).getEmployeeId().getCurrentReportingManagerTwo().getEmailId());
					}
				}
				else
				{
					model.put(Constants.SRM_EMAIL_ID, "");
				}
			}
			//added for mail configuraion End
			//}
		}
	}

	public void setEmailContentForTimeSheetApproval(Map<String, Object> model, Resource resource, Date weekStartDate, Date weekEndDate, Character isApprove, String ccEmailIdForManager, String remark,
			UserActivity userActivity, String loggedInResourceUserName) {

		//	Date currentDate = new Date();		

		model.put(Constants.WEEK_START_DATE, weekStartDate);
		model.put(Constants.WEEK_END_DATE, weekEndDate);
		//model.put(Constants.CURRENT_DATE, currentDate);
		model.put(Constants.FILE_NAME, Constants.TIMESHEET_APPROVAL_REJECT_FTL);
		// model.put(Constants.USER_NAME, userUtil.getCurrentUserName());
		// changed to send employee name in mail instead of user name.
		
		if(loggedInResourceUserName != null){
			model.put(Constants.USER_NAME, loggedInResourceUserName);
		}else {
			model.put(Constants.USER_NAME, userUtil.getUserContextDetails().getEmployeeName());
		}
		
		model.put(Constants.EMAIL_ID, resource.getEmailId());
		model.put(Constants.CC_EMAIL_ID,ccEmailIdForManager);
		model.put(Constants.USER_EMP_ID, resource.getYashEmpId());
		model.put(Constants.USER_FIRST_NAME, resource.getFirstName());
		model.put(Constants.PROJECT_BUNAME, userActivity.getResourceAllocId().getProjectId().getOrgHierarchy().getId());
		//model.put(Constants.CC_EMAIL_ID, userUtil.getLoggedInResource().getEmailId());
		if(isApprove.compareTo('A')==0){
			model.put(Constants.TIMESHEET_STATUS, "Approved");
			model.put(Constants.SUBJECT, Constants.TIMESHEET_APPROVAL_SUBJECT + " for " +userActivity.getResourceAllocId().getProjectId().getProjectName());
		}
		else 
			if(isApprove.compareTo('R')==0){
				model.put(Constants.TIMESHEET_STATUS, "Rejected");
				model.put(Constants.SUBJECT, Constants.TIMESHEET_REJECTION_SUBJECT + " for " +userActivity.getResourceAllocId().getProjectId().getProjectName());
				model.put(Constants.REMARKS, remark);
			}

		//added for mail configuraion start
		if(userActivity.getEmployeeId().getCurrentReportingManager()!=null){
			if( userActivity.getEmployeeId().getCurrentReportingManager().getUserRole().equals("ROLE_MANAGER")||userActivity.getEmployeeId().getCurrentReportingManager().getUserRole().equals("ROLE_DEL_MANAGER"))
				model.put(Constants.MANAGER_IRM_EMAIL_ID, userActivity.getEmployeeId().getCurrentReportingManager().getEmailId());
			else
				model.put(Constants.MANAGER_IRM_EMAIL_ID, "");
			model.put(Constants.IRM_EMAIL_ID, userActivity.getEmployeeId().getCurrentReportingManager().getEmailId());
		}
		else
		{

			model.put(Constants.MANAGER_IRM_EMAIL_ID, "");
			model.put(Constants.IRM_EMAIL_ID, "");

		}


		if(userActivity.getEmployeeId().getCurrentReportingManagerTwo()!=null){
			if( userActivity.getEmployeeId().getCurrentReportingManagerTwo().getUserRole().equals("ROLE_MANAGER")||userActivity.getEmployeeId().getCurrentReportingManagerTwo().getUserRole().equals("ROLE_DEL_MANAGER"))
				model.put(Constants.MANAGER_SRM_EMAIL_ID, userActivity.getEmployeeId().getCurrentReportingManagerTwo().getEmailId());

			else
				model.put(Constants.MANAGER_SRM_EMAIL_ID, "");
			model.put(Constants.SRM_EMAIL_ID, userActivity.getEmployeeId().getCurrentReportingManagerTwo().getEmailId());
			//added for mail configuraion End
		}
		else
		{
			model.put(Constants.MANAGER_SRM_EMAIL_ID, "");
			model.put(Constants.SRM_EMAIL_ID, "");
		}

	}

	public void setEmailContentForLoanTransfer(Map<String, Object> model,String resourceName,Map<String,List<String>> updatedInformation , String subject,Resource basicInformation, boolean hrFlag )
	{
		model.put(Constants.USER_NAME,resourceName);
		//model.put(Constants.USER_FIRST_NAME, firstname);	
		if(hrFlag){
			model.put(Constants.FILE_NAME, Constants.LOAN_TRANSFER_FTL_HR);	
			model.put("hrInfromation", updatedInformation);
		}else{
			model.put(Constants.FILE_NAME, Constants.LOAN_TRANSFER_FTL);
			model.put("information", updatedInformation);
			if(updatedInformation.get("RM1")!=null) {
				model.put("previousIRM", updatedInformation.get("RM1").get(0)!=null ? updatedInformation.get("RM1").get(0) : "Previous IRM");
			}
		}

		//model.put("information", updatedInformation);
		model.put("basicInfo", basicInformation);
		/*	if(previouuRM2EmailId!=null){

			toAddress[0]= emailId;
			toAddress[1]=	 previouuRM2EmailId;

	//	model.put(Constants.EMAIL_ID, toAddress);
		}
		else
			model.put(Constants.EMAIL_ID, "deepti.gupta@yash.com");*/
		//get subject
		// subject = updatedInformation.get(Constants.SUBJECT).get(0);
		model.put(Constants.SUBJECT, Constants.LOAN_TRANSFER_ACK_SUBJECT+subject);
	}

	public void setEmailContent(Map<String, Object> model,Resource resource, Date weekStartDate, Date weekEndDate, List<UserActivity> activityList) {
		Date currentDate = new Date();		
		model.put(Constants.ACTIVITY_LIST, activityList);
		model.put(Constants.WEEK_START_DATE, weekStartDate);
		model.put(Constants.WEEK_END_DATE, weekEndDate);
		model.put(Constants.CURRENT_DATE, currentDate);
		model.put(Constants.FILE_NAME, Constants.TIMESHEET_FTL);
		model.put(Constants.SUBJECT, Constants.TIMESHEET_SUBJECT);
		model.put(Constants.USER_NAME, userUtil.getCurrentUserName());
		model.put(Constants.USER_EMP_ID, resource.getYashEmpId());

	}

	public static void setEmailContent(Map<String, Object> model) {
		Date currentDate = new Date();		
		model.put(Constants.CURRENT_DATE, currentDate);
		model.put(Constants.FILE_NAME, Constants.UNFILLED_TIMESHEET_FTL);
		model.put(Constants.SUBJECT, Constants.UNFILLED_TIMESHEET_SUBJECT);
	}

	/**
	 * Method to find total number of non productive hours
	 * @param userActivityList
	 * @return
	 */
	public static double getNonProductiveHours(List<UserActivity> userActivityList) {
		double totalNonProductiveHours = 0;
		if (userActivityList != null && !userActivityList.isEmpty()) {
			for (UserActivity uactObj : userActivityList) {
				if (uactObj != null) {
					if (uactObj.getActivityId() != null) {
						if (!uactObj.getActivityId().isProductive()) {
							totalNonProductiveHours += uactObj.getD1Hours() != null ? uactObj.getD1Hours() : 0.0;
							totalNonProductiveHours += uactObj.getD2Hours() != null ? uactObj.getD2Hours() : 0.0;
							totalNonProductiveHours += uactObj.getD3Hours() != null ? uactObj.getD3Hours() : 0.0;
							totalNonProductiveHours += uactObj.getD4Hours() != null ? uactObj.getD4Hours() : 0.0;
							totalNonProductiveHours += uactObj.getD5Hours() != null ? uactObj.getD5Hours() : 0.0;
							totalNonProductiveHours += uactObj.getD6Hours() != null ? uactObj.getD6Hours() : 0.0;
							totalNonProductiveHours += uactObj.getD7Hours() != null ? uactObj.getD7Hours() : 0.0;
						}
					} if (uactObj.getCustomActivityId() != null) {
						if (!uactObj.getCustomActivityId().isProductive()) {

							totalNonProductiveHours += uactObj.getD1Hours() != null ? uactObj.getD1Hours() : 0.0;
							totalNonProductiveHours += uactObj.getD2Hours() != null ? uactObj.getD2Hours() : 0.0;
							totalNonProductiveHours += uactObj.getD3Hours() != null ? uactObj.getD3Hours() : 0.0;
							totalNonProductiveHours += uactObj.getD4Hours() != null ? uactObj.getD4Hours() : 0.0;
							totalNonProductiveHours += uactObj.getD5Hours() != null ? uactObj.getD5Hours() : 0.0;
							totalNonProductiveHours += uactObj.getD6Hours() != null ? uactObj.getD6Hours() : 0.0;
							totalNonProductiveHours += uactObj.getD7Hours() != null ? uactObj.getD7Hours() : 0.0;
						}
					}
				}
			}
		}
		return totalNonProductiveHours;
	}

	/**
	 * Method to find total number of productive hours
	 * @param userActivityList
	 * @return
	 */
	public static double getProductiveHours(List<UserActivity> userActivityList) {
		double totalProductiveHours = 0;
		if (userActivityList != null && !userActivityList.isEmpty()) {
			for (UserActivity uactObj : userActivityList) {
				if (uactObj != null) {
					if (uactObj.getActivityId() != null) {
						if (uactObj.getActivityId().isProductive()) {
							totalProductiveHours += uactObj.getD1Hours() != null ? uactObj.getD1Hours() : 0.0;
							totalProductiveHours += uactObj.getD2Hours() != null ? uactObj.getD2Hours() : 0.0;
							totalProductiveHours += uactObj.getD3Hours() != null ? uactObj.getD3Hours() : 0.0;
							totalProductiveHours += uactObj.getD4Hours() != null ? uactObj.getD4Hours() : 0.0;
							totalProductiveHours += uactObj.getD5Hours() != null ? uactObj.getD5Hours() : 0.0;
							totalProductiveHours += uactObj.getD6Hours() != null ? uactObj.getD6Hours() : 0.0;
							totalProductiveHours += uactObj.getD7Hours() != null ? uactObj.getD7Hours() : 0.0;
						}
					} if (uactObj.getCustomActivityId() != null) {
						if (uactObj.getCustomActivityId().isProductive()) {
							totalProductiveHours += uactObj.getD1Hours() != null ? uactObj.getD1Hours() : 0.0;
							totalProductiveHours += uactObj.getD2Hours() != null ? uactObj.getD2Hours() : 0.0;
							totalProductiveHours += uactObj.getD3Hours() != null ? uactObj.getD3Hours() : 0.0;
							totalProductiveHours += uactObj.getD4Hours() != null ? uactObj.getD4Hours() : 0.0;
							totalProductiveHours += uactObj.getD5Hours() != null ? uactObj.getD5Hours() : 0.0;
							totalProductiveHours += uactObj.getD6Hours() != null ? uactObj.getD6Hours() : 0.0;
							totalProductiveHours += uactObj.getD7Hours() != null ? uactObj.getD7Hours() : 0.0;
						}
					}
				}
			}
		}
		return totalProductiveHours;
	}

	/**
	 * Method to find total number of  vacation hours
	 * @param userActivityList
	 * @return
	 */
	public static double getVacationHours(List<UserActivity> userActivityList) {
		double totalVacationHours = 0;
		if(userActivityList != null && !userActivityList.isEmpty()) {
			for(UserActivity uactObj : userActivityList) {
				if(uactObj != null
						&& uactObj.getActivityId() != null
						&& uactObj.getActivityId().getActivityName() != null
						&& uactObj.getActivityId().getActivityName().contains(Constants.VACATION_ACTIVITY_TEXT)) {

					totalVacationHours += uactObj.getD1Hours()!=null ? uactObj.getD1Hours(): 0.0;
					totalVacationHours += uactObj.getD2Hours()!=null ? uactObj.getD2Hours(): 0.0;
					totalVacationHours += uactObj.getD3Hours()!=null ? uactObj.getD3Hours(): 0.0;
					totalVacationHours += uactObj.getD4Hours()!=null ? uactObj.getD4Hours(): 0.0;
					totalVacationHours += uactObj.getD5Hours()!=null ? uactObj.getD5Hours(): 0.0;
					totalVacationHours += uactObj.getD6Hours()!=null ? uactObj.getD6Hours(): 0.0;
					totalVacationHours += uactObj.getD7Hours()!=null ? uactObj.getD7Hours(): 0.0;
				}

			}
		}
		return totalVacationHours;
	}
	
	public void setEmailContentForClosingAllocation(Map<String, Object> model,Resource resource,Date weekEndDate,String projectName,String resourceName )
	{
		Date currentDate = new Date();		
		model.put(Constants.WEEK_END_DATE, weekEndDate);
		model.put(Constants.FILE_NAME, Constants.ACK_CLOSING_ALLOCATION_FTL);
		model.put(Constants.SUBJECT, Constants.ACK_CLOSING_ALLOCATION_SUBJECT+"Date: "+weekEndDate+"-"+resource.getYashEmpId()+"-"+resourceName);
		model.put(Constants.USER_NAME, userUtil.getUserContextDetails().getEmployeeName());
		model.put(Constants.RESOURCE_NAME, resourceName);
		model.put(Constants.USER_EMP_ID, resource.getYashEmpId());
		model.put(Constants.PROJECT_NAME,projectName);
		
	}

  public static List<UserActivity> getUserActivityBasedOnActiveProjectModule(
      List<UserActivity> userActivities) {
    List<UserActivity> retList = new ArrayList<UserActivity>();
    /*if (userActivities == null || userActivities.isEmpty()) {
        return retList;
    }*/
    
    for (Iterator<UserActivity> ua = userActivities.iterator(); ua.hasNext();) { 
    //for (UserActivity ua : userActivities) {
      UserActivity userActivity = ua.next();
      ResourceAllocation resourceAllocation = userActivity.getResourceAllocId();
     Project project=resourceAllocation.getProjectId();
    Set <ProjectModule> projectModule = new HashSet <ProjectModule>();
    Set <ProjectModule> activeProjectModules= new HashSet<ProjectModule>();
    activeProjectModules = projectModule = project.getModule();
    activeProjectModules=filterActiveModules(projectModule);
    project.setModule(activeProjectModules);
    /*for (Iterator<ProjectModule> itr = projectModule.iterator(); itr.hasNext();) { 
     ProjectModule module = itr.next();
      if(!module.isActive())
      {
        
      }
  }*/
    
    }
    return userActivities;
  }

  private static Set<ProjectModule> filterActiveModules(Set<ProjectModule> projectModule) {
    Set <ProjectModule> activeProjectModules= new HashSet<ProjectModule>();
    for (Iterator<ProjectModule> itr = projectModule.iterator(); itr.hasNext();) { 
      ProjectModule module = itr.next();
       if(module.isActive())
       {
         activeProjectModules.add(module);
       }
   }
    return activeProjectModules;
  }
  
  
  /**
   * This method maps UserActivity list to TimesheetSubmissionDTOList
   * 
   * @param userActivityList
   * @param employeeId
   * @return List<TimesheetSubmissionDTO>
   */
  public static List<TimesheetSubmissionDTO> convertUserActivityDomainToTimesheetSubmissionDTO(List<UserActivity> userActivityList, Integer employeeId){
	
	  List<TimesheetSubmissionDTO> timesheetSubmissionDTOs = new ArrayList<TimesheetSubmissionDTO>();
	  prepareTotalWeekHours(userActivityList );
	  for (UserActivity userActivity : userActivityList) {
		  
		  TimesheetSubmissionDTO timesheetSubmissionDTO = new TimesheetSubmissionDTO();
		  	timesheetSubmissionDTO.setEmployeeId(employeeId);
		  	timesheetSubmissionDTO.setProjectId(userActivity.getResourceAllocId().getProjectId().getId());
		  	timesheetSubmissionDTO.setProjectName(userActivity.getResourceAllocId().getProjectId().getProjectName());
		  	timesheetSubmissionDTO.setResourceAllocationId(userActivity.getResourceAllocId().getId());
		  	timesheetSubmissionDTO.setStatus(userActivity.getStatus().toString());
		  	timesheetSubmissionDTO.setTotalHours(userActivity.getRepHrsForProForWeekEndDate());
		  	timesheetSubmissionDTO.setWeekEndDate(userActivity.getWeekEndDate());
		  	timesheetSubmissionDTO.setWeekStartDate(userActivity.getWeekStartDate());
		  timesheetSubmissionDTOs.add(timesheetSubmissionDTO);
	  }
	  
	  return timesheetSubmissionDTOs;
	  
  }
  
  public static void prepareTotalWeekHours(List<UserActivity> userActivityList) {
	  
	  Map<String, Double> totalWeekHoursMap = new HashMap<String, Double>();
      
	  for (UserActivity userActivity : userActivityList) {
		  
		  System.out.println("User Activity :" +userActivity.getId() + " processing");
		  //logger.info("User Activity :" +userActivity.getId() + " processing.");
		  String projectName = userActivity.getResourceAllocId().getProjectId().getProjectName();
		  
		  if (userActivity.getWeekEndDate() != null) {
			  
			  String weekEndDate = userActivity.getWeekEndDate().toString();
			  Double sumOfWeekHours = (userActivity.getD1Hours() != null ? userActivity.getD1Hours() : 0) 
						+ (userActivity.getD2Hours() != null ? userActivity.getD2Hours() : 0)
						+ (userActivity.getD3Hours() != null ? userActivity.getD3Hours() : 0)
						+ (userActivity.getD4Hours() != null ? userActivity.getD4Hours() : 0)
						+ (userActivity.getD5Hours() != null ? userActivity.getD5Hours() : 0)
						+ (userActivity.getD6Hours() != null ?  + userActivity.getD6Hours() : 0)
						+ (userActivity.getD7Hours() != null ?  + userActivity.getD7Hours() : 0);
				
				if (totalWeekHoursMap.containsKey(projectName.concat(weekEndDate))) {
					
					Double hours = totalWeekHoursMap.get(projectName.concat(weekEndDate));
						hours = hours + sumOfWeekHours;
						
					totalWeekHoursMap.put(projectName.concat(weekEndDate), hours);
				} else {
					
					totalWeekHoursMap.put(projectName.concat(weekEndDate), sumOfWeekHours);
				}
			}
	  }
	  
	  setRepHrsForProForWeekToUserActivityList(userActivityList,totalWeekHoursMap);
  }

  private static void setRepHrsForProForWeekToUserActivityList(List<UserActivity> userActivityList, Map<String, Double> totalWeekHoursMap) {
	  
	  for (UserActivity userActivity : userActivityList) {
      
		  if(userActivity != null && userActivity.getWeekEndDate() != null) {
		  //if(userActivity != null) {
			  String endDate = userActivity.getWeekEndDate().toString();
			  if(totalWeekHoursMap.containsKey((userActivity.getResourceAllocId().getProjectId().getProjectName()).concat(endDate))){
				  userActivity.setRepHrsForProForWeekEndDate(totalWeekHoursMap.get((userActivity.getResourceAllocId().getProjectId().getProjectName()).concat(endDate)));
			  }
		  }
	  }
  }

}
