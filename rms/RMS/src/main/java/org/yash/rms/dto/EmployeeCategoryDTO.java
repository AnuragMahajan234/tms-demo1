package org.yash.rms.dto;

public class EmployeeCategoryDTO {
  private int id;
  private String employeeCategoryName;
  
  public EmployeeCategoryDTO() {}
  
  public int getId() {
    return id;
  }
  
  public void setId(int id) {
    this.id = id;
  }
  
  public String getEmployeeCategoryName() {
    return employeeCategoryName;
  }
  
  public void setEmployeeCategoryName(String employeeCategoryName) {
    this.employeeCategoryName = employeeCategoryName;
  }
}
