package org.yash.rms.dto;

import java.util.Date;

public class Module {
  private Integer id;
  private String moduleName;
  private String createdId;
  private Date creationTimestamp;
  private String lastUpdatedId;
  private Date lastupdatedTimestamp;
  
  public Module() {}
  
  public Integer getId() {
    return id;
  }
  
  public void setId(Integer id) {
    this.id = id;
  }
  
  public String getModuleName() {
    return moduleName;
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
  
  public String getLastUpdatedId() {
    return lastUpdatedId;
  }
  
  public void setLastUpdatedId(String lastUpdatedId) {
    this.lastUpdatedId = lastUpdatedId;
  }
  
  public Date getLastupdatedTimestamp() {
    return lastupdatedTimestamp;
  }
  
  public void setLastupdatedTimestamp(Date lastupdatedTimestamp) {
    this.lastupdatedTimestamp = lastupdatedTimestamp;
  }
  
  public void setModuleName(String moduleName) {
    this.moduleName = moduleName;
  }
}
