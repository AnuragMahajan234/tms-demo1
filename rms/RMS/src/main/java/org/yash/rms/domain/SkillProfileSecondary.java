package org.yash.rms.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;
import org.hibernate.envers.NotAudited;

@Entity
@Table(name="skill_profile_secondary")
@NamedQueries({ @NamedQuery(name = SkillProfileSecondary.DELETE_SKILLPROFILESECONDARY_BASED_ON_ID, query = SkillProfileSecondary.QUERY_SKILLPROFILESECONDARY_DELETE_BASED_ON_ID) })
public class SkillProfileSecondary {

	public final static String DELETE_SKILLPROFILESECONDARY_BASED_ON_ID = "DELETE_SKILLPROFILESECONDARY_BASED_ON_ID";
	public final static String QUERY_SKILLPROFILESECONDARY_DELETE_BASED_ON_ID = "DELETE SkillProfileSecondary s where s.id = :id";
	
	@ManyToOne
    @JoinColumn(name = "skill_id", referencedColumnName = "id")
    @NotAudited
    private Skills skillId;

    public Skills getSkillId() {
		return skillId;
	}

	public void setSkillId(Skills skillId) {
		this.skillId = skillId;
	}

	public UserProfile getProfileId() {
		return profileId;
	}

	public void setProfileId(UserProfile profileId) {
		this.profileId = profileId;
	}

	@ManyToOne
    @JoinColumn(name = "profile_id", referencedColumnName = "id")
    @NotAudited
    private UserProfile profileId;
    
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
    
    @Column(name = "rating")
    private Integer rating;
    
    @Column(name = "status")
    private Boolean status;
    
    @Column(name="created_id", updatable = false)
  	private String createdId;
  	
  	@Column(name="creation_timestamp",updatable = false, insertable = true)
  	private Date creationTimestamp;
  	
  	@Column(name="lastupdated_id")
  	private String	lastUpdatedId;
  	
  	@Column(name="lastupdated_timestamp")
  	private Date lastupdatedTimestamp;
    
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
