package org.yash.rms.service;

import java.util.List;

import org.yash.rms.domain.ProjectType;
import org.yash.rms.dto.ProjectTypeDTO;

public interface ProjectTypeService {
	
	public List<ProjectType> getAllProjectTypes();
}
