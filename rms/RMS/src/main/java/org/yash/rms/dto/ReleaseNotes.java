package org.yash.rms.dto;

import java.util.Date;

public class ReleaseNotes {
  private int id;
  private Date releaseDate;
  private String releaseNumber;
  private String description;
  private String type;
  
  public ReleaseNotes() {}
  
  public int getId() {
    return id;
  }
  
  public void setId(int id) { this.id = id; }
  
  public Date getReleaseDate() {
    return releaseDate;
  }
  
  public void setReleaseDate(Date releaseDate) { this.releaseDate = releaseDate; }
  
  public String getReleaseNumber() {
    return releaseNumber;
  }
  
  public void setReleaseNumber(String releaseNumber) { this.releaseNumber = releaseNumber; }
  
  public String getDescription() {
    return description;
  }
  
  public void setDescription(String description) { this.description = description; }
  
  public String getType() {
    return type;
  }
  
  public void setType(String type) { this.type = type; }
}
