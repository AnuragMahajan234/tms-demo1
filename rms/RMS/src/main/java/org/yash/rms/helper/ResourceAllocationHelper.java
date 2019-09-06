package org.yash.rms.helper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import org.springframework.stereotype.Component;
import org.yash.rms.domain.OrgHierarchy;
import org.yash.rms.domain.Resource;
import org.yash.rms.domain.ResourceAllocation;
import org.yash.rms.dto.UserContextDetails;
import org.yash.rms.service.ResourceAllocationService;
import org.yash.rms.service.ResourceService;
import org.yash.rms.util.UserUtil;

@Component("ResourceAllocationHelper")
public class ResourceAllocationHelper {
	

	public static List<Resource> getEligibleResourcesForProject(
			ResourceAllocationService resourceAllocationService,
			ResourceService resourceService) {
		boolean isCurrentUserAdmin = UserUtil.isCurrentUserIsAdmin();
		boolean isCurrentUserHr = UserUtil.isCurrentUserIsAdmin();
		boolean isCurrentUserDelManager = UserUtil
				.isCurrentUserIsDeliveryManager();
		Integer currentLoggedInUserId = UserUtil.getUserContextDetails()
				.getEmployeeId();
		boolean isCurrentUserBusinessGroupAdmin=UserUtil.isCurrentUserIsBusinessGroupAdmin();
		List<Integer> addedResourceList = new ArrayList<Integer>();
		if (currentLoggedInUserId != null) {
			List<Resource> list = new ArrayList<Resource>();
			List<Resource> allResources = new ArrayList<Resource>();
			if (isCurrentUserAdmin) {
				list = resourceService.findActiveResources();
			//	list.addAll(allResources);
			
			} else {
				if(isCurrentUserBusinessGroupAdmin || isCurrentUserHr)
				{
					List<OrgHierarchy> buList= UserUtil.getCurrentResource().getAccessRight();
					list= resourceService.findResourcesByBusinessGroup(buList,true,isCurrentUserHr);
				}
				else{
				Resource currentResource = resourceService
						.find(currentLoggedInUserId);

				List<Resource> currentReportintManagerList = resourceService
						.findResourcesByCurrentReportingManager(currentResource,"active");
				List<Resource> currentReportingManagerTwoList = resourceService
						.findResourcesByCurrentReportingManagerTwo(currentResource,"active");
				if (currentReportintManagerList != null
						&& currentReportintManagerList.size() > 0) {
					for (Resource resource : currentReportintManagerList) {
						list.add(resource);
						addedResourceList.add(resource.getEmployeeId());
					}

				}
				if (currentReportingManagerTwoList != null
						&& currentReportingManagerTwoList.size() > 0) {
					for (Resource resource : currentReportingManagerTwoList) {
						if (!addedResourceList.contains(resource
								.getEmployeeId())) {
							list.add(resource);

						}
					}
				}
				
				
				}
				
			}

			Collections.sort(list);
			return list;
		}
		return new ArrayList<Resource>();
	}

	public boolean copyResourceAllocations(
			ResourceAllocation copiedResourceAllocation, String resourceIds,ResourceAllocationService resourceAllocationService,ResourceService resourceService) {
		StringTokenizer stringTokenizer = new StringTokenizer(resourceIds, ",");
		boolean allCopied = true;
		boolean flag = true;
		while (stringTokenizer.hasMoreTokens()) {
			/** find resource on the basis of resourceIds **/
			Resource resource = resourceService.find(Integer.parseInt(stringTokenizer.nextToken()));
			List<ResourceAllocation> listRa = resourceAllocationService.findResourceAllocationsByEmployeeId(resource);
			
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
						resourceAllocationService.saveOrupdate(resourceAllocation);
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

				resourceAllocationService.saveOrupdate(newResourceAllocation);
				// newResourceAllocation.persist();

				/** update allocation sequence number **/
				listRa = resourceAllocationService
						.findResourceAllocationsByEmployeeId(resource);

				Collections
						.sort(listRa,
								new ResourceAllocation.ResourceAllocationTimeComparator());
				for (int i = 1; i <= listRa.size(); i++) {
					ResourceAllocation raObj = listRa.get(i - 1);
					raObj.setAllocationSeq(i);
					resourceAllocationService.saveOrupdate(raObj);

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
		newResourceAllocation.setAllocPercentage(resourceAllocation.getAllocPercentage());
		newResourceAllocation.setAllocStartDate(resourceAllocation
				.getAllocStartDate());
		newResourceAllocation.setBillable(resourceAllocation.getBillable());
		newResourceAllocation.setCurProj(resourceAllocation.getCurProj());
		newResourceAllocation.setOwnershipId(resourceAllocation
				.getOwnershipId());
		newResourceAllocation.setProjectId(resourceAllocation.getProjectId());
	//	newResourceAllocation.setRateId(resourceAllocation.getRateId());
		newResourceAllocation.setBehalfManager(resourceAllocation.getBehalfManager());
		newResourceAllocation.setResourceType(resourceAllocation.getResourceType());
		// newResourceAllocation.setTeam(resourceAllocation.getTeam());
	}

}
