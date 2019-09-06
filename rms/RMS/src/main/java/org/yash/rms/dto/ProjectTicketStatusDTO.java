package org.yash.rms.dto;

import flexjson.JSONSerializer;

public class ProjectTicketStatusDTO
{
  private Integer id;
  private String status;
  private int active;
  
  public ProjectTicketStatusDTO() {}
  
  public Integer getId() {
    return id;
  }
  
  public void setId(Integer id) { this.id = id; }
  
  public String getStatus() {
    return status;
  }
  
  public void setStatus(String status) { this.status = status; }
  

  public int getActive()
  {
    return active;
  }
  
  public void setActive(int active) { this.active = active; }
  

  public static String toJsonArray(java.util.Collection<ProjectTicketStatusDTO> collection)
  {
    return 
    

      new JSONSerializer().include(new String[] { "id", "status", "active" }).exclude(new String[] { "*" }).serialize(collection);
  }
}
