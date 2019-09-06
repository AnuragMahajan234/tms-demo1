package org.yash.rms.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;

@SuppressWarnings("serial")
@Entity
@Table(name = "skills")
@NamedQueries({
		@NamedQuery(name = Skills.SEARCH_SKILLS_BASED_ON_ID, query = Skills.QUERY_FOR_SEARCH_SKILLS_BASED_ON_ID),
		@NamedQuery(name = Skills.DELETE_SKILLS_BASED_ON_ID, query = Skills.QUERY_FOR_DELETE_SKILLS_BASED_ON_ID) })
public class Skills implements Serializable{
	public final static String SEARCH_SKILLS_BASED_ON_ID = "SEARCH_SKILLS_BASED_ON_ID";
	public final static String QUERY_FOR_SEARCH_SKILLS_BASED_ON_ID = "from Skills b where b.id = :id";

	public final static String DELETE_SKILLS_BASED_ON_ID = "DELETE_SKILLS_BASED_ON_ID";
	public final static String QUERY_FOR_DELETE_SKILLS_BASED_ON_ID = "DELETE Skills b where b.id = :id";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "skill", length = 256)
	@NotNull
	private String skill;

	@Column(name = "skill_type", length = 256)
	@NotNull
	private String skillType;
	@Column(name = "created_id", updatable = false)
	private String createdId;

	@Column(name = "creation_timestamp", updatable = false, insertable = true)
	private Date creationTimestamp;
	@Column(name = "lastupdated_id")
	private String lastUpdatedId;

	@Column(name = "lastupdated_timestamp")
	private Date lastupdatedTimestamp;

	public String getSkill() {
		return skill;
	}

	public void setSkill(String skill) {
		this.skill = skill;
	}

	public String getSkillType() {
		return skillType;
	}

	public void setSkillType(String skillType) {
		this.skillType = skillType;
	}

	public String getCreatedId() {
		return createdId;
	}

	public void setCreatedId(String createdId) {
		this.createdId = createdId;
	}

	public Date getCreationTimestamp() {
		return creationTimestamp;
	}

	public void setCreationTimestamp(Date creationTimestamp) {
		this.creationTimestamp = creationTimestamp;
	}

	public String getLastUpdatedId() {
		return lastUpdatedId;
	}

	public void setLastUpdatedId(String lastUpdatedId) {
		this.lastUpdatedId = lastUpdatedId;
	}

	public Date getLastupdatedTimestamp() {
		return lastupdatedTimestamp;
	}

	public void setLastupdatedTimestamp(Date lastupdatedTimestamp) {
		this.lastupdatedTimestamp = lastupdatedTimestamp;
	}
}
