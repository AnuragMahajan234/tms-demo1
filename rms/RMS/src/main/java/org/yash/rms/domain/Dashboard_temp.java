package org.yash.rms.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "dashboard_temp")
public class Dashboard_temp {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)

	@Column(name = "id")
	private Integer id;

	@Column(name = "project_bg_bu")
	private String project_bg_bu;

	@Column(name = "project_id")
	private Integer project_id;

	@Column(name = "project_name")
	private String project_name;

	@Column(name = "billable_resources")
	private Integer billable_resources;

	@Column(name = "non_billable_resources")
	private Integer non_billable_resources;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getProject_bg_bu() {
		return project_bg_bu;
	}

	public void setProject_bg_bu(String project_bg_bu) {
		this.project_bg_bu = project_bg_bu;
	}

	public Integer getProject_id() {
		return project_id;
	}

	public void setProject_id(Integer project_id) {
		this.project_id = project_id;
	}

	public String getProject_name() {
		return project_name;
	}

	public void setProject_name(String project_name) {
		this.project_name = project_name;
	}

	public Integer getBillable_resources() {
		return billable_resources;
	}

	public void setBillable_resources(Integer billable_resources) {
		this.billable_resources = billable_resources;
	}

	public Integer getNon_billable_resources() {
		return non_billable_resources;
	}

	public void setNon_billable_resources(Integer non_billable_resources) {
		this.non_billable_resources = non_billable_resources;
	}

}
