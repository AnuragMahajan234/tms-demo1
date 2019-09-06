package org.yash.rms.report.dto;

import java.util.List;
import java.util.Map;

public class ResourceMovementReportGraphs {

	private Integer totalNBfromBFAllocationType;
	private Integer totalNBfromBPartAllocationType;
	private Integer totalBFTfromNBAllocationType;

	private Integer totalBFTfromBpartAllocationType;
	private Integer totalBPartFromNBAllocationType;
	private Integer totalBPartFromBFAllocationType;

	private Integer totalPreviousNBAllocationType;
	private Integer totalpreviousFBillAllocationType;
	private Integer totalpreviousPartBillAllocationType;

	private Integer totalInvestmentfromNBAllocationType;
	private Integer totalInvestmentfromBFAllocationType;
	private Integer totalInvestmentfromBPartAllocationType;

	private Integer totalBFTfromInvestmentAllocationType;
	private Integer totalNBfromInvestmentAllocationType;
	private Integer totalPartBillFromInvestmentAllocationType;
	private Integer totalInvestmentfromPreviousAllocationType;

	private Integer totalNewHiredResourceAllocationType;
	private Integer totalBFTfromNewHiredAllocationType;
	private Integer totalNBfromNewHiredAllocationType;
	private Integer totalPartBillFromNewHiredAllocationType;
	private Integer totalInvestmentfromNewHiredAllocationType;

	private Map<String, Integer>  totalProject;
	private Map<String, Integer> totalInternalfromProject;
	private Map<String, Integer> totalExternalfromProject;

	List<ResourceMovementReport> resourceMovementAnalysedReportList;

	public Integer getTotalNBfromBFAllocationType() {
		return totalNBfromBFAllocationType;
	}

	public void setTotalNBfromBFAllocationType(Integer totalNBfromBFAllocationType) {
		this.totalNBfromBFAllocationType = totalNBfromBFAllocationType;
	}

	public Integer getTotalNBfromBPartAllocationType() {
		return totalNBfromBPartAllocationType;
	}

	public void setTotalNBfromBPartAllocationType(Integer totalNBfromBPartAllocationType) {
		this.totalNBfromBPartAllocationType = totalNBfromBPartAllocationType;
	}

	public Integer getTotalBFTfromNBAllocationType() {
		return totalBFTfromNBAllocationType;
	}

	public void setTotalBFTfromNBAllocationType(Integer totalBFTfromNBAllocationType) {
		this.totalBFTfromNBAllocationType = totalBFTfromNBAllocationType;
	}

	public Integer getTotalBFTfromBpartAllocationType() {
		return totalBFTfromBpartAllocationType;
	}

	public void setTotalBFTfromBpartAllocationType(Integer totalBFTfromBpartAllocationType) {
		this.totalBFTfromBpartAllocationType = totalBFTfromBpartAllocationType;
	}

	public Integer getTotalBPartFromNBAllocationType() {
		return totalBPartFromNBAllocationType;
	}

	public void setTotalBPartFromNBAllocationType(Integer totalBPartFromNBAllocationType) {
		this.totalBPartFromNBAllocationType = totalBPartFromNBAllocationType;
	}

	public Integer getTotalBPartFromBFAllocationType() {
		return totalBPartFromBFAllocationType;
	}

	public void setTotalBPartFromBFAllocationType(Integer totalBPartFromBFAllocationType) {
		this.totalBPartFromBFAllocationType = totalBPartFromBFAllocationType;
	}

	public Integer getTotalPreviousNBAllocationType() {
		return totalPreviousNBAllocationType;
	}

	public void setTotalPreviousNBAllocationType(Integer totalPreviousNBAllocationType) {
		this.totalPreviousNBAllocationType = totalPreviousNBAllocationType;
	}

	public Integer getTotalpreviousFBillAllocationType() {
		return totalpreviousFBillAllocationType;
	}

	public void setTotalpreviousFBillAllocationType(Integer totalpreviousFBillAllocationType) {
		this.totalpreviousFBillAllocationType = totalpreviousFBillAllocationType;
	}

	public Integer getTotalpreviousPartBillAllocationType() {
		return totalpreviousPartBillAllocationType;
	}

	public void setTotalpreviousPartBillAllocationType(Integer totalpreviousPartBillAllocationType) {
		this.totalpreviousPartBillAllocationType = totalpreviousPartBillAllocationType;
	}

	public List<ResourceMovementReport> getResourceMovementAnalysedReportList() {
		return resourceMovementAnalysedReportList;
	}

	public void setResourceMovementAnalysedReportList(List<ResourceMovementReport> resourceMovementAnalysisList) {
		this.resourceMovementAnalysedReportList = resourceMovementAnalysisList;
	}

	public Integer getTotalInvestmentfromNBAllocationType() {
		return totalInvestmentfromNBAllocationType;
	}

	public void setTotalInvestmentfromNBAllocationType(Integer totalInvestmentfromNBAllocationType) {
		this.totalInvestmentfromNBAllocationType = totalInvestmentfromNBAllocationType;
	}

	public Integer getTotalInvestmentfromBFAllocationType() {
		return totalInvestmentfromBFAllocationType;
	}

	public void setTotalInvestmentfromBFAllocationType(Integer totalInvestmentfromBFAllocationType) {
		this.totalInvestmentfromBFAllocationType = totalInvestmentfromBFAllocationType;
	}

	public Integer getTotalInvestmentfromBPartAllocationType() {
		return totalInvestmentfromBPartAllocationType;
	}

	public void setTotalInvestmentfromBPartAllocationType(Integer totalInvestmentfromBPartAllocationType) {
		this.totalInvestmentfromBPartAllocationType = totalInvestmentfromBPartAllocationType;
	}

	public Integer getTotalBFTfromInvestmentAllocationType() {
		return totalBFTfromInvestmentAllocationType;
	}

	public void setTotalBFTfromInvestmentAllocationType(Integer totalBFTfromInvestmentAllocationType) {
		this.totalBFTfromInvestmentAllocationType = totalBFTfromInvestmentAllocationType;
	}

	public Integer getTotalNBfromInvestmentAllocationType() {
		return totalNBfromInvestmentAllocationType;
	}

	public void setTotalNBfromInvestmentAllocationType(Integer totalNBfromInvestmentAllocationType) {
		this.totalNBfromInvestmentAllocationType = totalNBfromInvestmentAllocationType;
	}

	public Integer getTotalPartBillFromInvestmentAllocationType() {
		return totalPartBillFromInvestmentAllocationType;
	}

	public void setTotalPartBillFromInvestmentAllocationType(Integer totalPartBillFromInvestmentAllocationType) {
		this.totalPartBillFromInvestmentAllocationType = totalPartBillFromInvestmentAllocationType;
	}

	public Integer getTotalInvestmentfromPreviousAllocationType() {
		return totalInvestmentfromPreviousAllocationType;
	}

	public void setTotalInvestmentfromPreviousAllocationType(Integer totalInvestmentfromPreviousAllocationType) {
		this.totalInvestmentfromPreviousAllocationType = totalInvestmentfromPreviousAllocationType;
	}

	public Integer getTotalNewHiredResourceAllocationType() {
		return totalNewHiredResourceAllocationType;
	}

	public void setTotalNewHiredResourceAllocationType(Integer totalNewHiredResourceAllocationType) {
		this.totalNewHiredResourceAllocationType = totalNewHiredResourceAllocationType;
	}

	public Integer getTotalBFTfromNewHiredAllocationType() {
		return totalBFTfromNewHiredAllocationType;
	}

	public void setTotalBFTfromNewHiredAllocationType(Integer totalBFTfromNewHiredAllocationType) {
		this.totalBFTfromNewHiredAllocationType = totalBFTfromNewHiredAllocationType;
	}

	public Integer getTotalNBfromNewHiredAllocationType() {
		return totalNBfromNewHiredAllocationType;
	}

	public void setTotalNBfromNewHiredAllocationType(Integer totalNBfromNewHiredAllocationType) {
		this.totalNBfromNewHiredAllocationType = totalNBfromNewHiredAllocationType;
	}

	public Integer getTotalPartBillFromNewHiredAllocationType() {
		return totalPartBillFromNewHiredAllocationType;
	}

	public void setTotalPartBillFromNewHiredAllocationType(Integer totalPartBillFromNewHiredAllocationType) {
		this.totalPartBillFromNewHiredAllocationType = totalPartBillFromNewHiredAllocationType;
	}

	public Integer getTotalInvestmentfromNewHiredAllocationType() {
		return totalInvestmentfromNewHiredAllocationType;
	}

	public void setTotalInvestmentfromNewHiredAllocationType(Integer totalInvestmentfromNewHiredAllocationType) {
		this.totalInvestmentfromNewHiredAllocationType = totalInvestmentfromNewHiredAllocationType;
	}

	public Map<String, Integer> getTotalProject() {
		return totalProject;
	}

	public void setTotalProject(Map<String, Integer> totalProject) {
		this.totalProject = totalProject;
	}

	public Map<String, Integer> getTotalInternalfromProject() {
		return totalInternalfromProject;
	}

	public void setTotalInternalfromProject(Map<String, Integer> totalInternalfromProject) {
		this.totalInternalfromProject = totalInternalfromProject;
	}

	public Map<String, Integer> getTotalExternalfromProject() {
		return totalExternalfromProject;
	}

	public void setTotalExternalfromProject(Map<String, Integer> totalExternalfromProject) {
		this.totalExternalfromProject = totalExternalfromProject;
	}

}
