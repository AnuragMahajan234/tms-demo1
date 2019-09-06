package org.yash.rms.domain;


public class SkillsMapping {

	
	Integer Rating_Id;
	Integer Skill_Id;
	Integer primarySkill_Id;
	Integer secondarySkill_Id;
	Integer primarySkillRating_Id;
	Integer secondarySkillRating_Id;
	String  Skill_Name;
	String  primarySkill_Type;
	String  secondarySkill_Type;
	String  Rating_Name;
	Integer primarkSkillPKId;
	Integer secondarySkillPKId;
	Resource resource;
	Integer primaryExperience;
	Integer secondaryExperience;
//	UserProfile userProfile;
	
	public Integer getSecondaryExperience() {
		return secondaryExperience;
	}
	public void setSecondaryExperience(Integer secondaryExperience) {
		this.secondaryExperience = secondaryExperience;
	}
	public Resource getResource() {
		return resource;
	}
	public void setResource(Resource resource) {
		this.resource = resource;
	}
/*	public UserProfile getUserProfile() {
		return userProfile;
	}
	public void setUserProfile(UserProfile userProfile) {
		this.userProfile = userProfile;
	}*/
	
	public Integer getRating_Id() {
		return Rating_Id;
	}
	public void setRating_Id(Integer rating_Id) {
		Rating_Id = rating_Id;
	}
	public Integer getSkill_Id() {
		return Skill_Id;
	}
	public void setSkill_Id(Integer skill_Id) {
		Skill_Id = skill_Id;
	}
	public String getSkill_Name() {
		return Skill_Name;
	}
	public void setSkill_Name(String skill_Name) {
		Skill_Name = skill_Name;
	}
	public String getRating_Name() {
		return Rating_Name;
	}
	public void setRating_Name(String rating_Name) {
		Rating_Name = rating_Name;
	}
	
	public Integer getPrimarySkill_Id() {
		return primarySkill_Id;
	}
	public void setPrimarySkill_Id(Integer primarySkill_Id) {
		this.primarySkill_Id = primarySkill_Id;
	}
	public Integer getSecondarySkill_Id() {
		return secondarySkill_Id;
	}
	public void setSecondarySkill_Id(Integer secondarySkill_Id) {
		this.secondarySkill_Id = secondarySkill_Id;
	}
	public Integer getPrimarySkillRating_Id() {
		return primarySkillRating_Id;
	}
	public void setPrimarySkillRating_Id(Integer primarySkillRating_Id) {
		this.primarySkillRating_Id = primarySkillRating_Id;
	}
	public Integer getSecondarySkillRating_Id() {
		return secondarySkillRating_Id;
	}
	public void setSecondarySkillRating_Id(Integer secondarySkillRating_Id) {
		this.secondarySkillRating_Id = secondarySkillRating_Id;
	}
	public String getPrimarySkill_Type() {
		return primarySkill_Type;
	}
	public void setPrimarySkill_Type(String primarySkill_Type) {
		this.primarySkill_Type = primarySkill_Type;
	}
	public String getSecondarySkill_Type() {
		return secondarySkill_Type;
	}
	public void setSecondarySkill_Type(String secondarySkill_Type) {
		this.secondarySkill_Type = secondarySkill_Type;
	}
	public Integer getPrimarkSkillPKId() {
		return primarkSkillPKId;
	}
	public void setPrimarkSkillPKId(Integer primarkSkillPKId) {
		this.primarkSkillPKId = primarkSkillPKId;
	}
	public Integer getSecondarySkillPKId() {
		return secondarySkillPKId;
	}
	public void setSecondarySkillPKId(Integer secondarySkillPKId) {
		this.secondarySkillPKId = secondarySkillPKId;
	}
	public Integer getPrimaryExperience() {
		return primaryExperience;
	}
	public void setPrimaryExperience(Integer primaryExperience) {
		this.primaryExperience = primaryExperience;
	}
	
}
