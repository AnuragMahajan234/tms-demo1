package org.yash.rms.dto;

public class UserNotification {
  private Integer id;
  private String msg;
  private boolean isRead;
  private String msgType;
  
  public UserNotification() {}
  
  public Integer getId() {
    return id;
  }
  
  public void setId(Integer id) {
    this.id = id;
  }
  
  public String getMsg() {
    return msg;
  }
  
  public void setMsg(String msg) {
    this.msg = msg;
  }
  
  public boolean isRead() {
    return isRead;
  }
  
  public void setRead(boolean isRead) {
    this.isRead = isRead;
  }
  
  public String getMsgType() {
    return msgType;
  }
  
  public void setMsgType(String msgType) {
    this.msgType = msgType;
  }
}
