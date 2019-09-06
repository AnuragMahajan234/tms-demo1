package org.yash.rms.dto;

import java.util.Date;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;
import org.yash.rms.domain.AllocationType;
import org.yash.rms.domain.RequestRequisitionResourceStatus;
import org.yash.rms.domain.RequestRequisitionSkill;
import org.yash.rms.domain.Resource;

/**
 * DTO for RequestRequisitionResource. This class will be used as a variable in RequestRequisitionResourceFormDTO class.
 * @author samiksha.sant
 *
 */
public class RequestRequisitionResourceDTO {
	private Integer id;

	private RequestRequisitionSkill requestRequisitionSkill;

	private List<Resource> resourceList;
	
	private List<String> externalResourceNames;

	private RequestRequisitionResourceStatus requestRequisitionResourceStatus;
  
	private AllocationType allocationType;

	private String interviewDate;
	
    private List<MultipartFile> uploadResume;

	private List<String> resumeFileName;
	
	private String comment;
	private Date allocationDate;
	
	public Date getAllocationDate() {
		return allocationDate;
	}

	public void setAllocationDate(Date allocationDate) {
		this.allocationDate = allocationDate;
	}

	public AllocationType getAllocation() {
		return allocationType;
	}

	public void setAllocation(AllocationType allocationId) {
		this.allocationType = allocationId;
	}

	public RequestRequisitionResourceStatus getRequestRequisitionResourceStatus() {
		return requestRequisitionResourceStatus;
	}

	public void setRequestRequisitionResourceStatus(RequestRequisitionResourceStatus requestRequisitionResourceStatus) {
		this.requestRequisitionResourceStatus = requestRequisitionResourceStatus;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public RequestRequisitionSkill getRequestRequisitionSkill() {
		return requestRequisitionSkill;
	}

	public void setRequestRequisitionSkill(RequestRequisitionSkill requestRequisitionSkill) {
		this.requestRequisitionSkill = requestRequisitionSkill;
	}

	public List<Resource> getResourceList() {
		return resourceList;
	}

	public void setResourceList(List<Resource> resourceList) {
		this.resourceList = resourceList;
	}

	public List<String> getExternalResourceNames() {
		return externalResourceNames;
	}

	public void setExternalResourceNames(List<String> externalResourceNames) {
		this.externalResourceNames = externalResourceNames;
	}

	public AllocationType getAllocationType() {
		return allocationType;
	}

	public void setAllocationType(AllocationType allocationType) {
		this.allocationType = allocationType;
	}

	public List<MultipartFile> getUploadResume() {
		return uploadResume;
	}

	public void setUploadResume(List<MultipartFile> uploadResume) {
		this.uploadResume = uploadResume;
	}

	public List<String> getResumeFileName() {
		return resumeFileName;
	}

	public void setResumeFileName(List<String> resumeFileName) {
		this.resumeFileName = resumeFileName;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getInterviewDate() {
		return interviewDate;
	}

	public void setInterviewDate(String interviewDate) {
		this.interviewDate = interviewDate;
	}
}
