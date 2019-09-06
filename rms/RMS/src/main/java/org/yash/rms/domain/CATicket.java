package org.yash.rms.domain;

import java.io.Serializable;
import java.math.BigInteger;
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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.yash.rms.util.Constants;

import flexjson.JSONSerializer;
import flexjson.transformer.DateTransformer;

@Entity
@Table(name = "ca_ticket")
public class CATicket implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1498114689525438135L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "ticket_number")
	private BigInteger caTicketNo;

	@Column(name = "status")
	private String status;

	@Column(name = "updated_date")
	private Date updatedDate;

	@Column(name = "resolve_date")
	private Date resolveDate;

	@Column(name = "priority")
	private String priority;

	@Column(name = "area_category")
	private String areaOrCategory;

	@Column(name = "assignee_name")
	private String assigneeName;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "reviewer_id", referencedColumnName = "employee_id")
	private Resource reviewer;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "emp_id", referencedColumnName = "employee_id")
	private Resource assigneeId;

	@Column(name = "primary_group")
	private String primaryGroup;

	@Column(name = "reported_by_name")
	private String reportedByName;

	@Column(name = "description")
	private String description;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "module", referencedColumnName = "id")
	private Project moduleId;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "landscape_id", referencedColumnName = "id")
	private Landscape landscapeId;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "unit_id", referencedColumnName = "id")
	private Unit unitId;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "region", referencedColumnName = "id")
	private Region region;

	@Column(name = "sla_missed")
	private String slaMissed;

	@Column(name = "aging")
	private String aging;

	@Column(name = "days_open")
	private String daysOpen;

	@Column(name = "reopen_frequency")
	private int reopenFrequency;

	@Column(name = "solution_ready_for_review")
	private String solutionReadyForReview;

	@Column(name = "creation_date")
	private Date creationDate;

	@Column(name = "acknowledged_date")
	private Date acknowledgedDate;

	@Column(name = "required_completed_date")
	private Date requiredCompletedDate;

	@Column(name = "analysis_completed_date")
	private Date analysisCompletedDate;

	@Column(name = "solution_review_date")
	private Date solutionreViewDate;

	@Column(name = "solution_developed_date")
	private Date solutiondevelopedDate;

	@Column(name = "solution_accepted_date")
	private Date solutionAcceptedDate;

	@Column(name = "close_pending_date")
	private Date closePendingCustomerApprovalDate;

	// @Column(name = "reason_for_hopping")
	// private String reasonForHopping;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "reason_for_hopping", referencedColumnName = "id")
	private ReasonHopping reasonForHopping;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "reason_for_reopen", referencedColumnName = "id")
	private ReasonReopen reasonForReopen;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "reason_for_slamissed", referencedColumnName = "id")
	private ReasonForSLAMissed reasonForSLAMissed;

	@Column(name = "justified_hopping")
	private String justifiedHopping;

	@Transient
	private int daysOpenForPhase;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "process", referencedColumnName = "id")
	private CATicketProcess process;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "sub_process", referencedColumnName = "id")
	private CATicketSubProcess subProcess;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "rootcause", referencedColumnName = "id")
	private RootCause rootCause;

	@Column(name = "zreq_no")
	private int ZREQNo;

	@Column(name = "parent_ticket_no")
	private int parentTicketNo;

	@Column(name = "comment")
	private String comment;

	@Column(name = "req_complete_flag")
	private String reqCompleteFlag;

	@Column(name = "analysis_complete_flag")
	private String analysisCompleteFlag;

	@Column(name = "solution_review_flag")
	private String solutionReviewFlag;

	@Column(name = "solution_developed_flag")
	private String solutionDevelopedFlag;

	@Column(name = "solution_accepted_flag")
	private String solutionAcceptedFlag;

	@Column(name = "customer_approval_flag")
	private String customerApprovalFlag;

	@Column(name = "problem_status_flag")
	private String problemManagementFlag;

	@Column(name = "t3_status_flag")
	private String t3StatusFlag;

	@Column(name = "defect_status_flag")
	private String defectStatusFlag;

	@Column(name = "crop_status_flag")
	private String cropStatusFlag;

	@Column(name = "rework_status_flag")
	private String reworkStatusFlag;

	@Column(name = "justified_problem_management")
	private String justifiedProblemManagement;

	@Column(name = "group_name")
	private String groupName;

	@Column(name = "affected_end_user_id")
	private String affectedEndUserId;

	@Column(name = "affected_end_username")
	private String affectedEndUserName;

	@Column(name = "updated_by")
	private String updatedBy;

	@Column(name = "requirement_analysis_hours")
	private Float requirementAnalysisHours;

	@Column(name = "unit_testing_hours")
	private Float unitTestingHours;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "caTicket")
	private List<T3Contribution> t3Contribution;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "caTicket")
	private List<SolutionReview> solutionReview;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "caTicket")
	private List<DefectLog> defectLog;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "caTicket")
	private List<Rework> rework;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "caTicket")
	private List<Crop> crop;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public BigInteger getCaTicketNo() {
		return caTicketNo;
	}

	public void setCaTicketNo(BigInteger caTicketNo) {
		this.caTicketNo = caTicketNo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public Date getResolveDate() {
		return resolveDate;
	}

	public void setResolveDate(Date resolveDate) {
		this.resolveDate = resolveDate;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getAreaOrCategory() {
		return areaOrCategory;
	}

	public void setAreaOrCategory(String areaOrCategory) {
		this.areaOrCategory = areaOrCategory;
	}

	public String getAssigneeName() {
		return assigneeName;
	}

	public void setAssigneeName(String assigneeName) {
		this.assigneeName = assigneeName;
	}

	public Resource getReviewer() {
		return reviewer;
	}

	public void setReviewer(Resource reviewer) {
		this.reviewer = reviewer;
	}

	public Resource getAssigneeId() {
		return assigneeId;
	}

	public void setAssigneeId(Resource assigneeId) {
		this.assigneeId = assigneeId;
	}

	public String getPrimaryGroup() {
		return primaryGroup;
	}

	public void setPrimaryGroup(String primaryGroup) {
		this.primaryGroup = primaryGroup;
	}

	public String getReportedByName() {
		return reportedByName;
	}

	public void setReportedByName(String reportedByName) {
		this.reportedByName = reportedByName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Project getModuleId() {
		return moduleId;
	}

	public void setModuleId(Project moduleId) {
		this.moduleId = moduleId;
	}

	public Landscape getLandscapeId() {
		return landscapeId;
	}

	public void setLandscapeId(Landscape landscapeId) {
		this.landscapeId = landscapeId;
	}

	public Unit getUnitId() {
		return unitId;
	}

	public void setUnitId(Unit unitId) {
		this.unitId = unitId;
	}

	public Region getRegion() {
		return region;
	}

	public void setRegion(Region region) {
		this.region = region;
	}

	public String getSlaMissed() {
		return slaMissed;
	}

	public void setSlaMissed(String slaMissed) {
		this.slaMissed = slaMissed;
	}

	public String getAging() {
		return aging;
	}

	public void setAging(String aging) {
		this.aging = aging;
	}

	public String getDaysOpen() {
		return daysOpen;
	}

	public void setDaysOpen(String daysOpen) {
		this.daysOpen = daysOpen;
	}

	public int getReopenFrequency() {
		return reopenFrequency;
	}

	public void setReopenFrequency(int reopenFrequency) {
		this.reopenFrequency = reopenFrequency;
	}

	public String getSolutionReadyForReview() {
		return solutionReadyForReview;
	}

	public void setSolutionReadyForReview(String solutionReadyForReview) {
		this.solutionReadyForReview = solutionReadyForReview;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getAcknowledgedDate() {
		return acknowledgedDate;
	}

	public void setAcknowledgedDate(Date acknowledgedDate) {
		this.acknowledgedDate = acknowledgedDate;
	}

	public Date getRequiredCompletedDate() {
		return requiredCompletedDate;
	}

	public void setRequiredCompletedDate(Date requiredCompletedDate) {
		this.requiredCompletedDate = requiredCompletedDate;
	}

	public Date getAnalysisCompletedDate() {
		return analysisCompletedDate;
	}

	public void setAnalysisCompletedDate(Date analysisCompletedDate) {
		this.analysisCompletedDate = analysisCompletedDate;
	}

	public Date getSolutionreViewDate() {
		return solutionreViewDate;
	}

	public void setSolutionreViewDate(Date solutionreViewDate) {
		this.solutionreViewDate = solutionreViewDate;
	}

	public Date getSolutiondevelopedDate() {
		return solutiondevelopedDate;
	}

	public void setSolutiondevelopedDate(Date solutiondevelopedDate) {
		this.solutiondevelopedDate = solutiondevelopedDate;
	}

	public Date getSolutionAcceptedDate() {
		return solutionAcceptedDate;
	}

	public void setSolutionAcceptedDate(Date solutionAcceptedDate) {
		this.solutionAcceptedDate = solutionAcceptedDate;
	}

	public Date getClosePendingCustomerApprovalDate() {
		return closePendingCustomerApprovalDate;
	}

	public void setClosePendingCustomerApprovalDate(
			Date closePendingCustomerApprovalDate) {
		this.closePendingCustomerApprovalDate = closePendingCustomerApprovalDate;
	}

	public ReasonHopping getReasonForHopping() {
		return reasonForHopping;
	}

	public void setReasonForHopping(ReasonHopping reasonForHopping) {
		this.reasonForHopping = reasonForHopping;
	}

	public String getJustifiedHopping() {
		return justifiedHopping;
	}

	public void setJustifiedHopping(String justifiedHopping) {
		this.justifiedHopping = justifiedHopping;
	}

	public int getDaysOpenForPhase() {
		return daysOpenForPhase;
	}

	public void setDaysOpenForPhase(int daysOpenForPhase) {
		this.daysOpenForPhase = daysOpenForPhase;
	}

	public CATicketProcess getProcess() {
		return process;
	}

	public void setProcess(CATicketProcess process) {
		this.process = process;
	}

	public CATicketSubProcess getSubProcess() {
		return subProcess;
	}

	public void setSubProcess(CATicketSubProcess subProcess) {
		this.subProcess = subProcess;
	}

	public RootCause getRootCause() {
		return rootCause;
	}

	public void setRootCause(RootCause rootCause) {
		this.rootCause = rootCause;
	}

	public int getZREQNo() {
		return ZREQNo;
	}

	public void setZREQNo(int zREQNo) {
		ZREQNo = zREQNo;
	}

	public int getParentTicketNo() {
		return parentTicketNo;
	}

	public void setParentTicketNo(int parentTicketNo) {
		this.parentTicketNo = parentTicketNo;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getReqCompleteFlag() {
		return reqCompleteFlag;
	}

	public void setReqCompleteFlag(String reqCompleteFlag) {
		this.reqCompleteFlag = reqCompleteFlag;
	}

	public String getAnalysisCompleteFlag() {
		return analysisCompleteFlag;
	}

	public void setAnalysisCompleteFlag(String analysisCompleteFlag) {
		this.analysisCompleteFlag = analysisCompleteFlag;
	}

	public String getSolutionReviewFlag() {
		return solutionReviewFlag;
	}

	public void setSolutionReviewFlag(String solutionReviewFlag) {
		this.solutionReviewFlag = solutionReviewFlag;
	}

	public String getSolutionDevelopedFlag() {
		return solutionDevelopedFlag;
	}

	public void setSolutionDevelopedFlag(String solutionDevelopedFlag) {
		this.solutionDevelopedFlag = solutionDevelopedFlag;
	}

	public String getSolutionAcceptedFlag() {
		return solutionAcceptedFlag;
	}

	public void setSolutionAcceptedFlag(String solutionAcceptedFlag) {
		this.solutionAcceptedFlag = solutionAcceptedFlag;
	}

	public String getCustomerApprovalFlag() {
		return customerApprovalFlag;
	}

	public void setCustomerApprovalFlag(String customerApprovalFlag) {
		this.customerApprovalFlag = customerApprovalFlag;
	}

	public String getProblemManagementFlag() {
		return problemManagementFlag;
	}

	public void setProblemManagementFlag(String problemManagementFlag) {
		this.problemManagementFlag = problemManagementFlag;
	}

	public String getT3StatusFlag() {
		return t3StatusFlag;
	}

	public void setT3StatusFlag(String t3StatusFlag) {
		this.t3StatusFlag = t3StatusFlag;
	}

	public String getDefectStatusFlag() {
		return defectStatusFlag;
	}

	public void setDefectStatusFlag(String defectStatusFlag) {
		this.defectStatusFlag = defectStatusFlag;
	}

	public String getCropStatusFlag() {
		return cropStatusFlag;
	}

	public void setCropStatusFlag(String cropStatusFlag) {
		this.cropStatusFlag = cropStatusFlag;
	}

	public String getReworkStatusFlag() {
		return reworkStatusFlag;
	}

	public void setReworkStatusFlag(String reworkStatusFlag) {
		this.reworkStatusFlag = reworkStatusFlag;
	}

	public String getJustifiedProblemManagement() {
		return justifiedProblemManagement;
	}

	public void setJustifiedProblemManagement(String justifiedProblemManagement) {
		this.justifiedProblemManagement = justifiedProblemManagement;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public List<T3Contribution> getT3Contribution() {
		return t3Contribution;
	}

	public void setT3Contribution(List<T3Contribution> t3Contribution) {
		this.t3Contribution = t3Contribution;
	}

	public List<SolutionReview> getSolutionReview() {
		return solutionReview;
	}

	public void setSolutionReview(List<SolutionReview> solutionReview) {
		this.solutionReview = solutionReview;
	}

	public List<DefectLog> getDefectLog() {
		return defectLog;
	}

	public void setDefectLog(List<DefectLog> defectLog) {
		this.defectLog = defectLog;
	}

	public List<Rework> getRework() {
		return rework;
	}

	public void setRework(List<Rework> rework) {
		this.rework = rework;
	}

	public List<Crop> getCrop() {
		return crop;
	}

	public void setCrop(List<Crop> crop) {
		this.crop = crop;
	}

	public ReasonReopen getReasonForReopen() {
		return reasonForReopen;
	}

	public void setReasonForReopen(ReasonReopen reasonForReopen) {
		this.reasonForReopen = reasonForReopen;
	}

	public ReasonForSLAMissed getReasonForSLAMissed() {
		return reasonForSLAMissed;
	}

	public void setReasonForSLAMissed(ReasonForSLAMissed reasonForSLAMissed) {
		this.reasonForSLAMissed = reasonForSLAMissed;
	}

	public String getAffectedEndUserId() {
		return affectedEndUserId;
	}

	public void setAffectedEndUserId(String affectedEndUserId) {
		this.affectedEndUserId = affectedEndUserId;
	}

	public String getAffectedEndUserName() {
		return affectedEndUserName;
	}

	public void setAffectedEndUserName(String affectedEndUserName) {
		this.affectedEndUserName = affectedEndUserName;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Float getRequirementAnalysisHours() {
		return requirementAnalysisHours;
	}

	public void setRequirementAnalysisHours(Float requirementAnalysisHours) {
		this.requirementAnalysisHours = requirementAnalysisHours;
	}

	public Float getUnitTestingHours() {
		return unitTestingHours;
	}

	public void setUnitTestingHours(Float unitTestingHours) {
		this.unitTestingHours = unitTestingHours;
	}

	public static String toJsonArray(
			Collection<org.yash.rms.domain.CATicket> collection) {
		return new JSONSerializer()
				.include("id", "caTicketNo", "moduleId.moduleName",
						"landscapeId.landscapeName", "priority",
						"customerApprovalFlag", "landscape",
						"region.regionName", "aging", "slaMissed", "daysOpen",
						"problemManagementFlag")
				.exclude("*")
				.transform(new DateTransformer(Constants.DATE_PATTERN),
						Date.class).serialize(collection);
	}

	public static String toProjectJsonArray(
			Collection<org.yash.rms.domain.Project> collection) {
		return new JSONSerializer()
				.include("id", "moduleName")
				.exclude("*")
				.transform(new DateTransformer(Constants.DATE_PATTERN),
						Date.class).serialize(collection);
	}

	public static String resourseJsonArray(
			Collection<org.yash.rms.domain.Resource> resList) {
		return new JSONSerializer()
				.include("employeeId", "employeeName")
				.exclude("*")
				.transform(new DateTransformer(Constants.DATE_PATTERN),
						Date.class).serialize(resList);
	}

}
