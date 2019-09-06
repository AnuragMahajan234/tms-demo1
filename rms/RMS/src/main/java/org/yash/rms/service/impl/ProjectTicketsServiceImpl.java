package org.yash.rms.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.yash.rms.dao.ProjectTicketsDao;
import org.yash.rms.domain.Project;
import org.yash.rms.domain.ProjectTicketPriority;
import org.yash.rms.domain.ResourceAllocation;
import org.yash.rms.dto.ProjectTicketPriorityDTO;
import org.yash.rms.service.ProjectTicketsService;
import org.yash.rms.service.ResourceAllocationService;

/**
 * @author sarang.patil
 *
 */
@Service("ProjectTicketsService")
public class ProjectTicketsServiceImpl implements ProjectTicketsService {

	@Autowired @Qualifier("ProjectTicketsDao")
	private ProjectTicketsDao projectTicketsDao;

	@Autowired
	private ResourceAllocationService resourceAllocationService;
	
	private static final Logger logger = LoggerFactory.getLogger(ProjectTicketsServiceImpl.class);
	
	public List<ProjectTicketPriority> getConfiguredTicketPriorities (int projectId ){
		return projectTicketsDao.getConfiguredTicketPriorities(projectId);
	}
	
	public List<ProjectTicketPriority> getSelectedConfiguredTicketPriorities (int projectId ){
		return projectTicketsDao.getSelectedConfiguredTicketPriorities(projectId);
	}
	
	public List<ProjectTicketPriority> findById(int id, List<Integer> activityIds) {
		return projectTicketsDao.findById(id, activityIds);
	}
	
	public boolean delete(int id) {
		return projectTicketsDao.delete(id);
	}

	public boolean saveOrupdate(ProjectTicketPriority priority) {
		return projectTicketsDao.saveOrupdate(priority);
	}

	public List<ProjectTicketPriority> findAll() {
		return null;
	}

	public List<ProjectTicketPriority> findByEntries(int firstResult, int sizeNo) {
		return null;
	}

	public long countTotal() {
		return 0;
	}
	
	public boolean saveProjectPriorities(String projectPrioritiesList, Integer projectId) {
		if(projectPrioritiesList == "") {
			Integer entryUpdated = projectTicketsDao.setInactiveByProjectId(projectId);
			return true;
		}else {
			String[] prioritiesList = projectPrioritiesList.split(",");
			return projectTicketsDao.saveProjectPriorities(Arrays.asList(prioritiesList), projectId);
		}
	}

	public ProjectTicketPriority findProjectTicketPriorityById(
			Integer ticketPriorityId) {
		logger.info("Inside findProjectTicketPriorityById for ticketPriorityId "+ticketPriorityId+" on "+this.getClass().getCanonicalName());
		return projectTicketsDao.findProjectTicketPriorityById(ticketPriorityId);
	}

	public List<ProjectTicketPriority> getActiveProjectTicketPriorityForProjectId(
			Integer projectId) {
		logger.info("Inside getActiveProjectTicketPriorityForProjectId for projectId "+projectId+" on "+this.getClass().getCanonicalName());
		return projectTicketsDao.getActiveProjectTicketPriorityForProjectId(projectId);
	}

	public List<ProjectTicketPriorityDTO> convertProjectTicketPriorityListToDTOList(
			List<ProjectTicketPriority> projectTicketPrioritiesList) {
		logger.info("Inside convertProjectTicketPriorityListToDTOList for projectTicketPrioritieList "
			+(projectTicketPrioritiesList!=null?projectTicketPrioritiesList.size():null));
		List<ProjectTicketPriorityDTO> projectTicketPriorityDTOList=new ArrayList<ProjectTicketPriorityDTO>();
		for (ProjectTicketPriority projectTicketPriority : projectTicketPrioritiesList) {
			ProjectTicketPriorityDTO projectTicketPriorityDTO=new ProjectTicketPriorityDTO();
			projectTicketPriorityDTO.setActive(projectTicketPriority.getActive());
			projectTicketPriorityDTO.setId(projectTicketPriority.getId());
			projectTicketPriorityDTO.setPriority(projectTicketPriority.getPriority());
			projectTicketPriorityDTOList.add(projectTicketPriorityDTO);
		}
		return projectTicketPriorityDTOList;
	}

	public List<ProjectTicketPriority> getActiveTicketPrioritiesByResourceAllocationId(
			Integer resourceAllocationId) {
		logger.info("Inside getActiveTicketPrioritiesByResourceAllocationId for resourceAllocationId "+resourceAllocationId);
		ResourceAllocation resourceAllocation=resourceAllocationService.findById(resourceAllocationId);
		List<ProjectTicketPriority> projectTicketPriorityList=new ArrayList<ProjectTicketPriority>();
		if(resourceAllocation!=null)
		{
			Project project=resourceAllocation.getProjectId();
			if(project!=null)
			{
				Integer projectId=project.getId();
				if(projectId!=null)
				{
					projectTicketPriorityList=getActiveProjectTicketPriorityForProjectId(projectId);
				}
			}
		}
		return projectTicketPriorityList;
	}
}
