package org.yash.rms.dto;

public class SubModuleDTO {
  private Integer subModuleId;
  private String subModuleName;
  
  public SubModuleDTO() {}
  
  public Integer getSubModuleId() {
    return subModuleId;
  }
  
  public void setSubModuleId(Integer subModuleId)
  {
    this.subModuleId = subModuleId;
  }
  
  public String getSubModuleName() { return subModuleName; }
  
  public void setSubModuleName(String subModuleName) {
    this.subModuleName = subModuleName;
  }
}
