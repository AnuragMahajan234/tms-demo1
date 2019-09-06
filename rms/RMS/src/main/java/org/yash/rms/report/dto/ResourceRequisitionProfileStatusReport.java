package org.yash.rms.report.dto;

import java.util.List;

public class ResourceRequisitionProfileStatusReport {

	private String clientName;
	
	private int clientId;
	
	private int totalOpen;
	
    private ResourceRequisitionProfileStatus rejectedProfileStatus;
    private ResourceRequisitionProfileStatus shortlistedProfileStatus;
    private ResourceRequisitionProfileStatus submittedProfileStatus;
    private ResourceRequisitionProfileStatus closedProfileStatus;


	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	public int getClientId() {
		return clientId;
	}

	public void setClientId(int clientId) {
		this.clientId = clientId;
	}

    public ResourceRequisitionProfileStatus getRejectedProfileStatus() {
        return rejectedProfileStatus;
    }

    public void setRejectedProfileStatus(ResourceRequisitionProfileStatus rejectedProfileStatus) {
        this.rejectedProfileStatus = rejectedProfileStatus;
    }

    public ResourceRequisitionProfileStatus getShortlistedProfileStatus() {
        return shortlistedProfileStatus;
    }

    public void setShortlistedProfileStatus(ResourceRequisitionProfileStatus shortlistedProfileStatus) {
        this.shortlistedProfileStatus = shortlistedProfileStatus;
    }

    public ResourceRequisitionProfileStatus getSubmittedProfileStatus() {
        return submittedProfileStatus;
    }

    public void setSubmittedProfileStatus(ResourceRequisitionProfileStatus submittedProfileStatus) {
        this.submittedProfileStatus = submittedProfileStatus;
    }

    public ResourceRequisitionProfileStatus getClosedProfileStatus() {
        return closedProfileStatus;
    }

    public void setClosedProfileStatus(ResourceRequisitionProfileStatus closedProfileStatus) {
        this.closedProfileStatus = closedProfileStatus;
    }

    public int getTotalOpen() {
        return totalOpen;
    }

    public void setTotalOpen(int totalOpen) {
        this.totalOpen = totalOpen;
    }

    
	
}
