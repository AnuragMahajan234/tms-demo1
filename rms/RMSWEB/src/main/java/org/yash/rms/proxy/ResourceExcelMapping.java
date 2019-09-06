package org.yash.rms.proxy;

import java.util.Date;

import org.hibernate.validator.constraints.NotBlank;
import org.yash.rms.excel.Excel;
import org.yash.rms.excel.ExcelColumn;


@Excel
public class ResourceExcelMapping {
	@NotBlank(message = "Employee ID must not be blank.")
	@ExcelColumn(columnName = "Employee ID")
	private String yashEmpId;

	@NotBlank(message = "First Name must not be blank.")
	@ExcelColumn(columnName = "First Name")
	private String firstName;
	
	@NotBlank(message = "Last Name must not be blank.")
	@ExcelColumn(columnName = "Last Name")
	private String lastName;
	
	@NotBlank(message = "Designation must not be blank.")
	@ExcelColumn(columnName = "Designation")
	private Integer designation_Id;

	@NotBlank(message = "Grade must not be blank.")
	@ExcelColumn(columnName = "Grade")
	private Integer grade_Id;

	@NotBlank(message = "E-Mail ID must not be blank.")
	@ExcelColumn(columnName = "E-Mail ID")
	private String emailId;

	@NotBlank(message = "Ownership must not be blank.")
	@ExcelColumn(columnName = "Ownership")
	private Integer ownership_Id;

	@NotBlank(message = "Actual Capacity must not be blank.")
	@ExcelColumn(columnName = "Actual Capacity")
	private Integer actualCapacity;

	@NotBlank(message = "Planned capacity must not be blank.")
	@ExcelColumn(columnName = "Planned capacity")
	private Integer plannedCapacity;

	@NotBlank(message = "Date Of Joining must not be blank.")
	@ExcelColumn(columnName = "DOJ")
	private Date dateOfJoining;

	@NotBlank(message = "Locations must not be blank.")
	@ExcelColumn(columnName = "Locations")
	private Integer location_id;

	@NotBlank(message = "deploymentLocation must not be blank.")
	@ExcelColumn(columnName = "Deployment location")
	private Integer deploymentLocation_id;

	@ExcelColumn(columnName = "BU")
	private Integer buId;
	
	@ExcelColumn(columnName = "Current_BU")
	private Integer currentBuId;

	@ExcelColumn(columnName = "Contact_Number")
	private String contactNumber;

	@ExcelColumn(columnName = "Contact_Number_2")
	private String contactNumberTwo;

	@ExcelColumn(columnName = "Transfer Date")
	private Date transferDate;

	@ExcelColumn(columnName = "VISA Status")
	private Integer visaId;

	@ExcelColumn(columnName = "Visa valid until Date")
	private Date visaValidDate;

	@ExcelColumn(columnName = "Appraisal Date 1")
	private Date lastAppraisalDate;

	@ExcelColumn(columnName = "Appraisal Date 2")
	private Date penultimateAppraisalDate;

	@ExcelColumn(columnName = "Current Project")
	private Integer currentProjectId;

	@ExcelColumn(columnName = "Current RM 1")
	private Integer currentReportingManager;

	@ExcelColumn(columnName = "Current RM 2")
	private Integer current_reporting_manager_two;

	@ExcelColumn(columnName = "Customer User ID Details")
	private String customerIdDetail;

	@ExcelColumn(columnName = "Primary Skills")
	private String primarySkills;

	@ExcelColumn(columnName = "Secondary Skills")
	private String secondarySkills;

	@ExcelColumn(columnName = "Confirmation Date")
	private Date confirmationDate;

	@ExcelColumn(columnName = "Awards and recognitions")
	private String awardRecognition;

	@ExcelColumn(columnName = "Profit Centre")
	private String profitCentre;

	
	@ExcelColumn(columnName = "Release Date")
	private Date releaseDate;
	
	@ExcelColumn(columnName = "UserName")
	private String userName;

	@ExcelColumn(columnName = "UserRole")
	private String userRole;
	
	

	@NotBlank(message = "Middle Name must not be blank.")
	@ExcelColumn(columnName = "Middle Name")
	private String middleName;
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserRole() {
		return userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}

	public String getYashEmpId() {
		return yashEmpId;
	}

	public void setYashEmpId(String yashEmpId) {
		this.yashEmpId = yashEmpId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Integer getGrade_Id() {
		return grade_Id;
	}

	public void setGrade_Id(Integer grade_Id) {
		this.grade_Id = grade_Id;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public Integer getOwnership_Id() {
		return ownership_Id;
	}

	public void setOwnership_Id(Integer ownership_Id) {
		this.ownership_Id = ownership_Id;
	}

	public Integer getActualCapacity() {
		return actualCapacity;
	}

	public void setActualCapacity(Integer actualCapacity) {
		this.actualCapacity = actualCapacity;
	}

	public Integer getPlannedCapacity() {
		return plannedCapacity;
	}

	public void setPlannedCapacity(Integer plannedCapacity) {
		this.plannedCapacity = plannedCapacity;
	}

	public Date getDateOfJoining() {
		return dateOfJoining;
	}

	public void setDateOfJoining(Date dateOfJoining) {
		this.dateOfJoining = dateOfJoining;
	}

	public Integer getDesignation_Id() {
		return designation_Id;
	}

	public void setDesignation_Id(Integer designation_Id) {
		this.designation_Id = designation_Id;
	}

	public Integer getDeploymentLocation_id() {
		return deploymentLocation_id;
	}

	public void setDeploymentLocation_id(Integer deploymentLocation_id) {
		this.deploymentLocation_id = deploymentLocation_id;
	}

	public Integer getLocation_id() {
		return location_id;
	}

	public void setLocation_id(Integer location_id) {
		this.location_id = location_id;
	}

	public Integer getBuId() {
		return buId;
	}

	public void setBuId(Integer buId) {
		this.buId = buId;
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

	public Date getTransferDate() {
		return transferDate;
	}

	public void setTransferDate(Date transferDate) {
		this.transferDate = transferDate;
	}

	public Integer getVisaId() {
		return visaId;
	}

	public void setVisaId(Integer visaId) {
		this.visaId = visaId;
	}

	public Date getVisaValidDate() {
		return visaValidDate;
	}

	public void setVisaValidDate(Date visaValidDate) {
		this.visaValidDate = visaValidDate;
	}

	public Date getLastAppraisalDate() {
		return lastAppraisalDate;
	}

	public void setLastAppraisalDate(Date lastAppraisalDate) {
		this.lastAppraisalDate = lastAppraisalDate;
	}

	public Date getPenultimateAppraisalDate() {
		return penultimateAppraisalDate;
	}

	public void setPenultimateAppraisalDate(Date penultimateAppraisalDate) {
		this.penultimateAppraisalDate = penultimateAppraisalDate;
	}

	
	public Integer getCurrentProjectId() {
		return currentProjectId;
	}

	public void setCurrentProjectId(Integer currentProjectId) {
		this.currentProjectId = currentProjectId;
	}

	public Integer getCurrentReportingManager() {
		return currentReportingManager;
	}

	public void setCurrentReportingManager(Integer currentReportingManager) {
		this.currentReportingManager = currentReportingManager;
	}

	public Integer getCurrent_reporting_manager_two() {
		return current_reporting_manager_two;
	}

	public void setCurrent_reporting_manager_two(Integer current_reporting_manager_two) {
		this.current_reporting_manager_two = current_reporting_manager_two;
	}

	public String getCustomerIdDetail() {
		return customerIdDetail;
	}

	public void setCustomerIdDetail(String customerIdDetail) {
		this.customerIdDetail = customerIdDetail;
	}

	public String getPrimarySkills() {
		return primarySkills;
	}

	public void setPrimarySkills(String primarySkills) {
		this.primarySkills = primarySkills;
	}

	public String getSecondarySkills() {
		return secondarySkills;
	}

	public void setSecondarySkills(String secondarySkills) {
		this.secondarySkills = secondarySkills;
	}

	public Date getConfirmationDate() {
		return confirmationDate;
	}

	public void setConfirmationDate(Date confirmationDate) {
		this.confirmationDate = confirmationDate;
	}

	public String getAwardRecognition() {
		return awardRecognition;
	}

	public void setAwardRecognition(String awardRecognition) {
		this.awardRecognition = awardRecognition;
	}

	public String getProfitCentre() {
		return profitCentre;
	}

	public void setProfitCentre(String profitCentre) {
		this.profitCentre = profitCentre;
	}

	public Date getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}

	public Integer getCurrentBuId() {
		return currentBuId;
	}

	public void setCurrentBuId(Integer currentBuId) {
		this.currentBuId = currentBuId;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	

}
