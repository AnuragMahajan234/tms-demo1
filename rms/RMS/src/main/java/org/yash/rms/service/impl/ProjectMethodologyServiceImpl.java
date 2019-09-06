package org.yash.rms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.yash.rms.dao.RmsCRUDDAO;
import org.yash.rms.domain.ProjectMethodology;
import org.yash.rms.service.RmsCRUDService;
import org.yash.rms.util.DozerMapperUtility;

@Service("ProjectMethodologyService")
public class ProjectMethodologyServiceImpl implements RmsCRUDService<ProjectMethodology> {

	@Autowired@Qualifier("ProjectMethodologyDao")
	RmsCRUDDAO<ProjectMethodology> projectMethodologyDao;
	
	 
	@Autowired
	private DozerMapperUtility mapper;

	public boolean delete(int id) {
		// TODO Auto-generated method stub
		return projectMethodologyDao.delete(id);
	}

	public boolean saveOrupdate(ProjectMethodology projectMethodology) {
		// TODO Auto-generated method stub
		return projectMethodologyDao.saveOrupdate(mapper.convertDTOObjectToDomain(projectMethodology));
	}

	public List<ProjectMethodology> findAll() {
		// TODO Auto-generated method stub
		return mapper.convertprojectMethodologyDomainListToDTOList(projectMethodologyDao.findAll());
	}

	public List<ProjectMethodology> findByEntries(
			int firstResult, int sizeNo) {
		// TODO Auto-generated method stub
		return mapper.convertprojectMethodologyDomainListToDTOList(projectMethodologyDao.findByEntries(firstResult, sizeNo));
	}

	public long countTotal() {
		// TODO Auto-generated method stub
		return projectMethodologyDao.countTotal();
	}


	
	 

}
