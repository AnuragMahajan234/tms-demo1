package org.yash.rms.dto;

import java.util.List;

public class ModuleDTO {
  private Integer moduleId;
  private String moduleName;
  private List<SubModuleDTO> subModuleDTO;
  
  public ModuleDTO() {}
  
  public List<SubModuleDTO> getSubModuleDTO() {
    return subModuleDTO;
  }
  
  public Integer getModuleId()
  {
    return moduleId;
  }
  
  public void setModuleId(Integer moduleId)
  {
    this.moduleId = moduleId;
  }
  
  public String getModuleName() {
    return moduleName;
  }
  
  public void setModuleName(String moduleName) {
    this.moduleName = moduleName;
  }
  
  public void setSubModuleDTO(List<SubModuleDTO> subModuleDTO) {
    this.subModuleDTO = subModuleDTO;
  }
}
