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
@Table(name="INVOICE_BY")
@NamedQueries({
		@NamedQuery(name = InvoiceBy.SEARCH_INVOICE_BY_BASED_ON_ID, query = InvoiceBy.QUERY_INVOICE_BY_SEARCH_BASED_ON_ID),
		@NamedQuery(name = InvoiceBy.DELETE_INVOICE_BY_BASED_ON_ID, query = InvoiceBy.QUERY_INVOICE_BY_DELETE_BASED_ON_ID) })
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class InvoiceBy implements Serializable{
	
	public final static String SEARCH_INVOICE_BY_BASED_ON_ID="SEARCH_INVOICE_BY_BASED_ON_ID";
	public final static String QUERY_INVOICE_BY_SEARCH_BASED_ON_ID="from InvoiceBy ib where ib.id = :id";
	
	public final static String DELETE_INVOICE_BY_BASED_ON_ID="DELETE_INVOICE_BY_BASED_ON_ID";
	public final static String QUERY_INVOICE_BY_DELETE_BASED_ON_ID="DELETE InvoiceBy ib where ib.id = :id";
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="name")
	private String name;
	
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
