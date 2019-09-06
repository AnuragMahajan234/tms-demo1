package org.yash.rms.service;

import java.util.List;

import org.yash.rms.domain.SkillResourceSecondary;

public interface SkillResourceSecondaryService {

	public List<SkillResourceSecondary> findSkillResourcesByResourceId(int resourcId) ;
	
	SkillResourceSecondary findById(int id);

	public boolean delete(int pSkillId);
}
