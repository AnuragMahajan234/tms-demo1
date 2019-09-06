package org.yash.rms.json.mapper;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.yash.rms.domain.ProjectModule;

import flexjson.JSONSerializer;
/*User Story #4422(Create a new screen : Configure Project Module under configuration link)-change end*/
@Component("ProjectModuleJsonMapper")
public class ProjectModuleJsonMapper {
  private static final Logger logger = LoggerFactory.getLogger(ProjectModuleJsonMapper.class);

  public String toJsonArray(Collection<ProjectModule> collection) {
      try {
          return new JSONSerializer()
                  .include("id", "moduleName")
                  .exclude("*")
                  .serialize(collection);
      } catch (Exception ex) {
          ex.printStackTrace();
          logger.error("EXCEPTION OCCURED IN MAPPING PROJECT MODULE LIST TO JSON ARRAY "
                  + ex.getMessage());
          return "";
      }
  }
}
