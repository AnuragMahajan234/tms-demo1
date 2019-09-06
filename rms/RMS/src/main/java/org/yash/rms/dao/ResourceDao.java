package org.yash.rms.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.cxf.jaxrs.ext.search.SearchContext;
import org.yash.rms.domain.BGAdmin_Access_Rights;
import org.yash.rms.domain.Location;
import org.yash.rms.domain.OrgHierarchy;
import org.yash.rms.domain.Resource;
import org.yash.rms.domain.ResourceAllocation;
import org.yash.rms.domain.SkillResourcePrimary;
import org.yash.rms.domain.SkillResourceSecondary;
import org.yash.rms.domain.Skills;
import org.yash.rms.exception.DaoRestException;
import org.yash.rms.util.ResourceAllocationSearchCriteria;
import org.yash.rms.util.ResourceSearchCriteria;

/**
 * @author arpan.badjatiya
 * 
 */
public interface ResourceDao extends RmsCRUDDAO<Resource> {
	
	public Resource getReportUserId(int id);

	public Character getTimesheetComment(Integer employeeId);

	public Resource findByEmployeeId(int id);

	public Resource getCurrentResource(String userName);

	public Object getCurrentResourceUserRole(Integer employeeId);

	public Resource findResourcesByEmailIdEquals(String emailId);
	
	public Resource findResourcesByEmailIdEqualsForValidVal(String emailId, int employeeId);

	public Resource findResourcesByYashEmpIdEquals(String yashEmpId);

	public Set<Skills> findAllPrimarySkillsByResource(Resource resource);

	public SkillResourcePrimary findResourcePrimarySkillsByskillId(Resource resource, Skills skill);

	public Set<Skills> findAllSecondarySkillsByResource(Resource resource);

	public SkillResourceSecondary findResourceSecondarySkillsByskillId(Resource resource, Skills skill);

	public List<Integer> findResourceIdByRM2RM1(Integer employeeId);

	public List<Resource> findResourcesByCurrentReportingManagerTwo(Resource currentReportingManagerTwo,String activeOrAll);

	public List<Resource> findResourcesByCurrentReportingManager(Resource currentReportingManager,String activeOrAll);

	public List<Resource> findResourceEntries(int firstResult, int maxResults,boolean active);

	//public Resource findResource(Integer employeeId);

	public List<Resource> findResourcesByBusinessGroup(List<OrgHierarchy> businessGroup, boolean active, boolean isCurrentUserHr);

	public List<OrgHierarchy> getBUListForBGADMIN(Resource resourceIds);
	
	public List<Integer> findReourceIdsByBusinessGroup(List<OrgHierarchy> businessGroup);
 
	public boolean copyUserProfileToResource(Resource resource);
	
	public byte[] viewUploadedResume(Integer employeeId, String resumeFileName);
	
	public List<Resource> findResourceByRM1RM2(Resource employeeId,boolean active);
	
	public Boolean authenticateResource(String userName, String password);
	
	public List<Resource> findResourcesByBusinessGroups(List<OrgHierarchy> businessGroup,List<Integer> projectNameList, Integer page, Integer size, ResourceSearchCriteria resourceSearchCriteria,boolean active,boolean isCurrentUserHr);
	
	public List<Resource> findResourcesByBusinessGroupsPagination(List<OrgHierarchy> businessGroup,List<Integer> projectNameList, Integer page, Integer size, ResourceSearchCriteria resourceSearchCriteria,boolean active, boolean isCurrentUserHr);
	
	public List<Resource> findResourcesByRM1RM2(Resource employeeId, Integer page, Integer size, ResourceSearchCriteria resourceSearchCriteria,boolean active);
	
	public List<Resource> findResourceByRM1RM2Pagination(Resource employeeId, Integer page, Integer size, ResourceSearchCriteria resourceSearchCriteria,boolean active);
	
	public List<Resource> searchResources(ResourceSearchCriteria resourceSearchCriteria, List<OrgHierarchy> businessGroup, Resource currentResource,List<Integer> projectIds,Integer page, Integer size, boolean active, boolean isCurrentUserDelManager);

	public List<Resource> findResourceEntriesPagination(int firstResult, int maxResults, ResourceSearchCriteria resourceSearchCriteria,boolean active);
	
	public List<Resource> findActiveResources( );
	
	public List<Resource> findAllHeads( );
	
	public List<Integer> findActiveResourceIdByRM2RM1(Integer employeeId);
	
	public List<Integer> findActiveReourceIdsByBusinessGroup(List<OrgHierarchy> businessGroup,List<Integer> projectIdList, String ActivrOrAll, boolean isCurrentUserHr );
	
	public Long countActive();
	
	public Long countResourceForBGADMIN(List<OrgHierarchy> businessGroup, List<Integer> projectIdList , boolean active, boolean isCurrentUserHr);
	
	public Long countResourcesByRM1RM2(Resource employeeId, boolean active);
	
	public List<Integer> findActiveReourceIds();
	
	public List<Integer> findReourceIdsByBusinessGroupPagination(ResourceAllocationSearchCriteria resourceAllocationSearchCriteria, List<Integer> projectIdList, List<OrgHierarchy> businessGroup,int firstResult, int maxResults , boolean activeOrAll , boolean search, boolean isCurrentUserHr);
	
	public Long countReourceIdsByBusinessGroup(List<OrgHierarchy> businessGroup , boolean ActivrOrAll);
	
	public List<Integer> findReourceIdsByRM1RM2Pagination(ResourceAllocationSearchCriteria resourceAllocationSearchCriteria, Integer employeeId,int firstResult, int maxResults , boolean activeOrAll , boolean search);
	
	public List<Object[]> simlateUserList(List<OrgHierarchy> businessGroup , Resource loggedInUser,  boolean activeUserFlag);
	
	public List<Resource> findResourcesByHRROLE();
	
	public List<Resource> findResourcesByBGADMINROLE();
	
	public List getEmailIds(String role, List buId);
	
	public List<Resource> findAllAccountManager();

	public List<String> findAllBUs();

	public List<String> findAllProjectsOfBU(String bu, String role);

	public List<Resource> findResourcesByADMINROLE();
	
	public List<Resource> getEligibleResourcesForCopy(Date alocationStartDate, List<ResourceAllocation> allocations);
	
	public List<Integer> findReourceIdsByBusinessGroupDashboard(List<OrgHierarchy> businessGroup);
	
	public Set<BGAdmin_Access_Rights> getSavedPreferences(int employeeId);
	
	public void savePreference(List<OrgHierarchy> list, int employeeId);
	
	public Set<Location> getLocationListByBusinessGroup(List<Integer> ids);
	
	public List<String[]> getResourceNameById(List<Integer> employeeIds);

    public List<Integer> findReourceIdsOfOwnProject(Integer employeeId);

    public List<Integer> findReourceIdsOfOwnProject(List<OrgHierarchy> buList, Integer employeeId);

    public List<Integer> findActiveReourceIdsOfOwnProject(List<OrgHierarchy> buList, Integer employeeId);
	
	public Set<Location> getLocationListByBusinessGroupAndProjectIds(List<Integer> ids);
	
	public List<Integer> findAllEmployeeIds();
	
	public Set<Resource> findActiveReourceIdsByBusinessGroupAndProjectId(List<Integer> businessGroup,List<Integer> projectIdList,List<Integer> locationIdList);
	
	public List<Integer> findActiveReourceIdsByPojectId(List<Integer> projectIdList);

	public Resource findExistingResourcesByEmailId(String emailId);
	
	public List<String> findEmailById(ArrayList<Integer> al);
	
	public void updateResumeOfResource(Resource resource)throws Exception;
	
	public List<Resource> findResourcesByDeliveryManagerROLE();
	
	public List<Resource> findResourcesByEmailIds(List<String> emailList);

	public Long getResourceCount();
	
	public Resource findById(Integer id);

	public Resource findResourcesByYashEmpIdEquals(String yashEmpId, SearchContext ctx);
	
	List<Resource> findResourceByEmployeeIds(List<Integer> employeeIds);
public List<Resource> findResourceByROLE(final String role);
public List<Resource> findAllResources(Integer lowerLimit, Integer upperLimit,SearchContext context) throws DaoRestException;
}
