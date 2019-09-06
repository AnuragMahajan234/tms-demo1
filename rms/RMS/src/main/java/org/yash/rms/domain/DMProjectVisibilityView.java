package org.yash.rms.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="vw_dm_project_visibilty")
public class DMProjectVisibilityView implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="employee_id")
	private Integer employeeId;
	
	@Id
	@Column(name="project_name")
	private String projectName;
	
	
	@Id
	@Column(name="alloc_end_date")
	private Date allocEndDate;
	
	@Id
	@Column(name="offshore_del_mgr")
	private Integer offshoreDelMgr;

	@Id
	@Column(name="delivery_mgr")
	private Integer deliveryMgr;
	
	@Id
	@Column(name="project_end_date")
	private Date projectEndDate;

	public Integer getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public Date getAllocEndDate() {
		return allocEndDate;
	}

	public void setAllocEndDate(Date allocEndDate) {
		this.allocEndDate = allocEndDate;
	}

	public Integer getOffshoreDelMgr() {
		return offshoreDelMgr;
	}

	public void setOffshoreDelMgr(Integer offshoreDelMgr) {
		this.offshoreDelMgr = offshoreDelMgr;
	}

	public Date getProjectEndDate() {
		return projectEndDate;
	}

	public void setProjectEndDate(Date projectEndDate) {
		this.projectEndDate = projectEndDate;
	}
	public Integer getDeliveryMgr() {
		return deliveryMgr;
	}

	public void setDeliveryMgr(Integer deliveryMgr) {
		this.deliveryMgr = deliveryMgr;
	}
	
}
