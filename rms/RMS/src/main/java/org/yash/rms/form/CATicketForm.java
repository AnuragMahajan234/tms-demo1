package org.yash.rms.form;

import java.math.BigInteger;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;
import org.yash.rms.domain.CATicketProcess;
import org.yash.rms.domain.CATicketSubProcess;
import org.yash.rms.domain.Landscape;
import org.yash.rms.domain.Project;
import org.yash.rms.domain.ReasonForSLAMissed;
import org.yash.rms.domain.ReasonHopping;
import org.yash.rms.domain.ReasonReopen;
import org.yash.rms.domain.Region;
import org.yash.rms.domain.Resource;
import org.yash.rms.domain.RootCause;
import org.yash.rms.domain.Unit;

public class CATicketForm {

	private String id;

	private BigInteger caTicketNo;

	private String description;

	private String priority;

	private Project moduleId;

	private Landscape landscapeId;

	private Unit unitId;

	private Resource assigneeId;

	private Resource reviewer;

	private Region region;

	private int reopenFrequency;

	private String solutionReadyForReview;

	private String problemManagementFlag;

	private String justifiedProblemManagement;

	@DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss aa")
	private Date updatedDate;

	@DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss aa")
	private Date creationDate;

	@DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss aa")
	private Date acknowledgedDate;

	@DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss aa")
	private Date requiredCompletedDate;

	@DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss aa")
	private Date analysisCompletedDate;

	@DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss aa")
	private Date solutionreViewDate;

	@DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss aa")
	private Date solutiondevelopedDate;

	@DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss aa")
	private Date solutionAcceptedDate;

	@DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss aa")
	private Date closePendingCustomerApprovalDate;

	private CATicketProcess process;

	private CATicketSubProcess subProcess;

	private RootCause rootCause;

	private Integer ZREQNo;

	private Integer parentTicketNo;

	private String comment;

	private String reqCompleteFlag;

	private String analysisCompleteFlag;

	private String solutionReviewFlag;

	private String solutionDevelopedFlag;

	private String solutionAcceptedFlag;

	private String customerApprovalFlag;

	private String groupName;

	private String t3StatusFlag;

	private String defectStatusFlag;

	private String cropStatusFlag;

	private String reworkStatusFlag;

	private ReasonHopping reasonForHopping;

	private ReasonReopen reasonForReopen;

	private ReasonForSLAMissed reasonForSLAMissed;

	private String justifiedHopping;

	private String aging;

	private Integer discrepencyId;

	private String updatedBy;

	private Float requirementAnalysisHours;

	private Float unitTestingHours;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public BigInteger getCaTicketNo() {
		return caTicketNo;
	}

	public void setCaTicketNo(BigInteger caTicketNo) {
		this.caTicketNo = caTicketNo;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
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

	public Resource getAssigneeId() {
		return assigneeId;
	}

	public void setAssigneeId(Resource assigneeId) {
		this.assigneeId = assigneeId;
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

	public int getReopenFrequency() {
		return reopenFrequency;
	}

	public void setReopenFrequency(int reopenFrequency) {
		this.reopenFrequency = reopenFrequency;
	}

	public void setAnalysisCompleteFlag(String analysisCompleteFlag) {
		this.analysisCompleteFlag = analysisCompleteFlag;
	}

	public String getSolutionReadyForReview() {
		return solutionReadyForReview;
	}

	public void setSolutionReadyForReview(String solutionReadyForReview) {
		this.solutionReadyForReview = solutionReadyForReview;
	}

	public String getProblemManagementFlag() {
		return problemManagementFlag;
	}

	public void setProblemManagementFlag(String problemManagementFlag) {
		this.problemManagementFlag = problemManagementFlag;
	}

	public String getJustifiedProblemManagement() {
		return justifiedProblemManagement;
	}

	public void setJustifiedProblemManagement(String justifiedProblemManagement) {
		this.justifiedProblemManagement = justifiedProblemManagement;
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

	public Integer getZREQNo() {
		return ZREQNo;
	}

	public void setZREQNo(Integer zREQNo) {
		ZREQNo = zREQNo;
	}

	public Integer getParentTicketNo() {
		return parentTicketNo;
	}

	public void setParentTicketNo(Integer parentTicketNo) {
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

	public void setAnalysisCompletFalg(String analysisCompleteFlag) {
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

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
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

	public ReasonHopping getReasonForHopping() {
		return reasonForHopping;
	}

	public void setReasonForHopping(ReasonHopping reasonForHopping) {
		this.reasonForHopping = reasonForHopping;
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

	public String getJustifiedHopping() {
		return justifiedHopping;
	}

	public void setJustifiedHopping(String justifiedHopping) {
		this.justifiedHopping = justifiedHopping;
	}

	public String getAging() {
		return aging;
	}

	public void setAging(String aging) {
		this.aging = aging;
	}

	public Integer getDiscrepencyId() {
		return discrepencyId;
	}

	public void setDiscrepencyId(Integer discrepencyId) {
		this.discrepencyId = discrepencyId;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
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

}
