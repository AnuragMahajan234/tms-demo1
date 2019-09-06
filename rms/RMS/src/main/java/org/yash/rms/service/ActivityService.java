package org.yash.rms.service;

import java.util.List;

import org.yash.rms.domain.Activity;

public interface ActivityService extends RmsCRUDService<Activity> {

	public List<Activity> findAllActivitysByProjectId(int projectId, String activityId);

	public List<Activity> findActiveActivitysByResourceAllocationId(int resourceAllocationId);

	public Activity findById(int id);

	public List<Activity> findAllByIds(List<Integer> activityIds, List string);

	public List<Activity> findAllBySepgActivity(List<Integer> ids);

	public List<Activity> findSepgActivity(String type);

	public List<Activity> findLeftSepgActivity(String type, List<Integer> ids);

	public List<Activity> findSelectSepgActivity(List<Integer> ids);

}
