package org.yash.rms.dto;

import java.util.Date;

public class AllocationTypeDTO {
  private Integer id;
  private Integer priority;
  private String allocationType;
  private boolean isDefault;
  private String createdId;
  private Date creationTimeStamp;
  private String lastUpdatedId;
  private Date lastUpdatedTimeStamp;
  private String isActiveForRRF;
  private String bghMandatoryFlag;
  private String aliasAllocationName;
  
  public AllocationTypeDTO() {}
  
  public Integer getId() {
    return id;
  }
  
  public void setId(Integer id)
  {
    this.id = id;
  }
  
  public Integer getPriority()
  {
    return priority;
  }
  
  public void setPriority(Integer priority)
  {
    this.priority = priority;
  }
  
  public String getAllocationType() {
    return allocationType;
  }
  
  public void setAllocationType(String allocationType) {
    this.allocationType = allocationType;
  }
  
  public boolean isDefault() {
    return isDefault;
  }
  
  public void setDefault(boolean isDefault) {
    this.isDefault = isDefault;
  }
  
  public String getCreatedId() {
    return createdId;
  }
  
  public void setCreatedId(String createdId) {
    this.createdId = createdId;
  }
  
  public Date getCreationTimeStamp() {
    return creationTimeStamp;
  }
  
  public void setCreationTimeStamp(Date creationTimeStamp) {
    this.creationTimeStamp = creationTimeStamp;
  }
  
  public String getLastUpdatedId() {
    return lastUpdatedId;
  }
  
  public void setLastUpdatedId(String lastUpdatedId) {
    this.lastUpdatedId = lastUpdatedId;
  }
  
  public Date getLastUpdatedTimeStamp() {
    return lastUpdatedTimeStamp;
  }
  
  public void setLastUpdatedTimeStamp(Date lastUpdatedTimeStamp) {
    this.lastUpdatedTimeStamp = lastUpdatedTimeStamp;
  }
  
  public String getIsActiveForRRF() {
    return isActiveForRRF;
  }
  
  public void setIsActiveForRRF(String isActiveForRRF) {
    this.isActiveForRRF = isActiveForRRF;
  }
  
  public String getBghMandatoryFlag()
  {
    return bghMandatoryFlag;
  }
  
  public void setBghMandatoryFlag(String bghMandatoryFlag) {
    this.bghMandatoryFlag = bghMandatoryFlag;
  }
  
  public String getAliasAllocationName() {
    return aliasAllocationName;
  }
  
  public void setAliasAllocationName(String aliasAllocationName) {
    this.aliasAllocationName = aliasAllocationName;
  }
}
