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
@Table(name="VISA")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@NamedQueries({
	@NamedQuery(
			name = Visa.SEARCH_VISA_BASED_ON_ID,
			query = Visa.QUERY_FOR_SEARCH_VISA_BASED_ON_ID
			),
			@NamedQuery(
					name = Visa.DELETE_VISA_BASED_ON_ID,
					query = Visa.QUERY_FOR_DELETE_VISA_BASED_ON_ID
					)
})
public class Visa implements Serializable{
	
	public final static String SEARCH_VISA_BASED_ON_ID="SEARCH_VISA_BASED_ON_ID";
	public final static String QUERY_FOR_SEARCH_VISA_BASED_ON_ID="from Visa v where v.id = :id";
	
	public final static String DELETE_VISA_BASED_ON_ID="DELETE_VISA_BASED_ON_ID";
	public final static String QUERY_FOR_DELETE_VISA_BASED_ON_ID="DELETE Visa v where v.id = :id";
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="visa")
	private String visa;
	
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

	/**
	 * @return the visa
	 */
	public String getVisa() {
		return visa;
	}

	/**
	 * @param visa the visa to set
	 */
	public void setVisa(String visa) {
		this.visa = visa;
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
