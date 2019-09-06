package org.yash.rms.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.yash.rms.dto.UserContextDetails;
import org.yash.rms.domain.UserNotification;
import org.yash.rms.json.mapper.JsonObjectMapper;
import org.yash.rms.service.CustomerService;
import org.yash.rms.service.UserNotificationService;
import org.yash.rms.util.UserUtil;



/**
 * @author sumit.paul
 *
 */

@Controller
@RequestMapping("/usernotifications")
public class UserNotificationController {
	
	
	@Autowired
	@Qualifier("UsernoticationService")
	UserNotificationService userNotificationService;
	
	
	@Autowired
	JsonObjectMapper<UserNotification> jsonMapper;
	
	
	@Autowired
	@Qualifier("CustomerService")
	CustomerService customerService;
	
	@Autowired
	UserUtil userUtil;

	private static final Logger logger = LoggerFactory.getLogger(UserNotificationController.class);
	
	
	@RequestMapping(params = "find=ByEmployeeIdAndIsReadNotUpdated", headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> findUserNotificationsByEmployeeIdAndIsReadNotUpdated() throws Exception {
		
		logger.info("------UserNotificationController findUserNotificationsByEmployeeIdAndIsReadNotUpdated method start------");
		
		boolean isRead = false;
		List<UserNotification> notifications = null;

		
		// Get all unread notifications for current user
	    UserContextDetails userContextDetails = userUtil.getUserContextDetails();
				
		try{
		
			notifications = userNotificationService.findUserNotification(userContextDetails.getEmployeeId(),isRead);
		
			List<Integer> ids =  new ArrayList<Integer>();
			for (UserNotification userNotification : notifications ) {
			    ids.add(userNotification.getId());
			}
		
			// Mark retrieved notification as read in DB
			// To improve  performance , using native query to update all record with single query, instead of persisting each object.
			if (CollectionUtils.isEmpty(notifications) != true ) { // if we have any notifications to be updated
		        String idsString = StringUtils.collectionToCommaDelimitedString(ids);
		        userNotificationService.updateUserNotification(idsString);
		    }
		}catch(RuntimeException runtimeException)
		{				
			logger.error("RuntimeException occured in showJson method of Customer controller:"+runtimeException);				
			throw runtimeException;
		}catch(Exception exception){
			logger.error("Exception occured in showJson method of Customer controller:"+exception);				
			throw exception;
		}  	
	    
		// Respond all the notifications as json data
		HttpHeaders headers = new HttpHeaders();
	    headers.add("Content-Type", "application/json; charset=utf-8");
		return new ResponseEntity<String>(jsonMapper.toJsonArray(notifications), headers, HttpStatus.OK);
		
	 }

}
