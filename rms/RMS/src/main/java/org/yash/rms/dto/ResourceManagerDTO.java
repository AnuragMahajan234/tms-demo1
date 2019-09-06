package org.yash.rms.dto;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;



public class ResourceManagerDTO
{
  @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
  private Integer employeeId;
  @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
  private String firstName;
  @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
  private String middleName;
  @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
  private String lastName;
  private String yashEmployeeId;
  private String employeeName;
  private String designation;
  
  public ResourceManagerDTO() {}
  
  public Integer getEmployeeId()
  {
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
  
  public String getDesignation() {
    return designation;
  }
  
  public void setDesignation(String designation) {
    this.designation = designation;
  }
  
  public String getMiddleName() {
    return middleName;
  }
  
  public void setMiddleName(String middleName) {
    this.middleName = middleName;
  }
  
  public String getEmployeeName() {
    return employeeName;
  }
  
  public void setEmployeeName(String employeeName) {
    this.employeeName = employeeName;
  }
  
  public String getYashEmployeeId() {
    return yashEmployeeId;
  }
  
  public void setYashEmployeeId(String yashEmployeeId) {
    this.yashEmployeeId = yashEmployeeId;
  }
}
