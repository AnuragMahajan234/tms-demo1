/**
 * 
 */
package org.yash.rms.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonBackReference;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

/**
 * @author arpan.badjatiya
 *
 */
@SuppressWarnings("serial")

@Entity
@Table(name="DESIGNATIONS")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@NamedQueries({
		@NamedQuery(name = Designation.SEARCH_DESIGNATION_BASED_ON_ID, query = Designation.QUERY_DESIGNATION_BASED_ON_ID),
		@NamedQuery(name = Designation.DELETE_DESIGNATION_BASED_ON_ID, query = Designation.QUERY_DEGINATION_DELETE_BASED_ON_ID) })
public class Designation implements Serializable{
	
	public final static String SEARCH_DESIGNATION_BASED_ON_ID="SEARCH_DESIGNATION_BASED_ON_ID";
	public final static String QUERY_DESIGNATION_BASED_ON_ID="from Designation d where d.id = :id";
	
	public final static String DELETE_DESIGNATION_BASED_ON_ID="DELETE_DESIGNATION_BASED_ON_ID";
	public final static String QUERY_DEGINATION_DELETE_BASED_ON_ID="DELETE Designation d where d.id = :id";
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="designation_name")
	private String designationName;
	
	@Column(name="created_id", updatable = false)
	private String createdId;
	
	@Column(name="creation_timestamp",updatable = false, insertable = true)
	private Date creationTimestamp;
	
	@Column(name="lastupdated_id")
	private String	lastUpdatedId;
	
	@Column(name="lastupdated_timestamp")
	private Date lastupdatedTimestamp;

	
    @OneToMany(mappedBy = "designationId", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonBackReference
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDesignationName() {
		return designationName;
	}

	public void setDesignationName(String designationName) {
		this.designationName = designationName;
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
