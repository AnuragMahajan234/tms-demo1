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

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

/**
 * @author arpan.badjatiya
 *
 */
@SuppressWarnings("serial")

@Entity
@Table(name="BILLINGSCALE")
@NamedQueries({
		@NamedQuery(name = BillingScale.SEARCH_BILLING_SCALE_BASED_ON_ID, query = BillingScale.QUERY_BILLING_SCALE_SEARCH_BASED_ON_ID),
		@NamedQuery(name = BillingScale.DELETE_BILLING_SCALE_BASED_ON_ID, query = BillingScale.QUERY_BILLING_SCALE_DELETE_BASED_ON_ID) })
public class BillingScale implements Serializable{
	
	public final static String SEARCH_BILLING_SCALE_BASED_ON_ID="SEARCH_BILLING_SCALE_BASED_ON_ID";
	public final static String QUERY_BILLING_SCALE_SEARCH_BASED_ON_ID="from BillingScale b where b.id = :id";
	
	public final static String DELETE_BILLING_SCALE_BASED_ON_ID="DELETE_BILLING_SCALE_BASED_ON_ID";
	public final static String QUERY_BILLING_SCALE_DELETE_BASED_ON_ID="DELETE BillingScale b where b.id = :id";
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="billing_rate")
	private int billingRate;
	
	@Column(name="created_id", updatable = false)
	private String createdId;
	
	@Column(name="creation_timestamp",updatable = false, insertable = true)
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

	public int getBillingRate() {
		return billingRate;
	}

	public void setBillingRate(int billingRate) {
		this.billingRate = billingRate;
	}

	/**
	 * @return the createdId
	 */
	public String getCreatedId() {
		return createdId;
	}

	/**
	 * @param createdId the createdId to set
	 */
	public void setCreatedId(String createdId) {
		this.createdId = createdId;
	}


	/**
	 * @return the creationTimestamp
	 */
	public Date getCreationTimestamp() {
		return creationTimestamp;
	}

	/**
	 * @param creationTimestamp the creationTimestamp to set
	 */
	public void setCreationTimestamp(Date creationTimestamp) {
		this.creationTimestamp = creationTimestamp;
	}

	/**
	 * @return the lastUpdatedId
	 */
	public String getLastUpdatedId() {
		return lastUpdatedId;
	}

	/**
	 * @param lastUpdatedId the lastUpdatedId to set
	 */
	public void setLastUpdatedId(String lastUpdatedId) {
		this.lastUpdatedId = lastUpdatedId;
	}

	/**
	 * @return the lastupdatedTimestamp
	 */
	public Date getLastupdatedTimestamp() {
		return lastupdatedTimestamp;
	}

	/**
	 * @param lastupdatedTimestamp the lastupdatedTimestamp to set
	 */
	public void setLastupdatedTimestamp(Date lastupdatedTimestamp) {
		this.lastupdatedTimestamp = lastupdatedTimestamp;
	}

}
