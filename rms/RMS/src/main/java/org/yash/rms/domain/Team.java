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

import org.codehaus.jackson.annotate.JsonBackReference;
import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

@Entity
@Table(name="team")
@NamedQueries({
	
	@NamedQuery(name = Team.DELETE_TEAM_BASED_ON_ID, query = Team.QUERY_TEAM_DELETE_BASED_ON_ID) })

public class Team  {
	
	public final static String DELETE_TEAM_BASED_ON_ID="DELETE_TEAM_BASED_ON_ID";
	public final static String QUERY_TEAM_DELETE_BASED_ON_ID="DELETE Team b where b.id = :id";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="name")
	private String teamName;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "project_manager", referencedColumnName = "employee_id")
	@JsonBackReference
	private Resource projectManager;
	
	public Resource getProjectManager() {
		return projectManager;
	}
	public void setProjectManager(Resource projectManager) {
		this.projectManager = projectManager;
	}
	@Column(name="created_id", updatable = false)
	private String createdId;
	
	@Column(name="creation_timestamp",updatable = false, insertable = true)
	private java.util.Date creationTimeStamp;
	
	@Column(name="lastupdated_id")
	private String lastUpdatedId;
	
	@Column(name="lastupdated_timestamp")
	private java.util.Date lastUpdatedTimeStamp;

	public String getCreatedId() {
		return createdId;
	}
	public void setCreatedId(String createdId) {
		this.createdId = createdId;
	}
	public java.util.Date getCreationTimeStamp() {
		return creationTimeStamp;
	}
	public void setCreationTimeStamp(java.util.Date creationTimeStamp) {
		this.creationTimeStamp = creationTimeStamp;
	}
	public String getLastUpdatedId() {
		return lastUpdatedId;
	}
	public void setLastUpdatedId(String lastUpdatedId) {
		this.lastUpdatedId = lastUpdatedId;
	}
	public java.util.Date getLastUpdatedTimeStamp() {
		return lastUpdatedTimeStamp;
	}
	public void setLastUpdatedTimeStamp(java.util.Date lastUpdatedTimeStamp) {
		this.lastUpdatedTimeStamp = lastUpdatedTimeStamp;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTeamName() {
		return teamName;
	}
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
	

	
}
