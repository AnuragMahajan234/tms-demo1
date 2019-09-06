package org.yash.rms.service;

import java.util.List;
import java.util.Map;

import org.yash.rms.domain.Resource;
import org.yash.rms.domain.Skills;
import org.yash.rms.domain.UserProfile;
import org.yash.rms.dto.EditProfileDTO;

public interface EditProfileService {
	
	public List<UserProfile>findUserProfilesByLogicalDeleteEqualsAndYashEmpIdEquals(String logicalDelete, String yashEmpId);
	
	public List<UserProfile>findUserProfilesByYashEmpIdEquals(String yashEmpId);
	
	public List<Object[]> getSkillsMapping(String yashEmpId, String Query);
	
	public boolean saveOrUpdateUserProfile(EditProfileDTO editProfileDTO, boolean fromRestService);
    public Map<String, Object> downloadResume(Integer id);
    public boolean addUserSkill(String skillType, Resource employeeId, Skills skill, Integer exprienceVal,Integer rating);
    public boolean updateUserSkill(String skillType, Integer pId, Integer priExprVal, Integer priRatingVal);
	
}
