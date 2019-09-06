package org.yash.rms.forms;

import java.util.ArrayList;
import java.util.List;

import org.yash.rms.dto.ResourceStatusDTO;

public class UpdateResourceStatusForm {
	

	private List<ResourceStatusDTO> resourceStatuslist;
	
	private int requestRequisitionSkillId;

	public List<ResourceStatusDTO> getResourceStatuslist() {
		if(null==resourceStatuslist){
			resourceStatuslist = new ArrayList<ResourceStatusDTO>();
		}
		return resourceStatuslist;
	}

	public void setResourceStatuslist(List<ResourceStatusDTO> resourceStatuslist) {
		this.resourceStatuslist = resourceStatuslist;
	}
	
	public UpdateResourceStatusForm(){}

	public int getRequestRequisitionSkillId() {
		return requestRequisitionSkillId;
	}

	public void setRequestRequisitionSkillId(int requestRequisitionSkillId) {
		this.requestRequisitionSkillId = requestRequisitionSkillId;
	}
	
	public UpdateResourceStatusForm(List<ResourceStatusDTO> resourceStatuslist, int requestRequisitionSkillId) {
		super();
		this.resourceStatuslist = resourceStatuslist;
		this.requestRequisitionSkillId = requestRequisitionSkillId;
	}


}
