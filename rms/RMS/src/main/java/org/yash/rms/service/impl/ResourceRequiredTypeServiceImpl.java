package org.yash.rms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yash.rms.dao.ResourceRequiredTypeDao;
import org.yash.rms.domain.ResourceRequiredType;
import org.yash.rms.service.ResourceRequiredTypeService;

@Service("ResourceRequiredTypeService")
public class ResourceRequiredTypeServiceImpl implements ResourceRequiredTypeService {

	@Autowired
	ResourceRequiredTypeDao requiredTypeDao;
	
	
	public List<ResourceRequiredType> getResourceRequiredTypes() {
		return requiredTypeDao.getResourceRequiredTypes();
	}

}
