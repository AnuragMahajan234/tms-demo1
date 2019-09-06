package org.yash.rms.domain;

import java.io.Serializable;
import java.util.Date;

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
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;
import org.yash.rms.util.Constants;

import flexjson.JSONDeserializer;
import flexjson.transformer.DateTransformer;

@SuppressWarnings("serial")
@Entity
@Table(name = "timehrs")
public class Timehrs implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "week_ending_date")
	@NotNull
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(style = "M-")
	private Date weekEndingDate;

	@Column(name = "planned_hrs", precision = 22, scale = 2)
	private Double plannedHrs;

	@Column(name = "billed_hrs", precision = 22, scale = 2)
	private Double billedHrs;

	@Column(name = "remarks", length = 256)
	private String remarks;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "resource_id", referencedColumnName = "employee_id", nullable = false)
	private Resource resourceId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "resource_alloc_id", referencedColumnName = "id", nullable = false)
	private ResourceAllocation resourceAllocId;

	@Column(name = "created_id", updatable = false)
	private String createdId;

	@Column(name = "creation_timestamp", updatable = false, insertable = true)
	private Date creationTimestamp;

	@Column(name = "lastupdated_id")
	private String lastUpdatedId;

	@Column(name = "lastupdated_timestamp")
	private Date lastupdatedTimestamp;

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public ResourceAllocation getResourceAllocId() {
		return resourceAllocId;
	}

	public void setResourceAllocId(ResourceAllocation resourceAllocId) {
		this.resourceAllocId = resourceAllocId;
	}

	public Date getWeekEndingDate() {
		return weekEndingDate;
	}

	public void setWeekEndingDate(Date weekEndingDate) {
		this.weekEndingDate = weekEndingDate;
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

	public Resource getResourceId() {
		return resourceId;
	}

	public void setResourceId(Resource resourceId) {
		this.resourceId = resourceId;
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

	public static org.yash.rms.domain.Timehrs fromJsonToTimehrs(String json) {
		return new JSONDeserializer<Timehrs>().use(null, Timehrs.class).use(Date.class, new DateTransformer(Constants.DATE_PATTERN_4)).deserialize(json);
	}

}
