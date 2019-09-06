package org.yash.rms.dao;

import java.util.List;

import org.yash.rms.domain.Team;
import org.yash.rms.domain.TeamViewRightEmbedded;

public interface TeamDao<T> extends RmsCRUDDAO<T> {
	List<TeamViewRightEmbedded> teamViewList(int teamId);
	boolean saveTeamViewRight(TeamViewRightEmbedded teamAccessDto);
	public boolean deleteResource(int teamId,int resourceId) ;

	List<TeamViewRightEmbedded> getTeamAccessListByResourceId(int resourceId);
	public List<Team> getTeamNameById(List<Integer> teamIds) ;
}
