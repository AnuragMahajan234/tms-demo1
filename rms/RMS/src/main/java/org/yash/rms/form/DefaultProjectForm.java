package org.yash.rms.form;

import org.yash.rms.domain.AllocationType;
import org.yash.rms.domain.OrgHierarchy;
import org.yash.rms.domain.Project;

public class DefaultProjectForm {
	private Integer id;
	private OrgHierarchy orgHierarchy;
	private Project projectId;
	private AllocationType allocationTypeId;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public OrgHierarchy getOrgHierarchy() {
		return orgHierarchy;
	}
	public void setOrgHierarchy(OrgHierarchy orgHierarchy) {
		this.orgHierarchy = orgHierarchy;
	}
	public Project getProjectId() {
		return projectId;
	}
	public void setProjectId(Project projectId) {
		this.projectId = projectId;
	}
	public AllocationType getAllocationTypeId() {
		return allocationTypeId;
	}
	public void setAllocationTypeId(AllocationType allocationTypeId) {
		this.allocationTypeId = allocationTypeId;
	}
}
