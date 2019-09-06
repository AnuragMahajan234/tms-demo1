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

import org.codehaus.jackson.annotate.JsonBackReference;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;


@SuppressWarnings("serial")
@Entity
@Table(name="CUSTOMER_GROUP")
@NamedQueries({
	@NamedQuery(name = CustomerGroup.SEARCH_CUSTOMER_GROUP_BASED_ON_ID, query = CustomerGroup.QUERY_CUSTOMER_GROUP_SEARCH_BASED_ON_ID),
	@NamedQuery(name = CustomerGroup.DELETE_CUSTOMER_GROUP_BASED_ON_ID, query = CustomerGroup.QUERY_CUSTOMER_GROUP_DELETE_BASED_ON_ID),
	/*@NamedQuery(name = "CustomerGroup.findActiveCustomerGroupById", 
	query = "SELECT * FROM CustomerGroup WHERE  c.customerId = :customerId and active='Y'")*/
	})
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class CustomerGroup implements Serializable{
	
	public final static String SEARCH_CUSTOMER_GROUP_BASED_ON_ID="SEARCH_CUSTOMER_GROUP_BASED_ON_ID";
	public final static String QUERY_CUSTOMER_GROUP_SEARCH_BASED_ON_ID="from CustomerGroup c where c.customerId = :customerId";
	
	public final static String DELETE_CUSTOMER_GROUP_BASED_ON_ID="DELETE_CUSTOMER_GROUP_BASED_ON_ID";
	public final static String QUERY_CUSTOMER_GROUP_DELETE_BASED_ON_ID="DELETE CustomerGroup c where c.groupId = :groupId";
	
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="group_id")
	private int groupId;
	
	
@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "customer_id", referencedColumnName = "id")
@JsonBackReference
private Customer customerId;

@Column(name="active")
private String active;
	
	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}
	
	@Column(name="group_name")
	private String customerGroupName;
	
	@Column(name="created_id", updatable = false)
	private String createdId;
	
	@Column(name="creation_timestamp",updatable = false, insertable = true)
	private Date creationTimestamp;
	
	@Column(name="lastupdated_id")
	private String	lastUpdatedId;
	
	@Column(name="lastupdated_timestamp")
	private Date lastupdatedTimestamp;

	/*public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}*/

	@Column(name="pdl_email_id")
	private String groupEmail;
	public String getCustGroupemailId() {
		return groupEmail;
	}
	public void setCustGroupemailId(String groupEmail) {
		this.groupEmail = groupEmail;
	}

	public String getCustomerGroupName() {
		return customerGroupName;
	}

	public Customer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Customer customerId) {
		this.customerId = customerId;
	}

	public void setCustomerGroupName(String customerGroupName) {
		this.customerGroupName = customerGroupName;
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

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public void setLastupdatedTimestamp(Date lastupdatedTimestamp) {
		this.lastupdatedTimestamp = lastupdatedTimestamp;
	}

	/*@Override
	public String toString() {
		return "CustomerGroup [groupId=" + groupId + ", active=" + active
				+ ", customerGroupName=" + customerGroupName + ", createdId=" + createdId + ", creationTimestamp="
				+ creationTimestamp + ", lastUpdatedId=" + lastUpdatedId + ", lastupdatedTimestamp="
				+ lastupdatedTimestamp + "]";
	}*/

	
	
}
