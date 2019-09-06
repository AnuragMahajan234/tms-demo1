package org.yash.rms.domain;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

@SuppressWarnings("serial")
@Entity
@Table(name = "user_activity")
@NamedQueries({
	@NamedQuery(name = UserTimeSheet.DELETE_USER_ACTIVITY_DETAIL, query = "DELETE UserTimeSheetDetail where timeSheetId= :id"),
	@NamedQuery(name = UserTimeSheet.DELETE_USER_ACTIVITY, query = "DELETE UserTimeSheet where id= :id") })

public class UserTimeSheet implements Serializable{

//	public static final String UPDATE_USER_ACTIVITY= "updateUserActivity";
	public static final String DELETE_USER_ACTIVITY= "deleteUserActivity";
	public static final String DELETE_USER_ACTIVITY_DETAIL= "deleteUserActivityDetail";
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private BigInteger id;

	@Column(name = "module", length = 256)
	//@NotNull
	private String module;
	
	@Column(name = "sub_module", length = 256)
	//@NotNull
	private String subModule;
	
	@Column(name = "ticket_no", length = 256)
	private String ticketNo;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "activity_id", referencedColumnName = "id", nullable = false)
	private Activity activityId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "custom_activity_id", referencedColumnName = "id", nullable = false)
	private CustomActivity customActivityId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "res_alloc_id", referencedColumnName = "id", nullable = false)
	private ResourceAllocation resourceAllocId;
	
    @Column(name = "status")
    @NotNull
    private Character status;
    
    @Column(name = "created_id", updatable = false)
	private String createdId;

	@Column(name = "creation_timestamp", updatable = false, insertable = true)
	private Date creationTimestamp;

	@Column(name = "lastupdated_id")
	private String lastUpdatedId;

	@Column(name = "lastupdated_timestamp")
	private Date lastupdatedTimestamp;
	
	@Column(name = "rejection_remarks", length = 256)
	private String rejectRemarks;
	
	@Column(name = "approve_code", length = 256)
	private String approveCode;
	
	@Column(name = "reject_code", length = 256)
	private String rejectCode;
	
	@Column(name = "approved_by")
	@NotNull
	private int approvedBy;
	
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


	/*	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "projectticket_priorityid", referencedColumnName = "id")
	private ProjectTicketPriority projectTicketPriority;

	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "projectticket_statusid", referencedColumnName = "id")
	private ProjectTicketStatus projectTicketStatus;
	

	
	public ProjectTicketStatus getProjectTicketStatus() {
		return projectTicketStatus;
	}

	public void setProjectTicketStatus(ProjectTicketStatus projectTicketStatus) {
		this.projectTicketStatus = projectTicketStatus;
	}

	public ProjectTicketPriority getProjectTicketPriority() {
		return projectTicketPriority;
	}

	public void setProjectTicketPriority(ProjectTicketPriority projectTicketPriority) {
		this.projectTicketPriority = projectTicketPriority;
	}

*/
	@Column(name = "approval_pending_from")
	    private int approvalPendingFrom;
	    
	    
	    public int getApprovalPendingFrom() {
			return approvalPendingFrom;
		}

		public void setApprovalPendingFrom(int approvalPendingFrom) {
			this.approvalPendingFrom = approvalPendingFrom;
		}
	
	/**
	 * @return the approveCode
	 */
	public String getApproveCode() {
		return approveCode;
	}

	/**
	 * @param approveCode the approveCode to set
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
	 * @param rejectCode the rejectCode to set
	 */
	public void setRejectCode(String rejectCode) {
		this.rejectCode = rejectCode;
	}

	public String getRejectRemarks() {
		return rejectRemarks;
	}

	public void setRejectRemarks(String rejectRemarks) {
		this.rejectRemarks = rejectRemarks;
	}

	
	@OneToOne(mappedBy="timeSheetId", fetch = FetchType.LAZY,cascade = CascadeType.ALL,orphanRemoval=true)
	private UserTimeSheetDetail userTimeSheetDetails;
	
	public Character getStatus() {
        return status;
    }

    public void setStatus(Character status) {
    	 this.status = status == null ? 'N' : status;
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

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public String getTicketNo() {
		return ticketNo;
	}

	public void setTicketNo(String ticketNo) {
		this.ticketNo = ticketNo;
	}

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public String getCreatedId() {
		return createdId;
	}

	public void setCreatedId(String createdId) {
		this.createdId = createdId;
	}

	public Date getCreationTimestamp() {
		return creationTimestamp;
	}

	public void setCreationTimestamp(Date creationTimestamp) {
		this.creationTimestamp = creationTimestamp;
	}

	public String getLastUpdatedId() {
		return lastUpdatedId;
	}

	public void setLastUpdatedId(String lastUpdatedId) {
		this.lastUpdatedId = lastUpdatedId;
	}

	public Date getLastupdatedTimestamp() {
		return lastupdatedTimestamp;
	}

	public void setLastupdatedTimestamp(Date lastupdatedTimestamp) {
		this.lastupdatedTimestamp = lastupdatedTimestamp;
	}

	public UserTimeSheetDetail getUserTimeSheetDetails() {
		return userTimeSheetDetails;
	}

	public void setUserTimeSheetDetails(UserTimeSheetDetail userTimeSheetDetails) {
		this.userTimeSheetDetails = userTimeSheetDetails;
	}

	public int getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(int approvedBy) {
		this.approvedBy = approvedBy;
	}

	
	public String getSubModule() {
	
		return subModule;
	}
	
	public void setSubModule(String subModule) {
	
		this.subModule = subModule;
	}
	
	
}
