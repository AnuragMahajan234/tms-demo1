package org.yash.rms.dto;

import flexjson.JSONSerializer;
import flexjson.transformer.DateTransformer;
import java.io.Serializable;
import java.util.Collection;

public class Status implements Serializable
{
  private static final long serialVersionUID = -3582961457660652132L;
  private String status;
  
  public Status() {}
  
  public String getStatus()
  {
    return status;
  }
  
  public void setStatus(String status) {
    this.status = status;
  }
  
  public static String toJsonArray(Collection<Status> collection)
  {
    return 
    


      new JSONSerializer().include(new String[] { "status" }).exclude(new String[] { "*" }).transform(new DateTransformer("MM/dd/yyyy"), new Class[] { java.util.Date.class }).serialize(collection);
  }
}
