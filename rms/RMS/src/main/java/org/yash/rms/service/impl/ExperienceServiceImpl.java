package org.yash.rms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yash.rms.dao.ExperienceDao;
import org.yash.rms.domain.Experience;
import org.yash.rms.service.ExperienceService;

@Service("ExperienceService")
public class ExperienceServiceImpl implements ExperienceService {

	@Autowired
	ExperienceDao experienceDao;
	
	public List<Experience> getAllExperience() {
		return experienceDao.getAllExperience();
	}

	public Experience getExperienceById(Integer id) {
		return experienceDao.getExperienceById(id);
	}

}
