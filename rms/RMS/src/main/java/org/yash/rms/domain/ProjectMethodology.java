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
import javax.validation.constraints.NotNull;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

/**
 * @author arpan.badjatiya
 *
 */
@SuppressWarnings("serial")

@Entity
@Table(name="project_methodology")
@NamedQueries({
	@NamedQuery(
			name = ProjectMethodology.SEARCH_PROJECTMETHODOLOGY_BASED_ON_ID,
			query = ProjectMethodology.QUERY_FOR_SEARCH_PROJECTMETHODOLOGY_BASED_ON_ID
			),
			@NamedQuery(
					name = ProjectMethodology.DELETE_PROJECTMETHODOLOGY_BASED_ON_ID,
					query = ProjectMethodology.QUERY_FOR_DELETE_PROJECTMETHODOLOGY_BASED_ON_ID
					)
})
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ProjectMethodology implements Serializable{
	
	public final static String SEARCH_PROJECTMETHODOLOGY_BASED_ON_ID="SEARCH_PROJECTMETHODOLOGY_BASED_ON_ID";
	public final static String QUERY_FOR_SEARCH_PROJECTMETHODOLOGY_BASED_ON_ID="from ProjectMethodology b where b.id = :id";
	
	public final static String DELETE_PROJECTMETHODOLOGY_BASED_ON_ID="DELETE_PROJECTMETHODOLOGY_BASED_ON_ID";
	public final static String QUERY_FOR_DELETE_PROJECTMETHODOLOGY_BASED_ON_ID="DELETE ProjectMethodology b where b.id = :id";
	
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "id")
	    private Integer id;
	
	   @Column(name = "methodology", length = 256)
	    @NotNull
	    private String methodology;
	
	@Column(name="created_id", updatable = false)
	private String createdId;
	
	@Column(name="creation_timestamp",updatable = false, insertable = true)
	private Date creationTimestamp;
	
	@Column(name="lastupdated_id")
	private String	lastUpdatedId;
	
	@Column(name="lastupdated_timestamp")
	private Date lastupdatedTimestamp;
	
	public String getMethodology() {
		return methodology;
	}

	public void setMethodology(String methodology) {
		this.methodology = methodology;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
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
