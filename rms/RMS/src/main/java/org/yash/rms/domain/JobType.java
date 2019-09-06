package org.yash.rms.domain;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="job_type")
public class JobType {
	
	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "id")
	    private Integer id;
	    
	    @OneToMany(mappedBy = "jobTypeId")
	    private Set<JobPost> jobPosts;
	    
	    @Column(name = "jobtype", length = 257)
	    @NotNull
	    private String jobtype;
	    
	    @Column(name="created_id", updatable = false)
		private String createdId;
		
		@Column(name="creation_timestamp",updatable = false, insertable = true)
		private Date creationTimestamp;
		
		@Column(name="lastupdated_id")
		private String	lastUpdatedId;
		
		@Column(name="lastupdated_timestamp")
		private Date lastupdatedTimestamp;
	    
	    public Integer getId() {
	        return this.id;
	    }
	    
	    public void setId(Integer id) {
	        this.id = id;
	    }
	    
	    
	    public Set<JobPost> getJobPosts() {
	        return jobPosts;
	    }
	    
	    public void setJobPosts(Set<JobPost> jobPosts) {
	        this.jobPosts = jobPosts;
	    }
	    
	    public String getJobtype() {
	        return jobtype;
	    }
	    
	    public void setJobtype(String jobtype) {
	        this.jobtype = jobtype;
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
