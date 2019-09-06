package org.yash.rms.dto;

import flexjson.JSONSerializer;

public class ManagerTimeSheetGraphDTO
{
  private String projectName;
  private Integer billable;
  private Integer others;
  
  public ManagerTimeSheetGraphDTO() {}
  
  public String getProjectName() {
    return projectName;
  }
  
  public void setProjectName(String projectName) { this.projectName = projectName; }
  
  public Integer getBillable() {
    return billable;
  }
  
  public void setBillable(Integer billable) { this.billable = billable; }
  
  public Integer getOthers() {
    return others;
  }
  
  public void setOthers(Integer others) { this.others = others; }
  

  public static String toJsonArray(java.util.Collection<ManagerTimeSheetGraphDTO> collection)
  {
    String jsonString = new JSONSerializer().include(new String[] { "projectName", "billable", "others" })
      .serialize(collection);
    
    return jsonString;
  }
}
