/**
 * 
 */
package org.yash.rms.json.mapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.springframework.stereotype.Component;
import org.yash.rms.domain.ProjectSubModule;
import org.yash.rms.dto.ProjectSubModuleDTO;
import org.yash.rms.dto.ProjectSubModuleActiveInActiveListDTO;
import org.yash.rms.util.Constants;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import flexjson.transformer.DateTransformer;

@Component("ProjectSubModuleMapper")
public class ProjectSubModuleMapper {
	
	public String toJSONArrayForDropDown(Collection<ProjectSubModuleDTO> projectSubModulesDTO) {

		return new JSONSerializer().include("subModuleId", "subModuleName").exclude("*")
				.serialize(projectSubModulesDTO);
	}


	public static Collection<ProjectSubModule> fromJsonArrayToProjectSubModule(String json) {
		return new JSONDeserializer<List<ProjectSubModule>>().use(null, ArrayList.class).use("values", ProjectSubModule.class).use(Date.class, new DateTransformer(Constants.DATE_PATTERN_4))
				.deserialize(json);
	}

	public ProjectSubModuleDTO fromJsonToObject(String json, Class<ProjectSubModuleDTO> projectSubModuleDTO) {
		return new JSONDeserializer<ProjectSubModuleDTO>().use(null, projectSubModuleDTO).use(Date.class, new DateTransformer(Constants.DATE_PATTERN_4)).deserialize(json);
	}

	public ProjectSubModule fromJsonToObjectDomain(String json, Class<ProjectSubModule> class1) {
		return new JSONDeserializer<ProjectSubModule>().use(null, class1).deserialize(json);
	}

	public List<JSONArray> toJsonArray(List<ProjectSubModule> projectSubModuleList) {
		List<JSONArray> dataArray = new ArrayList<JSONArray>();

		for (ProjectSubModule subModuleList : projectSubModuleList) {
			JSONArray object = new JSONArray();
			object.put(1, Integer.toString(subModuleList.getId()));
			object.put(2, subModuleList.getModule().getModuleName() + "");
			object.put(3, subModuleList.getModule().getProjectId().getProjectName());
			object.put(4, subModuleList.getBgbu());
			object.put(5, subModuleList.getModule().getProjectId().getCustomerNameId().getCustomerName());
			dataArray.add(object);
		}

		return dataArray;
	}

	public String toJsonArrayModule(ProjectSubModuleActiveInActiveListDTO projectSubModulesDTO) {

		return new JSONSerializer().exclude("*.class", "moduleId").serialize(projectSubModulesDTO);
	}

	public String objectToJsonForDTO(List<ProjectSubModuleActiveInActiveListDTO> projectSubModulesDTO) {

		List<ProjectSubModuleDTO> activeSubModuleList = projectSubModulesDTO.get(0).getActiveProjectSubModules();
		List<ProjectSubModuleDTO> inActiveSubModuleList = projectSubModulesDTO.get(0).getInactiveProjectSubModules();
		ProjectSubModuleDTO projectSubModuleDTO = null;
		List<ProjectSubModuleDTO> listOfSubModule = new ArrayList<ProjectSubModuleDTO>();
		if (activeSubModuleList != null) {
			for (ProjectSubModuleDTO modulesDTO : activeSubModuleList) {

				projectSubModuleDTO = new ProjectSubModuleDTO();
				projectSubModuleDTO.setSubModuleId(modulesDTO.getSubModuleId());
				projectSubModuleDTO.setModule(modulesDTO.getModule());
				projectSubModuleDTO.setActiveIndicator(modulesDTO.getActiveIndicator());
				projectSubModuleDTO.setSubModuleName(modulesDTO.getSubModuleName());
				listOfSubModule.add(projectSubModuleDTO);
			}
		}
		if (inActiveSubModuleList != null) {

			for (ProjectSubModuleDTO modulesDTO : inActiveSubModuleList) {

				projectSubModuleDTO = new ProjectSubModuleDTO();
				projectSubModuleDTO.setSubModuleId(modulesDTO.getSubModuleId());
				projectSubModuleDTO.setModule(modulesDTO.getModule());
				projectSubModuleDTO.setActiveIndicator(modulesDTO.getActiveIndicator());
				projectSubModuleDTO.setSubModuleName(modulesDTO.getSubModuleName());
				listOfSubModule.add(projectSubModuleDTO);
			}
		}

		return new JSONSerializer().exclude("*.class", "module").serialize(listOfSubModule);
	}

}
