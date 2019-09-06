package org.yash.rms.dao;

import java.util.List;

import org.yash.rms.domain.SkillResourceSecondary;

public interface SkillResourceSecondaryDao extends RmsCRUDDAO<SkillResourceSecondary>{

	public List<SkillResourceSecondary> findSkillResourcesByResourceId(int resourcId);
	
	SkillResourceSecondary findById(int id);
}
