package org.yash.rms.dto;

import java.util.List;

public class ProjectSubModuleActiveInActiveListDTO
{
  private List<ProjectSubModuleDTO> inactiveProjectSubModules;
  private List<ProjectSubModuleDTO> activeProjectSubModules;
  
  public ProjectSubModuleActiveInActiveListDTO() {}
  
  public List<ProjectSubModuleDTO> getInactiveProjectSubModules() {
    if (inactiveProjectSubModules == null) {
      inactiveProjectSubModules = new java.util.ArrayList();
    }
    
    return inactiveProjectSubModules;
  }
  
  public void setInactiveProjectSubModules(List<ProjectSubModuleDTO> inactiveProjectSubModules) {
    this.inactiveProjectSubModules = inactiveProjectSubModules;
  }
  
  public List<ProjectSubModuleDTO> getActiveProjectSubModules() {
    if (activeProjectSubModules == null) {
      activeProjectSubModules = new java.util.ArrayList();
    }
    
    return activeProjectSubModules;
  }
  
  public void setActiveProjectSubModules(List<ProjectSubModuleDTO> activeProjectSubModules) {
    this.activeProjectSubModules = activeProjectSubModules;
  }
}
