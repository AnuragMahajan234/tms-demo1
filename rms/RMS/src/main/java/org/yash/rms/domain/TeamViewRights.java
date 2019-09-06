package org.yash.rms.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Id;


@Embeddable
public class TeamViewRights implements Serializable {
	
    private static final long serialVersionUID = 1L;
    
   // @Id
	@Column(name = "team_id", nullable = false)
    private int teamId;
    
   // @Id
    @Column(name = "resource_id", nullable = false)
    private int resourceId;
    
    
    @Column(name="created_id")
    private String createdBy;
    
    
    @Column(name="creation_timestamp")
    private Date creationTime;

	public int getTeamId() {
		return teamId;
	}

	public void setTeamId(int teamId) {
		this.teamId = teamId;
	}

	public int getResourceId() {
		return resourceId;
	}

	public void setResourceId(int resourceId) {
		this.resourceId = resourceId;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}

	 

}
