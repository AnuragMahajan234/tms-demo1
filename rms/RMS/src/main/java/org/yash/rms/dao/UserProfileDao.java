package org.yash.rms.dao;

import org.yash.rms.domain.UserProfile;

public interface UserProfileDao extends RmsCRUDDAO<UserProfile>{

	/*@Transactional
	UserProfile merge(UserProfile userProfile);
	*/
	public UserProfile findByYashId(String yashId);
	
}
