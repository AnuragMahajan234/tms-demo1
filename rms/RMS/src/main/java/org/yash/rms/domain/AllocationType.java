package org.yash.rms.domain;

import java.util.Comparator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

@Entity
@Table(name = "allocationtype")

@NamedQueries({

		@NamedQuery(name = AllocationType.DELETE_ALLOCATIONTYPE_BASED_ON_ID, query = AllocationType.QUERY_ALLOCATIONTYPE_DELETE_BASED_ON_ID) })

public class AllocationType {

	public final static String DELETE_ALLOCATIONTYPE_BASED_ON_ID = "DELETE_ALLOCATIONTYPE_BASED_ON_ID";
	public final static String QUERY_ALLOCATIONTYPE_DELETE_BASED_ON_ID = "DELETE AllocationType b where b.id = :id";

	@Id
	@Column(name = "id")
	private int id;

	@Column(name = "Priority")
	private int priority;

	@Column(name = "allocationtype")
	private String allocationType;

	@Column(name = "is_default")
	private boolean isDefault;

	@Column(name = "created_id", updatable = false)
	private String createdId;

	@Column(name = "creation_timestamp", updatable = false, insertable = true)
	private java.util.Date creationTimeStamp;

	@Column(name = "lastupdated_id")
	private String lastUpdatedId;

	@Column(name = "lastupdated_timestamp")
	private java.util.Date lastUpdatedTimeStamp;

	@Column(name = "required_for_rrf")
	private String isActiveForRRF;

	@Column(name = "bgh_mandatory_flag")
	private String bghMandatoryFlag;

	@Column(name = "alias_allocation_name")
	private String aliasAllocationName;

	public String getIsActiveForRRF() {
		return isActiveForRRF;
	}

	public void setIsActiveForRRF(String isActiveForRRF) {
		this.isActiveForRRF = isActiveForRRF;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAllocationType() {
		return allocationType;
	}

	public void setAllocationType(String allocationType) {
		this.allocationType = allocationType;
	}

	public String getCreatedId() {
		return createdId;
	}

	public void setCreatedId(String createdId) {
		this.createdId = createdId;
	}

	public java.util.Date getCreationTimeStamp() {
		return creationTimeStamp;
	}

	public void setCreationTimeStamp(java.util.Date creationTimeStamp) {
		this.creationTimeStamp = creationTimeStamp;
	}

	public String getLastUpdatedId() {
		return lastUpdatedId;
	}

	public void setLastUpdatedId(String lastUpdatedId) {
		this.lastUpdatedId = lastUpdatedId;
	}

	public java.util.Date getLastUpdatedTimeStamp() {
		return lastUpdatedTimeStamp;
	}

	public void setLastUpdatedTimeStamp(java.util.Date lastUpdatedTimeStamp) {
		this.lastUpdatedTimeStamp = lastUpdatedTimeStamp;
	}

	public boolean isDefault() {
		return isDefault;
	}

	public void setDefault(boolean isDefault) {
		this.isDefault = isDefault;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public String getBghMandatoryFlag() {
		return bghMandatoryFlag;
	}

	public void setBghMandatoryFlag(String bghMandatoryFlag) {
		this.bghMandatoryFlag = bghMandatoryFlag;
	}

	public String getAliasAllocationName() {
		return aliasAllocationName;
	}

	public void setAliasAllocationName(String aliasAllocationName) {
		this.aliasAllocationName = aliasAllocationName;
	}

	public static class AllocationTypeComparator implements Comparator<AllocationType> {

		public int compare(AllocationType allocationType1, AllocationType allocationType2) {

			if (allocationType1 == allocationType2) {

				return 0;

			} else {

				if (allocationType1.getAllocationType() != null) {

					return allocationType1.getAllocationType().compareTo(allocationType2.getAllocationType());

				} else if (allocationType2.getAllocationType() != null) {

					return allocationType2.getAllocationType().compareTo(allocationType1.getAllocationType());

				} else {

					return 0;
				}
			}
		}
	}
}
