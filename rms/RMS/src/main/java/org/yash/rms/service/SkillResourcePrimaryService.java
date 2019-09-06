package org.yash.rms.service;

import java.util.List;

import org.yash.rms.domain.SkillResourcePrimary;


public interface SkillResourcePrimaryService {

	public List<SkillResourcePrimary> findSkillResourcesByResourceId(int resourcId);
	
     SkillResourcePrimary findById(int id);
     
     public boolean delete(int id);
}
