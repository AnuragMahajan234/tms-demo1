/**
 * 
 */
package org.yash.rms.report.dto;

/**
 * @author arpan.badjatiya
 * 
 */
public class DashboardPerformance {

	private int id;
	private int totalAssigned;
	private int totalOpen;
	private int totalResolvedYes;
	private int totalResolvedNo;
	private float slaMissed;
	private int agingResolved;
	private int agingOpen;
	private int projectResolved;
	private int projectOpen;
	private int problemManagementProposed;
	private int problemManagementJustified;
	private int problemManagementJustifiedCrop;
	private String urgenResolutionTime;
	private float highResolutionTime;
	private float mediumResolutionTime;

	public int getProjectOpen() {
		return projectOpen;
	}

	public void setProjectOpen(int projectOpen) {
		this.projectOpen = projectOpen;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getTotalAssigned() {
		return totalAssigned;
	}

	public void setTotalAssigned(int totalAssigned) {
		this.totalAssigned = totalAssigned;
	}

	public int getTotalOpen() {
		return totalOpen;
	}

	public void setTotalOpen(int totalOpen) {
		this.totalOpen = totalOpen;
	}

	public int getTotalResolvedYes() {
		return totalResolvedYes;
	}

	public void setTotalResolvedYes(int totalResolvedYes) {
		this.totalResolvedYes = totalResolvedYes;
	}

	public int getTotalResolvedNo() {
		return totalResolvedNo;
	}

	public void setTotalResolvedNo(int totalResolvedNo) {
		this.totalResolvedNo = totalResolvedNo;
	}

	public float getSlaMissed() {
		return slaMissed;
	}

	public void setSlaMissed(float slaMissed) {
		this.slaMissed = slaMissed;
	}

	public void setSlaMissed(int slaMissed) {
		this.slaMissed = slaMissed;
	}

	public int getAgingResolved() {
		return agingResolved;
	}

	public void setAgingResolved(int agingResolved) {
		this.agingResolved = agingResolved;
	}

	public int getAgingOpen() {
		return agingOpen;
	}

	public void setAgingOpen(int agingOpen) {
		this.agingOpen = agingOpen;
	}

	public int getProjectResolved() {
		return projectResolved;
	}

	public void setProjectResolved(int projectResolved) {
		this.projectResolved = projectResolved;
	}

	public int getProblemManagementProposed() {
		return problemManagementProposed;
	}

	public void setProblemManagementProposed(int problemManagementProposed) {
		this.problemManagementProposed = problemManagementProposed;
	}

	public int getProblemManagementJustified() {
		return problemManagementJustified;
	}

	public void setProblemManagementJustified(int problemManagementJustified) {
		this.problemManagementJustified = problemManagementJustified;
	}

	// public float getUrgenResolutionTime() {
	// return urgenResolutionTime;
	// }
	//
	// public void setUrgenResolutionTime(float urgenResolutionTime) {
	// this.urgenResolutionTime = urgenResolutionTime;
	// }

	public float getHighResolutionTime() {
		return highResolutionTime;
	}

	public String getUrgenResolutionTime() {
		return urgenResolutionTime;
	}

	public void setUrgenResolutionTime(String urgenResolutionTime) {
		this.urgenResolutionTime = urgenResolutionTime;
	}

	public void setHighResolutionTime(float highResolutionTime) {
		this.highResolutionTime = highResolutionTime;
	}

	public float getMediumResolutionTime() {
		return mediumResolutionTime;
	}

	public void setMediumResolutionTime(float mediumResolutionTime) {
		this.mediumResolutionTime = mediumResolutionTime;
	}

	public int getProblemManagementJustifiedCrop() {
		return problemManagementJustifiedCrop;
	}

	public void setProblemManagementJustifiedCrop(
			int problemManagementJustifiedCrop) {
		this.problemManagementJustifiedCrop = problemManagementJustifiedCrop;
	}

}
