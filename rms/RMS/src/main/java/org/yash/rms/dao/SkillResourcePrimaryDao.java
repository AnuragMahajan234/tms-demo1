package org.yash.rms.dao;

import java.util.List;

import org.yash.rms.domain.SkillResourcePrimary;

public interface SkillResourcePrimaryDao extends RmsCRUDDAO<SkillResourcePrimary>{

	List<SkillResourcePrimary> findSkillResourcesByResourceId(int resourcId);
	
	SkillResourcePrimary findById(int id);
	
	public boolean delete(int id);
}
