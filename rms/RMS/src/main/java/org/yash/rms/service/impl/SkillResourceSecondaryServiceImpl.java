package org.yash.rms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yash.rms.dao.SkillResourceSecondaryDao;
import org.yash.rms.domain.SkillResourceSecondary;
import org.yash.rms.service.SkillResourceSecondaryService;

@Service("skillResourceSecondaryService")
public class SkillResourceSecondaryServiceImpl implements SkillResourceSecondaryService {

	@Autowired
	private SkillResourceSecondaryDao skillResourceSecondaryDao;
	
	public List<SkillResourceSecondary> findSkillResourcesByResourceId(int resourcId) {
		return skillResourceSecondaryDao.findSkillResourcesByResourceId(resourcId);
	}

	public SkillResourceSecondary findById(int id) {
		return skillResourceSecondaryDao.findById(id);
	}
	public boolean delete(int id) {
		return skillResourceSecondaryDao.delete(id);
	}
}
