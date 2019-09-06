package org.yash.rms.dto;

import java.util.List;

/**
 * This class is used as an input DTO, to send the respective RRF with selected
 * resourceIDS and PDLgroups.
 * 
 * @author samiksha.sant
 *
 */
public class ResourceAndPDLInputDTO {
	
	private List<String> resourceIds;

	private List<String> pdlIds;

	private String skillRequestID;
	
	private int currentPage;
	public List<String> getResourceIds() {
		return resourceIds;
	}
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public void setResourceIds(List<String> resourceIds) {
		this.resourceIds = resourceIds;
	}

	public List<String> getPdlIds() {
		return pdlIds;
	}

	public void setPdlIds(List<String> pdlIds) {
		this.pdlIds = pdlIds;
	}

	public String getSkillRequestID() {
		return skillRequestID;
	}

	public void setSkillRequestID(String skillRequestID) {
		this.skillRequestID = skillRequestID;
	}

	
}
