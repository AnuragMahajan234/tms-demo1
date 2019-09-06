package org.yash.rms.dto;

import java.util.Date;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;





public class DesignationDTO
{
  @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
  private Integer id;
  @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
  private String designationName;
  @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
  private String createdId;
  @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
  private Date creationTimestamp;
  @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
  private String lastUpdatedId;
  @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
  private Date lastupdatedTimestamp;
  
  public DesignationDTO() {}
  
  public Integer getId()
  {
    return id;
  }
  
  public void setId(Integer id) {
    this.id = id;
  }
  
  public String getDesignationName() {
    return designationName;
  }
  
  public void setDesignationName(String designationName) {
    this.designationName = designationName;
  }
  
  public String getCreatedId() {
    return createdId;
  }
  
  public void setCreatedId(String createdId) {
    this.createdId = createdId;
  }
  
  public Date getCreationTimestamp() {
    return creationTimestamp;
  }
  
  public void setCreationTimestamp(Date creationTimestamp) {
    this.creationTimestamp = creationTimestamp;
  }
  
  public String getLastUpdatedId() {
    return lastUpdatedId;
  }
  
  public void setLastUpdatedId(String lastUpdatedId) {
    this.lastUpdatedId = lastUpdatedId;
  }
  
  public Date getLastupdatedTimestamp() {
    return lastupdatedTimestamp;
  }
  
  public void setLastupdatedTimestamp(Date lastupdatedTimestamp) {
    this.lastupdatedTimestamp = lastupdatedTimestamp;
  }
}
