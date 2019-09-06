package org.yash.rms.dao;

import java.util.List;




import org.yash.rms.domain.ProjectTicketStatus;

/**
 * @author pratyoosh.tripathi
 * 
 */
public interface ProjectStatusDao extends RmsCRUDDAO<ProjectTicketStatus> {
	
	public List<ProjectTicketStatus> getConfiguredTicketStatus (int projectId);
	
	public List<ProjectTicketStatus> getSelectedConfiguredTicketStatus (int projectId);
	
	public List<ProjectTicketStatus> findById(int id, List<Integer> activityIds);
	
	public Integer setInactiveByProjectId(Integer projectId);
	public boolean saveProjectStatus(List<String> prioritiesList, Integer projectId);

	/**
	 * @param ticketStatusId
	 * @return
	 */
	public ProjectTicketStatus findProjectTicketStatusById(
			Integer ticketStatusId);

	/**
	 * @param projectId
	 * @return
	 */
	public List<ProjectTicketStatus> getActiveProjectTicketStatusForProjectId(
			Integer projectId);

}
