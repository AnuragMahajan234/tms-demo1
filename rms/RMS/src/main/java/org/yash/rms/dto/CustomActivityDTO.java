package org.yash.rms.dto;

import java.util.Date;

public class CustomActivityDTO
{
  private Integer id;
  @org.codehaus.jackson.annotate.JsonProperty("projectId")
  private ProjectDTO project;
  private String activityName;
  private boolean mandatory;
  private Integer max;
  private String format;
  private boolean productive;
  private Date creationTimestamp;
  private boolean active;
  private Date lastupdatedTimestamp;
  
  public CustomActivityDTO() {}
  
  public Integer getId()
  {
    return id;
  }
  
  public void setId(Integer id)
  {
    this.id = id;
  }
  
  public ProjectDTO getProject() {
    return project;
  }
  
  public void setProject(ProjectDTO project) {
    this.project = project;
  }
  
  public String getActivityName() {
    return activityName;
  }
  
  public void setActivityName(String activityName) {
    this.activityName = activityName;
  }
  
  public boolean isMandatory() {
    return mandatory;
  }
  
  public void setMandatory(boolean mandatory) {
    this.mandatory = mandatory;
  }
  
  public Integer getMax() {
    return max;
  }
  
  public void setMax(Integer max) {
    this.max = max;
  }
  
  public String getFormat() {
    return format;
  }
  
  public void setFormat(String format) {
    this.format = format;
  }
  
  public boolean isProductive() {
    return productive;
  }
  
  public void setProductive(boolean productive) {
    this.productive = productive;
  }
  
  public Date getCreationTimestamp() {
    return creationTimestamp;
  }
  
  public void setCreationTimestamp(Date creationTimestamp) {
    this.creationTimestamp = creationTimestamp;
  }
  
  public boolean isActive() {
    return active;
  }
  
  public void setActive(boolean active) {
    this.active = active;
  }
  
  public Date getLastupdatedTimestamp() {
    return lastupdatedTimestamp;
  }
  
  public void setLastupdatedTimestamp(Date lastupdatedTimestamp) {
    this.lastupdatedTimestamp = lastupdatedTimestamp;
  }
}
