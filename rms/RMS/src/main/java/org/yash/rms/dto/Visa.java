package org.yash.rms.dto;

import java.util.Date;

public class Visa {
  private int id;
  private String visa;
  private String createdId;
  private Date creationTimestamp;
  private String lastUpdatedId;
  private Date lastupdatedTimestamp;
  
  public Visa() {}
  
  public int getId() {
    return id;
  }
  
  public void setId(int id)
  {
    this.id = id;
  }
  
  public String getVisa()
  {
    return visa;
  }
  
  public void setVisa(String visa)
  {
    this.visa = visa;
  }
  
  public String getCreatedId()
  {
    return createdId;
  }
  
  public void setCreatedId(String createdId)
  {
    this.createdId = createdId;
  }
  
  public Date getCreationTimestamp()
  {
    return creationTimestamp;
  }
  
  public void setCreationTimestamp(Date creationTimestamp)
  {
    this.creationTimestamp = creationTimestamp;
  }
  
  public String getLastUpdatedId()
  {
    return lastUpdatedId;
  }
  
  public void setLastUpdatedId(String lastUpdatedId)
  {
    this.lastUpdatedId = lastUpdatedId;
  }
  
  public Date getLastupdatedTimestamp()
  {
    return lastupdatedTimestamp;
  }
  
  public void setLastupdatedTimestamp(Date lastupdatedTimestamp)
  {
    this.lastupdatedTimestamp = lastupdatedTimestamp;
  }
}
