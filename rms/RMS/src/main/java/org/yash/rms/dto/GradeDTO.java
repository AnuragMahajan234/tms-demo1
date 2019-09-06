package org.yash.rms.dto;

public class GradeDTO {
  private Integer id;
  private String grade;
  
  public GradeDTO() {}
  
  public Integer getId() {
    return id;
  }
  
  public void setId(Integer id)
  {
    this.id = id;
  }
  
  public String getGrade() {
    return grade;
  }
  
  public void setGrade(String grade) {
    this.grade = grade;
  }
}
