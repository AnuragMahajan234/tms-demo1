package org.yash.rms.dto;

public class CustomerGroupDTO {
	
	private int groupId;
	
	private String groupName;
	
	private String groupEmail;
	private String active;

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getGroupEmail() {
		return groupEmail;
	}
	public void setGroupEmail(String groupEmail) {
		this.groupEmail = groupEmail;
	}
	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}
	
}
