package org.yash.rms.dto;

import java.util.Date;
import javax.persistence.Column;

public class ProjectMethodology
{
  private Integer id;
  private String methodology;
  private String createdId;
  private Date creationTimestamp;
  @Column(name="lastupdated_id")
  private String lastUpdatedId;
  @Column(name="lastupdated_timestamp")
  private Date lastupdatedTimestamp;
  
  public ProjectMethodology() {}
  
  public String getMethodology()
  {
    return methodology;
  }
  
  public void setMethodology(String methodology) {
    this.methodology = methodology;
  }
  
  public void setId(Integer id) {
    this.id = id;
  }
  
  public Integer getId()
  {
    return id;
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
