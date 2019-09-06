package org.yash.rms.service;

import java.util.List;

import org.yash.rms.domain.ProjectTicketPriority;
import org.yash.rms.dto.ProjectTicketPriorityDTO;

/**
 * @author sarang.patil
 * 
 */
public interface ProjectTicketsService extends RmsCRUDService<ProjectTicketPriority> {
	
	public List<ProjectTicketPriority> getConfiguredTicketPriorities (int projectId);
	
	public List<ProjectTicketPriority> getSelectedConfiguredTicketPriorities (int projectId);
	
	public List<ProjectTicketPriority> findById(int id, List<Integer> activityIds);
	
	public boolean saveProjectPriorities(String prioritiesList, Integer projectId);

	/**
	 * @param ticketPriorityId
	 * @return
	 */
	public ProjectTicketPriority findProjectTicketPriorityById(Integer ticketPriorityId);

	/**
	 * @param projectId
	 * @return
	 */
	public List<ProjectTicketPriority> getActiveProjectTicketPriorityForProjectId(
			Integer projectId);

	/**
	 * @param projectTicketPrioritieList
	 * @return
	 */
	public List<ProjectTicketPriorityDTO> convertProjectTicketPriorityListToDTOList(
			List<ProjectTicketPriority> projectTicketPrioritieList);

	/**
	 * @param resourceAllocationId
	 * @return
	 */
	public List<ProjectTicketPriority> getActiveTicketPrioritiesByResourceAllocationId(
			Integer resourceAllocationId);
	
}
