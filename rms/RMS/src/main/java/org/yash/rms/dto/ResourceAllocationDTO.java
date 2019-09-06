package org.yash.rms.dto;

import java.util.Date;
import java.util.Set;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;

public class ResourceAllocationDTO
{
  @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
  private Integer id;
  @JsonSerialize(include=JsonSerialize.Inclusion.NON_DEFAULT)
  private boolean primayProjectIndicator;
  @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
  private Integer allocationSeq;
  @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
  private String allocStartDate;
  @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
  private String allocEndDate;
  @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
  private String allocRemarks;
  @JsonSerialize(include=JsonSerialize.Inclusion.NON_DEFAULT)
  private boolean curProj;
  @JsonSerialize(include=JsonSerialize.Inclusion.NON_DEFAULT)
  private boolean billable;
  @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
  private Integer allocHrs;
  @JsonSerialize(include=JsonSerialize.Inclusion.NON_DEFAULT)
  private boolean behalfManager;
  @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
  private Set<TimehrsDTO> timehrs;
  @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
  private ResourceDTO updatedBy;
  @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
  private ResourceDTO employeeId;
  @JsonProperty("project")
  @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
  private ProjectDTO projectId;
  @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
  private BillingScaleDTO rateId;
  @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
  private OwnershipDTO ownershipId;
  @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
  private AllocationTypeDTO allocationType;
  @JsonSerialize(include=JsonSerialize.Inclusion.NON_DEFAULT)
  private Boolean allocBlock;
  @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
  private Set<UserActivityViewDTO> userActivities;
  @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
  private String projectEndRemarks;
  @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
  private ResourceDTO allocatedBy;
  @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
  private String lastUserActivityDate;
  @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
  private DesignationDTO designation;
  @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
  private GradeDTO grade;
  @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
  private OrgHierarchyDTO buId;
  @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
  private OrgHierarchyDTO currentBuId;
  @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
  private LocationDTO location;
  @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
  private ResourceDTO currentReportingManager;
  @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
  private ResourceDTO currentReportingManagerTwo;
  @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
  private String ownershipTransferDate;
  @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
  private String createdId;
  @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
  private Date creationTimestamp;
  @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
  private String lastUpdatedId;
  @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
  private String lastupdatedTimestamp;
  @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
  private Date resourceAllocationStartDate;
  @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
  private Date resourceAllocationEndDate;
  
  public ResourceAllocationDTO() {}
  
  public Integer getId()
  {
    return id;
  }
  
  public void setId(Integer id) {
    this.id = id;
  }
  
  public Integer getAllocationSeq() {
    return allocationSeq;
  }
  
  public void setAllocationSeq(Integer allocationSeq) {
    this.allocationSeq = allocationSeq;
  }
  
  public String getAllocRemarks() {
    return allocRemarks;
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
  
  public void setAllocRemarks(String allocRemarks) {
    this.allocRemarks = allocRemarks;
  }
  
  public boolean getCurProj() {
    return curProj;
  }
  
  public void setCurProj(boolean curProj) {
    this.curProj = curProj;
  }
  
  public boolean getBillable() {
    return billable;
  }
  
  public void setBillable(boolean billable) {
    this.billable = billable;
  }
  
  public Integer getAllocHrs() {
    return allocHrs;
  }
  
  public void setAllocHrs(Integer allocHrs) {
    this.allocHrs = allocHrs;
  }
  
  public boolean getBehalfManager() {
    return behalfManager;
  }
  
  public void setBehalfManager(boolean behalfManager) {
    this.behalfManager = behalfManager;
  }
  
  public Set<TimehrsDTO> getTimehrs()
  {
    return timehrs;
  }
  
  public void setTimehrs(Set<TimehrsDTO> timehrs)
  {
    this.timehrs = timehrs;
  }
  
  public AllocationTypeDTO getAllocationType()
  {
    return allocationType;
  }
  
  public void setAllocationType(AllocationTypeDTO allocationType)
  {
    this.allocationType = allocationType;
  }
  
  public ResourceDTO getAllocatedBy() {
    return allocatedBy;
  }
  
  public void setAllocatedBy(ResourceDTO allocatedBy) {
    this.allocatedBy = allocatedBy;
  }
  
  public ResourceDTO getUpdatedBy() {
    return updatedBy;
  }
  
  public void setUpdatedBy(ResourceDTO updatedBy) {
    this.updatedBy = updatedBy;
  }
  
  public ResourceDTO getEmployeeId() {
    return employeeId;
  }
  
  public void setEmployeeId(ResourceDTO employeeId) {
    this.employeeId = employeeId;
  }
  
  public ProjectDTO getProjectId() {
    return projectId;
  }
  
  public void setProjectId(ProjectDTO projectId) {
    this.projectId = projectId;
  }
  
  public BillingScaleDTO getRateId() {
    return rateId;
  }
  
  public void setRateId(BillingScaleDTO rateId) {
    this.rateId = rateId;
  }
  
  public OwnershipDTO getOwnershipId() {
    return ownershipId;
  }
  
  public void setOwnershipId(OwnershipDTO ownershipId) {
    this.ownershipId = ownershipId;
  }
  
  public Boolean getAllocBlock() {
    return allocBlock;
  }
  
  public void setAllocBlock(Boolean allocBlock) {
    this.allocBlock = allocBlock;
  }
  
  public Set<UserActivityViewDTO> getUserActivities() {
    return userActivities;
  }
  
  public void setUserActivities(Set<UserActivityViewDTO> userActivities) {
    this.userActivities = userActivities;
  }
  
  public String getLastUserActivityDate() {
    return lastUserActivityDate;
  }
  
  public void setLastUserActivityDate(String lastUserActivityDate) {
    this.lastUserActivityDate = lastUserActivityDate;
  }
  
  public DesignationDTO getDesignation() {
    return designation;
  }
  
  public void setDesignation(DesignationDTO designation) {
    this.designation = designation;
  }
  
  public GradeDTO getGrade() {
    return grade;
  }
  
  public void setGrade(GradeDTO grade) {
    this.grade = grade;
  }
  
  public OrgHierarchyDTO getBuId() {
    return buId;
  }
  
  public void setBuId(OrgHierarchyDTO buId) {
    this.buId = buId;
  }
  
  public OrgHierarchyDTO getCurrentBuId() {
    return currentBuId;
  }
  
  public void setCurrentBuId(OrgHierarchyDTO currentBuId) {
    this.currentBuId = currentBuId;
  }
  
  public LocationDTO getLocation() {
    return location;
  }
  
  public void setLocation(LocationDTO location) {
    this.location = location;
  }
  
  public ResourceDTO getCurrentReportingManager() {
    return currentReportingManager;
  }
  
  public void setCurrentReportingManager(ResourceDTO currentReportingManager) {
    this.currentReportingManager = currentReportingManager;
  }
  
  public ResourceDTO getCurrentReportingManagerTwo() {
    return currentReportingManagerTwo;
  }
  
  public void setCurrentReportingManagerTwo(ResourceDTO currentReportingManagerTwo) {
    this.currentReportingManagerTwo = currentReportingManagerTwo;
  }
  
  public String getOwnershipTransferDate() {
    return ownershipTransferDate;
  }
  
  public void setOwnershipTransferDate(String ownershipTransferDate) {
    this.ownershipTransferDate = ownershipTransferDate;
  }
  
  public String getCreatedId() {
    return createdId;
  }
  
  public void setCreatedId(String createdId) {
    this.createdId = createdId;
  }
  
  public Date getCreationTimestamp() {
    return creationTimestamp;
  }
  
  public void setCreationTimestamp(Date creationTimestamp) {
    this.creationTimestamp = creationTimestamp;
  }
  
  public String getLastUpdatedId() {
    return lastUpdatedId;
  }
  
  public void setLastUpdatedId(String lastUpdatedId) {
    this.lastUpdatedId = lastUpdatedId;
  }
  
  public String getLastupdatedTimestamp() {
    return lastupdatedTimestamp;
  }
  
  public void setLastupdatedTimestamp(String lastupdatedTimestamp) {
    this.lastupdatedTimestamp = lastupdatedTimestamp;
  }
  
  public boolean isPrimayProjectIndicator() {
    return primayProjectIndicator;
  }
  
  public void setPrimayProjectIndicator(boolean primayProjectIndicator) {
    this.primayProjectIndicator = primayProjectIndicator;
  }
  
  public String getProjectEndRemarks() {
    return projectEndRemarks;
  }
  
  public void setProjectEndRemarks(String projectEndRemarks) {
    this.projectEndRemarks = projectEndRemarks;
  }
  
  public Date getResourceAllocationStartDate() {
    return resourceAllocationStartDate;
  }
  
  public void setResourceAllocationStartDate(Date resourceAllocationStartDate) {
    this.resourceAllocationStartDate = resourceAllocationStartDate;
  }
  
  public Date getResourceAllocationEndDate() {
    return resourceAllocationEndDate;
  }
  
  public void setResourceAllocationEndDate(Date resourceAllocationEndDate) {
    this.resourceAllocationEndDate = resourceAllocationEndDate;
  }
}
