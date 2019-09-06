package org.yash.rms.dto;

import java.util.List;

public class ResourceDashBoardDTO
{
  List<String> pending;
  List<String> notSubmitted;
  List<String> approved;
  List<String> rejected;
  List<String> timeSheetComplaince;
  
  public ResourceDashBoardDTO() {}
  
  public List<String> getPending() {
    if (pending == null) {
      pending = new java.util.ArrayList();
    }
    return pending;
  }
  
  public void setPending(List<String> pending)
  {
    this.pending = pending;
  }
  
  public List<String> getNotSubmitted()
  {
    if (notSubmitted == null) {
      notSubmitted = new java.util.ArrayList();
    }
    return notSubmitted;
  }
  
  public void setNotSubmitted(List<String> notApproved) {
    notSubmitted = notApproved;
  }
  
  public List<String> getApproved()
  {
    if (approved == null) {
      approved = new java.util.ArrayList();
    }
    return approved;
  }
  
  public void setApproved(List<String> approved) {
    this.approved = approved;
  }
  
  public List<String> getRejected()
  {
    if (rejected == null) {
      rejected = new java.util.ArrayList();
    }
    return rejected;
  }
  
  public void setRejected(List<String> rejected) {
    this.rejected = rejected;
  }
  
  public List<String> getTimeSheetComplaince()
  {
    if (timeSheetComplaince == null) {
      timeSheetComplaince = new java.util.ArrayList();
    }
    return timeSheetComplaince;
  }
  
  public void setTimeSheetComplaince(List<String> timeSheetComplaince) {
    this.timeSheetComplaince = timeSheetComplaince;
  }
}
