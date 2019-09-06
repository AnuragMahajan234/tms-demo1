package org.yash.rms.dto;

public class OrgHierarchyDTO {
  private Integer id;
  private OrgHierarchyDTO parentId;
  private String name;
  private String description;
  private boolean active;
  private Character user;
  private String createdId;
  private ResourceDTO employeeId;
  
  public OrgHierarchyDTO() {}
  
  public Integer getId() {
    return id;
  }
  
  public void setId(Integer id) {
    this.id = id;
  }
  
  public String getName() {
    return name;
  }
  
  public void setName(String name) {
    this.name = name;
  }
  
  public OrgHierarchyDTO getParentId() {
    return parentId;
  }
  
  public void setParentId(OrgHierarchyDTO parentId) {
    this.parentId = parentId;
  }
  
  public String getDescription() {
    return description;
  }
  
  public void setDescription(String description) {
    this.description = description;
  }
  
  public boolean isActive() {
    return active;
  }
  
  public void setActive(boolean active) {
    this.active = active;
  }
  
  public Character getUser() {
    return user;
  }
  
  public void setUser(Character user) {
    this.user = user;
  }
  
  public String getCreatedId() {
    return createdId;
  }
  
  public void setCreatedId(String createdId) {
    this.createdId = createdId;
  }
  
  public ResourceDTO getEmployeeId() {
    return employeeId;
  }
  
  public void setEmployeeId(ResourceDTO employeeId) {
    this.employeeId = employeeId;
  }
}
