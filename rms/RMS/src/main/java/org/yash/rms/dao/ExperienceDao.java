package org.yash.rms.dao;

import java.util.List;

import org.yash.rms.domain.Experience;

/**
 * 
 * @author samiksha.sant
 *
 */
public interface ExperienceDao {

	public List<Experience> getAllExperience();
	
	public Experience getExperienceById(Integer id);
	
}
