package org.yash.rms.dao;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.yash.rms.domain.RequestRequisition;
import org.yash.rms.domain.Resource;

public interface RequestDao {

	/*public boolean saveRequest(Resource currentLoggedInUserId, String currentBU,
			String projects, List<Skills> skills, Integer[] resourceNo, String comments);*/

	public Boolean saveRequest( Resource currentResource, String currentBU,
			String projID, List<Integer> skillList, List<Integer> resourceNo,
			List<Integer> designationList, List<String> experience,
			List<Integer> allocationTypeList, List<Integer> type, List<String> time,
			Date date, String comments ,List<String> primarySkills,List<String> desirableSkills , List<String> responsibilities,
			List<String> targetCompanies, List<String> keyInterviewersOneText,
			List<String> keyInterviewersTwoText, List<String> additionalComments ,List<String> careerGrowthPlan, 
			List<String> keyScanners,Integer projectId,String sentMailTo,String finalMailNotifyTo, String skillRequestId, String requestId, Integer customerName, List<String> reqIdList);

	public List getLastAddedRequestID(); 
	public int getUpdatedId();
	public File createTempFile(String prefix, String suffix);
	public File createAttachment(Map<String, Object> model, File file);

	public RequestRequisition getReqById(Integer id);
	
}
  
  