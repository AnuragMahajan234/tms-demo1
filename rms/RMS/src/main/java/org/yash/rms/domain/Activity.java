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
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

/**
 * @author arpan.badjatiya
 * 
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "ACTIVITY")
@NamedQueries({
		@NamedQuery(name = Activity.SEARCH_ACTIVITY_BASED_ON_ID, query = Activity.QUERY_ACTIVITY_BASED_ON_ID),
		@NamedQuery(name = Activity.DELETE_ACTIVITY_BASED_ON_ID, query = Activity.QUERY_ACTIVITY_DELETE_BASED_ON_ID) })
public class Activity implements Serializable {

	public final static String SEARCH_ACTIVITY_BASED_ON_ID = "SEARCH_ACTIVITY_BASED_ON_ID";
	public final static String QUERY_ACTIVITY_BASED_ON_ID = "from Activity a where a.id = :id";

	public final static String DELETE_ACTIVITY_BASED_ON_ID = "DELETE_ACTIVITY_BASED_ON_ID";
	public final static String QUERY_ACTIVITY_DELETE_BASED_ON_ID = "DELETE Activity a where a.id = :id";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "activity_name", length = 256, unique = true)
	@NotNull
	private String activityName;

	@Column(name = "mandatory")
	@NotNull
	private boolean mandatory;

	@Column(name = "type", length = 256)
	@NotNull
	private String type;

	@Column(name = "max")
	@NotNull
	private Integer max;

	@Column(name = "format", length = 256)
	private String format;

	@Column(name = "productive")
	@NotNull
	private boolean productive;

	@Column(name = "created_id", updatable = false)
	private String createdId;

	@Column(name = "creation_timestamp", updatable = false, insertable = true)
	private Date creationTimestamp;

	@Column(name = "lastupdated_id")
	private String lastUpdatedId;

	@Column(name = "lastupdated_timestamp")
	private Date lastupdatedTimestamp;
	
	@Transient
	private String activityType;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	public boolean isMandatory() {
		return mandatory;
	}

	public void setMandatory(boolean mandatory) {
		this.mandatory = mandatory;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getMax() {
		return max;
	}

	public void setMax(Integer max) {
		this.max = max;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public boolean isProductive() {
		return productive;
	}

	public void setProductive(boolean productive) {
		this.productive = productive;
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

	public String getActivityType() {
		return activityType;
	}

	public void setActivityType(String activityType) {
		this.activityType = activityType;
	}
}
