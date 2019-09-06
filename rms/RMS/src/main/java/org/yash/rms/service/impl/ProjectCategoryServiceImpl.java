package org.yash.rms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.yash.rms.dao.RmsCRUDDAO;
import org.yash.rms.domain.ProjectCategory;
import org.yash.rms.service.RmsCRUDService;
import org.yash.rms.util.DozerMapperUtility;

@Service("ProjectCategoryService")
public class ProjectCategoryServiceImpl implements RmsCRUDService<ProjectCategory> {

	@Autowired @Qualifier("ProjectCategoryDao")
	RmsCRUDDAO<ProjectCategory> projectCategorydao;
	@Autowired
	private DozerMapperUtility mapper;
	
	public boolean delete(int id) {
		return projectCategorydao.delete(id);
	}

	public boolean saveOrupdate(ProjectCategory categories) {
		return projectCategorydao.saveOrupdate(mapper.convertDTOObjectToDomain(categories));
	}

	public List<ProjectCategory> findAll() {
		List<ProjectCategory> categories = mapper.convertprojectCategoryDomainListToDTOList(projectCategorydao.findAll());
		return categories;
	}

	public List<ProjectCategory> findByEntries(int firstResult, int sizeNo) {
		// TODO Auto-generated method stub
		return null;
	}

	public long countTotal() {
		// TODO Auto-generated method stub
		return 0;
	}

}
