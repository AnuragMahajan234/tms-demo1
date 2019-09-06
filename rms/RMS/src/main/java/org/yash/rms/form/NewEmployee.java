package org.yash.rms.form;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Lob;

import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.yash.rms.domain.BGAdmin_Access_Rights;
import org.yash.rms.domain.Competency;
import org.yash.rms.domain.Designation;
import org.yash.rms.domain.EmployeeCategory;
import org.yash.rms.domain.Event;
import org.yash.rms.domain.Grade;
import org.yash.rms.domain.Location;
import org.yash.rms.domain.OrgHierarchy;
import org.yash.rms.domain.Ownership;
import org.yash.rms.domain.Project;
import org.yash.rms.domain.ResourceAllocation;
import org.yash.rms.domain.SkillResourcePrimary;
import org.yash.rms.domain.SkillResourceSecondary;
import org.yash.rms.domain.SkillsMapping;
import org.yash.rms.domain.Timehrs;
import org.yash.rms.domain.UserActivity;
import org.yash.rms.domain.Visa;

public class NewEmployee {

	private Integer employeeId;
	private String yashEmpId;

	private Integer actualCapacity;

	private String awardRecognition;

	private Date confirmationDate;
	
	private Date resignationDate;
	
	private Integer reportUserId;
	
	private Date endTransferDate;

	private String contactNumber;

	private String contactNumberThree;

	private String contactNumberTwo;

	private String customerIdDetail;

	private Date dateOfJoining;

	private String emailId;

	private Date lastAppraisal;

	private Date penultimateAppraisal;

	private Integer plannedCapacity;

	private String profitCentre;

	private Date releaseDate;

	private Date transferDate;

	private Date visaValid;

	private String userRole;

	private String userName;

	private String firstName;

	private String lastName;

	private Character timesheetCommentOptional;

	private CommonsMultipartFile file;

	private Double totalPlannedHrs;

	private Double totalReportedHrs;

	private Double totalBilledHrs;

	private String middleName;
	
	private String bGHComments;
	
private String bUHComments;
	
	private String hRComments;
	
	private org.yash.rms.domain.Resource bGHName;
	
private org.yash.rms.domain.Resource bUHName;
	
	private org.yash.rms.domain.Resource hRName;
	
	private Date bGCommentsTimestamp;
	
	private Date bUCommentsTimestamp;
	
	private Date bRCommentsTimestamp;
	
	private Character rejoiningFlag;
	
	private String projectIds;
	
	private Character rrfAccess;
	private double totalExper;
	private double relevantExper;
	 
	@Lob
    @Column(name ="upload_image" , length = 100000000)   //upload image
    private  byte[] uploadImage;
	/**Start- Added By Anjana for Resume and TEF upload Download **/
	@Lob
    @Column(name ="upload_resume" , length = 100000000)   //upload Resume
    private  byte[] uploadResume;
	
	@Lob
    @Column(name ="upload_tef" , length = 100000000)   //upload TEF
    private  byte[] uploadTEF;

	private String uploadResumeFileName;
	
	private String uploadTEFfileName;
	/**End- Added By Anjana for Resume and TEF upload Download **/

	public byte[] getUploadImage() {
		return uploadImage;
	}

	public void setUploadImage(byte[] uploadImage) {
		this.uploadImage = uploadImage;
	}	
	/**Start- Added By Anjana for Resume and TEF upload Download **/
	public byte[] getUploadResume() {
		return uploadResume;
	}

	public void setUploadResume(byte[] uploadResume) {
		this.uploadResume = uploadResume;
	}

	public byte[] getUploadTEF() {
		return uploadTEF;
	}

	public void setUploadTEF(byte[] uploadTEF) {
		this.uploadTEF = uploadTEF;
	}	

	public String getUploadResumeFileName() {
		return uploadResumeFileName;
	}

	public void setUploadResumeFileName(String uploadResumeFileName) {
		this.uploadResumeFileName = uploadResumeFileName;
	}

	public String getUploadTEFfileName() {
		return uploadTEFfileName;
	}

	public void setUploadTEFfileName(String uploadTEFfileName) {
		this.uploadTEFfileName = uploadTEFfileName;
	}
	/**End- Added By Anjana for Resume and TEF upload Download **/

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
	 * @return the bRCommentsTimestamp
	 */
	public Date getbRCommentsTimestamp() {
		return bRCommentsTimestamp;
	}

	/**
	 * @param bRCommentsTimestamp the bRCommentsTimestamp to set
	 */
	public void setbRCommentsTimestamp(Date bRCommentsTimestamp) {
		this.bRCommentsTimestamp = bRCommentsTimestamp;
	}

	public String getMiddleName() {
		return middleName;
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

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	private List<SkillsMapping> skillsMappingList;

	private org.yash.rms.domain.Resource currentReportingManager;

	private Set<org.yash.rms.domain.Resource> resources;

	private Set<org.yash.rms.domain.Resource> resources1;

	private org.yash.rms.domain.Resource currentReportingManagerTwo;
	
	private Set<BGAdmin_Access_Rights> bgAdminAccessRightlist;

	private String resumeFileName;

	private Set<Timehrs> timehrss;

	private Set<UserActivity> userActivities;

	private Set<ResourceAllocation> resourceAllocations;

	private OrgHierarchy currentBuId;

	private Grade gradeId;
	
	private Event eventId;

	private Project currentProjectId;

	private Visa visaId;

	private OrgHierarchy buId;

	private Location locationId;

	private Ownership ownership;
	
	private EmployeeCategory employeeCategory;
	
	private Competency competency;

	private Location deploymentLocation;
	
	private Location preferredLocation;
	
	public Location getPreferredLocation() {
		return preferredLocation;
	}

	public void setPreferredLocation(Location preferredLocation) {
		this.preferredLocation = preferredLocation;
	}

	public EmployeeCategory getEmployeeCategory() {
		return employeeCategory;
	}

	public void setEmployeeCategory(EmployeeCategory employeeCategory) {
		this.employeeCategory = employeeCategory;
	}

	private String deploymentLocationId;

	private Designation designationId;

	public String getDeploymentLocationId() {
		
		return deploymentLocationId;
	}

	public void setDeploymentLocationId(String deploymentLocationId) {
		this.deploymentLocationId = deploymentLocationId;
	}

	public Designation getDesignationId() {
		return designationId;
	}

	public void setDesignationId(Designation designationId) {
		this.designationId = designationId;
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

	private Set<SkillResourcePrimary> skillResourcePrimaries;

	private Set<SkillResourceSecondary> skillResourceSecondaries;

	public Integer getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
	}

	public Integer getActualCapacity() {
		return actualCapacity;
	}

	public void setActualCapacity(Integer actualCapacity) {
		this.actualCapacity = actualCapacity;
	}

	public String getAwardRecognition() {
		return awardRecognition;
	}

	public void setAwardRecognition(String awardRecognition) {
		this.awardRecognition = awardRecognition;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public String getContactNumberThree() {
		return contactNumberThree;
	}

	public void setContactNumberThree(String contactNumberThree) {
		this.contactNumberThree = contactNumberThree;
	}

	public String getContactNumberTwo() {
		return contactNumberTwo;
	}

	public void setContactNumberTwo(String contactNumberTwo) {
		this.contactNumberTwo = contactNumberTwo;
	}

	public String getCustomerIdDetail() {
		return customerIdDetail;
	}

	public void setCustomerIdDetail(String customerIdDetail) {
		this.customerIdDetail = customerIdDetail;
	}

	public CommonsMultipartFile getFile() {
		return file;
	}

	public void setFile(CommonsMultipartFile file) {
		this.file = file;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
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

	public Integer getPlannedCapacity() {
		return plannedCapacity;
	}

	public void setPlannedCapacity(Integer plannedCapacity) {
		this.plannedCapacity = plannedCapacity;
	}

	public String getProfitCentre() {
		return profitCentre;
	}

	public void setProfitCentre(String profitCentre) {
		this.profitCentre = profitCentre;
	}

	public Date getVisaValid() {
		return visaValid;
	}

	public void setVisaValid(Date visaValid) {
		this.visaValid = visaValid;
	}

	public String getUserRole() {
		return userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
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

	public Character getTimesheetCommentOptional() {
		return timesheetCommentOptional;
	}

	public void setTimesheetCommentOptional(Character timesheetCommentOptional) {
		this.timesheetCommentOptional = timesheetCommentOptional;
	}

	/*public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}*/

	public Double getTotalPlannedHrs() {
		return totalPlannedHrs;
	}

	public void setTotalPlannedHrs(Double totalPlannedHrs) {
		this.totalPlannedHrs = totalPlannedHrs;
	}

	public Double getTotalReportedHrs() {
		return totalReportedHrs;
	}

	public void setTotalReportedHrs(Double totalReportedHrs) {
		this.totalReportedHrs = totalReportedHrs;
	}

	public Double getTotalBilledHrs() {
		return totalBilledHrs;
	}

	public void setTotalBilledHrs(Double totalBilledHrs) {
		this.totalBilledHrs = totalBilledHrs;
	}

	public List<SkillsMapping> getSkillsMappingList() {
		return skillsMappingList;
	}

	public void setSkillsMappingList(List<SkillsMapping> skillsMappingList) {
		this.skillsMappingList = skillsMappingList;
	}

	public org.yash.rms.domain.Resource getCurrentReportingManager() {
		return currentReportingManager;
	}

	public void setCurrentReportingManager(
			org.yash.rms.domain.Resource currentReportingManager) {
		this.currentReportingManager = currentReportingManager;
	}

	public Set<org.yash.rms.domain.Resource> getResources() {
		return resources;
	}

	public void setResources(Set<org.yash.rms.domain.Resource> resources) {
		this.resources = resources;
	}

	public Set<org.yash.rms.domain.Resource> getResources1() {
		return resources1;
	}

	public void setResources1(Set<org.yash.rms.domain.Resource> resources1) {
		this.resources1 = resources1;
	}

	public org.yash.rms.domain.Resource getCurrentReportingManagerTwo() {
		return currentReportingManagerTwo;
	}

	public void setCurrentReportingManagerTwo(
			org.yash.rms.domain.Resource currentReportingManagerTwo) {
		this.currentReportingManagerTwo = currentReportingManagerTwo;
	}

	public String getResumeFileName() {
		return resumeFileName;
	}

	public void setResumeFileName(String resumeFileName) {
		this.resumeFileName = resumeFileName;
	}

	public Set<Timehrs> getTimehrss() {
		return timehrss;
	}

	public void setTimehrss(Set<Timehrs> timehrss) {
		this.timehrss = timehrss;
	}

	public Set<UserActivity> getUserActivities() {
		return userActivities;
	}

	public void setUserActivities(Set<UserActivity> userActivities) {
		this.userActivities = userActivities;
	}

	public Set<ResourceAllocation> getResourceAllocations() {
		return resourceAllocations;
	}

	public void setResourceAllocations(
			Set<ResourceAllocation> resourceAllocations) {
		this.resourceAllocations = resourceAllocations;
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

	public Project getCurrentProjectId() {
		return currentProjectId;
	}

	public void setCurrentProjectId(Project currentProjectId) {
		this.currentProjectId = currentProjectId;
	}

	public Visa getVisaId() {
		return visaId;
	}

	public void setVisaId(Visa visaId) {
		this.visaId = visaId;
	}

	public OrgHierarchy getBuId() {
		return buId;
	}

	public void setBuId(OrgHierarchy buId) {
		this.buId = buId;
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

 

	public Location getDeploymentLocation() {
		/*if(getDeploymentLocationId().equals(""))
		return null;
		else*/
		return deploymentLocation;
	}

	public void setDeploymentLocation(Location deploymentLocation) {
		this.deploymentLocation = deploymentLocation;
	}

	public String getYashEmpId() {
		return yashEmpId;
	}

	public void setYashEmpId(String yashEmpId) {
		this.yashEmpId = yashEmpId;
	}

	public Set<SkillResourcePrimary> getSkillResourcePrimaries() {
		return skillResourcePrimaries;
	}

	public void setSkillResourcePrimaries(
			Set<SkillResourcePrimary> skillResourcePrimaries) {
		this.skillResourcePrimaries = skillResourcePrimaries;
	}

	public Set<SkillResourceSecondary> getSkillResourceSecondaries() {
		return skillResourceSecondaries;
	}

	public void setSkillResourceSecondaries(
			Set<SkillResourceSecondary> skillResourceSecondaries) {
		this.skillResourceSecondaries = skillResourceSecondaries;
	}

	public Date getConfirmationDate() {
		return confirmationDate;
	}

	public void setConfirmationDate(Date confirmationDate) {
		this.confirmationDate = confirmationDate;
	}

	public Date getDateOfJoining() {
		return dateOfJoining;
	}

	public void setDateOfJoining(Date dateOfJoining) {
		this.dateOfJoining = dateOfJoining;
	}

	public Date getResignationDate() {
		return resignationDate;
	}

	public void setResignationDate(Date resignationDate) {
		this.resignationDate = resignationDate;
	}

	public Date getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}

	public Date getTransferDate() {
		return transferDate;
	}

	public void setTransferDate(Date transferDate) {
		this.transferDate = transferDate;
	}

	public Set<BGAdmin_Access_Rights> getBgAdminAccessRightlist() {
		return bgAdminAccessRightlist;
	}

	public void setBgAdminAccessRightlist(
			Set<BGAdmin_Access_Rights> bgAdminAccessRightlist) {
		this.bgAdminAccessRightlist = bgAdminAccessRightlist;
	}

	public Competency getCompetency() {
		return competency;
	}

	public void setCompetency(Competency competency) {
		this.competency = competency;
	}

	public Date getEndTransferDate() {
		return endTransferDate;
	}

	public void setEndTransferDate(Date endTransferDate) {
		this.endTransferDate = endTransferDate;
	}

	public Integer getReportUserId() {
		return reportUserId;
	}

	public void setReportUserId(Integer reportUserId) {
		this.reportUserId = reportUserId;
	}

	public Character getRejoiningFlag() {
		return rejoiningFlag;
	}

	public void setRejoiningFlag(Character rejoiningFlag) {
		this.rejoiningFlag = rejoiningFlag;
	}

	public String getProjectIds() {
		return projectIds;
	}

	public void setProjectIds(String projectIds) {
		this.projectIds = projectIds;
	}

	public Character getRrfAccess() {
		return rrfAccess;
	}

	public void setRrfAccess(Character rrfAccess) {
		this.rrfAccess = rrfAccess;
	}
	public double getTotalExper() {
		return totalExper;
	}
	public void setTotalExper(double totalExper) {
		this.totalExper = totalExper;
	}
	public double getRelevantExper() {
		return relevantExper;
	}
	public void setRelevantExper(double relevantExper) {
		this.relevantExper = relevantExper;
	}
}
