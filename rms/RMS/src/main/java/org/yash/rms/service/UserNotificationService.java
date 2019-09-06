package org.yash.rms.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;
import org.yash.rms.domain.UserNotification;


@Transactional
public interface UserNotificationService extends RmsCRUDService<UserNotification>{

	public List<UserNotification> findUserNotification(int employeeId,boolean isRead);
	
	public void updateUserNotification(String notificationId);

}
