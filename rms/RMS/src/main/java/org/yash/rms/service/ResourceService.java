/**
 * 
 */
package org.yash.rms.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.cxf.jaxrs.ext.search.SearchContext;
import org.yash.rms.domain.BGAdmin_Access_Rights;
import org.yash.rms.domain.Location;
import org.yash.rms.domain.OrgHierarchy;
import org.yash.rms.domain.Resource;
import org.yash.rms.domain.SkillResourcePrimary;
import org.yash.rms.domain.SkillResourceSecondary;
import org.yash.rms.domain.Skills;
import org.yash.rms.dto.EditProfileDTO;
import org.yash.rms.dto.ResourceDTO;
import org.yash.rms.form.NewEmployee;
import org.yash.rms.util.ResourceAllocationSearchCriteria;
import org.yash.rms.util.ResourceSearchCriteria;

public interface ResourceService extends RmsCRUDService<Resource> {

	public Character getTimesheetComment(Integer employeeId);

	public Resource getReportUserId(int id);

	public Resource find(int id);
	public Resource findById(Integer id);

	public Resource getLogedInResource(String userName);

	public Resource findResourcesByEmailIdEquals(String emailId);

	public Resource findResourcesByEmailIdEqualsForValidVal(String emailId, int employeeId);

	public Resource findResourcesByYashEmpIdEquals(String yashEmpId);

	public Resource getCurrentResource(String userName);

	public ResourceDTO getResourceDetailsByUserName(String userName);

	boolean isResourceBehalfManager(Integer employeeId);

	public List<Resource> findResourcesByCurrentReportingManager(Resource currentReportingManager, String activeOrAll);

	public List<Resource> findResourcesByCurrentReportingManagerTwo(Resource currentReportingManagerTwo, String activeOrAll);

	public Set<Skills> findAllPrimarySkillsByResource(Resource resource);

	public SkillResourcePrimary findResourcePrimarySkillsByskillId(Resource resource, Skills skill);

	public Set<Skills> findAllSecondarySkillsByResource(Resource resource);

	public SkillResourceSecondary findResourceSecondarySkillsByskillId(Resource resource, Skills skill);

	public List<Integer> findResourceIdByRM2RM1(Integer employeeId);

	public List<Resource> findResourceEntries(int firstResult, int maxResults, boolean active);

	public boolean save(NewEmployee form);

	public List<Resource> findResourcesByBusinessGroup(List<OrgHierarchy> businessGroup, boolean active, boolean isCurrentUserHr);

	public List<OrgHierarchy> getBUListForBGADMIN(Resource resourceIds);

	public List<Integer> findReourceIdsByBusinessGroup(List<OrgHierarchy> businessGroup);

	public boolean copyUserProfileToResource(Resource resource);

	public byte[] viewUploadedResume(Integer employeeId, String fileName);

	public List<Resource> findResourceByRM1RM2(Resource employeeId, boolean active);

	public Boolean authenticateResource(String userName, String password);

	public List<Resource> findResourcesByBusinessGroups(List<OrgHierarchy> businessGroup, List<Integer> projectIdList, Integer page, Integer size, ResourceSearchCriteria resourceSearchCriteria);

	public List<Resource> findResourcesByBusinessGroupsPagination(List<OrgHierarchy> businessGroup, List<Integer> projectIdList, Integer page, Integer size,
			ResourceSearchCriteria resourceSearchCriteria, boolean active, boolean isCurrentUserHr);

	public List<Resource> findResourcesByRM1RM2(Resource employeeId, Integer page, Integer size, ResourceSearchCriteria resourceSearchCriteria);

	public List<Resource> findResourceByRM1RM2Pagination(Resource employeeId, Integer page, Integer size, ResourceSearchCriteria resourceSearchCriteria, boolean active);

	public List<Resource> searchResources(ResourceSearchCriteria resourceSearchCriteria, List<OrgHierarchy> businessGroup,Resource currentResource, List<Integer> projectIdList, Integer page, Integer size, boolean active, boolean isCurrentUserDelManager);

	public List<Resource> findResourceEntriesPagination(int firstResult, int maxResults, ResourceSearchCriteria resourceSearchCriteria, boolean active);

	public List<Resource> findActiveResources();

	public List<Resource> findAllHeads();

	public List<Integer> findActiveResourceIdByRM2RM1(Integer employeeId);

	public List<Integer> findActiveReourceIdsByBusinessGroup(List<OrgHierarchy> businessGroup, List<Integer> projectIdList,String activeOrAll, boolean isCurrentUserHr );

	public Long countActive();

	public Long countResourceForBGADMIN(List<OrgHierarchy> businessGroup, List<Integer> projectIdList, boolean active, boolean isCurrentUserHr);

	public Long countResourceByRM1RM2(Resource employeeId, boolean active);

	public List<Integer> findReourceIdsByBusinessGroupPagination(ResourceAllocationSearchCriteria resourceAllocationSearchCriteria, List<Integer> projectIdList, List<OrgHierarchy> businessGroup,
			int firstResult, int maxResults, boolean activeOrAll, boolean search, boolean isCurrentUserHr);

	public Long countReourceIdsByBusinessGroup(List<OrgHierarchy> businessGroup, boolean ActivrOrAll);

	public List<Integer> findReourceIdsByRM1RM2Pagination(ResourceAllocationSearchCriteria resourceAllocationSearchCriteria, Integer employeeId, int firstResult, int maxResults, boolean activeOrAll,
			boolean search);

	public List<Object[]> simlateUserList(List<OrgHierarchy> businessGroup, Resource loggedInUser,  boolean activeUserFlag);

	public List<Resource> findResourceByHRROLE();

	public List<Resource> findResourcesByBGADMINROLE();

	public List getEmailIds(String role, List buId);

	public List<Resource> findAllAccountManager();

	public List<String> findAllBUs();

	public List<String> findAllActiveProjectsOfBU(String bu, String role);

	public List<Resource> findResourcesByADMINROLE();

	public List<Resource> getEligibleResourcesForCopy(int employeeId, int projId, Date alocationStartDate);

	public List<Integer> findReourceIdsByBusinessGroupDashboard(List<OrgHierarchy> businessGroup);

	public Set<BGAdmin_Access_Rights> getSavedPreferences(int employeeId);

	public void savePreference(List<OrgHierarchy> list, int employeeId);

	public Set<Location> getLocationListByBusinessGroup(List<Integer> ids);

	public List<Integer> findReourceIdsOfOwnProject(Integer employeeId);

	public List<Integer> findResourceIdsOfOwnProject(List<OrgHierarchy> buList, Integer employeeId);

	public List<Integer> findActiveResourceIdsOfOwnProject(List<OrgHierarchy> buList, Integer employeeId);

	public Set<Location> getLocationListByBusinessGroupAndProjectIds(List<Integer> ids);
	
	public EditProfileDTO getResourceDetailsByEmployeeId(Integer employeeId);
	
	public Set<Resource> findActiveReourceIdsByBusinessGroupAndProjectId(List<Integer> businessGroup, List<Integer> projectIdList, List<Integer> locationIdList);
	
	public List<Integer> findActiveReourceIdsByPojectId(List<Integer> projectIdList);

	public Resource findExistingResourcesByEmailId(String emailId);

	public List<String> findEmailById(ArrayList<Integer> intArray);
	
	public List<Resource> findResourcesByDeliveryManagerROLE();
	
	public List<Integer> findResourcesEmployeeIDByEmailIds(List<String> emails);

	//Resource Count here
	public Long getResourceCount();
	List<Resource> findResourceByEmployeeIds(List<Integer> employeeIds);
	
	public Resource findResourcesByYashEmpIdEquals(String yashEmpId,SearchContext ctx);
	
	public List<Resource> findAllResources(Integer lowerLimit, Integer upperLimit, SearchContext context);
	
	List<Resource> findResourcesByEmialIds(List<String> emailIds);

	public List<Resource> findResourceByROLE(final String role);
	
}
