package org.yash.rms.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;
@Entity
@Table(name="custom_activity")

@NamedQueries({
	
	@NamedQuery(name = CustomActivity.DELETE_CUSTOM_ACTIVITY_BASED_ON_ID, query = CustomActivity.QUERY_CUSTOM_ACTIVITY_DELETE_BASED_ON_ID) })

public class CustomActivity {
	public final static String DELETE_CUSTOM_ACTIVITY_BASED_ON_ID="DELETE_custom_activity_BASED_ON_ID";
	public final static String QUERY_CUSTOM_ACTIVITY_DELETE_BASED_ON_ID="DELETE CustomActivity pa where pa.id = :id";
	
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	 
	
	 @ManyToOne(fetch = FetchType.LAZY)
	   @JoinColumn(name = "project_id", referencedColumnName = "id")
	private Project projectId;
	
	 
	 
	 
	 
	 
	 @Column(name = "activity_name", length = 256, unique = true)
		@NotNull
		private String activityName;

		@Column(name = "mandatory")
		@NotNull
		private boolean mandatory;

		/*@Column(name = "type", length = 256)
		@NotNull
		private String type;*/

		@Column(name = "max")
		@NotNull
		private Integer max;

		@Column(name = "format", length = 256)
		private String format;

		@Column(name = "productive")
		@NotNull
		private boolean productive;

 

		@Column(name = "creation_timestamp", updatable = false, insertable = true)
		private Date creationTimestamp;

		 
		
		@Column(name = "active")
		@NotNull
		private boolean active;

		public boolean isActive() {
			return active;
		}
		public void setActive(boolean active) {
			this.active = active;
		}
		public String getActivityName() {
			return activityName;
		}
		public void setActivityName(String activityName) {
			this.activityName = activityName;
		}
		public boolean isMandatory() {
			return mandatory;
		}
		public void setMandatory(boolean mandatory) {
			this.mandatory = mandatory;
		}
		/*public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}*/
		public Integer getMax() {
			return max;
		}
		public void setMax(Integer max) {
			this.max = max;
		}
		public String getFormat() {
			return format;
		}
		public void setFormat(String format) {
			this.format = format;
		}
		public boolean isProductive() {
			return productive;
		}
		public void setProductive(boolean productive) {
			this.productive = productive;
		}
		public Date getCreationTimestamp() {
			return creationTimestamp;
		}
		public void setCreationTimestamp(Date creationTimestamp) {
			this.creationTimestamp = creationTimestamp;
		}
		public Date getLastupdatedTimestamp() {
			return lastupdatedTimestamp;
		}
		public void setLastupdatedTimestamp(Date lastupdatedTimestamp) {
			this.lastupdatedTimestamp = lastupdatedTimestamp;
		}
		@Column(name = "lastupdated_timestamp")
		private Date lastupdatedTimestamp;

	 
	 
	 
	 
	 
	 
	
	@Column(name="created_id", updatable = false)
	private String createdId;
	
	 
	
	@Column(name="lastupdated_id")
	private String lastUpdatedId;
	
 

	public String getCreatedId() {
		return createdId;
	}
	public void setCreatedId(String createdId) {
		this.createdId = createdId;
	}
 
	public String getLastUpdatedId() {
		return lastUpdatedId;
	}
	public void setLastUpdatedId(String lastUpdatedId) {
		this.lastUpdatedId = lastUpdatedId;
	}
 
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the projectId
	 */
	public Project getProjectId() {
		return projectId;
	}
	/**
	 * @param projectId the projectId to set
	 */
	public void setProjectId(Project projectId) {
		this.projectId = projectId;
	}


}
