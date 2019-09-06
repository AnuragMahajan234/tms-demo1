package org.yash.rms.dto;

public class SkillResourceSecondaryDTO {
  private Integer secondarySkillId;
  private String secondarySkillName;
  private Integer experience;
  
  public SkillResourceSecondaryDTO() {}
  
  public Integer getExperience() {
    return experience;
  }
  
  public void setExperience(Integer experience) {
    this.experience = experience;
  }
  
  public Integer getSecondarySkillId()
  {
    return secondarySkillId;
  }
  
  public void setSecondarySkillId(Integer secondarySkillId) {
    this.secondarySkillId = secondarySkillId;
  }
  
  public String getSecondarySkillName() {
    return secondarySkillName;
  }
  
  public void setSecondarySkillName(String secondarySkillName) {
    this.secondarySkillName = secondarySkillName;
  }
}
