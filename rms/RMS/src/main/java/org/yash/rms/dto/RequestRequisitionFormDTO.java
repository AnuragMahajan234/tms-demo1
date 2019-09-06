package org.yash.rms.dto;

import java.util.List;

public class RequestRequisitionFormDTO {

	public RequestRequisitionDTO requestRequisitionDto;
	
	public List<RequestRequisitionSkillDTO> requestRequisitionSkillDto;

	public RequestRequisitionDTO getRequestRequisitionDto() {
		return requestRequisitionDto;
	}

	public void setRequestRequisitionDto(RequestRequisitionDTO requestRequisitionDto) {
		this.requestRequisitionDto = requestRequisitionDto;
	}

	public List<RequestRequisitionSkillDTO> getRequestRequisitionSkillDto() {
		return requestRequisitionSkillDto;
	}

	public void setRequestRequisitionSkillDto(List<RequestRequisitionSkillDTO> requestRequisitionSkillDto) {
		this.requestRequisitionSkillDto = requestRequisitionSkillDto;
	}
}
