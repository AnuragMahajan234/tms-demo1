package org.yash.rms.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.yash.rms.dao.ProjectDao;
import org.yash.rms.dao.ResourceDao;
import org.yash.rms.dao.ResourceOnBenchDao;
import org.yash.rms.domain.Project;
import org.yash.rms.domain.Resource;
import org.yash.rms.domain.ResourceAllocation;
import org.yash.rms.domain.UserActivity;
import org.yash.rms.dto.ResourceOnBenchDto;
import org.yash.rms.dto.UserContextDetails;
import org.yash.rms.service.ResourceOnBenchService;
import org.yash.rms.service.ResourceService;
import org.yash.rms.util.ResourceAllocationSearchCriteria;
import org.yash.rms.util.ResourceOnBenchSearchCriteria;
import org.yash.rms.util.UserUtil;

@Service("resourceOnBenchService")
public class ResourceOnBenchServiceImpl implements ResourceOnBenchService {

	@Autowired
	@Qualifier("resourceOnBenchDao")
	ResourceOnBenchDao resourceOnBenchDao;
	
	//TODO : will be added after 9th August 2017 deployment.
	/*@Autowired
	@Qualifier("resourceReleaseSummaryDao")
	ResourceReleaseSummaryDao resourceReleaseSummaryDao;*/

	@Autowired
	@Qualifier("projectDaoImpl")
	ProjectDao projectDao;

	@Autowired
	ResourceDao resourceDao;

	private static final Logger logger = LoggerFactory.getLogger(ResourceOnBenchServiceImpl.class);

	public boolean delete(int id) {
		logger.info("------ResourceOnBenchServiceImpl  delete method start------");
		return resourceOnBenchDao.delete(id);

	}

	public List<Integer> findAllocatedResourceEmployeeIdByProjectIds(List<Integer> projectIds, String active) {
		logger.info("------ResourceOnBenchServiceImpl  findAllocatedResourceEmployeeIdByProjectIds method start------");
		return resourceOnBenchDao.findAllocatedResourceEmployeeIdByProjectIds(projectIds, active);
	}

	public boolean saveOrupdate(ResourceAllocation resourceAllocation) {
		logger.info("------ResourceOnBenchServiceImpl  saveOrupdate method start------");
		Project project = projectDao.findProject(resourceAllocation.getProjectId().getId());
		// defaultProjectList=projectDao.findAll();//

		resourceAllocation.setProjectId(project);
		// if primary Project flag is set yes then copy the projectId to
		// resource table as well.
		Resource resource = resourceDao.findByEmployeeId(resourceAllocation.getEmployeeId().getEmployeeId());
		if (resourceAllocation.getCurProj() != null) {
			if (resourceAllocation.getCurProj()) {
				resource.setCurrentProjectId(project);
				resourceDao.saveOrupdate(resource);
			}

		}
		// else{

		// resourceAllocation.getCurProj(resource.setCurrentProjectId(defaultProjectList));

		// List<DefaultProject> defaultProjectList = new
		// ArrayList<DefaultProject>();
		// defaultProjectList=projectDao.findAll();
		// return defaultProjectList;
		// resourceAllocation.getCurProj(defaultProjectList);
		// }//

		if (resourceAllocation.getProjectId().getProjectEndDate() != null) {
			if (resourceAllocation.getAllocEndDate() == null || (resourceAllocation.getAllocEndDate().compareTo(resourceAllocation.getProjectId().getProjectEndDate()) > 0)) {
				resourceAllocation.setAllocEndDate(resourceAllocation.getProjectId().getProjectEndDate());
				return resourceOnBenchDao.saveOrupdate(resourceAllocation);

			}
		}
		return resourceOnBenchDao.saveOrupdate(resourceAllocation);

	}

	public List<ResourceAllocation> findAll() {
		logger.info("------ResourceOnBenchServiceImpl  findAll method start------");

		List<ResourceAllocation> resourceAllocations = new ArrayList<ResourceAllocation>();
		resourceAllocations = resourceOnBenchDao.findAll();
		return resourceAllocations;

	}

	public List<ResourceAllocation> findByEntries(int firstResult, int sizeNo) {
		logger.info("------ResourceOnBenchServiceImpl  findByEntries method start------");
		return resourceOnBenchDao.findByEntries(firstResult, sizeNo);
	}

	public long countTotal() {
		logger.info("------ResourceOnBenchServiceImpl countTotal method start------");
		return resourceOnBenchDao.countTotal();
	}

	public List<ResourceAllocation> findResourceAllocationsByEmployeeId(Resource resource) {
		logger.info("------ResourceOnBenchServiceImpl  findResourceAllocationsByEmployeeId method start------");
		return resourceOnBenchDao.findResourceAllocationsByEmployeeId(resource);
	}

	public List<ResourceAllocation> findResourceAllocations(Resource resource) {
		logger.info("------ResourceOnBenchServiceImpl  findResourceAllocations method start------");
		return resourceOnBenchDao.findResourceAllocations(resource);
	}

	public ResourceAllocation findById(int id) {
		logger.info("------ResourceOnBenchServiceImpl  findById method start------");
		return resourceOnBenchDao.findById(id);
	}

	public List<ResourceAllocation> findResourceAllocationByEmployeeId(Integer employeeId) {
		logger.info("------ResourceOnBenchServiceImpl  findResourceAllocationByEmployeeId method start------");
		return resourceOnBenchDao.findResourceAllocationByEmployeeId(employeeId);

	}

	public List<ResourceAllocation> findResourceAllocationsByProjectId(Integer projectId) {
		logger.info("------ResourceOnBenchServiceImpl  findResourceAllocationsByProjectId method start------");
		// projectId = projectDao.findProject(projectId.getId());
		// projectId = projectDao.findProject(projectId.getId());
		List<ResourceAllocation> allocations = resourceOnBenchDao.findResourceAllocationsByProjectId(projectId);
		/*
		 * if(projectId.getProjectEndDate()!=null){ for(ResourceAllocation
		 * resourceAllocation: allocations){
		 * if(resourceAllocation.getAllocEndDate()==null){
		 * resourceAllocation.setAllocEndDate(projectId.getProjectEndDate());
		 * resourceAllocationDao.saveOrupdate(resourceAllocation); }else{
		 * if(resourceAllocation
		 * .getAllocEndDate().compareTo(projectId.getProjectEndDate())>0){
		 * resourceAllocation.setAllocEndDate(projectId.getProjectEndDate());
		 * resourceAllocationDao.saveOrupdate(resourceAllocation); } } } }
		 */
		return allocations;
	}

	public List<Object[]> findMinMaxUseractivity(Integer resAlloc) {
		logger.info("------ResourceOnBenchServiceImpl  findMinMaxUseractivity method start------");
		return resourceOnBenchDao.findMinMaxUseractivity(resAlloc);
	}

	public List<Object[]> findLastUseractivity(Integer resAlloc) {
		logger.info("------ResourceOnBenchServiceImpl  findLastUseractivity method start------");
		return resourceOnBenchDao.findLastUseractivity(resAlloc);
	}

	public List<Object[]> findFirstUseractivity(Integer resAlloc) {
		logger.info("------ResourceOnBenchServiceImpl  findFirstUseractivity method start------");
		return resourceOnBenchDao.findFirstUseractivity(resAlloc);
	}

	public ResourceAllocation findResourceAllocation(Integer id) {
		logger.info("------ResourceOnBenchServiceImpl  findResourceAllocation method start------");
		return resourceOnBenchDao.findResourceAllocation(id);
	}

	public ResourceAllocation findResourceAllocationsByEmployeeIdAndProjectId(Integer employeeId, Integer projectId) {
		logger.info("------ResourceOnBenchServiceImpl  findResourceAllocationsByEmployeeIdAndProjectId method start------");
		/*
		 * Resource resource=new Resource(); Project project=new Project();
		 * project.setId(projectId); resource.setEmployeeId(employeeId);
		 */
		return resourceOnBenchDao.findResourceAllocationsByEmployeeIdAndProjectId(employeeId, projectId);
	}

	public List<Integer> findAllocatedResourceEmployeeId(Integer projectId, String active) {
		logger.info("------ResourceOnBenchServiceImpl  findAllocatedResourceEmployeeId method start------");
		return resourceOnBenchDao.findAllocatedResourceEmployeeId(projectId, active);
	}
	
	//TODO : Dead code.
	/*public ResourceAllocation findResourceAllocationsByEmployeeIdAndProjectId(Resource employeeId, Project projectId) {
		logger.info("------ResourceOnBenchServiceImpl  findResourceAllocationsByEmployeeIdAndProjectId method start------");
		return resourceAllocationDao.findResourceAllocationsByEmployeeIdAndProjectId(employeeId, projectId);

	}*/

	public List<Integer> findProjectIdsByEmployeeIdAndIsBehalfManager(Integer employeeId) {
		return resourceOnBenchDao.findProjectIdsByEmployeeIdAndIsBehalfManager(employeeId);
	}

	public List<ResourceAllocation> findResourceAllocationsforManager(Resource resource, Resource manager, Date weekEndDate) {
		return resourceOnBenchDao.findResourceAllocationsforManager(resource, manager, weekEndDate);

	}

	public List<ResourceAllocation> findResourceAllocationsByEmployeeIdforTimeHours(Resource resource, Date weekEndDate) {
		return resourceOnBenchDao.findResourceAllocationsByEmployeeIdforTimeHours(resource, weekEndDate);
	}

	public List<ResourceAllocation> findResourceAllocationByActiveTypeEmployeeId(Resource resource , java.sql.Date allocationStartDate) {
		logger.info("------ResourceOnBenchServiceImpl  findResourceAllocationByActiveTypeEmployeeId method start------");
		return resourceOnBenchDao.findResourceAllocationByActiveTypeEmployeeId(resource, allocationStartDate);
	}
	
	public List<ResourceAllocation> findResourceAllocationByActiveTypeEmployeeId(Resource resource ) {
		logger.info("------ResourceOnBenchServiceImpl  findResourceAllocationByActiveTypeEmployeeId method start------");
		return resourceOnBenchDao.findResourceAllocationByActiveTypeEmployeeId(resource);
	}

	public List<ResourceAllocation> findActiveResourceAllocationsByProjectId(Integer projectId) {
		logger.info("------ResourceOnBenchServiceImpl  findActiveResourceAllocationsByProjectId method start------");
		List<ResourceAllocation> allocations = resourceOnBenchDao.findActiveResourceAllocationsByProjectId(projectId);

		return allocations;
	}

	public List<Integer> findAllocatedResourceEmployeeIdByProjectIdsPagination(int firstResult, int maxResults, List projectIds, String active,
			ResourceAllocationSearchCriteria resourceAllocationSearchCriteria, boolean search, Integer employeeId) {
		return resourceOnBenchDao.findAllocatedResourceEmployeeIdByProjectIdsPagination(firstResult, maxResults, projectIds, active, resourceAllocationSearchCriteria, search, employeeId);
	}

	/*
	 * public List findMinMaxUserActivity(int resAllocId) {
	 * 
	 * List minMaxUserActivity =
	 * resourceAllocationDao.findMinMaxUserActivity(resAllocId); Map<Integer,
	 * List<Object>> minMaxUserActivities=new HashMap<Integer, List<Object>>();
	 * return minMaxUserActivity; }
	 */

	public long countAllocatedResourceEmployeeIdByProjectIdsPagination(List projectIds, String active, ResourceAllocationSearchCriteria resourceAllocationSearchCriteria, boolean search,
			Integer employeeId) {
		return resourceOnBenchDao.countAllocatedResourceEmployeeIdByProjectIdsPagination(projectIds, active, resourceAllocationSearchCriteria, search, employeeId);
	}

	public List<ResourceAllocation> getAllocations(Integer projId) {

		return resourceOnBenchDao.findActiveResourceAllocationsByProjectId(projId);
	}

	public List<Integer> findAllocatedResourceEmployeeIdByProjectIdsDashboard(List projectIds, Integer employeeId) {
		// TODO Auto-generated method stub
		return resourceOnBenchDao.findAllocatedResourceEmployeeIdByProjectIdsDashboard(projectIds, employeeId);
	}

	// US3090(Added by Maya): START: Future timesheet delete functionality
	public List<UserActivity> isFutureTimesheetpresent(Integer resourceAllocId, Date allocWeekEndDate) {
		// TODO Auto-generated method stub
		return resourceOnBenchDao.isFutureTimesheetpresent(resourceAllocId, allocWeekEndDate);
	}

	public boolean deleteFutureTimesheet(List<Integer> userActivityIdlist, String resourceAllocId, String weekEndDate) {

		return resourceOnBenchDao.deleteFutureTimesheet(userActivityIdlist, resourceAllocId, weekEndDate);
	}

	public boolean checkIfAllocationIsOpen(Integer resourceAllocId, Date allocWeekEndDate) {
		
		return resourceOnBenchDao.checkIfAllocationIsOpen(resourceAllocId, allocWeekEndDate);
	}

	public List<ResourceOnBenchDto> resourceAllocationPagination(Integer firstResult,
			Integer maxResults,
			ResourceOnBenchSearchCriteria searchCriteria,
			String activeOrAll, boolean search) {
		 List<Object[]> resourceList = resourceOnBenchDao.resourceAllocationPagination(firstResult, maxResults, searchCriteria, activeOrAll, search);
		 return this.populateResourceListToResourceOnBenchDtoList(resourceList);
	}

	private List<ResourceOnBenchDto> populateResourceListToResourceOnBenchDtoList(List<Object[]> resourceList) {
		List<ResourceOnBenchDto> resourceOnBenchList = new ArrayList<ResourceOnBenchDto>();
		if(!resourceList.isEmpty()){
			for (Object[] object : resourceList) {
				ResourceOnBenchDto resourceOnBenchDto = new ResourceOnBenchDto();
				resourceOnBenchDto.setResume((String) object[0]);
				resourceOnBenchDto.setEmployeeId((Integer) object[1]);
				resourceOnBenchDto.setYashEmpId((String) object[2]);
				resourceOnBenchDto.setEmployeeName((String) object[3]);
				resourceOnBenchDto.setDesignation_name((String) object[4]);
				resourceOnBenchDto.setAllocationType((String) object[5]);
				resourceOnBenchDto.setDateOfJoining((java.sql.Date) object[6]);
				resourceOnBenchDto.setAllocStartDate((java.sql.Date) object[7]);
				resourceOnBenchDto.setAllocEndDate((java.sql.Date) object[8]);
				resourceOnBenchList.add(resourceOnBenchDto);
			}
		}
		return resourceOnBenchList;
		
	}

	public Integer getResourceCount(Integer firstResult, Integer maxResults,	ResourceOnBenchSearchCriteria searchCriteria, String activeOrAll,		boolean search) {
		return resourceOnBenchDao.getBenchResourchCount(firstResult, maxResults, searchCriteria, activeOrAll, search);
	}

	
	public boolean getBenchResource(
			ResourceAllocation copiedResourceAllocation, String resourceIds,ResourceOnBenchService benchResource,ResourceService resourceService) {
		StringTokenizer stringTokenizer = new StringTokenizer(resourceIds, ",");
		boolean allCopied = true;
		boolean flag = true;
		while (stringTokenizer.hasMoreTokens()) {
			/** find resource on the basis of resourceIds **/
			Resource resource = resourceService.find(Integer.parseInt(stringTokenizer.nextToken()));
			List<ResourceAllocation> listRa = benchResource.findResourceAllocationsByEmployeeId(resource);
			
			if (listRa != null && listRa.size() > 0) {

				for (ResourceAllocation resourceAllocation : listRa) {
					Date newstartdate = copiedResourceAllocation.getAllocStartDate();
					Date newEndDate = copiedResourceAllocation.getAllocEndDate();
					if (resourceAllocation != null && resourceAllocation.getProjectId() != null && copiedResourceAllocation != null && copiedResourceAllocation.getProjectId() != null
							 && ((!(resourceAllocation.getId().equals(copiedResourceAllocation.getId())) && resourceAllocation.getProjectId().getId().equals(copiedResourceAllocation.getProjectId().getId())))
								&& resourceAllocation.isOverlaps(newstartdate, newEndDate)) {
					/*if (resourceAllocation != null
							&& resourceAllocation.getProjectId() != null
							&& copiedResourceAllocation != null
							&& copiedResourceAllocation.getProjectId() != null
							&& resourceAllocation.getProjectId().getId() == copiedResourceAllocation
									.getProjectId().getId()) {*/
						flag = false; 
						break;
					}
					
					if(resourceAllocation != null
							&& copiedResourceAllocation != null && resourceAllocation.getCurProj()!= null && resourceAllocation.getCurProj() == true && copiedResourceAllocation.getCurProj()!= null && copiedResourceAllocation.getCurProj() == true){
						//flag = false;
						//allCopied = false;
						resourceAllocation.setCurProj(false);
						benchResource.saveOrupdate(resourceAllocation);
						break;
					}
					
				}
			}
			
			
			if (flag && copiedResourceAllocation != null) {
				/** initialize newResourceAllocation **/
				ResourceAllocation newResourceAllocation = new ResourceAllocation();
				/** map copiedResourceAllocation to newResourceAllocation **/
				cloneResourceAllocation(newResourceAllocation,
						copiedResourceAllocation);
				/** get current logged in user **/
				UserContextDetails currentLoggedinResource = UserUtil
						.getUserContextDetails();

				Resource resource2 = UserUtil
						.userContextDetailsToResource(currentLoggedinResource);

				/** setting AllocatedBy as currentLoggedinResource **/
				newResourceAllocation.setAllocatedBy(resource2);
				/** setting UpdatedBy as currentLoggedinResource **/
				newResourceAllocation.setUpdatedBy(resource2);
				/** setting employeeId as resource **/
				newResourceAllocation.setEmployeeId(resource);
				/** persisting newResourceAllocation to DB **/

				benchResource.saveOrupdate(newResourceAllocation);
				// newResourceAllocation.persist();

				/** update allocation sequence number **/
				listRa = benchResource
						.findResourceAllocationsByEmployeeId(resource);

				Collections
						.sort(listRa,
								new ResourceAllocation.ResourceAllocationTimeComparator());
				for (int i = 1; i <= listRa.size(); i++) {
					ResourceAllocation raObj = listRa.get(i - 1);
					raObj.setAllocationSeq(i);
					benchResource.saveOrupdate(raObj);

				}
			}

		}
		
		return flag;

	}
	
	private static void cloneResourceAllocation(
			ResourceAllocation newResourceAllocation,
			ResourceAllocation resourceAllocation) {
		newResourceAllocation.setAllocationTypeId(resourceAllocation
				.getAllocationTypeId());
		newResourceAllocation.setAllocBlock(resourceAllocation.getAllocBlock());
		newResourceAllocation.setAllocEndDate(resourceAllocation
				.getAllocEndDate());
		newResourceAllocation.setAllocHrs(resourceAllocation.getAllocHrs());
		newResourceAllocation.setAllocRemarks(resourceAllocation
				.getAllocRemarks());
		newResourceAllocation.setAllocStartDate(resourceAllocation
				.getAllocStartDate());
		newResourceAllocation.setBillable(resourceAllocation.getBillable());
		newResourceAllocation.setCurProj(resourceAllocation.getCurProj());
		newResourceAllocation.setOwnershipId(resourceAllocation
				.getOwnershipId());
		newResourceAllocation.setProjectId(resourceAllocation.getProjectId());
	//	newResourceAllocation.setRateId(resourceAllocation.getRateId());
		newResourceAllocation.setBehalfManager(resourceAllocation.getBehalfManager());
		// newResourceAllocation.setTeam(resourceAllocation.getTeam());
	}
}
