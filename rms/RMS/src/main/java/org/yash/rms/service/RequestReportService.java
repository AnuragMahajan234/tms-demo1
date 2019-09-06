package org.yash.rms.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.yash.rms.domain.CATicketSubProcess;
import org.yash.rms.domain.Request;
import org.yash.rms.domain.ResourceAllocation;
import org.yash.rms.domain.SkillRequest;
import org.yash.rms.domain.SkillResource;
import org.yash.rms.domain.SkillResourceStatusType;

@Service("RequestReportService")
public interface RequestReportService extends RmsCRUDService<SkillRequest>{

	//public List<Request> getReport();

	public boolean saveReport(String requestJSON);
	public boolean saveSkillRequestReport(String requestJSON);
	public List<SkillResource> updateResourceRequestWithStatus(String requestJSON);
	
	public List<SkillRequest> getReport(String opt,Integer id,String role);
	public boolean deleteSkillRequestResource(Integer id);

	public List<SkillRequest> addResourceWithSkillReqId(Integer id);
	public List<SkillRequest> getSkillRequestReport(Integer id,String role);
	public boolean reduceFullfilledSkillRequest(int id);
	public List<SkillResourceStatusType> getSkillResourceStatusList();
	public boolean updateSkillRequestStatus(Integer id ,Integer statusId) ;
	public boolean delete(int id);
	//mu21686
	public SkillRequest getRequestRecordForEdit(Integer id);
	//mu21686
}
