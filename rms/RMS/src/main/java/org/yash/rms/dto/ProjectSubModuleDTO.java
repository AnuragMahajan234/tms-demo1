package org.yash.rms.dto;

import java.util.Date;

public class ProjectSubModuleDTO {
  private int subModuleId;
  private String subModuleName;
  private String projectName;
  private Module moduleName;
  private Module module;
  private String bgbu;
  private String activeIndicator;
  private String createdId;
  private Date creationTimestamp;
  private String customerName;
  
  public ProjectSubModuleDTO() {}
  
  public int getSubModuleId() {
    return subModuleId;
  }
  
  public void setSubModuleId(int subModuleId) {
    this.subModuleId = subModuleId;
  }
  
  public String getProjectName() {
    return projectName;
  }
  
  public void setProjectName(String projectName) {
    this.projectName = projectName;
  }
  
  public Module getModuleName() {
    return moduleName;
  }
  
  public void setModuleName(Module moduleName) {
    this.moduleName = moduleName;
  }
  
  public String getBgbu() {
    return bgbu;
  }
  
  public void setBgbu(String bgbu) {
    this.bgbu = bgbu;
  }
  
  public String getActiveIndicator() {
    return activeIndicator;
  }
  
  public void setActiveIndicator(String activeIndicator) {
    this.activeIndicator = activeIndicator;
  }
  
  public String getCreatedId() {
    return createdId;
  }
  
  public void setCreatedId(String createdId) {
    this.createdId = createdId;
  }
  
  public Date getCreationTimestamp() {
    return creationTimestamp;
  }
  
  public void setCreationTimestamp(Date creationTimestamp) {
    this.creationTimestamp = creationTimestamp;
  }
  
  public String getCustomerName() {
    return customerName;
  }
  
  public void setCustomerName(String customerName) {
    this.customerName = customerName;
  }
  
  public Module getModule() {
    return module;
  }
  
  public void setModule(Module module) {
    this.module = module;
  }
  
  public String getSubModuleName() {
    return subModuleName;
  }
  
  public void setSubModuleName(String subModuleName) {
    this.subModuleName = subModuleName;
  }
}
