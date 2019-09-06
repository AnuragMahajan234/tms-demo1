package org.yash.rms.helper;

import java.util.Date;

import org.yash.rms.domain.Resource;
import org.yash.rms.domain.UserNotification;

public class UserNotificationHelper {

	public static void populateBeanFromForm(UserNotification userNotification,String resourceName,Resource resourceManager,Date weekEndDate,boolean isDelete){
		
		userNotification.setEmployeeId(resourceManager);
		if(isDelete){
			userNotification.setMsg("You had deleted time-sheet row for week "+weekEndDate);
		}
		else{
			userNotification.setMsg("User "+resourceName+" had saved time-sheet for week "+weekEndDate);
		}
		userNotification.setMsgType("SUCCESS");
		
	}
	
public static void populateBeanForSubmitting(UserNotification userNotification,String resourceName,Resource resourceManager,Date weekEndDate,boolean submitted){
		
		userNotification.setEmployeeId(resourceManager);
		if(submitted){
			userNotification.setMsg("User "+resourceName+" had submitted time-sheet for week "+weekEndDate);
		}else{
			userNotification.setMsg("User "+resourceName+" had saved time-sheet for week "+weekEndDate);
		}
		userNotification.setMsgType("SUCCESS");
		
	}
	
public static void populateBeanForEditProfile(UserNotification userNotification,Resource resource){
		
		userNotification.setEmployeeId(resource);
		
			userNotification.setMsg("Profile saved successfully.");
		
		userNotification.setMsgType("SUCCESS");
		
	}

public static void populateBeanForUserProfile(UserNotification userNotification,Resource resource,String message){
	
	userNotification.setEmployeeId(resource);
	userNotification.setMsg(message);
	userNotification.setMsgType("SUCCESS");
	
}
}
