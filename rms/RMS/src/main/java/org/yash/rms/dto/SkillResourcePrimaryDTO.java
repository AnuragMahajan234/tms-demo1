package org.yash.rms.dto;

public class SkillResourcePrimaryDTO {
  private Integer primarySkillId;
  private String primarySkillName;
  private Integer experience;
  
  public SkillResourcePrimaryDTO() {}
  
  public Integer getExperience() {
    return experience;
  }
  
  public void setExperience(Integer experience) { this.experience = experience; }
  

  public Integer getPrimarySkillId()
  {
    return primarySkillId;
  }
  
  public void setPrimarySkillId(Integer primarySkillId) {
    this.primarySkillId = primarySkillId;
  }
  
  public String getPrimarySkillName() {
    return primarySkillName;
  }
  
  public void setPrimarySkillName(String primarySkillName) {
    this.primarySkillName = primarySkillName;
  }
}
