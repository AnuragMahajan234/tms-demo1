/**
 * 
 */
package org.yash.rms.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

/**
 * @author ankita.shukla
 *
 */
@SuppressWarnings("serial")

@Entity
@Table(name="ENGAGEMENTMODEL")
@NamedQueries({
	@NamedQuery(
			name = EngagementModel.SEARCH_ENGAGEMENTMODEL_BASED_ON_ID,
			query = EngagementModel.QUERY_FOR_SEARCH_ENGAGEMENTMODEL_BASED_ON_ID
			),
			@NamedQuery(
					name = EngagementModel.DELETE_ENGAGEMENTMODEL_BASED_ON_ID,
					query = EngagementModel.QUERY_FOR_DELETE_ENGAGEMENTMODEL_BASED_ON_ID
					)
})
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class EngagementModel implements Serializable{
	
	public final static String SEARCH_ENGAGEMENTMODEL_BASED_ON_ID="SEARCH_ENGAGEMENTMODEL_BASED_ON_ID";
	public final static String QUERY_FOR_SEARCH_ENGAGEMENTMODEL_BASED_ON_ID="from EngagementModel l where l.id = :id";
	
	public final static String DELETE_ENGAGEMENTMODEL_BASED_ON_ID="DELETE_ENGAGEMENTMODEL_BASED_ON_ID";
	public final static String QUERY_FOR_DELETE_ENGAGEMENTMODEL_BASED_ON_ID="DELETE EngagementModel l where l.id = :id";
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="engagement_model_name")
	private String engagementModelName;
	
	@Column(name="timesheet_compulsory")
	private String timesheetCompulsory;
	
	@Column(name="created_id", updatable = false)
	private String createdId;
	
	@Column(name="creation_timestamp" ,updatable = false, insertable = true)
	private Date creationTimestamp;
	
	@Column(name="lastupdated_id")
	private String	lastUpdatedId;
	
	@Column(name="lastupdated_timestamp")
	private Date lastupdatedTimestamp;

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the engagementModelName
	 */
	public String getEngagementModelName() {
		return engagementModelName;
	}

	/**
	 * @param engagementModelName the engagementModelName to set
	 */
	public void setEngagementModelName(String engagementModelName) {
		this.engagementModelName = engagementModelName;
	}

	/**
	 * @return the timesheetCompulsory
	 */
	public String getTimesheetCompulsory() {
		return timesheetCompulsory;
	}

	/**
	 * @param timesheetCompulsory the timesheetCompulsory to set
	 */
	public void setTimesheetCompulsory(String timesheetCompulsory) {
		this.timesheetCompulsory = timesheetCompulsory;
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
}
