package org.yash.rms.dto;

import java.util.Date;

public class LocationDTO {
  private Integer id;
  private String location;
  private String createdId;
  private Date creationTimestamp;
  private String lastUpdatedId;
  private Date lastupdatedTimestamp;
  private String locationHrEmailId;
  
  public LocationDTO() {}
  
  public Integer getId() {
    return id;
  }
  
  public void setId(Integer id)
  {
    this.id = id;
  }
  
  public String getLocation() {
    return location;
  }
  
  public void setLocation(String location) {
    this.location = location;
  }
  
  public String getCreatedId() {
    return createdId;
  }
  
  public void setCreatedId(String createdId) {
    this.createdId = createdId;
  }
  
  public Date getCreationTimestamp()
  {
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
  
  public String getLocationHrEmailId() {
    return locationHrEmailId;
  }
  
  public void setLocationHrEmailId(String locationHrEmailId) {
    this.locationHrEmailId = locationHrEmailId;
  }
}
