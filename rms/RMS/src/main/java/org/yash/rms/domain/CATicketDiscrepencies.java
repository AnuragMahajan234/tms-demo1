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
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "ca_ticket_discrepencies")
public class CATicketDiscrepencies implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2405672045691751793L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "ticket_number")
	private String caTicketNo;

	@Column(name = "description")
	private String description;

	@Column(name = "parent_ticket_no")
	private String parentTicketNo;

	@Column(name = "priority")
	private String priority;

	@Column(name = "recf_id")
	private String recfId;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "emp_id", referencedColumnName = "employee_id")
	private Resource assigneeId;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "reviewer_id", referencedColumnName = "employee_id")
	private Resource reviewer;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "unit_id", referencedColumnName = "id")
	private Unit unitId;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "region", referencedColumnName = "id")
	private Region region;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "landscape_id", referencedColumnName = "id")
	private Landscape landscapeId;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "module", referencedColumnName = "id")
	private Project moduleId;

	@Column(name = "reopen_frequency")
	private String reopenFrequency;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "rootcause", referencedColumnName = "id")
	private RootCause rootCause;

	@Column(name = "creation_date")
	private Date creationDate;

	@Column(name = "solution_developed_date")
	private Date solutiondevelopedDate;

	@Column(name = "close_pending_date")
	private Date closePendingCustomerApprovalDate;

	@Column(name = "updated_date")
	private Date updatedDate;

	@Column(name = "solution_ready_for_review")
	private String solutionReadyForReview;

	@Column(name = "acknowledged_date")
	private Date acknowledgedDate;

	@Column(name = "required_completed_date")
	private Date requiredCompletedDate;

	@Column(name = "analysis_completed_date")
	private Date analysisCompletedDate;

	@Column(name = "solution_review_date")
	private Date solutionreViewDate;

	@Column(name = "solution_accepted_date")
	private Date solutionAcceptedDate;

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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCaTicketNo() {
		return caTicketNo;
	}

	public void setCaTicketNo(String caTicketNo) {
		this.caTicketNo = caTicketNo;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getParentTicketNo() {
		return parentTicketNo;
	}

	public void setParentTicketNo(String parentTicketNo) {
		this.parentTicketNo = parentTicketNo;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public Unit getUnitId() {
		return unitId;
	}

	public void setUnitId(Unit unitId) {
		this.unitId = unitId;
	}

	public Landscape getLandscapeId() {
		return landscapeId;
	}

	public void setLandscapeId(Landscape landscapeId) {
		this.landscapeId = landscapeId;
	}

	public Project getModuleId() {
		return moduleId;
	}

	public void setModuleId(Project moduleId) {
		this.moduleId = moduleId;
	}

	public String getReopenFrequency() {
		return reopenFrequency;
	}

	public void setReopenFrequency(String reopenFrequency) {
		this.reopenFrequency = reopenFrequency;
	}

	public RootCause getRootCause() {
		return rootCause;
	}

	public void setRootCause(RootCause rootCause) {
		this.rootCause = rootCause;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getSolutiondevelopedDate() {
		return solutiondevelopedDate;
	}

	public void setSolutiondevelopedDate(Date solutiondevelopedDate) {
		this.solutiondevelopedDate = solutiondevelopedDate;
	}

	public Date getClosePendingCustomerApprovalDate() {
		return closePendingCustomerApprovalDate;
	}

	public void setClosePendingCustomerApprovalDate(
			Date closePendingCustomerApprovalDate) {
		this.closePendingCustomerApprovalDate = closePendingCustomerApprovalDate;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getRecfId() {
		return recfId;
	}

	public void setRecfId(String recfId) {
		this.recfId = recfId;
	}

	public Resource getAssigneeId() {
		return assigneeId;
	}

	public void setAssigneeId(Resource assigneeId) {
		this.assigneeId = assigneeId;
	}

	public String getSolutionReadyForReview() {
		return solutionReadyForReview;
	}

	public void setSolutionReadyForReview(String solutionReadyForReview) {
		this.solutionReadyForReview = solutionReadyForReview;
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

	public Date getSolutionAcceptedDate() {
		return solutionAcceptedDate;
	}

	public void setSolutionAcceptedDate(Date solutionAcceptedDate) {
		this.solutionAcceptedDate = solutionAcceptedDate;
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

	public Resource getReviewer() {
		return reviewer;
	}

	public void setReviewer(Resource reviewer) {
		this.reviewer = reviewer;
	}

	public Region getRegion() {
		return region;
	}

	public void setRegion(Region region) {
		this.region = region;
	}

}
