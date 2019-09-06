package org.yash.rms.proxy;

import java.util.Date;

import org.yash.rms.excel.Excel;
import org.yash.rms.excel.ExcelColumn;

@Excel
public class CATicketExcelMapping {

	@ExcelColumn(columnName = "Ticket Number")
	private String caTicketNo;

	@ExcelColumn(columnName = "Open Date")
	private Date creationDate;

	@ExcelColumn(columnName = "Last Modified Date")
	private Date updatedDate;

	@ExcelColumn(columnName = "Resolve Date")
	private Date solutionDevelopedDate;

	@ExcelColumn(columnName = "Close Date")
	private Date closedPendingdate;

	@ExcelColumn(columnName = "Priority")
	private String priority;

	@ExcelColumn(columnName = "Unit - Affected End User")
	private String unit;

	@ExcelColumn(columnName = "Area / Category Level 3")
	private String landscape;

	@ExcelColumn(columnName = "Area / Category Level 4")
	private String module;

	@ExcelColumn(columnName = "Assignee UserID")
	private String recfId;

	@ExcelColumn(columnName = "Description")
	private String description;

	@ExcelColumn(columnName = "Parent Ticket Number")
	private String parentTicketNumber;

	@ExcelColumn(columnName = "Reopen Frequency")
	private String reopenFrequency;

	@ExcelColumn(columnName = "Root Cause")
	private String rootCause;

	@ExcelColumn(columnName = "Group - Assigned")
	private String groupAssigned;

	@ExcelColumn(columnName = "Affected End User UserID[Prompt]")
	private String affectedEndUserId;

	@ExcelColumn(columnName = "Affected End User Name")
	private String affectedEndUserName;

	public String getCaTicketNo() {
		return caTicketNo;
	}

	public void setCaTicketNo(String caTicketNo) {
		this.caTicketNo = caTicketNo;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public Date getSolutionDevelopedDate() {
		return solutionDevelopedDate;
	}

	public void setSolutionDevelopedDate(Date solutionDevelopedDate) {
		this.solutionDevelopedDate = solutionDevelopedDate;
	}

	public Date getClosedPendingdate() {
		return closedPendingdate;
	}

	public void setClosedPendingdate(Date closedPendingdate) {
		this.closedPendingdate = closedPendingdate;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getLandscape() {
		return landscape;
	}

	public void setLandscape(String landscape) {
		this.landscape = landscape;
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public String getRecfId() {
		return recfId;
	}

	public void setRecfId(String recfId) {
		this.recfId = recfId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getParentTicketNumber() {
		return parentTicketNumber;
	}

	public void setParentTicketNumber(String parentTicketNumber) {
		this.parentTicketNumber = parentTicketNumber;
	}

	public String getReopenFrequency() {
		return reopenFrequency;
	}

	public void setReopenFrequency(String reopenFrequency) {
		this.reopenFrequency = reopenFrequency;
	}

	public String getRootCause() {
		return rootCause;
	}

	public void setRootCause(String rootCause) {
		this.rootCause = rootCause;
	}

	public String getGroupAssigned() {
		return groupAssigned;
	}

	public void setGroupAssigned(String groupAssigned) {
		this.groupAssigned = groupAssigned;
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

}
