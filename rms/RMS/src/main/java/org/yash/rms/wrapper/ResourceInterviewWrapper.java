package org.yash.rms.wrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ResourceInterviewWrapper {

	private String userIdList;

	private String mailText;

	private boolean notifyTo;

	private boolean sendMailTo;

	private boolean pdlsTo;

	private Integer requestResourceId;

	private String jobdesc;

	private String interviewDate;

	private String interviewMode;

	private String interviewTime;

	private String amPOCName;

	private String amPOCCont;

	private String custPOCName;

	private String venue;

	private String gatenumber;
	
	private String requirementId;
	
	private String customerPOCContact;
	
	private String contactPersonIds; 
	
	private String discussionType;
	
	private String contactPersonNumber;
	
	public String getDiscussionType() {
		return discussionType;
	}
	
	public void setDiscussionType(String discussionType) {
		this.discussionType = discussionType;
	}
	
	public String getContactPersonIds() {
		return contactPersonIds;
	}
	
	public void setContactPersonIds(String contactPersonIds) {
		this.contactPersonIds = contactPersonIds;
	}
	
	public String getRequirementId() {
		return requirementId;
	}
	
	public void setRequirementId(String requirementId) {
		this.requirementId = requirementId;
	}

	public String getUserIdList() {
		return userIdList;
	}

	public void setUserIdList(String userIdList) {
		this.userIdList = userIdList;
	}

	public String getMailText() {
		return mailText;
	}

	public void setMailText(String mailText) {
		this.mailText = mailText;
	}

	public boolean isNotifyTo() {
		return notifyTo;
	}

	public void setNotifyTo(boolean notifyTo) {
		this.notifyTo = notifyTo;
	}

	public boolean isSendMailTo() {
		return sendMailTo;
	}

	public void setSendMailTo(boolean sendMailTo) {
		this.sendMailTo = sendMailTo;
	}

	public boolean isPdlsTo() {
		return pdlsTo;
	}

	public void setPdlsTo(boolean pdlsTo) {
		this.pdlsTo = pdlsTo;
	}

	public Integer getRequestResourceId() {
		return requestResourceId;
	}

	public void setRequestResourceId(Integer requestResourceId) {
		this.requestResourceId = requestResourceId;
	}

	public String getJobdesc() {
		return jobdesc;
	}

	public void setJobdesc(String jobdesc) {
		this.jobdesc = jobdesc;
	}

	public String getInterviewDate() {
		return interviewDate;
	}

	public void setInterviewDate(String interviewDate) {
		this.interviewDate = interviewDate;
	}

	public String getInterviewMode() {
		return interviewMode;
	}

	public void setInterviewMode(String interviewMode) {
		this.interviewMode = interviewMode;
	}

	public String getInterviewTime() {
		return interviewTime;
	}

	public void setInterviewTime(String interviewTime) {
		this.interviewTime = interviewTime;
	}

	public String getAmPOCName() {
		return amPOCName;
	}

	public void setAmPOCName(String amPOCName) {
		this.amPOCName = amPOCName;
	}

	public String getAmPOCCont() {
		return amPOCCont;
	}

	public void setAmPOCCont(String amPOCCont) {
		this.amPOCCont = amPOCCont;
	}

	public String getCustPOCName() {
		return custPOCName;
	}

	public void setCustPOCName(String custPOCName) {
		this.custPOCName = custPOCName;
	}

	public String getVenue() {
		return venue;
	}

	public void setVenue(String venue) {
		this.venue = venue;
	}

	public String getGatenumber() {
		return gatenumber;
	}

	public void setGatenumber(String gatenumber) {
		this.gatenumber = gatenumber;
	}
	
	public String getCustomerPOCContact() {
		return customerPOCContact;
	}
	
	public void setCustomerPOCContact(String customerPOCContact) {
		this.customerPOCContact = customerPOCContact;
	}

	public String getContactPersonNumber() {
		return contactPersonNumber;
	}
	
	public void setContactPersonNumber(String contactPersonNumber) {
		this.contactPersonNumber = contactPersonNumber;
	}
}
