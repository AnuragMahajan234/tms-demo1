package org.yash.rms.dto;

import java.util.ArrayList;

public class User
{
  private String username;
  private ArrayList<String> member_of;
  private String mail;
  private String department;
  private String office;
  private String mobile;
  private String manager;
  private String telephone_number;
  private boolean dataFromLDAPExist;
  
  public User() {}
  
  public java.util.List<String> getMember_of() {
    return member_of;
  }
  
  public void setMember_of(ArrayList<String> member_of)
  {
    this.member_of = member_of;
  }
  
  public String getTelephone_number()
  {
    return telephone_number;
  }
  
  public void setTelephone_number(String telephone_number)
  {
    this.telephone_number = telephone_number;
  }
  
  public boolean isDataFromLDAPExist()
  {
    return dataFromLDAPExist;
  }
  
  public void setDataFromLDAPExist(boolean dataFromLDAPExist)
  {
    this.dataFromLDAPExist = dataFromLDAPExist;
  }
  
  public java.util.List<String> getMemberOf()
  {
    return member_of;
  }
  
  public void setMemberOf(ArrayList<String> member_of)
  {
    this.member_of = member_of;
  }
  
  public String getMail()
  {
    return mail;
  }
  
  public void setMail(String mail)
  {
    this.mail = mail;
  }
  
  public String getDepartment()
  {
    return department;
  }
  
  public void setDepartment(String department)
  {
    this.department = department;
  }
  
  public String getOffice()
  {
    return office;
  }
  
  public void setOffice(String office)
  {
    this.office = office;
  }
  
  public String getMobile()
  {
    return mobile;
  }
  
  public void setMobile(String mobile)
  {
    this.mobile = mobile;
  }
  
  public String getManager()
  {
    return manager;
  }
  
  public void setManager(String manager)
  {
    this.manager = manager;
  }
  
  public String getTelephoneNumber()
  {
    return telephone_number;
  }
  
  public void setTelephoneNumber(String telephone_number)
  {
    this.telephone_number = telephone_number;
  }
  
  public String getUsername() { return username; }
  
  public void setUsername(String username) {
    this.username = username;
  }
  
  public static enum USER_DATA
  {
    MEMBER_OF, 
    MAIL, 
    DEPARTMENT, 
    OFFICE, 
    MOBILE, 
    MANAGER, 
    TELEPHONE_NUMBER;
  }
}
