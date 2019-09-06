package org.yash.rms.form;

import java.util.Date;

 




import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;
import org.yash.rms.domain.Designation;
import org.yash.rms.domain.EmployeeCategory;
import org.yash.rms.domain.Event;
import org.yash.rms.domain.Grade;
import org.yash.rms.domain.Location;
import org.yash.rms.domain.OrgHierarchy;
import org.yash.rms.domain.Ownership;

public class ResourceLoanTransferForm {

	private org.yash.rms.domain.Resource resourceId;		

	private Date transferDate;
 
	/*added field */
	private Date endTransferDate;

	private org.yash.rms.domain.Resource currentReportingManager;

	private org.yash.rms.domain.Resource currentReportingManagerTwo;
	
	private OrgHierarchy buId;
	
	private OrgHierarchy currentBuId;

	private Grade gradeId;
	
	private Event eventId;

	private Location locationId;	

	private Ownership ownership;
	
	private EmployeeCategory employeeCategory ;

	private Location deploymentLocationId;	

	private Designation designationId;
		
	private String ltStatus;
	
	private String createdId;
	
	private String yashEmpId;
	
	private String emailId;
	
	private String contactNumber;
	
	private String contactNumberTwo;
 
	private Date dateOfJoining;
	
 
	private Date confirmationDate;

 
	private Date lastAppraisal;
	
 
	private Date penultimateAppraisal;
	
 
	private Date releaseDate;
	
	private String bGHComments;
	
private String bUHComments;
	
	private String hRComments;
	
	private org.yash.rms.domain.Resource bGHName;
	
	private org.yash.rms.domain.Resource bUHName;
	
	private org.yash.rms.domain.Resource hRName;
	
	private Date bGCommentsTimestamp;
	
	private Date bUCommentsTimestamp;
	
	private Date hRCommentsTimestamp;
	
	private String locationHrEmailId;
	
	private String refResourceId;
		
	

	public String getLocationHrEmailId() {
		return locationHrEmailId;
	}

	public void setLocationHrEmailId(String locationHrEmailId) {
		this.locationHrEmailId = locationHrEmailId;
	}

	/**
	 * @return the eventId
	 */
	public Event getEventId() {
		return eventId;
	}

	/**
	 * @param eventId the eventId to set
	 */
	public void setEventId(Event eventId) {
		this.eventId = eventId;
	}

	/**
	 * @return the bUHName
	 */
	public org.yash.rms.domain.Resource getbUHName() {
		return bUHName;
	}

	/**
	 * @param bUHName the bUHName to set
	 */
	public void setbUHName(org.yash.rms.domain.Resource bUHName) {
		this.bUHName = bUHName;
	}

	/**
	 * @return the hRName
	 */
	public org.yash.rms.domain.Resource gethRName() {
		return hRName;
	}

	/**
	 * @param hRName the hRName to set
	 */
	public void sethRName(org.yash.rms.domain.Resource hRName) {
		this.hRName = hRName;
	}

	/**
	 * @return the bGHName
	 */
	public org.yash.rms.domain.Resource getbGHName() {
		return bGHName;
	}

	/**
	 * @param bGHName the bGHName to set
	 */
	public void setbGHName(org.yash.rms.domain.Resource bGHName) {
		this.bGHName = bGHName;
	}

	/**
	 * @return the bUCommentsTimestamp
	 */
	public Date getbUCommentsTimestamp() {
		return bUCommentsTimestamp;
	}

	/**
	 * @param bUCommentsTimestamp the bUCommentsTimestamp to set
	 */
	public void setbUCommentsTimestamp(Date bUCommentsTimestamp) {
		this.bUCommentsTimestamp = bUCommentsTimestamp;
	}

	/**
	 * @return the hRCommentsTimestamp
	 */
	public Date gethRCommentsTimestamp() {
		return hRCommentsTimestamp;
	}

	/**
	 * @param hRCommentsTimestamp the hRCommentsTimestamp to set
	 */
	public void sethRCommentsTimestamp(Date hRCommentsTimestamp) {
		this.hRCommentsTimestamp = hRCommentsTimestamp;
	}

	public Date getTransferDate() {
		return transferDate;
	}

	

	/**
	 * @return the hRComments
	 */
	public String gethRComments() {
		return hRComments;
	}

	/**
	 * @param hRComments the hRComments to set
	 */
	public void sethRComments(String hRComments) {
		this.hRComments = hRComments;
	}

	/**
	 * @return the bUHComments
	 */
	public String getbUHComments() {
		return bUHComments;
	}

	/**
	 * @param bUHComments the bUHComments to set
	 */
	public void setbUHComments(String bUHComments) {
		this.bUHComments = bUHComments;
	}

	

	/**
	 * @return the bGCommentsTimestamp
	 */
	public Date getbGCommentsTimestamp() {
		return bGCommentsTimestamp;
	}

	/**
	 * @param bGCommentsTimestamp the bGCommentsTimestamp to set
	 */
	public void setbGCommentsTimestamp(Date bGCommentsTimestamp) {
		this.bGCommentsTimestamp = bGCommentsTimestamp;
	}

	/**
	 * @return the bGHComments
	 */
	public String getbGHComments() {
		return bGHComments;
	}

	/**
	 * @param bGHComments the bGHComments to set
	 */
	public void setbGHComments(String bGHComments) {
		this.bGHComments = bGHComments;
	}

	public void setTransferDate(Date transferDate) {
		this.transferDate = transferDate;
	}

	public org.yash.rms.domain.Resource getCurrentReportingManager() {
		return currentReportingManager;
	}

	public void setCurrentReportingManager(
			org.yash.rms.domain.Resource currentReportingManager) {
		this.currentReportingManager = currentReportingManager;
	}

	public org.yash.rms.domain.Resource getCurrentReportingManagerTwo() {
		return currentReportingManagerTwo;
	}

	public void setCurrentReportingManagerTwo(
			org.yash.rms.domain.Resource currentReportingManagerTwo) {
		this.currentReportingManagerTwo = currentReportingManagerTwo;
	}

	public OrgHierarchy getBuId() {
		return buId;
	}

	public void setBuId(OrgHierarchy buId) {
		this.buId = buId;
	}

	public OrgHierarchy getCurrentBuId() {
		return currentBuId;
	}

	public void setCurrentBuId(OrgHierarchy currentBuId) {
		this.currentBuId = currentBuId;
	}

	public Grade getGradeId() {
		return gradeId;
	}

	public void setGradeId(Grade gradeId) {
		this.gradeId = gradeId;
	}

	public Location getLocationId() {
		return locationId;
	}

	public void setLocationId(Location locationId) {
		this.locationId = locationId;
	}

	public Ownership getOwnership() {
		return ownership;
	}

	public void setOwnership(Ownership ownership) {
		this.ownership = ownership;
	}

	public Location getDeploymentLocationId() {
		return deploymentLocationId;
	}

	public void setDeploymentLocationId(Location deploymentLocationId) {
		this.deploymentLocationId = deploymentLocationId;
	}

	public Designation getDesignationId() {
		return designationId;
	}

	public void setDesignationId(Designation designationId) {
		this.designationId = designationId;
	}
	
	
	public org.yash.rms.domain.Resource getResourceId() {
		return resourceId;
	}

	public void setResourceId(org.yash.rms.domain.Resource resourceId) {
		this.resourceId = resourceId;
	}
	
	public String getCreatedId() {
		return createdId;
	}

	public void setCreatedId(String createdId) {
		this.createdId = createdId;
	}

	public String getLtStatus() {
		return ltStatus;
	}

	public void setLtStatus(String ltStatus) {
		this.ltStatus = ltStatus;
	}

	public String getYashEmpId() {
		return yashEmpId;
	}

	public void setYashEmpId(String yashEmpId) {
		this.yashEmpId = yashEmpId;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public String getContactNumberTwo() {
		return contactNumberTwo;
	}

	public void setContactNumberTwo(String contactNumberTwo) {
		this.contactNumberTwo = contactNumberTwo;
	}

	public Date getDateOfJoining() {
		return dateOfJoining;
	}

	public void setDateOfJoining(Date dateOfJoining) {
		this.dateOfJoining = dateOfJoining;
	}

	public Date getConfirmationDate() {
		return confirmationDate;
	}

	public void setConfirmationDate(Date confirmationDate) {
		this.confirmationDate = confirmationDate;
	}

	public Date getLastAppraisal() {
		return lastAppraisal;
	}

	public void setLastAppraisal(Date lastAppraisal) {
		this.lastAppraisal = lastAppraisal;
	}

	public Date getPenultimateAppraisal() {
		return penultimateAppraisal;
	}

	public void setPenultimateAppraisal(Date penultimateAppraisal) {
		this.penultimateAppraisal = penultimateAppraisal;
	}

	public Date getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}

	public EmployeeCategory getEmployeeCategory() {
		return employeeCategory;
	}

	public void setEmployeeCategory(EmployeeCategory employeeCategory) {
		this.employeeCategory = employeeCategory;
	}

	public Date getEndTransferDate() {
		return endTransferDate;
	}

	public void setEndTransferDate(Date endTransferDate) {
		this.endTransferDate = endTransferDate;
	}

	public String getRefResourceId() {
		return refResourceId;
	}
	public void setRefResourceId(String refResourceId) {
		this.refResourceId = refResourceId;
	}
	


	
}
