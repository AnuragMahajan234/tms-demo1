package org.yash.rms.dto;

import java.util.Date;

public class TeamDTO {
  private Integer id;
  private String teamName;
  private String projectManager;
  private String createdId;
  private Date creationTimeStamp;
  private String lastUpdatedId;
  private Date lastUpdatedTimeStamp;
  
  public TeamDTO() {}
  
  public Integer getId() {
    return id;
  }
  
  public void setId(Integer id)
  {
    this.id = id;
  }
  
  public String getTeamName()
  {
    return teamName;
  }
  
  public void setTeamName(String teamName)
  {
    this.teamName = teamName;
  }
  
  public String getProjectManager()
  {
    return projectManager;
  }
  
  public void setProjectManager(String projectManager)
  {
    this.projectManager = projectManager;
  }
  
  public String getCreatedId()
  {
    return createdId;
  }
  
  public void setCreatedId(String createdId)
  {
    this.createdId = createdId;
  }
  
  public Date getCreationTimeStamp()
  {
    return creationTimeStamp;
  }
  
  public void setCreationTimeStamp(Date creationTimeStamp)
  {
    this.creationTimeStamp = creationTimeStamp;
  }
  
  public String getLastUpdatedId()
  {
    return lastUpdatedId;
  }
  
  public void setLastUpdatedId(String lastUpdatedId)
  {
    this.lastUpdatedId = lastUpdatedId;
  }
  
  public Date getLastUpdatedTimeStamp()
  {
    return lastUpdatedTimeStamp;
  }
  
  public void setLastUpdatedTimeStamp(Date lastUpdatedTimeStamp)
  {
    this.lastUpdatedTimeStamp = lastUpdatedTimeStamp;
  }
}
