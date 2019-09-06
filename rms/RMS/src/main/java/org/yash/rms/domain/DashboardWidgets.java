package org.yash.rms.domain;

import java.util.Collection;
import java.util.Date;

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
@Table(name = "dashboard_widgets")
public class DashboardWidgets {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
	private Integer id;
	
	@Column(name = "employee_id")
	private Integer employee_id;
	
	@Column(name = "project_id")
	private Integer project_id;
	
	@Column(name = "week_end_date")
	private Date week_end_date;
	
	@Column(name = "status")
	private String status;
	
	@Column(name = "employee_count")
	private Integer employee_count;
	
	@Column(name = "manager_id")
	private Integer manager_id;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getEmployee_id() {
		return employee_id;
	}

	public void setEmployee_id(Integer employee_id) {
		this.employee_id = employee_id;
	}

	public Integer getProject_id() {
		return project_id;
	}

	public void setProject_id(Integer project_id) {
		this.project_id = project_id;
	}

	public Date getWeek_end_date() {
		return week_end_date;
	}

	public void setWeek_end_date(Date week_end_date) {
		this.week_end_date = week_end_date;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getEmployee_count() {
		return employee_count;
	}

	public void setEmployee_count(Integer employee_count) {
		this.employee_count = employee_count;
	}

	public Integer getManager_id() {
		return manager_id;
	}

	public void setManager_id(Integer manager_id) {
		this.manager_id = manager_id;
	}
	
	public static String toJsonArray(
			Collection<org.yash.rms.domain.DashboardWidgets> collection) {
		return new JSONSerializer()
				.include("status","week_end_date", "employee_count")
				.exclude("*")
				.transform(new DateTransformer(Constants.DATE_PATTERN),
						Date.class).serialize(collection);
	}
}
