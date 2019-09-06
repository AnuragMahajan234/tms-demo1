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
import org.yash.rms.util.Constants;

import flexjson.JSONSerializer;
import flexjson.transformer.DateTransformer;

/**
 * @author arpan.badjatiya
 * 
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "request_requisition")
public class RequestRequisition implements Serializable {

	//public final static String SEARCH_ACTIVITY_BASED_ON_ID = "SEARCH_ACTIVITY_BASED_ON_ID";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "emp_id", referencedColumnName="employee_id")
	@NotNull
	private Resource resource;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "customer_id", referencedColumnName="id")
	@NotNull
	private Customer customer;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "group_id", referencedColumnName="group_id")
	private CustomerGroup group;

	@Column(name = "project_bu")
	private String projectBU;
	
	@Column(name = "date_of_Indent")
	private Date date;
	
	@Column(name = "indentor_comments")
	private String comments;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "project_id", referencedColumnName="id")
	private Project project;
	
	@Column(name = "sent_mail")
	private String sentMailTo;
	
	@Column(name = "notify_to")
	private String notifyMailTo;
	
	@Column(name = "pdl_email")
	private String pdlEmailIds;

	@Column(name = "creation_timestamp")
	private Date creationTimestamp;
	
	@Column(name = "created_by")
	private String createdBy;
	
	@Column(name = "lastupdated_by")
	private String lastupdatedBy;
	
	@Column(name = "lastupdated_timestamp")
	private Date lastupdatedTimestamp;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "requestor", referencedColumnName="employee_id")
	@NotNull
	private Resource requestor;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "delivery_poc", referencedColumnName="employee_id")
	@NotNull
	private Resource deliveryPOC;
	
	@Column(name = "client_type")
	private String clientType;
	
	@Column(name = "project_type")	
	private String projectType;
	
	@Lob
    @Column(name ="BUH_approval_file") 
	private byte[] BUHApprovalFile;
	
	@Column(name = "BUH_approval_file_name")
	private String BUHApprovalFileName;
	
	@Lob
    @Column(name ="BGH_approval_file") 
	private byte[] BGHApprovalFile;
	
	@Column(name = "BGH_approval_file_name")
	private String BGHApprovalFileName;
	
	@Column(name = "project_duration")
	private Integer projectDuration;
	
	@Column(name = "resource_required_date")
	@DateTimeFormat(style = "M-")
	private Date resourceRequiredDate;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "shift_type_id", referencedColumnName="id")
	@NotNull
	private ShiftTypes shiftType;
	
	@Column(name = "project_shift_other_details")
	private String projectShiftOtherDetails;
	
	@Column(name = "isclient_interview_required")
	private String isClientInterviewRequired;
	
	@Column(name = "isBGV_required")
	private String isBGVrequired;
	
	/**
	 * column for staffing/ODC
	 */
	@Column(name = "required_for")
	private String requiredFor;
	
	@Column(name = "exp_other_details")
	private String expOtherDetails;

	@Column(name = "hiring_bgbu")
	private String hiringBGBU;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "resourceid_for_replacement", referencedColumnName="employee_id")
	private Resource replacementResource;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "reasonid", referencedColumnName="id")
	private ReasonForReplacement reason;
	
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "priorityid", referencedColumnName="id")
	private Priority priority;
	
	/*@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "buh_id", referencedColumnName="id")
	private Resource buHead;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "bgh_id", referencedColumnName="id")
	private Resource bgHead;*/
	
	@Column(name = "am_job_code")
	private String amJobCode;

	@Column(name = "success_factor_id")
	private String successFactorId;
	
	@Column(name="rmg_poc")
	private String rmgPoc;
	
	@Column(name="tec_team_poc")
	private String tecTeamPoc;
	
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

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
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

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public CustomerGroup getGroup() {
		return group;
	}

	public void setGroup(CustomerGroup group) {
		this.group = group;
	}

	public String getPdlEmailIds() {
		return pdlEmailIds;
	}

	public void setPdlEmailIds(String pdlEmailIds) {
		this.pdlEmailIds = pdlEmailIds;
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
	public Date getCreationTimestamp() {
		return creationTimestamp;
	}

	public void setCreationTimestamp(Date creationTimestamp) {
		this.creationTimestamp = creationTimestamp;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getLastupdatedBy() {
		return lastupdatedBy;
	}

	public void setLastupdatedBy(String lastupdatedBy) {
		this.lastupdatedBy = lastupdatedBy;
	}

	public Date getLastupdatedTimestamp() {
		return lastupdatedTimestamp;
	}

	public void setLastupdatedTimestamp(Date lastupdatedTimestamp) {
		this.lastupdatedTimestamp = lastupdatedTimestamp;
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

	public byte[] getBUHApprovalFile() {
		return BUHApprovalFile;
	}

	public String getExpOtherDetails() {
		return expOtherDetails;
	}

	public void setExpOtherDetails(String expOtherDetails) {
		this.expOtherDetails = expOtherDetails;
	}

	public void setBUHApprovalFile(byte[] bUHApprovalFile) {
		BUHApprovalFile = bUHApprovalFile;
	}

	public String getBUHApprovalFileName() {
		return BUHApprovalFileName;
	}

	public void setBUHApprovalFileName(String bUHApprovalFileName) {
		BUHApprovalFileName = bUHApprovalFileName;
	}

	public byte[] getBGUApprovalFile() {
		return BGHApprovalFile;
	}

	public void setBGUApprovalFile(byte[] bGUApprovalFile) {
		BGHApprovalFile = bGUApprovalFile;
	}

	public String getBGUApprovalFileName() {
		return BGHApprovalFileName;
	}

	public void setBGUApprovalFileName(String bGUApprovalFileName) {
		BGHApprovalFileName = bGUApprovalFileName;
	}

	public Integer getProjectDuration() {
		return projectDuration;
	}

	public void setProjectDuration(Integer projectDuration) {
		this.projectDuration = projectDuration;
	}

	public Date getResourceRequiredDate() {
		return resourceRequiredDate;
	}

	public void setResourceRequiredDate(Date resourceRequiredDate) {
		this.resourceRequiredDate = resourceRequiredDate;
	}

	public ShiftTypes getShiftTypeId() {
		return shiftType;
	}

	public void setShiftTypeId(ShiftTypes shiftTypeId) {
		this.shiftType = shiftTypeId;
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

	public ReasonForReplacement getReason() {
		return reason;
	}

	public void setReason(ReasonForReplacement reason) {
		this.reason = reason;
	}
	
	public Priority getPriority() {
		return priority;
	}
	
	public void setPriority(Priority priority) {
		this.priority = priority;
	}

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

	public String getTecTeamPoc() {
		return tecTeamPoc;
	}

	public void setTecTeamPoc(String tecTeamPoc) {
		this.tecTeamPoc = tecTeamPoc;
	}
	
	public static  String toJsonString(RequestRequisition collection) {
		return new JSONSerializer()
				.include("id").exclude("*").transform(new DateTransformer(Constants.DATE_PATTERN_4), Date.class).serialize(collection);
		/*return new JSONSerializer()
				.include("id","requirementId","lost","isDeleted","").exclude("*").transform(new DateTransformer(Constants.DATE_PATTERN_4), Date.class).serialize(collection);*/
	}
}
