package org.yash.rms.service;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.yash.rms.domain.AllocationType;
import org.yash.rms.domain.Competency;
import org.yash.rms.domain.Designation;
import org.yash.rms.domain.Project;
import org.yash.rms.domain.Resource;
import org.yash.rms.domain.Skills;

@Service("RequestService")
public interface RequestService {

	/*
	 * Boolean saveRequest(Resource currentResource, String currentBU, String
	 * projects, List<Skills> skills, Integer[] resourceNo, String comments);
	 */

	Boolean saveRequest(Resource currentResource, String currentBU,
			String projID, List<Competency> skillList, List<Integer> resourceNo,
			List<Designation> designation, List<String> experience,
			List<AllocationType> bill, List<Integer> type, List<String> time,
			Date date, String comments, List<String> primarySkills,
			List<String> desirableSkills, List<String> responsibilities,
			List<String> targetCompanies, List<String> keyInterviewersOneText,
			List<String> keyInterviewersTwoText, List<String> additionalComments ,List<String> careerGrowthPlan, List<String> keyScanners,Project prj,String sentMailTo,String skillRequestId,String requestId);
		
	List getLastAddedRequestID();

}
