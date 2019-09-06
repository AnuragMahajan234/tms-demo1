package org.yash.rms.dto;

import java.util.Date;

public class RatingDTO {
  private String name;
  private String description;
  private Integer id;
  private String createdId;
  private Date creationTimestamp;
  private String lastUpdatedId;
  private Date lastupdatedTimestamp;
  
  public RatingDTO() {}
  
  public String getName() {
    return name;
  }
  
  public void setName(String name) { this.name = name; }
  
  public String getDescription() {
    return description;
  }
  
  public void setDescription(String description) { this.description = description; }
  
  public Integer getId() {
    return id;
  }
  
  public void setId(Integer id) { this.id = id; }
  
  public String getCreatedId() {
    return createdId;
  }
  
  public void setCreatedId(String createdId) { this.createdId = createdId; }
  
  public Date getCreationTimestamp() {
    return creationTimestamp;
  }
  
  public void setCreationTimestamp(Date creationTimestamp) { this.creationTimestamp = creationTimestamp; }
  
  public String getLastUpdatedId() {
    return lastUpdatedId;
  }
  
  public void setLastUpdatedId(String lastUpdatedId) { this.lastUpdatedId = lastUpdatedId; }
  
  public Date getLastupdatedTimestamp() {
    return lastupdatedTimestamp;
  }
  
  public void setLastupdatedTimestamp(Date lastupdatedTimestamp) { this.lastupdatedTimestamp = lastupdatedTimestamp; }
}
