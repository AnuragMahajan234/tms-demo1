package org.yash.rms.dto;

import org.yash.rms.domain.TeamViewRightEmbedded;

public class TeamAccessDto {
  String resourceName;
  String team;
  Integer resourceId;
  Integer teamId;
  String createdBy;
  
  public TeamAccessDto() {}
  
  public String getResourceName() {
    return resourceName;
  }
  
  public void setResourceName(String resourceName) { this.resourceName = resourceName; }
  
  public String getTeam() {
    return team;
  }
  
  public void setTeam(String team) { this.team = team; }
  
  public Integer getResourceId()
  {
    return resourceId;
  }
  
  public void setResourceId(Integer resourceId) { this.resourceId = resourceId; }
  
  public Integer getTeamId() {
    return teamId;
  }
  
  public void setTeamId(Integer teamId) { this.teamId = teamId; }
  

  public String getCreatedBy()
  {
    return createdBy;
  }
  
  public void setCreatedBy(String createdBy) { this.createdBy = createdBy; }
  
  public static void convertToTeamAccessDto(TeamAccessDto teamAccessDto, TeamViewRightEmbedded teamAccess) {}
}
