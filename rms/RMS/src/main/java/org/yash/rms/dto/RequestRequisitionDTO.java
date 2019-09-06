package org.yash.rms.dto;

import java.util.Date;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;
import org.yash.rms.domain.Customer;
import org.yash.rms.domain.CustomerGroup;
import org.yash.rms.domain.Experience;
import org.yash.rms.domain.Project;
import org.yash.rms.domain.Resource;
import org.yash.rms.domain.ShiftTypes;

public class RequestRequisitionDTO {

	private int id;

	private Resource resource;

	private Customer customer;

	private CustomerGroup group;

	private String projectBU;

	private String date;

	private String comments = "";

	private Project project;

	private String sentMailTo;

	private String notifyMailTo;

	private String pdlEmailIds;

	// Fields added for sending email
	private String[]  toMailIds;

	private String[] ccMailIds;

	private String sender;

	private String currentBU;

	private String sendMailFrom;

	private String customerName;

	private String groupName = "";
	
	private String projectName;
	
	private LocationDTO location;
	
	private Resource requestor;
	private Resource deliveryPOC;
	private String clientType;
	private String projectType;
	
	private Experience experience;
	
	private MultipartFile bUHFile;
	
	private MultipartFile bGHFile;
	
	private String BUHApprovalFileName;
	
	private String requiredFor;
	
	private String BGHApprovalFileName;
	
	private Integer projectDuration;
	
	private String resourceRequiredDate;
	
	private ShiftTypes shiftType;
	
	private String projectShiftOtherDetails;
	
	private String isClientInterviewRequired;
	
	private String isBGVrequired;
	
	private String expOtherDetails;
	
	private String hiringBGBU;
	
	private Resource replacementResource;
	
	private ReasonForReplacementDTO reason;
	
	private PriorityDTO priority;
	
	private String amJobCode;
	
	private String successFactorId;
	
	private String rmgPoc;
	
	private String techTeamPoc;
	
	private List<Resource> rmgPOCList;
	
	private List<Resource> techTeamPocList;
	
	private List<Resource> sentMailToList;

	private List<Resource> notifyMailToList;
	
	private Date lastupdatedTimestamp;
	
	private Date creationTimestamp;
	/*private EditProfileDTO buHead;
	
	private EditProfileDTO bgHead;*/
	
	public void setLastupdatedTimestamp(Date lastupdatedTimestamp) {
		this.lastupdatedTimestamp = lastupdatedTimestamp;
	}
	
	public Date getLastupdatedTimestamp() {
		return lastupdatedTimestamp;
	}
	
	public String getExpOtherDetails() {
		return expOtherDetails;
	}

	public void setExpOtherDetails(String expOtherDetails) {
		this.expOtherDetails = expOtherDetails;
	}

	public String getRequiredFor() {
		return requiredFor;
	}

	public void setRequiredFor(String requiredFor) {
		this.requiredFor = requiredFor;
	}
	public String getNotifyMailTo() {
		return notifyMailTo;
	}

	public void setNotifyMailTo(String notifyMailTo) {
		this.notifyMailTo = notifyMailTo;
	}

	public String getSentMailTo() {
		return sentMailTo;
	}

	public void setSentMailTo(String sentMailTo) {
		this.sentMailTo = sentMailTo;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Resource getResource() {
		return resource;
	}

	public void setResource(Resource employee) {
		this.resource = employee;
	}

	public String getProjectBU() {
		return projectBU;
	}

	public void setProjectBU(String projectBU) {
		this.projectBU = projectBU;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Customer getCustomer() {
		return customer;
	}

	public CustomerGroup getGroup() {
		return group;
	}

	public void setGroup(CustomerGroup group) {
		this.group = group;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public String getPdlEmailIds() {
		return pdlEmailIds;
	}

	public void setPdlEmailIds(String pdlEmailIds) {
		this.pdlEmailIds = pdlEmailIds;
	}
	
	public String[] getToMailIds() {
		return toMailIds;
	}

	public void setToMailIds(String[] toMailIds) {
		this.toMailIds = toMailIds;
	}

	public String[] getCcMailIds() {
		return ccMailIds;
	}

	public void setCcMailIds(String[] ccMailIds) {
		this.ccMailIds = ccMailIds;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getCurrentBU() {
		return currentBU;
	}

	public void setCurrentBU(String currentBU) {
		this.currentBU = currentBU;
	}

	public String getSendMailFrom() {
		return sendMailFrom;
	}

	public void setSendMailFrom(String sendMailFrom) {
		this.sendMailFrom = sendMailFrom;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public LocationDTO getLocation() {
		return location;
	}

	public void setLocation(LocationDTO location) {
		this.location = location;
	}
	public Resource getRequestor() {
		return requestor;
	}

	public void setRequestor(Resource requestor) {
		this.requestor = requestor;
	}

	public Resource getDeliveryPOC() {
		return deliveryPOC;
	}

	public void setDeliveryPOC(Resource deliveryPOC) {
		this.deliveryPOC = deliveryPOC;
	}

	public String getClientType() {
		return clientType;
	}

	public void setClientType(String clientType) {
		this.clientType = clientType;
	}

	public String getProjectType() {
		return projectType;
	}

	public void setProjectType(String projectType) {
		this.projectType = projectType;
	}

	public Experience getExperience() {
		return experience;
	}

	public void setExperience(Experience experience) {
		this.experience = experience;
	}

	public String getBUHApprovalFileName() {
		return BUHApprovalFileName;
	}

	
	public String getBGHApprovalFileName() {
		return BGHApprovalFileName;
	}



	public Integer getProjectDuration() {
		return projectDuration;
	}

	public void setProjectDuration(Integer projectDuration) {
		this.projectDuration = projectDuration;
	}

	public String getResourceRequiredDate() {
		return resourceRequiredDate;
	}

	public void setResourceRequiredDate(String resourceRequiredDate) {
		this.resourceRequiredDate = resourceRequiredDate;
	}

	public ShiftTypes getShiftType() {
		return shiftType;
	}

	public void setShiftType(ShiftTypes shiftType) {
		this.shiftType = shiftType;
	}

	public String getProjectShiftOtherDetails() {
		return projectShiftOtherDetails;
	}

	public void setProjectShiftOtherDetails(String projectShiftOtherDetails) {
		this.projectShiftOtherDetails = projectShiftOtherDetails;
	}

	public String getIsClientInterviewRequired() {
		return isClientInterviewRequired;
	}

	public void setIsClientInterviewRequired(String isClientInterviewRequired) {
		this.isClientInterviewRequired = isClientInterviewRequired;
	}

	public String getIsBGVrequired() {
		return isBGVrequired;
	}

	public void setIsBGVrequired(String isBGVrequired) {
		this.isBGVrequired = isBGVrequired;
	}

	public MultipartFile getbUHFile() {
		return bUHFile;
	}

	public void setbUHFile(MultipartFile bUHFile) {
		this.bUHFile = bUHFile;
	}

	public MultipartFile getbGHFile() {
		return bGHFile;
	}

	public void setbGHFile(MultipartFile bGHFile) {
		this.bGHFile = bGHFile;
	}

	public String getbUHApprovalFileName() {
		return BUHApprovalFileName;
	}

	public void setbUHApprovalFileName(String bUHApprovalFileName) {
		this.BUHApprovalFileName = bUHApprovalFileName;
	}

	public String getbGHApprovalFileName() {
		return BGHApprovalFileName;
	}

	public void setbGHApprovalFileName(String bGHApprovalFileName) {
		this.BGHApprovalFileName = bGHApprovalFileName;
	}

	public String getHiringBGBU() {
		return hiringBGBU;
	}

	public void setHiringBGBU(String hiringBGBU) {
		this.hiringBGBU = hiringBGBU;
	}

	public Resource getReplacementResource() {
		return replacementResource;
	}

	public void setReplacementResource(Resource replacementResource) {
		this.replacementResource = replacementResource;
	}

	public ReasonForReplacementDTO getReason() {
		return reason;
	}

	public void setReason(ReasonForReplacementDTO reason) {
		this.reason = reason;
	}

	public PriorityDTO getPriority() {
		return priority;
	}

	public void setPriority(PriorityDTO priority) {
		this.priority = priority;
	}

	/*public EditProfileDTO getBuHead() {
		return buHead;
	}

	public void setBuHead(EditProfileDTO buHead) {
		this.buHead = buHead;
	}

	public EditProfileDTO getBgHead() {
		return bgHead;
	}

	public void setBgHead(EditProfileDTO bgHead) {
		this.bgHead = bgHead;
	}
*/
	public String getAmJobCode() {
		return amJobCode;
	}

	public void setAmJobCode(String amJobCode) {
		this.amJobCode = amJobCode;
	}

	public String getSuccessFactorId() {
		return successFactorId;
	}

	public void setSuccessFactorId(String successFactorId) {
		this.successFactorId = successFactorId;
	}

	public String getRmgPoc() {
		return rmgPoc;
	}

	public void setRmgPoc(String rmgPoc) {
		this.rmgPoc = rmgPoc;
	}

	public String getTechTeamPoc() {
		return techTeamPoc;
	}

	public void setTechTeamPoc(String techTeamPoc) {
		this.techTeamPoc = techTeamPoc;
	}

	public List<Resource> getRmgPOCList() {
		return rmgPOCList;
	}

	public void setRmgPOCList(List<Resource> rmgPOCList) {
		this.rmgPOCList = rmgPOCList;
	}

	public List<Resource> getTechTeamPocList() {
		return techTeamPocList;
	}

	public void setTechTeamPocList(List<Resource> techTeamPocList) {
		this.techTeamPocList = techTeamPocList;
	}

	public List<Resource> getSentMailToList() {
		return sentMailToList;
	}

	public void setSentMailToList(List<Resource> sentMailToList) {
		this.sentMailToList = sentMailToList;
	}

	public List<Resource> getNotifyMailToList() {
		return notifyMailToList;
	}

	public void setNotifyMailToList(List<Resource> notifyMailToList) {
		this.notifyMailToList = notifyMailToList;
	}

	public Date getCreationTimestamp() {
		return creationTimestamp;
	}
	
	public void setCreationTimestamp(Date creationTimestamp) {
		this.creationTimestamp = creationTimestamp;
	}

	
	
	
	
}
