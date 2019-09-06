package org.yash.rms.dto;

import java.util.List;

public class TimeSheetApprovalDTO {
  private List<TimehrsViewDTO> timehrsViews;
  private Character timeSheetStatus;
  private String resourceStatus;
  private boolean checkAllocForManager;
  
  public TimeSheetApprovalDTO() {}
  
  public List<TimehrsViewDTO> getTimehrsViews() {
    return timehrsViews;
  }
  
  public void setTimehrsViews(List<TimehrsViewDTO> timehrsViews) {
    this.timehrsViews = timehrsViews;
  }
  
  public Character getTimeSheetStatus() { return timeSheetStatus; }
  
  public void setTimeSheetStatus(Character timeSheetStatus) {
    this.timeSheetStatus = timeSheetStatus;
  }
  
  public boolean isCheckAllocForManager() {
    return checkAllocForManager;
  }
  
  public void setCheckAllocForManager(boolean checkAllocForManager) { this.checkAllocForManager = checkAllocForManager; }
  
  public String getResourceStatus()
  {
    return resourceStatus;
  }
  
  public void setResourceStatus(String resourceStatus) { this.resourceStatus = resourceStatus; }
}
