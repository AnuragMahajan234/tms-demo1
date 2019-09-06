/**
 * 
 */
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

import org.codehaus.jackson.annotate.JsonBackReference;
import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;
import org.hibernate.envers.NotAudited;
import org.yash.rms.domain.Resource;
import org.yash.rms.domain.Skills;

@Entity
@Table(name = "skill_resource_primary")
@NamedQueries({ @NamedQuery(name = SkillResourcePrimary.DELETE_SKILLRESOURCEPRIMARY_BASED_ON_ID, query = SkillResourcePrimary.QUERY_SKILLRESOURCEPRIMARY_DELETE_BASED_ON_ID) })
public class SkillResourcePrimary {
	
	public final static String DELETE_SKILLRESOURCEPRIMARY_BASED_ON_ID = "DELETE_SKILLRESOURCEPRIMARY_BASED_ON_ID";
	public final static String QUERY_SKILLRESOURCEPRIMARY_DELETE_BASED_ON_ID = "DELETE SkillResourcePrimary srp where srp.id = :id";
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    
    @Column(name = "rating")
    private Integer rating;
    
    @Column(name = "primaryExperience")
    private Integer primaryExperience;
    @ManyToOne
    @JoinColumn(name = "resource_id", referencedColumnName = "employee_id")
    @JsonBackReference
    @NotAudited
    private Resource resourceId;

    @ManyToOne
    @JoinColumn(name = "skill_id", referencedColumnName = "id")
    @JsonBackReference
    @NotAudited
    private Skills skillId;
    
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
    
    public Resource getResourceId() {
		return resourceId;
	}

	public void setResourceId(Resource resourceId) {
		this.resourceId = resourceId;
	}

	public Skills getSkillId() {
		return skillId;
	}

	public void setSkillId(Skills skillId) {
		this.skillId = skillId;
	}

	public void setRating(Integer rating) {
        this.rating = rating;
    }
    
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
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

	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SkillResourcePrimary other = (SkillResourcePrimary) obj;
		if (skillId == null) {
			if (other.skillId != null)
				return false;
		} else if (!skillId.equals(other.skillId))
			return false;
		return true;
	}
	public Integer getPrimaryExperience() {
		return primaryExperience;
	}
	public void setPrimaryExperience(Integer primaryExperience) {
		this.primaryExperience = primaryExperience;
	}
}
