package org.yash.rms.form;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.FactoryUtils;
import org.apache.commons.collections.list.LazyList;
import org.yash.rms.domain.Resource;
import org.yash.rms.domain.SkillsMapping;
import org.yash.rms.domain.UserProfile;
import org.yash.rms.dto.ResourceDTO;

public class UserProfileForm {
private List<SkillsMapping> entries = LazyList.decorate(new ArrayList<SkillsMapping>(), FactoryUtils.instantiateFactory(SkillsMapping.class)); 
	
	public List<SkillsMapping> getEntries() {
		return entries;
	}
	public void setEntries(List<SkillsMapping> entries) {
		this.entries = entries;
	}
	ResourceDTO resource;
	UserProfile userProfile;
	
	
	public ResourceDTO getResource() {
		return resource;
	}
	public void setResource(ResourceDTO resource) {
		this.resource = resource;
	}
	public UserProfile getUserProfile() {
		return userProfile;
	}
	public void setUserProfile(UserProfile userProfile) {
		this.userProfile = userProfile;
	}
}
