package org.yash.rms.domain;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name="job_post")
public class JobPost {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    @ManyToMany
    @JoinTable(name = "jobpost_skill_primary", joinColumns = { @JoinColumn(name = "jobpost_id", nullable = false) }, inverseJoinColumns = { @JoinColumn(name = "skill_id", nullable = false) })
    private Set<Skills> skillss;
    
    @ManyToMany
    @JoinTable(name = "jobpost_skill_secondary", joinColumns = { @JoinColumn(name = "jobpost_id", nullable = false) }, inverseJoinColumns = { @JoinColumn(name = "skill_id", nullable = false) })
    private Set<Skills> skillss1;
    
    @ManyToOne
    @JoinColumn(name = "project_id", referencedColumnName = "id")
    private Project projectId;
    
    @ManyToOne
    @JoinColumn(name = "job_type_id", referencedColumnName = "id")
    private JobType jobTypeId;
    
    @Column(name = "no_of_position")
    private Integer noOfPosition;
    
    @Column(name = "job_name", length = 256)
    private String jobName;
    
    @Column(name="created_id", updatable = false)
	private String createdId;
	
	@Column(name="creation_timestamp",updatable = false, insertable = true)
	private Date creationTimestamp;
	
	@Column(name="lastupdated_id")
	private String	lastUpdatedId;
	
	@Column(name="lastupdated_timestamp")
	private Date lastupdatedTimestamp;
    
    public Set<Skills> getSkillss() {
        return skillss;
    }
    
    public void setSkillss(Set<Skills> skillss) {
        this.skillss = skillss;
    }
    
    public Set<Skills> getSkillss1() {
        return skillss1;
    }
    
    public void setSkillss1(Set<Skills> skillss1) {
        this.skillss1 = skillss1;
    }
    
    public Project getProjectId() {
        return projectId;
    }
    
    public void setProjectId(Project projectId) {
        this.projectId = projectId;
    }
    
    public JobType getJobTypeId() {
        return jobTypeId;
    }
    
    public void setJobTypeId(JobType jobTypeId) {
        this.jobTypeId = jobTypeId;
    }
    
    public Integer getNoOfPosition() {
        return noOfPosition;
    }
    
    public void setNoOfPosition(Integer noOfPosition) {
        this.noOfPosition = noOfPosition;
    }
    
    public String getJobName() {
        return jobName;
    }
    
    public void setJobName(String jobName) {
        this.jobName = jobName;
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
