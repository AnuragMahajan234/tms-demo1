package org.yash.rms.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.yash.rms.domain.RequestRequisition;
import org.yash.rms.domain.RequestRequisitionResource;
import org.yash.rms.domain.RequestRequisitionResourceStatus;
import org.yash.rms.domain.RequestRequisitionSkill;
import org.yash.rms.domain.Resource;
import org.yash.rms.domain.ResourceComment;
import org.yash.rms.dto.RequestRequisitionDashboardDTO;
import org.yash.rms.dto.RequestRequisitionDashboardInputDTO;
import org.yash.rms.dto.RequestRequisitionResourceFormDTO;
import org.yash.rms.dto.RequestRequisitionSkillDTO;
import org.yash.rms.dto.ResourceCommentDTO;
import org.yash.rms.dto.ResourceStatusDTO;
import org.yash.rms.exception.DAOException;
import org.yash.rms.exception.RMSServiceException;
//import org.yash.rms.report.dto.RequestRequisitionReport;
import org.yash.rms.report.dto.RequestRequisitionReport;
import org.yash.rms.wrapper.RequestRequisitionWrapper;
import org.yash.rms.wrapper.ResourceInterviewWrapper;


@Service("requestRequisitionDashboardService")
public interface RequestRequisitionDashboardService extends RmsCRUDService<RequestRequisitionSkill> {

	public RequestRequisitionDashboardDTO getRequestRequisitionDashboard(Integer id, String role) throws Exception;
	
	public RequestRequisitionResourceFormDTO getRequestRequisitionResourceDTO(Integer requestRequisitionId);

	public boolean saveReport(String requestJSON);

	public List<RequestRequisitionResource> updateResourceRequestWithStatus(String requestJSON);

	public List<RequestRequisitionSkill> getReport(String opt, Integer id, String role);

	public boolean deleteSkillRequestResource(Integer id,Integer skillId);

	public List<RequestRequisitionSkill> addResourceWithSkillReqId(Integer id);

	public List<RequestRequisitionSkill> getSkillRequestReport(Integer id, String role);

	public boolean reduceFullfilledSkillRequest(int id);

	public List<RequestRequisitionResourceStatus> getSkillResourceStatusList();

	public boolean updateSkillRequestStatus(Integer id, Integer statusId);

	public boolean delete(int id) throws DAOException;
	// 1005818
	public void saveSkillRequestReport(String i, MultipartFile[] files, String skillReqId, Integer fullfillResLen, 
			String comments, String resourceIds, String[] externalResourceNames, String[] resumeNames, String[] mailTo, String[] extraMailTo);

	public Map<String, Object> downloadSelectedResume(Integer id,String resourceType);
	
	public Map<String, Object> downloadSelectedTac(Integer id)throws Exception;

	public Integer saveSkillRequestReport(RequestRequisitionDashboardInputDTO dto) throws Exception;
	
	public void sendEmailForSkillRequestReport(RequestRequisitionDashboardInputDTO dto,Integer skillRequestId, List<ResourceStatusDTO> resourceStatuslist, List<ResourceStatusDTO> resourceStatusNewlist, RequestRequisitionResourceFormDTO requestRequisitionResourceFormDTO)throws Exception;

	public void updateResourceRequestWithStatus(int requestRequisitionSkillId,
			List<ResourceStatusDTO> resourceStatuslist);
	

	public void sendEmail(String[] resourceIds, String[] pdlIds,String skillReqId, Resource resource, String mailType, String rrfForwardComment);
	
	public RequestRequisitionDashboardDTO getRequestRequisitionReportForDateCriteria(Integer id, String role, Date startDate,
			Date endDate);
	
	public RequestRequisitionDashboardDTO getRequestRequisitionReport(Integer id, String role, Date startDate,
			Date endDate, List<Integer> customerList, List<Integer> groupList, String status);

	public List<RequestRequisitionReport> getDashBoardDataReport(Integer id, String role, List<Integer> customerList, List<Integer> groupList, String status,List<String> hiringUnits, List<String> reqUnits);
	public void saveResourceComment(ResourceComment resourceComment);

	public List<ResourceCommentDTO> getAllResourceCommentByResourceId(int resourceId);
	
	public List<RequestRequisitionSkillDTO> getRequestRequisitionSkillBySkillId(Integer id);
	
	public List<RequestRequisitionSkill> getRequestRequisitionSkillByReqSkillId(Integer reqSkillId);
	
    public void sendRRFClosureMail(List<RequestRequisitionResource> requestRequisitionResources, int requestRequisitionSkillId, RequestRequisitionSkill requisitionSkill);

    public Map<String, Object> downloadSelectedBUHApproval(Integer requestId);
//Ragini - closure email
	//public void clouserSendEmail(List<RequestRequisitionSkill> requestSkillList);
//	public List<RequestRequisitionResource> getResourceByRRFId(Integer id) ;

	public Map<String, Object> downloadSelectedBGHApproval(Integer id);
	
	public List<ResourceCommentDTO> getAllResourceCommentsBySkillRequestRequisitionID(Integer skillId);
	
	
	public void sendEmailForInterview(ResourceInterviewWrapper resourceInterviewWrapper, Resource resource) throws Exception;

	public Boolean sendDeleteRRFEmail(int id, int skillId);
	
	RequestRequisitionSkill findRequestRequisitionSkillBySkillId(int id);
	
	public void updateInterviewPanels(final String requestJson);

	public List<RequestRequisitionSkill> getRequestRequisitionList(Integer userId, String userRole, HttpServletRequest httpRequest);
	
	public Long getTotalCountRRF(Integer userId, String userRole, HttpServletRequest request) throws Exception;

	public RequestRequisitionSkillDTO copyRRFBySkillId(int skillRequestId);

	public boolean hardDeleteSkillRequest(Integer skillId);

	public RequestRequisitionWrapper getDataForScheduleInterviewByskillId(String skillid, Integer skillResourceId);
}
