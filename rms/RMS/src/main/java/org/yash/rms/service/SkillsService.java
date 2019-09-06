package org.yash.rms.service;

import java.util.List;

import org.yash.rms.domain.Skills;

public interface SkillsService extends RmsCRUDService<Skills> {

	public List<Skills> findPrimarySkills();

	public List<Skills> findSecondarySkills();

	Skills find(int id);

}
