package org.yash.rms.dao;

import java.text.ParseException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.yash.rms.domain.RequestRequisition;
import org.yash.rms.domain.RequestRequisitionResource;
import org.yash.rms.domain.RequestRequisitionSkill;
import org.yash.rms.domain.Resource;
import org.yash.rms.util.SearchCriteriaGeneric;

public interface RequestRequisitionSkillDao {

	public RequestRequisitionSkill findRequestRequisitionSkillBySkillId(Integer requestRequisitionSkillId);
	
	public List<RequestRequisitionSkill> findRequestRequisitionSkillsByRequestRequisitionId(int requestRequisitionId);
	
	public List<RequestRequisitionSkill>editReqId(Integer id);
	
	public String save(RequestRequisitionSkill requestRequisitionSkill);
	
	public List<RequestRequisitionSkill> findRequestRequisitionSkillsByRequestRequisitionSkillIds(List<Integer> requestRequisitionSkillsIds);
	
	
	public RequestRequisitionSkill updateRequestRequisitionSkillBySkillId(Integer requestRequisitionSkillId, String[] sent, String [] pdl, String rrfForwardComment);

	String findRequirementIdBySkillId(final int id);
	
	boolean saveOrUpdate(RequestRequisitionSkill requisitionSkill);

	public List<RequestRequisitionSkill> getRequestRequisitionList(Integer userId, String userRole, List<Integer> projectList,HttpServletRequest httpRequest,SearchCriteriaGeneric searchCriteriaGeneric);
	
	public Long getTotalCountRRF(Integer userId,String userRole,List<Integer> projectList,SearchCriteriaGeneric searchCriteriaGeneric) throws ParseException;

	public RequestRequisitionSkill getRequestRequisitionSkillByRRFId(String requirementId);
}
