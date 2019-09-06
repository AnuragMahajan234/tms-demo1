package org.yash.rms.dto;

import java.util.Date;

public class ActivitiesDTO {
  private Integer activityId;
  private String activityName;
  private boolean mandatory;
  private String type;
  private Integer max;
  private String format;
  private boolean productive;
  private String createdId;
  private Date creationTimestamp;
  private String lastUpdatedId;
  private Date lastUpdatedTimestamp;
  private String activityType;
  
  public ActivitiesDTO() {}
  
  public Integer getActivityId() {
    return activityId;
  }
  
  public void setActivityId(Integer activityId) {
    this.activityId = activityId;
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
  
  public String getType() {
    return type;
  }
  
  public void setType(String type) {
    this.type = type;
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
  
  public Date getLastUpdatedTimestamp() {
    return lastUpdatedTimestamp;
  }
  
  public void setLastUpdatedTimestamp(Date lastUpdatedTimestamp) {
    this.lastUpdatedTimestamp = lastUpdatedTimestamp;
  }
  
  public String getActivityType() {
    return activityType;
  }
  
  public void setActivityType(String activityType) {
    this.activityType = activityType;
  }
}
