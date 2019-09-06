package org.yash.rms.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name="skill_resource_status_type")

public class SkillResourceStatusType implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "status_name")
	private String statusName;
	
	@Column(name = "status_type")
	private String statusType;
	
	
	/*@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "skillResource_id")
	private SkillResource skillResourceId;
	
	

	public SkillResource getSkillResourceId() {
		return skillResourceId;
	}

	public void setSkillResourceId(SkillResource skillResourceId) {
		this.skillResourceId = skillResourceId;
	}
*/
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

	
	
}
