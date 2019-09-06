package org.yash.rms.dto;

import java.util.List;

import org.yash.rms.domain.Location;

public class EditProfileDTO {
  private Integer employeeId;
  private String yashEmployeeId;
  private String firstName;
  private String lastName;
  private String middleName;
  private String emailId;
  private String contactNumberOne;
  private String contactNumberTwo;
  private String customerIdDetail;
  private String grade;
  private String designationName;
  private String resourceManager1;
  private String resourceManager2;
  private byte[] uploadImage;
  private List userProfilePrimarySkillList;
  private List userProfileSecondarySkillList;
  private List ratingList;
  private List primarySkillsList;
  private List secondarySkillsList;
  private byte[] uploadResume;
  private String uploadResumeFileName;
  private String uploadImageFileName;
  private Integer experience;
  private double totalExp;
  private double relevantExp;
  private String yashExp;
  private Location preferredLocation;

public Location getPreferredLocation() {
	return preferredLocation;
}

public void setPreferredLocation(Location preferredLocation) {
	this.preferredLocation = preferredLocation;
}

public EditProfileDTO() {}
  
  public double getTotalExp() {
    return totalExp;
  }
  
  public void setTotalExp(double totalExp) {
    this.totalExp = totalExp;
  }
  
  public double getRelevantExp() {
    return relevantExp;
  }
  
  public void setRelevantExp(double relevantExp) {
    this.relevantExp = relevantExp;
  }
  
  public String getYashExp() {
    return yashExp;
  }
  
  public void setYashExp(String yashExp) {
    this.yashExp = yashExp;
  }
  
  public Integer getExperience() {
    return experience;
  }
  
  public void setExperience(Integer experience) {
    this.experience = experience;
  }
  
  public Integer getEmployeeId() {
    return employeeId;
  }
  
  public void setEmployeeId(Integer employeeId) {
    this.employeeId = employeeId;
  }
  
  public String getYashEmployeeId() {
    return yashEmployeeId;
  }
  
  public void setYashEmployeeId(String yashEmployeeId) {
    this.yashEmployeeId = yashEmployeeId;
  }
  
  public String getFirstName() {
    return firstName;
  }
  
  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }
  
  public String getLastName() {
    return lastName;
  }
  
  public void setLastName(String lastName) {
    this.lastName = lastName;
  }
  
  public String getMiddleName() {
    return middleName;
  }
  
  public void setMiddleName(String middleName) {
    this.middleName = middleName;
  }
  
  public String getEmailId() {
    return emailId;
  }
  
  public void setEmailId(String emailId) {
    this.emailId = emailId;
  }
  
  public String getContactNumberOne() {
    return contactNumberOne;
  }
  
  public void setContactNumberOne(String contactNumberOne) {
    this.contactNumberOne = contactNumberOne;
  }
  
  public String getContactNumberTwo() {
    return contactNumberTwo;
  }
  
  public void setContactNumberTwo(String contactNumberTwo) {
    this.contactNumberTwo = contactNumberTwo;
  }
  
  public String getCustomerIdDetail() {
    return customerIdDetail;
  }
  
  public void setCustomerIdDetail(String customerIdDetail) {
    this.customerIdDetail = customerIdDetail;
  }
  
  public String getGrade() {
    return grade;
  }
  
  public void setGrade(String grade) {
    this.grade = grade;
  }
  
  public String getDesignationName() {
    return designationName;
  }
  
  public void setDesignationName(String designationName) {
    this.designationName = designationName;
  }
  
  public String getResourceManager1() {
    return resourceManager1;
  }
  
  public void setResourceManager1(String resourceManager1) {
    this.resourceManager1 = resourceManager1;
  }
  
  public String getResourceManager2() {
    return resourceManager2;
  }
  
  public void setResourceManager2(String resourceManager2) {
    this.resourceManager2 = resourceManager2;
  }
  
  public List getUserProfilePrimarySkillList() {
    return userProfilePrimarySkillList;
  }
  
  public void setUserProfilePrimarySkillList(List userProfilePrimarySkillList) {
    this.userProfilePrimarySkillList = userProfilePrimarySkillList;
  }
  
  public List getUserProfileSecondarySkillList() {
    return userProfileSecondarySkillList;
  }
  
  public void setUserProfileSecondarySkillList(List userProfileSecondarySkillList) {
    this.userProfileSecondarySkillList = userProfileSecondarySkillList;
  }
  
  public List getRatingList() {
    return ratingList;
  }
  
  public void setRatingList(List ratingList) {
    this.ratingList = ratingList;
  }
  
  public List getPrimarySkillsList() {
    return primarySkillsList;
  }
  
  public void setPrimarySkillsList(List primarySkillsList) {
    this.primarySkillsList = primarySkillsList;
  }
  
  public List getSecondarySkillsList() {
    return secondarySkillsList;
  }
  
  public void setSecondarySkillsList(List secondarySkillsList) {
    this.secondarySkillsList = secondarySkillsList;
  }
  
  public byte[] getUploadImage() {
    return uploadImage;
  }
  
  public void setUploadImage(byte[] uploadImage) {
    this.uploadImage = uploadImage;
  }
  
  public byte[] getUploadResume() {
    return uploadResume;
  }
  
  public void setUploadResume(byte[] uploadResume) {
    this.uploadResume = uploadResume;
  }
  
  public String getUploadResumeFileName() {
    return uploadResumeFileName;
  }
  
  public void setUploadResumeFileName(String uploadResumeFileName) {
    this.uploadResumeFileName = uploadResumeFileName;
  }
  
  public String getUploadImageFileName() {
    return uploadImageFileName;
  }
  
  public void setUploadImageFileName(String uploadImageFileName) {
    this.uploadImageFileName = uploadImageFileName;
  }
}
