package org.yash.rms.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.yash.rms.dao.ProjectStatusDao;
import org.yash.rms.domain.Project;
import org.yash.rms.domain.ProjectTicketPriority;
import org.yash.rms.domain.ProjectTicketStatus;
import org.yash.rms.domain.ResourceAllocation;
import org.yash.rms.dto.ProjectTicketPriorityDTO;
import org.yash.rms.dto.ProjectTicketStatusDTO;
import org.yash.rms.service.ProjectStatusService;
import org.yash.rms.service.ResourceAllocationService;
import org.yash.rms.service.UserActivityService;


/**
 * @author pratyoosh.tripathi
 *
 */
@Service("ProjectStatusService")
public class ProjectStatusServiceImpl implements ProjectStatusService {

	@Autowired @Qualifier("ProjectStatusDao")
	private ProjectStatusDao projectStatusDao;

	@Autowired
	private ResourceAllocationService resourceAllocationService;
	
	private static final Logger logger = LoggerFactory.getLogger(ProjectStatusServiceImpl.class);
	
	public List<ProjectTicketStatus> getConfiguredTicketStatus (int projectId ){
		return projectStatusDao.getConfiguredTicketStatus(projectId);
	}
	
	public List<ProjectTicketStatus> getSelectedConfiguredTicketStatus (int projectId ){
		return projectStatusDao.getSelectedConfiguredTicketStatus(projectId);
	}
	
	public List<ProjectTicketStatus> findById(int id, List<Integer> activityIds) {
		return projectStatusDao.findById(id, activityIds);
	}
	
	public boolean delete(int id) {
		return projectStatusDao.delete(id);
	}

	public boolean saveOrupdate(ProjectTicketStatus status) {
		return projectStatusDao.saveOrupdate(status);
	}

	public List<ProjectTicketStatus> findAll() {
		return null;
	}

	public List<ProjectTicketStatus> findByEntries(int firstResult, int sizeNo) {
		return null;
	}

	public long countTotal() {
		return 0;
	}
	
	public boolean saveProjectStatus(String projectStatusList, Integer projectId) {
		
		if(projectStatusList == "") {
			/* wherever the active = 1 make it 0 by projectId*/
			Integer entryUpdated = projectStatusDao.setInactiveByProjectId(projectId);
			return true;
		}else {
			/* listed priority will go and change the active flag*/
			String[] statusList = projectStatusList.split(",");
			return projectStatusDao.saveProjectStatus(Arrays.asList(statusList), projectId);
		}
	}

	public ProjectTicketStatus findProjectTicketStatusById(
			Integer ticketStatusId) {
		logger.info("Inside findProjectTicketStatusById for ticketStatusId  "+ticketStatusId+" on "+this.getClass().getCanonicalName());
		return projectStatusDao.findProjectTicketStatusById(ticketStatusId);
	}

	public List<ProjectTicketStatus> getActiveProjectTicketStatusForProjectId(
			Integer projectId) {
		logger.info("Ïnside getActiveProjectTicketStatusForProjectId for projectId "+projectId+" on "+this.getClass().getCanonicalName());
		return projectStatusDao.getActiveProjectTicketStatusForProjectId(projectId);
	}

	public List<ProjectTicketStatusDTO> convertProjectTicketStatusListToDTOList(
			List<ProjectTicketStatus> projectTicketStatusList) {
		logger.info("Inside convertProjectTicketStatusListToDTOList for projectTicketStatusList "
			+(projectTicketStatusList!=null?projectTicketStatusList.size():null)+" on "+this.getClass().getCanonicalName());
		List<ProjectTicketStatusDTO> projectTicketStatusDTOList=new ArrayList<ProjectTicketStatusDTO>();
		for (ProjectTicketStatus projectTicketStatus : projectTicketStatusList) {
			ProjectTicketStatusDTO projectTicketStatusDTO=new ProjectTicketStatusDTO();
			projectTicketStatusDTO.setActive(projectTicketStatus.getActive());
			projectTicketStatusDTO.setId(projectTicketStatus.getId());
			projectTicketStatusDTO.setStatus(projectTicketStatus.getStatus());
			projectTicketStatusDTOList.add(projectTicketStatusDTO);
		}
		return projectTicketStatusDTOList;
	}

	public List<ProjectTicketStatus> getActiveTicketStatusByResourceAllocationId(
			Integer resourceAllocationId) {
		logger.info("Inside getActiveTicketStatusByResourceAllocationId for resourceAllocationId "+resourceAllocationId
				+" on "+this.getClass().getCanonicalName());
		ResourceAllocation resourceAllocation=resourceAllocationService.findById(resourceAllocationId);
		List<ProjectTicketStatus> projectTicketStatusList=new ArrayList<ProjectTicketStatus>();
		if(resourceAllocation!=null)
		{
			Project project=resourceAllocation.getProjectId();
			if(project!=null)
			{
				Integer projectId=project.getId();
				if(projectId!=null)
				{
					projectTicketStatusList=getActiveProjectTicketStatusForProjectId(projectId);
				}
			}
		}
		return projectTicketStatusList;
	}
}
