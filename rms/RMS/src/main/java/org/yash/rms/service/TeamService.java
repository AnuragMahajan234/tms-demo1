package org.yash.rms.service;

import java.util.List;

import org.yash.rms.domain.Team;
import org.yash.rms.dto.TeamAccessDto;

public interface TeamService extends RmsCRUDService<Team> {

	List<TeamAccessDto> getTeamAccessList(int teamId);

	boolean saveTeamViewRight(TeamAccessDto teamAccessDto);

	boolean deleteResource(int teamId, int resourceId);

	List<Team> getTeamListByResourceId(int resourceId);

}
