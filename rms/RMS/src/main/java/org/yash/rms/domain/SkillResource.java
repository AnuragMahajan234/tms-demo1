package org.yash.rms.domain;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.yash.rms.util.Constants;

import flexjson.JSONSerializer;
import flexjson.transformer.DateTransformer;

@Entity
@Table(name = "skill_request_resource")
public class SkillResource implements Serializable {

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
	private SkillRequest skillRequestId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "resource_id", referencedColumnName="employee_id")
	private Resource resourceId;
	
	/*@Column(name = "resource_id")
	private Integer resourceId;*/

	@Column(name = "external_resource_name")
	private String externalResourceName;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "status_id")
	private SkillResourceStatusType statusId;
   
	//, insertable=false, updatable=true , referencedColumnName="id"
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "allocation_id" )
	private AllocationType allocationId;
	
	@Column(name = "allocation_date")
	@DateTimeFormat(style = "M-")
	private Date allocationDate;
	
	@Column(name = "interview_date")
	@DateTimeFormat(style = "M-")
	private Date interviewDate;
	@Lob
    @Column(name ="upload_resume" , length = 100000000)   //upload Resume
    private byte[] uploadResume;
	
	//private String uploadResumeFileName;
	@Column(name= "resume_file_name")
	private String resumeFileName;
	
	public Date getAllocationDate() {
		return allocationDate;
	}

	public void setAllocationDate(Date allocationDate) {
		this.allocationDate = allocationDate;
	}

	public AllocationType getAllocationId() {
		return allocationId;
	}

	public void setAllocationId(AllocationType allocationId) {
		this.allocationId = allocationId;
	}

	public SkillResourceStatusType getStatusId() {
		return statusId;
	}

	public void setStatusId(SkillResourceStatusType statusId) {
		this.statusId = statusId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public SkillRequest getSkillRequestId() {
		return skillRequestId;
	}

	public void setSkillRequestId(SkillRequest skillRequestId) {
		this.skillRequestId = skillRequestId;
	}

	public Resource getResourceId() {
		return resourceId;
	}

	public void setResourceId(Resource resourceId) {
		this.resourceId = resourceId;
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
	
}
