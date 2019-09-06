package org.yash.rms.domain;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Formula;
import org.springframework.format.annotation.DateTimeFormat;
import org.yash.rms.util.Constants;

import flexjson.JSONSerializer;
import flexjson.transformer.DateTransformer;

@SuppressWarnings("serial")
@Entity
@Table(name = "vw_user_activity_rms_app")
public class UserActivity implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	// do not change this equals method as it is being used in contains method
	// in TimeHourEntryController
	// for approval or rejection emails.......
	@Override
	public boolean equals(Object obj) {
		UserActivity activity = (UserActivity) obj;
		if (this.weekStartDate.compareTo(activity.getWeekStartDate()) == 0 && this.weekEndDate.compareTo(activity.getWeekEndDate()) == 0)
			return true;
		else
			return false;
	}

	@Column(name = "module", length = 256)
	@NotNull
	private String module;

	@Column(name = "sub_module", length = 250)
	private String subModule;

	@Column(name = "ticket_no", length = 256)
	private String ticketNo;

	@Column(name = "d1_hours", precision = 22, scale = 2)
	private Double d1Hours;

	@Column(name = "d2_hours", precision = 22, scale = 2)
	private Double d2Hours;

	@Column(name = "d3_hours", precision = 22, scale = 2)
	private Double d3Hours;

	@Column(name = "d4_hours", precision = 22, scale = 2)
	private Double d4Hours;

	@Column(name = "d5_hours", precision = 22, scale = 2)
	private Double d5Hours;

	@Column(name = "d6_hours", precision = 22, scale = 2)
	private Double d6Hours;

	@Column(name = "d7_hours", precision = 22, scale = 2)
	private Double d7Hours;

	@Column(name = "d1_comment", length = 256)
	private String d1Comment;

	@Column(name = "d2_comment", length = 256)
	private String d2Comment;

	@Column(name = "d3_comment", length = 256)
	private String d3Comment;

	@Column(name = "d4_comment", length = 256)
	private String d4Comment;

	@Column(name = "d5_comment", length = 256)
	private String d5Comment;

	@Column(name = "d6_comment", length = 256)
	private String d6Comment;

	@Column(name = "d7_comment", length = 256)
	private String d7Comment;

	/*
	 * @Column(name = "submit_status") private Boolean submitStatus;
	 */
	@Column(name = "week_start_date")
	@NotNull
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(style = "M-")
	private Date weekStartDate;

	@Column(name = "week_end_date")
	@NotNull
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(style = "M-")
	private Date weekEndDate;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "activity_id", referencedColumnName = "id", nullable = false)
	private Activity activityId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "custom_activity_id", referencedColumnName = "id", nullable = false)
	private CustomActivity customActivityId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "resource_alloc_id", referencedColumnName = "id", nullable = false)
	private ResourceAllocation resourceAllocId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "employee_id", referencedColumnName = "employee_id")
	private Resource employeeId;

	@Column(name = "status")
	@NotNull
	private Character status;

	//@Formula("(select sum(IF(ua.d1_hours IS NULL,0,ua.d1_hours)+ IF(ua.d2_hours IS NULL,0,ua.d2_hours)+IF(ua.d3_hours IS NULL,0,ua.d3_hours)+IF(ua.d4_hours IS NULL,0,ua.d4_hours)+IF(ua.d5_hours IS NULL,0,ua.d5_hours)+IF(ua.d6_hours IS NULL,0,ua.d6_hours)+IF(ua.d7_hours IS NULL,0,ua.d7_hours)) from vw_user_activity_rms_app ua where ua.employee_Id=employee_Id and ua.week_end_date=week_end_date and ua.resource_alloc_id=resource_alloc_id)")
	@Transient
	private Double repHrsForProForWeekEndDate;

	@Transient
	private Double plannedHrs;

	@Transient
	private Double billedHrs;

	@Transient
	private String remarks;

	@Transient
	private Integer timeHrsId;

	@Transient
	private boolean viewFlag;

	@Column(name = "rejection_remarks")
	private String rejectionRemarks;

	@Column(name = "approve_code")
	private String approveCode;

	@Column(name = "reject_code")
	private String rejectCode;

	@Basic
	@Column(name="ticket_priority")
	private String ticketPriority;

	@Basic
	@Column(name="ticket_status")
	private String ticketStatus;

	public String getTicketPriority() {
		return ticketPriority;
	}

	public void setTicketPriority(String ticketPriority) {
		this.ticketPriority = ticketPriority;
	}

	public String getTicketStatus() {
		return ticketStatus;
	}

	public void setTicketStatus(String ticketStatus) {
		this.ticketStatus = ticketStatus;
	}

	/**
	 * @return the approveCode
	 */
	public String getApproveCode() {
		return approveCode;
	}

	/**
	 * @param approveCode
	 *            the approveCode to set
	 */
	public void setApproveCode(String approveCode) {
		this.approveCode = approveCode;
	}

	/**
	 * @return the rejectCode
	 */
	public String getRejectCode() {
		return rejectCode;
	}

	/**
	 * @param rejectCode
	 *            the rejectCode to set
	 */
	public void setRejectCode(String rejectCode) {
		this.rejectCode = rejectCode;
	}

	public String getRejectionRemarks() {
		return rejectionRemarks;
	}

	public void setRejectionRemarks(String rejectionRemarks) {
		this.rejectionRemarks = rejectionRemarks;
	}

	public Character getApproveStatus() {
		return status;
	}

	public void setStatus(Character approveStatus) {
		this.status = approveStatus == null ? 'N' : approveStatus;
		// this.approveStatus = approveStatus;
	}

	public Activity getActivityId() {
		return activityId;
	}

	public void setActivityId(Activity activityId) {
		this.activityId = activityId;
	}

	public CustomActivity getCustomActivityId() {
		return customActivityId;
	}

	public void setCustomActivityId(CustomActivity customActivityId) {
		this.customActivityId = customActivityId;
	}

	public ResourceAllocation getResourceAllocId() {
		return resourceAllocId;
	}

	public void setResourceAllocId(ResourceAllocation resourceAllocId) {
		this.resourceAllocId = resourceAllocId;
	}

	public Resource getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Resource employeeId) {
		this.employeeId = employeeId;
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public String getSubModule() {

		return subModule;
	}

	public void setSubModule(String subModule) {

		this.subModule = subModule;
	}

	public String getTicketNo() {
		return ticketNo;
	}

	public void setTicketNo(String ticketNo) {
		this.ticketNo = ticketNo;
	}

	public Double getD1Hours() {
		return d1Hours;
	}

	public void setD1Hours(Double d1Hours) {
		this.d1Hours = d1Hours;
	}

	public Double getD2Hours() {
		return d2Hours;
	}

	public void setD2Hours(Double d2Hours) {
		this.d2Hours = d2Hours;
	}

	public Double getD3Hours() {
		return d3Hours;
	}

	public void setD3Hours(Double d3Hours) {
		this.d3Hours = d3Hours;
	}

	public Double getD4Hours() {
		return d4Hours;
	}

	public void setD4Hours(Double d4Hours) {
		this.d4Hours = d4Hours;
	}

	public Double getD5Hours() {
		return d5Hours;
	}

	public void setD5Hours(Double d5Hours) {
		this.d5Hours = d5Hours;
	}

	public Double getD6Hours() {
		return d6Hours;
	}

	public void setD6Hours(Double d6Hours) {
		this.d6Hours = d6Hours;
	}

	public Double getD7Hours() {
		return d7Hours;
	}

	public void setD7Hours(Double d7Hours) {
		this.d7Hours = d7Hours;
	}

	public String getD1Comment() {
		return d1Comment;
	}

	public void setD1Comment(String d1Comment) {
		this.d1Comment = d1Comment;
	}

	public String getD2Comment() {
		return d2Comment;
	}

	public void setD2Comment(String d2Comment) {
		this.d2Comment = d2Comment;
	}

	public String getD3Comment() {
		return d3Comment;
	}

	public void setD3Comment(String d3Comment) {
		this.d3Comment = d3Comment;
	}

	public String getD4Comment() {
		return d4Comment;
	}

	public void setD4Comment(String d4Comment) {
		this.d4Comment = d4Comment;
	}

	public String getD5Comment() {
		return d5Comment;
	}

	public void setD5Comment(String d5Comment) {
		this.d5Comment = d5Comment;
	}

	public String getD6Comment() {
		return d6Comment;
	}

	public void setD6Comment(String d6Comment) {
		this.d6Comment = d6Comment;
	}

	public String getD7Comment() {
		return d7Comment;
	}

	public void setD7Comment(String d7Comment) {
		this.d7Comment = d7Comment;
	}

	public Date getWeekStartDate() {
		return weekStartDate;
	}

	public void setWeekStartDate(Date weekStartDate) {
		this.weekStartDate = weekStartDate;
	}

	public Date getWeekEndDate() {
		return weekEndDate;
	}

	public void setWeekEndDate(Date weekEndDate) {
		this.weekEndDate = weekEndDate;
	}

	/*
	 * public Boolean getSubmitStatus() { return submitStatus; }
	 * 
	 * public void setSubmitStatus(Boolean submitStatus) { this.submitStatus =
	 * submitStatus; }
	 */
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Double getRepHrsForProForWeekEndDate() {
		return repHrsForProForWeekEndDate;
	}

	public void setRepHrsForProForWeekEndDate(Double repHrsForProForWeekEndDate) {
		this.repHrsForProForWeekEndDate = repHrsForProForWeekEndDate;
	}

	public Double getPlannedHrs() {
		return plannedHrs;
	}

	public void setPlannedHrs(Double plannedHrs) {
		this.plannedHrs = plannedHrs;
	}

	public Double getBilledHrs() {
		return billedHrs;
	}

	public void setBilledHrs(Double billedHrs) {
		this.billedHrs = billedHrs;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Integer getTimeHrsId() {
		return timeHrsId;
	}

	public void setTimeHrsId(Integer timeHrsId) {
		this.timeHrsId = timeHrsId;
	}

	public boolean isViewFlag() {
		return viewFlag;
	}

	public void setViewFlag(boolean viewFlag) {
		this.viewFlag = viewFlag;
	}

	public void calculateEditFlag(List<Integer> projectIds) {

		if (projectIds != null) {
			// projectIds.contains(this.getResourceAllocId().getProjectId().getId().intValue());
			// for(Integer projectId:projectIds){
			if (projectIds.contains(this.getResourceAllocId().getProjectId().getId().intValue())) {
				setViewFlag(true);

			} else {
				setViewFlag(false);
			}
			// }
		} else {
			setViewFlag(false);
		}

	}

	/**
	 * @return the status
	 */
	public Character getStatus() {
		return status;
	}

	public static String toJsonArray(Collection<org.yash.rms.domain.UserActivity> collection) {
		return new JSONSerializer()
				.include("id", "employeeIdHidden", "employeeId.employeeId", "employeeId.yashEmpId", "resourceAllocId.id", "resourceAllocId.allocEndDate", "resourceAllocId.projectId.projectName",
						"employeeId.employeeId", "employeeId.employeeName", "behalfManager", "employeeId.designationId.designationName", "weekEndDate", "plannedHrs", "submitStatus", "billedHrs",
						"remarks", "approveStatus", "repHrsForProForWeekEndDate", "approved", "activityId.id", "customActivityId.id", "module", "d1Hours", "d2Hours", "d3Hours", "d4Hours", "d5Hours",
						"d6Hours", "d7Hours", "d1Comment", "d2Comment", "d3Comment", "d4Comment", "d5Comment", "d6Comment", "d7Comment", "weekStartDate", "userAction", "weekStartDateHidden",
						"weekEndDateHidden", "timeHrsId", "viewFlag", "rejectionRemarks").exclude("*").transform(new DateTransformer(Constants.DATE_PATTERN), Date.class).serialize(collection);
	}

}
