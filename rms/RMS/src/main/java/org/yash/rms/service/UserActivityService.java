package org.yash.rms.service;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.yash.rms.domain.Resource;
import org.yash.rms.domain.ResourceAllocation;
import org.yash.rms.domain.UserActivity;
import org.yash.rms.domain.UserActivityView;
import org.yash.rms.domain.UserTimeSheet;
import org.yash.rms.dto.ProjectDTO;
import org.yash.rms.dto.TimesheetSubmissionDTO;
import org.yash.rms.dto.UserContextDetails;
import org.yash.rms.form.NewTimeSheet;

public interface UserActivityService extends RmsCRUDService<UserActivity> {

	public List<UserActivityView> getUserActivityForEmployeeBetweenDate(Integer empid, Date minWeekStartDate, Date maxWeekStartDate);

	public List<UserActivity> findUserActivitysByResourceAllocId(ResourceAllocation resourceAllocId);

	public List<TimesheetSubmissionDTO> findUserActivitysByEmployeeIdAndWeekStartDateBetween(Date minWeekStartDate, Date maxWeekStartDate, Integer employeeId, boolean isDetailedTimesheet);

	public List<NewTimeSheet> findUserActivitysByWeekStartDateBetweenAndEmployeeId(Date minWeekStartDate, Date maxWeekStartDate, Integer employeeId, boolean isDetailedTimesheet);
	
	public List<UserActivity> findUserActivitysByResourceAllocIdAndWeekStartDateEquals(ResourceAllocation resourceAllocId, Date weekStartDate);

	public boolean validateDeleteForUserActivity(Integer id);

	public boolean delete(Integer employeeId, int id);

	public void saveOrupdate(Integer employeeId, List<NewTimeSheet> list, String submitStatus, String uaPKandStatusArray[], Date weekStartDate, Date weekEndDate, HttpServletRequest httpServletRequest, boolean fromRestService);

	public Boolean findByUserActivityId(Integer id, Character isApprove, Double billedhrs, Double plannedhrs, String remarks, Integer loggedInUser, Integer resourceEmployeeId, Integer resourceAllocId, boolean fromRestService) throws Exception;

	public List<UserActivity> findUserActivitysByEmployeeId(Character timeSheetStatus, Integer loggedInUser, List<Integer> employeeIdList);

	public List<UserActivity> findUserActivitiesByProjectId(Integer projectId, Character timeSheetStatus, String status, Date weekEndDate);

	public boolean saveOrupdate(UserTimeSheet userTimeSheet);

	public void saveUserActivityStatus(Integer id, Character status, String userName, String remarks, int approvedBy, boolean fromRestService);

	public List<Integer> findUserActivitysByStatus(Character timeSheetStatus, String activeOrAll, List<Integer> projectIds, UserContextDetails userContextDetails);

	public List<UserActivity> findUserActivitysByEmployeeIdHyper(Integer employeeId, Character timeSheetStatus, String weekEndDate);
	
	public UserActivity findByUserActivityId(Integer id);
	
	public List<ProjectDTO> findProjectsByEmployeeIdWithActiveActivityModuleAndSubModule(Integer employeeId) throws Exception;
	public List<UserActivity> findUserActivitiesForOwnedProjectOfLoggedInUser(Integer employeeId, Character timeSheetStatus, String weekEndDate, Integer loggedInUserId);
	public List<Integer> findEmployeeUderIRMSRM(Resource loggedInUser, Character timeSheetStatus);
}
