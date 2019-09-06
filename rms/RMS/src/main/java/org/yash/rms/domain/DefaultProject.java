package org.yash.rms.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonBackReference;
/**
 * 
 * @author purva.bhate
 *
 */

@SuppressWarnings("serial")
@Entity
@Table(name = "default_project")
public class DefaultProject implements Serializable {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	@ManyToOne
//	(fetch = FetchType.LAZY)
	@JoinColumn(name = "bu_id", referencedColumnName = "id")
	@JsonBackReference
	private OrgHierarchy orgHierarchy;
	
	@OneToOne
	//(fetch = FetchType.EAGER)
	@JoinColumn(name = "project_id", referencedColumnName = "id")
	@JsonBackReference
	private Project projectId;
	@ManyToOne
	//(fetch = FetchType.LAZY)
		@JoinColumn(name = "allocation_type_id", referencedColumnName = "id")
	    @JsonBackReference
		private AllocationType allocationTypeId;
		
		@Column(name = "creation_timestamp", updatable = false, insertable = true)
		private Date creationTimestamp;

		@Column(name = "lastupdated_id")
		private String lastUpdatedId;
	
		@Column(name = "lastupdated_timestamp")
		private Date lastupdatedTimestamp;
		
		
		@Column(name = "created_id", updatable = false)
		private String createdId;

	public OrgHierarchy getOrgHierarchy() {
		return orgHierarchy;
	}

	public void setOrgHierarchy(OrgHierarchy orgHierarchy) {
		this.orgHierarchy = orgHierarchy;
	}

	public Project getProjectId() {
		return projectId;
	}

	public void setProjectId(Project projectId) {
		this.projectId = projectId;
	}

	public AllocationType getAllocationTypeId() {
		return allocationTypeId;
	}

	public void setAllocationTypeId(AllocationType allocationTypeId) {
		this.allocationTypeId = allocationTypeId;
	}

	public Date getCreationTimestamp() {
		return creationTimestamp;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getLastupdatedTimestamp() {
		return lastupdatedTimestamp;
	}

	public void setLastupdatedTimestamp(Date lastupdatedTimestamp) {
		this.lastupdatedTimestamp = lastupdatedTimestamp;
	}

	public String getCreatedId() {
		return createdId;
	}

	public void setCreatedId(String createdId) {
		this.createdId = createdId;
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

	

	

	


}
