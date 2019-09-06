package org.yash.rms.dao;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.yash.rms.domain.Resource;
import org.yash.rms.domain.ResourceAllocation;
import org.yash.rms.domain.UserActivity;
import org.yash.rms.domain.UserActivityView;
import org.yash.rms.domain.UserTimeSheet;
import org.yash.rms.domain.UserTimeSheetDetail;
import org.yash.rms.dto.UserContextDetails;

public interface UserActivityDao extends RmsCRUDDAO<UserActivity> {

	public List<UserActivityView> getUserActivityForEmployeeBetweenDate(Integer employeeId, Date minWeekStartDate, Date maxWeekStartDate);

	public List<UserActivity> findUserActivitysByWeekStartDateBetweenAndEmployeeId(Date minWeekStartDate, Date maxWeekStartDate, Integer employeeId);

	public List<UserActivity> findUserActivitysByResourceAllocIdAndWeekStartDateEquals(ResourceAllocation resourceAllocation, Date weekStartDate);

	public UserActivity findById(Integer id);
	
	public UserTimeSheetDetail findUserTimeSheetDetailBasedOnIdAndDate(Integer id, Date date);

	public List<UserActivity> findUserActivitysByEmployeeId(Character timeSheetStatus, Integer loggedInUserId, List<Integer> employeeIdList);
	
	public List<UserActivity> findUserActivitiesByProjectId(Integer projectId, Character timeSheetStatus, String status, Date weekEndDate);
	
	public  List<UserActivity> findUserActivitysByResourceAllocId(ResourceAllocation resourceAllocation) ;

	public boolean saveOrupdate(UserTimeSheet userTimeSheet);
	
	public void saveUserActivityStatus(Integer id, Character status, String userName, String remarks, int aprovedById, boolean fromRestService);
	
	public List<Integer> findUserActivitysByStatus(Character timeSheetStatus, String activeOrAll, List<Integer> projectIds, UserContextDetails userContextDetails);

	public List<UserActivity> findUserActivitysByEmployeeIdHyper(Integer employeeId, Character timeSheetStatus, String weekEndDate);
	public List<UserActivity> findUserActivitiesForOwnedProjectOfLoggedInUser(Integer employeeId, Character timeSheetStatus, String weekEndDate, Integer loggedInUserId);
	
	public Double getTotalHours(); 
	
	public List<Integer> findEmployeeUderIRMSRM(Resource loggedInResource, Character timeSheetStatus);
}
