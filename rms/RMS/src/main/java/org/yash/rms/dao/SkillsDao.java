package org.yash.rms.dao;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;
import org.yash.rms.domain.BillingScale;
import org.yash.rms.domain.Resource;
import org.yash.rms.domain.Skills;

@Transactional
public interface SkillsDao extends RmsCRUDDAO<Skills> {
	 
	
	 public   List<Skills> getPrimarySkills();
	 public  List<Skills> getSecondrySkills();
	 Skills find(int id);
}
