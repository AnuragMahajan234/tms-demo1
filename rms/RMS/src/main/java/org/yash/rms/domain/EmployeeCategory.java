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


@SuppressWarnings("serial")

@Entity
@Table(name="employee_category")
@NamedQueries({
	@NamedQuery(
			name = EmployeeCategory.SEARCH_EmployeeCategory_BASED_ON_ID,
			query = EmployeeCategory.QUERY_FOR_SEARCH_EmployeeCategory_BASED_ON_ID
			),
			@NamedQuery(
					name = EmployeeCategory.DELETE_EmployeeCategory_BASED_ON_ID,
					query = EmployeeCategory.QUERY_FOR_DELETE_EmployeeCategory_BASED_ON_ID
					)
})
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class EmployeeCategory implements Serializable{
	
	public final static String SEARCH_EmployeeCategory_BASED_ON_ID="SEARCH_EmployeeCategory_BASED_ON_ID";
	public final static String QUERY_FOR_SEARCH_EmployeeCategory_BASED_ON_ID="from EmployeeCategory o where o.id = :id";
	
	public final static String DELETE_EmployeeCategory_BASED_ON_ID="DELETE_EmployeeCategory_BASED_ON_ID";
	public final static String QUERY_FOR_DELETE_EmployeeCategory_BASED_ON_ID="DELETE EmployeeCategory o where o.id = :id";
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="employeecategory_name")
	private String employeecategoryName;
	
	

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
	 * @return the createdId
	 */
	
	public String getEmployeecategoryName() {
		return employeecategoryName;
	}

	public void setEmployeecategoryName(String employeecategoryName) {
		this.employeecategoryName = employeecategoryName;
	}
	
	
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
