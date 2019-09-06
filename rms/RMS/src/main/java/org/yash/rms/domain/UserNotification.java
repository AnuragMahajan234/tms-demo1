package org.yash.rms.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.yash.rms.domain.Resource;

@SuppressWarnings("serial")
@Entity
@Table(name = "user_notification")
public class UserNotification implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@ManyToOne()
	@JsonIgnore
	@JoinColumn(name = "employee_id", referencedColumnName = "employee_id", nullable = false)
	private Resource employeeId;

	@Column(name = "msg", length = 256)
	@NotNull
	private String msg;

	@Column(name = "is_read")
	@NotNull
	private boolean isRead;

	@Column(name = "msg_type", length = 256)
	@NotNull
	private String msgType;

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Resource getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Resource employeeId) {
		this.employeeId = employeeId;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public boolean isIsRead() {
		return isRead;
	}

	public void setIsRead(boolean isRead) {
		this.isRead = isRead;
	}

	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

	public boolean isRead() {
		return isRead;
	}

	public void setRead(boolean isRead) {
		this.isRead = isRead;
	}

}
