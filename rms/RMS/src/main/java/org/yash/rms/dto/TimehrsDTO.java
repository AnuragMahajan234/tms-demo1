package org.yash.rms.dto;

import java.util.Date;

public class TimehrsDTO {
  private Integer id;
  private Date weekEndingDate;
  private Double plannedHrs;
  private Double billedHrs;
  private String remarks;
  private ResourceDTO resourceId;
  private ResourceAllocationDTO resourceAllocation;
  private String createdId;
  private Date creationTimestamp;
  private String lastUpdatedId;
  private Date lastupdatedTimestamp;
  
  public TimehrsDTO() {}
  
  public Integer getId() {
    return id;
  }
  
  public void setId(Integer id)
  {
    this.id = id;
  }
  
  public Date getWeekEndingDate()
  {
    return weekEndingDate;
  }
  
  public void setWeekEndingDate(Date weekEndingDate)
  {
    this.weekEndingDate = weekEndingDate;
  }
  
  public Double getPlannedHrs()
  {
    return plannedHrs;
  }
  
  public void setPlannedHrs(Double plannedHrs)
  {
    this.plannedHrs = plannedHrs;
  }
  
  public Double getBilledHrs()
  {
    return billedHrs;
  }
  
  public void setBilledHrs(Double billedHrs)
  {
    this.billedHrs = billedHrs;
  }
  
  public String getRemarks()
  {
    return remarks;
  }
  
  public void setRemarks(String remarks)
  {
    this.remarks = remarks;
  }
  
  public ResourceDTO getResourceId()
  {
    return resourceId;
  }
  
  public void setResourceId(ResourceDTO resourceId)
  {
    this.resourceId = resourceId;
  }
  
  public ResourceAllocationDTO getResourceAllocation()
  {
    return resourceAllocation;
  }
  
  public void setResourceAllocation(ResourceAllocationDTO resourceAllocation)
  {
    this.resourceAllocation = resourceAllocation;
  }
  
  public String getCreatedId()
  {
    return createdId;
  }
  
  public void setCreatedId(String createdId)
  {
    this.createdId = createdId;
  }
  
  public Date getCreationTimestamp()
  {
    return creationTimestamp;
  }
  
  public void setCreationTimestamp(Date creationTimestamp)
  {
    this.creationTimestamp = creationTimestamp;
  }
  
  public String getLastUpdatedId()
  {
    return lastUpdatedId;
  }
  
  public void setLastUpdatedId(String lastUpdatedId)
  {
    this.lastUpdatedId = lastUpdatedId;
  }
  
  public Date getLastupdatedTimestamp()
  {
    return lastupdatedTimestamp;
  }
  
  public void setLastupdatedTimestamp(Date lastupdatedTimestamp)
  {
    this.lastupdatedTimestamp = lastupdatedTimestamp;
  }
}
