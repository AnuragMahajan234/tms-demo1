/**
 * 
 */
package org.yash.rms.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.yash.rms.dao.ResourceDao;
import org.yash.rms.dao.TeamDao;
import org.yash.rms.domain.Team;
import org.yash.rms.domain.TeamViewRightEmbedded;
import org.yash.rms.domain.TeamViewRights;
import org.yash.rms.dto.TeamAccessDto;
import org.yash.rms.service.TeamService;

/**
 * @author apurva.sinha
 *
 */
@Service("teamService")
public class TeamServiceImpl implements TeamService {

	@Autowired
	@Qualifier("TeamDao")
	TeamDao<Team> teamDaoImpl;

	@Autowired
	@Qualifier("ResourceDao")
	ResourceDao resourceDao;

	public boolean delete(int id) {
		return teamDaoImpl.delete(id);
	}

	public boolean saveOrupdate(Team t) {
		return teamDaoImpl.saveOrupdate(t);
	}

	public List<Team> findAll() {
		return teamDaoImpl.findAll();
	}

	public List<Team> findByEntries(int firstResult, int sizeNo) {
		return null;
	}

	public long countTotal() {
		return 0;
	}

	public List<TeamAccessDto> getTeamAccessList(int teamId) {

		List<TeamAccessDto> teamAccessDtoList = new ArrayList<TeamAccessDto>();
		List<TeamViewRightEmbedded> teamList = teamDaoImpl.teamViewList(teamId);
		TeamAccessDto teamAccessDto = null;
		List<Integer> employeeIds = new ArrayList<Integer>();
		if (null != teamList && !teamList.isEmpty()) {
			for (int i = 0; i < teamList.size(); i++) {
				employeeIds.add(teamList.get(i).getTeamViewRight()
						.getResourceId());
			}
			List<String[]> list = resourceDao.getResourceNameById(employeeIds);

			for (TeamViewRightEmbedded team : teamList) {
				teamAccessDto = new TeamAccessDto();
				for (String[] record : list) {
					if (Integer.parseInt(record[1]) == team.getTeamViewRight()
							.getResourceId()) {
						teamAccessDto.setResourceName(record[0]);
						teamAccessDto.setResourceId(team.getTeamViewRight()
								.getResourceId());
						teamAccessDtoList.add(teamAccessDto);
					}
				}

			}
		}

		return teamAccessDtoList;
	}

	public boolean saveTeamViewRight(TeamAccessDto teamAccessDto) {
		TeamViewRightEmbedded teamAccess = new TeamViewRightEmbedded();
		this.convertFromTeamAccessDto(teamAccessDto, teamAccess);
		boolean result = teamDaoImpl.saveTeamViewRight(teamAccess);
		return result;

	}

	public void convertToTeamAccessDto(TeamAccessDto teamAccessDto,
			TeamViewRightEmbedded teamAccess) {

		teamAccessDto.setResourceId(teamAccess.getTeamViewRight()
				.getResourceId());
		teamAccessDto.setTeamId(teamAccess.getTeamViewRight().getTeamId());
	}

	public void convertFromTeamAccessDto(TeamAccessDto teamAccessDto,
			TeamViewRightEmbedded teamAccess) {
		if (null == teamAccess.getTeamViewRight()) {
			teamAccess.setTeamViewRight(new TeamViewRights());
		}
		teamAccess.getTeamViewRight().setResourceId(
				teamAccessDto.getResourceId());
		teamAccess.getTeamViewRight().setTeamId(teamAccessDto.getTeamId());
	}

	public boolean deleteResource(int teamId, int resourceId) {
		return teamDaoImpl.deleteResource(teamId, resourceId);
	}

	public List<Team> getTeamListByResourceId(int resourceId) {
		List<Team> teamList = null;
		List<TeamViewRightEmbedded> teamAccessList = teamDaoImpl
				.getTeamAccessListByResourceId(resourceId);
		List<Integer> teamIds = new ArrayList<Integer>();
		if (null != teamAccessList && !teamAccessList.isEmpty()) {
			for (int i = 0; i < teamAccessList.size(); i++) {
				teamIds.add(teamAccessList.get(i).getTeamViewRight()
						.getTeamId());
			}
			teamList = teamDaoImpl.getTeamNameById(teamIds);

		}

		return teamList;
	}

}
