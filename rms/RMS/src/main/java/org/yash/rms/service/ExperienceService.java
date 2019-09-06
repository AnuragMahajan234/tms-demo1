package org.yash.rms.service;

import java.util.List;

import org.yash.rms.domain.Experience;

public interface ExperienceService {

	public List<Experience> getAllExperience();

	public Experience getExperienceById(Integer id);

}
