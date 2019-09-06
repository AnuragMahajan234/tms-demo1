package org.yash.rms.dto;

public class OwnershipDTO {
  private Integer id;
  private String ownershipName;
  
  public OwnershipDTO() {}
  
  public Integer getId() {
    return id;
  }
  
  public void setId(Integer id)
  {
    this.id = id;
  }
  
  public String getOwnershipName() {
    return ownershipName;
  }
  
  public void setOwnershipName(String ownershipName) {
    this.ownershipName = ownershipName;
  }
}
