package org.yash.rms.dao;

import java.util.List;

import org.yash.rms.domain.SkillResourcePrimary;
import org.yash.rms.domain.SkillResourceSecondary;
import org.yash.rms.domain.SkillsMapping;
import org.yash.rms.domain.UserProfile;

public interface EditProfileDao  extends RmsCRUDDAO<UserProfile>{
	List<UserProfile> findUserProfilesByLogicalDeleteEqualsAndYashEmpIdEquals(String logicalDelete, String yashEmpId);
	List<UserProfile> findUserProfilesByYashEmpIdEquals(String yashEmpId);
	List<Object[]> getSkillsMapping(String yashEmpId,String query);
	Object getResumeById(Integer requestId);
	boolean UpdateUserPrimarySkill(SkillResourcePrimary skillProfilePrimary);
	boolean UpdateUserSecondarySkill(SkillResourceSecondary skillResourceSecondary);
}
