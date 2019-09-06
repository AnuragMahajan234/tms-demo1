package org.yash.rms.dto;

import flexjson.JSONSerializer;
import java.util.Date;

public class CustomerDTO
{
  private int id;
  private String code;
  private String customerName;
  private String geography;
  private String accountManager;
  private java.math.BigDecimal accountManagerContactNumber;
  private String customerAddress;
  private String customerEmail;
  private String createdId;
  private Date creationTimestamp;
  private String lastUpdatedId;
  private Date lastupdatedTimestamp;
  private java.util.List<CustomerGroupDTO> custGroups;
  
  public CustomerDTO() {}
  
  public int getId()
  {
    return id;
  }
  
  public void setId(int id) { this.id = id; }
  
  public String getCode()
  {
    return code;
  }
  
  public void setCode(String code) { this.code = code; }
  
  public String getCustomerName() {
    return customerName;
  }
  
  public void setCustomerName(String customerName) { this.customerName = customerName; }
  
  public String getCustomerEmail() {
    return customerEmail;
  }
  
  public void setCustomerEmail(String customerEmail) { this.customerEmail = customerEmail; }
  
  public String getGeography() {
    return geography;
  }
  
  public void setGeography(String geography) { this.geography = geography; }
  

  public java.math.BigDecimal getAccountManagerContactNumber()
  {
    return accountManagerContactNumber;
  }
  
  public void setAccountManagerContactNumber(java.math.BigDecimal accountManagerContactNumber) { this.accountManagerContactNumber = accountManagerContactNumber; }
  
  public String getCustomerAddress() {
    return customerAddress;
  }
  
  public void setCustomerAddress(String customerAddress) { this.customerAddress = customerAddress; }
  
  public String getCreatedId()
  {
    return createdId;
  }
  
  public void setCreatedId(String createdId) { this.createdId = createdId; }
  
  public Date getCreationTimestamp() {
    return creationTimestamp;
  }
  
  public void setCreationTimestamp(Date creationTimestamp) { this.creationTimestamp = creationTimestamp; }
  
  public String getLastUpdatedId() {
    return lastUpdatedId;
  }
  
  public void setLastUpdatedId(String lastUpdatedId) { this.lastUpdatedId = lastUpdatedId; }
  
  public Date getLastupdatedTimestamp() {
    return lastupdatedTimestamp;
  }
  
  public void setLastupdatedTimestamp(Date lastupdatedTimestamp) { this.lastupdatedTimestamp = lastupdatedTimestamp; }
  
  public String toJson()
  {
    return new JSONSerializer().exclude(new String[] { "*.class" }).include(new String[] { "customerPoes", "accountManager.firstName", "accountManager.lastName" }).serialize(this);
  }
  
  public String getAccountManager() { return accountManager; }
  
  public void setAccountManager(String accountManager) {
    this.accountManager = accountManager;
  }
  
  public java.util.List<CustomerGroupDTO> getCustGroups() { return custGroups; }
  
  public void setCustGroups(java.util.List<CustomerGroupDTO> custGroups) {
    this.custGroups = custGroups;
  }
}
