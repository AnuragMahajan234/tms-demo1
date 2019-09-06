package org.yash.rms.domain;

import java.io.Serializable;
import java.util.ArrayList;
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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.springframework.format.annotation.DateTimeFormat;
import org.yash.rms.util.Constants;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import flexjson.transformer.DateTransformer;

@SuppressWarnings("serial")
@Entity
@Table(name = "resource_ln_tr_dtl")
public class ResourceLoanTransfer implements Comparable<org.yash.rms.domain.ResourceLoanTransfer>,
		Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "employee_id", referencedColumnName = "employee_id", nullable = false)
	private Resource resourceId;

	@Column(name = "transfer_date")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(style = "M-")
	private Date transferDate;
	
	/*added field */
	@Column(name = "end_transfer_date")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(style = "M-")
	private Date endTransferDate;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "current_reporting_manager", referencedColumnName = "employee_id")
	private Resource currentReportingManager;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "current_reporting_two", referencedColumnName = "employee_id")
	private Resource currentReportingManagerTwo;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "current_bu_id", referencedColumnName = "id", nullable = false)
	private OrgHierarchy currentBuId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "grade_id", referencedColumnName = "id", nullable = false)
	private Grade gradeId;
	
	@ManyToOne
	@JoinColumn(name = "event_id", referencedColumnName = "id", nullable = false)
	private Event eventId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "bu_id", referencedColumnName = "id", nullable = false)
	private OrgHierarchy buId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "location_id", referencedColumnName = "id", nullable = false)
	private Location locationId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ownership", referencedColumnName = "id", nullable = false)
	private Ownership ownership;

	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "payroll_location", referencedColumnName = "id")
	private Location deploymentLocationId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "designation_id", referencedColumnName = "id", nullable = false)
	private Designation designationId;

	
	@Column(name = "created_id")
	private String createdId;

	@Column(name = "creation_timestamp", insertable = true)
	private Date creationTimestamp;
	
	@Column(name = "yash_emp_id")
	private String yashEmpId;
	
	
	@Column(name = "email_id")
	private String emailId;
	
	
	@Column(name = "contact_number")
	private String contactNumber;
	
	@Column(name = "contact_number_two")
	private String contactNumberTwo;
	
	@Column(name="date_Of_Joining")
	private Date dateOfJoining;
	
	@Column(name="confirmation_Date")
	private Date confirmationDate;

	@Column(name="last_Appraisal")
	private Date lastAppraisal;
	
	@Column(name="penultimate_Appraisal")
	private Date penultimateAppraisal;
	
	@Column(name="release_Date")
	private Date releaseDate;
	

	@Column(name = "bGComment_timestamp")
	private Date bGCommentsTimestamp;
	
	@Column(name = "bUComment_timestamp")
	private Date bUCommentsTimestamp;
	
	@Column(name = "hRComment_timestamp")
	private Date hRCommentsTimestamp;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "bGH_Name", referencedColumnName = "employee_id")
	private Resource bGHName;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "bU_Name", referencedColumnName = "employee_id")
	private Resource bUHName;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "hR_Name", referencedColumnName = "employee_id")
	private Resource hRName;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "employee_category", referencedColumnName = "id", nullable = false)
	private EmployeeCategory employeeCategory;
	
	/*@Column(name = "bUComment_timestamp", updatable = false, insertable = true)
	private Date bUCommentTimestamp ;
	
	@Column(name = "hRComment_timestamp", updatable = false, insertable = true)
	private Date hRCommentTimestamp ;*/
	
	
	public EmployeeCategory getEmployeeCategory() {
		return employeeCategory;
	}

	public void setEmployeeCategory(EmployeeCategory employeeCategory) {
		this.employeeCategory = employeeCategory;
	}

	@Column(name = "bGHComments")
	private String bGHComments;
	
	@Column(name = "bUComments")
	private String bUHComments;
	
	@Column(name = "hRComments")
	private String hRComments;
	


	

	/**
	 * @return the eventId
	 */
	public Event getEventId() {
		return eventId;
	}

	/**
	 * @param eventId the eventId to set
	 */
	public void setEventId(Event eventId) {
		this.eventId = eventId;
	}

	/**
	 * @return the bUCommentsTimestamp
	 */
	public Date getbUCommentsTimestamp() {
		return bUCommentsTimestamp;
	}

	/**
	 * @param bUCommentsTimestamp the bUCommentsTimestamp to set
	 */
	public void setbUCommentsTimestamp(Date bUCommentsTimestamp) {
		this.bUCommentsTimestamp = bUCommentsTimestamp;
	}

	/**
	 * @return the hRCommentsTimestamp
	 */
	public Date gethRCommentsTimestamp() {
		return hRCommentsTimestamp;
	}

	/**
	 * @param hRCommentsTimestamp the hRCommentsTimestamp to set
	 */
	public void sethRCommentsTimestamp(Date hRCommentsTimestamp) {
		this.hRCommentsTimestamp = hRCommentsTimestamp;
	}

	

	public Date getDateOfJoining() {
		return dateOfJoining;
	}

	/**
	 * @return the hRComments
	 */
	public String gethRComments() {
		return hRComments;
	}

	/**
	 * @param hRComments the hRComments to set
	 */
	public void sethRComments(String hRComments) {
		this.hRComments = hRComments;
	}

	/**
	 * @return the bUHComments
	 */
	public String getbUHComments() {
		return bUHComments;
	}

	/**
	 * @param bUHComments the bUHComments to set
	 */
	public void setbUHComments(String bUHComments) {
		this.bUHComments = bUHComments;
	}

	

	/**
	 * @return the bGCommentsTimestamp
	 */
	public Date getbGCommentsTimestamp() {
		return bGCommentsTimestamp;
	}

	/**
	 * @param bGCommentsTimestamp the bGCommentsTimestamp to set
	 */
	public void setbGCommentsTimestamp(Date bGCommentsTimestamp) {
		this.bGCommentsTimestamp = bGCommentsTimestamp;
	}

	/**
	 * @return the bGHComments
	 */
	public String getbGHComments() {
		return bGHComments;
	}

	/**
	 * @param bGHComments the bGHComments to set
	 */
	public void setbGHComments(String bGHComments) {
		this.bGHComments = bGHComments;
	}

	public void setDateOfJoining(Date dateOfJoining) {
		this.dateOfJoining = dateOfJoining;
	}

	public Date getConfirmationDate() {
		return confirmationDate;
	}

	public void setConfirmationDate(Date confirmationDate) {
		this.confirmationDate = confirmationDate;
	}

	public Date getLastAppraisal() {
		return lastAppraisal;
	}

	public void setLastAppraisal(Date lastAppraisal) {
		this.lastAppraisal = lastAppraisal;
	}

	public Date getPenultimateAppraisal() {
		return penultimateAppraisal;
	}

	public void setPenultimateAppraisal(Date penultimateAppraisal) {
		this.penultimateAppraisal = penultimateAppraisal;
	}

	public Date getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}

	public Designation getDesignationId() {
		return designationId;
	}

	public void setDesignationId(Designation designationId) {
		this.designationId = designationId;
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


	public static org.yash.rms.domain.ResourceLoanTransfer fromJsonToResource(String json) {
		return new JSONDeserializer<ResourceLoanTransfer>().use(null, ResourceLoanTransfer.class)
				.use(Date.class, new DateTransformer(Constants.DATE_PATTERN))
				.deserialize(json);
	}

	public static String toJsonArray(
			Collection<org.yash.rms.domain.ResourceLoanTransfer> collection) {
		return new JSONSerializer()
				.include(
						"resourceId.employeeName","yashEmpId","emailId","contactNumber","contactNumberTwo",
						"designationId.designationName","gradeId.grade",
						"transferDate","buId.name","currentBuId.name","buId.parentId.name","currentBuId.parentId.name",
						"currentReportingManager.employeeName",						
						"currentReportingManagerTwo.employeeName",												
						"locationId.location","deploymentLocationId.location","employeeCategory.id","employeeCategory.employeecategoryName",					
						"ownership.id","ownership.ownershipName","createdId","creationTimestamp","dateOfJoining","penultimateAppraisal",
						"lastAppraisal","confirmationDate","releaseDate",
						"bGHComments","bGCommentsTimestamp","bGHName.employeeName",
						"bUHComments","bUCommentsTimestamp","bUHName.employeeName",
						"hRComments","hRCommentsTimestamp","hRName.employeeName",
						"eventId.event","endTransferDate"
				)
				.exclude("*")
				.transform(new DateTransformer(Constants.DATE_PATTERN_4),
						Date.class).serialize(collection);
	}

	public static Collection<org.yash.rms.domain.ResourceLoanTransfer> fromJsonArrayToResources(
			String json) {
		return new JSONDeserializer<List<ResourceLoanTransfer>>()
				.use(null, ArrayList.class)
				.use(Date.class, new DateTransformer(Constants.DATE_PATTERN_4))
				.use("values", ResourceLoanTransfer.class).deserialize(json);
	}	

	public boolean isAccountNonExpired() {
		return true;
	}

	public boolean isAccountNonLocked() {
		return true;
	}

	public boolean isCredentialsNonExpired() {
		return true;
	}

	public boolean isEnabled() {
		return true;
	}

	public Date getTransferDate() {
		return transferDate;
	}

	public void setTransferDate(Date transferDate) {
		this.transferDate = transferDate;
	}
	
	public Resource getCurrentReportingManager() {
		return currentReportingManager;
	}

	public void setCurrentReportingManager(
			Resource currentReportingManager) {
		this.currentReportingManager = currentReportingManager;
	}

	public Resource getCurrentReportingManagerTwo() {
		return currentReportingManagerTwo;
	}

	public void setCurrentReportingManagerTwo(
			Resource currentReportingManagerTwo) {
		this.currentReportingManagerTwo = currentReportingManagerTwo;
	}

	public OrgHierarchy getCurrentBuId() {
		return currentBuId;
	}

	public void setCurrentBuId(OrgHierarchy currentBuId) {
		this.currentBuId = currentBuId;
	}

	public Grade getGradeId() {
		return gradeId;
	}

	public void setGradeId(Grade gradeId) {
		this.gradeId = gradeId;
	}

	public OrgHierarchy getBuId() {
		return buId;
	}

	public void setBuId(OrgHierarchy buId) {
		this.buId = buId;
	}

	public Location getLocationId() {
		return locationId;
	}

	public void setLocationId(Location locationId) {
		this.locationId = locationId;
	}

	public Ownership getOwnership() {
		return ownership;
	}

	public void setOwnership(Ownership ownership) {
		this.ownership = ownership;
	}

	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ResourceLoanTransfer other = (ResourceLoanTransfer) obj;
		if (resourceId == null) {
			if (other.resourceId != null)
				return false;
		} else if (!resourceId.equals(other.resourceId))
			return false;
		return true;
	}

	public int compareTo(ResourceLoanTransfer arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
		

	public Location getDeploymentLocationId() {
		return deploymentLocationId;
	}

	public void setDeploymentLocationId(Location deploymentLocationId) {
		this.deploymentLocationId = deploymentLocationId;
	}

	public Resource getResourceId() {
		return resourceId;
	}

	public void setResourceId(Resource resourceId) {
		this.resourceId = resourceId;
	}

	public String getYashEmpId() {
		return yashEmpId;
	}

	public void setYashEmpId(String yashEmpId) {
		this.yashEmpId = yashEmpId;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public String getContactNumberTwo() {
		return contactNumberTwo;
	}

	public void setContactNumberTwo(String contactNumberTwo) {
		this.contactNumberTwo = contactNumberTwo;
	}
	
	
	
	

	
	

	/**
	 * @return the bUHName
	 */
	public Resource getbUHName() {
		return bUHName;
	}

	/**
	 * @param bUHName the bUHName to set
	 */
	public void setbUHName(Resource bUHName) {
		this.bUHName = bUHName;
	}

	/**
	 * @return the hRName
	 */
	public Resource gethRName() {
		return hRName;
	}

	/**
	 * @param hRName the hRName to set
	 */
	public void sethRName(Resource hRName) {
		this.hRName = hRName;
	}

	

	/**
	 * @return the bGHName
	 */
	public Resource getbGHName() {
		return bGHName;
	}

	/**
	 * @param bGHName the bGHName to set
	 */
	public void setbGHName(Resource bGHName) {
		this.bGHName = bGHName;
	}

	
	
	
	public Date getEndTransferDate() {
		return endTransferDate;
	}

	public void setEndTransferDate(Date endTransferDate) {
		this.endTransferDate = endTransferDate;
	}

	public static ResourceLoanTransfer toResourceLoanTransfer(Resource resource) {
		
		ResourceLoanTransfer loanTransfer = new ResourceLoanTransfer();
		loanTransfer.setResourceId(resource);
		 loanTransfer.setOwnership(resource.getOwnership());
		 loanTransfer.setEmployeeCategory(resource.getEmployeeCategory());
		 loanTransfer.setDesignationId(resource.getDesignationId());
		 loanTransfer.setGradeId(resource.getGradeId());
		 loanTransfer.setLocationId(resource.getLocationId());
		 loanTransfer.setDeploymentLocationId(resource.getDeploymentLocation());
		 loanTransfer.setBuId(resource.getBuId());
		 loanTransfer.setCurrentBuId(resource.getCurrentBuId());
		 loanTransfer.setTransferDate(resource.getTransferDate());
		 loanTransfer.setCreatedId(resource.getCreatedId());
		 loanTransfer.setYashEmpId(resource.getYashEmpId());
		 loanTransfer.setEmailId(resource.getEmailId());
		 loanTransfer.setContactNumber(resource.getContactNumber());
		 loanTransfer.setContactNumberTwo(resource.getContactNumberTwo());
		 loanTransfer.setDateOfJoining(resource.getDateOfJoining());
		 loanTransfer.setPenultimateAppraisal(resource.getPenultimateAppraisal());
		 loanTransfer.setLastAppraisal(resource.getLastAppraisal());
		 loanTransfer.setConfirmationDate(resource.getConfirmationDate());
		
		 loanTransfer.setCurrentReportingManager(resource.getCurrentReportingManager());
		 loanTransfer.setCurrentReportingManagerTwo(resource.getCurrentReportingManagerTwo());
		 
		 loanTransfer.setReleaseDate(resource.getReleaseDate());
		 loanTransfer.setbGHComments(resource.getbGHComments());
		 loanTransfer.setEndTransferDate(resource.getEndTransferDate());
		 if (resource.getbGHName() == null) {
			loanTransfer.setbGHName(null);
		} else {
			loanTransfer.setbGHName(resource.getbGHName());
		}

		 loanTransfer.setbUHComments(resource.getbUHComments());
		 
		 if (resource.getbUHName() == null) {
				loanTransfer.setbUHName(null);
			} else {
				loanTransfer.setbUHName(resource.getbUHName());
			}
		
		 loanTransfer.sethRComments(resource.gethRComments());
		 
		 if (resource.gethRName()== null) {
				loanTransfer.sethRName(null);
			} else {
				loanTransfer.sethRName(resource.gethRName());
			}
		
		 loanTransfer.setbGCommentsTimestamp(resource.getbGCommentsTimestamp());
		loanTransfer.setbUCommentsTimestamp(resource.getbUCommentTimestamp());
		loanTransfer.sethRCommentsTimestamp(resource.gethRCommentTimestamp());
		 loanTransfer.setEventId(resource.getEventId());
		 if (resource.getEventId() == null) {
				loanTransfer.setEventId(null);
			} else {
				loanTransfer.setEventId(resource.getEventId());
			}
		 return loanTransfer;
	}

}
