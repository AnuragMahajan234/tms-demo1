package org.yash.rms.domain;

import java.io.Serializable;

//@Entity
//@NamedQueries({ @NamedQuery(name = "get_myPerformanceCount", query = "CALL get_myPerformanceCount(:employee_id)") })
//@Table(name = "ca_ticket")
public class CATicketNamedQuery implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6856741726056176802L;

	private Integer empId;

	private String module;

	private Integer moduleId;

	private String assigneeName;

	private Integer acknowledgedCount;

	private Integer reqCompleteCount;

	private Integer analysisCompleteCount;

	private Integer solutionDevelopedCount;

	private Integer sentForReviewCount;

	private Integer forMyReviewCount;

	private Integer solutionAcceptedCount;

	private Integer customerApprovalCount;

	private Integer problemManagementCount;

	private Integer groupCount;

	public Integer getEmpId() {
		return empId;
	}

	public void setEmpId(Integer empId) {
		this.empId = empId;
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public String getAssigneeName() {
		return assigneeName;
	}

	public void setAssigneeName(String assigneeName) {
		this.assigneeName = assigneeName;
	}

	public Integer getAcknowledgedCount() {
		return acknowledgedCount;
	}

	public void setAcknowledgedCount(Integer acknowledgedCount) {
		this.acknowledgedCount = acknowledgedCount;
	}

	public Integer getReqCompleteCount() {
		return reqCompleteCount;
	}

	public void setReqCompleteCount(Integer reqCompleteCount) {
		this.reqCompleteCount = reqCompleteCount;
	}

	public Integer getModuleId() {
		return moduleId;
	}

	public void setModuleId(Integer moduleId) {
		this.moduleId = moduleId;
	}

	public Integer getAnalysisCompleteCount() {
		return analysisCompleteCount;
	}

	public void setAnalysisCompleteCount(Integer analysisCompleteCount) {
		this.analysisCompleteCount = analysisCompleteCount;
	}

	public Integer getSolutionDevelopedCount() {
		return solutionDevelopedCount;
	}

	public void setSolutionDevelopedCount(Integer solutionDevelopedCount) {
		this.solutionDevelopedCount = solutionDevelopedCount;
	}

	public Integer getSentForReviewCount() {
		return sentForReviewCount;
	}

	public void setSentForReviewCount(Integer sentForReviewCount) {
		this.sentForReviewCount = sentForReviewCount;
	}

	public Integer getForMyReviewCount() {
		return forMyReviewCount;
	}

	public void setForMyReviewCount(Integer forMyReviewCount) {
		this.forMyReviewCount = forMyReviewCount;
	}

	public Integer getSolutionAcceptedCount() {
		return solutionAcceptedCount;
	}

	public void setSolutionAcceptedCount(Integer solutionAcceptedCount) {
		this.solutionAcceptedCount = solutionAcceptedCount;
	}

	public Integer getCustomerApprovalCount() {
		return customerApprovalCount;
	}

	public void setCustomerApprovalCount(Integer customerApprovalCount) {
		this.customerApprovalCount = customerApprovalCount;
	}

	public Integer getProblemManagementCount() {
		return problemManagementCount;
	}

	public void setProblemManagementCount(Integer problemManagementCount) {
		this.problemManagementCount = problemManagementCount;
	}

	public Integer getGroupCount() {
		return groupCount;
	}

	public void setGroupCount(Integer groupCount) {
		this.groupCount = groupCount;
	}

}
