/**
 * 
 */
package org.yash.rms.dao;

import java.util.List;

import org.yash.rms.domain.Activity;
import org.yash.rms.domain.AllProjectActivities;
import org.yash.rms.domain.CustomActivity;
import org.yash.rms.domain.UserActivity;

/**
 * @author arpan.badjatiya
 * 
 */
public interface ProjectActivityDao extends RmsCRUDDAO<CustomActivity> {

	public boolean save(List<CustomActivity> id);

	public List<CustomActivity> findById(int projectId,
			List<Integer> activityIds);

	public List<Integer> findCustomIds(List<Integer> defaultids, String type,
			List<Integer> customIds, Integer projectId);

	public List<Integer> findSEPGActivities(Integer projectId,
			List<String> sepglist, List<Integer> sepg);

	public CustomActivity findByActivityId(int activityId, int projectId);

	public boolean updateDefaultToInActive(List<Integer> ids,
			List<Integer> customIds, Integer projectId);

	public boolean updateCustomToInActive(List<Integer> ids,
			List<Integer> customIds, Integer projectId);

	public boolean saveActivity(List<AllProjectActivities> id);

	public List<Integer> projectTypeActivities(String type, int projectid);

	public List<CustomActivity> findBySelectedCustomId(List<Integer> id);

	public List<AllProjectActivities> findBySelectedActivityId(
			String[] activityList, int projectId, String[] customactivities);

	public List<UserActivity> findCustomIDsInTimsheet();
}
