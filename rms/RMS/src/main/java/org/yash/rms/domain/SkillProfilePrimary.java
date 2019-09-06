package org.yash.rms.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;
import org.hibernate.envers.NotAudited;

@Entity
@Table(name="skill_profile_primary")
@NamedQueries({ @NamedQuery(name = SkillProfilePrimary.DELETE_SKILLPRIMARYPROFILE_BASED_ON_ID, query = SkillProfilePrimary.QUERY_SKILLPRIMARYPROFILE_DELETE_BASED_ON_ID) })
public class SkillProfilePrimary implements Serializable{
	
	public final static String DELETE_SKILLPRIMARYPROFILE_BASED_ON_ID = "DELETE_SKILLPRIMARYPROFILE_BASED_ON_ID";
	public final static String QUERY_SKILLPRIMARYPROFILE_DELETE_BASED_ON_ID = "DELETE SkillProfilePrimary s where s.id = :id";
	
	@ManyToOne
    @JoinColumn(name = "profile_id", referencedColumnName = "id")
    @NotAudited
    private UserProfile profileId;

    @ManyToOne
    @JoinColumn(name = "skill_id", referencedColumnName = "id")
    @NotAudited
    private Skills skillId;
    
	@ManyToMany(mappedBy = "skillss")
    private Set<JobPost> jobPosts;
    
    @Column(name = "rating")
    private Integer rating;
    
    @Column(name = "status")
    private Boolean status;
    
    @ManyToMany(mappedBy = "skillss1")
    private Set<JobPost> jobPosts1;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    
    @Column(name="created_id", updatable = false)
  	private String createdId;
  	
  	@Column(name="creation_timestamp",updatable = false, insertable = true)
  	private Date creationTimestamp;
  	
  	@Column(name="lastupdated_id")
  	private String	lastUpdatedId;
  	
  	@Column(name="lastupdated_timestamp")
  	private Date lastupdatedTimestamp;
    
  	@Column (name="primaryExperience")
  	private Integer primaryExperience;
  	
    public Integer getPrimaryExperience() {
		return primaryExperience;
	}

	public void setPrimaryExperience(Integer primaryExperience) {
		this.primaryExperience = primaryExperience;
	}

	public UserProfile getProfileId() {
		return profileId;
	}

	public void setProfileId(UserProfile profileId) {
		this.profileId = profileId;
	}

	public Skills getSkillId() {
		return skillId;
	}

	public void setSkillId(Skills skillId) {
		this.skillId = skillId;
	}

	public Integer getRating() {
		return rating;
	}

	public void setRating(Integer rating) {
		this.rating = rating;
	}
    
    public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

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
    
    public Set<JobPost> getJobPosts1() {
        return jobPosts1;
    }
    
    public void setJobPosts1(Set<JobPost> jobPosts1) {
        this.jobPosts1 = jobPosts1;
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
    
//    public String getSkillType() {
//        return skillType;
//    }
//    
//    public void setSkillType(String skillType) {
//        this.skillType = skillType;
//    }

}
