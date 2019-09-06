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
 * @author arpan.badjatiya
 *
 */
@SuppressWarnings("serial")

@Entity
@Table(name="CURRENCY")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@NamedQueries({
		@NamedQuery(name = Currency.SEARCH_CURRENCY_BASED_ON_ID, query = Currency.QUERY_CURRENCY_SEARCH_BASED_ON_ID),
		@NamedQuery(name = Currency.DELETE_CURRENCY_BASED_ON_ID, query = Currency.QUERY_CURRENCY_DELETE_BASED_ON_ID) })
public class Currency implements Serializable{
	
	public final static String SEARCH_CURRENCY_BASED_ON_ID="SEARCH_CURRENCY_BASED_ON_ID";
	public final static String QUERY_CURRENCY_SEARCH_BASED_ON_ID="from Currency c where c.id = :id";
	
	public final static String DELETE_CURRENCY_BASED_ON_ID="DELETE_CURRENCY_BASED_ON_ID";
	public final static String QUERY_CURRENCY_DELETE_BASED_ON_ID="DELETE Currency c where c.id = :id";
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="currency_name")
	private String currencyName;
	
	@Column(name="created_id", updatable = false)
	private String createdId;
	
	@Column(name="creation_timestamp",updatable = false, insertable = true)
	private Date creationTimestamp;
	
	@Column(name="lastupdated_id")
	private String	lastUpdatedId;
	
	@Column(name="lastupdated_timestamp")
	private Date lastupdatedTimestamp;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCurrencyName() {
		return currencyName;
	}

	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
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
