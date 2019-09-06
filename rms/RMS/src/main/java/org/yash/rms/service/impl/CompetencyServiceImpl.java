package org.yash.rms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.yash.rms.dao.CompetencyDao;
import org.yash.rms.dao.EmployeeCategoryDao;
import org.yash.rms.dao.RmsCRUDDAO;
import org.yash.rms.domain.Competency;
import org.yash.rms.domain.EmployeeCategory;
import org.yash.rms.domain.Ownership;
import org.yash.rms.service.CompetencyService;
import org.yash.rms.service.EmployeeCategoryService;
import org.yash.rms.service.OwnershipService;
import org.yash.rms.service.RmsCRUDService;
import org.yash.rms.util.DozerMapperUtility;

@Service("CompetencyService")
public class CompetencyServiceImpl implements  CompetencyService {

	@Autowired 
	CompetencyDao competencyDao;
	
	@Autowired
	private DozerMapperUtility mapper;

	public boolean delete(int id) {
		return competencyDao.delete(id);
	}

	public boolean saveOrupdate(Competency competency) {
		return competencyDao.saveOrupdate(mapper.convertDTOObjectToDomain(competency));
	}

	public List<Competency> findAll() {
		return mapper.convertCompetencyDomainListToDTOList(competencyDao.findAll());
	}

	public List<Competency> findByEntries(int firstResult, int sizeNo) {
		return mapper.convertCompetencyDomainListToDTOList(competencyDao.findByEntries(firstResult, sizeNo));
	}

	public long countTotal() {
		return competencyDao.countTotal();
	}

	public Competency findById(int id) {
		// TODO Auto-generated method stub
		return competencyDao.findById(id);
	}
	


}
