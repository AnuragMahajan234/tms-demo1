/**
 * 
 */
package org.yash.rms.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @author arpan.badjatiya
 *
 */
@SuppressWarnings("serial")

@Entity
@Table(name="user_activity_view")
@NamedQueries({
	@NamedQuery(name = UserActivityView.GET_USER_ACTIVITY_VIEW_BASED_ON_EMPLOYEID_AND_WEEK_START_DATE, query = UserActivityView.QUERY_GET_USER_ACTIVITY)})
public class UserActivityView implements Serializable{
	
	public static final String GET_USER_ACTIVITY_VIEW_BASED_ON_EMPLOYEID_AND_WEEK_START_DATE="GET_USER_ACTIVITY_VIEW_BASED_ON_EMPLOYEID_AND_WEEK_START_DATE";
//	public static final String QUERY_GET_USER_ACTIVITY="SELECT DISTINCT o FROM UserActivityView o WHERE o.employeeId = :employeeId AND o.weekStartDate BETWEEN :minWeekStartDate AND :maxWeekStartDate ORDER BY o.weekStartDate DESC";
	public static final String QUERY_GET_USER_ACTIVITY="SELECT DISTINCT o FROM UserActivityView o WHERE o.employeeId = :employeeId AND o.weekEndDate BETWEEN :minWeekStartDate AND :maxWeekStartDate ORDER BY o.weekEndDate DESC";
	
	@Id
	@Column(name="employee_id")
	private int employeeId;
	
	@Column(name = "week_start_date")
	@NotNull
	@Id
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(style = "M-")
	private Date weekStartDate;

	@Column(name = "week_end_date")
	@NotNull
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(style = "M-")
	private Date weekEndDate;

	@Column(name = "resource_alloc_id")
	@Id
	private Integer resourceAllocId;
	
    @Column(name = "approve_status")
    @NotNull
    @Id
    private Character approveStatus;
    
    @Id
	@Column(name = "project_name")
	private String projectName;

	@Column(name = "repHrsPro")
	private Float repHrsPro;

	public int getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}

	public Character getApproveStatus() {
		return approveStatus;
	}

	public void setApproveStatus(Character status) {
		this.approveStatus = status;
	}

	public Integer getResourceAllocId() {
		return resourceAllocId;
	}

	public void setResourceAllocId(Integer resourceAllocId) {
		this.resourceAllocId = resourceAllocId;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	
	/**
	 * @return the repHrsPro
	 */
	public Float getRepHrsPro() {
		return repHrsPro;
	}

	/**
	 * @param repHrsPro the repHrsPro to set
	 */
	public void setRepHrsPro(Float repHrsPro) {
		this.repHrsPro = repHrsPro;
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

}
