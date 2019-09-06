/**
 * 
 */
package org.yash.rms.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.cxf.jaxrs.ext.search.SearchContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yash.rms.dao.ResourceAllocationDao;
import org.yash.rms.dao.ResourceDao;
import org.yash.rms.domain.BGAdmin_Access_Rights;
import org.yash.rms.domain.Location;
import org.yash.rms.domain.OrgHierarchy;
import org.yash.rms.domain.Rating;
import org.yash.rms.domain.Resource;
import org.yash.rms.domain.ResourceAllocation;
import org.yash.rms.domain.SkillResourcePrimary;
import org.yash.rms.domain.SkillResourceSecondary;
import org.yash.rms.domain.Skills;
import org.yash.rms.dto.EditProfileDTO;
import org.yash.rms.dto.ResourceDTO;
import org.yash.rms.exception.BusinessException;
import org.yash.rms.mobile.helper.ResourceDashboardHelper;
import org.yash.rms.namedquery.RmsNamedQuery;
import org.yash.rms.rest.utils.ExceptionUtil;
import org.yash.rms.service.EditProfileService;
import org.yash.rms.service.RatingService;
import org.yash.rms.service.ResourceService;
import org.yash.rms.service.SkillsService;
import org.yash.rms.util.DozerMapperUtility;
import org.yash.rms.util.ResourceAllocationSearchCriteria;
import org.yash.rms.util.ResourceSearchCriteria;

/**
 * @author arpan.badjatiya
 * 
 */
@Service("ResourceService")
public class ResourceServiceImpl implements ResourceService {

	@Autowired
	@Qualifier("ResourceDao")
	ResourceDao resourceDao;

	@Autowired
	@Qualifier("editProfileService")
	EditProfileService editProfileService;

	@Autowired
	@Qualifier("ratingService")
	private RatingService ratingService;

	@Autowired
	@Qualifier("SkillsService")
	private SkillsService skillsService;

	@Autowired
	ResourceAllocationDao resourceAllocationDao;

	@Autowired
	private DozerMapperUtility mapperUtility;

	@Autowired
	private ResourceDashboardHelper dashboardHelper;

	public boolean delete(int id) {

		return resourceDao.delete(id);
	}

	
	public boolean saveOrupdate(Resource resource) {

		return resourceDao.saveOrupdate(resource);
	}

	public List<Resource> findAll() {

		List<Resource> resourceList = new ArrayList<Resource>();
		resourceList = resourceDao.findAll();
		return resourceList;
	}

	public List<Resource> findByEntries(int firstResult, int sizeNo) {

		return resourceDao.findByEntries(firstResult, sizeNo);
	}

	public long countTotal() {
		return resourceDao.countTotal();
	}

	public Character getTimesheetComment(Integer employeeId) {

		return resourceDao.getTimesheetComment(employeeId);
	}

	@Transactional(readOnly=true)
	public Resource find(int id) {

		return resourceDao.findByEmployeeId(id);
	}

	public Resource getCurrentResource(String userName) {
		return resourceDao.getCurrentResource(userName);
	}
	
	public Resource findById(Integer id)
	{
		return resourceDao.findById(id);
	}

	public ResourceDTO getResourceDetailsByUserName(String userName) {
		// return
		// mapperUtility.convertResourceDomainToResourceDTO(resourceDao.getCurrentResource(userName));

		return dashboardHelper.convertResourceDomainToResourceDTO(resourceDao.getCurrentResource(userName));
	}

	public Resource getLogedInResource(String userName) {
		return null;
	}

	public Resource findResourcesByEmailIdEquals(String emailId) {
		return resourceDao.findResourcesByEmailIdEquals(emailId);
	}

	
	public Resource findResourcesByYashEmpIdEquals(String yashEmpId) {
		return resourceDao.findResourcesByYashEmpIdEquals(yashEmpId);
	}
	
	
	public Resource findResourcesByYashEmpIdEquals(String yashEmpId,SearchContext ctx) {
		return resourceDao.findResourcesByYashEmpIdEquals(yashEmpId,ctx);
	}

	@Transactional
	public boolean isResourceBehalfManager(Integer employeeId) {
		return resourceAllocationDao.isResourceBehalfManager(employeeId);
	}
	public boolean save(org.yash.rms.form.NewEmployee form) {

		return resourceDao.saveOrupdate(mapperUtility.convertResourceFormDTOResourceDomain(form));
	}

	public Set<Skills> findAllPrimarySkillsByResource(Resource resource) {

		return resourceDao.findAllPrimarySkillsByResource(resource);
	}

	public SkillResourcePrimary findResourcePrimarySkillsByskillId(Resource resource, Skills skill) {

		return resourceDao.findResourcePrimarySkillsByskillId(resource, skill);
	}

	public Set<Skills> findAllSecondarySkillsByResource(Resource resource) {

		return resourceDao.findAllSecondarySkillsByResource(resource);
	}

	public SkillResourceSecondary findResourceSecondarySkillsByskillId(Resource resource, Skills skill) {

		return resourceDao.findResourceSecondarySkillsByskillId(resource, skill);
	}

	public List<Resource> findResourcesByCurrentReportingManager(Resource currentReportingManager, String activeOrAll) {

		return resourceDao.findResourcesByCurrentReportingManager(currentReportingManager, activeOrAll);
	}

	public List<Resource> findResourcesByCurrentReportingManagerTwo(Resource currentReportingManagerTwo,
			String activeOrAll) {

		return resourceDao.findResourcesByCurrentReportingManagerTwo(currentReportingManagerTwo, activeOrAll);
	}

	public List<Integer> findResourceIdByRM2RM1(Integer employeeId) {

		return resourceDao.findResourceIdByRM2RM1(employeeId);
	}

	public List<Integer> findActiveResourceIdByRM2RM1(Integer employeeId) {

		return resourceDao.findActiveResourceIdByRM2RM1(employeeId);
	}

	public List<Resource> findResourceEntries(int firstResult, int maxResults, boolean active) {

		return resourceDao.findResourceEntries(firstResult, maxResults, active);
	}

	public List<Resource> findResourcesByBusinessGroup(List<OrgHierarchy> businessGroup, boolean active, boolean isCurrentUserHr) {

		return resourceDao.findResourcesByBusinessGroup(businessGroup, active, isCurrentUserHr);
	}

	public List<OrgHierarchy> getBUListForBGADMIN(Resource resourceIds) {

		return resourceDao.getBUListForBGADMIN(resourceIds);
	}

	public List<Integer> findReourceIdsByBusinessGroup(List<OrgHierarchy> businessGroup) {

		return resourceDao.findReourceIdsByBusinessGroup(businessGroup);
	}

	public List<Integer> findActiveReourceIdsByBusinessGroup(List<OrgHierarchy> businessGroup,
			List<Integer> projectIdList,String activeOrAll,boolean isCurrentUserHr) {

		return resourceDao.findActiveReourceIdsByBusinessGroup(businessGroup, projectIdList,activeOrAll,isCurrentUserHr);
	}

	public boolean copyUserProfileToResource(Resource resource) {

		return resourceDao.copyUserProfileToResource(resource);
	}

	public byte[] viewUploadedResume(Integer employeeId, String fileName) {

		return resourceDao.viewUploadedResume(employeeId, fileName);
	}

	public List<Resource> findResourceByRM1RM2(Resource employeeId, boolean active) {

		return resourceDao.findResourceByRM1RM2(employeeId, active);
	}

	// internally called by another method
	public List<Resource> findResourcesByBusinessGroups(List<OrgHierarchy> businessGroup, List<Integer> projectNameList,
			Integer page, Integer size, ResourceSearchCriteria resourceSearchCriteria) {

		return null;
	}

	// called for resource pagination when user is BG_ADMIN
	public List<Resource> findResourcesByBusinessGroupsPagination(List<OrgHierarchy> businessGroup,
			List<Integer> projectIdList, Integer page, Integer size, ResourceSearchCriteria resourceSearchCriteria,
			boolean active, boolean isCurrentUserHr) {

		return resourceDao.findResourcesByBusinessGroupsPagination(businessGroup, projectIdList, page, size,
				resourceSearchCriteria, active, isCurrentUserHr);
	}

	public List<Resource> findResourcesByRM1RM2(Resource employeeId, Integer page, Integer size,
			ResourceSearchCriteria resourceSearchCriteria) {

		return null;
	}

	// resource pagination when user role is DEL_MANAGER
	public List<Resource> findResourceByRM1RM2Pagination(Resource employeeId, Integer page, Integer size,
			ResourceSearchCriteria resourceSearchCriteria, boolean active) {

		return resourceDao.findResourceByRM1RM2Pagination(employeeId, page, size, resourceSearchCriteria, active);
	}

	// when user is searching for any record
	public List<Resource> searchResources(ResourceSearchCriteria resourceSearchCriteria,
			List<OrgHierarchy> businessGroup, Resource currentResource, List<Integer> projectIdList, Integer page, Integer size, boolean active, boolean isCurrentUserDelManager) {
		return resourceDao.searchResources(resourceSearchCriteria, businessGroup, currentResource, projectIdList, page, size, active, isCurrentUserDelManager);

	}
	
	public Boolean authenticateResource(String userName, String password) {
		return resourceDao.authenticateResource(userName, password);
	}

	public List<Resource> findResourceEntriesPagination(int firstResult, int maxResults,
			ResourceSearchCriteria resourceSearchCriteria, boolean active) {

		return resourceDao.findResourceEntriesPagination(firstResult, maxResults, resourceSearchCriteria, active);
	}

	public List<Resource> findActiveResources() {

		return resourceDao.findActiveResources();
	}

	public Long countActive() {

		return resourceDao.countActive();
	}

	public Long countResourceForBGADMIN(List<OrgHierarchy> businessGroup, List<Integer> projectIdList, boolean active,boolean isCurrentUserHr) {

		return resourceDao.countResourceForBGADMIN(businessGroup, projectIdList, active, isCurrentUserHr);
	}

	public Long countResourceByRM1RM2(Resource employeeId, boolean active) {

		return resourceDao.countResourcesByRM1RM2(employeeId, active);
	}

	public List<Integer> findReourceIdsByBusinessGroupPagination(
			ResourceAllocationSearchCriteria resourceAllocationSearchCriteria, List<Integer> projectidList,
			List<OrgHierarchy> businessGroup, int firstResult, int maxResults, boolean activeOrAll, boolean search, boolean isCurrentUserHr) {

		return resourceDao.findReourceIdsByBusinessGroupPagination(resourceAllocationSearchCriteria, projectidList,
				businessGroup, firstResult, maxResults, activeOrAll, search, isCurrentUserHr);
	}

	public Long countReourceIdsByBusinessGroup(List<OrgHierarchy> businessGroup, boolean ActivrOrAll) {

		return resourceDao.countReourceIdsByBusinessGroup(businessGroup, ActivrOrAll);
	}

	public List<Integer> findReourceIdsByRM1RM2Pagination(
			ResourceAllocationSearchCriteria resourceAllocationSearchCriteria, Integer employeeId, int firstResult,
			int maxResults, boolean activeOrAll, boolean search) {
		return resourceDao.findReourceIdsByRM1RM2Pagination(resourceAllocationSearchCriteria, employeeId, firstResult,
				maxResults, activeOrAll, search);
	}
	
	public List<Object[]> simlateUserList(List<OrgHierarchy> businessGroup, Resource loggedInUser, boolean activeUserFlag ) {

		return resourceDao.simlateUserList(businessGroup, loggedInUser, activeUserFlag);
	}

	public List<Resource> findResourceByHRROLE() {

		return resourceDao.findResourcesByHRROLE();
	}

	public List<Resource> findResourcesByBGADMINROLE() {

		return resourceDao.findResourcesByBGADMINROLE();
	}

	public List getEmailIds(String role, List buId) {

		return resourceDao.getEmailIds(role, buId);
	}

	public List<Resource> findAllAccountManager() {

		return resourceDao.findAllAccountManager();
	}

	public List<String> findAllBUs() {

		return resourceDao.findAllBUs();
	}

	public List<String> findAllActiveProjectsOfBU(String bu, String role) {

		return resourceDao.findAllProjectsOfBU(bu, role);
	}

	public List<Resource> findResourcesByADMINROLE() {

		return resourceDao.findResourcesByADMINROLE();
	}

	public Resource getReportUserId(int id) {

		return resourceDao.getReportUserId(id);
	}

	public List<Resource> getEligibleResourcesForCopy(int employeeId, int proId, Date alocationStartDate) {
		List<ResourceAllocation> allocations = resourceAllocationDao.findActiveResourceAllocationsByProjectId(proId);
		List<Resource> resources = resourceDao.getEligibleResourcesForCopy(alocationStartDate, allocations);
		return resources;
	}

	public List<Integer> findReourceIdsByBusinessGroupDashboard(List<OrgHierarchy> businessGroup) {

		return resourceDao.findReourceIdsByBusinessGroupDashboard(businessGroup);
	}

	public Resource findResourcesByEmailIdEqualsForValidVal(String emailId, int employeeId) {
		return resourceDao.findResourcesByEmailIdEqualsForValidVal(emailId, employeeId);
	}

	public Set<BGAdmin_Access_Rights> getSavedPreferences(int employeeId) {
		return resourceDao.getSavedPreferences(employeeId);
	}

	public void savePreference(List<OrgHierarchy> list, int employeeId) {
		resourceDao.savePreference(list, employeeId);

	}

	public Set<Location> getLocationListByBusinessGroup(List<Integer> ids) {

		return resourceDao.getLocationListByBusinessGroup(ids);
	}

	public List<Resource> findAllHeads() {
		return resourceDao.findAllHeads();
	}

	public List<Integer> findReourceIdsOfOwnProject(Integer employeeId) {
		return resourceDao.findReourceIdsOfOwnProject(employeeId);
	}

	public List<Integer> findResourceIdsOfOwnProject(List<OrgHierarchy> buList, Integer employeeId) {
		return resourceDao.findReourceIdsOfOwnProject(buList, employeeId);
	}

	public List<Integer> findActiveResourceIdsOfOwnProject(List<OrgHierarchy> buList, Integer employeeId) {
		return resourceDao.findActiveReourceIdsOfOwnProject(buList, employeeId);
	}

	public Set<Location> getLocationListByBusinessGroupAndProjectIds(List<Integer> ids) {

		return resourceDao.getLocationListByBusinessGroupAndProjectIds(ids);
	}

	public EditProfileDTO getResourceDetailsByEmployeeId(Integer employeeId) {

		Resource resource = resourceDao.findByEmployeeId(employeeId);

		List<Object[]> primarySkillsMappingList = editProfileService.getSkillsMapping(employeeId + "",
				RmsNamedQuery.getPrimarySkillByResourceQuery());

		List<Object[]> secondarySkillsMappingList = editProfileService.getSkillsMapping(employeeId + "",
				RmsNamedQuery.getSecondarySkillByResourceQuery());

		List<Rating> ratingList = ratingService.findAllRatings();
		List<Skills> primarySkillsList = skillsService.findPrimarySkills();
		List<Skills> secondarySkillsList = skillsService.findSecondarySkills();

		return dashboardHelper.convertResourceDomainToEditProfileDTO(resource, primarySkillsMappingList,
				secondarySkillsMappingList, ratingList, primarySkillsList, secondarySkillsList);
	}

	public Set<Resource> findActiveReourceIdsByBusinessGroupAndProjectId(List<Integer> businessGroup,
			List<Integer> projectIdList, List<Integer> locationIdList) {

		return resourceDao.findActiveReourceIdsByBusinessGroupAndProjectId(businessGroup, projectIdList,
				locationIdList);
	}

	public List<Integer> findActiveReourceIdsByPojectId(List<Integer> projectIdList) {

		return resourceDao.findActiveReourceIdsByPojectId(projectIdList);
	}

	public Resource findExistingResourcesByEmailId(String emailId) {
		return resourceDao.findExistingResourcesByEmailId(emailId);
	}

	public List<String> findEmailById(ArrayList<Integer> al) {
		return resourceDao.findEmailById(al);
	}

	public List<Resource> findResourcesByDeliveryManagerROLE() {
		return resourceDao.findResourcesByDeliveryManagerROLE();
	}

	public List<Integer> findResourcesEmployeeIDByEmailIds(List<String> emails) {
		List<Resource> resources = resourceDao.findResourcesByEmailIds(emails);
		List<Integer> resourceIDs = new ArrayList<Integer>();
		for (Resource res : resources) {
			resourceIDs.add(res.getEmployeeId());
		}
		return resourceIDs;
	}
	//Resource Count here
	public Long getResourceCount() {
		return resourceDao.getResourceCount();
	}

	public List<Resource> findResourceByEmployeeIds(List<Integer> employeeIds){
		return resourceDao.findResourceByEmployeeIds(employeeIds);
	}


	public List<Resource> findResourcesByEmialIds(List<String> emailIds) {
		return resourceDao.findResourcesByEmailIds(emailIds);
	}
	
	public List<Resource> findResourceByROLE(final String role) {
		return resourceDao.findResourceByROLE(role);
	}
	
	public List<Resource> findAllResources(Integer lowerLimit, Integer upperLimit, SearchContext context) throws BusinessException {
		try{
			return resourceDao.findAllResources(lowerLimit, upperLimit,context);
			}catch(Exception ex)
			{
			throw new BusinessException(ExceptionUtil.generateExceptionCode("Service","InfogramActiveResource",ex));	
			}
	}
	
}
