package org.yash.rms.form;

import java.util.Date;

 







import javax.persistence.Column;

import org.yash.rms.domain.Designation;
import org.yash.rms.domain.Event;
import org.yash.rms.domain.Grade;
import org.yash.rms.domain.Location;
import org.yash.rms.domain.OrgHierarchy;
import org.yash.rms.domain.Ownership;

public class EventDataForm {

	private String id;
	
	private String event;
	
	private String createdId;
	
	private Date creationTimestamp;

	private String lastUpdatedId;
	
	private Date lastupdatedTimestamp;
	
	private String description;
	
	private Character payrollLocation;
	
	private Character ownership;

	private Character locationId;

	private Character gradeId;

	private Character designationId;

	private Character currentReportingTwo;

	private Character currentReportingManager;

	private Character buId;

	private Character transferDate;
	
	//Added
	private Character endTransferDate;

	private Character employeeId;

	private Character bUHName;

	private Character bUHComments;

	private Character hRCommentsTimestamp;

	private Character bUCommentsTimestamp;

	private Character bGCommentsTimestamp;

	private Character hRName;

	private Character hRComments;

	private Character bGHName;

	private Character bGHComments;

	private Character releaseDate;

	private Character penultimateAppraisal;

	private Character lastAppraisal;

	private Character confirmationDate;

	private Character dateOfJoining;

	private Character contactNumberTwo;

	private Character contactNumber;

	private Character emailId;

	private Character yashEmpId;

	private Character currentBuId;
	
	private Character employeeCategory;


	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}


	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}


	


	/**
	 * @return the event
	 */
	public String getEvent() {
		return event;
	}


	/**
	 * @param event the event to set
	 */
	public void setEvent(String event) {
		this.event = event;
	}


	/**
	 * @return the createdId
	 */
	public String getCreatedId() {
		return createdId;
	}

	/**
	 * @param createdId the createdId to set
	 */
	public void setCreatedId(String createdId) {
		this.createdId = createdId;
	}

	/**
	 * @return the creationTimestamp
	 */
	public Date getCreationTimestamp() {
		return creationTimestamp;
	}

	/**
	 * @param creationTimestamp the creationTimestamp to set
	 */
	public void setCreationTimestamp(Date creationTimestamp) {
		this.creationTimestamp = creationTimestamp;
	}

	/**
	 * @return the lastUpdatedId
	 */
	public String getLastUpdatedId() {
		return lastUpdatedId;
	}

	/**
	 * @param lastUpdatedId the lastUpdatedId to set
	 */
	public void setLastUpdatedId(String lastUpdatedId) {
		this.lastUpdatedId = lastUpdatedId;
	}

	/**
	 * @return the lastupdatedTimestamp
	 */
	public Date getLastupdatedTimestamp() {
		return lastupdatedTimestamp;
	}

	/**
	 * @param lastupdatedTimestamp the lastupdatedTimestamp to set
	 */
	public void setLastupdatedTimestamp(Date lastupdatedTimestamp) {
		this.lastupdatedTimestamp = lastupdatedTimestamp;
	}

	/**
	 * @return the payrollLocation
	 */
	public Character getPayrollLocation() {
		return payrollLocation;
	}

	/**
	 * @param payrollLocation the payrollLocation to set
	 */
	public void setPayrollLocation(Character payrollLocation) {
		this.payrollLocation = payrollLocation;
	}

	/**
	 * @return the ownership
	 */
	public Character getOwnership() {
		return ownership;
	}

	/**
	 * @param ownership the ownership to set
	 */
	public void setOwnership(Character ownership) {
		this.ownership = ownership;
	}

	/**
	 * @return the locationId
	 */
	public Character getLocationId() {
		return locationId;
	}

	/**
	 * @param locationId the locationId to set
	 */
	public void setLocationId(Character locationId) {
		this.locationId = locationId;
	}

	/**
	 * @return the gradeId
	 */
	public Character getGradeId() {
		return gradeId;
	}

	/**
	 * @param gradeId the gradeId to set
	 */
	public void setGradeId(Character gradeId) {
		this.gradeId = gradeId;
	}

	/**
	 * @return the designationId
	 */
	public Character getDesignationId() {
		return designationId;
	}

	/**
	 * @param designationId the designationId to set
	 */
	public void setDesignationId(Character designationId) {
		this.designationId = designationId;
	}

	/**
	 * @return the currentReportingTwo
	 */
	public Character getCurrentReportingTwo() {
		return currentReportingTwo;
	}

	/**
	 * @param currentReportingTwo the currentReportingTwo to set
	 */
	public void setCurrentReportingTwo(Character currentReportingTwo) {
		this.currentReportingTwo = currentReportingTwo;
	}

	/**
	 * @return the currentReportingManager
	 */
	public Character getCurrentReportingManager() {
		return currentReportingManager;
	}

	/**
	 * @param currentReportingManager the currentReportingManager to set
	 */
	public void setCurrentReportingManager(Character currentReportingManager) {
		this.currentReportingManager = currentReportingManager;
	}

	/**
	 * @return the buId
	 */
	public Character getBuId() {
		return buId;
	}

	/**
	 * @param buId the buId to set
	 */
	public void setBuId(Character buId) {
		this.buId = buId;
	}

	/**
	 * @return the transferDate
	 */
	public Character getTransferDate() {
		return transferDate;
	}

	/**
	 * @param transferDate the transferDate to set
	 */
	public void setTransferDate(Character transferDate) {
		this.transferDate = transferDate;
	}

	/**
	 * @return the employeeId
	 */
	public Character getEmployeeId() {
		return employeeId;
	}

	/**
	 * @param employeeId the employeeId to set
	 */
	public void setEmployeeId(Character employeeId) {
		this.employeeId = employeeId;
	}

	/**
	 * @return the bUHName
	 */
	public Character getbUHName() {
		return bUHName;
	}

	/**
	 * @param bUHName the bUHName to set
	 */
	public void setbUHName(Character bUHName) {
		this.bUHName = bUHName;
	}

	/**
	 * @return the bUHComments
	 */
	public Character getbUHComments() {
		return bUHComments;
	}

	/**
	 * @param bUHComments the bUHComments to set
	 */
	public void setbUHComments(Character bUHComments) {
		this.bUHComments = bUHComments;
	}

	/**
	 * @return the hRCommentsTimestamp
	 */
	public Character gethRCommentsTimestamp() {
		return hRCommentsTimestamp;
	}

	/**
	 * @param hRCommentsTimestamp the hRCommentsTimestamp to set
	 */
	public void sethRCommentsTimestamp(Character hRCommentsTimestamp) {
		this.hRCommentsTimestamp = hRCommentsTimestamp;
	}

	/**
	 * @return the bUCommentsTimestamp
	 */
	public Character getbUCommentsTimestamp() {
		return bUCommentsTimestamp;
	}

	/**
	 * @param bUCommentsTimestamp the bUCommentsTimestamp to set
	 */
	public void setbUCommentsTimestamp(Character bUCommentsTimestamp) {
		this.bUCommentsTimestamp = bUCommentsTimestamp;
	}

	/**
	 * @return the bGCommentsTimestamp
	 */
	public Character getbGCommentsTimestamp() {
		return bGCommentsTimestamp;
	}

	/**
	 * @param bGCommentsTimestamp the bGCommentsTimestamp to set
	 */
	public void setbGCommentsTimestamp(Character bGCommentsTimestamp) {
		this.bGCommentsTimestamp = bGCommentsTimestamp;
	}

	/**
	 * @return the hRName
	 */
	public Character gethRName() {
		return hRName;
	}

	/**
	 * @param hRName the hRName to set
	 */
	public void sethRName(Character hRName) {
		this.hRName = hRName;
	}

	/**
	 * @return the hRComments
	 */
	public Character gethRComments() {
		return hRComments;
	}

	/**
	 * @param hRComments the hRComments to set
	 */
	public void sethRComments(Character hRComments) {
		this.hRComments = hRComments;
	}

	/**
	 * @return the bGHName
	 */
	public Character getbGHName() {
		return bGHName;
	}

	/**
	 * @param bGHName the bGHName to set
	 */
	public void setbGHName(Character bGHName) {
		this.bGHName = bGHName;
	}

	/**
	 * @return the bGHComments
	 */
	public Character getbGHComments() {
		return bGHComments;
	}

	/**
	 * @param bGHComments the bGHComments to set
	 */
	public void setbGHComments(Character bGHComments) {
		this.bGHComments = bGHComments;
	}

	/**
	 * @return the releaseDate
	 */
	public Character getReleaseDate() {
		return releaseDate;
	}

	/**
	 * @param releaseDate the releaseDate to set
	 */
	public void setReleaseDate(Character releaseDate) {
		this.releaseDate = releaseDate;
	}

	/**
	 * @return the penultimateAppraisal
	 */
	public Character getPenultimateAppraisal() {
		return penultimateAppraisal;
	}

	/**
	 * @param penultimateAppraisal the penultimateAppraisal to set
	 */
	public void setPenultimateAppraisal(Character penultimateAppraisal) {
		this.penultimateAppraisal = penultimateAppraisal;
	}

	/**
	 * @return the lastAppraisal
	 */
	public Character getLastAppraisal() {
		return lastAppraisal;
	}

	/**
	 * @param lastAppraisal the lastAppraisal to set
	 */
	public void setLastAppraisal(Character lastAppraisal) {
		this.lastAppraisal = lastAppraisal;
	}

	/**
	 * @return the confirmationDate
	 */
	public Character getConfirmationDate() {
		return confirmationDate;
	}

	/**
	 * @param confirmationDate the confirmationDate to set
	 */
	public void setConfirmationDate(Character confirmationDate) {
		this.confirmationDate = confirmationDate;
	}

	/**
	 * @return the dateOfJoining
	 */
	public Character getDateOfJoining() {
		return dateOfJoining;
	}

	/**
	 * @param dateOfJoining the dateOfJoining to set
	 */
	public void setDateOfJoining(Character dateOfJoining) {
		this.dateOfJoining = dateOfJoining;
	}

	/**
	 * @return the contactNumberTwo
	 */
	public Character getContactNumberTwo() {
		return contactNumberTwo;
	}

	/**
	 * @param contactNumberTwo the contactNumberTwo to set
	 */
	public void setContactNumberTwo(Character contactNumberTwo) {
		this.contactNumberTwo = contactNumberTwo;
	}

	/**
	 * @return the contactNumber
	 */
	public Character getContactNumber() {
		return contactNumber;
	}

	/**
	 * @param contactNumber the contactNumber to set
	 */
	public void setContactNumber(Character contactNumber) {
		this.contactNumber = contactNumber;
	}

	/**
	 * @return the emailId
	 */
	public Character getEmailId() {
		return emailId;
	}

	/**
	 * @param emailId the emailId to set
	 */
	public void setEmailId(Character emailId) {
		this.emailId = emailId;
	}

	/**
	 * @return the yashEmpId
	 */
	public Character getYashEmpId() {
		return yashEmpId;
	}

	/**
	 * @param yashEmpId the yashEmpId to set
	 */
	public void setYashEmpId(Character yashEmpId) {
		this.yashEmpId = yashEmpId;
	}

	/**
	 * @return the currentBuId
	 */
	public Character getCurrentBuId() {
		return currentBuId;
	}

	/**
	 * @param currentBuId the currentBuId to set
	 */
	public void setCurrentBuId(Character currentBuId) {
		this.currentBuId = currentBuId;
	}


	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}


	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}


	/**
	 * @return the employeeCategory
	 */
	public Character getEmployeeCategory() {
		return employeeCategory;
	}


	/**
	 * @param employeeCategory the employeeCategory to set
	 */
	public void setEmployeeCategory(Character employeeCategory) {
		this.employeeCategory = employeeCategory;
	}


	public Character getEndTransferDate() {
		return endTransferDate;
	}


	public void setEndTransferDate(Character endTransferDate) {
		this.endTransferDate = endTransferDate;
	}

	
	
}
