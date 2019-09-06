package org.yash.rms.helper;

import org.springframework.stereotype.Component;
import org.yash.rms.domain.ProjectSubModule;
import org.yash.rms.dto.Module;
import org.yash.rms.dto.ProjectSubModuleDTO;

@Component(value = "ProjectSubModulesHelper")
public class ProjectSubModulesHelper {

	public ProjectSubModuleDTO fromProjectSubModuleDomainToDto(ProjectSubModule projectSubModuleDomain, ProjectSubModuleDTO projectSubModuleDTO) {

		projectSubModuleDTO.setSubModuleId(projectSubModuleDomain.getId());

		Module module = new Module();

		module.setId(projectSubModuleDomain.getModule().getId());
		projectSubModuleDTO.setModule(module);
		module.setModuleName(projectSubModuleDomain.getModule().getModuleName());

		projectSubModuleDTO.setModuleName(module);
		projectSubModuleDTO.setActiveIndicator(projectSubModuleDomain.getActive());
		projectSubModuleDTO.setCreatedId(projectSubModuleDomain.getCreatedId());
		projectSubModuleDTO.setCreationTimestamp(projectSubModuleDomain.getLastupdatedTimestamp());
		projectSubModuleDTO.setSubModuleName(projectSubModuleDomain.getSubModuleName());

		return projectSubModuleDTO;

	}

}
