package org.yash.rms.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.yash.rms.util.Constants;

import flexjson.JSONSerializer;
import flexjson.transformer.DateTransformer;

@Entity
@Table(name="pdl_email_group")
public class PDLEmailGroup {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="pdl_email_id")
	private String pdlEmailId;
	
	@Column(name="created_by", updatable = false)
	private String created_by;
	
	@Column(name="created_timestamp",updatable = false, insertable = true)
	private java.util.Date createdTimestamp;
	
	@Column(name="modified_by")
	private String modifiedBy;
	
	@Column(name="modified_timestamp")
	private java.util.Date modifiedTimestamp;
	
	@Column(name="active")
	private String active;
	
	@Column(name="pdl_name")
	private String pdl_name;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPdlEmailId() {
		return pdlEmailId;
	}

	public void setPdlEmailId(String pdlEmailId) {
		this.pdlEmailId = pdlEmailId;
	}

	public String getCreated_by() {
		return created_by;
	}

	public void setCreated_by(String created_by) {
		this.created_by = created_by;
	}

	public java.util.Date getCreatedTimestamp() {
		return createdTimestamp;
	}

	public void setCreatedTimestamp(java.util.Date createdTimestamp) {
		this.createdTimestamp = createdTimestamp;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public java.util.Date getModifiedTimestamp() {
		return modifiedTimestamp;
	}

	public void setModifiedTimestamp(java.util.Date modifiedTimestamp) {
		this.modifiedTimestamp = modifiedTimestamp;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getPdlName() {
		return pdl_name;
	}

	public void setPdlName(String pdl_name) {
		this.pdl_name = pdl_name;
	}

	public static String toJsonString(List<PDLEmailGroup> pdlUserList) {
		return new JSONSerializer().include("id","active", "pdl_name", "pdlEmailId").exclude("*")
		        .transform(new DateTransformer(Constants.DATE_PATTERN), Date.class).serialize(pdlUserList);
	}
	
}
