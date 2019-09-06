package org.yash.rms.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "skill_request_resource")
public class RequestRequisitionResource implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7388154179837401630L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "skill_request_id", referencedColumnName = "id", nullable = false)
	private RequestRequisitionSkill requestRequisitionSkill;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "resource_id", referencedColumnName="employee_id")
	private Resource resource;
	
	/*@Column(name = "resource_id")
	private Integer resourceId;*/

	@Column(name = "external_resource_name")
	private String externalResourceName;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "status_id")
	private RequestRequisitionResourceStatus requestRequisitionResourceStatus;
   
	//, insertable=false, updatable=true , referencedColumnName="id"
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "allocation_id" )
	private AllocationType allocationType;
	
	@Column(name = "email_id", length = 256)
	private String emailId;
	
	@Column(name = "contact_number", length = 256)
	private String contactNumber;
	
	@Column(name = "total_experience")
	private String totalExperience;
	
	@Column(name = "skills")
	private String skills;
	
	@Column(name = "allocation_date")
	@DateTimeFormat(style = "M-")
	private Date allocationDate;
	
	@Column(name = "interview_date")
	@DateTimeFormat(style = "M-")
	private Date interviewDate;
	

	@Lob
    @Column(name ="upload_resume")   //upload Resume
    private byte[] uploadResume;
	
	//private String uploadResumeFileName;
	@Column(name= "resume_file_name")
	private String resumeFileName;
	
	@Column(name = "upload_tac_file_name", length = 256)
	private String uploadTacFileName;
	
	@Lob
    @Column(name ="upload_Tac_File")   //upload TAC
    private  byte[] uploadTacFile;
	
	@Column(name= "location")
	private String location;
	
	@Column(name= "notice_Period")
	private String noticePeriod;
	
	@Column(name = "creation_timestamp")
	private Date creationTimestamp;
	@Column(name = "lastupdated_timestamp")
	private Date lastupdatedTimestamp;
	
	@Column(name = "dt_submitted_to_poc")
	private Date submittedToPocDate;
	@Column(name = "dt_submitted_to_am")
	private Date submittedToAmDate;
	@Column(name = "dt_shortlisted")
	private Date shortlistedDate;
	@Column(name = "dt_rejection")
	private Date rejectionDate;
	
	@Column(name = "dt_joined")
	private Date joinedDate;
	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getNoticePeriod() {
		return noticePeriod;
	}

	public void setNoticePeriod(String noticePeriod) {
		this.noticePeriod = noticePeriod;
	}
	public String getUploadTacFileName() {
		return uploadTacFileName;
	}

	public void setUploadTacFileName(String uploadTacFileName) {
		this.uploadTacFileName = uploadTacFileName;
	}

	public byte[] getUploadTacFile() {
		return uploadTacFile;
	}

	public void setUploadTacFile(byte[] uploadTacFile) {
		this.uploadTacFile = uploadTacFile;
	}
	
	public Date getAllocationDate() {
		return allocationDate;
	}

	public void setAllocationDate(Date allocationDate) {
		this.allocationDate = allocationDate;
	}

	public AllocationType getAllocation() {
		return allocationType;
	}

	public void setAllocation(AllocationType allocationId) {
		this.allocationType = allocationId;
	}

	public RequestRequisitionResourceStatus getRequestRequisitionResourceStatus() {
		return requestRequisitionResourceStatus;
	}

	public void setRequestRequisitionResourceStatus(RequestRequisitionResourceStatus requestRequisitionResourceStatus) {
		this.requestRequisitionResourceStatus = requestRequisitionResourceStatus;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public RequestRequisitionSkill getRequestRequisitionSkill() {
		return requestRequisitionSkill;
	}

	public void setRequestRequisitionSkill(RequestRequisitionSkill requestRequisitionSkill) {
		this.requestRequisitionSkill = requestRequisitionSkill;
	}

	public Resource getResource() {
		return resource;
	}

	public void setResource(Resource resource) {
		this.resource = resource;
	}

	public String getExternalResourceName() {
		return externalResourceName;
	}

	public void setExternalResourceName(String externalResourceName) {
		this.externalResourceName = externalResourceName;
	}

	public byte[] getUploadResume() {
		return uploadResume;
	}
	
	public void setUploadResume(byte[] uploadResume) {
		this.uploadResume = uploadResume;
	}

	public String getResumeFileName() {
		return resumeFileName;
	}

	public void setResumeFileName(String resumeFileName) {
		this.resumeFileName = resumeFileName;
	}

	public Date getInterviewDate() {
		return interviewDate;
	}

	public void setInterviewDate(Date interviewDate) {
		this.interviewDate = interviewDate;
	}

	public Date getCreationTimestamp() {
		return creationTimestamp;
	}
	public void setCreationTimestamp(Date creationTimestamp) {
		this.creationTimestamp = creationTimestamp;
	}
	
	public Date getLastupdatedTimestamp() {
		return lastupdatedTimestamp;
	}
	public void setLastupdatedTimestamp(Date lastupdatedTimestamp) {
		this.lastupdatedTimestamp = lastupdatedTimestamp;
	}
	public Date getSubmittedToPocDate() {
		return submittedToPocDate;
	}

	public void setSubmittedToPocDate(Date submittedToPocDate) {
		this.submittedToPocDate = submittedToPocDate;
	}

	public Date getSubmittedToAmDate() {
		return submittedToAmDate;
	}

	public void setSubmittedToAmDate(Date submittedToAmDate) {
		this.submittedToAmDate = submittedToAmDate;
	}

	public Date getShortlistedDate() {
		return shortlistedDate;
	}

	public void setShortlistedDate(Date shortlistedDate) {
		this.shortlistedDate = shortlistedDate;
	}

	public Date getRejectionDate() {
		return rejectionDate;
	}

	public void setRejectionDate(Date rejectionDate) {
		this.rejectionDate = rejectionDate;
	}

	public Date getJoinedDate() {
		return joinedDate;
	}

	public void setJoinedDate(Date joinedDate) {
		this.joinedDate = joinedDate;
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

	public String getTotalExperience() {
		return totalExperience;
	}

	public void setTotalExperience(String totalExperience) {
		this.totalExperience = totalExperience;
	}

	public String getSkills() {
		return skills;
	}

	public void setSkills(String skills) {
		this.skills = skills;
	}
	
}
