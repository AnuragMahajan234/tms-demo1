package org.yash.rms.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.yash.rms.domain.Activity;
import org.yash.rms.domain.UserActivity;
import org.yash.rms.form.NewTimeSheet;
import org.yash.rms.form.TimeSheet;

@Component
public class TimeHourEntryHelper {

	private static final Logger logger = LoggerFactory.getLogger(TimeHourEntryHelper.class);
	
	public NewTimeSheet convertTimeSheetToNewTimeSheet(TimeSheet timeSheet) {
		logger.info("------TimeHourEntryHelper convertTimeSheetToNewTimeSheet method start------");
		NewTimeSheet newTimeSheet = null;
		try{
			newTimeSheet = new NewTimeSheet();
			if(timeSheet.getActivityId()!=null){
				newTimeSheet.setActivityId(timeSheet.getActivityId().toString());
			}
			if(timeSheet.getCustomActivityId()!=null){
				newTimeSheet.setCustomActivityId(timeSheet.getCustomActivityId());
			}
			if(timeSheet.getApproveStatus()!=null){
				newTimeSheet.setApproveStatus(timeSheet.getApproveStatus());
			}
			if(timeSheet.getD1Comment()!=null){
				newTimeSheet.setD1Comment(timeSheet.getD1Comment());
			}
			if(timeSheet.getD2Comment()!=null){
				newTimeSheet.setD2Comment(timeSheet.getD2Comment());
			}
			if(timeSheet.getD3Comment()!=null){
				newTimeSheet.setD3Comment(timeSheet.getD3Comment());
			}
			if(timeSheet.getD4Comment()!=null){
				newTimeSheet.setD4Comment(timeSheet.getD4Comment());
			}
			if(timeSheet.getD5Comment()!=null){
				newTimeSheet.setD5Comment(timeSheet.getD5Comment());
			}
			if(timeSheet.getD6Comment()!=null){
				newTimeSheet.setD6Comment(timeSheet.getD6Comment());
			}
			if(timeSheet.getD7Comment()!=null){
				newTimeSheet.setD7Comment(timeSheet.getD7Comment());
			}
			if(timeSheet.getD1Hours()!=null){
				newTimeSheet.setD1Hours(timeSheet.getD1Hours());
			}
			if(timeSheet.getD2Hours()!=null){
				newTimeSheet.setD2Hours(timeSheet.getD2Hours());
			}
			if(timeSheet.getD3Hours()!=null){
				newTimeSheet.setD3Hours(timeSheet.getD3Hours());
			}
			if(timeSheet.getD4Hours()!=null){
				newTimeSheet.setD4Hours(timeSheet.getD4Hours());
			}
			if(timeSheet.getD5Hours()!=null){
				newTimeSheet.setD5Hours(timeSheet.getD5Hours());
			}
			if(timeSheet.getD6Hours()!=null){
				newTimeSheet.setD6Hours(timeSheet.getD6Hours());
			}
			if(timeSheet.getD7Hours()!=null){
				newTimeSheet.setD7Hours(timeSheet.getD7Hours());
			}
			if(timeSheet.getModule()!=null){
				newTimeSheet.setModule(timeSheet.getModule());
			}
			if(timeSheet.getTicketNo()!=null){
				newTimeSheet.setTicketNo(timeSheet.getTicketNo());
			}
			if(timeSheet.getTicketPriority()!=null){
				newTimeSheet.setTicketPriority(timeSheet.getTicketPriority());
			}
			if(timeSheet.getTicketStatus()!=null){
				newTimeSheet.setTicketStatus(timeSheet.getTicketStatus());
			}
			if(timeSheet.getResourceAllocId()!=null){
				newTimeSheet.setResourceAllocId(timeSheet.getResourceAllocId());
			}
			if(timeSheet.getId()!=null){
				newTimeSheet.setId(timeSheet.getId());
			}
			newTimeSheet.setSubmitStatus(timeSheet.isSubmitStatus());
		}finally{
			logger.info("------TimeHourEntryHelper convertTimeSheetToNewTimeSheet method end------");
		}
		return newTimeSheet;
	}

	public UserActivity prepareUserActivity(UserActivity activity) {
		logger.info("------TimeHourEntryHelper prepareUserActivity method start------");
		UserActivity userActivity= null;
		try{
			userActivity = new UserActivity();
			if(activity.getCustomActivityId()!=null){
				Activity activity2 = new Activity();
				activity2.setId(activity.getCustomActivityId().getId());
				activity2.setActivityName(activity.getCustomActivityId().getActivityName());
				userActivity.setActivityId(activity2);
			}else{
				userActivity.setActivityId(activity.getActivityId());
			}
			if(activity.getD1Comment()!=null){
				userActivity.setD1Comment(activity.getD1Comment());
			}
			if(activity.getD2Comment()!=null){
				userActivity.setD2Comment(activity.getD2Comment());
			}
			if(activity.getD3Comment()!=null){
				userActivity.setD3Comment(activity.getD3Comment());
			}
			if(activity.getD4Comment()!=null){
				userActivity.setD4Comment(activity.getD4Comment());
			}
			if(activity.getD5Comment()!=null){
				userActivity.setD5Comment(activity.getD5Comment());
			}
			if(activity.getD6Comment()!=null){
				userActivity.setD6Comment(activity.getD6Comment());
			}
			if(activity.getD7Comment()!=null){
				userActivity.setD7Comment(activity.getD7Comment());
			}
			if(activity.getD1Hours()!=null){
				userActivity.setD1Hours(activity.getD1Hours());
			}
			if(activity.getD2Hours()!=null){
				userActivity.setD2Hours(activity.getD2Hours());
			}
			if(activity.getD3Hours()!=null){
				userActivity.setD3Hours(activity.getD3Hours());
			}
			if(activity.getD4Hours()!=null){
				userActivity.setD4Hours(activity.getD4Hours());
			}
			if(activity.getD5Hours()!=null){
				userActivity.setD5Hours(activity.getD5Hours());
			}
			if(activity.getD6Hours()!=null){
				userActivity.setD6Hours(activity.getD6Hours());
			}
			if(activity.getD7Hours()!=null){
				userActivity.setD7Hours(activity.getD7Hours());
			}
			if(activity.getModule()!=null){
				userActivity.setModule(activity.getModule());
			}
			if(activity.getSubModule()!=null){
				userActivity.setSubModule(activity.getSubModule());
			}
			if(activity.getTicketNo()!=null){
				userActivity.setTicketNo(activity.getTicketNo());
			}
			if(activity.getTicketPriority()!=null){
				userActivity.setTicketPriority(activity.getTicketPriority());
			}
			if(activity.getTicketStatus()!=null){
				userActivity.setTicketStatus(activity.getTicketStatus());
			}
			if(activity.getResourceAllocId()!=null){
				userActivity.setResourceAllocId(activity.getResourceAllocId());
			}
			if(activity.getId()!=null){
				userActivity.setId(activity.getId());
			}
			if(activity.getApproveCode()!=null){
				userActivity.setApproveCode(activity.getApproveCode());
			}
			if(activity.getRejectCode()!=null){
				userActivity.setRejectCode(activity.getRejectCode());
			}
			if(activity.getApproveStatus()!=null){
				userActivity.setStatus(activity.getApproveStatus());
			}
			if(activity.getBilledHrs()!=null){
				userActivity.setBilledHrs(activity.getBilledHrs());
			}
			if(activity.getEmployeeId()!=null){
				userActivity.setEmployeeId(activity.getEmployeeId());
			}
			if(activity.getPlannedHrs()!=null){
				userActivity.setPlannedHrs(activity.getPlannedHrs());
			}
			if(activity.getRejectionRemarks()!=null){
				userActivity.setRejectionRemarks(activity.getRejectionRemarks());
			}
			if(activity.getRemarks()!=null){
				userActivity.setRemarks(activity.getRemarks());
			}
			if(activity.getRepHrsForProForWeekEndDate()!=null){
				userActivity.setRepHrsForProForWeekEndDate(activity.getRepHrsForProForWeekEndDate());
			}
			if(activity.getWeekEndDate()!=null){
				userActivity.setWeekEndDate(activity.getWeekEndDate());
			}
			if(activity.getWeekStartDate()!=null){
				userActivity.setWeekStartDate(activity.getWeekStartDate());
			}
			userActivity.setViewFlag(activity.isViewFlag());
			if(activity.getTimeHrsId()!=null){
				userActivity.setTimeHrsId(activity.getTimeHrsId());
			}
		}finally{
			logger.info("------TimeHourEntryHelper prepareUserActivity method end------");
		}
		return userActivity;
	}

	public TimeSheet covertUserActivityToTimeSheet(UserActivity userActivity) {
		logger.info("------TimeHourEntryHelper covertUserActivityToTimeSheet method start------");
		TimeSheet timeSheet = null;
		try{
			timeSheet = new TimeSheet();
			if(userActivity.getActivityId()!=null){
				timeSheet.setActivityId(userActivity.getActivityId().getId());
			}
			if(userActivity.getCustomActivityId()!=null){
				timeSheet.setCustomActivityId(userActivity.getCustomActivityId().getId());
			}
			if(userActivity.getApproveStatus()!=null){
				timeSheet.setApproveStatus(userActivity.getApproveStatus());
			}
			if(userActivity.getD1Comment()!=null){
				timeSheet.setD1Comment(userActivity.getD1Comment());
			}
			if(userActivity.getD2Comment()!=null){
				timeSheet.setD2Comment(userActivity.getD2Comment());
			}
			if(userActivity.getD3Comment()!=null){
				timeSheet.setD3Comment(userActivity.getD3Comment());
			}
			if(userActivity.getD4Comment()!=null){
				timeSheet.setD4Comment(userActivity.getD4Comment());
			}
			if(userActivity.getD5Comment()!=null){
				timeSheet.setD5Comment(userActivity.getD5Comment());
			}
			if(userActivity.getD6Comment()!=null){
				timeSheet.setD6Comment(userActivity.getD6Comment());
			}
			if(userActivity.getD7Comment()!=null){
				timeSheet.setD7Comment(userActivity.getD7Comment());
			}
			if(userActivity.getD1Hours()!=null){
				timeSheet.setD1Hours(userActivity.getD1Hours());
			}
			if(userActivity.getD2Hours()!=null){
				timeSheet.setD2Hours(userActivity.getD2Hours());
			}
			if(userActivity.getD3Hours()!=null){
				timeSheet.setD3Hours(userActivity.getD3Hours());
			}
			if(userActivity.getD4Hours()!=null){
				timeSheet.setD4Hours(userActivity.getD4Hours());
			}
			if(userActivity.getD5Hours()!=null){
				timeSheet.setD5Hours(userActivity.getD5Hours());
			}
			if(userActivity.getD6Hours()!=null){
				timeSheet.setD6Hours(userActivity.getD6Hours());
			}
			if(userActivity.getD7Hours()!=null){
				timeSheet.setD7Hours(userActivity.getD7Hours());
			}
			if(userActivity.getModule()!=null){
				timeSheet.setModule(userActivity.getModule());
			}
			if(userActivity.getTicketNo()!=null){
				timeSheet.setTicketNo(userActivity.getTicketNo());
			}
			if(userActivity.getResourceAllocId()!=null){
				timeSheet.setResourceAllocId(userActivity.getResourceAllocId().getId());
			}
			if(userActivity.getId()!=null){
				timeSheet.setId(userActivity.getId());
			}
		}finally{
			logger.info("------TimeHourEntryHelper covertUserActivityToTimeSheet method end------");
		}
		return timeSheet;
	}

}
