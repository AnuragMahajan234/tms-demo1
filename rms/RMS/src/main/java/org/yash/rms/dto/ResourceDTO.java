package org.yash.rms.dto;

import java.util.Date;
import java.util.List;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.yash.rms.domain.Location;



























public class ResourceDTO
{
  @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
  private Integer employeeId;
  @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
  private String firstName;
  @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
  private String lastName;
  @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
  private String middleName;
  @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
  private String yashEmpId;
  @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
  private String userRole;
  @JsonProperty("designation")
  @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
  private DesignationDTO designationId;
  @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
  private Date confirmationDate;
  @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
  private Date dateOfJoining;
  @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
  private String emailId;
  @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
  private String userName;
  @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
  private String primaryLocation;
  @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
  private String deploymentLocation;
  @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
  private String parentBuId;
  @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
  private String currentBuId;
  @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
  private ResourceManagerDTO immediateReportingManager;
  @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
  private ResourceManagerDTO seniorReportingManager;
  @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
  private String grade;
  @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
  private String ownership;
  @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
  private String competency;
  @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
  private String employeeCategory;
  @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
  private Date releaseDate;
  @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
  private List<SkillResourcePrimaryDTO> resourcePrimarySkills;
  @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
  private List<SkillResourceSecondaryDTO> resourceSecondarySkills;
  @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
  private List<ResourceAllocationDTO> resourceAllocations;
  @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
  private String employeeName;
  @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
  private byte[] uploadResume;
  @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
  private String uploadResumeFileName;
  
  @JsonSerialize(include=JsonSerialize.Inclusion.ALWAYS)
  private Location preferredLocation;
  
  public Location getPreferredLocation() {
	return preferredLocation;
}

public void setPreferredLocation(Location preferredLocation) {
	this.preferredLocation = preferredLocation;
}

public ResourceDTO() {}
  
  public String getUsername()
  {
    return getEmployeeName();
  }
  
  public Integer getEmployeeId() {
    return employeeId;
  }
  
  public void setEmployeeId(Integer employeeId) {
    this.employeeId = employeeId;
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
  
  public String getYashEmpId() {
    return yashEmpId;
  }
  
  public void setYashEmpId(String yashEmpId) {
    this.yashEmpId = yashEmpId;
  }
  
  public Date getConfirmationDate() {
    return confirmationDate;
  }
  
  public void setConfirmationDate(Date confirmationDate) {
    this.confirmationDate = confirmationDate;
  }
  
  public Date getDateOfJoining() {
    return dateOfJoining;
  }
  
  public void setDateOfJoining(Date dateOfJoining) {
    this.dateOfJoining = dateOfJoining;
  }
  
  public String getEmailId() {
    return emailId;
  }
  
  public void setEmailId(String emailId) {
    this.emailId = emailId;
  }
  
  public String getUserRole() {
    return userRole;
  }
  
  public void setUserRole(String userRole) {
    this.userRole = userRole;
  }
  
  public String getUserName() {
    return userName;
  }
  
  public void setUserName(String userName) {
    this.userName = userName;
  }
  
  public String getMiddleName() {
    return middleName;
  }
  
  public void setMiddleName(String middleName) {
    this.middleName = middleName;
  }
  
  public Date getReleaseDate() {
    return releaseDate;
  }
  
  public void setReleaseDate(Date releaseDate) {
    this.releaseDate = releaseDate;
  }
  
  public String getPrimaryLocation() {
    return primaryLocation;
  }
  
  public DesignationDTO getDesignationId() {
    return designationId;
  }
  
  public void setDesignationId(DesignationDTO designation) {
    designationId = designation;
  }
  
  public void setPrimaryLocation(String primaryLocation) {
    this.primaryLocation = primaryLocation;
  }
  
  public String getDeploymentLocation() {
    return deploymentLocation;
  }
  
  public void setDeploymentLocation(String deploymentLocation) {
    this.deploymentLocation = deploymentLocation;
  }
  
  public String getParentBuId() {
    return parentBuId;
  }
  
  public void setParentBuId(String parentBuId) {
    this.parentBuId = parentBuId;
  }
  
  public String getCurrentBuId() {
    return currentBuId;
  }
  
  public void setCurrentBuId(String currentBuId) {
    this.currentBuId = currentBuId;
  }
  
  public String getGrade() {
    return grade;
  }
  
  public void setGrade(String grade) {
    this.grade = grade;
  }
  
  public String getOwnership() {
    return ownership;
  }
  
  public void setOwnership(String ownership) {
    this.ownership = ownership;
  }
  
  public String getCompetency() {
    return competency;
  }
  
  public void setCompetency(String competency) {
    this.competency = competency;
  }
  
  public String getEmployeeCategory() {
    return employeeCategory;
  }
  
  public void setEmployeeCategory(String employeeCategory) {
    this.employeeCategory = employeeCategory;
  }
  
  public List<SkillResourcePrimaryDTO> getResourcePrimarySkills() {
    return resourcePrimarySkills;
  }
  
  public void setResourcePrimarySkills(List<SkillResourcePrimaryDTO> resourcePrimarySkills) {
    this.resourcePrimarySkills = resourcePrimarySkills;
  }
  
  public List<SkillResourceSecondaryDTO> getResourceSecondarySkills() {
    return resourceSecondarySkills;
  }
  
  public void setResourceSecondarySkills(List<SkillResourceSecondaryDTO> resourceSecondarySkills) {
    this.resourceSecondarySkills = resourceSecondarySkills;
  }
  
  public List<ResourceAllocationDTO> getResourceAllocations() {
    return resourceAllocations;
  }
  
  public void setResourceAllocations(List<ResourceAllocationDTO> resourceAllocations) {
    this.resourceAllocations = resourceAllocations;
  }
  
  public ResourceManagerDTO getImmediateReportingManager() {
    return immediateReportingManager;
  }
  
  public void setImmediateReportingManager(ResourceManagerDTO immediateReportingManager) {
    this.immediateReportingManager = immediateReportingManager;
  }
  
  public ResourceManagerDTO getSeniorReportingManager() {
    return seniorReportingManager;
  }
  
  public void setSeniorReportingManager(ResourceManagerDTO seniorReportingManager) {
    this.seniorReportingManager = seniorReportingManager;
  }
  
  @JsonIgnore
  public String getEmployeeName() {
    if ((getFirstName() != null) && (getMiddleName() != null) && (getLastName() != null)) {
      return getFirstName().concat(" ").concat(getMiddleName()).concat(" ").concat(getLastName());
    }
    return getFirstName().concat(" ").concat(getLastName());
  }
  
  public void setEmployeeName(String employeeName) {
    this.employeeName = employeeName;
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
}
