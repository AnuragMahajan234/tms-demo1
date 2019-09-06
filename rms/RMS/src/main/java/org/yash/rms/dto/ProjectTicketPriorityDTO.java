package org.yash.rms.dto;

import flexjson.JSONSerializer;

public class ProjectTicketPriorityDTO
{
  private Integer id;
  private String priority;
  private int active;
  
  public ProjectTicketPriorityDTO() {}
  
  public Integer getId() {
    return id;
  }
  
  public void setId(Integer id) { this.id = id; }
  
  public String getPriority() {
    return priority;
  }
  
  public void setPriority(String priority) { this.priority = priority; }
  

  public int getActive()
  {
    return active;
  }
  
  public void setActive(int active) { this.active = active; }
  

  public static String toJsonArray(java.util.Collection<ProjectTicketPriorityDTO> collection)
  {
    return 
    

      new JSONSerializer().include(new String[] { "id", "priority", "active" }).exclude(new String[] { "*" }).serialize(collection);
  }
}
