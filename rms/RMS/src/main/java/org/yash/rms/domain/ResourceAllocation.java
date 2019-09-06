package org.yash.rms.domain;

import java.io.Serializable;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.springframework.format.annotation.DateTimeFormat;
import org.yash.rms.util.Constants;

import flexjson.JSONSerializer;
import flexjson.transformer.DateTransformer;

@SuppressWarnings("serial")
@Entity
@Table(name = "resource_allocation")
@NamedQueries({
	
	@NamedQuery(name = "ResourceAllocation.FIND_ALLOCATED_ACTIVE_PROJECT_BY_CURRENTLOGGEDIN", query = "SELECT o.employeeId.employeeId FROM ResourceAllocation AS o WHERE (o.projectId.deliveryMgr.employeeId =:employeeId or o.projectId.offshoreDelMgr.employeeId=:employeeId) and (o.allocEndDate>=DATE(:currentDate) or o.allocEndDate is null)"),
	@NamedQuery(name = "ResourceAllocation.FIND_ALLOCATED_PROJECT_BY_CURRENTLOGGEDIN", query = "SELECT o.employeeId.employeeId FROM ResourceAllocation AS o WHERE (o.projectId.deliveryMgr.employeeId =:employeeId or o.projectId.offshoreDelMgr.employeeId=:employeeId)"),
	
	@NamedQuery(name = "ResourceAllocation.FIND_ALLOCATED_ACTIVE_PROJECT_BY_RESOURCEIDS", query = "SELECT o.projectId.id FROM ResourceAllocation AS o WHERE o.employeeId.employeeId in  :employeeId and (o.allocEndDate>=DATE(:currentDate) or o.allocEndDate is null)"),
	@NamedQuery(name = "ResourceAllocation.FIND_ALLOCATED_PROJECT_BY_RESOURCEIDS", query = "SELECT o.projectId.id FROM ResourceAllocation AS o WHERE o.employeeId.employeeId in  :employeeId"),
		
	@NamedQuery(name = "ResourceAllocation.FIND_ALLOCATED_RESOURCE_EMPLOYEEID", query = "SELECT o.employeeId.employeeId FROM ResourceAllocation AS o WHERE o.projectId = :projectId"),
		@NamedQuery(name = "ResourceAllocation.FIND_ALLOCATED_RESOURCE_EMPLOYEEID_BY_PROJRCT_IDS", query = "SELECT o.employeeId.employeeId FROM ResourceAllocation AS o WHERE o.projectId.id in (:projectId) and (:currentDate BETWEEN o.allocStartDate AND o.allocEndDate or (o.allocEndDate is NULL))"),
		@NamedQuery(name = "ResourceAllocation.FIND_ALLOCATED_RESOURCE_EMPLOYEEID_BY_PROJRCT_IDS_ACTIVE", query = "SELECT o.employeeId.employeeId FROM ResourceAllocation AS o WHERE o.projectId.id in (:projectId) and (:currentDate BETWEEN o.allocStartDate AND o.allocEndDate or (o.allocEndDate is NULL)) and (o.employeeId.releaseDate>=current_date() or o.employeeId.releaseDate is null)") })
public class ResourceAllocation implements Serializable {

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ResourceAllocation other = (ResourceAllocation) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "allocation_seq")
	private Integer allocationSeq;

	@Column(name = "alloc_start_date")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(style = "M-")
	private Date allocStartDate;

	@Column(name = "alloc_end_date")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(style = "M-")
	private Date allocEndDate;

	@Column(name = "alloc_block")
	private Boolean allocBlock;

	@Column(name = "alloc_remarks", length = 256)
	private String allocRemarks;

	@Column(name = "project_end_remarks", columnDefinition = "varchar(255) default 'NA'")
	private String projectEndRemarks;

	@Column(name = "curProj")
	private Boolean curProj;

	@Column(name = "billable")
	private Boolean billable;

	@Column(name = "allocHrs")
	private Integer allocHrs;

	@Column(name = "behalf_manager")
	private Boolean behalfManager;

	@OneToMany(mappedBy = "resourceAllocId", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<Timehrs> timehrss;

	@OneToMany(mappedBy = "resourceAllocId", fetch = FetchType.LAZY)
	private Set<UserActivity> userActivities;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "allocated_by", referencedColumnName = "employee_id")
	private Resource allocatedBy;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "updated_by", referencedColumnName = "employee_id")
	private Resource updatedBy;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "employee_id", referencedColumnName = "employee_id", nullable = false)
	private Resource employeeId;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "project_id", referencedColumnName = "id")
	private Project projectId;

	@Transient
	private Date lastUserActivityDate;

	@Transient
	private Date firstUserActivityDate;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ownership_id", referencedColumnName = "id")
	private Ownership ownershipId;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "allocation_type_id", referencedColumnName = "id")
	private AllocationType allocationTypeId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "designation_id", referencedColumnName = "id")
	private Designation designation;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "grade_id", referencedColumnName = "id")
	private Grade grade;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "bu_id", referencedColumnName = "id")
	private OrgHierarchy buId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "current_bu_id", referencedColumnName = "id")
	private OrgHierarchy currentBuId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "location_id", referencedColumnName = "id")
	private Location location;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "current_reporting_manager", referencedColumnName = "employee_id")
	private Resource currentReportingManager;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "current_reporting_manager_two", referencedColumnName = "employee_id")
	private Resource currentReportingManagerTwo;

	@Column(name = "ownership_transfer_date")
	private Date ownershipTransferDate;

	@Column(name = "created_id", updatable = false)
	private String createdId;

	@Column(name = "creation_timestamp", updatable = false, insertable = true)
	private Date creationTimestamp;

	@Column(name = "lastupdated_id")
	private String lastUpdatedId;
	
	@Column(name = "alloc_percentage")
	private Integer allocPercentage;

	@Column(name = "lastupdated_timestamp")
	private Date lastupdatedTimestamp;
	
   @Column(name = "resource_type")
	private String resourceType;

	public Integer getAllocPercentage() {
	return allocPercentage;
    }

   public void setAllocPercentage(Integer allocPercentage) {
	this.allocPercentage = allocPercentage;
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

	public Designation getDesignation() {
		return designation;
	}

	public void setDesignation(Designation designation) {
		this.designation = designation;
	}

	public Grade getGrade() {
		return grade;
	}

	public void setGrade(Grade grade) {
		this.grade = grade;
	}

	public OrgHierarchy getBuId() {
		return buId;
	}

	public void setBuId(OrgHierarchy buId) {
		this.buId = buId;
	}

	public OrgHierarchy getCurrentBuId() {
		return currentBuId;
	}

	public void setCurrentBuId(OrgHierarchy currentBuId) {
		this.currentBuId = currentBuId;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public Resource getCurrentReportingManager() {
		return currentReportingManager;
	}

	public void setCurrentReportingManager(Resource currentReportingManager) {
		this.currentReportingManager = currentReportingManager;
	}

	public Resource getCurrentReportingManagerTwo() {
		return currentReportingManagerTwo;
	}

	public void setCurrentReportingManagerTwo(Resource currentReportingManagerTwo) {
		this.currentReportingManagerTwo = currentReportingManagerTwo;
	}

	public Date getOwnershipTransferDate() {
		return ownershipTransferDate;
	}

	public void setOwnershipTransferDate(Date ownershipTransferDate) {
		this.ownershipTransferDate = ownershipTransferDate;
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

	public Date getFirstUserActivityDate() {
		return firstUserActivityDate;
	}

	public void setFirstUserActivityDate(Date firstUserActivityDate) {
		this.firstUserActivityDate = firstUserActivityDate;
	}

	public Date getLastUserActivityDate() {
		return lastUserActivityDate;
	}

	public void setLastUserActivityDate(Date lastUserActivityDate) {
		this.lastUserActivityDate = lastUserActivityDate;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Set<Timehrs> getTimehrss() {
		return timehrss;
	}

	public void setTimehrss(Set<Timehrs> timehrss) {
		this.timehrss = timehrss;
	}

	public Set<UserActivity> getUserActivities() {
		return userActivities;
	}

	public void setUserActivities(Set<UserActivity> userActivities) {
		this.userActivities = userActivities;
	}

	public Resource getAllocatedBy() {
		return allocatedBy;
	}

	public void setAllocatedBy(Resource allocatedBy) {
		this.allocatedBy = allocatedBy;
	}

	public Resource getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Resource updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Resource getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Resource employeeId) {
		this.employeeId = employeeId;
	}

	public Project getProjectId() {
		return projectId;
	}

	public void setProjectId(Project projectId) {
		this.projectId = projectId;
	}

	public Ownership getOwnershipId() {
		return ownershipId;
	}

	public void setOwnershipId(Ownership ownershipId) {
		this.ownershipId = ownershipId;
	}

	public Integer getAllocationSeq() {
		return allocationSeq;
	}

	public void setAllocationSeq(Integer allocationSeq) {
		this.allocationSeq = allocationSeq;
	}

	public Date getAllocStartDate() {
		return allocStartDate;
	}

	public void setAllocStartDate(Date allocStartDate) {
		this.allocStartDate = allocStartDate;
	}

	public Date getAllocEndDate() {
		return allocEndDate;
	}

	public void setAllocEndDate(Date allocEndDate) {
		this.allocEndDate = allocEndDate;
	}

	public Boolean getAllocBlock() {
		return allocBlock;
	}

	public void setAllocBlock(Boolean allocBlock) {
		this.allocBlock = allocBlock;
	}

	public String getAllocRemarks() {
		return allocRemarks;
	}

	public void setAllocRemarks(String allocRemarks) {
		this.allocRemarks = allocRemarks;
	}

	public Boolean getCurProj() {
		return curProj;
	}

	public void setCurProj(Boolean curProj) {
		this.curProj = curProj;
	}

	public Boolean getBillable() {
		return billable;
	}

	public void setBillable(Boolean billable) {
		this.billable = billable;
	}

	public Integer getAllocHrs() {
		return allocHrs;
	}

	public void setAllocHrs(Integer allocHrs) {
		this.allocHrs = allocHrs;
	}

	public Boolean getBehalfManager() {
		return behalfManager;
	}

	public void setBehalfManager(Boolean behalfManager) {
		this.behalfManager = behalfManager;
	}

	public void setAllocationTypeId(AllocationType allocationTypeId) {
		this.allocationTypeId = allocationTypeId;
	}

	public AllocationType getAllocationTypeId() {
		return allocationTypeId;

	}

	public String getProjectEndRemarks() {
		return (this.projectEndRemarks == null) ? "NA" : projectEndRemarks;
	}

	public void setProjectEndRemarks(String projectEndRemarks) {
		if (projectEndRemarks == null)
			this.projectEndRemarks = "NA";
		else
			this.projectEndRemarks = projectEndRemarks;
	}
	
	public String getResourceType() {
		return (this.resourceType == null) ? "NA" : resourceType;
	}

	public void setResourceType(String resourceType) {
		if (resourceType == null)
			this.resourceType = "NA";
		else
			this.resourceType = resourceType;
	}

	public static class ResourceAllocationTimeComparator implements Comparator<ResourceAllocation> {

		public int compare(ResourceAllocation o1, ResourceAllocation o2) {
			if (o1 == o2) {
				return 0;
			} else {
				if (o1.getAllocStartDate() != null && o2.getAllocStartDate() != null) {
					if (o1.getAllocStartDate().compareTo(o2.getAllocStartDate()) > 0) {
						return 1;
					} else if (o1.getAllocStartDate().compareTo(o2.getAllocStartDate()) < 0) {
						return -1;
					} else {
						return (o1.getId() - o2.getId());
					}
				}
			}
			return (o1.getId() - o2.getId());
		}
	}

	public boolean isOverlaps(Date beginDate, Date endDate) {
		boolean isOverlaps = false;
		if (beginDate == null) {
			return isOverlaps;
		}
		if (endDate != null && beginDate.after(endDate)) {
			throw new IllegalArgumentException("endDate should be greater than beginDate");
		}

		Date allocStartDate = this.getAllocStartDate();
		Date allocEndDate = this.getAllocEndDate();

		if (allocEndDate == null) {
			if (endDate == null) {
				isOverlaps = true;
			} else { // (endDate != null)
				if (allocStartDate.before(endDate)) {
					isOverlaps = true;
				}
			}
		} else { // allocEndDate != null
			if (endDate == null) {
				if (allocEndDate.after(beginDate)) {
					isOverlaps = true;
				}
			} else { // (endDate != null)
				if (!(beginDate.before(allocStartDate) && endDate.before(allocStartDate)) && !(beginDate.after(allocEndDate) && endDate.after(allocEndDate))) {
					isOverlaps = true;
				}
			}
		}
		return isOverlaps;
	}

	public static String toJsonArray(Collection<org.yash.rms.domain.ResourceAllocation> collection) {
		return new JSONSerializer()
				.include("employeeId.employeeName", "id", "behalfManager", "employeeId.employeeId", "employeeId.yashEmpId", "employeeId", "projectId.id",
						"projectId.projectName", "allocationTypeId.id", "allocationTypeId.allocationtype","allocPercentage", "allocStartDate", "allocEndDate", "allocBlock", "allocRemarks", "curProj", "billable", "allocHrs",
						"allocatedBy.employeeId", "updatedBy.employeeId", "ownershipId.id", "lastUserActivityDate", "firstUserActivityDate", "buId.name", "currentBuId.name", "projectEndRemarks", "resourceType")
				.transform(new DateTransformer(Constants.DATE_PATTERN_4), Date.class).exclude("*").serialize(collection);
	}

}
