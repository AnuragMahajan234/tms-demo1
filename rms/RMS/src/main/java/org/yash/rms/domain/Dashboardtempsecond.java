package org.yash.rms.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "dashboard_temp_second")
public class Dashboardtempsecond {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name = "project_bg_bu")
	private String project_bg_bu;
	
	@Column(name = "project_id")
	private Integer project_id;
	
	@Column(name = "project_name")
	private String project_name;
	
	@Column(name = "manager_id")
	private Integer manager_id;
	
	@Column(name = "actual_hrs")
	private Long actual_hrs;
	
	@Column(name = "billing_hrs")
	private Long billing_hrs;
	
	@Column(name = "non_billing_hrs")
	private Long non_billing_hrs;

	public int getId() {
		return id;
	}

	public void setId(int id) {
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

	public Integer getManager_id() {
		return manager_id;
	}

	public void setManager_id(Integer manager_id) {
		this.manager_id = manager_id;
	}

	public Long getActual_hrs() {
		return actual_hrs;
	}

	public void setActual_hrs(Long actual_hrs) {
		this.actual_hrs = actual_hrs;
	}

	public Long getBilling_hrs() {
		return billing_hrs;
	}

	public void setBilling_hrs(Long billing_hrs) {
		this.billing_hrs = billing_hrs;
	}

	public Long getNon_billing_hrs() {
		return non_billing_hrs;
	}

	public void setNon_billing_hrs(Long non_billing_hrs) {
		this.non_billing_hrs = non_billing_hrs;
	}
}
