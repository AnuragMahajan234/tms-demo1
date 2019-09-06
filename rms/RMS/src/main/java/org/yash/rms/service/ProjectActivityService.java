package org.yash.rms.service;

import java.util.List;

import org.yash.rms.domain.AllProjectActivities;
import org.yash.rms.domain.CustomActivity;
import org.yash.rms.domain.UserActivity;

public interface ProjectActivityService extends RmsCRUDService<CustomActivity> {

	public List<CustomActivity> findById(int projectId,
			List<Integer> activityIds);

	public CustomActivity findByActivityId(int activityId, int projectId);

	public List<Integer> findCustomIds(List<Integer> defaultids, String type,
			List<Integer> customIds, Integer projectId);

	public boolean save(List<CustomActivity> list);

	public List<Integer> findSEPGActivities(Integer projectId,
			List<String> sepglist, List<Integer> sepg);

	public boolean updateDefaultToInActive(List<Integer> ids,
			List<Integer> customIds, Integer projectId);

	public boolean updateCustomToInActive(List<Integer> ids,
			List<Integer> customIds, Integer projectId);

	public boolean saveActivity(List<AllProjectActivities> list);

	public List<Integer> projectTypeActivities(String type, int projectId);

	public List<CustomActivity> findBySelectedCustomId(List<Integer> activityIds);

	public List<AllProjectActivities> findBySelectedActivityId(
			String[] activityList, int projectId, String[] customactivities);

	public List<UserActivity> findCustomIDsInTimsheet();

}
