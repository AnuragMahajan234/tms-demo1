package org.yash.rms.service;

import java.util.List;

import org.yash.rms.domain.ProjectTicketStatus;
import org.yash.rms.dto.ProjectTicketStatusDTO;

/**
 * @author pratyoosh.tripathi
 * 
 */
public interface ProjectStatusService extends RmsCRUDService<ProjectTicketStatus> {
	
	public List<ProjectTicketStatus> getConfiguredTicketStatus (int projectId);
	
	public List<ProjectTicketStatus> getSelectedConfiguredTicketStatus (int projectId);
	
	public List<ProjectTicketStatus> findById(int id, List<Integer> activityIds);
	
	public boolean saveProjectStatus(String prioritiesList, Integer projectId);

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

	/**
	 * @param projectTicketStatusList
	 * @return
	 */
	public List<ProjectTicketStatusDTO> convertProjectTicketStatusListToDTOList(
			List<ProjectTicketStatus> projectTicketStatusList);

	/**
	 * @param resourceAllocationId
	 * @return
	 */
	public List<ProjectTicketStatus> getActiveTicketStatusByResourceAllocationId(
			Integer resourceAllocationId);
	
}
