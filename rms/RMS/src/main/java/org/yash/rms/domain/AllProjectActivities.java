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

@Entity
@Table(name = "all_timesheet_activity")
public class AllProjectActivities {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "projectid", referencedColumnName = "id")
	private Project projectid;
	
	
	@Column(name = "actvty_type")
	private String activityType;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "custom_activity_id", referencedColumnName = "id")
	private CustomActivity customActivityid;
	
	public CustomActivity getCustomActivityid() {
		return customActivityid;
	}

	public void setCustomActivityid(CustomActivity customActivityid) {
		this.customActivityid = customActivityid;
	}

	@Column(name="deactive_flag")
	private int deactiveFlag;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "default_activity_id", referencedColumnName = "id")
	private Activity defaultActivityid;
	

	public Activity getDefaultActivityid() {
		return defaultActivityid;
	}

	public void setDefaultActivityid(Activity defaultActivityid) {
		this.defaultActivityid = defaultActivityid;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Project getProjectid() {
		return projectid;
	}

	public void setProjectid(Project projectid) {
		this.projectid = projectid;
	}

	public String getActivityType() {
		return activityType;
	}

	public void setActivityType(String activityType) {
		this.activityType = activityType;
	}

	public int getDeactiveFlag() {
		return deactiveFlag;
	}

	public void setDeactiveFlag(int deactiveFlag) {
		this.deactiveFlag = deactiveFlag;
	}


	
	
	
	
	

}
