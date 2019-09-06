package org.yash.rms.dto;

import java.util.Date;

public class TimesheetSubmissionDTO {
  private Integer id;
  private Integer employeeId;
  private Integer projectId;
  private String projectName;
  private Integer resourceAllocationId;
  private Date weekEndDate;
  private Date weekStartDate;
  private Double totalHours;
  private String status;
  
  public TimesheetSubmissionDTO() {}
  
  public Integer getProjectId() {
    return projectId;
  }
  
  public void setProjectId(Integer projectId) { this.projectId = projectId; }
  
  public String getProjectName() {
    return projectName;
  }
  
  public void setProjectName(String projectName) { this.projectName = projectName; }
  
  public Date getWeekEndDate() {
    return weekEndDate;
  }
  
  public void setWeekEndDate(Date weekEndDate) { this.weekEndDate = weekEndDate; }
  
  public Double getTotalHours() {
    return totalHours;
  }
  
  public void setTotalHours(Double totalHours) { this.totalHours = totalHours; }
  
  public String getStatus() {
    return status;
  }
  
  public void setStatus(String status) { this.status = status; }
  
  public Integer getEmployeeId() {
    return employeeId;
  }
  
  public void setEmployeeId(Integer employeeId) { this.employeeId = employeeId; }
  
  public Date getWeekStartDate()
  {
    return weekStartDate;
  }
  
  public void setWeekStartDate(Date weekStartDate) { this.weekStartDate = weekStartDate; }
  
  public Integer getResourceAllocationId() {
    return resourceAllocationId;
  }
  
  public void setResourceAllocationId(Integer resourceAllocationId) { this.resourceAllocationId = resourceAllocationId; }
  
  public Integer getId() {
    return id;
  }
  
  public void setId(Integer id) { this.id = id; }
}
