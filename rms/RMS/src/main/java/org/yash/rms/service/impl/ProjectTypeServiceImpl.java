package org.yash.rms.service.impl;

import java.util.List;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.yash.rms.dao.ProjectTypeDao;
import org.yash.rms.domain.ProjectType;
import org.yash.rms.service.ProjectTypeService;

@Service("ProjectTypeService")
public class ProjectTypeServiceImpl implements ProjectTypeService {

	@Autowired
	ProjectTypeDao projectTypeDao;
	

	@Autowired
	@Qualifier("mapper")
	DozerBeanMapper mapper;
	
	public List<ProjectType> getAllProjectTypes() {
		
		return projectTypeDao.getAllProjectTypes();
	}

}
