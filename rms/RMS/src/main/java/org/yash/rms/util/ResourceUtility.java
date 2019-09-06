package org.yash.rms.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.yash.rms.domain.Event;
import org.yash.rms.domain.Location;
import org.yash.rms.domain.OrgHierarchy;
import org.yash.rms.domain.Resource;
import org.yash.rms.domain.ResourceLoanTransfer;
import org.yash.rms.helper.EmailHelper;
import org.yash.rms.helper.UserActivityHelper;
import org.yash.rms.service.EventService;
import org.yash.rms.service.OrgHierarchyService;
import org.yash.rms.service.ResourceLoanAndTransferService;
import org.yash.rms.service.ResourceService;

@Component
public class ResourceUtility {

	@Autowired
	ResourceLoanAndTransferService resourceLoanAndTransferService;

	@Autowired
	ResourceService resourceService;

	@Autowired
	EventService eventService;

	@Autowired
	private UserUtil userUtil;
	@Autowired
	private EmailHelper emailHelper;
	@Autowired
	private UserActivityHelper userActivityHelper;

	@Autowired
	@Qualifier("OrgHierarchyService")
	OrgHierarchyService buService;

	//oldRMSResource is old one and newRMSResource is new 
	//form is new changes. 
	public Map<String, List<String>> getDeltaChangesForResource(Resource oldRMSResource, Resource newRMSResource) {

		Map<String, List<String>> information = new HashMap<String, List<String>>();
		Map<String, List<String>> hrInfromation = new LinkedHashMap<String, List<String>>();

		List<String> updatedList = null;
		List<String> hrupdatedList = null;
		boolean hrFlag = false;
		boolean baselocationchange = false;
		Resource bghName = null;
		Resource buhName = null;
		Resource hrName = null;
		Event event = null;
		String[] ccAddressHR = null;
		List<String> ccAddressList = new ArrayList<String>();
		String ccAddressListHR[] = new String[2];
		List<ResourceLoanTransfer> list = null;
		ResourceLoanTransfer loanTransfer = null;
		String previousRM2EmailId = null;
		Resource resourceRM1 = null;
		Resource resourceRM2 = null;
		String loggedInResourceEmail= userUtil.getLoggedInResource().getEmailId();
		
		 if(newRMSResource.getCurrentReportingManagerTwo()!=null && newRMSResource.getCurrentReportingManagerTwo().getEmployeeId()!=null)
			 resourceRM2= resourceService.find(newRMSResource.getCurrentReportingManagerTwo().getEmployeeId());
		 
		 if (newRMSResource.getbGHName() != null && newRMSResource.getbGHName().getEmployeeId() != null) {
			 bghName= resourceService.find(newRMSResource.getbGHName().getEmployeeId());
		}
		 if (newRMSResource.getbUHName()!= null && newRMSResource.getbUHName().getEmployeeId() != null) {
			 buhName= resourceService.find(newRMSResource.getbUHName().getEmployeeId());
		}
		 if (newRMSResource.gethRName()!= null && newRMSResource.gethRName().getEmployeeId() != null) {
			 hrName= resourceService.find(newRMSResource.gethRName().getEmployeeId());
		}
		 if (newRMSResource.getCurrentReportingManager() != null
					&& newRMSResource.getCurrentReportingManager().getEmployeeId() != null)
				resourceRM1 = resourceService.find(newRMSResource.getCurrentReportingManager().getEmployeeId());
		 
		String RM2 = null;
		if (resourceRM2 != null)
			RM2 = resourceRM2.getEmailId();
		Resource previousRM2 = null;
		System.out.println("new rm1-" + newRMSResource.getCurrentReportingManager().getEmailId());
		System.out.println("old rm1-" + oldRMSResource.getCurrentReportingManager().getEmailId());
		list = resourceLoanAndTransferService.find(newRMSResource.getEmployeeId());

		updatedList = new ArrayList<String>();

		if (oldRMSResource.getOwnership() != null) {
			updatedList.add(oldRMSResource.getOwnership().getOwnershipName());
		}
		if (newRMSResource.getOwnership() != null) {
			updatedList.add(newRMSResource.getOwnership().getOwnershipName());
		}

		if (newRMSResource.getOwnership() != null && oldRMSResource.getOwnership() != null
				&& newRMSResource.getOwnership().getId() != oldRMSResource.getOwnership().getId()) {
			hrFlag = true;
			hrInfromation.put("Ownership Status", updatedList);
		} else {
			hrInfromation.put("Ownership Status", updatedList);
		}

		information.put("Ownership Status", updatedList);
		/* ----------------------------------------------- */
		updatedList = new ArrayList<String>();
		if (oldRMSResource.getEmployeeCategory() != null) {
			updatedList.add(oldRMSResource.getEmployeeCategory().getEmployeecategoryName());
		}
		if (newRMSResource.getEmployeeCategory() != null) {
			updatedList.add(newRMSResource.getEmployeeCategory().getEmployeecategoryName());
		}
		

		information.put("Employee Category Status", updatedList);

		/* ----------------------------------------------- */

		updatedList = new ArrayList<String>();
		if (oldRMSResource.getDesignationId() != null) {
			updatedList.add(oldRMSResource.getDesignationId().getDesignationName());
		}
		if (newRMSResource.getDesignationId() != null) {
			updatedList.add(newRMSResource.getDesignationId().getDesignationName());
		}

		information.put("Designation", updatedList);
		/* ----------------------------------------------- */

		updatedList = new ArrayList<String>();
		if (oldRMSResource.getGradeId() != null) {
			updatedList.add(oldRMSResource.getGradeId().getGrade());
		}
		if (newRMSResource.getDesignationId() != null) {
			updatedList.add(newRMSResource.getGradeId().getGrade());
		}
		information.put("Grade", updatedList);

		/* ----------------------------------------------- */

		updatedList = new ArrayList<String>();
		if (oldRMSResource.getLocationId() != null) {
			updatedList.add(oldRMSResource.getLocationId().getLocation());
		}
		if (newRMSResource.getLocationId() != null) {
			updatedList.add(newRMSResource.getLocationId().getLocation());
		}

		information.put("Base Location", updatedList);

		/* ----------------------------------------------- */

		if (oldRMSResource.getLocationId() != null) {
			if (oldRMSResource.getLocationId() != newRMSResource.getLocationId()) {

				if (oldRMSResource.getLocationId().getId() != newRMSResource.getLocationId().getId()) {
					baselocationchange = true;
				}

			}
		}
		/* ----------------------------------------------- */
		updatedList = new ArrayList<String>();
		if (oldRMSResource.getDeploymentLocation() != null) {
		
			if (newRMSResource.getDeploymentLocation() != null && oldRMSResource.getDeploymentLocation().getLocation() != null) {
				updatedList.add(oldRMSResource.getDeploymentLocation().getLocation());
			} else {
				updatedList.add("None");
			}
		}
		Location deploymentLocation = newRMSResource.getDeploymentLocation();
		if (newRMSResource.getDeploymentLocation() != null) {
			updatedList.add(newRMSResource.getDeploymentLocation().getLocation());
		}

		information.put("Current Location", updatedList);

		/* ----------------------------------------------- */

		if (oldRMSResource.getDeploymentLocation() != null && oldRMSResource.getDeploymentLocation().getLocation() != null) {
			if (newRMSResource.getDeploymentLocation() != null) {
				if (oldRMSResource.getDeploymentLocation().getId() != newRMSResource.getDeploymentLocation().getId()) {
					hrFlag = true;
					hrInfromation.put("Current Location ", updatedList);
				} else {
					hrInfromation.put("Current Location ", updatedList);
				}
			}
		} else {
			hrupdatedList = new ArrayList<String>();
			hrupdatedList.add(newRMSResource.getDeploymentLocation().getLocation());
			hrupdatedList.add(oldRMSResource.getDeploymentLocation().getLocation()); // need to add NPE check. (samiksha)
			hrFlag = true;
			hrInfromation.put("Current Location", hrupdatedList);
		}

		/* ----------------------------------------------- */
		updatedList = new ArrayList<String>();

		if (oldRMSResource.getBuId() != null) {
			if (oldRMSResource.getBuId().getParentId() != null) {
				updatedList.add(oldRMSResource.getBuId().getParentId().getName() + "-" + oldRMSResource.getBuId().getName());
			}

		}
		if (newRMSResource.getBuId() != null) {
			if (newRMSResource.getBuId().getParentId() != null) {
				updatedList.add(newRMSResource.getBuId().getParentId().getName() + "-" + newRMSResource.getBuId().getName());
			}
		}

		information.put("Parent BG-BU", updatedList);

		// -------------------------------------------------------------------------------------------------------------------

		String previousCurrentBuId = "";
		//OrgHierarchy currentBu = buService.fingOrgHierarchiesById(newRMSResource.getCurrentBuId().getId());
		String newValueCurrentBuId = "";
		updatedList = new ArrayList<String>();
		
		if (oldRMSResource.getCurrentBuId() != null) {
			if (oldRMSResource.getCurrentBuId().getParentId() != null) {
				updatedList.add(oldRMSResource.getCurrentBuId().getParentId().getName() + "-" + oldRMSResource.getCurrentBuId().getName());
			}

		}
		if (newRMSResource.getCurrentBuId() != null) {
			if (newRMSResource.getCurrentBuId().getParentId() != null) {
				updatedList.add(newRMSResource.getCurrentBuId().getParentId().getName() + "-" + newRMSResource.getCurrentBuId().getName());
			}
		}

		updatedList.add(previousCurrentBuId);
		updatedList.add(newValueCurrentBuId);
		information.put("Current BG-BU", updatedList);

		if (oldRMSResource.getCurrentBuId() != null && newRMSResource.getCurrentBuId() != null) {
			if (oldRMSResource.getCurrentBuId().getId().intValue() != newRMSResource.getCurrentBuId().getId().intValue()) {
				hrFlag = true;
				hrInfromation.put("Current BG-BU", updatedList);
			} else {
				hrInfromation.put("Current BG-BU", updatedList);
			}
		}

		

			String previousRm1 = null;
			String newValueRm1 = null;
			if (oldRMSResource.getCurrentReportingManager() != null && oldRMSResource.getCurrentReportingManager().getEmployeeId() != null)
				previousRm1 = oldRMSResource.getCurrentReportingManager().getFirstName().concat(" ").concat(oldRMSResource.getCurrentReportingManager().getLastName());
			else
				previousRm1 = "None";
			if (resourceRM1 != null)
				newValueRm1 = resourceRM1.getFirstName().concat(" ").concat(resourceRM1.getLastName());
			else
				newValueRm1 = "None";
			updatedList = new ArrayList<String>();
			updatedList.add(previousRm1);
			updatedList.add(newValueRm1);
			information.put("RM1", updatedList);
			// }
			if (oldRMSResource.getCurrentReportingManager() != null
					&& oldRMSResource.getCurrentReportingManager().getEmployeeId() != null) {
				if (!oldRMSResource.getCurrentReportingManager().getEmployeeId()
						.equals(newRMSResource.getCurrentReportingManager().getEmployeeId())) {
					hrFlag = true;
					hrInfromation.put("RM1", updatedList);
				} else {
					hrInfromation.put("RM1", updatedList);
				}
			} else {
				hrupdatedList = new ArrayList<String>();
				hrupdatedList.add(previousRm1);
				hrupdatedList.add(newValueRm1);
				hrFlag = true;
				hrInfromation.put("RM1", hrupdatedList);
			}

		/*	if ((oldRMSResource.getCurrentReportingManagerTwo() != null
					&& oldRMSResource.getCurrentReportingManagerTwo().getEmployeeId() != null
					&& (newRMSResource.getCurrentReportingManagerTwo() == null
							|| newRMSResource.getCurrentReportingManagerTwo().getEmployeeId() == null))
					|| ((oldRMSResource.getCurrentReportingManagerTwo() == null
							|| oldRMSResource.getCurrentReportingManagerTwo().getEmployeeId() == null)
							&& (newRMSResource.getCurrentReportingManagerTwo() != null
									|| newRMSResource.getCurrentReportingManagerTwo().getEmployeeId() != null))
					|| (oldRMSResource.getCurrentReportingManagerTwo().getEmployeeId().intValue() != newRMSResource
							.getCurrentReportingManagerTwo().getEmployeeId().intValue())) {*/
				String previousRm2 = null;
				String newValueRm2 = null;
				if (oldRMSResource.getCurrentReportingManagerTwo() != null
						&& oldRMSResource.getCurrentReportingManagerTwo().getEmployeeId() != null)
					previousRm2 = oldRMSResource.getCurrentReportingManagerTwo().getFirstName().concat(" ")
							.concat(oldRMSResource.getCurrentReportingManagerTwo().getLastName());
				else
					previousRm2 = "None";
				if (resourceRM2 != null)
					newValueRm2 = resourceRM2.getFirstName().concat(" ").concat(resourceRM2.getLastName());
				else
					newValueRm2 = "None";
				updatedList = new ArrayList<String>();
				updatedList.add(previousRm2);
				updatedList.add(newValueRm2);
				information.put("RM2", updatedList);
				// }
				if (oldRMSResource.getCurrentReportingManagerTwo() != null
						&& oldRMSResource.getCurrentReportingManagerTwo().getEmployeeId() != null) {
					if (!oldRMSResource.getCurrentReportingManagerTwo().getEmployeeId()
							.equals(newRMSResource.getCurrentReportingManagerTwo().getEmployeeId())) {
						hrFlag = true;
						hrInfromation.put("RM2", updatedList);
					} else {
						hrInfromation.put("RM2", updatedList);
					}

				} else {
					hrupdatedList = new ArrayList<String>();
					hrupdatedList.add(previousRm1);
					hrupdatedList.add(newValueRm1);
					hrFlag = true;
					hrInfromation.put("RM2", hrupdatedList);
				}

				DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

				// if(!oldRMSResource.getDateOfJoining().equals(newRMSResource.getDateOfJoining()))
				// {
				String previousDateOfJoining = df.format(oldRMSResource.getDateOfJoining());
				String newValueDateOfJoining = df.format(newRMSResource.getDateOfJoining());

				updatedList = new ArrayList<String>();
				updatedList.add(previousDateOfJoining);
				updatedList.add(newValueDateOfJoining);
				information.put("DOJ", updatedList);
				// }

				if (oldRMSResource.getConfirmationDate() != null || newRMSResource.getConfirmationDate() != null) {
					String previous = "";
					String newValue = "";
					// String value = checkValue(oldRMSResource.getConfirmationDate());
					// if(!oldRMSResource.getConfirmationDate().equals(newRMSResource.getConfirmationDate()))
					// {
					if (oldRMSResource.getConfirmationDate() != null) {
						previous = df.format(oldRMSResource.getConfirmationDate());
					}
					if (newRMSResource.getConfirmationDate() != null) {
						newValue = df.format(newRMSResource.getConfirmationDate());
					}

					updatedList = new ArrayList<String>();
					updatedList.add(previous);
					updatedList.add(newValue);
					information.put("Confirmation Date", updatedList);
					// }
				}
				if (oldRMSResource.getLastAppraisal() != null || newRMSResource.getLastAppraisal() != null) {
					// if(!oldRMSResource.getLastAppraisal().equals(newRMSResource.getLastAppraisal()))
					// {
					String previousLastAppraisal = "";
					String newValueLastAppraisal = "";
					if (oldRMSResource.getLastAppraisal() != null) {
						previousLastAppraisal = df.format(oldRMSResource.getLastAppraisal());
					}
					if (newRMSResource.getLastAppraisal() != null) {
						newValueLastAppraisal = df.format(newRMSResource.getLastAppraisal());
					}
					updatedList = new ArrayList<String>();
					updatedList.add(previousLastAppraisal);
					updatedList.add(newValueLastAppraisal);
					information.put("Appraisal Date1", updatedList);
					// }
				}

				if (oldRMSResource.getPenultimateAppraisal() != null || newRMSResource.getPenultimateAppraisal() != null) {
					// if(!oldRMSResource.getPenultimateAppraisal().equals(newRMSResource.getPenultimateAppraisal()))
					// {
					String previousPenultimateAppraisal = "";
					String newValuePenultimateAppraisal = "";
					if (oldRMSResource.getPenultimateAppraisal() != null) {
						previousPenultimateAppraisal = df.format(oldRMSResource.getPenultimateAppraisal());
					}
					if (newRMSResource.getPenultimateAppraisal() != null) {
						newValuePenultimateAppraisal = df.format(newRMSResource.getPenultimateAppraisal());
					}
					updatedList = new ArrayList<String>();
					updatedList.add(previousPenultimateAppraisal);
					updatedList.add(newValuePenultimateAppraisal);
					information.put("Appraisal Date2", updatedList);
					// }
				}

				if (oldRMSResource.getReleaseDate() != null || newRMSResource.getReleaseDate() != null) {
					// if(!oldRMSResource.getReleaseDate().equals(newRMSResource.getReleaseDate()))
					// {
					String previousReleaseDate = "";
					String newValueReleaseDate = "";
					if (oldRMSResource.getReleaseDate() != null) {
						previousReleaseDate = df.format(oldRMSResource.getReleaseDate());
					}
					if (newRMSResource.getReleaseDate() != null) {
						newValueReleaseDate = df.format(newRMSResource.getReleaseDate());
					}
					updatedList = new ArrayList<String>();
					updatedList.add(previousReleaseDate);
					updatedList.add(newValueReleaseDate);
					information.put("Release Date", updatedList);
					// }
				}

				if (oldRMSResource.getTransferDate() != null || newRMSResource.getTransferDate() != null) {
					// if(!oldRMSResource.getTransferDate().equals(newRMSResource.getTransferDate()))
					// {
					String previousTransferDate = "";
					String newValueTransferDate = "";
					if (oldRMSResource.getTransferDate() != null) {
						previousTransferDate = df.format(oldRMSResource.getTransferDate());
					}
					if (newRMSResource.getTransferDate() != null) {
						newValueTransferDate = df.format(newRMSResource.getTransferDate());
					}
					updatedList = new ArrayList<String>();
					updatedList.add(previousTransferDate);
					updatedList.add(newValueTransferDate);
					information.put("Loan/Transfer Date From", updatedList);
					// }
					// else {
					if (oldRMSResource.getTransferDate() != null && newRMSResource.getTransferDate() != null) {
						if (oldRMSResource.getTransferDate().equals(newRMSResource.getTransferDate())) {
							// If transfer date is not changed then the entry should not go in
							// resourceLoanTransfer table
							newRMSResource.setTransferDate(null);
						}
					}
				}

				// added loan/transfer Date to

				if (oldRMSResource.getEndTransferDate() != null || newRMSResource.getEndTransferDate() != null) {
					String previousTransferEndDate = "";
					String newEndTransferDate = "";
					if (oldRMSResource.getEndTransferDate() != null) {
						previousTransferEndDate = df.format(oldRMSResource.getEndTransferDate());
					}
					if (newRMSResource.getEndTransferDate() != null) {
						newEndTransferDate = df.format(newRMSResource.getEndTransferDate());
					}
					updatedList = new ArrayList<String>();
					updatedList.add(previousTransferEndDate);
					updatedList.add(newEndTransferDate);
					information.put("Loan/Transfer Date To", updatedList);

					if (oldRMSResource.getEndTransferDate() != null && newRMSResource.getEndTransferDate() != null) {
						if (oldRMSResource.getEndTransferDate().equals(newRMSResource.getEndTransferDate())) {
							// If transfer date is not changed then the entry should not go in
							// resourceLoanTransfer table
							newRMSResource.setEndTransferDate(null);
						}
					}
				}

				// adding all the rest fields
				// adding BGH Name Field
				if ((newRMSResource.getbGHName() != null && newRMSResource.getbGHName().getEmployeeId() != null)
						|| (oldRMSResource.getbGHName() != null && oldRMSResource.getbGHName().getEmployeeId() != null)) {
					String previousbGHName = "";
					String newValuebGHName = "";

					if (bghName != null) {
						newValuebGHName = bghName.getEmployeeName();
					}
					if (oldRMSResource.getbGHName() != null) {
						Resource previousbGH = oldRMSResource.getbGHName();
						if (previousbGH != null) {
							previousbGHName = previousbGH.getEmployeeName();
						}
					}
					updatedList = new ArrayList<String>();
					updatedList.add(previousbGHName);
					updatedList.add(newValuebGHName);
					information.put("BGH Name", updatedList);
				}

				// adding BGH Comments Field
				if ((newRMSResource.getbGHComments() != null && !newRMSResource.getbGHComments().equals(""))
						|| (oldRMSResource.getbGHComments() != null && !oldRMSResource.getbGHComments().equals(""))) {
					String previousbGHComments = "";
					String newValuebGHComments = "";
					if (oldRMSResource.getbGHComments() != null && !oldRMSResource.getbGHComments().equals("")) {
						previousbGHComments = oldRMSResource.getbGHComments();
					}
					if (newRMSResource.getbGHComments() != null && !newRMSResource.getbGHComments().equals("")) {
						newValuebGHComments = newRMSResource.getbGHComments();
					}
					updatedList = new ArrayList<String>();
					updatedList.add(previousbGHComments);
					updatedList.add(newValuebGHComments);
					information.put("BGH Comments", updatedList);

				}

				// adding BGH Comments Date Field
				if (newRMSResource.getbGCommentsTimestamp() != null || oldRMSResource.getbGCommentsTimestamp() != null) {
					String previousbGCommentsTimestamp = "";
					String newValuebGCommentsTimestamp = "";
					if (newRMSResource.getbGCommentsTimestamp() != null) {
						newValuebGCommentsTimestamp = df.format(newRMSResource.getbGCommentsTimestamp());
					}
					if (oldRMSResource.getbGCommentsTimestamp() != null) {
						previousbGCommentsTimestamp = df.format(oldRMSResource.getbGCommentsTimestamp());
					}

					updatedList = new ArrayList<String>();
					updatedList.add(previousbGCommentsTimestamp);
					updatedList.add(newValuebGCommentsTimestamp);
					information.put("BGH Comments Date", updatedList);
				}

				// adding BUH Name Field
				if ((newRMSResource.getbUHName() != null && newRMSResource.getbUHName().getEmployeeId() != null)
						|| (oldRMSResource.getbUHName() != null && oldRMSResource.getbUHName().getEmployeeId() != null)) {
					String previousbUHName = "";
					String newValuebUHName = "";
					if (buhName != null) {
						newValuebUHName = buhName.getEmployeeName();
					}
					if (oldRMSResource.getbUHName() != null) {
						Resource previousbUH = oldRMSResource.getbUHName();
						if (previousbUH != null) {
							previousbUHName = previousbUH.getEmployeeName();
						}
					}
					updatedList = new ArrayList<String>();
					updatedList.add(previousbUHName);
					updatedList.add(newValuebUHName);
					information.put("BUH Name", updatedList);
				}

				// adding BUH Comments Field
				if ((newRMSResource.getbUHComments() != null && !newRMSResource.getbUHComments().equals(""))
						|| (oldRMSResource.getbUHComments() != null && !oldRMSResource.getbUHComments().equals(""))) {

					String previousbUHComments = "";
					String newValuebUHComments = "";

					if (oldRMSResource.getbUHComments() != null && !oldRMSResource.getbUHComments().equals("")) {
						previousbUHComments = oldRMSResource.getbUHComments();
					}
					if (newRMSResource.getbUHComments() != null && !newRMSResource.getbUHComments().equals("")) {
						newValuebUHComments = newRMSResource.getbUHComments();
					}
					updatedList = new ArrayList<String>();
					updatedList.add(previousbUHComments);
					updatedList.add(newValuebUHComments);
					information.put("BUH Comments", updatedList);

				}

				// adding BUH Comments Date Field
				if (newRMSResource.getbUCommentTimestamp() != null || oldRMSResource.getbGCommentsTimestamp() != null) {
					String previousbUCommentsTimestamp = "";
					String newValuebUCommentsTimestamp = "";

					if (newRMSResource.getbGCommentsTimestamp() != null) {
						newValuebUCommentsTimestamp = df.format(newRMSResource.getbUCommentTimestamp());
					}
					if (oldRMSResource.getbGCommentsTimestamp() != null) {
						previousbUCommentsTimestamp = df.format(oldRMSResource.getbGCommentsTimestamp());
					}

					updatedList = new ArrayList<String>();
					updatedList.add(previousbUCommentsTimestamp);
					updatedList.add(newValuebUCommentsTimestamp);
					information.put("BUH Comments Date", updatedList);
				} else {
					// If transfer date is not changed then the entry should not go in
					// resourceLoanTransfer table
					newRMSResource.setbUCommentTimestamp(null);
				}

				// adding HR Name Field
				if ((newRMSResource.gethRName() != null && newRMSResource.gethRName().getEmployeeId() != null)
						|| (oldRMSResource.gethRName() != null && oldRMSResource.gethRName().getEmployeeId() != null)) {
					String previoushRName = "";
					String newValuehRName = "";
					if (hrName != null) {
						newValuehRName = hrName.getEmployeeName();
					}
					if (oldRMSResource.gethRName() != null) {
						Resource previousHr = oldRMSResource.gethRName();
						if (previousHr != null) {
							previoushRName = previousHr.getEmployeeName();
						}
					}
					updatedList = new ArrayList<String>();
					updatedList.add(previoushRName);
					updatedList.add(newValuehRName);
					information.put("HR Name", updatedList);

				}

				// adding HR Comments Field
				if ((newRMSResource.gethRComments() != null && !newRMSResource.gethRComments().equals(""))
						|| (oldRMSResource.gethRComments() != null && !oldRMSResource.gethRComments().equals(""))) {
					String previoushRComments = "";
					String newValuehRComments = "";

					if (oldRMSResource.gethRComments() != null && !oldRMSResource.gethRComments().equals("")) {
						previoushRComments = oldRMSResource.gethRComments();
					}
					if (newRMSResource.gethRComments() != null && !newRMSResource.gethRComments().equals("")) {
						newValuehRComments = newRMSResource.gethRComments();
					}
					updatedList = new ArrayList<String>();
					updatedList.add(previoushRComments);
					updatedList.add(newValuehRComments);
					information.put("HR Comments", updatedList);
				}

				// adding HR Comments Date Field
				if (newRMSResource.gethRCommentTimestamp() != null || oldRMSResource.gethRCommentTimestamp() != null) {
					String previoushRCommentsTimestamp = "";
					String newValuehRCommentsTimestamp = "";

					if (newRMSResource.gethRCommentTimestamp() != null) {
						newValuehRCommentsTimestamp = df.format(newRMSResource.gethRCommentTimestamp());
					}
					if (oldRMSResource.gethRCommentTimestamp() != null) {
						previoushRCommentsTimestamp = df.format(oldRMSResource.gethRCommentTimestamp());
					}
					updatedList = new ArrayList<String>();
					updatedList.add(previoushRCommentsTimestamp);
					updatedList.add(newValuehRCommentsTimestamp);
					information.put("HR Comments Date", updatedList);
				} else {
					// If transfer date is not changed then the entry should not go in
					// resourceLoanTransfer table
					newRMSResource.sethRCommentTimestamp(null);
				}

				if (list.size() > 0 && !list.isEmpty()) {
					loanTransfer = list.get(0);
					if (loanTransfer.getCurrentReportingManagerTwo() != null
							&& !loanTransfer.getCurrentReportingManagerTwo().getEmailId().equals(RM2)) {
						// previousRM2EmailId=loanTransfer.getCurrentReportingManagerTwo().getEmailId();
						previousRM2EmailId = loanTransfer.getCurrentReportingManagerTwo().getEmailId();
					}
				} else {
					// previousRM2=
					// resourceService.find(newRMSResource.getResourceId().getEmployeeId());
					if (oldRMSResource.getCurrentReportingManagerTwo() != null) {
						if (!oldRMSResource.getCurrentReportingManagerTwo().getEmailId().equals(RM2)) {
							// previousRM2EmailId=loanTransfer.getCurrentReportingManagerTwo().getEmailId();
							previousRM2EmailId = oldRMSResource.getCurrentReportingManagerTwo().getEmailId();
						}
					}

				}

				// }

				// #816 - receivers in CC, parent and current BGBU, RMG, Central HR and,
				// location HR (basis on location per below)
				// ccAddressListHR.add(Constants.CENTRAL_HR);
				// get previous and current RM1 and RM2 fields to be copied for mail.
				if (newRMSResource.getCurrentReportingManager() != null) {
					ccAddressList.add(resourceRM1.getEmailId());
				}
				if (newRMSResource.getCurrentReportingManagerTwo() != null) {
					if (newRMSResource.getCurrentReportingManagerTwo() != newRMSResource.getCurrentReportingManager()) {
						ccAddressList.add(RM2);
					}
				}
				// check if the previous and current are not same then copy them too
				if (oldRMSResource.getCurrentReportingManager() != null) {
					if (newRMSResource.getCurrentReportingManager() != null) {
						if (oldRMSResource.getCurrentReportingManager() != newRMSResource.getCurrentReportingManager()
								|| oldRMSResource.getCurrentReportingManager() != newRMSResource
										.getCurrentReportingManagerTwo()) {
							Resource previousRM1 = resourceService
									.find(oldRMSResource.getCurrentReportingManager().getEmployeeId());
							if (!ccAddressList.contains(previousRM1.getEmailId())) {
								ccAddressList.add(previousRM1.getEmailId());
							}

						}
					}

					// if(!ccAddressList.contains(previousResourceRM2.getEmailId()))
					// ccAddressList.add(previousResourceRM2.getEmailId());
				}
				if (newRMSResource.getEmailId() != null) {
					if(newRMSResource.getEventId()!=null) {
						if (!(newRMSResource.getEventId().getId() == 10)) {
							ccAddressList.add(newRMSResource.getEmailId());
						}
					}
				}

				if (oldRMSResource.getCurrentReportingManagerTwo() != null) {
					if (newRMSResource.getCurrentReportingManager() != null) {
						if (oldRMSResource.getCurrentReportingManagerTwo() != newRMSResource.getCurrentReportingManager()
								|| oldRMSResource.getCurrentReportingManagerTwo() != newRMSResource
										.getCurrentReportingManagerTwo()) {
							Resource previousResourceRM2 = resourceService
									.find(oldRMSResource.getCurrentReportingManagerTwo().getEmployeeId());
							if (!ccAddressList.contains(previousResourceRM2.getEmailId())) {
								ccAddressList.add(previousResourceRM2.getEmailId());
							}

						}
					}
					if (!ccAddressList.contains(oldRMSResource.getCurrentReportingManagerTwo().getEmailId()))
						ccAddressList.add(oldRMSResource.getCurrentReportingManagerTwo().getEmailId());
				}
				// check Location change and add HR accordingly
				if (oldRMSResource.getLocationId() != null) {
					if (oldRMSResource.getLocationId() != newRMSResource.getLocationId()) {
						String currentResourcelocation = oldRMSResource.getLocationId().getLocationHrEmailId();

						String loanFormLocation = newRMSResource.getDeploymentLocation().getLocationHrEmailId();
						deploymentLocation.getLocationHrEmailId();
						// if (!ccAddressListHR.contains(currentResourcelocation) ||
						// !ccAddressListHR.contains(loanFormLocation)){
						ccAddressListHR[0] = oldRMSResource.getLocationId().getLocationHrEmailId();
						if (baselocationchange == true) {
							// ccAddressListHR[1] = newRMSResource.getLocationId().getLocationHrEmailId();
							ccAddressListHR[1] = deploymentLocation.getLocationHrEmailId();
						}

					}

				}

				/*
				 * if (oldRMSResource.getDeploymentLocation()!= null ) { if
				 * (oldRMSResource.getDeploymentLocation() != newRMSResource.getDeploymentLocation()
				 * || oldRMSResource.getDeploymentLocation() != newRMSResource.getLocationId()) {
				 * String currentResourceDeploymentLocation =
				 * Integer.toString(oldRMSResource.getDeploymentLocation().getId()); String
				 * loanFormLocation =
				 * Integer.toString(newRMSResource.getDeploymentLocation().getId()); if
				 * (!ccAddressListHR.contains(currentResourceDeploymentLocation) ||
				 * !ccAddressListHR.contains(loanFormLocation)){
				 * ccAddressListHR.add(Constants.getProperty(currentResourceDeploymentLocation))
				 * ; if(baselocationchange== true) {
				 * ccAddressListHR.add(Constants.getProperty(loanFormLocation)); } }
				 * 
				 * } }
				 */

				String[] toAddresscc = new String[1];
				int size = ccAddressList.size();
				toAddresscc = new String[size];
				for (int i = 0; i < size; i++) {
					toAddresscc[i] = ccAddressList.get(i);

				}

				/*
				 * int hrSize= ccAddressListHR.size(); if(hrFlag){ ccAddressHR = new
				 * String[hrSize]; for (int i = 0; i < size ; i++) {
				 * if(!ccAddressListHR.get(i).equals("")) ccAddressHR[i]=ccAddressListHR.get(i);
				 * } }
				 */

				// boolean success=resourceLoanAndTransferService.save(newRMSResource);
				Map<String, Object> model = new HashMap<String, Object>();
				//System.out.println(oldRMSResource.getEmployeeName());
				String subject = "-" + oldRMSResource.getFirstName() + " " + oldRMSResource.getLastName() + "[" + oldRMSResource.getYashEmpId() + "]";
				if (newRMSResource.getEventId() != null) {
					event = eventService.findById(newRMSResource.getEventId().getId());
					subject = subject + "-" + event.getEvent().toString() + " in RMS";

				}
				if (hrFlag) {
					userActivityHelper.setEmailContentForLoanTransfer(model,
							oldRMSResource.getFirstName().concat(" ").concat(oldRMSResource.getLastName()), hrInfromation,
							subject, oldRMSResource, hrFlag);
					Set<String> toAddressHR = new HashSet<String>();
					// toAddressHR [2]= "";
					// toAddressHR.add(Constants.CENTRAL_HR);
					// List<String> arrOfStr = new ArrayList<String>();
					int j;
					for (int i = 0; i < ccAddressListHR.length; i++) {
						j = i + 1;
						if (ccAddressListHR[i] != null) {
							if (ccAddressListHR[i].contains(
									";") /* || ccAddressListHR[i].contains(";") || ccAddressListHR[i].contains(" ") */) {
								String[] arrOfStr = ccAddressListHR[i].split(";");
								/*
								 * String[] arrOfStrSemiColon = null; String[] arrOfStrSpace = null; for(int k
								 * =0 ; k<arrOfStrComma.length ; k++) { arrOfStrSemiColon =
								 * arrOfStrComma[k].split(";"); } for(int l =0 ; l<arrOfStrSemiColon.length ;
								 * l++) { arrOfStrSpace = arrOfStrSemiColon[l].split(" "); }
								 * 
								 * arrOfStr.addAll(Arrays.asList(arrOfStrComma));
								 * arrOfStr.addAll(Arrays.asList(arrOfStrSemiColon));
								 * arrOfStr.addAll(Arrays.asList(arrOfStrSpace));
								 */for (String a : arrOfStr)
									toAddressHR.add(a);
							} else {
								toAddressHR.add(ccAddressListHR[i]);
							}
						}
					}
 					emailHelper.sendEmailNotification(model, toAddressHR, null);

				}
				userActivityHelper.setEmailContentForLoanTransfer(model, oldRMSResource.getFirstName().concat(" ").concat(oldRMSResource.getLastName()) ,information,subject,oldRMSResource,false);
				Set<String> toAddress = new HashSet<String>();
				toAddress.add(loggedInResourceEmail);
				
    			emailHelper.sendEmailNotification(model, toAddress,toAddresscc);

			
		
		return information;
	}
	
}