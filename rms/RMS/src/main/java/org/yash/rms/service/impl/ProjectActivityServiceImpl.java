/**
 * 
 */
package org.yash.rms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.yash.rms.dao.ProjectActivityDao;
import org.yash.rms.domain.AllProjectActivities;
import org.yash.rms.domain.CustomActivity;
import org.yash.rms.domain.UserActivity;
import org.yash.rms.service.ProjectActivityService;
import org.yash.rms.util.DozerMapperUtility;

/**
 * @author arpan.badjatiya
 *
 */
@Service("ProjectActivityService")
public class ProjectActivityServiceImpl implements ProjectActivityService {

	@Autowired @Qualifier("ProjectActivityDao")
	private ProjectActivityDao projectactivityDao;

	@Autowired
	private DozerMapperUtility mapperUtility;
	public boolean delete(int id) {
		return projectactivityDao.delete(id);
	}

	public boolean saveOrupdate(CustomActivity activity) {
		//System.out.println("DozerMapperUtility for billingScale::"+mapperUtility.convertDomainToDTO(activity));
		//System.out.println("DozerMapperUtility for billingScale::"+mapperUtility.convertDTOObjectToDomain(mapperUtility.convertDTOObjectToDomain(activity)));
		return projectactivityDao.saveOrupdate(activity);
	}

	public List<CustomActivity> findAll() {
		return projectactivityDao.findAll();
	}

	public List<CustomActivity> findByEntries(int firstResult, int sizeNo) {
		return null;
	}

	public long countTotal() {
		return projectactivityDao.countTotal();
	}

	public List<CustomActivity> findById(int projectId,List<Integer> activityIds) {
		// TODO Auto-generated method stub
		return projectactivityDao.findById(projectId,activityIds);
	}

	public CustomActivity findByActivityId(int activityId,int projectId) {
		// TODO Auto-generated method stub
		//return projectactivityDao.findByActivityId(projectId);
		
		return projectactivityDao.findByActivityId( activityId, projectId);
	}


	public List<Integer> findCustomIds(List<Integer> defaultids, String type,List<Integer> customIds, Integer projectId) {
		// TODO Auto-generated method stub
		return projectactivityDao.findCustomIds(defaultids,  type,customIds, projectId);
	}

	public boolean save(List<CustomActivity> list) {
		// TODO Auto-generated method stub
		return projectactivityDao.save(list);
	}

	public List<Integer> findSEPGActivities(Integer projectId,List<String> sepglist,List<Integer> sepg) {
		// TODO Auto-generated method stub
		return projectactivityDao.findSEPGActivities(projectId, sepglist, sepg);
	}


	public boolean updateDefaultToInActive(List<Integer> ids,List<Integer> customIds , Integer projectId) {
		// TODO Auto-generated method stub
		return projectactivityDao.updateDefaultToInActive(ids,customIds, projectId);
	}

	public boolean updateCustomToInActive(List<Integer> ids,List<Integer> customIds,  Integer projectId) {
		// TODO Auto-generated method stub
		return projectactivityDao.updateCustomToInActive(ids, customIds, projectId);
	}
 
	public boolean saveActivity(List<AllProjectActivities> list) {
		return projectactivityDao.saveActivity(list);
		
	}
	
	public List<Integer> projectTypeActivities (String type, int projectId ){
		return projectactivityDao.projectTypeActivities(type, projectId);
	}
	
	public List<CustomActivity> findBySelectedCustomId(List<Integer> id) {
		return projectactivityDao.findBySelectedCustomId(id);
	}
	
	public List<AllProjectActivities> findBySelectedActivityId(String [] activityList,int projectId,String [] customactivities ){
		
		return projectactivityDao.findBySelectedActivityId(activityList, projectId,customactivities);
		
	}
	
	public List<UserActivity> findCustomIDsInTimsheet(){
		return projectactivityDao.findCustomIDsInTimsheet();
	}
}
