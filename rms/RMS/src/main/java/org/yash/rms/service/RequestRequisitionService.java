package org.yash.rms.service;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.yash.rms.domain.RequestRequisition;
import org.yash.rms.domain.RequestRequisitionSkill;
import org.yash.rms.domain.Resource;
import org.yash.rms.dto.CustomerDTO;
import org.yash.rms.dto.RequestRequisitionFormDTO;
import org.yash.rms.dto.RequestRequisitionSkillDTO;


public interface RequestRequisitionService {

	/*
	 * Boolean saveRequest(Resource currentResource, String currentBU, String
	 * projects, List<Skills> skills, Integer[] resourceNo, String comments);
	 */

	Boolean saveRequest( Resource currentResource, String currentBU,
			String projID, List<Integer> skillList, List<Integer> resourceNo,
			List<Integer> designationList, List<String> experience,
			List<Integer> allocationTypeList, List<Integer> type, List<String> time,
			Date date, String comments, List<String> primarySkills,
			List<String> desirableSkills, List<String> responsibilities,
			List<String> targetCompanies, List<String> keyInterviewersOneText,
			List<String> keyInterviewersTwoText, List<String> additionalComments ,List<String> careerGrowthPlan, 
			List<String> keyScanners,Integer projectId,String sentMailTo,String notifyto,String skillRequestId,String requestId, Integer customerName, List<String> reqIdList);
		
	List getLastAddedRequestID();
	public int getUpdatedId();
	public File createAttachment(Map<String, Object> model, File file);
	public File createTempFile(String prefix, String suffix);
	public List<RequestRequisitionSkill> findRequestRequisitionSkillsByRequestRequisitionId(int requestRequisitionId);
	public RequestRequisition findRequestRequisitionById(Integer requestRequisitionId);
	public RequestRequisitionSkillDTO getRequestRecordForEdit(Integer id);

	public String saveRequest(RequestRequisitionFormDTO requestRequisitionFormDTO);
	
	public RequestRequisitionFormDTO getRequestRequisitionFormDTO(Integer requestRequisitionSkillIds);
	
	public void saveOrUpdateRRF(RequestRequisitionFormDTO requestRequisitionFormDTO);

	public void sendEmail(RequestRequisitionFormDTO requestRequisitionDTO,boolean holdFlag, boolean lostFlag, boolean update, boolean copyFlag);

   public void saveSkillRequestRequisition(RequestRequisitionFormDTO requestRequisitionFormDTO, RequestRequisition requestRequisition);
   
   public Integer updateRequisitionRequestSkill(RequestRequisitionFormDTO requestRequisitionFormDTO);

   public List<CustomerDTO> getRequestRequisitionsCustomer(Integer userId, String userRole);
   
   public RequestRequisitionSkillDTO getRequestRecordForCopy (Integer id);
   
   public String createRRFRequirementId(final String type) throws Exception;
   
   public boolean saveOrupdate(RequestRequisition requestRequisition) throws Exception;
   
   public List<RequestRequisitionSkill> findRRFByProjectId(Integer id);
   
   public String findRRFStatus(RequestRequisitionSkill requestRequisitionSkill);

}
