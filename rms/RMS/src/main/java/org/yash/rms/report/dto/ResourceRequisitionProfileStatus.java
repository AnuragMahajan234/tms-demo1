package org.yash.rms.report.dto;

public class ResourceRequisitionProfileStatus {

	private String profileStatusName;
	
	private int internalResourceCount;
	
	private int externalResourceCount;
	
	private int profileStatusId;

	public String getProfileStatusName() {
		return profileStatusName;
	}

	public void setProfileStatusName(String profileStatusName) {
		this.profileStatusName = profileStatusName;
	}

	public int getInternalResourceCount() {
		return internalResourceCount;
	}

	public void setInternalResourceCount(int internalResourceCount) {
		this.internalResourceCount = internalResourceCount;
	}

	public int getExternalResourceCount() {
		return externalResourceCount;
	}

	public void setExternalResourceCount(int externalResourceCount) {
		this.externalResourceCount = externalResourceCount;
	}

	public int getProfileStatusId() {
		return profileStatusId;
	}

	public void setProfileStatusId(int profileStatusId) {
		this.profileStatusId = profileStatusId;
	}
	
}
