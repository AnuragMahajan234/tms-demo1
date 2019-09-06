package org.yash.rms.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.yash.rms.domain.RequestRequisition;
import org.yash.rms.domain.RequestRequisitionResource;
import org.yash.rms.domain.RequestRequisitionSkill;
import org.yash.rms.domain.ResourceComment;
import org.yash.rms.dto.RequestRequisitionSkillDTO;
import org.yash.rms.dto.ResourceCommentDTO;
import org.yash.rms.report.dto.RequestRequisitionReport;
import org.yash.rms.domain.RequestRequisitionResourceStatus;

public interface RequestReportDao {

	public List<RequestRequisition> getReport();
	public boolean saveReport(RequestRequisition request, int skillId);
	public Integer saveSkillRequestReport(RequestRequisitionSkill request, int skillId, List<RequestRequisitionResource> skillResources)throws Exception;
	
	public List<RequestRequisitionSkill> getReport(String opt,Integer id, String role);
	public boolean deleteSkillRequestResource(Integer id);
	public List<RequestRequisitionSkill> getRequestRequisitionSkillById(Integer id); 
	public List<RequestRequisitionSkill> getSkillRequestReport(Integer id, String role);
	public  boolean reduceFullfilledSkillRequest(Integer  id) ;
	public List<RequestRequisitionResourceStatus> getSkillResourceStatusList() ;
	public boolean updateSkillRequestStatus(Integer id ,Integer statusId) ;
	public boolean delete(int id);
	//1003958 Ends[]

	public Object getResumeById(Integer id);
	public Object getResumeFromResourceById(Integer id);
	public Map<String, Object> getTacById(Integer id)throws Exception;
	
	public  List<RequestRequisitionSkill> getSkillRequestReport(Integer id, String role, Date startDate, Date endDate );
	
	public void saveResourceComment(ResourceComment resourceComment);
	public List<ResourceComment> getAllResourceCommentByResourceId(int resourceId);
	public  List<RequestRequisitionSkill> getSkillRequestReport(Integer id, String role, Date startDate, Date endDate,   List<Integer> customerList, List<Integer> groupList, String status);
	public  List<RequestRequisitionReport>  getSkillRequestDBTabReport(Integer id, String role, List<Integer> customerList, List<Integer> groupList, String status, List<String>  hiringUnits, List<String>  reqUnits);
	
	public  List<Object[]> getSkillRequestReport(Integer userId, String role, 
			List<Integer> customerList, List<Integer> groupList, List<Integer> projectList, String status, List<String>  hiringUnits, List<String>  reqUnits);
	public List<ResourceComment> getAllResourceCommentsBySkillRequestRequisitionID(Integer skillId);
	public boolean hardDeleteSkillRequest(Integer skillId);
}
