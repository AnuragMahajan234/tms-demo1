package org.yash.rms.dto;

import flexjson.JSONSerializer;

public class ResourceReleaseSummaryDTO
{
  private Integer employeeId;
  private Integer projectId;
  private Integer resourceAllocationId;
  private String employeeName;
  private String jobTitle;
  private String allocStartDate;
  private String allocEndDate;
  private String joiningDate;
  private String clientName;
  private String primarySkills;
  private String trainingName;
  private String teamHandleExperiance;
  private String ktStatus;
  private String reasonForRelease;
  private String pipStatus;
  private Integer jobKnowledgeRating;
  private Integer workQualityRating;
  private Integer attandanceRating;
  private Integer intiativeRating;
  private Integer communicationRating;
  private Integer listingSkillsRating;
  private Integer dependabilityRating;
  private Integer overAllRating;
  private String managerName;
  private String currentBGBU;
  
  public ResourceReleaseSummaryDTO() {}
  
  public String getCurrentBGBU() {
    return currentBGBU;
  }
  
  public void setCurrentBGBU(String currentBGBu) {
    currentBGBU = currentBGBu;
  }
  
  public Integer getEmployeeId() {
    return employeeId;
  }
  
  public void setEmployeeId(Integer employeeId) {
    this.employeeId = employeeId;
  }
  
  public Integer getProjectId() {
    return projectId;
  }
  
  public void setProjectId(Integer projectId) {
    this.projectId = projectId;
  }
  
  public Integer getResourceAllocationId() {
    return resourceAllocationId;
  }
  
  public void setResourceAllocationId(Integer resourceAllocationId) {
    this.resourceAllocationId = resourceAllocationId;
  }
  
  public String getEmployeeName() {
    return employeeName;
  }
  
  public void setEmployeeName(String employeeName) {
    this.employeeName = employeeName;
  }
  
  public String getJobTitle() {
    return jobTitle;
  }
  
  public void setJobTitle(String jobTitle) {
    this.jobTitle = jobTitle;
  }
  
  public String getAllocStartDate() {
    return allocStartDate;
  }
  
  public void setAllocStartDate(String allocStartDate) {
    this.allocStartDate = allocStartDate;
  }
  
  public String getAllocEndDate() {
    return allocEndDate;
  }
  
  public void setAllocEndDate(String allocEndDate) {
    this.allocEndDate = allocEndDate;
  }
  
  public String getJoiningDate() {
    return joiningDate;
  }
  
  public void setJoiningDate(String joiningDate) {
    this.joiningDate = joiningDate;
  }
  
  public String getClientName() {
    return clientName;
  }
  
  public void setClientName(String clientName) {
    this.clientName = clientName;
  }
  
  public String getTrainingName() {
    return trainingName;
  }
  
  public void setTrainingName(String trainingName) {
    this.trainingName = trainingName;
  }
  
  public String getTeamHandleExperiance() {
    return teamHandleExperiance;
  }
  
  public void setTeamHandleExperiance(String teamHandleExperiance) {
    this.teamHandleExperiance = teamHandleExperiance;
  }
  
  public String getKtStatus() {
    return ktStatus;
  }
  
  public void setKtStatus(String ktStatus) {
    this.ktStatus = ktStatus;
  }
  
  public String getReasonForRelease() {
    return reasonForRelease;
  }
  
  public void setReasonForRelease(String reasonForRelease) {
    this.reasonForRelease = reasonForRelease;
  }
  
  public String getPipStatus() {
    return pipStatus;
  }
  
  public void setPipStatus(String pipStatus) {
    this.pipStatus = pipStatus;
  }
  
  public Integer getJobKnowledgeRating() {
    return jobKnowledgeRating;
  }
  
  public void setJobKnowledgeRating(Integer jobKnowledgeRating) {
    this.jobKnowledgeRating = jobKnowledgeRating;
  }
  
  public Integer getWorkQualityRating() {
    return workQualityRating;
  }
  
  public void setWorkQualityRating(Integer workQualityRating) {
    this.workQualityRating = workQualityRating;
  }
  
  public Integer getAttandanceRating() {
    return attandanceRating;
  }
  
  public void setAttandanceRating(Integer attandanceRating) {
    this.attandanceRating = attandanceRating;
  }
  
  public Integer getIntiativeRating() {
    return intiativeRating;
  }
  
  public void setIntiativeRating(Integer intiativeRating) {
    this.intiativeRating = intiativeRating;
  }
  
  public Integer getCommunicationRating() {
    return communicationRating;
  }
  
  public void setCommunicationRating(Integer communicationRating) {
    this.communicationRating = communicationRating;
  }
  
  public Integer getListingSkillsRating() {
    return listingSkillsRating;
  }
  
  public void setListingSkillsRating(Integer listingSkillsRating) {
    this.listingSkillsRating = listingSkillsRating;
  }
  
  public Integer getDependabilityRating() {
    return dependabilityRating;
  }
  
  public void setDependabilityRating(Integer dependabilityRating) {
    this.dependabilityRating = dependabilityRating;
  }
  
  public Integer getOverAllRating() {
    return overAllRating;
  }
  
  public void setOverAllRating(Integer overAllRating) {
    this.overAllRating = overAllRating;
  }
  
  public String getPrimarySkills() {
    return primarySkills;
  }
  
  public void setPrimarySkills(String primarySkills) {
    this.primarySkills = primarySkills;
  }
  
  public String getManagerName() {
    return managerName;
  }
  
  public void setManagerName(String managerName) {
    this.managerName = managerName;
  }
  
  public String toString()
  {
    return "ResourceReleaseSummaryForm [employeeId=" + employeeId + ", projectId=" + projectId + ", employeeName=" + employeeName + ", jobTitle=" + jobTitle + ", allocStartDate=" + allocStartDate + ", allocEndDate=" + allocEndDate + ", joiningDate=" + joiningDate + ", clientName=" + clientName + ", primarySkills=" + primarySkills + ", trainingName=" + trainingName + ", teamHandleExperiance=" + teamHandleExperiance + ", ktStatus=" + ktStatus + ", reasonForRelease=" + reasonForRelease + ", pipStatus=" + pipStatus + ", jobKnowledgeRating=" + jobKnowledgeRating + ", workQualityRating=" + workQualityRating + ", attandanceRating=" + attandanceRating + ", intiativeRating=" + intiativeRating + ", communicationRating=" + communicationRating + ", listingSkillsRating=" + listingSkillsRating + ", dependabilityRating=" + dependabilityRating + ", overAllRating=" + overAllRating + ", managerName=" + managerName + "]";
  }
  
  public static String toJsonArray(java.util.List<ResourceReleaseSummaryDTO> resourceReleaseSummaryDTOs)
  {
    return new JSONSerializer().exclude(new String[] { "*.class" }).serialize(resourceReleaseSummaryDTOs);
  }
}
