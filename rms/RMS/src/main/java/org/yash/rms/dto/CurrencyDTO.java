package org.yash.rms.dto;

import java.util.Date;

public class CurrencyDTO {
  private Integer id;
  private String currencyName;
  private String createdId;
  private Date creationTimestamp;
  private String lastUpdatedId;
  private Date lastUpdatedTimestamp;
  
  public CurrencyDTO() {}
  
  public Integer getId() {
    return id;
  }
  
  public void setId(Integer id) {
    this.id = id;
  }
  
  public String getCurrencyName() {
    return currencyName;
  }
  
  public void setCurrencyName(String currencyName) {
    this.currencyName = currencyName;
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
  
  public Date getLastupdatedTimestamp() {
    return lastUpdatedTimestamp;
  }
  
  public void setLastupdatedTimestamp(Date lastupdatedTimestamp) {
    lastUpdatedTimestamp = lastupdatedTimestamp;
  }
}
