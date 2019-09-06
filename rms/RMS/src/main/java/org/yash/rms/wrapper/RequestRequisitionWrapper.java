package org.yash.rms.wrapper;

import java.util.Date;

import org.yash.rms.domain.RequestRequisition;
import org.yash.rms.domain.RequestRequisitionSkill;
import org.yash.rms.util.Constants;

import flexjson.JSONSerializer;
import flexjson.transformer.DateTransformer;

/**
 * Designed to render data of domains namely, RequestRequisition and
 * RequestRequisitionSkill
 * 
 * @author samiksha.sant
 *
 */
public class RequestRequisitionWrapper {

	private String requestorsBGBU;
	private String requirementID;
	private String customerName;
	private String jobLocation;
	private String deliveryPOCName;
	private String resourceName;
	private String resourceContactNumber;
	private String resourceEmpId;
	private String customerPOCName;
	private String aMPOCName;
	private String aMPOContact;
	private String deliveryPOCContact;
	private String customerPOCContact;
	private String interviewDate;
	private String interviewTime;
	private String interviewMode;
	private String venue;
	private String gatePassNumber;
	private String skillCategory;
	private String jobDescription;
	private String mailTo;
	private String notifyTo;
	private String PDLsTo;
	private String emailContent;
	private String mailsToMailIds; 
	private String notifyToMailIds; 
	private String pdlsToMailIds; 
	
	public String getMailsToMailIds() {
		return mailsToMailIds;
	}
	public void setMailsToMailIds(String mailsToMailIds) {
		this.mailsToMailIds = mailsToMailIds;
	}
	public String getNotifyToMailIds() {
		return notifyToMailIds;
	}
	public void setNotifyToMailIds(String notifyToMailIds) {
		this.notifyToMailIds = notifyToMailIds;
	}
	public String getPdlsToMailIds() {
		return pdlsToMailIds;
	}
	public void setPdlsToMailIds(String pdlsToMailIds) {
		this.pdlsToMailIds = pdlsToMailIds;
	}
	public String getRequestorsBGBU() {
		return requestorsBGBU;
	}
	public void setRequestorsBGBU(String requestorsBGBU) {
		this.requestorsBGBU = requestorsBGBU;
	}
	public String getRequirementID() {
		return requirementID;
	}
	public void setRequirementID(String requirementID) {
		this.requirementID = requirementID;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getJobLocation() {
		return jobLocation;
	}
	public void setJobLocation(String jobLocation) {
		this.jobLocation = jobLocation;
	}
	public String getDeliveryPOCName() {
		return deliveryPOCName;
	}
	public void setDeliveryPOCName(String deliveryPOCName) {
		this.deliveryPOCName = deliveryPOCName;
	}
	public String getResourceName() {
		return resourceName;
	}
	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}
	public String getResourceContactNumber() {
		return resourceContactNumber;
	}
	public void setResourceContactNumber(String resourceContactNumber) {
		this.resourceContactNumber = resourceContactNumber;
	}
	public String getResourceEmpId() {
		return resourceEmpId;
	}
	public void setResourceEmpId(String resourceEmpId) {
		this.resourceEmpId = resourceEmpId;
	}
	public String getCustomerPOCName() {
		return customerPOCName;
	}
	public void setCustomerPOCName(String customerPOCName) {
		this.customerPOCName = customerPOCName;
	}
	public String getaMPOCName() {
		return aMPOCName;
	}
	public void setaMPOCName(String aMPOCName) {
		this.aMPOCName = aMPOCName;
	}
	public String getaMPOContact() {
		return aMPOContact;
	}
	public void setaMPOContact(String aMPOContact) {
		this.aMPOContact = aMPOContact;
	}
	public String getDeliveryPOCContact() {
		return deliveryPOCContact;
	}
	public void setDeliveryPOCContact(String deliveryPOCContact) {
		this.deliveryPOCContact = deliveryPOCContact;
	}
	public String getCustomerPOCContact() {
		return customerPOCContact;
	}
	public void setCustomerPOCContact(String customerPOCContact) {
		this.customerPOCContact = customerPOCContact;
	}
	public String getInterviewDate() {
		return interviewDate;
	}
	public void setInterviewDate(String interviewDate) {
		this.interviewDate = interviewDate;
	}
	public String getInterviewTime() {
		return interviewTime;
	}
	public void setInterviewTime(String interviewTime) {
		this.interviewTime = interviewTime;
	}
	public String getInterviewMode() {
		return interviewMode;
	}
	public void setInterviewMode(String interviewMode) {
		this.interviewMode = interviewMode;
	}
	public String getVenue() {
		return venue;
	}
	public void setVenue(String venue) {
		this.venue = venue;
	}
	public String getGatePassNumber() {
		return gatePassNumber;
	}
	public void setGatePassNumber(String gatePassNumber) {
		this.gatePassNumber = gatePassNumber;
	}
	public String getSkillCategory() {
		return skillCategory;
	}
	public void setSkillCategory(String skillCategory) {
		this.skillCategory = skillCategory;
	}
	public String getJobDescription() {
		return jobDescription;
	}
	public void setJobDescription(String jobDescription) {
		this.jobDescription = jobDescription;
	}
	public String getMailTo() {
		return mailTo;
	}
	public void setMailTo(String mailTo) {
		this.mailTo = mailTo;
	}
	public String getNotifyTo() {
		return notifyTo;
	}
	public void setNotifyTo(String notifyTo) {
		this.notifyTo = notifyTo;
	}
	public String getPDLsTo() {
		return PDLsTo;
	}
	public void setPDLsTo(String pDLsTo) {
		PDLsTo = pDLsTo;
	}
	public String getEmailContent() {
		return emailContent;
	}
	public void setEmailContent(String emailContent) {
		this.emailContent = emailContent;
	}
	
	public String toJSONString(RequestRequisitionWrapper wrapper) {
		 return new JSONSerializer().include("requestorsBGBU", "requirementID", "customerName" ,"jobLocation","deliveryPOCName", "resourceName",
				 "resourceContactNumber" ,"resourceEmpId","customerPOCName", "aMPOCName", "aMPOContact" ,"deliveryPOCContact","customerPOCContact",
				 "interviewDate",
				 "interviewTime" ,"interviewMode"
				 ,"venue", "gatePassNumber", "skillCategory" ,"jobDescription"
				 ,"mailTo", "notifyTo", "PDLsTo" ,"emailContent", "mailsToMailIds", "notifyToMailIds", "pdlsToMailIds").exclude("*")
			        .transform(new DateTransformer(Constants.DATE_PATTERN), Date.class).serialize(wrapper);
		 
	/*	return "RequestRequisitionWrapper [requestorsBGBU=" + requestorsBGBU + ", requirementID=" + requirementID
				+ ", customerName=" + customerName + ", jobLocation=" + jobLocation + ", deliveryPOCName="
				+ deliveryPOCName + ", resourceName=" + resourceName + ", resourceContactNumber="
				+ resourceContactNumber + ", resourceEmpId=" + resourceEmpId + ", customerPOCName=" + customerPOCName
				+ ", aMPOCName=" + aMPOCName + ", aMPOContact=" + aMPOContact + ", deliveryPOCContact="
				+ deliveryPOCContact + ", customerPOCContact=" + customerPOCContact + ", interviewDate=" + interviewDate
				+ ", interviewTime=" + interviewTime + ", interviewMode=" + interviewMode + ", venue=" + venue
				+ ", gatePassNumber=" + gatePassNumber + ", skillCategory=" + skillCategory + ", jobDescription="
				+ jobDescription + ", mailTo=" + mailTo + ", notifyTo=" + notifyTo + ", PDLsTo=" + PDLsTo
				+ ", emailContent=" + emailContent + "]";*/
	}

	
	
}
