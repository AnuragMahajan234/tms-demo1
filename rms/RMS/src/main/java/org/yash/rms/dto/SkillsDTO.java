package org.yash.rms.dto;

import java.util.Date;

public class SkillsDTO {
  private Integer id;
  private String skill;
  private String skillType;
  private String createdId;
  private Date creationTimestamp;
  private String lastUpdatedId;
  private Date lastupdatedTimestamp;
  
  public SkillsDTO() {}
  
  public Integer getId() {
    return id;
  }
  
  public void setId(Integer id) { this.id = id; }
  
  public String getSkill() {
    return skill;
  }
  
  public void setSkill(String skill) { this.skill = skill; }
  
  public String getSkillType() {
    return skillType;
  }
  
  public void setSkillType(String skillType) { this.skillType = skillType; }
  
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
