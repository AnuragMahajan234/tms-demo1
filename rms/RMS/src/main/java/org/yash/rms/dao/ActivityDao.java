/**
 * 
 */
package org.yash.rms.dao;

import java.util.List;

import org.yash.rms.domain.Activity;
import org.yash.rms.domain.AllProjectActivities;
 
/**
 * @author arpan.badjatiya
 *
 */
public interface ActivityDao extends RmsCRUDDAO<Activity> {

	public Activity findById(int id);

	public List<Activity> findAllActivitysByProjectId(int projectId, String activityId);
	
	public List<Activity> findActiveActivitysByResourceAllocationId(int resourceAllocationId);
	
	public List<Activity> findAllByIds(List<Integer> activityIds,List type);
	
	public List<Activity> findAllBySepgActivity(List<Integer> ids);
	
	public List<Activity> findSepgActivity(String type);
	
	public List<Activity> findLeftSepgActivity(String type,List<Integer> ids);
	
	public List<Activity> findSelectSepgActivity(List<Integer> ids);
}
