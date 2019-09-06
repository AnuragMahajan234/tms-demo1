package org.yash.rms.dto;

import java.util.Date;

public class BillingScaleDTO {
  private Integer id;
  private Integer billingRate;
  private String createdId;
  private Date creationTimestamp;
  private String lastUpdatedId;
  private Date lastupdatedTimestamp;
  
  public BillingScaleDTO() {}
  
  public Integer getId() {
    return id;
  }
  
  public void setId(Integer id)
  {
    this.id = id;
  }
  
  public Integer getBillingRate()
  {
    return billingRate;
  }
  
  public void setBillingRate(Integer billingRate)
  {
    this.billingRate = billingRate;
  }
  
  public String getCreatedId() {
    return createdId;
  }
  
  public void setCreatedId(String createdId) {
    this.createdId = createdId;
  }
  
  public Date getCreationTimestamp()
  {
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
  
  public Date getLastupdatedTimestamp() {
    return lastupdatedTimestamp;
  }
  
  public void setLastupdatedTimestamp(Date lastupdatedTimestamp) {
    this.lastupdatedTimestamp = lastupdatedTimestamp;
  }
}
