package org.yash.rms.report.dto;

public class MuneerReportDto {
	
	int billableCount;
	int billablePartialCount;
	int nonBillableBlocked;
	int nonBillableContigent;
	int nonBillableDelMngmt;
	int nonBillableOperations;
	int nonBillableShadow;
	int nonBillableTrainee;
	int nonBillableTranstion;
	int nonBillableUnAllocated;
	int nonBillableAbsconding;
	int nonBillableAccunMgmt;
	int nonBillableInsideSale;
	int nonBillableInvstment;	
	
	int nonBillableLongLeave;
	int nonBillableMngmnt;
	
	int nonBillablePMO;
	int nonBillablePreSale;
	int nonBillableSale;
	int outBoundExitrelease;
	int pip;
	
	
	public int getBillableCount() {
		return billableCount;
	}
	public void setBillableCount(int billableCount) {
		this.billableCount = billableCount;
	}
	public int getBillablePartialCount() {
		return billablePartialCount;
	}
	public void setBillablePartialCount(int billablePartialCount) {
		this.billablePartialCount = billablePartialCount;
	}
	 
	public int getNonBillableContigent() {
		return nonBillableContigent;
	}
	public void setNonBillableContigent(int nonBillableContigent) {
		this.nonBillableContigent = nonBillableContigent;
	}
	public int getNonBillableDelMngmt() {
		return nonBillableDelMngmt;
	}
	public void setNonBillableDelMngmt(int nonBillableDelMngmt) {
		this.nonBillableDelMngmt = nonBillableDelMngmt;
	}
	public int getNonBillableOperations() {
		return nonBillableOperations;
	}
	public void setNonBillableOperations(int nonBillableOperations) {
		this.nonBillableOperations = nonBillableOperations;
	}
	public int getNonBillableShadow() {
		return nonBillableShadow;
	}
	public void setNonBillableShadow(int nonBillableShadow) {
		this.nonBillableShadow = nonBillableShadow;
	}
	public int getNonBillableTrainee() {
		return nonBillableTrainee;
	}
	public void setNonBillableTrainee(int nonBillableTrainee) {
		this.nonBillableTrainee = nonBillableTrainee;
	}
	public int getNonBillableTranstion() {
		return nonBillableTranstion;
	}
	public void setNonBillableTranstion(int nonBillableTranstion) {
		this.nonBillableTranstion = nonBillableTranstion;
	}
	 
	public int getNonBillableUnAllocated() {
		return nonBillableUnAllocated;
	}
	public void setNonBillableUnAllocated(int nonBillableUnAllocated) {
		this.nonBillableUnAllocated = nonBillableUnAllocated;
	}
	public int getNonBillableBlocked() {
		return nonBillableBlocked;
	}
	public void setNonBillableBlocked(int nonBillableBlocked) {
		this.nonBillableBlocked = nonBillableBlocked;
	}
	public int getNonBillableAbsconding() {
		return nonBillableAbsconding;
	}
	public void setNonBillableAbsconding(int nonBillableAbsconding) {
		this.nonBillableAbsconding = nonBillableAbsconding;
	}
	public int getNonBillableAccunMgmt() {
		return nonBillableAccunMgmt;
	}
	public void setNonBillableAccunMgmt(int nonBillableAccunMgmt) {
		this.nonBillableAccunMgmt = nonBillableAccunMgmt;
	}
	public int getNonBillableInsideSale() {
		return nonBillableInsideSale;
	}
	public void setNonBillableInsideSale(int nonBillableInsideSale) {
		this.nonBillableInsideSale = nonBillableInsideSale;
	}
	public int getNonBillableInvstment() {
		return nonBillableInvstment;
	}
	public void setNonBillableInvstment(int nonBillableInvstment) {
		this.nonBillableInvstment = nonBillableInvstment;
	}
	public int getNonBillableLongLeave() {
		return nonBillableLongLeave;
	}
	public void setNonBillableLongLeave(int nonBillableLongLeave) {
		this.nonBillableLongLeave = nonBillableLongLeave;
	}
	public int getNonBillableMngmnt() {
		return nonBillableMngmnt;
	}
	public void setNonBillableMngmnt(int nonBillableMngmnt) {
		this.nonBillableMngmnt = nonBillableMngmnt;
	}
	public int getNonBillablePMO() {
		return nonBillablePMO;
	}
	public void setNonBillablePMO(int nonBillablePMO) {
		this.nonBillablePMO = nonBillablePMO;
	}
	public int getNonBillablePreSale() {
		return nonBillablePreSale;
	}
	public void setNonBillablePreSale(int nonBillablePreSale) {
		this.nonBillablePreSale = nonBillablePreSale;
	}
	public int getNonBillableSale() {
		return nonBillableSale;
	}
	public void setNonBillableSale(int nonBillableSale) {
		this.nonBillableSale = nonBillableSale;
	}
	public int getOutBoundExitrelease() {
		return outBoundExitrelease;
	}
	public void setOutBoundExitrelease(int outBoundExitrelease) {
		this.outBoundExitrelease = outBoundExitrelease;
	}
	public int getPip() {
		return pip;
	}
	public void setPip(int pip) {
		this.pip = pip;
	}
	
	@Override
	public String toString() {
		return "MuneerReportDto [billableCount=" + billableCount
				+ ", billablePartialCount=" + billablePartialCount
				+ ", nonBillableBlocked=" + nonBillableBlocked
				+ ", nonBillableContigent=" + nonBillableContigent
				+ ", nonBillableDelMngmt=" + nonBillableDelMngmt
				+ ", nonBillableOperations=" + nonBillableOperations
				+ ", nonBillableShadow=" + nonBillableShadow
				+ ", nonBillableTrainee=" + nonBillableTrainee
				+ ", nonBillableTranstion=" + nonBillableTranstion
				+ ", nonBillableUnAllocated=" + nonBillableUnAllocated
				+ ", nonBillableAbsconding=" + nonBillableAbsconding
				+ ", nonBillableAccunMgmt=" + nonBillableAccunMgmt
				+ ", nonBillableInsideSale=" + nonBillableInsideSale
				+ ", nonBillableInvstment=" + nonBillableInvstment
				+ ", nonBillableLongLeave=" + nonBillableLongLeave
				+ ", nonBillableMngmnt=" + nonBillableMngmnt
				+ ", nonBillablePMO=" + nonBillablePMO
				+ ", nonBillablePreSale=" + nonBillablePreSale
				+ ", nonBillableSale=" + nonBillableSale
				+ ", outBoundExitrelease=" + outBoundExitrelease + ", pip="
				+ pip + "]";
	}
	
	
	
	
	

}
