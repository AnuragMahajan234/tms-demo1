package org.yash.rms.dao;

import java.util.List;

import org.yash.rms.domain.ProjectTicketPriority;

/**
 * @author sarang.patil
 * 
 */
public interface ProjectTicketsDao extends RmsCRUDDAO<ProjectTicketPriority> {
	
	public List<ProjectTicketPriority> getConfiguredTicketPriorities (int projectId);
	
	public List<ProjectTicketPriority> getSelectedConfiguredTicketPriorities (int projectId);
	
	public List<ProjectTicketPriority> findById(int id, List<Integer> activityIds);
	
	public Integer setInactiveByProjectId(Integer projectId);
	public boolean saveProjectPriorities(List<String> prioritiesList, Integer projectId);

	/**
	 * @param ticketPriorityId
	 * @return
	 */
	public ProjectTicketPriority findProjectTicketPriorityById(
			Integer ticketPriorityId);

	/**
	 * @param projectId
	 * @return
	 */
	public List<ProjectTicketPriority> getActiveProjectTicketPriorityForProjectId(
			Integer projectId);

}
