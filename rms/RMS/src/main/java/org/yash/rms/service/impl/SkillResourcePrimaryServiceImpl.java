package org.yash.rms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yash.rms.dao.SkillResourcePrimaryDao;
import org.yash.rms.domain.SkillResourcePrimary;
import org.yash.rms.service.SkillResourcePrimaryService;

@Service("skillResourcePrimaryService")
public class SkillResourcePrimaryServiceImpl implements SkillResourcePrimaryService{

	@Autowired
	private SkillResourcePrimaryDao skillResourcePrimaryDao;
	
	public List<SkillResourcePrimary> findSkillResourcesByResourceId(int resourcId) {
		return skillResourcePrimaryDao.findSkillResourcesByResourceId(resourcId);
	}

	public SkillResourcePrimary findById(int id) {
		return skillResourcePrimaryDao.findById(id);
	}

	public boolean delete(int id) {
		return skillResourcePrimaryDao.delete(id);
	}

}
