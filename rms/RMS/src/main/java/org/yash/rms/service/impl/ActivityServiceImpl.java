/**
 * 
 */
package org.yash.rms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.yash.rms.dao.ActivityDao;
import org.yash.rms.domain.Activity;
import org.yash.rms.service.ActivityService;
import org.yash.rms.util.DozerMapperUtility;

/**
 * @author arpan.badjatiya
 *
 */
@Service("ActivityService")
public class ActivityServiceImpl implements ActivityService {

	@Autowired @Qualifier("ActivityDao")
	private ActivityDao activityDao;

	@Autowired
	private DozerMapperUtility mapperUtility;
	public boolean delete(int id) {
		return activityDao.delete(id);
	}

	public boolean saveOrupdate(Activity activity) {
		System.out.println("DozerMapperUtility for billingScale::"+mapperUtility.convertDomainToDTO(activity));
		System.out.println("DozerMapperUtility for billingScale::"+mapperUtility.convertDTOObjectToDomain(mapperUtility.convertDTOObjectToDomain(activity)));
		return activityDao.saveOrupdate(activity);
	}

	public List<Activity> findAll() {
		return activityDao.findAll();
	}

	public List<Activity> findByEntries(int firstResult, int sizeNo) {
		return activityDao.findByEntries(firstResult, sizeNo);
	}

	public long countTotal() {
		return activityDao.countTotal();
	}


	public Activity findById(int id) {
		// TODO Auto-generated method stub
		return activityDao.findById(id);
	}

	public List<Activity> findAllByIds(List<Integer> activityIds,List type) {
		// TODO Auto-generated method stub
		return activityDao.findAllByIds(activityIds,type);
	}
	
	public List<Activity> findAllBySepgActivity(List<Integer> ids){
		
		return activityDao.findAllBySepgActivity(ids);
	}

	public List<Activity> findSepgActivity(String type) {
		// TODO Auto-generated method stub
		return activityDao.findSepgActivity(type);
	}

	public List<Activity> findLeftSepgActivity(String type, List<Integer> ids) {
		// TODO Auto-generated method stub
		return activityDao.findLeftSepgActivity(type, ids);
	}

	public List<Activity> findSelectSepgActivity(List<Integer> ids) {
		// TODO Auto-generated method stub
		return activityDao.findSelectSepgActivity(ids);
	}
	public List<Activity> findAllActivitysByProjectId(int projectId, String activityId) {
		return activityDao.findAllActivitysByProjectId(projectId, activityId);
	}

	public List<Activity> findActiveActivitysByResourceAllocationId(int resourceAllocationId) {
		
		return activityDao.findActiveActivitysByResourceAllocationId(resourceAllocationId);
	}

}
