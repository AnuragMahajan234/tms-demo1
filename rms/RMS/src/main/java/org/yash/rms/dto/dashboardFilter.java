package org.yash.rms.dto;

import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

public class dashboardFilter
{
  private static final long serialVersionUID = -1498114689525438135L;
  private String priority;
  private String module;
  private String reviewer;
  private String landscape;
  private String region;
  private String assignee;
  @DateTimeFormat(pattern="yyyy-MM-dd")
  private Date fromTime;
  @DateTimeFormat(pattern="yyyy-MM-dd")
  private Date toTime;
  
  public dashboardFilter() {}
  
  public String getPriority()
  {
    return priority;
  }
  
  public void setPriority(String priority) { this.priority = priority; }
  
  public String getModule() {
    return module;
  }
  
  public void setModule(String module) { this.module = module; }
  
  public String getReviewer() {
    return reviewer;
  }
  
  public void setReviewer(String reviewer) { this.reviewer = reviewer; }
  
  public String getLandscape() {
    return landscape;
  }
  
  public void setLandscape(String landscape) { this.landscape = landscape; }
  
  public String getRegion() {
    return region;
  }
  
  public void setRegion(String region) { this.region = region; }
  
  public String getAssignee() {
    return assignee;
  }
  
  public void setAssignee(String assignee) { this.assignee = assignee; }
  
  public Date getFromTime() {
    return fromTime;
  }
  
  public void setFromTime(Date fromTime) { this.fromTime = fromTime; }
  
  public Date getToTime() {
    return toTime;
  }
  
  public void setToTime(Date toTime) { this.toTime = toTime; }
}
