package org.yash.rms.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@SuppressWarnings("serial")
@Entity
@Table(name="mails_configuration")
public class MailConfiguration {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
	private int id; 
	
	 @ManyToOne(fetch = FetchType.LAZY)
	   @JoinColumn(name = "project_id", referencedColumnName = "id")
	 
	 private Project projectId;
	 
	 @ManyToOne(fetch = FetchType.LAZY)
	   @JoinColumn(name = "confg_id", referencedColumnName = "id")
	 
	 private ConfigurationCategory confgId;
	 
	 @ManyToOne(fetch = FetchType.LAZY)
	   @JoinColumn(name = "role_id", referencedColumnName = "id")
	 
	 private Roles roleId;
	 
	 
	 @Column(name = "mail_to")
		@NotNull
		private boolean to;
	 
	 @Column(name = "mail_cc")
		@NotNull
		private boolean cc;
	 
	 @Column(name = "mail_bcc")
		@NotNull
		private boolean bcc;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Project getProjectId() {
		return projectId;
	}

	public void setProjectId(Project projectId) {
		this.projectId = projectId;
	}

	public ConfigurationCategory getConfgId() {
		return confgId;
	}

	public void setConfgId(ConfigurationCategory confgId) {
		this.confgId = confgId;
	}

	public Roles getRoleId() {
		return roleId;
	}

	public void setRoleId(Roles roleId) {
		this.roleId = roleId;
	}

	public boolean isTo() {
		return to;
	}

	public void setTo(boolean to) {
		this.to = to;
	}

	public boolean isCc() {
		return cc;
	}

	public void setCc(boolean cc) {
		this.cc = cc;
	}

	public boolean isBcc() {
		return bcc;
	}

	public void setBcc(boolean bcc) {
		this.bcc = bcc;
	}
	 
	 
	

}
