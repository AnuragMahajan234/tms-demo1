/**
 * 
 */
package org.yash.rms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.yash.rms.dao.RmsCRUDDAO;
import org.yash.rms.dao.SkillsDao;
import org.yash.rms.domain.Skills;
import org.yash.rms.service.RmsCRUDService;
import org.yash.rms.service.SkillsService;
import org.yash.rms.util.DozerMapperUtility;
 
@Service("SkillsService")
public class SkillsServiceImpl implements SkillsService{

	@Autowired@Qualifier("skillsDaoImpl")
	SkillsDao skillsDao;
	
	@Autowired
	private DozerMapperUtility mapper;


	public boolean saveOrupdate(Skills skills) {
		// TODO Auto-generated method stub
		return skillsDao.saveOrupdate(mapper.convertDTOObjectToDomain(skills));
	}

	public List<Skills> findAll() {
		// TODO Auto-generated method stub
		return mapper.convertskillsDomainListToDTOList(skillsDao.findAll());
	}

	public List<Skills> findByEntries(int firstResult, int sizeNo) {
		// TODO Auto-generated method stub
		return mapper.convertskillsDomainListToDTOList(skillsDao.findByEntries(firstResult, sizeNo));
	}

	public long countTotal() {
		// TODO Auto-generated method stub
		return skillsDao.countTotal();
	}

	public boolean delete(int id) {
		// TODO Auto-generated method stub
		return skillsDao.delete(id);
	}

	 
	public List<Skills> findPrimarySkills() {
		// TODO Auto-generated method stub
		return skillsDao.getPrimarySkills();
	}

	public List<Skills> findSecondarySkills() {
		// TODO Auto-generated method stub
		return skillsDao.getSecondrySkills();
	}

	public Skills findSkillsById(int id) {
		// TODO Auto-generated method stub
		return skillsDao.find(id);
	}

	public Skills find(int id) {
		// TODO Auto-generated method stub
		return skillsDao.find(id);
	}

}
