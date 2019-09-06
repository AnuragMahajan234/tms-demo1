package org.yash.rms.dto;

public class SkillsMappingDTO {
  private Integer ratingId;
  private Integer skillId;
  private Integer primarySkillId;
  private Integer secondarySkillId;
  private Integer primarySkillRatingId;
  private Integer secondarySkillRatingId;
  private String skillName;
  private String primarySkillType;
  private String secondarySkillType;
  private String ratingName;
  private Integer primarySkillPKId;
  private Integer secondarySkillPKId;
  private Integer experience;
  private Integer primaryExperience;
  
  public SkillsMappingDTO() {}
  
  public Integer getExperience() {
    return experience;
  }
  
  public void setExperience(Integer experience) { this.experience = experience; }
  

  public Integer getRatingId()
  {
    return ratingId;
  }
  
  public void setRatingId(Integer ratingId) { this.ratingId = ratingId; }
  
  public Integer getSkillId() {
    return skillId;
  }
  
  public void setSkillId(Integer skillId) { this.skillId = skillId; }
  
  public Integer getPrimarySkillId() {
    return primarySkillId;
  }
  
  public void setPrimarySkillId(Integer primarySkillId) { this.primarySkillId = primarySkillId; }
  
  public Integer getSecondarySkillId() {
    return secondarySkillId;
  }
  
  public void setSecondarySkillId(Integer secondarySkillId) { this.secondarySkillId = secondarySkillId; }
  
  public Integer getPrimarySkillRatingId() {
    return primarySkillRatingId;
  }
  
  public void setPrimarySkillRatingId(Integer primarySkillRatingId) { this.primarySkillRatingId = primarySkillRatingId; }
  
  public Integer getSecondarySkillRatingId() {
    return secondarySkillRatingId;
  }
  
  public void setSecondarySkillRatingId(Integer secondarySkillRatingId) { this.secondarySkillRatingId = secondarySkillRatingId; }
  
  public String getSkillName() {
    return skillName;
  }
  
  public void setSkillName(String skillName) { this.skillName = skillName; }
  
  public String getPrimarySkillType() {
    return primarySkillType;
  }
  
  public void setPrimarySkillType(String primarySkillType) { this.primarySkillType = primarySkillType; }
  
  public String getSecondarySkillType() {
    return secondarySkillType;
  }
  
  public void setSecondarySkillType(String secondarySkillType) { this.secondarySkillType = secondarySkillType; }
  
  public String getRatingName() {
    return ratingName;
  }
  
  public void setRatingName(String ratingName) { this.ratingName = ratingName; }
  
  public Integer getPrimarySkillPKId() {
    return primarySkillPKId;
  }
  
  public void setPrimarySkillPKId(Integer primarySkillPKId) { this.primarySkillPKId = primarySkillPKId; }
  
  public Integer getSecondarySkillPKId() {
    return secondarySkillPKId;
  }
  
  public void setSecondarySkillPKId(Integer secondarySkillPKId) { this.secondarySkillPKId = secondarySkillPKId; }
  
  public Integer getPrimaryExperience() {
    return primaryExperience;
  }
  
  public void setPrimaryExperience(Integer primaryExperience) { this.primaryExperience = primaryExperience; }
}
