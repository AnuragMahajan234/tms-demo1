package org.yash.rms.dto;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class RequestRequisitionDashboardInputDTO {
	
	
	private List<ResourceFileDTO> resourceFileDTO;
	
	private Integer skillReqId;
	
	private Integer fullfillResLen;
	
	private String comments;
	
	private List<String> internalResourceIds;
	
	private String notifyTo;
	
	private String requestSentTo;
	
	private Integer projectId;
	
	private List<String> extraMailTo;
	
	
	private  String reqId;
	
	private String pdl;
	

	public String getNotifyTo() {
		return notifyTo;
	}

	public void setNotifyTo(String notifyTo) {
		this.notifyTo = notifyTo;
	}

	public String getRequestSentTo() {
		return requestSentTo;
	}

	public void setRequestSentTo(String requestSentTo) {
		this.requestSentTo = requestSentTo;
	}

	public List<String> getInternalResourceIds() {
		return internalResourceIds;
	}

	public void setInternalResourceIds(List<String> internalResourceIds) {
		this.internalResourceIds = internalResourceIds;
	}

	public Integer getSkillReqId() {
		return skillReqId;
	}

	public void setSkillReqId(Integer skillReqId) {
		this.skillReqId = skillReqId;
	}

	public Integer getFullfillResLen() {
		return fullfillResLen;
	}

	public void setFullfillResLen(Integer fullfillResLen) {
		this.fullfillResLen = fullfillResLen;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Integer getProjectId() {
		return projectId;
	}

	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}
	public List<String> getExtraMailTo() {
		return extraMailTo;
	}

	public void setExtraMailTo(List<String> extraMailTo) {
		this.extraMailTo = extraMailTo;
	}

	public String getReqId() {
		return reqId;
	}

	public void setReqId(String reqId) {
		this.reqId = reqId;
	}

	public List<ResourceFileDTO> getResourceFileDTO() {
		return resourceFileDTO;
	}

	public void setResourceFileDTO(List<ResourceFileDTO> resourceFileDTO) {
		this.resourceFileDTO = resourceFileDTO;
	}

	public String getPdl() {
		return pdl;
	}

	public void setPdl(String pdl) {
		this.pdl = pdl;
	}




}
