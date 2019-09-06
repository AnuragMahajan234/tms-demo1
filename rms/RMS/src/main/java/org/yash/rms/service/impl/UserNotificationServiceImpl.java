package org.yash.rms.service.impl;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.yash.rms.dao.UserNotificationDAO;
import org.yash.rms.domain.UserNotification;
import org.yash.rms.service.UserNotificationService;
import org.yash.rms.util.DozerMapperUtility;
import org.yash.rms.util.HibernateUtil;


@Service("UsernoticationService")
public class UserNotificationServiceImpl implements UserNotificationService {

	
	@Autowired @Qualifier("UserNotificationDAO")
	UserNotificationDAO userNotificationDAO;

	@Autowired
	private DozerMapperUtility mapper;
	
	
	public boolean delete(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean saveOrupdate(UserNotification userNotification) {
		return userNotificationDAO.saveOrupdate(userNotification);
	}

	public List<UserNotification> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<UserNotification> findByEntries(int firstResult, int sizeNo) {
		
		return null;
	}

	public long countTotal() {
		// TODO Auto-generated method stub
		return 0;
	}

	public List<UserNotification> findUserNotification(int employeeId,boolean isRead) {
		//Not Using Dozer Bean mapper as of now
//		return mapper.convertUserNotificationDomainToDTOList(userNotificationDAO.findUserNotification(employeeId, isRead));
		return userNotificationDAO.findUserNotification(employeeId, isRead);
	}
	
	public void updateUserNotification(String notificationId){
		userNotificationDAO.updateUserNotification(notificationId);
	}

}
