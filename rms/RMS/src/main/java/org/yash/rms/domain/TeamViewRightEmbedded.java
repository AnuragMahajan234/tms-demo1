package org.yash.rms.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name = "team_view_access")
public class TeamViewRightEmbedded implements Serializable {
	
    private static final long serialVersionUID = 1L;
    
	 @EmbeddedId
	 private TeamViewRights teamViewRight;
	 
	 

	public TeamViewRights getTeamViewRight() {
		return teamViewRight;
	}

	public void setTeamViewRight(TeamViewRights teamViewRight) {
		this.teamViewRight = teamViewRight;
	}
 
    

}
