package org.yash.rms.domain;

import java.io.Serializable;
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
@Table(name="skill_resource_status_type")

public class RequestRequisitionResourceStatus implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "status_name")
	private String statusName;
	
	@Column(name = "status_type")
	private String statusType;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public String getStatusType() {
		return statusType;
	}

	public void setStatusType(String statusType) {
		this.statusType = statusType;
	}

	public static String toJsonString(List<RequestRequisitionResourceStatus> reportStatusList) {
		return new JSONSerializer()
				.include("id", "statusName").exclude("*").transform(new DateTransformer(Constants.DATE_PATTERN_4), Date.class).serialize(reportStatusList);
		
	}
}
